<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- header -->
<%@ include file="/WEB-INF/views/common/header.jsp"%>

<h1>aptCode: ${aptCode}</h1>

<table class="table table-hover">
	<thead>
		<tr class="text-center">
			<th scope="col">aptCode</th>
			<th scope="col">dealAmount</th>
			<th scope="col">dealYear</th>
			<th scope="col">dealMonth</th>
			<th scope="col">dealDay</th>
			<th scope="col">area</th>
			<th scope="col">floor</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="housedeal" items="${housedealList}">
			<tr class="text-center">
				<th scope="row">${housedeal.aptCode}</th>
				<td>${housedeal.dealAmount}</td>
				<td>${housedeal.dealYear}</td>
				<td>${housedeal.dealMonth}</td>
				<td>${housedeal.dealDay}</td>
				<td>${housedeal.area}</td>
				<td>${housedeal.floor}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<!-- footer -->
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
</body>
</html>