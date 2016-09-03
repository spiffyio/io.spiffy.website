<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<jsp:include page="common/header.jsp">
  <jsp:param name="style" value="authenticate" />
</jsp:include>

  <c:if test="${ form eq 'login' }">
  <form class="login" <s:csrf name="login" /> data-return-uri="<c:out value="${ returnUri }" />" action="/login">
    <div class="input"><input type="email" placeholder="email" name="email" required autofocus /></div>
    <div class="input"><input type="password" placeholder="password" name="password" required /></div>
    <input type="hidden" name="fingerprint" />
    <div class="input"><div class="g-recaptcha" data-sitekey="6LeSviITAAAAAFC9aCd6CAmWFqLoIpzw174jMc-i"></div></div>
    <div class="message"></div> 
    <div class="input"><input class="button primary" type="submit" value="login" /></div>
    <br />
    <h3 style="text-align: center;">
      <a class="button primary" href="/forgot?returnUri=<c:out value="${ returnUri }" />">recover</a>
      <a class="button danger" href="/register?returnUri=<c:out value="${ returnUri }" />">register</a>
    </h3>
  </form>
  </c:if>
  
  <c:if test="${ form eq 'register' }">
  <form class="register" <s:csrf name="register" /> data-return-uri="<c:out value="${ returnUri }" />" action="/register">
    <div class="input"><input type="text" placeholder="username" name="username" required autofocus /></div>
    <div class="input"><input type="email" placeholder="email" name="email" required /></div>
    <div class="input"><input type="password" placeholder="password" name="password" required /></div>
    <div class="input"><input type="password" placeholder="retype password" name="confirm_password" data-parsley-equalto="[name='password']" required /></div>
    <input type="hidden" name="fingerprint" />
    <div class="input"><div class="g-recaptcha" data-sitekey="6LeSviITAAAAAFC9aCd6CAmWFqLoIpzw174jMc-i"></div></div>
    <div class="message"></div> 
    <div class="input"><input class="button danger" type="submit" value="register" /></div>
    <br />
    <h3 style="text-align: center;">
      <a class="button primary" href="/login?returnUri=<c:out value="${ returnUri }" />">login</a>
      <a class="button danger" href="/forgot?returnUri=<c:out value="${ returnUri }" />">recover</a>
    </h3>
  </form>
  </c:if>
  
  <c:if test="${ form eq 'forgot' }">
  <form class="forgot" <s:csrf name="forgot" /> data-return-uri="<c:out value="${ returnUri }" />" action="/forgot">
    <div class="input"><input type="email" placeholder="email" name="email" required autofocus /></div>
    <div class="input"><div class="g-recaptcha" data-sitekey="6LeSviITAAAAAFC9aCd6CAmWFqLoIpzw174jMc-i"></div></div>
    <div class="message"></div> 
    <div class="input"><input class="button primary" type="submit" value="continue" /></div>
    <br />
    <h3 style="text-align: center;">
      <a class="button primary" href="/login?returnUri=<c:out value="${ returnUri }" />">login</a>
      <a class="button danger" href="/register?returnUri=<c:out value="${ returnUri }" />">register</a>
    </h3>
  </form>
  </c:if>

</div>

<jsp:include page="common/footer.jsp">
  <jsp:param name="style" value="authenticate" />
</jsp:include>
