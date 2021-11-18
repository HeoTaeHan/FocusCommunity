package com.example.websocket;

import java.io.IOException;
import java.util.*;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/*
 * 각 클라이언트가 서버에 연결될때마다 @ServerEndpoint
 * 에노테이션이 붙은 클래스 객체가 매번 별도의 스레드내에서 생성되어 실행되는 구조임
 */
@ServerEndpoint(value = "/chat")
public class SimpleChatEndPoint {
	
	private static final List<Session> SESSION_LIST = new Vector<>(); // 채팅방 1개
	
	@OnOpen
	public void handleOpen(Session session) throws IOException {
		System.out.println("클라이언트 " + session.getId() + "가 서버에 연결됨....");
		
		SESSION_LIST.add(session); // 접속한 사용자(클라이언트)를 리스트에 추가
	} // end of handleOpen
	
	@OnMessage
	public void handleMessage(Session session, String message) throws IOException {
		System.out.println("@OnMessage : 클라이언트 " + session.getId() + " 로부터 메시지를 받음....");
		
		// 브로드캐스팅하기
		for (Session sess : SESSION_LIST) {
			sess.getBasicRemote().sendText(message);
		}
	} // end of handleMessage
	
	@OnClose
	public void handleClose(Session session, CloseReason closeReason) throws IOException {
		System.out.println("@Onclose : 클라이언트" + session.getId() + "와 " + closeReason + "이유로 인해 연결이 끊어짐....");
		
		SESSION_LIST.remove(session);
	} // end of handleClose
	
	@OnError
	public void handleError(Session session, Throwable throwable) {
		System.out.println("@OnError : 현재 클라이언트" + session.getId() + "와 연결중에 에러가 발생됨....");
		
		System.out.println(throwable.getMessage());
		throwable.printStackTrace();
	} // end of handleError
} // end of SimpleChatEndPoint