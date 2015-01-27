<%@page contentType="text/html" pageEncoding="UTF-8" language="java" buffer="64kb"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"     uri="http://www.springframework.org/security/tags" %>

<jsp:include page="../header.jsp"/>
<c:if test="${not empty Ausgabe}">
    ${Ausgabe}
</c:if>
<form:form method="POST"  commandName="Spende">
    <form:errors path="*" cssClass="fehler" element="div" />
    <div id="content">
        <h2>Spende einreichen</h2>
        <ul>
            <%-- http://html5pattern.com/ --%>
            <%-- Produktdaten --%>
            <li>
                <h3>Allgemeine Angaben</h3>
                <ul>
                    <li>
                        <form:label for="produktbezeichnung" path="Spende.Produktbezeichnung">Produktbezeichnung *</form:label>
                        <form:input id="produktbezeichnung" path="Spende.Produktbezeichnung" cssClass="field" />
                        <dl class="info">
                            <dt>&#x69;</dt>
                            <dd>Die Produktbezeichnung ist der Produktname.</dd>
                        </dl>
                    </li>
                    <li>         
                        <form:label for="rueckmeldefrist" path="Spende.Rueckmeldefrist">R&uuml;ckmeldefrist *</form:label>
                        <form:input id="rueckmeldefrist" path="Spende.Rueckmeldefrist" type="date" cssClass="field" />
                        <dl class="info">
                            <dt>&#x69;</dt>
                            <dd>Der Koordinator muss bis zu diesem Termin die &Uuml;bernahme der Spende best&auml;tigt haben.</dd>
                        </dl>
                    </li>
                    <li>
                        <form:label for="beschreibung" path="Spende.Beschreibung">Beschreibung</form:label>
                        <form:textarea id="beschreibung" path="Spende.Beschreibung"></form:textarea>
                        </li>
         
                    </ul>
                </li>

                <!-- Produkt -->
                <li>
                    <hr>
                    <h3>Produkt</h3>
                    <ul>
                        <li>
                        <form:label for="art" path="Spende.Art">Art des Produkts</form:label>
                            <div class="select">
                            <form:select id="art" path="Spende.Art" cssClass="field">
                                <form:option value="Food" label="Food" />
                                <form:option value="nonFood" label="NonFood" />
                                <form:option value="" label="Getränke" />
                            </form:select>
                        </div>
                        <dl class="info">
                            <dt>&#x69;</dt>
                            <dd>Die Art des Produktes, z.B. Food, Getr&auml;nke oder Non Food Artikel.</dd>
                        </dl>
                    </li>
                    <li class="pfand_wrap">
                        <form:label for="pfand" path="Spende.Art">Pfand</form:label>
                            <div class="select">
                            <form:select id="pfand" path="Spende.Art" cssClass="field">
                                <form:option value="Getraenke_ohnePfand" label="ohne Pfand" />
                                <form:option value="Getraenke_mitPfandGespendet" label="mit Pfand (Spende)" />
                                <form:option value="Getraenke_mitPfandZurueck" label="mit Pfand (Rückgabe)" />
                            </form:select>
                        </div>
                        <dl class="info">
                            <dt>&#x69;</dt>
                            <dd>Gibt es bei Getr&auml;nken Pfand? Soll dieser gespendet werden? Oder muss dieser bezahlt werden?</dd>
                        </dl>
                    </li>
                    <li class="mhd_wrap">         
                        <form:label for="mhd" path="Spende.mhd">Mindesthaltbarkeitsdatum</form:label>
                        <form:input for="id" path="Spende.mhd" type="date" cssClass="field" />
                        <dl class="info">
                            <dt>&#x69;</dt>
                            <dd>Das Mindesthaltbarkeitsdatum bei Lebensmitteln.</dd>
                        </dl>
                    </li>
                    <li class="kuehlung_wrap">
                        <form:label for="kuehlung" path="Spende.Kuehlung">K&uuml;hlung</form:label>
                            <div class="select">
                            <form:select id="kuehlung" path="Spende.Kuehlung" cssClass="field">
                                <form:option value="keine" label="Keine" />
                                <form:option value="kühl" label="Kühl" />
                                <form:option value="tiefgekühlt" label="Tiefkühl" />
                            </form:select>
                        </div>
                        <dl class="info">
                            <dt>&#x69;</dt>
                            <dd>Ist die Spende K&uuml;hlware oder Tiefk&uuml;hlware?</dd>
                        </dl>
                    </li>
                    <li class="alk_wrap">
                        <form:label for="alkohol" path="Spende.Alkohol">Enth&auml;lt Alkohol</form:label>
                            <div class="select">
                            <form:select id="alkohol" path="Spende.Alkohol" cssClass="field">
                                <form:option value="0" label="Nein" />
                                <form:option value="1" label="Ja" />
                            </form:select>
                        </div>
                        <dl class="info">
                            <dt>&#x69;</dt>
                            <dd>Ist in der Spende Alkohol enthalten?</dd>
                        </dl>
                    </li>
                </ul>
            </li>
            <!-- Menge -->
            <li>
                <hr>
                <h3>Gewicht/Menge</h3>
                <ul>
                    <!-- Volumen nur bei Getränke und dann ganz oben, sonst ausgeblendet-->
                    <li class="volumen_wrap">
                        <form:label for="volumen" path="Spende.Volumen">Volumen *</form:label>
                        <form:input id="volumen" type="number" path="Spende.Volumen" cssClass="field" />
                        <dl class="info">
                            <dt>&#x69;</dt>
                            <dd>Einheit: Liter <br/>Dieses Feld muss ausgefüllt werden. 0 ist möglich.</dd>
                        </dl>
                    </li>
                    <li>
                        <form:label for="anzahl" path="Spende.Anzahl">Anzahl *</form:label>
                        <form:input if="anzahl"  type="number" value=""  path="Spende.Anzahl"  cssClass="field" pattern="[0-9]+" title="Nur natürliche Zahlen, keine Brüche oder Dezimalstellen."/>
                        <dl class="info">
                            <dt>&#x69;</dt>
                            <dd>Einheit: St&uuml;ck <br/>Dieses Feld muss ausgefüllt werden. 0 ist möglich, jedoch sollte eine Mengenangabe ausgefüllt werden!</dd>
                        </dl>
                    </li>
                    <li>
                        <form:label for="gewicht" path="Spende.Gewicht">Gewicht *</form:label>
                        <form:input id="gewicht" type="number" path="Spende.Gewicht" cssClass="field" step="0.1" />
                        <dl class="info">
                            <dt>&#x69;</dt>
                            <dd>Einheit: KG <br/>Dies ist das Gesamtgewicht <br/>Dieses Feld muss ausgefüllt werden. 0 ist möglich.</dd>
                        </dl>
                    </li>
                    <li>
                        <form:label for="paletten" path="Spende.Paletten">Anzahl der Paletten *</form:label>
                        <form:input id="paletten" type="number" path="Spende.Paletten" cssClass="field" pattern="[0-9]+" title="Nur natürliche Zahlen, keine Brüche oder Dezimalstellen." />
                        <dl class="info">
                            <dt>&#x69;</dt>
                            <dd>Einheit: Paletten <br/>Anzahl der Paletten <br/>Dieses Feld muss ausgefüllt werden. 0 ist möglich.</dd>
                        </dl>
                    </li>
                    <li>
                        <form:label for="tauschpalette" path="Spende.Tauschpalette">Tauschpaletten erw&uuml;nscht</form:label>
                            <div class="select">
                            <form:select id="tauschpalette" path="Spende.Tauschpalette" cssClass="field">
                                <form:option value="keine" label="keine" />
                                <form:option value="EURO" label="EURO" />
                                <form:option value="CHEP" label="CHEP" />
                            </form:select>
                        </div>
                        <dl class="info">
                            <dt>&#x69;</dt>
                            <dd>Wird eine Tauschpalette ben&ouml;tigt? (EURO/CHEP)</dd>
                        </dl>
                    </li>


                </ul>
            </li>
            <!-- Standort -->
            <li>
                <hr>
                <h3>Standort / Lieferung</h3>
                <ul>
                    <li>
                        <form:label for="lieferung" path="Spende.Lieferung">Spende wird ausgeliefert</form:label>
                            <div class="select">
                            <form:select id="lieferung" path="Spende.Lieferung" cssClass="field">
                                <form:option value="0" label="Nein" />
                                <form:option value="1" label="Ja" />  
                            </form:select>
                        </div>
                        <dl class="info">
                            <dt>&#x69;</dt>
                            <dd>Soll die Ware durch die Tafeln abgeholt werden oder wird sie geliefert?</dd>
                        </dl>
                    </li>
                    <li class="abholfrist_wrap">
                        <form:label for="abholfrist" path="Spende.Abholfrist">Abzuholen bis *</form:label>
                        <form:input id="abholfrist" path="Spende.Abholfrist" type="date" class="field" />
                        <dl class="info">
                            <dt>&#x69;</dt>
                            <dd>Die Ware ist bis zu diesem Datum abzuholen.</dd>
                        </dl>
                    </li>
                    <li class="abholfrist_wrap">
                        <form:label path="CheckAdresse">Angaben weichen ab</form:label>
                            <div class="select">
                            <form:select id="checkAdresse" path="CheckAdresse" cssClass="field">
                                <form:option value="0" label="Nein" />
                                <form:option value="1" label="Ja" />
                            </form:select>
                        </div>
                        <dl class="info">
                            <dt>&#x69;</dt>
                            <dd>Die Anschrift wird aus dem Spenderkonto importiert.</dd>
                        </dl>
                    </li>
                    <li class="adresse_wrap abholfrist_wrap">
                        <form:label path="Adresse.Strasse">Stra&szlig;e * / Nr. *</form:label>
                        <form:input path="Adresse.Strasse" cssClass="field" value="${Strasse}" />
                    </li>
                    <li class="adresse_wrap abholfrist_wrap">
                        <form:label path="Adresse.plz">PLZ * / Ort *</form:label>
                        <form:input path="Adresse.plz" class="field small1" value="${PLZ}" title="Postleitzahl bestehend aus 5 Zahlen! BSP: 49808"/>
                        <form:input path="Adresse.Ort" class="field small3" value="${Ort}"/>
                    </li>
                    <li class="abholfrist_wrap">   
                        <form:label path="Adresse.Beschreibung">Beschreibung</form:label>
                        <form:input path="Adresse.Beschreibung" class="field" value="${Beschreibung}"/>
                    </li>
                    <li class="abholfrist_wrap">
                        <form:label path="Spende.Sicherheitsbestimmungen">Sicherheitsbestimmungen</form:label>
                        <form:input path="Spende.Sicherheitsbestimmungen" cssClass="field" value="${Sicherheitsbestimmungen}" />
                        <dl class="info">
                            <dt>&#x69;</dt>
                            <dd>Die Sicherheitsbestimmungen werden aus dem Spenderkonto importiert.</dd>
                        </dl>
                    </li>
                    <li class="abholfrist_wrap">
                        <form:label path="Spende.Abholzeiten">Abholzeiten</form:label>
                        <form:textarea path="Spende.Abholzeiten" /><!--${Abholzeiten}-->
                        <dl class="info left">
                            <dt>&#x69;</dt>
                            <dd>Die Abholzeiten werden aus dem Spenderkonto importiert.</dd>
                        </dl>
                    </li>

                </ul>
            </li>

            <!-- Kontakt -->
            <li>
                <hr>
                <h3>Ansprechpartner</h3>
                <ul>
                    <li>
                        <form:label path="CheckKontakt">Angaben weichen ab</form:label>
                            <div class="select">
                            <form:select id="checkKontakt" path="CheckKontakt" cssClass="field">
                                <form:option value="0" label="Nein" />
                                <form:option value="1" label="Ja" />
                            </form:select>
                        </div>
                        <dl class="info">
                            <dt>&#x69;</dt>
                            <dd>Der Kontakt wird aus dem Spenderkonto importiert.</dd>
                        </dl>
                    </li>
                    <li class="kontakt_wrap">
                        <form:label path="Kontakt.Nachname">Name *</form:label>
                        <form:input path="Kontakt.Nachname" cssClass="field" value="${Nachname}" />
                    </li>
                    <li class="kontakt_wrap">
                        <form:label path="Kontakt.Vorname">Vorname *</form:label>
                        <form:input path="Kontakt.Vorname" cssClass="field" value="${Vorname}"/>
                    </li>
                    <li class="kontakt_wrap">
                        <form:label path="Kontakt.EMail">E-Mail *</form:label>
                        <form:input type="email" path="Kontakt.EMail" class="field" value="${EMail}"/>
                    </li>
                    <li class="kontakt_wrap">
                        <form:label path="Kontakt.Telefon">Telefon</form:label>
                        <form:input type="tel" path="Kontakt.Telefon" class="field" value="${Telefon}" />
                    </li>
                    <li class="kontakt_wrap">
                        <form:label path="Kontakt.Fax">Fax</form:label>
                        <form:input type="tel" path="Kontakt.Fax" class="field" value="${Fax}"/>
                    </li>
                </ul>
            </li>

            <!-- Sonstige Angaben -->
            <li>
                <hr>
                <h3>Sonstige Angaben</h3>
                <ul>
                    <li>
                        <form:label path="Spende.Spendenbeleg">Spendenbeleg erw&uuml;nscht</form:label>
                            <div class="select">
                            <form:select path="Spende.Spendenbeleg" cssClass="field">
                                <form:option value="0" label="Nein" />
                                <form:option value="1" label="Ja" />
                            </form:select>
                        </div>
                        <dl class="info">
                            <dt>&#x69;</dt>
                            <dd>Soll ein Spendenbeleg erstellt werden?</dd>
                        </dl>
                    </li>
                    <li>
                        <form:label path="Spende.Bevorzugte_Tafeln">Bevorzugte Tafel</form:label>
                        <form:input path="Spende.Bevorzugte_Tafeln" class="field" />
                        <dl class="info">
                            <dt>&#x69;</dt>
                            <dd>Gibt es bevorzugte Tafeln? Diese k&ouml;nnen dem Koordinator vorgeschlagen werden.</dd>
                        </dl>
                    </li>
                    <li>
                        <form:label path="Spende.Kommentar">Kommentar </form:label>
                        <form:textarea path="Spende.Kommentar"></form:textarea>
                            <dl class="info left">
                                <dt>&#x69;</dt>
                                <dd>M&ouml;chten Sie einen Kommentar hinzuf&uuml;gen? Dieser wird nur f&uuml;r den Koordinator sichtbar sein.</dd>
                            </dl>
                        </li>

                    </ul>
                </li>
                <li>
                    <hr>
                    <a href="<%=request.getContextPath()%>" class="button minor">Abbrechen</a>
                    <input type="reset" value="Zur&uuml;cksetzen" class="button minor" >
                    <input type="submit" value="Spenden &raquo;" class="button" >
                    <div class="clear"></div>
                </li>
            </ul>
            <br>
            <p>Alle mit * gekennzeichneten Felder sind Pflichtfelder.</p>
        </div>
</form:form>
<jsp:include page="../footer.jsp"/>



