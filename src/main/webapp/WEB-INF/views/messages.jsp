<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<jsp:include page="common/header.jsp" />

<script data-template="chat-thread" type="text/x-handlebars-template">
<div class="chat-thread" data-thread-id="{{ id }}" style="{{#if display}}display: {{display}}{{/if}}">
  <div><img class="icon" src="{{ icon }}" /></div>
  <div class="chat-thread-information">
   <div class="name">{{ id }}</div>
   <div class="time">{{ time }}</div>
   <div class="preview">{{ preview }}</div>
  </div>
</div>
</script>

<script data-template="message-group" type="text/x-handlebars-template">
<div class="message-group {{side}}" data-message-id="{{ id }}" style="{{#if display}}display: {{display}}{{/if}}">
  <div><img class="icon" src="{{icon}}" /></div>
  <div class="message">
    {{message}}
  </div>
</div>
</script>

<div class="chats">
  <div class="chats-header">
    messages <img src="<s:resource file="svg/write.svg" />" />
  </div>
  <div class="chats-body">
    <div class="chat-thread active" data-thread-id="maj">
      <div><img class="icon" src="//cdn-beta.spiffy.io/media/DlXRpf-Cg.jpg" /></div>
      <div class="chat-thread-information">
        <div class="name">cjsmile</div>
        <div class="time">Yesterday</div>
        <div class="preview">sup bro!</div>
      </div>
    </div>
    <div class="chat-thread active" data-thread-id="maj">
      <div><img class="icon" src="//cdn-beta.spiffy.io/media/DgHpJP-Cg.jpg" /></div>
      <div class="chat-thread-information">
        <div class="name">maj</div>
        <div class="time">5 days ago</div>
        <div class="preview">I'm going to pewp</div>
      </div>
    </div>
  </div>
</div>

<div class="chat">
  <div class="chat-header">
    cjsmile
  </div>
  <div class="chat-body">
    <div class="message-group right">
      <div><img class="icon" src="//cdn-beta.spiffy.io/media/FXmVHZ-Cg.jpg" /></div>
      <div class="message">
          Mauris risus dolor, lobortis sit amet ex vel, blandit auctor dolor. Nulla at molestie diam. Nam luctus ullamcorper lectus sit amet dapibus. Integer vulputate venenatis neque at gravida. Vivamus sollicitudin, est ut euismod eleifend, nibh lacus fringilla lectus, sit amet fermentum elit odio a neque. Curabitur quis ultricies ante. Pellentesque nec laoreet sapien. Donec tincidunt, nulla vel imperdiet eleifend, felis diam condimentum leo, consectetur efficitur tellus sapien ac dui. Pellentesque eu urna commodo, tempus metus vel, iaculis lorem. Aliquam sollicitudin pretium aliquam. Donec scelerisque ac magna nec posuere.
      </div>
    </div>
    <div class="event">
        4:20 pm
    </div>
    <div class="message-group right">
      <div><img class="icon" src="//cdn-beta.spiffy.io/media/FXmVHZ-Cg.jpg" /></div>
      <div class="message">
          Mauris risus dolor, lobortis sit amet ex vel, blandit auctor dolor. Nulla at molestie diam. Nam luctus ullamcorper lectus sit amet dapibus. Integer vulputate venenatis neque at gravida. Vivamus sollicitudin, est ut euismod eleifend, nibh lacus fringilla lectus, sit amet fermentum elit odio a neque. Curabitur quis ultricies ante. Pellentesque nec laoreet sapien. Donec tincidunt, nulla vel imperdiet eleifend, felis diam condimentum leo, consectetur efficitur tellus sapien ac dui. Pellentesque eu urna commodo, tempus metus vel, iaculis lorem. Aliquam sollicitudin pretium aliquam. Donec scelerisque ac magna nec posuere.
      </div>
    </div>
    <div class="message-group left">
      <div><img class="icon" src="//cdn-beta.spiffy.io/media/DlXRpf-Cg.jpg" /></div>
      <div class="message">
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum congue nulla non mauris pellentesque laoreet. Sed ullamcorper nisl id libero accumsan imperdiet in quis dui. Nam ac molestie dui, sed sollicitudin metus. Etiam vel urna et ipsum posuere efficitur. Fusce ut cursus nunc. Pellentesque ut elit aliquam, convallis augue vitae, suscipit augue. Suspendisse potenti. Pellentesque consequat maximus ex, ut sagittis tortor congue vitae. Vestibulum viverra tempus mauris, in pulvinar libero finibus sed. Nulla vestibulum tortor in massa pretium, id porttitor augue elementum. Nullam neque velit, tristique eget nisi sit amet, consectetur volutpat mauris.
      </div>
    </div>
    <div class="event">
        11:11 am
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
