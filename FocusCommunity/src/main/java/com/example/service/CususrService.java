package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Criteria;
import com.example.domain.CususrVO;
import com.example.mapper.CususrMapper;

@Service
public class CususrService {
	@Autowired
	private CususrMapper	cususrMapper;
	@Autowired
	private CrecomService	crecomService;
	@Autowired
	private CusbodService	cusbodService;
	@Autowired
	private CsreplService	csreplService;
	@Autowired
	private AttachService	attachService;
	
	// 페이징, 검색어 적용하여 글 목록 가져오기
	public List<CususrVO> getCususrs(Criteria cri) {
		// 한 페이지당 글개수(amount)가 10개씩일때
		// 1 페이지 -> 0
		// 2 페이지 -> 10
		// 3 페이지 -> 20
		// 4 페이지 -> 30
		int startRow = (cri.getPageNum() -  1) * cri.getAmount();
		
		cri.setStartRow(startRow);
		return cususrMapper.getCususrPageWhereOption(cri);
	} // end of getCususrs
	
	public CususrVO select(String cusid) {
		return cususrMapper.select(cusid);
	} // end of select
	
	public List<CususrVO> selectAll() {
		return cususrMapper.selectAll();
	} // end of selectAll
	
	public void insert(CususrVO cususr) {
		cususrMapper.insert(cususr);
	} // end of insert
	
	public void update(CususrVO cususr) {
		cususrMapper.update(cususr);
	} // end of update
	
	public void delete(String cusid) {
		cususrMapper.delete(cusid);
	} // end of delete
	
	public void deleteVO(CususrVO cususrVO) {
		cususrMapper.delete(cususrVO.getCusid());
	} // end of deleteVO
	
	public int count(String cusid) {
		return cususrMapper.count(cusid);
	} // end of count
	
	public int countAll() {
		return cususrMapper.countAll();
	} // end of countAll
	
	public int countByEmail(String email) {
		return cususrMapper.countByEmail(email);
	} // end of countByEmail
	
	public int countAdmin(Criteria cri) {
		return cususrMapper.countAdmin(cri);
	} // end of countAdmin

	public List<CususrVO> getCususrPageWhereOption(Criteria cri) {
		// TODO Auto-generated method stub
		return cususrMapper.getCususrPageWhereOption(cri);
	} // end of getCususrPageWhereOption
	
	@Transactional
	public void deleteAll(String cusid) {
		if (cususrMapper.count(cusid) > 0) {
			cususrMapper.delete(cusid);
		}
		
		if (csreplService.countCusid(cusid) > 0) {
			csreplService.deleteCusid(cusid);
		}
		
		if(cusbodService.countCusid(cusid) > 0) {
			cusbodService.deleteCusid(cusid);
		}
		
		if(crecomService.countCusid(cusid) > 0) {
			crecomService.deleteCusid(cusid);
		}
		
		if(attachService.countByCusid(cusid) > 0) {
			attachService.deleteByCusid(cusid);
		}
	} // end of deleteAll
} // end of MemberService