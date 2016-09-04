Array::isArray     ?= (a) -> {}.toString.call(value) is '[object Array]'

String::startsWith         ?= (s) -> @slice(0, s.length) == s
String::endsWith           ?= (s) -> s == '' or @slice(-s.length) == s
String::contains           ?= (s) -> s == '' or @indexOf(s) > -1
String::containsIgnoreCase ?= (s) -> s == '' or @toUpperCase().indexOf(s.toUpperCase()) > -1
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
        .find 'form, input, textarea, select, button, div.g-recaptcha'
          .addBack()
          .not('[data-disabled="permanent"]')
          .prop 'disabled', true
          .attr 'data-disabled', true
          .addClass 'disabled'
          .data 'disabled', true
      elements.spiffy()
    enable: () ->
      elements
        .find 'form, input, textarea, select, button, div.g-recaptcha'
          .addBack()
          .not('[data-disabled="permanent"]')
          .removeAttr 'disabled'
          .removeAttr 'data-disabled'
          .removeClass 'disabled'
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
      else
        data[name] = data[name] + "," + value
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
    loading: (loading, enable = true, time = 2000) ->
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
        div = $ 'div.header-loading'
        if div? and div.is('div.header-loading')
          if enable
            div.animate { width: '80%' }, time
          else
            div.finish().animate { width: '100%' }, 250, 'swing', () ->
              div.animate { opacity: '0' }, 500, 'swing', () ->
                div.css 'width', '0%'
                div.css 'opacity', '1'

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

      #validate = form.validate Spiffy.firstDefined(options.validate, {})
      #if validate.numberOfInvalids() then return

      url = Spiffy.firstDefined options.url, form.data('url'), form.attr('action')
      type = Spiffy.firstDefined options.type, form.data('type'), form.attr('method'), 'POST'

      csrf = form.data 'csrf-token'
      if (not csrf?) and (type.equalsIgnoreCase 'POST') then alert 'csrf required'

      if options.confirm? and not form.data('confirmed')
        button = $ '#confirm-action'
        button.html options.confirm
        button.click () ->
          closeModal()
          form.attr 'data-confirmed', true
          form.submit()
          form.attr 'data-confirmed', false
          return
        openModal()
        return

      disable = Spiffy.firstDefined options.disable, true
      if disable then form.spiffy().disable()

      loading = Spiffy.firstDefined options.loading, form.data('loading'), 'overlay'
      form.spiffy().loading loading

      div = form.find 'div.message'
      if div? and div.is 'div.message' then div.slideUp()

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
          #validate.resetForm()
          if options.success?
            options.success form, data, textStatus, jqXHR
        error: (jqXHR, textStatus, errorThrown) ->
          response = jqXHR.responseJSON
          if (jqXHR.status is 401) and (response.uri?)
            go jqXHR.responseJSON.uri
            return
          if (jqXHR.status is 400) and (response.error?)
            input = form.find 'input[name="' + response.error + '"]'
            if input? and input.is 'input[name="' + response.error + '"]'
              div = input.parent()
              span = div.find 'span'
              div.addClass 'bt-flabels__error'
              span.html response.reason
            else
              div = form.find 'div.message'
              div.addClass 'error'
              div.html response.reason
              div.slideDown()
          if response.tip?
            div = form.find 'div.message'
            div.addClass 'tip'
            div.html response.tip
            div.slideDown()

          recaptcha = form.find '.g-recaptcha'
          if recaptcha? and recaptcha.is '.g-recaptcha' then grecaptcha.reset()

          form.spiffy().enable().loading(loading, false)
          #validate.resetForm()
          if options.error?
            options.error form, jqXHR, textStatus, errorThrown

      form.spiffy()
