<%@ page contentType="text/html" pageEncoding="UTF-8" language="java" buffer="64kb"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"     uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>

<jsp:include page="../header.jsp"/>

<c:if test="${not empty Ausgabe}">
    ${Ausgabe}
</c:if>

<form:form method="POST"  modelAttribute="tafel">

    <form:errors path="*" cssClass="fehler" element="div" />
    <div id="content">
        <h2>Tafel hinzuf&uuml;gen</h2>
        <ul>
            <li>
                <h3>Tafel</h3>
            </li>
            <li>
                <form:label path="tafelname">Tafelname *</form:label>
                <form:input path="tafelname" cssClass="field"/>
            </li>
            <li>
                <form:label path="transporter">Transporter</form:label>
                <form:input path="transporter" cssClass="field" />
            </li>
            <li>
                <form:label path="oeffnungszeiten">&Ouml;ffnungszeiten</form:label>
                <form:textarea path="oeffnungszeiten"></form:textarea>
            </li>
        </ul>
        <hr>
        
        <ul>
            <li>
                <h3>Adresse</h3>
            </li>
            <li>
                <form:label path="adresse.strasse">Stra&szlig;e * / Nr. *</form:label>
                <form:input path="adresse.strasse" cssClass="field" />
            </li>
            <li>
                <form:label path="adresse.plz">PLZ * / Ort *</form:label>
                <form:input path="adresse.plz" class="field small1" title="Postleitzahl bestehend aus 5 Zahlen! BSP: 49808"/>
                <form:input path="adresse.ort" class="field small3"/>
            </li>
            <li>
                <form:label path="adresse.beschreibung">Beschreibung</form:label>
                <form:input path="adresse.beschreibung" cssClass="field" />
            </li>
        </ul>
        <hr>
        
        <ul>
            <li>
                <h3>Kontakt</h3>
            </li>
            <li>
                <form:label path="kontakt.vorname">Vorname *</form:label>
                <form:input path="kontakt.vorname" cssClass="field" />
            </li>
            <li>
                <form:label path="kontakt.nachname">Nachname *</form:label>
                <form:input path="kontakt.nachname" cssClass="field" />
            </li>
            <li>
                <form:label path="kontakt.eMail">E-Mail *</form:label>
                <form:input path="kontakt.eMail" cssClass="field" />
            </li>
            <li>
                <form:label path="kontakt.telefon">Telefon</form:label>
                <form:input path="kontakt.telefon" cssClass="field" />
            </li>
            <li>
                <form:label path="kontakt.fax">Fax</form:label>
                <form:input path="kontakt.fax" cssClass="field" />
            </li>
        </ul>
        <hr>
        <div class="buttons">
            <a href="<%=request.getContextPath()%>/tafelverwaltung" class="button minor">Abbrechen</a>
            <input type="submit" value="Weiter &raquo;" class="button"> 
            <div class="clear"></div>   
        </div>
        <br>
        <p>Alle mit * gekennzeichneten Felder sind Pflichtfelder.</p>
    </div>
</form:form>
<jsp:include page="../footer.jsp"/>
