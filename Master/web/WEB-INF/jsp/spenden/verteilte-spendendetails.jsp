<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec"     uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../header.jsp"/>
<script type="text/javascript" src="//maps.googleapis.com/maps/api/js?sensor=false&amp;language=de"></script>     
<script type="text/javascript" src="<%=request.getContextPath()%>/js/gmap3.min.js"></script>    
<script type="text/javascript" src="<%=request.getContextPath()%>/js/highcharts.min.js"></script> 
<script type="text/javascript">
    $(function() {
        $('#map').gmap3({
            marker: {
                address: "${archiv.spende.adresse.strasse} ,${archiv.spende.adresse.plz} ${archiv.spende.adresse.ort}", options:{icon: "<%=request.getContextPath()%>/img/marker-active.png"}
            },
            map: {
                options: {
                    zoom: 9
                }
            }
        });
    });
</script>
<div id="content" class="list">
    <h2>Abgeschlossene Spende im Detail</h2>
    <ul>
        <li>
            <h3>${archiv.spende.produktbezeichnung}</h3>
            <ul class="table">
                <li>
                    <h4>Beschreibung</h4>
                    <p>${archiv.spende.beschreibung}</p>
                    
                </li>
            </ul>
            <ul class="table">
                <li>
                    <h4>Daten</h4>
                    <dl>
                        <dt>Erstellungsdatum</dt>
                        <dd><fmt:formatDate value="${archiv.spende.erstellungsdatum}" pattern="dd.MM.yyyy" /></dd>
                        
                        <dt>Mindesthaltbarkeitsdatum</dt>
                        <dd><fmt:formatDate value="${archiv.spende.mhd}" pattern="dd.MM.yyyy" /></dd>

                        <dt>R&uuml;ckmeldefrist</dt>
                        <dd><fmt:formatDate value="${archiv.spende.rueckmeldefrist}" pattern="dd.MM.yyyy" /></dd>

                        <dt>Tiefk&uuml;hlware</dt>
                        <dd>${archiv.spende.kuehlung}</dd>
                        
                        <c:if test="${archiv.spende.spendenbeleg == true}">
                            <dt>Spendenbeleg</dt>
                            <dd>erw&uuml;nscht</dd>
                        </c:if>
                        
                        <c:if test="${archiv.spende.alkohol == true}">
                            <dt>Enth&auml;lt Alkohol</dt>
                            <dd>Alkohol enthalten</dd>
                        </c:if>
                    </dl>                        
                </li>
                <li>
                    <h4>Gewicht/Anzahl</h4>
                    <dl>
                        <dt>Menge</dt>
                        <dd>${archiv.spende.anzahl} St&uuml;ck</dd>

                        <dt>Gesamtgewicht</dt>
                        <dd>${archiv.spende.gewicht} kg</dd>
                        
                        <dt>Paletten</dt>
                        <dd>${archiv.spende.paletten}</dd>

                        <dt>Tauschpalette</dt>
                        <dd>${archiv.spende.tauschpalette}</dd>
                        
                        <c:if test="${not empty archiv.spende.volumen}">
                            <dt>Volumen</dt>
                            <dd>${archiv.spende.volumen} Liter</dd>
                        </c:if>
                    </dl>                        
                </li>
            </ul>
            <ul class="table">
                <li>
                    <h4>Spender</h4>
                    <dl>
                        <dt>Unternehmen</dt>
                        <dd><c:if test="${not empty archiv.spende.benutzer.tafel}"> ${archiv.spende.benutzer.tafel.tafelname}</c:if>
                        <c:if test="${not empty archiv.spende.benutzer.spender}"> ${archiv.spende.benutzer.spender.firmenname}</c:if></dd>
                        
                        <dt>Standort</dt>
                        <dd>${archiv.spende.adresse.strasse}<br>
                            ${archiv.spende.adresse.plz} ${archiv.spende.adresse.ort}</dd>

                        <dt>&Ouml;ffnungszeiten</dt>
                        <dl>
                            <dd><c:if test="${not empty archiv.spende.benutzer.tafel}"> ${archiv.spende.benutzer.tafel.oeffnungszeiten}</c:if>
                            <c:if test="${not empty archiv.spende.benutzer.spender}">${archiv.spende.benutzer.spender.oeffnungszeiten}</c:if></dd>
                        </dl> 
                    </dl>                      
                </li>
                <li>
                    <h4>Kontaktdaten</h4>
                    <dl>
                        <dt>Name</dt>
                        <dd>${archiv.spende.kontakt.vorname} ${archiv.spende.kontakt.nachname}</dd>

                        <dt>E-Mail</dt>
                        <dd>${archiv.spende.kontakt.eMail}</dd>
                        
                        <dt>Telefon</dt>
                        <dd>${archiv.spende.kontakt.telefon}</dd>

                        <dt>Telefax</dt>
                        <dd>${archiv.spende.kontakt.fax}</dd>

                    </dl>                      
                </li>
            </ul>
            <ul class="table">
                <li>
                    <h4>Abholzeiten</h4>
                    
                    <dt>Abholfrist</dt>
                    <dd><fmt:formatDate value="${archiv.spende.abholfrist}" pattern="dd.MM.yyyy" /></dd>
                    
                    <dt>Zeitraum</dt>
                    <dd>${archiv.spende.abholzeiten}</dd>
                    
                    <dt>Lieferung</dt>
                    <c:choose> 
                        <c:when test="${archiv.spende.lieferung == true}">
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
<hr/>  
    <div id="content" class="table"> 
        <table>
            <thead>
                <tr>
                    <td>Tafel</td>
                    <td>Anzahl</td>
                    <td>Gewicht</td>
                    <td>Paletten</td>
                    <td>Liter</td>
                </tr>
            </thead>
            <tbody>          
                
                <tr class="${rowColor}">
                    <td>${archiv.tafel.tafelname}</td>
                    <td>${archiv.anzahl}</td>
                    <td>${archiv.gewicht} kg</td>
                    <td>${archiv.paletten}</td>
                    <td>${archiv.volumen} L</td>
                </tr>
                                
        </tbody>
        </table> 
    </div>
        </li>
    </ul>
    <div class="buttons">
        <a href="<%=request.getContextPath()%>/verteilte-spenden" class="button minor">Zur&uuml;ck</a>
        <div class="clear"></div>   
    </div>
</div>
<jsp:include page="../footer.jsp"/>