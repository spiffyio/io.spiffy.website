<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<jsp:include page="common/header.jsp">
  <jsp:param name="style" value="embed" />
</jsp:include>

<div class="post">

<div class="media <c:if test="${ empty unprocessed and (post.content.type eq 'VIDEO') }">video</c:if>">
<c:if test="${ empty unprocessed }">
<c:if test="${ post.content.type eq 'VIDEO' }">
<video autoplay="true" loop="true" preload="none" poster="<c:out value="${ post.content.poster.file }" />">
  <source src="<c:out value="${ post.content.mp4 }" />" type="video/mp4" />
  <source src="<c:out value="${ post.content.webm }" />" type="video/webm" />
  <c:if test="${ not empty post.content.gif }">
  <img data-src="<c:out value="${ not post.content.gif }" />" />
  </c:if>
</video>
</c:if>
<c:if test="${ post.content.type eq 'IMAGE' }">
<img src="<c:out value="${ post.content.file }" />" />
</c:if>
</c:if>
<c:if test="${ not empty unprocessed }">
  <div data-unprocessed="<c:out value="${ unprocessed }" />">
  </div>
</c:if>
<div class="information">
  <div class="description"><c:out value="${ post.description }" /></div>
</div>
</div>

</div>

<jsp:include page="common/footer.jsp">
  <jsp:param name="style" value="embed" />
</jsp:include>