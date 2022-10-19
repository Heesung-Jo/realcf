class diagram{
    constructor(){
    	
       var tempdata = window.localStorage.getItem('totaldata');	
       
       this.totaldata = JSON.parse(tempdata);
       
       
       
       this.samewordhash = {};
       this.samewordsetting();
       
       
       this.canvas = document.getElementById('canvas');
       this.func = {};
       this.width = 300;
       this.height = 150;
       this.canvasmaking = new Canvasmaking({left: this.width, top: this.height});
       this.canvasmaking.clickevent((x) => {
    	   
    	   this.func(x);
           listmanage.childNodes.forEach((s) => {
	           
	           
	           if(s.innerText == x){
    	           s.style.background = "blue";
	           }else{
		           s.style.background = "none";
	           }
           });
       })
       
       this.paintopt = 0;
       
       this.managearr = {};     //{"담당자": select, "팀1": select 등} 으로서 쉽게 담당자 등의 select에 접근하기 위해서 만들었음
                                // 마지막에 이 값만 ajax로 넘겨버리면 모든 답을 받을 수 있는 구조임
       this.manageinnertext = {};    //{"process1_0": div, "process1_1": div } 으로서 쉽게 사이사이에 있는 innertext에 쉽게 접근하기 위해서 만들었음
       this.managefirsttext = {};    //{"process1_0": "진짜글", "process1_1": "진짜글" } 으로서 여기서 @를 찾아서 위의 manageinnertext에 접근하여 글을 바꿀 것임
       
       
       this.realdist = "";
       this.realdistarr = [];
       
       this.currentkey = "";
       
       this.divisionsize_y = 100;
       this.divisionsize_x = 100;
       this.divisions = {};
       this.divisionpos = {};
       this.datapool = [];
   

       this.textwid = 100;
       
       // 아래쪽 테그 관련한 것들
       // 210505 이 부분을 클릭하면 이제 아작스로 받아오는 것으로 바꿀것
       // 대신에 기존에 아작스로 받아오는 부분은 수정해야함
       this.processcount = 7; //processexplain 등의 개수를 의미한 processexplain1 ~ 7일 때 여기서 7이 이에 해당함
       
       
       this.myworkradio = document.getElementsByName('myworkradio');
       
       this.myworkbutton = document.getElementById('myworkbutton');
       
       this.diagramselect_pa = document.getElementById('DiagramSelect_pa');
       this.diagramselect_ch = document.getElementById('DiagramSelect_ch');
       this.diagramteamselect = document.getElementById('DiagramTeamSelect');
       this.diagramcheckbox = document.getElementById('Diagramcheckbox');
       this.diagramexplain = document.getElementById('myexplain');
       this.diagramexplaincontrol = document.getElementById('mycontrol');
       
       // 이 아래쪽은 위의 diagramexplain과 관련된 우측의 table과 button에 관한 것들
       this.tablesize = {width : 2, height : 7};
       this.tablearrcount = 0;
       this.tablearr = {};
       this.table2size = {width : 2, height : 7};
       this.table2arr = {};
       this.table2arrcount = 0;
       
       this.selectlabel = {};
       
       var button = document.getElementById('process_answer');
       
	   this.table = document.createElement("table"); // div 가 나은것 같으면
       
	   this.table.setAttribute("border", "10");
	   this.maketable();
	   this.diagramexplain.appendChild(this.table); 
	   
       this.tablearr[0][0].innerText = "세부프로세스";
       this.tablearr[0][1].innerText = "설명";

       button.addEventListener('click',(me)=>{
	       this.process_answer();
	   })
	   
	   // 이 아래쪽은 위의 diagramexplain 항목을 선택했을때 나타나는 아래쪽 테이블 관련
	   this.table2 = document.createElement("table"); // div 가 나은것 같으면
       this.diagramexplaincontrol.appendChild(this.table2); 
	   this.table2.setAttribute("border", "10");
	   this.maketable2();
       this.table2arr[0][0].innerText = "통제활동";
       this.table2arr[0][1].innerText = "설명";
	   //this.makelabel(this.table2arr);

       
       // sentenceplus 부분을 서버에 넘겨서 db에 추가하기 위해서 만들 데이터
       this.sentencearr = {}            // arr는 말 그대로 문장저장 processexplain 같은 것 그대로 복제
       this.sentenceoptionarr = {}      // option부분 추가할 것을 저장하기 위해서 만들었음
       this.sentencecountarr = [];          // 새로 만들어지는게 processpluscount2 등에 2번째, 3번째 등의 기록이 될 것임
       
       // 질문서가 될 tag임
       this.maintextdiv = {};
       this.maintextarr = {};
       this.maintext = "";

    }
  
  
  //  이 아래부터 함수 시작
  
   setpaintopt(opt){
	   this.paintopt = opt;
       this.canvasmaking.paintopt = opt;
   }
  
    actevent = (key) => {
	       
    	   this.func(key);
    }    


   samewordsetting(){
	
    	// 일단은 sameword1으로만 세팅함. 나중에 필요하면 더욱 확장할 것
	   for(var item in this.totaldata){
		    var obj = this.totaldata[item];
		    if(obj.sameword1){
			     
			     if(obj.processoption[obj.sameword1].result){
				     this.samewordhash[obj.sameword1] = {result: obj.processoption[obj.sameword1].result, process: obj.processname1};
			     }
		     }
		    
	   }
  }


  samewordtest(word, value, process, opt){
	
	    // 해당 프로세스에서는 고칠 수 있어야 하므로 그 부분은 제외 
	    if(this.samewordhash[word]){
		    if(this.samewordhash[word].result != value && this.samewordhash[word].process != process){
			   if(opt == 1){
			     alert("선택한 단어인 " + value + "는 다른 프로세스와 연관된 단어로서, 이미 " + this.samewordhash[word].process + " 프로세스에서 " + this.samewordhash[word].result + "로 선택이 되었습니다.");
				
     			}
		         return this.samewordhash[word].result;
		    }
	    }
	    
	    return null;
	    
  }

  nodedatapos = (arr) => {
    // 리스트들 순환하면서 팀 위치를 만들 것
   

    // team hash 만들기
    for(var i in arr){
      
      if(this.divisions[arr[i]["team"]]){
        this.divisions[arr[i]["team"]] = 0;
      }else{
        this.divisions[arr[i]["team"]] = 0;
      }

    }

   // team hash에 위치 세팅하기
   var pos = 0;
   for(var i in this.divisions){
      this.divisionpos[i] = pos;
      pos += this.divisionsize_y;
   }
  }
  
 
  process_answer_front(){
	  
	  // realhash에 결정된 값을 집어넣기
	  var realhash = {}
	  var arr = ['select', 'input']
	  
	  
	  
	  var tabletotal = [this.tablearr, this.table2arr];
	  
	  for(var tag in arr){
		  
		  for(var aa = 0; aa < tabletotal.length; aa++){
			  
		      var tablearr = tabletotal[aa];
			  for(var i in tablearr){
				  var list = tablearr[i][1].querySelectorAll(arr[tag]);
				  
				  if(list.length > 0){
		
					  list.forEach((item) => {
						  // 이제 리스트 식이 아니라, name마다 하나씩이도록 조정함
						  if(item.value == "없음"){
							  realhash[item['name']] = null;
						  }else{
							
							
							 this.totaldata[this.currentkey].processoption[item['name']].result = item.value;
						  }
						  
					  });
				  }
			  }			  
		  }
	       

	  }
	  
	  // totaldata 갈아끼우기
	  window.localStorage.setItem('totaldata', JSON.stringify(this.totaldata));	
	  alert(this.currentkey + "에 대한 입력이 완료되었습니다.");
	  
	  
  }
  
  
  process_answer(){
	  
	  // realhash에 결정된 값을 집어넣기
	  var realhash = {}
	  var arr = ['select', 'input']
	  
	  
	  
	  
	  
	  for(var tag in arr){
		  for(var i in this.tablearr){
			  var list = this.tablearr[i][1].querySelectorAll(arr[tag]);
			  
			  if(list.length > 0){
	
				  list.forEach(function(item) {
					  
					  // 이제 리스트 식이 아니라, name마다 하나씩이도록 조정함
					  if(item.value == "없음"){
						  realhash[item['name']] = null;
					  }else{
						  realhash[item['name']] = item.value;
					  }
					  
					  /*
					  if(item['name'] in realhash){
						  realhash[item['name']].push(item.value)
					  }else{
						  realhash[item['name']] = [item.value]
					  }
					  */
					  
				  });
			  }
		  }
	  }


	  // realhash을 parse로 string으로 바꿀것
	  /*
	  for(var i in realhash){
		  var temp = {}
		  var num = 0;
		  for(var j in realhash[i]){
			  temp[num] = realhash[i][j];
			  num++
		  }
		  realhash[i] = JSON.stringify(temp)
	  }*/
	  
	  
	  realhash[this.currentkey] = JSON.stringify(this.totaldata[this.currentkey]);
	  
	  
	  this.process_answer_front();  // 뷰만 보여줄 예정이라서 프로튼에서 하는 것으로 수정하였음
	  
	  //this.ajaxmethod("process_answer/" + this.currentkey, realhash, () => alert("잘 입력이 되었습니다."));
	  
  }
  
  /*
	sentenceplus(JSONObject data, String name){
		
		 processoption pros = new processoption();
		 
		 uniteddata uni = UniteddataRepository.findByProcessname1(name).get(0);
		 
		 // 프로세스 부분 업데이트 하기
		 for(int i = 1; i <= this.processcount; i++) {

			 // processname 갈아끼우기
			 var word = "processname" + i;
			 
			 var processname = data[word];
			 Class<?>[] parameterTypes = new Class[1];
			 parameterTypes[0] = String.class;
			 
			 Method me = processdata.class.getMethod("setProcessname" + i, parameterTypes);

			 Object parameter[] = new Object[1];
			 parameter[0] = processname;
			 me.invoke(uni, parameter); // text 내용을 갈아끼웠음

			 // processexplain 갈아끼우기
			 word = "processexplain" + i;
			 
			 String processexplain = (String) data.get(word);
			 parameterTypes[0] = String.class;
			 
			 me = processdata.class.getMethod("setProcessexplain" + i, parameterTypes);
			 parameter[0] = processexplain;
			 me.invoke(uni, parameter); // text 내용을 갈아끼웠음

			 // processplus 갈아끼우기
			 word = "processplus" + i;
			 
			 System.out.println(word);
			 if(data.get(word) != null) {
				 System.out.println(data.get(word));
				 Integer processplus = Integer.parseInt(String.valueOf(data.get(word)));
				 parameterTypes[0] = Integer.class;
				 
				 me = processdata.class.getMethod("setProcessplus" + i, parameterTypes);
				 parameter[0] = processplus;
				 me.invoke(uni, parameter); // text 내용을 갈아끼웠음
		 }

			 // processpluscount 갈아끼우기
			 word = "processpluscount" + i;
			 System.out.println(word);
			 
			 if(data.get(word) != null) {
				 Integer processpluscount = Integer.parseInt(String.valueOf(data.get(word)));
				 parameterTypes[0] = Integer.class;
				 
				 me = processdata.class.getMethod("setProcesspluscount" + i, parameterTypes);
				 parameter[0] = processpluscount;
				 me.invoke(uni, parameter); // text 내용을 갈아끼웠음
			 }
			 
		 }
		 
		 UniteddataRepository.save(uni);
		 System.out.println("일단 통과함");
		
	}
  
  
  */
  

  possetting = (arr) => {
     
     // 팀별로 갯수세기
     var team = {};
     var team_other = {}; // 팀별로 위치를 세팅하기 위해서 만든 것 // 이건 그냥 리턴 값임
     var num = 0;
     var team_current = "";
     var team_start = ""


     for(var i in arr){
         
         if(arr[i].team in team){
            team[arr[i].team].count += 1;

         }else{
            team[arr[i].team] = {count: 1, 가로: 0, 세로: num, before: team_current};

            if(team_current != ""){
              team[team_current]["next"] = arr[i].team;
            }else{
              team_other["team_start"] = arr[i].team;
            }
            num += 1;
         }

         team_current =  arr[i].team;
     }

     // 팀 갯수 분할하기
     var 최초 = Math.ceil(Math.sqrt(Object.keys(arr).length));

     var 가로 = 최초;
     var 세로 = 0;
     for(var i in team){
        
        세로 += Math.ceil(team[i].count/가로);

     }
     
     var 최소 = Math.max(가로, 세로);
     var 결정가로 = 가로;
     var 결정세로 = 세로;
     //세로가 최초보다 클때까지는 가로를 늘려가면서 최적 팀별 위치 쪼개기 

     while(세로 > 최초){
        가로 += 1;
        세로 = 0;
        for(var i in team){

           세로 += Math.ceil(team[i].count/가로);
        }
        
        if(최소 > Math.max(가로, 세로)){
            결정가로 = 가로;
            결정세로 = 세로;
        }

     }
     
     var next = team[team_other["team_start"]];
     team_other["결정가로"] = 결정가로;
     team_other["결정세로"] = 결정세로;

     var num = 0;

     while(next){
         
         next.세로 = num;
         num += Math.ceil(next.count/결정가로);
         

         if(next.next){
            next = team[next.next];
         }else{
            next = "";
         }

     }
     // 210406 아래 함수 nodedatasetting2에서 활용할 것
     // 먼저 이 console부터 보고 시작할 것
     

     
     return {team: team, team_other: team_other}
  


  }
   
  
  possetting2 = (first) => {
    
    var count = 1;
    var co = 0;
    
    
    
    var arr = []
    for(var i in first){
        arr.push(first[i].processname1);
    }
    
    var teamdata = {};
    var passeddata = new Set([]);

    while(count > 0 && co < 10){
     co += 1;
     
     var next = [];
     var temp= {};
     
     for(var i in arr){
         if(this.totalarr[arr[i]].superpro.length > 0){
        	 // 211128 여기 일단은 superpor[0]으로 처리했으나,
        	 // 이거 manytomany로 수정하면서 바꾼 것이라서 전체적으로 수정필요
             passeddata.add(this.totalarr[arr[i]].superpro[0]);
             next.push(this.totalarr[arr[i]].superpro[0]); 
         }
    	 
       /* 예전과 반대로 프로세스데이터 자식관계가 설정되어서 이건 과거코드이고 위에있는 코드로 좀 수정하였음. 다만 다시 사용될 가능성이 있으므로 살려둠	 
       for(var j in this.totalarr[arr[i]].childnodedata){
    	 
          if(passeddata.has(this.totalarr[arr[i]].childnodedata[j]) == false){
             passeddata.add(this.totalarr[arr[i]].childnodedata[j]);
             next.push(this.totalarr[arr[i]].childnodedata[j]) 
          }
          
       }
       */

       // for문 안쪽 연산
       // temp를 통해서 팀별 갯수세기
       var turn = this.totalarr[arr[i]];
       if(turn.teamname in temp){
          temp[turn.teamname] += 1;
       }else{
    	   
          temp[turn.teamname] = 1;
       }

      }
     
      // for문 바깥쪽 시작임  

      // 다음 순환을 위한 세팅하기
       count = next.length;
       arr = next.slice();

       // for문 바깥쪽 연산  
       // 기존의 teamdata와 비교해서 더 높은 카운트 나온팀은 대체하기
      
       
       for(var i in temp){
           if(i in teamdata){
             
             if(teamdata[i] < temp[i]){
                teamdata[i] = temp[i];
             }

           }else{
             teamdata[i] = temp[i];
           }
       }
     }

    
     var count = 1;
     var realteamdata = {}
     for(var i in teamdata){
        var temp = [];
        for(var j = 1; j <= teamdata[i]; j++){
           temp.push(count)
           count += 1;
        }
        realteamdata[i] = temp;
     }

     this.realteamdata = realteamdata;
     
   }






   distcal = (realarr, teamarr) => {
      
      var dist = 0;
      for(var i in teamarr){
        // 나중에 이 부분이 여러개인 것 중에 이 전 계층인 것으로 고르는 것으로 변형 필요
        // 이 전 계층이 여러개이면 그 중에 하나만 선택하는 것도 필요
         var before = this.totalarr[teamarr[i]].before[0]; 
         var widthpos = this.totalarr[before].widthpos;
         dist += (widthpos - realarr[i]) * (widthpos - realarr[i]);
      }
      return dist;


   }
  
  
  
  
  
  nodedatasetting = (pos) => {
   // 그럼 위의 것이 나온다고 생각하면 됨
   
   /*
    this.diagram.add(
    $(go.Part, "Vertical",
      $(go.Node, "Auto", {location: "0 0"}),
      $(go.TextBlock, {text: "통제활동", font: '15px serif', margin: 2 }),
    )) 
   */

   var realarr = []
   


   //팀 세팅
   /* 팀 그림은 딱 고정되도록 canvas로 그리는게 좋을 듯
   var team =  {key : "회계팀", hei: 100, loc: "-150 0", fig: "Rectangle", color: "white"}
   realarr.push(team);
   var team =  {key : "자금팀", hei: 100, loc: "-150 100", fig: "Rectangle", color: "white"}
   realarr.push(team);
   */
   
   var arr = {} // 서버에서 받을 배열, 현재는 있다고 가정
   var shape = [{shape : "Rectangle", wid : 50, hei : 50}, {shape : "Decision", wid : 70, hei : 50}, 
                {shape : "Cylinder1", wid : 50, hei : 50}, {shape : "SquareArrow", wid : 50, hei : 50, angle: 90}]

   for(var i = 0; i < 4; i++){
      // sublistloop를 가져다 쓰는 것이 바탕이 되야함
      // 기본 그림
      // 지금은 임의로 내가 위치를 세팅 시켰는데, 서버에서 프로세스 배열이 넘어오면
      // 이런식으로 세팅할 것(아래 3문장 참고)
      //var y = this.divisionpos[arr["team"]]
      //var x = this.divisions[arr["team"]] * this.divisionsize_x;
      //this.divisions[arr["team"]] += 1;      

      var x = pos;
      var y = 70 * i;
      var obj = {key : "취득" + i,  fig: shape[i].shape, wid: shape[i].wid,
                 hei: shape[i].hei, angle: shape[i].angle, color: "white", loc: x + " " + y}
      

      // 부모 넣어주기
      if(i > 0){
        var before = i - 1;
        obj.parent = "취득" + before; 
      }
      this.nodedata.push(obj);
      
      // 통제활동이 있으면 추가할 것
      if(1 == 1){  // 나중에 조건 집어넣을 것
         this.pic_control(x, y)
      }

      // erp가 있으면 추가할 것
      if(1 == 1){  // 나중에 조건 집어넣을 것
         this.pic_erp(x, y)
      }



     }
     
   }


  nodedatasetting2 = (first) => {
   // 그럼 위의 것이 나온다고 생각하면 됨
   
   var temp = this.possetting(this.totalarr);
   var team = temp.team;
   var team_other = temp.team_other;

   // return 된 team의 구조
   // {team_start: "영업팀"
   // 영업팀: {count: 3, 가로: 0, 세로: 0, before: "", next: "회계팀"}
   // 회계팀: {count: 1, 가로: 0, 세로: 2, before: "영업팀"}   }

   var realarr = []
   
   var arr = []
   for(var i in first){
       arr.push(first[i].processname1);
   }   

   var shape = [{shape : "Rectangle", wid : 50, hei : 50}, {shape : "Decision", wid : 70, hei : 50}, 
                {shape : "Cylinder1", wid : 50, hei : 50}, {shape : "SquareArrow", wid : 50, hei : 50, angle: 90}]

   var count = 1;
   var co = 0;
   while(count > 0 && co < 10){
    co += 1;
    
    var next = [];

    for(var i in arr){
      // sublistloop를 가져다 쓰는 것이 바탕이 되야함
      // 기본 그림
      // 지금은 임의로 내가 위치를 세팅 시켰는데, 서버에서 프로세스 배열이 넘어오면
      // 이런식으로 세팅할 것(아래 3문장 참고)
      //var y = this.divisionpos[arr["team"]]
      //var x = this.divisions[arr["team"]] * this.divisionsize_x;
      //this.divisions[arr["team"]] += 1;      
      
      next = next.concat(this.totalarr[arr[i]].next);
      
      var turn = this.totalarr[arr[i]];
        
      
      // 위치결정
      var x = team[turn.team]["가로"] * this.width + 50;
      var y = team[turn.team]["세로"] * this.height + 50;

      // 다음 위치 배정하기      
      team[turn.team]["가로"] += 1;
      if(team[turn.team]["가로"] >= team_other["결정가로"]){
        team[turn.team]["가로"] = 0;
        team[turn.team]["세로"] += 1;
      }


      var obj = {key : turn.detailprocessname,  fig: turn.shape, wid: turn.detailprocessname.length * 15, 
                 hei: 50, angle: shape[i].angle, color: "white", loc: x + " " + y,
                 parent: turn.before, realkey: turn.detailprocessname, }
      

      this.nodedata.push(obj);

 


      }

      count = next.length;
      arr = next.slice();
      
    } 
     
   }

  
 nodedatasetting_func(first){
	  
	  
	  var arr = []
	   for(var i in first){
		      console.log(first[i].processname1);
		      
		  this.whilefunc(first[i].processname1, 0, 0);
	   }
       
	   this.canvasmaking.paint(this.currentkey);
  }

  
  whilefunc(myname, current_x, current_y){
	  
	  
	  var turn = this.totaldata[myname];
	     // 위치결정
      var x = current_x * this.width + 50;
      var y = current_y * this.height + 50;

      
      
      var text = turn.processname1;
      text = text.replace("^{", "");
	  text = text.replace("@{", "");
	  text = text.replace("}^", "");
	  text = text.replace("}@", "");
 
      var color = "white";
      if(myname == this.currentkey){color = "green"}
	  
	  if(this.paintopt == 0){
    	  this.canvasmaking.rect1(turn.processname1, text, {left: x, top: y}, color);
	  }else if(this.paintopt == 1){
		
		  var text = turn.division;
          text = text.replace("^{", "");
	      text = text.replace("@{", "");
	      text = text.replace("}^", "");
	      text = text.replace("}@", "");
	      text = text.replaceAll("@", "");

console.log(text)
console.log(turn.processoption[text])
		  var division = turn.processoption[text].result;
		  var control = turn.controlcode;
		  var risk = turn.riskcode;
		  
		  this.canvasmaking.rect2(turn.processname1, turn.processname1, division, control, risk, {left: x, top: y}, color);

	  }
	  
	  
      
      if(turn.subpro){
    	 
    	 // 다음턴으로 넘기기
    	 current_y++;
    	 for(var k in turn.subpro){
             
    		 // 선그리기
    		 this.canvasmaking.line({left: x + this.width/4, top: y + this.height/4}, {left: x + k * this.width + this.width/4 , top: y + this.height + this.height/4})
    		 // 다음 노드 작업하기
    		 console.log(turn.subpro[k]);
    		 
    		 this.whilefunc(turn.subpro[k], current_x, current_y);  
             current_x++;
    	 }

    	 current_x--;
      }
   
  }
  
  
  
  
  
  
 
  
  
  
  
  
  
  
 



// ajax 관련

	ajaxmethod = (link, data, act) => {
		
		
		
		// 스프링 시큐리티 관련
		var header = $("meta[name='_csrf_header']").attr('content');
		var token = $("meta[name='_csrf']").attr('content');
		
   		$.ajax({
   			type : "POST",
   			url : "/view/" + link,
   			data : data,
   			beforeSend: function(xhr){
   			  if(token && header) {
   				  //console.log(header);
   				  //console.log(token);
   		       // xhr.setRequestHeader(header, token);
   			  } 
   		    },
   		    success : (res) => {
   				
   		    	// 211015 어떻게 처리할 것인지 고민할 것
   		    	// 211015
   		    	// res는 있는데, 그림이 안 그려졌음
   		    	
   		    	
   		    	if(act){
   	   				act(res.processjson)
   		    	}
 
   			},
            error: function (jqXHR, textStatus, errorThrown)
            {
                   console.log(errorThrown + " " + textStatus);
            }
   		})		
	}

	
	
	
	listmake = (name) => {
		
		
		// 스프링 시큐리티 관련
		var header = $("meta[name='_csrf_header']").attr('content');
		var token = $("meta[name='_csrf']").attr('content');
		
		
		
		// ajax 관련
   		$.ajax({
   			type : "POST",
   			url : "listmake",
   			beforeSend: function(xhr){
   			  if(token && header) {
   		       xhr.setRequestHeader(header, token);
   			  } 
   		    },
   		    success : (res) => {
   		    	
   		    	// 가능한 목록을 여기서 받고, listmanage에다가 끼울 것임 
   		    	
   		    	var listmanage = document.getElementById('listmanage');
		    	
   	            for(var i in res){
   	            	
   	            	var text = res[i];
   	            	text = text.replace("^{", "");
   	            	text = text.replace("@{", "");
   	            	text = text.replace("}^", "");
   	            	text = text.replace("}@", "");
   	            	
   	            	var div1 = this.makediv(text, {"class": "detailcontent"});
   	            	listmanage.appendChild(div1);
   	            	div1.style.background = "none";
   	            	
   	            	// 각 div1들에게 클릭 이벤트 등록하기
   	            	this.additem2(div1, this.totaldata[i]);		
   	            }	    	
 
                
 
   			},
            error: function (jqXHR, textStatus, errorThrown)
            {
                   console.log(errorThrown + " " + textStatus);
            }
   		})		
	}	
	
	listmake2 = (name) => {
		
		console.log(this.totaldata);
	    	var listmanage = document.getElementById('listmanage');
	    	var first = "";
	    	// 부모노드 찾아서 먼저 넣기
            for(var str in this.totaldata){
            	
            	if(this.totaldata[str].superpro.length == 0){  // 완전부모일때만 표시
            	    first = str;
                }
            }
            
            // 자식노드 이제 진행하기
            var arr = [first];
            
            while(arr.length > 0){
	          
	          var newarr = [];
              for(var pro in arr){
	            
                	var text = arr[pro];
                	var text1 = text;
                	//console.log(text1);
                	text = text.replace("^{", "");
                	text = text.replace("@{", "");
                	text = text.replace("}^", "");
                	text = text.replace("}@", "");
                	
                	var div1 = this.makediv(text, {"class": "detailcontent"});
                	listmanage.appendChild(div1);
                	
                	// 각 div1들에게 클릭 이벤트 등록하기
                	this.additem2(div1, text);		
                	
	                for(var subpro in this.totaldata[arr[pro]].subpro){
		                //console.log(this.totaldata[text1].subpro[subpro]);
		                
		                newarr.push(this.totaldata[arr[pro]].subpro[subpro]);
	                }
                 
	  
              }
              
	             arr = newarr;
	             
            }
		    	
	}		
	

	poolmake2 = (name) => {

                // 그림도 그릴 것
                this.start(name);
                
                //  그 후 글짜도 쓸 것
                this.func(name);
                
	}
	
	poolmake = (name) => {
		
		
		// 스프링 시큐리티 관련
		var header = $("meta[name='_csrf_header']").attr('content');
		var token = $("meta[name='_csrf']").attr('content');
		
		
		
		// ajax 관련
   		$.ajax({
   			type : "POST",
   			data : name,
   			url : "poolmake",
   			beforeSend: function(xhr){
   			  if(token && header) {
   		         xhr.setRequestHeader(header, token);
   			  } 
   		    },
   		    success : (res) => {
   		    	
   		    	

                this.start(res.processjson);
                this.datapool = res;
   				
 
   			},
            error: function (jqXHR, textStatus, errorThrown)
            {
                   console.log(errorThrown + " " + textStatus);
            }
   		})		
	}	
	
// tag 관련

     //^^ viex에 관한 함수
    maketable = () => {
    	
        // processlist 반영하기 
        for(var i = 0; i < this.tablesize.height;i++){
    	    this.tablearr[i] = {}
    	    var subdiv = this.maketrtd(this.tablearr[i]);
     		this.table.appendChild(subdiv)
    	}
        
    }

    maketable2 = () => {
        // processlist 반영하기 
    	for(var i = 0; i < this.table2size.height;i++){
    	    this.table2arr[i] = {}
    	    var subdiv = this.maketrtd(this.table2arr[i]);
     		this.table2.appendChild(subdiv)
    	}
    }
    
    //
    // 여기서부터 문장작업
	//
	//

		
    makesentence(key, data, parameter){
		
    	
		var text = data[key][parameter];
		
		if(text == null){
			return;
		}
		
		var re = /\^\{[가-힣0-9\_]+\}\^/g;
		var textarr = text.split(re);
		
		
		
		var re = /(?<=\^\{)[가-힣0-9]+(?=\}\^)/g;
		//var re = /\^\{[가-힣0-9]+\}\^/g;
		var optionarr = text.match(re)

		// @ 뽑아내기
		var re2 = /(?<=\@\{)[가-힣0-9\_]+(?=\}\@)/g;
		var optionarr2 = text.match(re2);
		
		this.maintextdiv = document.createElement("div");
		this.maintextdiv.setAttribute("class", "maintext");
		
		this.maintext = "";
		
		if(optionarr){
			// 정상적인 경우
			// 순서가 엉망이 되어서, 재귀함수로 처리함
			
			this.nextloop(key, optionarr, textarr, data, 0, parameter);
		}else{
			// 옵션이 전혀없는 경우에는 바로 내용 띄울 것
    		var div1 = this.makediv(textarr[0]);
			this.maintextdiv.appendChild(div1);
			this.maintext += textarr[0];
		}
		
	}
    
    
    nextloop(key, optionarr, textarr, data, i, parameter){
		
		// makediv 만들고, 그 다음 maketext, makeselect 등 만들것
		var div1 = this.makediv(textarr[i]);
		this.manageinnertext[parameter + "_" + i] = div1; // this.managetext[0] = div1, this.managetext[1] ~~ 에 순차적으로 텍스트가 들어있으므로
		this.managefirsttext[parameter + "_" + i] = textarr[i]; // 이것만 쭈욱 갈아끼우면 됨. 
		this.maintextdiv.appendChild(div1); 
		
		this.maintext += textarr[i];
		
		
		if(i < optionarr.length){
			var val = optionarr[i]
			
			// select인지 text 등인지로 구분받아서, 함수실행
			
			var opt = data[key]['processoption'][val]["showing"]
			
			// 앞으로 selectplus 등 구현해 가야함
			if(opt == null  || opt == "sentenceplus"){
				opt = "select"
			}
			var div2 = this['make' + opt](data[key]['processoption'][val], val);
			
			this.managearr[optionarr[i]] = div2; // 이런 꼴을 통해서 관리해서, @에다가 집어씌우려고, this.managearr["담당자"] = <select />
			                                     // 위의 construct에 상세한 설명을 써놓았으니 참고할 것
			
			                                     
			this.maintextarr[key][val] = div2;
			this.maintextdiv.appendChild(div2);
			
			this.maintext += data[key]['processoption'][val].result;
		}

    	
		if(i + 1 < textarr.length){
			var i1 = i + 1;
			this.nextloop(key, optionarr, textarr, data, i1, parameter);
		}
		
		
    }
    
    
    makediv(value, style){
    	
    	
    	var field = document.createElement("div");
    	
    	// style은 json 형식으로 받을 것
    	if(style){
    		for(var i in style){
    			field.setAttribute(i, style[i]);
    		}
    	}
    	
    	field.innerText = value
    	return field;    	
    }


    makeselect(data, val){
       
       // option1 ~ option5 이것을 for문 5까지로 수정할 것
 	   var field = document.createElement("select");
 	   field.setAttribute("name", val);

       // 없음 집어넣기, 다만 나중에 없음을 이것을 일부분에 대해서만 적용하고 싶으면 이를 옵션으로 할지 고민하자 
       
 	   var option = document.createElement("option");
   	   //option.innerText = "없음";
       //field.appendChild(option);
 	   
 	   for(var j = 1; j <= 5; j++){
 		   
 		   if(data['option' + j] != ""){
 	       	   var option = document.createElement("option");
           	   option.innerText = data['option' + j];
               field.appendChild(option);
 		   }else{
 			   break
 		   }

 	   }
 	    
 	   
 	   field.addEventListener('change',(me)=>{
 		  
 		  this.changewordtable(1);
 	   })
 	   
		return field;    	
    }
    
    
    makeselectplus(data, val){
       
       var div1 = document.createElement("div");
       
       // 먼저는 select를 만들기
   	   var field = this.makeselect(data, val);
   	      
   	   // 뒤에 추가버튼 만들기
       var button = document.createElement("input");
       button.setAttribute('type', "button");
       button.value = "추가"
       
       div1.appendChild(field);
       div1.appendChild(button);
       
       // 버튼에 다른 select 추가기능 부여
       this.additem_plus(button, data, val, div1, button);
       
       return div1;
  	   
    }
    
    additem_plus(item, data, val, div1, button){

    	item.addEventListener('click',(me)=>{
        	
        	// 먼저 select의 개수가 최대개수를 초과했는지 확인하고 최대개수를 초과하지 않았으면 추가할 것

            var tags = document.getElementsByName(val);
            var num = 0;
      	    for(var j = 1; j <= 5; j++){
     		   if(data['option' + j] != ""){
     			   num ++
      		   }else{
     			   break
     		   }
       	    }
            
      	    if(tags.length < num){

      	    	// 단어 추가
      	    	var divs = document.createElement("div");
      	    	divs.innerText = ", ";
      	    	
            	// select 추가
      	    	var field = this.makeselect(data, val);
      	    	divs.appendChild(field);
            	
             	// 삭제 버튼 추가
                var button_remove = document.createElement("input");
                button_remove.setAttribute('type', "button");
                button_remove.value = "X"
                divs.appendChild(button_remove);
                div1.insertBefore(divs, button);
                
                // 삭제 버튼에 삭제 기능 추가하기
                this.additem_remove(button_remove, divs);
       	    }else{
      	    	alert("최대 개수가 초과되었습니다.")
      	    }
        
        })
    }
    

    additem_remove(item, parentitem){
    	
    	item.addEventListener('click',(me) => {
    		parentitem.parentNode.removeChild(parentitem); 
    	})
    
    }
    
    
    maketext(value){
    	
 		var field = document.createElement("input");
		field.setAttribute("type", "text");
		//field.setAttribute("name", data);
		field.setAttribute("value", "");
		
		return field;    	
    }
	
	//
	//
	// 문장관련 끝
	//
	
	changewordtable(opt){
		
		// 단어 갈아끼우면서 sameword 테스트까지 같이 하기
		for(var i in this.managefirsttext){
			var text = this.managefirsttext[i];
			
			
			for(var j in this.managearr){
				
				if(this.samewordhash[j]){
					var word = this.samewordtest(j, this.managearr[j].value, this.currentkey, opt);
					if(word != null){
						this.managearr[j].value = word;

					}
				}
				
				
				text = text.replace("@{" + j + "}@", this.managearr[j].value)
				this.manageinnertext[i].innerText = text;
				this.maintext = this.maintext.replace("@{" + j + "}@", this.managearr[j].value);
			}
		}
		
		// sameword 세팅해버리기
		this.samewordsetting();	
		
	}
	


    inputtable_only = (arr, tablearr, parameter1, parameter2, size, word) => {

    	var data = this.totaldata;
    	// 먼저 테이블 지우기
    	for(var i = 1; i < size; i++){
    		tablearr[i][0].innerText = "";
    		tablearr[i][1].innerText = "";
    	}
    	
    	// 테이블 다시 채우기
    	
        var rowval = Math.min(arr.length, this.tablesize.height - 1);
    	
        if(data == null){
        	data = arr
        }
        
        var r = 0;  
    	for(var i in arr){
                
                for(var num = 1; num <= this.tablesize.height; num++){
                	r += 1;
                	
                	
                	if(data[i][parameter1 + num]){
	                    
                		if(data[i][parameter1 + num] != ""){
                            
                            this.makesentence(i, data, parameter1 + num);
                            this.changewordtable(0);
                            tablearr[r][0].innerText = this.maintext;
                            this.makesentence(i, data, parameter2 + num);
                            this.changewordtable(0);
                            
                            tablearr[r][1].innerText = this.maintext;
                            
                            var processoption = this.totaldata[this.currentkey].processoption

       	        	        for(var k = 0; k <= 1; k++){
       	        	        	
                                this.tablearr[r][k].querySelectorAll('select').forEach((elem, index) => {
           	        	           var name = elem.getAttribute("name");
           	        	           var result
           	        	           if(name in processoption){
           	        	        	   if(processoption[name].result){
           	        	        		   elem.value = processoption[name].result
           	        	        	   }
           	        	           }
            	        	    })
       	        	        }
                            
                            var tag = this.makelabel2(i, data, num, parameter1, parameter2, r, tablearr, word);
                   	        
                            /* 3열을 일단은 지웠으므로 이것을 뺏음. 유동적이므로 지우지는 말 것 
                            for(var k = tablearr[r][2].childNodes.length - 1; k >= 0; k--){
                            	tablearr[r][2].removeChild(tablearr[r][2].childNodes[k]); 
             	            }
                            
                            if(tag){
                                tablearr[r][2].appendChild(tag);
                            }
                            */

                            
                		}else{
                			this[word] = num
                			break
                		}
                	}
                }
               	
                //this.additem5(this.tablearr[r][2], data[i]); 현재는 역할이 없는 것 같음
                if(r > rowval){
                	break;
                }
         }
         
     	
    }

	
    inputtable = (arr, tablearr, parameter1, parameter2, size, word) => {

    	var data = this.totaldata;
    	// 먼저 테이블 지우기
    	for(var i = 1; i < size; i++){
    		tablearr[i][0].innerText = "";
    		tablearr[i][1].innerText = "";
    	}
    	
    	// 테이블 다시 채우기
    	
        var rowval = Math.min(arr.length, this.tablesize.height - 1);
    	
        if(data == null){
        	data = arr
        }
        
        var r = 0;  
    	for(var i in arr){
                
                for(var num = 1; num <= this.tablesize.height; num++){
                	r += 1;
                	if(parameter1 + num in data[i]){
                		if(data[i][parameter1 + num]){
                            
                            this.makesentence(i, data, parameter1 + num);
                            tablearr[r][0].appendChild(this.maintextdiv);
                            this.makesentence(i, data, parameter2 + num);
                            tablearr[r][1].appendChild(this.maintextdiv);

                            // tablearr[r][0]의 답이 현재 정해져 있으면 집어넣기
                            // tablearr[r][1]의 답이 현재 정해져 있으면 집어넣기
                            
                            
                            var processoption = this.totaldata[this.currentkey].processoption

       	        	        for(var k = 0; k <= 1; k++){
       	        	        	
                                this.tablearr[r][k].querySelectorAll('select').forEach((elem, index) => {
           	        	           var name = elem.getAttribute("name");
           	        	           var result
           	        	           if(name in processoption){
           	        	        	   if(processoption[name].result){
           	        	        		   elem.value = processoption[name].result
           	        	        	   }
           	        	           }
            	        	    })
       	        	        }
                            
                            var tag = this.makelabel2(i, data, num, parameter1, parameter2, r, tablearr, word);
                   	        
                            /* 3열을 일단은 지웠으므로 이것을 뺏음. 유동적이므로 지우지는 말 것 
                            for(var k = tablearr[r][2].childNodes.length - 1; k >= 0; k--){
                            	tablearr[r][2].removeChild(tablearr[r][2].childNodes[k]); 
             	            }
                            
                            if(tag){
                                tablearr[r][2].appendChild(tag);
                            }
                            */

                            
                		}else{
                			this[word] = num
                			break
                		}
                	}
                }
               	
                //this.additem5(this.tablearr[r][2], data[i]); 현재는 역할이 없는 것 같음
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

    maketrtd = (arr) => {
       
     	var div = document.createElement("tr")

     	div.style = "height : 20px";

     	for(var i = 0; i < this.tablesize.width;i++){
    	   var subdiv = document.createElement("td");

    	   arr[i] = subdiv;
    	   if(i == 0){
        	   subdiv.setAttribute("width","30%");
    	   }else if(i == 1){
    		   subdiv.setAttribute("width","60%");
    	   }else if(i == 2){
    		   subdiv.setAttribute("width","10%");
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
           
           item[i][2].appendChild(this.selectlabel[i])   
        }
    }

    // sentenceplus를 구현하기 위해서 만들었음
    
    makelabel2(name, data, num, parameter1, parameter2, row, tablearr, word){
    	
    	var val = data[name]['processplus' + num];
    	
    	//객관식을 위해서, process1 ~ 5에서 필수적인 것은 1, 객관식 선택가능한 것은 2, sentenceplus의 경우에는 3 등으로 구분을 위한 열을 만들자. 
    	// Sentenceplus는 프로세스에 한번 컨트롤에 한번만 사용하도록 하자.
    	
    	if(val == 1){
    		// 여기서는 처리할 게 없음

    	}else if(val == 2){
    	       var check = document.createElement("input");
    	       check.setAttribute('type', "checkbox");
               return check;
    	
    	}else if(val == 3){
    	       var button = document.createElement("input");
    	       button.setAttribute('type', "button");
    	       button.value = "추가";
    	       
    	       
    	       // 이것을 클릭하면 그대로 복제해야 함
    	       button.addEventListener('click',(me)=>{
      	    	      // row 아래 것들은 한칸씩 밑으로 내릴것
      	    	      var count = this[word]
      	    	      var subdiv = this.maketrtd(tablearr[count]);
      	    	      
      	    	      
      	    	      tablearr[num][0].parentNode.parentElement.insertBefore(subdiv, tablearr[num][0].parentNode.nextElementSibling);
                      this.makesentence(name, data, parameter1 + num);
      	    	      tablearr[count][0].appendChild(this.maintextdiv);
                      this.makesentence(name, data, parameter2 + num);
                      tablearr[count][1].appendChild(this.maintextdiv);

    	        	  // sentencearr 갯수를 컨트롤 하고, 삭제버튼을 누를때 무조건 마지막 것만 삭제를 하도록 하기위해서 관리하는 배열 저장
    				  this.sentencecountarr.push(count);
                      
             	      var button2 = document.createElement("input");
        	          button2.setAttribute('type', "button");
        	          button2.value = "삭제";
        	          tablearr[count][2].appendChild(button2);
        	          this[word]++


        	          //tablearr[count][0] 및 tablearr[count][1]의 tag 중에서 name이 팀1이 있으면 이를 팀2 등으로 바꿔줄 것
        	          
        	          this.tablearr[count][0].querySelectorAll('select').forEach((elem, index) => {
        	        	  var name = elem.getAttribute("name");
        	        	  var pos = this.sentencecountarr.length + 1
        	        	  var re = /(?<=[가-힣]+)1$/g;
           	        	  elem.setAttribute("name", name.replace(re, pos));
                      })
        	          this.tablearr[count][1].querySelectorAll('select').forEach((elem, index) => {
        	        	  var name = elem.getAttribute("name");
        	        	  var pos = this.sentencecountarr.length + 1
        	        	  var re = /(?<=[가-힣]+)1$/g;
           	        	  elem.setAttribute("name", name.replace(re, pos));
                    	  
                      })

                      
           	          
           	          // 서버에 추가할 processexplain 등을 넘겨주기 위해서 sentencearr에 저장하기
           	          // 먼저 들어갈 위치 찾기
           	          for(var i = 1; i <= this.processcount; i++){
           	        	  
           	        	  // pluscount가 1에 해당하는 게 나오면 그것이 sentenceplus의 기준이 되는 것임  
           	        	  
           	        	  
           	              // 지금 현재는 pluscount에 숫자 들어가있는 것이 없기 때문에, 일단 뽑히도록 하기 위해서 아래처럼 함
           	              // 나중에는 이것으로 바꿀 것 if(data[name][parameter1.replace('name', 'pluscount') + i] == 1){
           	        	  if(data[name][parameter1.replace('name', 'pluscount') + i] == 1){
            	        	  var sentence1 = data[name][parameter1 + i];
           	        		  var sentence2 = data[name][parameter2 + i];
           	        	  }	
           	        	  
           	        	  // 집어넣을 곳 위치가 파악되면 집어넣기 // 위에 조건문부터 무조건 나오는구조므로 순환문 하나 더 파지않고, 그대로 수행하였음
           	        	  // 팀1 등을 팀2로 바꾸는 것 수행함
           	        	  if(!data[name][parameter2 + i]){
           	        		 var pos = this.sentencecountarr.length + 1
           	        		 
           	        		 var re = /(?<=\^\{[가-힣]+)1(?=\}\^)/g;
           	        		 data[name][parameter2 + i] = sentence2.replace(re, pos)
           	        		 data[name][parameter1 + i] = sentence1;
           	        		 data[name][parameter1.replace('name', 'pluscount') + i] = pos;
           	        		 data[name][parameter1.replace('name', 'plus') + i] = 3;
           	        		 // sentencearr를 저장하려고 하였으나, 그냥 쓰지 않고, 서버에서, processpluscount 등이 있으면 그것으로 저장을 하게 할 것
           	        		 
           	        		 var option = data[name]['processoption']
           	        		 var re = /(?<=[가-힣]+)1$/g;
           	        		 for(var j in option){
           	        			var optionarr = j.match(re);
           	        			if(optionarr){
           	        				var optionname = j.replace(re, pos);
           	        				
           	        				option[optionname] = JSON.parse(JSON.stringify(option[j]));
           	        				option[optionname]['realname'] = optionname;
           	        			}

           	        		 }
           	        		 
           	        		 
           	        		 break
           	        	  }
           	        	            	        	 
           	          }
           	        	
           	          // 삭제부분
        	          // 이거 누르면 다시 원래대로 삭제하고 돌아와야 함
           	          button2.addEventListener('click',(me)=>{

           	        	 var pos = this.sentencecountarr.pop();
           	        	 this[word]--;
           	        	 tablearr[pos][0].parentNode.parentNode.removeChild(tablearr[pos][0].parentNode)
          	        	 
           	        	 
           	        	 for(var i = 1; i <= this.processcount; i++){
          	        	  // 삭제할 위치가 파악되면 삭제하기
           	        	  if(data[name][parameter1.replace('name', 'pluscount') + i] == pos){
           	        		 
           	        		 
           	        		
           	        		 var re = /(?<=\^\{[가-힣]+)1(?=\}\^)/g;
           	        		 data[name][parameter2 + i] = null;
           	        		 data[name][parameter1 + i] = null;
           	        		 data[name][parameter1.replace('name', 'pluscount') + i] = null;
           	        		 data[name][parameter1.replace('name', 'plus') + i] = null;
           	        
           	        		 // sentencearr를 저장하려고 하였으나, 그냥 쓰지 않고, 서버에서, processpluscount 등이 있으면 그것으로 저장을 하게 할 것
           	        		 var re = "(?<=[가-힣]+)" + pos + "$";
           	        		 var option = data[name]['processoption']
           	        		 
           	        		 for(var j in option){
           	        			var optionarr = j.match(re);
           	        			if(optionarr){
                                    
           	        				delete(option[j])
           	        			}

           	        		 }
           	        		 
           	        		 
           	        		 break
           	        	   }
           	        	            	        	 
           	             }
           	        	 
           	        	 

           	        	 
           	          })           	        	  
           	        	  
           	          
    	        })
    	       
    	       return button;
    	}
    }

    
    
    additem2 = (item, text) => {
        item.addEventListener('click',(me)=>{
        	this.poolmake2(text);
        	listmanage.childNodes.forEach((s) => s.style.background = "none");
        	item.style.background = "blue";
        })
    }
 /*   
    additem3 = (item) => {

    	item.addEventListener('click',(me)=>{
             
             var arr_return = {}
             var real_return = {}
             // maintextarr는 tag를 그대로 들고 있기때문에, 서버로 넘기기 전에 이것을 값으로 전환함

             this.maintextresult = {}
             for(var i in this.maintextarr){
            	 this.maintextresult[i] = {};
            	
            	 for(var j in this.maintextarr[i]){
            		 if(this.maintextarr[i][j].tagName == "DIV"){
                         // selectplus 값이 여러개 들어오기 때문에, 이렇게 나누어졌음
                         var tags = document.getElementsByName(j);
                         var arr = []
                         tags.forEach((elem, index) => {
                        	  arr.push(elem.value);
                         })
                         
                         // 211202 나중에 arr이 값이 같으면 안되니까 검증하는 문구 추가해야함
             			 this.totalarr[i]['processoption'][j]['result_value'] = arr;
                        // 211202 또한 서버에서 result_val과 관련하여 arr도 받아야 하므로, 서버쪽도 함께 수정이 이루어져야 함 
            		 }else{
            			 // select나 text의 경우
                		 this.maintextresult[i][j] = this.maintextarr[i][j].value;
                		 this.totalarr[i]['processoption'][j]['result_value'] = this.maintextarr[i][j].value 
            		 }
            	 }
            	 
            	 arr_return =  this.totalarr[i];
            	 
            	 // processoption 배열로 전환해서 넘기기
            	 var temp = {}
            	 var num = 0
            	 for(var ia in arr_return['processoption']){
            	 	 for(var ib in arr_return['processoption'][ia]){
            			 real_return['processoption[' + num + '].' + ib] = arr_return['processoption'][ia][ib];
            		 }
                     num += 1
            	 }    
            	 
             }
             
             
             //this.ajaxmethod("maintext", real_return)
        })

    }

    additem4 = (item, item2) => {
        item.addEventListener('click',() => {
        	  
        	  var temp = this.Palette.model.nodeDataArray[0] 
        	  var obj = {key : item2.innerText,  shape: temp.shape, wid: temp.wid, 
                  hei: temp.hei, color: temp.color, type: temp.type,
                  realkey: temp.realkey};
        	 
        	  this.Palette.model = new go.TreeModel([obj])
              
        })

    }    

    additem5 = (item, arr) => {
        item.addEventListener('click',(me)=>{
             
             this.inputtable2(arr);
        })
    }


    addlisten = (item) => {
        item.addEventListener('click',()=>{
             
        	this.myworkradio.forEach((node) => {
            	    if(node.checked)  {
            	       
            	       var data = {subprocess: node.value};
                       this.ajaxmethod(data, this.start);
                	    
            	    }
         	  }) 
   
             
             
        })
    }
    */
    // 211022 possetting2, nodedatasetting_graph2 등 플로우차트까지 하지 않고, 함수를 쉽게 바꿀것. 복잡한 것은 gojs 거기서할 것임
    // 211022 gojo_work에서 업무용 선택으로 생각했던 것 이것저것 할 것임. 알았지?
    
    start = (name) => {
        
        
    	// 시작점 찾기
    	var pos = 1
    	var first = []
        this.currentkey = name;
        for(var str in this.totaldata){
	        if(this.totaldata[str].superpro.length == 0){
		       first.push(this.totaldata[str]);
	        }
        }

    	
  	    // 초기화하기
  	    this.canvasmaking.clear();

        this.nodedatasetting_func(first);
        
    	
    }
    
    // div에 관한 것
    
  




}