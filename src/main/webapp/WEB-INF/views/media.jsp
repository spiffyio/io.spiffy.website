<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<jsp:include page="common/header.jsp" />


<div class="allmedia">
<form class="delete" <s:csrf name="delete" /> action="/<c:out value="${ account.username }" />/media/delete">
  <div class="message"></div> 
  <input type="submit" class="button danger disabled" style="width: 25em; max-width: 100%;" value="delete selected" />
</form>

<c:forEach var="content" items="${ media }">
  <img class="thismedia" src="<c:out value="${ content.thumbnail }" />" data-media-name="<c:out value="${ content.name }" />"/>
</c:forEach>

<form class="delete" <s:csrf name="delete" /> action="/<c:out value="${ account.username }" />/media/delete">
  <div class="message"></div> 
  <input type="submit" class="button danger disabled" style="width: 25em; max-width: 100%;" value="delete selected" />
</form>
</div>

<jsp:include page="common/footer.jsp" />