recaptchaCallback = () ->
  $('.g-recaptcha').each (i, e) ->
    grecaptcha.render e, { 'sitekey': '6LeSviITAAAAAFC9aCd6CAmWFqLoIpzw174jMc-i' }
    return
  return

preventDefault = (e) ->
  e.preventDefault()
  return

overrideHandler = refresh
handler = (handler) ->
  overrideHandler = handler if defined handler
  return refresh if not defined overrideHandler
  return overrideHandler

medium = (img) ->
  quality img, 'm', ['a', 'l'], () ->
    high img
    return
  return

high = (img) ->
  quality img, 'h', ['a', 'l', 'm']
  return

quality = (img, q, old = ['a', 'l', 'm', 'h'], callback) ->
    src = img.attr 'src'
    for o in old
      src = src.replace 'q=' + o, 'q=' + q
    image = new Image()
    image.onload ->
      img
        .remove 'lazy'
        .attr 'src', src
      if callback? then callback()
      return
    image.src = src
    return

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
