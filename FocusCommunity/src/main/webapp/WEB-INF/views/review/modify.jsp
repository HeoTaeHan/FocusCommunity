<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
  	<meta content="width=device-width, initial-scale=1.0" name="viewport">

  	<title>Focus - 여행후기</title>
	<!-- Top Menu files -->
	<jsp:include page="/WEB-INF/views/include/head.jsp" />  
</head>

<body>
<form id="frm" action="/review/modify" method="post" enctype="multipart/form-data">
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
			<!-- row -->
			<div class="row">
				<div class="col-lg-12">
                	<div class="card">
						<div class="card-header">
							<h4 class="card-title">여행후기 - 글 수정</h4>
						</div>
						<div class="card-body">
							<fmt:formatDate value="${cusbodVO.cdate}" pattern="yyyy-MM-dd hh:mm:ss" var="strBirth" />
							<input type="hidden" name="cusid" id="cusid" value="${sessionid}"/>
							<input type="hidden" name="csnum" id="csnum" value="${cusbodVO.csnum}"/>
							<input type="hidden" name="pageNum" value="${ pageNum }">
							<input type="hidden" name="ctype" id="ctype" value="${ cusbodVO.ctype }">
							<input type="hidden" name="type" value="${ type }">
							<input type="hidden" name="keyword" value="${ keyword }">
							<input type="hidden" name="uuid" value="${ cusbodVO.filetype }">
							<input type="hidden" name="del" id="del" value="${ cusbodVO.filetype }">
							<div class="row">
								<div class="form-group">
									<input type="text" class="form-control" id="title" class="validate" name="csbjt" value="${cusbodVO.csbjt}" placeholder="제목을 입력해주세요.">
								</div>
								<div class="form-group note-editable">
									<textarea id="content" class="summernote" name="ccont" rows="25" style="width:100%" placeholder="내용을 입력해주세요.">${cusbodVO.ccont}</textarea>
								</div>
							</div>
							<br><br><br>
							<div class="d-flex justify-content-center">
								<button class="btn btn-info btn-left btn-sl-sm mb-5" type="button" id="savebutton" onclick="save();">
                                	<i class="bx bx-highlight"></i> 글수정
								</button>
								&nbsp;&nbsp;
								<button class="btn btn-dark btn-right btn-sl-sm mb-5" type="button" onclick="location.href='/review/list?pageNum=${ pageNum }&keyword=${keyword}&type=${type}'"> 글목록</button>
							</div>								
						</div>
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
	<jsp:include page="/WEB-INF/views/include/commonJs.jsp" />
	<script>
		$('#content').summernote({
				height: 500,                 // 에디터 높이
				minHeight: null,             // 최소 높이
				maxHeight: null,             // 최대 높이
				focus: true,                  // 에디터 로딩후 포커스를 맞출지 여부
				lang: "ko-KR",					// 한글 설정
				placeholder: '최대 2048자까지 쓸 수 있습니다',	//placeholder 설정
				callbacks: {	//여기 부분이 이미지를 첨부하는 부분
					onImageUpload : function(files) {
						uploadSummernoteImageFile(files[0],this);
					},
					onPaste: function (e) {
						var clipboardData = e.originalEvent.clipboardData;
						if (clipboardData && clipboardData.items && clipboardData.items.length) {
							var item = clipboardData.items[0];
							if (item.kind === 'file' && item.type.indexOf('image/') !== -1) {
								e.preventDefault();
							}
						}
					}
				}
		});
		
		
		
		/**
		* 이미지 파일 업로드
		*/
		function uploadSummernoteImageFile(file, editor) {
			data = new FormData();
			data.append("file", file);
			$.ajax({
				data : data,
				type : "POST",
				url : "/uploadSummernoteImageFile",
				contentType : false,
				processData : false,
				success : function(data) {
			    	//항상 업로드된 파일의 url이 있어야 한다.
			    	$("#del").val(data.save);
					$(editor).summernote('insertImage', data.url);
				}
			});
		}
		
		$("div.note-editable").on('drop',function(e){
	         for(i=0; i< e.originalEvent.dataTransfer.files.length; i++){
	         	uploadSummernoteImageFile(e.originalEvent.dataTransfer.files[i],$("#content")[0]);
	         }
	        e.preventDefault();
	   })
	    //전송버튼
	    $("#savebutton").click(function(){
	        //폼 submit
			$("#frm").submit();
	    })
	</script>
</form>	
</body>
</html>