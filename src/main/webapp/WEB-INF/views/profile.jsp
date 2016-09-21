<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<jsp:include page="common/header.jsp" />

<div style="width: 100%; max-height: 20em; overflow: hidden; display: flex; align-items: center; text-align: center;">
  <img class="profile-banner" src="<c:out value="${ profile.bannerUrl }" />" style="width: 100vw;"/>
</div>
<div style="width: 100%; height: auto; display: inline-block; background: #F5F5F5; text-align: center;">
  <div class="profile-bar">
    <div class="profile-icon-container">
      <img class="profile-icon" src="<c:out value="${ profile.iconUrl }" />" />
    </div>
    <br class="md-hidden" />
    <div class="profile-name">
      <c:out value="${ profile.username  }" />
    </div>
    <c:if test="${ (empty account) or (account.id ne profile.id) }">
    <div class="profile-button">
      <a href="#">FOLLOW</a>
    </div>
    <div class="profile-button">
      <a href="/messages/<c:out value="${ profile.username }" />">MESSAGE</a>
    </div>
    </c:if>
    <c:if test="${ (not empty account) and (account.id eq profile.id) }">
    <div class="profile-button" id="edit-icon">
      CHANGE ICON
    </div>
    <div class="profile-button" id="edit-banner">
      CHANGE BANNER
    </div>
    </c:if>
  </div>
</div>

<div style="width: 100%; height: 3em;"></div>

<div class="grid">
  <jsp:include page="common/column.jsp">
    <jsp:param name="visibility" value="" />
    <jsp:param name="begin" value="0" />
    <jsp:param name="step" value="3" />
  </jsp:include>
  <jsp:include page="common/column.jsp">
    <jsp:param name="visibility" value="md-visible" />
    <jsp:param name="begin" value="1" />
    <jsp:param name="step" value="3" />
  </jsp:include>
  <jsp:include page="common/column.jsp">
    <jsp:param name="visibility" value="xl-visible" />
    <jsp:param name="begin" value="2" />
    <jsp:param name="step" value="3" />
  </jsp:include>
</div>

<form class="load-posts" action="/posts" <s:csrf name="posts" /> data-loading="header">
  <input type="hidden" name="after" value="<c:out value="${ after }" />"/>
  <c:if test="${ not empty user }"><input type="hidden" name="user" value="<c:out value="${ user }" />"/></c:if>
  <input type="hidden" name="quantity" />
</form>

<c:if test="${ (not empty account) and (account.id eq profile.id) }">
<form id="icon-dz" class="dropzone full" action="/upload" enctype="multipart/form-data" <s:csrf name="upload" /> style="display: none;">
  <div class="dz-message"></div>
  <img src="<s:resource file="svg/upload.svg" />" />
  <s:idempotent />
  <input type="hidden" name="form" value="icon" />
  <div class="dz-message"></div>
</form>
<form id="banner-dz" class="dropzone full" action="/upload" enctype="multipart/form-data" <s:csrf name="upload" /> style="display: none;">
  <div class="dz-message"></div>
  <img src="<s:resource file="svg/upload.svg" />" />
  <s:idempotent />
  <input type="hidden" name="form" value="banner" />
  <div class="dz-message"></div>
</form>
<div id="icon-modal" class="modal-overlay">
  <div class="modal">
    <div class="modal-header">
      <div class="content">
        <h2>profile icon</h2>
        <s:close />
      </div>
    </div>
    <div class="modal-body">
      <div class="icon-container" style="display: inline-block; text-align: center;">
        <div class="spiffy-croppie"></div>
        <div class="button primary" style="width: 5em;">save</div>
      </div>
    </div>
  </div>
</div>
</c:if>

<jsp:include page="common/footer.jsp" />
