<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../header.jsp"/>
<c:if test="${not empty Ausgabe}">
	${Ausgabe}
</c:if>
<div id="content" class="table">
	<h2>Abgeschlossene Spenden</h2>   
	 <p>Insgesamt haben Sie bereits <b>${spendenanzahl}</b> Spenden angenommen.</p>
         <c:if test="${not empty diagramm}">
             <div id="diagramm"></div>
             <hr/>
         </c:if> 
	<table>
		<thead>
			<tr>
				<td>Produktbeschreibung</td>
				<td>Abschlussdatum</td>
				<td>Anzahl</td>
				<td>Gewicht</td>
				<td>&numsp;</td>
			</tr>
		</thead>
		<tbody>
			
			<!-- Schleife -->
			<c:forEach items="${archivliste}" var="element" varStatus="line" >
				<!-- ###### Farbauswahl der einzelnen Zeile ##### -->
				<c:choose>
					<c:when test='${(line.index)%2 eq 0}'>
						<c:set var="rowColor" value="even" scope="page"/>
					</c:when>
					<c:otherwise>
						<c:set var="rowColor" value="odd" scope="page"/>
					</c:otherwise>
				</c:choose>
				
				<tr class="${rowColor}">
					<td>${element.spende.produktbezeichnung}</td>
					<td><fmt:formatDate value="${element.spende.abschlussdatum}" pattern="dd.MM.yyyy" /></td>
					<td>${element.anzahl}</td>
					<td>${element.gewicht} kg</td>
					<td><a href="<%=request.getContextPath()%>/verteilte-spenden/${element.id}-details">Details&raquo;</a></td>
				</tr>
			</c:forEach>
	   </tbody>
	</table>
</div>
<jsp:include page="../footer.jsp"/>