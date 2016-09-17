$(document).on 'click', 'a[href="#"]', (e) ->
  e.preventDefault()
  return

Handlebars.html = (name, data) ->
  template = Handlebars.compile($('[data-template="' + name + '"]').html())
  return template(data)

$(document).on 'click', '.chat-thread, a', (e) ->
  Messenger.open $(this)
  return

$(document).ready (e) ->
  Messenger.loadThreads()

  return

Messenger =
  foo: () ->
    data =
      id: 'cjsmile'
      icon: '//cdn-beta.spiffy.io/media/DlXRpf-Cg.jpg'
      time: 'Yesterday'
      preview: 'wassup my brother from another, yo lol'
      display: 'none'
    Messenger.addThread data
    return
  bar: () ->
    data =
      id: 'foobar'
      side: 'left'
      icon: '//cdn-beta.spiffy.io/media/DlXRpf-Cg.jpg'
      message: 'hello dood'
      display: 'none'
    Messenger.addMessage data
    return

  add: (container, element, template, data) ->
    container = $ container
    container.prepend Handlebars.html(template, data)

    if not data.display? then return

    element = $ element
    element.slideDown()
    return

  addThread: (data) ->
    Messenger.add '.chats-body', '[data-thread-id="' + data.id + '"]', 'chat-thread', data
    return

  addMessage: (data) ->
    Messenger.add '.chat-body', '[data-message-id="' + data.id + '"]', 'message-group', data
    return

  open: (thread) ->
    if thread.hasClass 'active' then return
    $('.chat-thread.active').removeClass 'active'
    thread.addClass 'active'

    id = thread.data 'thread-id'
    history.pushState { id: id }, id, '/messages/' + id

    chat = $ '.chat'

    header = chat.find '.chat-header'
    header.html id

    body = chat.find '.chat-body'
    body.html ''
    return
