<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
  	<meta content="width=device-width, initial-scale=1.0" name="viewport">

  	<title>Focus - 게시글 검색</title>

	<jsp:include page="/WEB-INF/views/include/head.jsp" />  
	
		<style>
		.card-body {
			padding: 50px;
		}
		
		.info-row {
			float: left;
		}
		
		.article-info {
			float: right;
			text-align: right;
		}
		
		.article-info .rec-color {
			color: blue;
		}
		.sep:before {
			content: '|';
			font-size: 1.1em;
			font-weight: 300;
			margin: 0.5em;
			line-height: .9em;
		}
		
		.read-content textarea {
			background: rgb(255, 255, 255);
			color: #828690;
			border-color: rgb(149 133 133/ 60%);
			padding: 0.625rem 1.25rem;
		}
		
		.btn-list .btn-right {
			float: right;
		}
		
		.btn-center {
		    margin:auto;
		    display:block;
		}
		
		.btn {
		border: none;
		-webkit-box-shadow: 0 15px 30px 0 rgb(0 0 0 / 0%);
		}
		
		span.reply-level{
    		display: inline-block;
    	}
    	
    	.table {
		    width: 100%;
		    margin-bottom: 1rem;
    		color: #000000;
		}
		
		.table .td-left {
			border-left: none;
			text-align: right;
			background-color: #EEEEEE;
		}
		
		.table .td-left .a-cursor {
			cursor: pointer;
		}
		
		.table .td-right {
			border-right: none;
			background-color: #EEEEEE;
		}
		
		.table tbody tr td {
		    vertical-align: middle;
		    border-color: #BBB;
		}
		
		.pre-font {
		    display: block;
		    font-size: 100%;
		    color: #212529;
		}
		
		.addrow .reply-comment {
			float: right;
		}
		
		.read-content textarea {
		    background: rgb(255, 255, 255);
		    color: #000000;
		    border-color: rgb(149 133 133/ 60%);
		    padding: 0.625rem 1.25rem;
		}
	</style>
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
	<div class="content-body">
		<div class="container-fluid">
			<!-- row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="card">
						<div class="card-body">
							<div class="number-row">
								<input class="form-control" type="number" name="num" id="bno" placeholder="글번호를 검색해주세요.">
							</div>
							<div class="row">
								<div class="col-12">
									<div class="right-box-padding">
										<div class="read-content">
											<div class="media pt-3">
											<fmt:formatDate value="${cusbodVO.cdate}" pattern="yyyy-MM-dd hh:mm:ss" var="cdate" />
												<div class="media-body">
													<h5 class="text-primary" id="subject"><span id="subject"></span></h5>
													<div class="info-row">
														<span class="user-info"><span id="mid"></span></span>
													</div>
													<div class="article-info">
														<span class="head">공감</span> 
														<span class="body" style="color: #2356FF;">
															<span class="rec-color" id="recom" style="border:0; width:2%"></span>
														</span> 
														<span class="sep">
														</span>
														<span class="head">댓글</span> 
														<span class="body" id="csreplList"></span> 
														<span class="sep"></span> 
														<span class="head">조회수</span> 
														<span class="body" id="crdcn"></span> 
														<span class="date"> 
														<span class="sep"></span> 
														<span class="head">작성일</span> 
														<span class="body" id="cdate"></span>
														</span>
													</div>
												</div>
											</div>
											<hr>
											<div class="read-content-body">
												<pre id="content"></pre>
												<hr>
												<div id="fileArea">
										
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- ======= Footer ======= -->
	<jsp:include page="/WEB-INF/views/include/bottom.jsp" />
	<!-- End Footer -->

	<!-- Vendor JS Files -->
	<jsp:include page="/WEB-INF/views/include/commonJs.jsp" />
	<script src="/resources/js/jquery-3.6.0.js"></script>
	<script>
	
		$('input#bno').on('change', function () {
		
			var bno = $(this).val();
		
			$.ajax({
				url: '/api/boards/' + bno,
				method: 'GET',
				success: function (data) {
					showData(data);
				} // success
			});
		});
	
		function showData(obj) {
			
			// 게시글 정보 보이기
			var board = obj;
			
			const date 	= new Date(board.cdate);
			var year 	= date.getFullYear();
		    var month 	= ("0" + (1 + date.getMonth())).slice(-2);
		    var day 	= ("0" + date.getDate()).slice(-2);
		    var hour 	= ("0" + date.getHours()).slice(-2);
		    var minute 	= ("0" + date.getMinutes()).slice(-2);
		    var second 	= ("0" + date.getSeconds()).slice(-2);
			var today	= year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
			
			$('span#mid').html(board.cusid);
			$('span#subject').html(board.csbjt);
			$('span#csreplList').html(board.cnt);
			$('span#recom').html(board.recom);
			$('span#crdcn').html(board.crdcn);
			$('span#cdate').html(today);
			$('pre#content').html(board.ccont);
			
		
			var str = '';
			
			if (board.uuid != null) {
			
				str += '<ul>'
				if (board.filetype == 'O') {
					// 다운로드할 일반파일 경로
      					var fileCallPath = board.uploadpath + '/' + board.uuid + "_" + board.filename;
				
					str += `
						<li>
           					<a href="/download?fileName=\${fileCallPath}">
               					\${board.filename}
           					</a>
           				</li>
						`;
				} else if (board.filetype == 'I') {
					// 썸네일 이미지 경로
					var fileCallPath = board.uploadpath + '/s_' + board.uuid + "_" + board.filename;
      				// 원본 이미지 경로
      				var fileCallPathOrigin = board.uploadpath + '/' + board.uuid + "_" + board.filename;
				
					str += `
						<li>
           					<a href="/display?fileName=\${fileCallPathOrigin}">
           						<img src="/display?fileName=\${fileCallPath}">
           					</a>
           				</li>
						`;
				}
				str += '</ul>'
			} else { // attachList.length == 0
				str = '첨부파일 없음';
			}
			$('div#fileArea').html(str);
		} // showData
	</script>
</body>
</html>