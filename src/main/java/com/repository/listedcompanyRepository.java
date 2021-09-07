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
import com.entity.listedcompany;


public interface listedcompanyRepository extends JpaRepository<listedcompany, Integer> {
	
	
	List<listedcompany> findByname(String name); 


} // The End...
