<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<jsp:include page="common/header.jsp">
  <jsp:param name="style" value="centered" />
</jsp:include>

<div class="sessions">
  <h2>Active Sessions</h2>
  <table style="width: 100%;">
<c:forEach var="session" items="${ sessions }" varStatus="loop">
  <tr>
    <td>
      <c:out value="${ session.os }" />
    </td>
    <td>
      <c:out value="${ session.browser }" />
    </td>
    <td style="text-align: right;">
    <c:if test="${ loop.index eq 0 }">
      (current)
    </c:if>
    <c:if test="${ loop.index ne 0 }">
      <s:duration date="${ session.lastActivity }" />
    </c:if>
    </td>
    <td style="text-align: right;">
    <c:if test="${ loop.index ne 0 }">
      <a href="#" class="button danger" data-session-id="<s:obfuscate id="${ session.id }" />">deactivate</a>
    </c:if>
    
  </tr>
</c:forEach>
  </table>
</div>

<iframe src="http://localhost:1280/stream/KsGrmq/embed" style="width:400px;height:300px;border:0px;"></iframe>

<form class="logout" <s:csrf name="logout" /> action="/logout" data-loading="header">
  <input type="hidden" name="session" />
</form>

</div>
  
<jsp:include page="common/footer.jsp" />
