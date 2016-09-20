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

var blank, confirmation, contains, dataURItoBlob, defined, go, handler, high, initModalSize, medium, overrideHandler, preventDefault, quality, refresh;

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

dataURItoBlob = function(dataURI) {
  var byteString, bytes, c, data, i, j, len, meta, mimeType, parts, ref;
  parts = dataURI.split(',');
  meta = parts[0];
  data = parts[1];
  byteString = meta.containsIgnoreCase('base64') ? atob(data) : unescape(data);
  mimeType = meta.split(':')[1].split(';')[0];
  bytes = new Uint8Array(byteString.length);
  ref = byteString.split('');
  for (i = j = 0, len = ref.length; j < len; i = ++j) {
    c = ref[i];
    bytes[i] = c.charCodeAt(0);
  }
  return new Blob([bytes], {
    type: mimeType
  });
};

var Spiffy, base, base1, base2, base3, base4, base5;

if ((base = Array.prototype).isArray == null) {
  base.isArray = function(a) {
    return {}.toString.call(value) === '[object Array]';
  };
}

if ((base1 = String.prototype).startsWith == null) {
  base1.startsWith = function(s) {
    return this.slice(0, s.length) === s;
  };
}

if ((base2 = String.prototype).endsWith == null) {
  base2.endsWith = function(s) {
    return s === '' || this.slice(-s.length) === s;
  };
}

if ((base3 = String.prototype).contains == null) {
  base3.contains = function(s) {
    return s === '' || this.indexOf(s) > -1;
  };
}

if ((base4 = String.prototype).containsIgnoreCase == null) {
  base4.containsIgnoreCase = function(s) {
    return s === '' || this.toUpperCase().indexOf(s.toUpperCase()) > -1;
  };
}

if ((base5 = String.prototype).equalsIgnoreCase == null) {
  base5.equalsIgnoreCase = function(s) {
    return this.toUpperCase() === s.toUpperCase();
  };
}

Spiffy = {
  firstDefined: function() {
    var argument, defined, i;
    for (i = arguments.length - 1; i >= 0; i += -1) {
      argument = arguments[i];
      if (argument != null) {
        defined = argument;
      }
    }
    if (defined != null) {
      return defined;
    }
    return void 0;
  }
};

jQuery.fn.spiffy = function() {
  var elements, fn;
  elements = $(this);
  return fn = {
    end: function() {
      return elements;
    },
    disable: function() {
      elements.find('form, input, textarea, select, button, div.g-recaptcha').addBack().not('[data-disabled="permanent"]').prop('disabled', true).attr('data-disabled', true).addClass('disabled').data('disabled', true);
      return elements.spiffy();
    },
    enable: function() {
      elements.find('form, input, textarea, select, button, div.g-recaptcha').addBack().not('[data-disabled="permanent"]').removeAttr('disabled').removeAttr('data-disabled').removeClass('disabled').data('disabled', false);
      return elements.spiffy();
    },
    clear: function() {
      elements.find('input, textarea').val('');
      return elements.spiffy();
    },
    push: function(data) {
      var element, name, value;
      element = $(elements[0]);
      name = element.attr('name');
      value = element.val();
      if (data[name] == null) {
        data[name] = value;
      } else {
        data[name] = data[name] + "," + value;
      }
      return data;
    },
    data: function() {
      var data, element, i, len;
      if (elements.length > 1) {
        data = [];
        for (i = 0, len = elements.length; i < len; i++) {
          element = elements[i];
          data.push($(element).spiffy().data());
        }
        return data;
      }
      element = $(elements[0]);
      if (!element.is('form')) {
        data = element.val();
        return data;
      }
      data = {};
      element.find('[name]').each(function() {
        return $(this).spiffy().push(data);
      });
      $('[data-form-name="' + element.data('name') + '"]').each(function() {
        return $(this).spiffy().push(data);
      });
      return data;
    },
    loading: function(loading, enable, time) {
      var div, form, img, src;
      if (enable == null) {
        enable = true;
      }
      if (time == null) {
        time = 2000;
      }
      form = $(elements[0]);
      if (!form.is('form')) {
        return;
      }
      if ('overlay'.equalsIgnoreCase(loading)) {
        img = form.find('img.loading');
        if (enable && ((img == null) || (!img.is('img.loading')))) {
          form.css('position', 'relative');
          img = $(document.createElement('img'));
          img.addClass('loading');
          img.attr('src', 'https://cdn.spiffy.io/static/svg/loading.svg');
          img.prop('hidden');
          form.append(img);
        }
        if (enable && (img != null) && img.is('img.loading')) {
          img.slideDown();
        }
        if (!enable && (img != null) && img.is('img.loading')) {
          img.slideUp();
        }
      } else if ('header'.equalsIgnoreCase(loading)) {
        img = $('img.header-icon');
        if ((img != null) && img.is('img.header-icon')) {
          src = img.attr('src');
          src = enable ? src.replace('icon', 'loading') : src.replace('loading', 'icon');
          img.attr('src', src);
        }
        div = $('div.header-loading');
        if ((div != null) && div.is('div.header-loading')) {
          if (enable) {
            div.animate({
              width: '80%'
            }, time);
          } else {
            div.finish().animate({
              width: '100%'
            }, 250, 'swing', function() {
              return div.animate({
                opacity: '0'
              }, 500, 'swing', function() {
                div.css('width', '0%');
                return div.css('opacity', '1');
              });
            });
          }
        }
      }
      return form.spiffy();
    },
    options: function(options) {
      var form;
      form = $(elements[0]);
      if (!form.is('form')) {
        return;
      }
      if (options != null) {
        form.data('options', $.extend(form.data('options'), options));
        return form.spiffy();
      }
      return form.data('options');
    },
    submit: function(options) {
      var button, csrf, data, disable, div, form, loading, type, url;
      form = $(elements[0]);
      if (!form.is('form')) {
        return;
      }
      if (form.data('disabled')) {
        return;
      }
      options = Spiffy.firstDefined(options, $(form).spiffy().options(), {});
      url = Spiffy.firstDefined(options.url, form.data('url'), form.attr('action'));
      url = url.startsWith('+') ? window.location.pathname + url.substr(1) : url;
      type = Spiffy.firstDefined(options.type, form.data('type'), form.attr('method'), 'POST');
      csrf = form.data('csrf-token');
      if ((csrf == null) && (type.equalsIgnoreCase('POST'))) {
        alert('csrf required');
      }
      if ((options.confirm != null) && !form.data('confirmed')) {
        button = $('#confirm-action');
        button.html(options.confirm);
        button.click(function() {
          closeModal();
          form.attr('data-confirmed', true);
          form.submit();
          form.attr('data-confirmed', false);
        });
        openModal();
        return;
      }
      disable = Spiffy.firstDefined(options.disable, true);
      if (disable) {
        form.spiffy().disable();
      }
      loading = Spiffy.firstDefined(options.loading, form.data('loading'), 'overlay');
      form.spiffy().loading(loading);
      div = form.find('div.message');
      if ((div != null) && div.is('div.message')) {
        div.slideUp();
      }
      data = form.spiffy().data();
      $.ajax({
        url: url,
        data: data,
        dataType: 'json',
        headers: {
          'X-CSRF-Token': csrf
        },
        type: type,
        success: function(data, textStatus, jqXHR) {
          form.spiffy().enable().loading(loading, false);
          if (options.success != null) {
            return options.success(form, data, textStatus, jqXHR);
          }
        },
        error: function(jqXHR, textStatus, errorThrown) {
          var input, recaptcha, response, span;
          response = jqXHR.responseJSON;
          if ((jqXHR.status === 401) && (response.uri != null)) {
            go(jqXHR.responseJSON.uri);
            return;
          }
          if ((jqXHR.status === 400) && (response.error != null)) {
            input = form.find('input[name="' + response.error + '"]');
            if ((input != null) && input.is('input[name="' + response.error + '"]')) {
              div = input.parent();
              span = div.find('span');
              div.addClass('bt-flabels__error');
              span.html(response.reason);
            } else {
              div = form.find('div.message');
              div.addClass('error');
              div.html(response.reason);
              div.slideDown();
            }
          }
          if (response.tip != null) {
            div = form.find('div.message');
            div.addClass('tip');
            div.html(response.tip);
            div.slideDown();
          }
          recaptcha = form.find('.g-recaptcha');
          if ((recaptcha != null) && recaptcha.is('.g-recaptcha')) {
            grecaptcha.reset();
          }
          form.spiffy().enable().loading(loading, false);
          if (options.error != null) {
            return options.error(form, jqXHR, textStatus, errorThrown);
          }
        }
      });
      return form.spiffy();
    }
  };
};

var Messenger;

$(document).on('click', 'a[href="#"]', function(e) {
  e.preventDefault();
});

Handlebars.html = function(name, data) {
  var template;
  template = Handlebars.compile($('[data-template="' + name + '"]').html());
  return template(data);
};

$(document).on('click', '.chat-thread', function(e) {
  Messenger.open($(this));
});

$(document).on('click', '.new-message', function(e) {
  Messenger["new"]();
});

window.addEventListener('popstate', function(event) {
  var parts, uri;
  uri = window.location.pathname;
  parts = uri.split('/');
  if (!parts.length === 3) {
    return;
  }
  if (!parts[1].equalsIgnoreCase('messages')) {
    return;
  }
  Messenger.open($('[data-thread-id="' + parts[2] + '"]'), false);
});

$(document).ready(function(e) {
  var func, id, parts, thread, uri, url;
  thread = $('.chat-thread.active');
  id = thread.data('thread-id');
  url = '/messages/' + id;
  uri = window.location.pathname;
  parts = uri.split('/');
  if (parts.length === 2 && parts[1].equalsIgnoreCase('messages')) {
    history.replaceState({
      id: id
    }, id, url);
  }
  $('form.new').spiffy().options({
    success: function(form, json) {
      form.find('input[type="text"]').val('');
      json.thread.display = 'none';
      Messenger.addThread(json.thread);
      Messenger.open($('[data-thread-id="' + json.thread.id + '"]'));
    }
  });
  $('form.message').spiffy().options({
    success: function(form, json) {
      form.find('input[type="text"]').val('');
      form.find('input[name="idempotentId"]').val(json.idempotentId);
      json.message.display = 'none';
      Messenger.addMessage(json.message);
      form.find('input[type="text"]').focus();
    }
  });
  thread = $('.chat-thread.active');
  id = thread.data('thread-id');
  url = '/messages/' + id;
  func = function() {
    var data, message;
    if (!thread.hasClass('active')) {
      return;
    }
    data = {};
    message = $('[data-message-id]:first');
    if (message.is('[data-message-id]:first')) {
      data.after = message.data('message-id');
    }
    return $.get({
      url: url,
      dataType: 'json',
      data: data,
      cache: false,
      success: function(data) {
        Messenger.loadMessages(data);
        return setTimeout(func, 10000);
      }
    });
  };
  func();
});

Messenger = {
  add: function(container, selector, template, data) {
    var element;
    element = $(selector);
    if (element.is(selector)) {
      return;
    }
    container = $(container);
    container.prepend(Handlebars.html(template, data));
    element = $(selector);
    element.slideDown();
    element.animate({
      opacity: 1
    }, 500);
  },
  addThread: function(data) {
    Messenger.add('.chats-body', '[data-thread-id="' + data.id + '"]', 'chat-thread', data);
  },
  addMessage: function(data) {
    Messenger.add('.chat-body', '[data-message-id="' + data.id + '"]', 'message-group', data);
  },
  "new": function() {
    var form, title;
    form = $('form.new');
    title = form.parent().find('.title');
    if (form.is(':visible')) {
      Messenger.open($('[data-thread-id="' + title.html() + '"]'));
    } else {
      Messenger.open($('[data-thread-id="new"]'));
    }
  },
  loadMessages: function(data) {
    var i, len, message, messages;
    if (data == null) {
      return;
    }
    messages = data.messages;
    if (messages == null) {
      return;
    }
    for (i = 0, len = messages.length; i < len; i++) {
      message = messages[i];
      message.opacity = '0.0';
      Messenger.addMessage(message);
    }
  },
  open: function(thread, pushState) {
    var body, chat, first, form, func, header, id, input, title, url;
    if (pushState == null) {
      pushState = true;
    }
    if (!thread.length) {
      return;
    }
    if (thread.hasClass('active')) {
      return;
    }
    $('.chat-thread.active').removeClass('active');
    thread.addClass('active');
    id = thread.data('thread-id');
    url = '/messages/' + id;
    if (pushState) {
      history.pushState({
        id: id
      }, id, url);
    }
    if (!id.equalsIgnoreCase('new')) {
      first = true;
      func = function() {
        var data, message;
        if (!thread.hasClass('active')) {
          return;
        }
        data = {};
        if (!first) {
          message = $('[data-message-id]:first');
          if (message.is('[data-message-id]:first')) {
            data.after = message.data('message-id');
          }
        } else {
          first = false;
        }
        return $.get({
          url: url,
          dataType: 'json',
          data: data,
          cache: false,
          success: function(data) {
            Messenger.loadMessages(data);
            return setTimeout(func, 10000);
          }
        });
      };
      func();
    }
    chat = $('.chat');
    header = chat.find('.chat-header');
    form = header.find('form');
    title = header.find('.title');
    if (id.equalsIgnoreCase('new')) {
      title.hide();
      form.show();
      input = form.find('input');
    } else {
      title.html(id).show();
      form.hide();
      input = $('form.message').find('[name="message"]');
    }
    input.focus();
    body = chat.find('.chat-body');
    body.html('');
  }
};

var profileDZ, profileDZAddedFile;

Dropzone.autoDiscover = false;

profileDZ = new Dropzone('form#profile-dz', {
  paramName: 'file',
  maxFiles: 1,
  maxFilesize: 200,
  uploadMultiple: false,
  createImageThumbnails: true,
  autoProcessQueue: false,
  accept: function(file, done) {
    var type;
    file.acceptDimensions = done;
    file.rejectDimensions = function() {
      return done('image must be at least 160 x 160 pixels');
    };
    type = file.type;
    if (type.equalsIgnoreCase('image/jpg')) {
      done();
    } else if (type.equalsIgnoreCase('image/jpeg')) {
      done();
    } else if (type.equalsIgnoreCase('image/png')) {
      done();
    } else {
      done('unable to upload file: ' + file.name);
    }
  },
  init: function() {
    this.on('thumbnail', function(file) {
      if (file.width < 160 || file.height < 160) {
        file.rejectDimensions();
      } else {
        file.acceptDimensions();
      }
    });
    return this.on('addedfile', function(file) {
      profileDZAddedFile(file);
    });
  }
});

profileDZAddedFile = function(file) {
  var croppie, div, form, message, src;
  if (file.accepted == null) {
    setTimeout(function() {
      return profileDZAddedFile(file);
    }, 10);
    return;
  }
  if (!file.accepted) {
    form = $(profileDZ.element);
    message = form.find('.message');
    message.html('unable to upload file: ' + file.name);
    message.slideDown();
    form.animate({
      height: '10em'
    }, 500);
    return;
  }
  if (profileDZ.options.autoProcessQueue) {
    return;
  }
  $(profileDZ.element).hide();
  openModal();
  src = URL.createObjectURL(file);
  div = $('.profile-container');
  croppie = new Croppie(div.find('.croppie')[0], {
    url: src,
    enableOrientation: true,
    viewport: {
      width: 160,
      height: 160,
      type: 'square'
    }
  });
  div.find('.button.primary').click(function() {
    croppie.result({
      size: 'original'
    }).then(function(canvas) {
      var blob;
      blob = dataURItoBlob(canvas);
      profileDZ.removeAllFiles(true);
      profileDZ.options.autoProcessQueue = true;
      profileDZ.addFile(blob);
    });
    return croppie.get();
  });
};

var addedfile, adjustColumns, closeModal, emptyColumn, fillColumn, fingerprint, load, loadPosts, openModal, sortColumn;

addedfile = function(file) {
  var div, form, func, img, message, preview, processing, source, src, video;
  if (file.accepted == null) {
    func = function() {
      addedfile(file);
    };
    setTimeout(func, 10);
    return;
  }
  if (!file.accepted) {
    form = $('#dz-form');
    message = form.find('.message');
    message.html('unable to upload file: ' + file.name);
    message.slideDown();
    form.animate({
      height: '10em'
    }, 500);
    return;
  }
  form = $('form.submit');
  preview = form.find('div.preview');
  div = $(document.createElement('div'));
  src = URL.createObjectURL(file);
  form.attr('data-media-src', src);
  form.attr('data-media-type', file.type);
  if (file.type.containsIgnoreCase('video')) {
    form.spiffy().loading('header', true, 30000);
    div.addClass('video');
    div.addClass('paused');
    video = $(document.createElement('video'));
    video.attr('autoplay', true);
    video.attr('muted', true);
    video.attr('loop', true);
    video.attr('preload', 'auto');
    video.attr('poster', '/static/png/processing.png');
    source = $(document.createElement('source'));
    source.attr('type', file.type);
    source.attr('src', src);
    video.append(source);
    processing = $(document.createElement('img'));
    processing.attr('src', '/static/png/processing.png');
    video.append(processing);
    div.append(video);
  } else if (file.type.containsIgnoreCase('image')) {
    form.spiffy().loading('header', true, 5000);
    img = $(document.createElement('img'));
    img.attr('src', src);
    div.append(img);
  } else {
    form.spiffy().loading('header', true, 5000);
    img = $(document.createElement('img'));
    img.attr('src', '/static/png/processing.png');
    div.append(img);
  }
  preview.prepend(div);
  form.slideDown();
};

Dropzone.options.dzForm = {
  paramName: 'file',
  maxFiles: 1,
  maxFilesize: 200,
  uploadMultiple: false,
  createImageThumbnails: false,
  autoProcessQueue: true,
  accept: function(file, done) {
    var type;
    type = file.type;
    if (type.equalsIgnoreCase('image/gif')) {
      done();
    } else if (type.equalsIgnoreCase('image/jpg')) {
      done();
    } else if (type.equalsIgnoreCase('image/jpeg')) {
      done();
    } else if (type.equalsIgnoreCase('image/png')) {
      done();
    } else if (type.equalsIgnoreCase('video/mov')) {
      done();
    } else if (type.equalsIgnoreCase('video/mp4')) {
      done();
    } else if (type.equalsIgnoreCase('video/mpeg4')) {
      done();
    } else if (type.equalsIgnoreCase('video/quicktime')) {
      done();
    } else if (type.equalsIgnoreCase('video/webm')) {
      done();
    } else {
      done('unable to upload file: ' + file.name);
    }
  },
  init: function() {
    this.on('addedfile', function(file) {
      addedfile(file);
    });
    return this.on('uploadprogress', function(file) {});
  },
  success: function(file, response) {
    var form, media;
    form = $('form.submit');
    form.spiffy().enable().loading('header', false);
    media = form.find('input[name="media"]');
    media.val(response.name);
  }
};

$(document).ready(function(e) {
  var hash;
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
  $('[data-unprocessed]').each(function() {
    var div, img, post, processing, source, src, type, video;
    div = $(this);
    post = div.data('unprocessed');
    src = sessionStorage.getItem('src:' + post);
    type = sessionStorage.getItem('type:' + post);
    if (!((src != null) && (type != null))) {
      return;
    }
    if (type.containsIgnoreCase('video')) {
      div.addClass('video');
      div.addClass('paused');
      video = $(document.createElement('video'));
      video.attr('autoplay', true);
      video.attr('muted', true);
      video.attr('loop', true);
      video.attr('preload', 'auto');
      video.attr('poster', '/static/png/processing.png');
      source = $(document.createElement('source'));
      source.attr('type', type);
      source.attr('src', src);
      video.append(source);
      processing = $(document.createElement('img'));
      processing.attr('src', '/static/png/processing.png');
      video.append(processing);
      div.html(video);
    } else if (type.containsIgnoreCase('image')) {
      img = $(document.createElement('img'));
      img.attr('src', src);
      div.html(img);
    }
  });
  $('form:not(#dz-form)').submit(function(e) {
    var form;
    preventDefault(e);
    form = $(this);
    form.spiffy().submit();
  });
  $('form.load-posts').spiffy().options({
    success: function(form, json) {
      if (json.posts == null) {
        return form.spiffy().disable();
      } else {
        return load(json);
      }
    }
  });
  $('form.forgot').spiffy().options({
    success: function(form) {
      var div;
      form.spiffy().disable();
      div = form.find('div.message');
      div.addClass('success');
      div.html('recovery email sent');
      div.slideDown();
    }
  });
  $('form[data-return-uri]').spiffy().options({
    success: function(form) {
      return go(form.data('return-uri'));
    }
  });
  $('form.comment').spiffy().options({
    success: function() {
      return refresh();
    }
  });
  $('form.notifications').spiffy().options({
    success: function(form, data) {
      var func, span;
      span = $('span.notification-count');
      if (data.count === 0) {
        document.title = 'SPIFFY.io';
        span.html('');
      } else {
        document.title = '(' + data.count + ') SPIFFY.io';
        span.html(data.count);
      }
      func = function() {
        return form.submit();
      };
      setTimeout(func, 30000);
    }
  });
  setTimeout(function() {
    return $('form.notifications').submit();
  }, 15000);
  $('form.submit').spiffy().options({
    success: function(form, data) {
      sessionStorage.setItem('src:' + data.name, form.data('media-src'));
      sessionStorage.setItem('type:' + data.name, form.data('media-type'));
      return go('/stream/' + data.name);
    }
  });
  $('a[data-form]').click(function(e) {
    var form;
    preventDefault(e);
    form = $('form.' + $(this).data('form'));
    form.submit();
  });
  $('a[data-session-id]').click(function(e) {
    var form;
    preventDefault(e);
    form = $('form.logout');
    form.find('input[name="session"]').val($(this).data('session-id'));
    form.submit();
  });
  $('form.logout').spiffy().options({
    success: function(form) {
      var button, input, session;
      input = form.find('input[name="session"]');
      session = input.val();
      button = $('[data-session-id="' + session + '"]');
      button.parent().parent().slideUp();
    }
  });
  $('form.delete').each(function() {
    $(this).spiffy().options({
      success: function() {
        refresh();
      },
      confirm: 'confirm deletion'
    });
  });
  $('form.action').spiffy().options({
    success: function() {
      go('/');
    }
  });
  $('div.actions').find('button').each(function(e) {
    var button, form;
    button = $(this);
    form = button.parents('div.actions:first').find('form');
    button.click(function(e) {
      var action, input;
      input = form.find('input[name="action"]');
      action = button.data('action');
      input.val(action);
      form.spiffy().options({
        confirm: 'confirm ' + action
      });
      form.submit();
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
  $('video[data-autoplay="true"]').each(function() {
    var video;
    video = $(this);
    if ((!video.is(':in-viewport')) || (!video.is(':in-viewport(' + (video[0].getBoundingClientRect().bottom - video[0].getBoundingClientRect().top) + ')'))) {
      if (!video[0].paused) {
        video[0].pause();
        video.parents('div.video:first').addClass('paused');
      }
    } else if (video[0].paused) {
      video[0].play();
      video.parents('div.video:first').removeClass('paused');
    }
  });
  $(document).on('click', 'a.menu', function(e) {
    var menu, toggle;
    toggle = $(this);
    toggle.toggleClass('expanded');
    menu = toggle.parent().find('.sub-menu');
    menu.toggleClass('show');
  });
  $(document).on('click', '.thismedia', function(e) {
    var form, img, input;
    img = $(this);
    img.toggleClass('clicked');
    form = $('form.delete');
    if ($('img.thismedia.clicked').length) {
      form.spiffy().enable();
    } else {
      form.spiffy().disable();
    }
    if (img.hasClass('clicked')) {
      input = $(document.createElement('input'));
      input.attr('type', 'hidden');
      input.attr('name', 'media');
      input.val(img.data('media-name'));
      form.append(input);
    } else {
      form.find('input[type="hidden"]').each(function() {
        input = $(this);
        if (input.val() === img.data('media-name')) {
          input.remove();
        }
      });
    }
  });
  $(document).on('click', '[data-go]', function(e) {
    var button;
    button = $(this);
    go(button.data('go') + location.search);
  });
  $(document).on('click', 'video', function(e) {
    var video;
    video = $(this);
    video.attr('data-clicked', true);
    if (video[0].paused) {
      video[0].play();
      video.parents('div.video:first').removeClass('paused');
    } else {
      video[0].pause();
      video.parents('div.video:first').addClass('paused');
    }
  });
  adjustColumns();
  $('div.input').each(function() {
    var div, input, label, span;
    div = $(this);
    input = div.find('input');
    label = $(document.createElement('label'));
    label.html(input.attr('placeholder'));
    div.prepend(label);
    span = $(document.createElement('span'));
    span.addClass('bt-flabels__error-desc');
    span.html(Spiffy.firstDefined(div.data('error-message'), 'error'));
    div.append(span);
  });
});

openModal = function() {
  $('.modal-overlay').slideDown(500);
};

closeModal = function() {
  $('.modal-overlay').slideUp(250);
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
  var form, input;
  loadPosts(json.posts);
  form = $('form.load-posts');
  input = form.find('input[name="after"]');
  input.val(json.next);
};

loadPosts = function(posts) {
  var centeryo, col, cols, content, div, i, img, index, j, last, panel, post, ref, source, template, video;
  for (i = j = 0, ref = posts.length; 0 <= ref ? j < ref : j > ref; i = 0 <= ref ? ++j : --j) {
    post = posts[i];
    panel = $(document.createElement('div'));
    panel.addClass('panel');
    panel.attr('data-post-id', post.postId);
    centeryo = $(document.createElement('div'));
    centeryo.addClass('centeryo');
    div = $(document.createElement('div'));
    div.addClass('mediayo');
    content = post.content;
    if (content.type.equalsIgnoreCase('video')) {
      div.addClass('video');
      div.addClass('paused');
      video = $(document.createElement('video'));
      video.attr('muted', true);
      video.attr('loop', true);
      video.attr('poster', content.poster.file);
      source = $(document.createElement('source'));
      source.attr('src', content.mp4);
      source.attr('type', 'video/mp4');
      video.append(source);
      source = $(document.createElement('source'));
      source.attr('src', content.webm);
      source.attr('type', 'video/webm');
      video.append(source);
      if (content.gif != null) {
        img = $(document.createElement('img'));
        img.attr('src', content.gif);
        video.append(img);
      }
      div.prepend(video);
    } else if (content.type.equalsIgnoreCase('image')) {
      img = $(document.createElement('img'));
      img.attr('src', content.medium);
      div.prepend(img);
    } else {
      template = Handlebars.compile($('[data-template="panel-ad"]').html());
      div.append(template({}));
    }
    centeryo.html(div);
    panel.prepend(centeryo);
    if (content.type.equalsIgnoreCase('ad')) {
      template = Handlebars.compile($('[data-template="panel-ad-source"]').html());
    } else {
      template = Handlebars.compile($('[data-template="panel-source"]').html());
    }
    panel.append(template({
      post: post
    }));
    cols = 3;
    if ($(window).width() < Width.xl) {
      cols = 2;
    }
    if ($(window).width() < Width.md) {
      cols = 1;
    }
    col = $('.col[data-index="' + (i % cols) + '"]');
    last = col.find('.panel:last');
    index = (last != null) && (last.is('.panel')) ? last.data('index') + cols : col.data('index');
    panel.attr('data-index', index);
    col.append(panel);
    if (content.type.equalsIgnoreCase('ad')) {
      (adsbygoogle = window.adsbygoogle || []).push({});;
    }
  }
};

adjustColumns = function() {
  var col, form, input, panel;
  $('.col').each(function(i) {
    var offset;
    $(this).attr('data-index', i);
    offset = i;
    $(this).find('.panel').each(function(i) {
      $(this).attr('data-index', $('.col').length * i + offset);
    });
  });
  form = $('form.load-posts');
  if ((form != null) && form.is('form.load-posts')) {
    col = $('.col[data-index="2"]');
    panel = col.find('.panel:last');
    if (panel.data('post-id').equalsIgnoreCase('ad')) {
      col = $('.col[data-index="1"]');
      panel = col.find('.panel:last');
    }
    input = form.find('input[name="after"]');
    input.val(panel.data('post-id'));
  }
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

fillColumn = function(i, cols) {
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
    if (panel.data('index') % cols !== i) {
      return;
    }
    panel.detach().appendTo('.col[data-index="' + i + '"]');
  });
  sortColumn(i);
};

sortColumn = function(i) {
  var col, panels, sorted;
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
    fillColumn(1, 2);
  }
  if ((width < window.pWidth) && (width < Width.md)) {
    $('.subheader').hide();
    $('.hamburger').removeClass('active');
    emptyColumn(1);
  }
  if ((width > window.pWidth) && (width >= Width.xl)) {
    emptyColumn(1);
    fillColumn(1, 3);
    fillColumn(2, 3);
  }
  if ((width < window.pWidth) && (width < Width.xl)) {
    emptyColumn(2);
  }
  window.pWidth = width;
});

$(window).scroll(function(e) {
  $('video').each(function() {
    var source, top, video;
    video = $(this);
    if (video.data('clicked')) {
      return;
    }
    top = video[0].getBoundingClientRect().top;
    source = video.parents('.panel:first').find('.source');
    if ((top < 75) || (!video.is(':in-viewport')) || (!source.is(':in-viewport'))) {
      if (!video[0].paused) {
        video[0].pause();
        video.parents('div.video:first').addClass('paused');
      }
    } else if (video[0].paused) {
      video[0].play();
      if (!video[0].paused) {
        video.parents('div.video:first').removeClass('paused');
      }
    }
  });
});

$(window).scroll(function(e) {
  $('div.col').each(function() {
    var col, panel;
    col = $(this);
    panel = col.find('div.panel:last');
    if (panel == null) {
      return;
    }
    if (panel.is(':in-viewport')) {
      $('form.load-posts').submit();
    }
  });
});

(function($) {
  'use strict';
  var floatingLabel;
  floatingLabel = function(onload) {
    var $input;
    $input = $(this);
    if (onload) {
      $.each($('.input input'), function(index, value) {
        var $current_input;
        $current_input = $(value);
        if ($current_input.val()) {
          $current_input.closest('.input').addClass('bt-flabel__float');
        }
      });
    }
    setTimeout((function() {
      if ($input.val()) {
        $input.closest('.input').addClass('bt-flabel__float');
      } else {
        $input.closest('.input').removeClass('bt-flabel__float');
      }
    }), 1);
  };
  $('.input input').keydown(floatingLabel);
  $('.input input').change(floatingLabel);
  window.addEventListener('load', floatingLabel(true), false);
  $('form').each(function() {
    var form;
    form = $(this);
    if (!form.find('input').length) {
      return;
    }
    form.attr('data-parsley-errors-messages-disabled', '');
    form.parsley().on('form:error', function() {
      $.each(this.fields, function(key, field) {
        var div, reason;
        if (field.validationResult !== true) {
          reason = field.validationResult[0].assert.name;
          if (reason.equalsIgnoreCase('type')) {
            reason = 'invalid ' + field.validationResult[0].assert.requirements;
          } else if (reason.equalsIgnoreCase('equalto')) {
            reason = 'unmatched ' + field.$element.parents('form:first').find(field.validationResult[0].assert.requirements).attr('placeholder');
          }
          div = field.$element.parent();
          div.find('span').html(reason);
          div.addClass('bt-flabels__error');
        }
      });
    });
    return form.parsley().on('field:validated', function() {
      var div;
      if (this.validationResult === true) {
        div = this.$element.parent();
        div.removeClass('bt-flabels__error');
      } else {
        div = this.$element.parent();
        div.addClass('bt-flabels__error');
      }
    });
  });
})(jQuery);

