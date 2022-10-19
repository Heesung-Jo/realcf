package com.restcontrollers;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import java.io.IOException;
import java.net.URI;
import java.util.stream.Collectors;
import java.util.*;

import org.springframework.http.converter.*;
import org.json.simple.JSONArray;

import org.springframework.web.servlet.*;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.service.mywork;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;


import javax.servlet.ServletRequest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.springframework.web.bind.support.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.entity.accountingdata;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.sym.Name;



import org.aspectj.lang.annotation.Aspect;

import com.service_internal.jsonmake;


@RestController
//@SessionAttributes("process")
public class CfRestController {

	@Autowired
	private mywork mywork;

	@Autowired
	private jsonmake jsonmake;

    
	@PostMapping("/cashflow/controlmethod")
	public ResponseEntity<Object> inputtest(HttpSession session,
		@RequestParam(value="realcoa[]") List<String> realcoa,	HttpServletRequest request) throws ParseException {
	
		ArrayList<String> names = new ArrayList<>();
		ArrayList<String> paras = new ArrayList<>();
 
		System.out.println(realcoa);
		JSONArray arr = new JSONArray();
		JSONObject realarr = new JSONObject();
        realarr = mywork.coatest_flask(realcoa);
        
        
        System.out.println("이제 넘어왔습니다.");
		return ResponseEntity.status(HttpStatus.OK).body(realarr);
	       
	}    

	@PostMapping("/cashflow2/controlmethod")
	public ResponseEntity<Object> inputtest2(HttpSession session,
		@RequestParam(value="realcoa[]") List<String> realcoa,	HttpServletRequest request) throws ParseException {
	
		ArrayList<String> names = new ArrayList<>();
		ArrayList<String> paras = new ArrayList<>();
 
		System.out.println(realcoa);
		JSONArray arr = new JSONArray();
		JSONObject realarr = new JSONObject();
        realarr = mywork.coatest_flask(realcoa);
		return ResponseEntity.status(HttpStatus.OK).body(realarr);
	       
	}  
	
	@PostMapping("/cashflow/sortobj")
	public ResponseEntity<Object> makesortobj(HttpSession session,
			HttpServletRequest request) {
	
		 System.out.println("여기는 제대로 들어왔니");
		HashMap<String, Object> realdata = new HashMap<>();
		realdata.put("sortobj", mywork.getsortobj());
		realdata.put("middlecoa", mywork.getmiddlecoa());
		return ResponseEntity.status(HttpStatus.OK).body(realdata);
	       
	}    
	
	
	@PostMapping("/cashflow/tutorialdata")
	public ResponseEntity<Object> getaccountlist(HttpSession session,
			HttpServletRequest request) throws JsonProcessingException, ParseException {
	
    	System.out.println("tutorialdata");
		
    	List<accountingdata> accountlist = mywork.getaccountlist();
    	
		return ResponseEntity.status(HttpStatus.OK).body(jsonmake.processtojson3(accountlist));
	       
	}    
	
	
	@PostMapping("/cashflow/poolmake")
	public ResponseEntity<Object> getactiondata(HttpSession session, 
			 Model model //, @PathVariable String name
	) {
		
	//System.out.println(name);
    
    
	try {	
		System.out.println("여기는 통과");
		JSONObject process = new JSONObject();

		System.out.println("여기는 통과");
	    
		return ResponseEntity.status(HttpStatus.OK).body(process);

	} catch(Exception e) {
    	  System.out.println(e);
		  return ResponseEntity.status(HttpStatus.OK).body(1909);
	}
    	  
  }
	
	/*
	@PostMapping("/mycontrol/cfcontrol")
	public ResponseEntity<Object> mycontrol(HttpSession session, 
			 Model model //, @PathVariable String name
	) {
		
	//System.out.println(name);
    
    
	try {	
	    mywork.setting();
		return ResponseEntity.status(HttpStatus.OK).body(123);

	} catch(Exception e) {
    	  System.out.println(e);
		  return ResponseEntity.status(HttpStatus.OK).body(1909);
	}
    	  
  }*/
	
	
	
}
