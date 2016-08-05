<%@ tag trimDirectiveWhitespaces="true"%>
<%@ attribute name="target" required="true" %>
<svg class="hamburger" viewBox="0 0 320 320" xmlns="http://www.w3.org/2000/svg" data-target="${ target }">
  <g class="outer">
    <rect x="10" y="10" width="300" height="300" style="stroke-width:10"/>
  </g>
  <g class="inner">
    <rect x="80" y="110" width="160" height="25"/>
    <rect x="80" y="160" width="160" height="25"/>
    <rect x="80" y="210" width="160" height="25"/>
  </g>
</svg>