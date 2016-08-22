<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<jsp:include page="common/header.jsp">
  <jsp:param name="style" value="upload" />
</jsp:include>

<form style="width: 100%; height: 20em; border: 5px black dashed;" class="new-post dropzone" data-form="new-post" action="/upload" id="dz-form" enctype="multipart/form-data" <s:csrf name="upload" />>
  <div class="dz-message"></div>
  <s:idempotent />
</form>

<form class="submit full" action="/submit" data-name="submit" <s:csrf name="submit" /> hidden>
  <div class="input center"><input type="text" name="title" placeholder="title" required/></div>
  <img src="" />
  <input type="hidden" name="media" />
  <div class="input"><input type="text" name="description" placeholder="description"/></div>
  <s:idempotent />
  <div class="message"></div> 
  <div class="input center"><input class="button primary" type="submit" value="submit" /></div>
</form>

</div>
  
<jsp:include page="common/footer.jsp" />
