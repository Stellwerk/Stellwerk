<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"     uri="http://www.springframework.org/security/tags" %>

<jsp:include page="../header.jsp"/>
<script type="text/javascript">
$(document).ready(function() {
	var gesamtmenge = $('span.gesamtmenge').text();
	var gesamtgewicht = $('span.gesamtgewicht').text();
	var gesamtvolumen = $('span.gesamtvolumen').text();
	var gesamtpaletten = $('span.gesamtpaletten').text();
	$('.tafel-item').each(function() {
		var anteil = $(this).find('.anteil').text();
		$(this).find('.angeforderte-menge').text(Math.round(gesamtmenge*(anteil/100)));
		$(this).find('.angefordertes-gewicht').text(Math.round(gesamtgewicht*(anteil/100)));
		$(this).find('.angefordertes-volumen').text(Math.round(gesamtvolumen*(anteil/100)));
		$(this).find('.angeforderte-paletten').text(Math.round(gesamtpaletten*(anteil/100)));
	});
	$('input.number').bind('keyup change', function() {
		var kummuliertemenge = 0;
		var kummuliertesgewicht = 0;
		var kummuliertesvolumen = 0;
		var kummuliertepaletten = 0;
		var wert = parseFloat($(this).val());
		if ($(this).hasClass('menge')) {
			var Werte = mengenRechner(wert, gesamtmenge, gesamtgewicht, gesamtvolumen, gesamtpaletten);
			$(this).closest('.tafel-item').find('input.gewicht').val(Werte[1]);
			$(this).closest('.tafel-item').find('input.volumen').val(Werte[2]);
			$(this).closest('.tafel-item').find('input.paletten').val(Werte[3]);
		} else if ($(this).hasClass('gewicht')) {
			var Werte = gewichtRechner(wert, gesamtmenge, gesamtgewicht, gesamtvolumen, gesamtpaletten);
			$(this).closest('.tafel-item').find('input.menge').val(Werte[0]);
			$(this).closest('.tafel-item').find('input.volumen').val(Werte[2]);
			$(this).closest('.tafel-item').find('input.paletten').val(Werte[3]);
		} else if ($(this).hasClass('volumen')) {
			var Werte = volumenRechner(wert, gesamtmenge, gesamtgewicht, gesamtvolumen, gesamtpaletten);
			$(this).closest('.tafel-item').find('input.menge').val(Werte[0]);
			$(this).closest('.tafel-item').find('input.gewicht').val(Werte[1]);
			$(this).closest('.tafel-item').find('input.paletten').val(Werte[3]);
		} else if ($(this).hasClass('paletten')) {
			var Werte = palettenRechner(wert, gesamtmenge, gesamtgewicht, gesamtvolumen, gesamtpaletten);
			$(this).closest('.tafel-item').find('input.menge').val(Werte[0]);
			$(this).closest('.tafel-item').find('input.gewicht').val(Werte[1]);
			$(this).closest('.tafel-item').find('input.volumen').val(Werte[2]);
		}
		console.log(Werte[3]);
		$('input.menge').each(function() {
			kummuliertemenge = kummuliertemenge + Math.round(parseFloat($(this).val())*100)/100;
			var restmenge = gesamtmenge - kummuliertemenge;
			restmenge = Math.round(restmenge*100)/100;
			$('span.restmenge').text(restmenge);
		});
		$('input.gewicht').each(function() {
			kummuliertesgewicht = kummuliertesgewicht + Math.round(parseFloat($(this).val())*100)/100;
			var restgewicht = gesamtgewicht - kummuliertesgewicht;
			restgewicht = Math.round(restgewicht*100)/100;
			$('span.restgewicht').text(restgewicht);
		});
		$('input.volumen').each(function() {
			kummuliertesvolumen = kummuliertevolumen + Math.round(parseFloat($(this).val())*100)/100;
			var restvolumen = gesamtvolumen - kummuliertesvolumen;
			restvolumen = Math.round(restvolumen*100)/100;
			$('span.restvolumen').text(restvolumen);
		});
		$('input.paletten').each(function() {
			kummuliertepaletten = kummuliertepaletten + Math.round(parseFloat($(this).val())*100)/100;
			var restpaletten = gesamtpaletten - kummuliertepaletten;
			restpaletten = Math.round(restpaletten*100)/100;
			$('span.restpaletten').text(restpaletten);
		});
	});
});
</script>
<div id="content" class="list">
	<h2>Spende #${spende.spendeID}: ${spende.produktbezeichnung}</h2>
        <dl class="bold">
		<dt>Menge</dt>
		<dd><span class="gesamtmenge">${spende.anzahl}</span> St&uuml;ck</dd>
	</dl>
	<dl>
		<dt>Rest</dt>
		<dd><span class="restmenge">${spende.anzahl}</span> St&uuml;ck</dd>
	</dl>
        
        <br/>
        
        <c:if test="${spende.gewicht > 0}">
	<dl class="bold">
		<dt>Gewicht</dt>
		<dd><span class="gesamtgewicht">${spende.gewicht}</span> kg</dd>
	</dl>
	<dl>
		<dt>Rest</dt>
		<dd><span class="restgewicht">${spende.gewicht}</span> kg</dd>
	</dl>
        </c:if>
        
        <c:if test="${spende.paletten > 0}">
	<dl class="bold">
		<dt>Paletten</dt>
		<dd><span class="gesamtpaletten">${spende.paletten}</span> Paletten</dd>
	</dl>
	<dl>
		<dt>Rest</dt>
		<dd><span class="restpaletten">${spende.paletten}</span> Paletten</dd>
	</dl>
        </c:if>
        
        <c:if test="${spende.volumen > 0 }">                        
            <dl class="bold">
                    <dt>Volumen</dt>
                    <dd><span class="gesamtvolumen">${spende.volumen}</span> Liter</dd>
            </dl>
            <dl>
                    <dt>Rest</dt>
                    <dd><span class="restvolumen">${spende.volumen}</span> Liter</dd>
            </dl>
        </c:if>
        <br/>
        
	<form:form action="${linkpost}" method="POST" modelAttribute="spendenvorgangsliste" id="mengenform">
		<c:forEach items="${spendenvorgangsliste.spendenvorgang}" var="element" varStatus="row">
			<div class="tafel-item">
                            <form:input path="spendenvorgang[${row.index}].tafel.id" type ="hidden"/>
                            <form:input path="spendenvorgang[${row.index}].spendenvorgangsID" type ="hidden"/>
                            <form:input path="spendenvorgang[${row.index}].tafel.kontakt.eMail" type ="hidden"/>
                            <form:input path="spendenvorgang[${row.index}].tafel.tafelname" type ="hidden"/>
				<h3>${element.tafel.tafelname} (<span class="anteil">${element.anteil}</span> %)</h3>
				<dl>
					<dt>Angeforderte Menge</dt>
					<dd><span class="angeforderte-menge"></span> St&uuml;ck</dd>

					<dt>Zugeteilte Menge</dt>
					<dd><form:input path="spendenvorgang[${row.index}].spende.anzahl" class="field small1 number menge" type="number" title="" value="0" step="0.01" /> St&uuml;ck</dd>
				</dl>
				<div class="clear"></div>
            <c:if test="${spende.gewicht > 0}">
				<dl>
					<dt>Angefordertes Gewicht</dt>
					<dd><span class="angefordertes-gewicht"></span> Kilo</dd>

					<dt>Zugeteiltes Gewicht</dt>
					<dd><form:input path="spendenvorgang[${row.index}].spende.gewicht" class="field small1 number gewicht" type="number" title="" value="0" step="0.01" /> Kilo</dd>
				</dl>
				<div class="clear"></div>
            </c:if>
            <c:if test="${spende.paletten > 0}">
				<dl>
					<dt>Angeforderte Paletten</dt>
					<dd><span class="angeforderte-paletten"></span> Paletten</dd>

					<dt>Zugeteilte Paletten</dt>
					<dd><form:input path="spendenvorgang[${row.index}].spende.paletten" class="field small1 number paletten" type="number" title="" value="0" step="0.01" /> Paletten</dd>
				</dl>
				<div class="clear"></div>
            </c:if>
            <c:if test="${spende.volumen > 0}">
				<dl>
					<dt>Angefordertes Volumen</dt>
					<dd><span class="angefordertes-volumen"></span> Liter</dd>

					<dt>Zugeteiltes Volumen</dt>
					<dd><form:input path="spendenvorgang[${row.index}].spende.volumen" class="field small1 number volumen" type="number" title="" value="0" step="0.01" /> Liter</dd>
				</dl>
				<div class="clear"></div>
            </c:if>
			</div>
		</c:forEach>

<%--<h3>Lingener Tafel e.V.</h3>
	<dl>
		<dt>Angeforderte Menge</dt>
		<dd>99 Kilo</dd>

		<dt>Zugeteilte Menge</dt>
								<dd><input class="field small1 number gewicht" type="number" title=""/> Kilo</dd>
	</dl>
	<div class="clear"></div>
	<h3>Hann&ouml;versche Tafel e.V.</h3>
	<dl>
		<dt>Angeforderte Menge</dt>
		<dd>99 Kilo</dd>

		<dt>Zugeteilte Menge</dt>
		<dd><input class="field small1 number gewicht" type="number" title=""/> Kilo</dd>
	</dl>
	<div class="clear"></div>--%>
		<div class="buttons">
                        <button type="button" class="annahme button" title="Verteilen">Verteilen</button>
			<a href="<%=request.getContextPath()%>" class="button minor" title="Zur&uuml;ck">Zur&uuml;ck</a>
			<div class="clear"></div>
		</div>
                        
                <%-- Bestätigung --%>
                <div class="annahme dialog">
                    <h4>Spende verteilen</h4>
                    <p>Dieser Schritt kann nicht r&uuml;ckg&auml;ngig gemacht werden! <br>Alle Beteiligten werden hierauf Benachrichtigt.</p>
                    <input type="button" class="button minor cancel" value="Abbrechen" />
                    <input class="button" type="submit" value="Verteilen &raquo;" id="mengenformsenden">
                </div> 
	</form:form>
</div>
<jsp:include page="../footer.jsp"/>
