<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<jsp:include page="common/header.jsp">
  <jsp:param name="style" value="upload" />
</jsp:include>

<form id="file-dz" class="dropzone full" action="/upload" enctype="multipart/form-data" <s:csrf name="upload" />>
  <div class="dz-message"></div>
  <img src="<s:resource file="svg/upload.svg" />" />
  <div class="message error">ahhhh</div> 
  <s:idempotent />
  <input type="hidden" name="form" value="file" />
</form>

<form class="submit full" action="/submit" data-name="submit" <s:csrf name="submit" /> data-disabled="true" hidden>
  <div class="preview">
    <div class="input"><input type="text" name="description" placeholder="description"/></div>
  </div>
  <input type="hidden" name="media" />
  <s:idempotent />
  <div class="message"></div> 
  <div class="input center"><input class="button primary disabled" type="submit" value="submit" /></div>
</form>

</div>
  
<jsp:include page="common/footer.jsp">
  <jsp:param name="style" value="upload" />
</jsp:include>
