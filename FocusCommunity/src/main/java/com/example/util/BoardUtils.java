package com.example.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.AttachVO;
import com.example.domain.Criteria;
import com.example.domain.CsreplVO;
import com.example.domain.CusbodVO;
import com.example.domain.PageDTO;
import com.example.service.AttachService;
import com.example.service.CrecomService;
import com.example.service.CsreplService;
import com.example.service.CusbodService;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.coobird.thumbnailator.Thumbnailator;

public class BoardUtils {
	
	// 이미지 파일여부, 리턴하는 메서드
	private static boolean checkImageType(File file) throws IOException {
		boolean isImage = false;
		
		String contentType = Files.probeContentType(file.toPath());
		
		if(contentType != null) {
			isImage = contentType.startsWith("image");
		}
		
		return isImage;
	} // end of checkImageType
	
	// 년/월/일 형식의 폴더명을 리턴하는 메서드
	private static String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String str = sdf.format(new Date());
		return str;
	} // end of getFolder
	
	// 첨부파일 업로드(썸네일 이미지 생성) 후 attachList 리턴하는 메서드
	public static AttachVO uploadFileAndGetAttachList(MultipartFile file, int bno, String cusid, String ctype) throws IllegalStateException, IOException {
			
		AttachVO attachList = new AttachVO();
			
		// 업로드 처리로 생성할 파일 정보가 없으면 메서드 종료
		if(file != null) {
			if(file.isEmpty()) {
				return attachList;
			}
				
			String 	uploadFolder 	= "D:/upload"; // 입로드 기준경로
			File	uploadPath		= new File(uploadFolder, getFolder()); // "D:/upload/2021/10/19"
				
			if(!uploadPath.exists()) {
				uploadPath.mkdirs();
			}
				
			if(!file.isEmpty()) {
				String originalFilename = file.getOriginalFilename();
				
				UUID uuid = UUID.randomUUID();
					
				String uploadFilename = uuid.toString() + "_" + originalFilename;
				
				File fil = new File(uploadPath, uploadFilename); // 생성할 파일경로 파일명 정보
				
				// 파일 1개 업로드(파일 생성) 완료
				file.transferTo(fil);
				// =====================================================
				
				// 현재 업로드한 파일이 이미지 파일이면 썸네일 이미지를 추가로 생성하기
				boolean isImage = checkImageType(fil); // 이미지 파일여부 true/false로 리턴
				
				if(isImage) {
					File outFile = new File(uploadPath, "s_"+uploadFilename);
					
					Thumbnailator.createThumbnail(fil, outFile, 100, 100); // 썸네일 이미지파일 생성하기
				}
				// ========================================
				// insert할 주글 AttachVO 객체 준비 및 데이터 저장
				attachList.setUuid(uuid.toString());
				attachList.setUploadpath(getFolder());
				attachList.setFilename(originalFilename);
				attachList.setFiletype(isImage? "I": "O");
				attachList.setBno(bno);
				attachList.setCusid(cusid);
				attachList.setCtype(ctype);
			}
		}
		
		return attachList;
	} // end of uploadFileAndGetAttachList
	
	// 첨부파일 삭제하는 메소드
	private static void deleteAttachFiles(AttachVO attachList) {
		// 삭제할 파일정보가 없으면 메소드 종료
		if (attachList == null) {
			return;
		}
		
		String basePath = "D:/upload";
		
		String uploadpath = basePath + "/" + attachList.getUploadpath(); // "C:/ksw/upload/2021/10/20"
		String filename = attachList.getUuid() + "_" + attachList.getFilename(); // "uuid_커비.png"
		
		File file = new File(uploadpath, filename); // "C:/ksw/upload/2021/10/20/uuid_커비.png"
		
		if (file.exists() == true) { // 해당 경로에 첨부파일이 존재하면
			file.delete();  // 첨부파일 삭제하기
		}
		
		// 첨부파일이 이미지일 경우 썸네일 이미지 파일도 삭제하기
		if (attachList.getFiletype().equals("I")) {  // "Image" 파일이면
			// 썸네일 이미지 파일 존재여부 확인 후 삭제하기
			File thumbnailFile = new File(uploadpath, "s_" + filename);
			if (thumbnailFile.exists() == true) {
				thumbnailFile.delete();
			}
		}
	} // deleteAttachFiles
	
	private static void galleryDeleteFiles(String uuid) {
		// 삭제할 파일정보가 없으면 메소드 종료
		if (uuid == null) {
			return;
		}
		
		String basePath = "C:/LCJ/upload/";
		
		String uploadpath = basePath; // "C:/ksw/upload/2021/10/20"
		String filename = uuid; // "uuid_커비.png"
		
		File file = new File(uploadpath, filename); // "C:/ksw/upload/2021/10/20/uuid_커비.png"
		
		if (file.exists() == true) { // 해당 경로에 첨부파일이 존재하면
			file.delete();  // 첨부파일 삭제하기
		}
		
	} // end of galleryDeleteFiles
	
	public static void getList(CusbodService cusbodService, HttpSession sessoin, Criteria cri, Model model, String ctype) {
		String id = (String) sessoin.getAttribute("sessionid");
		cri.setCtype(ctype);
		if(cusbodService.getListTypeCount(cri) == (cri.getPageNum() - 1) * cri.getAmount() && cusbodService.getListTypeCount(cri) != 0){
			cri.setPageNum((cri.getPageNum() - 1));
		}
		
		// 한 페이지당 글개수(amount)가 10개씩일때
		// 1 페이지 -> 0
		// 2 페이지 -> 10
		// 3 페이지 -> 20
		// 4 페이지 -> 30
		// cri.setStartRow((cri.getPageNum() -  1) * cri.getAmount());
		List<CusbodVO> 	cusbodList	= cusbodService.getListType(cri);
		int				totalCount 	= cusbodService.getListTypeCount(cri);
		PageDTO			pageDTO		= new PageDTO(cri, totalCount);
		
		// 뷰에서 사용할 데이터를 Model 객체에 저장 → 스프링이 requestScope 로 옮겨줌.
		model.addAttribute("cusbodList", cusbodList);
		model.addAttribute("pageMaker", pageDTO);
		model.addAttribute("ctype", ctype);
	} // end of getList
	
	public static String getWrite(CusbodService cusbodService, MultipartFile file, HttpServletRequest request, String pageNum, String keyword, String type, 
			String cusid, String ctype, CusbodVO cusbodVO, Model model, 
			RedirectAttributes rttr, String url) throws IllegalStateException, IOException {
		// 스프링 웹에서는 클라이언트로부터 넘어오는 file 타입의 input 요소의 갯수만큼
		// MultipartFile 타입의 객체로 전달받게 됨. 
		
		
		// insert할 새 글번호 가져오기
		int num = cusbodService.getNextnum(ctype);
		// 첨부파일 업로드(썸네일 이미지 생성) 후 attachList 리턴
		AttachVO attach = uploadFileAndGetAttachList(file, num, cusid, ctype);
		
		//======= insert 할 cusbodVO 객체의 데이터 설정 ===========
		cusbodVO.setCsnum((long) num);
		cusbodVO.setCrdcn(0);
		cusbodVO.setCdate(new Date());
		cusbodVO.setCtmid(request.getRemoteAddr());
		cusbodVO.setCrerf(num);  // 주글일 경우 글그룹번호는 글번호와 동일함
		cusbodVO.setCrelv(0);    // 주글일 경우 들여쓰기 레벨은 0
		cusbodVO.setCresq(0);    // 주글일 경우 글그룹 안에서의 순번은 0
		cusbodVO.setAttach(attach);
		
		
		// 주글 한개(boardVO)와 첨부파일 여러개(attachList)를 한개의 트랜잭션으로 insert 처리함
		cusbodService.inserts(cusbodVO);
		//==============================================================
		//System.out.println("csnum = " + cusbodVO.getCsnum());
		// 리다이렉트 시 쿼리스트링으로 서버에 전달할 데이터를
		// RedirectAttributes 타입의 객체에 저장하면,
		// 스프링이 자동으로 쿼리스트링으로 변환해서 처리해줌
		model.addAttribute("cusbod", cusbodVO);
		rttr.addAttribute("csnum", cusbodVO.getCsnum());
		rttr.addAttribute("cusid", cusbodVO.getCusid());
		rttr.addAttribute("ctype", ctype);
		rttr.addAttribute("pageNum", pageNum);
		rttr.addAttribute("keyword", keyword);
		rttr.addAttribute("type", type);
		
		return url;
	} // end of getWrite
	
	public static String getGalleryWrite(CusbodService cusbodService, HttpServletRequest request, String pageNum, String keyword, String type, 
			String cusid, String ctype, CusbodVO cusbodVO, Model model, 
			RedirectAttributes rttr, String url) throws IllegalStateException, IOException {
		// 스프링 웹에서는 클라이언트로부터 넘어오는 file 타입의 input 요소의 갯수만큼
		// MultipartFile 타입의 객체로 전달받게 됨. 
		
		
		// insert할 새 글번호 가져오기
		int num = cusbodService.getNextnum(ctype);
		// 첨부파일 업로드(썸네일 이미지 생성) 후 attachList 리턴
		
		//======= insert 할 cusbodVO 객체의 데이터 설정 ===========
		cusbodVO.setCsnum((long) num);
		cusbodVO.setCrdcn(0);
		cusbodVO.setCdate(new Date());
		cusbodVO.setCtmid(request.getRemoteAddr());
		cusbodVO.setCrerf(num);  // 주글일 경우 글그룹번호는 글번호와 동일함
		cusbodVO.setCrelv(0);    // 주글일 경우 들여쓰기 레벨은 0
		cusbodVO.setCresq(0);    // 주글일 경우 글그룹 안에서의 순번은 0
		
		
		// 주글 한개(boardVO)와 첨부파일 여러개(attachList)를 한개의 트랜잭션으로 insert 처리함
		cusbodService.insert(cusbodVO);
		//==============================================================
		//System.out.println("csnum = " + cusbodVO.getCsnum());
		// 리다이렉트 시 쿼리스트링으로 서버에 전달할 데이터를
		// RedirectAttributes 타입의 객체에 저장하면,
		// 스프링이 자동으로 쿼리스트링으로 변환해서 처리해줌
		model.addAttribute("cusbod", cusbodVO);
		rttr.addAttribute("csnum", cusbodVO.getCsnum());
		rttr.addAttribute("cusid", cusbodVO.getCusid());
		rttr.addAttribute("ctype", ctype);
		rttr.addAttribute("pageNum", pageNum);
		rttr.addAttribute("keyword", keyword);
		rttr.addAttribute("type", type);
		
		return url;
	} // end of getGalleryWrite
	
	public static String getModify(AttachService attachService, CusbodService cusbodService, 
			MultipartFile file, CusbodVO cusbodVO, String pageNum, String type, String keyword, HttpServletRequest request,
			String delUuidList, RedirectAttributes rttr, String url)  throws IllegalStateException, IOException {
		int 		csnum			= Integer.parseInt(String.valueOf(cusbodVO.getCsnum()));
		String 		cusid 			= cusbodVO.getCusid();
		String 		ctype			= cusbodVO.getCtype();
		AttachVO 	newAttachList 	= uploadFileAndGetAttachList(file, csnum, cusid, ctype);
		//System.out.println("========== POST modify - 신규 첨부파일 업로드 완료 ==========");
		
		AttachVO delAttachList = null;
		if (delUuidList != null) {
			delAttachList = attachService.select(delUuidList);
			
			deleteAttachFiles(delAttachList); // 첨부파일(썸네일도 삭제) 삭제하기
		}
		
		// 3) 테이블 작업
		//    boardVO 게시글 수정
		//    attach 테이블에 신규 파일정보(newAttachList)를 insert, 삭제할 정보(delUuidList)를 delete
		
		// update 할 BoardVO 객체의 데이터 설정
		cusbodVO.setCtmid(request.getRemoteAddr()); // 사용자 IP주소 저장
		
		// 글번호에 해당하는 게시글 수정, 첨부파일정보 수정(insert, delete) - 한개의 트랜잭션으로 처리
		cusbodService.updateCusbodAndInsertAttacheAndDeleteAttache(cusbodVO, newAttachList, delUuidList);
		
		rttr.addAttribute("csnum", cusbodVO.getCsnum());
		rttr.addAttribute("cusid", cusbodVO.getCusid());
		rttr.addAttribute("ctype", ctype);
		rttr.addAttribute("pageNum", pageNum);
		rttr.addAttribute("keyword", keyword);
		rttr.addAttribute("type", type);
		
		return url;
	}
	
	public static String getGalleryModify(AttachService attachService, CusbodService cusbodService, 
			CusbodVO cusbodVO, String pageNum, String type, String keyword, HttpServletRequest request,
			String delUuidList, String del, RedirectAttributes rttr, String url)  throws IllegalStateException, IOException {
		int 		csnum			= Integer.parseInt(String.valueOf(cusbodVO.getCsnum()));
		String 		cusid 			= cusbodVO.getCusid();
		String 		ctype			= cusbodVO.getCtype();
		//System.out.println("========== POST modify - 신규 첨부파일 업로드 완료 ==========");
		
		if (delUuidList != null && !del.equals(delUuidList)) {
			galleryDeleteFiles(delUuidList); // 첨부파일 삭제하기
		}
		
		// 3) 테이블 작업
		//    boardVO 게시글 수정
		//    attach 테이블에 신규 파일정보(newAttachList)를 insert, 삭제할 정보(delUuidList)를 delete
		
		// update 할 BoardVO 객체의 데이터 설정
		cusbodVO.setCtmid(request.getRemoteAddr()); // 사용자 IP주소 저장
		
		// 글번호에 해당하는 게시글 수정, 첨부파일정보 수정(insert, delete) - 한개의 트랜잭션으로 처리
		cusbodService.update(cusbodVO);
		
		rttr.addAttribute("csnum", cusbodVO.getCsnum());
		rttr.addAttribute("cusid", cusbodVO.getCusid());
		rttr.addAttribute("ctype", ctype);
		rttr.addAttribute("pageNum", pageNum);
		rttr.addAttribute("keyword", keyword);
		rttr.addAttribute("type", type);
		
		return url;
	}
	
	public static String getRemove(AttachService attachService, CusbodService cusbodService, String pageNum, 
			String keyword, String type, int csnum, String ctype, String url, RedirectAttributes rttr) {
		// ============ 첨부파일 삭제 ============
		
		int bno = csnum;
		
		AttachVO attachList = attachService.selectByBno(bno, ctype);
		
		deleteAttachFiles(attachList); // 첨부파일(썸네일도 삭제) 삭제하기
		
		
		// attach와 board 테이블 내용 삭제 - 한개의 트랜잭션 단위로 처리
		cusbodService.deleteCusbodAndAttache(csnum, ctype);
		
		rttr.addAttribute("ctype", ctype);
		rttr.addAttribute("pageNum", pageNum);
		rttr.addAttribute("keyword", keyword);
		rttr.addAttribute("type", type);
		
		return url;
	}
	
	public static String getGalleryRemove(AttachService attachService, CusbodService cusbodService, String pageNum, 
			String keyword, String type, int csnum, String ctype, String uuid, String url, RedirectAttributes rttr) {
		// ============ 첨부파일 삭제 ============
		
		galleryDeleteFiles(uuid); // 첨부파일 삭제하기
		
		//board 테이블 내용 삭제 
		cusbodService.delete(csnum, ctype);
		
		rttr.addAttribute("ctype", ctype);
		rttr.addAttribute("pageNum", pageNum);
		rttr.addAttribute("keyword", keyword);
		rttr.addAttribute("type", type);
		
		return url;
	}
	
	// 답글
	public static String getReplyWrite(CusbodService cusbodService, MultipartFile file, HttpServletRequest request, String pageNum, String keyword, String type, 
			String cusid, String ctype, CusbodVO cusbodVO, Model model, 
			RedirectAttributes rttr, String url) throws IllegalStateException, IOException {
		// 스프링 웹에서는 클라이언트로부터 넘어오는 file 타입의 input 요소의 갯수만큼
		// MultipartFile 타입의 객체로 전달받게 됨. 
		
		
		// insert할 새 글번호 가져오기
		int num = cusbodService.getNextnum(ctype);
		// 첨부파일 업로드(썸네일 이미지 생성) 후 attachList 리턴
		AttachVO attach = uploadFileAndGetAttachList(file, num, cusid, ctype);
		
		//======= insert 할 cusbodVO 객체의 데이터 설정 ===========
		cusbodVO.setCsnum((long) num);
		cusbodVO.setCrdcn(0);
		cusbodVO.setCtype(ctype);
		cusbodVO.setCdate(new Date());
		cusbodVO.setCtmid(request.getRemoteAddr());
		cusbodVO.setAttach(attach);
		
		
		// ※ 현재 boardVO에 들어있는 reRef, reLev, reSeq는 바로 insert될 답글 정보가 아님에 주의!
		//   답글을 남길 대상글에 대한 reRef, reLev, reSeq 정보임에 주의!
		
		/*
		주글, 답글 데이터

		num    subject              re_ref    re_lev    re_seq
		===========================================================
		 6     주글3                  6         0         0
		 4     주글2                  4         0         0
		 5       답글2                4         1         1
		 1     주글1                  1         0         0
		 7       답글2 (*)            1         1         1
		 2       답글1                1         1         1+1=2
		 3         답글1_1            1         2         2+1=3  
		*/
		
		//boardService.addReplyAndAttaches(boardVO, attachList);
		cusbodService.addReply(cusbodVO);
		//=========================================================
		model.addAttribute("cusbod", cusbodVO);
		rttr.addAttribute("csnum", (long) num);
		rttr.addAttribute("cusid", cusid);
		rttr.addAttribute("ctype", ctype);
		rttr.addAttribute("pageNum", pageNum);
		rttr.addAttribute("keyword", keyword);
		rttr.addAttribute("type", type);
		
		return url;
	} // end of getWrite
	
	// 답글
	public static String getGalleryReplyWrite(CusbodService cusbodService, HttpServletRequest request, String pageNum, String keyword, String type, 
			String cusid, String ctype, CusbodVO cusbodVO, Model model, 
			RedirectAttributes rttr, String url) throws IllegalStateException, IOException {
		// 스프링 웹에서는 클라이언트로부터 넘어오는 file 타입의 input 요소의 갯수만큼
		// MultipartFile 타입의 객체로 전달받게 됨. 
		
		
		// insert할 새 글번호 가져오기
		int num = cusbodService.getNextnum(ctype);
		// 첨부파일 업로드(썸네일 이미지 생성) 후 attachList 리턴
		
		//======= insert 할 cusbodVO 객체의 데이터 설정 ===========
		cusbodVO.setCsnum((long) num);
		cusbodVO.setCrdcn(0);
		cusbodVO.setCtype(ctype);
		cusbodVO.setCdate(new Date());
		cusbodVO.setCtmid(request.getRemoteAddr());
		
		
		// ※ 현재 boardVO에 들어있는 reRef, reLev, reSeq는 바로 insert될 답글 정보가 아님에 주의!
		//   답글을 남길 대상글에 대한 reRef, reLev, reSeq 정보임에 주의!
		
		/*
		주글, 답글 데이터

		num    subject              re_ref    re_lev    re_seq
		===========================================================
		 6     주글3                  6         0         0
		 4     주글2                  4         0         0
		 5       답글2                4         1         1
		 1     주글1                  1         0         0
		 7       답글2 (*)            1         1         1
		 2       답글1                1         1         1+1=2
		 3         답글1_1            1         2         2+1=3  
		*/
		
		//boardService.addReplyAndAttaches(boardVO, attachList);
		cusbodService.addGalleryReply(cusbodVO);
		//=========================================================
		model.addAttribute("cusbod", cusbodVO);
		rttr.addAttribute("csnum", (long) num);
		rttr.addAttribute("cusid", cusid);
		rttr.addAttribute("ctype", ctype);
		rttr.addAttribute("pageNum", pageNum);
		rttr.addAttribute("keyword", keyword);
		rttr.addAttribute("type", type);
		
		return url;
	} // end of getGalleryReplyWrite
	
	// 댓글
	public static String getReply(CsreplService csreplService, String pageNum, String keyword, String type, int csnum, 
			String ctype, RedirectAttributes rttr, HttpServletRequest request, String id, String cusid, String url) {
		CsreplVO	csreplVO	= new CsreplVO();
		
		int conum = csreplService.getMaxConum(csnum, ctype);
		
		csreplVO.setCsnum(csnum);
		csreplVO.setCusid(id);
		csreplVO.setConum(conum);
		csreplVO.setCrerf(conum);
		csreplVO.setCtype(ctype);
		csreplVO.setCrelv(0);
		csreplVO.setCresq(0);
		csreplVO.setCnten(request.getParameter("cnten"));
		csreplVO.setCdate(new Date());
		csreplVO.setTrmid(request.getRemoteAddr());
		
		csreplService.insert(csreplVO);
		
		getParameters(csreplVO, rttr, cusid, ctype, pageNum, keyword, type);
		
		return url;
	}
	
	public static String getReplyModify(CsreplService csreplService, String pageNum, String keyword, String type, int csnum, 
			String ctype, RedirectAttributes rttr, HttpServletRequest request, String id, String cusid, int conum, String url) {
		CsreplVO	csreplVO	= new CsreplVO();
		
		if(csreplService.count(csnum, ctype, conum) != 0){
			csreplVO = csreplService.select(csnum, ctype, conum);
			csreplVO.setCdate(new Date());
			csreplVO.setTrmid(request.getRemoteAddr());
			csreplVO.setCnten(request.getParameter("cnten"));
			csreplService.update(csreplVO);
		}
		
		getParameters(csreplVO, rttr, cusid, ctype, pageNum, keyword, type);
		
		return url;
	}
	
	public static String getReplyRe(CsreplService csreplService, String pageNum, String keyword, String type, int csnum, 
			String ctype, RedirectAttributes rttr, HttpServletRequest request, String id, String cusid, String url) {
		
		CsreplVO	csreplVO	= new CsreplVO();
		
		int conum = csreplService.getMaxConum(csnum, ctype);
		
		csreplVO.setCsnum(csnum);
		csreplVO.setConum(conum);
		csreplVO.setCusid(id);
		csreplVO.setCtype(ctype);
		csreplVO.setCrerf(Integer.parseInt(request.getParameter("crerf")));
		csreplVO.setCrelv(Integer.parseInt(request.getParameter("crelv")));
		csreplVO.setCresq(Integer.parseInt(request.getParameter("cresq")));
		csreplVO.setCnten(request.getParameter("cnten"));
		csreplVO.setCdate(new Date());
		csreplVO.setTrmid(request.getRemoteAddr());

		
		csreplService.addReply(csreplVO);
		
		getParameters(csreplVO, rttr, cusid, ctype, pageNum, keyword, ctype);
		
		return url;
	}
	
	public static String getReplyRemove(CsreplService csreplService, String pageNum, String keyword, String type, int csnum, 
			String ctype, RedirectAttributes rttr, HttpServletRequest request, String id, String cusid, int conum, String url) {
		
		csreplService.delete(csnum, ctype, conum);
		
		rttr.addAttribute("csnum", csnum);
		rttr.addAttribute("cusid", cusid);
		rttr.addAttribute("ctype", ctype);
		rttr.addAttribute("pageNum", pageNum);
		rttr.addAttribute("keyword", keyword);
		rttr.addAttribute("type", type);
		
		return url;
	}
	
	private static void getParameters(CsreplVO csreplVO, RedirectAttributes rttr, String cusid, String ctype, String pageNum, String keyword, String type) {
		rttr.addAttribute("csnum", csreplVO.getCsnum());
		rttr.addAttribute("cusid", cusid);
		rttr.addAttribute("ctype", ctype);
		rttr.addAttribute("pageNum", pageNum);
		rttr.addAttribute("keyword", keyword);
		rttr.addAttribute("type", type);
	}
} // end of BoardUtils