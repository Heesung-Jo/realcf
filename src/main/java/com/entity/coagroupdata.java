package com.entity; 

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
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.*;

import java.util.*;
import java.util.Set;

import javax.inject.Inject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
public class coagroupdata { 

	
	 @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column
	 private Long id;	
	 
	 private String name; // 계정과목 이름을 말함 // 세부분류 계정과목을 뜻함
	 
	 @Column(nullable = true) 
	 private double val;        // 금액을 의미함 
	 private double val_before;  // 직전년도 금액을 의미함
	 private int yeartime;          // 2020년 등 결산기간을 의미함
	 private String bspl;       // BS/IS/CF를 의미함
	 private String company;    // 사실 아래의 financialstatements이나, 여기서 간단히 string만으로 조회가능토록 구상

	 //private double reallevel;         // 레벨에 관련된 열 
	 private double ratio;         //비율과 관련된 열  
	 private String business;      //비지니스 종류와 관련된 열
	 //private String exceptcol;      //제외해야하는지 여부 확인하는 열
	 
	 //private double realnumber;        // 나중에 회사의 재무제표를 순서대로 보여줄수있도록 number 계정 설정함
	 //private String reportname; // 사업보고서 상의 계정과목 이름을 말함
	 
	 @ManyToOne
     @JoinColumn(name = "financialstatements_id")
     private financialstatements financialstatements;

	 /*
	 @OneToMany(mappedBy = "coagroupdata", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	 private Set<coadata> coadata = new HashSet<>();
*/
	 
	 public coagroupdata() {
		 
	 }
	 
	/* 
	 public void setrealnumber(double realnumber) {
		 this.realnumber = realnumber;
	 }
	 
	 public double getrealnumber() {
		 return realnumber;
	 }
	 public void setreportname(String val) {
		 this.reportname = val;
	 }
	 
	 public String getreportname() {
		 return reportname;
	 }

	 public void setexceptcol(String exceptcol) {
		 this.exceptcol = exceptcol;
	 }
	 
	 public String getexceptcol() {
		 return exceptcol;
	 }
*/
	 public void setbusiness(String business) {
		 this.business = business;
	 }
	 
	 public String getbusiness() {
		 return business;
	 }

	 public void setratio(double ratio) {
		 this.ratio = ratio;
	 }
	 
	 public double getratio() {
		 return ratio;
	 }
	 
	 /*
	 public void setreallevel(double reallevel) {
		 this.reallevel = reallevel;
	 }
	 
	 public double getreallevel() {
		 return reallevel;
	 }
*/
	 

	 public void setbspl(String val) {
		 this.bspl = val;
	 }
	 
	 public String getbspl() {
		 return bspl;
	 }
	 
	 public void setcompany(String val) {
		 this.company = val;
	 }
	 
	 public String getcompany() {
		 return company;
	 }
	 
	 
	 public void setyeartime(int year) {
		 this.yeartime = year;
	 }
	 
	 public int getyeartime() {
		 return yeartime;
	 }

	 
	 
	 public void setval(double val) {
		 this.val = val;
	 }

	 public void setval_before(double val) {
		 this.val_before = val;
	 }
	 
	 public double getval() {
		 return val;
	 }

	 public double getval_before() {
		 return val_before;
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

     public void setfinancialstatements(financialstatements x){ 
         this.financialstatements = x; 
     }
     
     public financialstatements getfinancialstatements(){ 
         return financialstatements; 
     }

     /*
     public void setcoadata(HashSet<coadata> act) {
    	 this.coadata = act;
     }

     public void addcoadata(coadata act) {
    	 this.coadata.add(act);
    	 act.setcoagroupdata(this);
     }

     public Set<coadata> getcoadata() {
    	 return coadata;
     }     
     */
     
}