package com.repository;

import com.entity.memberdata;
import com.entity.memberdata;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class memberdatarepository  {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public memberdata save(memberdata pro) {
    	
    	if (pro.getId() == null) {
	    	em.persist(pro);
	    } else {
	    	em.merge(pro);
	    }
    	em.flush();
    	return pro;
    }
    
    public memberdata findByEmail(String name) {
    	
       try {
           return em.createQuery("select m from memberdata m where m.email = :name", memberdata.class)
                   .setParameter("name", name)
                   .getResultList().get(0);
       }catch(IndexOutOfBoundsException e) {
    	   System.out.println("IndexOutOfBoundsException 에러 발생");
    	   return null;
       }
       
    }

    public memberdata getOne(int id) {
    	return em.find(memberdata.class, id);
    }
    
    
    public List<memberdata> findAll() {
        return em.createQuery("select m from memberdata m", memberdata.class)
                             .getResultList();
    }
    
} // The End...
