<%@ page contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<jsp:include page="common/header.jsp">
  <jsp:param name="style" value="authenticate" />
</jsp:include>

  <c:if test="${ form eq 'login' }">
  <form class="login" <s:csrf name="login" /> data-return-uri="<c:out value="${ returnUri }" />">
    <input type="email" placeholder="email" name="email" required autofocus />
    <input type="password" placeholder="password" name="password" required />
    <div class="g-recaptcha"></div>
    <input class="button primary" type="submit" value="login" />
  </form>
  </c:if>
  
  <c:if test="${ form eq 'register' }">
  <form class="register" <s:csrf name="register" /> data-return-uri="<c:out value="${ returnUri }" />">
    <input type="text" placeholder="username" name="username" required autofocus />
    <input type="email" placeholder="email" name="email" required />
    <input type="password" placeholder="password" name="password" required />
    <div class="g-recaptcha"></div>
    <input class="button danger" type="submit" value="register" />
  </form>
  </c:if>

 </div>

<jsp:include page="common/footer.jsp">
  <jsp:param name="style" value="simple" />
</jsp:include>
