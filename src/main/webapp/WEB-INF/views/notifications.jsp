<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<jsp:include page="common/header.jsp">
  <jsp:param name="style" value="centered" />
</jsp:include>

<div class="sessions">
  <h2>Notifications</h2>
  <table style="width: 100%;">
<c:forEach var="notification" items="${ notifications }" varStatus="loop">
  <tr>
    <td style="width: 3.5em;">
      <img style="height: 3em;" src="<c:out value="${ notification.iconUrl }" />" />
    </td>
    <td>
      <a style="text-align: left;" href="<c:out value="${ notification.actionUrl }" />"><c:out value="${ notification.message }" /></a>
    </td>
  </tr>
</c:forEach>
  </table>
</div>

</div>
  
<jsp:include page="common/footer.jsp" />
