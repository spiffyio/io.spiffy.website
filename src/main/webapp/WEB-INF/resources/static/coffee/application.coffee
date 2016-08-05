# @prepros-prepend constants.coffee
# @prepros-prepend functions.coffee
# @prepros-prepend extension.coffee

Dropzone.options.dzForm = {
  paramName: 'file',
  maxFilesize: 2,
  accept: (file, done) ->
    done()
}

$(document).ready (e) ->
  $('div.header').mouseenter () ->
    if $(this).is ':hover'
      hAnimate '0'
    return

  $('div.header').mouseleave () ->
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

  $('form.sign-up').submit (e) ->
    preventDefault e
    $(this).spiffySubmit '/signup', $(this).spiffyFormData(['username', 'email', 'password']), handler()
    return

  $('.close').click (e) ->
    closeModal()
    return

  $('.modal-overlay').click (e) ->
    if $(e.target).hasClass 'modal-overlay' then closeModal()
    return

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
