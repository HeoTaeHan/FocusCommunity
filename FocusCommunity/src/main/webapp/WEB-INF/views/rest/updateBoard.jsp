<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta content="width=device-width, initial-scale=1.0" name="viewport">
	<title>Focus - 게시글 수정</title>
	<jsp:include page="/WEB-INF/views/include/head.jsp" /> 
	<style>
	span.delete-oldfile, span.delete-addfile {
		background-color: yellow;
		color: red;
		font-weight: bold;
		cursor: pointer;
		border: 1px solid black;
		border-radius: 5px;
		padding: 2px;
		margin-left: 10px;
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
			<div class="row">
				<div class="col-lg-12">
					<div class="card">
						<div class="card-header">
							<h4 class="card-title">글 수정</h4>
						</div>
						<form id="frm" enctype="multipart/form-data">
							<div class="card-body">
								<input type="hidden" name="cusid" id="cusid" value="${sessionid}"/>
								<div class="row">
									<div class="form-group">
										<input class="form-control" type="number" name="csnum" id="bno" placeholder="글번호를 검색해주세요.">
									</div>
									<div class="form-group">
										<input class="form-control" type="text" name="csbjt" id="subject">
									</div>
									<div class="form-group note-editable">
										<textarea class="form-control" rows="25" style="width:100%; resize: none;" name="ccont" id="content"></textarea>
									</div>
									<div class="form-group read-content-attachment" style="margin-left: 15px">
										<h6>
											<i class="fa fa-download mb-2"></i> 첨부파일
										</h6>
										<div id="oldFileBox">
												
										</div>
										<div id="newFileBox">
										
										</div>
									</div>
								</div>
								<br><br><br>
								<div class="d-flex justify-content-center">
									<button class="btn btn-info btn-left btn-sl-sm mb-5" type="button" id="btnModify">
	                                	<i class="bx bx-highlight"></i> 글수정
									</button>
								</div>								
							</div>
						</form>
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
	<script src="/resources/js/jquery.serializeObject.min.js"></script>

	<script>

		var fileCount = 0; // 파일 입력요소 갯수
		var fileIndex = 0;
		
		$('input#bno').on('change', function () {
			
			fileCount = 0;
			$('div#newFileBox').empty();
			$('div#oldFileBox').empty();
			
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
			
			$('input#id').val(board.cusid);
			$('input#subject').val(board.csbjt);
			$('textarea#content').val(board.ccont);
			
			// 첨부파일 정보 보이기
			
			
			var str = '';
			if(typeof board.uploadpath != 'undefined' && board.uploadpath != null){
				str += `
						<input type="hidden" name="oldfile" value="\${board.uuid}">
						<div class="form-group">
							<span class="filename">\${board.filename}</span>
							<button type="button" class="btn btn-danger delete-oldfile" onclick="clicks(event)">X</button>
						</div>
				
				`;
			} else {
				str += `
					<div class="form-group" align="center">
            			<input type="file" name="file"  class="form-control">
           			</div>
				`;	
			}
			
			$('div#oldFileBox').html(str);
			
		} // showData
		
		function clicks(event) {
			event.preventDefault(); // a태그 기본동작 막기
			
			
			swal({ title: "첨부파일 삭제", 
				text: "첨부파일을 정말 삭제하시겠습니까?", 
				type: "warning", 
				showCancelButton: !0, 
				confirmButtonColor: "#DD6B55", 
				confirmButtonText: "예", 
				cancelButtonText: '아니요',
				closeOnConfirm: !1
			}).then((result) => {
				if (result.value == true) {
		    		$('button.delete-oldfile').parent().prev().prop('name', 'delfile'); // oldfile -> delfile(서버에서 찾을 파라미터값.)
			
		    		// 현재 클릭한 요소의 직꼐부모(parent) 요소를 제거하기
		    		$('button.delete-oldfile').parent().remove();
		    		
		    		var str = `
		    			<div class="form-group" align="center">
			            	<input type="file" name="file"  class="form-control">
		            	</div>
		    		`;
		    	
		    		$('div#newFileBox').append(str);
				}
			});
		}
		
		// 동적 이벤트 연결 (이벤트 위임 방식)
		$('#newFileBox').on('click', 'span.delete-addfile', function () {
			$(this).parent().remove();
			
		});
		
		$('div#oldFileBox').on('click', 'span.delete-oldfile', function () {
			
			$(this).parent().prev().prop('name', 'delfile'); // hidden input 요소의 name속성 변경
			
			$(this).parent().remove();
			
		});
		
		///////////////////////
		
		// 글수정 버튼 클릭했을 때
		$('#btnModify').on('click', function () {
			
			var form = $('form#frm')[0];
			
			var formData = new FormData(form); // 쿼리스트링으로 넘겨줌
			
			var bno = $('input#bno').val(); // parseInt("2")  Number("2") -> 2
			
			$.ajax({
				url: '/api/boards/' + bno,
				//enctype: 'multipart/form-data',
				method: 'PUT',
				data: formData,
				processData: false, // 파일전송시 false 설정 필수!
				contentType: false, // 파일전송시 false 설정 필수!
				success: function (data) {
	
					swal('성공', '글수정 성공!','success').then((result) => {
					
						$('form#frm')[0].reset();
						$('div#newFileBox').empty();
						$('div#oldFileBox').empty();
					});	
				} // success
			});
		});
	</script>
</body>
</html>






