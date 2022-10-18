package com.repository_internal;

import com.entity_internal.processdata;
import com.entity_internal.Scopingdata;
import com.entity_internal.coadata;
import com.entity_internal.teamdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * User: HolyEyE
 * Date: 2013. 12. 3. Time: 오전 1:08
 */
@Repository
public interface ScopingdataRepository extends JpaRepository<Scopingdata, Integer> {

	List<Scopingdata> findByCoaname(String name);

}
