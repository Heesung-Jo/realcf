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

      <div style="width: 100%; height: 100%; overflow: hidden">
  
         <canvas id = 'canvas' width ="1000" height ="1000" > 
         </canvas>
         <canvas id="movecanvas" width="200" height="100">
          </canvas>        
        

         <div id= "myexplain" >
             <div> 회사입력 </div>
             <input type = "text" id = "company"/>
             <input type = "button" id = "companyplus" value = "조회하기"/>
         </div>


         <div id = "tag">
         </div>
           
      </div>
      
           <div id = 'subtablediv'>
                <table id ='subtable'></table>
            </div> 
 
    </body>
 
<style>
 
  table {
    width: 100%;
    border: 1px solid #444444;
    border-collapse: collapse;
  }
  th, td {
    border: 1px solid #444444;
  }



#canvas {
    float: left;
    width: 80%;
    height: 100%;
    border: 1px solid blue; 
    
 }

#movecanvas {
   position: absolute;
   left : 5%;
   top : 5%;
   background: rgba(200,100,0,1);
   box-shadow: rgb(0 0 0) 4px 4px 6px;
   z-index: 100;
}


#tag {
 
    width: 80%;
    height: 100%;
    font-size: 13px;
    
 }
 

#myexplain {
    position: absolute;
    z-index: 100;
    float: left;
    left: 79%;
    width: 21%;
    height: 100%;
    border: 1px solid blue; 
    margin : 0 0 0 0.5%;
    background-color: white;
   
 } 

#hide {
    position: absolute;
    z-index: 100;
    left: 0%;
    top: 0%;
    width: 14%;
    height: 100%;
    background-color: white;
 } 
 
 
#main_lnb {
   
   z-index: 100;
} 
 
#myexplain * {
    margin: 5% 0 0 10%
} 

#myexplain input {
    width: 80%
} 
 
#rect {
   border: 2px solid blue;
   box-shadow:5px 5px 5px #aaa;
   word-break: break-all;
   z-index: 10;
   
} 

#rect div {

    margin : 3%
}

#subtablediv {
   z-index: 100;
   width: 14%;
}



#subtable tr:hover th {
   background: yellow;
}

table {
    border-collapse: collapse;
    width: 100%;
    
  }
  
table th {
  border: 1px solid #444444;
  font-weight: bold;
  background: #dcdcd1;
  width: 14%;
  height: 20px;
}

table td {
    border: 1px solid #444444;
    background: white;
    height: 20px;
}


 
</style>
 
 
  <script src = "http://code.jquery.com/jquery-3.4.1.js"></script>

  <script>


class canvasmaking{

	constructor () {
	    this.total = document.getElementById('content_wrap');
     	this.canvas = document.getElementById('canvas');
    	this.lefttag = document.getElementById("main_lnb");  
    	
    	this.movecanvas = document.getElementById('movecanvas');

        
     	this.company = document.getElementById("company");    
     	this.companybutton = document.getElementById("companyplus");    // 내용(text) +  x표시 
    	this.subtablediv = document.getElementById("subtablediv");
        this.selectoption = document.getElementById("selectoption");
        this.subtable = document.getElementById("subtable"); // div 가 나은것 같으면
        this.subtablearr = {};
    	this.keyeventcount = "";
     	
     	this.realtag = document.getElementById('tag');
     	this.rightbutton = document.getElementById('rightbutton');
     	this.leftbutton = document.getElementById('leftbutton');
	    
     	// size 및 distance 등의 정의
     	this.movement = 0.05
     	this.shapecount = 5;
     	this.size = {};
     	this.size.x = this.canvas.width * 0.8/this.shapecount - 40 // 20은 간격임
     	this.size.y = this.canvas.height * 0.8/this.shapecount - 40 // 20은 간격임
     	this.distance = {};
     	this.distance.x = this.canvas.width * 0.8/this.shapecount;  // 20은 간격임
     	this.distance.y = this.canvas.height * 0.8/this.shapecount; // 20은 간격임
     	this.basic = {};
     	this.basic.x = this.canvas.width * this.movement;  // 기본적으로 5% 이동한 것에서 그려야한다는 의미
     	this.basic.y = this.canvas.height * 0.4;  // 기본적으로 50% 이동한 것에서 그려야한다는 의미            	
  	    
     	// tag로도 반영이 필요한 것 같아서 만들었음
     	this.tagsize = {};
     	this.tagsize.x = this.canvas.offsetWidth  * 0.8/this.shapecount - 40 // 20은 간격임
     	this.tagsize.y = this.canvas.offsetHeight  * 0.8/this.shapecount - 40 // 20은 간격임
     	this.tagdistance = {};
     	this.tagdistance.x = this.canvas.offsetWidth * 0.8/this.shapecount;  // 20은 간격임
     	this.tagdistance.y = this.canvas.offsetHeight * 0.8/this.shapecount; // 20은 간격임
     	this.tagbasic = {};
     	this.tagbasic.x = this.canvas.offsetWidth * this.movement;  // 기본적으로 5% 이동한 것에서 그려야한다는 의미
     	this.tagbasic.y = this.canvas.offsetHeight * 0.4;  // 기본적으로 50% 이동한 것에서 그려야한다는 의미 
     	this.tagwidth = Math.round(this.tagsize.x/this.total.offsetWidth * 1000) /10 - 0.1 + "%";
     	this.tagheight = Math.round(this.tagsize.y/this.total.offsetHeight * 1000) /10 + 0.5 + "%";

        // 기초 회사데이터 받아오기
        this.companyarr = [];
        this.companysortedarr = [];
        this.listedcompany = [];
        this.ajaxmethod("stockstart", null, (res) => {
     	   //this.companyarr = res.companystock;
     	   //this.companyarr.sort();
           
     	   this.companysortedarr = res.companystock_opp;  // 일단 이것밖에 필요없느 것 같아서 나머지는 지움
     	   this.companysortedarr.sort();
     	   //this.listedcompany = res.listedcompany;      // 좀더 고민해볼 것   
     	   //this.companyhash = res.companystockhash;
     	   //this.companyhash_opp = res.companystockhash_opp;
     	   console.log(res);
     	  
     	   
        });

        // movecanvas 그리기
     	this.context = this.canvas.getContext('2d');
        this.context.font = '20px impact'
        this.context.lineWidth = 2.0;	
        this.arrow = "";

     	this.movecontext = this.movecanvas.getContext('2d');
        this.movemargin = 10;
        this.parentnode = {}
        this.childnode = {}

        
        this.paintmovecanvas(this.movecontext);
        this.movecanvas.onmousedown = (e) => {
           console.log("눌러졌니")
           
		   var rect = movecanvas.getBoundingClientRect(),
		   x = e.x || e.clientX,
		   y = e.y || e.clientY;

		   e.preventDefault();
		   e.stopPropagation();

		   
		   if (x-rect.left > movecanvas.width/2){
		      this.arrow = "left"
		      this.painttag(0.15);
		   }else{
			  this.arrow = "right"
		      this.painttag(-0.15);
		   }
		   
      	   this.paintmovecanvas(this.movecontext);
      	   
      	   
		 };	        
      
 
    	this.addevent_company(this.company);
    	this.companybutton.addEventListener('click',()=>{
    		this.finddata(this.company.value);
    	})
    	
    	this.posx = 0;
     	
	}
	
	// 순환문 관련
	nodedatafunc(arr, now, pos, before, sign){
	   	
	   	var data = arr[now]
	   	for(var i in data){
	   		if(sign > 0){
	       		this.line(pos, {x: this.posx, y: pos.y + sign});   // 모회사인지 자회사인지에 따라서 방향이 바뀌어야 함
	   		}else{
	       		this.line({x: this.posx, y: pos.y + sign}, pos);   // 모회사인지 자회사인지에 따라서 방향이 바뀌어야 함
	   		}

	   		this.rect(data[i].realname, data[i].percent + "%", {x: this.posx, y: pos.y + sign});
	   		this.nodedatafunc(arr, data[i].name, {x: this.posx, y: pos.y + sign}, {x: this.posx, y: pos.y}, sign)
	   		this.posx += 1;
	   		//console.log(now +":" + pos.y + ":" + sign)
	   	}
	   		
	   	
	}
	   
	   
	// 움직임 canvas 그리기
	paintmovecanvas() {
        this.movecontext.clearRect(0,0, this.movecanvas.width,this.movecanvas.height);

        if (this.arrow == "left") {
            this.movecontext.fillStyle = 'rgba(100,140,230,0.8)';
            this.paintLeftArrow(this.movecontext);
            this.movecontext.fillStyle = 'rgba(255,255,0,0.8)'
            this.paintRightArrow(this.movecontext);
            
            setTimeout(()=> {
            	this.movecontext.fillStyle = 'rgba(100,140,230,0.8)';
            	this.paintRightArrow(this.movecontext);
            	
            }, 500)
            
        }else if(this.arrow == "right"){
        	this.movecontext.fillStyle = 'rgba(100,140,230,0.8)';
        	this.paintRightArrow(this.movecontext);
        	this.movecontext.fillStyle = 'rgba(255,255,0,0.8)'
        	this.paintLeftArrow(this.movecontext);

            setTimeout(()=> {
            	this.movecontext.fillStyle = 'rgba(100,140,230,0.8)';
            	this.paintLeftArrow(this.movecontext);
            	
            }, 500)

        }else{
        	this.movecontext.fillStyle = 'rgba(100,140,230,0.8)'
        	this.paintLeftArrow(this.movecontext);
        	this.movecontext.fillStyle = 'rgba(100,140,230,0.8)';
        	this.paintRightArrow(this.movecontext);
        	
        }
     }
	

	  paintRightArrow(context) {
		  context.save();
		  context.translate(this.movecanvas.width, 0);
		  context.scale(-1,1);
		   this.paintArrow(context);
		   context.restore();
	  }

	  paintLeftArrow(context) {
		   this.paintArrow(context);
	  }
		   
	  paintArrow(context) {
		   context.beginPath();
		   context.moveTo( this.movecanvas.width/2 - this.movemargin/2,
		                   this.movemargin/2);

		   context.lineTo( this.movecanvas.width/2 - this.movemargin/2,
		                   this.movecanvas.height - this.movemargin);

		   context.quadraticCurveTo(this.movecanvas.width/2 - this.movemargin/2,
		                            this.movecanvas.height - this.movemargin/2,
		                            this.movecanvas.width/2 - this.movemargin,
		                            this.movecanvas.height - this.movemargin/2);

		   context.lineTo( this.movemargin,
		                   this.movecanvas.height/2 + this.movemargin/2);

		   context.quadraticCurveTo(this.movemargin - 3,
		                            this.movecanvas.height/2,
		                            this.movemargin, this.movecanvas.height/2 - this.movemargin/2);

		   context.lineTo( this.movecanvas.width/2 - this.movemargin,
		                   this.movemargin/2);

		   context.quadraticCurveTo(this.movecanvas.width/2 - this.movemargin,
		                            this.movemargin/2, this.movecanvas.width/2 - this.movemargin/2,
		                            this.movemargin/2);
		   context.fill();
		   context.stroke();
		}
	

	painttag(val){
	   	  // 기존 화면 삭제 
    	this.context.clearRect(0, 0, this.canvas.width, this.canvas.height);   
	        for(var k = this.realtag.childNodes.length - 1; k >= 0; k--){
		        	this.realtag.removeChild(this.realtag.childNodes[k]); 
		       }	    	  

    	// 이제 그리기
    	this.movement += val;
       	this.basic.x = this.canvas.width * this.movement; 
     	this.tagbasic.x = this.canvas.offsetWidth * this.movement;

       	var now = "";
       	
       	for(var i in this.childnode){
       		now = i;
       		break;
       	}
       	
       	this.posx = 0;
   	    this.rect(now, "", {x: 0, y: 0});
   	    this.nodedatafunc(this.childnode, i, {x: 0, y: 0}, {x: 0, y: 0}, 1)

       	for(var i in this.parentnode){
       		now = i;
       		break;
       	}
   	    this.posx = 0;
   	    this.rect(now, "", {x: 0, y: 0});
   	    this.nodedatafunc(this.parentnode, i, {x: 0, y: 0}, {x: 0, y: 0}, -1)	
   	    
   	    this.posx = 0;
	} 
	  
	
	// 수치 초기화하기
	reset_vary(){
		
     	// size 및 distance 등의 정의
     	this.movement = 0.05
     	this.shapecount = 5;
     	this.size = {};
     	this.size.x = this.canvas.width * 0.8/this.shapecount - 40 // 20은 간격임
     	this.size.y = this.canvas.height * 0.8/this.shapecount - 40 // 20은 간격임
     	this.distance = {};
     	this.distance.x = this.canvas.width * 0.8/this.shapecount;  // 20은 간격임
     	this.distance.y = this.canvas.height * 0.8/this.shapecount; // 20은 간격임
     	this.basic = {};
     	this.basic.x = this.canvas.width * this.movement;  // 기본적으로 5% 이동한 것에서 그려야한다는 의미
     	this.basic.y = this.canvas.height * 0.4;  // 기본적으로 50% 이동한 것에서 그려야한다는 의미            	
  	    
     	// tag로도 반영이 필요한 것 같아서 만들었음
     	this.tagsize = {};
     	this.tagsize.x = this.canvas.offsetWidth  * 0.8/this.shapecount - 40 // 20은 간격임
     	this.tagsize.y = this.canvas.offsetHeight  * 0.8/this.shapecount - 40 // 20은 간격임
     	this.tagdistance = {};
     	this.tagdistance.x = this.canvas.offsetWidth * 0.8/this.shapecount;  // 20은 간격임
     	this.tagdistance.y = this.canvas.offsetHeight * 0.8/this.shapecount; // 20은 간격임
     	this.tagbasic = {};
     	this.tagbasic.x = this.canvas.offsetWidth * this.movement;  // 기본적으로 5% 이동한 것에서 그려야한다는 의미
     	this.tagbasic.y = this.canvas.offsetHeight * 0.4;  // 기본적으로 50% 이동한 것에서 그려야한다는 의미 
     	this.tagwidth = Math.round(this.tagsize.x/this.total.offsetWidth * 1000) /10 - 0.1 + "%";
     	this.tagheight = Math.round(this.tagsize.y/this.total.offsetHeight * 1000) /10 + 0.5 + "%";		
		
	}
	
	
	// 조회하기
	
	finddata(name){
  	  // 기존 화면 삭제 
    	this.context.clearRect(0, 0, this.canvas.width, this.canvas.height);   
	        for(var k = this.realtag.childNodes.length - 1; k >= 0; k--){
		        	this.realtag.removeChild(this.realtag.childNodes[k]); 
		       }	  
	        
        this.reset_vary();
     	var data = {name: name, opt: 0}
        this.ajaxmethod("stocksearch", data, (res) => {
          // 자회사 수행	 
           this.posx = 0;
           
     	   for(var i in res){
     		   var now = i;
     		   break;
     	   }
     	    this.rect(now, "", {x: 0, y: 0});
     	    this.nodedatafunc(res, i, {x: 0, y: 0}, {x: 0, y: 0}, 1);
     	    this.childnode = res;
     	    
        });  
        
       
        var data = {name: name, opt: 1}
        this.ajaxmethod("stocksearch", data, (res) => {
            // 모회사 수행	 
           this.posx = 0;
       	   for(var i in res){
       		   var now = i;
       		   break;
       	   }
       	   
       	    this.rect(now, "", {x: 0, y: 0});
       	    this.nodedatafunc(res, i, {x: 0, y: 0}, {x: 0, y: 0}, -1)
       	    this.parentnode = res;
          }); 
        
        this.posx = 0;
		
	}
	
	
	// 사이즈 변환
	resize(pos, opt){
		var realpos = {}
		
		if(opt){
			realpos.x = Math.round((pos.x * this.tagdistance.x + this.tagbasic.x)/this.total.offsetWidth * 100) + 0.5 + "%";
			realpos.y = Math.round((pos.y * this.tagdistance.y + this.tagbasic.y)/this.total.offsetHeight * 100) + 0.5 + "%";
			//console.log(realpos)
		}else{
			realpos.x = pos.x * this.distance.x + this.basic.x;
			realpos.y = pos.y * this.distance.y + this.basic.y;
		}

		return realpos;
	}
	
	// 사각형 그리기
	rect(name, percent, pos_tem){
		
		
		
		// size 등의 정의
		var size = this.size;
		var pos = this.resize(pos_tem);
		/*	
		// 그리기
        this.context.shadowColor = 'rgba(0, 0, 0, 0.8)';
        this.context.shadowOffsetX = 12;
        this.context.shadowOffsetY = 12;
        this.context.shadowBlur = 15;
		
        this.context.fillStyle = 'rgba(100, 180, 230, 0.5)';
     	this.context.fillRect(pos.x, pos.y, size.x, size.y)

     	
        this.context.shadowOffsetX = 0;
        this.context.shadowOffsetY = 0;
        this.context.shadowBlur = 0;

     	this.context.fillStyle = 'black';
     	
     	// 이것을 활용해서 간격을 맞춰보자
     	console.log(this.context.measureText(name).width);
     	console.log(this.size.x);
      */
      
     	// 글은 테그로 써보자
     	var realpercent = percent ? "지분율: " + percent : null;
     	var div = this.makediv(realpercent, name)
     	var pos = this.resize(pos_tem, 1);
     	
     	var posy = Math.round(pos.y + size.y/2 - 5);
     	div.setAttribute("id","rect")
        div.style = "overflow : hidden; position: absolute; left: " + pos.x + "; top: " + pos.y +"; width: " + this.tagwidth + "; height: " + this.tagheight;

     	this.realtag.appendChild(div);

     	/*
     	if(pos.x.substr(0, 2) > 75 || pos.x.substr(0, 2) < 0.02){
             div.style.display = "none"
     	}else{
            div.style.display = "block"
     	}
     	*/
        
        //this.context.fillText(name, pos.x + 10, pos.y + size.y/2 -5)  
        //this.context.fillText(division, pos.x + 10, pos.y + size.y/4 - 5)  

        //  this.context.fillText(this.sublist[this.name].preparer1, this.position.left + 10, this.position.top + this.topsize/2 + 15)  
              
	}	
	
	// 간격 맞추기
	
	
	// 선 그리기
    line(a_tem, b_tem){

	  var a = this.resize(a_tem);
	  var b = this.resize(b_tem);
	  
	  a.y += this.size.y;
	  a.x += this.size.x/2;
	  b.x += this.size.x/2;
	  this.context.strokeStyle = 'rgba(100, 100, 230, 1)';
      
      if(a.x == b.x){
    	this.context.beginPath();
    	this.context.moveTo(a.x, a.y);
    	this.context.lineTo(b.x, b.y);
        this.context.stroke();
        // 화살표 그리기
        var direction = a.y - b.y;
        if(direction < 0){
            var sign = -1
        }else{
        	sign = 1
        }

     
        sign = -1
       	this.context.beginPath();
       	this.context.moveTo(b.x - 5, b.y + sign * 10);
   	    this.context.lineTo(b.x, b.y);
   	    this.context.lineTo(b.x + 5, b.y + sign * 10);
        this.context.stroke();


      }else{

      	
    	this.context.beginPath();
    	this.context.moveTo(a.x, a.y);
    	this.context.lineTo(a.x, (a.y + b.y)/2);
    	this.context.lineTo(b.x, (a.y + b.y)/2);
    	this.context.lineTo(b.x, b.y);
        this.context.stroke();

        // 화살표 그리기
        var direction = a.x - b.x;
        if(direction < 0){
            var sign = -1
        }else{
        	sign = 1
        }

        sign = -1
       	this.context.beginPath();
       	this.context.moveTo(b.x - 5, b.y + sign * 10);
   	    this.context.lineTo(b.x, b.y);
   	    this.context.lineTo(b.x + 5, b.y + sign * 10);
        this.context.stroke();

      }	

	}	

 // tag 관련

    //^^ viex에 관한 함수
   
   
   inputtable = (arr, data, node) => {

   	// 먼저 테이블 지우기
   	for(var i = 1; i < 11; i++){
           this.tablearr[i][0].innerText = "";
           this.tablearr[i][1].innerText = "";
   	}
   	
   	// 테이블 다시 채우기
   	
       var rowval = Math.min(arr.length, this.tablesize.height - 1);
   	
       if(data == null){
       	data = arr
       }
       
       var r = 0;    
   	for(var i in arr){
               r += 1;
               try{
                  this.tablearr[r][0].innerText = data[i].detailprocessname;
                  this.tablearr[r][1].innerText = data[i].processexplain;
                  
                  if(node != null){
               	   // 좌측의 팔레트를 클릭했을때임
                      this.additem4(this.tablearr[r][2], this.tablearr[r][0], node);
                  }else{
               	   // 가운데의 다이어크램을 클릭했을때임
                  ;
               	   this.additem5(this.tablearr[r][2], data[i]);
                  }

               }catch{
                  
               }
               
               if(r > rowval){
               	break;
               }

           
        }
   }

   inputtable2 = (arr) => {

       var rowval = Math.min(arr.length, this.tablesize.height - 1);
       var r = 0;    
                r += 1;
               try{
                  this.table2arr[r][0].innerText = arr.controlname;
                  this.table2arr[r][1].innerText = arr.controlexplain;


               }catch{
                  
               }
        
   }

   maketrtd(arr, count, td, stylearr){
       
       var div = document.createElement("tr");
    	for(var i = 0; i < count;i++){

    		if(td){
            	var subdiv = document.createElement(td)
    		}else if(i == 0){
            	var subdiv = document.createElement("th")
            	
       	}else{
            	var subdiv = document.createElement("td")
       	}

   	   arr[i] = subdiv;
   	   
   	   
   	   for(var j in stylearr){
       	   subdiv.setAttribute(j, stylearr[j]);
   	   }
   	   
   	   div.appendChild(subdiv);
    	}
   	return div
   }    
   
   makelabel = (item) => {

   	item[0][2].innerText = "선택";
       
       for(var i = 1; i <= this.tablesize.height - 1; i++){
          // 라디오 버튼을 테이브 앞단에 둬, 제목열 선택할 수 있게할 것
          this.selectlabel[i] = document.createElement("input")
          this.selectlabel[i].setAttribute('type', "radio");
          this.selectlabel[i].setAttribute('name', "subject");
          
          // 나중에 이것으로 이벤트 추가할 것 this.additem2(this.selectlabel[i], i);

          item[i][2].appendChild(this.selectlabel[i])   
       }
   }

   additem2 = (item, i) => {
       item.addEventListener('change',(me)=>{
            this.itemarray["제목행"] = i;
       })
   }
   
   additem3 = (item) => {
       item.addEventListener('click',(me)=>{
            console.log("click");
            this.testselect()
       })

   }

   additem4 = (item, item2) => {
       item.addEventListener('click',() => {
       	  console.log(this.Palette.model.nodeDataArray);
       	  var temp = this.Palette.model.nodeDataArray[0] 
       	  var obj = {key : item2.innerText,  shape: temp.shape, wid: temp.wid, 
                 hei: temp.hei, color: temp.color, type: temp.type,
                 realkey: temp.realkey};
       	 
       	  this.Palette.model = new go.TreeModel([obj])
             
       })

   }    

   additem5 = (item, arr) => {
       item.addEventListener('click',(me)=>{
            console.log("click");
            this.inputtable2(arr);
       })
   }


   start = (data) => {
       
   	
   	
   	// 부모, 자식 세팅하기
   	for(var i in data.child){
   		data[i].before = data.child[i];
   	}

   	for(var i in data.parent){
   		data[i].next = data.parent[i];
   	}
       
   	// totalarr 세팅하기 나중에 shape 등은 서버에서 세팅되어야 함
   	var totalarr = {}
   	for(var i in data){
           if(i != "child" && i != "parent"){
           	totalarr[i] = data[i];
           	totalarr[i].shape = "Rectangle";
           	totalarr[i].type = 1; 
           	
           }
   	}
   	
   	this.totalarr = totalarr;
   	
   	
   	
   	// 시작점 찾기
       for(var i in this.totalarr){
        	
       	if(this.totalarr[i].before.length > 0){
       	  if(this.totalarr[i].before[0] == "start"){
       		this.totalarr[i].widthpos = 1;  
       		var first = this.totalarr[i];
       		
       		
       		break
       	  }
       	}
       }    	
   	
       this.nodedata = []
       this.passeddata =  new Set([]);
       this.graphdata = [];

       this.diagram.model.nodeDataArray = [];
       
       
       
       this.possetting2(first);
       this.possetting3(first);
       this.nodedatasetting_graph2(first);
       //diagram1.nodedatasetting_graph(datasetting, datasetting["전표 접수"]);
       
       this.picture("graph");
   	
   }
   
   // div에 관한 것
   
   makediv = (text1, text2) => {
   	var div = document.createElement("div")

   	var subdiv1 = document.createElement("div")
  	subdiv1.innerText = text1;
   	

   	var subdiv2 = document.createElement("div")
    subdiv2.innerText = text2;


    div.appendChild(subdiv1);
    div.appendChild(subdiv2);
       
       return div;
   }

	makediv2(opt1, opt2, opt3){
		// 내용 + 금액/비율 + x표시
    	var div = document.createElement("div")
        div.setAttribute('class', "bundle");
        // 내용
        if(opt1 != null){
          if(typeof(opt1) == "object"){
        	var subdiv1 = this.makeselect(opt1);
        	subdiv1.setAttribute('class', "detailcontent");
        	subdiv1.style = "width: 80%;"
            div.appendChild(subdiv1);
          }else{
          	var subdiv1 = this.maketext();
        	subdiv1.setAttribute('class', "detailcontent");
        	subdiv1.style = "width: 80%;"
          	div.appendChild(subdiv1);
          }
        }

        // 금액/비율 
        
        if(opt2 != null){
        	var subdiv2 = this.makeselect(["금액", "비율"]);
        	subdiv2.setAttribute('class', "detailcontent");
        	subdiv2.style = "width: 5%;"
            div.appendChild(subdiv2);		
        }

        // x표시
        if(opt3 != null){
        	var subdiv3 = this.makebutton(opt3);
        	subdiv3.setAttribute('class', "detailcontent");
        	subdiv3.style = "width: 20%; font-weight: bold;"
            div.appendChild(subdiv3);		
        }
    	
        return div
	} 
   
    maketext(){
    	var text = document.createElement("input");
    	text.setAttribute('type', "text");
    	return text
    }
    makebutton(val){
        var button = document.createElement("Input");
        button.setAttribute('type', "button");
        button.setAttribute('value', val);
        button.addEventListener('click',()=>{this.closediv(button.parentNode)});
        return button;
    }    
   


	addevent_company(me){

			  var text = me;

		      text.addEventListener('input', (me)=>{
		    	  this.decidecompanysmall(me.target.value, me)
		      })

		      text.addEventListener('blur', (me)=>{
		    	  this.keyeventcount = "";
	  			  this.subtablediv.style =  "position: absolute;z-index: -100"
	     		        for(var k = this.subtable.childNodes.length - 1; k >= 0; k--){
	     		        	this.subtable.removeChild(this.subtable.childNodes[k]); 
	     		       }	    	  
		    	  
	          })

		    	  
		      text.addEventListener('keydown', (e)=>{

		    	  if(e.key == "Enter"){
	       			  this.subtablediv.style =  "position: absolute;z-index: -100"
	       		        for(var k = this.subtable.childNodes.length - 1; k >= 0; k--){
	       		        	this.subtable.removeChild(this.subtable.childNodes[k]); 
	       		       }
	       			  event.preventDefault();
		    	  }
		    	  
		    	  if(e.key == "ArrowDown"){
		    		  
		    		  
		    		  if(this.keyeventcount ===""){
		    			  
			    		  this.keyeventcount = 0;
		    		  }else if(this.keyeventcount >= this.subtable.childNodes.length - 1){
		    			  
		    			  return;
		    		  }else{
			    		  this.subtable.childNodes[this.keyeventcount].childNodes[0].style = "";
			    		  this.keyeventcount += 1
			    		  
		    		  }
		    		  e.target.value = this.subtable.childNodes[this.keyeventcount].innerText;
		    		  this.subtable.childNodes[this.keyeventcount].childNodes[0].style = "background: yellow;"
		    		  
		    		  
		    	  }else if(e.key == "ArrowUp"){

		    		  if(this.keyeventcount ==""){
			    		  this.keyeventcount = 0;
		    		  }else if(this.keyeventcount <= 0){
		    		     return;
		    		  }else{
			    		  this.subtable.childNodes[this.keyeventcount].childNodes[0].style = "";
			    		  this.keyeventcount -= 1
		    		  }

		    		  e.target.value = this.subtable.childNodes[this.keyeventcount].innerText;
		    		  this.subtable.childNodes[this.keyeventcount].childNodes[0].style = "background: yellow;"
		    	  }
		    	  
		    	  
		       })
		    	  
		
	}   
   
    decidecompanysmall(word, me){
    	
    	
    	var count_min = 1; 
    	var count_max = this.companysortedarr.length;
    	
    	for(var i = 0; i < 15; i++){
    		
    		var count = Math.round((count_min + count_max)/2)
    		
    		if(count_max - count_min <= 1)
    		    break
    		else if(this.companysortedarr[count] < word ){
    			count_min = count
    		}else{
    			count_max = count
    		}
    		
    	}
    	
    	
    	var arr = []
    	
    	for(var i = 1; i < 5; i++){
        	arr.push(this.companysortedarr[count_min + i])
    		if(count_min + i >= this.companysortedarr.length - 1){
    			break
    		}
    	}
    	
    	this.makesubtable(arr, me)
    	
    }
    
    
    makesubtable(arr, me){
        for(var k = this.subtable.childNodes.length - 1; k >= 0; k--){
        	this.subtable.removeChild(this.subtable.childNodes[k]); 
       }

    	var pos = me.target.getBoundingClientRect();
    	
    	// 절대좌표 기준으로 설정
    	
    	var left = Math.round(pos.left - this.lefttag.getBoundingClientRect().width) + 'px';
    	var top = Math.round(pos.top - 20 + window.pageYOffset) + 'px';
    	this.subtablediv.style = "position: absolute; left: " + left + "; top: " +top+ ";"
    	
    	// processlist 반영하기 
    	for(var i = 0; i < arr.length;i++){
    	    this.subtablearr[i] = {}
    	    var subdiv = this.maketrtd(this.subtablearr[i], 1, null, {"width": "10%"});
     		this.subtable.appendChild(subdiv)
    	    this.subtablearr[i][0].innerText = arr[i]
     		
     		this.addsubtest(me, i)
     		this.subtablearr[i][0].addEventListener("click", (real) => {
     			me.target.value = real.target.innerText;
     			this.subtablediv.style =  "position: absolute;z-index: -100"
       		        for(var k = this.subtable.childNodes.length - 1; k >= 0; k--){
       		        	this.subtable.removeChild(this.subtable.childNodes[k]); 
       		       }

     		})     		
    	}
    	         	
    }
    

    addsubtest(me, i){
 		this.subtablearr[i][0].addEventListener("mouseover", (real) => {
 			if(this.keyeventcount != ""){
     			this.subtable.childNodes[this.keyeventcount].childNodes[0].style = "";
 			}
 			this.keyeventcount = i;
 			me.target.value = real.target.innerText;
 		})
    }
   

   
   // ajax 관련

	ajaxmethod(link, data, act){
		
		return new Promise((resolve) => {
		
		// 스프링 시큐리티 관련
		var header = $("meta[name='_csrf_header']").attr('content');
		var token = $("meta[name='_csrf']").attr('content');
		
  		$.ajax({
  			type : "POST",
  			url : "/company/" + link,
  			data : data,
  			beforeSend: function(xhr){
  			  if(token && header) {
  				  
  		        xhr.setRequestHeader(header, token);
  			  } 
  		    },
  		    success : (res) => {
  				
  		    	console.log(res);
  		    	
  		    	if("error" in res){
  		    		alert(res['error']);
  		    	}else{
  		    		if(act){
  	   	                act(res); 
  		    		}
  		    	}
  		    	resolve()
               
  		    	
  				

  			},
           error: function (jqXHR, textStatus, errorThrown)
           {
                  console.log(errorThrown + " " + textStatus);
           }
  		})		
	  })
	}

   
   
}  
  
  
 
  window.onload = function(){
     
	 // diagram 관련 세팅 
	 var canvas = new canvasmaking();
	 


  }




 
  </script>


