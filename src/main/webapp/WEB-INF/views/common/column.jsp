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
    <video loop="true" muted="true" preload="none" poster="<c:out value="${ post.content.poster }" />">
      <source src="<c:out value="${ post.content.mp4 }" />" type="video/mp4" />
      <source src="<c:out value="${ post.content.webm }" />" type="video/webm" />
      <c:if test="${ not empty post.content.gif }">
      <img data-src="<c:out value="${ not post.content.gif }" />" />
      </c:if>
    </video>
    </div>
    </c:if>
    <c:if test="${ post.content.type eq 'IMAGE' }">
    <img src="<c:out value="${ post.content.thumbnail }" />" />
    </c:if>
    </div>
    </div>
    <div class="source"><a href="/stream/<c:out value="${ post.postId }" />"><c:out value="${ post.title }" /></a></div>
  </div>
  </c:forEach>
</div>