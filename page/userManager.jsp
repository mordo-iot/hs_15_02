<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String username = (String)session.getAttribute("username");
%>

<!DOCTYPE>
<html>
  <head>
    <title>用户信息</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="style/style.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
	  .editUser-content .firstButton{margin-left:30px;margin-right:10px;}
	  .resetPassword-content .firstButton{margin-left:40px;margin-right:10px;}
	  .deleteUser-content .firstButton{margin-left:40px;margin-right:10px;}
    </style>
	<script type="text/javascript" src="js/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="js/tipswindown.js"></script>
  </head>

  <script type="text/javascript">
	function doAddUser() {
		showTipsWindown('新增用户', 'addUser', 340, 225);
	}
	
	function doEditUser(userid4edit, username4edit, permission4edit) {
		document.getElementById("userid2edit").value = userid4edit;
		document.getElementById("username2edit").setAttribute("value", username4edit);
		if (permission4edit == "管理员") {
			document.getElementById("option2").removeAttribute("selected");
			document.getElementById("option1").setAttribute("selected", "selected");
		} else {
			document.getElementById("option1").removeAttribute("selected");
			document.getElementById("option2").setAttribute("selected", "selected");
		}
		
		showTipsWindown('编辑用户', 'editUser', 340, 175);
	}
	
	function delUser(userid) {
		document.getElementById("userid2del").value = userid;
		showTipsWindown('删除用户','deleteUser',290,115);
	}
	
	function resetPassword(userid) {
		document.getElementById("userid2reset").value = userid;
		showTipsWindown('重置密码','resetPassword',290,125);
	}
	
	function doResetPwd() {
		var userId = $("#userid2reset").val();
		$.ajax({
			type:"post",
			url:"user.do?resetpasswd",
			timeout: 3000,
			dataType:"text",
			async:false,
			data:{userId:userId},
			success:function(data) {
				alert(data);
			}
		});
	}
	
	function showTipsWindown(title, id, width, height) {
		tipsWindown(title, "id:" + id, width, height, "true", "", "true", id);
	}
	
	function closeIt() {
		parent.closeWindown();
	}
	
	function gotopage(pagenumber) {
		$("#pageNumber").val(pagenumber);
		getCurrentPage();
	}
	
	function gotoselectpage() {
		gotopage($("#tarpage").val());
	}
	
	$(document).ready(function() {
		getCurrentPage();
		getCurrentAlarmInfo();
		setInterval(getCurrentAlarmInfo, 5000);  //每隔一段时间获取一次报警数据，看看有没有新报警
	});
	
	var alarmHash = "";
	
	function getCurrentAlarmInfo() {
		$.ajax({
			type:"post",
			url:"currentAlarm.do?show",
			timeout: 3000,
			dataType:"text",
			data:{},
			success:function(data) {
				var d = $.parseJSON(data);
				if (alarmHash == "") {
					alarmHash = d.hashCode;
				} else {
					if (alarmHash != d.hashCode) {
						$("#alarmpoint").css('display','block');
					}
				}
			}
		});
	}
	
	function checkAddInfo() {
		var username = $("#windown-box").find('input[id="username2add"]').val();
		if (username == null || username == "") {
			alert("请输入用户名");
			return false;
		}
		
		var password = $("#windown-box").find('input[id="password2add"]').val();
		if (password == null || password == "") {
			alert("请输入密码");
			return false;
		}
		var permission = $("#windown-box").find('select[id="permission2add"]').val();
		addUserInfo("", username, password, permission);
	}
	
	function checkEditInfo() {
		var username = $("#windown-box").find('input[id="username2edit"]').val();
		if (username == null || username == "") {
			alert("请输入用户名");
			return false;
		}
		var userid = $("#windown-box").find('input[id="userid2edit"]').val();
		var password = "";
		var permission = $("#windown-box").find('select[id="permission2edit"]').val();
		addUserInfo(userid, username, password, permission);
	}
	
	function addUserInfo(userid, username, password, permission) {
		$.ajax({
			type:"post",
			url:"user.do?adduser",
			timeout: 3000,
			dataType:"text",
			async:false,
			data:{userid:userid, username:username, password:password, permission:permission},
			success:function(data) {
				alert(data);
				if (data == "操作成功") {
					getCurrentPage();
					closeIt();
				} else {
					
				}
			}
		});
	}
	
	function delUserInfo() {
		var userid = $("#windown-box").find('input[id="userid2del"]').val();
		$.ajax({
			type:"post",
			url:"user.do?del",
			timeout: 3000,
			dataType:"text",
			async:false,
			data:{userId:userid},
			success:function(data) {
				alert(data);
				if (data == "删除成功") {
					getCurrentPage();
					closeIt();
				} else {
					
				}
			}
		});
	}
	
	function getCurrentPage() {
		var pageNumber = $("#pageNumber").val();
		$.ajax({
			type:"post",
			url:"user.do?page",
			timeout: 3000,
			dataType:"text",
			async:false,
			data:{pageNumber:pageNumber},
			success:function(data) {
				var d = $.parseJSON(data);
				$("#totalpositionitems").html(d.totalitem);
				$("#totaldevpages").html(d.totalpage);
				$("#pagein1").html(d.paginhtml);
				var html2append = "<li style=\"background-color:#2e94e3;color:#fff;font-weight:bold;\">";
				html2append += "<span>用户名</span>";
				html2append += "<span>注册时间</span>";
				html2append += "<span>最近登录</span>";
				html2append += "<span>权限</span>";
				html2append += "<span>操作</span>";
				html2append += "<span onclick=\"doAddUser();\">新增用户</span>";
				html2append += "</li>";
				
				$.each(d.userInfos, function(i, item) {
					html2append += "<li>";
					html2append += "<span>" + item.userName + "&nbsp;</span>";
					html2append += "<span>" + item.regTime + "&nbsp;</span>";
					html2append += "<span>" + item.lastLogin + "&nbsp;</span>";
					html2append += "<span>" + item.permission + "&nbsp;</span>";
					if (item.userId > 0) {
						html2append += "<span>";
						html2append += "<input type=\"button\" value=\"编辑\" onclick=\"doEditUser('" + item.userId + "', '" + item.userName + "', '" + item.permission + "');\">";
						if (item.userName != "admin" && item.userName != d.currentuser) {  //不允许删除admin，不允许删除自己
							html2append += "<input type=\"button\" value=\"删除\" onclick=\"delUser('" + item.userId + "');\">";
						} else {
							html2append += "<input type=\"hidden\">";
						}
						html2append += "<input type=\"button\" value=\"重置密码\" onclick=\"resetPassword('" + item.userId + "', '" + item.userName + "')\">";
						html2append += "</span>";
					} else {
						html2append += "<span>&nbsp;</span>";
					}
					html2append += "</li>";
				});
				$("#userinfos").html(html2append);
			}
		});
	}
	
	function changePW() {
		showTipsWindown('修改密码', 'pwchange', 340, 175);
	}
	
	function checkNewPasswd() {
		var passwd = $("#windown-box").find('input[id="newpasswd"]').val();
		if (passwd == null || passwd =="") {
			alert("请输入密码");
		} else {
			if ($("#windown-box").find('input[id="repeatpasswd"]').val() == passwd) {
				$.ajax({
					type:"post",
					url:"user.do?passwdchange",
					timeout: 3000,
					dataType:"text",
					async:false,
					data:{newpassword:passwd},
					success:function(data) {
						alert(data);
						if (data=="操作成功") {
							closeIt();
						}
					}
				});
			} else {
				alert("两次输入的密码不一致");
			}
		}
	}
  </script>
  
  <body>
    <div class="headerbg">
      <div></div>
      <div></div>
      <div class="regards">
        <span>您好</span>&nbsp;<span id="currentusername"><%=username %></span>&nbsp;丨<a onclick="changePW();">修改密码</a>&nbsp;丨<a onclick="window.location.href='loginController.do?logout'">注销</a>
      </div>
    </div>
    
    <input type="hidden" id="pageNumber" name="pageNumber" value="${currentpage}">
    <input type="hidden" id="userid2reset" name="userid">
    
    <div class="leftbar">
      <ul>
        <li onclick="self.location='currentAlarm.do?list'"><img src="images/alarm.png" />报警<span id="alarmpoint" style="display:none;"></span></li>
        <li onclick="self.location='map.do?show'"><img src="images/map.png"/>地图</li>
        <li onclick="self.location='dev.do?show'"><img src="images/equipment.png"/>设备</li>
        <li onclick="self.location='position.do?show'"><img src="images/equipment1.png"/>设备分布</li>
        <li onclick="self.location='user.do?list'" class="hover"><img src="images/v-card.png"/>用户</li>
      </ul>
    </div>
    
    <div class="Contentbox">
      <div style="display:none;"><!-- 弹出框聚集地 -->
        <div id="deleteUser" ><!--删除用户弹出框开始-->
          <div class="deleteUser-content">
            <p>确定删除此用户？</p>
              <input type="hidden" id="userid2del" name="userid">
            <input type="button" value="确定" onClick="delUserInfo();" class="firstButton"/>
            <input type="button" value="取消" onClick="closeIt();"/>
          </div>
        </div><!--删除用户结束-->
        
        <div id="editUser" ><!--编辑用户弹出框开始-->
          <div class="editUser-content">
            <p>用户名 <input id="username2edit" name="username" maxlength="32">&nbsp;&nbsp;(<label style="color:red;">*</label>必填)</p>
            <p>权　限 <select id="permission2edit" name="permission">
              <option id="option1" value="1">管理员</option>
              <option id="option2" value="2">普通用户</option>
            </select>&nbsp;&nbsp;(<label style="color:red;">*</label>必填)</p>
            <input type="button" value="更新" onClick="checkEditInfo();" class="firstButton" style="margin-left: 60px;"/>
            <input type="button" value="取消" onClick="closeIt();" style="margin-left: 10px"/>
            <input type="hidden" id="userid2edit" name="userid" value="">
          </div>
        </div><!--编辑用户结束-->
        
        <div id="pwchange" ><!--密码修改弹出-->
          <div class="editUser-content">
            <p>新密码　<input id="newpasswd" name="newpasswd" maxlength="32">&nbsp;&nbsp;(<label style="color:red;">*</label>必填)</p>
            <p>重复密码<input id="repeatpasswd" name="repeatpasswd" maxlength="32">&nbsp;&nbsp;(<label style="color:red;">*</label>必填)</p>
            <input type="button" value="更新" onClick="checkNewPasswd();" class="firstButton" style="margin-left: 60px;"/>
            <input type="button" value="取消" onClick="closeIt();" style="margin-left: 10px"/>
          </div>
        </div><!--密码修改结束-->
        
        <div id="addUser" ><!--新增用户弹出框开始-->
          <div class="editUser-content">
            <p>用户名<input id="username2add" name="username" type="text" maxlength="32">&nbsp;&nbsp;(<label style="color:red;">*</label>必填)</p>
            <p>密　码<input type="password" id="password2add"  name="password" maxlength="32">&nbsp;&nbsp;(<label style="color:red;">*</label>必填)</p>
            <p>权　限<select id="permission2add" name="permission">
              <option value="1">管理员</option>
              <option value="2" selected="selected">普通用户</option>
            </select>&nbsp;&nbsp;(<label style="color:red;">*</label>必填)</p>
            <input type="button" value="确定" onClick="checkAddInfo();" class="firstButton" style="margin-left: 60px;"/>
            <input type="button" value="取消" onClick="closeIt();"/>
          </div>
        </div><!--新增用户结束-->
        <div id="resetPassword"><!--重置密码弹出框开始-->
          <div class="resetPassword-content">
            <p>确定重置此用户密码为默认？</p>
            <input type="button" value="确定" onClick="doResetPwd();closeIt();" class="firstButton"/>
            <input type="button" value="取消" onClick="closeIt();" style="margin-left:10px;"/>
          </div>
        </div><!--重置密码结束-->
      </div>
      
      <div><!-- 用户页面开始 -->
        <ul class="Contentheader">
          <li id="userManagement1"  class="hoverclicked">用户管理</li>
        </ul>
        <div id="userManagement_1">
          <ul id="userinfos" class="userManagement">
            <li style="background-color:#2e94e3;color:#fff;font-weight:bold;">
              <span>用户名</span>
              <span>注册时间</span>
              <span>最近登录</span>
              <span>权限</span>
              <span>操作</span>
              <span onclick="doAddUser();">新增用户</span>
            </li>
            <c:forEach begin="1" end="10" step="1">
              <li>
                <span>&nbsp;</span>
                <span>&nbsp;</span>
                <span>&nbsp;</span>
                <span>&nbsp;</span>
                <span>&nbsp;</span>
              </li>
            </c:forEach>
          </ul>
        </div>
        <p style="margin-top:10px;text-align:center;">共搜索到&nbsp;<b id="totalpositionitems">0</b>&nbsp;条匹配数据，共&nbsp;<b id="totaldevpages">1</b>&nbsp;页</p>
        <div id="devselectpagin" class="Pagination" style="margin-top:10px;width:749px;margin-left:200px;">
          <span id="pagein1" class="Pagin1">
            ${paginhtml}
          </span>
          <span class="Pagin2">
            <input id="tarpage" name="tarpage" type="text" value="1"/><a onclick="gotoselectpage();"><b>GO</b></a>
          </span>
        </div>
      </div><!-- 用户页面结束 -->
    </div>
    
    <div class="footer">
      <p>Copyright&nbsp;</p><span>©</span><p>&nbsp;2015 苏州摩多物联科技有限公司 All rights reserved. 版权所有</p>
    </div>
  </body>
</html>
