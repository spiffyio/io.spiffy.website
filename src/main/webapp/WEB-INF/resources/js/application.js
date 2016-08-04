var menu;

Dropzone.options.dzForm = {
  paramName: 'file',
  maxFilesize: 2,
  dictDefaultMessage: '<img style="width: 5em; height: auto;" src="https://cdn.spiffy.io/static/svg/icon.svg" />',
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
});

menu = function(top) {
  $('div.header').find('div.menu').finish().animate({
    top: top
  });
};

