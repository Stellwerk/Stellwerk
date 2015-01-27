<%@page contentType="text/html" pageEncoding="UTF-8" buffer="64kb"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"     uri="http://www.springframework.org/security/tags" %>

<jsp:include page="../header.jsp"/>
<script type="text/javascript" src="//maps.googleapis.com/maps/api/js?sensor=false&amp;language=de"></script>     
<script type="text/javascript" src="<%=request.getContextPath()%>/js/gmap3.min.js"></script>      
<script type="text/javascript">
    $(function() {
        $('#map').gmap3({
            marker: {
                address: "${spende.adresse.strasse}, ${spende.adresse.plz} ${spende.adresse.ort}"
            },
            map: {
                options: {
                    zoom: 12
                }
            }
        });
    });
</script>
<div id="content">
	<h2>Spende bearbeiten</h2>
        <form:form method="POST"  modelAttribute="spende">
		<ul>
			<!-- Produktdaten -->
			<li>
				<h3>Allgemeine Angaben</h3>
				<ul>   
					<li>
						<form:label for="produktbezeichnung" path="produktbezeichnung">Produktbezeichnung</form:label>
						<form:input id="produktbezeichnung" path="produktbezeichnung" cssClass="field" />
                                            <dl class="info">
                                                    <dt>&#x69;</dt>
                                                    <dd>Die Produktbezeichnung ist der Produktname.</dd>
                                            </dl>
                                                <form:errors path="produktbezeichnung" />
					</li>
					<li>         
						<form:label for="rueckmeldefrist" path="rueckmeldefrist">R&uuml;ckmeldefrist</form:label>
						<form:input id="rueckmeldefrist" path="rueckmeldefrist" type="date" cssClass="field" />
						<dl class="info">
							<dt>&#x69;</dt>
							<dd>Der Koordinator muss bis zu diesem Termin die &Uuml;bernahme der Spende best&auml;tigt haben.</dd>
						</dl>
                                                <form:errors path="rueckmeldefrist" />
					</li>
					<li>
						<form:label for="beschreibung" path="beschreibung">Beschreibung</form:label>
						<form:textarea id="beschreibung" path="beschreibung"></form:textarea>
                                                <form:errors path="beschreibung" />
					</li>
					</ul>
				</li>

				<!-- Produkt -->
				<li>
					<hr>
					<h3>Produkt</h3>
					<ul>
						<li>
						<form:label for="art" path="art">Art des Produkts</form:label>
						<div class="select">
							<form:select id="art" path="art" cssClass="field">
								<form:option value="Food" label="Food" />
								<form:option value="nonFood" label="NonFood" />
								<form:option value="Getraenke" label="Getränke" />
							</form:select>
						</div>
						<dl class="info">
							<dt>&#x69;</dt>
							<dd>Die Art des Produktes, z.B. Food, Getr&auml;nke oder Non Food Artikel.</dd>
						</dl>
                                                <form:errors path="art" />
					</li>
					<li class="pfand_wrap">
						<form:label for="pfand" path="art">Pfand</form:label>
						<div class="select">
							<form:select id="pfand" path="art" cssClass="field">
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
						<form:label for="mhd" path="mhd">Mindesthaltbarkeitsdatum</form:label>
						<form:input for="id" path="mhd" type="date" cssClass="field" />
						<dl class="info">
							<dt>&#x69;</dt>
							<dd>Das Mindesthaltbarkeitsdatum bei Lebensmitteln.</dd>
						</dl>
                                                <form:errors path="mhd" />
					</li>
					<li class="kuehlung_wrap">
						<form:label for="kuehlung" path="kuehlung">K&uuml;hlung</form:label>
						<div class="select">
							<form:select id="kuehlung" path="kuehlung" cssClass="field">
								<form:option value="keine" label="Keine" />
								<form:option value="kühl" label="Kühl" />
								<form:option value="tiefgekühlt" label="Tiefkühl" />
							</form:select>
						</div>
						<dl class="info">
							<dt>&#x69;</dt>
							<dd>Ist die spende K&uuml;hlware oder Tiefk&uuml;hlware?</dd>
						</dl>
                                                <form:errors path="kuehlung" />
					</li>
					<li class="alk_wrap">
						<form:label for="alkohol" path="alkohol">Enth&auml;lt Alkohol</form:label>
						<div class="select">
							<form:select id="alkohol" path="alkohol" cssClass="field">
								<form:option value="0" label="Nein" />
								<form:option value="1" label="Ja" />
							</form:select>
						</div>
						<dl class="info">
							<dt>&#x69;</dt>
							<dd>Ist in der Spende Alkohol enthalten?</dd>
						</dl>
                                                <form:errors path="alkohol" />
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
						<form:label for="volumen" path="volumen">Volumen</form:label>
						<form:input id="volumen" type="number" path="volumen" cssClass="field" />
                                                <form:errors path="volumen" />
					</li>
					<li>
						<form:label for="anzahl" path="anzahl">Anzahl</form:label>
						<form:input if="anzahl" type="number" path="anzahl" cssClass="field" pattern="[0-9]+" title="Nur natürliche Zahlen, keine Brüche oder Dezimalstellen."/>
                                                <form:errors path="anzahl" />
					</li>
					<li>
						<form:label for="gewicht" path="gewicht">Gewicht in Kilogramm</form:label>
						<form:input id="gewicht" type="number" path="gewicht" cssClass="field" step="0.1" />
                                                <form:errors path="gewicht" />
					</li>
					<li>
						<form:label for="paletten" path="paletten">Anzahl der Paletten</form:label>
						<form:input id="paletten" type="number" path="paletten" cssClass="field" pattern="[0-9]+" title="Nur natürliche Zahlen, keine Brüche oder Dezimalstellen." /> 
                                                <form:errors path="paletten" />
					</li>
					<li>
						<form:label for="tauschpalette" path="tauschpalette">Tauschpaletten erw&uuml;nscht</form:label>
						<div class="select">
							<form:select id="tauschpalette" path="tauschpalette" cssClass="field">
								<form:option value="keine" label="keine" />
								<form:option value="EURO" label="EURO" />
								<form:option value="CHEP" label="CHEP" />
							</form:select>
						</div>
						<dl class="info">
							<dt>&#x69;</dt>
							<dd>Wird eine Tauschpalette ben&ouml;tigt? (EURO/CHEP)</dd>
						</dl>
                                                <form:errors path="tauschpalette" />
					</li>


				</ul>
			</li>
			<!-- Standort -->
			<li>
				<hr>
				<h3>Standort / Lieferung</h3>
				<ul>
					<li>
						<form:label for="lieferung" path="lieferung">Spende wird ausgeliefert</form:label>
						<div class="select">
							<form:select id="lieferung" path="lieferung" cssClass="field">
								<form:option value="0" label="Nein" />
								<form:option value="1" label="Ja" /> 
							</form:select>
						</div>
						<dl class="info">
							<dt>&#x69;</dt>
							<dd>Geschieht die Lieferung der Ware durch Selbstabholung oder durch eine kostenfreie Anlieferung?</dd>
						</dl>
                                                <form:errors path="lieferung" />
					</li>
					<li class="abholfrist_wrap">
						<form:label for="abholfrist" path="abholfrist">Abzuholen bis</form:label>
						<form:input id="abholfrist" path="abholfrist" type="date" class="field" />
						<dl class="info">
							<dt>&#x69;</dt>
							<dd>Die Ware ist bis zu diesem Datum abzuholen.</dd>
						</dl>
                                                <form:errors path="abholfrist" />
					</li>
					<li class="abholfrist_wrap">
						<form:label path="adresse.strasse">Strasse</form:label>
						<form:input path="adresse.strasse" cssClass="field" />
                                                <form:errors path="adresse.strasse" />
					</li>
					<li class="abholfrist_wrap">
						<form:label path="adresse.plz">PLZ / Ort</form:label>
						<form:input path="adresse.plz" class="field small1" pattern="[0-9]{5}" title="Postleitzahl bestehend aus 5 Zahlen! BSP: 49808"/>
						<form:input path="adresse.Ort" class="field small3" />
                                                <form:errors path="adresse.Ort" />
					</li>
					<li class="abholfrist_wrap">   
						<form:label path="adresse.Beschreibung">Beschreibung</form:label>
						<form:input path="adresse.Beschreibung" class="field"/>
                                                <form:errors path="adresse.Beschreibung" />
					</li>
					<li class="abholfrist_wrap">
						<form:label path="sicherheitsbestimmungen">Sicherheitsbestimmungen</form:label>
						<form:input path="sicherheitsbestimmungen" cssClass="field" />
						<dl class="info">
							<dt>&#x69;</dt>
							<dd>Die Sicherheitsbestimmungen werden aus dem Spenderkonto importiert.</dd>
						</dl>
                                                <form:errors path="sicherheitsbestimmungen" />
					</li>
					<li class="abholfrist_wrap">
						<form:label path="abholzeiten">Abholzeiten</form:label>
						<form:textarea path="abholzeiten" /><!--${Abholzeiten}-->
						<dl class="info left">
							<dt>&#x69;</dt>
							<dd>Die Abholzeiten werden aus dem Spenderkonto importiert.</dd>
						</dl>
                                                <form:errors path="abholzeiten" />
					</li>
                                        <c:if test="${!lieferung}">
                                           <li class="abholfrist_wrap">
                                                <h4>Karte</h4>
                                                <div id="map"></div>                     
                                            </li> 
                                        </c:if>
				</ul>
			</li>

			<!-- kontakt -->
			<li>
				<hr>
				<h3>Ansprechpartner</h3>
				<ul>
			
					<li >
						<form:label path="kontakt.nachname">Name</form:label>
						<form:input path="kontakt.nachname" cssClass="field" />
                                                <form:errors path="kontakt.nachname" />
					</li>
					<li >
						<form:label path="kontakt.vorname">Vorname</form:label>
						<form:input path="kontakt.vorname" cssClass="field" />
                                                <form:errors path="kontakt.vorname" />
					</li>
					<li >
						<form:label path="kontakt.telefon">Telefon</form:label>
						<form:input type="tel" path="kontakt.telefon" class="field" />
                                                <form:errors path="kontakt.telefon" />
					</li>
					<li >
						<form:label path="kontakt.fax">Fax</form:label>
						<form:input type="tel" path="kontakt.fax" class="field" />
                                                <form:errors path="kontakt.fax" />
					</li>
					<li>
						<form:label path="kontakt.EMail">E-Mail</form:label>
						<form:input type="email" path="kontakt.EMail" class="field" />
                                                <form:errors path="kontakt.EMail" />
					</li>
				</ul>
			</li>

			<!-- Sonstige Angaben -->
			<li>
				<hr>
				<h3>Sonstige Angaben</h3>
				<ul>
					<li>
						<form:label path="spendenbeleg">Spendenbeleg erw&uuml;nscht</form:label>
						<div class="select">
							<form:select path="spendenbeleg" cssClass="field">
								<form:option value="0" label="Nein" />
								<form:option value="1" label="Ja" />
							</form:select>
						</div>
						<dl class="info">
							<dt>&#x69;</dt>
							<dd>Soll ein Spendenbeleg erstellt werden?</dd>
						</dl>
                                                <form:errors path="spendenbeleg" />
					</li>
					<li>
						<form:label path="bevorzugte_Tafeln">Bevorzugte Tafel</form:label>
						<form:input path="bevorzugte_Tafeln" class="field" />
						<dl class="info">
							<dt>&#x69;</dt>
							<dd>Gibt es bevorzugte Tafeln? Diese k&ouml;nnen vom Koordinator vorgeschlagen werden.</dd>
						</dl>
                                                <form:errors path="bevorzugte_Tafeln" />
					</li>
					<li>
						<form:label path="kommentar">Kommentar </form:label>
						<form:textarea path="kommentar"></form:textarea>
						<dl class="info left">
							<dt>&#x69;</dt>
							<dd>M&ouml;chten Sie einen Kommentar hinzuf&uuml;gen? Dieser wird nur f&uuml;r den Koordinator sichtbar sein.</dd>
						</dl>
                                                <form:errors path="kommentar" />
					</li>
					</ul>
				</li>
				<li>
					<hr>
					<a href="<%=request.getContextPath()%>/" class="button minor">Abbrechen</a>
                                        <input type="submit" value="&Uuml;bernehmen &raquo;" class="button" >
					<div class="clear"></div>
				</li>
			</ul>
	</form:form>
</div>
<jsp:include page="../footer.jsp"/>



