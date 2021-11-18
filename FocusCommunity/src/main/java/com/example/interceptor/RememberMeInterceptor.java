package com.example.interceptor;

import javax.servlet.http.Cookie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.example.domain.CususrVO;
import com.example.service.CususrService;


// HandlerInterceptor 인터페이스 이름은 다른말로 하면 ControllerInterceptor

public class RememberMeInterceptor implements HandlerInterceptor {
	
	@Autowired
	private CususrService cususrService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String activeStr = "";
		
		if(!request.getRequestURL().equals("index") && !String.valueOf(request.getRequestURL()).replace("/","").equals("http:localhost:8090")){
			activeStr = String.valueOf(request.getRequestURL()).substring(22,24);
		}
		
		// 컨트롤러 메서드가 실행되기 전에 호출됨
		// 요청 사용자의 세션 가져오기
		HttpSession session = request.getSession();
		// 세션에 로그인 아이닥 있는지 확인(이미 로그인 되어있는지 확인)
		String id = (String) session.getAttribute("sessionid");
		
		session.setAttribute("url", activeStr);
		// 세션에 로그인 아이디가 없으면 쿠키에서 아이디 찾아서  세션에 저장(로그인 처리)
		if(id == null) {
			// 쿠키 배열객체 가져오기
			Cookie[] cookies = request.getCookies();
			
			// 로그인 상태유지용 쿠키정보를 찾기 
			if(cookies != null) {
				for (Cookie cookie : cookies) {
					if(cookie.getName().equals("loginId")) {
						id = cookie.getValue();
						
						
						// 세션에 저장(로그인 인증 처리)
						session.setAttribute("sessionid", id);
						if(cususrService.count(id) > 0) {
							CususrVO cususr = cususrService.select(id);
							session.setAttribute("cususr", cususr);
						}
					}
				}
			}
		} else {
			if(cususrService.count(id) > 0) {
				CususrVO cususr = cususrService.select(id);
				session.setAttribute("cususr", cususr);
			}
		}
		/*
		 * 리턴값이 true면 호출이 예정되있는 해당 컨트롤러 메서드를 호출함.
		 * 리턴값이 false면 호출이 예정되있는 해당 컨트롤러 메서드를 호출안함.
		 */
		return true;
	} // end of preHandle

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 컨트롤러 메서드 실행직후 view페이지 랜더링 되기 전 호출됨
	} // end of postHandle

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// view페이지가 렌더링 되고 난 후 호출됨
	} // end of afterCompletion
} // end of RememberMeInterceptor