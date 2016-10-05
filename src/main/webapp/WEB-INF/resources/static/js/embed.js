var Spiffy;

Spiffy = {};

Spiffy.constants = {};

Spiffy.c = Spiffy.constants;

Spiffy.c["enum"] = {};

Spiffy.c["enum"].loglevel = {
  FATAL: 0,
  ERROR: 1,
  WARN: 2,
  INFO: 3,
  DEBUG: 4,
  TRACE: 5
};

Spiffy.c.param = {
  ATTEMPT: 1,
  ETAG: void 0
};

Spiffy.c.retry = {
  MAX_COUNT: 3,
  TIMEOUT: 5000
};

Spiffy.c.key = {
  LEFT: 37,
  RIGHT: 39
};

Spiffy.c.size = {};

Spiffy.c.size.width = {
  XS: 320,
  SM: 480,
  MD: 768,
  LG: 992,
  XL: 120
};

Spiffy.c.config = {
  CDN: '//cdn.spiffy.io',
  LOGLEVEL: Spiffy.c["enum"].loglevel.TRACE
};

var confirmation, dataURItoBlob, defined, go, handler, initModalSize, overrideHandler, preventDefault, refresh;

Spiffy.functions = {};

Spiffy.f = Spiffy.functions;

Spiffy.f.log = function(level, value) {
  if (level <= Spiffy.c.config.LOGLEVEL) {
    console.log(value);
  }
};

Spiffy.f.authenticated = function() {
  return $('meta[name="account"]').length !== 0;
};

Spiffy.f.first = function() {
  var argument, defined, j;
  for (j = arguments.length - 1; j >= 0; j += -1) {
    argument = arguments[j];
    if (argument != null) {
      defined = argument;
    }
  }
  return defined;
};

Spiffy.f.firstDefined = Spiffy.f.first;

Spiffy.firstDefined = Spiffy.f.firstDefined;

Spiffy.f.click = function(selector, handler, preventDefault) {
  if (preventDefault == null) {
    preventDefault = true;
  }
  $(document).on('click', selector, function(e) {
    if (preventDefault) {
      Spiffy.f.prevent(e);
    }
    handler(e, $(this));
  });
};

Spiffy.f.prevent = function(e) {
  e.preventDefault();
};

Spiffy.f.element = {};

Spiffy.f.element.template = function(name) {
  var selector;
  selector = '[data-template="$name"]'.replace('$name', name);
  return $(selector);
};

Spiffy.f.timeout = {};

Spiffy.f.timeout.simple = function(timeout, call) {
  setTimeout(call, timeout);
};

Spiffy.f.timeout.retry = function(attempt, call) {
  if (attempt >= Spiffy.c.retry.MAX_COUNT) {
    Spiffy.f.log(Spiffy.c["enum"].loglevel.ERROR, 'max retry attempts exceeded... ' + call);
    return;
  }
  setTimeout(call, Spiffy.c.retry.TIMEOUT * attempt * attempt);
};

Spiffy.f.update = {};

Spiffy.f.update.notifications = function(count) {
  var span;
  span = $('span.notification-count');
  if (count === 0) {
    document.title = 'SPIFFY.io';
    span.html('');
  } else {
    document.title = '(' + count + ') SPIFFY.io';
    span.html(count);
  }
};

Spiffy.f.update.poll = function(etag, attempt) {
  if (etag == null) {
    etag = Spiffy.c.param.ETAG;
  }
  if (attempt == null) {
    attempt = Spiffy.c.param.ATTEMPT;
  }
  if (!Spiffy.f.authenticated()) {
    Spiffy.f.log(Spiffy.c["enum"].loglevel.INFO, 'polling disabled...');
    return;
  }
  Spiffy.f.log(Spiffy.c["enum"].loglevel.INFO, 'polling...' + (etag != null ? ' [etag: ' + etag + ']' : ''));
  return $.get({
    url: '/longpoll',
    dataType: 'json',
    cache: false,
    beforeSend: function(xhr) {
      if (etag != null) {
        xhr.setRequestHeader('If-None-Match', etag);
      }
    },
    success: function(data, status, xhr) {
      Spiffy.f.update.notifications(data.notifications);
      Spiffy.f.update.poll(xhr.getResponseHeader('ETag'));
    },
    error: function() {
      Spiffy.f.timeout.retry(attempt, function() {
        Spiffy.f.update.poll(etag, attempt + 1);
      });
    }
  });
};

preventDefault = function(e) {
  Spiffy.f.prevent(e);
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

confirmation = function(title, action) {
  var dialog;
  dialog = $('#confirmation');
  dialog.find('.modal-title').text(title);
  dialog.find('.continue').on('click', function(e) {
    Spiffy.f.prevent(e);
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

defined = function(v) {
  if (typeof v === 'undefined') {
    return false;
  }
  return v !== 'undefined';
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

Spiffy.f.click('a[href="#"]', function() {
  return {};
});

Spiffy.f.click('a.menu', function(e, toggle) {
  var menu;
  toggle.toggleClass('expanded');
  menu = toggle.parent().find('.sub-menu');
  menu.toggleClass('show');
});

Spiffy.f.click('[data-go]', function(e, button) {
  go(button.data('go') + location.search);
});

Spiffy.f.click('.close', function() {
  closeModal();
});

Spiffy.f.click('.modal-overlay', function(e) {
  if ($(e.target).hasClass('modal-overlay')) {
    closeModal();
  }
});

Spiffy.f.click('div.pause, video', function(e, control) {
  var video;
  video = control.parent().parent().find('video');
  video.attr('data-clicked', true);
  if (video[0].paused) {
    video[0].play();
    video.parents('div.video:first').removeClass('paused');
  } else {
    video[0].pause();
    video.parents('div.video:first').addClass('paused');
  }
});



