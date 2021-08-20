package com.repository;



import com.entity.coadata;
import com.entity.financialstatements;
import com.entity.coagroupdata;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


public interface CoagroupdataRepository extends JpaRepository<coagroupdata, Integer>, CoagroupdataRepositoryCustom {
	@Cacheable("realcfcache")
	List<coagroupdata> findByname(String name);
	@Cacheable("realcfcache")
	coagroupdata findByCompanyAndLevelAndName(String company, double level, String name);
} // The End...
