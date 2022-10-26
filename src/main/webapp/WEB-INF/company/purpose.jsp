<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>   

<div id="content">
  </br>
  <h5>만든목적</h5>
  <p>Dart 사이트에서 회사마다의 재무정보를 찾아볼 수 있으나, 여러 회사를 동시에 비교하기가 용이하지가 않습니다.</p>
  <p>또한 Dart 사이트의 계정과목은 회사마다 다양하기 때문에, 회계에 대해서 잘 알지 못할 경우, 재무제표를 이해하기도 쉽지 않습니다.</p>
  
  </br>
  <h5>계정분류</h5>
  <p>딥러닝을 활용하여 Dart의 회사 재무제표 계정과목을 아래와 같이 구분하여 새롭게 분류하였습니다.</p>
  <p>-	손익: 매출액, 매출원가, 판관비, 영업외손익, 금융손익, 법인세비용, 당기순이익</p>
  <p>-	자산: 현금성자산, 금융자산, 재고자산, 유형자산, 무형자산, 투자자산, 기타자산</p>
  <p>-	부채: 금융부채, 기타부채, 충당부채</p>
  <p> 실제 회사의 계정과는 차이가 있을 수 있으나, 누구나 쉽게, 여러 개의 회사를 간단한 계정과목별 카테고리로 비교하여 볼 수 있습니다.</p>
  
  </br>
  <h5>사용방법</h5>
  <p> 사용방법은 좌측의 활용방법 카테고리를 참고하시기 바랍니다.</p>
  <p> * 다만, 모든 상장회사에 대한 완전성을 보장하지는 않습니다.</p>  
  
</div>
            

<style>



h3, h5 {
   margin: 10px 0 5px 20px;
}


p {
   margin: 10px 40px 10px 40px;
}


#content {
  background-size: 100% 100%;
  width: 100%;
  height: 100%;
         }


</style>


