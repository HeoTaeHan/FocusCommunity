<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
  	<meta content="width=device-width, initial-scale=1.0" name="viewport">
  	<title>Focus - 여행정보</title>
	<!-- Top Menu files -->
	<jsp:include page="/WEB-INF/views/include/head.jsp" />  
	<style>
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
<form id="frm">
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
							<h4 class="card-title">여행정보</h4>
						</div>
						<c:if test="${ not empty sessionid }">
							<div>
				                <a href="/info/write?pageNum=${pageMaker.cri.pageNum}&type=${pageMaker.cri.type}&keyword=${pageMaker.cri.keyword}" class="btn btn-sm btn-primary float-right me-sm-3">
				                  <i class="bx bx-highlight me-sm-1"></i>새글쓰기
				                </a>
				            </div>
						</c:if>
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
										<c:choose>
											<c:when test="${pageMaker.totalCount gt 0}">
												<c:forEach var="cusbod" items="${cusbodList}" varStatus="i">
													<fmt:formatDate value="${cusbod.cdate}" pattern="yyyy-MM-dd" var="strBirth" />
													<tr class="datatr" onclick="location.href='/info/content?csnum=${cusbod.csnum}&cusid=${cusbod.cusid}&ctype=${cusbod.ctype}&pageNum=${pageMaker.cri.pageNum}&type=${pageMaker.cri.type}&keyword=${pageMaker.cri.keyword}'">
														<td width="10%" align="center">${cusbod.csnum}</td>
														<td width="50%">
															<c:if test="${cusbod.crelv gt 0}">
																<span class="reply-level" style="width: ${cusbod.crelv * 15}px">  </span>
																<i class="bx bx-subdirectory-right"></i>
															</c:if>
															${cusbod.csbjt}
														</td>
														<td width="10%" align="center">${cusbod.cname}</td>
														<td width="10%" align="center">${strBirth}</td>
														<td width="10%" align="center">${cusbod.crdcn}</td>
														<td width="10%" align="center">${cusbod.recom}</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<td align="center" colspan="7">게시판 글이 없습니다.</td>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
								<nav class="pagination">
                                    <ul class="pagination pagination-xs pagination-circle">
                                   		<c:if test="${pageMaker.prev eq true}">
	                                        <li class="page-item page-indicator">
	                                            <a class="page-link" href="/info/list?pageNum=${pageMaker.startPage - 1}&type=${pageMaker.cri.type}&keyword=${pageMaker.cri.keyword}#board"><i class="icon-arrow-left"></i></a>
	                                        </li>
                                   		</c:if>
                                   		<c:forEach var="i" begin="${pageMaker.startPage}" end="${pageMaker.endPage}" step="1">
                                        	<li class="page-item ${pageMaker.cri.pageNum eq i ? 'active' : ''}"><a class="page-link" href="/info/list?pageNum=${ i }&type=${ pageMaker.cri.type }&keyword=${ pageMaker.cri.keyword }">${i}</a></li>
                                   		</c:forEach>
                                   		<c:if test="${pageMaker.next eq true}">
	                                        <li class="page-item page-indicator">
	                                            <a class="page-link" href="/info/list?pageNum=${pageMaker.endPage + 1}&type=${pageMaker.cri.type}&keyword=${pageMaker.cri.keyword}#board"><i class="icon-arrow-right"></i></a>
	                                        </li>
                                   		</c:if>
                                    </ul>
                                </nav>
                               <div class="pagination">
                   					<div class="col-sm-0">
                   						<select class="form-select" name="type">
			                      			<option value="csbjt" ${pageMaker.cri.type eq 'csbjt' ? 'selected' : ''}>제목</option>
			                      			<option value="ccont" ${pageMaker.cri.type eq 'ccont' ? 'selected' : ''}>내용</option>
			                      			<option value="cname" ${pageMaker.cri.type eq 'cname' ? 'selected' : ''}>작성자</option>
			                    		</select>
                   					</div>
                   					<div class="col-sm-2">
										<input type="text" class="form-control" name="keyword" value="${pageMaker.cri.keyword}" placeholder="검색어를 입력하세요.">
									</div>
									<div class="col-sm-2">
										<button type="button" class="btn btn-info" id="btnSearch"><i class="fa fa-search"></i> 검색</button>
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
		content end
	***********************************-->
	
	<!-- ======= Footer ======= -->
	<jsp:include page="/WEB-INF/views/include/bottom.jsp" />
	<!-- End Footer -->

	<!-- Vendor JS Files -->
	<jsp:include page="/WEB-INF/views/include/commonJs.jsp" />
	<script>
	$('button#btnSearch').on('click', function (){
		var query = $('#frm').serialize();
		
		
			var url = '/info/list';
			location.href = url + '?' + query + '#board';
		});
		
		$(function(){
			$('.data').mouseover(function(e) { // mouse hover 좌표
				$(this).mousemove(function(e) {
			    	var t = e.pageY-100;
			    	var l = e.pageX-290;
			    	$('#divLayer').css({"top":t, "left":l,"position":"absolute","opacity":"0,8" }).show();
			  });
			});
			$('.data').mouseout(function() {
				$('#divLayer').hide();
			});
		});
  	</script>
</form>  	
</body>
</html>