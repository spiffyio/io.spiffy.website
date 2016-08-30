<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<jsp:include page="common/header.jsp" />

<div class="post">
<div class="media <c:if test="${ empty unprocessed and (post.content.type eq 'VIDEO') }">video</c:if>">
<c:if test="${ empty unprocessed }">
<c:if test="${ post.content.type eq 'VIDEO' }">
<video autoplay="true" loop="true" preload="none" poster="<c:out value="${ post.content.poster }" />">
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
</div>
<div style="height: 0.25em;"></div>
<div class="information">
  <h2><c:out value="${ post.title }" /></h2>
  <div class="description"><c:out value="${ post.description }" /></div>
</div>
</div>
<div class="actions">
  <button class="button primary" data-action="like"><img src="<s:resource file="svg/linea/arrows_up_double.svg" />" /></button>
  <button class="button primary" data-action="dislike"><img src="<s:resource file="svg/linea/arrows_down_double-34.svg" />" /></button>
  <button class="button primary" data-action="favorite"><img src="<s:resource file="svg/linea/basic_heart.svg" />" /></button>
  <button class="button success invisible"><img src="<s:resource file="svg/linea/arrows_check.svg" />" /></button>
  <div class="sm-hidden" style="height: 0.25em;"></div>
  <button class="button primary" data-action="share"><img src="<s:resource file="svg/linea/basic_share.svg" />" /></button>
  <button class="button primary" data-action="link"><img src="<s:resource file="svg/linea/basic_link.svg" />" /></button>
  <button class="button primary" data-action="download"><img src="<s:resource file="svg/linea/basic_download.svg" />" /></button>
  <button class="button danger" data-action="<c:if test="${ post.account.id eq account.id }">delete"><img src="<s:resource file="svg/linea/basic_trashcan.svg" />" /></c:if><c:if test="${ post.account.id ne account.id }">report"><img src="<s:resource file="svg/linea/basic_flag1.svg" />" /></c:if>
  </button>

  <form class="action" <s:csrf name="action" /> action="/stream/<c:out value="${ post.postId }" />/action" data-loading="header">
    <input type="hidden" name="action" />
    <div class="message"></div>
  </form>
</div>

<div class="centeryo">
  <div class="comments">
  <form class="comment" action="/stream/<c:out value="${ post.postId }" />/comment" <s:csrf name="comment" />>
    <div class="input text"><input type="text" name="comment" placeholder="comment" required/></div>
    <div class="input submit"><input class="button primary" type="submit" value="post" /></div>
    <s:idempotent />
    <div class="message"></div> 
  </form>
  
  <div class="initialyo">
  <c:forEach var="comment" items="${ comments }">
    <div class="comment">
      <span><c:out value="${ comment.comment }" /></span> - <c:out value="${ comment.account.username }" />
    </div>
  </c:forEach>
  </div>
  </div>
</div>

<jsp:include page="common/footer.jsp" />