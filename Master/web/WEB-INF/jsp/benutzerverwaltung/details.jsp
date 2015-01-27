<%@ page contentType="text/html" pageEncoding="UTF-8" language="java" buffer="64kb"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"     uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>

<jsp:include page="../header.jsp"/>
<c:if test="${not empty Ausgabe}">
    <c:out value="${Ausgabe}"></c:out>
</c:if>
<form:form method="POST"  modelAttribute="benutzer">
    <form:errors path="*" cssClass="fehler" element="div" />
    <div id="content">
        <h2>Benutzerdetails</h2>

        <%-- ############################Allgemein############################## --%>


        <ul>
            <%-- Dieser Teil ist zum Ändern der Rolle, führte jedoch zu Fehlern, die noch aufgefangen werden müssen (nullpointer)
            <li>
                <form:label path="rolle">Funktionsumfang</form:label>
                <div class="select">
                        <form:select path="rolle" cssClass="field">
                                <form:option value="Spender" label="Spender" />
                                <form:option value="Tafel" label="Tafel" />
                                <form:option value="Koordinator" label="Koordinator" />
                                <form:option value="Administrator" label="Administrator" />
                        </form:select>
                </div>
            </li>
            --%>
            <form:input path="rolle" type="hidden" />
            <li>
                <form:label path="aktiviert">Aktiviert</form:label>
                    <div class="select">
                    <form:select path="aktiviert" cssClass="field">
                        <form:option value="1" label="Aktiviert" />
                        <form:option value="0" label="Deaktiviert" />
                    </form:select>
                </div> 
                <dl class="info">
                    <dt>&#x69;</dt>
                    <dd>Achtung: Deaktivierte Benutzer k&ouml;nnen sich nicht im System anmelden!</dd>
                </dl>
            </li>
            <!-- Break -->     
            <hr/>
            <li>
                <h3>Benutzerkonto</h3>
            </li>
            <li>
                <form:label path="eMail">E-Mail *</form:label>
                <form:input path="eMail" cssClass="field" />
            </li>
            <li>
                <form:label path="vorname">Vorname *</form:label>
                <form:input path="vorname" cssClass="field"/>
            </li>                    
            <li>
                <form:label path="nachname">Nachname *</form:label>
                <form:input path="nachname" cssClass="field" />
            </li>
            <form:input path="passwort" cssClass="field" type="hidden" />
            <%-- ############################Spender############################## --%>    

            <!-- Benutzer ist einem Spender zugewiesen -->     
            <c:if test="${benutzer.rolle == 'Spender' && benutzer.spender != null}">
                <form:input path="spender.id" type="hidden" />
                <hr/>
                <li>
                    <h3>Spender</h3>
                </li>
                <li>
                    <form:label path="spender.firmenname">Firmenname *</form:label>
                    <form:input path="spender.firmenname" cssClass="field" />
                </li>
                <li>
                    <form:label path="spender.sicherheitsbestimmungen">Sicherheitsbestimmungen</form:label>
                    <form:input path="spender.sicherheitsbestimmungen" cssClass="field" />
                </li>
                <li>
                    <form:label path="spender.oeffnungszeiten">&Ouml;ffnungszeiten</form:label>
                    <form:input path="spender.oeffnungszeiten" cssClass="field" />
                </li>
                <li>
                    <form:label path="spender.status">Status</form:label>
                        <div class="select">
                        <form:select path="spender.status" cssClass="field">
                            <form:option value="grün" label="grün" />
                            <form:option value="gelb" label="gelb" />
                        </form:select>
                    </div> 
                    <dl class="info">
                        <dt>&#x69;</dt>
                        <dd>Der Status ist nur für den Koordinator sichtbar und wichtig. Ebenso kann für bestimmte Vorf&auml;lle ein Kommentar zu der Organisation hinterlegt werden.</dd>
                    </dl>
                </li>
                <li>
                    <form:label path="spender.notizen">Notizen</form:label>
                    <form:textarea path="spender.notizen"></form:textarea>
                    </li>
            </c:if>
            <c:if test="${benutzer.rolle == 'Spender' && benutzer.spender == null && orgaliste.spender != null}">
                <%-- #### Spenderliste anzeigen ####--%>
                <li>
                    <form:form action="/benutzerverwaltung/${benutzer.benutzerID}/details/bestaetigung" method="POST"  modelAttribute="orgaliste">
                        <form:label path="">Spender</form:label>
                            <div class="select">
                            <form:select path="selectedID" cssClass="field">
                                <%-- #### Schleife ####--%>
                                <c:forEach items="${orgaliste.spender}" var="element" varStatus="line" >
                                    <form:option value="${element.id}" label="${element.firmenname}" />
                                </c:forEach>
                            </form:select>
                        </div>
                        <dl class="info">
                            <dt>&#x69;</dt>
                            <dd>Bitte dem Benutzer einen Spender zuweisen! (ggf. Spender vorher anlegen!)</dd>
                        </dl>
                    </form:form>
                </li>

                <!-- 
                    neuen Spender zuweisen mit Pulldown Menü?
                -->
            </c:if>
            <c:if test="${benutzer.rolle == 'Spender' && benutzer.spender.adresse != null}">
                <form:input path="spender.adresse.adressID" type="hidden" />
                <hr/>
                <li>
                    <h3>Spender - Adresse</h3>
                </li>
                <li>
                    <form:label path="spender.adresse.strasse">Stra&szlig;e * / Nr. *</form:label>
                    <form:input path="spender.adresse.strasse" cssClass="field" />
                </li>
                <li>
                    <form:label path="spender.adresse.plz">PLZ *</form:label>
                    <form:input path="spender.adresse.plz" cssClass="field" />
                </li>
                <li>
                    <form:label path="spender.adresse.ort">Ort *</form:label>
                    <form:input path="spender.adresse.ort" cssClass="field" />
                </li>
                <li>
                    <form:label path="spender.adresse.beschreibung">Beschreibung</form:label>
                    <form:input path="spender.adresse.beschreibung" cssClass="field" />
                </li>
            </c:if>  
            <c:if test="${benutzer.rolle == 'Spender' && benutzer.spender.kontakt != null}">
                <form:input path="spender.kontakt.kontaktID" type="hidden" />
                <hr/>
                <li>
                    <h3>Spender - Kontakt</h3>
                </li>
                <li>
                    <form:label path="spender.kontakt.vorname">Vorname *</form:label>
                    <form:input path="spender.kontakt.vorname" cssClass="field" />
                </li>
                <li>
                    <form:label path="spender.kontakt.nachname">Nachname *</form:label>
                    <form:input path="spender.kontakt.nachname" cssClass="field" />
                </li>
                <li>
                    <form:label path="spender.kontakt.eMail">E-Mail *</form:label>
                    <form:input path="spender.kontakt.eMail" cssClass="field" />
                </li>
                <li>
                    <form:label path="spender.kontakt.telefon">Telefon</form:label>
                    <form:input path="spender.kontakt.telefon" cssClass="field" />
                </li>
                <li>
                    <form:label path="spender.kontakt.fax">Fax</form:label>
                    <form:input path="spender.kontakt.fax" cssClass="field" />
                </li>
            </c:if> 

            <%-- ############################Tafel############################## --%>

            <!-- Benutzer ist einer Tafel zugewiesen -->     
            <c:if test="${benutzer.rolle == 'Tafel' && benutzer.tafel != null}">
                <form:input path="tafel.id" type="hidden" />
                <hr/>
                <li>
                    <h3>Tafel</h3>
                </li>
                <li>
                    <form:label path="tafel.tafelname">Tafelname *</form:label>
                    <form:input path="tafel.tafelname" cssClass="field" />
                </li>
                <li>
                    <form:label path="tafel.transporter">Transporter</form:label>
                    <form:input path="tafel.transporter" cssClass="field" />
                </li>
                <li>
                    <form:label path="tafel.oeffnungszeiten">&Ouml;ffnungszeiten</form:label>
                    <form:input path="tafel.oeffnungszeiten" cssClass="field" />
                </li>
                <li>
                    <form:label path="tafel.status">Status</form:label>
                        <div class="select">
                        <form:select path="tafel.status" cssClass="field">
                            <form:option value="grün" label="grün" />
                            <form:option value="gelb" label="gelb" />
                        </form:select>
                    </div> 
                    <dl class="info">
                        <dt>&#x69;</dt>
                        <dd>Der Status ist nur für den Koordinator sichtbar und wichtig. Ebenso kann für bestimmte Vorf&auml;lle ein Kommentar zu der Organisation hinterlegt werden.</dd>
                    </dl>
                </li>
                <li>
                    <form:label path="tafel.notizen">Notizen</form:label>
                    <form:textarea path="tafel.notizen"></form:textarea>
                    </li>
            </c:if>
            <c:if test="${benutzer.rolle == 'Tafel' && benutzer.tafel == null && orgaliste.tafel != null}">
                <%-- #### Spenderliste anzeigen ####--%>
                <li>
                    <form:form action="/benutzerverwaltung/${benutzer.benutzerID}/details/bestaetigung" method="POST"  modelAttribute="orgaliste">
                        <form:label path="">Tafel</form:label>
                            <div class="select">
                            <form:select path="selectedID" cssClass="field">
                                <%-- #### Schleife ####--%>
                                <c:forEach items="${orgaliste.tafel}" var="element" varStatus="line" >
                                    <form:option value="${element.id}" label="${element.tafelname}" />
                                </c:forEach>
                            </form:select>
                        </div>
                        <dl class="info">
                            <dt>&#x69;</dt>
                            <dd>Bitte dem Benutzer eine Tafel zuweisen! (ggf. Tafel vorher anlegen!)</dd>
                        </dl>
                    </form:form>
                </li>
                <!-- 
                    neuen Spender zuweisen mit Pulldown Menü?
                -->
            </c:if>
            <c:if test="${benutzer.rolle == 'Tafel' && benutzer.tafel.adresse != null}">
                <form:input path="tafel.adresse.adressID" type="hidden" />
                <hr/>
                <li>
                    <h3>Tafel - Adresse</h3>
                </li>
                <li>
                    <form:label path="tafel.adresse.strasse">Stra&szlig;e * / Nr. *</form:label>
                    <form:input path="tafel.adresse.strasse" cssClass="field" />
                </li>
                <li>
                    <form:label path="tafel.adresse.plz">PLZ *</form:label>
                    <form:input path="tafel.adresse.plz" cssClass="field" />
                </li>
                <li>
                    <form:label path="tafel.adresse.ort">Ort *</form:label>
                    <form:input path="tafel.adresse.ort" cssClass="field" />
                </li>
                <li>
                    <form:label path="tafel.adresse.beschreibung">Beschreibung</form:label>
                    <form:input path="tafel.adresse.beschreibung" cssClass="field" />
                </li>
            </c:if>  
            <c:if test="${benutzer.rolle == 'Tafel' && benutzer.tafel.kontakt != null}">
                <form:input path="tafel.kontakt.kontaktID" type="hidden" />
                <hr/>
                <li>
                    <h3>Tafel - Kontakt</h3>
                </li>
                <li>
                    <form:label path="tafel.kontakt.vorname">Vorname *</form:label>
                    <form:input path="tafel.kontakt.vorname" cssClass="field" />
                </li>
                <li>
                    <form:label path="tafel.kontakt.nachname">Nachname *</form:label>
                    <form:input path="tafel.kontakt.nachname" cssClass="field" />
                </li>
                <li>
                    <form:label path="tafel.kontakt.eMail">E-Mail *</form:label>
                    <form:input path="tafel.kontakt.eMail" cssClass="field" />
                </li>
                <li>
                    <form:label path="tafel.kontakt.telefon">Telefon</form:label>
                    <form:input path="tafel.kontakt.telefon" cssClass="field" />
                </li>
                <li>
                    <form:label path="tafel.kontakt.fax">Fax</form:label>
                    <form:input path="tafel.kontakt.fax" cssClass="field" />
                </li>
            </c:if>    
        </ul>        
        <hr>
        <div class="buttons">
            <a href="<%=request.getContextPath()%>/benutzerverwaltung" class="button minor">Abbrechen</a>
            <c:if test="${benutzer.rolle != 'Administrator' and benutzer.rolle != 'Koordinator'}">
                <a href="<%=request.getContextPath()%>/benutzerverwaltung/loesen/${benutzer.benutzerID}" class="button minor">L&ouml;sen</a>
            </c:if>
            <a href="<%=request.getContextPath()%>/benutzerverwaltung/${benutzer.benutzerID}/kennwort" class="button" title="Benutzer hinzuf&uuml;gen &raquo;">Kennwort &auml;ndern</a>
            <input type="submit" value="Weiter &raquo;" class="button"> 

            <div class="clear"></div>   
        </div>
        <br>
        <p>Alle mit * gekennzeichneten Felder sind Pflichtfelder.</p>
    </div>
</form:form>
<jsp:include page="../footer.jsp"/>