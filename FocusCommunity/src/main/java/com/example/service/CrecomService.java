package com.example.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.CrecomVO;
import com.example.mapper.CrecomMapper;

// Service Class for Crecom
/**
 *
 * @(#) CrecomService.java
 *
 *
 * @version v0.1
 * @date    2021-10-25
 * @author  TaeHan Heo
 * @since   JDK1.8
 *
 */
@Service
public class CrecomService {

    @Autowired
    private CrecomMapper crecomMapper;

    /**
    * Get one Record 
    * @param long csnum, String ctype, String cusid
    * @return CrecomMapper 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    public CrecomVO select(long csnum, String ctype, String cusid) {
        return crecomMapper.select(csnum, ctype, cusid);
    } // end of select

    /**
    * Get All Record 
    * @param  List 
    * @return List 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    public List<CrecomVO> selectAll() {
        return crecomMapper.selectAll();
    } // end of selectAll

    /**
    * Add Record 
    * @param CrecomVO 
    * @return void 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    public void insert(CrecomVO Crecom) {
        crecomMapper.insert(Crecom);
    } // end of insert

    /**
    * Update Record 
    * @param CrecomVO 
    * @return void 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    public void update(CrecomVO Crecom) {
        crecomMapper.update(Crecom);
    } // end of update

    /**
    * Delete Record 
    * @param long csnum, String ctype, String cusid
    * @return void 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    public void delete(long csnum, String ctype, String cusid) {
        crecomMapper.delete(csnum, ctype, cusid);
    } // end of delete

    /**
    * Get Rows Count 
    * @param long csnum, String ctype, String cusid
    * @return int 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    public int count(long csnum, String ctype, String cusid) {
        return crecomMapper.count(csnum, ctype, cusid);
    } // end of count

    /**
    * Get All Rows Count 
    * @param void 
    * @return int 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    public int countAll() {
        return crecomMapper.countAll();
    } // end of countAll
    
    public int countCusid(String cusid) {
    	return crecomMapper.countCusid(cusid);
    } // end of countCusid
    
    public void deleteCusid(String cusid) {
    	crecomMapper.deleteCusid(cusid);
    } // end of deleteCusid
    
    public int sumCsnum(long csnum) {
    	return crecomMapper.sumCsnum(csnum);
    }
} // end of CrecomService class