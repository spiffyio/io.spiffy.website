Dropzone.options.dzForm = {
  paramName: 'file',
  maxFilesize: 2,
  dictDefaultMessage: '<img style="width: 5em; height: auto;" src="https://cdn.spiffy.io/static/svg/icon.svg" />',
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
  return

menu = (top) ->
  $('div.header').find('div.menu').finish().animate {top: top}
  return
