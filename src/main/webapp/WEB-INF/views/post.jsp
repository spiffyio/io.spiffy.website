<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<jsp:include page="common/header.jsp">
  <jsp:param name="style" value="centered" />
</jsp:include>

<h2 style="width: 100%; text-align: center;">
  <c:out value="${ post.title }" />
</h2>

<div class="panel">
  <c:forEach var="type" items="${ post.types }">
    <c:set var="type" value="${ fn:toLowerCase(type) }" />
    <c:set var="video" value="${ video or (type eq 'mp4') or (type eq 'webm')}" />
  </c:forEach>
  <c:if test="${ video }">
  <c:set var="type" value="${ post.types[0] }" />
  <c:set var="type" value="${ fn:toLowerCase(type) }" />
  <video loop <c:if test="${ type eq 'png' }">poster="<c:out value="${ post.url }" /><c:out value="${ type }" />"</c:if>>
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
  </c:if>
</div>

<p style="width: 100%; text-align: center;">
  <c:out value="${ post.description }" />
</p>

</div>

<jsp:include page="common/footer.jsp" />