<%@ page contentType="text/html" pageEncoding="UTF-8" language="java" buffer="64kb"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"     uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>

<jsp:include page="../header.jsp"/>

<form:form method="POST" commandName="formular">
    <form:errors path="*" cssClass="fehler" element="div" />
    ${nachricht}
    <div id="content">
        <h2>Spender: ${formular.firmenname}</h2>
        <ul>
            <li>
                <h3>Allgemein</h3> <form:hidden path="id" />
            </li>
            <li>
                <form:label path="status">Status</form:label>
                    <div class="select">
                    <form:select path="status" cssClass="field">
                        <form:option value="grün" label="grün" />
                        <form:option value="gelb" label="gelb" />
                    </form:select>
                </div> 
                <dl class="info">
                    <dt>&#x69;</dt>
                    <dd>Status gelb: Spender wurde bereits verwarnt.</dd>
                </dl>
            </li>
            <li>
                <form:label path="firmenname">Firmenname *</form:label>
                <form:input path="firmenname" cssClass="field"  />
            </li>
            <li>
                <form:label path="oeffnungszeiten">&Ouml;ffnungszeiten</form:label>
                <form:input path="oeffnungszeiten" cssClass="field"  />
            </li>
            <li>
                <form:label path="sicherheitsbestimmungen">Sicherheitsbestimmungen</form:label>
                <form:input path="sicherheitsbestimmungen" cssClass="field"  />
            </li>
            <li>
                <form:label path="notizen">Notizen</form:label>
                <form:textarea path="notizen" cssClass="field"  />
            </li>
        </ul>

        <hr>
        <ul>
            <li>
                <h3>Adresse</h3> <form:hidden path="adresse.adressID" />
            </li>
            <li>
                <form:label path="adresse.beschreibung">Beschreibung</form:label>
                <form:input path="adresse.beschreibung" cssClass="field"  />
            </li>
            <li>
                <form:label path="adresse.strasse">Stra&szlig;e * / Nr. *</form:label>
                <form:input path="adresse.strasse" cssClass="field"  />
            </li>

            <li>
                <form:label path="adresse.plz">PLZ *</form:label>
                <form:input path="adresse.plz" cssClass="field"  />
            </li>

            <li>
                <form:label path="adresse.ort">Ort *</form:label>
                <form:input path="adresse.ort" cssClass="field"  />
            </li>
        </ul>


        <hr>
        <ul>
            <li>
                <h3>Kontakt</h3><form:hidden path="kontakt.kontaktID" />
            </li>
            <li>
                <form:label path="kontakt.eMail">E-Mail *</form:label>
                <form:input path="kontakt.eMail" cssClass="field"  />
            </li>
            <li>
                <form:label path="kontakt.vorname">Vorname *</form:label>
                <form:input path="kontakt.vorname" cssClass="field"  />
            </li>
            <li>
                <form:label path="kontakt.nachname">Nachname *</form:label>
                <form:input path="kontakt.nachname" cssClass="field"  />
            </li>
            <li>
                <form:label path="kontakt.telefon">Telefon</form:label>
                <form:input path="kontakt.telefon" cssClass="field" type ="tel"  />
            </li>
            <li>
                <form:label path="kontakt.fax">Fax</form:label>
                <form:input path="kontakt.fax" cssClass="field" type="tel"  />
            </li>
        </ul>

        <hr>
        <div class="buttons">
            <a href="<%=request.getContextPath()%>/spenderverwaltung/" class="button minor">Abbrechen</a>


            <input type="submit" value="Aktualisieren &raquo;" class="button"  /> 
            <div class="clear"></div>   
        </div>
        <br>
        <p>Alle mit * gekennzeichneten Felder sind Pflichtfelder.</p>
    </div>
</form:form>
<jsp:include page="../footer.jsp"/>
