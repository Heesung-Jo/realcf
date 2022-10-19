package com.repository;



import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.entity_internal.member;

@Repository
public class memberdatarepository  {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public member save(member pro) {
    	
    	if (pro.getId() == null) {
	    	em.persist(pro);
	    } else {
	    	em.merge(pro);
	    }
    	em.flush();
    	return pro;
    }
    
    public member findByEmail(String name) {
    	
       try {
           return em.createQuery("select m from member m where m.email = :name", member.class)
                   .setParameter("name", name)
                   .getResultList().get(0);
       }catch(IndexOutOfBoundsException e) {
    	   System.out.println("IndexOutOfBoundsException 에러 발생");
    	   return null;
       }
       
    }

    public member getOne(int id) {
    	return em.find(member.class, id);
    }
    
    
    public List<member> findAll() {
        return em.createQuery("select m from member m", member.class)
                             .getResultList();
    }
    
} // The End...
