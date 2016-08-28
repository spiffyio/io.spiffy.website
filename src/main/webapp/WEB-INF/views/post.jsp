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
  <c:if test="${ post.content.type eq 'VIDEO' }">
  <div class="video" style="position: relative">
  <video autoplay="true" loop="true" preload="none" poster="<c:out value="${ post.content.poster }" />">
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

<div class="actions">
  <button class="button primary" data-action="like"><img src="<s:resource file="svg/linea/arrows_up_double.svg" />" /></button>
  <button class="button primary" data-action="dislike"><img src="<s:resource file="svg/linea/arrows_down_double-34.svg" />" /></button>
  <button class="button primary" data-action="favorite"><img src="<s:resource file="svg/linea/basic_heart.svg" />" /></button>
  <button class="button primary" data-action="share"><img src="<s:resource file="svg/linea/basic_share.svg" />" /></button>
  <button class="button primary" data-action="link"><img src="<s:resource file="svg/linea/basic_link.svg" />" /></button>
  <button class="button primary" data-action="download"><img src="<s:resource file="svg/linea/basic_download.svg" />" /></button>
  <button class="button success invisible"><img src="<s:resource file="svg/linea/arrows_check.svg" />" /></button>
  <button class="button danger" data-action="<c:if test="${ post.accountId eq account.id }">delete"><img src="<s:resource file="svg/linea/basic_trashcan.svg" />" /></c:if><c:if test="${ post.accountId ne account.id }">report"><img src="<s:resource file="svg/linea/basic_flag1.svg" />" /></c:if>
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