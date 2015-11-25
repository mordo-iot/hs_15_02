<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String username = (String)session.getAttribute("username");
Integer role = (Integer)session.getAttribute("role");
%>

<!DOCTYPE>
<html>
  <head>
    <title>园区地图</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="style/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="js/jquery-1.7.min.js"></script>
    <script type="text/javascript" src="js/tipswindown.js"></script>
    <script src="./js/raphael.js"></script>
    <style type="text/css">
      .fontintitle {
        color: #fff;
      }
      
      .mapposselector {
        background:transparent url("./images/currentFloorbg.png") no-repeat scroll 0% 0%;
        border: 0px none;
        width: 180px;
        height: 30px;
        text-indent: 35px;
        margin-top: -2px;
        margin-left: 10px;
      }
      
      #personNumberSpan {
        color: #f5ad58;
        font-size: 24px;
      }
      
      .personDetailTable {
        padding-left: 100px;
        padding-top: 20px;
        text-align: center;
        font-weight: bold;
      }
      
      .personDetailTable .col1 {
        width: 432px;
        height: 40px;
        vertical-align: middle;
      }
      
      .personDetailTable .col2 {
        width: 600px;
        height: 40px;
        vertical-align: middle;
      }
      
      .nomaphint {
        position: relative;
        padding-top: 200px;
        padding-left: 450px;
        font-size: 32px;
      }
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
		getBuildings();  //获取building下拉列表的数据
		floorChanged();  //获取定位人数信息
		getPointDetail();  //获取定位点详细
		setInterval(getPointDetail, 4000);  //每隔一段时间获取一次定位数据
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
	
	function getBuildings() {
		$.ajax({
			type:"post",
			url:"map.do?building",
			timeout: 3000,
			dataType:"text",
			async:true,
			data:{},
			success:function(data) {
				var d = $.parseJSON(data);
				var contentInSelect = "<option value=\"\" selected=\"selected\">总览</option>";
				$.each(d, function(i, item) {
					contentInSelect += "<option value=\"" + item.positionId + "\">" + item.positionName + "</option>";
				});
				$("#buildingSelector").html(contentInSelect);
			}
		});
	}
	
	function getFloors() {
		var buildingId = $("#buildingSelector").val();
		$.ajax({
			type:"post",
			url:"map.do?floor",
			timeout: 3000,
			dataType:"text",
			async:true,
			data:{buildingId:buildingId},
			success:function(data) {
				var d = $.parseJSON(data);
				var contentInSelect = "<option value=\"\" selected=\"selected\">总览</option>";
				$.each(d, function(i, item) {
					contentInSelect += "<option value=\"" + item.positionId + "\">" + item.positionName + "</option>";
				});
				$("#floorSelector").html(contentInSelect);
			}
		});
		floorChanged();  //每次获取楼层时刷新
	}
	
	function getPersonNumber() {
		var buildingId = $("#buildingSelector").val();
		var floorId = $("#floorSelector").val();
		$.ajax({
			type:"post",
			url:"currlocation.do?locationNumber",
			timeout: 3000,
			dataType:"text",
			async:true,
			data:{buildingId:buildingId, floorId:floorId},
			success:function(data) {
				$("#personNumberSpan").html(data);
			}
		});
	}
	
	function floorChanged() {
		getPersonNumber();
		setTimeout(getMapInfo, 100);
	}
	
	var haveMapInfo = false;  //是否有地图信息
	var paintarea;  //绘图画布对象
	
	function getMapInfo() {
		var buildingId = $("#buildingSelector option:selected").val();
		var floorId = $("#floorSelector option:selected").val();
		var positionId = null;
		if (floorId != null && floorId != "") {
			positionId = floorId;
		} else if (buildingId != null && buildingId != "") {
			positionId = buildingId;
		}
		
		if (positionId != null) {  //posId不为null的情况下，获取地图等信息
			$.ajax({
				type:"post",
				url:"map.do?mapinfo",
				timeout: 3000,
				dataType:"text",
				async:true,
				data:{positionId:positionId},
				success:function(data) {
					var d = $.parseJSON(data);
					if (d.mapurl != null && d.mapurl != "") {  //地图存在的情况
						$("#mapcontainer").html("<img id=\"oImg\" src=\"" + d.mapurl + "\" alt=\"地图文件丢失\"/>");
						setTimeout(function() {
							$("#mapcontainer").css("width", $("#oImg").width());
							$("#mapcontainer").css("height", $("#oImg").height());
							$("#paintcontainer").css("width", $("#oImg").width());
							$("#paintcontainer").css("height", $("#oImg").height());
							paintarea = Raphael("paintcontainer");
						}, 100);
						haveMapInfo = true;
					} else {  //地图不存在的情况
						if (floorId != null && floorId != "") {  //指定楼层的情况，显示地图未配置
							$("#mapcontainer").html("<p class=\"nomaphint\">未配置地图信息</p>");
						} else {  //未指定楼层的情况，显示各楼层详细信息
							$("#mapcontainer").html("");
							getPositionDetail(buildingId);
						}
						haveMapInfo = false;
					}
				}
			});
		} else {
			getPositionDetail("0");
		}
	}
	
	function getPositionDetail(buildingId) {
		$.ajax({
			type:"post",
			url:"map.do?buildingdetail",
			timeout: 3000,
			dataType:"text",
			async:true,
			data:{buildingId:buildingId},
			success:function(data) {
				var tableinfos = $.parseJSON(data);
				var htmlcode = "<table class=\"personDetailTable\"><tr><td class=\"col1\"><span>大楼名称</span></td><td class=\"col2\"><span>在线人数</span></td></tr>";
				$.each(tableinfos, function(i, item) {
					htmlcode += "<tr><td class=\"col1\"><span>" + item.positionName + "</span></td><td class=\"col2\"><span>" + item.personNumber + "</span></td></tr>";
				});
				htmlcode += "</table>";
				$("#mapcontainer").html(htmlcode);
				$("#paintcontainer").html("");
				$("#paintcontainer").css("width", "0px");
				$("#paintcontainer").css("height", "0px");
			}
		});
	}
	
	var lastPointState = "";
	
	function getPointDetail() {
		if (haveMapInfo) {  //仅当存在地图信息的情况下，有意义
			var buildingId = $("#buildingSelector option:selected").val();
			var floorId = $("#floorSelector option:selected").val();
			var positionId = null;
			if (floorId != null && floorId != "") {
				positionId = floorId;
			} else if (buildingId != null && buildingId != "") {
				positionId = buildingId;
			} else {
				return;
			}
			$.ajax({
				type: "post",
				url: "map.do?loc",
				timeout: 3000,
				dataType: "text",
				async: true,
				data: {positionId:positionId,lastState:lastPointState},
				success: function(data) {
					var d = $.parseJSON(data);
					lastPointState = JSON.stringify(d.state);  //将当前状态保存
					//清除画布
					paintarea.clear();
					//初始状态
					if (d.begin != null) {
						$.each(d.begin, function(i, item) {
							paintarea.circle(item.x, item.y, 8).attr({stroke:"none",fill:"#F22"});
							paintarea.text(item.x, item.y - 15, item.text).attr({"font-size": 16});
						});
					}
					//动画要播放的
					if (d.animate != null && d.animate.length > 0) {
						$.each(d.animate, function(i, item) {
							//动画
							if (item.pathLength != null && item.pathLength > 0) {  //pathLength大于0
								var point;
								var pointtext;
								var pointxJSON = "{";
								var pointyJSON = "{";
								var textxJSON = "{";
								var textyJSON = "{";
								$.each(item.path, function (j, subpath) {
									if (j == 0) {
										point = paintarea.circle(subpath.x, subpath.y, 8).attr({stroke:"none",fill:"#F22"});
										pointtext = paintarea.text(subpath.x, subpath.y - 15, item.text).attr({"font-size": 16});
									}
									var indexvalue = Number((j + 1) / item.pathLength).toFixed(2);
									pointxJSON += indexvalue + ":" + "{\"cx\":" + subpath.x + "},";
									pointyJSON += indexvalue + ":" + "{\"cy\":" + subpath.y + "},";
									textxJSON += indexvalue + ":" + "{\"x\":" + subpath.x + "},";
									textyJSON += indexvalue + ":" + "{\"y\":" + (subpath.y - 15) + "},";
								});
								pointxJSON += "}";
								pointyJSON += "}";
								textxJSON += "}";
								textyJSON += "}";
								var pointxObj = eval('('+ pointxJSON + ')');
								var pointyObj = eval('('+ pointyJSON + ')');
								var textxObj = eval('('+ textxJSON + ')');
								var textyObj = eval('('+ textyJSON + ')');
								point.stop().animate(pointxObj, 3000).animate(pointyObj, 3000);
								pointtext.stop().animate(textxObj, 3000).animate(textyObj, 3000);
							}
						});
						//末尾状态
						setTimeout(function() {
							//清除画布
							paintarea.clear();
							//末尾状态
							if (d.end != null) {
								$.each(d.end, function(i, item) {
									paintarea.circle(item.x, item.y, 8).attr({stroke:"none",fill:"#F22"});
									paintarea.text(item.x, item.y - 15, item.text).attr({"font-size": 16});
								});
							}
						}, 3000);  //动画播放完成后，切换为末态
					}
				}
			});
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
          <li id="Caution2" onclick="self.location='map.do?show'" class="hoverclicked" style="cursor: pointer;">园区地图</li>
          <li id="Caution2" onclick="self.location='map.do?outdoor'" style="cursor: pointer;">室外地图</li>
        </ul>
        
        <div id="distribution_1">
          <div class="distributionBanner" style="background-color:#2e94e3;text-align: left;">
            <span class="fontintitle" style="margin-left:48px;">当前显示大楼</span>
            <select id="buildingSelector" class="mapposselector" onchange="getFloors()">
              <option value="" selected="selected">总览</option>
            </select>
            <span class="fontintitle" style="margin-left:20px;">当前显示楼层</span>
            <select id="floorSelector" class="mapposselector" onchange="floorChanged()">
              <option value="" selected="selected">总览</option>
            </select>
            <span class="fontintitle" style="margin-left:200px;">当前位置在线人数</span>
            <span id="personNumberSpan"></span>
            <span class="fontintitle">人</span>
          </div>
          <div style="position: relative;overflow: auto; height: 705px;">
            <div style="z-index: 2;top: 0px;left: 0px;position: absolute;width: 1132px; height: 665px;" id="mapcontainer">
              <!-- 
              <img id="oImg" src="./images/map2.png" alt="地图文件丢失" onclick="getMousePos(event)"/>
               -->
               <!-- 
               <table class="personDetailTable">
                 <tr>
                   <td class="col1">
                     <span>大楼名称</span>
                   </td>
                   <td class="col2">
                     <span>在线人数</span>
                   </td>
                 </tr>
               </table>
                -->
            </div>
            <div style="z-index: 3;position: absolute;" id="paintcontainer">
            </div>
          </div>
        </div>
      </div><!-- 地图结束 -->
    </div>
    
    <div class="footer">
      <p>Copyright&nbsp;</p><span>©</span><p>&nbsp;2015 苏州摩多物联科技有限公司 All rights reserved. 版权所有</p>
    </div>
  </body>
</html>
