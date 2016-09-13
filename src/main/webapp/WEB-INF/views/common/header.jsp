<%@ page contentType="text/html; charset=UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

<c:choose>
  <c:when test="${ param.style eq 'authenticate' }">
    <c:set var="include_captcha" value="true" />
    <c:set var="include_centered" value="true" />
    <c:set var="include_logo" value="true" />
  </c:when>
  <c:when test="${ param.style eq 'centered' }">
    <c:set var="include_centered" value="true" />
    <c:set var="include_bar" value="true" />
  </c:when>
  <c:when test="${ param.style eq 'embed' }">
    <c:set var="include_centered" value="true" />
    <c:set var="include_embed_onload" value="true" />
  </c:when>
  <c:when test="${ param.style eq 'exception' }">
    <c:set var="include_centered" value="true" />
    <c:set var="include_logo" value="true" />
  </c:when>
  <c:when test="${ param.style eq 'upload' }">
    <c:set var="include_centered" value="true" />
    <c:set var="include_bar" value="true" />
  </c:when>
  <c:otherwise>
    <c:set var="include_bar" value="true" />
  </c:otherwise>
</c:choose>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="mobile-web-app-capable" content="yes">
<meta name="description" content="share content, and stuff.">
<meta name="author" content="SPIFFY.io, LLC">

<title>SPIFFY.io</title>

<link rel="apple-touch-icon" sizes="57x57" href="<s:resource file="favicon/apple-touch-icon-57x57.png" />">
<link rel="apple-touch-icon" sizes="60x60" href="<s:resource file="favicon/apple-touch-icon-60x60.png" />">
<link rel="apple-touch-icon" sizes="72x72" href="<s:resource file="favicon/apple-touch-icon-72x72.png" />">
<link rel="apple-touch-icon" sizes="76x76" href="<s:resource file="favicon/apple-touch-icon-76x76.png" />">
<link rel="apple-touch-icon" sizes="114x114" href="<s:resource file="favicon/apple-touch-icon-114x114.png" />">
<link rel="apple-touch-icon" sizes="120x120" href="<s:resource file="favicon/apple-touch-icon-120x120.png" />">
<link rel="apple-touch-icon" sizes="144x144" href="<s:resource file="favicon/apple-touch-icon-144x144.png" />">
<link rel="apple-touch-icon" sizes="152x152" href="<s:resource file="favicon/apple-touch-icon-152x152.png" />">
<link rel="apple-touch-icon" sizes="180x180" href="<s:resource file="favicon/apple-touch-icon-180x180.png" />">
<link rel="icon" type="image/png" href="<s:resource file="favicon/favicon-32x32.png" />" sizes="32x32">
<link rel="icon" type="image/png" href="<s:resource file="favicon/android-chrome-192x192.png" />" sizes="192x192">
<link rel="icon" type="image/png" href="<s:resource file="favicon/favicon-96x96.png" />" sizes="96x96">
<link rel="icon" type="image/png" href="<s:resource file="favicon/favicon-16x16.png" />" sizes="16x16">
<link rel="manifest" href="<s:resource file="favicon/manifest.json" />">
<link rel="mask-icon" href="<s:resource file="favicon/safari-pinned-tab.svg" />" color="#12b1de">
<meta name="apple-mobile-web-app-title" content="SPIFFY.io">
<meta name="application-name" content="SPIFFY.io">
<meta name="msapplication-TileColor" content="#c0effd">
<meta name="msapplication-TileImage" content="<s:resource file="favicon/mstile-144x144.png" />">
<meta name="theme-color" content="#c0effd">

<s:resource file="application" type="css" version="2" />
<c:if test="${ include_captcha }">
<script src='https://www.google.com/recaptcha/api.js' async defer></script>
</c:if>
</head>
<body <c:if test="${ include_embed_onload }"> onload="document.getElementById('embeded').src = window.location.href;" </c:if>>

<c:if test="${ include_bar }">
<div class="header">
  <div class="menu">
    <ul>
      <li class="logo"><a href="/"><img class="header-icon" src="<s:resource file="svg/icon.svg" />" /><img class="header-logo" src="<s:resource file="svg/logo.svg" />" /></a></li>
      <c:if test="${ empty account }">
      <li class="text"><a href="/register">register</a></li>
      <li class="text"><a href="/login">login</a></li>
      </c:if>
      <c:if test="${ not empty account }">
      <li>
        <a href="#menu" class="menu">
          <img class="hamburger" src="<s:resource file="svg/hamburger.svg" />" />
          <img class="close" src="<s:resource file="svg/close.svg" />" />
        </a>
        <ul class="sub-menu">
          <li><a href="/<c:out value="${ account.username }" />/stream">stream</a></li>
          <li><a href="/<c:out value="${ account.username }" />/images">images</a></li>
          <li><a href="/<c:out value="${ account.username }" />/videos">videos</a></li>
          <li><a href="/sessions">sessions</a></li>
          <li><a href="/logout">logout</a></li>
        </ul>
      </li>
      <li>
        <a href="/notifications" class="notifications <c:if test="${ context.notificationCount ne 0 }">notify</c:if>">
          <img class="bell" src="<s:resource file="svg/bell.svg" />" />
          <img class="bello" src="<s:resource file="svg/bello.svg" />" />
        </a>
      </li>
      <li><a href="/upload"><img src="<s:resource file="svg/upload.svg" />" /></a></li>
      </c:if>
    </ul>
  </div>
  <div class="header-loading"></div>
</div>
</c:if>

<div class="body">

<c:if test="${ include_bar }">
  <div class="header-padding"></div>
</c:if>

<c:if test="${ include_centered }">
  <div class="centered">
</c:if>

<c:if test="${ include_logo }">
    <s:logo />
</c:if>
