<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
	<meta content="width=device-width, initial-scale=1.0" name="viewport">
	
	<title>Focus - 여행정보</title>
	<!-- Top Menu files -->
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
		Sidebar start
	***********************************-->
	<jsp:include page="/WEB-INF/views/include/left.jsp" />
	<!--**********************************
		Sidebar end
	***********************************-->

	<!--**********************************
		content start
	***********************************-->
	<!--**********************************
            Content body start
        ***********************************-->
	<div class="content-body">
		<div class="container-fluid">
			<!-- row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="card">
						<div class="card-body">
							<div class="row">
								<div class="col-12">
									<div class="right-box-padding">
										<div class="read-content">
											<div class="media pt-3">
												<fmt:formatDate value="${cusbodVO.cdate}" pattern="yyyy-MM-dd hh:mm:ss" var="strBirth" />
												<input type="hidden" name="cusid" id="cusid" value="${cusbodVO.cusid}"/>
												<input type="hidden" name="csnum" id="csnum" value="${cusbodVO.csnum}"/>
												<input type="hidden" name="pageNum" value="${ pageNum }">
												<input type="hidden" name="ctype" id="ctype" value="${ cusbodVO.ctype }">
												<input type="hidden" name="type" value="${ type }">
												<input type="hidden" name="keyword" value="${ keyword }">
												<div class="media-body">
													<h5 class="text-primary">${ cusbodVO.csbjt }</h5>
													<div class="info-row">
														<span class="user-info">${ cusbodVO.cusid }</span>
													</div>
													<div class="article-info">
														<span class="head">공감</span> 
														<span class="body" style="color: #2356FF;">
															<input type="text" class="rec-color" id="recom" value="${ empty cusbodVO.recom  ? '0' : cusbodVO.recom}" style="border:0; width:2%" readonly="readonly">
														</span> <span class="sep"></span>
														<span class="head">댓글</span> <span class="body">${fn:length(csreplList)}</span> <span
															class="sep"></span> <span class="head">조회수</span> <span
															class="body">${ cusbodVO.crdcn }</span> <span class="date"> <span
															class="sep"></span> <span class="head">작성일</span> <span
															class="body">${ strBirth }</span>
														</span>
													</div>
												</div>
											</div>
											<hr>
											<div class="read-content-body">
												<pre class="pre-font">${ cusbodVO.ccont }</pre>
												<div class="btn-list">
													<c:if test="${not empty sessionid}">
														<button type="button" 
															class="btn btn-rounded btn-danger btn-center btn-lg btn-sl-sm mb-5"
															onclick="recomCheckFunction()">
															<i class="bx bx-donate-heart"></i> 공감
														</button>
													</c:if>
												</div>
												<hr>
											</div>
											<div class="read-content-attachment">
												<h6>
													<i class="fa fa-download mb-2"></i> 첨부파일
												</h6>
												<div class="row attachment">
													<c:if test="${ cusbodVO.filetype eq 'O' }"><%-- 일반파일 --%>
														<%-- 다운로드할 일반파일 경로 --%>
					                   					<c:set var="fileCallPath" value="${ cusbodVO.uploadpath }/${ cusbodVO.uuid }_${ cusbodVO.filename }" />
					                   					<div class="col-auto">
						                       				<a href="/download?fileName=${ fileCallPath }" class="text-muted">
							                       				${ cusbodVO.filename }
						                       				</a>
						                       			</div>
													</c:if>                   		
					                    			<c:if test="${ cusbodVO.filetype eq 'I' }"><%-- 이미지파일 --%>
					                    				<%-- 썸네일 이미지 경로 --%>
						                   				<c:set var="fileCallPath" value="${ cusbodVO.uploadpath }/s_${ cusbodVO.uuid }_${ cusbodVO.filename }" />
						                   				<%-- 원본 이미지 경로 --%>
						                   				<c:set var="fileCallPathOrigin" value="${ cusbodVO.uploadpath }/${ cusbodVO.uuid }_${ cusbodVO.filename }" />
						                   				<div class="col-auto">
						                       				<a href="/display?fileName=${ fileCallPathOrigin }" class="text-muted">
						                       					<img src="/display?fileName=${ fileCallPath }">
						                       				</a>
						                       			</div>
					                    			</c:if>
												</div>
											</div>
											<hr>
											<div class="btn-list">
													<c:if test="${cusbodVO.cusid eq sessionid}">
														<button class="btn btn-info btn-left btn-sl-sm mb-5"
															type="button" onclick="location.href='/info/modify?pageNum=${pageNum}&keyword=${keyword}&type=${type}&csnum=${cusbodVO.csnum}&cusid=${usbodVO.cusid}&ctype=${cusbodVO.ctype}'">
															<i class="bx bx-edit"></i> 수정
															</button>
														<button class="btn btn-warning btn-left btn-sl-sm mb-5"
															type="button" id="btnRemove">
															<i class="bx bx-trash"></i> 삭제
															</button>
													</c:if>
													<c:if test="${not empty sessionid}">
													<button class="btn btn-success btn-left btn-sl-sm mb-5"
														type="button" onclick="location.href='/info/replyWrite?crerf=${ cusbodVO.crerf }&crelv=${ cusbodVO.crelv }&cresq=${ cusbodVO.cresq }&pageNum=${ pageNum }&keyword=${keyword}&type=${type}'">
														<i class="bx bx-note"></i> 답글
														</button>
													</c:if>
												<button class="btn btn-dark btn-right btn-sl-sm mb-5"
													type="button" onclick="location.href='/info/list?pageNum=${pageNum}&keyword=${keyword}&type=${type}'">
													<i class="bx bx-list-ul"></i> 글목록
													</button>
											</div>
											<div class="read-content-attachment">
												<h6>
                  									<i class="bx bx-message-square-dots"></i> 댓글
                  								</h6>
                  							</div>
											<div class="form-group">
												<table class="table table-bordered" style="border-spacing : 0;">
													<c:choose>
														<c:when test="${ fn:length(csreplList) gt 0 }">
															<c:forEach var="csrepl" items="${ csreplList }" varStatus="i">
																<input type="hidden" name="conum" value="${csrepl.conum}">
																<tr style="border:0;" class="test${i.count}">
																	<td class="td-right">
																		<c:if test="${csrepl.crelv gt 0}">
																			<span class="reply-level" style="width: ${csrepl.crelv * 15}px"></span>
																			<i class="bx bx-subdirectory-right"></i>
																		</c:if>
																		${csrepl.cname}(${csrepl.cusid})
																	</td>
																	<td class="td-left">
																		<fmt:formatDate value="${csrepl.cdate}" pattern="yyyy-MM-dd hh:mm:ss" var="strBirths" />
																		<c:if test="${csrepl.cusid eq sessionid}">
																			<span style="border-right:1px solid;"></span>
							                  								&nbsp;<a class="a-cursor" onclick="remove2(event, '${csrepl.conum}', '${cusbodVO.cusid}')" id="replyRemove">
							                  								<i class="bx bx-trash"></i>삭제
							                  								</a>&nbsp;
							                  								<span style="border-right:1px solid;"></span>
							                  								&nbsp;<a class="a-cursor" onclick="modify(event, '${csrepl.conum}', 
							                  								'${csrepl.cnten}','${strBirths}',
							                  								'${csrepl.conum}','${csrepl.cname}',
							                  								'${cusbodVO.cusid}','test${i.count}','tests${i.count}')" id="replyModify">
							                  								<i class="bx bx-edit"></i>수정
							                  								</a>&nbsp;
					                  										<span style="border-right:1px solid;"></span>
																		</c:if>
																		<c:if test="${not empty sessionid}">
																			&nbsp;<a class="a-cursor" onclick="replyre(event,'tests${i.count}', '${cusbodVO.cusid }','${csrepl.crerf}','${csrepl.crelv}'
						                  									,'${csrepl.cresq}')">
						                  									<i class="bx bx-note"></i>답글
						                  									</a>
					                  									</c:if>
																	</td>
																</tr>
																<tr class="tests${i.count}">
																	<td colspan="2">
																		<pre class="pre-font" style="border:0;background-color:white">${csrepl.cnten}</pre>
																	</td>	
																</tr>
															</c:forEach>
														</c:when>
													</c:choose>
				                  				</table>
											</div>
											<c:if test="${not empty sessionid}">
												<div class="form-group pt-3">
													<textarea style="resize: none;" class="w-100" name="cnten" id="write-email"
														cols="30" rows="5" placeholder="댓글 달기"></textarea>
												</div>
											</c:if>
										</div>
										<c:if test="${not empty sessionid}">
											<div class="text-right">
												<button class="btn btn-secondary btn-sl-sm mb-5" type="button" onclick="reply(event, '${cusbodVO.cusid}')">
												<i class="bx bx-highlight me-sm-1"></i> 작성
												</button>
											</div>
										</c:if>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--**********************************
            Content body end
        ***********************************-->
	<!--**********************************
		content end
	***********************************-->

	<!-- ======= Footer ======= -->
	<jsp:include page="/WEB-INF/views/include/bottom.jsp" />
	<!-- End Footer -->

	<!-- Vendor JS Files -->
	<jsp:include page="/WEB-INF/views/include/commonJs.jsp" />
	<script>
		function recomCheckFunction(){
			var id    = '${sessionid}';
			var num   = $("#csnum").val();
			var ctype = $("#ctype").val();
			var cusid =  id + "*" + num + "_" + ctype;
			
			$.ajax({ 
				type : 'POST', 
				url : '/BoardRecomCheckServlet', 
				data : {cusid : cusid}, 
				success : function(result){ 
					$("#recom").val(result);					
				} 
			}) 
		}
		
		function modify(event, data, cnten, date, conum, cname, cusid, count, count2){
			event.preventDefault(); // a태그 기본동작 막기
			var cnten = cnten.replaceAll('<br>','\n');
			var htmls = `
				<tr class="test\${count}">
					<td class="td-right">\${cname}(\${cusid})</td>
					<td class="td-left" style="cursor:pointer;">
						<span style="border-right:1px solid;"></span>
						&nbsp;<a id="replyModify2" onclick="reply2(event, '\${cusid}','\${conum}')">
						<i class="bx bx-edit"></i>수정
						</a>&nbsp;
						<span style="border-right:1px solid;"></span>
						&nbsp;<a onclick="location.reload();" id="replyModify2">
						<i class="bx bx-comment-x"></i>취소
						</a>&nbsp;
					</td>
				</tr>
				<tr class="tests\${count2}">
					<td colspan="2">
						<textarea style="border:1px solid black; float:left; resize: none; border: 1px solid #c3b9b9;" class="w-100" name="cnten2" id="ta2">\${cnten}</textarea>
					</td> 
				</tr>
				<input type="hidden" name="conum" value="\${conum}">
			`;
			$('.'+count).empty();
			$('.'+count2).empty();
			$('.'+count).replaceWith(htmls);
			var addrow = $('.addrow');
			if(addrow != null){
				$('.addrow').empty();	
			}
		}
		
		function replyre(event, data, cusid, crerf, crelv, cresq){
			event.preventDefault(); // a태그 기본동작 막기
			var htmls = `
				<tr class="addrow">
					<td colspan="2">
						답글 작성
						<textarea style="border:1px solid black; float:left; resize: none; border: 1px solid #c3b9b9; margin-top: 10px;" class="w-100" name="cnten" id="ta3" placeholder="댓글 달기"></textarea>
						<a class="btn btn-secondary btn-sl-sm" style="color: white; float: right; margin-bottom: 10px; margin-top: 15px;" onclick="reply3(event, '\${cusid}','\${crerf}','\${crelv}','\${cresq}')" style="align:right">
	      				<i class="bx bx-highlight testre">답글작성</i>
      					</a>
      				</td>
      			</tr>
				<input type="hidden" name="crerf" value="\${crerf}">
				<input type="hidden" name="crelv" value="\${crelv}">
				<input type="hidden" name="cresq" value="\${cresq}">
			`;
			if($('.testre').val() == undefined){
				$('.'+data).after(htmls);
			}
		}
		
		function remove2(event, data, cusid){
			event.preventDefault(); // a태그 기본동작 막기
			
			swal({ title: "댓글 삭제", 
				text: "이 댓글을 정말 삭제하시겠습니까?", 
				type: "warning", 
				showCancelButton: !0, 
				confirmButtonColor: "#DD6B55", 
				confirmButtonText: "예", 
				cancelButtonText: '아니요',
				closeOnConfirm: !1
			}).then((result) => {
				if (result.value == true) {
					location.href = '/info/replyRemove?pageNum=${pageNum}&keyword=${keyword}&type=${type}&csnum=${cusbodVO.csnum}&cusid=' + cusid + '&conum='+data;
				}
			});
		}
		
		function reply2(event, cusid, conum){
			event.preventDefault();
			
			var cnten = $('textarea#ta2').val().replace(/(\n|\r\n)/g, '<br>');
			
			location.href = '/info/replyModify?pageNum=${pageNum}&keyword=${keyword}&type=${type}&csnum=${cusbodVO.csnum}&cusid=' + cusid + '&ctype=${cusbodVO.ctype}&cnten='+cnten + '&conum='+conum;
		}
		
		function reply3(event, cusid, crerf, crelv, cresq){
			event.preventDefault();
			
			var cnten = $('textarea#ta3').val().replace(/(\n|\r\n)/g, '<br>');
			
			location.href = '/info/replyRe?pageNum=${pageNum}&keyword=${keyword}&type=${type}&csnum=${cusbodVO.csnum}&cusid=' + cusid + '&ctype=${cusbodVO.ctype}&cnten='+cnten + '&crerf=' + crerf + '&crelv=' + crelv + '&cresq=' + cresq;
		}
		
		function reply(event, cusid){
			event.preventDefault();
			
			var cnten = $('textarea#write-email').val().replace(/(\n|\r\n)/g, '<br>');
			
			location.href = '/info/reply?pageNum=${pageNum}&keyword=${keyword}&type=${type}&csnum=${cusbodVO.csnum}&cusid=' + cusid + '&${cusbodVO.ctype}&cnten='+cnten;
		}
		
		$('#btnRemove').on('click', function(){
			event.preventDefault();
			
			swal({ title: "게시글삭제", 
				text: "이 글을 삭제하시겠습니까?", 
				type: "warning", 
				showCancelButton: !0, 
				confirmButtonColor: "#DD6B55", 
				confirmButtonText: "예", 
				cancelButtonText: '아니요',
				closeOnConfirm: !1
			}).then((result) => {
				if (result.value == true) {
	  				location.href = '/info/remove?csnum=${ cusbodVO.csnum }&ctype=${cusbodVO.ctype}&pageNum=${ pageNum }&keyword=${keyword}&type=${type}';
				}
			});
		});
	</script>
</body>
</html>