	package com.example.controller;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.AttachVO;
import com.example.domain.CususrVO;
import com.example.service.AttachService;
import com.example.service.CususrService;
import com.example.util.JScript;

@Controller
@RequestMapping(path = "/member/*")
public class CususrController {
	
	@Autowired
	private AttachService attachService;
	
	@Autowired
	private CususrService cususrService;
	
	@GetMapping("/join")
	public void join() {} // end of join
	
	@PostMapping("/join")
	public ResponseEntity<String> join(CususrVO cususrVO, String email1, String email2) {
		// 회원가입날짜 설정
		cususrVO.setCdate(new Date());
		
		String birthday = cususrVO.getBirth();
		birthday = birthday.replace("-", ""); // 하이픈 문자열을 빈문자열로 대체
		cususrVO.setBirth(birthday);
		
		String email  = email1 + "@" + email2;
		cususrVO.setEmail(email);
		
		
		cususrService.insert(cususrVO);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/html; charset=UTF-8");
		
		String str = JScript.href("회원가입 성공!", "/member/joinPro");
		
		
		
		return new ResponseEntity<String>(str, headers, HttpStatus.OK);
	} // end of joinForm
	
	@GetMapping("/login")
	public void login() {
	} // end of login
	
	@PostMapping("/login")
	public ResponseEntity<String> loginForm(HttpServletRequest request, String cusid, String paswd, String rememberMe, HttpSession session, HttpServletResponse response, Model model) {
		session.setAttribute("sessionid", cusid);
		
		
		CususrVO cususrVO =  cususrService.select(cusid);
		
		if(cususrVO == null) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "text/html; charset=UTF-8");
			
			String str = JScript.href("", "/member/loginPro");
			
			session.invalidate(); // 세션 비우기
			
			// 쿠키값 가져오기
			Cookie[] cookies = request.getCookies();

			// 특정 쿠키 삭제하기(브라우저가 삭제하도록 유효기간 0초로 설정해서 보내기)
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("loginId")) {
						cookie.setMaxAge(0); // 쿠키 유효기간 0초 설정(삭제 의도)
						cookie.setPath("/");
						response.addCookie(cookie); // 응답객체에 추가하기
					}
				}
			}
			
			return new ResponseEntity<String>(str, headers, HttpStatus.OK);
		} else {
		
			if(cususrVO.getPaswd().equals(paswd)) {
				// 사용자가 로그인 상태유지를 체크했으면
				if(rememberMe != null){
					// 쿠키 생성
					Cookie cookie = new Cookie("loginId", cusid);
					// 쿠키 요효시간(유통기간) 설정
					//cookie.setMaxAge(600); // 초단위로 설정. 10분 60초 * 10
					cookie.setMaxAge(60 * 60 * 24 * 7); // 일주일 설정
					// 쿠키 경로설정
					cookie.setPath("/"); // 프로젝트 모든 경로에서 쿠키 받도록 설정
					
					// 클라이언트로 보낼 쿠기를 response 웅답객체에 추가하기. -> 응답시 쿠키도 함께 보냄.
					response.addCookie(cookie);
					
					HttpHeaders headers = new HttpHeaders();
					headers.add("Content-Type", "text/html; charset=UTF-8");
					
					String str = JScript.href("", "/");
					
					
					return new ResponseEntity<String>(str, headers, HttpStatus.OK);
				}
				
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", "text/html; charset=UTF-8");
				
				String str = JScript.href("", "/");
				
				
				return new ResponseEntity<String>(str, headers, HttpStatus.OK);
			} else {
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", "text/html; charset=UTF-8");
				
				String str = JScript.href("", "/member/loginPro");
				
				return new ResponseEntity<String>(str, headers, HttpStatus.OK);
			}
		}
	} // end of loginForm
	
	@GetMapping("/modify")
	public String modify(Model model, HttpSession session) {
		String 	 id		  = (String) session.getAttribute("sessionid");
		CususrVO cususrVO = cususrService.select(id);
		model.addAttribute("id", id);
		model.addAttribute("email1", cususrVO.getEmail().substring(0,cususrVO.getEmail().lastIndexOf("@")));
		model.addAttribute("email2", cususrVO.getEmail().substring(cususrVO.getEmail().lastIndexOf("@")+1));
		return "/member/modifyMember";
	} // end of modify
	
	@PostMapping("/modify")
	public ResponseEntity<String> modify(String cusid, String email1, String email2, CususrVO cususrVO , Model model) {
		String birth = cususrVO.getBirth();
		birth = birth.replace("-", ""); // 하이픈 문자열을 빈문자열로 대체
		cususrVO.setBirth(birth);
		
		String email  = email1 + "@" + email2;
		cususrVO.setEmail(email);
		
		cususrService.update(cususrVO);
		
		model.addAttribute("cusid", cusid);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/html; charset=UTF-8");
		
		String str = JScript.href("회원정보수정 성공!", "/member/modifyPro");
		
		return new ResponseEntity<String>(str, headers, HttpStatus.OK);
	} // end of modify
	
	@GetMapping("/remove")
	public String remove(String cusid, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		List<AttachVO> attachV = attachService.getAttachesByCusid(cusid);
		
		
		// 업로드 기준경로
		String	uploadFolder = "D:/upload";
			
		for(AttachVO attach: attachV){
			String path  = uploadFolder + "/" + attach.getUploadpath() + "/" + attach.getUuid() + "_" + attach.getFilename();
			String path2 = uploadFolder + "/" + attach.getUploadpath() + "/s_"  + attach.getUuid() + "_" + attach.getFilename();
			File deleteFile  = new File(path);
			//File deleteFile2 = new File(path2);
				
			if(deleteFile.exists()){ // 삭제할 파일이 해당경로에 존재하면
				deleteFile.delete(); // 파일 삭제하기
					
				if(attach.getFiletype().equals("I")){ // 이미지 파일이면
					File tumbnailFile = new File(path2);
					if(tumbnailFile.exists()){ // 썸네일 이미지파일 존재하면
						tumbnailFile.delete(); // 썸네일 이미지파일 삭제하기
					}
				}
			}
				
			//if(deleteFile2.exists()){ // 삭제할 파일이 해당경로에 존재하면
				//deleteFile2.delete(); // 파일 삭제하기
			//}
		}
		
		cususrService.deleteAll(cusid); // DB 레코드 삭제
		
		session.invalidate(); // 세션 비우기
		
		// 쿠키값 가져오기
		Cookie[] cookies = request.getCookies();

		// 특정 쿠키 삭제하기(브라우저가 삭제하도록 유효기간 0초로 설정해서 보내기)
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("loginId")) {
					cookie.setMaxAge(0); // 쿠키 유효기간 0초 설정(삭제 의도)
					cookie.setPath("/");
					response.addCookie(cookie); // 응답객체에 추가하기
				}
			}
		}
		
		return "redirect:/";
	} // end of remove
	
	@GetMapping("/logout")
	public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		// 세션 비우기
		session.invalidate();
		// 로그인 상태유지용 쿠키가 있으면 삭제처리하기
		// 쿠키값 가져오기
		Cookie[] cookies = request.getCookies();

		// 특정 쿠키 삭제하기(브라우저가 삭제하도록 유효기간 0초로 설정해서 보내기)
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("loginId")) {
					cookie.setMaxAge(0); // 쿠키 유효기간 0초 설정(삭제 의도)
					cookie.setPath("/");
					response.addCookie(cookie); // 응답객체에 추가하기
				}
			}
		}
		// 로그인 화면으로 리다이렉트 이동
		return "redirect:/";
	} // end of logout
	
	@GetMapping("/pwFind")
	public void pwFind() {
		
	} // end of pwFind
	
	@PostMapping("/pwFind")
	public String pwFind(String cusid, Model model) {
		
		
		model.addAttribute("cusid", cusid);
		
		if(cususrService.count(cusid) > 0) {
			
			return "redirect:/member/pwFindSuccess";
		} else {
			
			return "redirect:/member/pwFindPro";
		}
		
		
	} // end of pwFind
	
	@GetMapping("/pwFindSuccess")
	public void pwFindSuccess() {} // end of pwFindSuccess
	
	@GetMapping("/pwFindPro")
	public void pwFindPro(String str, Model model) {
		model.addAttribute("str", "아이디 없음!");
	} // end of loginPro
	
	@GetMapping("/loginPro")
	public void loginPro(String str, Model model) {
		model.addAttribute("str", "아이디 혹은 비밀번호 틀림!");
	} // end of loginPro
	
	@GetMapping("/modifyPro")
	public void modifyPro(String str, Model model) {
		model.addAttribute("str", "회원정보수정 성공!");
	} // end of modifyPro
	
	@GetMapping("/joinPro")
	public void joinPro(String str, Model model) {
		model.addAttribute("str", "회원가입 성공!");
	} // end of joinPro
}// end of MemberController