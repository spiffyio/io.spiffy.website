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

<jsp:include page="common/footer.jsp" />
