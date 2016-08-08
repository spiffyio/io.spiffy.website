<%@ page contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<jsp:include page="common/header.jsp" />

  <form class="login" <s:csrf name="login" />>
    <input type="email" placeholder="email" name="email" required autofocus />
    <input type="password" placeholder="password" name="password" required />
    <input class="button primary" type="submit" value="sign in" />
  </form>
  
  <form class="register" <s:csrf name="register" />>
    <input type="text" placeholder="username" name="username" required autofocus />
    <input type="email" placeholder="email" name="email" required />
    <input type="password" placeholder="password" name="password" required />
    <input class="button primary" type="submit" value="sign up" />
  </form>

<jsp:include page="common/footer.jsp" />
