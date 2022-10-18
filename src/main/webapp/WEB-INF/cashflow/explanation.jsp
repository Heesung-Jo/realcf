<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>   
            <div id="content">
            </div>
            
            
            <canvas id ="rightcanvas" width ="1200" height ="100">  </canvas>
            <canvas id='realcanvas' width='800' height='500'></canvas>




<style>



h3 {
   margin: 10px 0 5px 20px;
}


p {
   margin: 10px 0 40px 40px;
}


#content {
  cursor: pointer;
  position: absolute;
  top: 50;
  left: 875;
  width: 375;
  height: 500;
  border: thin solid #aaaaaa;
  -webkit-box-shadow: rgba(0, 0, 0, 0.5) 2px 2px 4px;
  -moz-box-shadow: rgba(0, 0, 0, 0.5) 2px 2px 4px;
   box-shadow: rgba(0, 0, 0, 0.5) 2px 2px 4px;
  -webkit-border-radius: 8px;
  -moz-border-radius: 8px;
  border-radius: 8px;
         }


#rightcanvas {

  position: absolute;
  top: 575;
  left: 50;
  background: #eeeeef;
  border: thin solid #aaaaaa;
  cursor: pointer;
  -webkit-box-shadow: rgba(0, 0, 0, 0.5) 2px 2px 4px;
  -moz-box-shadow: rgba(0, 0, 0, 0.5) 2px 2px 4px;
   box-shadow: rgba(0, 0, 0, 0.5) 2px 2px 4px;
  -webkit-border-radius: 8px;
  -moz-border-radius: 8px;
  border-radius: 8px;
}


 #realcanvas {
         position: absolute;
         top: 50;
         left: 50;
         border: thin solid #aaaaaa;
         -webkit-box-shadow: rgba(0, 0, 0, 0.5) 2px 2px 4px;
         -moz-box-shadow: rgba(0, 0, 0, 0.5) 2px 2px 4px;
         box-shadow: rgba(0, 0, 0, 0.5) 2px 2px 4px;
        -webkit-border-radius: 8px;
        -moz-border-radius: 8px;
        border-radius: 8px;
 }


</style>

<script src = "/JS/explanation.js" ></script>

<script type="text/javascript">

// 210816 처분손익 등이
// 차변 리스부채 xxx || 사용권자산 xxx
//               || 유형자산처분이익 xxx
// 이런것은 유형자산처분이익이 리스부채로 분류되는데, 나중에 이런것을 틀어줄 것. 이미 incometype인가 손익류하는 것이 있으므로
// 여기에 추가해버릴것

// 변수선언
var offcanvas1=document.createElement('canvas')
offcanvas1.height = 250
offcanvas1.width = 1000
var offctx1=offcanvas1.getContext('2d')

// 이미지 배열
var imagearr = []
imagearr.push('/UI/image1.jpeg')
imagearr.push('/UI/image2.jpeg')
imagearr.push('/UI/image3.jpeg')
imagearr.push('/UI/image4.jpeg')
imagearr.push('/UI/image5.jpeg')

var realtotalarr = [];
var realsubarr1 = []; 
var realsubarr2 = []; 
var realsubarr3 = []; 
var realsubarr4 = []; 
var realsubarr5 = []; 

var realexplanation = {}  // h1, p로 구성됨
// subarr1 => 파일입력
var subarr1 = []
subarr1.push('/UI/파일입력1.png')
subarr1.push('/UI/파일입력2.png')

realexplanation[0] = {}
realexplanation[0]["파일선택1"] = "클릭하기" 
realexplanation[0]["파일선택2"] = "총계정원장이 들어있는 파일선택하기" 

// subarr2 => 옵션선택
var subarr2 = []


subarr2.push('/UI/옵션선택1.png')
subarr2.push('/UI/옵션선택2.png')
subarr2.push('/UI/옵션선택3.png')
subarr2.push('/UI/옵션선택4.png')

realexplanation[1] = {}
realexplanation[1]["시트선택"] = "총계정원장이 들어있는 시트선택하기" 
realexplanation[1]["시작행선택"] = "총계정원장의 값들이 시작되는 행선택하기" 
realexplanation[1]["제목선택"] = "전표번호, 계정과목, 합계열(차변, 대변 합계) 선택하기" 
realexplanation[1]["실행하기"] = "실행버튼 클릭하기" 

// subarr3 => 계정과목선택
var subarr3 = []
subarr3.push('/UI/계정과목선택1.png')
subarr3.push('/UI/계정과목선택2.png')
subarr3.push('/UI/계정과목선택3.png')

realexplanation[2] = {}
realexplanation[2]["대분류선택"] = "계정과목들의 대분류(BS, IS 등) 선택하기" 
realexplanation[2]["세부분류선택"] = "계정과목들의 세부분류 선택하기" 
realexplanation[2]["확정하기"] = "확정버튼 클릭하기" 


// subarr4 => 메인계정선택
var subarr4 = []
subarr4.push('/UI/메인계정선택1.png')
subarr4.push('/UI/메인계정선택2.png')

realexplanation[3] = {}
realexplanation[3]["메인계정선택"] = "감가상각누계액 같이, 주계정의 차감형계정의 주계정명(ex: 유형자산) 선택하기" 
realexplanation[3]["확정하기"] = "확정버튼 클릭하기" 


// subarr5 => 결과보기
var subarr5 = []
subarr5.push('/UI/결과보기1.png')
subarr5.push('/UI/결과보기2.png')



realexplanation[4] = {}
realexplanation[4]["계정선택"] = "결과를 보고싶은 계정선택하기" 
realexplanation[4]["전표보기"] = "보고싶은 전표번호 선택하기" 
                                  


repeatarr(subarr1, realsubarr1, 0, "0");
repeatarr(subarr2, realsubarr2, 0, "1");
repeatarr(subarr3, realsubarr3, 0, "2");
repeatarr(subarr4, realsubarr4, 0, "3");
repeatarr(subarr5, realsubarr5, 0, "4");



window.onload = () => {
	var drawing = new draw_class();

}






</script>
