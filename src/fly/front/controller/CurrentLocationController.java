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

import fly.entity.currentLocation.CurrentLocationEntity;
import fly.entity.position.PositionEntity;
import fly.service.currentLocation.CurrentLocationService;
import fly.service.position.PositionService;


@Scope("prototype")
@Controller
@RequestMapping("/currlocation")
public class CurrentLocationController {

	@RequestMapping(params="locations")
	@ResponseBody
	public String getCurrentLocations(HttpServletRequest request) {
		
		ArrayList<CurrentLocationEntity> locations = new ArrayList<CurrentLocationEntity>();
		CurrentLocationService service = CurrentLocationService.getInstance();
		List<Object> temploc = service.getListByCondition(null);
		if (temploc != null && temploc.size() > 0) {
			for (Object obj : temploc) {
				CurrentLocationEntity loc = (CurrentLocationEntity) obj;
				if (loc != null) {
					locations.add(loc);
				}
			}
		}
		
		JSONArray result = JSONArray.fromObject(locations);
		
		String json2return = result.toString();
		try {
			json2return = new String(json2return.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {

		}
		return json2return;
	}
	
	@RequestMapping(params="locationNumber")
	@ResponseBody
	public String getCurrentPosLocations(HttpServletRequest request) {
		String buildingId = request.getParameter("buildingId");
		String floorId = request.getParameter("floorId");
		
		Integer building = 0;
		try {
			building = Integer.parseInt(buildingId);
		} catch (NumberFormatException e) {
			
		}
		Integer floor = 0;
		try {
			floor = Integer.parseInt(floorId);
		} catch (NumberFormatException e) {
			
		}
		
		return getCurrLocationsByPos(building, floor);
	}
	
	/**
	 * 根据位置信息获取在线人数
	 * @param building 大楼id
	 * @param floor 楼层id
	 * @return 在线人数
	 */
	private String getCurrLocationsByPos(int building, int floor) {
		int locations = 0;
		CurrentLocationService service = CurrentLocationService.getInstance();
		List<Object> temploc = service.getListByCondition(null);
		if (temploc != null && temploc.size() > 0) {
			for (Object obj : temploc) {
				CurrentLocationEntity loc = (CurrentLocationEntity) obj;
				if (loc != null) {
					if (loc.getPositionId() > 0) {
						if (building == 0 && floor == 0) {  //未指定时
							locations++;
						} else{
							PositionService posService = PositionService.getInstance();
							if (floor != 0) {  //指定了floor时
								int result = 0;
								int temp = loc.getPositionId();
								while (true) {
									PositionEntity pos = posService.getById(temp, false, false);
									if (pos == null) {
										break;
									} else {
										if (pos.getParentId() > 0) {
											result = temp;
											temp = pos.getParentId();
										} else {
											break;
										}
									}
								}
								if (result == floor) {
									locations++;
								}
							} else {  //指定了building时
								int temp = loc.getPositionId();
								while (true) {
									PositionEntity pos = posService.getById(temp, false, false);
									if (pos == null) {
										break;
									} else {
										if (pos.getParentId() > 0) {
											temp = pos.getParentId();
										} else {
											break;
										}
									}
								}
								if (temp == building) {
									locations++;
								}
							}
						}
					}
				}
			}
		}
		
		String result = locations + "";
		return result;
	}
	
	@RequestMapping(params="buildings")
	@ResponseBody
	public String getBuildingInfos(HttpServletRequest request) {
		JSONArray result = new JSONArray();
		PositionService service = PositionService.getInstance();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("parentId", 0);
		List<Object> buildingInfos = service.getListByCondition(queryMap);
		if (buildingInfos != null && buildingInfos.size() > 0) {
			for (Object obj : buildingInfos) {
				PositionEntity posInfo = (PositionEntity) obj;
				if (posInfo != null) {
					JSONObject temp = new JSONObject();
					temp.put("positionName", posInfo.getName());
					temp.put("personNumber", getCurrLocationsByPos(posInfo.getId(), 0));
					result.add(temp);
				}
			}
		}
		
		String json2return = result.toString();
		try {
			json2return = new String(json2return.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {

		}
		return json2return;
	}
	
	@RequestMapping(params="floors")
	@ResponseBody
	public String getFloorInfos(HttpServletRequest request) {
		String buildingIdString = request.getParameter("buildingId");
		int buildingId = 0;
		try {
			buildingId = Integer.parseInt(buildingIdString);
		} catch (NumberFormatException e) {
			
		}

		JSONArray result = new JSONArray();
		if (buildingId > 0) {
			PositionService service = PositionService.getInstance();
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("parentId", buildingId);
			List<Object> buildingInfos = service.getListByCondition(queryMap);
			if (buildingInfos != null && buildingInfos.size() > 0) {
				for (Object obj : buildingInfos) {
					PositionEntity posInfo = (PositionEntity) obj;
					if (posInfo != null) {
						JSONObject temp = new JSONObject();
						temp.put("positionName", posInfo.getName());
						temp.put("personNumber", getCurrLocationsByPos(0, posInfo.getId()));
						result.add(temp);
					}
				}
			}
		}
		
		String json2return = result.toString();
		try {
			json2return = new String(json2return.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {

		}
		return json2return;
	}
	
//	@RequestMapping(params="floorDetail")
//	@ResponseBody
//	public String getFloorDetail(HttpServletRequest request) {
//		String floorIdString = request.getParameter("floorId");
//		int floorId = 0;
//		try {
//			floorId = Integer.parseInt(floorIdString);
//		} catch (NumberFormatException e) {
//			
//		}
//		if (floorId > 0) {
//			
//		}
//	}
}
