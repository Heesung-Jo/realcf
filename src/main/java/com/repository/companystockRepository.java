package com.repository;




import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.query.Param;

import com.entity.companystock;
import com.entity.financialstatements;


public interface companystockRepository extends JpaRepository<companystock, Integer> {
	
	
	List<companystock> findByname(String name); 

	
	List<companystock> findByparentname(String name); 
	
	@Query("select m.name from companystock m group by m.name")
	List<String> findnames();

	/*
	@Cacheable("realcfcache")
	@Query("select m from companystock m left join fetch m.financialstatements where m.name = :name")
	List<companystock> findBynamefull(@Param("name") String name); 
	*/
} // The End...
