package com.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;

import com.entity.memberdata;



public interface memberService {


    memberdata getUser(int id);


    memberdata findUserByEmail(String email);


    List<memberdata> findUsersByEmail(String partialEmail);


    int createUser(memberdata user);


}
