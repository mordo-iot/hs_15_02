package fly.front.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.framework.system.util.Md5Utils;

import fly.entity.user.UserEntity;
import fly.front.entity.LoginResult;
import fly.front.tools.Datetools;
import fly.service.user.UserService;


@Scope("prototype")
@Controller
@RequestMapping("/loginController")
public class LoginController {

	@RequestMapping(params="login")
	public ModelAndView loginpage() {
		ModelAndView mav = new ModelAndView("login");
		return mav;
	}
	
	@RequestMapping(params="logout")
	public ModelAndView logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("username");
		session.removeAttribute("role");
		session.invalidate();
		return loginpage();
	}
	
	@RequestMapping(params="doLogin")
	@ResponseBody
	public String login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request) {
		LoginResult result;
		
		System.out.println("longinController login: username:" + username + ", password:" + password);
		if (isInsertInlaw(username, password)) {
			password = Md5Utils.MD5(password);
			UserService service = UserService.getInstance();
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("name", username);
			queryMap.put("password", password);
			List<Object> results = service.getListByCondition(queryMap);
			if (results != null && results.size() == 1) {
				UserEntity user = (UserEntity) results.get(0);
				if (user != null) {
					user.setLstlogindate(Datetools.getCurrentDate());
					service.save(user);
					result = new LoginResult(true, "登入成功", "", 0);
					request.getSession().setAttribute("username", username);
					request.getSession().setAttribute("role", user.getPermissions());
				} else {
					result = new LoginResult(false, "用户名或密码错误", username, 0);
				}
			} else {
				if (results == null) {
					System.out.println("用户名、密码相符的数据条数为0");
				} else {
					System.out.println("用户名、密码相符的数据条目数为" + results.size());
				}
				result = new LoginResult(false, "用户名或密码错误", username, 0);
			}
		} else {
			result = new LoginResult(false, "用户名或密码不能为空", "", 0);
		}
		
		String json2return = JSONObject.fromObject(result).toString();
		try {
			json2return = new String(json2return.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {

		}
		return json2return;
	}
	
	private boolean isInsertInlaw(String username, String password) {
		if (username == null || password == null || "".equals(username.trim()) || "".equals(password.trim())) {
			return false;
		} else {
			return true;
		}
	}
}
