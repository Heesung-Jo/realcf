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

import javax.inject.Inject;

import java.io.Serializable;
import java.security.Principal;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
public class boarddata implements Serializable { 

	
	 @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column
	 private Long id;	
	
	 
	 //@DateTimeFormat(pattern = "yyyyMMddHH")
	 @NotNull
	 private String when;
	 
	 @NotEmpty(message = "제목이 기재되어야 합니다.")
	 private String name; // 회사이름을 의미
     
	 @NotEmpty(message = "내용이 기재되어야 합니다.")
	 private String detail;
	 
	 @NotNull
	 @ManyToOne(fetch = FetchType.EAGER)
	 @JoinColumn(name = "member_id")
	 private member member;
	
	 
	 public boarddata() {
		 
	 }

     public void setmember(member x){ 
         this.member = x; 
         member.addboarddata(this);
     }
     
     public member getmember(){ 
         return member; 
     }
           
	 
     public void setId(Long x){ 
         this.id = x; 
     }
     
     public Long getId(){ 
         return id; 
     }

     public void setwhen(String x){ 
         this.when = x; 
     }
     
     
     public String getwhen(){ 
         return when; 
     }
     
     
     
     public void setname(String x){ 
         this.name = x; 
     }
     
     
     public String getName(){ 
         return name; 
     }
     public void setdetail(String x){ 
         this.detail = x; 
     }
     
     public String getdetail(){ 
         return detail; 
     }
     private static final long serialVersionUID = 8433999509932007961L;
/*
     public void setcoagroupdata(HashSet<coagroupdata> act) {
    	 this.coagroupdata = act;
     }

     public void addcoagroupdata(coagroupdata act) {
    	 this.coagroupdata.add(act);
    	 act.setfinancialstatements(this);
     }

     public Set<coagroupdata> getcoagroupdata() {
    	 return coagroupdata;
     }
  */   
}