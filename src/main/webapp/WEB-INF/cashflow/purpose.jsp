<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>   

<div id="content">

   <h3>회계원장가공</h3>
   
   <p>
     회계원장을 가공하여, 현금정산표를 만들거나, 계정과목간의 관계를 분석할 때 복잡한 전표들로 인해 분석하는 작업이 수월하지 않은 경우가 있습니다.
   </p>

   <p>
     저는 해당업무를 수월하게 수행하도록 툴을 만들어 업무를 수행하고 있는데, 이 툴을 활용하면 회계원장을 좀 더 쉽게 분석이 가능해진다고 생각합니다. 
   </p>

   <p>
     해당 툴을 활용하는 방법은 아래텝인 활용방법을 참고하시면 됩니다. 
   </p>


</div>
            

<style>



h3 {
   margin: 10px 0 5px 20px;
}


p {
   margin: 10px 0 10px 40px;
}


#content {
  cursor: pointer;
  position: absolute;
  top: 50;
  left: 100;
  width: 1000;
  height: 500;
  border: thin solid #aaaaaa;
  -webkit-box-shadow: rgba(0, 0, 0, 0.5) 2px 2px 4px;
  -moz-box-shadow: rgba(0, 0, 0, 0.5) 2px 2px 4px;
   box-shadow: rgba(0, 0, 0, 0.5) 2px 2px 4px;
  -webkit-border-radius: 8px;
  -moz-border-radius: 8px;
  border-radius: 8px;

  
         }


</style>


