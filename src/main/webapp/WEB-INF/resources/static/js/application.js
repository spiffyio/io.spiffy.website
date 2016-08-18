var Config, Keys, Width;

Config = {
  cdn: '//cdn.spiffy.io/static'
};

Keys = {
  left: 37,
  right: 39
};

Width = {
  xs: 320,
  sm: 480,
  md: 768,
  lg: 992,
  xl: 1200
};

var blank, confirmation, contains, defined, go, handler, high, initModalSize, medium, overrideHandler, preventDefault, quality, recaptchaCallback, refresh;

recaptchaCallback = function() {
  $('.g-recaptcha').each(function(i, e) {
    grecaptcha.render(e, {
      'sitekey': '6LeSviITAAAAAFC9aCd6CAmWFqLoIpzw174jMc-i'
    });
  });
};

preventDefault = function(e) {
  e.preventDefault();
};

overrideHandler = refresh;

handler = function(handler) {
  if (defined(handler)) {
    overrideHandler = handler;
  }
  if (!defined(overrideHandler)) {
    return refresh;
  }
  return overrideHandler;
};

medium = function(img) {
  quality(img, 'm', ['a', 'l'], function() {
    high(img);
  });
};

high = function(img) {
  quality(img, 'h', ['a', 'l', 'm']);
};

quality = function(img, q, old, callback) {
  var image, j, len, o, src;
  if (old == null) {
    old = ['a', 'l', 'm', 'h'];
  }
  src = img.attr('src');
  for (j = 0, len = old.length; j < len; j++) {
    o = old[j];
    src = src.replace('q=' + o, 'q=' + q);
  }
  image = new Image();
  image.onload(function() {
    img.remove('lazy').attr('src', src);
    if (callback != null) {
      callback();
    }
  });
  image.src = src;
};

confirmation = function(title, action) {
  var dialog;
  dialog = $('#confirmation');
  dialog.find('.modal-title').text(title);
  dialog.find('.continue').on('click', function(e) {
    e.preventDefault();
    return action();
  });
  dialog.modal('show');
};

go = function(url) {
  return location.href = url;
};

refresh = function() {
  location.reload(true);
  return window.location = self.location;
};

blank = function(v) {
  if (!defined(v)) {
    return true;
  }
  return v === '';
};

defined = function(v) {
  if (typeof v === 'undefined') {
    return false;
  }
  return v !== 'undefined';
};

contains = function(s, v) {
  var index;
  index = s.indexOf(v);
  return index > -1;
};

initModalSize = function() {
  var height;
  height = ($(window).height() - 125) + 'px';
  $('.modal-body').css('max-height', height);
  return height;
};

var base, base1, base2;

if ((base = String.prototype).startsWith == null) {
  base.startsWith = function(s) {
    return this.slice(0, s.length) === s;
  };
}

if ((base1 = String.prototype).endsWith == null) {
  base1.endsWith = function(s) {
    return s === '' || this.slice(-s.length) === s;
  };
}

if ((base2 = String.prototype).contains == null) {
  base2.contains = function(s) {
    return s === '' || this.indexOf(s) > -1;
  };
}

jQuery.fn.isany = function(values) {
  var element, i, len, value;
  element = $(this[0]);
  for (i = 0, len = values.length; i < len; i++) {
    value = values[i];
    if (element.is(value)) {
      return true;
    }
  }
  return false;
};

jQuery.fn.spiffyDisable = function(disable) {
  var element, i, input, len, ref;
  if (disable == null) {
    disable = true;
  }
  element = $(this[0]);
  element.data('disabled', disable);
  ref = ['input', 'textarea', 'select', 'button'];
  for (i = 0, len = ref.length; i < len; i++) {
    input = ref[i];
    element.find(input).prop('disabled', disable);
  }
  return element;
};

jQuery.fn.spiffyEnable = function() {
  var element;
  element = $(this[0]);
  return element.spiffyDisable(false);
};

jQuery.fn.spiffyClear = function() {
  var form, i, input, len, ref;
  form = $(this[0]);
  ref = ['input', 'textarea'];
  for (i = 0, len = ref.length; i < len; i++) {
    input = ref[i];
    form.find(input).val('');
  }
  return form;
};

jQuery.fn.spiffyValue = function() {
  var element, i, len, ref, value;
  element = $(this[0]);
  ref = ['accept', 'decline', 'follow', 'unfollow', 'friend', 'unfriend', 'settings', 'like', 'hate', 'favorite', 'download', 'report'];
  for (i = 0, len = ref.length; i < len; i++) {
    value = ref[i];
    if (element.hasClass(value)) {
      return value;
    }
  }
  return void 0;
};

jQuery.fn.spiffyFormValue = function(name) {
  var element, form, value;
  form = $(this[0]);
  element = form.find('[name=' + name + ']')[0];
  if (element == null) {
    element = $('[data-form=' + form.data('form') + '][name=' + name + ']')[0];
  }
  if (element == null) {
    return void 0;
  }
  return value = $(element).val();
};

jQuery.fn.spiffyFormData = function(names) {
  var data, form, i, len, name;
  form = $(this[0]);
  data = {};
  for (i = 0, len = names.length; i < len; i++) {
    name = names[i];
    data[name] = form.spiffyFormValue(name);
  }
  return data;
};

jQuery.fn.spiffySubmit = function(options, data, success, error) {
  var disable, form, img, method, url, validate;
  form = $(this[0]);
  if (form.data('disabled') === true) {
    return;
  }
  validate = form.validate();
  if (validate.numberOfInvalids()) {
    return;
  }
  disable = defined(options.disable) ? options.disable : true;
  if (disable) {
    form.spiffyDisable();
  }
  if ((defined(options.loading)) && (options.loading === 'header')) {
    img = $('img.header-logo');
    img.attr('src', img.attr('src').replace('icon', 'loading'));
  } else {
    if (form.find('img.loading').length === 0) {
      form.css('position', 'relative');
      form.append('<img class="loading" src="https://cdn.spiffy.io/static/svg/loading.svg" style="position:absolute;left:0;top:0;right:0;bottom:0;margin:auto;max-width:100%;max-height:100%;z-index:1000;" hidden="true"/>');
    }
    form.find('img.loading').slideDown();
  }
  url = defined(options.url) ? options.url : options;
  method = defined(options.method) ? options.method : 'POST';
  $.ajax({
    url: url,
    data: data,
    dataType: 'json',
    headers: {
      'X-CSRF-Token': form.data('csrf-token')
    },
    type: method,
    success: function(data, textStatus, jqXHR) {
      success(data);
      if ((defined(options.loading)) && (options.loading === 'header')) {
        img = $('img.header-logo');
        img.attr('src', img.attr('src').replace('loading', 'icon'));
      } else {
        form.find('img.loading').hide(250);
      }
      if (disable) {
        form.spiffyEnable();
      }
      validate.resetForm();
    },
    error: function(jqXHR, textStatus, errorThrown) {
      var json;
      if ((defined(options.loading)) && (options.loading === 'header')) {
        img = $('img.header-logo');
        img.attr('src', img.attr('src').replace('loading', 'icon'));
      } else {
        form.find('img.loading').hide(250);
      }
      if (disable) {
        form.spiffyEnable;
      }
      if (jqXHR.status === 401) {
        handler(function(json) {
          $('.modal').modal('hide');
          $("meta[name='csrf-header']").attr('content', json.csrf.header);
          $("meta[name='csrf-token']").attr('content', json.csrf.token);
          form.submit();
        });
        $('#signin').modal('show');
        return;
      }
      json = jqXHR.responseJSON;
      if (json.error) {
        if (form.find('input[name="error"]').length === 0) {
          form.append('<input name="error" hidden="true">');
        }
        validate.showErrors({
          'error': json.error
        });
      }
      if (defined(error)) {
        error(validate, json);
      }
    }
  });
};

var adjustColumns, closeModal, emptyColumn, fillColumn, fingerprint, load, loadPosts, openModal, sortColumn;

Dropzone.options.dzForm = {
  paramName: 'file',
  maxFilesize: 2,
  accept: function(file, done) {
    done();
  },
  success: function(file, response) {
    var media;
    media = $('form.submit').find('input[name="media"]');
    if (media.val().length) {
      media.val(media.val() + ',' + response.id);
    } else {
      media.val(response.id);
    }
  }
};

$(document).ready(function(e) {
  var hash;
  $('div.header').mouseenter(function() {
    if ($(this).is(':hover')) {
      $(this).removeClass('hidden');
    }
  });
  $('div.header').mouseleave(function() {
    var func, header;
    header = $(this);
    func = function() {
      if ((!header.is(':hover')) && ($(window).scrollTop() > 400)) {
        header.addClass('hidden');
      }
    };
    setTimeout(func, 500);
  });
  $('[data-modal]').click(function(e) {
    openModal($(this).data('modal'));
  });
  $('[data-uri]').click(function(e) {
    go($(this).data('uri'));
  });
  if ($('input[name="fingerprint"]')) {
    hash = fingerprint();
    if (hash != null) {
      $('input[name="fingerprint"]').each(function() {
        $(this).val(hash);
      });
    }
  }
  $('form.load-posts').submit(function(e) {
    var form;
    preventDefault(e);
    form = $(this);
    form.spiffySubmit({
      url: '/posts',
      method: 'GET',
      loading: 'header'
    }, $(this).spiffyFormData(['after', 'quantity']), load);
  });
  $('form.login').submit(function(e) {
    var form;
    preventDefault(e);
    form = $(this);
    form.spiffySubmit('/login', $(this).spiffyFormData(['email', 'password', 'fingerprint', 'g-recaptcha-response']), function() {
      return go(form.data('returnUri'));
    });
  });
  $('form.register').submit(function(e) {
    var form;
    preventDefault(e);
    form = $(this);
    form.spiffySubmit('/register', $(this).spiffyFormData(['username', 'email', 'password', 'fingerprint', 'g-recaptcha-response']), function() {
      return go(form.data('returnUri'));
    });
  });
  $('form.submit').submit(function(e) {
    var form;
    preventDefault(e);
    form = $(this);
    form.spiffySubmit('/submit', $(this).spiffyFormData(['media', 'title', 'description', 'idempotentId']), function() {
      return go('/');
    });
  });
  $('.close').click(function(e) {
    closeModal();
  });
  $('.modal-overlay').click(function(e) {
    if ($(e.target).hasClass('modal-overlay')) {
      closeModal();
    }
  });
  adjustColumns();
});

openModal = function(modal) {
  if (!$('[data-modal-id="' + modal + '"]').length) {
    return;
  }
  $('[data-modal-id]').not('[data-modal-id="' + modal + '"]').hide(0);
  $('[data-modal-id="' + modal + '"]').show(0);
  $('.modal-overlay').show(0);
  $('.modal').slideDown(500);
};

closeModal = function() {
  $('.modal').slideUp(250, function() {
    $('[data-modal-id]').hide(0);
    return $('.modal-overlay').hide(0);
  });
};

fingerprint = function() {
  var fp, options;
  fp = sessionStorage.getItem('fingerprint');
  if (fp != null) {
    return fp;
  }
  options = {
    excludeAdBlock: true,
    excludeAvailableScreenResolution: true
  };
  new Fingerprint2(options).get(function(hash) {
    sessionStorage.setItem('fingerprint', hash);
    $('input[name="fingerprint"]').each(function() {
      $(this).val(hash);
    });
  });
  return null;
};

load = function(json) {
  loadPosts(json.posts);
  $('form.load-posts').find('input[name="after"]').val(json.next);
  adjustColumns();
};

loadPosts = function(posts) {
  var col, footer, i, img, j, panel, post, ref;
  for (i = j = 0, ref = posts.length; 0 <= ref ? j < ref : j > ref; i = 0 <= ref ? ++j : --j) {
    post = posts[i];
    panel = $(document.createElement('div'));
    panel.addClass('panel');
    img = $(document.createElement('img'));
    img.attr('src', post.url);
    panel.append(img);
    footer = $(document.createElement('div'));
    footer.addClass('footer');
    footer.html(post.title);
    panel.append(footer);
    col = $('.col[data-index="' + (i % 3) + '"]');
    col.append(panel);
  }
};

adjustColumns = function() {
  $('.col').each(function(i) {
    var offset;
    $(this).attr('data-index', i);
    offset = i;
    $(this).find('.panel').each(function(i) {
      $(this).attr('data-index', $('.col').length * i + offset);
    });
  });
  if ($(window).width() < Width.xl) {
    emptyColumn(2);
  }
  if ($(window).width() < Width.md) {
    emptyColumn(1);
  }
};

emptyColumn = function(i) {
  var col, panels;
  col = $('.col[data-index="' + i + '"]');
  panels = col.find('.panel');
  if (!panels.length) {
    return;
  }
  panels.each(function() {
    var index, panel;
    panel = $(this);
    index = panel.data('index');
    index = (i === 1) || (index % 2 === 0) ? index - 1 : index - 2;
    panel.detach().insertAfter('.panel[data-index="' + index + '"]');
  });
  sortColumn(1);
};

fillColumn = function(i) {
  var col, panels;
  col = $('.col[data-index="' + i + '"]');
  panels = col.find('.panel');
  if (panels.length) {
    return;
  }
  col = $('.col[data-index!="' + i + '"]');
  panels = col.find('.panel');
  panels.each(function() {
    var panel;
    panel = $(this);
    if (panel.data('index') % 3 !== i) {
      return;
    }
    panel.detach().appendTo('.col[data-index="' + i + '"]');
  });
  sortColumn(i);
};

sortColumn = function(i) {
  var col, panels, sorted;
  console.log('sort');
  col = $('.col[data-index="' + i + '"]');
  panels = col.find('.panel');
  sorted = panels.sort(function(a, b) {
    return $(a).data('index') - $(b).data('index');
  });
  col.html(sorted);
};

$(window).resize(function(e) {
  var width;
  width = $(window).width();
  if ((width > window.pWidth) && (width >= Width.md)) {
    $('.subheader').show();
    fillColumn(1);
  }
  if ((width < window.pWidth) && (width < Width.md)) {
    $('.subheader').hide();
    $('.hamburger').removeClass('active');
    emptyColumn(1);
  }
  if ((width > window.pWidth) && (width >= Width.xl)) {
    fillColumn(2);
  }
  if ((width < window.pWidth) && (width < Width.xl)) {
    emptyColumn(2);
  }
  window.pWidth = width;
});

$(window).scroll(function(e) {
  if ($(window).scrollTop() > 400) {
    $('div.header').addClass('hidden');
  } else {
    $('div.header').removeClass('hidden');
  }
  $('div.col').each(function() {
    var col, panel;
    col = $(this);
    panel = col.find('div.panel:last');
    if (panel.offset().top < $(window).scrollTop()) {
      $('form.load-posts').submit();
    }
  });
});

