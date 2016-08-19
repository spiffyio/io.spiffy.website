<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<jsp:include page="common/header.jsp">
  <jsp:param name="style" value="centered" />
</jsp:include>


<c:forEach var="session" items="${ sessions }">
  <c:out value="${ session.authenticatedIPAddress }" />
  <br />
  <c:out value="${ session.lastIPAddress }" />
  
  <br />
  <br />
</c:forEach>

</div>
  
<jsp:include page="common/footer.jsp" />
