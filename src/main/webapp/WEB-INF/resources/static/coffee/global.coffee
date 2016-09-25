Spiffy.f.click 'a[href="#"]', () -> { }

$(document).ready ->
  poll()

poll = (etag = Spiffy.c.param.ETAG, attempt = Spiffy.c.param.ATTEMPT) ->
  Spiffy.f.log Spiffy.c.enum.loglevel.INFO, 'polling...' + if etag? then ' [etag: ' + etag + ']' else ''
  $.get
    url: '/longpoll'
    dataType: 'json'
    beforeSend: (xhr) ->
      if etag?
        xhr.setRequestHeader 'If-None-Match', etag
      return
    success: (data, status, xhr) ->
      console.log data
      notifications data.notifications
      poll xhr.getResponseHeader 'ETag'
      return
    error: ->
      Spiffy.f.timeout.retry attempt, ->
        poll etag, attempt+1
        return
      return

notifications = (count) ->
    span = $ 'span.notification-count'
    if count is 0
      document.title = 'SPIFFY.io'
      span.html ''
    else
      document.title = '(' + count + ') SPIFFY.io'
      span.html count
    return
