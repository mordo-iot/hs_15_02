package fly.front.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.framework.system.db.query.OrderVO;
import com.framework.system.db.query.PageList;

import fly.entity.alarmAll.AlarmAllEntity;
import fly.entity.alarmCurrent.AlarmCurrentEntity;
import fly.entity.alarmHistory.AlarmHistoryEntity;
import fly.entity.devPosition.DevPositionEntity;
import fly.front.entity.HistoryAlarmInfo;
import fly.front.tools.AlarmCodeConverter;
import fly.front.tools.Datetools;
import fly.service.alarmAll.AlarmAllService;
import fly.service.alarmCurrent.AlarmCurrentService;
import fly.service.alarmHistory.AlarmHistoryService;
import fly.service.devPosition.DevPositionService;


@Controller
@Scope("prototype")
@RequestMapping("/hisalarm")
public class HistoryAlarmController {

	@RequestMapping(params="list")
	public ModelAndView getHistoryAlarmList() {
		ModelAndView mav = new ModelAndView("historyAlarm");
		return mav;
	}
	
	@RequestMapping(params="query")
	@ResponseBody
	public String queryHistoryAlarmInfos(HttpServletRequest request) {
		String begintime = request.getParameter("begintime");
		String endtime = request.getParameter("endtime");
		String alarmtype = request.getParameter("alarmtype");
		String handlestatus = request.getParameter("handlestatus");
		String devidString = request.getParameter("devid");
		String positionidString = request.getParameter("positionid");
		String pagenumber = request.getParameter("pagenumber");
		
		AlarmAllService service = AlarmAllService.getInstance();
		Map<String, Object> querymap = new HashMap<String, Object>();
		
		
		if (begintime != null && begintime.length() > 0) {
			String timestart = "";
			timestart = begintime.replace("/", "").replace(":", "").replace(" ", "");  //把“年年年年/月月/日日 时时:分分”转换成“年年年年月月日日时时分分”
			timestart += "00";  //秒数
			if (timestart.length() == 14) {
				querymap.put("createdate_ge", timestart);
			}
		}
		
		if (endtime != null && endtime.length() > 0) {
			String timeend = "";
			timeend = endtime.replace("/", "").replace(":", "").replace(" ", "");  //把“年年年年/月月/日日 时时:分分”转换成“年年年年月月日日时时分分”
			timeend += "00";  //秒数
			if (timeend.length() == 14) {
				querymap.put("createdate_le", timeend);
			}
		}
		
		if (alarmtype != null && alarmtype.length() > 0) {
			querymap.put("code", alarmtype);
		}
		
		if (handlestatus != null && handlestatus.length() > 0) {
			try {
				querymap.put("handlestate", Integer.parseInt(handlestatus));
			} catch (NumberFormatException e) {
				
			}
		}
		
		try {
			Integer devId = Integer.parseInt(devidString);
			if (devId > 0) {
				querymap.put("devId", devId);
			}
		} catch (NumberFormatException e) {
			
		}
		
		try{
			Integer posId = Integer.parseInt(positionidString);
			if (posId >= 0) {
				DevPositionService service1 = DevPositionService.getInstance();
				Map<String, Object> queryMap1 = new HashMap<String, Object>();
				queryMap1.put("positionId", posId);
				List<Object> relations = service1.getListByCondition(queryMap1);
				String devids = "";
				if (relations != null && relations.size() > 0) {
					for (Object obj : relations) {
						DevPositionEntity info = (DevPositionEntity) obj;
						if (info != null && info.getDevId() != null && info.getDevId() > 0) {
							devids += info.getDevId() + ",";
						}
					}
				}
				if (devids.length() > 0) {
					devids = devids.substring(0, devids.length() - 1);
				} else {
					devids = "''";
				}
				querymap.put("devId_in", devids);
			}
		} catch (NumberFormatException e) {
			
		}
		
		Integer pageno = 1;
		try {
			pageno = Integer.parseInt(pagenumber);
		} catch (NumberFormatException e) {
			
		}
		
		if (pageno == null || pageno < 1) {
			pageno = 1;
		}
		
		ArrayList<OrderVO> orderList = new ArrayList<OrderVO>();
		OrderVO order = new OrderVO();
		order.setName("createdate");
		order.setOrderType(OrderVO.desc);
		orderList.add(order);
		PageList pl = service.getListByCondition(querymap, orderList, pageno, 11, true);
		
        ArrayList<HistoryAlarmInfo> alarmInfos = new ArrayList<HistoryAlarmInfo>();
		
        Integer totalpage = 1;
        Integer totalitem = 0;
        
		if (pl != null) {
			totalpage = pl.getPageCount();
			totalitem = pl.getRecordCount();
			List<Object> alarms = pl.getResultList();
			if (alarms != null && alarms.size() > 0) {
				for (Object obj : alarms) {
					AlarmAllEntity temp = (AlarmAllEntity) obj;
					if (temp != null) {
						HistoryAlarmInfo info = new HistoryAlarmInfo();
						info.setAlarmId(temp.getId());
						info.setAlarmContent(temp.getContent());
						info.setAlarmId(temp.getId());
						if (temp.getDev() != null) {
							info.setAlarmTarget(temp.getDev().getName());
						} else {
							info.setAlarmTarget("");
						}
						info.setAlarmTime(Datetools.formateDate(temp.getCreatedate()));
						info.setAlarmType(AlarmCodeConverter.getAlarmTypeName(temp.getCode()));
						info.setHandleDes(temp.getHandledesc());
						if (temp.getHandlestate() == null) {
							info.setHandleStatus("");
						} else if (temp.getHandlestate() == 0) {
							info.setHandleStatus("未处理");
						} else if (temp.getHandlestate() == 1) {
							info.setHandleStatus("已处理");
						} else if (temp.getHandlestate() == 2) {
							info.setHandleStatus("手动取消");
						} else {
							info.setHandleStatus("");
						}
						alarmInfos.add(info);
					}
				}
			}
		}
		
		while (alarmInfos.size() < 11) {
			HistoryAlarmInfo info = new HistoryAlarmInfo(0, "", "", "", "", "", "");
			alarmInfos.add(info);
		}
		
		if (totalpage == null || totalpage < 1) {
			totalpage = 1;
		}
		if (totalitem == null || totalitem < 0) {
			totalitem = 0;
		}
		
		String paginhtml = "";
		if (pageno > 1) {
			paginhtml += "<a onclick=\"searchButtonClicked('" + (pageno - 1) + "');\"><b>&lt;&lt;</b></a>";
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
				paginhtml += "<a onclick=\"searchButtonClicked('" + i + "');\" class=\"selectedpage\">" + i + "</a>";
			} else {
				paginhtml += "<a onclick=\"searchButtonClicked('" + i + "');\">" + i + "</a>";
			}
		}
		if (pageno < totalpage) {
			paginhtml += "<a onclick=\"searchButtonClicked('" + (pageno + 1) + "');\"><b>&gt;&gt;</b></a>";
		} else {
			paginhtml += "<a style=\"display:none\"><b>&gt;&gt;</b></a>";
		}
		
		paginhtml += "<input type=\"hidden\" id=\"currentPageno\" value=\"" + pageno + "\">";
		
		JSONObject obj = new JSONObject();
		obj.put("totalpage", totalpage);
		obj.put("totalitem", totalitem);
		obj.put("paginhtml", paginhtml);
		obj.put("list", JSONArray.fromObject(alarmInfos));
		
		
		String json2return = obj.toString();
		
		try {
			json2return = new String(json2return.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {

		}
		
		return json2return;
	}
	
	@RequestMapping(params="update")
	@ResponseBody
	public String addAlarmDes(HttpServletRequest request) {
		String alarmidString = request.getParameter("alarmid");
		String des = request.getParameter("des");
		
		String result = "";
		
		Integer alarmId = 0;
		try {
			alarmId = Integer.parseInt(alarmidString);
		} catch (NumberFormatException e) {
			
		}
		
		if (alarmId <= 0) {
			result = "错误的报警信息ID";
		} else {
			AlarmCurrentService serviceCurr = AlarmCurrentService.getInstance();
			AlarmHistoryService serviceHis = AlarmHistoryService.getInstance();
			
			AlarmCurrentEntity curr = serviceCurr.getById(alarmId, false);
			AlarmHistoryEntity his = serviceHis.getById(alarmId, false);
			
			if (curr == null && his == null) {
				result = "报警信息不存在";
			} else if (curr == null) {
				his.setHandledesc(des);
				his.setHandledate(Datetools.getCurrentDate());
				boolean saveresult = serviceHis.save(his);
				if (saveresult) {
					result = "更新成功";
				} else {
					result = "更新失败";
				}
			} else if (his == null) {
				curr.setHandledesc(des);
				curr.setHandledate(Datetools.getCurrentDate());
				boolean saveresult = serviceCurr.save(curr);
				if (saveresult) {
					result = "更新成功";
				} else {
					result = "更新失败";
				}
			} else {
				his.setHandledesc(des);
				his.setHandledate(Datetools.getCurrentDate());
				curr.setHandledesc(des);
				curr.setHandledate(Datetools.getCurrentDate());
				boolean saveresult = serviceCurr.save(curr) && serviceHis.save(his);
				if (saveresult) {
					result = "更新成功";
				} else {
					result = "更新失败";
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
