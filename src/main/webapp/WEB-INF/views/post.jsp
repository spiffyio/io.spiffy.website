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
  <div class="video paused">
  <video data-autoplay="true" loop <c:if test="${ type eq 'png' }">poster="<c:out value="${ post.url }" /><c:out value="${ type }" />"</c:if>>
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
  </div>
  </c:if>
  <c:if test="${ not video }">
  <c:set var="type" value="${ fn:toLowerCase(post.types[0]) }" />
  <img src="<c:out value="${ post.url }" /><c:out value="${ type }" />" />
  </c:if>
</div>

<div class="actions">
  <button class="button primary">+</button>
  <button class="button primary">-</button>
  <button class="button primary"><3</button>
  <button class="button primary">share</button>
  <button class="button primary">embed</button>
  <button class="button primary">download</button>
  <button class="button danger">
    <c:if test="${ post.accountId eq account.id }">delete</c:if>
    <c:if test="${ post.accountId ne account.id }">report</c:if>
  </button>
  
  <form class="action" <s:csrf name="action" /> action="/stream/<c:out value="${ post.postId }" />/action" data-loading="header">
    <input type="hidden" name="action" />
    <div class="message"></div>
  </form>
</div>



<p style="width: 100%; text-align: center;">
  <c:out value="${ post.description }" />
</p>

<form class="comment full" action="/stream/<c:out value="${ post.postId }" />/comment" <s:csrf name="comment" />>
  <div class="input"><input type="text" name="comment" placeholder="comment" required/></div>
  <s:idempotent />
  <div class="message"></div> 
  <div class="input center"><input class="button primary" type="submit" value="comment" /></div>
</form>

<c:forEach var="comment" items="${ comments }">
  <div class="comment">
    <span><c:out value="${ comment.comment }" /></span>
  </div>
</c:forEach>

</div>

<jsp:include page="common/footer.jsp" />