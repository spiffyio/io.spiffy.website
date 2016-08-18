<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<div class="col ${ param.visibility }">
  <c:forEach var="post" items="${ posts }" begin="${ param.begin }" step="${ param.step }">
  <div class="panel">
    <img src="<c:out value="${ post.url }" />" />
    <div class="footer"><c:out value="${ post.title }" /></div>
  </div>
  </c:forEach>
</div>