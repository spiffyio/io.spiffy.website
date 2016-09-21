<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<jsp:include page="common/header.jsp" />

<div style="width: 100%; max-height: 20em; overflow: hidden; display: flex; align-items: center; text-align: center;">
  <img src="//cdn.spiffy.io/media/HnRPtk.jpg" style="width: 100vw;"/>
</div>
<div style="width: 100%; height: 3em; background: #F5F5F5; text-align: center;">
  <div style="width: 100%; max-width: 800px; display: inline-block; text-align: left;">
    <div style="position: relative; display: inline-block; margin-top: -5em; border: 0.25em solid #F5F5F5; float: left;">
      <img class="profile-icon" style="width: 160px; height: 160px;" src="<c:out value="${ profile.iconUrl }" />" />
      
      <c:if test="${ (not empty account) and (account.id eq profile.id) and false }">
      <form id="profile-dz" class="dropzone full" action="/upload" enctype="multipart/form-data" <s:csrf name="upload" /> style="width: auto; height: auto; position: absolute; top: 1em; left: 1em; bottom: 1em; right: 1em; border-color: #F5F5F5;">
        <div class="dz-message"></div>
        <img src="<s:resource file="svg/upload.svg" />" />
        <div class="message error">ahhhh</div> 
        <s:idempotent />
        <input type="hidden" name="form" value="icon" />
        <div class="dz-message"></div>
      </form>
      </c:if>
    </div>
    <div style="margin-left: 3em; display: inline-block; height: 3em; width: 10em; text-align: center; font-weight: bold; line-height: 3em; text-transform: uppercase; cursor: pointer;">
      <a href="#">STREAM</a>
    </div>
    <div style="display: inline-block; height: 3em; width: 10em; text-align: center; font-weight: bold; line-height: 3em; text-transform: uppercase; cursor: pointer;">
      <a href="/messages/<c:out value="${ profile.username }" />">MESSAGE</a>
    </div>
    <div style="display: inline-block; height: 3em; width: 10em; text-align: center; font-weight: bold; line-height: 3em; text-transform: uppercase; cursor: pointer;">
      <a href="#">FOLLOW</a>
    </div>
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

<jsp:include page="common/footer.jsp" />
