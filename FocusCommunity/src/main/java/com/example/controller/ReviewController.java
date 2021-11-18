package com.example.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.Criteria;
import com.example.domain.CsreplVO;
import com.example.domain.CusbodVO;
import com.example.service.AttachService;
import com.example.service.CsreplService;
import com.example.service.CusbodService;
import com.example.util.BoardUtils;

@Controller
@RequestMapping("/review/*")
public class ReviewController {
	
	@Autowired
	private AttachService attachService;
	
	@Autowired
	private CsreplService csreplService;
	
	@Autowired
	private CusbodService cusbodService;
	
	// 글목록
	@GetMapping("/list")
	public void list(HttpSession sessoin, Criteria cri, Model model) {
		
		BoardUtils.getList(cusbodService, sessoin, cri, model, "R");
	} // end of list
	
	// 상세보기
	@GetMapping("/content")
	public void content(Model model, HttpSession session, @ModelAttribute("pageNum") String pageNum, 
			@ModelAttribute("keyword") String keyword, @ModelAttribute("type") String type, @ModelAttribute("cusid") String cusid, 
			@ModelAttribute("csnum") int csnum, String ctype) {
		
		
		cusbodService.updateReadcount(csnum);
		
		CusbodVO		cusbodVO	= cusbodService.selectCusbodAndAttatchAndRecom(csnum, ctype, cusid);
		List<CsreplVO> 	list		= csreplService.selectBoards(csnum, ctype);
		
		model.addAttribute("cusbodVO", cusbodVO);
		model.addAttribute("csreplList", list);
	} // end of content
	
	// 주글쓰기
	@GetMapping("/write")
	public String write(@ModelAttribute("pageNum") String pageNum, 
			@ModelAttribute("keyword") String keyword, @ModelAttribute("type") String type, HttpSession session, Model model) {

		model.addAttribute("ctype", "R");
		return "review/write";
	} // end of write
	
	@PostMapping("/write")
	public String write(CusbodVO cusbodVO, @ModelAttribute("ctype") String ctype, String type, String pageNum, 
			String keyword, HttpServletRequest request, RedirectAttributes rttr, String cusid, Model model) throws IllegalStateException, IOException {
		
		return BoardUtils.getGalleryWrite(cusbodService, request, pageNum, keyword, type, cusid, "R", cusbodVO, model, rttr, "redirect:/review/content");
	} // end of galleryWrite
	
	// 글수정
	@GetMapping("/modify")
	public void modify(Model model, HttpSession session, @ModelAttribute("pageNum") String pageNum, 
			@ModelAttribute("keyword") String keyword, @ModelAttribute("type") String type, int csnum, String ctype) {
		String cusid = (String) session.getAttribute("sessionid");
		
		CusbodVO		cusbodVO	= cusbodService.selectCusbodAndAttatchAndRecom(csnum, "R", cusid);
		
		model.addAttribute("cusbodVO", cusbodVO);
	}
	
	@PostMapping("/modify")
	public String modify(@ModelAttribute("pageNum")  String pageNum,
			@ModelAttribute("keyword") String keyword, @ModelAttribute("type") String type, int csnum, 
			String ctype, RedirectAttributes rttr, HttpServletRequest request, CusbodVO cusbodVO,
			@RequestParam(name = "uuid", required = false) String delUuidList, String del) throws IllegalStateException, IOException {
		return BoardUtils.getGalleryModify(attachService, cusbodService, cusbodVO, pageNum, type, keyword, request, delUuidList, del, rttr, "redirect:/review/content");
	}
	
	// 글삭제
	@GetMapping("/remove")
	public String remove(Model model, @ModelAttribute("pageNum") String pageNum, 
			@ModelAttribute("keyword") String keyword, @ModelAttribute("type") String type,
			@ModelAttribute("csnum") int csnum, 
			@ModelAttribute("ctype") String ctype, @ModelAttribute("uuid") String uuid, RedirectAttributes rttr) {
		
		
		// 글목록으로 리다이렉트 이동
		return BoardUtils.getGalleryRemove(attachService, cusbodService, pageNum, keyword, type, csnum, "R", uuid, "redirect:/review/list", rttr);
	} // end of remove
	
	// 답글
	@GetMapping("/replyWrite")
	public void replyWrite(@ModelAttribute("crerf") String crerf, 
			@ModelAttribute("crelv") String crelv, 
			@ModelAttribute("cresq") String cresq, 
			@ModelAttribute("pageNum") String pageNum, @ModelAttribute("keyword") String keyword, @ModelAttribute("type") String type) {
		
	}
	
	@PostMapping("/replyWrite")
	public String replyWrite(@ModelAttribute("crerf") String crerf, 
			@ModelAttribute("crelv") String crelv, 
			@ModelAttribute("cresq") String cresq, 
			@ModelAttribute("pageNum") String pageNum, @ModelAttribute("keyword") String keyword,
			@ModelAttribute("type") String type, HttpServletRequest request, 
			CusbodVO cusbodVO, String ctype, Model model, RedirectAttributes rttr, HttpSession session) throws IllegalStateException, IOException {
		String cusid = (String) session.getAttribute("sessionid");
		return BoardUtils.getGalleryReplyWrite(cusbodService, request, pageNum, keyword, type, cusid, "R", cusbodVO, model, rttr, "redirect:/review/content");
	}
	
	// 댓글
	@GetMapping("/reply")
	public String reply(@ModelAttribute("pageNum") String pageNum, 
			@ModelAttribute("keyword") String keyword, @ModelAttribute("type") String type,  @ModelAttribute("csnum")  int csnum, 
			String ctype, RedirectAttributes rttr, HttpServletRequest request, @ModelAttribute("cusid") String cusid, HttpSession session) {
		
		String id = (String) session.getAttribute("sessionid");
		 
		return BoardUtils.getReply(csreplService, pageNum, keyword, type, csnum, "R", rttr, request, id, cusid, "redirect:/review/content");
	}
	
	// 댓글수정
	@GetMapping("/replyModify")
	public String replyModify(@ModelAttribute("pageNum") String pageNum, 
			@ModelAttribute("keyword") String keyword, @ModelAttribute("type") String type, @ModelAttribute("csnum")  int csnum, 
			String ctype, RedirectAttributes rttr, HttpServletRequest request, @ModelAttribute("cusid") String cusid, HttpSession session) {
		String 	id 		= (String) session.getAttribute("sessionid");
		int 	conum	= Integer.parseInt(request.getParameter("conum"));
		
		return BoardUtils.getReplyModify(csreplService, pageNum, keyword, type, csnum, "R", rttr, request, id, cusid, conum, "redirect:/review/content");
	}
	
	@GetMapping("/replyRemove")
	public String replyRemove(@ModelAttribute("pageNum") String pageNum, 
			@ModelAttribute("keyword") String keyword, @ModelAttribute("type") String type, @ModelAttribute("cusid") String cusid, @ModelAttribute("csnum")  int csnum, 
			String ctype, RedirectAttributes rttr, HttpServletRequest request, HttpSession session) {
		String 	id 		= (String) session.getAttribute("sessionid");
		int 	conum	= Integer.parseInt(request.getParameter("conum"));
		return BoardUtils.getReplyRemove(csreplService, pageNum, keyword, type, csnum, "R", rttr, request, id, cusid, conum, "redirect:/review/content");
	}
	
	// 대댓글
	@GetMapping("/replyRe")
	public String replyRe(@ModelAttribute("pageNum") String pageNum, 
			@ModelAttribute("keyword") String keyword, @ModelAttribute("type") String type, @ModelAttribute("cusid") String cusid, @ModelAttribute("csnum")  int csnum, 
			String ctype, RedirectAttributes rttr, HttpServletRequest request, HttpSession session) {
		String 	id 		= (String) session.getAttribute("sessionid");
		return BoardUtils.getReplyRe(csreplService, pageNum, keyword, type, csnum, "R", rttr, request, id, cusid, "redirect:/review/content");
	}
	
} // end of ReviewController
