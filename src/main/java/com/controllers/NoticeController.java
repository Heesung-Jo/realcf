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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.security.core.context.SecurityContextHolder;
import com.auth.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.web.servlet.ModelAndView;

import com.entity.memberdata;
import com.service.memberService;
import com.service.xlmake;

import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.service.memberService;
import java.time.*;
import com.entity.memberdata;
//import com.service.UserContext;

import com.model.SignupForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import java.io.UnsupportedEncodingException;



import com.except.WrongIdPasswordException;

import java.time.*;
import java.time.format.DateTimeFormatter;

import com.service.mywork;
import com.service.companywork;
import com.service.coaarray;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import com.entity.boarddata;
import com.repository.BoarddataRepository;

import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import org.aspectj.lang.annotation.Aspect;

@Controller
@RequestMapping
@SessionAttributes("member")
public class NoticeController {
	
    private static final Logger logger = LoggerFactory
            .getLogger(NoticeController.class);

	@Autowired
	private memberService memberservice;

	@Autowired
	private LocalValidatorFactoryBean localValidator;

	@Autowired
	private BoarddataRepository boarddatarepository;

	
	
    private final UserContext userContext;

	private final memberService memberService;
	
	/*
    private AuthService authService;
    
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }
   */

    @Autowired
    public NoticeController(memberService memberService, UserContext userContext
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



    
    
    @GetMapping("/notice/login")
    public String form(LoginCommand loginCommand) {
    	System.out.println(123);
    	System.out.println(1239090);
        //@PathVariable("name") String name
    	return "/notice/login";
    }

    /*
 // ????????? ?????? oauth2 ???????????? ????????? ??????
    @GetMapping("/notice/login")
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {

        logger.info("******login(error): {} ***************************************", error);
        logger.info("******login(logout): {} ***************************************", logout);

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addObject("message", "You've been logged out successfully.");
        }
        model.setViewName("/notice/login");

        return model;

    }

    */
    
    // ????????? ?????? oauth2 ???????????? ????????? ??????
    
    /*
    @PostMapping("/notice/login")
    public String submit(
    		LoginCommand loginCommand, Errors errors, HttpSession session,
    		HttpServletResponse response) {
    	
    	System.out.println(123);
    	System.out.println(loginCommand.getusername());
    	System.out.println(errors);
    	
        if (errors.hasErrors()) {
            return "/notice/loginForm";
        } 
        try {
        	
            return "survey/surveyForm";
        } catch (WrongIdPasswordException e) {
            errors.reject("idPasswordNotMatching");
            return "/notice/loginForm";
        }
    }
    */
  
    
    @RequestMapping("/notice/loginSuccess")
    public String submit2(Model model, OAuth2AuthenticationToken authentication) {
        
    	memberdata member = userContext.getCurrentUser();
    	model.addAttribute("name", member.getname());
    	
    	return "/notice/loginsuccess";
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    
    @GetMapping("/notice/write")
    public String writeshow(Model model) {
    	System.out.println("????????????");
    	memberdata member = userContext.getCurrentUser();
      	model.addAttribute("member", member);
    	return "/notice/write";
    }    
 
    
    
    @PostMapping("/notice/write")
    public String write(boarddata boarddata, BindingResult result, @ModelAttribute("member") memberdata member,
    		SessionStatus status, Model model) {
    	System.out.println("???????????????");
    	

    	
    	String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    	System.out.println(member.getEmail());
    	boarddata.settimerecord(time);
    	boarddata.setmemberdata(member);
    	localValidator.validate(boarddata, result);
    	
    	//System.out.println(member.getEmail());
    	if(result.hasErrors()) {
    		System.out.println(result);
    		return "/notice/write";
    	}
    	
    	boarddatarepository.save(boarddata);
    	status.setComplete();
    	
    	System.out.println("????????????");
    	
    	return "redirect:/notice/show";
    }
    
    @RequestMapping("/notice/logout")
    public String logout(Model model) {
    	SecurityContextHolder.getContext().setAuthentication(null);
    	System.out.println("???????????????????????????.");
    	return "redirect:/notice/login";
    }
    @RequestMapping("/notice/notice")
    public String notice(Model model) {
       	memberdata member = userContext.getCurrentUser();
    	model.addAttribute("name", member.getname());
    	
    	return "/notice/loginsuccess";    }


    @GetMapping("/notice/show")
    public String showopt( @PageableDefault(sort = {"timerecord"}, size = 20, direction = Sort.Direction.DESC) Pageable pageable, Model model, 
    		HttpServletRequest request) {
     	
    	System.out.println("?????? ?????????.");
    	
    	Page<boarddata> page = boarddatarepository.findAll(pageable);
      	model.addAttribute("boardlist", page);
    	System.out.println("?????? ?????????2.");      	
      	int last = page.getNumber() + 4;
      	if(page.getNumber() + 5 > page.getTotalPages() - 1) {
      		last = Math.min(page.getTotalPages() - 1, page.getNumber() + 4);
      	}
      	model.addAttribute("lastpage", last);
    	System.out.println("?????? ?????????3.");     	
      	return "/notice/show";
    } 
 
    @RequestMapping("/notice/showdetail")
    public String showdetail(Model model, @RequestParam("id") Long id) {
        
    	
    	System.out.println("???????????? ??????????????????.");
    	boarddata boarddata = boarddatarepository.findByid(id);
    	model.addAttribute("boarddata", boarddata);
    	memberdata member = boarddata.getmemberdata();
      	model.addAttribute("member", member);
    	return "/notice/showdetail";
    }
    
    @GetMapping("/")
    public String defaultpage(Model model) {
    	return "/notice/purpose";
    } 
    
    @GetMapping("/notice/purpose")
    public String showpurpose(Model model) {
    	return "/notice/purpose";
    }     
    
}
