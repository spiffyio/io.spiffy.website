<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<jsp:include page="common/header.jsp">
  <jsp:param name="style" value="centered" />
</jsp:include>

<h2 style="width: 100%; text-align: center;">
  <c:out value="${ post.title }" />
</h2>

<div class="panel">
  <img src="<c:out value="${ post.url }" />" />
</div>

<p style="width: 100%; text-align: center;">
  <c:out value="${ post.description }" />
</p>

</div>

<jsp:include page="common/footer.jsp" />