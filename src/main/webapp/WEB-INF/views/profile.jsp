<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<jsp:include page="common/header.jsp">
  <jsp:param name="style" value="centered" />
</jsp:include>

<div style="position: relative; display: inline-block;">
  <img class="profile-icon" style="width: 160px; height: 160px;" src="<c:out value="${ profile.iconUrl }" />" />
  
  <c:if test="${ (not empty account) and (account.id eq profile.id) }">
  <form id="profile-dz" class="dropzone full" action="/upload" enctype="multipart/form-data" <s:csrf name="upload" /> style="width: auto; height: auto; position: absolute; top: 1em; left: 1em; bottom: 1em; right: 1em; border-color: #F5F5F5;">
    <div class="dz-message"></div>
    <img src="<s:resource file="svg/upload.svg" />" />
    <div class="message error">ahhhh</div> 
    <s:idempotent />
    <input type="hidden" name="form" value="profile" />
    <div class="dz-message"></div>
  </form>
  </c:if>
</div>

<div id="profile-modal" class="modal-overlay">
  <div class="modal">
    <div class="modal-header">
      <div class="content">
        <h2>profile icon</h2>
        <s:close />
      </div>
    </div>
    <div class="modal-body">
      <div class="profile-container" style="display: inline-block; text-align: center;">
        <div class="croppie"></div>
        <div class="button primary" style="width: 5em;">save</div>
      </div>
    </div>
  </div>
</div>

</div>

<jsp:include page="common/footer.jsp" />
