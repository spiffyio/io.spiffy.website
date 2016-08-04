var menu;

$(document).ready(function(e) {
  var uploader;
  $('div.header').mouseenter(function() {
    var func, header;
    header = $(this);
    func = function() {
      if (header.is(":hover")) {
        return menu('0');
      }
    };
    return setTimeout(func, 200);
  });
  $('div.header').mouseleave(function() {
    var func, header;
    header = $(this);
    func = function() {
      if (!header.is(":hover")) {
        return menu('-3em');
      }
    };
    return setTimeout(func, 500);
  });
  return uploader = new qq.FineUploaderBasic({});
});

menu = function(top) {
  return $('div.header').find('div.menu').finish().animate({
    top: top
  });
};

