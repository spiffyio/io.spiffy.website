<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<jsp:include page="common/header.jsp">
  <jsp:param name="style" value="centered" />
</jsp:include>

<c:forEach var="content" items="${ media }">
  <img style="width: 150px; height: 150px; display: inline-block; margin: 0.5em;" src="<c:out value="${ content.thumbnail }" />" />
</c:forEach>

</div>

<jsp:include page="common/footer.jsp" />