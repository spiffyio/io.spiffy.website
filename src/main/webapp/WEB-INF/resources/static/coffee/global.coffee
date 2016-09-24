Spiffy.f.click 'a[href="#"]', () -> { }

$(document).ready ->
  poll 'init'

poll = (value, attempt = 1) ->
  $.get
    url: '/longpoll'
    dataType: 'json'
    data:
      value: value
    cache: false
    success: (data) ->
      console.log data
      poll data.value
      return
    error: ->
      Spiffy.f.timeout.retry attempt, ->
        poll value, attempt+1
        return
      return
