<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<div class="col ${ param.visibility }">
  <c:forEach var="post" items="${ posts }" begin="${ param.begin }" step="${ param.step }">
  <c:set var="video" value="false" />
  <c:forEach var="type" items="${ post.types }">
    <c:set var="type" value="${ fn:toLowerCase(type) }" />
    <c:set var="video" value="${ video or (type eq 'mp4') or (type eq 'webm')}" />
  </c:forEach>
  <div class="panel <c:if test="${ video }">gif paused</c:if>" data-post="<c:out value="${ post.postId }" />">
    <c:if test="${ video }">
    <c:set var="type" value="${ post.types[0] }" />
    <c:set var="type" value="${ fn:toLowerCase(type) }" />
    <video loop muted preload="none" <c:if test="${ type eq 'png' }">poster="<c:out value="${ post.url }" /><c:out value="${ type }" />"</c:if>>
    <c:forEach var="type" items="${ post.types }">
      <c:set var="type" value="${ fn:toLowerCase(type) }" />
      <c:if test="${ (type eq 'mp4') or (type eq 'webm')}">
      <source src="<c:out value="${ post.url }" /><c:out value="${ type }" />" type="video/<c:out value="${ type }" />" />
      </c:if>
      <c:if test="${ type eq 'gif' }">
      <img data-src="<c:out value="${ post.url }" /><c:out value="${ type }" />" />
      </c:if>
    </c:forEach>
    </video>
    </c:if>
    <c:if test="${ not video }">
    <c:set var="type" value="${ fn:toLowerCase(post.types[0]) }" />
    <img src="<c:out value="${ post.url }" /><c:out value="${ type }" />" />
    <div class="footer"><c:out value="${ post.title }" /></div>
    </c:if>
  </div>
  </c:forEach>
</div>