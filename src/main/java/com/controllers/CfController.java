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

import com.service.memberService;




import org.springframework.beans.factory.annotation.Autowired;




import com.service.mywork;


import org.aspectj.lang.annotation.Aspect;


@Controller
@RequestMapping
public class CfController {
	
    private static final Logger logger = LoggerFactory
            .getLogger(CfController.class);

	@Autowired
	private memberService memberservice;
	@Autowired
	private mywork mywork;
	
    private final UserContext userContext;

	private final memberService memberService;
	
	/*
    private AuthService authService;
    
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }
   */

    @Autowired
    public CfController(memberService memberService, UserContext userContext
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

    // ????????? tiles??? ??????
	@GetMapping("/cashflow2/subwindow")
    public String getsubwindow(
    		Model model, HttpSession session, 
    		HttpServletResponse response) {
    	System.out.println("subwin");
         return "/cashflow2/subwindow";
    }

    
    @GetMapping("/cashflow/purpose")
    public String purpose(Model model) {
    	
      return "/cashflow/purpose";
    }
 
    
    
    @GetMapping("/cashflow/cashflow")
    public String home(Model model) {
    	
      return "/cashflow/start";
    }

    @GetMapping("/cashflow/explanation")
    public String explanation(Model mav) {

    	System.out.println(8989898);
    	//SecurityContextHolder.getContext().setAuthentication(null);
    	
    	/* ?????????????????? ????????? ????????? ??????????????? ????????? ???????????????, ????????? ????????? ????????? ??? ???????????????
    	      ??????, ?????????????????? ???????????? ????????? ?????? ?????????, ?????????
		String url = "http://localhost:5000";
		String sb = "";
		try {
			
		  // post ?????? //?????? ?????? ????????? ?????????	
	        Map<String,Object> params = new LinkedHashMap<>(); // ???????????? ??????
	        params.put("name", "james");
	        params.put("email", "james@example.com");
	        params.put("reply_to_thread", 10394);
	        params.put("message", "Hello World");
	 
	        StringBuilder postData = new StringBuilder();
	        for(Map.Entry<String,Object> param : params.entrySet()) {
	            if(postData.length() != 0) postData.append('&');
	            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
	            postData.append('=');
	            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
	        }
	        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
	 
	        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
	        conn.setDoOutput(true);
	        conn.getOutputStream().write(postDataBytes); // POST ??????

	        */
	        
       /* get??????			
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
       */
			
   /*
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

			String line = null;

			while ((line = br.readLine()) != null) {
				sb = sb + line + "\n";
			}
			System.out.println("========br======" + sb.toString());
			if (sb.toString().contains("ok")) {
				System.out.println("test");
				
			}
			br.close();
           
			System.out.println("" + sb.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mav.addAttribute("test", sb.toString()); // "test1"??? jsp???????????? ????????? ??????, 
        						//sb.toString??? value???(???????????? test)
    	
      */
    	
      return "/cashflow/explanation";
    }



    
    
    // ????????? ???????????? ????????? ?????? ?????? ??? ???????????? ???
    @RequestMapping("/cashflow/start")
    public String Scoping(Model model) {

     	
    	return "/cashflow/start";
    }
    
    
    
 
    
    
    
}
