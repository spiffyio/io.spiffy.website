Dropzone.options.dzForm = {
  paramName: 'file',
  maxFilesize: 2,
  accept: (file, done) ->
    done()
}

$(document).ready (e) ->
  $('div.header').mouseenter () ->
    header = $(this)
    func = () ->
      if header.is ':hover'
        menu '0'
      return
    setTimeout(func, 200)
    return

  $('div.header').mouseleave () ->
    header = $(this)
    func = () ->
      if not header.is ':hover'
        menu '-3em'
      return
    setTimeout(func, 500)
    return

  window.addEventListener 'dragenter', (e) ->
    $('#dz-form').show()
    return

  window.addEventListener 'dragleave', (e) ->
    if (e.target is $('div.header')[0]) # not correct, but works for now
      $('#dz-form').hide()
    return

  return

menu = (top) ->
  $('div.header').find('div.menu').finish().animate {top: top}
  return
