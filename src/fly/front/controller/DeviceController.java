package fly.front.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.framework.system.db.query.OrderVO;
import com.framework.system.db.query.PageList;

import fly.entity.dev.DevEntity;
import fly.entity.devLeftJionMap.DevLeftJionMapEntity;
import fly.entity.devPosition.DevPositionEntity;
import fly.entity.historyBed.HistoryBedEntity;
import fly.entity.historyDoor.HistoryDoorEntity;
import fly.entity.historyIr.HistoryIrEntity;
import fly.entity.historyKeyalarm.HistoryKeyalarmEntity;
import fly.entity.historyLocation.HistoryLocationEntity;
import fly.entity.historyLocationBody.HistoryLocationBodyEntity;
import fly.entity.historyLocationManual.HistoryLocationManualEntity;
import fly.entity.historyLocationMove.HistoryLocationMoveEntity;
import fly.entity.historyTizhengAll.HistoryTizhengAllEntity;
import fly.entity.historyTizhengBed.HistoryTizhengBedEntity;
import fly.entity.historyUrine.HistoryUrineEntity;
import fly.entity.historyWandai.HistoryWandaiEntity;
import fly.entity.position.PositionEntity;
import fly.front.entity.DeviceInfo;
import fly.front.entity.HistoryData;
import fly.front.entity.HistoryDataResult;
import fly.front.tools.Datetools;
import fly.front.tools.DeviceTools;
import fly.service.dev.DevService;
import fly.service.devLeftJionMap.DevLeftJionMapService;
import fly.service.devPosition.DevPositionService;
import fly.service.historyBed.HistoryBedService;
import fly.service.historyDoor.HistoryDoorService;
import fly.service.historyIr.HistoryIrService;
import fly.service.historyKeyalarm.HistoryKeyalarmService;
import fly.service.historyLocation.HistoryLocationService;
import fly.service.historyLocationBody.HistoryLocationBodyService;
import fly.service.historyLocationManual.HistoryLocationManualService;
import fly.service.historyLocationMove.HistoryLocationMoveService;
import fly.service.historyTizhengAll.HistoryTizhengAllService;
import fly.service.historyTizhengBed.HistoryTizhengBedService;
import fly.service.historyUrine.HistoryUrineService;
import fly.service.historyWandai.HistoryWandaiService;
import fly.service.position.PositionService;

@Scope("prototype")
@Controller
@RequestMapping("/dev")
public class DeviceController {

	@RequestMapping(params="show")
	public ModelAndView getDevList(HttpServletRequest request) {
		Integer role = (Integer) request.getSession().getAttribute("role");
		if (role == null || role != 1) {
			return new ModelAndView("currentAlarm");
		} else {
			return new ModelAndView("device");
		}
	}
	
	@RequestMapping(params="type")
	@ResponseBody
	public String getDevByType(HttpServletRequest request) {
		List<DeviceInfo> result = new ArrayList<DeviceInfo>();
		
		String devtype = request.getParameter("devtype");
		
		Integer typeid = 0;
		try {
			typeid = Integer.parseInt(devtype);
		} catch (NumberFormatException e) {
			
		}
		
		Map<String, Object> querymap = new HashMap<String, Object>();
		if (typeid > 0) {
			querymap.put("type", devtype);
		}
		
		List<Object> tempdev = DevService.getInstance().getListByCondition(querymap, null, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		if (tempdev != null && tempdev.size() > 0) {
			for (Object temp : tempdev) {
				DevEntity dev = (DevEntity) temp;
				if (dev != null) {
					DeviceInfo info = new DeviceInfo();
					info.setDevId(dev.getId());
					info.setDevName(dev.getName());
					info.setDevMac(dev.getCode());
					Integer devType = 0;
					try {
						devType = Integer.parseInt(dev.getType());
					} catch (NumberFormatException e) {
						
					}
					info.setDevType(DeviceTools.getDeviceTypeNameByTypeId(devType));
					
					if (dev.getPositionList() != null && dev.getPositionList().size() > 0) {
						PositionEntity pos = dev.getPositionList().get(0);
						if (pos != null) {
							info.setPosition(pos.getName());
						}
					}
					result.add(info);
				}
			}
		}
		
		String json2return = JSONArray.fromObject(result).toString();
		
		try {
			json2return = new String(json2return.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			
		}
		
		return json2return;
	}
	
	@RequestMapping(params="getdev")
	@ResponseBody
	public String getDeviceInfoById(HttpServletRequest request) {
		String deviceid = request.getParameter("deviceid");
		Integer devid = 0;
		try {
			devid = Integer.parseInt(deviceid);
		} catch (NumberFormatException e) {
			
		}
		
		DeviceInfo info;
		if (devid > 0) {
			DevService service = DevService.getInstance();
			DevEntity dev = service.getById(devid, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
			if (dev != null) {
				info = new DeviceInfo();
				info.setCardContent(dev.getAlarmcontent());
				info.setDevId(dev.getId());
				info.setDevMac(dev.getCode());
				info.setDevName(dev.getName());
				info.setDevType(dev.getType());
				info.setEboard(dev.getLightdevid());
				info.setEboardContent(dev.getLightno().toString());
				
				String alarmMacs = dev.getAlarmdevid();
				String ledmac = "";
				String doorlightmac = "";
				String alarmcardmac = "";
				
				if (alarmMacs != null && alarmMacs.length() > 0) {
					String[] templist = alarmMacs.split(",");
					if (templist != null && templist.length > 0) {
						for (String temp : templist) {
							if (temp != null && temp.length() > 0) {  //LED 0E000000-0EFFFFFF
								if (temp.matches("0?[eE][0-9a-fA-F]{6}")) {
									ledmac += temp + ",";
								} else if (temp.matches("0?[dD][0-9a-fA-F]{6}")) {  //门灯 0D000000-0DFFFFFF
									doorlightmac += temp + ",";
								} else if (temp.matches("0?[bB][0-9a-fA-F]{6}")) {  //胸卡 0B000000-0BFFFFFF
									alarmcardmac += temp + ",";
								}
							}
						}
					}
				}
				
				if (alarmcardmac.length() > 0) {
					alarmcardmac = alarmcardmac.substring(0, alarmcardmac.length() - 1);
				}
				if (ledmac.length() > 0) {
					ledmac = ledmac.substring(0, ledmac.length() - 1);
				}
				if (doorlightmac.length() > 0) {
					doorlightmac = doorlightmac.substring(0, doorlightmac.length() - 1);
				}
				
				info.setLed(ledmac);
				info.setDoorLight(doorlightmac);
				info.setAlarmCard(alarmcardmac);
				//以上 根据mac地址范围来区分设备MAC并赋值给正确的对象
				
				if (dev.getParentDev() != null) {
					info.setParentDevid(dev.getParentDev().getId());
					info.setAlarmDev(dev.getParentDev().getName());
				} else {
					info.setParentDevid(0);
					info.setAlarmDev("请选择");
				}
				
				if (dev.getAttribute() != null && dev.getAttribute().length() > 0) {
					JSONObject jso = null;
					try {
						jso = JSONObject.fromObject(dev.getAttribute());
					} catch (JSONException e) {
						
					}
					if (jso != null) {
						if (jso.containsKey("alarmstarttime")) {
							info.setStartTime(jso.getString("alarmstarttime"));
						} else {
							info.setStartTime("");
						}
						if (jso.containsKey("alarmendtime")) {
							info.setEndTime(jso.getString("alarmendtime"));
						} else {
							info.setEndTime("");
						}
						if (jso.containsKey("alarmdelay")) {
							info.setAlarmDelay(jso.getString("alarmdelay"));
						} else {
							info.setAlarmDelay("");
						}
					} else {
						info.setStartTime("");
						info.setEndTime("");
						info.setAlarmDelay("");
					}
				} else {
					info.setStartTime("");
					info.setEndTime("");
					info.setAlarmDelay("");
				}
				
				if (dev.getPositionList() != null && dev.getPositionList().size() > 0) {
					PositionEntity pe = dev.getPositionList().get(0);
					if (pe != null) {
						info.setPosition(pe.getName());
						info.setPositionId(pe.getId());
					} else {
						info.setPosition("请选择");
						info.setPositionId(0);
					}
				} else {
					info.setPosition("请选择");
					info.setPositionId(0);
				}
			} else {
				info = new DeviceInfo(0, "", "", "", "请选择", "请选择", "", "", "", "", "", "", "", "", "");
			}
		} else {
			info = new DeviceInfo(0, "", "", "", "", "请选择", "请选择", "", "", "", "", "", "", "", "");
		}
		
		String json2return = JSONObject.fromObject(info).toString();
		
		try {
			json2return = new String(json2return.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {

		}
		
		return json2return;
	}  
	
	@RequestMapping(params="save")
	@ResponseBody
	public String saveDeviceInfo(HttpServletRequest request) {
		String result = null;
		
		String devicename = request.getParameter("devicename");
		String devicetype = request.getParameter("devicetype");
		String devicemac = request.getParameter("devicemac");
		String devicepostionid = request.getParameter("devicepostionid");
		String parentdeviceid = request.getParameter("parentdeviceid");
		String alarmstarttime = request.getParameter("alarmstarttime");
		String alarmendtime = request.getParameter("alarmendtime");
		String alarmdelay = request.getParameter("alarmdelay");
		String alarmcardmac = request.getParameter("alarmcardmac");
		String doorlightmac = request.getParameter("doorlightmac");
		String ledmac = request.getParameter("ledmac");
		String eboardmac = request.getParameter("eboardmac");
		String eboardcontent = request.getParameter("eboardcontent");
		String alarmcardcontent = request.getParameter("alarmcardcontent");
		String deviceid = request.getParameter("deviceid");
		
		DevEntity dev = new DevEntity();
		
		Integer devid = 0;
		try {
			devid = Integer.parseInt(deviceid);
		} catch (NumberFormatException e) {
			
		}
		
		//校验设备类型以及mac地址是否符合格式
		Integer devtype = 0;
		try {
			devtype = Integer.parseInt(devicetype);
		} catch (NumberFormatException e) {
			
		}
		String checkresult = DeviceTools.checkDevMac(devtype, devicemac);
		
		if (checkresult != null) {
			result = checkresult;
		} else {
			//校验设备类型和mac地址是否有重复
			DevService service = DevService.getInstance();
			if (devicemac != null && devicemac.length() > 0) {
				Map<String, Object> querymap = new HashMap<String, Object>();
				querymap.put("type", devtype);
				querymap.put("code", devicemac);
				List<Object> devlist = service.getListByCondition(querymap);
				if (devlist != null && devlist.size() > 0) {
					for (Object obj : devlist) {
						DevEntity tempDev = (DevEntity) obj;
						if (tempDev != null) {
							if (devid > 0) {
								if (devid.intValue() != tempDev.getId().intValue()) {
									result = "MAC地址重复";
								} else {
									continue;
								}
							} else {
								result = "MAC地址重复";
							}
						}
					}
				}
			}
			
			if (result == null) {  //校验报警开始时间
				if (alarmstarttime != null && alarmstarttime.length() > 0) {
					if (!alarmstarttime.matches("[0-2][0-9]{3}")) {
						result = "报警开始时间应为时时分分四位数字";
					} else {
						try {
							Integer timevalue = Integer.parseInt(alarmstarttime);
							if (timevalue > 2400) {
								result = "报警开始时间不能晚于24时";
							}
						} catch (NumberFormatException e) {
							result = "报警开始时间应为时时分分四位数字";
						}
					}
				}
			}
			
			if (result == null) {  //校验报警结束时间
				if (alarmendtime != null && alarmendtime.length() > 0) {
					if (!alarmendtime.matches("[0-2][0-9]{3}")) {
						result = "报警结束时间应为时时分分四位数字";
					} else {
						try {
							Integer timevalue = Integer.parseInt(alarmendtime);
							if (timevalue > 2400) {
								result = "报警结束时间不能晚于24时";
							}
						} catch (NumberFormatException e) {
							result = "报警结束时间应为时时分分四位数字";
						}
					}
				}
			}
			
			if (result == null) {  //校验报警延迟时间
				if (alarmdelay != null && alarmdelay.length() > 0) {
					try {
						Integer timevalue = Integer.parseInt(alarmdelay);
						if (timevalue < 0) {
							result = "报警延迟时间不能为负";
						}
					} catch (NumberFormatException e) {
						result = "报警延迟时间应为数字";
					}
				}
			}
			
			//校验胸卡、门灯、led的MAC
			if (result == null) {
				int totallength = 0;
				if (alarmcardmac != null) {
					totallength += alarmcardmac.length();
				}
				if (doorlightmac != null) {
					if (totallength == 0) {
						totallength++;
					}
					totallength += doorlightmac.length();
				}
				if (ledmac != null) {
					if (totallength == 0) {
						totallength++;
					}
					totallength += ledmac.length();
				}
				if (totallength > 500) {
					result = "所属护工胸卡、门灯、LED屏，内容总长不能超过500";
				}
			}
			
			String alarmdevid = "";
			if (result == null) {
				if (alarmcardmac != null) {  //校验胸卡
					String[] alarmcardmacs = alarmcardmac.split(",");
					if (alarmcardmacs != null && alarmcardmacs.length > 0) {
						for (String mac : alarmcardmacs) {
							if (mac != null && mac.length() > 0) {
								checkresult = DeviceTools.checkDevMac(DeviceTools.hugongxiongpai, mac);
								if (checkresult != null) {
									result = checkresult;
									break;
								} else {
									alarmdevid += mac + ",";
								}
							}
						}
					}
				}
			}
			
			if (result == null) {  //校验门灯的MAC
				if (doorlightmac != null) {
					String[] doorlightmacs = doorlightmac.split(",");
					if (doorlightmacs != null && doorlightmacs.length > 0) {
						for (String mac : doorlightmacs) {
							if (mac != null && mac.length() > 0) {
								checkresult = DeviceTools.checkDevMac(DeviceTools.mendeng, mac);
								if (checkresult != null) {
									result = checkresult;
									break;
								} else {
									alarmdevid += mac + ",";
								}
							}
						}
					}
				}
			}
			
			if (result == null) {  //校验led的MAC
				if (ledmac != null) {
					String[] ledmacs = ledmac.split(",");
					if (ledmacs != null && ledmacs.length > 0) {
						for (String mac : ledmacs) {
							if (mac != null && mac.length() > 0) {
								checkresult = DeviceTools.checkDevMac(DeviceTools.led, mac);
								if (checkresult != null) {
									result = checkresult;
									break;
								} else {
									alarmdevid += mac + ",";
								}
							}
						}
					}
				}
			}
			
			if (result == null) {  //校验胸卡报警内容
				if (alarmcardcontent != null && alarmcardcontent.length() > 0) {
					if (!alarmcardcontent.matches("[0-9]{1,4}")) {
						result = "胸卡报警内容应为1~4位数字";
					}
				}
			}
			
			String alarmeboardid = "";
			if (result == null) {  //校验eboard
				if (eboardmac != null) {
					String[] eboardmacs = eboardmac.split(",");
					if (eboardmacs != null && eboardmacs.length > 0) {
						for (String mac : eboardmacs) {
							if (mac != null && mac.length() > 0) {
								checkresult = DeviceTools.checkDevMac(DeviceTools.dianzikanban, mac);
								if (checkresult != null) {
									result = checkresult;
									break;
								} else {
									alarmeboardid += mac + ",";
								}
							}
						}
					}
				}
			}
			
			Integer lightno = 0;  //校验电子看板报警内容
			if (eboardcontent != null && eboardcontent.length() > 0) {
				if (eboardcontent.length() > 9) {
					result = "电子看板内容过长";
				} else {
					try {
						lightno = Integer.parseInt(eboardcontent);
						if (lightno < 0) {
							result = "电子看板内容应为数字";
						}
					} catch (NumberFormatException e) {
						result = "电子看板内容应为数字";
					}
				}
			}
			
			if (result == null) {
				if (devid > 0) {  //修改
					dev.setId(devid);
				} else {  //新增
					dev.setCreatedate(Datetools.getCurrentDate());
				}
				dev.setName(devicename);
				dev.setType(devicetype);
				dev.setCode(devicemac);
				dev.setAlarmcontent(alarmcardcontent);
				
				if (alarmdevid.length() > 0) {
					alarmdevid = alarmdevid.substring(0, alarmdevid.length() - 1);
				}
				dev.setAlarmdevid(alarmdevid);
				
				dev.setLightno(lightno);
				if (alarmeboardid.length() > 0) {
					alarmeboardid = alarmeboardid.substring(0, alarmeboardid.length() - 1);
				}
				dev.setLightdevid(alarmeboardid);
				dev.setAlarmphone("");
				
				Integer parentid = 0;
				try {
					parentid = Integer.parseInt(parentdeviceid);
				} catch (NumberFormatException e) {
					
				}
				
				dev.setEmitid(parentid);
				dev.setParentId(parentid);
				
				String attr = "{";
				
				if (alarmstarttime != null && alarmstarttime.length() > 0) {
					attr += "alarmstarttime:'" + alarmstarttime + "',";
				}
				if (alarmendtime != null && alarmendtime.length() > 0) {
					attr += "alarmendtime:'" + alarmendtime + "',";
				}
				if (alarmdelay != null && alarmdelay.length() > 0) {
					attr += "alarmdelay:'" + alarmdelay + "'";
				}
				
				attr += "}";
				
				dev.setAttribute(attr);
				dev.setUpdatedate(Datetools.getCurrentDate());
				
				boolean saveresult = service.save(dev);
				
				if (saveresult) {
					result = "设备信息保存成功";
					
					DevPositionService service2 = DevPositionService.getInstance();
					Map<String, Object> queryMap = new HashMap<String, Object>();
					queryMap.put("devId", dev.getId());
					List<Object> relations = service2.getListByCondition(queryMap);
					if (relations != null && relations.size() > 0) {
						for (Object obj : relations) {
							DevPositionEntity temp = (DevPositionEntity) obj;
							if (temp != null) {
								service2.del(temp.getId());
							}
						}
					}
					
					DevPositionEntity dp = new DevPositionEntity();
					dp.setCreatedate(Datetools.getCurrentDate());
					dp.setDevId(dev.getId());
					Integer positionId = 0;
					try {
						positionId = Integer.parseInt(devicepostionid);
					} catch (NumberFormatException e) {
						
					}
					dp.setPositionId(positionId);
					saveresult = service2.save(dp);
					if (!saveresult) {
						result += "，位置信息保存失败";
					}
				} else {
					result = "保存失败";
				}
			}
		}
		
		try {
			result = new String(result.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {

		}
		
		return result;
	}
	
	@RequestMapping(params="choose")
	@ResponseBody
	public String getParentDevices4choose(HttpServletRequest request) {
		String devname = request.getParameter("devname");
		String pageNumber = request.getParameter("pageNumber");
		
		Map<String, Object> queryMap = new HashMap<String, Object>();
		if (devname != null && devname.length() > 0) {
			queryMap.put("name_like", devname);
		}
		queryMap.put("type_in", "7,15");
		
		Integer pageno = 1;  //当前页码
		Integer totalpage = 1;  //总页数
		Integer totalitem = 0;  //总数据条目数
		
		try {
			pageno = Integer.parseInt(pageNumber);
		} catch (NumberFormatException e) {
			
		}
		
		if (pageno < 1) {
			pageno = 1;
		}
		
		DevService service = DevService.getInstance();
		PageList pl = service.getListByCondition(queryMap, null, pageno, 10, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		
		
		ArrayList<DeviceInfo> positions = new ArrayList<DeviceInfo>();
		if (pl != null) {
			if (pl.getResultList() != null && pl.getResultList().size() > 0) {
				for (Object obj : pl.getResultList()) {
					DevEntity pe = (DevEntity) obj;
					if (pe != null) {
						DeviceInfo info = new DeviceInfo();
						info.setDevId(pe.getId());
						info.setDevName(pe.getName());
						info.setDevMac(pe.getCode());
						if (pe.getPositionList() != null && pe.getPositionList().size() > 0 && pe.getPositionList().get(0) != null && pe.getPositionList().get(0).getName() != null) {
							info.setPosition(pe.getPositionList().get(0).getName());
						} else {
							info.setPosition("");
						}
						positions.add(info);
					}
				}
			}
			totalpage = pl.getPageCount();
			if (totalpage == null || totalpage < 1) {
				totalpage = 1;
			}
			
			totalitem = pl.getRecordCount();
			if (totalitem == null || totalitem < 0) {
				totalitem = 0;
			}
		}
		
		while (positions.size() < 10) {
			positions.add(new DeviceInfo(0, "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
		}
		
		String paginhtml = "";
		paginhtml = "<span class=\"Pagin1\">";
		if (pageno > 1) {
			paginhtml += "<a onclick=\"getParentDevInfo('" + devname + "', '" + (pageno - 1) + "');\"><b>&lt;&lt;</b></a>";
		} else {
			paginhtml += "<a style=\"display:none\"><b>&lt;&lt;</b></a>";
		}
		
		
		int startindex = pageno;
		int endindex = pageno;
		if (totalpage > 7) {
			startindex = pageno;
			endindex = pageno;
			while (endindex - startindex < 6) {
				if (startindex > 1) {
					startindex = startindex - 1;
				}
				if (endindex < totalpage) {
					endindex = endindex + 1;
				}
			}
		} else {
			startindex = 1;
			endindex = totalpage;
		}
		
		for (int i = startindex; i <= endindex; i++) {
			if (i == pageno) {
				paginhtml += "<a onclick=\"getParentDevInfo('" + devname + "', '" + i + "');\" class=\"selectedpage\">" + i + "</a>";
			} else {
				paginhtml += "<a onclick=\"getParentDevInfo('" + devname + "', '" + i + "');\">" + i + "</a>";
			}
		}
		if (pageno < totalpage) {
			paginhtml += "<a onclick=\"getParentDevInfo('" + devname + "', '" + (pageno + 1) + "');\"><b>&gt;&gt;</b></a>";
		} else {
			paginhtml += "<a style=\"display:none\"><b>&gt;&gt;</b></a>";
		}
//		paginhtml += "</span><span class=\"Pagin2\"><input id=\"tardevpage\" value=\"1\"/><a onclick=\"getParentDevInfo('" + devname + "', $(\"#tardevpage\").val());\"><b>GO</b></a></span>";
		paginhtml += "</span><span class=\"Pagin2\"></span>";
		
		JSONObject jso = new JSONObject();
		jso.put("totalpage", totalpage);
		jso.put("totalitem", totalitem);
		jso.put("list", positions);
		jso.put("paginhtml", paginhtml);
		
		String json2return = jso.toString();
		
		try {
			json2return = new String(json2return.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {

		}
		
		return json2return;
	}
	
	@RequestMapping(params="chooseAll")
	@ResponseBody
	public String getAllDevices4choose(HttpServletRequest request) {
		String devname = request.getParameter("devname");
		String pageNumber = request.getParameter("pageNumber");
		
		Map<String, Object> queryMap = new HashMap<String, Object>();
		if (devname != null && devname.length() > 0) {
			queryMap.put("name_like", devname);
		}
		
		Integer pageno = 1;  //当前页码
		Integer totalpage = 1;  //总页数
		Integer totalitem = 0;  //总数据条目数
		
		try {
			pageno = Integer.parseInt(pageNumber);
		} catch (NumberFormatException e) {
			
		}
		
		if (pageno < 1) {
			pageno = 1;
		}
		
		DevService service = DevService.getInstance();
		PageList pl = service.getListByCondition(queryMap, null, pageno, 10, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
		
		
		ArrayList<DeviceInfo> positions = new ArrayList<DeviceInfo>();
		if (pl != null) {
			if (pl.getResultList() != null && pl.getResultList().size() > 0) {
				for (Object obj : pl.getResultList()) {
					DevEntity pe = (DevEntity) obj;
					if (pe != null) {
						DeviceInfo info = new DeviceInfo();
						info.setDevId(pe.getId());
						info.setDevName(pe.getName());
						info.setDevMac(pe.getCode());
						if (pe.getPositionList() != null && pe.getPositionList().size() > 0 && pe.getPositionList().get(0) != null && pe.getPositionList().get(0).getName() != null) {
							info.setPosition(pe.getPositionList().get(0).getName());
						} else {
							info.setPosition("");
						}
						positions.add(info);
					}
				}
			}
			totalpage = pl.getPageCount();
			if (totalpage == null || totalpage < 1) {
				totalpage = 1;
			}
			
			totalitem = pl.getRecordCount();
			if (totalitem == null || totalitem < 0) {
				totalitem = 0;
			}
		}
		
		while (positions.size() < 10) {
			positions.add(new DeviceInfo(0, "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
		}
		
		String paginhtml = "";
		paginhtml = "<span class=\"Pagin1\">";
		if (pageno > 1) {
			paginhtml += "<a onclick=\"getParentDevInfo('" + devname + "', '" + (pageno - 1) + "');\"><b>&lt;&lt;</b></a>";
		} else {
			paginhtml += "<a style=\"display:none\"><b>&lt;&lt;</b></a>";
		}
		
		
		int startindex = pageno;
		int endindex = pageno;
		if (totalpage > 7) {
			startindex = pageno;
			endindex = pageno;
			while (endindex - startindex < 6) {
				if (startindex > 1) {
					startindex = startindex - 1;
				}
				if (endindex < totalpage) {
					endindex = endindex + 1;
				}
			}
		} else {
			startindex = 1;
			endindex = totalpage;
		}
		
		for (int i = startindex; i <= endindex; i++) {
			if (i == pageno) {
				paginhtml += "<a onclick=\"getParentDevInfo('" + devname + "', '" + i + "');\" class=\"selectedpage\">" + i + "</a>";
			} else {
				paginhtml += "<a onclick=\"getParentDevInfo('" + devname + "', '" + i + "');\">" + i + "</a>";
			}
		}
		if (pageno < totalpage) {
			paginhtml += "<a onclick=\"getParentDevInfo('" + devname + "', '" + (pageno + 1) + "');\"><b>&gt;&gt;</b></a>";
		} else {
			paginhtml += "<a style=\"display:none\"><b>&gt;&gt;</b></a>";
		}
//		paginhtml += "</span><span class=\"Pagin2\"><input id=\"tardevpage\" value=\"1\"/><a onclick=\"getParentDevInfo('" + devname + "', $(\"#tardevpage\").val());\"><b>GO</b></a></span>";
		paginhtml += "</span><span class=\"Pagin2\"></span>";
		
		JSONObject jso = new JSONObject();
		jso.put("totalpage", totalpage);
		jso.put("totalitem", totalitem);
		jso.put("list", positions);
		jso.put("paginhtml", paginhtml);
		
		String json2return = jso.toString();
		
		try {
			json2return = new String(json2return.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {

		}
		
		return json2return;
	}
	
	@RequestMapping(params="devNotInstall")
	@ResponseBody
	public String getDevicesNotInstalled(HttpServletRequest request) {
		String devname = request.getParameter("devname");
		String pageNumber = request.getParameter("pageNumber");
		
		Map<String, Object> queryMap = new HashMap<String, Object>();
		if (devname != null && devname.length() > 0) {
			queryMap.put("name_like", devname);
		}
		
		List<OrderVO> orderList = new ArrayList<OrderVO>();
		OrderVO order = new OrderVO();
		order.setName("positionId");
		order.setOrderType(OrderVO.asc);
		orderList.add(order);
		
		Integer pageno = 1;  //当前页码
		Integer totalpage = 1;  //总页数
		Integer totalitem = 0;  //总数据条目数
		
		try {
			pageno = Integer.parseInt(pageNumber);
		} catch (NumberFormatException e) {
			
		}
		
		if (pageno < 1) {
			pageno = 1;
		}
		
		DevLeftJionMapService service = DevLeftJionMapService.getInstance();
		PageList pl = service.getListByCondition(queryMap, orderList, pageno, 10);
		
		
		ArrayList<DeviceInfo> positions = new ArrayList<DeviceInfo>();
		if (pl != null) {
			if (pl.getResultList() != null && pl.getResultList().size() > 0) {
				PositionService posService = PositionService.getInstance();
				for (Object obj : pl.getResultList()) {
					DevLeftJionMapEntity pe = (DevLeftJionMapEntity) obj;
					if (pe != null) {
						DeviceInfo info = new DeviceInfo();
						info.setDevId(pe.getId());
						info.setDevName(pe.getName());
						info.setDevMac(pe.getCode());
						Integer posid = pe.getPositionId();
						if (posid != null && posid >= 0) {
							if (posid == 0) {
								info.setPosition("园区");
							} else {
								PositionEntity posInfo = posService.getById(posid, false, false);
								if (posInfo != null && posInfo.getName() != null) {
									info.setPosition(posInfo.getName());
								} else {
									info.setPosition("");
								}
							}
						} else {
							info.setPosition("");
						}
						
						positions.add(info);
					}
				}
			}
			totalpage = pl.getPageCount();
			if (totalpage == null || totalpage < 1) {
				totalpage = 1;
			}
			
			totalitem = pl.getRecordCount();
			if (totalitem == null || totalitem < 0) {
				totalitem = 0;
			}
		}
		
		while (positions.size() < 10) {
			positions.add(new DeviceInfo(0, "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
		}
		
		String paginhtml = "";
		paginhtml = "<span class=\"Pagin1\">";
		if (pageno > 1) {
			paginhtml += "<a onclick=\"getParentDevInfo('" + devname + "', '" + (pageno - 1) + "');\"><b>&lt;&lt;</b></a>";
		} else {
			paginhtml += "<a style=\"display:none\"><b>&lt;&lt;</b></a>";
		}
		
		
		int startindex = pageno;
		int endindex = pageno;
		if (totalpage > 7) {
			startindex = pageno;
			endindex = pageno;
			while (endindex - startindex < 6) {
				if (startindex > 1) {
					startindex = startindex - 1;
				}
				if (endindex < totalpage) {
					endindex = endindex + 1;
				}
			}
		} else {
			startindex = 1;
			endindex = totalpage;
		}
		
		for (int i = startindex; i <= endindex; i++) {
			if (i == pageno) {
				paginhtml += "<a onclick=\"getParentDevInfo('" + devname + "', '" + i + "');\" class=\"selectedpage\">" + i + "</a>";
			} else {
				paginhtml += "<a onclick=\"getParentDevInfo('" + devname + "', '" + i + "');\">" + i + "</a>";
			}
		}
		if (pageno < totalpage) {
			paginhtml += "<a onclick=\"getParentDevInfo('" + devname + "', '" + (pageno + 1) + "');\"><b>&gt;&gt;</b></a>";
		} else {
			paginhtml += "<a style=\"display:none\"><b>&gt;&gt;</b></a>";
		}
//		paginhtml += "</span><span class=\"Pagin2\"><input id=\"tardevpage\" value=\"1\"/><a onclick=\"getParentDevInfo('" + devname + "', $(\"#tardevpage\").val());\"><b>GO</b></a></span>";
		paginhtml += "</span><span class=\"Pagin2\"></span>";
		
		JSONObject jso = new JSONObject();
		jso.put("totalpage", totalpage);
		jso.put("totalitem", totalitem);
		jso.put("list", positions);
		jso.put("paginhtml", paginhtml);
		
		String json2return = jso.toString();
		
		try {
			json2return = new String(json2return.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {

		}
		
		return json2return;
	}
	
	@RequestMapping(params="del")
	@ResponseBody
	public String delDevice(HttpServletRequest request) {
		String operResult = "";
		
		String devid2de = request.getParameter("deviceid");
		Integer devid = 0;
		try {
			devid = Integer.parseInt(devid2de);
		} catch (NumberFormatException e) {
			
		}
		
		if (devid > 0) {
			DevService service = DevService.getInstance();
			boolean result = service.del(devid, true, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true);
			if (result) {
				operResult = "删除成功";
			} else {
				operResult = "删除失败";
			}
		} else {
			operResult = "错误的设备ID";
		}
		
		try {
			operResult = new String(operResult.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {

		}
		
		return operResult;
	}
	
	/**
	 * 获取设备的历史数据
	 * @return
	 */
	@RequestMapping(params="history")
	@ResponseBody
	public String getHistoryData(HttpServletRequest request) {
		HistoryDataResult result = new HistoryDataResult();
		
		String deviceId = request.getParameter("deviceid");
		String begindate = request.getParameter("begindate");
		String enddate = request.getParameter("enddate");
		String pagenumber = request.getParameter("pagenumber");
		String subtype = request.getParameter("subtype");
		
		Integer pageno = 1;
		try {
			pageno = Integer.parseInt(pagenumber);
		} catch (NumberFormatException e) {
			
		}
		if (pageno < 1) {
			pageno = 1;
		}
		
		final int pagesize = 10;  // 固定大小
		
		String timestart = "";
		String timeend = "";
		
		Integer devId = 0;
		try {
			devId = Integer.parseInt(deviceId);
		} catch (NumberFormatException e) {
			
		}
		
		if (begindate != null && begindate.length() > 0) {
			timestart = begindate.replace("/", "").replace(":", "").replace(" ", "");
			timestart += "00";  //秒数
			
			if (timestart.length() != 14) {
				timestart = "";
			}
		}
		
		if (enddate != null && enddate.length() > 0) {
			timeend = enddate.replace("/", "").replace(":", "").replace(" ", "");
			timeend += "00";  //分钟及秒数
			
			if (timeend.length() != 14) {
				timeend = "";
			}
		}
		
		ArrayList<HistoryData> list = new ArrayList<HistoryData>();
		
		Integer totalpage = 1;
		Integer totalitem = 0;
		
		if (devId > 0) {
			DevService devservice = DevService.getInstance();
			DevEntity devEntity = devservice.getById(devId, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
			if (devEntity != null) {
				if ("2".equals(devEntity.getType())) {  //床垫
					HistoryBedService service = HistoryBedService.getInstance();
					Map<String, Object> queryMap = new HashMap<String, Object>();
					if (timestart.length() > 0) {
						queryMap.put("alarmupdatetime_ge", timestart);
					}
					if (timeend.length() > 0) {
						queryMap.put("alarmupdatetime_lt", timeend);
					}
					queryMap.put("devId", devId);
					List<OrderVO> orderList = new ArrayList<OrderVO>();
					OrderVO vo = new OrderVO();
					vo.setName("alarmupdatetime");
					vo.setOrderType(OrderVO.desc);
					orderList.add(vo);
					PageList pl = service.getListByCondition(queryMap, orderList, pageno, pagesize, false);
					if (pl != null) {
						totalpage = pl.getPageCount();
						totalitem = pl.getRecordCount();
						if (pl.getResultList() != null && pl.getResultList().size() > 0) {
							for (Object obj : pl.getResultList()) {
								HistoryBedEntity temp = (HistoryBedEntity) obj;
								if (temp != null) {
									HistoryData data = new HistoryData();
									data.setUpdateDate(Datetools.formateDate(temp.getAlarmupdatetime()));
									if ("Y".equalsIgnoreCase(temp.getHavingbody())) {
										data.setValue("有人");
									} else {
										data.setValue("无人");
									}
									list.add(data);
								}
							}
						}
					}
					result.setResult("获取成功");
				} else if("4".equals(devEntity.getType())) {  //园区内定位
					if ("全部".equals(subtype)) {
						HistoryLocationService service = HistoryLocationService.getInstance();
						Map<String, Object> queryMap = new HashMap<String, Object>();
						if (timestart.length() > 0) {
							queryMap.put("time_ge", timestart);
						}
						if (timeend.length() > 0) {
							queryMap.put("time_lt", timeend);
						}
						queryMap.put("devId", devId);
						List<OrderVO> orderList = new ArrayList<OrderVO>();
						OrderVO vo = new OrderVO();
						vo.setName("time");
						vo.setOrderType(OrderVO.desc);
						orderList.add(vo);
						PageList pl = service.getListByCondition(queryMap, orderList, pageno, pagesize, false);
						if (pl != null) {
							totalpage = pl.getPageCount();
							totalitem = pl.getRecordCount();
							if (pl.getResultList() != null && pl.getResultList().size() > 0) {
								for (Object obj : pl.getResultList()) {
									HistoryLocationEntity temp = (HistoryLocationEntity) obj;
									if (temp != null) {
										HistoryData data = new HistoryData();
										data.setUpdateDate(Datetools.formateDate(temp.getTime()));
										if (temp.getMoving() != null) {
											if ("Y".equalsIgnoreCase(temp.getMoving())) {
												data.setValue("未人卡分离");
											} else {
												data.setValue("人卡分离");
											}
										} else if (temp.getBodystate() != null) {
											if ("Y".equalsIgnoreCase(temp.getBodystate())) {
												data.setValue("未摔倒");
											} else {
												data.setValue("摔倒");
											}
										} else if (temp.getManualalarm() != null) {
											if ("Y".equalsIgnoreCase(temp.getManualalarm())) {
												data.setValue("未报警");
											} else {
												data.setValue("手动报警");
											}
										}
										list.add(data);
									}
								}
							}
						}
						result.setResult("获取成功");
					} else if ("人卡分离".equals(subtype)) {
						HistoryLocationMoveService service = HistoryLocationMoveService.getInstance();
						Map<String, Object> queryMap = new HashMap<String, Object>();
						if (timestart.length() > 0) {
							queryMap.put("movingupdatetime_ge", timestart);
						}
						if (timeend.length() > 0) {
							queryMap.put("movingupdatetime_lt", timeend);
						}
						queryMap.put("devId", devId);
						List<OrderVO> orderList = new ArrayList<OrderVO>();
						OrderVO vo = new OrderVO();
						vo.setName("movingupdatetime");
						vo.setOrderType(OrderVO.desc);
						orderList.add(vo);
						PageList pl = service.getListByCondition(queryMap, orderList, pageno, pagesize, false);
						if (pl != null) {
							totalpage = pl.getPageCount();
							totalitem = pl.getRecordCount();
							if (pl.getResultList() != null && pl.getResultList().size() > 0) {
								for (Object obj : pl.getResultList()) {
									HistoryLocationMoveEntity temp = (HistoryLocationMoveEntity) obj;
									if (temp != null) {
										HistoryData data = new HistoryData();
										data.setUpdateDate(Datetools.formateDate(temp.getMovingupdatetime()));
										if ("Y".equalsIgnoreCase(temp.getMoving())) {
											data.setValue("正常");
										} else {
											data.setValue("人卡分离");
										}
										list.add(data);
									}
								}
							}
						}
						result.setResult("获取成功");
					} else if ("摔倒".equals(subtype)) {
						HistoryLocationBodyService service = HistoryLocationBodyService.getInstance();
						Map<String, Object> queryMap = new HashMap<String, Object>();
						if (timestart.length() > 0) {
							queryMap.put("bodyupdatetime_ge", timestart);
						}
						if (timeend.length() > 0) {
							queryMap.put("bodyupdatetime_lt", timeend);
						}
						queryMap.put("devId", devId);
						List<OrderVO> orderList = new ArrayList<OrderVO>();
						OrderVO vo = new OrderVO();
						vo.setName("bodyupdatetime");
						vo.setOrderType(OrderVO.desc);
						orderList.add(vo);
						PageList pl = service.getListByCondition(queryMap, orderList, pageno, pagesize, false);
						if (pl != null) {
							totalpage = pl.getPageCount();
							totalitem = pl.getRecordCount();
							if (pl.getResultList() != null && pl.getResultList().size() > 0) {
								for (Object obj : pl.getResultList()) {
									HistoryLocationBodyEntity temp = (HistoryLocationBodyEntity) obj;
									if (temp != null) {
										HistoryData data = new HistoryData();
										data.setUpdateDate(Datetools.formateDate(temp.getBodyupdatetime()));
										if ("Y".equalsIgnoreCase(temp.getBodystate())) {
											data.setValue("正常");
										} else {
											data.setValue("摔倒");
										}
										list.add(data);
									}
								}
							}
						}
						result.setResult("获取成功");
					} else if ("手动报警".equals(subtype)) {
						HistoryLocationManualService service = HistoryLocationManualService.getInstance();
						Map<String, Object> queryMap = new HashMap<String, Object>();
						if (timestart.length() > 0) {
							queryMap.put("bodyupdatetime_ge", timestart);
						}
						if (timeend.length() > 0) {
							queryMap.put("bodyupdatetime_lt", timeend);
						}
						queryMap.put("devId", devId);
						List<OrderVO> orderList = new ArrayList<OrderVO>();
						OrderVO vo = new OrderVO();
						vo.setName("bodyupdatetime");
						vo.setOrderType(OrderVO.desc);
						orderList.add(vo);
						PageList pl = service.getListByCondition(queryMap, orderList, pageno, pagesize, false);
						if (pl != null) {
							totalpage = pl.getPageCount();
							totalitem = pl.getRecordCount();
							if (pl.getResultList() != null && pl.getResultList().size() > 0) {
								for (Object obj : pl.getResultList()) {
									HistoryLocationManualEntity temp = (HistoryLocationManualEntity) obj;
									if (temp != null) {
										HistoryData data = new HistoryData();
										data.setUpdateDate(Datetools.formateDate(temp.getBodyupdatetime()));
										if ("Y".equalsIgnoreCase(temp.getManualalarm())) {
											data.setValue("正常");
										} else {
											data.setValue("手动报警");
										}
										list.add(data);
									}
								}
							}
						}
						result.setResult("获取成功");
					} else {
						result.setResult("错误的老人状态标识");
					}
				} else if("9".equals(devEntity.getType())) {  //一键报警设备
					HistoryKeyalarmService service = HistoryKeyalarmService.getInstance();
					Map<String, Object> queryMap = new HashMap<String, Object>();
					if (timestart.length() > 0) {
						queryMap.put("alarmupdatetime_ge", timestart);
					}
					if (timeend.length() > 0) {
						queryMap.put("alarmupdatetime_lt", timeend);
					}
					queryMap.put("devId", devId);
					List<OrderVO> orderList = new ArrayList<OrderVO>();
					OrderVO vo = new OrderVO();
					vo.setName("alarmupdatetime");
					vo.setOrderType(OrderVO.desc);
					orderList.add(vo);
					PageList pl = service.getListByCondition(queryMap, orderList, pageno, pagesize, false);
					if (pl != null) {
						totalpage = pl.getPageCount();
						totalitem = pl.getRecordCount();
						if (pl.getResultList() != null && pl.getResultList().size() > 0) {
							for (Object obj : pl.getResultList()) {
								HistoryKeyalarmEntity temp = (HistoryKeyalarmEntity) obj;
								if (temp != null) {
									HistoryData data = new HistoryData();
									data.setUpdateDate(Datetools.formateDate(temp.getAlarmupdatetime()));
									if ("Y".equalsIgnoreCase(temp.getAlarm())) {
										data.setValue("报警");
									} else {
										data.setValue("正常");
									}
									list.add(data);
								}
							}
						}
					}
					result.setResult("获取成功");
				} else if("10".equals(devEntity.getType())) {  //无线门磁
					HistoryDoorService service = HistoryDoorService.getInstance();
					Map<String, Object> queryMap = new HashMap<String, Object>();
					if (timestart.length() > 0) {
						queryMap.put("alarmupdatetime_ge", timestart);
					}
					if (timeend.length() > 0) {
						queryMap.put("alarmupdatetime_lt", timeend);
					}
					queryMap.put("devId", devId);
					List<OrderVO> orderList = new ArrayList<OrderVO>();
					OrderVO vo = new OrderVO();
					vo.setName("alarmupdatetime");
					vo.setOrderType(OrderVO.desc);
					orderList.add(vo);
					PageList pl = service.getListByCondition(queryMap, orderList, pageno, pagesize, false);
					if (pl != null) {
						totalpage = pl.getPageCount();
						totalitem = pl.getRecordCount();
						if (pl.getResultList() != null && pl.getResultList().size() > 0) {
							for (Object obj : pl.getResultList()) {
								HistoryDoorEntity temp = (HistoryDoorEntity) obj;
								if (temp != null) {
									HistoryData data = new HistoryData();
									data.setUpdateDate(Datetools.formateDate(temp.getAlarmupdatetime()));
									if ("Y".equalsIgnoreCase(temp.getOpenclose())) {
										data.setValue("关");
									} else {
										data.setValue("开");
									}
									list.add(data);
								}
							}
						}
					}
					result.setResult("获取成功");
				} else if("11".equals(devEntity.getType())) {  //尿湿感应
					HistoryUrineService service = HistoryUrineService.getInstance();
					Map<String, Object> queryMap = new HashMap<String, Object>();
					if (timestart.length() > 0) {
						queryMap.put("alarmupdatetime_ge", timestart);
					}
					if (timeend.length() > 0) {
						queryMap.put("alarmupdatetime_lt", timeend);
					}
					queryMap.put("devId", devId);
					List<OrderVO> orderList = new ArrayList<OrderVO>();
					OrderVO vo = new OrderVO();
					vo.setName("alarmupdatetime");
					vo.setOrderType(OrderVO.desc);
					orderList.add(vo);
					PageList pl = service.getListByCondition(queryMap, orderList, pageno, pagesize, false);
					if (pl != null) {
						totalpage = pl.getPageCount();
						totalitem = pl.getRecordCount();
						if (pl.getResultList() != null && pl.getResultList().size() > 0) {
							for (Object obj : pl.getResultList()) {
								HistoryUrineEntity temp = (HistoryUrineEntity) obj;
								if (temp != null) {
									HistoryData data = new HistoryData();
									data.setUpdateDate(Datetools.formateDate(temp.getAlarmupdatetime()));
									if ("Y".equalsIgnoreCase(temp.getAlarm())) {
										data.setValue("报警");
									} else {
										data.setValue("正常");
									}
									list.add(data);
								}
							}
						}
					}
					result.setResult("获取成功");
				} else if("12".equals(devEntity.getType())) {  //腕带呼叫器
					HistoryWandaiService service = HistoryWandaiService.getInstance();
					Map<String, Object> queryMap = new HashMap<String, Object>();
					if (timestart.length() > 0) {
						queryMap.put("alarmupdatetime_ge", timestart);
					}
					if (timeend.length() > 0) {
						queryMap.put("alarmupdatetime_lt", timeend);
					}
					queryMap.put("devId", devId);
					List<OrderVO> orderList = new ArrayList<OrderVO>();
					OrderVO vo = new OrderVO();
					vo.setName("alarmupdatetime");
					vo.setOrderType(OrderVO.desc);
					orderList.add(vo);
					PageList pl = service.getListByCondition(queryMap, orderList, pageno, pagesize, false);
					if (pl != null) {
						totalpage = pl.getPageCount();
						totalitem = pl.getRecordCount();
						if (pl.getResultList() != null && pl.getResultList().size() > 0) {
							for (Object obj : pl.getResultList()) {
								HistoryWandaiEntity temp = (HistoryWandaiEntity) obj;
								if (temp != null) {
									HistoryData data = new HistoryData();
									data.setUpdateDate(Datetools.formateDate(temp.getAlarmupdatetime()));
									if ("Y".equalsIgnoreCase(temp.getAlarm())) {
										data.setValue("报警");
									} else {
										data.setValue("无报警");
									}
									list.add(data);
								}
							}
						}
					}
					result.setResult("获取成功");
				} else if("13".equals(devEntity.getType())) {  //红外设备
					HistoryIrService service = HistoryIrService.getInstance();
					Map<String, Object> queryMap = new HashMap<String, Object>();
					if (timestart.length() > 0) {
						queryMap.put("alarmupdatetime_ge", timestart);
					}
					if (timeend.length() > 0) {
						queryMap.put("alarmupdatetime_lt", timeend);
					}
					queryMap.put("devId", devId);
					List<OrderVO> orderList = new ArrayList<OrderVO>();
					OrderVO vo = new OrderVO();
					vo.setName("alarmupdatetime");
					vo.setOrderType(OrderVO.desc);
					orderList.add(vo);
					PageList pl = service.getListByCondition(queryMap, orderList, pageno, pagesize, false);
					if (pl != null) {
						totalpage = pl.getPageCount();
						totalitem = pl.getRecordCount();
						if (pl.getResultList() != null && pl.getResultList().size() > 0) {
							for (Object obj : pl.getResultList()) {
								HistoryIrEntity temp = (HistoryIrEntity) obj;
								if (temp != null) {
									HistoryData data = new HistoryData();
									data.setUpdateDate(Datetools.formateDate(temp.getAlarmupdatetime()));
									if ("Y".equalsIgnoreCase(temp.getHavingbody())) {
										data.setValue("有人");
									} else {
										data.setValue("无人");
									}
									list.add(data);
								}
							}
						}
					}
					result.setResult("获取成功");
				} else if("16".equals(devEntity.getType())) {  //心率床垫
					Map<String, Object> queryMap = new HashMap<String, Object>();
					if ("全部".equals(subtype)) {
						HistoryTizhengAllService service = HistoryTizhengAllService.getInstance();
						if (timestart.length() > 0) {
							queryMap.put("time_ge", timestart);
						}
						if (timeend.length() > 0) {
							queryMap.put("time_lt", timeend);
						}
						queryMap.put("devId", devId);
						List<OrderVO> orderList = new ArrayList<OrderVO>();
						OrderVO vo = new OrderVO();
						vo.setName("time");
						vo.setOrderType(OrderVO.desc);
						orderList.add(vo);
						PageList pl = service.getListByCondition(queryMap, orderList, pageno, pagesize, false);
						if (pl != null) {
							totalpage = pl.getPageCount();
							totalitem = pl.getRecordCount();
							if (pl.getResultList() != null && pl.getResultList().size() > 0) {
								for (Object obj : pl.getResultList()) {
									HistoryTizhengAllEntity temp = (HistoryTizhengAllEntity) obj;
									if (temp != null) {
										HistoryData data = new HistoryData();
										data.setUpdateDate(Datetools.formateDate(temp.getTime()));
										if (temp.getHavingbody() != null) {
											if ("Y".equalsIgnoreCase(temp.getHavingbody())) {
												data.setValue("有人");
											} else {
												data.setValue("无人");
											}
										} else if (temp.getBreath() != null && temp.getBreath() > 0) {
											data.setValue("心跳：" + temp.getBreath().toString() + "次/分钟");
										} else if (temp.getHeart() != null && temp.getHeart() > 0) {
											data.setValue("呼吸：" + temp.getHeart().toString() + "次/分钟");
										}
										list.add(data);
									}
								}
							}
						}
						result.setResult("获取成功");
					} else if ("床垫状态".equals(subtype)) {
						HistoryTizhengBedService service = HistoryTizhengBedService.getInstance();
						if (timestart.length() > 0) {
							queryMap.put("alarmupdatetime_ge", timestart);
						}
						if (timeend.length() > 0) {
							queryMap.put("alarmupdatetime_lt", timeend);
						}
						queryMap.put("devId", devId);
						queryMap.put("havingbody_isNotNull", 0);  //有床垫状态数据
						List<OrderVO> orderList = new ArrayList<OrderVO>();
						OrderVO vo = new OrderVO();
						vo.setName("alarmupdatetime");
						vo.setOrderType(OrderVO.desc);
						orderList.add(vo);
						PageList pl = service.getListByCondition(queryMap, orderList, pageno, pagesize, false);
						if (pl != null) {
							totalpage = pl.getPageCount();
							totalitem = pl.getRecordCount();
							if (pl.getResultList() != null && pl.getResultList().size() > 0) {
								for (Object obj : pl.getResultList()) {
									HistoryTizhengBedEntity temp = (HistoryTizhengBedEntity) obj;
									if (temp != null && temp.getHavingbody() != null) {
										HistoryData data = new HistoryData();
										data.setUpdateDate(Datetools.formateDate(temp.getAlarmupdatetime()));
										if ("Y".equalsIgnoreCase(temp.getHavingbody())) {
											data.setValue("有人");
										} else {
											data.setValue("无人");
										}
										list.add(data);
									}
								}
							}
						}
						result.setResult("获取成功");
					} else if ("心跳".equals(subtype)) {
						HistoryTizhengBedService service = HistoryTizhengBedService.getInstance();
						if (timestart.length() > 0) {
							queryMap.put("devupdatetime_ge", timestart);
						}
						if (timeend.length() > 0) {
							queryMap.put("devupdatetime_lt", timeend);
						}
						queryMap.put("devId", devId);
						queryMap.put("heart_gt", 0);  //有心跳数据，且大于0
						List<OrderVO> orderList = new ArrayList<OrderVO>();
						OrderVO vo = new OrderVO();
						vo.setName("devupdatetime");
						vo.setOrderType(OrderVO.desc);
						orderList.add(vo);
						PageList pl = service.getListByCondition(queryMap, orderList, pageno, pagesize, false);
						if (pl != null) {
							totalpage = pl.getPageCount();
							totalitem = pl.getRecordCount();
							if (pl.getResultList() != null && pl.getResultList().size() > 0) {
								for (Object obj : pl.getResultList()) {
									HistoryTizhengBedEntity temp = (HistoryTizhengBedEntity) obj;
									if (temp != null && temp.getHeart() != null) {
										HistoryData data = new HistoryData();
										data.setUpdateDate(Datetools.formateDate(temp.getDevupdatetime()));
										data.setValue(temp.getHeart().toString());
										list.add(data);
									}
								}
							}
						}
						result.setResult("获取成功");
					} else if ("呼吸".equals(subtype)) {
						HistoryTizhengBedService service = HistoryTizhengBedService.getInstance();
						if (timestart.length() > 0) {
							queryMap.put("devupdatetime_ge", timestart);
						}
						if (timeend.length() > 0) {
							queryMap.put("devupdatetime_lt", timeend);
						}
						queryMap.put("devId", devId);
						queryMap.put("breath_gt", 0);  //有呼吸数据，且大于0
						List<OrderVO> orderList = new ArrayList<OrderVO>();
						OrderVO vo = new OrderVO();
						vo.setName("devupdatetime");
						vo.setOrderType(OrderVO.desc);
						orderList.add(vo);
						PageList pl = service.getListByCondition(queryMap, orderList, pageno, pagesize, false);
						if (pl != null) {
							totalpage = pl.getPageCount();
							totalitem = pl.getRecordCount();
							if (pl.getResultList() != null && pl.getResultList().size() > 0) {
								for (Object obj : pl.getResultList()) {
									HistoryTizhengBedEntity temp = (HistoryTizhengBedEntity) obj;
									if (temp != null && temp.getBreath() != null) {
										HistoryData data = new HistoryData();
										data.setUpdateDate(Datetools.formateDate(temp.getDevupdatetime()));
										data.setValue(temp.getBreath().toString());
										list.add(data);
									}
								}
							}
						}
						result.setResult("获取成功");
					} else {
						result.setResult("错误的体征床垫标识");
					}
				} else {
					result.setResult("错误的设备类型");
				}
			} else {
				result.setResult("无对应设备信息");
			}
		} else {
			result.setResult("错误的设备ID");
		}
		
		while (list.size() < 10) {
			list.add(new HistoryData("", ""));
		}
		
		result.setList(list);
		result.setTotalitem(totalitem);
		result.setTotalpage(totalpage);
		

		String paginhtml = "";
		if (pageno > 1) {
			paginhtml += "<a onclick=\"getHistoryData('" + (pageno - 1) + "')\"><b>&lt;&lt;</b></a>";
		} else {
			paginhtml += "<a style=\"display:none\"><b>&lt;&lt;</b></a>";
		}
		
		int startindex = pageno;
		int endindex = pageno;
		if (totalpage > 7) {
			startindex = pageno;
			endindex = pageno;
			while (endindex - startindex < 6) {
				if (startindex > 1) {
					startindex = startindex - 1;
				}
				if (endindex < totalpage) {
					endindex = endindex + 1;
				}
			}
		} else {
			startindex = 1;
			endindex = totalpage;
		}
		
		for (int i = startindex; i <= endindex; i++) {
			if (i == pageno) {
				paginhtml += "<a onclick=\"getHistoryData('" + i + "')\" class=\"selectedpage\">" + i + "</a>";
			} else {
				paginhtml += "<a onclick=\"getHistoryData('" + i + "')\">" + i + "</a>";
			}
		}
		if (pageno < totalpage) {
			paginhtml += "<a onclick=\"getHistoryData('" + (pageno + 1) + "')\"><b>&gt;&gt;</b></a>";
		} else {
			paginhtml += "<a style=\"display:none\"><b>&gt;&gt;</b></a>";
		}
		
		result.setPaginhtml(paginhtml);
		
		String json2return = JSONObject.fromObject(result).toString();
		try {
			json2return = new String(json2return.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {

		}
		return json2return;
	}
	
	@RequestMapping(params="installDev")
	@ResponseBody
	public String installDev(HttpServletRequest request) {
		String devidString = request.getParameter("devId");
		String positionidString = request.getParameter("positionId");

		String result = "";
		
		Integer devId = 0;
		try {
			devId = Integer.parseInt(devidString);
		} catch (NumberFormatException e) {
			
		}
		
		if (devId <= 0) {
			result = "错误的设备ID信息";
		} else {
			DevService devService = DevService.getInstance();
			DevEntity devInfo = devService.getById(devId, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
			if (devInfo == null) {
				result = "设备不存在";
			} else {
				Integer positionId = -1;
				try {
					positionId = Integer.parseInt(positionidString);
				} catch (NumberFormatException e) {
					
				}
				
				if (positionId < 0) {
					result = "错误的位置信息";
				} else {
					PositionEntity posInfo = null;
					if (positionId > 0) {
						PositionService posService = PositionService.getInstance();
						posInfo = posService.getById(positionId, false, false);
					}
					
					if (positionId > 0 && posInfo == null) {
						result = "指定的位置不存在";
					} else {
						//如果设备有安装位置的，删除之
						DevPositionService service = DevPositionService.getInstance();
						Map<String, Object> queryMap = new HashMap<String, Object>();
						queryMap.put("devId", devId);
						boolean delresult = service.delList(queryMap);
						if (delresult) {
							DevPositionEntity info2save = new DevPositionEntity();
							info2save.setCreatedate(Datetools.getCurrentDate());
							info2save.setDevId(devId);
							info2save.setPositionId(positionId);
							boolean saveresult = service.save(info2save);
							if (saveresult) {
								result = "安装成功";
							} else {
								result = "安装失败";
							}
						} else {
							result = "清除原设备安装信息失败";
						}
					}
				}
			}
		}
		
		try {
			result = new String(result.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {

		}
		return result;
	}
}
