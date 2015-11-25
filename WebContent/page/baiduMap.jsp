<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String username = (String)session.getAttribute("username");
Integer role = (Integer)session.getAttribute("role");
%>

<!DOCTYPE>
<html>
  <head>
    <title>室外地图</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="style/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="js/jquery-1.7.min.js"></script>
    <script type="text/javascript" src="js/tipswindown.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=TbEPXcQbr7UROaKUR1zzKGoB"></script>
    <style type="text/css">
      #outdoorMap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
    </style>
  </head>

  <script type="text/javascript">
	function showTipsWindown(title, id, width, height) {
		tipsWindown(title, "id:" + id, width, height, "true", "", "true", id);	
	}
	
	function closeIt() {
		parent.closeWindown();
	}
	
	$(document).ready(function() {
		getCurrentAlarmInfo();  //获取报警数据
		setInterval(getCurrentAlarmInfo, 5000);  //每隔一段时间获取一次报警数据，看看有没有新报警
		// 百度地图API功能
		var map = new BMap.Map("outdoorMap");    // 创建Map实例
		map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);  // 初始化地图,设置中心点坐标和地图级别
		map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
		map.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
		map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
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
    
    <div class="leftbar">
      <ul>
        <li onclick="self.location='currentAlarm.do?list'"><img src="images/alarm.png" />报警<span id="alarmpoint" style="display:none;"></span></li>
        <li onclick="self.location='map.do?show'" class="hover"><img src="images/map.png"/>地图</li>
        <c:if test="${role == 1}">
          <li onclick="self.location='dev.do?show'"><img src="images/equipment.png"/>设备</li>
          <li onclick="self.location='position.do?show'"><img src="images/equipment1.png"/>设备分布</li>
          <li onclick="self.location='user.do?list'"><img src="images/v-card.png"/>用户</li>
        </c:if>
      </ul>
    </div>
    
    <div class="Contentbox">
      <div style="display:none;">  <!-- 弹出框聚集地 -->
        <div id="pwchange" ><!--密码修改弹出-->
          <div class="editUser-content">
            <p>新密码　<input id="newpasswd" name="newpasswd" maxlength="32">&nbsp;&nbsp;(<label style="color:red;">*</label>必填)</p>
            <p>重复密码<input id="repeatpasswd" name="repeatpasswd" maxlength="32">&nbsp;&nbsp;(<label style="color:red;">*</label>必填)</p>
            <input type="button" value="更新" onClick="checkNewPasswd();" class="firstButton" style="margin-left: 60px;"/>
            <input type="button" value="取消" onClick="closeIt();" style="margin-left: 10px"/>
          </div>
        </div><!--密码修改结束-->
      </div>
      
      <div id="con_one_4">  <!-- 地图开始 -->
        <ul class="Contentheader" id="pagetop">
          <!-- 位置导航栏信息 -->
          <li id="Caution2" onclick="self.location='map.do?show'" style="cursor: pointer;">园区地图</li>
          <li id="Caution2" onclick="self.location='map.do?outdoor'" class="hoverclicked" style="cursor: pointer;">室外地图</li>
        </ul>
        
        <div id="distribution_1">
          <!--
          <div class="distributionBanner" style="background-color:#2e94e3;text-align:center;">室外地图</div>
          -->
          <div id="outdoorMap" style="position:relative;height:755px;">
          </div>
        </div>
      </div><!-- 地图结束 -->
    </div>
    
    <div class="footer">
      <p>Copyright&nbsp;</p><span>©</span><p>&nbsp;2015 苏州摩多物联科技有限公司 All rights reserved. 版权所有</p>
    </div>
  </body>
</html>
