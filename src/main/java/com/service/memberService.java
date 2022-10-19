package com.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;


import com.entity_internal.member;



public interface memberService {


	member getUser(int id);


	member findUserByEmail(String email);


    List<member> findUsersByEmail(String partialEmail);


    int createUser(member user);


}
