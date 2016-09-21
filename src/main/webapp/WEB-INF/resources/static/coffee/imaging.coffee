Dropzone.autoDiscover = false

$(document).ready (e) ->
  if $('form#icon-dz').length
    initIconDZ()
  if $('form#banner-dz').length
    initBannerDZ()

initIconDZ =  ->
  div = $ '.icon-container'
  croppie = new Croppie div.find('.spiffy-croppie')[0],
    enableOrientation: true,
    viewport:
      width: 160,
      height: 160,
      type: 'square'

  div.find('.button.primary').click ->
    croppie.result({ size: { width: 160, height: 160 }, format: croppie.format }).then (canvas) ->
      closeModal '#icon-modal'
      img = $ '.profile-icon'
      img.attr 'src', canvas
      blob = dataURItoBlob canvas
      blob.name = 'thumbnail.' + croppie.format
      iconDZ.addFile blob
      iconDZ.processQueue()
      return
    return croppie.get()

  iconDZ = new Dropzone 'form#icon-dz',
    paramName: 'icon',
    clickable: $('#edit-icon')[0],
    maxFiles: 2,
    maxFilesize: 200,
    uploadMultiple: true,
    createImageThumbnails: true,
    autoProcessQueue: false,
    acceptedFiles: ".jpeg,.jpg,.png",
    accept: (file, done) ->
      file.acceptDimensions = done
      file.rejectDimensions = () -> done 'image must be at least 160 x 160 pixels'
      type = file.type
      if type.equalsIgnoreCase 'image/jpg' then done()
      else if type.equalsIgnoreCase 'image/jpeg' then done()
      else if type.equalsIgnoreCase 'image/png' then done()
      else done 'unable to upload file: ' + file.name
      return
    init: () ->
      this.on 'thumbnail', (file) ->
        if file.width < 160 or file.height < 160 then file.rejectDimensions() else file.acceptDimensions()
        return
      this.on 'addedfile', (file) ->
        iconDZAddedFile file
        return

  $(document).on 'click', '#edit-icon', (e) ->
    iconDZ.removeAllFiles true

  iconDZAddedFile = (file) ->
    if not file.accepted?
      setTimeout () ->
        iconDZAddedFile(file)
      , 10
      return

    if not file.accepted
      form = $ iconDZ.element
      message = form.find '.message'
      message.html 'unable to upload file: ' + file.name
      message.slideDown()
      form.animate {height: '10em'}, 500
      return

    if iconDZ.files.length is 2 then return

    $(iconDZ.element).hide()
    openModal '#icon-modal'
    src = URL.createObjectURL(file)
    div = $ '.icon-container'

    croppie.bind { url : src }
    croppie.format = if file.type.equalsIgnoreCase 'image/png' then 'png' else 'jpeg'
    return

initBannerDZ =  ->
  bannerDZ = new Dropzone 'form#banner-dz',
    paramName: 'banner',
    clickable: $('#edit-banner')[0],
    maxFiles: 1,
    maxFilesize: 200,
    uploadMultiple: false,
    createImageThumbnails: true,
    autoProcessQueue: true,
    acceptedFiles: ".jpeg,.jpg,.png",
    accept: (file, done) ->
      file.acceptDimensions = done
      file.rejectDimensions = () -> done 'image must be at least 320 x 180 pixels'
      type = file.type
      if type.equalsIgnoreCase 'image/jpg' then done()
      else if type.equalsIgnoreCase 'image/jpeg' then done()
      else if type.equalsIgnoreCase 'image/png' then done()
      else done 'unable to upload file: ' + file.name
      return
    init: () ->
      this.on 'thumbnail', (file) ->
        if file.width < 320 or file.height < 180 then file.rejectDimensions() else file.acceptDimensions()
        return
      this.on 'addedfile', (file) ->
        bannerDZAddedFile file
        return

  $(document).on 'click', '#edit-banner', (e) ->
    bannerDZ.removeAllFiles true

  bannerDZAddedFile = (file) ->
    if not file.accepted?
      setTimeout () ->
        bannerDZAddedFile(file)
      , 10
      return

    if not file.accepted
      form = $ bannerDZ.element
      message = form.find '.message'
      message.html 'unable to upload file: ' + file.name
      message.slideDown()
      form.animate {height: '10em'}, 500
      return

    src = URL.createObjectURL(file)
    img = $ '.profile-banner'
    img.attr 'src', src
    return
