package com.fjsmu.comm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;

/**
 * Service 父类
 *
 * Created by zzh on 16/11/29.
 */
@Service
public class BaseService {

    @Autowired
    public Validator validator;
}
