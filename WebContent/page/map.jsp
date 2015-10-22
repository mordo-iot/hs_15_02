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
    <title>地图</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="style/style.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="history/history.css" />
    <script type="text/javascript" src="history/history.js"></script>
    <script type="text/javascript" src="js/jquery-1.7.min.js"></script>
    <script type="text/javascript" src="js/swfobject.js"></script>
    <script type="text/javascript">
      // For version detection, set to min. required Flash Player version, or 0 (or 0.0.0), for no version detection.
      var swfVersionStr = "10.2.0";
      // To use express install, set to playerProductInstall.swf, otherwise the empty string.
      var xiSwfUrlStr = "playerProductInstall.swf";
      var flashvars = {};
      var params = {};
      params.quality = "high";
      params.bgcolor = "#ffffff";
      params.allowscriptaccess = "sameDomain";
      params.allowfullscreen = "true";
      var attributes = {};
      attributes.id = "Main";
      attributes.name = "Main";
      attributes.align = "middle";
      swfobject.embedSWF(
        "Main.swf?v=0.1", "con_one_2",
        "100%", "100%",
        swfVersionStr, xiSwfUrlStr,
        flashvars, params, attributes);
      // JavaScript enabled so display the flashContent div in case it is not replaced with a swf object.
      swfobject.createCSS("#con_one_2", "display:block;text-align:left;");
    </script>
  </head>

  <script type="text/javascript">
    $(document).ready(function() {
      getCurrentAlarmInfo();
      setInterval(getCurrentAlarmInfo, 5000);  //每隔一段时间获取一次报警数据，看看有没有新报警
    });
    
    var alarmHash = "";
    
    function getCurrentAlarmInfo() {
      $.ajax({
        type:"post",
        url:"<%=path %>/currentAlarm.do?show",
        dataType:"text",
        async:false,
        data:{},
        success:function(data){
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
  </script>
  
  <body>
    <div class="headerbg">
      <div></div>
      <div></div>
      <div class="regards">
        <span>您好</span>&nbsp;<span><%=username %></span>&nbsp;丨<a onclick="window.location.href='page/login.jsp'">注销</a>
      </div>
    </div>
    
    <form id="f3" action="user.do?list" method="post">
      <input type="hidden" id="pageNumber" name="pageNumber" value="${currentpage}">
    </form>
    <input type="hidden" id="userid2reset" name="userid">
    
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
   <div id="con_one_2"><!-- 地图页面开始 -->
       
   </div><!-- 地图页面结束 -->
   <noscript>
     <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
       width="100%" height="100%" id="shuichan">
       <param name="movie" value="shuichan.swf?v=2.15" />
       <param name="quality" value="high" />
       <param name="bgcolor" value="#ffffff" />
       <param name="allowScriptAccess" value="sameDomain" />
       <param name="allowFullScreen" value="true" />
       <!--[if !IE]>-->
       <object type="application/x-shockwave-flash"
         data="shuichan.swf?v=2.15" width="100%" height="100%">
         <param name="quality" value="high" />
         <param name="bgcolor" value="#ffffff" />
         <param name="allowScriptAccess" value="sameDomain" />
         <param name="allowFullScreen" value="true" />
         <!--<![endif]-->
         <!--[if gte IE 6]>-->
           <p>
             Either scripts and active content are not permitted to run or
             Adobe Flash Player version 10.2.0 or greater is not installed.
           </p>
         <!--<![endif]-->
         <a href="http://www.adobe.com/go/getflashplayer"><img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash Player" /></a>
         <!--[if !IE]>-->
       </object>
     <!--<![endif]-->
     </object>
     </noscript>
   </div>
   
   <div class="footer">
     <p>Copyright&nbsp;</p><span>©</span><p>&nbsp;2015 苏州摩多物联科技有限公司 All rights reserved. 版权所有</p>
   </div>
 </html>
 