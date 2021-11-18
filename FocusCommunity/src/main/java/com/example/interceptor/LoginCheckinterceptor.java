package com.example.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.domain.CususrVO;
import com.example.service.CususrService;

public class LoginCheckinterceptor implements HandlerInterceptor {
	
	@Autowired
	private CususrService cususrService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		HttpSession session = request.getSession();
		// 세션에 로그인 아이닥 있는지 확인(이미 로그인 되어있는지 확인)
		String id = (String) session.getAttribute("sessionid");
		// 세션에 로그인 아이디가 없으면 쿠키에서 아이디 찾아서  세션에 저장(로그인 처리)
		
		CususrVO cususrVO = cususrService.select(id);
		
		if(id == null && cususrVO != null) {
			response.sendRedirect("/member/login");
			return false; // false를 리턴하여 호출 예정이었던 컨트롤러 메서드를 호출 안함!
		}
		
		return true;
	} // end of preHandle
} // end of LoginCheckinterceptor