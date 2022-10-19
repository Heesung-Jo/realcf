package com.service_internal; 

 import javax.persistence.DiscriminatorValue; 
import javax.persistence.Entity; 

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;


import com.entity_internal.processdata;
import com.entity_internal.processoption;
import com.entity_internal.result_value;
import com.entity_internal.teamdata;
import com.entity_internal.uniteddata;
import com.enumfolder.Role;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.entity_internal.nodedata;
import com.entity_internal.parentnodedata;
import com.entity_internal.childnodedata;
import com.entity_internal.currentmanage;
import com.entity_internal.member;
import com.repository_internal.ParentnodedataRepository;
import com.repository_internal.ProcessdataRepository;
import com.repository_internal.TeamRepository;
import com.repository_internal.RoleRepository;
import com.repository_internal.ProcessoptionRepository;
import com.repository_internal.UniteddataRepository;
import com.repository_internal.Result_valRepository;
import com.repository_internal.CurrentmanageRepository;

import com.entity_internal.answerlist;
import com.entity_internal.answerstructure;
import com.entity_internal.basequestion;
import com.entity_internal.basicdata;
import com.repository_internal.BasequestionRepository;
import com.repository_internal.Coa_processRepository;
import com.repository_internal.AnswerlistRepository;
import com.repository_internal.AnswerstructureRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.PostConstruct;
import javax.inject.Inject;


// jsp에서 지금 바로는 원하는데로 안받아지므로, submit를 바꿔서, ajax 형태로 입력되도록 수정할 것

@Service
public class internalwork<E> { 


	private HashMap<String, ArrayList<String>> coaarray_bs = new LinkedHashMap<>(); 
	private HashMap<String, String> oppositecoa_bs = new LinkedHashMap<>(); 
	private HashMap<String, ArrayList<String>> coaarray_is = new LinkedHashMap<>(); 
	private HashMap<String, String> oppositecoa_is = new LinkedHashMap<>(); 
	private HashMap<String, HashMap<String, String>> processmap = new LinkedHashMap<>(); 
	private HashMap<String, HashMap<String, ArrayList<String>>> processinmap = new LinkedHashMap<>(); 
	

	private Integer processcount = 7;     // 이것을 기준으로 processname1 ~ 7까지 결정, controlname1 ~ 7까지 결정 등임
	private Integer optionriskcount = 5;  // 미비점으로 집계하기 위한 risk1 ~ 5 까지 결정 등에 대한 카운터
	
	private HashMap<String, String> processteammap = new LinkedHashMap<>();
	private HashMap<String, ArrayList<String>> coaprocessmap = new LinkedHashMap<>();

	private HashMap<String, String> teamteam1map = new LinkedHashMap<>();
	private HashMap<String, ArrayList<String>> teamteam2map = new LinkedHashMap<>();

	
	
	@Autowired
	private ProcessdataRepository processrepository;

	@Autowired
	private TeamRepository teamrepository;
	
	@Autowired
	private ProcessoptionRepository ProcessoptionRepository;
	
	@Autowired
	private ParentnodedataRepository parentnodedataRepository;
 	
	@Autowired
	private BasequestionRepository BasequestionRepository;

	@Autowired
	private AnswerstructureRepository AnswerRepository;

	@Autowired
	private AnswerlistRepository AnswerlistRepository;

	@Autowired
	private Coa_processRepository coa_processrepository;
    
	@Autowired
	private UniteddataRepository UniteddataRepository;

	
	@Autowired
	private Result_valRepository Result_valRepository;
	
	@Autowired
	private jsonmake jsonmake;

	@Autowired
	private CurrentmanageRepository CurrentmanageRepository;

	
	@Autowired
	private currentmanageservice currentmanageservice;

	
	public void mywork() { 
    	
    } 

	
	/* 순서세팅
	<li><a href="Scoping">Scoping</a></li>
	<li><a href="basemapping">각종 매핑</a></li>
	<li><a href="basestructure">기본계층 만들기</a></li>
	<li><a href="basequestion">기본질문지</a></li>
	<li><a href="gojs_work">상세질문지</a></li>
	<li><a href="gojs9">플로우차트</a></li>
	<li><a href="explanation">이해하기</a></li>
	*/
	

	
	
    public void save(processdata pro) {
    	processrepository.save(pro);
    }
    
 
    
    
    // 현재 모든 스코핑 담당자의 업무가 완료가 되었는지 확인하여 승인을 하면 다음단계로 넘어감 
    public void confirm_scoping() {
    	
    }
    
    
    
    
    // processoption 입력하기
    @Transactional
    public void process_answer(HashMap<String, List<String>> datas, String process){

		uniteddata pro =  UniteddataRepository.findByProcessname1(process).get(0);
		pro.getProcessoption().forEach((opt) -> {

			for(String str : datas.keySet()) {
	    		if(opt.getRealname().equals(str) == true) {
	    			
	    			
		    		List<String> vals = datas.get(str);
	    			List<result_value> result_val = new ArrayList<>();
		    		System.out.println(vals);
		    		
		    		for(String val : vals) {
		    			result_value tem = new result_value();
		    			tem.setVal(val);
		    			tem.setProcessoption(opt);
		    			result_val.add(tem);
		    		}
	    			/* 밑에 process_answer2를 사용함.
	    			 
		    		Result_valRepository.deleteAllInBatch(opt.getResult_value());
	    			opt.getResult_value().addAll(result_val);
	    		    */ 
	    		}
	    	}
		});

		processrepository.save(pro);
    	
    }
    

    // 무조건 processoption에 모두 있는 것은 아니라서, 이를 반영하였음.
    @Transactional
    public void process_answer2(HashMap<String, String> datas, String process) throws CloneNotSupportedException, JsonProcessingException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{

    	System.out.println(process);
		List<uniteddata> pros =  UniteddataRepository.findByProcessname1(process);
		System.out.println(pros.size());
		uniteddata pro = pros.get(0);
		List<processoption> options = pro.getProcessoption();

		
		// processoption을 쉽게 찾을 수 있도록, hashmap을 만들었음
		HashMap<String, processoption> opt_hash = new HashMap<>();
		
		for(processoption opta : options) {
		    opt_hash.put(opta.getRealname(), opta);	
		}
		// 옵션값인 result_val을 입력하기 위한 순환문임
		for(String str : datas.keySet()) {
			System.out.println(str);
			
			if(opt_hash.containsKey(str) == true) {
			
			    // 이 경우에는 바로 result_val을 입력해주면 되는 상황임
		    	
				String vals = datas.get(str);
				opt_hash.get(str).setResult(vals);
				
				// 혹시 미비점으로 집계해야하는지 검증하기
				incompletetest(opt_hash.get(str), vals);
				
				ProcessoptionRepository.saveAndFlush(opt_hash.get(str));
				

			}else {
    			// 이 경우에는 sentenceplus의 팀2, 팀3 이런 경우이니, 새롭게 option에 추가해줘야 하는 상황임
    			// 일단 팀1을 복제해야 하므로 그것을 찾을 것
    			// 여기서 일치하는 팀1을 못찾으면 오류가 날 수 있음. 기억할 것
    			String first = str.substring(0, str.length()-1) + "1";
    			processoption option_new =  new processoption(); //(processoption) opt_hash.get(first).clone();
    			
    			ObjectMapper mapper = new ObjectMapper();
				String temp = mapper.writeValueAsString(opt_hash.get(first));  
				 
				option_new = mapper.readValue(temp, processoption.class);

    			option_new.setRealname(str);
    			
    			option_new.setProcessdata(pro);

    			String vals = datas.get(str);
    			option_new.setResult(vals);
				// 혹시 미비점으로 집계해야하는지 검증하기
				incompletetest(option_new, vals);
		    		
		   		ProcessoptionRepository.saveAndFlush(option_new);
		   		
			}
		
		}


		processrepository.save(pro);
    	
    }    
    
    // 일단 여기를 오지를 않음
    public void incompletetest(processoption option, String ans) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    	
    	 for(int i = 1; i <= this.optionriskcount; i++) {
    		 Class<?>[] parameterTypes = new Class[1];
    		 Method me = processoption.class.getMethod("getRisk" + i);
    		 String word = (String) me.invoke(option); // text 내용을 갈아끼웠음
    		 
    		 if(word != null) {
    			 if(word.equals(ans) == true) {
    				 //option.setIncompletepoint(true);
    				 //option.setFailposition(i);
    			 }
    		 }
    	 }

    	 
    }
    
    
    
    // processdata 조회하기
    @Transactional
    public JSONObject getprocessquery(ArrayList<String> names, ArrayList<String> paras){
    	return processlist(processrepository.getprocessquery(names, paras));
    }
    @Transactional
    public JSONObject getprocessquery(String name1, String name2, String para1, String para2){
    	return jsonmake.processtojson(processrepository.getprocessquery(name1, name2, para1, para2));
    }
    @Transactional
    public JSONObject getprocessquery(String name, String para){
    	
    	return processlist(processrepository.getprocessquery(name, para));
    	
    }
    
    
    
    
    
    

    @Transactional
    public List<JSONObject> get_exist_subprocess2(String name1, String name2) throws JsonProcessingException, ParseException{

    	List<answerstructure> arrlist = AnswerRepository.findByMainprocessAndSubprocess1(name1, name2).stream().filter((pros) -> 
    		pros.getSubprocess2() != null 
    	).collect(Collectors.toList());
    	
    	return jsonmake.processtojson3(arrlist);
    	
    }

    
    
    //  
    public List<String> findstartlist(){
    	
    	return parentnodedataRepository.findstartlist().stream().collect(Collectors.toList());
    	
    }

    
    public List<String> findstartlist(member user){
    	
    	return parentnodedataRepository.findstartlist().stream().filter(s -> 
    		UniteddataRepository.findByProcessname1(s).get(0).getPersoncharge().equals(user.getRealname())
        ).collect(Collectors.toList());
    	
    }
    
    // 이것은 기본질문서의 리스트를 만들기 위한 것임   
    public Set<String> findbasestartlist(){
    	
    	// 먼저 우리회사에 있는 프로세스 뽑
    	List<String> pros = coa_processrepository.findgroupprocess();
    	Set<String> data = new HashSet<>();

	   // member user = userContext.getCurrentUser();
    	
    	BasequestionRepository.findAll().forEach((base) -> {
    		//if(base.getPerson_charge().equals(user.getRealname()) == true) {
    			data.add(base.getMainprocess());
    		//}
    	});
    	System.out.println(pros);
    	System.out.println(data);
    	
    	return data;
    }


    public List<JSONObject> findstructurebasestartlist(String name) throws JsonProcessingException, ParseException{
    	
    	List<answerstructure> answerlist =  AnswerRepository.findByStep("0");
    	
    	return jsonmake.processtojson3(answerlist);
    }

    
    public List<basequestion> findrelatedbasequestion(String name){
    	return BasequestionRepository.findByMainprocess(name);
    }	
    
    
    
    public List<JSONObject> findstep0(String step0) throws JsonProcessingException, ParseException{

    	System.out.println(step0);
    	
    	// 현재 step0, "0"에 대한 쿼리는 1개라는 가정하에 작성되었으니, 나중에 내용이 변경되면 적당히 바꿀것
    	List<basequestion> bases = BasequestionRepository.findByMainprocessAndSubprocess(step0, "0");
    	
    	List<answerstructure> ans = AnswerRepository.findByMainprocessAndStep(step0, "0");
    	
    	List<JSONObject> jsonlist = jsonmake.processtojson3(bases);
    	if(ans.size() > 0) {
    		List<JSONObject> json_ans = jsonmake.processtojson3(ans);
        	jsonlist.get(0).put("answer", json_ans);
    	}
    	
    	System.out.println(jsonlist);
    	
        return jsonlist;
    }

    public JSONObject findstep2(String mainprocess, String subprocess) throws JsonProcessingException, ParseException{

    	System.out.println(subprocess);
    	System.out.println(mainprocess);
    	// 현재 step0, "0"에 대한 쿼리는 1개라는 가정하에 작성되었으니, 나중에 내용이 변경되면 적당히 바꿀것
    	List<basequestion> bases = BasequestionRepository.findByMainprocessAndSubprocess(mainprocess, "2");
    	JSONObject totaljson = new JSONObject();
    	
    	for(basequestion base : bases) {
        	ArrayList<JSONObject> jsonlist = new ArrayList<>();
        	
        	JSONObject json_base = jsonmake.processtojson3(base);
    		List<answerlist> anlist = base.getAnswer();
    		for(answerlist an : anlist) {
    			if(an.getSubprocess1().equals(subprocess)) {
    				JSONObject json = jsonmake.processtojson3(an);
    				jsonlist.add(json);
    			}
    		}
    		
    		json_base.put("answer", jsonlist);
    		totaljson.put(json_base.get("realname"), json_base);
    	}
    	
        return totaljson;
    }    
    
    
    public ArrayList<JSONObject> findnextstep(String process, String grade) throws JsonProcessingException, ParseException{

    	List<basequestion> bases = BasequestionRepository.findByMainprocessAndSubprocess(process, grade);
    	
    	ArrayList<JSONObject> answerlist = new ArrayList<>();
    	
    	for(basequestion que : bases) {
    		
    		JSONObject json = jsonmake.processtojson3(que);
        	List<answerlist> templist = que.getAnswer();
        	
        	if(templist.size() > 0) {
        		json.put("answer" , jsonmake.processtojson3(templist));
        	}
        	
        	answerlist.add(json);
    	}
    	
    	return answerlist;
    }	
    
    
    public ArrayList<uniteddata> findrelatedstart(String name){
    	
    	ArrayList<uniteddata> child = UniteddataRepository.findByProcessname1(name); 
    	System.out.println(name);
    	ArrayList<uniteddata> arr_return = new ArrayList<>();
    	Set<uniteddata> childs = new HashSet<>();
    	for(uniteddata chi : child) {
    		childs.add(chi);
    		arr_return.add(chi);
    		break;
    	}
    	
        int size = 1;
    	//211128 이거 manytomany로 수정해서 수정이 필요함
    	while(size == 1) {
    		
    		Set<uniteddata> next_arr = new HashSet<>();
    		if(childs.size() > 0) {
    			
               for(uniteddata chi : childs) {
            	   System.out.println(chi.getProcessname1());
            	   for(processdata ch : chi.getSuperpro()) {
            		   System.out.println("sub입니다");
            		   System.out.println(ch.getProcessname1());
                	   if(arr_return.contains(ch) == false) {
                		   next_arr.add((uniteddata) ch);
                		   arr_return.add((uniteddata) ch);
                	   }
            	   };
               }
               System.out.println(next_arr.size());
               childs = next_arr;
               if(next_arr.size() == 0) {
            	   size = 0;
            	   break;
               }
               
    		}else {
    			size = 0;
    			break;
    		}
    	}
    	
    	return arr_return;
    }
    
    public JSONObject findByName(String name) throws JsonProcessingException, ParseException{
    	return jsonmake.processtojson2(processrepository.findByProcessname1(name));
    }
    
    
    public processdata findByName2(String name) {
    	return processrepository.findByProcessname1(name).get(0);
    }
    
    
    
    
    
    // teamdata 조회
    
    // 여기서 부하가 많이 들어가는 것 같음. 나중에 캐쉬 사용해서 가용성 늘릴것
    public ArrayList<String> findwork(String name){
    	
    	System.out.println(name);
    	List<teamdata> teams = teamrepository.findByTeamname(name);
    	
    	ArrayList<String> process = new ArrayList<>();
    	
    	for(teamdata team : teams) {
    	    String temp = team.getProcessdata().getsubprocess();
    	
   		    if(process.contains(temp) == false) {
    			process.add(temp);
    		}
    	}
    	
    	return process;
    }
    
    public Set<String> findwork_person(){
    	
        
    	//Set<String> process = UniteddataRepository.findByPersoncharge(name).stream().map(s -> s.getsubprocess()).collect(Collectors.toSet());
    	
    	// 이제 전체를 리턴하도록 수정함
    	Set<String> process = UniteddataRepository.findAll().stream().map(s -> s.getsubprocess()).collect(Collectors.toSet());
    	
    	return process;
    }

    
    
    // 기본질문서의 answer 입력하기
    @Transactional
	public void baseanswer(String name1, String name2, JSONObject answer)  {
        
    	// 현재는 거의 단계 0에만 적용하고 있음.
    	basequestion question = BasequestionRepository.findByMainprocessAndSubprocess(name1, name2.toString()).get(0);

    	// 기존 item 지우기
    	List<answerstructure> current_ans = AnswerRepository.findByMainprocessAndStep(question.getMainprocess(), "0");
    	
    	for(answerstructure an : current_ans) {
    		for(answerlist ans : an.getAnswer()) {
        		AnswerlistRepository.delete(ans);
    		}
    		AnswerRepository.delete(an);
    	}
    	
    	// AnswerRepository.deleteAllInBatch(current_ans);

    	// 입력받은 item으로 갈아끼우기

    	ArrayList<answerstructure> answerstructure = new ArrayList<>();

    	for(Object s : answer.keySet()) {

       		answerstructure ans1 = new answerstructure();
    		ans1.setMainprocess(name1);
    		ans1.setSubprocess1(s.toString());
    		ans1.setPerson_charge(answer.get(s.toString()).toString());
    		ans1.setCurrentgrade("미완성");
       		ans1.setVal(s.toString());
       		ans1.setStep("0");
       		answerstructure.add(ans1);
    	}
    	
    	question.setCurrentgrade("승인대기중");
   		AnswerRepository.saveAll(answerstructure);
    	BasequestionRepository.save(question);
    }

    @Transactional
	public List<JSONObject> baseinventoryquestion() throws JsonProcessingException, ParseException {
		List<basequestion> que = BasequestionRepository.findByMainprocessAndSubprocess("재고자산", "1");
    	return jsonmake.processtojson3(que);
    }
    
    @Transactional
	public void baseanswer_step2(String mainprocess, String subprocess, HashMap<String, String> answer) throws ParseException  {
        
    	// 현재 이 함수는 단계 0의 일반 질문 답변 입력에 활용하고 있음.

    	// 기존 item 지우기findByMainprocessAndSubprocess1
    	answerstructure ans = AnswerRepository.findByMainprocessAndSubprocess1AndStep(mainprocess, subprocess, "0").get(0);
    	List<answerlist> anlist = AnswerlistRepository.findByMainprocessAndSubprocess1AndStep(mainprocess, subprocess, "2");
		if(anlist.size() > 0) {
    	    AnswerlistRepository.deleteAllInBatch(anlist);
		}
		
    	for(Object name : answer.keySet()) {
    		
    		basequestion question = BasequestionRepository.findByRealname(name.toString());

    	    // 이제 추가하기
    		JSONParser parser = new JSONParser();
        	ArrayList<answerlist> answerlist = new ArrayList<>();
        	
        	// 입력받은 item으로 갈아끼우기
    		JSONObject data = (JSONObject) parser.parse(answer.get(name).toString());
        	for(Object s : data.values()) {
            	ArrayList<answerstructure> answerstructure = new ArrayList<>();
                System.out.println(s);
        		answerlist ans0 = new answerlist();
        		ans0.setMainprocess(ans.getMainprocess());
        		ans0.setSubprocess1(ans.getSubprocess1());
        		
           		ans0.setVal(s.toString());
           		ans0.setStep(question.getSubprocess());
           		answerlist.add(ans0);
        	}

        	ans.setAnswer(answerlist);
       		question.setAnswer(answerlist);

        	AnswerlistRepository.saveAll(answerlist);
        	BasequestionRepository.save(question);    		
    		
    	}
    	
    	ans.setCurrentgrade("승인대기중");
    	AnswerRepository.save(ans);
    	
    	
    	
    	
    }    

    @Transactional
	public void baseanswer_step3(String subprocess, HashMap<String, String> answer) throws ParseException  {
        
    	// 현재는 이 함수는 단계 생산공정 확정에 적용하고 있음.
    	// 기존 item 지우기
		
 		List<answerstructure> ans = AnswerRepository.findByMainprocessAndSubprocess1("재고자산", subprocess);

 		System.out.println(subprocess);
 		
        for(answerstructure an : ans) {
        	String abc = an.getSubprocess2();
        	if(abc != null) {
                
        		List<answerlist> anlist = an.getAnswer();
        		if(anlist.size() > 0) {
        			for(answerlist answer1 : anlist) {
                    	AnswerlistRepository.delete(answer1);
        			}
        		}
        		
                AnswerRepository.delete(an);
        	}
        }

    	
    	for(Object name : answer.keySet()) {


            //이제 추가하기        	
        	
    		JSONParser parser = new JSONParser();
        	ArrayList<answerstructure> list = new ArrayList<>();
        	
        	// 입력받은 item으로 갈아끼우기
    		JSONObject data = (JSONObject) parser.parse(answer.get(name).toString());
    		
    		if(data.values().size() > 0) {
            	for(Object s : data.values()) {
                    System.out.println(s);
                    answerstructure ans0 = new answerstructure();
            		ans0.setMainprocess("재고자산");
            		ans0.setSubprocess1(subprocess);
            		
            		JSONObject temp = (JSONObject) s;
               		ans0.setSubprocess2(temp.get("from").toString());
               		ans0.setSubprocess3(temp.get("to").toString());
               		list.add(ans0);
            	}
    		}else {
                answerstructure ans0 = new answerstructure();
        		ans0.setMainprocess("재고자산");
        		ans0.setSubprocess1(subprocess);
           		ans0.setSubprocess2(name.toString());
           		list.add(ans0);    			
    		}

        	AnswerRepository.saveAll(list);
    		
    	}
    }  
    
    @Transactional
	public void baseanswer_final(String pro1, String pro2, String pro3, HashMap<String, String> answer) throws ParseException  {
        
    	// 현재 이 함수는 단계 1의 공정 질문 답변 입력에 활용하고 있음. 즉 최종 답변임
		answerstructure ans = AnswerRepository.findByMainprocessAndSubprocess1AndSubprocess2AndSubprocess3("재고자산", pro1, pro2, pro3).get(0);
    	// 기존 item 지우기
		
		List<answerlist> anlist = ans.getAnswer();
		
		if(anlist.isEmpty() == false) {
        	AnswerlistRepository.deleteAllInBatch(anlist);
		}

		System.out.println("재고수박이 왔다");
    	for(Object name : answer.keySet()) {
    		
    		basequestion question = BasequestionRepository.findByRealname(name.toString());
 
        	// 이제 추가하기
    		JSONParser parser = new JSONParser();
        	ArrayList<answerlist> answerlist = new ArrayList<>();
        	
        	// 입력받은 item으로 갈아끼우기
    		JSONObject data = (JSONObject) parser.parse(answer.get(name).toString());
        	for(Object s : data.values()) {
            	ArrayList<answerstructure> answerstructure = new ArrayList<>();
                System.out.println(s);
        		answerlist ans0 = new answerlist();
        		ans0.setMainprocess(ans.getMainprocess());
        		ans0.setSubprocess1(ans.getSubprocess1());
        		ans0.setSubprocess2(ans.getSubprocess2());
        		ans0.setSubprocess3(ans.getSubprocess3());
           		ans0.setVal(s.toString());
           		ans0.setStep(question.getSubprocess());
           		answerlist.add(ans0);
        	}

        	ans.setAnswer(answerlist);
       		question.setAnswer(answerlist);

        	AnswerlistRepository.saveAll(answerlist);
        	BasequestionRepository.save(question);    		
    		
    	}

    	ans.setCurrentgrade("승인대기중");
    	AnswerRepository.save(ans);

    }  
    
    
    
    
    


    @Transactional
	public JSONObject processlist(List<basicdata> arr) {

		JSONObject realdata = new JSONObject();
		JSONObject childdata = new JSONObject();
		JSONObject padata = new JSONObject();
		
		
		System.out.println(arr.size());
		
		for(processdata pro : arr) {
			JSONObject temp = new JSONObject();
			temp.put("name", pro.getname());
			temp.put("processname", pro.getprocessname());
			temp.put("team", pro.getteamdata().get(0).getTeamname());

			JSONArray childs = new JSONArray();
			for(childnodedata data : pro.getchildnodedata()) {
				childs.add(data.getname());
			}
			

			JSONArray pas = new JSONArray();
			for(parentnodedata data : pro.getparentnodedata()) {
				pas.add(data.getname());
			}

			
		}

		realdata.put("child", childdata);
		realdata.put("parent", padata);

		System.out.println(realdata);
		return realdata;
	}
	
	
    
    // 배열, array 등의 get, set문
    
    public HashMap<String, String> getoppositecoa(String opt){
    	if(opt.equals("BS") == true) {
    		return oppositecoa_bs;
    	}else {
    		return oppositecoa_is;
    	}
    }


    public HashMap<String, ArrayList<String>> getcoaarray(String opt){
    	if(opt.equals("BS") == true) {
    		return coaarray_bs;
    	}else {
    		return coaarray_is;
    	}
    }
    
    public HashMap<String, String> getprocessteammap(){
    	return processteammap;
    }


    public HashMap<String, HashMap<String, String>> getprocessmap(){
    	return processmap;
    }
    
    
    public HashMap<String, ArrayList<String>> getcoaprocessmap(){
    	return coaprocessmap;
    }
    
    public HashMap<String, String> getteamteam1map(){
    	return teamteam1map;
    }
    
    public HashMap<String, ArrayList<String>> getteamteam2map(){
    	return teamteam2map;
    }

  
    
  /*  
    // 엑셀 등의 데이터로 데이터 세팅
	public void setprocesssetting() {
		
		
		
		// rcm 기본파일 세팅
		// 엑셀관련한 것은 이것으로 돌릴 것
		try {
    		rcmlistmake("Total", "통제활동.xls", 2, 37, "subwork");
    		
    		//부모노드를 기초로 자식노드도 추가할 것
    		processrepository.setparentnodetotal();
		}catch(Exception e) {
			System.out.println(e);
		}
		
		// coa 매핑
		processlistmakexl("BS", "(AAS)표준 CoA 및 Process_JSH.xls", oppositecoa_bs, coaarray_bs, 0, 0, 0);
		processlistmakexl("IS", "(AAS)표준 CoA 및 Process_JSH.xls", oppositecoa_is, coaarray_is, 0, 0, 0);
		
		// 프로세스 매핑
		String[] arr = {"구매지출"}; //, "유무형", "재고", "인사", "자금", "재무보고", "매출", "공시관리", "기타"};
		
		for(String key : arr) {
			System.out.println(key);
			HashMap<String, String> array = new HashMap<>();
			HashMap<String, ArrayList<String>> inarray = new HashMap<>();
			processmap.put(key, array);
			
			processlistmakexl2(key, "sub-process와 부서명 mapping.xls", array, inarray, 0, 23, 0, 0);
			System.out.println(array.size());
		}
		
		
		
		
		// 프로세스 vs 팀 매핑
		processlistmakexl("team", "계정.xls", processteammap, null, 0, 0, 1);
		
		// coa vs process 매핑
		processlistmakexl("processmap", "계정.xls", null, coaprocessmap, 1, 0, 0);		

		// team vs team 매핑
		processlistmakexl("teammapping", "계정.xls", teamteam1map, teamteam2map, 0, 0, 0);		


	
	}

	*/
    
	
	/*
    // xl 사용하기 위해서 테스트
    public void processlistmakexl(String sheetname, String name, HashMap<String, String> oppositearray,
    		       HashMap<String, ArrayList<String>> inarray, int pos, int pos2, int opt) {
      // 각 배열에 앞뒤로 집어넣기, pos는 엑셀 열의 위치를 부여하기
      try {
      	FileInputStream fis = new FileInputStream("C:\\java\\gob\\프로토타입\\계정\\" + name); //"/usr/local/gob/" "C:\\java\\gob\\프로토타입\\계정\\"
      	HSSFWorkbook book = new HSSFWorkbook(fis);
      	HSSFSheet sheet = book.getSheet(sheetname);
      	//조금이라도 null이면 null이 떠 버리니 조심할 것
      	//그리고 열과행은 0부터 시작한다는 것 주의할 것
      	//https://blog.naver.com/wlgh325/221391234592
      	int rowexisting = 1;
      	int rownum = 0;
      	while(rowexisting == 1) {	

      	HSSFRow row = sheet.getRow(rownum);
         
  		
  	    ArrayList<JSONObject> sentences = new ArrayList<>();
  	    JSONObject obj = new JSONObject();
  	    String activitystring = "";
  	    String real = "";
      	int existing = 1;
      	
      	if(row != null) {
      		
      		// 유형자산 등
      		String coa = row.getCell(pos2).toString();
       		
      	    //	String processname = row.getCell(2).toString();
      		int num = pos;
      		ArrayList<String> array = new ArrayList<>();
      		
          	while(existing == 1) {
          		HSSFCell line = row.getCell(num);
          	    if(line != null && line.toString().isEmpty() == false) {
          	    	array.add(line.toString());
          	    	          	    	
          	    	//oppositecoa 채우기
          	    	if(oppositearray != null) {
          	    		if(opt == 0) {
                  	    	oppositearray.put(line.toString(), coa);
          	    		}else {
          	    			oppositearray.put(coa, line.toString());
          	    		}
          	    	}
          	    	num += 1;
          	    }else {
          	    	existing = 0;
          	    }
 
          	}
          	
          	// array를 coaarray에 집어넣기
          	if(inarray != null) {
  	    		inarray.put(coa, array);
          	}
 
  	            rownum += 1;
      	}else {
      		rowexisting = 0;
      		
      	}
      	
      	book.close();
  	}
  	
      }catch(FileNotFoundException e) {
    	  System.out.println(e);
      }catch(IOException e) {
    	  System.out.println(e);
      }
      
      
    }   
	*/
/*
 // xl 사용하기 위해서 테스트
    public void processlistmakexl2(String sheetname, String name, HashMap<String, String> oppositearray,
    		       HashMap<String, ArrayList<String>> inarray, int row1, int row2, int opt, int real) {
      // 각 배열에 앞뒤로 집어넣기, pos는 엑셀 열의 위치를 부여하기
      try {
      	FileInputStream fis = new FileInputStream("C:\\java\\gob\\프로토타입\\계정\\" + name); ///usr/local/gob/"
      	HSSFWorkbook book = new HSSFWorkbook(fis);
      	HSSFSheet sheet = book.getSheet(sheetname);
      	//조금이라도 null이면 null이 떠 버리니 조심할 것
      	//그리고 열과행은 0부터 시작한다는 것 주의할 것
      	//https://blog.naver.com/wlgh325/221391234592
      	int rowexisting = 1;
      	int rownum = 0;
      	while(rowexisting == 1) {	

      	HSSFRow row = sheet.getRow(rownum);
         
  	    ArrayList<JSONObject> sentences = new ArrayList<>();
  	    JSONObject obj = new JSONObject();
  	    String activitystring = "";
  	    
      	int existing = 1;
      	
      	if(row != null) {
      		
      		
      		// 유형자산 등
      		String coa = row.getCell(real).toString();
       		
       		
      	//	String processname = row.getCell(2).toString();
      		ArrayList<String> array = new ArrayList<>();
          	for(int num = row1; num <= row2; num++) {
          		HSSFCell line = row.getCell(num);
          	    if(line != null && line.toString().isEmpty() == false) {
          	    	array.add(line.toString());
          	    	          	    	
          	    	//oppositecoa 채우기
          	    	if(oppositearray != null) {
          	    		if(opt == 0) {
                  	    	oppositearray.put(line.toString(), coa);
          	    		}else {
          	    			oppositearray.put(coa, line.toString());
          	    		}
          	    	}
          	    }
 
          	}
          	
          	// array를 coaarray에 집어넣기
          	if(inarray != null) {
  	    		inarray.put(coa, array);
          	}
 
  	            rownum += 1;
      	}else {
      		rowexisting = 0;
      		
      	}
      	
      	book.close();
  	}
  	
      }catch(FileNotFoundException e) {
    	  System.out.println(e);
      }catch(IOException e) {
    	  System.out.println(e);
      }
      
      
    }  
    
 
    // xl 사용하기 위해서 테스트
    public void rcmlistmake(String sheetname, String name, int row1, int row2, String para)
   		 throws ClassNotFoundException, IllegalAccessException, InstantiationException
         ,NoSuchMethodException, InvocationTargetException 
    {
      // 각 배열에 앞뒤로 집어넣기, pos는 엑셀 열의 위치를 부여하기
      	
      try {
    	  
      	FileInputStream fis = new FileInputStream("C:\\java\\gob\\프로토타입\\계정\\" + name); ///usr/local/gob/"
      	HSSFWorkbook book = new HSSFWorkbook(fis);
      	HSSFSheet sheet = book.getSheet(sheetname);
      	
      	
      	//조금이라도 null이면 null이 떠 버리니 조심할 것
      	//그리고 열과행은 0부터 시작한다는 것 주의할 것
      	//https://blog.naver.com/wlgh325/221391234592
      	int rowexisting = 1;

      	for(int num = row1; num <= row2; num++) {
      		
      		HSSFRow row = sheet.getRow(num);
         
  	        ArrayList<JSONObject> sentences = new ArrayList<>();
  	        JSONObject obj = new JSONObject();
  	        String activitystring = "";
  	    
      	    int existing = 1;
      	    processdata pro = new basicdata();
      	    if(row != null) {
      	      //subwork(row, pro);
      	      	
      	      // 아래쪽에 서브클래스에 subwork 함수를 만들고 익명클래스로 subwork를 오버라이드해서 그것으로 즉시 인스턴스화해서 사용할까도 고려했으나, 
      	      // 예시 위쪽에서 함수 실행시키기 전에 new subclass(){@override
      	      //	                                  subwork(){ ~~~~ 할일을 불라불라 기재 ~~~ } 
      	      // 이것을 매개변수로 넘기기. 이렇게하면 위에가 너무 지저분해지므로       	    	
      	      // 일단은 invoke를 사용함	
      	      
      	      Method me = internalwork.class.getMethod(para, HSSFRow.class, processdata.class);
      		  Object[] param = new Object[] {row, pro};
              me.invoke(internalwork.class.newInstance(), param);
              
      	    }
      	
      	  processrepository.save(pro);
      	}
      	book.close();
      }catch(FileNotFoundException e) {
    	  System.out.println(e);
      }catch(IOException e) {
    	  System.out.println(e);
      }
      
      
    }      
    */
 
    // 위의 invoke 실행을 위한 서브 함수
    public void subwork(HSSFRow row, processdata pro) {
	      // rcm 각 값들 집어넣기
		  ArrayList<String> array = new ArrayList<>();
		  
		  
	      String businesscode  = makestring(row, 0);
	      if(businesscode  != null) {
	      pro.setbusinesscode (businesscode );
	      }



	      String processname = makestring(row, 3);
	      if(processname != null) {
	      pro.setprocessname(processname);
	      }


	      String subprocess = makestring(row, 5);
	      if(subprocess != null) {
	      pro.setsubprocess(subprocess);
	      }

	      String riskcode = makestring(row, 6);
	      if(riskcode != null) {
	      pro.setriskcode(riskcode);
	      }

	      String risk = makestring(row, 7);
	      if(risk != null) {
	      pro.setrisk(risk);
	      }



		  
	      String teams = makestring(row, 47);
	      if(teams != null) {
	    	  
	    	  // split하기
	    	  String[] teamarr = teams.split( "," );
	    	  for(String team : teamarr) {
	    		  
	    		  teamdata tempteam = new teamdata();
	    		  tempteam.setTeamname(team.trim());
	    		  pro.addteamdata(tempteam);
	    	  }
	      }
	      
	      // 자식 집어넣기
	      String node = makestring(row, 50);
	      System.out.println(node);
	      if(node != null) {

	         childnodedata act2 = new childnodedata();
	         act2.setname(node);
	         act2.setsubprocess(pro.getsubprocess());
             pro.addchildnodedata(act2);

	      }    	

    }

    // 위의 invoke 실행을 위한 서브 함수
    public void subwork_node(HSSFRow row, processdata pro) {
	      // node 집어넣기 // 사실 이 부분도 추후에는 위의 split 등을 활용해서 list로 집어넣어야 함
	      String node = makestring(row, 75);
	      if(node != null) {
	    	  
	    	  
	    	  processdata parent = processrepository.getprocessquery("detailprocessname", node).get(0);
	    	  parentnodedata act = new parentnodedata();
	    	 // act.setname(parent.getdetailprocessname());
              pro.addparentnodedata(act);

	    	  childnodedata act2 = new childnodedata();
	    	//  act2.setname(pro.getdetailprocessname());
              parent.addchildnodedata(act2);
              
	    	   
	      }

    }
  
    
    ////
    public String makestring(HSSFRow row, int num) {

    	HSSFCell line = row.getCell(num);
  	    if(line != null && line.toString().isEmpty() == false) {
  	    	return line.toString();
  	    }
   	
    	return null;
    }

	// detailprocess를 찾고, 부모와 자식관계 집어넣기 위한 함수
	public String finddetailprocess(String name){
		List<basicdata> pros = processrepository.getprocessquery("detailprocessname", name); 
		processdata pro = pros.get(0);
		return null;
	}

	class subclass{
		
	}
    
	//// sentenceplus 관련
	public void sentenceplus(JSONObject data, String name) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

	
		 processoption pros = new processoption();
//		 pros.setOption1(subtype);
		 
		 uniteddata uni = UniteddataRepository.findByProcessname1(name).get(0);
		 
		 // 프로세스 부분 업데이트 하기
		 for(int i = 1; i <= this.processcount; i++) {

			 // processname 갈아끼우기
			 String word = "processname" + i;
		 
			 String processname = (String) data.get(word);
			 if(processname == null) {
				 continue;
			 }

			 Class<?>[] parameterTypes = new Class[1];
			 parameterTypes[0] = String.class;
			 
			 Method me = processdata.class.getMethod("setProcessname" + i, parameterTypes);

			 Object parameter[] = new Object[1];
			 parameter[0] = processname;
			 me.invoke(uni, parameter); // text 내용을 갈아끼웠음

			 // processexplain 갈아끼우기
			 word = "processexplain" + i;
			 
			 String processexplain = (String) data.get(word);
			 parameterTypes[0] = String.class;
			 
			 me = processdata.class.getMethod("setProcessexplain" + i, parameterTypes);
			 parameter[0] = processexplain;
			 me.invoke(uni, parameter); // text 내용을 갈아끼웠음

			 // processplus 갈아끼우기
			 word = "processplus" + i;
			 
			 System.out.println(word);
			 if(data.get(word) != null) {
				 System.out.println(data.get(word));
				 Integer processplus = Integer.parseInt(String.valueOf(data.get(word)));
				 parameterTypes[0] = Integer.class;
				 
				 me = processdata.class.getMethod("setProcessplus" + i, parameterTypes);
				 parameter[0] = processplus;
				 me.invoke(uni, parameter); // text 내용을 갈아끼웠음
		 }

			 // processpluscount 갈아끼우기
			 word = "processpluscount" + i;
			 System.out.println(word);
			 
			 if(data.get(word) != null) {
				 Integer processpluscount = Integer.parseInt(String.valueOf(data.get(word)));
				 parameterTypes[0] = Integer.class;
				 
				 me = processdata.class.getMethod("setProcesspluscount" + i, parameterTypes);
				 parameter[0] = processpluscount;
				 me.invoke(uni, parameter); // text 내용을 갈아끼웠음
			 }
			 
		 }
		 
		 UniteddataRepository.save(uni);
		 System.out.println("일단 통과함");
		
	}
	
    
}