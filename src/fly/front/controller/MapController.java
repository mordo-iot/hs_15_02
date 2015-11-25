package fly.front.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import roadinfo.api.RoadGenerator;

import fly.entity.currentLocation.CurrentLocationEntity;
import fly.entity.position.PositionEntity;
import fly.front.entity.PositionInfo;
import fly.front.entity.PositionPersonNumber;
import fly.front.entity.map.AnimatePointInfo;
import fly.front.entity.map.LocationOnMap;
import fly.front.entity.map.PointInfo;
import fly.front.entity.map.PointState;
import fly.service.currentLocation.CurrentLocationService;
import fly.service.position.PositionService;

@Scope("prototype")
@Controller
@RequestMapping("/map")
public class MapController {
	
	@RequestMapping(params="show")
	public ModelAndView showMap() {
		return new ModelAndView("map");
	}
	
	@RequestMapping(params="outdoor")
	public ModelAndView showBaiduMap() {
		return new ModelAndView("baiduMap");
	}
	
	@RequestMapping(params="building")
	@ResponseBody
	public String getBuildings() {
		JSONArray positions = new JSONArray();
		PositionService posService = PositionService.getInstance();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("parentId", 0);
		List<Object> postions = posService.getListByCondition(queryMap);
		if (postions != null && postions.size() > 0) {
			for (Object obj : postions) {
				PositionEntity pos = (PositionEntity) obj;
				if (pos != null) {
					PositionInfo posinfo = new PositionInfo(pos.getId(), pos.getName());
					positions.add(JSONObject.fromObject(posinfo));
				}
			}
		}
		
		String json2return = positions.toString();
		try {
			json2return = new String(json2return.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			
		}
		return json2return;
	}

	@RequestMapping(params="floor")
	@ResponseBody
	public String getFloors(String buildingId) {
		int building = 0;
		try {
			building = Integer.parseInt(buildingId);
		} catch (NumberFormatException e) {
			
		}

		JSONArray positions = new JSONArray();
		
		if (building > 0) {
			PositionService posService = PositionService.getInstance();
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("parentId", building);
			List<Object> postions = posService.getListByCondition(queryMap);
			if (postions != null && postions.size() > 0) {
				for (Object obj : postions) {
					PositionEntity pos = (PositionEntity) obj;
					if (pos != null) {
						PositionInfo posinfo = new PositionInfo(pos.getId(), pos.getName());
						positions.add(JSONObject.fromObject(posinfo));
					}
				}
			}
		}
		
		String json2return = positions.toString();
		try {
			json2return = new String(json2return.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			
		}
		return json2return;
	}
	
	@RequestMapping(params="mapinfo")
	@ResponseBody
	public String getMapInfo(String positionId) {
		int posId = 0;
		
		String mapurl = "";  //地图地图的url
		String locateString = "";  //地图位置点
		
		try {
			posId = Integer.parseInt(positionId);
		} catch (NumberFormatException e) {
			
		}
		
		if (posId > 0) {
			PositionService posService = PositionService.getInstance();
			PositionEntity entity = posService.getById(posId, false, false);
			if (entity != null) {
				mapurl = entity.getPhoto();
			}

			ArrayList<String> currentPoints = new ArrayList<String>();
			String mapconfig = RoadGenerator.read(posId);
			if (mapconfig != null && mapconfig.length() > 0) {
				JSONObject jso = null;
				try {
					jso = JSONObject.fromObject(mapconfig);
				} catch (JSONException e) {
					
				}
				if (jso != null) {
					if (jso.containsKey("locate")) {
						JSONArray locates = null;
						try {
							locates = JSONArray.fromObject(jso.getString("locate"));
						} catch (JSONException e) {
							
						}
						if (locates != null) {
							for (int i = 0; i < locates.size(); i++) {
								JSONObject loc = locates.getJSONObject(i);
								if (loc != null) {
									if (loc.containsKey("x") && loc.containsKey("y")) {
										String tempPointinfoString = "x:" + loc.getInt("x") + ",y:" + loc.getInt("y");
										currentPoints.add(tempPointinfoString);
									}
									if (loc.containsKey("posId")) {
										try {
											int locId = loc.getInt("posId");
											if (locId > 0) {
												PositionEntity pos = posService.getById(locId, false, false);
												if (pos != null && pos.getName() != null) {
													loc.put("posName", pos.getName());
												}
											}
										} catch (Exception e) {
											
										}
									}
								}
							}
							locateString = locates.toString();
						}
					}
				}
			}
		}
		
		JSONObject resultObj = new JSONObject();
		resultObj.put("mapurl", mapurl);
		resultObj.put("locate", locateString);
		
		String json2return = resultObj.toString();
		try {
			json2return = new String(json2return.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			
		}
		return json2return;
	}
	
	@RequestMapping(params="buildingdetail")
	@ResponseBody
	public String getBuildingDetail(String buildingId) {
		String result = "";
		
		int building = 0;
		try {
			building = Integer.parseInt(buildingId);
		} catch (NumberFormatException e) {
			
		}
		
		List<Object> postions = PositionService.getInstance().getListByCondition(null);
		ArrayList<PositionEntity> allPos = new ArrayList<PositionEntity>();
		if (postions != null && postions.size() > 0) {
			ArrayList<PositionPersonNumber> details = new ArrayList<PositionPersonNumber>();
			for (Object obj : postions) {
				PositionEntity pos = (PositionEntity) obj;
				if (pos != null) {
					allPos.add(pos);
					if (pos.getParentId() == building) {
						details.add(new PositionPersonNumber(pos, 0));
					}
				}
			}
			
			List<Object> locs = CurrentLocationService.getInstance().getListByCondition(null);
			if (locs != null && locs.size() > 0) {
				for (Object obj : locs) {
					CurrentLocationEntity locInfo = (CurrentLocationEntity) obj;
					if (locInfo != null) {
						Integer posId = locInfo.getPositionId();
						if (posId != null && posId > 0) {
							int temp = posId;
							while (true) {
								boolean founded = false;
								for (PositionPersonNumber entry : details) {
									if (entry.getPosinfo().getId() == temp) {
										entry.setPersonNumber(entry.getPersonNumber() + 1);
										founded = true;
										break;
									}
								}
								
								if (founded) {
									break;
								}
								
								boolean haveparent = false;
								for (PositionEntity temppos : allPos) {
									if (temppos.getId() == temp) {
										temp = temppos.getParentId();
										haveparent = true;
										break;
									}
								}
								
								if (haveparent) {
									if (temp == 0) {
										break;
									} else {
										continue;
									}
								} else {
									break;
								}
							}
						}
					}
				}
			}
			
			JSONArray resultArray = new JSONArray();
			for (PositionPersonNumber entry : details) {
				JSONObject temp = new JSONObject();
				temp.put("positionName", entry.getPosinfo().getName());
				temp.put("personNumber", entry.getPersonNumber());
				resultArray.add(temp);
			}
			result = resultArray.toString();
		}
		
		try {
			result = new String(result.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			
		}
		
		return result;
	}
	
	/**
	 * 根据位置信息、前状态获取新位置信息
	 * @param positionId  位置信息，PositionEntity的Id
	 * @param lastState 当前地图定位到的老人的信息
	 * @return JSON格式字串，最新的老人位置信息，移动动画的描述
	 */
	@RequestMapping(params="loc")
	@ResponseBody
	public String getLocDetail(String positionId, String lastState) {
		System.out.println("function getLocDetail start with arg positionId:" + positionId + ", lastState:" + lastState);
		JSONObject result = new JSONObject();
		JSONArray beginArray = new JSONArray();
		JSONArray animateArray = new JSONArray();
		JSONArray endArray = new JSONArray();
		JSONArray stateArray = new JSONArray();
		
		int mapId = 0;
		try {
			mapId = Integer.parseInt(positionId);
		} catch (NumberFormatException e) {
			
		}
		
		if (mapId > 0) {
			//读取已配置的地图信息
			String mapconfig = RoadGenerator.read(mapId);
			JSONObject mapconfigInfo = null;
			try {
				mapconfigInfo = JSONObject.fromObject(mapconfig);
			} catch (JSONException e) {
				
			}
			
			ArrayList<LocationOnMap> savedLocations = new ArrayList<LocationOnMap>();  //保存的地图配置中，定位点的信息
			String idsContained = "";  //本地图上的定位点，包括的posId
			
			//解析保存的点信息
			if (mapconfigInfo != null && mapconfigInfo.containsKey("locate")) {
				JSONArray savedJsonLocates = JSONArray.fromObject(mapconfigInfo.getString("locate"));
				for (int i = 0; i < savedJsonLocates.size(); i++) {
					JSONObject tempLoc = savedJsonLocates.getJSONObject(i);
					if (tempLoc != null && tempLoc.containsKey("posId") && tempLoc.containsKey("x") && tempLoc.containsKey("y")) {
						LocationOnMap loc = new LocationOnMap();
						loc.setPosId(tempLoc.getInt("posId"));
						loc.setX(tempLoc.getInt("x"));
						loc.setY(tempLoc.getInt("y"));
						savedLocations.add(loc);
						idsContained += loc.getPosId() + ",";
					}
				}
			}
			
			//获取当前定位信息
			ArrayList<CurrentLocationEntity> currentInfos = new ArrayList<CurrentLocationEntity>();
			if (idsContained.length() > 0) {  //地图有配置定位点时，才有意义
				idsContained = idsContained.substring(0, idsContained.length() - 1);  //去掉最后一个逗号
				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("positionId_in", idsContained);  //只查找定位到posId属于地图的定位信息
				List<Object> currentInfoTemp = CurrentLocationService.getInstance().getListByCondition(queryMap, null, true, false);
				if (currentInfoTemp != null && currentInfoTemp.size() > 0) {  //地图上有定位信息时才有意义
					for (Object obj : currentInfoTemp) {
						CurrentLocationEntity currentinfo = (CurrentLocationEntity) obj;
						if (currentinfo != null) {
							currentInfos.add(currentinfo);
						}
					}
				}
			}
			
			ArrayList<PointState> lastPointState = new ArrayList<PointState>();
			if (currentInfos.size() > 0) {  //有定位信息时，才有意义
				//解析前一次状态
				JSONArray lastStateJson = null;
				try {
					lastStateJson = JSONArray.fromObject(lastState);
				} catch (JSONException e) {
					
				}
				if (lastStateJson != null) {
					for (int i = 0; i < lastStateJson.size(); i++) {
						JSONObject tempState = lastStateJson.getJSONObject(i);
						if (tempState != null && tempState.containsKey("devId") && tempState.containsKey("locationId")) {
							PointState temp = new PointState();
							temp.setDevId(tempState.getInt("devId"));
							temp.setLocationId(tempState.getInt("locationId"));
							lastPointState.add(temp);
						}
					}
				}
				
				ArrayList<PointInfo> beginList = new ArrayList<PointInfo>();
				ArrayList<PointInfo> endList = new ArrayList<PointInfo>();
				ArrayList<PointState> currentPointState = new ArrayList<PointState>();
				ArrayList<AnimatePointInfo> animateInfos = new ArrayList<AnimatePointInfo>();

				//生成初始状态(前状态中没有的，或者位置没变的)
				//生成末状态(所有点)
				//生成当前状态
				//生成动画状态（前状态中有的，且位置改变的点）
				for (CurrentLocationEntity tempPos : currentInfos) {  //遍历当前定位位置信息
					boolean existInLast = false;  //是否存在于前状态
					boolean positionChanged = false;  //位置是否改变
					
					//生成动画状态
					for (PointState tempState : lastPointState) {  //遍历之前一次的状态
						if (tempState.getDevId() == tempPos.getDevId()) {
							if (tempState.getLocationId() != tempPos.getPositionId()) {
								positionChanged = true;
								//生成动画状态（前状态中有的，且位置改变的点）
								boolean existAnimate = false;
								for (AnimatePointInfo tempAnimate : animateInfos) {
									if (tempAnimate.getStartLocationId() == tempState.getLocationId() && tempAnimate.getEndLocationId() == tempPos.getPositionId()) {
										existAnimate = true;
										tempAnimate.getDevInfo().add(tempPos.getDev());
										break;
									}
								}
								if (!existAnimate) {
									AnimatePointInfo tempAnimate = new AnimatePointInfo();
									tempAnimate.setStartLocationId(tempState.getLocationId());
									tempAnimate.setEndLocationId(tempPos.getPositionId());
									tempAnimate.getDevInfo().add(tempPos.getDev());
									animateInfos.add(tempAnimate);
								}
							}
							existInLast = true;
							break;
						}
					}
					
					//生成初始状态(前状态中没有的，或者位置没变的)
					if (existInLast == false || positionChanged == false) {
						boolean alreadyInBegin = false;
						for (PointInfo tempPoint : beginList) {
							if (tempPoint.getLocationInfo().getPosId() == tempPos.getPositionId()) {
								alreadyInBegin = true;
								tempPoint.getDevInfo().add(tempPos.getDev());
								break;
							}
						}
						if (!alreadyInBegin) {
							LocationOnMap lom = new LocationOnMap();
							for (LocationOnMap tempLocation : savedLocations) {
								if (tempLocation.getPosId() == tempPos.getPositionId()) {
									lom = tempLocation;
									break;
								}
							}
							PointInfo tempPoint = new PointInfo();
							tempPoint.setLocationInfo(lom);
							tempPoint.getDevInfo().add(tempPos.getDev());
							beginList.add(tempPoint);
						}
					}
					
					//生成末状态(所有点)
					boolean alreadyInEnd = false;
					for (PointInfo tempPoint : endList) {
						if (tempPoint.getLocationInfo().getPosId() == tempPos.getPositionId()) {
							alreadyInEnd = true;
							tempPoint.getDevInfo().add(tempPos.getDev());
							break;
						}
					}
					if (!alreadyInEnd) {
						LocationOnMap lom = new LocationOnMap();
						for (LocationOnMap tempLocation : savedLocations) {
							if (tempLocation.getPosId() == tempPos.getPositionId()) {
								lom = tempLocation;
								break;
							}
						}
						PointInfo tempPoint = new PointInfo();
						tempPoint.setLocationInfo(lom);
						tempPoint.getDevInfo().add(tempPos.getDev());
						endList.add(tempPoint);
					}
					
					//生成状态
					PointState tempState = new PointState(tempPos.getDevId(), tempPos.getPositionId());
					currentPointState.add(tempState);
				}
				
				//初始状态对象序列化
				for (PointInfo temp : beginList) {
					beginArray.add(temp.toJson());
				}
				
				//动画事件序列化
				for (AnimatePointInfo temp : animateInfos) {
					animateArray.add(temp.toJson(mapId));
				}
				
				//结束状态对象序列化
				for (PointInfo temp : endList) {
					endArray.add(temp.toJson());
				}
				
				//结束状态记录（下次对比用）
				stateArray = JSONArray.fromObject(currentPointState);
			}
		}
		
		
		result.put("begin", beginArray);  //初态
		result.put("animate", animateArray);  //动画
		result.put("end", endArray);  //末态
		result.put("state", stateArray);  //当前状态
		
		String json2return = result.toString();
		System.out.println("json2return is:" + json2return);
		try {
			json2return = new String(json2return.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			
		}
		
		return json2return;
	}
}
