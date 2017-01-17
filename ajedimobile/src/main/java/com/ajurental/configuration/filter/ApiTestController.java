package com.ajurental.configuration.filter;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiTestController {
	
	@RequestMapping(value="/mobile/user/{id}/{a}/{b}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> user(@PathVariable String id, @PathVariable String a, @PathVariable String b) throws Exception{
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("Method", "user");
		resultMap.put("name", "Apple");
		resultMap.put("age", "34");
		resultMap.put("ID", id);
		resultMap.put("ID", a);
		resultMap.put("ID", b);
		return resultMap;
	}
	
	@RequestMapping(value="/mobile/test/", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> test() throws Exception{
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("Method", "test");
		resultMap.put("name", "Apple");
		resultMap.put("age", "34");
		return resultMap;
	}
	
	@RequestMapping(value="/mobile/login/", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> login() throws Exception{
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("Method", "login");
		resultMap.put("name", "Apple");
		resultMap.put("age", "34");
		return resultMap;
	}
	
	@RequestMapping(value="/mobile/error/", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> error() throws Exception{
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("Method", "error");
		resultMap.put("name", "Apple");
		resultMap.put("age", "34");
		return resultMap;
	}

	
	@RequestMapping(value="/pc/test/", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> pc() throws Exception{
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("name", "Apple");
		resultMap.put("age", "34");
		
		return resultMap;
		
	}
	
	

}
