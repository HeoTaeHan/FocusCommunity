<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
  	<meta content="width=device-width, initial-scale=1.0" name="viewport">

  	<title>Focus - 회원정보관리</title>
	<!-- Top Menu files -->
	<jsp:include page="/WEB-INF/views/include/head.jsp" />  
	<style type="text/css">
		
		.datatr:nth-child(odd){
			background-color: #99ffff;
			cursor: pointer;
		}
		
		.datatr:nth-child(odd):hover {
			color:#0099ff;
		}
		
		.datatr:nth-child(even){
			background-color: #ffffff;
			cursor: pointer;
		}
		
		.datatr:nth-child(even):hover {
			color:#0099ff;
		}
		
		.pagination{
			display: flex;
  			justify-content: center;
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
			<div class="row">
				<div class="col-lg-12">
					<div class="card">
						<div class="card-header">
                    		<h4 class="card-title">회원정보 관리</h4>
						</div>
						<div class="card-body">
							<div class="table-responsive">
								<table class="table table-striped table-responsive-sm" style="cususr:pointer;">
									<thead>
										<tr>
							 				<th style="text-align:center">유저ID</th>
											<th style="text-align:center">유저명</th>
											<th style="text-align:center">이메일</th>
											<th style="text-align:center">주소</th>
											<th style="text-align:center">가입일자</th>
										</tr>
						 			</thead>
						 			<tbody>
						 				<c:choose>
						 					<c:when test="${pageMaker.totalCount gt 0}">
												<c:forEach var="cususr" items="${cususrList}">
													<!-- 문자열 →  Date 객체 변환  -->
													<fmt:parseDate value="${cususr.birth}" pattern="yyyymmdd" var="dateBirth"/>
													<!-- Date 객체 → 문자열 변환  -->
													<fmt:formatDate value="${pageScope.dateBirth}" pattern="yyyy-mm-dd" var="strBirth" />
													<tr class="datatr" onclick="location.href='/admin/content?cusid=${cususr.cusid}&pageNum=${pageMaker.cri.pageNum}&type=${pageMaker.cri.type}&keyword=${pageMaker.cri.keyword}'">
														<td width="10%" align="center">${cususr.cusid}</td>
														<td width="10%" align="center">${cususr.cname}</td>
														<td width="20%" align="center">${cususr.email}</td>
														<td width="50%" align="center">${cususr.cusid}</td>
														<td width="10%" align="center">${strBirth}</td>
													</tr>
                								</c:forEach>
                							</c:when>
						 					<c:otherwise>
								 				<tr>
	                								<td colspan="5">게시판 글이 없습니다.</td>
												</tr>
						 					</c:otherwise>
						 				</c:choose>
						 			</tbody>
						 		</table>
						 		<nav class="pagination">
                                    <ul class="pagination pagination-xs pagination-circle">
                                   		<c:if test="${pageMaker.prev eq true}">
	                                        <li class="page-item page-indicator">
	                                            <a class="page-link" href="/admin/list?pageNum=${pageMaker.startPage - 1}&type=${pageMaker.cri.type}&keyword=${pageMaker.cri.keyword}#board"><i class="icon-arrow-left"></i></a>
	                                        </li>
                                   		</c:if>
                                   		<c:forEach var="i" begin="${pageMaker.startPage}" end="${pageMaker.endPage}" step="1">
                                        	<li class="page-item ${pageMaker.cri.pageNum eq i ? 'active' : ''}"><a class="page-link" href="javascript:void()">${i}</a></li>
                                   		</c:forEach>
                                   		<c:if test="${pageMaker.next eq true}">
	                                        <li class="page-item page-indicator">
	                                            <a class="page-link" href="/admin/list?pageNum=${pageMaker.endPage + 1}&type=${pageMaker.cri.type}&keyword=${pageMaker.cri.keyword}#board"><i class="icon-arrow-right"></i></a>
	                                        </li>
                                   		</c:if>
                                    </ul>
                                </nav>
                               <div class="pagination">
                   					<div class="col-sm-0">
                   						<select class="form-select" name="type">
			                      			<option value="cusid" ${pageMaker.cri.type eq 'cusid' ? 'selected' : ''}>유저ID</option>
			                      			<option value="cname" ${pageMaker.cri.type eq 'cname' ? 'selected' : ''}>유저명</option>
			                      			<option value="email" ${pageMaker.cri.type eq 'email' ? 'selected' : ''}>이메일</option>
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
			
			
  			var url = '/admin/list';
  			location.href = url + '?' + query + '#board';
  		});
  	</script>
</form>  	
</body>
</html>