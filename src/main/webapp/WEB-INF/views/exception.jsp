<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<jsp:include page="common/header.jsp">
  <jsp:param name="style" value="exception" />
</jsp:include>

<h2><c:out value="${ code }" /></h2>
<p><c:out value="${ message }" /></p>

<c:if test="${ not empty stacktrace }">
<pre><c:out value="${ stacktrace }" /></pre>
</c:if>

</div>

<jsp:include page="common/footer.jsp">
  <jsp:param name="style" value="exception" />
</jsp:include>
