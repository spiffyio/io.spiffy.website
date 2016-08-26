# @prepros-prepend constants.coffee
# @prepros-prepend functions.coffee
# @prepros-prepend extension.coffee

Dropzone.options.dzForm = {
  paramName: 'file',
  maxFiles: 1,
  maxFilesize: 200,
  accept: (file, done) ->
    done()
    return
  success: (file, response) ->
    $('#dz-form').slideUp()
    form = $ 'form.submit'
    preview = form.find 'div.preview'
    video = false
    for type in response.types
      video = video or type.equalsIgnoreCase('MP4') or type.equalsIgnoreCase('WEBM')
    if video
      video = $ document.createElement 'video'
      video.attr 'muted', true
      video.attr 'loop', true
      for type in response.types
        if type.equalsIgnoreCase 'MP4' or type.equalsIgnoreCase 'WEBM'
          source = $ document.createElement 'source'
          source.attr 'src', response.url + type.toLowerCase()
          source.attr 'type', 'video/' + type.toLowerCase()
          video.append source
        else
          img = $ document.createElement 'img'
          img.attr 'src', response.url + type.toLowerCase()
          video.append img
      preview.prepend video
    else
      img = $ document.createElement 'img'
      img.attr 'src', response.url + response.types[0].toLowerCase()
      preview.prepend img
    form.slideDown()
    media = form.find 'input[name="media"]'
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

  $('form.comment').spiffy().options
    success: () -> refresh()

  $('form.submit').spiffy().options
    success: () -> go '/'

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

  $('video[data-autoplay="true"]').each () ->
    video = $ this
    if (not video.is ':in-viewport') or (not video.is ':in-viewport(' + (video[0].getBoundingClientRect().bottom - video[0].getBoundingClientRect().top) + ')')
      if not video[0].paused
        video[0].pause()
        video.parents('div.video:first').addClass 'paused'
    else if video[0].paused
      video[0].play()
      video.parents('div.video:first').removeClass 'paused'
    return

  $(document).on 'click', 'video', (e) ->
    video = $ this
    if video[0].paused
      video[0].play()
      video.parents('div.video:first').removeClass 'paused'
    else
      video[0].pause()
      video.parents('div.video:first').addClass 'paused'
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

  form = $ 'form.load-posts'
  input = form.find 'input[name="after"]'
  start = input.val()
  input.val json.next
  history.replaceState json, 'SPIFFY.io', location.pathname + '?start=' + start
  return

loadPosts = (posts) ->
  for i in [0...posts.length]
    post = posts[i]
    panel = $ document.createElement 'div'
    panel.addClass 'panel'

    video = false
    for type in post.types
      video = video or type.equalsIgnoreCase('MP4') or type.equalsIgnoreCase('WEBM')
    if video
      video = $ document.createElement 'video'
      video.attr 'muted', true
      video.attr 'loop', true
      video.attr 'preload', 'none'
      if post.types[0].equalsIgnoreCase 'PNG'
        video.attr 'poster', post.url + post.types[0].toLowerCase()
      for type in post.types
        if type.equalsIgnoreCase 'MP4' or type.equalsIgnoreCase 'WEBM'
          source = $ document.createElement 'source'
          source.attr 'src', post.url + type.toLowerCase()
          source.attr 'type', 'video/' + type.toLowerCase()
          video.append source
        else if type.equalsIgnoreCase 'GIF'
          img = $ document.createElement 'img'
          img.attr 'data-src', post.url + type.toLowerCase()
          video.append img
      div = $ document.createElement 'div'
      div.addClass 'video'
      div.addClass 'paused'
      div.append video
      panel.prepend div
    else
      img = $ document.createElement 'img'
      img.attr 'src', post.url + post.types[0].toLowerCase()
      panel.prepend img

    source = $ document.createElement 'div'
    source.addClass 'source'

    link = $ document.createElement 'a'
    link.attr 'href', '/stream/' + post.postId
    link.html post.title
    source.html link

    panel.append source

    cols = 3
    if ($(window).width() < Width.xl) then cols = 2
    if ($(window).width() < Width.md) then cols = 1

    col = $('.col[data-index="' + (i % cols) + '"]')

    last = col.find '.panel:last'
    index = if last? and (last.is('.panel')) then last.data('index') + cols else col.data 'index'

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
  $('video').each () ->
    video = $ this
    top = video[0].getBoundingClientRect().top
    source = video.parents('.panel:first').find '.source'
    if (top < 75) or (not video.is ':in-viewport') or (not source.is ':in-viewport')
      if not video[0].paused
        video[0].pause()
        video.parents('div.video:first').addClass 'paused'
    else if video[0].paused
      video[0].play()
      if not video[0].paused then video.parents('div.video:first').removeClass 'paused'
    return
  return

$(window).scroll (e) ->
  if $(window).scrollTop() > 400
    $('div.header').addClass 'hidden'
  else
    $('div.header').removeClass 'hidden'

  $('div.col').each () ->
    col = $(this)
    panel = col.find('div.panel:last')
    if not panel? then return
    if panel.is ':in-viewport'
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
          if reason.equalsIgnoreCase 'type'
            reason = 'invalid ' + field.validationResult[0].assert.requirements
          else if reason.equalsIgnoreCase 'equalto'
            reason = 'unmatched ' + field.$element.parents('form:first').find(field.validationResult[0].assert.requirements).attr 'placeholder'
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
