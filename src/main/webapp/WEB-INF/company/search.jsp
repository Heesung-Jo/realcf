<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t" %>
<%@ page session="false" %>

            <form id="search" method = "post" action = "">
              <div id = "contentitem"> 
                </br>
                <div >
                    <span>업종 선택</span>
                    <span id = "business" class = "listmanage"></span>
                    <span>
                         <input type ="button" class = "button" id = "businessplus" value = "+"/>
                    </span>
                </div>
            
               </br>
                <div>
                    <span>회사 선택</span>
                    <span id = "company" class = "listmanage"></span>
                    <span >    
                        <input type ="button" class = "button" id = "companyplus" value = "+"/>
                    </span>
                </div>
                
                </br>
                <div>
                    <span>계정 선택</span>
                    <span id = "coa" class = "listmanage"></span>
                    <span>    
                        <input type ="button" class = "button" id = "coaplus" value = "+"/>
                    </span>
                </div>
                
                </br>
                <div style = "width: 100%">
                    <span >조회 기준</span>
                    <span style = "width: 15%">
                       <select id = "selectoption" style = "float: left; height: 22px; width: 100%">
                          <option>금액기준으로 조회</option>
                          <option>자산대비 비율기준으로 조회</option>
                       </select>
                    </span>
                    <span style = "width: 65%">
                       <input type ="button" id = "submitbutton" value = "제출하기" />
                    </span>
                </div>
            
                </br>
                
              </div>
              
              <div id = "tableitem"> 
                  <div id = 'tablediv'>
                  </div>
              </div>
                             
            </form>
            
            
            <div id = 'subtablediv'>
                <table id ='subtable'></table>
            </div>
            
<style>


#submitbutton {
  float: left;
  margin: 0 0 0 20px;
  width: 100%;
}

#subtablediv {
   width: 14%;
}

#subtable tr:hover th {
   background: yellow;
}

span {
    display: inline-block;
    height: 20px;
    vertical-align: middle;
    font-weight: bold;
    
}

#contentitem {
    position: relative;
    width: 90%;
    margin: 20px 0 5px 120px;
    padding: 20px 0 5px 28px;
    border: 1px solid black; 
    background: #f7f9fa;
 }



.listmanage{
  height: 20px;
  width: 80%;
  display: inline-block;
  border: 1px solid #444444;
  font-weight: bold;
  background: white;
  padding: 0px;
  overflow: hidden;
  
}


.listmanage:active {
   border: 1px solid blue;
}

.bundle{

  float: left;
  padding: 0px;
  width: 20%;
  height: 100%;
}

.detailcontent{
  float : left; 
  border: 1px solid black; 
  height: 100%; 
  border: 0px;
}

.button {
  width: 20px;
  height: 20px;
  border: 1px solid #444444;
  padding: 0px;
}





table {
    border-collapse: collapse;
    width: 100%;
    
  }
  
table th {
  border: 1px solid #444444;
  font-weight: bold;
  background: #dcdcd1;
  width: 14%;
  height: 20px;
}

table td {
    border: 1px solid #444444;
    background: white;
    height: 20px;
}


#tableitem {
    
    width: 92%;
    margin: 20px 0 5px 120px;
    border: 1px solid black; 
    background: #f7f9fa;
    border-collapse: collapse;
 }


#tablediv {
   align-items: center;
   justify-content: center;
   height: 450px;
   overflow: scroll;
   width: 100%;
}




</style>            
            
<head>
   <meta name = "_csrf" content = "${_csrf.token}" />
   <meta name = "_csrf_header" content = "${_csrf.headerName}" />

</head>

<script src = "http://code.jquery.com/jquery-3.4.1.js"></script>
            
<script type="text/javascript">


class showing{

	constructor(obj){
		
	  // tag 세팅
	  this.business = document.getElementById("business");  
	  this.company = document.getElementById("company");    
	  this.coa = document.getElementById("coa");
	  this.opt = document.getElementById("selectoption");
      this.optval = "";
	  this.subtablediv = document.getElementById("subtablediv");
      this.selectoption = document.getElementById("selectoption");
      this.subtable = document.getElementById("subtable"); // div 가 나은것 같으면
      this.subtablearr = {};
	  
	  // 회사선택, 계정선택 등을 위한 구성화면과 그에 따른 리스너
	  this.businessarr = []; // 서버에서 받아올 것
	  this.coaarr = [];  // 서버에서 받아올 것
	  this.companyarr = []; // 서버에서 받아올 것
	  
	  this.searcharr = {}; // user의 요청에 대한 서버의 회신 arr
	  
	  // 임시의 코드임
	  
	  this.businessbutton = document.getElementById("businessplus");  // 내용(select) + x표시
	  this.companybutton = document.getElementById("companyplus");    // 내용(text) +  x표시 
	  this.coabutton = document.getElementById("coaplus");            // 내용(select) + 금액/비율 + x표시
	  
	  this.submitbutton = document.getElementById("submitbutton"); 
	  
	// 비지니스 더블 클릭할때 이벤트 기록
	this.business.addEventListener('dblclick',(me) => {
		  if(this.business.childNodes.length < 5){
			  var div = this.makediv(this.businessarr, null, "X");
			  this.business.appendChild(div);
		  }else{
			  alert("더이상 추가되지 않습니다.")
			  
		  }
		
	})
	  
	// 비지니스 버튼 등을 누를때 이벤트 기록
	this.businessbutton.addEventListener('click',(me) => {
	  if(this.business.childNodes.length < 5){
		  var div = this.makediv(this.businessarr, null, "X");
		  this.business.appendChild(div);
	  }else{
		  alert("더이상 추가되지 않습니다.")
		  
	  }
	});        


	this.keyeventcount = "";
	// 회사를 더블 클릭할때 이벤트 기록
	this.company.addEventListener('dblclick',(me) => {
		this.addevent_company(me);
	})
	// 회사를 더블 클릭할때 이벤트 기록
	this.companybutton.addEventListener('click',(me) => {
		  
         this.addevent_company(me);

	});        

	// coa을 더블클릭할때 발생이베튼 
	this.coa.addEventListener('dblclick',(me) => {
		  if(this.coa.childNodes.length < 5){
			  var div = this.makediv(this.coasortedarr, null, "X"); 
			  this.coa.appendChild(div);
		  }else{
			  alert("더이상 추가되지 않습니다.");
		  }
    })

	// coa 버튼을 클릭할때 발생이베튼 
	this.coabutton.addEventListener('click',(me) => {
		  if(this.coa.childNodes.length < 5){
			  var div = this.makediv(this.coasortedarr, null, "X"); 
			  this.coa.appendChild(div);
		  }else{
			  alert("더이상 추가되지 않습니다.");
		  }
		  
     });        

	  // 제출하기 버튼. 안의 값이 비었는지 등을 확인하고 서버에 요청하자
      
	  this.submitbutton.addEventListener('click',(me) => {
              this.submittest();
	  });        
	  
	  var func = (res) => {
		  this.coaarr = new Set(res.coa);
		  this.coasortedarr = JSON.parse(JSON.stringify(res.coa));
		  this.coasortedarr.sort();
		  this.coasortedarr = new Set(this.coasortedarr);
		  this.companysortedarr = JSON.parse(JSON.stringify(res.company));
		  this.companysortedarr.sort();
		  this.companyarr = new Set(res.company); 
		  
		  this.businessarr = new Set(res.business.sort());

	  }

      this.ajaxmethod("searcharray", {}, func);

	  
	  // 테이블 만들기
	   this.tablearr = {}
	   this.table = this.maketable();
 	   var temp = document.getElementById("tablediv")
 	   temp.appendChild(this.table);
	  
	}

	
	addevent_company(me){
		if(this.company.childNodes.length < 5){
			  var div = this.makediv("select", null, "X");  
			  this.company.appendChild(div);
			  var text = div.querySelector("input[type='text']");

		      text.addEventListener('input', (me)=>{
		    	  this.decidecompanysmall(me.target.value, me)})

		      text.addEventListener('blur', (me)=>{
		    	  this.keyeventcount = "";
	  			  this.subtablediv.style =  "position: absolute;z-index: -100"
	     		        for(var k = this.subtable.childNodes.length - 1; k >= 0; k--){
	     		        	this.subtable.removeChild(this.subtable.childNodes[k]); 
	     		       }	    	  
		    	  
	          })

		    	  
		      text.addEventListener('keydown', (e)=>{

		    	  if(e.key == "Enter"){
	       			  this.subtablediv.style =  "position: absolute;z-index: -100"
	       		        for(var k = this.subtable.childNodes.length - 1; k >= 0; k--){
	       		        	this.subtable.removeChild(this.subtable.childNodes[k]); 
	       		       }
	       			  event.preventDefault();
		    	  }
		    	  
		    	  if(e.key == "ArrowDown"){
		    		  
		    		  
		    		  if(this.keyeventcount ===""){
		    			  
			    		  this.keyeventcount = 0;
		    		  }else if(this.keyeventcount >= this.subtable.childNodes.length - 1){
		    			  
		    			  return;
		    		  }else{
			    		  this.subtable.childNodes[this.keyeventcount].childNodes[0].style = "";
			    		  this.keyeventcount += 1
			    		  
		    		  }
		    		  e.target.value = this.subtable.childNodes[this.keyeventcount].innerText;
		    		  this.subtable.childNodes[this.keyeventcount].childNodes[0].style = "background: yellow;"
		    		  
		    		  
		    	  }else if(e.key == "ArrowUp"){

		    		  if(this.keyeventcount ==""){
			    		  this.keyeventcount = 0;
		    		  }else if(this.keyeventcount <= 0){
		    		     return;
		    		  }else{
			    		  this.subtable.childNodes[this.keyeventcount].childNodes[0].style = "";
			    		  this.keyeventcount -= 1
		    		  }

		    		  e.target.value = this.subtable.childNodes[this.keyeventcount].innerText;
		    		  this.subtable.childNodes[this.keyeventcount].childNodes[0].style = "background: yellow;"
		    	  }
		    	  
		    	  
		       })
		    	  
			}else{
				
				alert("더이상 추가되지 않습니다.");
			}  		
	}
	
	
	async submittest(){
		// 먼저 값들이 모두 들어가 있는지 확인하기
		var arr = [this.business, this.coa, this.company]
		var data = {"business": new Set([]), "coa": new Set([]), "company": new Set([])}
		
		for(var i in arr){
	        for(var k = 0; k < arr[i].childNodes.length; k++){
                
	        	// 어떤 요소인지 찾기
	        	var id = arr[i].id;
	        	
	        	var select = arr[i].childNodes[k].querySelector("select");
	        	
	        	if(select != null){
	        		var val = select.value;
	        		if(this[id + 'arr'].has(val) == false){
	        			alert(id + "을 올바르게 선택해주세요");
	        			return
	        		}else{
	        		    data[id].add(val)	
	        		}
	        	}
	            
	        	var text = arr[i].childNodes[k].querySelector("input[type=text]");
	        	if(text != null){
	        		var val = text.value;
		        	if(this[id + 'arr'].has(val) == false){
		        		alert(id + "을 올바르게 선택해주세요");
		        		return
		        	}else{
		        		data[id].add(val)
		        	}
	        	}
	        	
	       }
		}
		
		// data 변환
		for(var i in data){
			data[i] = Array.from(data[i])
		}
		
		// option도 추가해줄 것

		if(this.opt.value == "금액기준으로 조회"){
			this.optval = "val";
			data['opt'] = "val";			
		}else if(this.opt.value == "자산대비 비율기준으로 조회"){
			this.optval = "ratio"
			data['opt'] = "ratio";
		}
		// ajaxmethod
		
		var func = (res) => {
   		    	this.searcharr = res;
   		    	console.log(res);
   		    	
   		    	this.table.parentNode.removeChild(this.table); 
   		    	this.table = this.maketable(this.searcharr);
   		    	var temp = document.getElementById("tablediv")
   		 	    temp.appendChild(this.table);
		}
		
		await this.ajaxmethod("searchrequest", data, func);
		
	}
	
	
	ajaxmethod(link, data, act){
		
		return new Promise((resolve) => {
		
		// 스프링 시큐리티 관련
		var header = $("meta[name='_csrf_header']").attr('content');
		var token = $("meta[name='_csrf']").attr('content');
		
   		$.ajax({
   			type : "POST",
   			url : "/company/" + link,
   			data : data,
   			beforeSend: function(xhr){
   			  if(token && header) {
   				  
   		        xhr.setRequestHeader(header, token);
   			  } 
   		    },
   		    success : (res) => {
   				
   		    	
   		    	if("error" in res){
   		    		alert(res['error']);
   		    	}else{
   	                act(res); 
   		    	}
   		    	resolve()
                
   		    	
   				
 
   			},
            error: function (jqXHR, textStatus, errorThrown)
            {
                   console.log(errorThrown + " " + textStatus);
            }
   		})		
	  })
	}	
	
	makediv(opt1, opt2, opt3){
		// 내용 + 금액/비율 + x표시
    	var div = document.createElement("div")
        div.setAttribute('class', "bundle");
        // 내용
        if(opt1 != null){
          if(typeof(opt1) == "object"){
        	var subdiv1 = this.makeselect(opt1);
        	subdiv1.setAttribute('class', "detailcontent");
        	subdiv1.style = "width: 80%;"
            div.appendChild(subdiv1);
          }else{
          	var subdiv1 = this.maketext();
        	subdiv1.setAttribute('class', "detailcontent");
        	subdiv1.style = "width: 80%;"
          	div.appendChild(subdiv1);
          }
        }

        // 금액/비율 
        
        if(opt2 != null){
        	var subdiv2 = this.makeselect(["금액", "비율"]);
        	subdiv2.setAttribute('class', "detailcontent");
        	subdiv2.style = "width: 5%;"
            div.appendChild(subdiv2);		
        }

        // x표시
        if(opt3 != null){
        	var subdiv3 = this.makebutton(opt3);
        	subdiv3.setAttribute('class', "detailcontent");
        	subdiv3.style = "width: 20%; font-weight: bold;"
            div.appendChild(subdiv3);		
        }
    	
        return div
	} 
	
	
    makeselect(arr){
        var select = document.createElement('select');
        
        for(var i of arr){
            var opt = document.createElement('option');
            opt.innerText = i;
            select.appendChild(opt)
        }
        return select;
    }

    
    maketext(){
    	var text = document.createElement("input");
    	text.setAttribute('type', "text");
    	return text
    }
    
    makebutton(val){
        var button = document.createElement("Input");
        button.setAttribute('type', "button");
        button.setAttribute('value', val);
        button.addEventListener('click',()=>{this.closediv(button.parentNode)});
        return button;
    } 
    
    
    decidecompanysmall(word, me){
    	
    	
    	var count_min = 1; 
    	var count_max = this.companysortedarr.length;
    	
    	for(var i = 0; i < 15; i++){
    		
    		var count = Math.round((count_min + count_max)/2)
    		
    		if(count_max - count_min <= 1)
    		    break
    		else if(this.companysortedarr[count] < word ){
    			count_min = count
    		}else{
    			count_max = count
    		}
    		
    	}
    	
    	
    	var arr = []
    	
    	for(var i = 1; i < 5; i++){
        	arr.push(this.companysortedarr[count_min + i])
    		if(count_min + i >= this.companysortedarr.length - 1){
    			break
    		}
    	}
    	
    	this.makesubtable(arr, me)
    	
    }
    
    
    makesubtable(arr, me){
        for(var k = this.subtable.childNodes.length - 1; k >= 0; k--){
        	this.subtable.removeChild(this.subtable.childNodes[k]); 
       }

    	var pos = me.target.getBoundingClientRect();
    	
    	// 절대좌표 기준으로 설정
    	var left = Math.round(pos.left - 200 ) + 'px';
    	var top = Math.round(pos.top - 20 + window.pageYOffset) + 'px';
    	this.subtablediv.style = "position: absolute; left: " + left + "; top: " +top+ ";"
    	
    	// processlist 반영하기 
    	for(var i = 0; i < arr.length;i++){
    	    this.subtablearr[i] = {}
    	    var subdiv = this.maketrtd(this.subtablearr[i], 1, null, {"width": "10%"});
     		this.subtable.appendChild(subdiv)
    	    this.subtablearr[i][0].innerText = arr[i]
     		
     		this.addsubtest(me, i)
     		this.subtablearr[i][0].addEventListener("click", (real) => {
     			me.target.value = real.target.innerText;
     			this.subtablediv.style =  "position: absolute;z-index: -100"
       		        for(var k = this.subtable.childNodes.length - 1; k >= 0; k--){
       		        	this.subtable.removeChild(this.subtable.childNodes[k]); 
       		       }

     		})     		
    	}
    	         	
    }
    

    addsubtest(me, i){
 		this.subtablearr[i][0].addEventListener("mouseover", (real) => {
 			if(this.keyeventcount != ""){
     			this.subtable.childNodes[this.keyeventcount].childNodes[0].style = "";
 			}
 			this.keyeventcount = i;
 			me.target.value = real.target.innerText;
 		})
    }
    
	maketable(arr){
		   
		   // 이제 집어넣기 
	       // 테이블 만들어 추가하기
	   	   var temptable = document.createElement("table");
		   this.tablearr = {}  // 기존 tablearr 비우기
	       
	       // 제목행 
	       var thead = document.createElement("thead");
	   	   temptable.appendChild(thead);
	       var temp = {}
	      
	       /*
	       if(arr){
		       var count = Object.keys(arr).length + 2
	       }else{
	    	   count = 3
	       }
           */
           count = 7
	       
	       var subdiv = this.maketrtd(temp, count, "th");
	       temp[0].innerText = "계정명"
	       temp[1].innerText = "계정종류"
		   if(arr){
			   //var count = Object.keys(arr).length + 2
			   var count = 7
		       num = 0;
			   for(var i in arr){
		        	   temp[num + 2].innerText = i;
		               num += 1;
			   }

		    }else{
		       temp[2].innerText = "회사1"
		       temp[3].innerText = "회사2"
			   temp[4].innerText = "회사3"
			   temp[5].innerText = "회사4"
		       temp[6].innerText = "회사5"
	       }
	       
	       
	       thead.appendChild(subdiv);

	       // 내용행
	       var tbody = document.createElement("tbody");
	       temptable.appendChild(tbody);
	       
	       // 테이블 갯수 최소가 20개이고 배열이 더 많으면 그 이상 만들기
	      // var temcount = arr ? Object.keys(arr).length : 0;
          // var count = Math.max(20, temcount);	       
           
   		   
           if(arr){
        	    // 회사별 정렬임
        	    
     		   // 순서대로 표시하기 위해서 this.coaarr를 활용함
     		   
        	   for(var k of this.coaarr){
                   var check = 0;
                   var num = 0;
                   var temparr = {}
                   
                   // 먼저 k란 계정을 반드시 집어넣어야 하는지 체크하기
                   for(var j in arr){
        			   num += 1;
    				   if(k in arr[j]){
    					   check = 1;
    					   break
    				   }
    			   }
        		   
                   // 체크가 되었다면, 이제 집어넣기
        		   if(check == 1){
    	    		   var temp = {}
           			   var subdiv = this.maketrtd(temp, count);
              		   tbody.appendChild(subdiv)

        			   temp[0].innerText = arr[j][k]['name'];
    			       temp[1].innerText = arr[j][k]['bspl'];
    			       var num = 0;
                       for(var j in arr){
            			   
        				   if(k in arr[j]){
        					   check = 1;
        					   
        				       temp[2 + num].innerText = this.valratio(arr[j][k][this.optval]);
        					   temp[2 + num].style = "text-align: right;"
        				   }
        				   num += 1;
        			   }
     			       
        		   }
                   
        		   
        		   
    		   }

	       }else{
	    	   for(var i = 0; i < 20; i++){
	    		   var temp = {}
       			   var subdiv = this.maketrtd(temp, count);
          		   tbody.appendChild(subdiv)
	    		   
	    	   }
	       }   
	        
	      

	       return temptable;
	   }	   
	   
	   comma = (str) => { 
		      str = String(str); 
		      return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,'); 
		   } 
	   
	   valratio(val){
		   
		   if(this.optval == "val"){
			   return this.comma(val);
		   }else if(this.optval =="ratio"){
			   return Math.round(val * 10000)/100 + "%";
		   }
	   }
	   
	   maketrtd(arr, count, td, stylearr){
	       
	       var div = document.createElement("tr");
	    	for(var i = 0; i < count;i++){

	    		if(td){
	            	var subdiv = document.createElement(td)
	    		}else if(i == 0){
	            	var subdiv = document.createElement("th")
	            	
	       	}else{
	            	var subdiv = document.createElement("td")
	       	}

	   	   arr[i] = subdiv;
	   	   
	   	   
	   	   for(var j in stylearr){
	       	   subdiv.setAttribute(j, stylearr[j]);
	   	   }
	   	   
	   	   div.appendChild(subdiv);
	    	}
	   	return div
	   }    
    
    
    
    
    closediv(item){
    	item.parentNode.removeChild(item); 
    }
    
    
}

window.onload = () => {
	show = new showing()

}

</script>
