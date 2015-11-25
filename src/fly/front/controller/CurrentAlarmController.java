package fly.front.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.framework.system.db.query.OrderVO;
import com.framework.system.db.query.PageList;

import fly.entity.alarmCurrent.AlarmCurrentEntity;
import fly.front.entity.CurrentAlarmInfo;
import fly.front.entity.GetCurrentAlarmInfoResult;
import fly.front.tools.AlarmCodeConverter;
import fly.front.tools.Datetools;
import fly.service.alarmCurrent.AlarmCurrentService;


@Scope("prototype")
@Controller
@RequestMapping("/currentAlarm")
public class CurrentAlarmController {

	@RequestMapping(params="list")
	public ModelAndView currentAlarm() {
		ModelAndView mav = new ModelAndView("currentAlarm");
		return mav;
	}
	
	@RequestMapping(params="show")
	@ResponseBody
	public String getCurrentAlarmInfos(HttpServletRequest request) {
		String alarmType = request.getParameter("alarmType");
		
		GetCurrentAlarmInfoResult result = new GetCurrentAlarmInfoResult();
		
		AlarmCurrentService service = AlarmCurrentService.getInstance();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("handlestate", 0);
		if (alarmType != null && (!"".equals(alarmType.trim()))) {
			queryMap.put("code", alarmType);
		}
		
		List<OrderVO> orderlist = new ArrayList<OrderVO>();
		OrderVO vo = new OrderVO();
		vo.setName("createdate");
		vo.setOrderType(OrderVO.desc);
		orderlist.add(vo);
		PageList pl = service.getListByCondition(queryMap, orderlist, 1, 100, true);
		ArrayList<CurrentAlarmInfo> alarminfos = new ArrayList<CurrentAlarmInfo>();
		
		if (pl != null) {
			List<Object> listInPl = pl.getResultList();
			result.setTotalItem(pl.getRecordCount());
			
			if (listInPl != null && listInPl.size() > 0) {
				for (Object obj : listInPl) {
					AlarmCurrentEntity alarm = (AlarmCurrentEntity) obj;
					if (alarm != null) {
						CurrentAlarmInfo info = new CurrentAlarmInfo();
						info.setAlarmId(alarm.getId());
						info.setAlarmContent(alarm.getContent());
						if (alarm.getDev() != null) {
							info.setAlarmTarget(alarm.getDev().getName());
						}
						info.setAlarmTime(Datetools.formateDate(alarm.getCreatedate()));
						info.setAlarmType(AlarmCodeConverter.getAlarmTypeName(alarm.getCode()));
						alarminfos.add(info);
					}
				}
			}
		}
		result.setList(alarminfos);
		
		String json2return = JSONObject.fromObject(result).toString();

		try {
			json2return = new String(json2return.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {

		}
		return json2return;
	}
	
	/**
	 * 取消报警
	 * @param alarmid 报警信息的ID
	 * @return
	 */
	@RequestMapping(params="cancelAlarm")
	@ResponseBody
	public String cancelAlarm(String alarmid) {
		String json2return;
		AlarmCurrentService service = AlarmCurrentService.getInstance();
		
		try {
			AlarmCurrentEntity currentalarm = service.getById(Integer.parseInt(alarmid), false);
			if (currentalarm != null) {
				currentalarm.setHandlestate(2);
				boolean result = service.save(currentalarm);  //2为手动取消
				if (result) {
					json2return = "取消成功";
				} else {
					json2return = "更改报警状态失败";
				}
			} else {
				json2return = "未找到对应的报警信息";
			}
		} catch (NumberFormatException e) {
			json2return = "错误的报警信息ID";
		}
		
		try {
			json2return = new String(json2return.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {

		}
		return json2return;
	}
}
