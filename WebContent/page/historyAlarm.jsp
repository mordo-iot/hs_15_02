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
    <title>历史报警</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" type="text/css" href="style/jquery.datetimepicker.css"/>
    <link href="style/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="js/jquery-1.7.min.js"></script>
    <script type="text/javascript" src="js/tipswindown.js"></script>
    <script type="text/javascript" src="js/jquery.datetimepicker.full.js"></script>
    <script>
      $(document).ready(function() {
        searchButtonClicked(1);
        getCurrentAlarmInfo();
        setInterval(getCurrentAlarmInfo, 5000);  //每隔一段时间获取一次报警数据，看看有没有新报警
        
        $.datetimepicker.setLocale('ch');
        $("input.mh_date").datetimepicker({
          formatDate:'Y.m.d',
          defaultDate:'',
          defaultTime:'00:00'
        });
      });
      
      function showTipsWindown(title,id,width,height){
        tipsWindown(title,"id:"+id,width,height,"true","","true",id);
      }
      
      function showDesAddWindow(alarmid, des, title, id, width, height) {
        $("#alarmid2update").val(alarmid);
        $("#des2update").text(des);
        showTipsWindown(title, id, width, height);
      }
      
      function closeIt() {
        parent.closeWindown();
      }
      
      var alarmHash = "";
      
      function getCurrentAlarmInfo() {
        $.ajax({
          type:"post",
          url:"<%=path %>/currentAlarm.do?show",
          dataType:"text",
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
      
      function searchButtonClicked(pagenumber) {
        var begintime = $("#begin_date").val();
        var endtime = $("#end_date").val();
        if (begintime != null && begintime != "" && endtime != null && endtime != "") {
          var begin = begintime.substr(0,4) + begintime.substr(5,2) + begintime.substr(8,2) + begintime.substr(11,2);
          var end = endtime.substr(0,4) + endtime.substr(5,2) + endtime.substr(8,2) + endtime.substr(11,2);
          if (parseInt(begin, "10") >= parseInt(end, "10")) {
        	alert("结束时间应该晚于开始时间");
            return;
          }
        }
        var alarmtype = $("#alarmTypeSelector option:selected").val();
        var handlestatus = $("#handleStatusSelector option:selected").val();
        var devid = $("#devid").val();
        var positionid = $("#positionid").val();
        $.ajax({
          type:"post",
          url:"<%=path %>/hisalarm.do?query",
          dataType:"text",
          async:false,
          data:{begintime:begintime, endtime:endtime, devid:devid, positionid:positionid, alarmtype:alarmtype, handlestatus:handlestatus, pagenumber:pagenumber},
          success:function(data){
            var d = $.parseJSON(data);
            $("#warningHistory").html("");
            $.each(d.list, function(i, item) {
              var htmlString = "<li>";
              htmlString += "<span>" + item.alarmTime + "&nbsp;</span>";
              htmlString += "<span>" + item.alarmType + "&nbsp;</span>";
              htmlString += "<span>" + item.alarmContent + "&nbsp;</span>";
              htmlString += "<span>" + item.alarmTarget + "&nbsp;</span>";
              if (item.handleStatus == '未处理') {
                htmlString += "<span class=\"red\">" + item.handleStatus + "&nbsp;</span>";
                htmlString += "<span>" + item.handleDes + "&nbsp;</span>";
              } else {
                htmlString += "<span class=\"green\">" + item.handleStatus + "&nbsp;</span>";
                if (item.alarmId != null && item.alarmId != "") {
                  if (item.handleDes == null || item.handleDes == "") {
                    htmlString += "<span onclick=\"showDesAddWindow('" + item.alarmId + "','" + item.handleDes + "','添加处理说明','Processing-instructions',300,270)\">点击添加说明</span>";
                  } else {
                    htmlString += "<span onclick=\"showDesAddWindow('" + item.alarmId + "','" + item.handleDes + "','添加处理说明','Processing-instructions',300,270)\">" + item.handleDes + "&nbsp;</span>";
                  }
                }
              }
              htmlString += "</li>";
              $("#warningHistory").append(htmlString);
            });
            
            $("#totalpages").html(d.totalpage);
            $("#totalitems").html(d.totalitem);
            $("#paginarea").html(d.paginhtml);
          }
        });
      }
      
      function updateDesinfo() {
        var alarmid = $("#alarmid2update").val();
        var des = $("#windown-box").find('textarea[id="des2update"]').val();
        $.ajax({
          type:"post",
          url:"<%=path %>/hisalarm.do?update",
          dataType:"text",
          async:false,
          data:{alarmid:alarmid, des:des},
          success:function(data){
            alert(data);
            if (data=="更新成功") {
              closeIt();
              searchButtonClicked($("#currentPageno").val());
            }
          }
        });
      }
      
      function getPositionInfo(positionName, pageNumber) {
        $("#positionsearchbox").attr("value", positionName);
        $.ajax({
          type:"post",
          url:"<%=path %>/position.do?choose",
          dataType:"text",
          async:false,
          data:{positionName:positionName, pageNumber:pageNumber},
          success:function(data){
            var info = $.parseJSON(data);
            $("#positionselecttable").html("");
            $("#positionselecttable").append("<tr><th>位置</th><th>操作</th></tr>");
            $.each(info.list, function(i, item) {
              var html2append = "<tr><td>" + item.positionName + "&nbsp;</td>";
              if (item.positionId == 0) {
                html2append += "<td>&nbsp;</td></tr>";
              } else {
                html2append += "<td><input type=\"button\" value=\"选择\" onclick=\"selectPostion('" + item.positionId + "','" + item.positionName + "')\"></td></tr>";
              }
              $("#positionselecttable").append(html2append);
            });
            $("#totalpositionpages").html("" + info.totalpage);
            $("#totalpositionitems").html("" + info.totalitem);
            $("#positionselectpagin").html(info.paginhtml);
          }
        });
        
        showTipsWindown('设备位置选择','installationLocation',590,480);
      }
      
      function selectPostion(positionId, positionName) {
        $("#devpostionname").html(positionName);
        $("#positionid").val(positionId);
        closeIt();
      }
      
      function selectDev(devId, devName) {
        $("#parentdevname").html(devName);
        $("#devid").val(devId);
        closeIt();
      }
      
      function getParentDevInfo(devname, pageNumber) {
          $("#devselectbox").attr("value", devname);
          $.ajax({
            type:"post",
            url:"<%=path %>/dev.do?chooseAll",
            dataType:"text",
            async:false,
            data:{devname:devname, pageNumber:pageNumber},
            success:function(data){
              var info = $.parseJSON(data);
              $("#parentdevselecttable").html("");
              $("#parentdevselecttable").append("<tr><th>名称</th><th>MAC</th><th>位置</th><th>操作</th></tr>");
              $.each(info.list, function(i, item) {
                var html2append = "<tr><td>" + item.devName + "</td><td>" + item.devMac + "</td><td>" + item.position + "</td>";
                if (item.devId == 0) {
                  html2append += "<td>&nbsp;</td></tr>";
                } else {
                  html2append += "<td><input type=\"button\" value=\"选择\" onclick=\"selectDev('" + item.devId + "', '" + item.devName + "')\"></td></tr>";
                }
                $("#parentdevselecttable").append(html2append);
              });
              $("#totaldevpages").html("" + info.totalpage);
              $("#totalpositionitems").html("" + info.totalitem);
              $("#devselectpagin").html(info.paginhtml);
            }
          });
          showTipsWindown('所属设备选择','equipmentBelong',590,480);
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
  </head>
  
  <body>
    <input id="positionid" name="positionid" type="hidden">
    <input id="devid" name="devid" type="hidden">
    
    <div class="headerbg">
      <div></div>
      <div></div>
      <div class="regards">
        <span>您好</span>&nbsp;<span id="currentusername"><%=username %></span>&nbsp;丨<a onclick="changePW();">修改密码</a>&nbsp;丨<a onclick="window.location.href='page/login.jsp'">注销</a>
      </div>
    </div>
    
    <div class="leftbar">
      <ul>
        <li onclick="self.location='currentAlarm.do?list'" class="hover"><img src="images/alarm.png" />报警<span id="alarmpoint" style="display:none;"></span></li>
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
          <li id="Caution1" onclick="self.location='currentAlarm.do?list'" style="cursor: pointer;">报警预警</li>
          <li id="Caution2" onclick="self.location='hisalarm.do?list'" class="hoverclicked" style="cursor: pointer;">历史报警</li>
        </ul>
        
        <div style="display:none;"><!-- 弹出框聚集地 -->
        <div id="pwchange" ><!--密码修改弹出-->
          <div class="editUser-content">
            <p>新密码　<input id="newpasswd" name="newpasswd" maxlength="32">&nbsp;&nbsp;(<label style="color:red;">*</label>必填)</p>
            <p>重复密码<input id="repeatpasswd" name="repeatpasswd" maxlength="32">&nbsp;&nbsp;(<label style="color:red;">*</label>必填)</p>
            <input type="button" value="更新" onClick="checkNewPasswd();" class="firstButton" style="margin-left: 60px;"/>
            <input type="button" value="取消" onClick="closeIt();" style="margin-left: 10px"/>
          </div>
        </div><!--密码修改结束-->
        
          <div id="Processing-instructions" ><!--处理说明弹出框开始-->
            <div class="resetPassword-content">
              <textarea id="des2update" style="width:250px;height:180px;max-width:250px;max-height:180px;margin:20px 0 0 30px;line-height:20px;"></textarea>
              <input type="hidden" id="alarmid2update" name="alarmid2update">
              <input type="button" value="保存" onClick="updateDesinfo();" style="margin-left:45px"/>
              <input type="button" value="取消" onClick="closeIt();" class=""/>
            </div>
          </div><!--处理说明结束-->
          <div id="installationLocation"><!--设备安装位置开始-->
            <div class="six-six">
              <span style="margin-left: 80px;">设备位置选择:</span>
              <input id="positionsearchbox" placeholder="位置搜索" style="margin-left:30px;" onkeydown="if(event.keyCode==13){closeIt();getPositionInfo(this.value, '1');}"/>
              <input type="button" onclick="selectPostion('', '未选择')"; value="取消选择" style="background: transparent url('./images/button.png') no-repeat scroll 0% 0%;border: 0px none;width: 100px;height: 30px;color: #fff;">
              <table id="positionselecttable" border="1" cellspacing="0">
                <tr>
                  <th>位置</th>
                  <th>操作</th>
                </tr>
                <tr>
                  <td>位置</td>
                  <td><input type="button" value="选择"></td>
                </tr>
              </table>
              <p>共搜索到&nbsp;<b id="totalpositionitems">0</b>&nbsp;条匹配数据，共&nbsp;<b id="totalpositionpages">1</b>&nbsp;页</p>
              <div id="positionselectpagin" class="Pagination">
                <span class="Pagin1">
                  <a onclick="gotoPrePage();" ><b>&lt;&lt;</b></a>
                  <a onclick="gotoPage();" class="selectedpage">1</a>  <!-- 当前页 -->
                  <a onclick="gotoPage();">2</a>
                  <a onclick="gotoPage();">3</a>
                  <a onclick="gotoPage();">4</a>
                  <a onclick="gotoPage();">5</a>
                  <a onclick="gotoPage();">6</a>
                  <a onclick="gotoNextPage();"><b>&gt;&gt;</b></a>
                </span>
                <span class="Pagin2">
                  <input id="tarpage" name="tarpage" type="text" value="1"/><a onclick="" ><b>GO</b></a>
                </span>
              </div>
            </div>
          </div><!--设备安装位置结束-->
          <div id="equipmentBelong" ><!--所属设备开始-->
          <div class="six-eight">
            <span style="margin-left: 80px;">所属设备选择:</span>
            <input id="devsearchbox" type="text" placeholder="设备搜索" style="margin-left:30px;" onkeydown="if(event.keyCode==13){closeIt();getParentDevInfo(this.value, '1');}"/>
            <input type="button" onclick="selectDev('', '未选择')"; value="取消选择" style="background: transparent url('./images/button.png') no-repeat scroll 0% 0%;border: 0px none;width: 100px;height: 30px;color: #fff;">
            <table id="parentdevselecttable" border="1" cellspacing="0">
              <tr>
                <th>名称</th>
                <th>MAC</th>
                <th>位置</th>
                <th>操作</th>
              </tr>
              <tr>
                <td>名称</td>
                <td>MAC</td>
                <td>位置</td>
                <td><input type="button" value="选择"></td>
              </tr>
            </table>
            <p>共搜索到&nbsp;<b id="totalpositionitems">0</b>&nbsp;条匹配数据，共&nbsp;<b id="totaldevpages">1</b>&nbsp;页</p>
            <div id="devselectpagin" class="Pagination">
              <span class="Pagin1">
                <a onclick="gotoPrePage();" ><b>&lt;&lt;</b></a>
                <a onclick="gotoPage();" class="selectedpage">1</a>  <!-- 当前页 -->
                <a onclick="gotoPage();">2</a>
                <a onclick="gotoPage();">3</a>
                <a onclick="gotoPage();">4</a>
                <a onclick="gotoPage();">5</a>
                <a onclick="gotoPage();">6</a>
                <a onclick="gotoNextPage();"><b>&gt;&gt;</b></a>
              </span>
              <span class="Pagin2">
                <input id="tarpage" name="tarpage" type="text" value="1"/><a onclick="" ><b>GO</b></a>
              </span>
            </div>
          </div>
        </div><!--所属设备结束-->
        </div>
        
        <!-- 历史报警开始 -->
        <div id="Caution_2">
          <ul class="HistoryRefer">
            <li>
              &nbsp;&nbsp;时段&nbsp;
              <input type="text" id="begin_date" class="mh_date" readonly="readonly" style="width: 180px;background-image: url('./images/usernamebg.png');"/>
              &nbsp;—&nbsp;
            </li>
            <li>
              <input type="text" id="end_date" class="mh_date" readonly="readonly" style="width: 180px;background-image: url('./images/usernamebg.png');"/>
            </li>
            <li style="margin-left:30px;">
              设备位置：&nbsp;<label id="devpostionname">未选择</label><button type="button" class="newaddbutton" onclick="getPositionInfo('', '1')";>选择</button>
            </li>
            <li style="margin-left:30px;">
              设备类型：&nbsp;<label id="parentdevname">未选择</label><button type="button" class="newaddbutton" onclick="getParentDevInfo('', '1')";>选择</button>
            </li>
            <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class="search-bottom" type="button" value="查询" onclick="searchButtonClicked(1)"/></li>
          </ul>
          <ul class="warningHistory">
            <li style="background-color:#34a1f0;color:#fff;font-weight:bold;">
              <span>报警时间</span>
              <span>
                <select id="alarmTypeSelector" class="select-style" onchange="searchButtonClicked(1)">
                  <option value="">报警类型</option>
                  <option value="E001">设备离线</option>
                  <option value="E002">设备低电压</option>
                  <option value="E003">设备故障</option>
                  <option value="E011">床垫预警</option>
                  <option value="E012">床垫报警</option>
                  <option value="E013">猝死报警</option>
                  <option value="E021">越界报警</option>
                  <option value="E022">摔倒报警</option>
                  <option value="E023">手动报警</option>
                  <option value="E024">人卡分离</option>
                  <option value="E031">门磁预警</option>
                  <option value="E032">门磁报警</option>
                  <option value="E041">一键报警</option>
                  <option value="E051">尿湿报警</option>
                  <option value="E061">腕带主动报警</option>
                  <option value="E071">红外设备报警</option>
                  <option value="E081">体征床垫离床</option>
                </select>
              </span>
              <span>报警内容</span>
              <span>报警对象</span>
              <span>
                <select id="handleStatusSelector" class="select-style1" onchange="searchButtonClicked(1)">
                  <option value="">处理状态</option>
                  <option value="0">待处理</option>
                  <option value="1">已处理</option>
                  <option value="2">手动取消</option>
                </select>
              </span>
              <span>处理说明</span>
            </li>
            <form id="warningHistory">
            </form>
          </ul>
        </div>  <!-- 历史报警结束 -->
      </div>  <!-- 报警页面结束 -->
      
      <p style="margin-top:10px;text-align:center;">搜索到&nbsp;<b id="totalitems">0</b>&nbsp;条匹配数据，共&nbsp;<b id="totalpages">1</b>&nbsp;页</p>
      <div id="devselectpagin" class="Pagination" style="margin-top:10px;width:749px;margin-left:200px;">
        <span id="paginarea" class="Pagin1">
          <a onclick="gotoPrePage();" ><b>&lt;&lt;</b></a>
          <a onclick="gotoPage();" class="selectedpage">1</a>
          <a onclick="gotoPage();">2</a>
        </span>
        
        <span class="Pagin2">
          <input id="hisdatapage" name="tarpage" type="text" value="1" onkeydown="if(event.keyCode==13){searchButtonClicked($('#hisdatapage').val());}"/><a onclick="searchButtonClicked($('#hisdatapage').val())"><b>GO</b></a>
        </span>
      </div>
    </div>
    
    <div class="footer">
      <p> Copyright&nbsp;</p><span>©</span><p>&nbsp;2015 苏州摩多物联科技有限公司 All rights reserved. 版权所有</p>
    </div>
  </body>
</html>
