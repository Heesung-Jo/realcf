package com.entity_internal; 

 import javax.persistence.DiscriminatorValue; 
import javax.persistence.Entity; 

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
import javax.persistence.Table;

import org.springframework.stereotype.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

@Getter
@Setter
@Entity
public class deletelist { 

	 @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column
	 private Long id;	

	 private String answername;  // 네, 아니오 등의 답변
	 private String processname;   // 지워야할 프로세스 이름

	 @ManyToOne
     @JoinColumn(name = "basequestion_id")
     private basequestion basequestion;

	 /* 현재는 딱히 필요없어보임(211207)
	 @OneToOne
     @JoinColumn(name = "processdata_id")
     private processdata processdata;
     */
	 
	 public deletelist() {
		 
	 }
           


}