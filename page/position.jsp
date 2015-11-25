<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String username = (String)session.getAttribute("username");
%>

<!DOCTYPE>
<html>
  <head>
    <title>设备分布</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="style/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="js/jquery-1.7.min.js"></script>
    <script type="text/javascript" src="js/tipswindown.js"></script>
    <style type="text/css">
    .addLocation input:nth-child(5){background:url(./images/button2.png) no-repeat;border:0;width:80px;height:30px;color:#fff;}
    .addLocation input:nth-child(5):hover{background:url(./images/button3.png)}
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
		getCurrentAlarmInfo();
		if ($("#currentPositionId").val() != null && $("#currentPositionId").val() != "") {
			getPositionInfo($("#currentPositionId").val());
		} else {
			getPositionInfo('0');
		}
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
	
	function getPositionInfo(positionId) {
		$.ajax({
			type:"post",
			url:"position.do?position",
			timeout: 3000,
			dataType:"text",
			async:false,
			data:{positionId:positionId},
			success:function(data) {
				var d = $.parseJSON(data);
				
				$("#currentPositionId").val(d.parentPosition.positionId);
				$("#addDistributionLocation").html(d.parentPosition.positionName);
				
				//根位置是虚拟位置，不允许配置地图
				if ($("#currentPositionId").val() != null && $("#currentPositionId").val() != "" && $("#currentPositionId").val() != "0") {
					$("#mapconfigbutton").css("display", "inline");
				} else {
					$("#mapconfigbutton").css("display", "none");
				}
				
				var html2top = "";  //位置导航栏
				$("#pagetop").html(html2top);
				if (d.parents != null && d.parents.length > 0) {
					html2top = "<li onclick=\"getPositionInfo('0');\" style=\"cursor: pointer;\">所有位置</li>";
					$.each(d.parents, function(i, item) {
						if (i == d.parents.length - 1) {
							html2top += "<li style=\"cursor: pointer;\" onclick=\"getPositionInfo('" + item.positionId + "')\" class=\"hoverclicked\">" + item.positionName + "</li>";
						} else {
							html2top += "<li style=\"cursor: pointer;\" onclick=\"getPositionInfo('" + item.positionId + "')\">" + item.positionName + "</li>";
						}
					});
				} else {
					html2top = "<li onclick=\"getPositionInfo('0');\" style=\"cursor: pointer;\" class=\"hoverclicked\">所有位置</li>";
				}
				$("#pagetop").html(html2top);
				
				var html2content = "";  //位置列表
				$("#distributionDetail_building").html(html2content);
				$.each(d.positionInfos, function(i, item) {
					html2content += "<div class=\"images-wrapper\">";
					html2content += "<img src=\"images/building.png\" alt=\"" + item.positionName + "\" title=\"" + item.positionName + "\" onclick=\"getPositionInfo('" + item.positionId + "');\"/>";
					html2content += "<div class=\"images-delete\" onclick=\"delposition('" + item.positionId + "');\"></div>";
					html2content += "<div class=\"images-content\">";
					html2content += "<input value=\"" + item.positionName + "\" maxlength=\"200\" onchange=\"updatePosition('" + item.positionId + "', this.value)\" onkeydown=\"if(event.keyCode==13){updatePosition('" + item.positionId + "', this.value);)}\"/>";
					html2content += "<p class=\"input-tips\">提示:点击位置名称可进行重命名!</p>";
					html2content += "</div>";
					html2content += "</div>";
				});
				$("#distributionDetail_building").html(html2content);
				
				var html2dev = "";  //当前位置设备列表
				$("#devContent").html(html2dev);
				$.each(d.devs, function(i, item) {
					html2dev += "<div>";
					html2dev += "<div class=\"yijianbaojing\" onclick=\"$(this).next().toggle(500)\">" + item.devType + "</div>";
					html2dev += "<div class=\"hahaha\" style=\"display: block;\">";
					$.each(item.devList, function(j, dev) {
						html2dev += "<div>";
						html2dev += "<p class=\"darkBlue\">" + dev.devName + "&nbsp;</p>";
						html2dev += "<p>设备类型：<span>" + dev.devType + "</span></p>";
						html2dev += "<p>MAC&nbsp;地址：<span>" + dev.devMac + "&nbsp;</span></p>";
						html2dev += "</div>";
					});
					html2dev += "</div>";
					html2dev += "</div>";
				});
				$("#devContent").html(html2dev);
			}
		});
	}
	
	function checkPositionInsert() {
		var posname = $("#newpositionname").val();
		if (posname == null || posname == "") {
			alert("请输入位置名称");
			return false;
		} else {
			doAddPosition();
		}
	}
	
	function doAddPosition() {
		var parentid = $("#currentPositionId").val();
		var positionname = $("#newpositionname").val();
		$.ajax({
			type:"post",
			url:"position.do?add",
			timeout: 3000,
			dataType:"text",
			async:false,
			data:{parent:parentid,posname:positionname},
			success:function(data) {
				alert(data);
				if (data == "保存成功") {
					$("#newpositionname").val("");
					getPositionInfo(parentid);
				}
			}
		});
	}
	
	function delposition(posid) {
		document.getElementById("positionid2del").value = posid;
		showTipsWindown('删除位置','deleteUser',290,135);
	}
	
	function delPosInfo() {
		var posid = $("#positionid2del").val();
		$.ajax({
			type:"post",
			url:"position.do?del",
			timeout: 3000,
			dataType:"text",
			async:false,
			data:{positionId:posid},
			success:function(data) {
				alert(data);
				if (data == "删除成功") {
					var parentid = $("#currentPositionId").val();
					closeIt();
					getPositionInfo(parentid);
				}
			}
		});
	}
	
	function updatePosition(id, name) {
		if (name == null || name == "") {
			alert("位置名称不能为空");
		} else {
			$.ajax({
				type:"post",
				url:"position.do?update",
				timeout: 3000,
				dataType:"text",
				async:false,
				data:{positionId:id,positionName:name},
				success:function(data) {
					if (data == "更新成功") {
						var parentid = $("#currentPositionId").val();
						closeIt();
						getPositionInfo(parentid);
					} else {
						alert(data);
					}
				}
			});
		}
	}
	
	function getParentDevInfo(devname, pageNumber) {
		$("#devselectbox").attr("value", devname);
		$.ajax({
			type:"post",
			url:"dev.do?devNotInstall",
			timeout: 3000,
			dataType:"text",
			async:false,
			data:{devname:devname, pageNumber:pageNumber},
			success:function(data) {
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
		showTipsWindown('设备安装','equipmentBelong',590,480);
	}
	
	function selectDev(devId, devName) {
		var positionId = $("#currentPositionId").val();
		$.ajax({
			type:"post",
			url:"dev.do?installDev",
			timeout: 3000,
			dataType:"text",
			async:false,
			data:{devId:devId, positionId:positionId},
			success:function(data) {
				alert(data);
				var parentid = $("#currentPositionId").val();
				closeIt();
				getPositionInfo(parentid);
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
        
        <div id="deleteUser" ><!--删除位置弹出框开始-->
          <div class="deleteUser-content">
            <p style="line-height:50px;font-size:18px;">确定删除此位置？</p>
            <p style="line-height:20px;">子位置、设备位置属性等也将被清除</p>
            <input type="hidden" id="positionid2del" name="positionid2del">
            <input type="button" value="确定" onClick="delPosInfo();" class="firstButton" style="margin-top:15px;margin-left:40px;"/>
            <input type="button" value="取消" onClick="closeIt();" style="margin-top:15px;margin-left:10px;"/>
          </div>
        </div><!--删除结束-->
        
        <div id="equipmentBelong" ><!--安装设备开始-->
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
        </div><!--安装设备结束-->
      </div>
      
      <div id="con_one_4">  <!-- 设备分布开始 -->
        <ul class="Contentheader" id="pagetop">
          <!-- 位置导航栏信息 -->
        </ul>
        
        <div id="distribution_1">
          <div class="distributionList">
            <div class="distributionBanner" style="background-color:#2e94e3;">位置列表</div>
            <div class="distributionDetail">  <!-- 此处位置信息详细 -->
              <div id="distributionDetail_building">
                <!-- 位置单元 -->
              </div>
            </div>
            
            <div class="addLocation" style="left: 20px">
              设备位置添加
              <p style="text-indent: 15px;">
                在&nbsp;
                <span class="darkBlue" id="addDistributionLocation"></span>
                &nbsp;下&nbsp;添加&nbsp;
                <input id="newpositionname" name="name" maxlength="200" onkeydown="if(event.keyCode==13){checkPositionInsert();}">
                &nbsp;位置名称（<label style="color:red;">*</label>必填）&nbsp;&nbsp;
                <input type="button" onclick="checkPositionInsert();" value="确定"/>
                &nbsp;&nbsp;
                <input type="button" value="取消" onclick="$('#newpositionname').val('');"/>
              </p>
            </div>
          </div>
          
          <div class="currentDistribution">
            <div class="distributionBanner" style="background-color:#34a1f0;">当前位置设备信息</div>
            <div class="currentDistributionContent" id="devContent" style="height: 640px;">
              <!-- 各类设备 -->
            </div>
            <div class="newEquipment" style="text-align: center;">
              <input type="button" value="安装设备" onclick="getParentDevInfo('', '1');" style="margin-top: 22px; float: none;"/>
              <input id="mapconfigbutton" type="button" value="配置地图" onclick="self.location='mapconfig.do?show&positionId=' + $('#currentPositionId').val();" style="margin-left: 15px; margin-top: 22px; float: none;display:none;"/>
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
