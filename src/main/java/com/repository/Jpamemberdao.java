package com.repository;


import com.entity.Role;
import com.entity.memberdata;

import com.repository.memberdatarepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.repository.memberdao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A jdbc implementation of {@link memberdao}.
 *
 * @author Rob Winch
 * @author Mick Knutson
 *
 */
@Repository
public class Jpamemberdao implements memberdao {

    private static final Logger logger = LoggerFactory
            .getLogger(Jpamemberdao.class);

    // --- members ---
    @Autowired
    private memberdatarepository userRepository;

    // --- constructors ---

    @Autowired
    public Jpamemberdao(final memberdatarepository repository
                              ) {
        if (repository == null) {
            throw new IllegalArgumentException("repository cannot be null");
        }

        this.userRepository = repository;
    }

    // --- CalendarUserDao methods ---
    
   // @Override
    @Transactional(readOnly = true)
    public memberdata getUser(final int id) {

        return userRepository.getOne(id);
    }

    
  //  @Override
    @Transactional(readOnly = true)
    public memberdata findUserByEmail(final String email) {
    	
    	
        if (email == null) {
            throw new IllegalArgumentException("email cannot be null");
        }
        try {
        	
            return userRepository.findByEmail(email);
        } catch (EmptyResultDataAccessException notFound) {
        	System.out.println("여기가 에러니");
        	
            return null;
        }
    }

    
    // TODO FIXME Need to add a LIKE clause
   // @Override
    @Transactional(readOnly = true)
    public List<memberdata> findUsersByEmail(final String email) {
        if (email == null) {
            throw new IllegalArgumentException("email cannot be null");
        }
        if ("".equals(email)) {
            throw new IllegalArgumentException("email cannot be empty string");
        }
        return userRepository.findAll();
    }

    // @Override
    public int createUser(final memberdata userToAdd) {
        if (userToAdd == null) {
            throw new IllegalArgumentException("userToAdd cannot be null");
        }
        if (userToAdd.getId() != null) {
            //throw new IllegalArgumentException("userToAdd.getId() must be null when creating a "+member.class.getName());
        }

        
        Role role = Role.USER;
        userToAdd.setRole(role);

        memberdata result = userRepository.save(userToAdd);
        

        return result.getId();
    }

} // The End...
