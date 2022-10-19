<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
       <meta name="_csrf_header" content="${_csrf.headerName}">
       <meta name="_csrf" content="${_csrf.token}">
  
 
</head>

<style>

table, input {
    border: 1px solid #444444;
    border-collapse: collapse;
    width: 90%;
    margin-top: 20px;
    margin-left: 20px;
 }
  
table th {
  border: 1px solid #444444;
  font-weight: bold;
  background: #dcdcd1;
  width: 100px;

  }

table td {
    border: 1px solid #444444;
    background: white;
    height: 25px;
    width: 100px;
  }


  .maintext{
      display: inline;
 } 
 
  .maintext div{
      display: inline;
 } 

 .listmanage{
  height: 50px;
  width: 1410px;
  display: inline-block;
  border: 1px solid #444444;
  font-weight: bold;
  background: white;
  padding: 0px;
  overflow: hidden;
  margin-top: 20px;
  margin-left: 10px;
}
 

.detailcontent{
  float : left; 
  border: 1px solid black; 
  height: 45%; 
  width: 200px;
  background: gray;
  cursor: pointer;
}

</style>

<body>
                 <table id = 'realtable'></table>
  
</body>
</html>

    <script src = "http://code.jquery.com/jquery-3.4.1.js"></script>


<script>


window.onload = () => {
        var styleobj = document.getElementById('content_wrap');    
        styleobj.style.overflow = "scroll";
        
		var tablecon = new table();
}



class table{
	
	constructor(){

		this.table = document.getElementById('realtable');
		
		this.tablesize = {width: 4, height: 1};
		// 로컬스토리지 데이터 조회
		this.totaldata = JSON.parse(window.localStorage.getItem('totaldata'));	
		
		
	       this.tablesize = {width : 4, height : 1};
	       this.tablearrcount = 0;
	       this.tablearr = {};
		
		
		this.incompletelist = {};
		this.processcount = 7; 
		this.button_possible = 1;
		this.maintextarr = {}
		
		this.findincomplete().then(() => {
			
			this.tablesize['height'] = Object.keys(this.incompletelist).length + 1;
			this.maketable();
			
			// 문장 만들기
		       for(var str in this.incompletelist){
		           var input = {};
		           input[str] = str;
		           this.currentkey = str;
		           this.maintextarr[str] = {}; 

		    	   this.inputtable(input, this.tablearr, "processexplain", "controlexplain", this.tablesize.height, "tablearrcount");        
		    	

		    	   
		       }
		    // 변경사항 반영하기
		       this.changewordtable();
			
		});
		  
		  this.totalmanage = {} // 아래 3개를 관리하기 위해서 만들었음
	      this.managearr = {};     //{"담당자": select, "팀1": select 등} 으로서 쉽게 담당자 등의 select에 접근하기 위해서 만들었음
          // 마지막에 이 값만 ajax로 넘겨버리면 모든 답을 받을 수 있는 구조임
          this.manageinnertext = {};    //{"process1_0": div, "process1_1": div } 으로서 쉽게 사이사이에 있는 innertext에 쉽게 접근하기 위해서 만들었음
          this.managefirsttext = {};    //{"process1_0": "진짜글", "process1_1": "진짜글" } 으로서 여기서 @를 찾아서 위의 manageinnertext에 접근하여 글을 바꿀 것임
	
          
          this.makebutton();
          
	}

	
	findincomplete(){
		
		return new Promise((resolve) => {
			
			var count = 0;
			
			var size = Object.keys(this.totaldata).length;
			for(var data in this.totaldata){
                
				var fail = 0;

				for(var da in this.totaldata[data].processoption){
                    
					for(var risk = 1; risk <= 5; risk++){
						var riskname = this.totaldata[data].processoption[da]['risk' + risk];
						if(this.totaldata[data].processoption[da].result == riskname){
							
							if(this.incompletelist[data]){
								
							}else{
								this.incompletelist[data] = {};
							}
							this.incompletelist[data][da] = this.totaldata[data].processoption[da]['riskreason' + risk];
							
					    }
						
					}
					
				}
				
				
			}
			
			
			resolve();
			
		})
	}
	
	  process_answer_front(){
		  
		  
		  // realhash에 결정된 값을 집어넣기
		  var realhash = {}
		  var arr = ['select']
		  
		  
		  

		  
		  for(var tag in arr){
			  
			  for(var aa = 0; aa < 2; aa++){  // 2열이므로 aa는 두개임
				  
			      
				  for(var current in this.tablearr){
					  var list = this.tablearr[current][aa].querySelectorAll(arr[tag]);
					  
					  if(list.length > 0){
						  var failsure = 1;
						  list.forEach((item) => {
							  // 이제 리스트 식이 아니라, name마다 하나씩이도록 조정함
							  if(item.value == "없음"){
								  realhash[item['name']] = null;
							  }else{
								  // 값 넣기전에 risk위반인지 검증하기 
								 
							     for(var risk = 1; risk <= 5; risk++){
							         
							    	 if(this.totaldata[current].processoption[item['name']]["risk" + risk] == item.value){
							    		 alert(current + " 프로세스의 " + item['name'] + ": 리스크가 해소되지 않았습니다.");
							    		 failsure = 0;
							    		 break;
							    	 }
							     }
								 
								 this.totaldata[current].processoption[item['name']].result = item.value;
							  }
							  
						  });
						  
						  if(failsure == 0){
							  return 0;
						  }
						  
					  }
				  }			  
			  }
		       

		  }
		  
		  // totaldata 갈아끼우기
		  window.localStorage.setItem('totaldata', JSON.stringify(this.totaldata));	
          return 1;
	  }
	
		makebutton(){
		   	 
			 // 뒤에 추가버튼 만들기
		     var button = document.createElement("input");
		     button.setAttribute('type', "button");
		     button.value = "확정하기"
		     
	    	 document.getElementById("content_wrap").appendChild(button);
		     
		     button.addEventListener('click', () => {
		    	 
		    	 var fail = 0;
		    	 
			    	 if(this.process_answer_front() == 0){
					    	fail = 1;
					    	
				     }
		    		
		    	 
		    	 if(fail == 0){
				     alert("정상적으로 입력이 되었습니다.");
		    	 }
		    	 
		     })
		     
		     
		}
	
	maketable(){
		
	    // processlist 반영하기 
	    var list = Object.keys(this.incompletelist)
	    for(var i = 0; i < this.tablesize.height;i++){

	    	if(i == 0){
			    var subdiv = this.maketrtd(["구분", "미비사항", "프로세스", '통제활동'], "th");
		 		this.table.appendChild(subdiv)
		    	
		    }else{
		    	
		    	var weaksentence = "";
		    	
		    	for(var ia in this.incompletelist[list[i - 1]]){
		    		weaksentence += ia + ": " + this.incompletelist[list[i - 1]][ia] + "\n";
		    	}
		    	
		    	
		    	var process = this.totaldata[list[i - 1]].processexplain1;
		    	var control = this.totaldata[list[i - 1]].controlexplain1;
		    	this.tablearr[list[i - 1]] = {}
		    	
			    var subdiv = this.maketrtd([list[i - 1], weaksentence, "", ""], "td", this.tablearr[list[i - 1]]);
		 		this.table.appendChild(subdiv)
		    	
		    }
		}
	    
	}


	maketrtd(arr, opt, tablearr){
	    
	 	var div = document.createElement("tr")

	 	div.style = "height : 20px";

	 	for(var i = 0; i < this.tablesize.width;i++){
		   var subdiv = document.createElement(opt);

		   subdiv.innerText = arr[i];
		   if(i == 0){
	    	   subdiv.style.width = "10%";
		   }else if(i == 1){
	    	   subdiv.style.width = "20%";
		   }else if(i >= 2){
	    	   subdiv.style.width = "35%";
	    	   
	    	   if(tablearr){
		    	   tablearr[i-2] = subdiv;
	    	   }
		   }
		   div.appendChild(subdiv);
	 	}
		return div
	} 
	

	
    inputtable = (arr, tablearr, parameter1, parameter2, size, word) => {
        
    	var data = this.totaldata;
    	
    	// 테이블 다시 채우기
    	
        var rowval = Math.min(arr.length, this.tablesize.height - 1);
    	
        if(data == null){
        	data = arr
        }
        
        var r = 0;  
        var num = 1;
        
        
    	for(var i in arr){
                
               // for(var r in tablearr){
                	
                	
                	if(parameter1 + num in data[i]){
                		if(data[i][parameter1 + num] != ""){
                            
                            this.makesentence(i, data, parameter1 + num);
                            tablearr[i][0].appendChild(this.maintextdiv);
                            this.makesentence(i, data, parameter2 + num);
                            tablearr[i][1].appendChild(this.maintextdiv);

                             
                            var processoption = this.totaldata[this.currentkey].processoption

       	        	        for(var k in tablearr[r]){
       	        	        	
                                this.tablearr[r][k].querySelectorAll('select').forEach((elem, index) => {
           	        	           var name = elem.getAttribute("name");
           	        	           var result
           	        	           if(name in processoption){
           	        	        	   if(processoption[name].result){
           	        	        		   elem.value = processoption[name].result
           	        	        	   }
           	        	           }
            	        	    })
       	        	        }
                            
                            var tag = this.makelabel2(i, data, num, parameter1, parameter2, r, tablearr, word);
                   	        
                            /* 3열을 일단은 지웠으므로 이것을 뺏음. 유동적이므로 지우지는 말 것 
                            for(var k = tablearr[r][2].childNodes.length - 1; k >= 0; k--){
                            	tablearr[r][2].removeChild(tablearr[r][2].childNodes[k]); 
             	            }
                            
                            if(tag){
                                tablearr[r][2].appendChild(tag);
                            }
                            */

                            
                		}else{
                			this[word] = num
                			break
                		}
                	}
                //}
               	
                //this.additem5(this.tablearr[r][2], data[i]); 현재는 역할이 없는 것 같음
                if(r > rowval){
                	break;
                }
         }
    	
    }
    
    makediv(value, style){
    	
    	
    	var field = document.createElement("div");
    	
    	// style은 json 형식으로 받을 것
    	if(style){
    		for(var i in style){
    			field.setAttribute(i, style[i]);
    		}
    	}
    	
    	field.innerText = value
    	return field;    	
    }

    
    makesentence(key, data, parameter){
		
    	
    	
		var text = data[key][parameter];
		
		
		if(text == null){
			return;
		}
		
		var re = /\^\{[가-힣0-9\_]+\}\^/g;
		var textarr = text.split(re);
		
		
		
		var re = /(?<=\^\{)[가-힣0-9]+(?=\}\^)/g;
		//var re = /\^\{[가-힣0-9]+\}\^/g;
		var optionarr = text.match(re)

		// @ 뽑아내기
		var re2 = /(?<=\@\{)[가-힣0-9\_]+(?=\}\@)/g;
		var optionarr2 = text.match(re2);
		
		this.maintextdiv = document.createElement("div");
		this.maintextdiv.setAttribute("class", "maintext");
		
		if(key in this.managefirsttext){
			
		}else{
			this.managefirsttext[key] = {};
			this.managearr[key] = {};
			this.manageinnertext[key] = {};
		}
		
		
		if(optionarr){
			// 정상적인 경우
			// 순서가 엉망이 되어서, 재귀함수로 처리함
			
			this.nextloop(key, optionarr, textarr, data, 0, parameter);
		}else{
			// 옵션이 전혀없는 경우에는 바로 내용 띄울 것
    		var div1 = this.makediv(textarr[0]);
			this.maintextdiv.appendChild(div1);
		}
		
	}
    
	changewordtable(){
		
		
		for(var i1 in this.managefirsttext){
			
			for(var i2 in this.managefirsttext[i1]){
				var text = this.managefirsttext[i1][i2];
				
				
				for(var j in this.managearr[i1]){
					text = text.replace("@{" + j + "}@", this.managearr[i1][j].value)
					this.manageinnertext[i1][i2].innerText = text;
				}
				
			}
		   
		}	

		
	}
	
    nextloop(key, optionarr, textarr, data, i, parameter){
		
		
		// makediv 만들고, 그 다음 maketext, makeselect 등 만들것
		var div1 = this.makediv(textarr[i]);
		this.manageinnertext[key][parameter + "_" + i] = div1; // this.managetext[0] = div1, this.managetext[1] ~~ 에 순차적으로 텍스트가 들어있으므로
		this.managefirsttext[key][parameter + "_" + i] = textarr[i]; // 이것만 쭈욱 갈아끼우면 됨. 
		this.maintextdiv.appendChild(div1); 
		
		
		
		
		if(i < optionarr.length){
			var val = optionarr[i]
			
			// select인지 text 등인지로 구분받아서, 함수실행
			
			var opt = data[key]['processoption'][val]["showing"]
			
			// 앞으로 selectplus 등 구현해 가야함
			if(opt == null  || opt == "sentenceplus"){
				opt = "select"
			}
			var div2 = this['make' + opt](data[key]['processoption'][val], val);
			
			this.managearr[key][optionarr[i]] = div2; // 이런 꼴을 통해서 관리해서, @에다가 집어씌우려고, this.managearr["담당자"] = <select />
			                                     // 위의 construct에 상세한 설명을 써놓았으니 참고할 것
			
			                                     
			this.maintextarr[key][val] = div2;
			this.maintextdiv.appendChild(div2);
		}

    	
		if(i + 1 < textarr.length){
			var i1 = i + 1;
			this.nextloop(key, optionarr, textarr, data, i1, parameter);
		}
		
		
    }
	
    makeselect(data, val){
        
        // option1 ~ option5 이것을 for문 5까지로 수정할 것
  	   var field = document.createElement("select");
  	   field.setAttribute("name", val);

        // 없음 집어넣기, 다만 나중에 없음을 이것을 일부분에 대해서만 적용하고 싶으면 이를 옵션으로 할지 고민하자 
        
  	   var option = document.createElement("option");
    	   //option.innerText = "없음";
        //field.appendChild(option);
  	   
  	   for(var j = 1; j <= 5; j++){
  		   
  		   if(data['option' + j] != ""){
  	       	   var option = document.createElement("option");
            	   option.innerText = data['option' + j];
                field.appendChild(option);
  		   }else{
  			   break
  		   }

  	   }
  	    
  	   
  	   field.addEventListener('change',(me)=>{
  		  
  		  this.changewordtable();
  	   })
  	   
 		return field;    	
     }
    
    
    makelabel2(name, data, num, parameter1, parameter2, row, tablearr, word){
    	
    	var val = data[name]['processplus' + num];
    	
    	//객관식을 위해서, process1 ~ 5에서 필수적인 것은 1, 객관식 선택가능한 것은 2, sentenceplus의 경우에는 3 등으로 구분을 위한 열을 만들자. 
    	// Sentenceplus는 프로세스에 한번 컨트롤에 한번만 사용하도록 하자.
    	
    	if(val == 1){
    		// 여기서는 처리할 게 없음

    	}else if(val == 2){
    	       var check = document.createElement("input");
    	       check.setAttribute('type', "checkbox");
               return check;
    	
    	}else if(val == 3){
    	       var button = document.createElement("input");
    	       button.setAttribute('type', "button");
    	       button.value = "추가";
    	       
    	       
    	       // 이것을 클릭하면 그대로 복제해야 함
    	       button.addEventListener('click',(me)=>{
      	    	      // row 아래 것들은 한칸씩 밑으로 내릴것
      	    	      var count = this[word]
      	    	      var subdiv = this.maketrtd(tablearr[count]);
      	    	      
      	    	      
      	    	      tablearr[num][0].parentNode.parentElement.insertBefore(subdiv, tablearr[num][0].parentNode.nextElementSibling);
                      this.makesentence(name, data, parameter1 + num);
      	    	      tablearr[count][0].appendChild(this.maintextdiv);
                      this.makesentence(name, data, parameter2 + num);
                      tablearr[count][1].appendChild(this.maintextdiv);

    	        	  // sentencearr 갯수를 컨트롤 하고, 삭제버튼을 누를때 무조건 마지막 것만 삭제를 하도록 하기위해서 관리하는 배열 저장
    				  this.sentencecountarr.push(count);
                      
             	      var button2 = document.createElement("input");
        	          button2.setAttribute('type', "button");
        	          button2.value = "삭제";
        	          tablearr[count][2].appendChild(button2);
        	          this[word]++


        	          //tablearr[count][0] 및 tablearr[count][1]의 tag 중에서 name이 팀1이 있으면 이를 팀2 등으로 바꿔줄 것
        	          
        	          this.tablearr[count][0].querySelectorAll('select').forEach((elem, index) => {
        	        	  var name = elem.getAttribute("name");
        	        	  var pos = this.sentencecountarr.length + 1
        	        	  var re = /(?<=[가-힣]+)1$/g;
           	        	  elem.setAttribute("name", name.replace(re, pos));
                      })
        	          this.tablearr[count][1].querySelectorAll('select').forEach((elem, index) => {
        	        	  var name = elem.getAttribute("name");
        	        	  var pos = this.sentencecountarr.length + 1
        	        	  var re = /(?<=[가-힣]+)1$/g;
           	        	  elem.setAttribute("name", name.replace(re, pos));
                    	  
                      })

                      
           	          
           	          // 서버에 추가할 processexplain 등을 넘겨주기 위해서 sentencearr에 저장하기
           	          // 먼저 들어갈 위치 찾기
           	          for(var i = 1; i <= this.processcount; i++){
           	        	  
           	        	  // pluscount가 1에 해당하는 게 나오면 그것이 sentenceplus의 기준이 되는 것임  
           	        	  
           	        	  
           	              // 지금 현재는 pluscount에 숫자 들어가있는 것이 없기 때문에, 일단 뽑히도록 하기 위해서 아래처럼 함
           	              // 나중에는 이것으로 바꿀 것 if(data[name][parameter1.replace('name', 'pluscount') + i] == 1){
           	        	  if(data[name][parameter1.replace('name', 'pluscount') + i] == 1){
            	        	  var sentence1 = data[name][parameter1 + i];
           	        		  var sentence2 = data[name][parameter2 + i];
           	        	  }	
           	        	  
           	        	  // 집어넣을 곳 위치가 파악되면 집어넣기 // 위에 조건문부터 무조건 나오는구조므로 순환문 하나 더 파지않고, 그대로 수행하였음
           	        	  // 팀1 등을 팀2로 바꾸는 것 수행함
           	        	  if(!data[name][parameter2 + i]){
           	        		 var pos = this.sentencecountarr.length + 1
           	        		 
           	        		 var re = /(?<=\^\{[가-힣]+)1(?=\}\^)/g;
           	        		 data[name][parameter2 + i] = sentence2.replace(re, pos)
           	        		 data[name][parameter1 + i] = sentence1;
           	        		 data[name][parameter1.replace('name', 'pluscount') + i] = pos;
           	        		 data[name][parameter1.replace('name', 'plus') + i] = 3;
           	        		 // sentencearr를 저장하려고 하였으나, 그냥 쓰지 않고, 서버에서, processpluscount 등이 있으면 그것으로 저장을 하게 할 것
           	        		 
           	        		 var option = data[name]['processoption']
           	        		 var re = /(?<=[가-힣]+)1$/g;
           	        		 for(var j in option){
           	        			var optionarr = j.match(re);
           	        			if(optionarr){
           	        				var optionname = j.replace(re, pos);
           	        				
           	        				option[optionname] = JSON.parse(JSON.stringify(option[j]));
           	        				option[optionname]['realname'] = optionname;
           	        			}

           	        		 }
           	        		 
           	        		 
           	        		 break
           	        	  }
           	        	            	        	 
           	          }
           	        	
           	          // 삭제부분
        	          // 이거 누르면 다시 원래대로 삭제하고 돌아와야 함
           	          button2.addEventListener('click',(me)=>{

           	        	 var pos = this.sentencecountarr.pop();
           	        	 this[word]--;
           	        	 tablearr[pos][0].parentNode.parentNode.removeChild(tablearr[pos][0].parentNode)
          	        	 
           	        	 
           	        	 for(var i = 1; i <= this.processcount; i++){
          	        	  // 삭제할 위치가 파악되면 삭제하기
           	        	  if(data[name][parameter1.replace('name', 'pluscount') + i] == pos){
           	        		 
           	        		 
           	        		
           	        		 var re = /(?<=\^\{[가-힣]+)1(?=\}\^)/g;
           	        		 data[name][parameter2 + i] = null;
           	        		 data[name][parameter1 + i] = null;
           	        		 data[name][parameter1.replace('name', 'pluscount') + i] = null;
           	        		 data[name][parameter1.replace('name', 'plus') + i] = null;
           	        
           	        		 // sentencearr를 저장하려고 하였으나, 그냥 쓰지 않고, 서버에서, processpluscount 등이 있으면 그것으로 저장을 하게 할 것
           	        		 var re = "(?<=[가-힣]+)" + pos + "$";
           	        		 var option = data[name]['processoption']
           	        		 
           	        		 for(var j in option){
           	        			var optionarr = j.match(re);
           	        			if(optionarr){
                                    
           	        				delete(option[j])
           	        			}

           	        		 }
           	        		 
           	        		 
           	        		 break
           	        	   }
           	        	            	        	 
           	             }
           	        	 
           	        	 

           	        	 
           	          })           	        	  
           	        	  
           	          
    	        })
    	       
    	       return button;
    	}
    }
    
    
	
}

 



function ajaxmethod(){
	

	 
	// 스프링 시큐리티 관련
	
	var header = $("meta[name='_csrf_header']").attr('content');
	var token = $("meta[name='_csrf']").attr('content');
	
		$.ajax({
			type : "POST",
			url : "currentview_confirm",
			data : {},
			beforeSend: function(xhr){
			  if(token && header) {
		        xhr.setRequestHeader(header, token);
			  } 
		    },
		    success : (res) => {
				

    		    	//alert(Object.keys(res).length);
		    	
		    	 if(res['결과'] == "다음 단계로 넘어갔습니다."){
		    		 
		    		 // 저장해야하는 상태
		    		 if(Object.keys(res).length > 1){
		    			 
		    			 for(var str in res){
		    				 if(str != '결과'){
		    					 delete res['결과'];
		    					 window.localStorage.setItem('totaldata', JSON.stringify(res));
		    					 
		    				 }
		    			 }
		    		 }
		    		 
		    			location.href= "/internal/loginSuccess";	
            }
                
                
			},
        error: function (jqXHR, textStatus, errorThrown)
        {
               console.log(errorThrown + " " + textStatus);
        }
		})		
}

/*
<li><a href="Scoping">Scoping</a></li>
<li><a href="basemapping">각종 매핑</a></li>
<li><a href="basestructure">기본계층 만들기</a></li>
<li><a href="basequestion">기본질문지</a></li>
<li><a href="gojs_work">상세질문지</a></li>
<li><a href="gojs9">플로우차트</a></li>
<li><a href="explanation">이해하기</a></li>
*/


</script>