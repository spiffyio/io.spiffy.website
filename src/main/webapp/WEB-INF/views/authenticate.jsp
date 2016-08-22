<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<jsp:include page="common/header.jsp">
  <jsp:param name="style" value="authenticate" />
</jsp:include>

  <style type="text/css">
    form input[type] {
      padding: 5px;
      position: relative;
      width: 100%;
      height: 50px;
      border: none;
      border-radius: 0;
      font-size: 125%;
      line-height: 1.5em;
      z-index: 1;
      -webkit-transition: background 0.3s cubic-bezier(0.215, 0.61, 0.355, 1), border-color 0.3s cubic-bezier(0.215, 0.61, 0.355, 1);
      transition: background 0.3s cubic-bezier(0.215, 0.61, 0.355, 1), border-color 0.3s cubic-bezier(0.215, 0.61, 0.355, 1);
    }
    form input[type]:focus {
      background: #C0EFFD;
    }
    form label {
      pointer-events: none;
      position: absolute;
      opacity: 0;
      top: 0;
      -webkit-transform: translateY(15%);
              transform: translateY(15%);
      z-index: 2;
      font-weight: bold;
      font-size: 10px;
      text-transform: uppercase;
      padding-left: 6px;
      color: #13c3f8;
      -webkit-transition: opacity 0.3s cubic-bezier(0.215, 0.61, 0.355, 1), -webkit-transform 0.3s cubic-bezier(0.215, 0.61, 0.355, 1);
      transition: opacity 0.3s cubic-bezier(0.215, 0.61, 0.355, 1), -webkit-transform 0.3s cubic-bezier(0.215, 0.61, 0.355, 1);
      transition: transform 0.3s cubic-bezier(0.215, 0.61, 0.355, 1), opacity 0.3s cubic-bezier(0.215, 0.61, 0.355, 1);
      transition: transform 0.3s cubic-bezier(0.215, 0.61, 0.355, 1), opacity 0.3s cubic-bezier(0.215, 0.61, 0.355, 1), -webkit-transform 0.3s cubic-bezier(0.215, 0.61, 0.355, 1);
    }
    form > *:not(:first-child).input,
    form > *:not(:first-child) .input {
      border-top: none;
    }
    .input {
      margin: 0.5em 0;
      position: relative;
    }
    .bt-flabels__error-desc {
      position: absolute;
      top: 0;
      right: 6px;
      opacity: 0;
      font-weight: bold;
      color: #ffb60e;
      font-size: 10px;
      text-transform: uppercase;
      z-index: 3;
      pointer-events: none;
    }
    .bt-flabels__error input[type] {
      background: #FFECC0;
    }
    .bt-flabels__error input[type]:focus {
      background: #FFECC0;
    }
    .bt-flabels__error .bt-flabels__error-desc {
      opacity: 1;
      -webkit-transform: translateY(0);
              transform: translateY(0);
    }
    .bt-flabels--right {
      border-left: none;
    }
    .bt-flabel__float label {
      opacity: 1;
      -webkit-transform: translateY(0);
              transform: translateY(0);
    }
    .bt-flabel__float input[type] {
      padding-top: 9px;
    }
  </style>

  <c:if test="${ form eq 'login' }">
  <form class="login" <s:csrf name="login" /> data-return-uri="<c:out value="${ returnUri }" />" action="/login" data-parsley-validate data-parsley-errors-messages-disabled>
    <div class="input"><input type="email" placeholder="email" name="email" required autofocus /></div>
    <div class="input"><input type="password" placeholder="password" name="password" required /></div>
    <input type="hidden" name="fingerprint" />
    <div class="input"><div class="g-recaptcha"></div></div>
    <div class="input"><input class="button primary" type="submit" value="login" /></div>
  </form>
  </c:if>
  
  <c:if test="${ form eq 'register' }">
  <form class="register" <s:csrf name="register" /> data-return-uri="<c:out value="${ returnUri }" />" action="/register">
    <input type="text" placeholder="username" name="username" required autofocus />
    <input type="email" placeholder="email" name="email" required />
    <input type="password" placeholder="password" name="password" required />
    <input type="password" placeholder="retype password" name="confirm_password" required />
    <input type="hidden" name="fingerprint" />
    <div class="g-recaptcha"></div>
    <input class="button danger" type="submit" value="register" />
  </form>
  </c:if>

</div>

<jsp:include page="common/footer.jsp">
  <jsp:param name="style" value="authenticate" />
</jsp:include>
