<%--
	Der hier von Netbeans angezeigte Fehler ist keiner.

	gpaschke
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec"     uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../header.jsp"/>
<script type="text/javascript" src="//maps.googleapis.com/maps/api/js?sensor=false&amp;language=de"></script>     
<script type="text/javascript" src="<%=request.getContextPath()%>/js/gmap3.min.js"></script>    
<script type="text/javascript" src="<%=request.getContextPath()%>/js/highcharts.min.js"></script> 
<script type="text/javascript">
    $(function() {
        $('#map').gmap3({
            marker: {
                address: "${spende.adresse.strasse} ,${spende.adresse.plz} ${spende.adresse.ort}", options:{icon: "<%=request.getContextPath()%>/img/marker-active.png"}
            },
            map: {
                options: {
                    zoom: 9
                }
            }
        });
        
        
        //----------------------------------------------------------------------
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
				text: 'Aufteilung der Spende an die Tafeln'
			},
			tooltip: {
				pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
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
				type: 'pie',
				name: 'Spendenanteil',
				data: [
                                    <c:forEach items="${archivliste}" var="element" varStatus="line" >
                                        <c:choose>
                                            <c:when test="${element.anzahl != 0}">
                                                ['${element.tafel.tafelname}',	${element.anzahl}]
                                            </c:when>
                                            <c:when test="${element.gewicht != 0}">
                                                ['${element.tafel.tafelname}',	${element.gewicht}]
                                            </c:when>
                                            <c:when test="${element.volumen != 0}">
                                                ['${element.tafel.tafelname}',	${element.volumen}]
                                            </c:when>
                                            <c:otherwise>
                                                ['Fehler!',	100]
                                            </c:otherwise>
                                        </c:choose>
                                        <c:if test="${line.count < fn:length(archivliste)}"><c:out value=","></c:out></c:if>
                                    </c:forEach>
                        
//					['Firefox',	25.21],
//					['IE',		24.05],
//					{name: 'Chrome', y: 12.8, slice: true, selected: true}
//http://www.highcharts.com/demo/pie-gradient
				]
			}]
		});
    });
</script>
<div id="content" class="list">
    <h2>Abgeschlossene Spende im Detail</h2>
    <ul>
        <li>
            <h3>${spende.produktbezeichnung}</h3>
            <ul class="table">
                <li>
                    <h4>Beschreibung</h4>
                    <p>${spende.beschreibung}</p>
                    
                </li>
            </ul>
            <ul class="table">
                <li>
                    <h4>Daten</h4>
                    <dl>
                        <dt>Erstellungsdatum</dt>
                        <dd><fmt:formatDate value="${spende.erstellungsdatum}" pattern="dd.MM.yyyy" /></dd>
                        
                        <dt>Mindesthaltbarkeitsdatum</dt>
                        <dd><fmt:formatDate value="${spende.mhd}" pattern="dd.MM.yyyy" /></dd>

                        <dt>R&uuml;ckmeldefrist</dt>
                        <dd><fmt:formatDate value="${spende.rueckmeldefrist}" pattern="dd.MM.yyyy" /></dd>

                        <dt>Tiefk&uuml;hlware</dt>
                        <dd>${spende.kuehlung}</dd>
                        
                        <c:if test="${spende.spendenbeleg == true}">
                            <dt>Spendenbeleg</dt>
                            <dd>erw&uuml;nscht</dd>
                        </c:if>
                        
                        <c:if test="${spende.alkohol == true}">
                            <dt>Enth&auml;lt Alkohol</dt>
                            <dd>Alkohol enthalten</dd>
                        </c:if>
                    </dl>                        
                </li>
                <li>
                    <h4>Gewicht/Anzahl</h4>
                    <dl>
                        <dt>Menge</dt>
                        <dd>${spende.anzahl} St&uuml;ck</dd>

                        <dt>Gesamtgewicht</dt>
                        <dd>${spende.gewicht} kg</dd>
                        
                        <dt>Paletten</dt>
                        <dd>${spende.paletten}</dd>

                        <dt>Tauschpalette</dt>
                        <dd>${spende.tauschpalette}</dd>
                        
                        <c:if test="${not empty spende.volumen}">
                            <dt>Volumen</dt>
                            <dd>${spende.volumen} Liter</dd>
                        </c:if>
                    </dl>                        
                </li>
            </ul>
            <ul class="table">
                <li>
                    <h4>Spender</h4>
                    <dl>
                        <dt>Unternehmen</dt>
                        <dd><c:if test="${not empty spende.benutzer.tafel}">${spende.benutzer.tafel.tafelname}</c:if>
                        <c:if test="${not empty spende.benutzer.spender}">${spende.benutzer.spender.firmenname}</c:if></dd>
                        
                        <dt>Standort</dt>
                        <dd>${spende.adresse.strasse}<br>
                            ${spende.adresse.plz} ${spende.adresse.ort}</dd>

                        <dt>&Ouml;ffnungszeiten</dt>
                        <dd><c:if test="${not empty spende.benutzer.tafel}">${spende.benutzer.tafel.oeffnungszeiten}</c:if>
                            <c:if test="${not empty spende.benutzer.spender}">${spende.benutzer.spender.oeffnungszeiten}</c:if></dd>
                    </dl>                      
                </li>
                <li>
                    <h4>Kontaktdaten</h4>
                    <dl>
                        <dt>Name</dt>
                        <dd>${spende.kontakt.vorname} ${spende.kontakt.nachname}</dd>

                        <dt>E-Mail</dt>
                        <dd>${spende.kontakt.eMail}</dd>
                        
                        <dt>Telefon</dt>
                        <dd>${spende.kontakt.telefon}</dd>

                        <dt>Telefax</dt>
                        <dd>${spende.kontakt.fax}</dd>

                    </dl>                      
                </li>
            </ul>
            <ul class="table">
                <li>
                    <h4>Abholzeiten</h4>
                    
                    <dt>Zeitraum</dt>
                    <dd>${spende.abholzeiten}</dd>
                    
                    <dt>Abholfrist</dt>
                    <dd><fmt:formatDate value="${formular.abholfrist}" pattern="dd.MM.yyyy" /></dd>
                    
                    <dt>Lieferung</dt>
                    <c:choose> 
                        <c:when test="${spende.lieferung == true}">
                            <dd>Ware wird vom Spender geliefert</dd>
                        </c:when><c:otherwise>
                            <dd>Ware wird von den Tafeln abgeholt</dd>
                        </c:otherwise>
                    </c:choose>

                </li>
                <li>
                    <h4>Karte</h4>
                    <div id="map"></div>                     
                </li>
            </ul>
        <c:if test="${not empty spende.kommentar}">
            <ul class="table">
                <li>
                    <h4>Kommentar</h4>
                    <p>${spende.kommentar}</p>                
                </li>
            </ul>
        </c:if>           
<hr/>  
        <c:if test="${not empty archivliste}">
            <div id="diagramm"></div>
        </c:if>
    <div id="content" class="table">
        <table>
            <thead>
                <tr>
                    <td>Tafel</td>
                    <td>Anzahl</td>
                    <td>Gewicht</td>
                    <td>Paletten</td>
                    <td>Volumen</td>
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
                    <td>${element.tafel.tafelname}</td>
                    <td>${element.anzahl}</td>
                    <td>${element.gewicht} kg</td>
                    <td>${element.paletten}</td>
                    <td>${element.volumen} L</td>
                </tr>
                
            </c:forEach>
                
        </tbody>
        </table> 
    </div>
        </li>
    </ul>
    <div class="buttons">
        <a href="<%=request.getContextPath()%>/spenden/abgeschlossen" class="button minor">Zur&uuml;ck</a>
        <div class="clear"></div>   
    </div>
</div>
<jsp:include page="../footer.jsp"/>
