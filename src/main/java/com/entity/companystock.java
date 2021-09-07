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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
import java.util.Set;

import javax.inject.Inject;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class companystock { 

	
	 @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column
	 private Long id;	
	
	 private String name; // 자회사 이름을 말함 
	 private String realname; // inc 등 생략되지 않은 이름을 말함
	 private double percent;        // 지분율을 의미함 
	 
	 private String parentname;  // 모회사 이름임. 어차피 부모 엔티티를 조회해버리면 시간이 많이 걸릴 수도 있으므로
	                             // 부모이름만 필요할때는 이것으로 쉽게 끝내버리기 위하여
	 
	 @Column(nullable = true) 
	 private double val;        // 금액을 의미함 
	 
	 /* 현재는 굳이 join을 할 필요가 없을 것 같음. 일단 유보
	 @ManyToOne
     @JoinColumn(name = "financialstatements_id")
     private financialstatements financialstatements;
     */
	 
	 /*
	 @OneToMany(mappedBy = "coagroupdata", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	 private Set<coadata> coadata = new HashSet<>();
*/
	 

     
}