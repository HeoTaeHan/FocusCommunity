<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
  	<meta content="width=device-width, initial-scale=1.0" name="viewport">
  	<title>Focus - 전체글</title>
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
							<h4 class="card-title">전체글</h4>
						</div>
						<div class="card-body">
							<div class="table-responsive">
								<table class="table table-responsive-sm" style="cususr:pointer;">
									<thead>
                                    	<tr>
                                        	<th style="text-align:center">번호</th>
                                        	<th style="text-align:center">게시판종류</th>
                                            <th style="text-align:center">이미지</th> <!-- 이미지 -->
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
													<c:set var="fileCallPath" value="${ cusbod.uploadpath }/s_${ cusbod.uuid }_${ cusbod.filename }" />
													<c:set var="fileCallPathOrigin" value="${cusbod.uploadpath}/${cusbod.uuid}_${cusbod.filename}" />
													<fmt:formatDate value="${cusbod.cdate}" pattern="yyyy-MM-dd" var="strBirth" />
													<c:choose>
														<c:when test="${cusbod.ctype eq 'I'}">
															<c:set var="trdata" value="location.href='/info/content?cusid=${cusbod.cusid}&ctype=${cusbod.ctype}&csnum=${cusbod.csnum}&pageNum=1&type=${pageMaker.cri.type}&keyword=${pageMaker.cri.keyword}'"/>
														</c:when>
														<c:when test="${cusbod.ctype eq 'R'}">
															<c:set var="trdata" value="location.href='/review/content?cusid=${cusbod.cusid}&ctype=${cusbod.ctype}&csnum=${cusbod.csnum}&pageNum=1&type=${pageMaker.cri.type}&keyword=${pageMaker.cri.keyword}'"/>
														</c:when>
														<c:when test="${cusbod.ctype eq 'T'}">
															<c:set var="trdata" value="location.href='/talk/content?cusid=${cusbod.cusid}&ctype=${cusbod.ctype}&csnum=${cusbod.csnum}&pageNum=1&type=${pageMaker.cri.type}&keyword=${pageMaker.cri.keyword}'"/>
														</c:when>
														<c:when test="${cusbod.ctype eq 'Q'}">
															<c:set var="trdata" value="location.href='/qna/content?cusid=${cusbod.cusid}&ctype=${cusbod.ctype}&csnum=${cusbod.csnum}&pageNum=1&type=${pageMaker.cri.type}&keyword=${pageMaker.cri.keyword}'"/>
														</c:when>
													</c:choose>
													<tr class="datatr" onclick="${trdata}">
														<td width="5%" align="center">${cusbod.csnum}</td>
														<td width="10%" align="center">
															<c:choose>
																<c:when test="${cusbod.ctype eq 'I' }">
																	<span class="badge badge-primary">여행정보</span>
																</c:when>
																<c:when test="${cusbod.ctype eq 'R' }">
																	<span class="badge badge-info">여행후기</span>
																</c:when>
																<c:when test="${cusbod.ctype eq 'T' }">
																	<span class="badge badge-danger">잡담</span>
																</c:when>
																<c:otherwise>
																	<span class="badge badge-warning">질문</span>
																</c:otherwise>
															</c:choose>
														</td>
														<td width="10%" align="center"> <!-- 이미지 -->
															<c:choose>
																<c:when test="${cusbod.ctype ne 'R' }">
																	<div id="divLayer" class="overLayer">
																		<div class="conttent">
																	    	<span id="conttent"><img src="/display?fileName=${ fileCallPathOrigin }" style="width: 400px; height: 200px;"></span>
																	  	</div>
																	</div>
																	<c:choose>
																		<c:when test="${not empty cusbod.uploadpath}">
																			<img src="/display?fileName=${ fileCallPath }" style="width: 50px;" class="data" />
																		</c:when>
																		<c:otherwise>																
																			<img src="/resources/images/carmera.jpg" style="width:50px" />
																		</c:otherwise>
																	</c:choose>	
																</c:when>
																<c:otherwise>
																	<div id="divLayer" class="overLayer">
																		<div class="conttent">
																	    	<span id="conttent"><img src="/display?fileName=${ fileCallPathOrigin }" style="width: 400px; height: 200px;"></span>
																	  	</div>
																	</div>
																	<c:choose>
																		<c:when test="${cusbod.uuid eq 'img'}">
																			<div id="divLayer" class="overLayer">
																				<div class="conttent">
																			    	<span id="conttent"><img src="/display?fileName=${cusbod.filetype}" style="width: 400px; height: 300px;"></span>
																			  	</div>
																			</div>
																			<div class="conttent2 data">
																				${cusbod.filename }
																			</div>
																		</c:when>
																		<c:otherwise>																
																			<img src="/resources/images/carmera.jpg" style="width:50px" />
																		</c:otherwise>
																	</c:choose>	
																</c:otherwise>
															</c:choose>
														</td>
														<td width="40%">
															<c:if test="${cusbod.crelv gt 0}">
																<span class="reply-level" style="width: ${cusbod.crelv * 15}px">  </span>
																<i class="bx bx-subdirectory-right"></i>
															</c:if>
															${cusbod.csbjt}
														</td>
														<td width="10%" align="center">${cusbod.cname}</td>
														<td width="10%" align="center">${strBirth}</td>
														<td width="5%" align="center">${cusbod.crdcn}</td>
														<td width="10%" align="center">${cusbod.recom}</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<td colspan="8">게시판 글이 없습니다.</td>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
								<nav class="pagination">
                                    <ul class="pagination pagination-xs pagination-circle">
                                   		<c:if test="${pageMaker.prev eq true}">
	                                        <li class="page-item page-indicator">
	                                            <a class="page-link" href="/total/list?pageNum=${pageMaker.startPage - 1}&type=${pageMaker.cri.type}&keyword=${pageMaker.cri.keyword}#board"><i class="icon-arrow-left"></i></a>
	                                        </li>
                                   		</c:if>
                                   		<c:forEach var="i" begin="${pageMaker.startPage}" end="${pageMaker.endPage}" step="1">
                                        	<li class="page-item ${pageMaker.cri.pageNum eq i ? 'active' : ''}"><a class="page-link" href="/total/list?pageNum=${ i }&type=${ pageMaker.cri.type }&keyword=${ pageMaker.cri.keyword }">${i}</a></li>
                                   		</c:forEach>
                                   		<c:if test="${pageMaker.next eq true}">
	                                        <li class="page-item page-indicator">
	                                            <a class="page-link" href="/total/list?pageNum=${pageMaker.endPage + 1}&type=${pageMaker.cri.type}&keyword=${pageMaker.cri.keyword}#board"><i class="icon-arrow-right"></i></a>
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
			
			
  			var url = '/total/list';
  			location.href = url + '?' + query + '#board';
  		});
		
		$(function(){
			$('.data').mouseover(function(e) { // mouse hover 좌표
				$(this).mousemove(function(e) {
			    	var t = e.pageY-100;
			    	var l = e.pageX-290;
			    	$(this).prev().css({"top":t, "left":l,"position":"absolute","opacity":"0,8" }).show();
			  });
			});
			$('.data').mouseout(function() {
				$(this).prev().hide();
			});
		});
  	</script>
</form>  	
</body>
</html>