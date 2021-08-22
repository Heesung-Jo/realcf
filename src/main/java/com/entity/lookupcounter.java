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
import java.time.LocalDateTime;

@Entity
public class lookupcounter { 

	
	 @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column
	 private Long id;	
	
	 private String name; // 사람들이 조회하는 메서드 등 명
	 private int count;    // 카운팅 된 숫자
	 private String timecheck;  // 마지막 카운팅 된 날짜
	 
	 
	 public lookupcounter() {
		 
	 }

	 
	 public void setcount(int val) {
		 this.count = val;
	 }
	 
	 public int getcount() {
		 return this.count;
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

     public void settimecheck(String x){ 
         this.timecheck = x; 
     }
     public String gettimecheck(){ 
         return timecheck; 
     }


     
}