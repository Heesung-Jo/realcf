<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
 <html>
    <head>
       <meta name="_csrf_header" content="${_csrf.headerName}">
       <meta name="_csrf" content="${_csrf.token}">
    <body>



     <div>
        <span id = "listmanage" class = "listmanage"></span>
     </div>
     
      
      <div style="width: 1500px; display: flex;">
         <canvas id = 'canvas' width ="700px" height ="600px"  style="width: 700px; height: 600px;"></canvas>
         <div style = "overflow: hidden">
           <div id= "myexplain" style="width: 700px; height: 300px;overflow: hidden;"></div>
           <div id= "mycontrol" style="width: 700px; height: 300px;overflow: hidden;"></div>
         </div>
      </div>
 

 
      <input type = "button" class = "btn btn-primary" id = "process_answer" value = "확정하기"/>
 
    </body>
 
<style>
 
          #canvas {
            background: #ffffff;
			border: 1px solid black; 
            cursor: default;
            margin-left: 10px;
            margin-top: 10px;
         }

          #mycontrol, #myexplain  {
            background: #ffffff;
            cursor: default;
            margin-left: 10px;
            margin-top: 10px;
         } 

         #process_answer {

            margin-left: 10px;
            margin-top: 10px;

         }        

 
  table {
    width: 100%;
    border: 0px solid black;
    border-collapse: collapse;
  }
  th, td {
    border: 1px solid black;
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
  height: 50%; 
  width: 200px;
  background: none;
  cursor: pointer;
}


 
</style>
 
    <script src="/JS/Canvasmaking.js"></script>
     <script src="/JS/diagram.js"></script>
    <script src = "http://code.jquery.com/jquery-3.4.1.js"></script>

  <script>

  

  
  var request;
  
  function createRequest(){
		
		try{
			request = new XMLHttpRequest();
			
			
		} catch (exception){
			try {
				request = new ActiveXObjet('Msxml2.XMLHTTP');
			} catch (innerException){
				request = new ActiveXObject('Microsoft.XMLHTTP');
			}
			
		}
	     return request;	
	   }
  
  
 


  window.onload = function(){
     
	 // diagram 관련 세팅 
	 var diagram1 = new diagram();
	 
	 var func = (x) =>{
		 
         var input = {};
         input[x] = x;
         diagram1.currentkey = x;
         diagram1.maintextarr[x] = {}; 
         diagram1.inputtable(input, diagram1.tablearr, "processname", "processexplain", diagram1.tablesize.height, "tablearrcount"); // 프로세스 관련 인풋
         diagram1.inputtable(input, diagram1.table2arr, "controlname", "controlexplain", diagram1.table2size.height,  "table2arrcount"); // 컨트롤 관련 인풋
         // @{}@ 를 제대로 처리하기 위해서
         diagram1.changewordtable();
         diagram1.paintopt = 0;

	 }
	 
	 diagram1.func = func;
	 
	 
     //diagram1.nodedatasetting(0);
     diagram1.listmake2();
    // diagram1.poolmake("인사계획서 작성");
    
     
  /*   
     console.log(diagram1.totalarr)
     // 데이타 세팅
     // 1. 서버에서 관련 데이터 풀 받아오기
     diagram1.poolmake("전표관리");
     
     // 2. 현재는 표준 플로우차트 열이 없어서 임의로 세팅함
     
     diagram1.possetting2(datasetting["전표 접수"]);
     diagram1.possetting3(datasetting["전표 접수"]);
     diagram1.nodedatasetting_graph2(datasetting["전표 접수"]);
     //diagram1.nodedatasetting_graph(datasetting, datasetting["전표 접수"]);
     
     diagram1.picture("graph");
*/
     //diagram1.picture2();

    // ajax 관련 세팅 
    var request = createRequest(request);
 	request.onreadystatechange = function (event){
		if(request.readyState ==4){
			if (request.status == 200){
    			//console.log(request.response)
    			
			}
		}
	}


  }




 
  </script>