<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<jsp:include page="common/header.jsp" />

<style>
.pewpl {
  position: relative;
}
.pewpl:before {
  content: ' ';
  position: absolute;
  width: 0;
  height: 0;
  left: -1em;
  right: auto;
  top: 0;
  bottom: auto;
  border: 1em solid;
  border-color: #FFECC0 transparent transparent transparent;
}
.pewpr {
  position: relative;
}
.pewpr:before {
  content: ' ';
  position: absolute;
  width: 0;
  height: 0;
  right: -1em;
  left: auto;
  top: 0;
  bottom: auto;
  border: 1em solid;
  border-color: #C0EFFD transparent transparent transparent;
}
</style>

<div style="height: calc(100vh - 6em); width: 300px; float: left; background: white; overflow-y: scroll;">
  <div style="height: 3em; width: 100%; border-bottom: 1px #D3D3D3 solid; color: #3D3D3D; line-height: 3em; text-align: center; font-weight: bold;">
    messages
  </div>
  <div style="height: 3em; width: calc(100% - 1em); padding: 0.5em 0.5em;">
    <div style="float: left;">
      <img style="width: 3em; border-radius: 3em;" src="//cdn-beta.spiffy.io/media/FXmVHZ-Cg.jpg" />
    </div>
    <div style="height: 3em; width: calc(100% - 5em); float: left; margin-left: 1em; border-bottom: 1px #D3D3D3 solid;">
      <div>
        <div style="float: left; color: #5F5F5F;">John Rich</div>
        <div style="float: right; color: #D3D3D3;">Yesterday</div>
      </div>
      <div style="clear: both; color: #A1A1A1; overflow: hidden; white-space:nowrap; text-overflow: ellipsis;">
        wassup my brother from another, yo lol
      </div>
    </div>
  </div>
</div>

<div style="height: calc(100vh - 6em); width: calc(100% - 301px); float: right; border-left: 1px #5F5F5F solid; background: white;">
  <div style="height: 3em; width: 100%; border-bottom: 1px #D3D3D3 solid; color: #3D3D3D; line-height: 3em; text-align: center; font-weight: bold;">
    John Rich
  </div>
  <div style="height: calc(100% - 6.5em); width: 100%; overflow-y: scroll; margin-bottom: 0.5em;">
    <div style="clear: both; padding: 0.5em;">
      <div style="float: left;">
        <img style="width: 2em; border-radius: 2em;" src="//cdn-beta.spiffy.io/media/FXmVHZ-Cg.jpg" />
      </div>
      <div class="pewpl" style="max-width: calc(100% - 8em); padding: 0.5em; float: left; margin-left: 1em; background: #FFECC0; border-radius: 1em;">
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum congue nulla non mauris pellentesque laoreet. Sed ullamcorper nisl id libero accumsan imperdiet in quis dui. Nam ac molestie dui, sed sollicitudin metus. Etiam vel urna et ipsum posuere efficitur. Fusce ut cursus nunc. Pellentesque ut elit aliquam, convallis augue vitae, suscipit augue. Suspendisse potenti. Pellentesque consequat maximus ex, ut sagittis tortor congue vitae. Vestibulum viverra tempus mauris, in pulvinar libero finibus sed. Nulla vestibulum tortor in massa pretium, id porttitor augue elementum. Nullam neque velit, tristique eget nisi sit amet, consectetur volutpat mauris.
      </div>
    </div>
    <div style="clear: both; padding: 0.5em;">
      <div style="float: right;">
        <img style="width: 2em; border-radius: 2em;" src="//cdn-beta.spiffy.io/media/FXmVHZ-Cg.jpg" />
      </div>
      <div class="pewpr" style="max-width: calc(100% - 8em); padding: 0.5em; float: right; margin-right: 1em; background: #C0EFFD; border-radius: 1em;">
          Mauris risus dolor, lobortis sit amet ex vel, blandit auctor dolor. Nulla at molestie diam. Nam luctus ullamcorper lectus sit amet dapibus. Integer vulputate venenatis neque at gravida. Vivamus sollicitudin, est ut euismod eleifend, nibh lacus fringilla lectus, sit amet fermentum elit odio a neque. Curabitur quis ultricies ante. Pellentesque nec laoreet sapien. Donec tincidunt, nulla vel imperdiet eleifend, felis diam condimentum leo, consectetur efficitur tellus sapien ac dui. Pellentesque eu urna commodo, tempus metus vel, iaculis lorem. Aliquam sollicitudin pretium aliquam. Donec scelerisque ac magna nec posuere.
      </div>
    </div>
    <div style="clear: both; padding: 0.5em;">
      <div style="float: right;">
        <img style="width: 2em; border-radius: 2em;" src="//cdn-beta.spiffy.io/media/FXmVHZ-Cg.jpg" />
      </div>
      <div class="pewpr" style="max-width: calc(100% - 8em); padding: 0.5em; float: right; margin-right: 1em; background: #C0EFFD; border-radius: 1em;">
          Mauris risus dolor, lobortis sit amet ex vel, blandit auctor dolor. Nulla at molestie diam. Nam luctus ullamcorper lectus sit amet dapibus. Integer vulputate venenatis neque at gravida. Vivamus sollicitudin, est ut euismod eleifend, nibh lacus fringilla lectus, sit amet fermentum elit odio a neque. Curabitur quis ultricies ante. Pellentesque nec laoreet sapien. Donec tincidunt, nulla vel imperdiet eleifend, felis diam condimentum leo, consectetur efficitur tellus sapien ac dui. Pellentesque eu urna commodo, tempus metus vel, iaculis lorem. Aliquam sollicitudin pretium aliquam. Donec scelerisque ac magna nec posuere.
      </div>
    </div>
    <div style="clear: both; padding: 0.5em;">
      <div style="float: right;">
        <img style="width: 2em; border-radius: 2em;" src="//cdn-beta.spiffy.io/media/FXmVHZ-Cg.jpg" />
      </div>
      <div class="pewpr" style="max-width: calc(100% - 8em); padding: 0.5em; float: right; margin-right: 1em; background: #C0EFFD; border-radius: 1em;">
          Mauris risus dolor, lobortis sit amet ex vel, blandit auctor dolor. Nulla at molestie diam. Nam luctus ullamcorper lectus sit amet dapibus. Integer vulputate venenatis neque at gravida. Vivamus sollicitudin, est ut euismod eleifend, nibh lacus fringilla lectus, sit amet fermentum elit odio a neque. Curabitur quis ultricies ante. Pellentesque nec laoreet sapien. Donec tincidunt, nulla vel imperdiet eleifend, felis diam condimentum leo, consectetur efficitur tellus sapien ac dui. Pellentesque eu urna commodo, tempus metus vel, iaculis lorem. Aliquam sollicitudin pretium aliquam. Donec scelerisque ac magna nec posuere.
      </div>
    </div>
    <div style="clear: both; padding: 0.5em;">
      <div style="float: right;">
        <img style="width: 2em; border-radius: 2em;" src="//cdn-beta.spiffy.io/media/FXmVHZ-Cg.jpg" />
      </div>
      <div class="pewpr" style="max-width: calc(100% - 8em); padding: 0.5em; float: right; margin-right: 1em; background: #C0EFFD; border-radius: 1em;">
          Mauris risus dolor, lobortis sit amet ex vel, blandit auctor dolor. Nulla at molestie diam. Nam luctus ullamcorper lectus sit amet dapibus. Integer vulputate venenatis neque at gravida. Vivamus sollicitudin, est ut euismod eleifend, nibh lacus fringilla lectus, sit amet fermentum elit odio a neque. Curabitur quis ultricies ante. Pellentesque nec laoreet sapien. Donec tincidunt, nulla vel imperdiet eleifend, felis diam condimentum leo, consectetur efficitur tellus sapien ac dui. Pellentesque eu urna commodo, tempus metus vel, iaculis lorem. Aliquam sollicitudin pretium aliquam. Donec scelerisque ac magna nec posuere.
      </div>
    </div>
    <div style="clear: both; padding: 0.5em;">
      <div style="float: right;">
        <img style="width: 2em; border-radius: 2em;" src="//cdn-beta.spiffy.io/media/FXmVHZ-Cg.jpg" />
      </div>
      <div class="pewpr" style="max-width: calc(100% - 8em); padding: 0.5em; float: right; margin-right: 1em; background: #C0EFFD; border-radius: 1em;">
          Mauris risus dolor, lobortis sit amet ex vel, blandit auctor dolor. Nulla at molestie diam. Nam luctus ullamcorper lectus sit amet dapibus. Integer vulputate venenatis neque at gravida. Vivamus sollicitudin, est ut euismod eleifend, nibh lacus fringilla lectus, sit amet fermentum elit odio a neque. Curabitur quis ultricies ante. Pellentesque nec laoreet sapien. Donec tincidunt, nulla vel imperdiet eleifend, felis diam condimentum leo, consectetur efficitur tellus sapien ac dui. Pellentesque eu urna commodo, tempus metus vel, iaculis lorem. Aliquam sollicitudin pretium aliquam. Donec scelerisque ac magna nec posuere.
      </div>
    </div>
    <div style="clear: both; padding: 0.5em;">
      <div style="float: right;">
        <img style="width: 2em; border-radius: 2em;" src="//cdn-beta.spiffy.io/media/FXmVHZ-Cg.jpg" />
      </div>
      <div class="pewpr" style="max-width: calc(100% - 8em); padding: 0.5em; float: right; margin-right: 1em; background: #C0EFFD; border-radius: 1em;">
          Mauris risus dolor, lobortis sit amet ex vel, blandit auctor dolor. Nulla at molestie diam. Nam luctus ullamcorper lectus sit amet dapibus. Integer vulputate venenatis neque at gravida. Vivamus sollicitudin, est ut euismod eleifend, nibh lacus fringilla lectus, sit amet fermentum elit odio a neque. Curabitur quis ultricies ante. Pellentesque nec laoreet sapien. Donec tincidunt, nulla vel imperdiet eleifend, felis diam condimentum leo, consectetur efficitur tellus sapien ac dui. Pellentesque eu urna commodo, tempus metus vel, iaculis lorem. Aliquam sollicitudin pretium aliquam. Donec scelerisque ac magna nec posuere.
      </div>
    </div>
  </div>
  <div style="height: 3em; width: 100%; border-top: 1px #D3D3D3 solid; color: #3D3D3D; line-height: 3em;">
    <form>
      <input style="width: calc(100% - 5em); float: left;" type="text" placeholder="Type a message..." />
      <input style="width: 5em; float: right;" class="button primary" type="submit" value="send" />
    </form>
  </div>
</div>

<jsp:include page="common/footer.jsp" />
