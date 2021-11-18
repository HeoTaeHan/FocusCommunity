package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FileUtils;

import com.example.domain.CrecomVO;
import com.example.service.CrecomService;
import com.example.service.CsreplService;
import com.example.service.CususrService;
import com.google.gson.JsonObject;


@Controller // @Component 계열 애노테이션
public class HomeController {
	
	@Autowired
	private CsreplService csreplService;
	
	@Autowired
	private CrecomService crecomService;
	
	@Autowired
	private CususrService cususrService;
	
	@GetMapping(value = {"/", "/index"})
	public String home() {
		return "index";
	} // end of home
	
	@GetMapping("/display")
	@ResponseBody // 컨트롤러 메서드가 리턴하는 데이터 자체를 바로 응답으로 주고자 할 경우 사용함
	public ResponseEntity<byte[]> getImageFile(String fileName) throws IOException {
		File file = new File("C:/LCJ/upload", fileName);
		
		HttpHeaders headers		= new HttpHeaders();
		String		contentType = Files.probeContentType(file.toPath());
		headers.add("Content-Type", contentType);
		
		byte[]					imageData		= FileCopyUtils.copyToByteArray(file);
		ResponseEntity<byte[]> 	responseEntity 	= new ResponseEntity<byte[]>(imageData, headers, HttpStatus.OK);
		
		return responseEntity;
	} // end of getImageFile
	
	@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(String fileName) throws UnsupportedEncodingException {
		File file = new File("C:/LCJ/upload", fileName);
		
		Resource resource = new FileSystemResource(file);
		
		if(!resource.exists()) { // 다운로드할 파일이 존재하지 않으면
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND); // 404코드. 자원없음 응답코드 보내고 종료.
		}
		
		// 다운로드할 파일이 존재하면
		String 		resourceName 	= resource.getFilename();
		int 		beginIdenx		= resourceName.indexOf("_") + 1;
		String		originFilename	= resourceName.substring(beginIdenx); // 순수 파일면 구하기
		
		// 다운로드 파일형식으로 변환
		// 다운로드 파일명의 문자셋을 utf-8에서 iso-8859-1로 변환
		String		downFilename	= new String(originFilename.getBytes("utf-8"), "iso-8859-1");
		
		HttpHeaders	headers			= new HttpHeaders();
		// 애노테이션의 produces 속성으로 대체함
		headers.add("Content-Disposition", "attachment; filename=" + downFilename); //  
		
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	} // end of downloadFile
	
	@PostMapping("/BoardIdCheckServlet")
	@ResponseBody
	public void BoardIdCheckServlet(String cusid, HttpServletResponse res) throws IOException {
		res.getWriter().write((new StringBuilder(String.valueOf(cususrService.count(cusid)))).toString());
	} // end of BoardIdCheckServlet
	
	@PostMapping("/BoardEmailCheckServlet")
	@ResponseBody
	public void BoardEmailCheckServlet(String email, HttpServletResponse res) throws IOException {
		res.getWriter().write((new StringBuilder(String.valueOf(cususrService.countByEmail(email)))).toString());
	} // end of BoardIdCheckServlet
	
	@PostMapping("/BoardRecomCheckServlet")
	@ResponseBody
	public void BoardRecomCheckServlet(String cusid, Model model, HttpServletResponse res) throws IOException {
		res.getWriter().write((new StringBuilder(String.valueOf(crecom(cusid, model)))).toString());
	}
	
	public int crecom(String cusid, Model model) {
		String 	id		= cusid.substring(0, cusid.lastIndexOf("*"));
		String 	temp	=  cusid.substring(cusid.lastIndexOf("*") + 1);
		int 	csnum	= Integer.parseInt(temp.substring(0, temp.lastIndexOf("_")));
		String 	ctype 	= cusid.substring(cusid.lastIndexOf("_")+1);
		
		CrecomVO crecomVO = new CrecomVO();
        if(crecomService.count(csnum, ctype, id) == 0) {
            crecomVO.setCsnum(csnum);
            crecomVO.setCusid(id);
            crecomVO.setCtype(ctype);
            crecomVO.setRecom(1);
            crecomService.insert(crecomVO);
        } else {
            crecomVO = crecomService.select(csnum, ctype, id);
            if(crecomVO.getRecom() <= 0) {
            	crecomVO.setRecom(crecomVO.getRecom() + 1);
            } else {
            	crecomVO.setRecom(crecomVO.getRecom() - 1);
            }
            crecomService.update(crecomVO);
        }
        
        model.addAttribute("crecom", crecomVO);
        
        return crecomService.sumCsnum(csnum);
	} // end of crecom
	
	@PostMapping(value="/uploadSummernoteImageFile", produces = "application/json")
	@ResponseBody
	public String uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) {
		
		JsonObject jsonObject = new JsonObject();
		
		String fileRoot = "D:\\upload\\";	//저장될 외부 파일 경로
		String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자
				
		String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명
		
		File targetFile = new File(fileRoot + savedFileName);	
		
		try {
			InputStream fileStream = multipartFile.getInputStream();
			FileUtils.copyInputStreamToFile(fileStream, targetFile);	//파일 저장
			jsonObject.addProperty("url", "/img/"+savedFileName);
			jsonObject.addProperty("save", savedFileName);
			jsonObject.addProperty("responseCode", "success");
				
		} catch (IOException e) {
			FileUtils.deleteQuietly(targetFile);	//저장된 파일 삭제
			jsonObject.addProperty("responseCode", "error");
			e.printStackTrace();
		}
		
		return jsonObject.toString();
	}
} // end of HomeController