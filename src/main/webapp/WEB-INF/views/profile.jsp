<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<jsp:include page="common/header.jsp" />

<!-- <form id="dz-form" class="dropzone full" action="/upload" enctype="multipart/form-data" <s:csrf name="upload" />> -->
<!--   <div class="dz-message"></div> -->
<%--   <img src="<s:resource file="svg/upload.svg" />" /> --%>
<!--   <div class="message error">ahhhh</div>  -->
<%--   <s:idempotent /> --%>
<!-- </form> -->

<!-- <form class="submit full" action="/submit" data-name="submit" <s:csrf name="submit" /> data-disabled="true" hidden> -->
<!--   <div class="preview"></div> -->
<!--   <input type="hidden" name="media" /> -->
<%--   <s:idempotent /> --%>
<!--   <div class="message"></div>  -->
<!--   <div class="input center"><input class="button primary disabled" type="submit" value="submit" /></div> -->
<!-- </form> -->

<div style="width: 100%; height: calc(100vh - 6em); overflow: hidden; text-align: center; background: rgba(0, 0, 0, 0.6);">
  <div style="display: inline-block; position: relative; ">
    <div id="crop" style="position: absolute; box-shadow: 0 0 0 500em rgba(0, 0, 0, 0.6); border: 2px white solid; cursor: move; display: none;'">
      <div style="position: absolute; top: -0.5em; left: -0.5em; border-radius: 1em; width: 1em; height: 1em; background: white;"></div>
      <div style="position: absolute; top: -0.5em; right: -0.5em; border-radius: 1em; width: 1em; height: 1em; background: white;"></div>
      <div style="position: absolute; bottom: -0.5em; left: -0.5em; border-radius: 1em; width: 1em; height: 1em; background: white;"></div>
      <div style="position: absolute; bottom: -0.5em; right: -0.5em; border-radius: 1em; width: 1em; height: 1em; background: white;"></div>
    </div>
    <img style="max-width: 100%; max-height: calc(100vh - 6em);" src="//cdn-beta.spiffy.io/media/GzwxXB.jpg" />
  </div>
</div>

<jsp:include page="common/footer.jsp" />
