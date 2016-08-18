String::startsWith ?= (s) -> @slice(0, s.length) == s
String::endsWith   ?= (s) -> s == '' or @slice(-s.length) == s
String::contains   ?= (s) -> s == '' or @indexOf(s) > -1

jQuery.fn.isany = (values) ->
  element = $ this[0]
  for value in values
    if element.is value then return true
  false

jQuery.fn.spiffyDisable = (disable = true) ->
  element = $ this[0]
  element.data 'disabled', disable
  element.find(input).prop('disabled', disable) for input in ['input', 'textarea', 'select', 'button']
  element

jQuery.fn.spiffyEnable = () ->
  element = $ this[0]
  element.spiffyDisable false

jQuery.fn.spiffyClear = () ->
  form = $ this[0]
  form.find(input).val('') for input in ['input', 'textarea']
  form

jQuery.fn.spiffyValue = () ->
  element = $(this[0])
  for value in ['accept', 'decline', 'follow', 'unfollow', 'friend', 'unfriend', 'settings', 'like', 'hate', 'favorite', 'download', 'report']
    if element.hasClass value then return value
  undefined

jQuery.fn.spiffyFormValue = (name) ->
  form = $ this[0]
  element = form.find('[name=' + name + ']')[0]
  if not element?
    element = $('[data-form=' + form.data('form') + '][name=' + name + ']')[0]
  if not element?
    return undefined
  value = $(element).val()
  #value = encodeURIComponent value
  #value.replace /%20/g, '+'

jQuery.fn.spiffyFormData = (names) ->
  form = $ this[0]
  data = {}
  for name in names
    data[name] = form.spiffyFormValue name
  data

jQuery.fn.spiffySubmit = (options, data, success, error) ->
  form = $ this[0]
  if form.data('disabled') is true then return

  validate = form.validate()
  if validate.numberOfInvalids() then return

  disable = if defined options.disable then options.disable else true
  if disable then form.spiffyDisable()

  if (defined options.loading) and (options.loading is 'header')
    img = $('img.header-logo')
    img.attr('src', img.attr('src').replace('icon', 'loading'))
  else
    if form.find('img.loading').length is 0
      form.css 'position', 'relative'
      form.append '<img class="loading" src="https://cdn.spiffy.io/static/svg/loading.svg" style="position:absolute;left:0;top:0;right:0;bottom:0;margin:auto;max-width:100%;max-height:100%;z-index:1000;" hidden="true"/>'
    form.find('img.loading').slideDown()

  url = if defined options.url then options.url else options
  method = if defined options.method then options.method else 'POST'

  $.ajax
    url: url
    data: data
    dataType: 'json'
    headers:
      'X-CSRF-Token': form.data 'csrf-token'
    type: method
    success: (data, textStatus, jqXHR) ->
      success(data)
      if (defined options.loading) and (options.loading is 'header')
        img = $('img.header-logo')
        img.attr('src', img.attr('src').replace('loading', 'icon'))
      else
        form
          .find 'img.loading'
          .hide 250
      if disable then form.spiffyEnable()
      validate.resetForm()
      return
    error: (jqXHR, textStatus, errorThrown) ->
      if (defined options.loading) and (options.loading is 'header')
        img = $('img.header-logo')
        img.attr('src', img.attr('src').replace('loading', 'icon'))
      else
        form
          .find 'img.loading'
          .hide 250
      if disable then form.spiffyEnable
      if jqXHR.status is 401
        handler (json) ->
          $('.modal').modal 'hide'
          $("meta[name='csrf-header']").attr 'content', json.csrf.header
          $("meta[name='csrf-token']").attr 'content', json.csrf.token
          form.submit()
          # TODO: refresh page? (old comment left around)
          return
        $('#signin').modal 'show'
        return
      json = jqXHR.responseJSON
      if json.error
        if form.find 'input[name="error"]'
          .length is 0 then form.append '<input name="error" hidden="true">'
        validate.showErrors { 'error': json.error }
      if defined error then error(validate, json)
      return

  return
