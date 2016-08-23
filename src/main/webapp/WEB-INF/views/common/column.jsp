<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<div class="col ${ param.visibility }">
  <c:forEach var="post" items="${ posts }" begin="${ param.begin }" step="${ param.step }">
  <div class="panel" data-post="<c:out value="${ post.postId }" />">
    <c:set var="video" value="false" />
    <c:forEach var="type" items="${ post.types }">
      <c:set var="type" value="${ fn:toLowerCase(type) }" />
      <c:set var="video" value="${ video or (type eq 'mp4') or (type eq 'webm')}" />
    </c:forEach>
    <c:if test="${ video }">
    <video loop muted>
    <c:forEach var="type" items="${ post.types }">
      <c:set var="type" value="${ fn:toLowerCase(type) }" />
      <c:if test="${ (type eq 'mp4') or (type eq 'webm')}">
      <source src="<c:out value="${ post.url }" /><c:out value="${ type }" />" type="video/<c:out value="${ type }" />" />
      </c:if>
      <c:if test="${ (type ne 'mp4') and (type ne 'webm')}">
      <img src="<c:out value="${ post.url }" /><c:out value="${ type }" />" />
      </c:if>
    </c:forEach>
    </video>
    </c:if>
    <c:if test="${ not video }">
      <c:set var="type" value="${ fn:toLowerCase(post.types[0]) }" />
    <img src="<c:out value="${ post.url }" /><c:out value="${ type }" />" />
    </c:if>
    <div class="footer"><c:out value="${ post.title }" /></div>
  </div>
  </c:forEach>
</div>