# @prepros-prepend constants.coffee
# @prepros-prepend functions.coffee
# @prepros-prepend extension.coffee

Dropzone.options.dzForm = {
  paramName: 'file',
  maxFiles: 1,
  maxFilesize: 20,
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
  $('div.header').mouseenter () ->
    if $(this).is ':hover'
      $(this).removeClass 'hidden'
    return

  $('div.header').mouseleave () ->
    header = $(this)
    func = () ->
      if (not header.is(':hover')) and ($(window).scrollTop() > 400)
        header.addClass 'hidden'
      return
    setTimeout(func, 500)
    return

  $('[data-modal]').click (e) ->
    openModal $(this).data('modal')
    return

  $('[data-uri]').click (e) ->
    go $(this).data('uri')
    return

  $('[data-post]').click (e) ->
    go '/stream/' + $(this).data('post')
    return

  if $('input[name="fingerprint"]')
    hash = fingerprint()
    if hash?
      $('input[name="fingerprint"]').each () ->
        $(this).val hash
        return

  $('form:not(#dz-form)').submit (e) ->
    preventDefault e
    form = $ this
    form.spiffy().submit()
    return

  $('form.load-posts').spiffy().options
    success: (form, json) ->
      if not json.posts?
        form.spiffy().disable()
      else
        load(json)

  $('form[data-return-uri]').spiffy().options
    success: (form) -> go(form.data('return-uri'))

  $('a[data-form]').click (e) ->
    preventDefault e
    form = $ 'form.' + $(this).data('form')
    form.submit()
    return

  $('a[data-session-id]').click (e) ->
    preventDefault e
    form = $ 'form.logout'
    form.find('input[name="session"]').val($(this).data('session-id'))
    form.submit()
    return

  $('form.logout').spiffy().options
    success: (form) ->
      input = form.find 'input[name="session"]'
      session = input.val()
      button = $ '[data-session-id="' + session + '"]'
      button.parent().parent().slideUp()
      return


  $('.close').click (e) ->
    closeModal()
    return

  $('.modal-overlay').click (e) ->
    if $(e.target).hasClass 'modal-overlay' then closeModal()
    return

  adjustColumns()

  $('div.input').each () ->
    div = $ this
    input = div.find 'input'
    label = $ document.createElement 'label'
    label.html input.attr('placeholder')
    div.prepend label
    span = $ document.createElement 'span'
    span.addClass 'bt-flabels__error-desc'
    span.html Spiffy.firstDefined div.data('error-message'), 'error'
    div.append span
    return

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

load = (json) ->
  loadPosts json.posts

  $('[data-post]').click (e) ->
    go '/stream/' + $(this).data('post')
    return

  start = $('form.load-posts').find('input[name="after"]').val()

  $('form.load-posts').find('input[name="after"]').val json.next
  adjustColumns()

  history.replaceState json, 'SPIFFY.io', '/stream?start=' + start
  return

loadPosts = (posts) ->
  for i in [0...posts.length]
    post = posts[i]
    panel = $ document.createElement 'div'
    panel.addClass 'panel'
    panel.attr 'data-post', post.postId

    img = $ document.createElement 'img'
    img.attr 'src', post.url
    panel.append img

    footer = $ document.createElement 'div'
    footer.addClass 'footer'
    footer.html post.title
    panel.append footer

    col = $('.col[data-index="' + (i % 3) + '"]')
    col.append panel
  return

adjustColumns = () ->
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

$(window).scroll (e) ->
  if $(window).scrollTop() > 400
    $('div.header').addClass 'hidden'
  else
    $('div.header').removeClass 'hidden'

  $('div.col').each () ->
    col = $(this)
    panel = col.find('div.panel:last')
    if (not panel?) or (not panel.offset()?) then return
    if panel.offset().top - panel.height() < $(window).scrollTop()
      $('form.load-posts').submit()
    return
  return

$(window).on 'beforeunload', () ->
  if location.search.contains 'start'
    $(window).scrollTop 0
  return

(($) ->
  'use strict'

  floatingLabel = (onload) ->
    $input = $(this)

    # Check for value onload
    if onload
      $.each $('.input input'), (index, value) ->
        $current_input = $(value)
        if $current_input.val()
          $current_input.closest('.input').addClass 'bt-flabel__float'
          return

    setTimeout (->
      if $input.val()
        $input.closest('.input').addClass 'bt-flabel__float'
      else
        $input.closest('.input').removeClass 'bt-flabel__float'
      return
    ), 1
    return

  $('.input input').keydown floatingLabel
  $('.input input').change floatingLabel

  window.addEventListener 'load', floatingLabel(true), false

  $('form').each () ->
    form = $ this
    if not form.find('input').length then return
    form.attr 'data-parsley-errors-messages-disabled', ''
    form.parsley().on 'form:error', ->
      $.each this.fields, (key, field) ->
        if field.validationResult isnt true
          reason = field.validationResult[0].assert.name
          reason = if reason.equalsIgnoreCase 'type' then 'invalid ' + field.validationResult[0].assert.requirements else reason
          div = field.$element.parent()
          div.find('span').html reason
          div.addClass 'bt-flabels__error'
          return
      return
    form.parsley().on 'field:validated', ->
      if this.validationResult is true
        div = this.$element.parent()
        div.removeClass 'bt-flabels__error'
        return
      else
        div = this.$element.parent()
        div.addClass 'bt-flabels__error'
        return
  return
) jQuery
