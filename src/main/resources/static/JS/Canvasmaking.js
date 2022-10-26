class Canvasmaking  {
	
	constructor (size) {


		this.name = name;
		//this.sublist = obj;
		this.divisionrow = {}; //division
		this.currentcolumn = 0
		this.size = size; //{left : 300, top : 150}

        this.moving = {x: 0 ,y :0};
        
		this.totaldata = {};
		this.position = {top : 0, left : 0};
		this.leftsize = 150;
		this.fontsize = 15;
		this.topsize = 40;
		this.buttonsize = 50
        this.currentview = {};
        
        this.paintopt = 0;
        
        
        // 마우스 무브 관련
        this.startposition = {};
		
		// 캔버스 가져오기
		this.realcanvas = document.getElementById('canvas');
		this.realcontext = this.realcanvas.getContext('2d');
		
     	this.canvas = document.createElement('canvas');
     	this.canvas.width = this.realcanvas.width * 2;
     	this.canvas.height = this.realcanvas.height * 2;
     	this.canvas.style = this.realcanvas.style;
     	this.context = this.canvas.getContext('2d');

		this.dpr = window.devicePixelRatio * 2; //이거 수정하면 hovertest도 수정필요
		
        this.realcanvas.width =  this.realcanvas.width * this.dpr;
        this.realcanvas.height = this.realcanvas.height * this.dpr;
        this.realcontext.scale(this.dpr, this.dpr);

        this.canvas.width =  this.canvas.width * this.dpr;
        this.canvas.height = this.canvas.height * this.dpr;

        this.context.font = this.fontsize + "px Arial";
        this.context.scale(this.dpr, this.dpr);
		this.selection = null;

            var dom = this.realcanvas;
            var domrect = this.realcanvas.getBoundingClientRect();
            
    
		this.realcanvas.addEventListener("mousemove", (event) => {
            
			var x = event.clientX - domrect.x;
			var y = event.clientY- domrect.y;
			
            this.hovertest(this.rectlist, x, y);
			
        }, false);
        
        
        this.realcanvas.addEventListener("mousedown", (event) => {
	         this.startposition = {x: event.clientX, y: event.clientY};
        })


        this.realcanvas.addEventListener("mouseup", (event) => {
	         this.moving = {x: this.moving.x + event.clientX - this.startposition.x, y: this.moving.y + event.clientY - this.startposition.y};
	         
	         this.repaint(this.moving.x, this.moving.y);
        })


		this.realcanvas.addEventListener("click", (event) => {
 
			var x = event.clientX - domrect.x;
			var y = event.clientY- domrect.y;
			
			
            this.selection = this.hovertest(this.rectlist, x, y);
            if(this.selection){
	           // 글씨 쓰기
			   this.func(this.selection)
			   
			   // 그림 그리기
			   
			   this.repaint_full(this.selection);
			   
            }
			
        }, false);
		
		// 도형의 위치 감지관련
		this.rectlist = {};
		this.linelist = [];
		
	
	}
	

	// 함수 시작
	// 아작스 하기
	clear(){
		this.rectlist = {};
		this.linelist = [];
		this.moving = {x: 0, y: 0};
		
		this.context.clearRect(0, 0, this.canvas.width, this.canvas.height);
	}

	clickevent(func){
		this.func = func;
	}
	

	repaint_full(key){
		
		// 일단 지우기 
		this.context.clearRect(0, 0, this.canvas.width, this.canvas.height);
		this.moving.x = 0;
		this.moving.y = 0;
		
		// 다시 그리기
		for(var ai in this.rectlist){
			
			var obj = this.rectlist[ai];
			var color = "";
			
			if(obj.realname == key){
				color = "green";
			}else{
				color = "white";
			}
			
			if(this.paintopt == 0){
    			this.rect1(obj.realname, obj.name, obj.position, color);  // 색을 바꾸기 위해서 그리는 것임
			}else{
    			this.rect2(obj.realname, obj.name, obj.division, obj.control, obj.risk, obj.position, color);  // 색을 바꾸기 위해서 그리는 것임
			}
		}
		
		for(var ai in this.linelist){
			this.line(this.linelist[ai].a, this.linelist[ai].b);
		}
		
		this.paint(key);
		
	}
	
	

    repaint(x, y){
	     this.realcontext.clearRect(0, 0, this.realcanvas.width, this.realcanvas.height);
	     this.realcontext.drawImage(this.canvas, x, y, this.canvas.width/this.dpr, this.canvas.height/this.dpr)
	     
    }
    
    paint(name){
	     //console.log(name);
	     this.moving.x = this.realcanvas.width/(2 * this.dpr) - this.rectlist[name].x;
	     this.moving.y = this.realcanvas.height/(2 * this.dpr) - this.rectlist[name].y;
	     this.realcontext.clearRect(0, 0, this.realcanvas.width, this.realcanvas.height);
 	     this.realcontext.drawImage(this.canvas, this.moving.x, this.moving.y, this.canvas.width/this.dpr, this.canvas.height/this.dpr)
	
     }
    

    // hover 감지
	hovertest(rectlist, x, y){
		
        var totalwidth = parseInt(window.getComputedStyle(this.realcanvas).width.replace("px", ""));
        var totalheight =parseInt(window.getComputedStyle(this.realcanvas).height.replace("px", ""));		
		
		for(var xy in rectlist){
            var rect = rectlist[xy];
			
			// x 관련 y 관련
			var x1 = (rect.x - this.size.left/4 + this.moving.x) * totalwidth/ this.realcanvas.width * this.dpr;
			var x2 = (rect.x + this.size.left/4 + this.moving.x) * totalwidth/ this.realcanvas.width * this.dpr;
			var y1 = (rect.y - this.size.top/4 + this.moving.y) * totalheight/ this.realcanvas.height * this.dpr;
			var y2 = (rect.y + this.size.top/4 + this.moving.y) * totalheight/ this.realcanvas.height * this.dpr;

			if( x1 < x && x2 > x && y1 < y && y2 > y){
                this.realcanvas.style.cursor = "pointer"; 
				return xy;
			}else{
				this.realcanvas.style.cursor = "default"; 
			}
			
		}
             
		return null;
	}
	
	
	// 사각형 그리기
	rect1(realname, name, position, color){
        
        var moving = this.moving;
        
		// 리스트에 집어넣기
		this.rectlist[realname] ={x: position.left + this.size.left/4, y: position.top+ this.size.top/4
		                         , realname: realname, name: name, position: position, color: color};
		
	     // 사각형 그리기
		this.context.lineWidth = 1;
		this.context.fillStyle = color;
		this.context.shadowColor = 'rgba(0,0,0,0.7)';
        this.context.shadowOffsetX = 5;
        this.context.shadowOffsetY = 5;
        this.context.shadowBlur = 6;
     	this.context.fillRect(position.left + moving.x, position.top + moving.y, this.size.left/2, this.size.top/2)
		this.context.textAlign = "center";

		// 세팅 바꾸기
		this.context.shadowColor = 'rgba(0,0,0,0.7)';
        this.context.shadowOffsetX = 0;
        this.context.shadowOffsetY = 0;
        this.context.shadowBlur = 0;
        this.context.strokeRect(position.left + moving.x, position.top + moving.y, this.size.left/2, this.size.top/2)
		
		// 글씨 쓰기
        this.context.fillStyle = 'black';
		this.context.font = "12px arial";
		// 10자를 초과하면 쪼개서 두줄로 쓰기
		
		var name1 = name;
		if(name.length > 10){
			name1 = name.substr(0, name.length/2);
	    	var name2 = name.substr(name.length/2, name.length - name.length/2); 
     		this.context.fillText(name2, position.left + this.size.left/4  + moving.x, position.top + this.size.top/4* 2 - 5 + moving.y, this.size.left/2 ) 
			
		}
		this.context.fillText(name1, position.left + this.size.left/4 + moving.x, position.top + this.size.top/4 * 1 - 5+ moving.y, this.size.left/2 ) 
        
	}	
	
	rect2(realname, name, division, control, risk, position, color){
 
        var moving = this.moving;

		// 리스트에 집어넣기
		this.rectlist[realname] = {x: position.left + this.size.left/4, y: position.top+ this.size.top/4
		                           , realname: realname, name: name, position: position, color: color, 
		                           division: division, control: control, risk: risk};

		
	     // 사각형 그리기
		this.context.lineWidth = 1;
		this.context.fillStyle = color;
		this.context.shadowColor = 'rgba(0,0,0,0.7)';
        this.context.shadowOffsetX = 5;
        this.context.shadowOffsetY = 5;
        this.context.shadowBlur = 6;
     	this.context.fillRect(position.left + moving.x, position.top + moving.y, this.size.left/2, this.size.top/2)
		this.context.textAlign = "center";

		// 세팅 바꾸기
		this.context.shadowColor = 'rgba(0,0,0,0.7)';
        this.context.shadowOffsetX = 0;
        this.context.shadowOffsetY = 0;
        this.context.shadowBlur = 0;
        this.context.strokeRect(position.left + moving.x, position.top + moving.y, this.size.left/2, this.size.top/2)
		
		// 글씨 쓰기
        this.context.fillStyle = 'black';
		this.context.font = "12px arial";
		// 10자를 초과하면 쪼개서 두줄로 쓰기
		
		var name1 = name;
		if(name.length > 10){
			name1 = name.substr(0, name.length/2);
			
			
			var name2 = name.substr(name.length/2, name.length - name.length/2); 
     		this.context.fillText(name2, position.left + this.size.left/4  + moving.x, position.top + this.size.top/8 * 4 - 5 + moving.y, this.size.left/2 ) 
			
		}
		this.context.fillText(name1, position.left + this.size.left/4 + moving.x, position.top + this.size.top/8 * 3 - 5+ moving.y, this.size.left/2 ) 

		// 부서명
        this.context.fillText(division, position.left+ this.size.left/4 + moving.x, position.top + this.size.top/8 - 5+ moving.y, this.size.left/2 )  
        
		// 리스크 번호
		
		
		this.context.fillText(risk, position.left+ this.size.left/8 + moving.x, position.top + this.size.top/8*2 - 5+ moving.y, this.size.left/4 );

		// 통제 번호
		console.log(control);
		this.context.fillText(control, position.left+ this.size.left/8*3 + moving.x, position.top + this.size.top/8*2 - 5+ moving.y, this.size.left/4 );

		// 선그리기
    	this.context.beginPath();
    	this.context.moveTo(position.left + moving.x, position.top + this.size.top/4 + moving.y);
    	this.context.lineTo(position.left + this.size.left/2 + moving.x, position.top + this.size.top/4 + moving.y);
        this.context.stroke();

		this.context.beginPath();
    	this.context.moveTo(position.left + moving.x, position.top + this.size.top/8 + moving.y);
    	this.context.lineTo(position.left + this.size.left/2 + moving.x, position.top + this.size.top/8 + moving.y);
        this.context.stroke();

		this.context.beginPath();
    	this.context.moveTo(position.left + this.size.left/4 + moving.x, position.top + this.size.top/8 + moving.y);
    	this.context.lineTo(position.left + this.size.left/4 + moving.x, position.top + this.size.top/8*2 + moving.y);
        this.context.stroke();
        
	}	
		
	
	// 선 그리기
    line(a_tem, b_tem){
      this.linelist.push({a: a_tem, b: b_tem});
      
      var moving = this.moving;
	  var a = {left: a_tem.left  + moving.x, top: a_tem.top + this.size.top/4 + moving.y};
	  var b = {left: b_tem.left + moving.x, top: b_tem.top - this.size.top/4 + moving.y};
		
      if(a.left == b.left){
		  
    	this.context.beginPath();
    	this.context.moveTo(a.left, a.top);
    	this.context.lineTo(b.left, b.top);
        this.context.stroke();

      }else{

      	
    	this.context.beginPath();
    	this.context.moveTo(a.left, a.top);
    	this.context.lineTo(a.left, (a.top + b.top)/2);
    	this.context.lineTo(b.left, (a.top + b.top)/2);
    	this.context.lineTo(b.left, b.top);
        this.context.stroke();


      }	
        // 화살표 그리기
        var direction = a.top - b.top;
        if(direction < 0){
            var sign = -1
        }else{
        	sign = 1
        }

       	this.context.beginPath();
       	this.context.moveTo(b.left - 6, b.top + sign * 10);
   	    this.context.lineTo(b.left, b.top);
   	    this.context.lineTo(b.left + 6, b.top + sign * 10);
        this.context.stroke();

       
	}
	
}  