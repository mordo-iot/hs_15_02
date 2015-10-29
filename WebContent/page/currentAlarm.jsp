<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String username = (String)session.getAttribute("username");
if (username == null) {
  response.sendRedirect("page/login.jsp");
}
Integer role = (Integer)session.getAttribute("role");
%>

<!DOCTYPE>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>报警</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="style/style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="js/tipswindown.js"></script>
  </head>

  <script type="text/javascript">
    function showTipsWindown(title,id,width,height){
      tipsWindown(title,"id:"+id,width,height,"true","","true",id);
    }
    
    function closeIt() {
      parent.closeWindown();
    }
    
    function doCancelAlarm(alarmid) {
      showTipsWindown('提示','CancelAlarm',290,115);
      $("#alarmid4del").val(alarmid);
    }
    
    function sure4del() {
      var alarmid = $("#alarmid4del").val();
      $.ajax({
        type:"post",
        url:"<%=path %>/currentAlarm.do?cancelAlarm",
        dataType:"text",
        async:false,
        data:{alarmid:alarmid},
        success:function(data){
          alert(data);
          if (data == "取消成功") {
            getCurrentAlarmInfo();
          }
        }
      });
      closeIt();
    }
    
    $(document).ready(function() {
      $("#alarmhash").val("");
      getCurrentAlarmInfo();
      setInterval(getCurrentAlarmInfo, 3000);
    });
    
    function getCurrentAlarmInfo() {
      var alarmType = $("#alarmTypeSelector").val();
      $.ajax({
        type:"post",
        url:"<%=path %>/currentAlarm.do?show",
        dataType:"text",
        data:{alarmType:alarmType},
        success:function(data){
          var d = $.parseJSON(data);
          if ($("#alarmhash").val() != d.hashCode) {
            $("#alarmhash").val(d.hashCode);
            var htmlString = "";
            $.each(d.list, function(i, item) {
              htmlString += "<li>";
              htmlString += "<span>" + item.alarmTime + "&nbsp;</span>";
              htmlString += "<span>" + item.alarmType + "&nbsp;</span>";
              htmlString += "<span>" + item.alarmContent + "&nbsp;</span>";
              htmlString += "<span>" + item.alarmTarget + "&nbsp;</span>";
              htmlString += "<span><input type=\"button\" value=\"取消\" onclick=\"doCancelAlarm('" + item.alarmId + "','提示','CancelAlarm',290,115)\"></input></span>";
              htmlString += "</li>";
            });
            $("#messageArea").html(htmlString);
          } else {
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
            url:"<%=path %>/user.do?passwdchange",
            dataType:"text",
            async:false,
            data:{newpassword:passwd},
            success:function(data){
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
  
  <body>&nbsp; 
    <input id="alarmhash" type="hidden">
    <div class="headerbg">
      <div></div>
      <div></div>
      <div class="regards">
        <span>您好</span>&nbsp;<span id="currentusername"><%=username %></span>&nbsp;丨<a onclick="changePW();">修改密码</a>&nbsp;丨<a onclick="window.location.href='page/login.jsp'">注销</a>
      </div>
    </div>
    
    <div class="leftbar">
      <ul>
        <li onclick="self.location='currentAlarm.do?list'" class="hover"><img src="images/alarm.png" />报警<span style="display:none;"></span></li>
        <li onclick="self.location='map.do?show'" ><img src="images/map.png"/>地图</li>
        <c:if test="${role == 1}">
          <li onclick="self.location='dev.do?show'"><img src="images/equipment.png"/>设备</li>
          <li onclick="self.location='position.do?show'"><img src="images/equipment1.png"/>设备分布</li>
          <li onclick="self.location='user.do?list'"><img src="images/v-card.png"/>用户</li>
        </c:if>
      </ul>
    </div>
    
    <div class="Contentbox">
      <div id="con_one_1">
        <ul class="Contentheader">
          <li id="Caution1" onclick="self.location='currentAlarm.do?list'" class="hoverclicked" style="cursor: pointer;">报警预警</li>
          <li id="Caution2" onclick="self.location='hisalarm.do?list'" style="cursor: pointer;">历史报警</li>
        </ul>
        
        <div id="Caution_1">
          <ul class="warningMessage">
            <li style="background-color:#2e94e3;color:#fff;font-weight:bold;">
              <span>报警时间</span>
              <span>
                <select class="select-style" id="alarmTypeSelector" onchange="getCurrentAlarmInfo()">
                  <option value="">报警类型</option>
                  <option value="E001" style="color:#000;">设备离线</option>
                  <option value="E002" style="color:#000;">设备低电压</option>
                  <option value="E003" style="color:#000;">设备故障</option>
                  <option value="E011" style="color:#000;">床垫预警</option>
                  <option value="E012" style="color:#000;">床垫报警</option>
                  <option value="E013" style="color:#000;">猝死报警</option>
                  <option value="E021" style="color:#000;">越界报警</option>
                  <option value="E022" style="color:#000;">摔倒报警</option>
                  <option value="E023" style="color:#000;">手动报警</option>
                  <option value="E024" style="color:#000;">人卡分离</option>
                  <option value="E031" style="color:#000;">门磁预警</option>
                  <option value="E032" style="color:#000;">门磁报警</option>
                  <option value="E041" style="color:#000;">一键报警</option>
                  <option value="E051" style="color:#000;">尿湿报警</option>
                  <option value="E061" style="color:#000;">腕带主动报警</option>
                  <option value="E071" style="color:#000;">红外设备报警</option>
                  <option value="E081" style="color:#000;">体征床垫离床</option>
                </select>
              </span>
              <span>报警内容</span>
              <span>报警对象</span>
              <span>操作</span>
            </li>
          </ul>
          <ul class="warningMessage" style="width:100%;height:700px;" id="messageArea">
          </ul>
          
          <div style="display:none;"><!-- 弹出框 -->
            <div id="CancelAlarm" >
              <div class="CancelAlarm-content">
                <p>是否取消该报警</p>
                <input type="button" value="确定" onClick="sure4del()"/>
                <input type="button" value="取消" onClick="closeIt();"/>
                <input type="hidden" id="alarmid4del" name="alarmid4del"/>
              </div>
            </div>
        
        <div id="pwchange" ><!--密码修改弹出-->
          <div class="editUser-content">
            <p>新密码　<input id="newpasswd" name="newpasswd" maxlength="32">&nbsp;&nbsp;(<label style="color:red;">*</label>必填)</p>
            <p>重复密码<input id="repeatpasswd" name="repeatpasswd" maxlength="32">&nbsp;&nbsp;(<label style="color:red;">*</label>必填)</p>
            <input type="button" value="更新" onClick="checkNewPasswd();" class="firstButton" style="margin-left: 60px;"/>
            <input type="button" value="取消" onClick="closeIt();" style="margin-left: 10px"/>
          </div>
        </div><!--密码修改结束-->
          </div>
        </div>
      </div>
    </div>
    
    <div class="footer">
      <p> Copyright&nbsp;</p><span>©</span><p>&nbsp;2015 苏州摩多物联科技有限公司 All rights reserved. 版权所有</p>
    </div>
  </body>
</html>
