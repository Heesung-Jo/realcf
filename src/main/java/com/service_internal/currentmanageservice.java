package com.service_internal; 


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;


import com.entity_internal.Scopingdata;
import com.entity_internal.answerstructure;
import com.entity_internal.basemapping;
import com.entity_internal.basequestion;
import com.entity_internal.coa_process;
import com.entity_internal.currentmanage;
import com.entity_internal.member;
import com.entity_internal.roledata;
import com.entity_internal.uniteddata;
import com.entity_internal.level0table;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.repository_internal.CoadataRepository;
import com.repository_internal.MemberRepository;
import com.repository_internal.ScopingdataRepository;
import com.repository_internal.TeamRepository;


import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import com.repository_internal.ProcessdataRepository;
import com.repository_internal.AnswerstructureRepository;
import com.repository_internal.BasemapRepository;
import com.repository_internal.Coa_processRepository;
import com.repository_internal.BasequestionRepository;
import com.repository_internal.CurrentmanageRepository;
import com.repository_internal.UniteddataRepository;
import com.repository_internal.Level0Repository;

@Setter
@Getter
@Service
public class currentmanageservice { 

 
	private ArrayList<String> currentgradearr = new ArrayList<>();
	private Integer current_num = 0;  // 이제 레벨 관리를 프론트에서 하게되므로 이건 현재는 무쓸모임

	
	private HashMap<String, String> managemapping = new LinkedHashMap<>();
	
	private HashMap<String, String> currentviewarr0 = new LinkedHashMap<>();


	@Autowired
	private AnswerstructureRepository ansrepository;
	
	@Autowired
	private BasequestionRepository BasequestionRepository;
	@Autowired
	private CurrentmanageRepository CurrentmanageRepository;

	@Autowired
	private UniteddataRepository UniteddataRepository;

	@Autowired
	private unitedwork unitedwork;
	
	@Autowired
	private Level0Repository Level0Repository;
	
	private int condition =  0;  // 1일때는 바로 위의 list<uniteddata> 데이터를 넘길때임		
	
    @PostConstruct
    public void setting() {
    	
    	currentviewarr0.put("스코핑", "미완성");
    	currentviewarr0.put("조직도입력", "미완성");
    	currentviewarr0.put("팀매핑", "미완성");
    	currentviewarr0.put("문서매핑", "미완성");
    	currentviewarr0.put("프로세스매핑", "미완성");
    	
    	
    	managemapping.put("회계팀원", "재무보고");
    	managemapping.put("인사팀원", "인사");
    	managemapping.put("총무팀원", "구매");
    	managemapping.put("생산팀원", "재고");
    	managemapping.put("영업팀원", "매출");
    	managemapping.put("회계팀장", "재무보고");
    	managemapping.put("인사팀장", "인사");
    	managemapping.put("총무팀장", "구매");
    	managemapping.put("생산팀장", "재고");
    	managemapping.put("영업팀장", "매출");    	
    	
    	
    }

    
    // 현재단계 리턴해주기
    public HashMap<String, String> currentgrade(Integer num) {
    
    	
    	List<String> tem = new ArrayList<>();
    	HashMap<String, String> resulthash = new HashMap<String, String>();
    	try {
    		//로그인이 된 경우
    		
    		System.out.println("여기왔음");
        	//member user = userContext.getCurrentUser();
        	//Set<String> roles = user.getRoledata().stream().map(s -> s.getRole()).collect(Collectors.toSet());
    		//System.out.println(roles);
    		
    		/* 이것도 이제 로그인과 상관없이 변경함
        	tem = CurrentmanageRepository.findByGrade(current_num.toString()).stream().sorted()
        			.filter(s -> roles.contains(findmember(s, 1)) || roles.contains(findmember(s, 2))).map(s -> s.getViewname())
        			.collect(Collectors.toList());
        			*/
    		
        	tem = CurrentmanageRepository.findByGrade(num.toString()).stream().map(s -> s.getViewname())
        			.collect(Collectors.toList());
        	
        	HashMap<String, String> newhash = new HashMap<String, String>();
        	newhash.put("Scoping", "Scoping");
        	newhash.put("gojs_work", "상세질문지");
        	newhash.put("basequestion", "기본질문지");
        	newhash.put("weakpoint", "미비점");
        	newhash.put("gojs9", "플로우차트");
        	newhash.put("currentview", "현재상태");
        	newhash.put("explanation1", "설명");
        	newhash.put("explanation2", "설명");
        	newhash.put("explanation3", "설명");
        	newhash.put("explanation4", "설명");
        	newhash.put("explanation5", "설명");
        	
        	
        	for(String str : tem) {
        		resulthash.put(str, newhash.get(str));
        	}
        	
        	
        	
    		
    	}catch(Exception e) {
    		//로그인이 아직 안된경우
    		tem.add("explanation");
    	}
    	
    	return resulthash;
    }
    
    
    public String findmember(currentmanage manage, int grade) {
    	
    	
    	
    	String role = "";
    	if(grade == 1) {
    		role = manage.getPerson1();
    	}else if(grade == 2) {
    		role = manage.getPerson2();
    	}else if(grade == 3) {
    		role = manage.getPerson3();
    	}
    	
    	return role;
    }

    public String findgrade(currentmanage manage, int grade) {
    	
    	
    	String role = "";
    	if(grade == 1) {
    		role = manage.getGrade1();
    	}else if(grade == 2) {
    		role = manage.getGrade2();
    	}else if(grade == 3) {
    		role = manage.getGrade3();
    	}
    	
    	return role;
    }
    
  
    public String currentupgrade(int num, String str) throws JsonProcessingException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

    	condition = 0;
    	// 먼저 권한 체크하기

    	System.out.println("upgrade:" + num);
    	
    	
    	// 업그레이드를 해도 되는지 체크하기
    	// 여기선 num 1만 처리
         if(num == 1) {
    		int failsure = 0;
    		for(answerstructure pro : ansrepository.findByStep("0")) {
    			if(pro.getCurrentgrade().equals("미완성")== true) {
           			failsure = 1;
    			};
    		};
    		
    		if(failsure == 1) {
    			return "아직 미완성인 부분이 있습니다.";
    		}else {
    			//current_num++;
    		}
    		
    		
    		unitedwork.unite_data(str);
    		condition = 1;
    		
    	}
       
    	return "다음 단계로 넘어갔습니다.";
    }
    
    
    public String currentupgrade(int num) throws JsonProcessingException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

    	condition = 0;
    	// 먼저 권한 체크하기

    	System.out.println("upgrade:" + num);
    	
    	
    	// 업그레이드를 해도 되는지 체크하기
    	if(num == 0) {

    		// 권한이 있는 것만 넘겨줄 것
    		for(level0table level :  Level0Repository.findAll()) {
      			if(level.getCurrentgrade().equals("미완성") == true) {
        			return "아직 미완성인 부분이 있습니다.";
    			}
    		};
    		// 여기에 basequestion의 0번대 질문을 포함시킬 것
    		for(basequestion base :  BasequestionRepository.findBySubprocess("0")) {
      			if(base.getCurrentgrade().equals("미완성") == true) {
        			return "아직 미완성인 부분이 있습니다.";
    			}
    		};
    		
    		
    		
    		
    	}else if(num == 1) {
    		int failsure = 0;
    		for(answerstructure pro : ansrepository.findByStep("0")) {
    			if(pro.getCurrentgrade().equals("미완성")== true) {
           			failsure = 1;
    			};
    		};
    		
    		if(failsure == 1) {
    			return "아직 미완성인 부분이 있습니다.";
    		}else {
    			//current_num++;
    		}
    		
    		//레벨 1일땐 currentupgrade(int num, String str) 에서 처리
    		//unitedwork.unite_data();
    		//condition = 1;
    		
    	}else if(num >= 2) {
    		
    		// 모든 것을 프론트로 넘겼으므로 실패가 나올리가 없음
    		/*
    		int failsure = 0;
    		for(uniteddata pro : UniteddataRepository.findAll()){
    			if(pro.getCurrentgrade().equals("미완성")== true) {
           			failsure = 1;
    			};
    		};
    		
    		if(failsure == 1) {
    			return "아직 미완성인 부분이 있습니다.";
    		}else {
    			//current_num++;
    		}
    		*/
    	}   	
       
    	return "다음 단계로 넘어갔습니다.";
    }
    
    
    // 현재단계에 대해 보여줄 뷰를 리턴해주기
    public HashMap<String, String> currentview(int num) {
    	
    	
    	HashMap<String, String> temp = new LinkedHashMap<>();
    	
    	//member user = userContext.getCurrentUser();
    	
    	
    	if(num == 0) {
    		
    		// 권한이 있는 것만 넘겨줄 것
    		Level0Repository.findAll().forEach(pro -> {
    			temp.put(pro.getRealname(), pro.getCurrentgrade());
    		});
    		
    		// 여기에 basequestion의 0번대 질문을 포함시킬 것
    		BasequestionRepository.findBySubprocess("0").forEach(pro -> {
    			temp.put(pro.getRealname(), pro.getCurrentgrade());
    		});
    		
    		return temp;
    		
    	}else if(num == 1) {
    		/*
    		BasequestionRepository.findAll().stream().filter(s -> s.getSubprocess().equals("0") == false).forEach(pro -> {
    			temp.put(pro.getQuestion(), pro.getCurrentgrade());
    		});
    		*/
    		
    		ansrepository.findByStep("0").stream().forEach(pro -> {
    			temp.put(pro.getMainprocess() + "/" + pro.getSubprocess1(), pro.getCurrentgrade());
    		});
    		
    		
    		return temp;
    	}else if(num == 3) {
    		UniteddataRepository.findAll().forEach(pro -> {
    			temp.put(pro.getProcessname1(), pro.getCurrentgrade());
    		});
    	}
    	
    	
    	return null;
    }

    
    // 구체적 단계 업데이트 하기
    
    // 스코핑 단계 업데이트
    public void upgrade_scoping(String viewname, String realname, Integer num) {
    	
    	
    	currentmanage manage = CurrentmanageRepository.findByViewname(viewname).get(0);
    	
    	CurrentmanageRepository.save(manage);
    	
    	level0table level0 = Level0Repository.findByRealname(realname).get(0);
    	level0.setPerson_charge(findmember(manage, num));
        level0.setCurrentgrade(findgrade(manage, num));
        level0.setRealgrade(num);
        Level0Repository.save(level0);
    }
    
    
    

}