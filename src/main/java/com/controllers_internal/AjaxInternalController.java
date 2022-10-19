package com.controllers_internal;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.stream.Collectors;
import java.util.*;

import org.springframework.http.converter.*;

import org.springframework.web.servlet.*;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.service_internal.internalwork;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import javax.annotation.Nullable;
import javax.servlet.ServletRequest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.springframework.web.bind.support.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.sym.Name;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.entity_internal.Scopingdata;
import com.entity_internal.Scopingdatalist;
import com.entity_internal.basequestion;
import com.entity_internal.basicdata;
import com.entity_internal.coa_process;
import com.entity_internal.member;
import com.entity_internal.uniteddata;
import com.entity_internal.memberlist;
import com.entity_internal.parentnodedata;
import com.entity_internal.processdata;
import com.entity_internal.processoption;
import com.service_internal.Scoping;
import com.service_internal.unitedwork;
import com.service_internal.jsonmake;
import com.service_internal.currentmanageservice;

@RestController
@SessionAttributes("processdata")
public class AjaxInternalController {

	@Autowired
	private internalwork internalwork;

	@Autowired
	private jsonmake jsonmake;
	
	@Autowired
	private unitedwork unitedwork;

	@Autowired
	private currentmanageservice currentmanageservice;
	
	   
	@Autowired
	private Scoping Scoping;

	
	
	
	@PostMapping("/internal/controlmethod")
	public ResponseEntity<Object> inputtest(HttpSession session,
			HttpServletRequest request) {
	
		ArrayList<String> names = new ArrayList<>();
		ArrayList<String> paras = new ArrayList<>();

		for(String name : request.getParameterMap().keySet()) {
			names.add(name);
			paras.add(request.getParameter(name));
	        System.out.println(name);
	        System.out.println(paras);
		}
		
            
        JSONObject data = internalwork.getprocessquery(names, paras);
        System.out.println(data);
		return ResponseEntity.status(HttpStatus.OK).body(data);
	       
	}    

	@PostMapping("/internal/tutorialdata")
	public ResponseEntity<Object> getbalancedata(){
		System.out.println("tutorialdata");
		return ResponseEntity.status(HttpStatus.OK).body(Scoping.getbalancedata());
	}
	
	@PostMapping("/internal/maintext")
	public ResponseEntity<Object> searchrequest(HttpSession session, HttpServletRequest request,
			@ModelAttribute("processdata") processdata process, @RequestParam @Nullable HashMap<String, Object> maintext
    ) throws SQLException, NullPointerException {

		// 211117 
		System.out.println(process.getProcessoption().get(0).getRealname());
		
		System.out.println(process.getControlexplain1());
		
		// 211123 이 사이에 검증 단계를 구현하면 될 듯
		
		internalwork.save(process);
		
		return ResponseEntity.status(HttpStatus.OK).body(1909);
	}	

	
	@PostMapping("/internal/sortedcoa")
	public ResponseEntity<Object> findsortedcoa(HttpSession session, HttpServletRequest request,
			@RequestParam @Nullable HashMap<String, String> coalist
    ) throws SQLException, NullPointerException {

		System.out.println("123123123123");
		// 211123 이 사이에 검증 단계를 구현하면 될 듯
		return ResponseEntity.status(HttpStatus.OK).body(Scoping.findresultcoa());
	}	

	
	@PostMapping("/internal/coaprocess")
	public ResponseEntity<Object> findcoaprocess(HttpSession session, HttpServletRequest request,
			@RequestParam @Nullable HashMap<String, String> coalist
    ) throws SQLException, NullPointerException {

		System.out.println("123123123123");
		// 211123 이 사이에 검증 단계를 구현하면 될 듯
		JSONObject json = new JSONObject();
		for(coa_process coa : Scoping.findcoaprocess()) {
			json.put(coa.getRealcoa(), coa.getProcess());
		}
		return ResponseEntity.status(HttpStatus.OK).body(json);
	}	
	
	
	@PostMapping("/internal/coamake")
	public ResponseEntity<Object> coamake(HttpSession session, HttpServletRequest request,
			@RequestParam @Nullable HashMap<String, String> coalist
    ) throws SQLException, NullPointerException, ParseException {

		HashMap<String, String> result = new HashMap<>();
		System.out.println("123123123123");
		// 211123 이 사이에 검증 단계를 구현하면 될 듯
		

		// 요거 오류없이 잘 되는지 확인하여야 함
		return ResponseEntity.status(HttpStatus.OK).body(Scoping.coatest_bspl(coalist));
	}	
		
	
	
	@PostMapping("/internal/scopingmake")
	public ResponseEntity<Object> scopingmake(HttpSession session, HttpServletRequest request
			//, @ModelAttribute @Nullable Scopingdatalist list // 나중에 이거 필요하게 되면 잘 살릴것
    ) throws SQLException, NullPointerException, ParseException {

		HashMap<String, String> result = new HashMap<>();
		
		session.setAttribute("scoping", "완성");
		session.setAttribute("구매", "미완성");
		
		/* 원래는 이 아래를 수행해야 하나, 데이터베이스를 건들지 않으므로, 위에로 대체함
		System.out.println("123123123123");
		// 211123 이 사이에 검증 단계를 구현하면 될 듯
		List<Scopingdata> coalist = list.getScopingdata();
		
		// coalist에 realcoa와 process와 매핑을 시킬것
		Scoping.coa_pro_map(coalist);
		
		System.out.println("scopingmake1");
		// 그 후 저장할 것
		Scoping.save(coalist);
		
		currentmanageservice.upgrade_scoping("Scoping","Scoping", 2);
		*/
        return ResponseEntity.status(HttpStatus.OK).body("success");
		//return ResponseEntity.status(HttpStatus.OK).body(Scoping.coatest(coalist.keySet()));
	}	
	

	
	
	
	@PostMapping("/internal/listmake")
	public ResponseEntity<Object> listmake(HttpSession session, 
			 Model model //, @PathVariable String name
	) {
		
		System.out.println("listmake");
	//System.out.println(name);
	try {	
		
		//JSONObject process = internalwork.getprocessquery("subprocess", "전표관리");
		// 나중에는 start에 대한것으로 수정이 필요함
		
	    //member user = userContext.getCurrentUser();

		
		List<String> pros = internalwork.findstartlist();
		
		for(String pa : pros) {
			System.out.println(pa);
		}
	    
		return ResponseEntity.status(HttpStatus.OK).body(pros);

	} catch(Exception e) {
    	  System.out.println(e);
		  return ResponseEntity.status(HttpStatus.OK).body(1909);
	}
    	  
  }

	@PostMapping("/internal/listmake_flowchart")
	public ResponseEntity<Object> listmake_flowchart(HttpSession session, 
			 Model model //, @PathVariable String name
	) {
		
	//System.out.println(name);
	try {	
		System.out.println("여기는 통과");
		//JSONObject process = internalwork.getprocessquery("subprocess", "전표관리");
		// 나중에는 start에 대한것으로 수정이 필요함
		
		List<String> pros = internalwork.findstartlist();
		
	    
		return ResponseEntity.status(HttpStatus.OK).body(pros);

	} catch(Exception e) {
    	  System.out.println(e);
		  return ResponseEntity.status(HttpStatus.OK).body(1909);
	}
    	  
  }

	@PostMapping("/internal/baselistmake")
	public ResponseEntity<Object> baselistmake(HttpSession session, 
			 Model model //, @PathVariable String name
	) {
		
    	try {	
    		System.out.println("여기는 통과");
		    Set<String> pros = internalwork.findbasestartlist();

		    
		    return ResponseEntity.status(HttpStatus.OK).body(pros);

    	} catch(Exception e) {
        	System.out.println(e);
		    return ResponseEntity.status(HttpStatus.OK).body(1909);
	    }
    	  
     }

	
	@PostMapping("/internal/baseinventoryconfirm/{subprocess}")
	public ResponseEntity<Object> baseinventoryconfirm(HttpSession session, 
			 Model model, @RequestParam @Nullable HashMap<String, String> stephash, @PathVariable String subprocess
	) {
		
    	try {	
		   
    		System.out.println(stephash);
    		internalwork.baseanswer_step3(subprocess, stephash);
    		
    		return ResponseEntity.status(HttpStatus.OK).body(internalwork.findnextstep("재고자산", "3"));

    	} catch(Exception e) {
        	System.out.println(e);
		    return ResponseEntity.status(HttpStatus.OK).body(1909);
	    }
    	  
     }	
	
	//@PostMapping("/internal/currentview_confirm")
	@PostMapping("/internal/basestructure_list")
	public ResponseEntity<Object> basestructurelistmake(HttpSession session, 
			 Model model //, @PathVariable String name
	) {
		System.out.println("basestructurelistmake");
    	try {	
    		
    		
    		
    		List<JSONObject> pros = internalwork.findstructurebasestartlist(null);
			    
    		return ResponseEntity.status(HttpStatus.OK).body(pros);

    	} catch(Exception e) {
        	System.out.println(e);
		    return ResponseEntity.status(HttpStatus.OK).body(1909);
	    }
    	  
     }	
	@PostMapping("/internal/poolmake")
	public ResponseEntity<Object> getactiondata(HttpSession session, 
			 Model model //, @PathVariable String name
	) {
		
		System.out.println("poolmake");
		//System.out.println(name);
		
	try {	
		String name = "^{인사계획문서}^ 작성";
		
		//JSONObject process = internalwork.getprocessquery("subprocess", "전표관리");
		// 나중에는 start에 대한것으로 수정이 필요함
		
		ArrayList<basicdata> pros = internalwork.findrelatedstart(name);
		
		model.addAttribute("processdata",  pros);
		model.addAttribute("processjson",  jsonmake.processtojson2(pros));
		System.out.println("여기는 통과");
	    
		return ResponseEntity.status(HttpStatus.OK).body(model);

	} catch(Exception e) {
    	  System.out.println(e);
		  return ResponseEntity.status(HttpStatus.OK).body(1909);
	}
    	  
  }

	
	@PostMapping("/internal/basestructure")
	public ResponseEntity<Object> basestructure(HttpSession session, 
			 Model model , @RequestParam @Nullable HashMap<String, String> stephash	
	) throws JsonProcessingException, ParseException {
		//JSONObject process = internalwork.getprocessquery("subprocess", "전표관리");
		// 나중에는 start에 대한것으로 수정이 필요함
		// stephash는 {step0: 재고자산, step1: 공장1} 이런 데이터가 넘어옴
		// 만약 step0까지만 넘어왔다면, {step0: 재고자산, step1: ""} 이렇게 넘어옴
		// 현재 단계의 후순위 데이터를 조회해서 넘겨줘야함. 예를 들면 step0까지만 넘어왔다면 step1의 데이터를 조회해서 넘겨줘야함
		// step2도 처음에 포함했다가, step2 뒤에는 없으므로 받지 않음.
		// 그리고 step0는 answer entity의 mainprocess, step1은 subprocess1과 대응됨
		
		String step0 = stephash.get("step0");
		
		model.addAttribute("arr",  internalwork.findstep0(step0));

		//Set<String> members = memberservice.findall().stream().map(s -> s.getRealname()).collect(Collectors.toSet());
		//model.addAttribute("members",  members);
		
		System.out.println(step0);
		return ResponseEntity.status(HttpStatus.OK).body(model);
    	  
   }

	@PostMapping("/internal/basemapping")
	public ResponseEntity<Object> basemapping(HttpSession session, 
			 Model model , @RequestParam @Nullable HashMap<String, String> data	
	) throws JsonProcessingException, ParseException {

		String str = data.get("data");
		System.out.println(str);
		
		if(str.equals("team")) {
			return ResponseEntity.status(HttpStatus.OK).body(Scoping.findmapping("team"));
		}else if(str.equals("document")) {
			return ResponseEntity.status(HttpStatus.OK).body(Scoping.findmapping("document"));
		}else if(str.equals("system")) {
			return ResponseEntity.status(HttpStatus.OK).body(Scoping.findmapping("system"));
		}else if(str.equals("person")) {
			model.addAttribute("data",  Scoping.findpersonmapping());
			//System.out.println(memberservice.findall());
			
			//model.addAttribute("person",  memberservice.findall());

			return ResponseEntity.status(HttpStatus.OK).body(model);
		}else if(str.equals("leader")) {
			model.addAttribute("data",  Scoping.findpersonmapping());
			//System.out.println(memberservice.findall());
			
			//model.addAttribute("person",  memberservice.findall());

			return ResponseEntity.status(HttpStatus.OK).body(model);
		}
		
		
		return null;
	}
	

	@PostMapping("/internal/basemap_submit/{name}")
	public ResponseEntity<Object> basemap_submit(HttpSession session, @PathVariable String name,
			 Model model , @RequestParam @Nullable HashMap<String, String> data	
	) throws Exception {

		// data의 구조 {"~~~는 무엇인가요": sap, ~~~} 이런 형식임
		
		Scoping.save_basemap(data);
		// 해당 단계 완료 표시하기
		currentmanageservice.upgrade_scoping("basemapping", name, 2);
		
		return ResponseEntity.status(HttpStatus.OK).body("success");
	}

	@PostMapping("/internal/basemap_person")
	public ResponseEntity<Object> basemap_person(HttpSession session, 
			 Model model , @RequestParam @Nullable HashMap<String, String> data	
	) throws Exception {

		// data의 구조 {"~~~는 무엇인가요": sap, ~~~} 이런 형식임
		
		Scoping.save_basemap_person2(data, "person");

		// 해당 단계 완료 표시하기
		currentmanageservice.upgrade_scoping("basemapping", "담당자매핑", 2);

		return ResponseEntity.status(HttpStatus.OK).body("success");
	}

	@PostMapping("/internal/basemap_leader")
	public ResponseEntity<Object> basemap_leader(HttpSession session, 
			 Model model , @RequestParam @Nullable HashMap<String, String> data	
	) throws Exception {

		// data의 구조 {"~~~는 무엇인가요": sap, ~~~} 이런 형식임
		// 저장하기
		Scoping.save_basemap_person(data, "leader");
		
		// 해당 단계 완료 표시하기
		currentmanageservice.upgrade_scoping("basemapping", "팀장매핑", 2);

		return ResponseEntity.status(HttpStatus.OK).body("success");
	}

	
	@PostMapping("/internal/basemap_team")
	public ResponseEntity<Object> basemap_team(HttpSession session, 
			 Model model , @ModelAttribute @Nullable memberlist list
	) throws Exception {

		// data의 구조 {"~~~는 무엇인가요": sap, ~~~} 이런 형식임
		System.out.println(list);
		System.out.println(list.getMember());
		
		    // 저장하기
			Scoping.save_baseteam(list.getMember());

			// 해당 단계 완료 표시하기
			currentmanageservice.upgrade_scoping("basemapping", "조직도입력", 2);
			return ResponseEntity.status(HttpStatus.OK).body("success");
	}
	
	
	@PostMapping("/internal/basepoolmake")
	public ResponseEntity<Object> basepoolmake(HttpSession session, 
			 Model model , @RequestParam @Nullable HashMap<String, String> stephash	
	) throws JsonProcessingException, ParseException {
		//JSONObject process = internalwork.getprocessquery("subprocess", "전표관리");
		// 나중에는 start에 대한것으로 수정이 필요함
		// stephash는 {step0: 재고자산, step1: 공장1} 이런 데이터가 넘어옴
		// 만약 step0까지만 넘어왔다면, {step0: 재고자산, step1: ""} 이렇게 넘어옴
		// 현재 단계의 후순위 데이터를 조회해서 넘겨줘야함. 예를 들면 step0까지만 넘어왔다면 step1의 데이터를 조회해서 넘겨줘야함
		// step2도 처음에 포함했다가, step2 뒤에는 없으므로 받지 않음.
		// 그리고 step0는 answer entity의 mainprocess, step1은 subprocess1과 대응됨
		
		System.out.println(stephash);
		String step0 = stephash.get("mainprocess");
		String step1 = stephash.get("val");
		String grade = "2";
		
		
		
		System.out.println("여기는 통과");
	    
		return ResponseEntity.status(HttpStatus.OK).body(internalwork.findstep2(step0, step1));
    	  
   }

	@PostMapping("/internal/process_answer/{name1}")
	public ResponseEntity<Object> process_answer(HttpSession session, 
			 Model model , @PathVariable String name1, @RequestParam @Nullable HashMap<String, String> answerlist		
			) {
    
	 try {	

    	//answerlist의 구조는 {name1: processdata, 팀1: ~, 팀2: ~, system: ~ } 등으로 구성하였음
		// sentenceplus 등을 추가 반영하기

		JSONParser parser = new JSONParser();

		System.out.println(answerlist.get(name1));
		JSONObject sentences = (JSONObject) parser.parse(answerlist.get(name1));
		internalwork.sentenceplus(sentences, name1);
		
		// 참고로 name1이 메인프로세스이고 name2가 서브프로세스임
		System.out.println("1단계 시작임");

		HashMap<String, String> realdata = new HashMap<>();
		for(String i : answerlist.keySet()) {
			
			// name1과 동일할 때는 sentenceplus의 경우이므로, 이 경우에는  제외함
			if(i.equals(name1) == false) {
				List<String> temp = new ArrayList<>();
				String ans = answerlist.get(i);
				
				/* 무조건 key 하나당 하나씩 답변을 하도록 수정하였음
				JSONObject data = (JSONObject) parser.parse(ans);
				
				for(Object str : data.values()) {
					temp.add((String) str);
				}*/
				
				realdata.put(i, ans);
			}
		}
		
		System.out.println("2단계 시작임");
		internalwork.process_answer2(realdata, name1);
	    
		return ResponseEntity.status(HttpStatus.OK).body("success");

	  } catch(Exception e) {
    	  System.out.println(e);
		  return ResponseEntity.status(HttpStatus.OK).body(1909);
	  }
    	  
    }	
	
	
	
	
	
	
	@PostMapping("/internal/basesturctureanswer/{name1}")
	public ResponseEntity<Object> basestructureanswer(HttpSession session, 
			 Model model , @PathVariable String name1, @RequestParam @Nullable HashMap<String, String> answerlist		
			) {
    
	 try {	
		System.out.println("basestructureanswer");
		System.out.println(name1);
		System.out.println(answerlist);
		
		// 참고로 name1이 메인프로세스이고 name2가 서브프로세스임
		
		JSONParser parser = new JSONParser();
		String ans = answerlist.get("step0");
		
		JSONObject data = (JSONObject) parser.parse(ans);
		System.out.println(data);
	    
		internalwork.baseanswer(name1,  "0", data);
		// 210111 internalwork쪽의 baseanswer 함수도 수정을 해야함
		// 
		
		return ResponseEntity.status(HttpStatus.OK).body("success");

	  } catch(Exception e) {
    	  System.out.println(e);
		  return ResponseEntity.status(HttpStatus.OK).body(1909);
	  }
    	  
    }	
	
	@PostMapping("/internal/baseinventoryquestion")
	public ResponseEntity<Object> baseinventoryquestion(HttpSession session, 
			 Model model, @RequestParam @Nullable HashMap<String, String> realdata
			) {
    
	 try {	
		System.out.println("baseinventoryquestion");

		// 공정내용이 이미 작성이 되었다면, 공정의 작성을 건너띄고 현재 입력된 공정의 내용을 표시하고 그에 따른 질문서를 작성하는게 맞으므로
		// 먼저 공정내용이 이미 작성이 되었는지 확인함
		List<JSONObject> jsonlist = internalwork.get_exist_subprocess2(realdata.get("mainprocess"), realdata.get("subprocess1"));
		System.out.println(jsonlist);
		
        if(jsonlist.size() > 0) {
        	List<JSONObject> question = internalwork.findnextstep("재고자산", "3");
        	HashMap<String, JSONObject> json_question = new HashMap<>();
        	
        	for(JSONObject que : question) {
        		json_question.put((String) que.get("realname"), que);
        	}
        	
        	HashMap<String, Object> data_return = new HashMap<>();
        	data_return.put("question", json_question);
        	data_return.put("jsonlist", jsonlist);
    		return ResponseEntity.status(HttpStatus.OK).body(data_return);
    		
        }else {
    		return ResponseEntity.status(HttpStatus.OK).body(internalwork.baseinventoryquestion());
        }

	  } catch(Exception e) {
    	  System.out.println(e);
		  return ResponseEntity.status(HttpStatus.OK).body(1909);
	  }
    	  
    }
	
	/*  이 기능을 currentview 단계 1로 넘겨버렸음
	@PostMapping("/view/confirm_final")
	public ResponseEntity<Object> confirm_final(HttpSession session, 
			 Model model
			) {
    
	 try {	
		System.out.println("confirm_final");
		
		// 최종적으로 여기서 만들어서 unitedata에 쓰고 그것으로 상세질문지 작업을 하는 것임
		
		
		return ResponseEntity.status(HttpStatus.OK).body("success");

	  } catch(Exception e) {
    	  System.out.println(e);
		  return ResponseEntity.status(HttpStatus.OK).body(1909);
	  }
    	  
    }
 */
	
	
	
	@PostMapping("/internal/baseanswer")
	public ResponseEntity<Object> baseanswer(HttpSession session, 
			 Model model, @RequestParam @Nullable HashMap<String, String> answerlist		
			) {
    
	 try {	
		System.out.println("왜 안오는거니");
		// 참고로 name1이 step임 현재는 step이 2인경우를 의미함. 다만 step이 subprocess로 현재는 표시되어 있음.
		
		//internalwork.baseanswer_step2(name1, name2, answerlist);
		
		// 그냥 시연용이므로 간략하게 수정함

		session.setAttribute("구매", "완성");
		
		for(String str : answerlist.keySet()) {
			System.out.println(str);
			System.out.println(answerlist.get(str));
	   		session.setAttribute(str, answerlist.get(str));
		}
   		
	    
		// 210111 internalwork쪽의 baseanswer 함수도 수정을 해야함
		// 
		
		return ResponseEntity.status(HttpStatus.OK).body("success");

	  } catch(Exception e) {
    	  System.out.println(e);
		  return ResponseEntity.status(HttpStatus.OK).body(1909);
	  }
    	  
    }



	@PostMapping("/internal/basefinalanswer/{pro1}/{pro2}/{pro3}")
	public ResponseEntity<Object> basefinalanswer(HttpSession session, 
			 Model model , @PathVariable String pro1, @PathVariable String pro2, @PathVariable String pro3, 
			 @RequestParam @Nullable HashMap<String, String> answerlist		
			) {
    
	 try {	
		System.out.println("왜 안오는거니");

		// 참고로 name1이 step임 현재는 step이 2인경우를 의미함. 다만 step이 subprocess로 현재는 표시되어 있음.
		
		internalwork.baseanswer_final(pro1, pro2, pro3, answerlist);
		
	    
		// 210111 internalwork쪽의 baseanswer 함수도 수정을 해야함
		// 
		
		return ResponseEntity.status(HttpStatus.OK).body("success");

	  } catch(Exception e) {
    	  System.out.println(e);
		  return ResponseEntity.status(HttpStatus.OK).body(1909);
	  }
    	  
    }	
	
	
	@PostMapping("/internal/currentview_confirm")
    public ResponseEntity<Object> currentview_confirm(Model model,  HttpServletRequest request) throws JsonProcessingException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException {
    
    	// 단계 업데이트 하기
    	System.out.println("currentview_confirm");
    	HttpSession session = request.getSession();
    	int level = (Integer) session.getAttribute("level");
    	String result = "";
    	
    	if(level == 1) {
    		String ans = (String) session.getAttribute("구매2");
        	result = currentmanageservice.currentupgrade(level, ans);
    		
    	}else {
        	result = currentmanageservice.currentupgrade(level);

    	}

    	System.out.println(currentmanageservice.getCondition());
    	if(result.equals("다음 단계로 넘어갔습니다.")) {
    		session.setAttribute("level", level + 1);
    	}
    	
    	
    	if(currentmanageservice.getCondition() == 1) {
    		System.out.println("다음단계인 getunitelist");
   		 JSONObject jsons = jsonmake.processtojson2_unite(unitedwork.getunitelist());
   		 jsons.put("결과", result);
   		   return ResponseEntity.status(HttpStatus.OK).body(jsons);
     	}

    	
    	JSONObject tem = new JSONObject();
    	tem.put("결과", result);
    	
 
    	
    	return ResponseEntity.status(HttpStatus.OK).body(tem);
    }
	
	
	@PostMapping("/internal/scoping_confirm_submit")
    public ResponseEntity<Object> scoping_confirm_submit(Model model,
    		@RequestParam("data") String data) {
    
    	// 단계 업데이트 하기
    	System.out.println("scoping_confirm_submit");
    	System.out.println(data);
    	
        currentmanageservice.upgrade_scoping("Scoping","Scoping", 3);
    	
    	HashMap<String, String> tem = new HashMap<>();
    	tem.put("결과", "123");
    	return ResponseEntity.status(HttpStatus.OK).body(tem);
    }

	/*
	@PostMapping("/mycontrol/internalcontrol")
	public ResponseEntity<Object> mycontrol(HttpSession session, 
			 Model model //, @PathVariable String name
	) {
		
	//System.out.println(name);
    
    
	try {	
	    Scoping.settingresultcoa();
		return ResponseEntity.status(HttpStatus.OK).body(123);

	} catch(Exception e) {
    	  System.out.println(e);
		  return ResponseEntity.status(HttpStatus.OK).body(1909);
	}
    	  
  }*/
    
	
   }
