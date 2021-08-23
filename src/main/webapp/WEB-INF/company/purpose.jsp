<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>   

<div id="content">
   <h3>상장회사 분석</h3>
   <p>
     주식을 분석하기 위해서는 재무제표 분석은 기본적으로 수행되어야 합니다.
   </p>

   <p>
     저는 주식을 분석하기 위하여, 전자공시시스템의 감사보고서를 활용하여, 크롤링 등을 통해 수치분석을 수행하곤 하였는데요, 여기에 이렇게 분석한 자료를 넣어두었습니다. 
   </p>

   <p>
     이 자료를 통해 상장회사 중에서 유형자산이 가장 많은 회사는 어디인지 또는 자산대비 매출비중이 가장 높은 회사는 어디인지 등을 찾을 수 있습니다. 
   </p>

   <p>
     다만, 감사보고서 등의 계정과목 등이 미묘하게 계정분류체계가 차이가 있기때문에, 계정체계는 임의로 분류되어 있습니다.
   </p>

</div>
            

<style>



h3 {
   margin: 10px 0 5px 20px;
}


p {
   margin: 10px 40px 10px 40px;
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


