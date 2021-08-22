package com.repository;


import com.entity.coagroupdata;
import com.entity.member;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.sql.SQLException;
@Component
public class CoagroupdataRepositoryImpl implements CoagroupdataRepositoryCustom {


    @PersistenceContext
    EntityManager em;
    
	@Transactional
	public List<coagroupdata> getprocessquery(String name1, String name2, String para1, String para2){
        
		List<coagroupdata> process = new ArrayList<coagroupdata>();
		
		StringBuilder jpql = new StringBuilder("select m from coadata m where ");
		List<String> criteria = new ArrayList<String>();
        		
		criteria.add("m." + name1 + " = :name1 and ");
		criteria.add("m." + name2 + " = :name2");
		jpql.append(criteria.get(0));
		jpql.append(criteria.get(1));
		
		process = em.createQuery(jpql.toString(), coagroupdata.class)
		        .setParameter("name1", para1)
		        .setParameter("name2", para2)
		        .getResultList();
        
		System.out.println("success");
		
		
		return process;
	}
	
	
	@Transactional
	public coagroupdata getprocessquery(ArrayList<String> names, ArrayList<String> paras){
        
		List<coagroupdata> process = new ArrayList<coagroupdata>();
		
		StringBuilder jpql = new StringBuilder("select m from coadata m where ");
		List<String> criteria = new ArrayList<String>();
        
		int num = 0;
		for(String i : names) {
			num += 1;
			
			if(num == names.size()) {
				criteria.add("m." + i + " = :name" + num);
			}else {
				criteria.add("m." + i + " = :name" + num + " and ");
			}
			jpql.append(criteria.get(criteria.size() - 1));
		}
		
		TypedQuery<coagroupdata> abc = em.createQuery(jpql.toString(), coagroupdata.class);
		
		num = 0;
		for(String i : paras) {
			num += 1;
			abc = abc.setParameter("name" + num, i);
		}
        
		System.out.println("success");
		
		return abc.getResultList().get(0);
	}	    



	@Transactional
	public List<Object[]> findmaxval(String name){

		List<Object[]> list = em.createQuery("select m.company, sum(m.val) as sums from coagroupdata m where m.name = :name and m.exceptcol = '포함' group by m.company order by sums desc")
				.setParameter("name", name).setMaxResults(2).getResultList();

		
		return list;
	}	

	
	@Transactional
	public List<Object[]> findmaxval(List<String> names){
        
		String word = "";
		for(String name : names) {
			word = word + "'" + name + "',"; 
		}
		
		word = word.substring(0, word.length() - 1);
		System.out.println(word);
		
		List<Object[]> list = em.createQuery("select m.company, sum(m.val) as sums from coagroupdata m where m.name in (" + word + " ) and m.exceptcol = '포함' group by m.company order by sums desc")
				.setMaxResults(5).getResultList();

		
		
		return list;
	}	

	// 회사만만 주워졌을때
	@Transactional
	public List<Object[]> findval_company(List<String> names, String opt) throws SQLException {
        
		// opt가 잘못 들어오는 것 대비함
		if(opt.equals("val") == true || opt.equals("ratio") == true) {
			
		}else {
			SQLException e = new SQLException();
			throw e;
		}
		
		
		String word = "";
		for(String name : names) {
			word = word + "'" + name + "',"; 
		}
	
		word = word.substring(0, word.length() - 1);
		System.out.println(word);

		
		List<Object[]> list = em.createQuery("select m.company, sum(m." + opt + ") as sums from coagroupdata m where m.name in (" + word + " ) and m.exceptcol = '포함' group by m.company order by sums desc")
				.setMaxResults(5).getResultList();
		
		return list;
	}		
	
	
	
	
	// coaname만 주워졌을때
	@Transactional
	public List<Object[]> findmaxval_name(List<String> names, String opt) throws SQLException {
        
		// opt가 잘못 들어오는 것 대비함
		if(opt.equals("val") == true || opt.equals("ratio") == true) {
			
		}else {
			SQLException e = new SQLException();
			throw e;
		}
		
		
		String word = "";
		for(String name : names) {
			word = word + "'" + name + "',"; 
		}
	
		word = word.substring(0, word.length() - 1);
		System.out.println(word);

		
		List<Object[]> list = em.createQuery("select m.company, sum(m." + opt + ") as sums from coagroupdata m where m.name in (" + word + " ) and m.exceptcol = '포함' group by m.company order by sums desc")
				.setMaxResults(5).getResultList();
		
		return list;
	}	
	
	// business만 주워졌을때
	@Transactional
	public List<Object[]> findmaxval_business(List<String> names, String opt) throws SQLException {
        
		// opt가 잘못 들어오는 것 대비함
		if(opt.equals("val") == true || opt.equals("ratio") == true) {
			
		}else {
			SQLException e = new SQLException();
			throw e;
		}
		
		
		String word = "";
		for(String name : names) {
			word = word + "'" + name + "',"; 
		}
	
		word = word.substring(0, word.length() - 1);
		System.out.println(word);

		
		List<Object[]> list = em.createQuery("select m.company, sum(m." + opt + ") as sums from coagroupdata m where m.business in (" + word + " ) and m.name = '자산총계' and m.exceptcol = '포함' group by m.company order by sums desc")
				.setMaxResults(5).getResultList();
		
		return list;
	}	
	
	// business, coaname모두 주워졌을때
	@Transactional
	public List<Object[]> findmaxval_all(List<String> names, List<String> businesses, String opt) throws SQLException {
        
		// opt가 잘못 들어오는 것 대비함
		if(opt.equals("val") == true || opt.equals("ratio") == true) {
			
		}else {
			SQLException e = new SQLException();
			throw e;
		}
		
		
		String word = "";
		for(String name : names) {
			word = word + "'" + name + "',"; 
		}
	
		word = word.substring(0, word.length() - 1);
		System.out.println(word);

		String word2 = "";
		for(String name : businesses) {
			word2 = word2 + "'" + name + "',"; 
		}
	
		word2 = word2.substring(0, word2.length() - 1);
		System.out.println(word2);
		
		List<Object[]> list = em.createQuery("select m.company, sum(m." + opt + ") as sums from coagroupdata m where m.name in (" + word + " ) and m.business in (" + word2 + ") and m.exceptcol = '포함' group by m.company order by sums desc")
				.setMaxResults(5).getResultList();

		
		
		return list;
	}		
} // The End...
