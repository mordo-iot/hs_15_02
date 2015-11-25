package fly.front.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import roadinfo.api.RoadGenerator;

import fly.entity.position.PositionEntity;
import fly.front.entity.PositionInfo;
import fly.service.position.PositionService;

@Controller
@Scope("prototype")
@RequestMapping("/mapconfig")
public class MapConfigController {

	@RequestMapping(params="show")
	public ModelAndView currentAlarm(HttpServletRequest request, HttpServletResponse response, String positionId) {
		Integer role = (Integer) request.getSession().getAttribute("role");
		if (role == null || role != 1) {
			return new ModelAndView("currentAlarm");
		} else {
			ModelAndView mav = new ModelAndView("mapConfig");
			if (positionId != null && positionId.length() > 0) {
				mav.addObject("positionId", positionId);
			}
			return mav;
		}
	}
	
	@RequestMapping(params = "pic")
	@ResponseBody
	public String uploadPic(@RequestParam("currentpos") String currentpos, HttpServletRequest request) {
		String result = "";
		
		int posId = -1;
		try {
			posId = Integer.parseInt(currentpos);
		} catch (NumberFormatException e) {
			
		}
		
		if (posId >= 0) {
			// 创建一个通用的多部分解析器
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			// 判断 request 是否有文件上传,即多部分请求
			if (multipartResolver.isMultipart(request)) {
				// 转换成多部分request
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				// 取得request中的所有文件名
				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					// 取得上传文件
					MultipartFile file = multiRequest.getFile(iter.next());
					if (file != null) {
						// 取得当前上传文件的文件名称
						String myFileName = file.getOriginalFilename();
						// 如果名称不为"",说明该文件存在，否则说明该文件不存在
						if (myFileName.trim() != "") {
							System.out.println(myFileName);
							// 重命名上传后的文件名
							String fileName = "maps/" + new Date().getTime() + file.getOriginalFilename();
							// 定义上传路径
							String path = request.getSession().getServletContext().getRealPath("/") + fileName;
							System.out.println(path);
							File localFile = new File(path);
							try {
								file.transferTo(localFile);
								PositionService service = PositionService.getInstance();
								PositionEntity entity = service.getById(posId, false, false);
								if (entity != null) {
									entity.setPhoto(fileName);
									boolean saveresult = service.save(entity);
									if (saveresult) {
										result = fileName;
									}
								}
							} catch (IllegalStateException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		} else {
			
		}
		
		return result;
	}
	
	@RequestMapping(params = "delmap")
	@ResponseBody
	public String delMap(HttpServletRequest request) {
		String result = "";
		
		String positionId = request.getParameter("positionId");
		int posId = -1;
		try {
			posId = Integer.parseInt(positionId);
		} catch (NumberFormatException e) {
			
		}
		
		if (posId >= 0) {
			PositionService service = PositionService.getInstance();
			PositionEntity entity = service.getById(posId, false, false);
			if (entity != null) {
				String filepath = request.getSession().getServletContext().getRealPath("/") + entity.getPhoto();
				try {
					File file = new File(filepath);
					if (file.exists()) {
						file.delete();
					}
				} catch (Exception e) {
					System.out.println("文件删除失败：【" + filepath + "】");
				}
				
				entity.setPhoto("");
				boolean saveResult = RoadGenerator.save(posId, "", "") && service.save(entity);
				if (saveResult) {
					result = "操作成功";
				} else {
					result = "操作成功";
				}
			}
		} else {
			result = "未能取得位置信息";
		}
		
		try {
			result = new String(result.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			
		}
		
		return result;
	}
	
	@RequestMapping(params="positionmap")
	@ResponseBody
	public String getPositionData4Map(HttpServletRequest request) {
		String positionId = request.getParameter("positionId");
		JSONObject resultObj = new JSONObject();
		
		Integer posid = 0;
		
		try {
			posid = Integer.parseInt(positionId);
		} catch (NumberFormatException e) {
			
		}
		PositionService service = PositionService.getInstance();
		
		if (posid > 0) {
			PositionEntity posinfo = service.getById(posid, false, true);
			if (posinfo != null) {
				ArrayList<PositionInfo> parents = new ArrayList<PositionInfo>();
				PositionInfo tempparent = new PositionInfo(posinfo.getId(), posinfo.getName(), posinfo.getPhoto());
				resultObj.put("parentPosition", JSONObject.fromObject(tempparent));
				parents.add(tempparent);
				
				if (posinfo.getParentPosition() != null) {
					PositionInfo parentPosition = new PositionInfo(posinfo.getParentPosition().getId(), posinfo.getParentPosition().getName());
					parents.add(0, parentPosition);
					if (parentPosition.getPositionId() > 0) {
						PositionEntity temp = service.getById(posinfo.getParentPosition().getParentId(), false, false);
						while (true) {
							if (temp != null) {
								PositionInfo temppos = new PositionInfo(temp.getId(), temp.getName());
								parents.add(0, temppos);
								temp = service.getById(temp.getParentId(), false, false);
							} else {
								break;
							}
						}
					}
				}
				
				JSONArray parentsArray = JSONArray.fromObject(parents);
				resultObj.put("parents", parentsArray);
			} else {
				PositionInfo parentPosition = new PositionInfo(0, "所有位置");
				resultObj.put("parentPosition", JSONObject.fromObject(parentPosition));
			}
			
			String roadstring = RoadGenerator.read(posid);
			JSONArray tempPoints = new JSONArray();
			ArrayList<String> currentPoints = new ArrayList<String>();
			if (roadstring != null && roadstring.length() > 0) {
				JSONObject jso = JSONObject.fromObject(roadstring);  //读取路网信息
				if (jso != null) {
					if (jso.containsKey("locate")) {
						JSONArray locates = null;
						try {
							locates = JSONArray.fromObject(jso.getString("locate"));
						} catch (JSONException e) {
							
						}
						PositionService posService = PositionService.getInstance();
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
						}
						resultObj.put("locate", locates.toString());
						System.out.println("locate\n" + locates.toString());
					}
					if (jso.containsKey("road")) {
						JSONArray roads = null;
						try {
							roads = JSONArray.fromObject(jso.getString("road"));
						} catch (JSONException e) {
							
						}
						if (roads != null) {
							for (int i = 0; i < roads.size(); i++) {
								JSONObject path = roads.getJSONObject(i);
								if (path != null) {
									if (path.containsKey("x1") && path.containsKey("y1") && path.containsKey("x2") && path.containsKey("y2")) {
										String tempPointinfoString = "x:" + path.getInt("x1") + ",y:" + path.getInt("y1");
										boolean contains = false;
										for (String point : currentPoints) {
											if (point.equals(tempPointinfoString)) {
												contains = true;
												break;
											}
										}
										if (!contains) {
											currentPoints.add(tempPointinfoString);
											JSONObject tempPointInfo = new JSONObject();
											tempPointInfo.put("x", path.getInt("x1"));
											tempPointInfo.put("y", path.getInt("y1"));
											tempPoints.add(tempPointInfo);
										}
										
										tempPointinfoString = "x:" + path.getInt("x2") + ",y:" + path.getInt("y2");
										contains = false;
										for (String point : currentPoints) {
											if (point.equals(tempPointinfoString)) {
												contains = true;
												break;
											}
										}
										if (!contains) {
											currentPoints.add(tempPointinfoString);
											JSONObject tempPointInfo = new JSONObject();
											tempPointInfo.put("x", path.getInt("x2"));
											tempPointInfo.put("y", path.getInt("y2"));
											tempPoints.add(tempPointInfo);
										}
									}
								}
							}
							resultObj.put("temploc", tempPoints.toString());
						}
						System.out.println("road\n" + jso.getString("road"));
						resultObj.put("road", jso.getString("road"));
					}
				}
			}
		} else {
			PositionInfo parentPosition = new PositionInfo(0, "所有位置");
			resultObj.put("parentPosition", JSONObject.fromObject(parentPosition));
		}
		
		if (!resultObj.containsKey("positionInfos")) {
			resultObj.put("positionInfos", new JSONArray());
		}
		if (!resultObj.containsKey("parents")) {
			resultObj.put("parents", new JSONArray());
		}
		
		String json2return = resultObj.toString();
		
		try {
			json2return = new String(json2return.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			
		}
		
		return json2return;
	}
	
	@RequestMapping(params="choose")
	@ResponseBody
	public String getPostions4choose(HttpServletRequest request) {
		String parentPosition = request.getParameter("parentPosition");
		String pageNumber = request.getParameter("pageNumber");
		final int pageSize = 10;  //每页数据条目数
		
		int parentId = -1;
		try {
			parentId = Integer.parseInt(parentPosition);
		} catch (Exception e) {
			
		}

		PositionService service = PositionService.getInstance();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		ArrayList<PositionInfo> allPositions = new ArrayList<PositionInfo>();
		
		List<Object> list = service.getListByCondition(queryMap);  //全部查出来
		if (list != null && list.size() > 0) {
			if (parentId > 0) {
				ArrayList<Integer> parentids = new ArrayList<Integer>();
				parentids.add(parentId);
				while (parentids.size() > 0) {
					int tempParent = parentids.get(0);
					parentids.remove(0);
					for (Object obj : list) {
						if (obj != null) {
							PositionEntity pos = (PositionEntity) obj;
							if (pos != null && pos.getParentId() != null && pos.getParentId() == tempParent) {
								allPositions.add(new PositionInfo(pos.getId(), pos.getName()));
								if (pos.getId() != null) {
									 parentids.add(pos.getId());
								}
							}
						}
					}
				}
			}
		} else {
			for (Object obj : list) {
				if (obj != null) {
					PositionEntity pos = (PositionEntity) obj;
					if (pos != null) {
						allPositions.add(new PositionInfo(pos.getId(), pos.getName()));
					}
				}
			}
		}
		

		ArrayList<PositionInfo> positions = new ArrayList<PositionInfo>();
		
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
		
		totalitem = allPositions.size();
		if (totalitem > 0 && totalitem % pageSize == 0) {
			totalpage = totalitem / pageSize;
		} else {
			totalpage = totalitem / pageSize + 1;
		}
		
		for (int i = (pageno - 1) * pageSize; i < pageno * pageSize && i < totalitem; i++) {
			positions.add(allPositions.get(i));
		}
		
		while (positions.size() < 10) {
			positions.add(new PositionInfo(0, ""));
		}
		
		String paginhtml = "";
		paginhtml = "<span class=\"Pagin1\">";
		if (pageno > 1) {
			paginhtml += "<a onclick=\"getPosition4bind('" + (pageno - 1) + "');\"><b>&lt;&lt;</b></a>";
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
				paginhtml += "<a onclick=\"getPosition4bind('" + i + "');\" class=\"selectedpage\">" + i + "</a>";
			} else {
				paginhtml += "<a onclick=\"getPosition4bind('" + i + "');\">" + i + "</a>";
			}
		}
		if (pageno < totalpage) {
			paginhtml += "<a onclick=\"getPosition4bind('" + (pageno + 1) + "');\"><b>&gt;&gt;</b></a>";
		} else {
			paginhtml += "<a style=\"display:none\"><b>&gt;&gt;</b></a>";
		}
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
	
	@RequestMapping(params="saveConfig")
	@ResponseBody
	public String saveMapOptions(HttpServletRequest request) {
		String result = "";
		String mapId = request.getParameter("mapId");
		String locate = request.getParameter("locate");
		String road = request.getParameter("road");
		
		int map = 0;
		try {
			map = Integer.parseInt(mapId);
		} catch (NumberFormatException e) {
			
		}
		
		if (map > 0) {
			if (locate == null) {
				locate = "";
			}
			if (road == null) {
				road = "";
			}
			locate = locate.replace(",]", "]");
			road = road.replace(",]", "]");
			boolean saveresult = RoadGenerator.save(map, locate, road);
			if (saveresult) {
				result = "保存成功";
			} else {
				result = "保存失败";
			}
		} else {
			result = "错误的目标地图";
		}
		
		try {
			result = new String(result.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {

		}
		
		return result;
	}
	
}
