Spiffy.functions =
  log: (level, value) ->
    if level <= Spiffy.c.config.LOGLEVEL
      console.log value
    return
  firstDefined: () ->
    defined = argument for argument in arguments by -1 when argument?
    if defined? then return defined
    return undefined
  click: (selector, handler, preventDefault = true) ->
    $(document).on 'click', selector, (e) ->
      if preventDefault then e.preventDefault()
      handler e, $(this)
      return
    return
  element:
    template: (name) ->
      selector = '[data-template="$name"]'.replace '$name', name
      return $ selector
  timeout:
    simple: (timeout, call) ->
      setTimeout call, timeout
      return
    retry: (attempt, call) ->
      if attempt >= Spiffy.c.retry.MAX_COUNT
        Spiffy.f.log Spiffy.c.enum.loglevel.ERROR, 'max retry attempts exceeded... ' + call
        return
      setTimeout call, Spiffy.c.retry.TIMEOUT * attempt * attempt
      return

Spiffy.f = Spiffy.functions

Spiffy.firstDefined = Spiffy.f.firstDefined

preventDefault = (e) ->
  e.preventDefault()
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
      e.preventDefault()
      action()
  dialog.modal 'show'
  return

go = (url) ->
  location.href = url

refresh = ->
  location.reload true
  window.location = self.location

blank = (v) ->
  if not defined v then return true
  v is ''

defined = (v) ->
  if typeof v is 'undefined' then return false
  v isnt 'undefined'

contains = (s, v) ->
  index = s.indexOf v
  index > -1

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
