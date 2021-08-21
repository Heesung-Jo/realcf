package com.repository;



import com.entity.lookupcounter;
import com.entity.financialstatements;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.query.Param;

public interface lookupcounterRepository extends JpaRepository<lookupcounter, Integer> {
	
	Optional<lookupcounter> findByname(String name); 

} // The End...
