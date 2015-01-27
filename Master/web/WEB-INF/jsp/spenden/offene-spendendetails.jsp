<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec"     uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../header.jsp"/>

<script type="text/javascript" src="//maps.googleapis.com/maps/api/js?sensor=false&amp;language=de"></script>     
<script type="text/javascript" src="<%=request.getContextPath()%>/js/gmap3.min.js"></script>      
<script type="text/javascript">
    $(function() {
        $('#map').gmap3({
            marker: {
                address: "${spendenvorgang.spende.adresse.plz} ${spendenvorgang.spende.adresse.ort}", options: {icon: "<%=request.getContextPath()%>/img/marker-active.png"}
            },
            map: {
                options: {
                    zoom: 9
                }
            }
        });
        var auswahltotal = parseInt($('span.total').text());
        
    });
</script>

<div id="content" class="list">
    <h2>Spendendetails</h2>
    <ul>
        <li>
            <h3>${spendenvorgang.spende.produktbezeichnung}</h3>


            <ul class="table">
                <li>
                    <h4>Beschreibung</h4>
                    <p>${spendenvorgang.spende.beschreibung}</p>
                </li>
            </ul>
            <ul class="table">
                <li>
                    <h4>Daten</h4>
                    <dl>
                        <dt>Mindesthaltbarkeitsdatum</dt>
                        <dd><fmt:formatDate value="${spendenvorgang.spende.mhd}" pattern="dd.MM.yyyy" /></dd>

                        <dt>Tiefk&uuml;hlware</dt>
                        <dd>${spendenvorgang.spende.kuehlung}</dd>

                    </dl>                        
                </li>
                <li>
                    <h4>Gewicht/Anzahl</h4>
                    <dl>
                        <dt>Menge</dt>
                        <dd>${spendenvorgang.spende.anzahl} St&uuml;ck</dd>

                        <dt>Gesamtgewicht</dt>
                        <dd>${spendenvorgang.spende.gewicht} kg</dd>

                        <dt>Paletten</dt>
                        <dd>${spendenvorgang.spende.paletten}</dd>

                        <dt>Tauschpalette</dt>
                        <dd>${spendenvorgang.spende.tauschpalette}</dd>

                        <c:if test="${not empty spende.volumen}">
                            <dt>Volumen</dt>
                            <dd>${spendenvorgang.spende.volumen} Liter</dd>
                        </c:if>
                    </dl>                        
                </li>
            </ul>
            <ul class="table">
                <li>
                    <h4>Spender</h4>
                    <dl>
                        <dt>Unternehmen</dt>
                        <dd><c:if test="${not empty spendenvorgang.spende.benutzer.tafel}">${spendenvorgang.spende.benutzer.tafel.tafelname}</c:if>
                        <c:if test="${not empty spendenvorgang.spende.benutzer.spender}">${spendenvorgang.spende.benutzer.spender.firmenname}</c:if></dd>

                            <dt>Standort</dt>
                            <dd>${spendenvorgang.spende.adresse.plz} ${spendenvorgang.spende.adresse.ort}</dd>

                    </dl>                      
                </li>
                <li>
                    <h4>Abholung</h4>
                    <dl>

                        <dt>&Ouml;ffnungszeiten</dt>
                        <dd><c:if test="${not empty spendenvorgang.spende.benutzer.tafel}">${spendenvorgang.spende.benutzer.tafel.oeffnungszeiten}</c:if>
                        <c:if test="${not empty spendenvorgang.spende.benutzer.spender}">${spendenvorgang.spende.benutzer.spender.oeffnungszeiten}</c:if></dd>

                            <dt>Abholzeitraum</dt>
                            <dd>${spendenvorgang.spende.abholzeiten}</dd>

                        <dt>Abholfrist</dt>
                        <dd><fmt:formatDate value="${spendenvorgang.spende.abholfrist}" pattern="dd.MM.yyyy" /></dd>

                        <dt>Lieferung</dt>
                        <c:choose> 
                            <c:when test="${spendenvorgang.spende.lieferung == true}">
                                <dd>Ware wird vom Spender geliefert.</dd>
                            </c:when><c:otherwise>
                                <dd>Ware muss abgeholt werden.</dd>
                            </c:otherwise>
                        </c:choose>
                    </dl>                      
                </li>
            </ul>
            <ul class="table">
                <li>
                    <dl>

                    </dl>                        
                </li>
                <li>
                    <h4>Karte</h4>
                    <div id="map"></div>                     
                </li>
            </ul>
            <div class="buttons">
                <button class="annahme button" title="Annehmen &raquo;">Annehmen</button>
                <button class="ablehnen button minor" title="Ablehnen">Ablehnen</button>
                <a href="<%=request.getContextPath()%>/offene-spenden/" class="button minor" title="Abbrechen">Abbrechen</a>
                <div class="clear"></div>
            </div>

            <form:form class="annahme dialog" action="${contextpath}/offene-spenden/rueckmeldung-${spendenvorgang.spendenvorgangsID}/annehmen" method="POST"  commandName="spendenvorgang">
                <h4>Spende Annehmen</h4>
                <p>Gesamtmenge: <span class="total menge">${spendenvorgang.spende.anzahl}</span> St&uuml;ck<br />
                Ausgew&auml;hlte Menge: <span class="current menge">0</span> St&uuml;ck</p>
                
                <c:if test="${spendenvorgang.spende.gewicht > 0}">
                    <p>Gesamtgewicht: <span class="total gewicht">${spendenvorgang.spende.gewicht}</span> kg<br />
                    Ausgew&auml;hltes Gewicht: <span class="current gewicht">0</span> kg</p>
                </c:if>
                    
                <c:if test="${spendenvorgang.spende.volumen > 0}">
                    <p>Gesamtvolumen: <span class="total volumen">${spendenvorgang.spende.volumen}</span> L<br />
                    Ausgew&auml;hltes Volumen: <span class="current volumen">0</span> L</p>
                </c:if>
                    
                <c:if test="${spendenvorgang.spende.paletten > 0}">
                    <p>Gesamtpaletten: <span class="total paletten">${spendenvorgang.spende.paletten}</span><br />
                    Ausgew&auml;hlte Paletten: <span class="current paletten">0</span></p>
                </c:if>
                    
            <form:input path="anteil" type="hidden" id="prozentwert"/>
                <form:label path="anteil">Bitte geben Sie den gew&uuml;nschten Anteil an:</form:label>
                <div id="slider"></div>
                <input type="submit" class="button" value="Bestätigen" />
                <input type="button" class="button minor cancel" value="Abbrechen" />
                <div class="clear"></div>
            </form:form>

            <form:form class="ablehnen dialog" action="${contextpath}/offene-spenden/rueckmeldung-${spendenvorgang.spendenvorgangsID}/ablehnen" method="POST"  commandName="spendenvorgang">
                <h4>Spende Ablehnen</h4>
                <form:label path="spende.kommentar">Hier k&ouml;nnen Sie einen Grund angeben:</form:label>
                <form:input path="spende.kommentar" cssClass="field"/><!-- Hier irgendein Feld mit nem String -->
                <input type="submit" class="button" value="Bestätigen" />
                <input type="button" class="button minor cancel" value="Abbrechen" />
                <div class="clear"></div>
            </form:form>

        </li>
    </ul>
</div>
<jsp:include page="../footer.jsp"/>
