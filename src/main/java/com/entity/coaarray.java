package com.entity; 

 import javax.persistence.DiscriminatorValue; 
import javax.persistence.Entity; 
import java.util.*;

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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import org.springframework.stereotype.*;

import com.entity_internal.answerdeletelist;
import com.entity_internal.answerstructure;
import com.entity_internal.basequestion;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

import javax.inject.Inject;

@Getter
@Setter
@Entity
public class coaarray { 

	 @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column
	 private Long id;	
	 
	 private String detailname; 
	 private String resultname;
     private String middlecategory;
     private String largecategory;
	 //private int order;
	 private ArrayList<String> list_sort = new ArrayList<>();
	 
	 private String bspl;
	 
	 public coaarray() {

	 }
     


}