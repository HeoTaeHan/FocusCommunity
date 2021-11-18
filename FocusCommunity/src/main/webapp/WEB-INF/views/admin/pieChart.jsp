<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
  	<meta content="width=device-width, initial-scale=1.0" name="viewport">

  	<title>Focus - 파이 그래프(회원별 글 작성수)</title>
	<!-- Top Menu files -->
	<link rel="stylesheet" href="/resources/vendor/chartist/css/chartist.min.css">
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
		Sidebar start
	***********************************-->
	<jsp:include page="/WEB-INF/views/include/left.jsp" />
	<!--**********************************
		Sidebar end
	***********************************-->
	
	<!--**********************************
		content start
	***********************************-->
    <div class="content-body">
		<div class="container-fluid">
			<div class="col-lg-6 col-sm-6">
				<div class="card">
                	<div class="card-header">
                    	<h4 class="card-title">파이 그래프 - 회원별 글 작성수</h4>
					</div>
                    <div class="card-body">
                    	<canvas id="myChart" width="400" height="300"></canvas>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--**********************************
		content end
	***********************************-->
	
	<!-- ======= Footer ======= -->
	<jsp:include page="/WEB-INF/views/include/bottom.jsp" />
	<!-- End Footer -->

	<!-- Vendor JS Files -->
	<!-- Chart Chartist plugin files -->
    <script src="/resources/vendor/chartist/js/chartist.min.js"></script>
    <script src="/resources/vendor/chartist-plugin-tooltips/js/chartist-plugin-tooltip.min.js"></script>
	<jsp:include page="/WEB-INF/views/include/commonJs.jsp" />
	<script>
		//Simple pie chart
		$(document).ready(function() {
			requestChartData();
		});
		
		function requestChartData() {
			$.ajax({
				url: '/admin/gender-per-count',
				method: 'GET',
				success: function (data) {
					drawChart(data.labelList, data.dataList);
				}
			});
		} // requestChartData
		
		function drawChart(labelList, dataList) {
			// 차트를 그릴 캔버스 객체 가져오기
			var canvas = document.getElementById('myChart');
			// 캔버스 객체에 그림을 그릴 컨텍스트 객체 가져오기
			var context = canvas.getContext('2d');
			// 파이 차트 그리기
			var myChart = new Chart(context, {
				type: 'pie',
				data: {
					labels: labelList, // 차트 레이블 적용
					datasets: [
						{
							data: dataList, // 차트 데이터 적용
							backgroundColor: ['green', 'yellow', 'blue', 'gray'],
							hoverBackgroundColor: ['lightgreen', 'lightyellow', 'lightblue', 'lightgray']
						}
					]
				},
				options: {
					responsive: false
				}
			});
		}
	</script>
	
</body>
</html>