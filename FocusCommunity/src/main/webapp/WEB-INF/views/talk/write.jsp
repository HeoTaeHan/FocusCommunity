<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
  	<meta content="width=device-width, initial-scale=1.0" name="viewport">

  	<title>Focus - 잡담</title>
	<!-- Top Menu files -->
	<jsp:include page="/WEB-INF/views/include/head.jsp" />  
</head>

<body>
<form id="frm" action="/talk/write" method="post" enctype="multipart/form-data">
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
							<h4 class="card-title">잡담 - 글쓰기</h4>
						</div>
						<div class="card-body">
							<input type="hidden" name="cusid" value="${ sessionid }">
							<input type="hidden" name="pageNum" value="${ pageNum }">
							<input type="hidden" name="ctype" value="${ ctype }">
							<input type="hidden" name="type" value="${ type }">
							<input type="hidden" name="keyword" value="${ keyword }">
							<div class="row">
								<div class="form-group">
									<input type="text" class="form-control" id="title" class="validate" name="csbjt" placeholder="제목을 입력해주세요.">
								</div>
								<div class="form-group">
									<textarea id="content" class="form-control" name="ccont" rows="25" style="width:100%" placeholder="내용을 입력해주세요."></textarea>
								</div>
							</div>
							<div class="row">
								<input type="file" name="file" class="form-control drop-zone">
							</div>
							<br><br><br>
							<div class="d-flex justify-content-center">
								<button class="btn btn-primary btn-left btn-sl-sm mb-5" type="button" id="savebutton" onclick="save();">
                                	<i class="bx bx-highlight"></i> 글쓰기
								</button>
								&nbsp;&nbsp;
								<button class="btn btn-dark btn-right btn-sl-sm mb-5" type="button" onclick="location.href='/talk/list?pageNum=${ pageNum }'"> 글목록</button>
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
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="/resources/nse/js/HuskyEZCreator.js" charset="utf-8"></script>
	<script>
		$(function(){
		    //전역변수
		    var obj = [];              
		    //스마트에디터 프레임생성
		    nhn.husky.EZCreator.createInIFrame({
		        oAppRef: obj,
		        elPlaceHolder: "content",
		        sSkinURI: "/resources/nse/SmartEditor2Skin.html",
		        htParams : {
		            // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
		            bUseToolbar : true,            
		            // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
		            bUseVerticalResizer : true,    
		            // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
		            bUseModeChanger : true,
		        }
		    });
		    //전송버튼
		    $("#savebutton").click(function(){
		        //id가 smarteditor인 textarea에 에디터에서 대입
		        obj.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
		        //폼 submit
		        $("#frm").submit();
		    })
		})
	</script>
</form>	
</body>
</html>