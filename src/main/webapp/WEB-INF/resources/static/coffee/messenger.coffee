$(document).on 'click', 'a[href="#"]', (e) ->
  e.preventDefault()
  return

Handlebars.html = (name, data) ->
  template = Handlebars.compile($('[data-template="' + name + '"]').html())
  return template(data)

$(document).on 'click', '.chat-thread', (e) ->
  Messenger.open $(this)
  return

$(document).on 'click', '.new-message', (e) ->
  Messenger.new()
  return

window.addEventListener 'popstate', (event) ->
  uri = window.location.pathname
  console.log uri
  parts = uri.split('/')
  if not parts.length is 3 then return
  if not parts[1].equalsIgnoreCase 'messages' then return
  Messenger.open $('[data-thread-id="' + parts[2] + '"]'), false
  return

$(document).ready (e) ->

  return

Messenger =
  add: (container, element, template, data) ->
    container = $ container
    container.prepend Handlebars.html(template, data)

    element = $ element
    element.slideDown()
    element.animate { opacity: 1 }, 500
    return

  addThread: (data) ->
    Messenger.add '.chats-body', '[data-thread-id="' + data.id + '"]', 'chat-thread', data
    return

  addMessage: (data) ->
    Messenger.add '.chat-body', '[data-message-id="' + data.id + '"]', 'message-group', data
    return

  new: () ->
    form = $ 'form.new'
    title = form.parent().find '.title'
    if form.is ':visible'
      Messenger.open $('[data-thread-id="' + title.html() + '"]')
    else
      Messenger.open $('[data-thread-id="new"]')
    return

  loadMessages: (data) ->
    if not data? then return

    messages = data.messages
    if not messages? then return

    for message in messages
      message.opacity = '0.0'
      Messenger.addMessage message
    return

  open: (thread, pushState = true) ->
    if thread.hasClass 'active' then return
    $('.chat-thread.active').removeClass 'active'
    thread.addClass 'active'

    id = thread.data 'thread-id'
    url = '/messages/' + id
    if pushState then history.pushState { id: id }, id, url

    if not id.equalsIgnoreCase 'new'
      $.get { url: url, dataType: 'json', success: (data) -> Messenger.loadMessages data }

    chat = $ '.chat'

    header = chat.find '.chat-header'
    form = header.find 'form'
    title = header.find '.title'

    if id.equalsIgnoreCase 'new'
      title.hide()
      form.show()
      input = form.find 'input'
    else
      title.html(id).show()
      form.hide()
      input = $('form.message').find '[name="message"]'

    input.focus()

    body = chat.find '.chat-body'
    body.html ''
    return
