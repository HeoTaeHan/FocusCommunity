<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
  	<meta content="width=device-width, initial-scale=1.0" name="viewport">

  	<title>Focus - 글 목록 보기</title>

	<jsp:include page="/WEB-INF/views/include/head.jsp" />  
	<style>
		.table {
			color: #000000;
		}
	
		.datatr {
			color: black;
		}
		
		.datatr:hover {
			color:#0099ff;
			cursor: pointer;
		}
		
		.pagination {
			display: flex;
  			justify-content: center;
		}
		
		.overLayer {
			display: none; 
			width: auto; 
			height: autto; 
			border: 1px solid;
		}
		
		.tableLayer {
			height:30px
		}
		
		span.reply-level{
    		display: inline-block;
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
			<fmt:formatDate value="${cusbod.cdate}" pattern="yyyy-MM-dd" var="cdate" />
			<!-- row -->
			<div class="row">
				<div class="col-lg-12">
                	<div class="card">
						<div class="card-header">
							<h4 class="card-title">글목록 보기</h4>
						</div>
						<br>
						<div class="number-row">
							<input class="form-control" type="number" name="num" id="num" style="margin: auto;" placeholder="페이지 번호를 검색해주세요.">
						</div>
						<div class="card-body">
							<div class="table-responsive">
								<table class="table table-responsive-sm" style="cususr:pointer;">
									<thead>
                                    	<tr>
                                        	<th style="text-align:center">번호</th>
                                            <th style="text-align:center">제목</th>
                                            <th style="text-align:center">작성자</th>
                                            <th style="text-align:center">작성일</th>
                                            <th style="text-align:center">조회수</th>
                                            <th style="text-align:center">공감</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
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
		$('input#num').on('change', function () {
				
				var page = $(this).val();
				
				$.ajax({
					url: '/api/boards/pages/' + page,
					method: 'GET',
					success: function (data) {
						
						showData(data);
					}
				});
				
			});
			
			function showData(array) {
				str = '';
				
				if (array.length > 0) {
					for (var board of array) {
						const date 	= new Date(board.cdate);
						var year 	= date.getFullYear();
					    var month 	= ("0" + (1 + date.getMonth())).slice(-2);
					    var day 	= ("0" + date.getDate()).slice(-2);
					    var hour 	= ("0" + date.getHours()).slice(-2);
					    var minute 	= ("0" + date.getMinutes()).slice(-2);
					    var second 	= ("0" + date.getSeconds()).slice(-2);
						var today	= year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
						
						str += `
							<tr>
								<td width="10%" align="center">\${board.csnum}</td>
								<td width="40%" align="center">\${board.csbjt}</td>
								<td width="10%" align="center">\${board.cusid}</td>
								<td width="20%" align="center">\${today}</td>
								<td width="10%" align="center">\${board.crdcn}</td>
								<td width="10%" align="center">\${board.recom}</td>
							</tr>
						`;
					} // for
				} else {
					str += `
						<tr>
							<td colspan="6" align="center">게시판 글이 없습니다.</td>
						</tr>
					`;
				}
				
				$('tbody').html(str);
			} // showData
			
			// 페이지 로딩후 바로 실행하는 명령문 =====
			$(window).on('scroll', function () {
				console.log('scroll 이벤트 발생');
				
				// 스크롤 이벤트 문서 최하단 감지하면, 데이터 가져와서 화면에 덧붙이기
				if ($(window).scrollTop() + $(window).height() >= $(document).height()) {
					getData(page);
					page++;
				}
			});
	</script>
</body>
</html>






