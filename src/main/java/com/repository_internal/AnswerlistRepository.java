package com.repository_internal;

import com.entity_internal.processdata;
import com.entity_internal.answerlist;
import com.entity_internal.answerstructure;
import com.entity_internal.basequestion;
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
public interface AnswerlistRepository extends JpaRepository<answerlist, Integer> {

	 
	List<answerlist> findByMainprocessAndSubprocess1(String name1, String name2);
	List<answerlist> findByMainprocessAndSubprocess1AndStep(String name1, String name2, String name3);
	List<answerlist> findBySubprocess1(String name);
}



