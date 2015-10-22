<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String username = (String)session.getAttribute("username");
if (username == null) {
  response.sendRedirect("page/login.jsp");
} else {
  Integer role = (Integer)session.getAttribute("role");
  if (role == null || role != 1) {
    response.sendRedirect("currentAlarm.do?list");
  }
}
%>

<!DOCTYPE>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>设备信息</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <link href="style/style.css" rel="stylesheet" type="text/css"/>
  <script type="text/javascript" src="js/jquery-1.7.min.js"></script>
  <script type="text/javascript" src="js/tipswindown.js"></script>
    <script type="text/javascript" src="js/date.js"></script>
  <script type="text/javascript" src="js/js-temporary.js"></script>
  </head>

  <script type="text/javascript">
    function showTipsWindown(title,id,width,height) {
      tipsWindown(title,"id:"+id,width,height,"true","","true",id);
    }
    
    function closeIt() {
      parent.closeWindown();
    }
    
    function openDiv(objname) {  //打开id为objname的编辑框
      document.getElementById(objname).style.display="block";
    }
    
    function closeDiv(objname) {  //关闭编辑框
      document.getElementById(objname).style.display="none";
    }
    
    $(document).ready(function() {
      getDevListByType();
      $("input.mh_date").manhuaDate();  //取相同class调用函数，id会重复显示年月！
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
    
    function getDevListByType() {
      var devtypeid = $("#devtypefilter option:selected").val();
      $.ajax({
        type:"post",
        url:"<%=request.getContextPath() %>/dev.do?type",
        dataType:"text",
        async:false,
        data:{devtype:devtypeid},
        success:function(data){
          var d = $.parseJSON(data);
          $("#devlistarea").html("");
          $.each(d, function(i, item) {
            $("#devlistarea").append("<div class=\"mattress\" onclick=\"getDevInfo('" + item.devId + "')\"><div class=\"mattress-head\">" + item.devName + "</div><div class=\"mattress-content\" style=\"display:none;\"><p class=\"darkBlue\">" + item.devName + "</p><p>设备类型：<span>" + item.devType + "</span></p><p>MAC&nbsp;地址：<span>" + item.devMac + "</span></p><p>设备位置：<span>" + item.position + "</span></p></div></div>");
          });
          
          $(".mattress-head").click(function() {
            $(this).parent().siblings().children("div.mattress-content").css({"display":"none"});
            $(this).parent().siblings().children("div.mattress-head").css({"background":"url(images/closed.png) 160px 8px no-repeat"});  //关闭其他展开项，并把icon变为+
           var brother=$(this).next();  //同胞元素
            brother.toggle(300, function() {
              if (brother.css('display')=="block") {  //callback事件判断icon状态，此处this为brother元素
                $(this).prev().css({"background":"url(images/open.png) 160px 8px no-repeat"});
              } else {
                $(this).prev().css({"background":"url(images/closed.png) 160px 8px no-repeat"});
              }
            });
          });
        }
      });
    }
    
    function addDevicePreWork() {
      $("#addNewDeviceButton").css("display", "inline");
      $("#cancelAddDeviceButton").css("display", "inline");
      $("#saveDeviceEditButton").css("display", "none");
      $("#viewDeviceHistoryButton").css("display", "none");
      $("#delDeviceButton").css("display", "none");
      $("#cancelEditButton").css("display", "none");
      
      $("#devicename").val("");
      $("#devicetype").val("");
      $("#devicemac").val("");
      $("#devicepostionid").val("");
      $("#devpostionname").html("请选择");
      $("#parentdeviceid").val("");
      $("#parentdevname").html("请选择");
      $("#alarmstarttime").val("");
      $("#alarmendtime").val("");
      $("#alarmdelay").val("");
      $("#alarmcardmac").val("");
      $("#doorlightmac").val("");
      $("#ledmac").val("");
      $("#eboardmac").val("");
      $("#eboardcontent").val("");
      $("#alarmcardcontent").val("");
      $("#deviceid").val("");
    }
    
    function saveDevInfo() {
      var devicename = $("#devicename").val();
      var devicetype = $("#devicetype option:selected").val();
      var devicemac = $("#devicemac").val();
      var devicepostionid = $("#devicepostionid").val();
      var parentdeviceid = $("#parentdeviceid").val();
      var alarmstarttime = $("#alarmstarttime").val();
      var alarmendtime = $("#alarmendtime").val();
      var alarmdelay = $("#alarmdelay").val();
      var alarmcardmac = $("#alarmcardmac").val();
      var doorlightmac = $("#doorlightmac").val();
      var ledmac = $("#ledmac").val();
      var eboardmac = $("#eboardmac").val();
      var eboardcontent = $("#eboardcontent").val();
      var alarmcardcontent = $("#alarmcardcontent").val();
      var deviceid = $("#deviceid").val();
      
      if (devicename == null || devicename == "") {
        alert("请输入设备名称");
        return false;
      }
      
      if (devicetype == null || devicetype == "") {
        alert("请选择设备类型");
        return false;
      }
      
      if (devicemac == null || devicemac == "") {
        alert("请输入设备MAC信息");
        return false;
      }
      
      $.ajax({
        type:"post",
        url:"<%=request.getContextPath() %>/dev.do?save",
        dataType:"text",
        async:false,
        data:{devicename:devicename,devicetype:devicetype,devicemac:devicemac,devicepostionid:devicepostionid,parentdeviceid:parentdeviceid,alarmstarttime:alarmstarttime,alarmendtime:alarmendtime,alarmdelay:alarmdelay,alarmcardmac:alarmcardmac,doorlightmac:doorlightmac,ledmac:ledmac,eboardmac:eboardmac,eboardcontent:eboardcontent,alarmcardcontent:alarmcardcontent,deviceid:deviceid},
        success:function(data){
          alert(data);
          if (data != "保存失败" && deviceid == "") {
            addDevicePreWork();
            getDevListByType();
          }
        }
      });
    }
    
    function getDevInfo(deviceid) {
      $.ajax({
        type:"post",
        url:"<%=request.getContextPath() %>/dev.do?getdev",
        dataType:"text",
        async:false,
        data:{deviceid:deviceid},
        success:function(data){
          var item = $.parseJSON(data);
          
          $("#deviceid").val(item.devId);
          $("#devicename").val(item.devName);
          $("#devicetype").val(item.devType);
          $("#devicemac").val(item.devMac);
          $("#devicepostionid").val(item.positionId);
          $("#parentdeviceid").val(item.parentDevid);
          $("#alarmstarttime").val(item.startTime);
          $("#alarmendtime").val(item.endTime);
          $("#alarmdelay").val(item.alarmDelay);
          $("#alarmcardmac").val(item.alarmCard);
          $("#doorlightmac").val(item.doorLight);
          $("#ledmac").val(item.led);
          $("#eboardmac").val(item.eboard);
          $("#eboardcontent").val(item.eboardContent);
          $("#alarmcardcontent").val(item.cardContent);
          $("#deviceid").val(item.devId);
          
          $("#devpostionname").html(item.position);
          $("#parentdevname").html(item.alarmDev);
          
          $("#addNewDeviceButton").css("display", "none");
          $("#cancelAddDeviceButton").css("display", "none");
          $("#saveDeviceEditButton").css("display", "inline");
          if (item.devType == "2" || item.devType == "9" || item.devType == "10" || item.devType == "11" || item.devType == "12" ||item.devType == "13") {
            $("#viewDeviceHistoryButton").css("display", "inline");
            $("#subtypeselector").css("display", "none");
            $("#subtypeselector").html("");
          } else if (item.devType == "4") {
            $("#viewDeviceHistoryButton").css("display", "inline");
            $("#subtypeselector").css("display", "inline");
            $("#subtypeselector").html("<option value=\"人卡分离\" selected=\"selected\">人卡分离</option><option value=\"摔倒\">摔倒</option><option value=\"手动报警\">手动报警</option>");
          } else if (item.devType == "16") {
            $("#viewDeviceHistoryButton").css("display", "inline");
            $("#subtypeselector").css("display", "inline");
            $("#subtypeselector").html("<option value=\"床垫状态\" selected=\"selected\">床垫状态</option><option value=\"心跳\">心跳</option><option value=\"呼吸\">呼吸</option>");
          } else {
            $("#viewDeviceHistoryButton").css("display", "none");
            $("#subtypeselector").html("");
          }
          $("#delDeviceButton").css("display", "inline");
          $("#cancelEditButton").css("display", "inline");
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
    
    function getParentDevInfo(devname, pageNumber) {
      $("#devselectbox").attr("value", devname);
      $.ajax({
        type:"post",
        url:"<%=path %>/dev.do?choose",
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
    
    function selectPostion(positionId, positionName) {
      $("#devpostionname").html(positionName);
      $("#devicepostionid").val(positionId);
      closeIt();
    }
    
    function selectDev(devId, devName) {
      $("#parentdevname").html(devName);
      $("#parentdeviceid").val(devId);
      closeIt();
    }
    
    function delCurrentDev() {
      closeIt();
      var deviceid = $("#deviceid").val();
      $.ajax({
        type:"post",
        url:"<%=path %>/dev.do?del",
        dataType:"text",
        async:true,
        data:{deviceid:deviceid},
        success:function(data){
          alert(data);
          if (data == "删除成功") {
            getDevListByType();
            addDevicePreWork();
          }
        }
      });
    }
    
    function getHistoryData(pagenumber) {
      var deviceid = $("#deviceid").val();
      var begindate = $("#begin_date").val();
      var beginhour = $("#begin_hour option:selected").val();
      var enddate = $("#end_date").val();
      var endhour = $("#end_hour option:selected").val();
      var subtype = $("#subtypeselector option:selected").val();
      $.ajax({
        type:"post",
        url:"<%=path %>/dev.do?history",
        dataType:"text",
        async:true,
        data:{deviceid:deviceid, pagenumber:pagenumber, subtype:subtype, endhour:endhour, enddate:enddate, beginhour:beginhour, begindate:begindate},
        success:function(data) {
          var d = $.parseJSON(data);
          if (d.result != "获取成功") {
            alert(d.result);
          }
          $("#historydatatable").html("<tr><th style=\"width:50%\">时间</th><th style=\"width:50%\">数据</th></tr>");
          $.each(d.list, function(i, item) {
            var htmlString = "<tr><td style=\"width:50%\">" + item.updateDate + "&nbsp;</td><td style=\"width:50%\">" + item.value + "&nbsp;</td></tr>";
            $("#historydatatable").append(htmlString);
          });
          $("#historypagin").html(d.paginhtml);
          $("#totalhistoryitems").html(d.totalitem);
          $("#totalhistorypages").html(d.totalpage);
        }
      });
    }
    
    function go2tarhispage() {
      var pagenum = $("#tarhispage").val();
      if (pagenum != null && pagenum != "") {
        getHistoryData(pagenum);
      }
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
    
    <div class="leftbar">
      <ul>
        <li onclick="self.location='currentAlarm.do?list'"><img src="images/alarm.png" />报警<span id="alarmpoint" style="display:none;"></span></li>
        <li onclick="self.location='map.do?show'"><img src="images/map.png"/>地图</li>
        <li onclick="self.location='dev.do?show'" class="hover"><img src="images/equipment.png"/>设备</li>
        <li onclick="self.location='position.do?show'"><img src="images/equipment1.png"/>设备分布</li>
        <li onclick="self.location='user.do?list'"><img src="images/v-card.png"/>用户</li>
      </ul>
    </div>
    
    <div class="Contentbox">
      <div style="display:none;">  <!-- 弹出框 -->
        <div id="installationLocation"><!--设备安装位置开始-->
          <div class="six-six">
            <span style="margin-left: 80px;">设备位置选择:</span>
            <input id="positionsearchbox" placeholder="位置搜索" style="margin-left:30px;" onkeydown="if(event.keyCode==13){closeIt();getPositionInfo(this.value, '1');}"/>
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
        
        <div id="deleteEquipmentInformation">  <!--删除设备信息开始-->
          <div class="deleteUser-content">
            <p>是否删除该设备？</p>
            <input type="button" value="确定" onClick="delCurrentDev();"/>
            <input type="button" value="取消" onClick="closeIt();"/>
          </div>
        </div>  <!--删除设备信息结束-->
      </div>
      
      <div>  <!-- 设备页面开始 -->
        <ul class="Contentheader">
          <li id="equipment1"  class="hoverclicked">设备列表</li>
        </ul>
        <div id="equipment_1">
          <div class="equipmentCategory">
            <div class="equipmentCategoryBanner"><img src="images/zoom.png"/></div>
            <div class="equipmentCategorySelect">&nbsp;&nbsp;类别
              <select id="devtypefilter" onchange="getDevListByType()" style="text-indent:0px;">
                <option value="0" selected="selected">所有设备</option>
                <option value="1">温湿度设备</option>
                <option value="2">床垫设备</option>
                <option value="3">洗手间红外设备</option>
                <option value="4">园区内定位设备</option>
                <option value="5">园区外定位设备</option>
                <option value="6">园区内定位辅助设备</option>
                <option value="7">床报警器设备</option>
                <option value="8">园区内定位辅助设备-越界</option>
                <option value="9">一键报警终端</option>
                <option value="10">无线门磁终端</option>
                <option value="11">尿湿感应设备</option>
                <option value="12">腕带呼叫器</option>
                <option value="13">红外设备</option>
                <option value="14">护工胸牌</option>
                <option value="15">护工胸牌发射器</option>
                <option value="16">心率床垫</option>
                <option value="17">平安星手表</option>
                <option value="18">优频定位器</option>
                <option value="19">门灯</option>
                <option value="20">LED屏</option>
                <option value="21">电子看板</option>
              </select>
            </div>
            <div id="devlistarea" class="equipmentDetail">
            </div>
            <div class="add-equipment">
              <input type="button" value="新增设备" onclick="addDevicePreWork()"/>
            </div>
          </div>
          
          <div class="equipmentInformation">
            <div style="background-color:#2e94e3;height:50px;">设备信息</div>
              <div class="assessment-edit-pos1" style="width:0;height:0;left:0;top:0;position:relative;"> <!-- 父元素相对定位 -->
                <!--设备安装位置开始-->
                <div id="historyLocation" style="width:600px;height:520px;top:30px;left:50px;display:none;background-color:#fff;z-index:98;position:absolute;-webkit-box-shadow:0 0 10px 2px #888;box-shadow:0 0 10px 2px #888;">
                  <!-- 标题头部及关闭按钮 -->
                  <div style="width:100%;height:35px;line-height:35px;background-color:#2e94e3">
                    <span style="width:565px;text-align:center;line-height:35px;display:block;float:left;font-weight:bold;color:#fff;">历史数据</span>
                    <span onclick="closeDiv('historyLocation')"  style="width:35px;display:block;float:left;">
                      <img src="images/tipbg.png" style="margin-top:8px;margin-left:5px;">
                    </span>
                  </div>
                  
                  <div class="six-six">
                    <span>
                    <input type="text" id="begin_date" class="mh_date" readonly="readonly" style="text-indent: 10px;" onchange="getHistoryData('1');"/>
                    <select id="begin_hour" class="date-hour" style="width:48px;height:30px;border:0; background:url(./images/selectsmallest.png) no-repeat;text-indent:7px;margin-top:-3px;margin-left:10px;-moz-appearance: none;">
                      <c:forEach begin="0" end="24" step="1" varStatus="status">
                        <option value="${status.index}">${status.index}</option>
                      </c:forEach>
                    </select>
                    &nbsp;时&nbsp;—&nbsp;&nbsp;
                    <input type="text" id="end_date" class="mh_date" readonly="readonly" style="text-indent: 10px;" onchange="getHistoryData('1');"/>
                    <select id="end_hour" class="date-hour" style="width:48px;height:30px;border:0; background:url(./images/selectsmallest.png) no-repeat;text-indent:7px;margin-top:-3px;margin-left:10px;-moz-appearance: none;">
                      <c:forEach begin="0" end="24" step="1" varStatus="status">
                        <option value="${status.index}">${status.index}</option>
                      </c:forEach>
                    </select>
                    &nbsp;时
                    <button type="button" class="newaddbutton" onclick="getHistoryData('1')";>搜索</button>
                    </span>
                    <select id="subtypeselector" onchange="getHistoryData('1');" style="width:89px;background:transparent url(./images/selectsmall.png) no-repeat scroll 0% 0%;-moz-appearance: none;text-indent:7px;margin-top:-3px;margin-left:5px;">
                      <option value="人卡分离">人卡分离</option>
                      <option value="摔倒">摔倒</option>
                      <option value="手动报警">手动报警</option>
                      <option value="床垫状态">床垫状态</option>
                      <option value="心跳">心跳</option>
                      <option value="呼吸">呼吸</option>
                    </select>
                    
                    <table id="historydatatable" border="1" cellspacing="0">
                      <tr>
                        <th style="width:50%">时间</th>
                        <th style="width:50%">数据</th>
                      </tr>
                      <tr>
                        <td style="width:50%">时间</td>
                        <td style="width:50%">数据</td>
                      </tr>
                    </table>
                    
                    <p>共搜索到&nbsp;<b id="totalhistoryitems">0</b>&nbsp;条匹配数据，共&nbsp;<b id="totalhistorypages">1</b>&nbsp;页</p>
                    
                    <div class="Pagination">
                      <span id="historypagin" class="Pagin1">
                        <a onclick="gotoPrePage();" ><b>&lt;&lt;</b></a>
                        <a onclick="gotoPage();" class="selectedpage">1</a>  <!-- 当前页 -->
                        <a onclick="gotoPage();">2</a>
                        <a onclick="gotoNextPage();"><b>&gt;&gt;</b></a>
                      </span>
                      
                      <span class="Pagin2">
                        <input id="tarhispage" name="tarhispage" type="text" value="1" onkeydown="if(event.keyCode==13){go2tarhispage();}"/><a onclick="go2tarhispage();"><b>GO</b></a>
                      </span>
                    </div>
                  </div>
                </div><!--设备安装位置结束-->
              </div>   <!-- 父元素定位结束 -->

            <div class="equipmentInformationContent">
              <ul>
                <li>
                  <span>设备名称：</span>
                  <span><input id="devicename" name="devicename" type="text" maxlength="50"/></span>
                  <span>（<label style="color: red;">*</label>必填）</span>
                  <input id="deviceid" name="deviceid" type="hidden">
                </li>
                <li>
                  <span>设备类型：</span>
                  <span><select id="devicetype" name="devicetype">
                    <option value="">请选择</option>
                    <option value="1">温湿度设备</option>
                    <option value="2">床垫设备</option>
                    <option value="3">洗手间红外设备</option>
                    <option value="4">园区内定位设备</option>
                    <option value="5">园区外定位设备</option>
                    <option value="6">园区内定位辅助设备</option>
                    <option value="7">床报警器设备</option>
                    <option value="8">园区内定位辅助设备-越界</option>
                    <option value="9">一键报警终端</option>
                    <option value="10">无线门磁终端</option>
                    <option value="11">尿湿感应设备</option>
                    <option value="12">腕带呼叫器</option>
                    <option value="13">红外设备</option>
                    <option value="14">护工胸牌</option>
                    <option value="15">护工胸牌发射器</option>
                    <option value="16">心率床垫</option>
                    <option value="17">平安星手表</option>
                    <option value="18">优频定位器</option>
                    <option value="19">门灯</option>
                    <option value="20">LED屏</option>
                    <option value="21">电子看板</option>
                  </select></span>
                  <span>（<label style="color: red;">*</label>必填）</span>
                </li>
                <li>
                  <span>设备MAC地址：</span>
                  <span><input id="devicemac" name="devicemac" type="text" maxlength="50"/></span>
                  <span>（<label style="color: red;">*</label>必填）</span>
                </li>
                <li>
                  <span>设备安装位置：</span>
                  <span>
                    <span id="devpostionname" style="width:120px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;">请选择</span>
                    <button type="button" onclick="getPositionInfo('', '1')";>选择</button>
                    <input id="devicepostionid" name="devicepositionid" type="hidden">
                  </span>
                  <span>&nbsp;&nbsp;</span>
                </li>
                <li>
                  <span>所属设备：</span>
                  <span>
                    <span id="parentdevname">请选择</span>
                    <button type="button" onclick="getParentDevInfo('', '1')";>选择</button>
                    <input id="parentdeviceid" name="parentdeviceid" type="hidden">
                  </span>
                  <span>&nbsp;&nbsp;</span>
                </li>
                <li>
                  <span>报警开始时间：</span>
                  <span><input id="alarmstarttime" name="alarmstarttime" type="text" maxlength="4"/></span>
                  <span>（4位数字）</span>
                </li>
                <li>
                  <span>报警结束时间：</span>
                  <span><input id="alarmendtime" name="alarmendtime" type="text" maxlength="4"/></span>
                  <span>（4位数字）</span>
                </li>
                <li>
                  <span>报警延迟：</span>
                  <span><input id="alarmdelay" name="alarmdelay" type="text" maxlength="4"/></span>
                  <span>（分钟）</span>
                </li>
                <li>
                  <span>所属护工胸卡：</span>
                  <span><input id="alarmcardmac" name="alarmcardmac" type="text" maxlength="500"/></span>
                  <span>&nbsp;&nbsp;</span>
                </li>
                <li>
                  <span>所属门灯：</span>
                  <span><input id="doorlightmac" name="doorlightmac" type="text" maxlength="500"/></span>
                  <span>&nbsp;&nbsp;</span>
                </li>
                <li>
                  <span>所属LED屏：</span>
                  <span><input id="ledmac" name="ledmac" type="text" maxlength="500"/></span>
                  <span>&nbsp;&nbsp;</span>
                </li>
                <li>
                  <span>所属电子看板：</span>
                  <span><input id="eboardmac" name="eboardmac" type="text" maxlength="500"/></span>
                  <span>&nbsp;&nbsp;</span>
                </li>
                <li>
                  <span>电子看板内容：</span>
                  <span><input id="eboardcontent" name="eboardcontent" type="text" maxlength="9"/></span>
                  <span>&nbsp;</span>
                </li>
                <li>
                  <span>胸卡报警内容：</span>
                  <span><input id="alarmcardcontent" name="alarmcardcontent" type="text" maxlength="4"/></span>
                  <span>（4位数字）</span>
                </li>
              </ul>
              <input type="button" id="addNewDeviceButton" value="新增" onClick="saveDevInfo()" class="" style="margin-left: 50px"/>
              <input type="button" id="saveDeviceEditButton" value="保存修改" onClick="saveDevInfo()" class="" style="display:none"/>
              <input type="button" id="viewDeviceHistoryButton" value="历史数据" onClick="openDiv('historyLocation');getHistoryData('1');" class="" style="display:none"/>
              <input type="button" id="delDeviceButton" value="删除" onClick="showTipsWindown('删除','deleteEquipmentInformation',290,115)" class="" style="display:none"/>
              <input type="button" id="cancelAddDeviceButton" value="取消" onClick="addDevicePreWork()" class="" style="margin-left: 50px"/>
              <input type="button" id="cancelEditButton" value="取消" onClick="addDevicePreWork()" class="" style="display:none"/>
            </div>
          </div>
        </div>
      </div><!-- 设备页面结束-->
    </div>
    
    <div class="footer">
      <p> Copyright&nbsp;</p><span>©</span><p>&nbsp;2015 苏州摩多物联科技有限公司 All rights reserved. 版权所有</p>
    </div>
  </body>
</html>
