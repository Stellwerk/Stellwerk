<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"     uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../header.jsp"/>
<script type="text/javascript" src="//maps.googleapis.com/maps/api/js?sensor=false&amp;language=de"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/gmap3.min.js"></script>
<script type="text/javascript">
    $(function() {
        $('#map').gmap3({
            marker: {
                address: "${formular.adresse.strasse}, ${formular.adresse.plz} ${formular.adresse.ort}"
            },
            map: {
                options: {
                    zoom: 14
                }
            }
        });
    });
</script>
<div id="content" class="list">
    <h2>Folgende Spende wurde in unserem System erfasst:</h2>
    <ul>
        <li>
            <h3>${formular.produktbezeichnung}</h3>
            <ul class="table">
                <li>
                    <h4>Beschreibung</h4>
                    <p>${formular.beschreibung}</p>
                </li>
            </ul>
            <ul class="table">
                <li>
                    <h4>Daten</h4>
                    <dl>
                        <dt>Mindesthaltbarkeitsdatum</dt>
                        <dd><fmt:formatDate value="${formular.mhd}" pattern="dd.MM.yyyy" /></dd>

                        <dt>Art</dt>
                        <dd>${formular.art}</dd>

                        <c:if test="${formular.alkohol == true}">
                            <dt>Enth&auml;lt Alkohol</dt>
                            <dd>Alkohol enthalten</dd>
                        </c:if>

                        <dt>R&uuml;ckmeldefrist</dt>
                        <dd><fmt:formatDate value="${formular.rueckmeldefrist}" pattern="dd.MM.yyyy" /></dd>

                        <dt>Tiefk&uuml;hlware</dt>
                        <dd>${formular.kuehlung}</dd>

                        <c:if test="${formular.lieferung == true}">
                            <dt>Lieferung</dt>
                            <dd>ja</dd>
                        </c:if>
                        <c:if test="${formular.lieferung == false}">
                            <dt>Lieferung</dt>
                            <dd>nein</dd>
                        </c:if>

                        <dt>Sicherheitsbestimmungen</dt>
                        <dd>${formular.sicherheitsbestimmungen}</dd>

                        <c:if test="${formular.spendenbeleg == true}">
                            <dt>Spendenbeleg</dt>
                            <dd>erw&uuml;nscht</dd>
                        </c:if>

                        <dt>Kommentar an Koordinator</dt>
                        <dd>${formular.kommentar}</dd>

                        <dt>Bevorzugte Tafeln</dt>
                        <dd>${formular.bevorzugte_Tafeln}</dd>
                    </dl>
                </li>
                <li>
                    <h4>Gewicht/Anzahl</h4>
                    <dl>
                        <dt>Anzahl Paletten</dt>
                        <dd>${formular.paletten}</dd>

                        <dt>Tauschpalette</dt>
                        <dd>${formular.tauschpalette}</dd>

                        <dt>Anzahl</dt>
                        <dd>${formular.anzahl} St&uuml;ck</dd>

                        <dt>Gewicht</dt>
                        <dd>${formular.gewicht} kg</dd>

                        <dt>Volumen</dt>
                        <dd>${formular.volumen} Liter</dd>
                    </dl>
                </li>
            </ul>
            <ul class="table">
                <li>
                    <h4>Spender</h4>
                    <dl>
                        <dt>Standort</dt>
                        <dd>${formular.adresse.strasse}<br>
                            ${formular.adresse.plz} ${formular.adresse.ort} <br>
                            ${formular.adresse.beschreibung}</dd>

                        <dt>Abholfrist und -zeiten</dt>
                        <dd><fmt:formatDate value="${formular.abholfrist}" pattern="dd.MM.yyyy" /><br>
                            ${formular.abholzeiten}</dd>
                    </dl>
                </li>
                <li>
                    <h4>Karte</h4>
                    <div id="map"></div>
                </li>
            </ul>
            <ul class="table">
                <li>
                    <h4>Kontaktdaten</h4>
                    <dl>
                        <dt>Name</dt>
                        <dd>${formular.kontakt.vorname} ${formular.kontakt.nachname}</dd>

                        <dt>E-Mail</dt>
                        <dd>${formular.kontakt.eMail}</dd>

                        <dt>Telefon</dt>
                        <dd>${formular.kontakt.telefon}</dd>

                        <dt>Telefax</dt>
                        <dd>${formular.kontakt.fax}</dd>
                    </dl>
                </li>
            </ul>
        </li>
    </ul>
</div>

<jsp:include page="../footer.jsp"/>
