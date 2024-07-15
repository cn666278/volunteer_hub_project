package com.wsa.service;

import com.wsa.mapper.AuthorityMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {
    private static final Logger logger = LogManager.getLogger(AuthorityService.class);
    @Autowired
    private AuthorityMapper authorityMapper;

}
