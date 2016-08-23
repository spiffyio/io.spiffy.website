<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<c:choose>
  <c:when test="${ param.style eq 'authenticate' }">
    <c:set var="include_fingerprint" value="true" />
  </c:when>
  <c:when test="${ param.style eq 'embed' }">
  </c:when>
  <c:when test="${ param.style eq 'exception' }">
  </c:when>
  <c:otherwise>
  </c:otherwise>
</c:choose>
<c:set var="include_fingerprint" value="true" />

  <div class="footer-padding"></div>
</div>
  
<div class="footer">
  <a href="/llc">SPIFFY.io, LLC</a> &copy; 2016 &middot; <a href="/privacy">Privacy</a> &middot; <a href="/terms">Terms</a>
</div>

<s:resource file="jquery" type="js" />
<s:resource file="parsley" type="js" />
<s:resource file="dropzone" type="js" />
<s:resource file="jquery.isinviewport" type="js" />
<c:if test="${ include_fingerprint }">
<s:resource file="fingerprint" type="js" />
</c:if>
<s:resource file="application" type="js" />
</body>
</html>
