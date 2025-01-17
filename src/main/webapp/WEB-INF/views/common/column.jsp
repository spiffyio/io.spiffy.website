<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<div class="col ${ param.visibility }">
  <c:forEach var="post" items="${ posts }" begin="${ param.begin }" step="${ param.step }">
  <div class="panel" data-post-id="<c:out value="${ post.postId }" />">
    <div class="centeryo">
    <div class="mediayo">
    <c:if test="${ post.content.type eq 'VIDEO' }">
    <div class="video paused" style="position: relative">
    <video loop="true" muted="true" poster="<c:out value="${ post.content.poster.medium }" />">
      <source src="<c:out value="${ post.content.mp4 }" />" type="video/mp4" />
      <source src="<c:out value="${ post.content.webm }" />" type="video/webm" />
      <c:if test="${ not empty post.content.gif }">
      <img data-src="<c:out value="${ not post.content.gif }" />" />
      </c:if>
    </video>
    </div>
    </c:if>
    <c:if test="${ post.content.type eq 'IMAGE' }">
    <img src="<c:out value="${ post.content.medium }" />" />
    </c:if>
    <c:if test="${ post.content.type eq 'AD' }">
    <ins class="adsbygoogle"
     style="display:inline-block;width:300px;height:250px"
     data-ad-client="ca-pub-1276323787739973"
     data-ad-slot="4167354043"></ins>
    <script>(adsbygoogle = window.adsbygoogle || []).push({});</script>
    </c:if>
    </div>
    </div>
    <div class="source <c:if test="${ post.content.type eq 'AD' }">advertisement</c:if>">
    <c:if test="${ post.content.type eq 'AD' }">
      <span>advertisement</span>
    </c:if>
    <c:if test="${ post.content.type ne 'AD' }">
      <div class="post-information">
        <div class="discuss">
          <a href="/stream/<c:out value="${ post.postId }" />"><img style="width: 2em; margin: 0.5em;" src="<s:resource file="svg/message.svg" />" /></a>
        </div>
      </div>
      <div class="account">
        <div class="thumbnail">
          <a href="/<c:out value="${ post.account.username }" />"><img src="<c:out value="${ post.account.iconUrl }" />" /></a>
        </div>
        <div class="username">
          <a href="/<c:out value="${ post.account.username }" />"><c:out value="${ post.account.username }" /></a>
          <br />
          <span class="time"><c:out value="${ post.duration }" /></span>
        </div>
      </div>
    </c:if>
    </div>
  </div>
  </c:forEach>
</div>