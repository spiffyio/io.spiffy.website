<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<c:choose>
  <c:when test="${ param.style eq 'authenticate' }">
    <c:set var="include_fingerprint" value="true" />
  </c:when>
  <c:when test="${ param.style eq 'exception' }">
  </c:when>
  <c:otherwise>
  </c:otherwise>
</c:choose>

  <div class="footer-padding"></div>
</div>
  
<div class="footer">
  <a href="/llc">SPIFFY.io, LLC</a> &copy; 2016 &middot; <a href="/privacy">Privacy</a> &middot; <a href="/terms">Terms</a>
</div>
  
<script type="text/javascript" src="<s:resource file="js/jquery.min.js" />"></script>
<script type="text/javascript" src="<s:resource file="js/jquery.validate.min.js" />"></script>
<script type="text/javascript" src="<s:resource file="js/dropzone.min.js" />"></script>
<c:if test="${ include_fingerprint }">
<script type="text/javascript" src="<s:resource file="js/fingerprint.min.js" />"></script>
</c:if>
<script type="text/javascript" src="<s:resource file="js/application.min.js" />"></script>
</body>
</html>
