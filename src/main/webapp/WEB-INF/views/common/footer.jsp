  <div class="footer-padding"></div>
</div>
  
<div class="footer">
  <a href="/llc">SPIFFY.io, LLC</a> &copy; 2016 &middot; <a href="/privacy">Privacy</a> &middot; <a href="/terms">Terms</a>
</div>

<div class="modal-overlay">
  <div class="modal">
    <div class="modal-header">
      <div class="content">
        <h2 data-modal-id="sign-in">sign in</h2>
        <h2 data-modal-id="sign-up">sign up</h2>
        <s:close />
      </div>
    </div>
    <div class="modal-body" data-modal-id="sign-in">
      <form class="sign-in" data-form="sign-in" <s:csrf name="login" />>
        <input type="email" placeholder="email" name="email" required autofocus />
        <input type="password" placeholder="password" name="password" required />
        <input class="button primary" type="submit" value="sign in" />
      </form>
    </div>
    <div class="modal-body" data-modal-id="sign-up">
      <form class="sign-up" data-form="sign-up">
        <input type="text" placeholder="username" required autofocus />
        <input type="email" placeholder="email" required />
        <input type="password" placeholder="password" required />
        <input class="button primary" type="submit" value="sign up" />
      </form>
    </div>
    <div class="modal-body" data-modal-id="new-post">
      <form style="margin: 10% 25%; width: 50%; height: 20em; border: 5px black dashed;" class="new-post dropzone" data-form="new-post" action="/upload" id="dz-form" enctype="multipart/form-data">
        <div class="dz-message"></div>
      </form>
    </div>
  </div>
</div>
  
<script type="text/javascript" src="<s:resource file="js/jquery.min.js" />"></script>
<script type="text/javascript" src="<s:resource file="js/jquery.validate.min.js" />"></script>
<script type="text/javascript" src="<s:resource file="js/dropzone.min.js" />"></script>
<script type="text/javascript" src="<s:resource file="js/application.min.js" />"></script>
</body>
</html>
