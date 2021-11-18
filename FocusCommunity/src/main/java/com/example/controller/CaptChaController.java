package com.example.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import captcha.AudioCaptCha;
import captcha.CaptCha;
import nl.captcha.Captcha;

@Controller
@RequestMapping("/captcha/*")
public class CaptChaController {
	@GetMapping("/CaptChaAudio")
	@ResponseBody
	public void CaptChaAudio(String ans, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
		if ("y".equals(ans)) {

	            Captcha captcha = (Captcha) session.getAttribute(Captcha.NAME);
	
	            ans = captcha.getAnswer();
	
	
	            return;
	
	     }
	
	
	     new AudioCaptCha().getAudioCaptCha(request, response, ans);
	} // end of CaptChaAudio
	
	@GetMapping("/CaptChaImg")
	@ResponseBody
	public void CaptChaImg(HttpServletRequest request, HttpServletResponse response) throws IOException {
		new CaptCha().getCaptCha(request, response);
	} // end of CaptChaImg
	
	@PostMapping("/CaptchaSubmit")
	@ResponseBody
	public ResponseEntity<String> CaptchaSubmit(HttpServletRequest request, HttpSession session) {
		Captcha captcha = (Captcha) session.getAttribute(Captcha.NAME);
		String answer	= request.getParameter("answer"); //사용자가 입력한 문자열
		String result   = "";
		if ( answer != null && !"".equals(answer) ) {
			if (captcha.isCorrect(answer)) { //사용자가 입력한 문자열과 CaptCha 클래스가 생성한 문자열
				session.removeAttribute(Captcha.NAME);
				result = "입력값이 일치합니다.";
			} else {
				result = "입력값이 일치하지 않습니다.";
			}
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/html; charset=UTF-8");
		
		return new ResponseEntity<String>(result, headers, HttpStatus.OK);
	} // end of CaptchaSubmit
} // end of CaptChaController
