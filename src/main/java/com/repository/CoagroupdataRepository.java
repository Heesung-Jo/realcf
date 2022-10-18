package com.repository;




import com.entity.financialstatements;
import com.entity.coagroupdata;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


public interface CoagroupdataRepository extends JpaRepository<coagroupdata, Integer>, CoagroupdataRepositoryCustom {
	@Cacheable("realcfcache")
	List<coagroupdata> findByname(String name);
	
	
	//@Cacheable("realcfcache")
	//coagroupdata findByCompanyAndReallevelAndName(String company, double level, String name);
	
	
	 @Query("select m.business from coagroupdata m group by m.business ")
	 List<String> findbusiness();

	 @Query("select m.name from coagroupdata m group by m.name ")
	 List<String> findcoaname();

	 
} // The End...
