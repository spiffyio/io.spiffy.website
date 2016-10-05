Spiffy.f.click 'a[href="#"]', -> { }

Spiffy.f.click 'a.menu', (e, toggle) ->
  toggle.toggleClass 'expanded'
  menu = toggle.parent().find '.sub-menu'
  menu.toggleClass 'show'
  return

Spiffy.f.click '[data-go]', (e, button) ->
  go button.data('go') + location.search
  return

Spiffy.f.click '.close', ->
  closeModal()
  return

Spiffy.f.click '.modal-overlay', (e) ->
  if $(e.target).hasClass 'modal-overlay' then closeModal()
  return

Spiffy.f.click 'div.pause, video', (e, control) ->
  video = control.parent().parent().find 'video'
  video.attr 'data-clicked', true
  if video[0].paused
    video[0].play()
    video.parents('div.video:first').removeClass 'paused'
  else
    video[0].pause()
    video.parents('div.video:first').addClass 'paused'
  return
