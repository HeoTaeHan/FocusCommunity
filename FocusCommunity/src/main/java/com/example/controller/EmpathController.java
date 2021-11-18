package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Criteria;
import com.example.domain.CusbodVO;
import com.example.domain.PageDTO;
import com.example.service.CusbodService;

@Controller
@RequestMapping("/empath/*")
public class EmpathController {
	
	@Autowired
	private CusbodService cusbodService;
	
	@GetMapping("/list") 
	public void list(HttpSession session, Model model, Criteria cri) {
		String id = (String) session.getAttribute("sessionid");
		if(cusbodService.getEmpathListCount(cri) == (cri.getPageNum() - 1) * cri.getAmount() && cusbodService.getEmpathListCount(cri) != 0){
			cri.setPageNum((cri.getPageNum() - 1));
		}
		
		// 한 페이지당 글개수(amount)가 10개씩일때
		// 1 페이지 -> 0
		// 2 페이지 -> 10
		// 3 페이지 -> 20
		// 4 페이지 -> 30
		// cri.setStartRow((cri.getPageNum() -  1) * cri.getAmount());
		List<CusbodVO> 	cusbodList	= cusbodService.getEmpathList(cri);
		int				totalCount 	= cusbodService.getEmpathListCount(cri);
		PageDTO			pageDTO		= new PageDTO(cri, totalCount);
		
		// 뷰에서 사용할 데이터를 Model 객체에 저장 → 스프링이 requestScope 로 옮겨줌.
		model.addAttribute("cusbodList", cusbodList);
		model.addAttribute("pageMaker", pageDTO);
	} // end of list
} // end of EmpathController