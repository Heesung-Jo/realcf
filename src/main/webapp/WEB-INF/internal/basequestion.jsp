
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


#content_wrap {
   width: 100%;
   overflow: hidden;
}



#content_center {
   
   width: 100%;
   overflow: hidden;

}

#content_right { 
 
  background: white;
  float: left;
  width: 20%;
  height: 50%;
}


#content_left {
 
  width : 70%;
  border: 1px solid #5F6673;
  background: #f7f9fa;
  height: 50%;
  float: left;
  
}


 #content_bottom {
  height: 70%;
  width: 95%;
  display: inline-block;
  border: 1px solid #444444;
  font-weight: bold;
  background: white;
  padding: 0px;
  overflow: scroll;
}


 .listmanage{
  height: 50px;
  width: 95%;
  display: inline-block;
  border: 1px solid #444444;
  font-weight: bold;
  background: white;
  padding: 0px;
  overflow: hidden;
}

#inventory {
  height: 20px;
  display: inline-block;
  overflow: hidden;
  
}

.detailcontent{
  float : left; 
  border: 1px solid black; 
  height: 45%; 
  width: 200px;
  background: gray;
  cursor: pointer;
  
}

#item, #content, select, input {
   margin-left: 10px;
   margin-top: 10px;
}

#basequestion {
   margin-left: 10px;
   margin-top: 10px;
}

</style>


<body>
  <!-- This top nav is not part of the sample code -->
         <script src = "http://code.jquery.com/jquery-3.4.1.js"></script>
     
    <div id = "basequestion">
     <div>아래 항목을 클릭해주세요. </div>
     <div>(실제로는 Scoping 과정에서 더 많은 프로세스가 집계되었으나, 시연을 위한 프로그램으로 구매 프로세스 중 일부부만 진행합니다.) </div>
     
     <span id = "step0" class = "listmanage"></span>
     <span id = "inventory" style = "display: none">
           <input type = "radio"  name = "inventoryitem" value = "0" checked >일반질문</input>
           <input type = "radio"  name = "inventoryitem" value = "1" >공정흐름</input>
     </span>
      

     

     
      <div  id = "content_bottom"   >
        <div  id = "item" >
        </div>
      </div>
      <div id = "final"></div>
     </div> 
      
</body>
<!--  This script is part of the gojs.net website, and is not needed to run the sample -->



<script> 

 


    function makeselect(arr, select){
        
        var opt = document.createElement('option');
        opt.innerText = "없음";
        select.appendChild(opt)

        for(var i in arr){
            var opt = document.createElement('option');
            //opt.setAttribute('value', i)
            opt.innerText = arr[i].text;
            select.appendChild(opt)
        }
        return select;

    }




     function poswhile(posarr, realarr, arr, pos, passedhash){

           // passedhash는 이미 위치를 세팅한게 또 나오면 그 위치의 y좌표는 바꾸지 않도록 하기위함
           // 그리고 x위치는 더 큰거로 바꾸는게 목표임
           for(var i in arr){
                 
                 // arr[i]를 realarr에서 찾아서 위치를 바꿔주기
                 for(var j in realarr){
      
                     if(realarr[j].key == arr[i].key){
      
                         // 먼저 passedhash에 있는거라면, 위치를 위에 써진대로 바꿔주
                         if(arr[i].key in passedhash){

                             var ypos = Number(realarr[j].loc.match(/[0-9]{1,4}$/gi)[0])
                             var xpos = Number(realarr[j].loc.match(/^[0-9]{1,4}/gi)[0])
                             xpos = Math.max(pos.x, xpos);
                             realarr[j].loc = xpos + " " + ypos;  
                             var newpos = {x: xpos + 200, y: ypos}
                             break
 
                         }else{
                             realarr[j].loc = pos.x + " " + pos.y;
                             
                             
                             passedhash[arr[i].key] = 1;
                             var newpos = {x: pos.x + 200, y: pos.y}
                             pos.y += 100;
                             break
                         }

                      }
                     
                 }

                 var nextarr = []

                 // 이제 자식을 찾아서 넣어주기
                 for(var j in posarr){
                    if(posarr[j].from == arr[i].key){
                       var to = posarr[j].to;

                       for(var z in realarr){
                             if(realarr[z].key == to){
                               nextarr.push(realarr[z])
                               break
                           }
                       }   
                    }
                 }
                 
                      
                 if(nextarr.length > 0){
                     poswhile(posarr, realarr, nextarr, newpos, passedhash)
                 }                                

           }
     }

     

      function addlisten(item){
        item.addEventListener('click',()=>{
            addnodedata()
        })
      }

      function addlisten_sel(item, color){
        item.addEventListener('change',(me)=>{
        	
            changecolor(me.target.value, color)
        })
      }



      // div 관련 함수들
      
      function additem(){
          //var currentitem = this.totalarr[this.countnumber]
          var div = makediv(currentitem.question, {id: "content"})
          var item = document.getElementById('content_bottom');
        	
          item.appendChild(div);
      }

      function makediv(value, style){
      	
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


      function inputitem(currentitem){
      	var div = makediv(currentitem.question, {id: "content"})
    	var item = document.getElementById('content_bottom');
    	item.appendChild(div);
      }

      function makediv(value, style){
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

      
 

  	
  	//////////////////////////////
  	//
  	//
  	//
  	//
  	//
  	///////////////////////////////
  	//
  	//
  	//
  	//
  	///////////////////////////////
  	
class diagram{
	
    constructor(){

       this.countnumber = 1;  // 아래의 this.totalarr의 항목중 하나가 보여질텐데, 보여지는 항목이 몇번인지 표시하는 숫자임
       this.button = {}  
       this.countprocess = "";
       
       this.realtag = "";
       this.realdata = {}; // 나중에 공장이 딸기인지 바나나 공장인지 눌러진 데이터 값을 보존하는 변수
       
       this.totalarr = [];
       this.totalanswer = {}
       this.step1list = {}
       this.step2now = ""
       this.step1now = ""
       this.step0now = ""
   	   this.currentitems = []
       
       this.answerlist = {};
       // 재고자산은 다른 템플릿을 사용하는게 나을 것 같아서, 필요한 정보는 localStorage에 저장
       this.content_center = document.getElementById('content_center');
       this.content_bottom = document.getElementById('content_bottom');
       this.content_wrap = document.getElementById('content_wrap');
       this.step1 = document.getElementById('step1');
       this.step0 = document.getElementById('step0');
       this.inventoryfinaltag = "";
       
  
/*    	
	// 제출하기 버튼 추가하기
        var button = document.createElement("input");
        button.setAttribute('type', "button");
        button.style.display = "none";
        button.value = "제출하기"
        var finaldiv = document.getElementById('final');

        finaldiv.appendChild(button);
        this.additem_submit(button)
  */
  
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

    
	listmake = () => {
		
		// 스프링 시큐리티 관련
		var header = $("meta[name='_csrf_header']").attr('content');
		var token = $("meta[name='_csrf']").attr('content');
		
		// ajax 관련
   		$.ajax({
   			type : "POST",
   			url : "basestructure_list",
   			data : {},
   			beforeSend: function(xhr){
   			  if(token && header) {
   		        xhr.setRequestHeader(header, token);
   			  } 
   		    },
   		    success : (res) => {
   		    	
   		    	// 가능한 목록을 여기서 받고, listmanage에다가 끼울 것임 

   		    	
   		    	this.totalarr = res;
                this.listmake_detail(res, this.step0);
                
 
   			},
            error: function (jqXHR, textStatus, errorThrown)
            {
                   console.log(errorThrown + " " + textStatus);
            }
   		})		
	}	

	
	
	listmake_detail(res, listmanage){
	    	
            for(var i in res){
            	if(res[i]){
            		var text = res[i].mainprocess + ", " + res[i].val;
                	var div1 = this.makediv(text, {"class": "detailcontent"});
                	listmanage.appendChild(div1);
                	
                	// 각 div1들에게 클릭 이벤트 등록하기
                	this.additem2(div1, res[i]);
            	}
            }
	}
	
	
	

	additem2 = (item, data) => {

		item.addEventListener('click', (me) => {
			
			if(this.realtag){
				this.realtag.style.background = "gray"
			}

			
 	 			
 			
            var grade = "";
            this.realtag = me.target;
            this.realtag.style.background = "blue"
            this.realdata = data;
            this.poolmake(data);
            this.button.display ="visible";
			
        })
    }

	
	makingnodetag(realhash){
		var div = document.getElementById("inventoryprocess");
		div.style.display = "block"
		
		// 기존의 자식이 있으면 날려버리기
		
		for(var i = div.childNodes.length - 1; i >=0; i--){
			div.removeChild(div.childNodes[i]);
		}
		
		var num = 0
		for(var i in realhash){
			
			for(var j in realhash[i]){
				var val = realhash[i][j].from + ", " + realhash[i][j].to
				var div_tem = this.makediv(val, {class: "detailcontent", style: "height: 100%"});
				div.appendChild(div_tem);
				this.additem_node(div_tem, realhash[i][j])
				
				if(num == 0){
					div_tem.style.background = "blue"
					this.inventoryfinaltag = div_tem;
					this.inventoryfinalprocess = realhash[i][j];
				}
				num++
			}
		}
		
	}
	


	
	
	additem_node(item, data){
    	item.addEventListener('click', (me) => {
    		
    		if(this.inventoryfinaltag){
        		this.inventoryfinaltag.style.background = "gray"
    		}
    		this.inventoryfinaltag = me.target
    		this.inventoryfinaltag.style.background = "blue"
            this.inventoryfinalprocess = data;
    		
    		// 정답이 있으면 갈아끼울 것
    		this.itemmake(data['from'], data['to'])
    		
    		
    		
    	}) 	
	}
	
	
    itemmake(pro1, pro2){

    	// 질문지를 만드는 함수임 // 중요함수임
    	var item = document.getElementById('item');
    	for(var ia = item.childNodes.length - 1; ia >= 0; ia--){
    		item.removeChild(item.childNodes[ia]);
    	}
    	
    	var inventory = "";
    	this.currentitems = [];
    	
    	for(var ia in this.totalarr){
    		   
            var currentitem = this.totalarr[ia]
            var div = this.makediv(currentitem.question, {id: "content"})
            item.appendChild(div);
            this.currentitems.push(currentitem);
             
             
            if(currentitem.inputs){
            		
                var text = this["make" + currentitem.inputs](currentitem.realname)
                var field = document.createElement("div");
            	field.setAttribute("id", currentitem.realname);
            	field.appendChild(text)
                item.appendChild(field);
 
            	if(currentitem.answer.length > 0){

            		if(pro1){
            			// 재고생산 공정인 경우
            			// 공정의 답변이 {문제1: {..., answer: [{공정1, 공정2, }]}} 이런식으로 들어옴
            			// 여기는 현재의 공정에 해당하는 경우만 정답에 추가하는 것임
            			
            			var num = 0
                		for(var ans in currentitem.answer){
                			  
                			var com1 = currentitem.answer[ans].subprocess2;
                			var com2 = currentitem.answer[ans].subprocess3;
                			if(pro1 == com1 && pro2 == com2){	
                				if(num == 0){
                					var input = text.querySelector("input[type=text]")
                					input.value = currentitem.answer[ans].val
                					
                				}else{
                					var div = this["make" + currentitem.inputs + "_add"](currentitem.answer[ans].val)
                					field.appendChild(div);
                				}
                			 }
                				num++
                		}	
            				
            		}else{
            				// 재고공정 이외의 경우
            				// 이외의 답변은 {문제1: {..., answer: [{답변1, 답변2, }]}} 이런식으로 들어옴
            				// 여기는 모든 답변을 정답으로 추가하는 것임
                			var num = 0
                			for(var ans in currentitem.answer){
                				if(num == 0){
                					var input = text.querySelector("input[type=text]")
                					
                					input.value = currentitem.answer[ans].val
                					
                				}else{
                					var div = this["make" + currentitem.inputs + "_add"](currentitem.answer[ans].val)
                					field.appendChild(div);
                				}
                				num++
                			}
            			}
            		}
            	}
            	
            	inventory = currentitem.mainprocess;
    	}

		var inventorytag = document.getElementById("inventory");

    	if(inventory == "재고자산"){
    		inventorytag.style.display = "block";
    	}else{
    		inventorytag.style.display = "none";
    	}
    	
	// 제출하기 버튼 추가하기
        var button = document.createElement("input");
        button.setAttribute('type', "button");
        button.value = "제출하기"
        var finaldiv = document.getElementById('final');

        finaldiv.appendChild(button);
        this.additem_submit(button)
        this.button = button;       
    }
	
    additem_submit = (item) => {

    	item.addEventListener('click', (me) => {
            
         	/////////////////////////////////////////
         	/////////////////////////////////////
         	// 재고자산 생산공정 입력인 경우
         	// 
         	///////////////////////////////////////
         	/////////////////////////////////////////
         	
         	
         	var condition = "1"
         	if(this.totalarr instanceof(Array) == true){
         		condition = this.totalarr[0].subprocess;
         	}
         	
         	
        	if(condition == "1" && this.totalarr instanceof(Array) == true && this.realdata.mainprocess == "재고자산"){
        		// 이 경우에는 공정을 서버에 넘겨서 넣어줘야하는 상황임. 즉 mydiagram의 데이터를 받아야함

            	var realhash = this.make_diagram_hash()

            	var realbackhash = {}
            	for(var ia in realhash){
            		realbackhash[ia] = JSON.stringify(realhash[ia])
            	}
            	
            	 
            	 this.ajaxmethod("baseinventoryconfirm/" + this.realdata.subprocess1, realbackhash, (res) => {
            		 this.totalarr = res;
            		 this.makingnodetag(realhash);
            		 this.itemmake();
            	 })
            	 
            	 return
            	 
        	}
         	

        	// 재고자산 생산공정이 아닌경우
         	// 2단계용 질문으로 구성됨
        	var step = "2";
        	
        	var realdata = {}
        	for(var ia in this.totalarr){
        		
        		var name = this.totalarr[ia].realname
            	var tags = document.getElementById(name);
 
        	    var data = "";
        	    var num = 0;
        	    var list = tags.querySelectorAll("select")

        	    for(var i = 0 ; i < list.length; i++){
        	    	data = list[i].value;
            	}
        	    
        	    // 220112 이거 수정해야 함
        	    var data_json = JSON.stringify(data)
        	    
        	    
                realdata[name] = data;
                
        	} 

                //console.log(this.inventoryfinalprocess)
             	/////////////////////////////////////////
             	/////////////////////////////////////
             	// 재고자산 생산공정 최종 답변인 경우
             	// 
             	///////////////////////////////////////
             	/////////////////////////////////////////
            	 var pro1 = this.realdata.val
             	 //var pro2 = this.inventoryfinalprocess.from
            	// var pro3 = this.inventoryfinalprocess.to
            	 
                if(this.totalarr instanceof(Array) == true && this.realdata.mainprocess == "재고"){
                	
                	this.ajaxmethod("basefinalanswer/" + pro1 + "/" + pro2 + "/" + pro3, realdata, () => alert("입력이 되었습니다."))
                	return
                	
                }else{
                    // 현상태 서버에 입력하기
                    
            	    this.ajaxmethod("baseanswer", realdata, () => alert("입력이 되었으니, 현재상태창에 가서 다음단계를 진행해주세요"))
                }

           
            
     
        	
        	
        })
    }
	
	poolmake = (stephash) => {
		
		// step0, 1, 2 단계별로 선택된 값이 들어옴
		// 이것을 받아서, 현재 step의 하위 step에 대한 정보를 조회해서 넘겨줌
		// 하위 step이 하나도 없다면 새로 하위 step 작성이 필요한 것임
		
		// 스프링 시큐리티 관련
		var header = $("meta[name='_csrf_header']").attr('content');
		var token = $("meta[name='_csrf']").attr('content');
		
		// ajax 관련
   		$.ajax({

   			type : "POST",
   			data : stephash,
   			url : "basepoolmake",
   			beforeSend: function(xhr){

   			  if(token && header) {
   				  
   	    	      xhr.setRequestHeader(header, token);
   			  } 
   		    },
   		    success : (res) => {
   		    	
   		    	
                this.totalarr = res;
                this.countnumber = 1;
                
                this.itemmake();
 
   			},
            error: function (jqXHR, textStatus, errorThrown)
            {
                   console.log(errorThrown + " " + textStatus);
            }
   		})		
	}		
	
	
	
	ajaxmethod = (link, data, act) => {
		
		
		// 스프링 시큐리티 관련
		var header = $("meta[name='_csrf_header']").attr('content');
		var token = $("meta[name='_csrf']").attr('content');
		
   		$.ajax({
   			type : "POST",
   			url : link,
   			data : data,
   			beforeSend: function(xhr){
   			  if(token && header) {
   				  //console.log(header);
   				  //console.log(token);
   		        xhr.setRequestHeader(header, token);
   			  } 
   		    },
   		    success : (res) => {
   				
   		    	// 211015
   		    	// res는 있는데, 그림이 안 그려졌음
   		    	//console.log(res);
   		    	if(act){
   	   				act(res)
   		    	}
 
   			},
            error: function (jqXHR, textStatus, errorThrown)
            {
                   console.log(errorThrown + " " + textStatus);
            }
   		})		
	}
 
	// make + select 등 함수들
    makeselect(val){
        
        // option1 ~ option5 이것을 for문 5까지로 수정할 것
  	   var field = document.createElement("select");
  	   field.setAttribute("name", val);
       	   var option = document.createElement("option");
         	option.innerText = "예";
             field.appendChild(option);
  	       	   var option = document.createElement("option");
	            	option.innerText = "아니요";
	                field.appendChild(option);
  	   
  	  
  	
  	  
 		return field;    	
     }

	
    makeselectplus(val, data){
        
        var div1 = document.createElement("div");
        
        // 먼저는 select를 만들기
    	var field = this.makeselect(data, val);
    	      
    	   // 뒤에 추가버튼 만들기
        var button = document.createElement("input");
        button.setAttribute('type', "button");
        button.value = "추가"
        
        div1.appendChild(field);
        div1.appendChild(button);
        
        // 버튼에 다른 select 추가기능 부여
        
        // 이건 추가로 구현해야함
        //this.additem_plus(button, data, val, div1, button);
        
        return div1;
   	   
     }
	
    maketextplus(val){
        
        var div1 = document.createElement("div");
        
        // 먼저는 text를 만들기
 		var field = document.createElement("input");
		field.setAttribute("type", "text");
    	      
    	   // 뒤에 추가버튼 만들기
        var button = document.createElement("input");
        button.setAttribute('type', "button");
        button.value = "추가"
        
        div1.appendChild(field);
        div1.appendChild(button);
        
        // 버튼에 다른 select 추가기능 부여
        this.additem_textplus(button, val, div1);
        return div1;
        
     }	

    maketextplus_add(val){
    	// 먼저 select의 개수가 최대개수를 초과했는지 확인하고 최대개수를 초과하지 않았으면 추가할 것
 
  	    	// 단어 추가
  	    	var divs = document.createElement("div");
  	    	divs.innerText = ", ";
  	    	
            // 먼저는 text를 만들기
		    var field = document.createElement("input");
	        field.setAttribute("type", "text");
  	    	divs.appendChild(field);
  	    	field.value = val
        	
         	// 삭제 버튼 추가
            var button_remove = document.createElement("input");
            button_remove.setAttribute('type', "button");
            button_remove.value = "X"
            divs.appendChild(button_remove);
            
            
            // 삭제 버튼에 삭제 기능 추가하기
            this.additem_remove(button_remove, divs);
            
            return divs
    }
    
    additem_textplus(item, val, div1){

    	item.addEventListener('click',(me)=>{
        	
        	// 먼저 select의 개수가 최대개수를 초과했는지 확인하고 최대개수를 초과하지 않았으면 추가할 것
        	
            var tags = document.getElementById(val);
        	
            
      	    if(tags.childNodes.length < 5){

      	    	// 단어 추가
      	    	var divs = document.createElement("div");
      	    	divs.innerText = ", ";
      	    	
                // 먼저는 text를 만들기
 		        var field = document.createElement("input");
		        field.setAttribute("type", "text");
      	    	divs.appendChild(field);
            	
             	// 삭제 버튼 추가
                var button_remove = document.createElement("input");
                button_remove.setAttribute('type', "button");
                button_remove.value = "X"
                divs.appendChild(button_remove);
                tags.appendChild(divs);
                
                // 삭제 버튼에 삭제 기능 추가하기
                this.additem_remove(button_remove, divs);
       	    }else{
      	    	alert("최대 개수가 초과되었습니다.")
      	    }
        
        })
    }

    additem_remove(item, parentitem){
    	
    	item.addEventListener('click',(me) => {
    		parentitem.parentNode.removeChild(parentitem); 
    	})
    
     }
   }  	
 
  	
   window.onload = function(){
	   var diagram1 = new diagram();
	   diagram1.listmake();
   } 	
  	

  </script>


</html>