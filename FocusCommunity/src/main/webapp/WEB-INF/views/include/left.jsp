<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="quixnav">
	<div class="quixnav-scroll">
		<ul class="metismenu" id="menu">
			<li>
				<a class="has-arrow" href="javascript:void()" aria-expanded="false"> 
					<i class="bx bxs-plane-alt"></i>
					<span class="nav-text">여행</span>
				</a>
				<ul>
					<li><a href="/info/list">여행정보</a></li>
					<li><a href="/review/list">여행후기</a></li>
				</ul>
			</li>
			<li>
				<a class="has-arrow" href="javascript:void()" aria-expanded="false"> 
					<i class="icon icon-single-04"></i> 
					<span class="nav-text">커뮤니티</span>
				</a>
				<ul aria-expanded="false">
					<li><a href="/talk/list" ${url eq 'ta' ? "class='active'" : ""}>잡담</a></li>
					<li><a href="/qna/list">질문</a></li>
				</ul>
			</li>
			<c:if test="${sessionid eq  'admin'}">
				<li>
					<a class="has-arrow" href="javascript:void()" aria-expanded="false">
						 <i class="bx bx-bar-chart"></i> 
						 <span class="nav-text">차트</span>
					</a>
					<ul aria-expanded="false">
						<li><a href="/admin/pieChart">파이 그래프(회원별 글 작성수)</a></li>
					</ul>
				</li>
				<li>
					<a class="has-arrow" href="javascript:void()" aria-expanded="false">
						<i class="bx bx-highlight"></i>
						<span class="nav-text">메일전송</span>
					</a>
					<ul aria-expanded="false">
						<li><a href="/admin/email">MultiPartEmail 전송하기</a></li>
					</ul>
				</li>
				<li>
					<a class="has-arrow" href="javascript:void()" aria-expanded="false">
						<i class="bx bxs-detail"></i>
						<span class="nav-text">레스트API</span>
					</a>
					<ul aria-expanded="false">
						<li><a href="/rest/selectOneBoard">글 상세보기</a></li>
						<li><a href="/rest/selectPagingBoards">글 목록 보기</a></li>
						<li><a href="/rest/insertBoard">글 쓰기</a></li>
						<li><a href="/rest/updateBoard">글 수정</a></li>
						<li><a href="/rest/deleteBoard">글 삭제</a></li>
					</ul>
				</li>
			</c:if>
			<c:if test="${not empty sessionid}">
				<li>
					<a class="has-arrow" href="javascript:void()" aria-expanded="false">
						 <i class="bx bx-merge"></i> 
						 <span class="nav-text">채팅</span>
					</a>
					<ul aria-expanded="false">
						<li><a href="/websocket/chat">채팅</a></li>
					</ul>
				</li>
			</c:if>
			<li class="nav-label"></li>
		</ul>
	</div>
</div>