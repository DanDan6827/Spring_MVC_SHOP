<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>장바구니 보기</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript">
</script>
<style type="text/css">
*{font-size:12px;}
table {
	margin: 10px auto;
	width: 800px;
	border-collapse: collapse;
	font-size: 8pt;
	border-color: navy;
}

table, th, td {
	border: 1px solid black;
}
</style>
</head>
<script type="text/javascript">
function edit_cart(f) {
	f.action="/edit";
	f.submit();
}
function delete_cart(f) {
	f.action="/delete";
	f.submit();
}
function checkAll() {
	if( $("#th_checkAll").is(':checked') ){
        $("input[name=checkRow]").prop("checked", true);
      }else{
        $("input[name=checkRow]").prop("checked", false);
      }
}
</script>
<body>
<p> <jsp:include page="top.jsp" /></p>
	<table>
		<caption><h2> :: 장바구니 내용 :: </h2></caption>
		<thead>
			<tr bgcolor="#dedede">
			<th style="width: 10%">전체삭제<input type="checkbox" name="checkAll" id="th_checkAll" onclick="checkAll()"></th>
				<th style="width: 10%">제품번호</th>
				<th style="width: 15%" >제품명</th>
				<th style="width: 25%">단가</th>
				<th style="width: 15%">수량</th>
				<th style="width: 10%">금액</th>
				<th style="width: 10%">삭제</th>
				
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${empty clist}">
				 <tr>
				 	<td colspan="6">
				 		<h3>장바구니가 비어있습니다.</h3>
				 	</td>
				 </tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="k" items="${clist}">
						<tr>
						<td><form method="post">
						<input type="checkbox" id="checkRow" name="checkRow" value="${k.p_num}">
						</form></td>
						<td>${k.p_num}</td>
						<td>${k.p_name}</td>
						<td>정가 : ${k.p_price} &nbsp;&nbsp;
						<font color="red">(세일가격: ${k.p_saleprice})</font> </td>
						<td>
						 <form method="post">
						 	<input type="number" name="amount" value="${k.amount}" style="width: 30px;">
						 	<input type="hidden" name="id" value="${k.id}">
						 	<input type="hidden" name="p_num" value="${k.p_num}">
						 	<input type="button" value="수정" onclick="edit_cart(this.form)">
						 </form>
						</td>
						<td>${k.p_saleprice*k.amount}</td>
						<td>
						 <form method="post">
						 	<input type="hidden" name="id" value="${k.id}">
						 	<input type="hidden" name="p_num" value="${k.p_num}">
						 	<input type="button" value="삭제" onclick="delete_cart(this.form)">
						 </form>
						</td>
						</tr>
						<c:set var="total" value="${total = total+(k.p_saleprice * k.amount)}"/>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</tbody>
		<tfoot>
			<tr align="right">
				<td colspan="7">
					
				
				</th><h2>총 결제액:<fmt:formatNumber value="${total}" pattern="#,###"/>원</h2>
				<form method="post">
				<input type="button" id="sdelete" value="선택항목삭제"> 
				</form>
				</td>
			</tr>
		</tfoot>
	</table>
</body>
</html>