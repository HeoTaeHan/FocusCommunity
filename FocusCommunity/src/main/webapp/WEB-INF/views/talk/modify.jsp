<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
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
<form id="frm" action="/talk/modify" method="post" enctype="multipart/form-data">
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
							<h4 class="card-title">잡담 - 글 수정</h4>
						</div>
						<div class="card-body">
							<fmt:formatDate value="${cusbodVO.cdate}" pattern="yyyy-MM-dd hh:mm:ss" var="strBirth" />
							<input type="hidden" name="cusid" id="cusid" value="${sessionid}"/>
							<input type="hidden" name="csnum" id="csnum" value="${cusbodVO.csnum}"/>
							<input type="hidden" name="pageNum" value="${ pageNum }">
							<input type="hidden" name="ctype" id="ctype" value="${ cusbodVO.ctype }">
							<input type="hidden" name="type" value="${ type }">
							<input type="hidden" name="keyword" value="${ keyword }">
							<div class="row">
								<div class="form-group">
									<input type="text" class="form-control" id="title" class="validate" name="csbjt" value="${cusbodVO.csbjt}" placeholder="제목을 입력해주세요.">
								</div>
								<div class="form-group">
									<textarea id="content" class="form-control" name="ccont" rows="25" style="width:100%" placeholder="내용을 입력해주세요.">${cusbodVO.ccont}</textarea>
								</div>
							</div>
							<div class="read-content-attachment">
								<h6>
									<i class="fa fa-download mb-2"></i> 첨부파일
								</h6>
								<c:if test="${not empty cusbodVO.uuid}">
									<div class="form-group" id="oldFileBox">
										<input type="hidden" name="oldfile" value="${cusbodVO.uuid}">
		               					<div class="form-group">
		                					<span class="filename">${cusbodVO.filename}</span>
		                					<button class="btn btn-danger delete-oldfile">X</button>
			                    		</div>
									</div>
								</c:if>
							</div>		
							<!-- 신규 첨부파일 영역 -->
							<div class="form-group" align="center" id="newFileBox">
								<c:if test="${empty cusbodVO.uuid}">
									<div class="form-group" align="center">
						            	<input type="file" name="file"  class="form-control drop-zone">
					            	</div>
								</c:if>
	            			</div>										
							<br><br><br>
							<div class="d-flex justify-content-center">
								<button class="btn btn-info btn-left btn-sl-sm mb-5" type="button" id="savebutton" onclick="save();">
                                	<i class="bx bx-highlight"></i> 글수정
								</button>
								&nbsp;&nbsp;
								<button class="btn btn-dark btn-right btn-sl-sm mb-5" type="button" onclick="location.href='/talk/list?pageNum=${ pageNum }&keyword=${keyword}&type=${type}'"> 글목록</button>
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
		
		$('button.delete-oldfile').on('click', function (event) {
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
		    		$(this).parent().prev().prop('name', 'delfile'); // oldfile -> delfile(서버에서 찾을 파라미터값.)
			
		    		// 현재 클릭한 요소의 직꼐부모(parent) 요소를 제거하기
		    		$(this).parent().remove();
		    		
		    		var str = `
		    			<div class="form-group" align="center">
			            	<input type="file" name="file"  class="form-control">
		            	</div>
		    		`;
		    	
		    		$('div#newFileBox').append(str);
				}
			});
    	});
	</script>
</form>	
</body>
</html>