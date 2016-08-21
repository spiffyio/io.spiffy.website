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

var Spiffy, base, base1, base2, base3, base4;

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

if ((base4 = String.prototype).equalsIgnoreCase == null) {
  base4.equalsIgnoreCase = function(s) {
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
      elements.find('form, input, textarea, select, button').addBack().prop('disabled', true).attr('data-disabled', true);
      return elements.spiffy();
    },
    enable: function() {
      elements.find('form, input, textarea, select, button').addBack().removeAttr('disabled').removeAttr('data-disabled');
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
      } else if (Array.isArray(data[name])) {
        data[name].push(value);
      } else {
        data[name] = [data[name], value];
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
    options: function(options) {
      var form;
      form = $(elements[0]);
      if (!form.is('form')) {
        return;
      }
      if (options != null) {
        form.data('options', options);
        return form.spiffy();
      }
      return form.data('options');
    },
    submit: function(options) {
      var csrf, data, disable, form, img, loading, src, type, url, validate;
      form = $(elements[0]);
      if (!form.is('form')) {
        return;
      }
      if (form.data('disabled')) {
        return;
      }
      options = Spiffy.firstDefined(options, $(form).spiffy().options(), {});
      validate = form.validate(Spiffy.firstDefined(options.validate, {}));
      if (validate.numberOfInvalids()) {
        return;
      }
      url = Spiffy.firstDefined(options.url, form.data('url'), form.attr('action'));
      type = Spiffy.firstDefined(options.type, form.data('type'), form.attr('method'), 'POST');
      csrf = form.data('csrf-token');
      if ((csrf == null) && (type.equalsIgnoreCase('POST'))) {
        alert('csrf required');
      }
      disable = Spiffy.firstDefined(options.disable, true);
      if (disable) {
        form.spiffy().disable();
      }
      loading = Spiffy.firstDefined(options.loading, form.data('loading'), 'overlay');
      if ('overlay'.equalsIgnoreCase(loading)) {
        img = form.find('img.loading');
        if ((img == null) || (!img.is('img.loading'))) {
          form.css('position', 'relative');
          img = $(document.createElement('img'));
          img.addClass('loading');
          img.attr('src', 'https://cdn.spiffy.io/static/svg/loading.svg');
          img.prop('hidden');
          form.append(img);
        }
        img.slideDown();
      } else if ('header'.equalsIgnoreCase(loading)) {
        img = $('img.header-logo');
        src = img.attr('src');
        src = src.replace('icon', 'loading');
        img.attr('src', src);
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
          return console.log(data);
        },
        error: function(jqXHR, textStatus, errorThrown) {
          return console.log(jqXHR.responseJSON);
        }
      });
      return form.spiffy();
    }
  };
};

var adjustColumns, closeModal, emptyColumn, fillColumn, fingerprint, load, loadPosts, openModal, sortColumn;

Dropzone.options.dzForm = {
  paramName: 'file',
  maxFiles: 1,
  maxFilesize: 20,
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
  $('[data-post]').click(function(e) {
    go('/stream/' + $(this).data('post'));
  });
  if ($('input[name="fingerprint"]')) {
    hash = fingerprint();
    if (hash != null) {
      $('input[name="fingerprint"]').each(function() {
        $(this).val(hash);
      });
    }
  }
  $('form:not(#dz-form)').submit(function(e) {
    var form;
    preventDefault(e);
    form = $(this);
    form.spiffy().submit();
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
  var start;
  loadPosts(json.posts);
  $('[data-post]').click(function(e) {
    go('/stream/' + $(this).data('post'));
  });
  start = $('form.load-posts').find('input[name="after"]').val();
  $('form.load-posts').find('input[name="after"]').val(json.next);
  adjustColumns();
  history.replaceState(json, 'SPIFFY.io', '/stream?start=' + start);
};

loadPosts = function(posts) {
  var col, footer, i, img, j, panel, post, ref;
  for (i = j = 0, ref = posts.length; 0 <= ref ? j < ref : j > ref; i = 0 <= ref ? ++j : --j) {
    post = posts[i];
    panel = $(document.createElement('div'));
    panel.addClass('panel');
    panel.attr('data-post', post.postId);
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
    if ((panel == null) || (panel.offset() == null)) {
      return;
    }
    if (panel.offset().top - panel.height() < $(window).scrollTop()) {
      $('form.load-posts').submit();
    }
  });
});

$(window).on('beforeunload', function() {
  if (location.search.contains('start')) {
    $(window).scrollTop(0);
  }
});

