<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<jsp:include page="common/header.jsp" />

<script data-template="chat-thread" type="text/x-handlebars-template">
<div class="chat-thread" data-thread-id="{{ id }}" style="{{#if display}}display: {{display}};{{/if}}{{#if opacity}}opacity: {{opacity}};{{/if}}">
  <div><img class="icon" src="{{ icon }}" /></div>
  <div class="chat-thread-information">
   <div class="name">{{ id }}</div>
   <div class="time">{{ time }}</div>
   <div class="preview">{{ preview }}</div>
  </div>
</div>
</script>

<script data-template="message-group" type="text/x-handlebars-template">
<div class="message-group {{side}}" data-message-id="{{ id }}" style="{{#if display}}display: {{display}};{{/if}}{{#if opacity}}opacity: {{opacity}};{{/if}}">
  <div><img class="icon" src="{{icon}}" /></div>
  <div class="message">
    {{message}}
  </div>
</div>
</script>

<div class="chats">
  <div class="chats-header">
    <div class="title">messages</div>
    <div class="icon"><img class="new-message clickable" src="<s:resource file="svg/write.svg" />" /></div>
  </div>
  <div class="chats-body">
    <c:forEach var="thread" items="${ threads }">
    <div class="chat-thread <c:if test="${ thread.id eq activeThread }">active</c:if>" data-thread-id="<c:out value="${ thread.id }" />">
      <div><img class="icon" src="<c:out value="${ thread.icon }" />" /></div>
      <div class="chat-thread-information">
        <div class="name"><c:out value="${ thread.id }" /></div>
        <div class="time"><c:out value="${ thread.time }" /></div>
        <div class="preview"><c:out value="${ thread.preview }" /></div>
      </div>
    </div>
    </c:forEach>
    <div class="chat-thread" data-thread-id="new" style="display: none;"></div>
  </div>
</div>

<div class="chat">
  <div class="chat-header">
    <form class="new" action="/messages/new" <s:csrf name="new" /> <c:if test="${ 'new' ne activeThread }">style="display: none;"</c:if>>
      <div class="input text"><input type="text" name="participants" placeholder="To: name(s)" required/></div>
    </form>
    <div class="title" <c:if test="${ 'new' eq activeThread }">style="display: none;"</c:if>><c:out value="${ activeThread }" /></div>
  </div>
  <div class="chat-body">
    <c:forEach var="message" items="${ messages }">
    <div class="message-group <c:out value="${ message.side }" />" data-message-id="<c:out value="${ message.id }" />">
      <div><img class="icon" src="<c:out value="${ message.icon }" />" /></div>
      <div class="message">
        <c:out value="${ message.message }" />
      </div>
    </div>
    </c:forEach>
  </div>
  <div style="height: 3em; width: 100%; border-top: 1px #D3D3D3 solid; color: #3D3D3D; line-height: 3em;">
    <form class="message">
      <input style="width: calc(100% - 5em); float: left;" type="text" name="message" placeholder="Type a message..." />
      <input style="width: 5em; float: right;" class="button primary" type="submit" value="send" />
    </form>
  </div>
</div>

<jsp:include page="common/footer.jsp" />
