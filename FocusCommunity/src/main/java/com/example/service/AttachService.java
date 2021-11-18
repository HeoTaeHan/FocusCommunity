package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.AttachVO;
import com.example.mapper.AttachMapper;

// Service Class for Attach
/**
 *
 * @(#) AttachService.java
 *
 *
 * @version v0.1
 * @date    2021-10-22
 * @author  TaeHan Heo
 * @since   JDK1.8
 *
 */
@Service
public class AttachService {

    @Autowired
    private AttachMapper attachMapper;

    /**
    * Get one Record 
    * @param String uuid
    * @return AttachMapper 
    * @author TaeHan Heo 
    * @date 2021-10-22
    */
    public AttachVO select(String uuid) {
        return attachMapper.select(uuid);
    } // end of select

    /**
    * Get All Record 
    * @param  List 
    * @return List 
    * @author TaeHan Heo 
    * @date 2021-10-22
    */
    public List<AttachVO> selectAll() {
        return attachMapper.selectAll();
    } // end of selectAll

    /**
    * Add Record 
    * @param AttachVO 
    * @return void 
    * @author TaeHan Heo 
    * @date 2021-10-22
    */
    public void insert(AttachVO Attach) {
        attachMapper.insert(Attach);
    } // end of insert

    /**
    * Update Record 
    * @param AttachVO 
    * @return void 
    * @author TaeHan Heo 
    * @date 2021-10-22
    */
    public void update(AttachVO Attach) {
        attachMapper.update(Attach);
    } // end of update

    /**
    * Delete Record 
    * @param String uuid
    * @return void 
    * @author TaeHan Heo 
    * @date 2021-10-22
    */
    public void delete(String uuid) {
        attachMapper.delete(uuid);
    } // end of delete

    /**
    * Get Rows Count 
    * @param String uuid
    * @return int 
    * @author TaeHan Heo 
    * @date 2021-10-22
    */
    public int count(String uuid) {
        return attachMapper.count(uuid);
    } // end of count

    /**
    * Get All Rows Count 
    * @param void 
    * @return int 
    * @author TaeHan Heo 
    * @date 2021-10-22
    */
    public int countAll() {
        return attachMapper.countAll();
    } // end of countAll
    
    public List<AttachVO> getAttachesByCusid(String cusid) {
    	return attachMapper.getAttachesByCusid(cusid);
    } // end of getAttachesByCusid
    
    public AttachVO selectByBno(int bno, String ctype) {
    	return attachMapper.selectByBno(bno, ctype);
    }
    
    public AttachVO getAttacheByUuid(String uuidList) {
		return attachMapper.getAttacheByUuid(uuidList);
	}
    
    public int countByCusid(String cusid) {
    	return attachMapper.countByCusid(cusid);
    }
    
    public void deleteByCusid(String cusid) {
    	attachMapper.deleteByCusid(cusid);
    }
} // end of AttachService class