<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<c:set var="media" value="" />
<c:if test="${ empty unprocessed and (post.content.type eq 'IMAGE') }"><c:set var="media" value="image" /></c:if>
<c:if test="${ empty unprocessed and (post.content.type eq 'VIDEO') }"><c:set var="media" value="video" /></c:if>
<c:if test="${ not empty unprocessed }"><c:set var="media" value="unprocessed" /></c:if>

<jsp:include page="common/header.jsp">
  <jsp:param name="style" value="embed" />
</jsp:include>

<div class="post">
  <div class="media <c:out value="${ media }" />">
    <c:if test="${ media eq 'video' }">
    <div class="video paused">
      <video muted="muted" loop="loop" preload="metadata" poster="<c:out value="${ post.content.poster.file }" />">
        <source src="<c:out value="${ post.content.mp4 }" />" type="video/mp4" />
        <source src="<c:out value="${ post.content.webm }" />" type="video/webm" />
        <c:if test="${ not empty post.content.gif }"><img data-src="<c:out value="${ not post.content.gif }" />" /></c:if>
      </video>
      <div class="controls">
        <div class="pause"><img src="<s:resource file="svg/pause.svg" />" /></div>
        <div class="volume"><img src="<s:resource file="svg/vid.svg" />" /></div>
      </div>
    </div>
    </c:if>
    <c:if test="${ media eq 'image' }"><img src="<c:out value="${ post.content.file }" />" /></c:if>
    <c:if test="${ media eq 'unprocessed' }"><div data-unprocessed="<c:out value="${ unprocessed }" />"></div></c:if>
    <div class="information">
      <div class="icon">
        <a href="https://spiffy.io/<c:out value="${ post.account.username }" />" target="_blank"><img src="<c:out value="${ post.account.iconUrl }" />" /></a>
      </div>
      <div class="description"><%--<c:out value="${ post.description }" /> --%></div>
      <div class="logo">
        <a href="https://spiffy.io" target="_blank"><img src="<s:resource file="svg/icon.svg" />" /></a>  
      </div>
    </div>
  </div>
</div>

<jsp:include page="common/footer.jsp">
  <jsp:param name="style" value="embed" />
</jsp:include>