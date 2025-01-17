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
      <a class="button primary" style="width: 5em;" href="/forgot?returnUri=<c:out value="${ returnUri }" />">recover</a>
      <a class="button danger" style="width: 5em;" href="/register?returnUri=<c:out value="${ returnUri }" />">register</a>
    </h3>
    <br />
    <div style="width: 100%; text-align: center;">
      <div style="width: calc(50% - 2em); display: inline-block; border-bottom: #D3D3D3 dashed 0.5em;"></div>
      <h2 style="padding: 0 0.5em; display: inline-block; color: #3D3D3D;">or</h2>
      <div style="width: calc(50% - 2em); display: inline-block; border-bottom: #D3D3D3 dashed 0.5em;"></div>
    </div>
    <br style="clear: both;"/>
    <div style="text-align: center;">
      <a class="button social" href="/login?provider=facebook&returnUri=<c:out value="${ returnUri }" />"><img src="<s:resource file="svg/social-facebook.svg" />" /></a>
      <a class="button social" href="/login?provider=amazon&returnUri=<c:out value="${ returnUri }" />"><img src="<s:resource file="svg/social-amazon.svg" />" /></a>
      <a class="button social" href="/login?provider=google&returnUri=<c:out value="${ returnUri }" />"><img src="<s:resource file="svg/social-google.svg" />" /></a>
    </div>
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
    <br />
    <div style="width: 100%; text-align: center;">
      <div style="width: calc(50% - 2em); display: inline-block; border-bottom: #D3D3D3 dashed 0.5em;"></div>
      <h2 style="padding: 0 0.5em; display: inline-block; color: #3D3D3D;">or</h2>
      <div style="width: calc(50% - 2em); display: inline-block; border-bottom: #D3D3D3 dashed 0.5em;"></div>
    </div>
    <br style="clear: both;"/>
    <div style="text-align: center;">
      <a class="button social" href="/login?provider=facebook&returnUri=<c:out value="${ returnUri }" />"><img src="<s:resource file="svg/social-facebook.svg" />" /></a>
      <a class="button social" href="/login?provider=amazon&returnUri=<c:out value="${ returnUri }" />"><img src="<s:resource file="svg/social-amazon.svg" />" /></a>
      <a class="button social" href="/login?provider=google&returnUri=<c:out value="${ returnUri }" />"><img src="<s:resource file="svg/social-google.svg" />" /></a>
    </div>
  </form>
  </c:if>
  
  <c:if test="${ form eq 'create' }">
  <form class="create" <s:csrf name="create" /> data-return-uri="<c:out value="${ returnUri }" />" action="/create">
    <div class="input"><input type="text" placeholder="username" name="username" required autofocus /></div>
    <input type="hidden" name="token" value="<c:out value="${ token }" />" />
    <input type="hidden" name="fingerprint" />
    <div class="message"></div> 
    <div class="input"><input class="button danger" type="submit" value="register" /></div>
  </form>
  </c:if>
  
  <c:if test="${ form eq 'forgot' }">
  <form class="forgot" <s:csrf name="forgot" /> action="/forgot">
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
  
  <c:if test="${ form eq 'recover' }">
  <form class="recover" <s:csrf name="recover" /> data-return-uri="/" action="/recover">
    <div class="input"><input class="disabled" type="email" placeholder="email" name="email" value="<c:out value="${ email }" />" required data-disabled="permanent" disabled /></div>
    <div class="input"><input type="password" placeholder="new password" name="password" required autofocus /></div>
    <div class="input"><input type="password" placeholder="retype password" name="confirm_password" data-parsley-equalto="[name='password']" required /></div>
    <div class="input"><div class="g-recaptcha" data-sitekey="6LeSviITAAAAAFC9aCd6CAmWFqLoIpzw174jMc-i"></div></div>
    <div class="input"><input type="hidden" name="token" value="<c:out value="${ token }" />"/></div>
    <input type="hidden" name="fingerprint" />
    <div class="message"></div> 
    <div class="input"><input class="button primary" type="submit" value="continue" /></div>
    <br />
    <h3 style="text-align: center;">
      <a class="button primary" href="/login">login</a>
      <a class="button danger" href="/register">register</a>
    </h3>
  </form>
  </c:if>

</div>

<jsp:include page="common/footer.jsp">
  <jsp:param name="style" value="authenticate" />
</jsp:include>
