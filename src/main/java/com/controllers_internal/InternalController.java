package com.controllers_internal;

import java.util.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;


import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.service_internal.Scoping;

import com.entity_internal.member;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;


import com.entity_internal.basicdata;
import com.entity_internal.coadata;
import com.entity_internal.processdata;
import com.entity_internal.roledata;
import com.entity_internal.teamdata;


import com.service_internal.internalwork;
import com.service_internal.unitedwork;
import com.service_internal.currentmanageservice;

@Controller
@RequestMapping
public class InternalController {
	


	@Autowired
	private internalwork internalwork;

	@Autowired
	private unitedwork uniwork;
	
	@Autowired
	private currentmanageservice currentmanageservice;

    @Autowired
    private Scoping scoping;
	
	/*
    private AuthService authService;
    
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }
   */

    @Autowired
    public InternalController() {
    }

    /*
    @GetMapping("/register/loginForm")
    public String form(Model model) {
    	System.out.println(123);
    	System.out.println(1239090);
        //@PathVariable("name") String name
        model.addAttribute("currentgrade", currentmanageservice.currentgrade());

    	return "login";
    }*/

    @GetMapping("/internal/explanation1")
    public String home(Model model, HttpServletRequest request) {
    	
    	/*
    	try{
    		int level = (Integer) session.getAttribute("level");
    		System.out.println("레벨있음");

    	} catch (Exception e) {
        	

    	}*/
    	HttpSession session = request.getSession();
    	String ip= request.getRemoteAddr();

    	session.setAttribute("ip", ip);
    	session.setAttribute("level", 0);
    	session.setAttribute("scoping", "미완성");
		System.out.println(session.getAttribute("scoping"));
    	
    	int level = (Integer) session.getAttribute("level");

    	model.addAttribute("currentgrade", currentmanageservice.currentgrade(level));
      return "/internal/explanation1";
    }


    @GetMapping("/internal/currentview")
    public String currentview(Model model, HttpServletRequest request) {
    	
    	HttpSession session2 = request.getSession();
    	int level = (Integer) session2.getAttribute("level");
    	model.addAttribute("currentlevel", level);
    	model.addAttribute("currentgrade", currentmanageservice.currentgrade(level));
    	
    	
    	// 아래것 추가해야함
    	if(level == 0) {
    		
    		JSONObject json = new JSONObject();
    		json.put("scoping", session2.getAttribute("scoping"));
        	model.addAttribute("currentarr", json);
    	}else if(level == 1) {
        	
    		JSONObject json = new JSONObject();
    		json.put("구매", session2.getAttribute("구매"));
        	model.addAttribute("currentarr", json);
        	
    	}else {
        	model.addAttribute("currentarr", currentmanageservice.currentview(level));
    		
    	}
    	
      return "/internal/currentview";
    }

    

    
	@GetMapping("/internal2/subwindow")
    public String getsubwindow(
    		Model model, HttpSession session, 
    		HttpServletResponse response, HttpServletRequest request) {
		
    	HttpSession session2 = request.getSession();
    	int level = (Integer) session2.getAttribute("level");

		model.addAttribute("currentgrade", currentmanageservice.currentgrade(level));
    	  System.out.println("subwin");
         return "/internal2/subwindow";
    }
    
	@GetMapping("/internal/basestructure")
    public  String baseinventorymake(
    		HttpSession session, HttpServletResponse response, SessionStatus sessionStatus,
    		Model model) {
    	
		//uniwork.test();
		model.addAttribute("currentgrade", currentmanageservice.currentgrade(0));
	      return "/internal/basestructure";
	}

	@GetMapping("/internal/basemapping")
    public  String basemapping(
    		HttpSession session, HttpServletResponse response, SessionStatus sessionStatus,
    		Model model, HttpServletRequest request) {
		
    	HttpSession session2 = request.getSession();
    	int level = (Integer) session2.getAttribute("level");

		model.addAttribute("currentgrade", currentmanageservice.currentgrade(level));
	      return "/internal/basemapping";
	}
	
	/*
    @GetMapping("/internal/explanation1")
    public String explanation1(Model mav, HttpServletRequest request) {
    	
    	HttpSession session2 = request.getSession();
    	int level = (Integer) session2.getAttribute("level");

		mav.addAttribute("currentgrade", currentmanageservice.currentgrade(level));
      	
      return "/internal/explanation1";
    }*/
	

    @GetMapping("/internal/explanation2")
    public String explanation2(Model mav, HttpServletRequest request) {
    	
    	HttpSession session2 = request.getSession();
    	int level = (Integer) session2.getAttribute("level");

		mav.addAttribute("currentgrade", currentmanageservice.currentgrade(level));
      	
      return "/internal/explanation2";
    }
    @GetMapping("/internal/explanation3")
    public String explanation3(Model mav, HttpServletRequest request) {
    	
    	HttpSession session2 = request.getSession();
    	int level = (Integer) session2.getAttribute("level");

		mav.addAttribute("currentgrade", currentmanageservice.currentgrade(level));
      	
      return "/internal/explanation3";
    }
    @GetMapping("/internal/explanation4")
    public String explanation4(Model mav, HttpServletRequest request) {
    	
    	HttpSession session2 = request.getSession();
    	int level = (Integer) session2.getAttribute("level");

		mav.addAttribute("currentgrade", currentmanageservice.currentgrade(level));
      	
      return "/internal/explanation4";
    }
    @GetMapping("/internal/explanation5")
    public String explanation5(Model mav, HttpServletRequest request) {
    	
    	HttpSession session2 = request.getSession();
    	int level = (Integer) session2.getAttribute("level");

		mav.addAttribute("currentgrade", currentmanageservice.currentgrade(level));
      	
      return "/internal/explanation5";
    }

    
    @GetMapping("/internal/gojs")
    public String gojs(Model model, HttpServletRequest request) {
    	
    	HttpSession session2 = request.getSession();
    	int level = (Integer) session2.getAttribute("level");

    	model.addAttribute("currentgrade", currentmanageservice.currentgrade(level));
      return "gojs";
    }
    
    @GetMapping("/internal/gojs9")
    public String gojs5(Model model, HttpServletRequest request) {
      
      System.out.println("여기는 왔니");
      ArrayList<String> processlist = internalwork.findwork("회계팀");
      System.out.println("여기는 어때");
      
  	HttpSession session2 = request.getSession();
  	int level = (Integer) session2.getAttribute("level");

      model.addAttribute("currentgrade", currentmanageservice.currentgrade(level));
      model.addAttribute("processlist", processlist);
      return "/internal/gojs9";
    }

    @GetMapping("/internal/gojs_work")
    public String gojs6(Model model, HttpServletRequest request) {
      
      System.out.println("여기는 왔니");
      
      //member user = userContext.getCurrentUser();
      Set<String> processlist = internalwork.findwork_person();
      System.out.println("여기는 어때");
  	HttpSession session2 = request.getSession();
  	int level = (Integer) session2.getAttribute("level");

      model.addAttribute("currentgrade", currentmanageservice.currentgrade(level));
      model.addAttribute("processlist", processlist);
      return "/internal/gojs_work";
    }


    @GetMapping("/internal/basequestion")
    public String basequestion(Model model, HttpServletRequest request) {
    	
    	HttpSession session2 = request.getSession();
    	int level = (Integer) session2.getAttribute("level");

    	model.addAttribute("currentgrade", currentmanageservice.currentgrade(level));
      return "/internal/basequestion";
    }

    @GetMapping("/internal/weakpoint")
    public String weakpoint(Model model, HttpServletRequest request) {
    	
    	HttpSession session2 = request.getSession();
    	int level = (Integer) session2.getAttribute("level");

    	model.addAttribute("currentgrade", currentmanageservice.currentgrade(level));
      return "/internal/weakpoint";
    }    
    
    
    // 로그인 이후에만 되도록 세션 검증 등 추가해야 함
    @RequestMapping("/internal/Scoping")
    public String Scoping(Model model, HttpServletRequest request) {
    	
    	//member user = userContext.getCurrentUser();
    	HttpSession session2 = request.getSession();
    	int level = (Integer) session2.getAttribute("level");

    	model.addAttribute("currentgrade", currentmanageservice.currentgrade(level));
    	
    	int number = 1;
    	/*
    	for(roledata role : user.getRoledata()) {
    		if(role.getRole().equals("회계팀원") == true) {
    			number = 1;
    			break;
    		}else if(role.getRole().equals("회계팀장") == true) {
    			number = 2;
    			break;
    		}
    	}
    	System.out.println(user.getRoledata().toString());
    	System.out.println(number);
    	*/
    	
    	if(number == 1) {
    		
    		// 만약 현재 단계가 1단계가 아니면 넘길것
    		
    		int grade = currentmanageservice.getLevel0Repository().findByRealname("Scoping").get(0).getRealgrade();  
   
    		System.out.println(grade);
    		/* 이렇게 되면, 스코핑 한번 해버리면 더 이상 스코핑 못하기 때문에 일단 취소함
   		
    		if(grade != 1) {
        		return "loginsuccess";
        	}
    		*/
    		
    		// 회계팀원인 경우
        	
            model.addAttribute("oppositecoa_bs", internalwork.getoppositecoa("BS"));
            model.addAttribute("oppositecoa_is", internalwork.getoppositecoa("IS"));
            model.addAttribute("coaarray_bs", internalwork.getcoaarray("BS"));
            model.addAttribute("coaarray_is", internalwork.getcoaarray("IS"));

            model.addAttribute("coaprocessmap", internalwork.getcoaprocessmap());
            model.addAttribute("teamteam1map", internalwork.getteamteam1map());
            model.addAttribute("teamteam2map", internalwork.getteamteam2map());
            model.addAttribute("processmap", internalwork.getprocessmap());

            model.addAttribute("processteammap", internalwork.getprocessteammap());
        	return "/internal/Scoping";
    		
    	}else if(number == 2) {

    		int grade = currentmanageservice.getLevel0Repository().findByRealname("Scoping").get(0).getRealgrade();  
    		System.out.println(grade);
    		
    		/* 이렇게 되면, 스코핑 한번 해버리면 더 이상 스코핑 못하기 때문에 일단 취소함
        	if(grade == 1) {
        		return "loginsuccess";
        	}
    		*/
    		
    		// 회계팀장인 경우
    		
    		System.out.println(scoping.findparameter());
    		model.addAttribute("scoping", scoping.findall());
    		model.addAttribute("parameter", scoping.findparameter());
    		return "/internal/Scoping_confirm";
    		
    	}
    	
        return null;
    }
    
    
    /*
    @PostMapping("/register/login")
    public String submit(
    		Errors errors, HttpSession session,
    		HttpServletResponse response, Model model) {
    	
    	model.addAttribute("currentgrade", currentmanageservice.currentgrade());
    	
        if (errors.hasErrors()) {
            return "login/loginForm";
        } 

            return "survey/surveyForm";
       
    }*/

    @PostMapping("/internal/processsubmit")
    public String processsubmit(
    		@RequestParam Map<String, String> data, HttpSession session,
    		HttpServletRequest request, HttpServletResponse response, Model model) 
            throws UnsupportedEncodingException {
    	
    	HttpSession session2 = request.getSession();
    	int level = (Integer) session2.getAttribute("level");

    	model.addAttribute("currentgrade", currentmanageservice.currentgrade(level));
      	request.setCharacterEncoding("UTF-8");
      	//response.setContentType("text/html;charset=UTF-8");

        // 여기서 나중에 프론트 데이터 적정성 검사하는 코드 입력할 것
      	
     	
      	// 프론트 데이터 저장하기
    	for(String pro : data.keySet()) {
    		System.out.println(pro);
        	
    		// table 테이터에 프로세스 명으로 끌어오는데, process명_hidden, process명_기타옵션 등으로 계속 추가 구별해 나갈 것이므로
    		// 아래에 process명만 끌어오기 위하여 조건문 사용
    		if(pro.contains("_hidden") != true) {
        		// 프로세스 저장
        		processdata process = new basicdata();
            	process.setname(pro);
              
            	// 팀 저장
        		String[] arrayParam = request.getParameterValues(pro);
              	for (int i = 0; i < arrayParam.length; i++) {
              		
              		teamdata team = new teamdata();
              		
              		team.setTeamname(arrayParam[i]);
              	    process.addteamdata(team);
              	}
              	
              	// coa 저장
              	coadata coa = new coadata();
              	//coa.setname(request.getParameter(pro + "_hidden"));
              	//process.setcoadata(coa);

            	internalwork.save(process);
      		}
        	
    	}

    	
    	// 어떤 화면 띄울지 고민하자
    	return "/internal/inputsuccess";
    }	
    
    
    @RequestMapping("/internal/loginSuccess")
    public String submit2(Model model, HttpServletRequest request) {
    	
    	HttpSession session2 = request.getSession();
    	int level = (Integer) session2.getAttribute("level");

    	model.addAttribute("currentgrade", currentmanageservice.currentgrade(level));
    	return "/internal/loginsuccess";
    }
    
    /*
    @RequestMapping("/register/realform")
    public String signup(Model model) {
    	model.addAttribute("currentgrade", currentmanageservice.currentgrade());
        return "realform";
    }
 */
    

    
    
    // 파일 업로드 처리하기
	@RequestMapping(value = "/internal/upload_organization", method = RequestMethod.POST)
	public String upload(@RequestParam("uploadFile") MultipartFile file,
			     Model model, HttpServletRequest request) throws IllegalStateException, IOException {
		
    	HttpSession session2 = request.getSession();
    	int level = (Integer) session2.getAttribute("level");

		model.addAttribute("currentgrade", currentmanageservice.currentgrade(level));
		String filename = file.getOriginalFilename();
		System.out.println(filename);
		String path = System.getProperty("user.dir"); 
		
		String FILE_PATH = path + "/upload";
		if(!file.getOriginalFilename().isEmpty()) {
			
			file.transferTo(new File(FILE_PATH, filename));
			model.addAttribute("msg", "File uploaded successfully.");
			model.addAttribute("fileName", filename);
		
		}else{
		
			model.addAttribute("msg", "Please select a valid mediaFile..");
		}
		
		return "/internal/basemapping";
	}    
    
    // 파일 다운로드 처리하기
	
	
	@RequestMapping("/internal/download_organization")
	@ResponseBody
	public byte[] download(HttpServletResponse response,
			      @RequestParam String filename, Model model, HttpServletRequest request) throws IOException{
		
    	HttpSession session2 = request.getSession();
    	int level = (Integer) session2.getAttribute("level");

		model.addAttribute("currentgrade", currentmanageservice.currentgrade(level));
		String path = System.getProperty("user.dir"); 
		
		String FILE_PATH = path + "/download";
		
		File file = new File(FILE_PATH, "조직도입력.xlsx");
		
		byte[] bytes = FileCopyUtils.copyToByteArray(file);
		
		String fn = new String(file.getName().getBytes("utf-8"), "ISO-8859-1");
		System.out.println(fn);

		response.setContentType("application/octet-stream;charset=utf-8");

		response.setHeader("Content-Disposition", "attachment;filename=\"" + fn + "\"");
		response.setContentLength(bytes.length);
		
		
		
		return bytes;
	}
	
	
	/*
	@RequestMapping(value ="/view/download_organization")
	public ResponseEntity<byte[]> downloadTest(@RequestHeader(name = HttpHeaders.USER_AGENT) String userAgent){

		String path = System.getProperty("user.dir"); 
		
		String FILE_PATH = path + "/download";
		
		File file = new File(FILE_PATH, "조직도입력.xlsx");
		
		byte[] bytes = FileCopyUtils.copyToByteArray(file);
		
		HttpHeaders guavaHeader = new HttpHeaders();	
	
	  guavaHeader.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	  guavaHeader.set("Content-Disposition", "attachment; filename=" + new String("하이.txt".getBytes(), StandardCharsets.ISO_8859_1));
	  return new ResponseEntity<byte[]>(file, guavaHeader, HttpStatus.OK);	
	  
	}
	*/
	
}
