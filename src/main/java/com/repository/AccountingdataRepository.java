package com.repository;



import com.entity.accountingdata;
import com.entity.boarddata;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


public interface AccountingdataRepository extends JpaRepository<accountingdata, Integer> {

	
} // The End...
