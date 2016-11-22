// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst 
// Source File Name:   IndexAction.java

package com.yisi.stiku.quartz.web.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

@Controller
@RequestMapping("/quartz")
public class IndexController extends BaseController {

	private Date now;

	public IndexController() {
		now = new Date(System.currentTimeMillis());
	}

	public Date getDateNow() {
		return now;
	}

	@RequestMapping("/index")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext webApplicationContext = ContextLoader
				.getCurrentWebApplicationContext();

		now = new Date(System.currentTimeMillis());
		return "quartz/quartzManager";
	}
}
