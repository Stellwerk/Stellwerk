<%--
	Der hier von Netbeans angezeigte Fehler ist keiner.

	gpaschke
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"     uri="http://www.springframework.org/security/tags" %>


<jsp:include page="../header.jsp"/>
<script type="text/javascript" src="//maps.googleapis.com/maps/api/js?sensor=false&amp;language=de"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/gmap3.min.js"></script>
<script type="text/javascript">
var normalMarker = '<%=request.getContextPath()%>/img/marker-tafel-normal.png';
var activeMarker = '<%=request.getContextPath()%>/img/marker-tafel-active.png';

$(function() {

	$("#map").gmap3({
		map: {
			options: {
				center: [52.673051, 9.3219],
				zoom: 6
			}
		},
		marker: {
			values: [
                                <c:forEach items="${TafelListe.tafelliste}" var="i" varStatus="status">
                                    {
                                        address: '${fn:escapeXml(i.adresse.strasse)}, ${i.adresse.plz} ${fn:escapeXml(i.adresse.ort)}, Germany',
                                        tag: ['${i.id}']
                                    }<c:if test="${status.count < fn:length(TafelListe.tafelliste)}">
                                        <c:out value=","></c:out>
                                    </c:if>
                                </c:forEach>
                                ],
				<%-- Für jedes Element wird die Adresse nach folgendem Schema erstellt
				{
					address: "Schlachthofstrasse 1, 49074 Osnabrueck, Germany",
					data: "Osnabr&uuml;cker Tafel e.V.",
					tag: ['3']
				}, //wenn es nicht das letzte Element ist, wird es mit einem Komma vom nächsten getrennt
				--%>               
				options: {
					icon: new google.maps.MarkerImage(normalMarker),
					draggable: false
				},
				events: {
					click: function (marker, event, context) {
						var tid = "#tafel-" + context.tag[0];
						var checkbox_status = $(tid).prop('checked');
						if (checkbox_status) { // checked === true, unchecked === false
							$(tid).prop("checked", false);
							marker.setOptions({
								icon: normalMarker
							});
						} else {
							$(tid).prop("checked", true);
							marker.setOptions({
								icon: activeMarker
							});
						}
					}
				}
			}
		},
	"autofit");

	// Marker Checkbox Verbindung
	$("#tafelauswahl :checkbox").each(changeMarker);
	$("#tafelauswahl :checkbox").on("change", changeMarker);
});
</script>
<c:if test="${not empty Ausgabe}">
	${Ausgabe}
</c:if>

<div id="content" class="list">
	<h2>Spende #${spende.spendeID}: ${spende.produktbezeichnung}</h2>

		<form:form id="tafelauswahl" modelAttribute="TafelListe" method="POST">
		<ul class="list-with-map">
			<li>
                            <h3>Vom Spender erw&uuml;nschte Tafeln:</h3>
                            <ul>
                                <li>
                                    <p>${spende.bevorzugte_Tafeln}</p>
                                </li>
                            </ul>
                            <h3>Bitte Tafeln ausw&auml;hlen:</h3>
                            <ul>
                                <c:forEach items="${TafelListe.tafelliste}" var="element" varStatus="row">
                                        <li>
                                            <form:input path="tafelliste[${row.index}].id" type="hidden" />
                                            <form:checkbox path="tafelliste[${row.index}].ausgewaehlt" id="tafel-${element.id}"/>

                                            <%-- Wenn die Tafel markiert wurde färbe sie gelb--%>
                                            <c:choose>
                                                <c:when test='${element.status == "gelb"}'>
                                                    <label for="tafel-${element.id}" class ="yellow">${element.tafelname}</label>
                                                </c:when>
                                                <c:otherwise>
                                                    <label for="tafel-${element.id}">${element.tafelname}</label>
                                                </c:otherwise>
                                            </c:choose>

                                        </li>
                                </c:forEach>
                            </ul>
			</li>
			<li id="map"></li>
		</ul>
		<div class="clear"></div>
		<div class="buttons">
			<input type="submit" value="Weiter &raquo;" class="button" />
			<a href="<%=request.getContextPath()%>" class="button minor" title="Abbrechen">Abbrechen</a>
			<div class="clear"></div>
		</div>
		 <!-- commandName="spende" -->
	</form:form>
</div>
<jsp:include page="../footer.jsp"/>
