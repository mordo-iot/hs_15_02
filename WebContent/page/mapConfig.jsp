<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String username = (String)session.getAttribute("username");
%>

<!DOCTYPE>
<html>
  <head>
    <title>地图配置</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="style/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="js/jquery-1.7.min.js"></script>
    <script type="text/javascript" src="js/tipswindown.js"></script>
    <script type="text/javascript" src="js/jquery.form.js"></script>
    <script src="./js/raphael.js"></script>
    <style type="text/css">
      #nomapSpan {
        position: absolute;
        top: 300px;
        font-weight: bold;
        font-size: 24px;
        text-align: center;
        width: 1149px;
      }
      
      .mapButton {
        background: transparent url("./images/button.png") no-repeat scroll 0% 0%;
        border: 0px none;
        width: 100px;
        height: 30px;
        margin-top: 7px;
        margin-left: 10px;
        color: #FFF;
        font-size: 16px;
        font-family: "Microsoft YaHei" !important;
        vertical-align: middle;
        outline: medium none;
        cursor: pointer;
      }
      
      .mapButton:hover {
        background:url(./images/button1.png) no-repeat;
      }
      
      .mapButton2 {
        background: transparent url("./images/button-1.png") no-repeat scroll 0% 0%;
        border: 0px none;
        width: 50px;
        height: 30px;
        margin-top: 7px;
        margin-left: 5px;
        color: #FFF;
        font-size: 16px;
        font-family: "Microsoft YaHei" !important;
        text-align: center;
        vertical-align: middle;
        outline: medium none;
        cursor: pointer;
      }
      
      .mapButton2:hover {
        background: transparent url("./images/button-2.png") no-repeat scroll 0% 0%;
      }
    </style>
  </head>

  <script type="text/javascript">
	var haveMap = false;  //判断页面时否有地图
	var pointMode = 0;  //点编辑模式，0-关闭，1-添加，2-删除
	var pathMode = 0;  //线编辑模式，0-关闭，1-添加，2-删除

	function showTipsWindown(title, id, width, height) {
		tipsWindown(title, "id:" + id, width, height, "true", "", "true", id);
	}
	
	function closeIt() {
		parent.closeWindown();
	}
	
	$(document).ready(function() {
		$("#mapoperbutton").val("选择地图");
		$("#mapoperbutton").bind("click", showMapSelectorMenu);
		getCurrentAlarmInfo();
		if ($("#currentPositionId").val() != null && $("#currentPositionId").val() != "") {
			getPositionInfo($("#currentPositionId").val());
		} else {
			getPositionInfo('0');
		}
		setInterval(getCurrentAlarmInfo, 5000);  //每隔一段时间获取一次报警数据，看看有没有新报警
		
		var options = {
			type:"post",
			url:"mapconfig.do?pic"
		};
		$("#picuploadform").ajaxForm(options);
		$("#addpoint").bind("click", pointAddButtonAction);
		$("#bindpoint").bind("click", pointBindButtonAction);
		$("#delpoint").bind("click", pointDelButtonAction);
		$("#addpath").bind("click", pathAddButtonAction);
		$("#delpath").bind("click", pathDelButtonAction);
	});
	
	var alarmHash = "";
	
	function getCurrentAlarmInfo() {
		$.ajax({
			type:"post",
			url:"currentAlarm.do?show",
			timeout: 3000,
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
	
	var paintarea;
	
	function getPositionInfo(positionId) {
		$.ajax({
			type:"post",
			url:"mapconfig.do?positionmap",
			timeout: 3000,
			dataType:"text",
			async:false,
			data:{positionId:positionId},
			success:function(data){
				var d = $.parseJSON(data);
				
				$("#currentPositionId").val(d.parentPosition.positionId);
				$("#addDistributionLocation").html(d.parentPosition.positionName);
				if (d.parentPosition.photo != null && d.parentPosition.photo != "") {
					haveMap = true;
					initMapContainer(d.parentPosition.photo);
				}
				
				var html2top = "";  //导航栏
				$("#pagetop").html(html2top);
				if (d.parents != null && d.parents.length > 0) {
					html2top = "<li onclick=\"go2Position('0');\" style=\"cursor: pointer;\">所有位置</li>";
					$.each(d.parents, function(i, item) {
						if (i == d.parents.length - 1) {
							html2top += "<li style=\"cursor: pointer;\" onclick=\"go2Position('" + item.positionId + "')\" class=\"hoverclicked\">" + item.positionName + "</li>";
						} else {
							html2top += "<li style=\"cursor: pointer;\" onclick=\"go2Position('" + item.positionId + "')\">" + item.positionName + "</li>";
						}
					});
				} else {
					html2top = "<li onclick=\"go2Position('0');\" style=\"cursor: pointer;\" class=\"hoverclicked\">所有位置</li>";
				}
				$("#pagetop").html(html2top);
				
				setTimeout(function() {
					//绘制路径
					if (d.road != null && d.road != "") {
						$.each(d.road, function(i, item) {
							var lineString = "M" + item.x1 + " " + item.y1 + "L" + item.x2 + " " + item.y2;
							paintarea.path(lineString).attr({stroke: "#F22","stroke-width":3});
						});
					}
					//绘制定位点
					if (d.locate != null && d.locate != "") {
						$.each(d.locate, function(i, item) {
							var temp = paintarea.image("images/position-point1.png", item.x - 6, item.y - 6, 12, 12);
							$("#paintcontainer image").each(function() {
								if ($(this).attr("x") == item.x - 6 && $(this).attr("y") == item.y - 6) {
									$(this).attr("posId", item.posId);
								}
							});
							paintarea.text(item.x, item.y - 15, item.posName);
						});
					}
					//绘制中间点
					if (d.temploc != null && d.temploc != "") {
						$.each(d.temploc, function(i, item) {
							paintarea.image("images/position-point1.png", item.x - 6, item.y - 6, 12, 12);
						});
					}
				}, 200);
			}
		});
	}
	
	function initMapContainer(imgpath) {
		$("#mapcontainer").html("<img id=\"oImg\" src=\"" + imgpath + "\" alt=\"地图文件丢失\"/>");
		setTimeout(function() {
			$("#paintcontainer").html("");
			$("#mapoperbutton").val("清除地图");
			$("#mapoperbutton").unbind("click", showMapSelectorMenu);
			$("#mapoperbutton").bind("click", clearMap);
			
			$("#mapcontainer").css("width", $("#oImg").width());
			$("#mapcontainer").css("height", $("#oImg").height());
			$("#paintcontainer").css("width", $("#oImg").width());
			$("#paintcontainer").css("height", $("#oImg").height());
			
			paintarea = Raphael("paintcontainer");
		}, 100);
	}
	
	function clearMap() {
		var positionId = $("#currentPositionId").val();
		$.ajax({
			type:"post",
			url:"mapconfig.do?delmap",
			timeout: 3000,
			dataType:"text",
			async:false,
			data:{positionId:positionId},
			success:function(data) {
				if (data == "操作成功") {
					$("#mapcontainer").html("<span id=\"nomapSpan\">未配置地图信息</span>");
					$("#paintcontainer").html("");
					$("#mapcontainer").css("width", "1132px");
					$("#mapcontainer").css("height", "665px");
					$("#paintcontainer").css("width", "0px");
					$("#paintcontainer").css("height", "0px");
					$("#mapoperbutton").val("选择地图");
					$("#mapoperbutton").bind("click", showMapSelectorMenu);
				} else {
					alert(data);
				}
			}
		});
	}
	
	function go2Position(positionId) {
		self.location = "position.do?show&positionId=" + positionId;
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
	
	function showMapSelectorMenu() {
		showTipsWindown('选择地图底图', 'mappicselector', 340, 100);
	}
	
	function uploadPic() {
		var positionId = $("#currentPositionId").val();
		var ajax_option = {
			async:false,
			data:{currentpos:positionId},
			success:function(data) {
				if (data != null && data != "") {
					haveMap = true;
					initMapContainer(data);  //用地图图片路径初始化地图容器
					closeIt();
				} else {
					alert(data);
				}
			}
		};
		$("#windown-box").find('form[id="picuploadform"]').ajaxSubmit(ajax_option);
	}
	
	//绘点函数
	function drawPoint(event) {
		var e = event || window.event;
        var scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
        var scrollY = document.documentElement.scrollTop || document.body.scrollTop;
        var x = e.pageX || e.clientX + scrollX;
        var y = e.pageY || e.clientY + scrollY;
        x = x - 332;
        y = y - 238;
        //paintarea.circle(x, y, 10).attr({stroke: "#f22", "stroke-width": 4});
        //x = x - 6 - 332;
        //y = y - 28 - 238;
        //paintarea.image("images/locatePoint.png", x, y, 12, 28);
        x = x - 6;
        y = y - 6;
        paintarea.image("images/position-point1.png", x, y, 12, 12);
	}
	
	function finishButtonActionState() {
		//结束点添加状态
		if ($("#addpoint").val() == "完成") {
			pointAddButtonAction();
		}
		
		//结束点绑定状态
		if ($("#bindpoint").val() == "完成") {
			pointBindButtonAction();
		}
		
		//结束点删除状态
		if ($("#delpoint").val() == "完成") {
			pointDelButtonAction();
		}
		
		//结束绘线状态
		if ($("#addpath").val() == "完成") {
			pathAddButtonAction();
		}
		
		//结束删线状态
		if ($("#delpath").val() == "完成") {
			pathDelButtonAction();
		}
	}
	
	//绘点按钮点击事件
	function pointAddButtonAction() {
		if ($("#addpoint").val() == "添加") {
			finishButtonActionState();
			
			$("#addpoint").val("完成");
			$("#paintcontainer").bind("click", drawPoint);
		} else {
			$("#addpoint").val("添加");
			$("#paintcontainer").unbind("click", drawPoint);
		}
	}
	
	//点绑定位置事件
	function pointBindButtonAction() {
		if ($("#bindpoint").val() == "绑定") {
			finishButtonActionState();
			
			$("#bindpoint").val("完成");
			$("#paintcontainer image").each(function() {
				$(this).bind("click", bindPosition);
			});
		} else {
			$("#bindpoint").val("绑定");
			$("#paintcontainer image").each(function() {
				$(this).unbind("click", bindPosition);
			});
		}
	}
	
	var currBindTar = null;
	
	//点绑定位置事件
	function bindPosition(event) {
		currBindTar = event.target;
		showTipsWindown('定位点位置绑定', 'installationLocation', 590, 480);
		getPosition4bind(1);
	}
	
	function getPosition4bind(pageNumber) {
		var parentPosition = $("#currentPositionId").val();
		$.ajax({
			type:"post",
			url:"mapconfig.do?choose",
			timeout: 3000,
			dataType:"text",
			async:false,
			data:{parentPosition:parentPosition, pageNumber:pageNumber},
			success:function(data) {
				var info = $.parseJSON(data);
				var html2append = "<tr><th>位置</th><th>操作</th></tr>";
				$.each(info.list, function(i, item) {
					html2append += "<tr><td>" + item.positionName + "&nbsp;</td>";
					if (item.positionId == 0) {
						html2append += "<td>&nbsp;</td></tr>";
					} else {
						html2append += "<td><input type=\"button\" value=\"选择\" onclick=\"selectPostion('" + item.positionId + "','" + item.positionName + "')\"></td></tr>";
					}
				});
				$("#windown-box").find('table[id="positionselecttable"]').html(html2append);
				
				$("#windown-box").find('b[id="positionselecttable"]').html("" + info.totalpage);
				$("#windown-box").find('b[id="totalpositionitems"]').html("" + info.totalitem);
				$("#windown-box").find('div[id="positionselectpagin"]').html(info.paginhtml);
			}
		});
	}
	
	function selectPostion(posId, posName) {
		if (currBindTar != null) {
			$(currBindTar).attr("posId", posId);
			var x = $(currBindTar).attr("x") - (-6);
			var y = $(currBindTar).attr("y") - 15;
			$("#paintcontainer text").each(function() {
				if ($(this).attr("x") == x && $(this).attr("y") == y) {
					$(this).remove();
				}
			});
			paintarea.text(x, y, posName);
		}
		closeIt();
	}
	
	//删点按钮点击事件
	function pointDelButtonAction() {
		if ($("#delpoint").val() == "删除") {
			finishButtonActionState();
			
			$("#delpoint").val("完成");
			//$("#paintcontainer circle").each(function() {}
			$("#paintcontainer image").each(function() {
				$(this).click(function() {
					//解绑事件
					$(this).unbind();
					
					//删除点相关的线
					var point = "#" + ($(this).attr("x") - (-6)) + "," + ($(this).attr("y") - (-6)) + "#";
					$("#paintcontainer path").each(function() {
						lineString = "#" + $(this).attr("d") + "#";
						lineString = lineString.replace("M", "#").replace("L", "#");
						if (lineString.indexOf(point) > 0) {
							$(this).remove();
						} else {
							alert(lineString);
						}
					});
					
					//删除相关的文本
					if ($(this).attr("posId") != null && $(this).attr("posId") != "") {
						var x = $(this).attr("x") - (-6);
						var y = $(this).attr("y") - 15;
						$("#paintcontainer text").each(function() {
							if ($(this).attr("x") == x && $(this).attr("y") == y) {
								$(this).remove();
							}
						});
					}
					
					//删除点
					$(this).remove();
				});
			});
		} else {
			$("#delpoint").val("删除");
			//$("#paintcontainer circle").each(function() {}
			$("#paintcontainer image").each(function() {
				$(this).unbind();
			});
		}
	}
	
	//绘线按钮点击事件
	function pathAddButtonAction() {
		if ($("#addpath").val() == "绘制") {
			finishButtonActionState();
			
			$("#addpath").val("完成");
			$("#paintcontainer image").each(function() {
				$(this).bind("click", drawline);
			});
		} else {
			$("#addpath").val("绘制");
			point1 = null;
			$("#paintcontainer image").each(function() {
				$(this).unbind("click", drawline);
			});
		}
	}
	
	var point1 = null;
	
	//画线，选定点一，点二
	function drawline(event) {
		if (point1 == null) {  //第一点未选定时
			point1 = event.target;
		} else {  //第一点已选定时
			if (point1 == event.target) {  //第二点还是第一点——取消第一点的选择
				point1 = null;
			} else {
				var lineString = "M" + ($(point1).attr("x") - (-6)) + " " + ($(point1).attr("y") - (-6)) + "L" + ($(event.target).attr("x") - (-6)) + " " + ($(event.target).attr("y") - (-6));
				path = paintarea.path(lineString).attr({stroke: "#F22","stroke-width":3});
				path.toBack();
				point1 = null;
			}
		}
		//$(event.target).attr("posId", "20");
		//alert($(event.target).attr("posId"));
	}
	
	//删线按钮点击事件
	function pathDelButtonAction() {
		if ($("#delpath").val() == "删除") {
			finishButtonActionState();
			
			$("#delpath").val("完成");
			$("#paintcontainer path").each(function() {
				$(this).bind("click", delline);
			});
		} else {
			$("#delpath").val("删除");
			$("#paintcontainer path").each(function() {
				$(this).unbind("click", delline);
			});
		}
	}
	
	//点击线，删除线
	function delline(event) {
		$(event.target).unbind();
		$(event.target).remove();
	}
	
	//保存当前配置信息
	function saveMapInfos() {
		//遍历所有点
		var locate = "[";
		$("#paintcontainer image").each(function() {
			if ($(this).attr("posId") != null && $(this).attr("posId") != "") {
				locate += "{\"posId\":" + $(this).attr("posId") + ",\"x\":" + ($(this).attr("x") - (-6)) + ",\"y\":" + ($(this).attr("y") - (-6)) + "},";
			}
		});
		locate += "]";
		
		//遍历所有线
		var road = "[";
		$("#paintcontainer path").each(function() {
			var drawString = $(this).attr("d");
			drawString = drawString.replace("M", "").replace("L", ",");
			var arr = drawString.split(",");
			if (arr.length == 4) {
				road += "{\"x1\":" + arr[0] + ",\"y1\":" + arr[1] + ",\"x2\":" + arr[2] + ",\"y2\":" + arr[3] + "},";
			}
		});
		road += "]";
		
		var positonId = $("#currentPositionId").val();
		$.ajax({
			type:"post",
			url:"mapconfig.do?saveConfig",
			timeout: 3000,
			dataType:"text",
			async:false,
			data:{mapId:positonId, locate:locate, road:road},
			beforeSend:function() {
				showTipsWindown('', 'savinghint', 340, 100);
			},
			complete:closeIt,
			success:function(data) {
				closeIt();
				alert(data);
				/*if (data == "保存成功") {
					closeIt();
					
				} else {
					alert(data);
				}*/
			}
		});
	}
  </script>
  
  <body>
    <!-- http://localhost:8080/hs_15_02/position.do?show&positionId=24 -->
    <input type="hidden" id="currentPositionId" name="currentPositionId" value="<%=request.getAttribute("positionId") %>">
    
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
        <li onclick="self.location='map.do?show'" ><img src="images/map.png"/>地图</li>
        <li onclick="self.location='dev.do?show'"><img src="images/equipment.png"/>设备</li>
        <li onclick="self.location='position.do?show'" class="hover"><img src="images/equipment1.png"/>设备分布</li>
        <li onclick="self.location='user.do?list'"><img src="images/v-card.png"/>用户</li>
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
        
        <div id="mappicselector" ><!--地图图片选择弹出-->
          <form id="picuploadform" action="mapconfig.do?pic" method="post" enctype="multipart/form-data">
          <div class="editUser-content">
            <p style="margin-top: 25px;"><input id="pic4upload" name="pic4upload" type="file" style="background:none;"></p>
            <input type="button" value="上传" onClick="uploadPic();" style="margin-left: 60px;"/>
            <input type="button" value="取消" onClick="closeIt();" style="margin-left: 10px"/>
          </div>
          </form>
        </div><!--地图图片选择结束-->
        
        <div id="savinghint" ><!--地图配置保存中提示-->
          <form id="picuploadform" action="mapconfig.do?pic" method="post" enctype="multipart/form-data">
          <div class="editUser-content">
            <p style="margin-top: 20px;">地图信息保存中,请稍等</p>
            <input type="button" value="确定" onClick="closeIt();" style="margin-left: 124px; margin-top: 10px"/>
          </div>
          </form>
        </div><!--地图图片选择结束-->
        
        <div id="installationLocation"><!--设备安装位置开始-->
          <div class="six-six">
            <span style="margin-left: 80px;">设备位置选择:</span>
            <input id="positionsearchbox" placeholder="位置搜索" style="margin-left:30px;background: transparent url('./images/usernamebg.png') no-repeat scroll 0% 0%;" onkeydown="if(event.keyCode==13){closeIt();getPositionInfo4selector();}"/>
            <button type="button" class="newaddbutton" onclick="getPositionInfo4selector()">搜索</button>
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
      </div>
      
      <div id="con_one_4">  <!-- 设备分布开始 -->
        <ul class="Contentheader" id="pagetop">
          <!-- 位置导航栏信息 -->
        </ul>
        
        <div id="distribution_1">
          <div class="distributionBanner" style="background-color:#2e94e3;">当前位置地图配置</div>
          <div style="height: 40px;">
            <input id="mapoperbutton" type="button" class="mapButton" value="选择地图" style="margin-left: 40px;">
            <input id="drawpointbutton" type="button" class="mapButton" value="添加定位点">
            <input id="addpoint" type="button" class="mapButton2" value="添加">
            <input id="bindpoint" type="button" class="mapButton2" value="绑定">
            <input id="delpoint" type="button" class="mapButton2" value="删除">
            <input type="button" class="mapButton" value="绘制路径">
            <input id="addpath" type="button" class="mapButton2" value="绘制">
            <input id="delpath" type="button" class="mapButton2" value="删除">
            <input type="button" class="mapButton" value="配置路径" onclick="saveMapInfos();">
          </div>
          <div style="position: relative;overflow: auto; height: 665px;">
            <div style="z-index: 2;top: 0px;left: 0px;position: absolute;width: 1132px; height: 665px;" id="mapcontainer">
              <span id="nomapSpan">未配置地图信息</span>
              <!-- 
              <img id="oImg" src="./images/map2.png" alt="地图文件丢失" onclick="getMousePos(event)"/>
               -->
            </div>
            <div style="z-index: 3;position: absolute;" id="paintcontainer">
            </div>
          </div>
        </div>
      </div><!-- 设备分布结束 -->
    </div>
    
    <div class="footer">
      <p>Copyright&nbsp;</p><span>©</span><p>&nbsp;2015 苏州摩多物联科技有限公司 All rights reserved. 版权所有</p>
    </div>
  </body>
</html>
