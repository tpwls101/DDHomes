<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- header -->
<%@ include file="/WEB-INF/views/common/header.jsp"%>

<h1>dongCode: ${selectedValue}</h1>

<table class="table table-hover">
	<thead>
		<tr class="text-center">
			<th scope="col">aptCode</th>
			<th scope="col">apartmentName</th>
			<th scope="col">roadName</th>
			<th scope="col">roadNameBonBun</th>
			<th scope="col">dongCode</th>
			<th scope="col">dong</th>
			<th scope="col">lng</th>
			<th scope="col">lat</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="houseinfo" items="${houseinfoList}">
			<tr class="text-center">
				<th scope="row">${houseinfo.aptCode}</th>
				<td class="text-start"><a
					href="${root}/apt/deal?aptCode=${houseinfo.aptCode}"
					class="article-title link-dark" style="text-decoration: none">
						${houseinfo.apartmentName} </a></td>
				<td>${houseinfo.roadName}</td>
				<td>${houseinfo.roadNameBonBun}</td>
				<td>${houseinfo.dongCode}</td>
				<td>${houseinfo.dong}</td>
				<td>${houseinfo.lng}</td>
				<td>${houseinfo.lat}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>


<!-- footer -->
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
</body>
</html>