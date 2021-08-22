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
imagearr.push('/UI/image6.jpeg')
imagearr.push('/UI/image7.jpeg')
imagearr.push('/UI/image8.jpeg')
imagearr.push('/UI/image9.jpeg')
imagearr.push('/UI/image10.jpeg')

var realtotalarr = [];
var realsubarr1 = []; 
var realsubarr2 = []; 
var realsubarr3 = []; 
var realsubarr4 = []; 
var realsubarr5 = []; 

var realexplanation = {}  // h1, p로 구성됨
// subarr1 => 파일입력
var subarr1 = []
subarr1.push('/UI/회사1.png')
subarr1.push('/UI/회사2.png')
subarr1.push('/UI/회사3.png')
subarr1.push('/UI/회사4.png')
subarr1.push('/UI/회사5.png')


realexplanation[0] = {}
realexplanation[0]["회사입력창 선택"] = "클릭하여 회사입력창 선택하기" 
realexplanation[0]["회사입력"] = "조회하고 싶은 회사 입력하기" 
realexplanation[0]["조회버튼 클릭"] = "조회버튼 클릭하기" 
realexplanation[0]["금액기준 조회"] = "선택한 회사의 각 계정별 정보가 조회됨" 
realexplanation[0]["비율기준 조회"] = "선택한 회사의 자산대비 각 계정의 비율정보가 조회됨" 
// subarr2 => 옵션선택

var subarr2 = []
subarr2.push('/UI/회사계정1.png')
subarr2.push('/UI/회사계정2.png')
subarr2.push('/UI/회사계정3.png')
subarr2.push('/UI/회사계정4.png')
subarr2.push('/UI/회사계정5.png')


realexplanation[1] = {}
realexplanation[1]["회사선택창 클릭"] = "클릭하여 회사입력창 선택하기" 
realexplanation[1]["회사선택"] = "조회하고 싶은 회사 입력하기" 
realexplanation[1]["계정선택"] = "조회하고 싶은 계정과목 선택하기" 
realexplanation[1]["조회하기"] = "조회버튼 클릭하기" 
realexplanation[1]["결과보기"] = "조회한 결과보기" 

// subarr3 => 계정과목선택
var subarr3 = []
subarr3.push('/UI/계정1.png')
subarr3.push('/UI/계정2.png')
subarr3.push('/UI/계정3.png')
subarr3.push('/UI/계정4.png')

realexplanation[2] = {}
realexplanation[2]["계정선택창 클릭"] = "클릭하여 계정입력창 선택하기" 
realexplanation[2]["계정과목 선택"] = "조회하고 싶은 계정과목들 선택하기" 
realexplanation[2]["조회하기"] = "조회버튼 클릭하기" 
realexplanation[2]["결과보기"] = "조회하고 싶은 계정과목들의 값의 합이 큰 회사 순으로 5개까지 조회됨" 

// subarr4 => 메인계정선택
var subarr4 = []
subarr4.push('/UI/업종1.png')
subarr4.push('/UI/업종2.png')
subarr4.push('/UI/업종3.png')
subarr4.push('/UI/업종4.png')

realexplanation[3] = {}
realexplanation[3]["업종선택창 클릭"] = "클릭하여 업종입력창 선택하기" 
realexplanation[3]["업종 선택"] = "조회하고 싶은 업종 선택하기" 
realexplanation[3]["조회하기"] = "조회버튼 클릭하기" 
realexplanation[3]["결과보기"] = "조회하고 싶은 업종들 중에서 자산총계 합이 큰 회사 순으로 5개까지 조회됨" 


// subarr5 => 결과보기
var subarr5 = []
subarr5.push('/UI/업종계정1.png')
subarr5.push('/UI/업종계정2.png')
subarr5.push('/UI/업종계정3.png')
subarr5.push('/UI/업종계정4.png')

realexplanation[4] = {}
realexplanation[4]["업종선택창 클릭"] = "클릭하여 업종입력창 선택하기" 
realexplanation[4]["업종 선택"] = "조회하고 싶은 업종 선택하기" 
realexplanation[4]["계정 선택"] = "조회하고 싶은 계정과목 선택하기" 
realexplanation[4]["결과보기"] = "조회하고 싶은 업종들 중에서 선택한 계정과목의 합이 큰 회사 순으로 5개까지 조회됨" 


repeatarr(subarr1, realsubarr1, 0, "0");
repeatarr(subarr2, realsubarr2, 0, "1");
repeatarr(subarr3, realsubarr3, 0, "2");
repeatarr(subarr4, realsubarr4, 0, "3");
repeatarr(subarr5, realsubarr5, 0, "4");


console.log(realtotalarr)
window.onload = () => {
	var drawing = new draw_class();
}
</script>




