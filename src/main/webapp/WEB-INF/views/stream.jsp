<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<jsp:include page="common/header.jsp" />

<div class="grid">
  <jsp:include page="common/column.jsp">
    <jsp:param name="visibility" value="" />
    <jsp:param name="begin" value="0" />
    <jsp:param name="step" value="3" />
  </jsp:include>
  <jsp:include page="common/column.jsp">
    <jsp:param name="visibility" value="md-visible" />
    <jsp:param name="begin" value="1" />
    <jsp:param name="step" value="3" />
  </jsp:include>
  <jsp:include page="common/column.jsp">
    <jsp:param name="visibility" value="xl-visible" />
    <jsp:param name="begin" value="2" />
    <jsp:param name="step" value="3" />
  </jsp:include>
</div>

<form class="load-posts" action="/posts" <s:csrf name="posts" /> data-loading="header">
  <input type="hidden" name="after" value="<c:out value="${ after }" />"/>
  <c:if test="${ not empty user }"><input type="hidden" name="user" value="<c:out value="${ user }" />"/></c:if>
  <input type="hidden" name="followees" value="<c:out value="${ followees }" />"/>
  <input type="hidden" name="quantity" />
</form>

<jsp:include page="common/footer.jsp" />
