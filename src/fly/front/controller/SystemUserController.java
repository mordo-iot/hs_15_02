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
import com.framework.system.util.Md5Utils;

import fly.entity.user.UserEntity;
import fly.front.entity.UserInfo;
import fly.front.tools.Datetools;
import fly.service.user.UserService;

/**
 * 系统用户
 * @author WongYong
 */
@Scope("prototype")
@Controller
@RequestMapping("/user")
public class SystemUserController {

	/**
	 * 列出系统用户
	 * @return 对应视图
	 */
	@RequestMapping(params="list")
	public ModelAndView listUsers() {
		return new ModelAndView("userManager");
	}
	
	@RequestMapping(params="page")
	@ResponseBody
	public String getPage(HttpServletRequest request) {
		String pageNumber = request.getParameter("pageNumber");
		int currentpage;
		final int pagesize = 12;
		
		try {
			currentpage = Integer.parseInt(pageNumber);
			if (currentpage < 1) {
				currentpage = 1;
			}
		} catch (NumberFormatException e) {
			currentpage = 1;
		}
		
		UserService service = UserService.getInstance();
		PageList pl = service.getListByCondition(null, currentpage, pagesize);
		
		
		if (pl == null && currentpage > 1) {
			pl = service.getListByCondition(null, 1, pagesize);
			if (pl != null) {
				pl = service.getListByCondition(null, pl.getPageCount(), pagesize);
			}
		}
		
		int totalpage = 1;
		int totalitem = 0;
		ArrayList<UserInfo> userInfos = new ArrayList<UserInfo>();
		
		if (pl != null) {
			totalpage = pl.getPageCount();
			if (totalpage > 0 && currentpage > totalpage) {
				currentpage = totalpage;
				pl = service.getListByCondition(null, currentpage, pagesize);
				if (pl != null) {
					totalpage = pl.getPageCount();
				}
			}
			
			totalitem = pl.getRecordCount();
			List<Object> resultList = pl.getResultList();
			if (resultList != null && resultList.size() > 0) {
				for (Object obj : resultList) {
					UserEntity user = (UserEntity) obj;
					UserInfo info = new UserInfo();
					info.setLastLogin(Datetools.formateDate(user.getLstlogindate()));
					if (user.getPermissions() == 1) {
						info.setPermission("管理员");
					} else {
						info.setPermission("普通用户");
					}
					info.setRegTime(Datetools.formateDate(user.getCreatedate()));
					info.setUserId(user.getId());
					info.setUserName(user.getName());
					
					if (user != null) {
						userInfos.add(info);
					}
				}
			}
		}

		while (userInfos.size() < 12) {
			userInfos.add(new UserInfo(0, "", "", "", ""));
		}
		
		String paginhtml = "";
		paginhtml = "";
		if (currentpage > 1) {
			paginhtml += "<a onclick=\"gotopage('" + (currentpage - 1) + "');\"><b>&lt;&lt;</b></a>";
		} else {
			paginhtml += "<a style=\"display:none\"><b>&lt;&lt;</b></a>";
		}
		
		
		int startindex = currentpage;
		int endindex = currentpage;
		if (totalpage > 7) {
			startindex = currentpage;
			endindex = currentpage;
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
			if (i == currentpage) {
				paginhtml += "<a onclick=\"gotopage('" + i + "');\" class=\"selectedpage\">" + i + "</a>";
			} else {
				paginhtml += "<a onclick=\"gotopage('" + i + "');\">" + i + "</a>";
			}
		}
		if (currentpage < totalpage) {
			paginhtml += "<a onclick=\"gotopage('" + (currentpage + 1) + "');\"><b>&gt;&gt;</b></a>";
		} else {
			paginhtml += "<a style=\"display:none\"><b>&gt;&gt;</b></a>";
		}
		paginhtml += "";
		
		JSONObject result = new JSONObject();
		result.put("paginhtml", paginhtml);
		result.put("totalpage", totalpage);
		result.put("totalitem", totalitem);
		result.put("userInfos", JSONArray.fromObject(userInfos));
		result.put("currentuser", request.getSession().getAttribute("username"));
		
		String json2return = result.toString();
		
		try {
			json2return = new String(json2return.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {

		}
		
		return json2return;
	}
	
	/**
	 * 添加或修改系统用户
	 * @return 对应视图
	 */
	@RequestMapping(params="adduser")
	@ResponseBody
	public String addUser(HttpServletRequest request) {
		String userid = request.getParameter("userid");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String permission = request.getParameter("permission");

		String addResult = "";
		
		UserService service = UserService.getInstance();
		boolean result;
		

		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("name", username);
		List<Object> users = service.getListByCondition(queryMap);
		
		if (userid == null || "".endsWith(userid.trim())) {  //添加操作
			if (users != null && users.size() > 0) {
				addResult = "用户名已存在";
			} else {
				UserEntity user = new UserEntity();
				user.setName(username);
				user.setPassword(Md5Utils.MD5(password));
				user.setPermissions(Integer.parseInt(permission));
				user.setCreatedate(Datetools.getCurrentDate());
				result = service.save(user);
				if (result) {
					addResult = "操作成功";
				} else {
					addResult = "操作失败";
				}
			}
		} else {  //修改操作
			Integer userId = 0;
			try {
				userId = Integer.parseInt(userid);
				if (userId > 0) {
					boolean haveSameUsername = false;
					if (users != null && users.size() > 0) {
						for (Object obj : users) {
							UserEntity user = (UserEntity) obj;
							if (user != null && user.getId().intValue() != userId.intValue()) {
								haveSameUsername = true;
							}
						}
					}
					if (haveSameUsername) {
						addResult = "用户名已存在";
					} else {
						UserEntity user = service.getById(userId, false);
						user.setName(username);
						user.setPermissions(Integer.parseInt(permission));
						result = service.save(user);
						if (result) {
							addResult = "操作成功";
						} else {
							addResult = "操作失败";
						}
					}
				} else {
					addResult = "错误的用户ID";
				}
			} catch (NumberFormatException e) {
				addResult = "错误的用户ID";
			}
			
		}
		
		
		try {
			addResult = new String(addResult.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {

		}
		
		return addResult;
	}
	
	/**
	 * 删除用户
	 * @param userid
	 * @return
	 */
	@RequestMapping(params="del")
	@ResponseBody
	public String delUser(HttpServletRequest request) {
		String userid = request.getParameter("userId");
		String delresult = "";
		
		UserService service = UserService.getInstance();
		Integer userId = 0;
		
		try {
			userId = Integer.parseInt(userid);
			boolean result = service.del(userId, false);
			if (result) {
				delresult = "删除成功";
			} else {
				delresult = "删除失败";
			}
		} catch (NumberFormatException e) {
			delresult = "错误的用户ID";
		}
		
		try {
			delresult = new String(delresult.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {

		}
		
		return delresult;
	}
	
	/**
	 * 重置用户密码为默认
	 * @param userid 要重置密码的用户id
	 * @return 对应视图
	 */
	@RequestMapping(params="resetpasswd")
	@ResponseBody
	public String resetPassword(HttpServletRequest request) {
		String operresult = "";
		
		UserService service = UserService.getInstance();
		String userid = request.getParameter("userId");
		Integer userId = 0;
		try {
			userId = Integer.parseInt(userid);
			UserEntity info = service.getById(userId, false);
			if (info != null) {
				info.setPassword(Md5Utils.MD5("123456"));
				boolean result = service.save(info);
				if (result) {
					operresult = "重置成功";
				} else {
					operresult = "重置失败";
				}
			}
		} catch (NumberFormatException e) {
			operresult = "未找到指定的用户信息";
		}
		
		try {
			operresult = new String(operresult.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {

		}
		
		return operresult;
	}
}
