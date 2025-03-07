<%@ tag trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="type" required="false" %>
<svg class="icon" viewBox="0 0 32 32" xmlns="http://www.w3.org/2000/svg">
<c:choose>
  <c:when test="${ type eq 'loading' }">
  <style type="text/css">
    g.g {
      animation-name: wp;
      animation-duration: 5s;
      animation-iteration-count: infinite;
      transform-origin: 50% 50%;
    }
    @keyframes wp {
      0% { transform: rotate(0deg); }
      100% { transform: rotate(360deg); }
    }
  </style>
  <g class="g">
    <g fill="#FFC336">
      <circle fill-opacity="1.0" cx="16" cy="4" r="4"/>
      <circle fill-opacity="0.25" cx="28" cy="16" r="4"/>
      <circle fill-opacity="0.5" cx="16" cy="28" r="4"/>
      <circle fill-opacity="0.75" cx="4" cy="16" r="4"/>
    </g>
    <g fill="#39C6EE" transform="rotate(45 16 16)">
      <circle fill-opacity="0.125" cx="16" cy="4" r="4"/>
      <circle fill-opacity="0.375" cx="28" cy="16" r="4"/>
      <circle fill-opacity="0.625" cx="16" cy="28" r="4"/>
      <circle fill-opacity="0.875" cx="4" cy="16" r="4"/>
    </g>
  </g>
  </c:when>
  <c:otherwise>
  <g fill="#FFC336">
    <circle cx="16" cy="4" r="4"/>
    <circle cx="28" cy="16" r="4"/>
    <circle cx="16" cy="28" r="4"/>
    <circle cx="4" cy="16" r="4"/>
  </g>
  <g fill="#39C6EE" transform="rotate(45 16 16)">
    <circle cx="16" cy="4" r="4"/>
    <circle cx="28" cy="16" r="4"/>
    <circle cx="16" cy="28" r="4"/>
    <circle cx="4" cy="16" r="4"/>
  </g>
  </c:otherwise>
</c:choose>
  <circle fill="#000000" cx="16" cy="16" r="13"/>
  <circle fill="#C0EFFD" cx="16" cy="16" r="10"/>
  <g fill="#FFC336">
    <rect x="14" y="9" width="4" height="4"/>
    <rect x="14" y="15" width="4" height="8"/>
  </g>
</svg>