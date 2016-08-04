$(document).ready (e) ->
  $('div.header').mouseenter () ->
    header = $(this)
    func = () ->
      if header.is ":hover"
        menu '0'
    setTimeout(func, 200)

  $('div.header').mouseleave () ->
    header = $(this)
    func = () ->
      if not header.is ":hover"
        menu '-3em'
    setTimeout(func, 500)

  uploader = new qq.FineUploaderBasic {}

menu = (top) ->
  $('div.header').find('div.menu').finish().animate {top: top}
