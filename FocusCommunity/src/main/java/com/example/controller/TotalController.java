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
import com.example.domain.CususrVO;
import com.example.domain.PageDTO;
import com.example.service.CusbodService;

@Controller
@RequestMapping("/total/*")
public class TotalController {
	
	@Autowired
	private CusbodService cusbodService;
	
	@GetMapping("/list")
	public void list(HttpSession sessoin, Criteria cri, Model model) {
		String id = (String) sessoin.getAttribute("sessionid");
		if(cusbodService.getListCount(cri) == (cri.getPageNum() - 1) * cri.getAmount() && cusbodService.getListCount(cri) != 0){
			cri.setPageNum((cri.getPageNum() - 1));
		}
		
		// 한 페이지당 글개수(amount)가 10개씩일때
		// 1 페이지 -> 0
		// 2 페이지 -> 10
		// 3 페이지 -> 20
		// 4 페이지 -> 30
		// cri.setStartRow((cri.getPageNum() -  1) * cri.getAmount());
		List<CusbodVO> 	cusbodList	= cusbodService.getList(cri);
		int				totalCount 	= cusbodService.getListCount(cri);
		PageDTO			pageDTO		= new PageDTO(cri, totalCount);
		
		// 뷰에서 사용할 데이터를 Model 객체에 저장 → 스프링이 requestScope 로 옮겨줌.
		model.addAttribute("cusbodList", cusbodList);
		model.addAttribute("pageMaker", pageDTO);
	} // end of list
} // end of TotalController