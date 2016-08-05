var menu;

Dropzone.options.dzForm = {
  paramName: 'file',
  maxFilesize: 2,
  accept: function(file, done) {
    return done();
  }
};

$(document).ready(function(e) {
  $('div.header').mouseenter(function() {
    var func, header;
    header = $(this);
    func = function() {
      if (header.is(':hover')) {
        menu('0');
      }
    };
    setTimeout(func, 200);
  });
  $('div.header').mouseleave(function() {
    var func, header;
    header = $(this);
    func = function() {
      if (!header.is(':hover')) {
        menu('-3em');
      }
    };
    setTimeout(func, 500);
  });
  window.addEventListener('dragenter', function(e) {
    $('#dz-form').show();
  });
  window.addEventListener('dragleave', function(e) {
    if (e.target === $('div.header')[0]) {
      $('#dz-form').hide();
    }
  });
});

menu = function(top) {
  $('div.header').find('div.menu').finish().animate({
    top: top
  });
};

