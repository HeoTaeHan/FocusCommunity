<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="nl.captcha.*" %>
<%
   Captcha captcha = (Captcha) session.getAttribute(Captcha.NAME);
   String answer = request.getParameter("answer"); //사용자가 입력한 문자열
   if ( answer != null && !"".equals(answer) ) {

       if (captcha.isCorrect(answer)) { //사용자가 입력한 문자열과 CaptCha 클래스가 생성한 문자열
           session.removeAttribute(Captcha.NAME);
           out.print("입력값이 일치합니다.");
       } else {
           out.print("입력값이 일치하지 않습니다.");
       }
   }
%>
