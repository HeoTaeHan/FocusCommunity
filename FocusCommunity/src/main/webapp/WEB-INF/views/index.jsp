<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
  	<meta content="width=device-width, initial-scale=1.0" name="viewport">

  	<title>Focus - Index</title>

	<jsp:include page="/WEB-INF/views/include/head.jsp" />  
</head>

<body>
    	<!--**********************************
        	Nav header start
    	***********************************-->
    	<jsp:include page="/WEB-INF/views/include/top.jsp" />
    	<!--**********************************
        	Nav header end
    	***********************************-->

    	<!--**********************************
        	Header start
    	***********************************-->
    
    	<!--**********************************
        	Header end ti-comment-alt
    	***********************************-->

    	<!--**********************************
        	Sidebar start
    	***********************************-->
    	<jsp:include page="/WEB-INF/views/include/left.jsp" />
  		<!-- ======= Top Bar ======= -->
 

  		<!-- ======= Header ======= -->
  

  		<!-- ======= Hero Section ======= -->
  		<section id="hero" class="d-flex align-items-center">
    		<div class="container" data-aos="zoom-out" data-aos-delay="100">
      			<h1>Welcome to <span>Focus</span></h1>
      			<h2>Focus는 건전한 여행 문화 확립을 목적으로 탄생하였습니다.</h2>
      			<div class="d-flex">
        			<a href="https://youtu.be/MeSVJb837ro" class="glightbox btn-watch-video"><i class="bi bi-play-circle"></i><span>추천 여행지</span></a>
      			</div>
    		</div>
  		</section>
  		<!-- End Hero -->
  		<main id="main">

    		<!-- ======= Services Section ======= -->
    		<jsp:include page="/WEB-INF/views/include/services.jsp" />
    		<!-- End Services Section -->
	
    		<!-- ======= Frequently Asked Questions Section ======= -->
    		<jsp:include page="/WEB-INF/views/include/faq.jsp" />
    		<!-- End Frequently Asked Questions Section -->
  		</main><!-- End #main -->
  		<!-- ======= Footer ======= -->
  		<jsp:include page="/WEB-INF/views/include/bottom.jsp" />
		<!-- End Footer -->

  		<!-- Vendor JS Files -->
  		<jsp:include page="/WEB-INF/views/include/commonJs.jsp" />
</body>
</html>