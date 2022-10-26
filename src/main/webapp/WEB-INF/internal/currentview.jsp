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
    width: 50%;
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

</style>

<body>

     <c:set var="name" value="완성" />
            
         <table id = "realtable">
            <c:if test="${currentlevel < '2'}"> 

                  <tr>
                    <th>업무종류</th>
                    <th>진행단계</th>
                  </tr>
          
              <c:forEach var = "item" items = "${currentarr}" >
                 <tr>
                    <td>${item.key}</td>
                    <td>${item.value}</td>
                 </tr>
                 
                 <c:if test="${item.value eq '미완성'}">
                     <c:set var="name" value="미완성" />
                 </c:if>
              </c:forEach>
            </c:if>
         </table>
              
       <c:if test="${currentlevel < '2'}"> 
             <c:if test="${name eq '완성'}">
                    <input type ="submit" class = "btn btn-primary" value = "확정하기" onClick = "ajaxmethod()"/>
             </c:if>
       </c:if>
 
</body>
</html>

    <script src = "http://code.jquery.com/jquery-3.4.1.js"></script>


<script>


var currentlevel = ${currentlevel};

window.onload = () => {
	// 이 경우에는 직접 프론트에서 실행을 시켜야함
	
	console.log("level: " + currentlevel)
	if(currentlevel > 1 && currentlevel < 4){
		var tablecon = new table(currentlevel);
	}
	
}



class table{
	
	constructor(currentlevel){
        this.level = currentlevel
		this.table = document.getElementById('realtable');
		this.tablearr = {};
		this.tablesize = {width: 2, height: 1};
		// 로컬스토리지 데이터 조회
		
		
		this.totaldata = JSON.parse(window.localStorage.getItem('totaldata'));	
		
		
		this.incompletelist = {};
		this.processcount = 7; 
		this.button_possible = 1;
		
		this.findincomplete(currentlevel).then(() => {
			
			this.tablesize['height'] = Object.keys(this.incompletelist).length + 1;
			this.maketable();
			
			if(this.button_possible == 1){
				
				this.makebutton();
			}
			
		});
		
	}

	
	findincomplete(opt){
		
		return new Promise((resolve) => {
			
			var count = 0;
			
			var size = Object.keys(this.totaldata).length;
			for(var data in this.totaldata){
                
				var fail = 0;
				var text = data;
			    text = text.replace("^{", "");
				text = text.replace("@{", "");
				text = text.replace("}^", "");
				text = text.replace("}@", "");

				for(var da in this.totaldata[data].processoption){

					if(this.test(data, da, opt) == true){

							this.incompletelist[text] = "미완성";
							this.button_possible = 0;
							fail = 1;
							break;
					}
				}
				
				if(fail == 0){
					this.incompletelist[text] = "완성";
				}
				
			}
			
			
			resolve();
			
		})
	}
	
	test(data, da, opt){
		
		if(opt == 2){
			
			if(this.totaldata[data].processoption[da].result == null){
				return true;
			}
			
		}else if(opt == 3){

			for(var risk = 1; risk <= 5; risk++){
				if(this.totaldata[data].processoption[da]['risk' + risk] == this.totaldata[data].processoption[da].result){
					return true;
				}
			}
		}
		
        return false;		
	}
	
	maketable(){
		
	    // processlist 반영하기 
	    var list = Object.keys(this.incompletelist)
	    for(var i = 0; i < this.tablesize.height;i++){

	    	if(i == 0){
			    var subdiv = this.maketrtd(["업무진행", "진행단계"], "th");
		 		this.table.appendChild(subdiv)
		    	
		    }else{
			    var subdiv = this.maketrtd([list[i - 1], this.incompletelist[list[i - 1]]], "td");
		 		this.table.appendChild(subdiv)
		    	
		    }
		}
	    
	}


	maketrtd(arr, opt){
	    
	 	var div = document.createElement("tr")

	 	div.style = "height : 20px";

	 	for(var i = 0; i < this.tablesize.width;i++){
		   var subdiv = document.createElement(opt);

		   subdiv.innerText = arr[i];
		   if(i == 0){
	    	   subdiv.setAttribute("width","30%");
		   }else if(i == 1){
			   subdiv.setAttribute("width","60%");
		   }else if(i == 2){
			   subdiv.setAttribute("width","10%");
		   }
		   div.appendChild(subdiv);
	 	}
		return div
	} 
	
	
	makebutton(){
	   	 
		 // 뒤에 추가버튼 만들기
	     var button = document.createElement("input");
	     button.setAttribute('type', "button");
	     button.setAttribute('class', "btn btn-primary");
	     
	     button.value = "확정하기"
	     
    	 document.getElementById("content_wrap").appendChild(button);
	     
	     button.addEventListener('click', () => {
	          ajaxmethod();
	     })
	     
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