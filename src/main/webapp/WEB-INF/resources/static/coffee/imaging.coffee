Dropzone.autoDiscover = false

$(document).ready (e) ->
  if $('form#profile-dz').length
    initProfileDZ()

initProfileDZ =  ->
  profileDZ = new Dropzone 'form#profile-dz',
    paramName: 'icon',
    clickable: $('#edit-icon')[0],
    maxFiles: 2,
    maxFilesize: 200,
    uploadMultiple: true,
    createImageThumbnails: true,
    autoProcessQueue: false,
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
        profileDZAddedFile file
        return

  profileDZAddedFile = (file) ->
    if not file.accepted?
      setTimeout () ->
        profileDZAddedFile(file)
      , 10
      return

    if not file.accepted
      form = $ profileDZ.element
      message = form.find '.message'
      message.html 'unable to upload file: ' + file.name
      message.slideDown()
      form.animate {height: '10em'}, 500
      return

    if profileDZ.files.length is 2 then return

    $(profileDZ.element).hide()
    openModal '#profile-modal'
    src = URL.createObjectURL(file)
    div = $ '.profile-container'

    croppie = new Croppie div.find('.croppie')[0],
      url: src,
      enableOrientation: true,
      viewport:
        width: 160,
        height: 160,
        type: 'square'

    div.find('.button.primary').click ->
      croppie.result({ size: { width: 160, height: 160 }, format: if file.type.equalsIgnoreCase 'image/png' then 'png' else 'jpeg' }).then (canvas) ->
        closeModal '#profile-modal'
        img = $ '.profile-icon'
        img.attr 'src', canvas
        blob = dataURItoBlob canvas
        profileDZ.addFile blob
        profileDZ.processQueue()
        return
      return croppie.get()
    return
