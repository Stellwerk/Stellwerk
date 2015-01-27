<%@page contentType="text/html" pageEncoding="UTF-8" buffer="64kb"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec"     uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../header.jsp"/>
<script type="text/javascript" src="//maps.googleapis.com/maps/api/js?sensor=false&amp;language=de"></script>     
<script type="text/javascript" src="<%=request.getContextPath()%>/js/gmap3.min.js"></script>      
<script type="text/javascript">
    $(function() {
        $('#map').gmap3({
            marker: {
                address: "${spende.adresse.strasse}, ${spende.adresse.plz} ${spende.adresse.ort}", options:{icon: "<%=request.getContextPath()%>/img/marker-active.png"}
            },
            map: {
                options: {
                    zoom: 9
                }
            }
        });
    });
</script>
<c:if test="${not empty Ausgabe}">
    ${Ausgabe}
</c:if>
<div id="content" class="list">
    <h2>Spendendetails</h2>
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
                        <dt>Status</dt>
                        <dd>${spende.status}</dd>
                            
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
                            
                            <dt>Bevorzugte Tafeln</dt>
                            <dd>${spende.bevorzugte_Tafeln}</dd>
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
                    
                    <dt>Abholfrist</dt>
                    <dd><fmt:formatDate value="${spende.abholfrist}" pattern="dd.MM.yyyy" /></dd>
                    
                    <dt>Zeitraum</dt>
                    <dd>${spende.abholzeiten}</dd>
                    
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
            <div class="buttons">
                <a href="${linkverteilen}" class="button" title="Verteilen &raquo;">Verteilen &raquo;</a>
                <a href="${linkbearbeiten}" class="button minor" title="Bearbeiten">Bearbeiten</a>
                <button type="button" class="annahme button minor" title="L&ouml;schen">L&ouml;schen</button>
            <div class="clear"></div>   
            </div>
                
            <%-- Bestätigung EMail senden --%>
            <div class="annahme dialog">
                <h4>Spende l&ouml;schen</h4>
                
                <p>Dieser Schritt kann nicht r&uuml;ckg&auml;ngig gemacht werden!</p>
                <input type="button" class="button minor cancel" value="Abbrechen" />
                <a href="<%=request.getContextPath()%>/spende/${spende.spendeID}/loeschen" class="button minor" title="L&ouml;schen">L&ouml;schen</a>
            </div> 
        
        </li>
    </ul>
</div>
<jsp:include page="../footer.jsp"/>