<%@ page contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/spiffy.tld" prefix="s"%>

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

<link rel="stylesheet" type="text/css" href="<s:resource file="css/application.min.css" />">
</head>
<body>
<div class="header">
  <div class="logo"><a href="/"><img src="<s:resource file="svg/icon.svg" />" /></a></div>
  <div class="menu">
    <ul>
      <li>&nbsp;</li>
      <li><span data-modal="new-post">new post</span></li>
      <li><span data-modal="sign-in">sign in</span></li>
      <li>&nbsp;</li>
    </ul>
  </div>
</div>
  
<div class="body">
  <div class="header-padding"></div>