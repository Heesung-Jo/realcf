package com.service; 

import javax.annotation.PostConstruct;


import java.util.*;




import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;



import com.entity.coagroupdata;
import com.entity.financialstatements;



import com.repository.CoagroupdataRepository;

import com.repository.financialstatementsRepository;



// jsp에서 지금 바로는 원하는데로 안받아지므로, submit를 바꿔서, ajax 형태로 입력되도록 수정할 것

import java.sql.SQLException;
@Service
public class companywork { 


	
    @Autowired
    private financialstatementsRepository financialstatementsRepository;
    
    @Autowired
    private CoagroupdataRepository coagroupdataRepository;
    
 
   // @Autowired
  // private xlmake xlmake;

    private ArrayList<String> coaturnarr = new ArrayList<>();
    private HashSet<String> companyarr = new HashSet<>();
    private HashSet<String> businessarr = new HashSet<>();
    
    @Autowired
    public void companywork() { 
    	setting();
    } 
	
    public financialstatements findbyname(String name) {
    	return financialstatementsRepository.findByname(name);
    }
    
    
    
    
    public void setting() {
    	
    	
    	// 회사배열 입력
    	for(String com : financialstatementsRepository.findnames()) {
    		
    		companyarr.add(com);
    		
    	}
    	System.out.println("여기입니다.");
    	// 비지니스배열 입력
    	for(String com : coagroupdataRepository.findbusiness()) {
    		System.out.println(com);
    		businessarr.add(com);
    	}
     	
    	for(String com : coagroupdataRepository.findcoaname()) {
    		coaturnarr.add(com);
    	}
     	
    }
    
    
    
    
    /*
    
    
    
    
    
   // @PostConstruct
    public int setting() {
      
    	// coadata에 이미 데이터가 있으면 넘겨버리고 없으면 후속작업 진행할 것
        	//List<coagroupdata> coa = coagroupdataRepository.findByname("유동자산");
        	if(financialstatementsRepository.count() > 0) {
            	System.out.println("있으니 인풋하면 안됨");
            	return 0;
        	}
    	
    	
 
    	
//    	HSSFRow row;
  //  	row.getCell(0).getNumericCellValue()
    	
    	// datafordb라는 파일을 db에 저장하기
    	
    	// coaturnobj 시트 저장하기
    	// coaturnarr 만들기
  
    	
    	xlsubwork coms = new xlsubwork() {
    		public void work(HSSFRow row) {
    			String company = row.getCell(0).toString();
    			financialstatements statement;
     			statement = new financialstatements();
    			statement.setname(company);
    			statement.setyear(2020);
    			financialstatementsRepository.save(statement);
   			
    		};
    	};
    	
    	// main data sheet 저장하기
    	xlsubwork sub = new xlsubwork() {
    		public void work(HSSFRow row) {
 
    			
     			
    			coagroupdata coa = new coagroupdata();
    			
    			
    			String bspl  = row.getCell(1).toString();
    			String name = row.getCell(2).toString();
    			String reportname = row.getCell(3).toString();
    			double number = row.getCell(5).getNumericCellValue();
    			double level = row.getCell(6).getNumericCellValue();
    			String company = row.getCell(0).toString();
    			String exceptcol = row.getCell(8).toString();
    			String business = row.getCell(10).toString();
    			
    			try {
    				// null값을 허용함
        			double cash = row.getCell(4).getNumericCellValue();
        			coa.setval(cash);
        			double ratio = row.getCell(9).getNumericCellValue();
        			coa.setratio(ratio);
    			}catch(Exception e) {
    				      				
    			}
    		    
    		
    			// coa data db에 저장하기
    			coa.setcompany(company);
    			coa.setbspl(bspl);
    			coa.setname(name);
    			coa.setreportname(reportname);
    			coa.setnumber(number);
    		    coa.setyear(2020);	
    		    coa.setlevel(level);
    		    coa.setbusiness(business);
    		    coa.setexceptcol(exceptcol);
    		    
    			
    			//statement.addcoadata(coa);
    		    coagroupdataRepository.save(coa);
    			
    			
    			// coagroupdata에 저장하기
   		
    			
     		}
    	};
    	
    	try {
    		xlmake.listmake("company", "datafordb_BS.xls", 0, 2102, coms);
        	xlmake.listmake("data", "datafordb_BS.xls", 0, 65014, sub); // 15619
        	xlmake.listmake("data2", "datafordb_BS.xls", 0, 62357, sub);
    	}catch(Exception e1) {
            // 결국 이것은 파일이 없는 에러이므로 나중에
    		// 다 throw fileexception으로 처리할 것
    		System.out.println(e1);
    	}
    	
    	
    	// company에 자식들 채워넣기
    	for(coagroupdata tempcoa : coagroupdataRepository.findAll()) {
    		System.out.println(tempcoa.getcompany());
    		financialstatements statements = financialstatementsRepository.findByname(tempcoa.getcompany());
    		System.out.println(statements.getname());
    		statements.addcoagroupdata(tempcoa);   	
    		financialstatementsRepository.save(statements);
    	}

    	return 1;
    }
 
    
    
  //  @PostConstruct
    public void setting2() {
    	// companyarr이 만들기
       	xlsubwork coa = new xlsubwork() {
    		public void work(HSSFRow row) {
    			coaturnarr.add(row.getCell(0).toString());
    		}
    	};
    	
    	// businessarr 만들기
       	xlsubwork business = new xlsubwork() {
    		public void work(HSSFRow row) {
    			
    			businessarr.add(row.getCell(0).toString());
    		}
    	};

      	xlsubwork company = new xlsubwork() {
    		public void work(HSSFRow row) {
    			
    			companyarr.add(row.getCell(0).toString());
    		}
    	};
    	
    	try {
    		xlmake.listmake("coaturn", "datafordb_BS.xls", 0, 142, coa);
    		xlmake.listmake("business", "datafordb_BS.xls", 0, 82, business);
    		xlmake.listmake("company", "datafordb_BS.xls", 0, 2102, company);
    	}catch(Exception e) {
            // 결국 이것은 파일이 없는 에러이므로 나중에
    		// 다 throw fileexception으로 처리할 것
    		System.out.println(e);
    	}
    	
 
    }
    
    */
    
    
    public HashMap<String, JSONObject> toresponse(Set<coagroupdata> coas){

    	
    	HashMap<String, JSONObject> temp = new HashMap<>();
    	for(coagroupdata coa : coas) {
    		
    		if(coa.getexceptcol().equals("포함") == true) {
        		// 필요한 데이터 추출하여 json에 집어넣기
        		JSONObject json = new JSONObject();
        		json.put("name", coa.getname());
        		json.put("bspl", coa.getbspl());
        		json.put("ratio", coa.getratio());
        		json.put("business", coa.getbusiness());
        		json.put("val", coa.getval());
        		
        		if(temp.containsKey(coa.getname()) == true) {
        			JSONObject tem = temp.get(coa.getname());
        			tem.put("val", (double) tem.get("val") + coa.getval());
        			tem.put("ratio", (double) tem.get("ratio") + coa.getval());
        			
        		}else {
            		temp.put(coa.getname(), json);
        		}
    		}
    	}
    	
    	return temp;
    }
	     
    
   public ArrayList<String> getcoaturnarr(){
	   return coaturnarr;
	   
   }

   public HashSet<String> getcompanyarr(){
	   return companyarr;
   }
   
   public HashSet<String> getbusinessarr(){
	   return businessarr;
   }

   
   
   public  List<Object[]> findmaxval(String name){
	   return coagroupdataRepository.findmaxval(name);

   }
   public  List<Object[]> findmaxval(List<String> name){
	   return coagroupdataRepository.findmaxval(name);

   }

   public  List<Object[]> findlistobject(List<String> coa, List<String> business, String opt) throws SQLException {
	   
		if(coa != null && business != null) {
			return coagroupdataRepository.findmaxval_all(coa, business, opt);
		}else if(coa != null) {
			return coagroupdataRepository.findmaxval_name(coa, opt);
		}else if(business != null) {
			return coagroupdataRepository.findmaxval_business(business, opt);
		}
	   
		return null;

   }
   

   
}      
    
    
    
    
    
  