package com.controllers;

import java.util.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.net.*;
import java.io.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.security.core.context.SecurityContextHolder;
import com.auth.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.servlet.ModelAndView;


import com.service.memberService;
import com.service.xlmake;

import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.service.memberService;

//import com.service.UserContext;

import com.model.SignupForm;

import org.springframework.beans.factory.annotation.Autowired;
import java.io.UnsupportedEncodingException;




import com.service.companywork;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;


import org.aspectj.lang.annotation.Aspect;


@Controller
@RequestMapping
public class CompanyController {
	
    private static final Logger logger = LoggerFactory
            .getLogger(CompanyController.class);

	@Autowired
	private memberService memberService;
	
	
	
	@Autowired
	private companywork companywork;

	@Autowired
    private final UserContext userContext;
	
	/*
    private AuthService authService;
    
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }
   */

    @Autowired
    public CompanyController(memberService memberService, UserContext userContext
    		) {
        if (userContext == null) {
            throw new IllegalArgumentException("userContext cannot be null");
        }
        if (memberService == null) {
            throw new IllegalArgumentException("memberService cannot be null");
        }
        this.userContext = userContext;
        this.memberService = memberService;
        
    }
    
    
    @GetMapping("/company/company")
    public String company(Model model) {
//    	System.out.println("why why why");
      return "/company/search";
    }

    @GetMapping("/company/purpose")
    public String purpose(Model model) {
    	
    	
      return "/company/purpose";
    }
    
    @GetMapping("/company/search")
    public String second(Model model, HttpSession httpSession) {
//    	System.out.println("why why why");
    	//member member = userContext.getCurrentUser();
    	
    	//System.out.println(member.getEmail());
    	//System.out.println(member.getname());
 
    	
      return "/company/search";
    }
    @GetMapping("/company/explanation")
    public String search(Model model) {
    	//System.out.println("why why why");
      return "/company/explanation";
    }
 
    
    
    @GetMapping("/company/stock")
    public String stock(Model model) {
    	//System.out.println("comin coming");
      return "/company/stock";
    }

    
    @GetMapping("/company/simulation")
    public String simulation(Model model) {
    	//System.out.println("comin coming");
      return "/company/simulation";
    }
    
}
