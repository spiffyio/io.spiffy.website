<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<c:choose>
  <c:when test="${ param.style eq 'authenticate' }">
  </c:when>
  <c:when test="${ param.style eq 'simple' }">
  </c:when>
  <c:otherwise>
    <c:set var="includeModal" value="true" />
  </c:otherwise>
</c:choose>

  <div class="footer-padding"></div>
</div>
  
<div class="footer">
  <a href="/llc">SPIFFY.io, LLC</a> &copy; 2016 &middot; <a href="/privacy">Privacy</a> &middot; <a href="/terms">Terms</a>
</div>

<c:if test="${ includeModal }">
<div class="modal-overlay">
  <div class="modal">
    <div class="modal-header">
      <div class="content">
        <h2 data-modal-id="new post">new post</h2>
        <s:close />
      </div>
    </div>
    <div class="modal-body" data-modal-id="new-post">
      <form style="margin: 10% 25%; width: 50%; height: 20em; border: 5px black dashed;" class="new-post dropzone" data-form="new-post" action="/upload" id="dz-form" enctype="multipart/form-data">
        <div class="dz-message"></div>
      </form>
    </div>
  </div>
</div>
</c:if>
  
<script type="text/javascript" src="<s:resource file="js/jquery.min.js" />"></script>
<script type="text/javascript" src="<s:resource file="js/jquery.validate.min.js" />"></script>
<script type="text/javascript" src="<s:resource file="js/dropzone.min.js" />"></script>
<script type="text/javascript" src="<s:resource file="js/fingerprint.min.js" />"></script>
<script type="text/javascript" src="<s:resource file="js/application.min.js" />"></script>
</body>
</html>
