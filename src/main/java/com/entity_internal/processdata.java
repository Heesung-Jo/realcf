package com.entity_internal; 

 import javax.persistence.DiscriminatorValue; 
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

@Getter
@Setter
@Entity
@Table
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class processdata { 
	 
	
	 @JsonIgnore
	 @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column
	 private Long id;	
	
	 private String name; 

	 private String division;
	 private String currentgrade;
	 private String personcharge;
	 private Integer realgrade;
	 
	 
	 
	// private String sameword1;  // 한 플로우차트 안에서 공유되는 단어를 의미함
	// private String sameword2;
	// private String sameword3;
	 

	 private String realname; // 현재는 processname1을 realname으로 생각했는데, 없는 경우도 있으므로, 별도의 열을 추가하였음.
	 
	 @JsonIgnore
	 @OneToMany(mappedBy = "processdata", cascade = CascadeType.ALL)
	 private List<teamdata> teamdata = new ArrayList<>();

	 /* 나중에 부모 및 자식 노드 추가를 위한 것음
	 @OneToMany(mappedBy = "processdata", cascade = CascadeType.ALL)
	 private List<nodedata> nodedata = new ArrayList<>();
     
     */
	 
	 
	 @JsonIgnore
	 @OneToMany(mappedBy = "processdata", cascade = CascadeType.ALL)
	 private List<processoption> processoption = new ArrayList<>();
	 
	 @JsonIgnore
	 @OneToMany(mappedBy = "processdata", cascade = CascadeType.ALL)
	 private List<childnodedata> childnodedata = new ArrayList<>();

	 @JsonIgnore
	 @OneToMany(mappedBy = "processdata", cascade = CascadeType.ALL)
	 private List<parentnodedata> parentnodedata = new ArrayList<>();
	 
	 /*
	 @OneToOne(mappedBy = "processdata", cascade = CascadeType.ALL)
	 private coadata coadata; 
     */
	 
	 // childnode 관련
     public void setchildnodedata(List<childnodedata> act) {
    	 this.childnodedata = act;
     }

     public void addchildnodedata(childnodedata act) {
    	 this.childnodedata.add(act);
    	 act.setprocessdata(this);
     }

     public List<childnodedata> getchildnodedata() {
    	 return childnodedata;
     }

     // parentnode 관련
     public void setparentnodedata(List<parentnodedata> act) {
    	 this.parentnodedata = act;
     }

     public void addparentnodedata(parentnodedata act) {
    	 this.parentnodedata.add(act);
    	 act.setprocessdata(this);
     }

     public List<parentnodedata> getparentnodedata() {
    	 return parentnodedata;
     }	 
	 
     
     // 여기가 진짜임
     
     // 개략적인 차원의 변수들
     private String industry;
	 private String businesscode;
	 private String processname;
	 
	 private String mainprocess;
	 private String subprocesscode;
	 private String subprocess;
	 private String subprocessnumber;

     
	 // 세부 프로세스 관련 변수들

	 // 객관식을 위해서, process1 ~ 5에서 필수적인 것은 1, 객관식 선택가능한 것은 2, sentenceplus의 경우에는 3 등으로 구분을 위한 열을 만들자.
	// private Integer processplus1;
	// private Integer processplus2;
	// private Integer processplus3;
	// private Integer processplus4;
	// private Integer processplus5;
	// private Integer processplus6;
	// private Integer processplus7;
	 
	 /*
	 // sentenceplus일 경우에, 순번을 부여하기 위해서 만들었음
	 private Integer processpluscount1;
	 private Integer processpluscount2;
	 private Integer processpluscount3;
	 private Integer processpluscount4;
	 private Integer processpluscount5;
	 private Integer processpluscount6;
	 private Integer processpluscount7;
	 
	 */
	 private String processname1;
     private String processname2;
     private String processname3;
     private String processname4;
     private String processname5;
     private String processname6;
     private String processname7;
     
     private String processexplain1;
     private String processexplain2;
     private String processexplain3;
     private String processexplain4;
     private String processexplain5;
     private String processexplain6;
     private String processexplain7;

     
     // 통제관련 변수들
     /*
	 private Integer controlplus1;
	 private Integer controlplus2;
	 private Integer controlplus3;
	 private Integer controlplus4;
	 private Integer controlplus5;
	 private Integer controlplus6;
	 private Integer controlplus7;

	 // sentenceplus일 경우에, 순번을 부여하기 위해서 만들었음
	 private Integer controlpluscount1;
	 private Integer controlpluscount2;
	 private Integer controlpluscount3;
	 private Integer controlpluscount4;
	 private Integer controlpluscount5;
	 private Integer controlpluscount6;
	 private Integer controlpluscount7;

	 
     private String controlcode1;
     private String controlcode2;
     private String controlcode3;
     private String controlcode4;
     private String controlcode5;
     private String controlcode6;
     private String controlcode7;
*/
     
     private String controlname1;
     private String controlname2;
     private String controlname3;
     private String controlname4;
     private String controlname5;
     private String controlname6;
     private String controlname7;

     private String controlexplain1;
     private String controlexplain2;
     private String controlexplain3;
     private String controlexplain4;
     private String controlexplain5;
     private String controlexplain6;
     private String controlexplain7;

     /*
     // 후행 프로세스 관련 변수들
     private String processname1_after;
     private String processname2_after;
     private String processname3_after;
     private String processname4_after;

     private String processexplain1_after;
     private String processexplain2_after;
     private String processexplain3_after;
     private String processexplain4_after;
*/
     /*
     
     // 진짜 그 다음으로 이어는 후행 프로세스 관련 변수들
     @JsonIgnore
     @ManyToOne(fetch = FetchType.EAGER)
     @JoinColumn(name = "superpro_id")
     private processdata superpro;

     @JsonIgnore
     @OneToMany(mappedBy = "superpro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
     private List<processdata> subpro = new ArrayList<>();
     */
     
     @JsonIgnore
     @ManyToMany(fetch = FetchType.EAGER)
     @JoinTable(name = "superpro_subpro",
             joinColumns = @JoinColumn(name = "superpro_id"),
             inverseJoinColumns = @JoinColumn(name = "subpro_id"))
     private Set<processdata> subpro;

     @JsonIgnore
     @ManyToMany(fetch = FetchType.EAGER, mappedBy = "subpro")
     private Set<processdata> superpro;
     
     
     
     
     // 예외사항 관련 변수들
     private String exceptitem;
     private String exceptexplain;
     
     
     // 팀 관련. 위에 childtable에 있으나, 일단 작성된 엑셀파일에 따라서 String 변수로 반영. 나중에 수정할 것
     private String teamname;
     private String ControlOwner;
     
     private String proofname;
     
     
     
     
     // 아래는 과거 부분임
	 //210419 추가함 나중에 항목을 더 늘려야함
	 private String companyname;
	 private String processcode;
	 private String riskcode;
	 private String risk;
	 private String riskgrade;
	 private String detailprocess;
	 private String detailprocessname;
	 private String processexplain;
	 private String controlcode;
	 private String controlname;
	 private String controlexplain;

	 public void setbusinesscode(String x){
	        this.businesscode = x;
	}
	public String getbusinesscode(){
	        return businesscode;
	}
	public void setcompanyname(String x){
	        this.companyname = x;
	}
	public String getcompanyname(){
	        return companyname;
	}
	public void setprocesscode(String x){
	        this.processcode = x;
	}
	public String getprocesscode(){
	        return processcode;
	}
	public void setprocessname(String x){
	        this.processname = x;
	}
	public String getprocessname(){
	        return processname;
	}
	public void setsubprocesscode(String x){
	        this.subprocesscode = x;
	}
	public String getsubprocesscode(){
	        return subprocesscode;
	}
	public void setsubprocess(String x){
	        this.subprocess = x;
	}
	public String getsubprocess(){
	        return subprocess;
	}
	public void setriskcode(String x){
	        this.riskcode = x;
	}
	public String getriskcode(){
	        return riskcode;
	}
	public void setrisk(String x){
	        this.risk = x;
	}
	public String getrisk(){
	        return risk;
	}
	public void setriskgrade(String x){
	        this.riskgrade = x;
	}
	public String getriskgrade(){
	        return riskgrade;
	}
	public void setdetailprocess(String x){
	        this.detailprocess = x;
	}
	public String getdetailprocess(){
	        return detailprocess;
	}
	public void setdetailprocessname(String x){
	        this.detailprocessname = x;
	}
	public String getdetailprocessname(){
	        return detailprocessname;
	}
	public void setprocessexplain(String x){
	        this.processexplain = x;
	}
	public String getprocessexplain(){
	        return processexplain;
	}
	public void setcontrolcode(String x){
	        this.controlcode = x;
	}
	public String getcontrolcode(){
	        return controlcode;
	}
	public void setcontrolname(String x){
	        this.controlname = x;
	}
	public String getcontrolname(){
	        return controlname;
	}
	public void setcontrolexplain(String x){
	        this.controlexplain = x;
	}
	public String getcontrolexplain(){
	        return controlexplain;
	}

	 // 210419 여기까지 추가됨
	 
	 
	 
	 public processdata() {
		 
	 }
	 
	 
     public void setid(Long x){ 
         this.id = x; 
     }
     
     public Long getid(){ 
         return id; 
     }
     
     public void setname(String x){ 
         this.name = x; 
     }
     
     public String getname(){ 
         return name; 
     }

     public void setteamdata(List<teamdata> act) {
    	 this.teamdata = act;
     }

     public void addteamdata(teamdata act) {
    	 this.teamdata.add(act);
    	 act.setProcessdata(this);
     }

     public List<teamdata> getteamdata() {
    	 return teamdata;
     }

     /* 나중을 위한 것임 위 참고할 것
     public void setnodedata(List<nodedata> act) {
    	 this.nodedata = act;
     }

     public void addnodedata(nodedata act) {
    	 this.nodedata.add(act);
    	 act.setprocessdata(this);
     }

     public List<nodedata> getnodedata() {
    	 return nodedata;
     }
     
     
     
     public void setcoadata(coadata act) {
    	 this.coadata = act;
    	 act.setprocessdata(this);
     }
     
     public coadata getcoadata() {
    	 return coadata;
     }
     */

}