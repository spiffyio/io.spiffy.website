<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<c:set var="include_ads" value="true" />
<c:choose>
  <c:when test="${ param.style eq 'authenticate' }">
    <c:set var="include_fingerprint" value="true" />
    <c:set var="include_ads" value="false" />
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

<script data-template="panel-ad" type="text/x-handlebars-template">
<ins class="adsbygoogle"
  style="display:inline-block;width:300px;height:250px"
  data-ad-client="ca-pub-1276323787739973"
  data-ad-slot="4167354043"></ins>
</script>

<script data-template="panel-ad-source" type="text/x-handlebars-template">
<div class="source advertisement">
  <span>advertisement</span>
</div>
</script>

<script data-template="panel-source" type="text/x-handlebars-template">
<div class="source">
<div class="post-information">
  <div class="discuss">
    <a href="/stream/{{post.postId}}"><img style="width: 2em; margin: 0.5em;" src="<s:resource file="svg/linea/basic_message_txt.svg" />" /></a>
  </div>
  <div class="title">
    <a href="/stream/{{post.postId}}">{{post.title}}</a>
  </div>
</div>
<div class="account">
  <div class="thumbnail">
    <a href="/{{post.account.username}}"><img style="width: 3em;" src="//cdn-beta.spiffy.io/media/MkTmMs.png" /></a>
  </div>
  <div class="username">
    <a href="/{{post.account.username}}">{{post.account.username}}</a>
    <br />
    <span class="time">{{post.duration}}</span>
  </div>
</div>
</div>
</script>

<s:resource file="jquery" type="js" />
<s:resource file="parsley" type="js" />
<s:resource file="dropzone" type="js" />
<s:resource file="jquery.isinviewport" type="js" />
<s:resource file="handlebars" type="js" />
<c:if test="${ include_fingerprint }">
<s:resource file="fingerprint" type="js" />
</c:if>
<s:resource file="application" type="js" />
<c:if test="${ include_fingerprint }">
<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<script>
  (adsbygoogle = window.adsbygoogle || []).push({
    google_ad_client: "ca-pub-1276323787739973",
    enable_page_level_ads: true
  });
</script>
</c:if>
</body>
</html>
