<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE>
<html>
  <head>
	<title>登陆页面</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="style/style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="js/jquery.cookie.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
		$("#username").focus();
		if ($.cookie("rememberUsername") != null && $.cookie("rememberUsername") == "true") {
			$("#rememberUsername").prop("checked", true);
			$("#username").val($.cookie("username"));
		}
		
		if ($.cookie("rememberPasswd") != null && $.cookie("rememberPasswd") == "true") {
			$("#rememberPasswd").prop("checked", true);
			$("#password").val($.cookie("password"));
		}
	});
	
	function login() {
		var username = $("#username").val();
		var password = $("#password").val();
		if ($("#rememberUsername").prop("checked")) {
			$.cookie("rememberUsername", "true", {expires: 7, path:"/"});
			$.cookie("username", username, {expires: 7, path:"/"});
			
			if ($("#rememberPasswd").prop("checked")) {
				$.cookie("rememberPasswd", "true", {expires: 7, path:"/"});
				$.cookie("password", password, {expires: 7, path:"/"});
			} else {
				$.cookie("rememberPasswd", "false", {expires: -1, path:"/"});
				$.cookie("password", password, {expires: -1, path:"/"});
			}
		} else {
			$.cookie("rememberUsername", "false", {expires: -1, path:"/"});
			$.cookie("username", "", {expires: -1, path:"/"});
			$.cookie("rememberPasswd", "false", {expires: -1, path:"/"});
			$.cookie("password", "", {expires: -1, path:"/"});
		}
		
		$.ajax({
			type:"post",
			url:"loginController.do?doLogin",
			timeout: 3000,
			dataType:"text",
			async:false,
			data:{username:username, password:password},
			success:function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					self.location='currentAlarm.do?list';
				} else {
					$("#errormessage").html(d.des);
				}
			}
		});
	}
	</script>
  </head>
  
  <body>
      <div class="Login">
        <div class="LoginBlock">
          <div class="LoginBlock1">
            <img src="images/mordo.png"/>
            <img src="images/yanglao.png"/>
          </div>
          <div class="LoginBlock2">
            <div>
              <p class="username">
                <span>用</span><span>户</span><span>名</span>：<input id="username" name="username" onkeydown="if(event.keyCode==13){$('#password').focus();}">
              </p>
              <p class="username">
                <span>密</span><span>　</span><span>码</span>：<input id="password" name="password" type="password"" onkeydown="if(event.keyCode==13){login();}">
              </p>
              <p id="errormessage" class="loginerro"></p>
              <div class="usercheck" style="margin-left:66px;">
                <input type="checkbox" id="rememberUsername">
                <label for="rememberUsername"></label>
                <span>&nbsp;记住用户名</span>
              </div>
              <div class="usercheck" style="margin-left:23px;">
                <input type="checkbox" id="rememberPasswd">
                <label for="rememberPasswd"></label>
                <span>&nbsp;记住密码</span>
              </div>
            </div>
            <div>
              <input class="loginbottom" type="button" value="登入" onclick="login();"/>
            </div>
          </div>
        </div>
      </div>
  </body>
</html>
