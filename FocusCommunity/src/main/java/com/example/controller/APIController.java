package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.AttachVO;
import com.example.domain.Criteria;
import com.example.domain.CusbodVO;
import com.example.service.AttachService;
import com.example.service.CusbodService;

import net.coobird.thumbnailator.Thumbnailator;

@RestController
@RequestMapping("/api/*")
public class APIController {
	
	@Autowired
	private AttachService attachService;
	
	@Autowired
	private CusbodService cusbodService;
	
	// "년/월/일" 형식의 폴더명을 리턴하는 메소드
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String str = sdf.format(new Date());
		return str;
	} // end of getFolder
	
	// 이미지 파일여부 리턴하는 메소드
	private boolean checkImageType(File file) throws IOException {
		boolean isImage = false;

		String contentType = Files.probeContentType(file.toPath()); // "image/jpg" "image/gif" "image/png"

		isImage = contentType.startsWith("image");
		return isImage;
	} // checkImageType
	
	// 첨부파일 업로드(썸네일 이미지 생성) 후 attachList 리턴하는 메서드
	private AttachVO uploadFileAndGetAttachList(MultipartFile file, int bno, String cusid, String ctype) throws IllegalStateException, IOException {
			
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
	private void deleteAttachFiles(AttachVO attachList) {
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
	
	@GetMapping(value = "/boards/{num}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<CusbodVO> getOne(@PathVariable("num") int num) {
		
		// 특정 글 번호에 해당하는 게시글 한 개와 게시글에 포함된 첨부파일 정보 가져오기
		CusbodVO dbBoardVO = cusbodService.selectCusbodAndAttache(num);

		return new ResponseEntity<CusbodVO>(dbBoardVO, HttpStatus.OK);
	} // end of getOne
	
	@GetMapping(value = "/boards/pages/{pageNum}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<CusbodVO>> getListWithPage(@PathVariable("pageNum") int pageNum) {

		Criteria cri = new Criteria();
		cri.setPageNum(pageNum);

		// 특정 페이지 번호에 해당하는 게시글 목록을 가져오기
		List<CusbodVO> boardList = cusbodService.getApiList(cri);

		return new ResponseEntity<List<CusbodVO>>(boardList, HttpStatus.OK);
	} // end of getListWithPage
	
	// 첨부파일 업로드와 함께 주글쓰기 처리
	@PostMapping(value = "/boards", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public CusbodVO write(MultipartFile file, @ModelAttribute("cusid") String cusid, CusbodVO cusbodVO,
			HttpServletRequest request, RedirectAttributes rttr)
			throws IllegalStateException, IOException {
		// 스프링 웹에서는 클라이언트로부터 넘어오는 file 타입의 input 요소의 갯수만큼
		// MultipartFile 타입의 객체로 전달받게 됨. 
		// insert할 새 글번호 가져오기
		int num = cusbodService.getNextnum("I");
		
		// 첨부파일 업로드(썸네일 이미지 생성) 후 attachList 리턴
		AttachVO attachList = uploadFileAndGetAttachList(file, num, cusid, "I");
		//======= insert 할 BoardVO 객체의 데이터 설정 ===========
		cusbodVO.setCsnum((long) num);
		cusbodVO.setCtype("I");
		cusbodVO.setCrdcn(0);
		cusbodVO.setCdate(new Date());
		cusbodVO.setCtmid(request.getRemoteAddr());
		cusbodVO.setCrerf(num);  // 주글일 경우 글그룹번호는 글번호와 동일함
		cusbodVO.setCrelv(0);    // 주글일 경우 들여쓰기 레벨은 0
		cusbodVO.setCresq(0);    // 주글일 경우 글그룹 안에서의 순번은 0
		
		cusbodVO.setAttach(attachList); // 첨부파일 리스트 저장
		
		// 주글 한개(boardVO)와 첨부파일 여러개(attachList)를 한개의 트랜잭션으로 insert 처리함
		cusbodService.inserts(cusbodVO);
		//==============================================================
		
		
		CusbodVO dbBoardVO = cusbodService.selectCusbodAndAttache(num);
		
		return dbBoardVO; // 클라이언트에 보내줄 응답 문자열을 리턴
	} // write
	
	@PutMapping(value = "/boards/{num}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<CusbodVO> modify(MultipartFile file, @ModelAttribute("csnum") int csnum, 
			@ModelAttribute("cusid") String cusid, CusbodVO cusbodVO,
			@RequestParam(name = "delfile", required = false) String delUuidList, HttpServletRequest request) 
					throws IllegalStateException, IOException {
		
		AttachVO delAttachList = null;
		if (delUuidList != null) {
			delAttachList = attachService.select(delUuidList);
			
			deleteAttachFiles(delAttachList); // 첨부파일(썸네일도 삭제) 삭제하기
		}
		
		AttachVO 	newAttachList 	= uploadFileAndGetAttachList(file, csnum, cusid, "I");
		
		// 3) 테이블 작업
		//    boardVO 게시글 수정
		//    attach 테이블에 신규 파일정보(newAttachList)를 insert, 삭제할 정보(delUuidList)를 delete
		
		// update 할 BoardVO 객체의 데이터 설정
		cusbodVO.setCtmid(request.getRemoteAddr()); // 사용자 IP주소 저장
		cusbodVO.setCtype("I");
		
		// 글번호에 해당하는 게시글 수정, 첨부파일정보 수정(insert, delete) - 한개의 트랜잭션으로 처리
		cusbodService.updateCusbodAndInsertAttacheAndDeleteAttache(cusbodVO, newAttachList, delUuidList);
		
		CusbodVO dbBoardVO = cusbodService.selectCusbodAndAttache(csnum);

		return new ResponseEntity<CusbodVO>(dbBoardVO, HttpStatus.OK);
	} // end of modify
	
	@DeleteMapping("/boards/{csnum}")
	public void getDelete(@ModelAttribute("csnum") int csnum) {
		
		AttachVO attachList = attachService.selectByBno(csnum, "I");
		
		
		deleteAttachFiles(attachList); // 첨부파일(썸네일도 삭제) 삭제하기
		
		
		// attach와 board 테이블 내용 삭제 - 한개의 트랜잭션 단위로 처리
		cusbodService.deleteCusbodAndAttache(csnum, "I");
	} // end of getDelete
} // end of APIController