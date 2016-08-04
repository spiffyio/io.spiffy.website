<%@ page contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<link rel="stylesheet" type="text/css" href="/css/application.min.css">
<link rel="stylesheet" type="text/css" href="http://yolo.richio.co/css/main.css">
</head>
<body>
  <div class="header">
    <div class="logo"><img src="https://cdn.spiffy.io/static/svg/icon.svg" /></div>
    <div class="menu">
      <ul>
        <li>&nbsp;</li>
        <li><a href="">new post</a></li>
        <li><a href="">sign in</a></li>
        <li>&nbsp;</li>
      </ul>
    </div>
  </div>
  
  <form style="margin: 10% 25%; width: 50%; height: calc(50% - 5em); border: 5px black dashed;" action="/upload" class="dropzone" id="dz-form" enctype="multipart/form-data" hidden>
    <div class="dz-message"></div>
  </form>
  
  <script type="text/javascript" src="/js/jquery.min.js"></script>
  <script type="text/javascript" src="/js/dropzone.min.js"></script>
  <script type="text/javascript" src="/js/application.js"></script>
</body>
</html>
