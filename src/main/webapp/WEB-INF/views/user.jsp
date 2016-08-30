<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<jsp:include page="common/header.jsp">
  <jsp:param name="style" value="centered" />
</jsp:include>


<h2><c:out value="${ user }" /></h2>
<h2><a href="/<c:out value="${ user }" />/stream">stream</a></h2>
<c:if test="${ myaccount }">
  <h2><a href="/<c:out value="${ user }" />/images">images</a></h2>
  <h2><a href="/<c:out value="${ user }" />/videos">videos</a></h2>
  <h2><a href="/sessions">sessions</a></h2>
</c:if>

</div>

<jsp:include page="common/footer.jsp" />