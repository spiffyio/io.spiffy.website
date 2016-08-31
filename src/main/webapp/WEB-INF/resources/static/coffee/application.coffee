# @prepros-prepend constants.coffee
# @prepros-prepend functions.coffee
# @prepros-prepend extension.coffee

addedfile = (file) ->
  if not file.accepted?
    func = () ->
      addedfile file
      return
    setTimeout func, 10
    return

  if not file.accepted then return

  $('#dz-form').hide()
  form = $ 'form.submit'

  preview = form.find 'div.preview'
  div = $ document.createElement 'div'

  src = URL.createObjectURL(file)
  form.attr 'data-media-src', src
  form.attr 'data-media-type', file.type

  if file.type.containsIgnoreCase 'video'
    form.spiffy().loading 'header', true, 30000
    div.addClass 'video'
    div.addClass 'paused'

    video = $ document.createElement 'video'
    video.attr 'muted', true
    video.attr 'loop', true
    video.attr 'autoplay', false

    source = $ document.createElement 'source'
    source.attr 'type', file.type
    source.attr 'src', src
    video.append source

    div.append video
  else
    form.spiffy().loading 'header', true, 5000
    img = $ document.createElement 'img'
    img.attr 'src', src
    div.append img
  preview.prepend div
  form.slideDown()
  return

Dropzone.options.dzForm = {
  paramName: 'file',
  maxFiles: 1,
  maxFilesize: 200,
  uploadMultiple: false,
  createImageThumbnails: false,
  autoProcessQueue: true,
  accept: (file, done) ->
    done()
    return
  init: () ->
    this.on 'addedfile', (file) ->
      addedfile file
      return
    this.on 'uploadprogress', (file) ->
      console.log file.upload.progress
      return
  success: (file, response) ->
    form = $ 'form.submit'
    form.spiffy().enable().loading 'header', false
    media = form.find 'input[name="media"]'
    media.val response.name
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

  $('[data-unprocessed]').each () ->
    div = $ this
    post = div.data 'unprocessed'
    src = sessionStorage.getItem 'src:' + post
    type = sessionStorage.getItem 'type:' + post

    if not (src? and type?) then return

    if type.containsIgnoreCase 'video'
      div.addClass 'video'
      div.addClass 'paused'

      video = $ document.createElement 'video'
      video.attr 'muted', true
      video.attr 'loop', true
      video.attr 'autoplay', false

      source = $ document.createElement 'source'
      source.attr 'type', type
      source.attr 'src', src
      video.append source

      div.html video
    else if type.containsIgnoreCase 'image'
      img = $ document.createElement 'img'
      img.attr 'src', src
      div.html img
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
    success: (form, data) ->
      sessionStorage.setItem 'src:' + data.name, form.data('media-src')
      sessionStorage.setItem 'type:' + data.name, form.data('media-type')
      go '/stream/' + data.name

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

  $('form.action').spiffy().options
    success: (form) ->
      div = form.parents 'div.actions:first'
      button = div.find 'button.success'
      if button? and button.is 'button.success'
        button.animate { opacity: 1 }, 500, () ->
          button.animate { opacity: 0 }, 1500
      return

  $('div.actions').find('button').each (e) ->
    button = $ this
    form = button.parents('div.actions:first').find 'form'
    button.click (e) ->
      input = form.find 'input[name="action"]'
      action = button.data 'action'
      input.val action
      if action.equalsIgnoreCase 'delete'
        form.spiffy().options
          success: () ->
            go '/'
            return
      form.submit()
      return
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

  $(document).on 'click', '[data-go]', (e) ->
    button = $ this
    go button.data('go') + location.search
    return

  $(document).on 'click', 'video', (e) ->
    video = $ this
    video.attr 'data-clicked', true
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
  input.val json.next
  return

loadPosts = (posts) ->
  for i in [0...posts.length]
    post = posts[i]
    panel = $ document.createElement 'div'
    panel.addClass 'panel'
    panel.attr 'data-post-id', post.postId

    centeryo = $ document.createElement 'div'
    centeryo.addClass 'centeryo'

    div = $ document.createElement 'div'
    div.addClass 'mediayo'

    content = post.content
    if content.type.equalsIgnoreCase 'video'
      div.addClass 'video'
      div.addClass 'paused'

      video = $ document.createElement 'video'
      video.attr 'muted', true
      video.attr 'loop', true
      video.attr 'poster', content.poster

      source = $ document.createElement 'source'
      source.attr 'src', content.mp4
      source.attr 'type', 'video/mp4'
      video.append source

      source = $ document.createElement 'source'
      source.attr 'src', content.webm
      source.attr 'type', 'video/webm'
      video.append source

      if content.gif?
        img = $ document.createElement 'img'
        img.attr 'src', content.gif
        video.append img

      div.prepend video
    else if content.type.equalsIgnoreCase 'image'
      img = $ document.createElement 'img'
      img.attr 'src', content.thumbnail
      div.prepend img
    else
      template = Handlebars.compile($('[data-template="panel-ad"]').html())
      div.append template({ })

    centeryo.html div
    panel.prepend centeryo

    if content.type.equalsIgnoreCase 'ad'
      template = Handlebars.compile($('[data-template="panel-ad-source"]').html())
    else
      template = Handlebars.compile($('[data-template="panel-source"]').html())

    panel.append template({ post: post })

    cols = 3
    if ($(window).width() < Width.xl) then cols = 2
    if ($(window).width() < Width.md) then cols = 1

    col = $('.col[data-index="' + (i % cols) + '"]')

    last = col.find '.panel:last'
    index = if last? and (last.is('.panel')) then last.data('index') + cols else col.data 'index'
    panel.attr 'data-index', index

    col.append panel

    if content.type.equalsIgnoreCase 'ad' then `(adsbygoogle = window.adsbygoogle || []).push({});`
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

fillColumn = (i, cols) ->
  col = $('.col[data-index="' + i + '"]')
  panels = col.find '.panel'
  if panels.length
    return
  col = $('.col[data-index!="' + i + '"]')
  panels = col.find '.panel'
  panels.each () ->
    panel = $(this)
    if panel.data('index') % cols isnt i then return
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
    fillColumn 1, 2
  if (width < window.pWidth) and (width < Width.md)
    $('.subheader').hide()
    $('.hamburger').removeClass 'active'
    emptyColumn 1
  if (width > window.pWidth) and (width >= Width.xl)
    emptyColumn 1
    fillColumn 1, 3
    fillColumn 2, 3
  if (width < window.pWidth) and (width < Width.xl)
    emptyColumn 2
  window.pWidth = width
  return

$(window).scroll (e) ->
  $('video').each () ->
    video = $ this
    if video.data 'clicked' then return
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
  form = $ 'form.load-posts'
  if not (form? and form.is 'form.load-posts') then return
  col = $ '.col[data-index="0"]'
  panel = col.find '.panel:in-viewport:first'
  if panel.data('post-id').equalsIgnoreCase 'ad' then return
  first = col.find '.panel:first'
  uri = location.pathname
  if not panel.is first
    uri = uri + '?start=' + panel.data('post-id')
  history.replaceState {}, 'SPIFFY.io', uri
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
