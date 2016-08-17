<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<div class="col ${ param.visibility }">
  <c:forEach var="post" items="${ posts }" begin="${ param.begin }" step="${ param.step }">
    <div class="panel">
      <img src="<c:out value="${ post.url }" />" />
      <div class="source">
        <div class="thumbnail"></div>
        <div class="title"><div class="content"><c:out value="${ post.title }" /></div></div>
        <div class="name"><div class="content"><c:out value="${ post.username }" /></div></div>
      </div>
      <div class="right"><s:options /></div>
      <div class="right"><s:heart /></div>
      <div class="clearfix"></div>
    </div>
  </c:forEach>
</div>