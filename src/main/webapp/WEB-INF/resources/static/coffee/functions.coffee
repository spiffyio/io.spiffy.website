Spiffy.functions = {}
Spiffy.f = Spiffy.functions

Spiffy.f.log = (level, value) ->
  if level <= Spiffy.c.config.LOGLEVEL
    console.log value
  return

Spiffy.f.authenticated = ->
  return $('meta[name="account"]').length isnt 0

Spiffy.f.first = ->
  defined = argument for argument in arguments by -1 when argument?
  return defined

Spiffy.f.firstDefined = Spiffy.f.first
Spiffy.firstDefined = Spiffy.f.firstDefined

Spiffy.f.click = (selector, handler, preventDefault = true) ->
  $(document).on 'click', selector, (e) ->
    if preventDefault then Spiffy.f.prevent e
    handler e, $(this)
    return
  return

Spiffy.f.prevent = (e) ->
  e.preventDefault()
  return

Spiffy.f.element = {}

Spiffy.f.element.template = (name) ->
  selector = '[data-template="$name"]'.replace '$name', name
  return $ selector

Spiffy.f.timeout = {}

Spiffy.f.timeout.simple = (timeout, call) ->
  setTimeout call, timeout
  return

Spiffy.f.timeout.retry = (attempt, call) ->
  if attempt >= Spiffy.c.retry.MAX_COUNT
    Spiffy.f.log Spiffy.c.enum.loglevel.ERROR, 'max retry attempts exceeded... ' + call
    return
  setTimeout call, Spiffy.c.retry.TIMEOUT * attempt * attempt
  return

Spiffy.f.update = {}

Spiffy.f.update.notifications = (count) ->
  span = $ 'span.notification-count'
  if count is 0
    document.title = 'SPIFFY.io'
    span.html ''
  else
    document.title = '(' + count + ') SPIFFY.io'
    span.html count
  return

Spiffy.f.update.poll = (etag = Spiffy.c.param.ETAG, attempt = Spiffy.c.param.ATTEMPT) ->
  if not Spiffy.f.authenticated()
    Spiffy.f.log Spiffy.c.enum.loglevel.INFO, 'polling disabled...'
    return
  Spiffy.f.log Spiffy.c.enum.loglevel.INFO, 'polling...' + if etag? then ' [etag: ' + etag + ']' else ''
  $.get
    url: '/longpoll'
    dataType: 'json'
    beforeSend: (xhr) ->
      if etag?
        xhr.setRequestHeader 'If-None-Match', etag
      return
    success: (data, status, xhr) ->
      Spiffy.f.update.notifications data.notifications
      Spiffy.f.update.poll xhr.getResponseHeader 'ETag'
      return
    error: ->
      Spiffy.f.timeout.retry attempt, ->
        Spiffy.f.update.poll etag, attempt+1
        return
      return

preventDefault = (e) ->
  Spiffy.f.prevent e
  return

overrideHandler = refresh
handler = (handler) ->
  overrideHandler = handler if defined handler
  return refresh if not defined overrideHandler
  return overrideHandler

confirmation = (title, action) ->
  dialog = $ '#confirmation'
  dialog
    .find '.modal-title'
    .text title
  dialog
    .find '.continue'
    .on 'click', (e) ->
      Spiffy.f.prevent e
      action()
  dialog.modal 'show'
  return

go = (url) ->
  location.href = url

refresh = ->
  location.reload true
  window.location = self.location

defined = (v) ->
  if typeof v is 'undefined' then return false
  v isnt 'undefined'

initModalSize = ->
  height = ($(window).height() - 125) + 'px'
  $('.modal-body').css 'max-height', height
  height

dataURItoBlob = (dataURI) ->
  parts = dataURI.split ','
  meta = parts[0]
  data = parts[1]

  byteString = if meta.containsIgnoreCase 'base64' then atob data else unescape data
  mimeType = meta.split(':')[1].split(';')[0]

  bytes = new Uint8Array byteString.length
  for c, i in byteString.split ''
    bytes[i] = c.charCodeAt 0

  new Blob [bytes], { type: mimeType }
