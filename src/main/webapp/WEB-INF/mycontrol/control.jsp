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

  
       <input type ="submit" value = "CF" onClick = "ajaxmethod('cfcontrol')"/>
       <input type ="submit" value = "internal" onClick = "ajaxmethod('internalcontrol')"/>
       <input type ="submit" value = "com" onClick = "ajaxmethod('companycontrol')"/>
  
</body>
</html>

    <script src = "http://code.jquery.com/jquery-3.4.1.js"></script>


<script>



window.onload = () => {
	// 이 경우에는 직접 프론트에서 실행을 시켜야함
	
}






function ajaxmethod(link){
	
	 
	// 스프링 시큐리티 관련
	
	var header = $("meta[name='_csrf_header']").attr('content');
	var token = $("meta[name='_csrf']").attr('content');
	
		$.ajax({
			type : "POST",
			url : link,
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