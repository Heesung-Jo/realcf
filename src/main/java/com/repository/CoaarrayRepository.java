package com.repository;



import com.entity.boarddata;
import com.entity.coaarray;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


public interface CoaarrayRepository extends JpaRepository<coaarray, Integer> {

	//@Query("select m from boarddata m left join fetch m.member where m.id = :id")
	//boarddata findByid(Long id);
	
} // The End...
