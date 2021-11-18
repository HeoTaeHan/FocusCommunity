package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.AttachVO;
import com.example.domain.Criteria;
import com.example.domain.CususrVO;
import com.example.domain.PageDTO;
import com.example.service.AttachService;
import com.example.service.CusbodService;
import com.example.service.CususrService;
import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@Controller
@RequestMapping("/admin/*")
public class AdminController {
	
	@Autowired
	private AttachService attachService;
	
	@Autowired
	private CususrService cususrService;
	
	@Autowired
	private CusbodService cusbodService;
	
	// 년/월/일 폴더명 생성하는 메소드
	private String getFolder() {
		SimpleDateFormat 	sdf 	= new SimpleDateFormat("yyyy/MM/dd"); // "yyyy-MM-dd"
		Date				date 	= new Date();
		String 				str		= sdf.format(date);
		//str = str.replace("-", File.separator);
		return str;
	} // end of getFolder
	
	@GetMapping("/list")
	public String list(Criteria cri, Model model, HttpSession session) {
		String id = (String) session.getAttribute("sessionid");
		if(cususrService.countAdmin(cri) == (cri.getPageNum() - 1) * cri.getAmount() && cususrService.countAdmin(cri) != 0){
			cri.setPageNum((cri.getPageNum() - 1));
		}
		
		// 한 페이지당 글개수(amount)가 10개씩일때
		// 1 페이지 -> 0
		// 2 페이지 -> 10
		// 3 페이지 -> 20
		// 4 페이지 -> 30
		// cri.setStartRow((cri.getPageNum() -  1) * cri.getAmount());
		List<CususrVO> 	cususrList	= cususrService.getCususrPageWhereOption(cri);
		int				totalCount 	= cususrService.countAdmin(cri);
		PageDTO			pageDTO		= new PageDTO(cri, totalCount);
		
		// 뷰에서 사용할 데이터를 Model 객체에 저장 → 스프링이 requestScope 로 옮겨줌.
		model.addAttribute("cususrList", cususrList);
		model.addAttribute("pageMaker", pageDTO);
		
		return "/admin/list";
	} // end of list
	
	@GetMapping("/content")
	public void content(String cusid, @ModelAttribute("pageNum") String pageNum, @ModelAttribute("keyword") String keyword, @ModelAttribute("type") String type, 
			HttpSession session, Model model) {
		String		id			= (String) session.getAttribute("sessionid");
		CususrVO	cususrVO	= cususrService.select(cusid);
		model.addAttribute("cususrVO", cususrVO);
	} // end of content
	
	@GetMapping("/modify")
	public String modify(@ModelAttribute("cusid") String cusid, Model model, @ModelAttribute("pageNum") String pageNum, 
			@ModelAttribute("keyword") String keyword, @ModelAttribute("type") String type, HttpSession session) {
		String 	 id		  = (String) session.getAttribute("sessionid");
		CususrVO cususrVO = cususrService.select(cusid);
		model.addAttribute("cusid", cusid);
		model.addAttribute("cususr", cususrVO);
		model.addAttribute("email1", cususrVO.getEmail().substring(0,cususrVO.getEmail().lastIndexOf("@")));
		model.addAttribute("email2", cususrVO.getEmail().substring(cususrVO.getEmail().lastIndexOf("@")+1));
		return "/admin/modify";
	} // end of modify
	
	@PostMapping("/modify")
	public String modify(@ModelAttribute("cusid") String cusid, @ModelAttribute("pageNum") String pageNum, 
			@ModelAttribute("keyword") String keyword, @ModelAttribute("type") String type, String email1, String email2, 
			CususrVO cususrVO , Model model, RedirectAttributes rttr) {
		String birth = cususrVO.getBirth();
		birth = birth.replace("-", ""); // 하이픈 문자열을 빈문자열로 대체
		cususrVO.setBirth(birth);
		
		String email  = email1 + "@" + email2;
		cususrVO.setEmail(email);
		
		cususrService.update(cususrVO);
		
		model.addAttribute("cusid", cusid);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/html; charset=UTF-8");
		
		rttr.addAttribute("cusid", cusid);
		rttr.addAttribute("pageNum", pageNum);
		rttr.addAttribute("keyword", keyword);
		rttr.addAttribute("type", type);
		
		return "/admin/modifyPro";
	} // end of modify
	
	@GetMapping("/modifyPro")
	public void modifyPro(@ModelAttribute("cusid") String cusid, @ModelAttribute("pageNum") String pageNum, 
			@ModelAttribute("keyword") String keyword, @ModelAttribute("type") String type, Model model, RedirectAttributes rttr) {
		rttr.addAttribute("cusid", cusid);
		rttr.addAttribute("pageNum", pageNum);
		rttr.addAttribute("keyword", keyword);
		rttr.addAttribute("type", type);
	} // end of modifyPro
	
	@GetMapping("/remove")
	public String remove(String cusid, HttpServletRequest request, HttpServletResponse response, HttpSession session, @ModelAttribute("pageNum") String pageNum, 
			@ModelAttribute("keyword") String keyword, @ModelAttribute("type") String type, RedirectAttributes rttr) {
		
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
		
		rttr.addAttribute("pageNum", pageNum);
		rttr.addAttribute("keyword", keyword);
		rttr.addAttribute("type", type);
		
		return "redirect:/admin/list";
	} // end of remove
	
	@GetMapping("/pieChart")
	public void pieChart() {} // end of chart
	
	@GetMapping("/gender-per-count")
	@ResponseBody
	public  void chart(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Map<String, Object>> list = cusbodService.getCount();
		// char.js에서 사용될 수 있도록 데이터를 가공하기
		List<String>	labelList 	= new ArrayList<>(); // 레이블을 담을 리스트 준비
		List<Long> 		dataList	= new ArrayList<>(); // 레이블을 담을 리스트 준비
		

		for(Map<String, Object> map : list){
			labelList.add((String) map.get("cusid"));
			dataList.add((Long) map.get("cnt"));
		}

		Map<String, Object> map = new HashMap<>(); // {labelList : ['남성','여성'], dataList : []}
		map.put("labelList", labelList);
		map.put("dataList", dataList);

		// Gson객체 준비
		Gson	gson	= new Gson();
		String	strJson = gson.toJson(map);

		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(strJson);
		out.flush();
		out.close();
	} // end of chart
	
	@GetMapping("/email")
	public void email() {} // end of email
	
	@SuppressWarnings("unused")
	@PostMapping("/multipart-mail")
	@ResponseBody
	public void mutilEmail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String uploadFolder = "D:/upload"; // 업로드 기준경로
		
		File uploadPath = new File(uploadFolder, getFolder()); // "C:/ksw/upload/2021/08/03"

		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		
		// MultipartRequest 인자값
		// 1. request
		// 2. 업로드할 물리적 경로.  "C:/ksw/upload"
		// 3. 업로드 최대크기 바이트 단위로 제한. 1024Byte * 1024Byte = 1MB 
		// 4. request의 텍스트 데이터, 파일명 인코딩 "utf-8"
		// 5. 파일명 변경 정책. 파일명 중복시 이름변경규칙 가진 객체를 전달

		// 파일 업로드하기
		MultipartRequest multi = new MultipartRequest(
				request
				, uploadPath.getPath()
				, 1024 * 1024 * 50
				, "utf-8"
				, new DefaultFileRenamePolicy());
		// ===== 파일 업로드 완료됨. =====
		
		
		if(multi.getFilesystemName("file") == null) {
			
			System.out.println("msg" + multi.getParameter("msg"));
			// 요청 파라미터 값 가져오기
			String		receiver	= multi.getParameter("receiver");
			String[] 	receivers 	= null; // 받는사람 배열타입(여러명일 수 있음)
			String		subject		= multi.getParameter("subject");
			String		msgClean	= multi.getParameter("msg").replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", "");
			String		replace1	= msgClean.replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", "");
			String		msg			= replace1.replaceAll("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>", "");
			/* 메일전송기능 라이브러리 : commons-email 라이브러리
			 * SimpleEmail 클래스 : 텍스트 메일 전송 용도
			 * MultiPartEmail 클래스 : 텍스트 메시지와 파일을 함꼐 전송
			 * HtmlEmail 클래스 : 이메일 내용을 HTML 문서 형식으로 전송용도
			 */
			SimpleEmail email		= new SimpleEmail();
			
			if(receiver != null) {
				receivers = receiver.split(",");
			}
			
			// SMTP 서버 연결설정
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(465); // 기본포트 465(SSL) 587(TLS)
			email.setAuthentication("springjava0506", "spring0506");
			
			// SMTP SSL, TLS 설정 활성화 설정
			email.setSSLOnConnect(true);
			email.setStartTLSEnabled(true);
			
			try {
				// 보내는사람. 제약사항 : 로그인한 아이디와 동일한 곚ㅇ이 되어야 함.
				email.setFrom("springjava0506@gmail.com", "관리자", "utf-8");
				// 받는사람
				for (String emailAddr : receivers) {
					email.addTo(emailAddr.trim());
				}
				// 받는사람 (참조인) 설정
				email.addCc("springjava0506@gmail.com", "이철진", "utf-8");
				// 받는사람 (숨은참조인) 설정
				email.addBcc("springjava0506@gmail.com", "관리자", "utf-8");
				// 메일 제목설정
				email.setSubject(subject);
				// 본문설정
				email.setMsg(msg);
				
				// 메일 전송
				email.send();
			} catch (EmailException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter 	out = response.getWriter();
			StringBuilder	sb	= new StringBuilder();
			sb.append("<script>");
			sb.append("	alert('메일 전송 성공!');");
			sb.append("	location.href = '/admin/email';");
			sb.append("</script>");
			out.print(sb.toString());
			out.flush();
			out.close();
		} else {
			
			
			// 요청 파라미터 값 가져오기
			String 		receiver	= multi.getParameter("receiver"); // "aa@a.com, bb@b.com, ..."
			String[] 	receivers 	= receiver.split(","); // 받는사람 배열타입(여러명일수 있음)
			
			String subject 	= multi.getParameter("subject"); // 메일 제목
			String msgClean	= multi.getParameter("msg").replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", "");
			String replace1	= msgClean.replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", "");
			String msg		= replace1.replaceAll("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>", ""); // 메일 내용
			String filename = multi.getFilesystemName("file"); // 업로드된 파일명
	
	
			// MultiPartEmail 클래스 : 텍스트 메시지와 파일을 함께 전송 용도
			//   EmailAttachment 클래스 : 첨부파일 정보 표현
			
			long beginTime = System.currentTimeMillis(); // 시작시간
			
			// 첨부파일 EmailAttachment 객체 생성
			EmailAttachment attach = new EmailAttachment();
			// 경로상에 한글이 있으면 에러가 발생하므로 유의
			attach.setPath(uploadPath.getPath() + "/" + filename);
			attach.setDescription("파일 설명글");
			//attach.setName(filename);
			
			// MultiPartEmail 객체 생성
			MultiPartEmail email = new MultiPartEmail();
			
			// SMTP 서버 연결설정
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(465); // 기본포트  465(SSL)  587(TLS)
			email.setAuthentication("springjava0506", "spring0506");
			
			// SMTP  SSL, TLS 활성화 설정
			email.setSSLOnConnect(true);
			email.setStartTLSEnabled(true);
			
			String message = "fail";
			
			try {
				// 보내는 사람 설정. 제약사항: 보내는사람은 로그인한 아이디와 동일한 계정이 되어야 함.
				email.setFrom("spring@gmail.com", "관리자", "utf-8");
				
				// 받는사람 설정
	//			email.addTo("zencoding@daum.net", "김상우", "utf-8");
	//			email.addTo("zencoding@daum.net", "김상우", "utf-8");
	//			email.addTo("zencoding@daum.net", "김상우", "utf-8");
				for (String emailAddr : receivers) {
					email.addTo(emailAddr.trim());
				} // for
				
				// 받는사람(참조인) 설정
				//email.addCc("zencoding@hanmail.net", "김상우", "utf-8");
				
				// 받는사람(숨은참조인) 설정
				//email.addBcc("zencoding@hanmail.net", "김상우", "utf-8");
				
				
				// 제목 설정
				email.setSubject(subject);
				// 본문 설정
				email.setMsg(msg);
				
				// 첨부파일 정보 추가
				email.attach(attach);
				
				// 메일 전송
				message = email.send();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			long endTime = System.currentTimeMillis(); // 종료시간
			
			long execTime = endTime - beginTime;
			
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			StringBuilder sb = new StringBuilder();
			sb.append("<script>");
			sb.append("    alert('메일 전송 성공! 전송시간: " + execTime + "ms message : " + message + "');");
			sb.append("    location.href = '/admin/email';");
			sb.append("</script>");
			
			out.print(sb.toString());
			out.flush();
			out.close();
		}
	}
} // end of AdminController