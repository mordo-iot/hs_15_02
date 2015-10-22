package fly.front.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Scope("prototype")
@Controller
@RequestMapping("/map")
public class MapController {
	
	@RequestMapping(params="show")
	public ModelAndView showMap() {
		return new ModelAndView("map");
	}
	
	@RequestMapping(params="test")
	@ResponseBody
	public String getMapTest() {
		return "you got it";
	}

}
