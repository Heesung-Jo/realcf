

function repeatarr(arr, realarr, count, name, call){
	var image = new Image();
    image.src = arr[count];
    image.onload = function(){
        realarr[count] = image;
        count += 1; 
        
    	if(arr.length > count){
    		repeatarr(arr, realarr, count, name);
    	}else{
    		realtotalarr[name] = realarr;
    		if(call){
	            console.log("들어옴")
	            call()
            }
    		
    	}
   	}
}

class draw_class{
	
	constructor(){
	   this.rightcanvas = document.getElementById('rightcanvas');
	   this.rightctx = this.rightcanvas.getContext('2d');
	   this.realcanvas = document.getElementById('realcanvas');
	   this.realctx = this.realcanvas.getContext('2d');
       this.content = document.getElementById('content');
       this.itemarray = [];
		for(var ai = 0; ai < 13; ai++){	
            this.itemarray[ai] = {x: ai * 100 + 10, y: 10, w: 80, h: 80}
		}	   
	   
	   this.imagearr = {};
	       
	   this.realexplanationarr = {};    
	   this.repeatarr(imagearr, {}, (image) => {
                  
             this.drawitems();
		     this.imagedraw(image, offctx1);	
		     this.firstdraw();  

    	   }, 0)

	   // 이벤트 등록
       this.rightcanvas.onmousedown = (e) => {
           var x = e.x || e.clientX,
               y = e.y || e.clientY,
               loc = this.windowToCanvas(this.rightcanvas, x, y);

           e.preventDefault();
           this.mouseDown(loc);
      }	   
	  
	  this.repeat = null;
      
       
	}

	
	
	// 함수들
	repeatarr = (arr, realarr, callback, count) => {
		var image = new Image();

		image.src = arr[count];
	    image.onload = () => {
	    	realarr[count] = image;
	        count += 1; 
	              
	    	if(arr.length > count){
	    		this.repeatarr(arr, realarr, callback, count)
	    		 
	    	}else{
	    		callback(realarr)
                this.imagearr = realarr;
	    	}

	    }
	}
	
   windowToCanvas(canvas, x, y) {
      var bbox = canvas.getBoundingClientRect();
      return { x: x - bbox.left * (canvas.width  / bbox.width),
            y: y - bbox.top  * (canvas.height / bbox.height)
          };
    }	
	
   
   contentmake(name){
	   
	   
	   // 기존 글 삭제하기
       for(var k = this.content.childNodes.length - 1; k >= 0; k--){
           this.content.removeChild(this.content.childNodes[k]); 
      }
       this.realexplanationarr = {}; 
	   // 글 추가하기
	   var num = 0;
	   for(var i in realexplanation[name]){
		   var div =document.createElement('div');
		   var subject =document.createElement('h3');
		   subject.innerText = i;
		   div.appendChild(subject)
		   var subject =document.createElement('p');
		   subject.innerText = realexplanation[name][i];
		   div.appendChild(subject)
		   this.content.appendChild(div)
		   this.realexplanationarr[num] = div;
		   num++
	   }

   }
   
   repeatfunc = (pos) => {
	   
	   if(this.repeat){
 		   clearInterval(this.repeat);
		   this.repeat = null;
	   }
	   
	   var count = 0;
	   console.log(this.realexplanationarr)
	   this.realctx.clearRect(0, 0, this.realcanvas.width, this.realcanvas.height);
	   this.realctx.drawImage(realtotalarr[pos][count % realtotalarr[pos].length], 0, 0, this.realcanvas.width, this.realcanvas.height);
	   this.realexplanationarr[count].style = "background: pink;"
	   
	   this.repeat = setInterval(()=> {
		   count++;
           // 이미지 채우기
		   this.realctx.clearRect(0, 0, this.realcanvas.width, this.realcanvas.height);
		   this.realctx.drawImage(realtotalarr[pos][count % realtotalarr[pos].length], 0, 0, this.realcanvas.width, this.realcanvas.height);
		   this.realexplanationarr[count-1].style = ""
		   this.realexplanationarr[count].style = "background: pink;"
		   
		   if(count > realtotalarr[pos].length * 1 - 2){
		   // 여기에 클릭 등을 하면 화면에 그림 그리고 글도 pink로 변하게 할 것
              
              for(var a in this.realexplanationarr){
                  this.addevent(pos, a);
              }

			   clearInterval(this.repeat);
			   this.repeat = null;
		   }
		   
		   // 글자 색깔 바꾸기
		   
		   
	   }, 2000)
   }
	
   addevent(pos, a){

     
	 this.realexplanationarr[a].addEventListener('click',(me)=>{
         
	     for(var b in this.realexplanationarr){
		              this.realexplanationarr[b].style = "";
	     }
         this.realexplanationarr[a].style =  "background: pink;"
	     this.realctx.clearRect(0, 0, this.realcanvas.width, this.realcanvas.height);
	     this.realctx.drawImage(realtotalarr[pos][a], 0, 0, this.realcanvas.width, this.realcanvas.height);
         
        });
     }	
	
	
   mouseDown(loc) {
 
   
   for(var i = 0; i < this.itemarray.length; i++){
	   
	   var rect = this.itemarray[i];
       this.rightctx.beginPath();
       
       this.rightctx.rect(rect.x, rect.y, rect.w, rect.h);
       if (this.rightctx.isPointInPath(loc.x, loc.y)) {
    	   if(i < Object.keys(realtotalarr).length){
    		   this.contentmake(i);
    		   
    		   this.repeatfunc(i);
    		   
    		   
    	   }
    	   
	       this.drawitems(rect);
      }

       
   }
   
   this.imagedraw(this.imagearr, this.imagearr, offctx1);

};
	

   firstdraw() {
   
	   var i = 0;
	   var rect = this.itemarray[i];
       this.rightctx.beginPath();
       this.rightctx.rect(rect.x, rect.y, rect.w, rect.h);
       this.contentmake(i);
 	   
   	   this.repeatfunc(i);
 
};


	
	
	drawitems(selected) {
		   this.rightctx.clearRect(0, 0, this.rightcanvas.width, this.rightcanvas.height);
 		   
		   for(var i = 0; i < this.itemarray.length; i++){
			      var rect = this.itemarray[i];
			      this.rightctx.save();

    		      if (selected === rect) this.shadowmake(4);
			      else                   this.shadowmake(1);
			      this.rightctx.fillStyle = '#71B1D1';
			      this.rightctx.fillRect(rect.x, rect.y, rect.w, rect.h);
                  
			      this.rightctx.restore();
			      this.drawicon(rect);
		   }
           
		      
		}

	

	
	drawicon(rect) {
		   this.rightctx.save();
		   this.rightctx.strokeStyle = 'rgba(100, 140, 230, 0.5)';
		   this.rightctx.strokeRect(rect.x, rect.y, rect.w, rect.h);
		   this.rightctx.strokeStyle = 'rgb(100, 140, 230)';
		   this.rightctx.restore();
		}
	
   shadowmake(color) {
		   this.rightctx.shadowColor = 'rgba(0,0,0,0.7)';
		   this.rightctx.shadowOffsetX = color;
		   this.rightctx.shadowOffsetY = color;
		   this.rightctx.shadowBlur = color + 1;
		}
	
	
	imagedraw = (arr, ctx) => {

		var num = 0
		console.log(arr);
		for(var count in arr){
		    var rect = this.itemarray[num];
		    num += 1;
			//this.rightctx.drawImage(arr[count], rect.x, rect.y, rect.w, rect.h);	

            this.rightctx.font = '50px impact'
            this.rightctx.lineWidth = 2.0;	

            if(num >= 10){
	              this.rightctx.fillText(num, rect.x + 20, rect.y + 60);
            }else{
	              this.rightctx.fillText(num, rect.x + 25, rect.y + 60);
            }


		}
		
		
		
		
	}


	drawpicture = (image, rect) => {

		if(image){
	       this.rightctx.drawImage(image, rect.x, rect.y, rect.w, rect.h)	
	    }
	    this.rightctx.restore();
	 }
}


