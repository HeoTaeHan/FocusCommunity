<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="main-wrapper">
	<div class="nav-header">
        	<a href="/" class="brand-logo">
            	<img class="logo-abbr" src="/resources/images/logo.png" alt="">
            	<img class="logo-compact" src="/resources/images/logo-text.png" alt="">
            	<img class="brand-title" src="/resources/images/logo-text.png" alt="">
        	</a>

        	<div class="nav-control">
            	<div class="hamburger">
                	<span class="line"></span><span class="line"></span><span class="line"></span>
            	</div>
        	</div>
    	</div>
    	<!--**********************************
	        Nav header end
    	***********************************-->

    	<!--**********************************
        	Header start
    	***********************************-->
    	<div class="header">
        	<div class="header-content">
            	<nav class="navbar navbar-expand">
                	<div class="collapse navbar-collapse justify-content-between">
                    	<div class="header-left">
                        	<div class="search_bar dropdown">
                            	<div class="container d-flex align-items-center justify-content-between"></div>
                        	</div>
                    	</div>

                    	<ul class="navbar-nav header-right">
                        	<nav id="navbar" class="navbar">
                          		<ul>
                            		<li class="dropdown"><a class="nav-link scrollto ${url eq 'to' ? 'active' : ''}" href="/total/list"><span>전체글</span></a></li>
                            		<li class="dropdown"><a class="nav-link scrollto ${url eq 'em' ? 'active' : ''}" href="/empath/list"><span>공감글</span></a></li>
                            		<li class="dropdown">
                            			<a href="#">
                            				<span>
                            					<c:choose>
                            						<c:when test="${empty sessionid }">
                            							회원정보
                            						</c:when>
                            						<c:otherwise>
                            							${sessionScope.cususr.cname}(${sessionScope.sessionid})님
                            						</c:otherwise>
                            					</c:choose>
                            				</span> 
                            				<i class="bi bi-chevron-down"></i>
                            			</a>
	                              		<ul style="margin-left:-100px;">
	                              			<c:choose>
	                              				<c:when test="${empty sessionid }">
			                                		<li>
			                                  			<a href="/member/join" class="dropdown-item">
			                                    			<i class="icon-user"></i>
			                                    			<span class="ml-2">회원가입 </span>
			                                  			</a>
			                                		</li>
			                                		<li>
			                                  			<a href="/member/login" class="dropdown-item">
			                                    			<i class="icon-key"></i>
			                                    			<span class="ml-2">로그인 </span>
			                                  			</a>
			                                		</li>
	                              				</c:when>
	                              				<c:when test="${sessionid eq 'admin' }">
	                              					<li>
	                              						<a href="/admin/list" class="dropdown-item">
			                                    			<i class="icon-user"></i>
			                                    			<span class="ml-2">회원정보관리 </span>
			                                  			</a>
	                              					</li>
	                              					<li>
			                                  			<a href="/member/logout" class="dropdown-item">
			                                    			<i class="icon-key"></i>
			                                    			<span class="ml-2">로그아웃 </span>
			                                  			</a>
			                                		</li>
	                              				</c:when>
	                              				<c:otherwise>
	                              					<li>
			                                  			<a href="/member/modify" class="dropdown-item">
			                                    			<i class="icon-user"></i>
			                                    			<span class="ml-2">회원정보수정 </span>
			                                  			</a>
			                                		</li>
			                                		<li>
			                                  			<a href="/member/logout" class="dropdown-item">
			                                    			<i class="icon-key"></i>
			                                    			<span class="ml-2">로그아웃 </span>
			                                  			</a>
			                                		</li>
	                              				</c:otherwise>
	                              			</c:choose>
	                              		</ul>
                            		</li>
                          		</ul>
                        	</nav>
                    	</ul>
                	</div>
            	</nav>
        	</div>
    	</div>