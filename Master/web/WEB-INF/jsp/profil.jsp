<%@ page contentType="text/html" pageEncoding="UTF-8" language="java" buffer="64kb"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"     uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>

<jsp:include page="header.jsp"/>

<sec:authorize access="hasAnyRole('ROLE_SPENDER', 'ROLE_KOORD', 'ROLE_TAFEL', 'ROLE_ADMIN')">
    <c:set var="readonly" value="${true}"/>
</sec:authorize>

<form:form method="POST" commandName="Profil">
    <form:errors path="*" cssClass="fehler" element="div" />
    ${Profil.nachricht}
    ${textDeaktiviert}
    <div id="content">
        <h2>Mein Profil</h2>

        <ul>
            <li>
                <h3>Benutzerkonto</h3> <form:input path="benutzer.benutzerID" type="hidden" />
            </li>
            <li>
                <form:label path="benutzer.vorname">Vorname *</form:label>
                <form:input path="benutzer.vorname" cssClass="field" readonly="${readonly}" />
                <dl class="info">
                    <dt>&#x69;</dt>
                    <dd>Zum &Auml;ndern Ihres Namens und Ihrer E-Mail-Adresse wenden Sie sich bitte an den Administrator.</dd>
                </dl>
            </li>                    

            <li>
                <form:label path="benutzer.nachname">Nachname *</form:label>
                <form:input path="benutzer.nachname" cssClass="field" readonly="${readonly}" />
            </li>
            <li>
                <form:label path="benutzer.eMail">E-Mail *</form:label>
                <form:input path="benutzer.eMail" cssClass="field" readonly="${readonly}" />
            </li>
        </ul>
        <sec:authorize access="hasAnyRole('ROLE_SPENDER', 'ROLE_TAFEL', 'ROLE_KOORD')">


            <sec:authorize access="hasAnyRole('ROLE_SPENDER')">
                <hr>
                <ul>
                    <li>
                        <h3>Organisation</h3> <form:hidden path="spender.id" />
                    </li>
                    <li>
                        <form:label path="spender.firmenname">Firmenname *</form:label>
                        <form:input path="spender.firmenname" cssClass="field" readonly="${readonly}" />
                    </li>
                    <li>
                        <form:label path="spender.oeffnungszeiten">&Ouml;ffnungszeiten</form:label>
                        <form:input path="spender.oeffnungszeiten" cssClass="field" />
                    </li>
                    <li>
                        <form:label path="spender.sicherheitsbestimmungen">Sicherheitsbestimmungen</form:label>
                        <form:input path="spender.sicherheitsbestimmungen" cssClass="field" />
                    </li>
                </ul>
            </sec:authorize>
            <%-- Prüfung TafelKoord wurde implementiert falls ein Koordinator zusätzlich eine Tafel zugewiesen bekommt. Diese Funktion wurde im Verlauf des Projektes wurde diese Funktion nicht realisiert, wurde aber für evtl. nachstehende Entwicklungen beibehalten. --%>
            <c:if test ="${Profil.rolle=='Tafel' || Profil.rolle=='TafelKoord'}">
                <hr>
                <ul>
                    <li>
                        <h3>Tafel</h3> <form:hidden path="tafel.id" />
                    </li>
                    <li>
                        <form:label path="tafel.tafelname">Tafelname *</form:label>
                        <form:input path="tafel.tafelname" cssClass="field" readonly="${readonly}" />
                        <dl class="info">
                            <dt>&#x69;</dt>
                            <dd>Zum &Auml;ndern des Tafelnamens wenden Sie sich bitte an den Administrator.</dd>
                        </dl>
                    </li>
                    <li>
                        <form:label path="tafel.oeffnungszeiten">&Ouml;ffnungszeiten</form:label>
                        <form:input path="tafel.oeffnungszeiten" cssClass="field"  />
                    </li>
                    <li>
                        <form:label path="tafel.transporter">Transporter</form:label>
                        <form:input path="tafel.transporter" cssClass="field"  />
                    </li>
                </ul>

            </c:if>
            <c:if test ="${Profil.rolle=='Tafel' || Profil.rolle=='TafelKoord' || Profil.rolle == 'Spender'}">
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


                </c:if>    
                <c:if test ="${Profil.rolle=='Tafel' || Profil.rolle=='TafelKoord' || Profil.rolle == 'Spender'}">
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
                </c:if> 

            </sec:authorize>    
            <hr>
            <ul>
                <li>
                    <h3>Passwort &auml;ndern?</h3>
                </li>
                <li>
                    <form:label path="neuesPasswort">Neues Passwort</form:label>
                    <form:input path="neuesPasswort" cssClass="field" type="password" />
                </li>
                <li>
                    <form:label path="bestNeuesPasswort">Passwort wiederholen</form:label>
                    <form:input path="bestNeuesPasswort" cssClass="field" type="password" />
                </li>
            </ul>
            <hr>
            <ul>
                <li>
                    <h3>Passwort best&auml;tigen</h3>
                </li>
                <li>
                    <form:label path="passwortkontrolle">Passwort *</form:label>
                    <form:input path="passwortkontrolle" cssClass="field" type="password"  />
                    <dl class="info">
                        <dt>&#x69;</dt>
                        <dd>Zum Best&auml;tigen ihrer &Auml;nderungen bitte ihr aktuelles Passwort eingeben.</dd>
                    </dl>
                </li>
            </ul>
            <hr>
            <div class="buttons">
                <a href="<%=request.getContextPath()%>" class="button minor">Abbrechen</a>


                <input type="submit" value="Aktualisieren &raquo;" class="button"  /> 
                <div class="clear"></div>   
            </div>
            <br>
            <p>Alle mit * gekennzeichneten Felder sind Pflichtfelder.</p>
    </div>
</form:form>
<jsp:include page="footer.jsp"/>
