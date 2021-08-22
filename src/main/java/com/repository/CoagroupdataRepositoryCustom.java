package com.repository;




import com.entity.coagroupdata;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.Cacheable;

public interface CoagroupdataRepositoryCustom  {

	@Cacheable("realcfcache")
	List<coagroupdata> getprocessquery(String name1, String name2, String para1, String para2);
	@Cacheable("realcfcache")
	coagroupdata getprocessquery(ArrayList<String> names, ArrayList<String> paras);
	@Cacheable("realcfcache")
	List<Object[]> findmaxval(String coa);
	@Cacheable("realcfcache")
	List<Object[]> findmaxval(List<String> coa);
	@Cacheable("realcfcache")
	List<Object[]> findmaxval_name(List<String> coa, String opt) throws SQLException;
	@Cacheable("realcfcache")
	List<Object[]> findmaxval_business(List<String> coa, String opt) throws SQLException;
	@Cacheable("realcfcache")
	List<Object[]> findmaxval_all(List<String> coa, List<String> coa2, String opt) throws SQLException;
	@Cacheable("realcfcache")
	List<Object[]> findval_company(List<String> names, String opt) throws SQLException; 

} // The End...
