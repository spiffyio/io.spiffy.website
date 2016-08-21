Array::isArray     ?= (a) -> {}.toString.call(value) is '[object Array]'

String::startsWith         ?= (s) -> @slice(0, s.length) == s
String::endsWith           ?= (s) -> s == '' or @slice(-s.length) == s
String::contains           ?= (s) -> s == '' or @indexOf(s) > -1
String::equalsIgnoreCase   ?= (s) -> @toUpperCase() == s.toUpperCase()

Spiffy =
  firstDefined : () ->
    defined = argument for argument in arguments by -1 when argument?
    if defined? then return defined
    return undefined

jQuery.fn.spiffy = () ->
  elements = $ this
  fn =
    end: () ->
      elements
    disable: () ->
      elements
        .find 'form, input, textarea, select, button'
          .addBack()
          .prop 'disabled', true
          .attr 'data-disabled', true
          .data 'disabled', true
      elements.spiffy()
    enable: () ->
      elements
        .find 'form, input, textarea, select, button'
          .addBack()
          .removeAttr 'disabled'
          .removeAttr 'data-disabled'
          .data 'disabled', false
      elements.spiffy()
    clear: () ->
      elements.find 'input, textarea'
        .val ''
      elements.spiffy()
    push: (data) ->
      element = $ elements[0]
      name = element.attr 'name'
      value = element.val()
      if not data[name]?
        data[name] = value
      else if Array.isArray data[name]
        data[name].push value
      else
        data[name] = [data[name], value]
      data
    data: () ->
      if elements.length > 1
        data = []
        data.push $(element).spiffy().data() for element in elements
        return data

      element = $ elements[0]

      if not element.is 'form'
        data = element.val()
        return data

      data = {}
      element.find '[name]'
        .each () ->
          $(this).spiffy().push data
      $ '[data-form-name="' + element.data('name') + '"]'
        .each () ->
          $(this).spiffy().push data
      return data
    loading: (loading, enable = true) ->
      form = $ elements[0]
      if not form.is 'form' then return

      if 'overlay'.equalsIgnoreCase loading
        img = form.find 'img.loading'
        if enable and ((not img?) or (not img.is('img.loading')))
          form.css 'position', 'relative'
          img = $ document.createElement 'img'
          img.addClass 'loading'
          img.attr 'src', 'https://cdn.spiffy.io/static/svg/loading.svg'
          img.prop 'hidden'
          form.append img
        if enable and img? and img.is('img.loading') then img.slideDown()
        if not enable and img? and img.is('img.loading') then img.slideUp()
      else if 'header'.equalsIgnoreCase loading
        img = $ 'img.header-logo'
        if img? and img.is('img.header-logo')
          src = img.attr 'src'
          src = if enable then src.replace 'icon', 'loading' else src.replace 'loading', 'icon'
          img.attr 'src', src

      return form.spiffy()
    options: (options) ->
      form = $ elements[0]
      if not form.is 'form' then return

      if options?
        form.data 'options', $.extend(form.data('options'), options)
        return form.spiffy()

      return form.data 'options'
    submit: (options) ->
      form = $ elements[0]
      if not form.is 'form' then return
      if form.data 'disabled' then return

      options = Spiffy.firstDefined options, $(form).spiffy().options(), {}

      validate = form.validate Spiffy.firstDefined(options.validate, {})
      if validate.numberOfInvalids() then return

      url = Spiffy.firstDefined options.url, form.data('url'), form.attr('action')
      type = Spiffy.firstDefined options.type, form.data('type'), form.attr('method'), 'POST'

      csrf = form.data 'csrf-token'
      if (not csrf?) and (type.equalsIgnoreCase 'POST') then alert 'csrf required'

      disable = Spiffy.firstDefined options.disable, true
      if disable then form.spiffy().disable()

      loading = Spiffy.firstDefined options.loading, form.data('loading'), 'overlay'
      form.spiffy().loading loading

      data = form.spiffy().data()

      $.ajax
        url: url
        data: data
        dataType: 'json'
        headers:
          'X-CSRF-Token': csrf
        type: type
        success: (data, textStatus, jqXHR) ->
          form.spiffy().enable().loading(loading, false)
          validate.resetForm()
          if options.success?
            options.success form, data, textStatus, jqXHR
        error: (jqXHR, textStatus, errorThrown) ->
          form.spiffy().enable().loading(loading, false)
          validate.resetForm()
          if options.error?
            options.error form, jqXHR, textStatus, errorThrown

      form.spiffy()
