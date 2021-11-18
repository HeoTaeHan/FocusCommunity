package com.example.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.AttachVO;
import com.example.domain.Criteria;
import com.example.domain.CsreplVO;
import com.example.domain.CusbodVO;
import com.example.mapper.AttachMapper;
import com.example.mapper.CsreplMapper;
import com.example.mapper.CusbodMapper;

// Service Class for Cusbod
/**
 *
 * @(#) CusbodService.java
 *
 *
 * @version v0.1
 * @date    2021-10-25
 * @author  TaeHan Heo
 * @since   JDK1.8
 *
 */
@Service
public class CusbodService {
	
	@Autowired
	private AttachMapper attachMapper;

    @Autowired
    private CusbodMapper cusbodMapper;
    
    @Autowired
    private CsreplMapper csreplMapper;

    /**
    * Get one Record 
    * @param long csnum, String ctype
    * @return CusbodMapper 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    public CusbodVO select(long csnum, String ctype) {
        return cusbodMapper.select(csnum, ctype);
    } // end of select

    /**
    * Get All Record 
    * @param  List 
    * @return List 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    
    public int getNextnum(String ctype) {
		return cusbodMapper.getNextnum(ctype);
	}
    
    public List<CusbodVO> selectAll() {
        return cusbodMapper.selectAll();
    } // end of selectAll

    /**
    * Add Record 
    * @param CusbodVO 
    * @return void 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    public void insert(CusbodVO Cusbod) {
        cusbodMapper.insert(Cusbod);
    } // end of insert
    
    @Transactional
    public void inserts(CusbodVO Cusbod) {
        cusbodMapper.insert(Cusbod);
        
        AttachVO attachList = Cusbod.getAttach();
		
		
		if(attachList.getUuid() != null) {
			attachMapper.inserts(attachList);
		}
    } // end of insert

    /**
    * Update Record 
    * @param CusbodVO 
    * @return void 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    public void update(CusbodVO Cusbod) {
        cusbodMapper.update(Cusbod);
    } // end of update

    /**
    * Delete Record 
    * @param long csnum, String ctype
    * @return void 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    public void delete(long csnum, String ctype) {
        cusbodMapper.delete(csnum, ctype);
    } // end of delete

    /**
    * Get Rows Count 
    * @param long csnum, String ctype
    * @return int 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    public int count(long csnum, String ctype) {
        return cusbodMapper.count(csnum, ctype);
    } // end of count

    /**
    * Get All Rows Count 
    * @param void 
    * @return int 
    * @author TaeHan Heo 
    * @date 2021-10-25
    */
    public int countAll() {
        return cusbodMapper.countAll();
    } // end of countAll
    
    public int countCusid(String cuisd) {
    	return cusbodMapper.countCusid(cuisd);
    } // end of countCusid
    
    public void deleteCusid(String cusid) {
    	cusbodMapper.deleteCusid(cusid);
    } // end of deleteCusid
    
    public List<Map<String, Object>> getCount() {
    	return cusbodMapper.getCount();
    } // end of getCount
    
    public List<CusbodVO> getList(Criteria cri) {
    	
    	int startRow = (cri.getPageNum() - 1) * cri.getAmount();
		cri.setStartRow(startRow);
		
    	return cusbodMapper.getList(cri);
    } // end of getList
    
    public int getListCount(Criteria cri) {
    	return cusbodMapper.getListCount(cri);
    } // end of getListCount
    
    public List<CusbodVO> getApiList(Criteria cri) {
    	
    	int startRow = (cri.getPageNum() - 1) * cri.getAmount();
		cri.setStartRow(startRow);
		
    	return cusbodMapper.getApiList(cri);
    } // end of getApiList
    
    public List<CusbodVO> getEmpathList(Criteria cri) {
    	
    	int startRow = (cri.getPageNum() - 1) * cri.getAmount();
		cri.setStartRow(startRow);
		
    	return cusbodMapper.getEmpathList(cri);
    } // end of getEmpathList
    
    public int getEmpathListCount(Criteria cri) {
    	return cusbodMapper.getEmpathListCount(cri);
    } // end of getEmpathListCount
    
    public List<CusbodVO> getListType(Criteria cri) {
    	
    	int startRow = (cri.getPageNum() - 1) * cri.getAmount();
		cri.setStartRow(startRow);
		
    	return cusbodMapper.getListType(cri);
    } // end of getListType
    
    public int getListTypeCount(Criteria cri) {
    	return cusbodMapper.getListTypeCount(cri);
    } // end of getListTypeCount
    
    public void updateReadcount(int csnum) {
    	cusbodMapper.updateReadcount(csnum);
    } // end of updateReadcount
    
    public CusbodVO selectCusbodAndAttatchAndRecom(int csnum, String ctype, String cusid) {
    	return cusbodMapper.selectCusbodAndAttatchAndRecom(csnum, ctype, cusid);
    } // end of selectCusbodAndAttatchAndRecom
    
    @Transactional
	public void updateCusbodAndInsertAttacheAndDeleteAttache(CusbodVO cusbodVO, AttachVO newAttachList, String delUuidList) {
    	
    	if (newAttachList.getUuid() != null) {
			attachMapper.insert(newAttachList);
		}
    	
		if (delUuidList != null) {
			attachMapper.delete(delUuidList);
		}
		cusbodMapper.update(cusbodVO);
    } // end of updateCusbodAndInsertAttacheAndDeleteAttache
    
    @Transactional
	public void deleteCusbodAndAttache(int num, String ctype) {
		// 외래키 관계가 있다면 삭제 순서는 외래키로 참조하는 테이블부터 삭제함에 유의!
		attachMapper.deleteAttacheByBno(num, ctype);
		csreplMapper.deleteCsreplByCsnum(num, ctype);
		cusbodMapper.deleteCusbodByCsnum(num, ctype);
	} // end of deleteCusbodAndAttache
    
    @Transactional
    public void addReply(CusbodVO cusbodVO) {
    	// 답글을 남길 대상글과 같은 글그룹(crerf) 안에서
		// 대상글의 순번(cresq)보다 큰 글들의 순번을 1씩 증가(UPDATE)
    	cusbodMapper.updateCresqPlusOne(cusbodVO.getCrerf(), cusbodVO.getCresq());
    	
    	// insert할 답글 re값으로 수정
    	cusbodVO.setCrelv(cusbodVO.getCrelv() + 1);
    	cusbodVO.setCresq(cusbodVO.getCresq() + 1);
    	
    	// 답글 insert 하기
    	cusbodMapper.insert(cusbodVO);
    	
    	AttachVO attachList = cusbodVO.getAttach();
		
		
		if(attachList.getUuid() != null) {
			attachMapper.inserts(attachList);
		}
    } // end of addReply
    
    @Transactional
    public void addGalleryReply(CusbodVO cusbodVO) {
    	// 답글을 남길 대상글과 같은 글그룹(crerf) 안에서
		// 대상글의 순번(cresq)보다 큰 글들의 순번을 1씩 증가(UPDATE)
    	cusbodMapper.updateCresqPlusOne(cusbodVO.getCrerf(), cusbodVO.getCresq());
    	
    	// insert할 답글 re값으로 수정
    	cusbodVO.setCrelv(cusbodVO.getCrelv() + 1);
    	cusbodVO.setCresq(cusbodVO.getCresq() + 1);
    	
    	// 답글 insert 하기
    	cusbodMapper.insert(cusbodVO);
    } // end of addReply
    
    public CusbodVO selectCusbodAndAttache(int num) {
    	return cusbodMapper.selectCusbodAndAttache(num);
    } // end of selectCusbodAndAttache
} // end of CusbodService class