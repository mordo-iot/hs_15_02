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

import com.framework.system.db.query.PageList;

import fly.entity.dev.DevEntity;
import fly.entity.position.PositionEntity;
import fly.front.entity.DevInfo4PositionPage;
import fly.front.entity.DeviceInfo;
import fly.front.entity.PositionInfo;
import fly.front.tools.Datetools;
import fly.front.tools.DeviceTools;
import fly.service.dev.DevService;
import fly.service.position.PositionService;

@Scope("prototype")
@Controller
@RequestMapping("/position")
public class PositionController {

	@RequestMapping(params="show")
	public ModelAndView showPositionpage() {
		return new ModelAndView("position");
	}
	
	@RequestMapping(params="position")
	@ResponseBody
	public String getDataInPosition(HttpServletRequest request) {
		String positionId = request.getParameter("positionId");
		JSONObject resultObj = new JSONObject();
		
		Integer posid = 0;
		
		try {
			posid = Integer.parseInt(positionId);
		} catch (NumberFormatException e) {
			
		}
		PositionService service = PositionService.getInstance();
		
		//获取当前位置下的所有位置信息
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("parentId", posid);
		List<Object> subposList = service.getListByCondition(queryMap);
		if (subposList != null && subposList.size() > 0) {
			ArrayList<PositionInfo> positionInfos = new ArrayList<PositionInfo>();
			for (Object obj : subposList) {
				PositionEntity temp = (PositionEntity) obj;
				if (temp != null) {
					PositionInfo info = new PositionInfo(temp.getId(), temp.getName());
					positionInfos.add(info);
				}
			}
			JSONArray positionArray = JSONArray.fromObject(positionInfos);
			resultObj.put("positionInfos", positionArray);
		}
		
		ArrayList<DeviceInfo> devs = new ArrayList<DeviceInfo>();
		
		if (posid > 0) {
			PositionEntity posinfo = service.getById(posid, true, true);
			if (posinfo != null) {
				ArrayList<PositionInfo> parents = new ArrayList<PositionInfo>();
				PositionInfo tempparent = new PositionInfo(posinfo.getId(), posinfo.getName());
//				mav.addObject("parentPosition", tempparent);
				resultObj.put("parentPosition", JSONObject.fromObject(tempparent));
				parents.add(tempparent);
				
				if (posinfo.getDevList() != null && posinfo.getDevList().size() > 0) {
					for (DevEntity dev : posinfo.getDevList()) {
						DeviceInfo devinfo = new DeviceInfo();
						if (dev.getName() != null) {
							devinfo.setDevName(dev.getName());
						} else {
							devinfo.setDevName("");
						}
						Integer devType = 0;
						try {
							devType = Integer.parseInt(dev.getType());
						} catch (NumberFormatException e) {
							
						}
						devinfo.setDevType(DeviceTools.getDeviceTypeNameByTypeId(devType));
						devinfo.setDevMac(dev.getCode());
						devs.add(devinfo);
					}
				}
				
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
				
//				mav.addObject("parents", parents);
				JSONArray parentsArray = JSONArray.fromObject(parents);
				resultObj.put("parents", parentsArray);
			} else {
				PositionInfo parentPosition = new PositionInfo(0, "所有位置");
//				mav.addObject("parentPosition", parentPosition);
				resultObj.put("parentPosition", JSONObject.fromObject(parentPosition));
			}
		} else {
			if (posid == 0) {
				queryMap = new HashMap<String, Object>();
				queryMap.put("positionId", posid);
				List<Object> infos = DevService.getInstance().getListByCondition(queryMap);
				if (infos != null && infos.size() > 0) {
					for (Object obj : infos) {
						DevEntity dev = (DevEntity) obj;
						if (dev != null) {
							DeviceInfo devinfo = new DeviceInfo();
							devinfo.setDevName(dev.getName());
							Integer devType = 0;
							try {
								devType = Integer.parseInt(dev.getType());
							} catch (NumberFormatException e) {
								
							}
							devinfo.setDevType(DeviceTools.getDeviceTypeNameByTypeId(devType));
							devinfo.setDevMac(dev.getCode());
							devs.add(devinfo);
						}
					}
				}
			}
			
			PositionInfo parentPosition = new PositionInfo(0, "所有位置");
//			mav.addObject("parentPosition", parentPosition);
			resultObj.put("parentPosition", JSONObject.fromObject(parentPosition));
		}
		
		if (devs.size() > 0) {
			ArrayList<DevInfo4PositionPage> list4positon = new ArrayList<DevInfo4PositionPage>();
			for (DeviceInfo info : devs) {
				String typeName = info.getDevType();
				boolean existType = false;
				for (DevInfo4PositionPage temp : list4positon) {
					if (temp.getDevType() != null && temp.getDevType().equals(typeName)) {
						existType = true;
						temp.getDevList().add(info);
						break;
					}
				}
				if (!existType) {
					DevInfo4PositionPage temp = new DevInfo4PositionPage();
					temp.setDevType(typeName);
					ArrayList<DeviceInfo> templist = new ArrayList<DeviceInfo>();
					
					if (info.getDevName() != null) {
						if (info.getDevName().length() > 11) {
							info.setDevName(info.getDevName().substring(0, 11) + "...");
						}
					} else {
						info.setDevName("");
					}
					
					templist.add(info);
					temp.setDevList(templist);
					list4positon.add(temp);
				}
			}
//			mav.addObject("devs", list4positon);
			resultObj.put("devs", JSONArray.fromObject(list4positon));
		}
		
		if (!resultObj.containsKey("positionInfos")) {
			resultObj.put("positionInfos", new JSONArray());
		}
		if (!resultObj.containsKey("parents")) {
			resultObj.put("parents", new JSONArray());
		}
		if (!resultObj.containsKey("devs")) {
			resultObj.put("devs", new JSONArray());
		}
		
		String json2return = resultObj.toString();
		
		try {
			json2return = new String(json2return.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			
		}
		
		return json2return;
	}
	
	@RequestMapping(params="del")
	@ResponseBody
	public String delPosition(HttpServletRequest request) {
		String positionId = request.getParameter("positionId");
		
		String result = "删除成功";
		
		Integer posid = 0;
		
		try {
			posid = Integer.parseInt(positionId);
		} catch (NumberFormatException e) {
			
		}
		
		if (posid > 0) {
			List<Integer> ids2del = new ArrayList<Integer>();
			List<Integer> temp = new ArrayList<Integer>();
			ids2del.add(posid);
			temp.add(posid);
			
			PositionService posService = PositionService.getInstance();

			while (temp.size() > 0) {
				Integer tempid = temp.get(0);
				temp.remove(0);
				
				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("parentId", tempid);
				List<Object> poslist = posService.getListByCondition(queryMap);
				if (poslist != null && poslist.size() > 0) {
					for (Object obj : poslist) {
						PositionEntity item = (PositionEntity) obj;
						if (item != null && item.getId() != null && item.getId() > 0) {
							ids2del.add(item.getId());
							temp.add(item.getId());
						}
					}
				}
			}
			
			//删除所有位置
			Map<String, Object> queryMap = new HashMap<String, Object>();
			String ids4del = "";
			for (Integer id : ids2del) {
				ids4del += id + ",";
			}
			if (ids4del.length() > 0) {
				ids4del = ids4del.substring(0, ids4del.length() - 1);
			}
			queryMap.put("id_in", ids4del);
			boolean delresult = posService.delList(queryMap, true, false);
			if (delresult) {
				result = "删除成功";
			} else {
				result = "删除失败";
			}
		} else {
			result = "要删除的位置信息错误";
		}
		
		
		try {
			result = new String(result.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			
		}
		
		return result;
	}
	
	/**
	 * 添加位置
	 * @param parent 父节点位置信息
	 * @param name 位置名称
	 * @return
	 */
	@RequestMapping(params="add")
	@ResponseBody
	public String addPosition(HttpServletRequest request) {
		String parent = request.getParameter("parent");
		String name = request.getParameter("posname");
		
		String des = "";
		
		try {
			Integer parentId = Integer.parseInt(parent);
			PositionEntity pos2add = new PositionEntity();
			pos2add.setName(name);
			pos2add.setParentId(parentId);
			pos2add.setCreatedate(Datetools.getCurrentDate());
			
			if (parentId >= 0) {
				if (name != null && (!"".equals(name.trim()))) {  //空校验
					if (name.length() <= 200) {  //长度校验
						PositionService service = PositionService.getInstance();
						Map<String, Object> queryMap = new HashMap<String, Object>();
						queryMap.put("parentId", parentId);
						queryMap.put("name", name);
						List<Object> items = service.getListByCondition(queryMap);
						if (items != null && items.size() > 0) {
							des = "该层已有同名位置";
						} else {
							boolean result = service.save(pos2add);
							if (result) {
								des = "保存成功";
							} else {
								des = "保存失败";
							}
						}
					} else {
						des = "位置名称过长";
					}
				} else {
					des = "位置名称不能为空";
				}
			} else {
				des = "上层位置信息错误";
			}
		} catch (NumberFormatException e) {
			des = "上层位置信息错误";
		}
		
		try {
			des = new String(des.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			
		}
		
		return des;
	}
	
	@RequestMapping(params="choose")
	@ResponseBody
	public String getPostions4choose(HttpServletRequest request) {
		String positionName = request.getParameter("positionName");
		String pageNumber = request.getParameter("pageNumber");
		
		Map<String, Object> queryMap = new HashMap<String, Object>();
		if (positionName != null && positionName.length() > 0) {
			queryMap.put("name_like", positionName);
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
		
		PositionService service = PositionService.getInstance();
		PageList pl = service.getListByCondition(queryMap, pageno, 10);
		
		
		ArrayList<PositionInfo> positions = new ArrayList<PositionInfo>();
		if (pl != null) {
			if (pl.getResultList() != null && pl.getResultList().size() > 0) {
				for (Object obj : pl.getResultList()) {
					PositionEntity pe = (PositionEntity) obj;
					if (pe != null) {
						PositionInfo info = new PositionInfo(pe.getId(), pe.getName());
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
			positions.add(new PositionInfo(0, ""));
		}
		
		String paginhtml = "";
		paginhtml = "<span class=\"Pagin1\">";
		if (pageno > 1) {
			paginhtml += "<a onclick=\"getPositionInfo('" + positionName + "','" + (pageno - 1) + "');\"><b>&lt;&lt;</b></a>";
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
				paginhtml += "<a onclick=\"getPositionInfo('" + positionName + "','" + i + "');\" class=\"selectedpage\">" + i + "</a>";
			} else {
				paginhtml += "<a onclick=\"getPositionInfo('" + positionName + "','" + i + "');\">" + i + "</a>";
			}
		}
		if (pageno < totalpage) {
			paginhtml += "<a onclick=\"getPositionInfo('" + positionName + "','" + (pageno + 1) + "');\"><b>&gt;&gt;</b></a>";
		} else {
			paginhtml += "<a style=\"display:none\"><b>&gt;&gt;</b></a>";
		}
//		paginhtml += "</span><span class=\"Pagin2\"><input id=\"tarpositionpage\" value=\"1\"/><a onclick=\"getPositionInfo('" + positionName + "', $(\"#tarpositionpage\").val());\"><b>GO</b></a></span>";
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
	
	@RequestMapping(params="list4map")
	@ResponseBody
	public String getPositionList4map() {
		PositionService service = PositionService.getInstance();
		JSONArray positions = new JSONArray();
		List<Object> list = service.getListByCondition(null);
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				PositionEntity pos = (PositionEntity) obj;
				if (pos != null) {
					JSONObject temp = new JSONObject();
					temp.put("positionId", pos.getId());
					temp.put("positionName", pos.getName());
					temp.put("parentId", pos.getParentId());
					positions.add(temp);
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
	
	@RequestMapping(params="update")
	@ResponseBody
	public String updatePositionInfo(HttpServletRequest request) {
		String result = "";
		
		String positionId = request.getParameter("positionId");
		String posName = request.getParameter("positionName");
		
		Integer posId = 0;
		try {
			posId = Integer.parseInt(positionId);
			if (posId > 0) {
				if (posName != null && posName.trim().length() > 0) {
					if (posName.length() > 200) {
						result = "位置名称过长";
					} else {
						PositionService service = PositionService.getInstance();
						PositionEntity entity = service.getById(posId, false, false);
						if (entity != null) {
							entity.setName(posName);
							entity.setUpdatedate(Datetools.getCurrentDate());
							boolean update = service.save(entity);
							if (update) {
								result = "更新成功";
							} else {
								result = "更新失败";
							}
						} else {
							result = "要更新的位置信息不存在";
						}
					}
				} else {
					result = "位置名称不能为空";
				}
			} else {
				result = "错误的位置ID";
			}
		} catch (NumberFormatException e) {
			result = "错误的位置ID";
		}
		
		
		try {
			result = new String(result.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			
		}
		
		return result;
	}
}
