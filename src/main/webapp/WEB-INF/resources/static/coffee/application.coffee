# @prepros-prepend constants.coffee
# @prepros-prepend functions.coffee
# @prepros-prepend extension.coffee
# @prepros-prepend global.coffee
# @prepros-prepend messenger.coffee
# @prepros-prepend imaging.coffee


$(document).ready ->
  Spiffy.f.update.poll()

addedfile = (file) ->
  if not file.accepted?
    func = () ->
      addedfile file
      return
    setTimeout func, 10
    return

  if not file.accepted
    form = $ '#file-dz'
    message = form.find '.message'
    message.html 'unable to upload file: ' + file.name
    message.slideDown()
    form.animate {height: '10em'}, 500
    return
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
    video.attr 'autoplay', true
    video.attr 'muted', true
    video.attr 'loop', true
    video.attr 'preload', 'auto'
    video.attr 'poster', '/static/png/processing.png'

    source = $ document.createElement 'source'
    source.attr 'type', file.type
    source.attr 'src', src
    video.append source

    processing = $ document.createElement 'img'
    processing.attr 'src', '/static/png/processing.png'
    video.append processing

    div.append video
  else if file.type.containsIgnoreCase 'image'
    form.spiffy().loading 'header', true, 5000
    img = $ document.createElement 'img'
    img.attr 'src', src
    div.append img
  else
    form.spiffy().loading 'header', true, 5000
    img = $ document.createElement 'img'
    img.attr 'src', '/static/png/processing.png'
    div.append img
  preview.prepend div
  $('#file-dz').slideUp()
  form.slideDown()
  return

$(document).ready (e) ->
  if $('form#file-dz').length
    iconDZ = new Dropzone 'form#file-dz',
      paramName: 'file',
      maxFiles: 1,
      maxFilesize: 200,
      uploadMultiple: false,
      createImageThumbnails: false,
      autoProcessQueue: true,
      acceptedFiles: ".jpeg,.jpg,.png,.gif,.mov,.mp4,.mpeg4,.mpeg,.webm",
      accept: (file, done) ->
        type = file.type
        if type.equalsIgnoreCase 'image/gif' then done()
        else if type.equalsIgnoreCase 'image/jpg' then done()
        else if type.equalsIgnoreCase 'image/jpeg' then done()
        else if type.equalsIgnoreCase 'image/png' then done()
        else if type.equalsIgnoreCase 'video/mov' then done()
        else if type.equalsIgnoreCase 'video/mp4' then done()
        else if type.equalsIgnoreCase 'video/mpeg4' then done()
        else if type.equalsIgnoreCase 'video/quicktime' then done()
        else if type.equalsIgnoreCase 'video/webm' then done()
        else done 'unable to upload file: ' + file.name
        return
      init: () ->
        this.on 'addedfile', (file) ->
          addedfile file
          return
        this.on 'uploadprogress', (file) ->
          return
      success: (file, response) ->
        form = $ 'form.submit'
        form.spiffy().enable().loading 'header', false
        media = form.find 'input[name="media"]'
        media.val response.name
        return

$(document).ready (e) ->
  $('.action-button').click (e) ->
    form = $ 'form.profile-action'
    input = form.find '[name="action"]'
    input.attr 'value', $(this).html()
    form.submit()
    return

  $('form.profile-action').spiffy().options
    success: (form) ->
      input = form.find '[name="action"]'
      val = input.attr 'value'
      $('.action-button').html if 'FOLLOW'.equalsIgnoreCase val then 'UNFOLLOW' else 'FOLLOW'
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
      video.attr 'autoplay', true
      video.attr 'muted', true
      video.attr 'loop', true
      video.attr 'preload', 'auto'
      video.attr 'poster', '/static/png/processing.png'

      source = $ document.createElement 'source'
      source.attr 'type', type
      source.attr 'src', src
      video.append source

      processing = $ document.createElement 'img'
      processing.attr 'src', '/static/png/processing.png'
      video.append processing

      div.html video
    else if type.containsIgnoreCase 'image'
      img = $ document.createElement 'img'
      img.attr 'src', src
      div.html img
    return

  $('form:not(.dropzone)').submit (e) ->
    Spiffy.f.prevent e
    form = $ this
    form.spiffy().submit()
    return

  $('form.load-posts').spiffy().options
    success: (form, json) ->
      if not json.posts?
        form.spiffy().disable()
      else
        load(json)

  $('form.forgot').spiffy().options
    success: (form) ->
      form.spiffy().disable()
      div = form.find 'div.message'
      div.addClass 'success'
      div.html 'recovery email sent'
      div.slideDown()
      return

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
    Spiffy.f.prevent e
    form = $ 'form.' + $(this).data('form')
    form.submit()
    return

  $('a[data-session-id]').click (e) ->
    Spiffy.f.prevent e
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

  $('form.delete').each () ->
    $(this).spiffy().options
      success: () ->
        refresh()
        return
      confirm: 'confirm deletion'
    return

  $('form.action').spiffy().options
    success: () ->
      go '/'
      return

  $('div.actions').find('button').each (e) ->
    button = $ this
    form = button.parents('div.actions:first').find 'form'
    button.click (e) ->
      input = form.find 'input[name="action"]'
      action = button.data 'action'
      input.val action
      form.spiffy().options
        confirm: 'confirm ' + action
      form.submit()
      return
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

  $(document).on 'click', '.thismedia', (e) ->
    img = $ this
    img.toggleClass 'clicked'
    form = $ 'form.delete'
    if $('img.thismedia.clicked').length
      form.spiffy().enable()
    else
      form.spiffy().disable()

    if img.hasClass 'clicked'
      input = $ document.createElement 'input'
      input.attr 'type', 'hidden'
      input.attr 'name', 'media'
      input.val img.data('media-name')
      form.append input
    else
      form.find('input[type="hidden"]').each () ->
        input = $ this
        if input.val() is img.data('media-name') then input.remove()
        return
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

openModal = (selector = '.modal-overlay') ->
  $(selector).slideDown 500
  return

closeModal = (selector = '.modal-overlay') ->
  $(selector).slideUp 250
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
      video.attr 'poster', content.poster.file

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
      img.attr 'src', content.medium
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
    if ($(window).width() < Spiffy.c.size.width.XL) then cols = 2
    if ($(window).width() < Spiffy.c.size.width.MD) then cols = 1

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

  form = $ 'form.load-posts'
  if form? and form.is 'form.load-posts'
    col = $ '.col[data-index="2"]'
    panel = col.find '.panel:last'
    if panel.data('post-id').equalsIgnoreCase 'ad'
      col = $ '.col[data-index="1"]'
      panel = col.find '.panel:last'
    if $('.col[data-index="0"]').find('.panel').length > $('.col[data-index="1"]').find('.panel').length
      col = $ '.col[data-index="0"]'
      panel = col.find '.panel:last'
    input = form.find 'input[name="after"]'
    input.val panel.data('post-id')

  if ($(window).width() < Spiffy.c.size.width.XL) then emptyColumn 2
  if ($(window).width() < Spiffy.c.size.width.MD) then emptyColumn 1

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
  if (width > window.pWidth) and (width >= Spiffy.c.size.width.MD)
    $('.subheader').show()
    fillColumn 1, 2
  if (width < window.pWidth) and (width < Spiffy.c.size.width.MD)
    $('.subheader').hide()
    $('.hamburger').removeClass 'active'
    emptyColumn 1
  if (width > window.pWidth) and (width >= Spiffy.c.size.width.XL)
    emptyColumn 1
    fillColumn 1, 3
    fillColumn 2, 3
  if (width < window.pWidth) and (width < Spiffy.c.size.width.XL)
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

$(document).ready ->
  href = location.href
  if href.endsWith '#'
    history.replaceState {}, document.title, href.substring(0, href.length - 1)
  $(window).scroll (e) ->
    $('div.col').each () ->
      col = $(this)
      panel = col.find('div.panel:last')
      if not panel? then return
      if panel.is ':in-viewport'
        form = $ 'form.load-posts'
        form.submit()
      return
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
