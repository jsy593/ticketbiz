package com.jsy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jsy.business.ISystem;
import com.jsy.controller.common.BaseController;


@Controller
public class TestPost extends BaseController{

	
	@Autowired
	private ISystem systemImpl;
	
	@RequestMapping(value="/test",method= RequestMethod.POST)
	public void recivePost(String username){
		System.out.println(username);
		this.writeJson(username);
	}
	
	@RequestMapping(value="/testCreateTabel")//,method= RequestMethod.POST
	public void testCreateTabel(String systemName){
		systemName="fanaer";
		systemImpl.createTableWithSysName(systemName);
	}
}
