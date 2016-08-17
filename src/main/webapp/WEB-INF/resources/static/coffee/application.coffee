# @prepros-prepend constants.coffee
# @prepros-prepend functions.coffee
# @prepros-prepend extension.coffee

Dropzone.options.dzForm = {
  paramName: 'file',
  maxFilesize: 2,
  accept: (file, done) ->
    done()
    return
  success: (file, response) ->
    media = $('form.submit').find 'input[name="media"]'
    if media.val().length
      media.val media.val() + ',' + response.id
    else
      media.val response.id
    return
}

$(document).ready (e) ->
  $('div.header.hideable').mouseenter () ->
    if $(this).is ':hover'
      hAnimate '0'
    return

  $('div.header.hideable').mouseleave () ->
    header = $(this)
    func = () ->
      if not header.is ':hover'
        hAnimate '-3em'
      return
    setTimeout(func, 500)
    return

  $('[data-modal]').click (e) ->
    openModal $(this).data('modal')
    return

  $('[data-uri]').click (e) ->
    go $(this).data('uri')
    return

  if $('input[name="fingerprint"]')
    hash = fingerprint()
    if hash?
      $('input[name="fingerprint"]').each () ->
        $(this).val hash
        return

  $('form.login').submit (e) ->
    preventDefault e
    form = $(this)
    form.spiffySubmit '/login', $(this).spiffyFormData(['email', 'password', 'fingerprint', 'g-recaptcha-response']), () -> go(form.data('returnUri'))
    return

  $('form.register').submit (e) ->
    preventDefault e
    form = $(this)
    form.spiffySubmit '/register', $(this).spiffyFormData(['username', 'email', 'password', 'fingerprint', 'g-recaptcha-response']), () -> go(form.data('returnUri'))
    return

  $('form.submit').submit (e) ->
    preventDefault e
    form = $(this)
    form.spiffySubmit '/submit', $(this).spiffyFormData(['media', 'title', 'description', 'idempotentId']), () -> go('/')
    return

  $('.close').click (e) ->
    closeModal()
    return

  $('.modal-overlay').click (e) ->
    if $(e.target).hasClass 'modal-overlay' then closeModal()
    return

  $('.col').each (i) ->
    $(this).attr 'data-index', i
    offset = i
    $(this).find('.panel').each (i) ->
      $(this).attr 'data-index', $('.col').length * i + offset
      return
    return

  if ($(window).width() < Width.xl) then emptyColumn 2
  if ($(window).width() < Width.md) then emptyColumn 1

  return

hAnimate = (top) ->
  $('div.header').find('div.menu').finish().animate {top: top}
  return

openModal = (modal) ->
  if not $('[data-modal-id="' + modal + '"]').length
    return
  $('[data-modal-id]').not('[data-modal-id="' + modal + '"]').hide 0
  $('[data-modal-id="' + modal + '"]').show 0
  $('.modal-overlay').show 0
  $('.modal').slideDown 500
  return

closeModal = () ->
  $('.modal').slideUp 250, () ->
    $('[data-modal-id]').hide 0
    $('.modal-overlay').hide 0
  return

fingerprint = () ->
  fp = sessionStorage.getItem 'fingerprint'
  if fp?
    return fp
  options = { excludeAdBlock : true, excludeAvailableScreenResolution : true }
  new Fingerprint2(options).get (hash) ->
    sessionStorage.setItem 'fingerprint', hash
    $('input[name="fingerprint"]').each () ->
      $(this).val hash
      return
    return
  return null

emptyColumn = (i) ->
  col = $('.col[data-index="' + i + '"]')
  panels = col.find '.panel'
  if not panels.length
    return
  panels.each () ->
    panel = $(this)
    index = panel.data 'index'
    index = if (i is 1) or (index % 2 is 0) then index - 1 else index - 2
    panel.detach().insertAfter '.panel[data-index="' +  index + '"]'
    return
  sortColumn 1
  return

fillColumn = (i) ->
  col = $('.col[data-index="' + i + '"]')
  panels = col.find '.panel'
  if panels.length
    return
  col = $('.col[data-index!="' + i + '"]')
  panels = col.find '.panel'
  panels.each () ->
    panel = $(this)
    if panel.data('index') % 3 isnt i then return
    panel.detach().appendTo '.col[data-index="' + i + '"]'
    return
  sortColumn i
  return

sortColumn = (i) ->
  console.log 'sort'
  col = $('.col[data-index="' + i + '"]')
  panels = col.find '.panel'
  sorted = panels.sort (a, b) ->
    return $(a).data('index') - $(b).data('index')
  col.html sorted
  return

$(window).resize (e) ->
  width = $(window).width()
  if (width > window.pWidth) and (width >= Width.md)
    $('.subheader').show()
    fillColumn 1
  if (width < window.pWidth) and (width < Width.md)
    $('.subheader').hide()
    $('.hamburger').removeClass 'active'
    emptyColumn 1
  if (width > window.pWidth) and (width >= Width.xl)
    fillColumn 2
  if (width < window.pWidth) and (width < Width.xl)
    emptyColumn 2
  window.pWidth = width
  return
