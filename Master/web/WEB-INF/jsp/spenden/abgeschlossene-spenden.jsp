<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec"     uri="http://www.springframework.org/security/tags" %>
<jsp:include page="../header.jsp"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/highcharts.min.js"></script> 
<script type="text/javascript">
$(function () {
		
		// Radialize the colors
		Highcharts.getOptions().colors = Highcharts.map(Highcharts.getOptions().colors, function(color) {
			return {
				radialGradient: { cx: 0.5, cy: 0.3, r: 0.7 },
				stops: [
					[0, color],
					[1, Highcharts.Color(color).brighten(-0.3).get('rgb')] // darken
				]
			};
		});
		
		// Build the chart
		$('#diagramm').highcharts({
			chart: {
				plotBackgroundColor: null,
				plotBorderWidth: null,
				plotShadow: false
			},
			title: {
				text: 'Spendenabschluss nach Monaten'
			},
			tooltip: {
				enabled: true,
                                formatter: function() {
                                    return '<b>'+ this.series.name +'</b><br/>'+
                                        'im '+ this.x +': '+ this.y +' Abschlüsse';
                                }
			},
                        xAxis: {
                                categories: ['Jan', 'Feb', 'Mär', 'Apr', 'Mai', 'Jun', 'Jul', 'Aug', 'Sep', 'Okt', 'Nov', 'Dez']
                        },
                        yAxis: {
                                title: {
                                text: 'Anzahl der abgeschlossenen Spenden (dieses Jahr)'
                                }
                        },
			plotOptions: {
				pie: {
					allowPointSelect: true,
					cursor: 'pointer',
					dataLabels: {
						enabled: true,
						color: '#000000',
						connectorColor: '#000000',
						formatter: function() {
							return '<b>'+ this.point.name +'</b>';
						}
					}
				}
			},
			series: [{
				type: 'area',
				name: 'Spendenabschlüsse',
				data: ${diagramm}
			}]
		});
	});
	
</script>
<div id="content" class="table">
	<h2>Abgeschlossene Spenden</h2>   
	 <p>Insgesamt wurden bereits <b>${spendenanzahl}</b> Spenden abgeschlossen.</p>
         <c:if test="${not empty diagramm}">
             <div id="diagramm"></div>
             <hr/>
         </c:if> 
	<table>
		<thead>
			<tr>
				<td class="small">ID</td>
				<td>Produktbeschreibung</td>
				<td>Abschlussdatum</td>
				<td>Anzahl</td>
				<td>Gewicht</td>
				<td>&numsp;</td>
			</tr>
		</thead>
		<tbody>
			
			<!-- Schleife -->
			<c:forEach items="${spendenliste}" var="element" varStatus="line" >
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
					<td><a href="<%=request.getContextPath()%>/spenden/abgeschlossen/${element.spendeID}-details">${element.spendeID}</a></td>
					<td>${element.produktbezeichnung}</td>
					<td><fmt:formatDate value="${element.abschlussdatum}" pattern="dd.MM.yyyy" /></td>
					<td>${element.anzahl}</td>
					<td>${element.gewicht} kg</td>
					<td><a href="<%=request.getContextPath()%>/spenden/abgeschlossen/${element.spendeID}-details">Details&raquo;</a></td>
				</tr>
			</c:forEach>
	   </tbody>
	</table>
</div>
<jsp:include page="../footer.jsp"/>