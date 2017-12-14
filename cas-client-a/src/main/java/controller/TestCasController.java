package controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/cas")
public class TestCasController {
	 @ResponseBody
    @RequestMapping(value="/test")
	private Map test(){
		Map map = new HashMap();
		map.put("name", "123");
		return map;
	}
}
