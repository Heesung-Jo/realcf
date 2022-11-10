package com.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.repository.memberdatarepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.repository.CoagroupdataRepository;
import com.repository.memberdao;
import com.entity.Role;

import com.entity_internal.member;
import com.entity.boarddata;
import com.repository.BoarddataRepository;

/**
 * A default implementation of {@link memberService} that delegates to {@link EventDao} and {@link memberdao}.
 *
 * @author Rob Winch
 * @author Mick Knutson
 *
 */
@Service
public class DefaultmemberService implements memberService {

    private static final Logger logger = LoggerFactory
            .getLogger(DefaultmemberService.class);

    
    private memberdao userDao;
  //  private final PasswordEncoder passwordEncoder;
    @Autowired
    private memberdatarepository userRepository;

    @Autowired
    private BoarddataRepository BoarddataRepository;
    
     
    
    @Autowired
    public DefaultmemberService(
                                  memberdao userDao,
                                  memberdatarepository userRepository,
                                  BoarddataRepository BoarddataRepository
                                  //final PasswordEncoder passwordEncoder
                                  ) {
        if (userDao == null) {
            throw new IllegalArgumentException("userDao cannot be null");
        }
        if (userRepository == null) {
            throw new IllegalArgumentException("userRepository cannot be null");
        }
        /*
        if (passwordEncoder == null) {
            throw new IllegalArgumentException("passwordEncoder cannot be null");
        }
        */
        
        this.userDao = userDao;
        this.BoarddataRepository = BoarddataRepository;
 		// role 세팅
		Role role = Role.USER;
		// 기본 아이디 세팅
		member user = new member();
	        user.setEmail("gochoking@naver.com");
	       
	        user.setPassword("{noop}1234");
	        user.setRole(role);
	        
	        
             
	        
	        // 팀데이터도 일부 만들어 집어넣어야 함
	        //user.setTeamdata();
	        //System.out.println("여기가 문제니");
	        int id = createUser(user);
        
       // this.passwordEncoder = passwordEncoder;
    }


    @Override
    public member getUser(int id) {
        return userDao.getUser(id);
    }

    @Override
    public member findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    public List<member> findUsersByEmail(String partialEmail) {
        return userDao.findUsersByEmail(partialEmail);
    }

    /**
     * Create a new Signup User
     * @param user
     *            the new {@link memberdata} to create. The {@link memberdata#getId()} must be null.
     * @return
     */
    @Override
    public int createUser(member user) {
        String encodedPassword = user.getPassword(); // passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        int id = userDao.createUser(user);
        
        return id;
    }
     


} // The End...
