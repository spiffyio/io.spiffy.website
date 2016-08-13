<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="col ${ param.visibility }">
  <c:forEach var="url" items="${ urls }" begin="${ param.begin }" step="${ param.step }">
    <div class="panel">
      <img src="${ url }" />
      <div class="source">
        <div class="thumbnail"><img src="//xkcd.com/s/919f27.ico" /></div>
        <div class="name"><div class="content">XKCD</div></div>
        <div class="time"><!-- <div class="content"></div> --></div>
      </div>
      <div class="right"><s:options /></div>
      <div class="right"><s:heart /></div>
      <div class="clearfix"></div>
    </div>
  </c:forEach>
</div>