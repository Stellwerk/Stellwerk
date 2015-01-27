$(function() {
	// Datepicker
	if (!Modernizr.inputtypes.date) {
		$.datepicker.regional['de'] = {clearText: 'löschen', clearStatus: 'aktuelles Datum löschen',
			closeText: 'schließen', closeStatus: 'ohne Änderungen schließen',
			prevText: '< zurück', prevStatus: 'letzten Monat zeigen',
			nextText: 'weiter >', nextStatus: 'nächsten Monat zeigen',
			currentText: 'heute', currentStatus: '',
			monthNames: ['Januar', 'Februar', 'März', 'April', 'Mai', 'Juni',
				'Juli', 'August', 'September', 'Oktober', 'November', 'Dezember'],
			monthNamesShort: ['Jan', 'Feb', 'Mär', 'Apr', 'Mai', 'Jun',
				'Jul', 'Aug', 'Sep', 'Okt', 'Nov', 'Dez'],
			monthStatus: 'anderen Monat anzeigen', yearStatus: 'anderes Jahr anzeigen',
			weekHeader: 'Wo', weekStatus: 'Woche des Monats',
			dayNames: ['Sonntag', 'Montag', 'Dienstag', 'Mittwoch', 'Donnerstag', 'Freitag', 'Samstag'],
			dayNamesShort: ['So', 'Mo', 'Di', 'Mi', 'Do', 'Fr', 'Sa'],
			dayNamesMin: ['So', 'Mo', 'Di', 'Mi', 'Do', 'Fr', 'Sa'],
			dayStatus: 'Setze DD als ersten Wochentag', dateStatus: 'Wähle D, M d',
			dateFormat: 'yy-mm-dd', firstDay: 1,
//			dateFormat: 'dd.mm.yy', firstDay: 1,
			initStatus: 'Wähle ein Datum', isRTL: false};
		$.datepicker.setDefaults($.datepicker.regional['de']);
		$('input[type=date]').datepicker();

	}

	// Test ob webkitAppearance unterstützt wird
	Modernizr.addTest('appearance', function() {
		return Modernizr.testProp('webkitAppearance');
	});


	// Show/Hide Skript
	$(".palette").hide();
	// Wenn das Dropdown geändert wird
	$("#Einheit").change(function() {
		$(".einheit_wrap").slideUp().find("input").removeClass("active_einheit");
		switch ($("#Einheit option:selected").attr("value")) {
			case 'STK':
				$("#gewicht_wrap").slideDown().find("input").addClass("active_einheit");
				break;
			case 'KG':
				break;
			case 'PAL':
				$(".palette").slideDown().find("input").addClass("active_einheit");
				$("#gewicht_wrap").slideDown().find("input").addClass("active_einheit");
				break;
		}
	});


	$(".pfand_wrap").hide();
	$(".volumen_wrap").hide();
	// Wenn das Dropdown geändert wird

	$("#art").change(function() {
		$(".pfand_wrap").slideUp().find("input").removeClass("active_art");
		$(".alk_wrap").slideUp().find("input").removeClass("active_art");
		$(".kuehlung_wrap").slideUp().find("input").removeClass("active_art");
		$(".mhd_wrap").slideUp().find("input").removeClass("active_art").prop('disabled', true);
		$(".volumen_wrap").slideUp().find("input").removeClass("active_art");
		switch ($("#art option:selected").attr("value")) {
			case 'Food':
				$(".kuehlung_wrap").slideDown().find("input").addClass("active_art");
				$(".alk_wrap").slideDown().find("input").addClass("active_art");
				$(".mhd_wrap").slideDown().find("input").addClass("active_art").prop('disabled', false);
				break;
			case 'nonFood':
				break;
			case '': // Getränke
				$(".pfand_wrap").slideDown().find("input").addClass("active_art");
				$(".alk_wrap").slideDown().find("input").addClass("active_art");
				$(".kuehlung_wrap").slideDown().find("input").addClass("active_art");
				$(".mhd_wrap").slideDown().find("input").addClass("active_art").prop('disabled', false);
				$(".volumen_wrap").slideDown().find("input").addClass("active_art");
				break;
		}
		;
	});

	$("#lieferung").change(function() {
		$(".abholfrist_wrap").slideUp().find("input").removeClass("active_art").prop('disabled', true);
		switch ($("#lieferung option:selected").attr("value")) {
			case '0':
				$(".abholfrist_wrap").slideDown().find("input").addClass("active_art").prop('disabled', false);
				break;
		}
		;
	});

	// Input Felder deaktivieren
	$(".adresse_wrap").children("input").prop('disabled', true);
	$("#checkAdresse").change(function() {
		$(".adresse_wrap").children("input").prop('disabled', true);
		switch ($("#checkAdresse option:selected").attr("value")) {
			case '1':
				$(".adresse_wrap").children("input").prop('disabled', false);
				break;
		}
		;
	});

	// Input Felder deaktivieren
	$(".kontakt_wrap").children("input").prop('disabled', true);
	$("#checkKontakt").change(function() {
		$(".kontakt_wrap").children("input").prop('disabled', true);
		switch ($("#checkKontakt option:selected").attr("value")) {
			case '1':
				$(".kontakt_wrap").children("input").prop('disabled', false);
				break;
		}
		;
	});


	// Info Button
	$(".info dt").hover(function() {
		$(this).parent(".info").toggleClass("active").children("dd").toggle();
	});


	// Hide Element on Delete
	$('.delete.button').click(function() {
		$(this).parent().parent().hide('20000', function() {
			$(this).remove();
		});
	});



	// Annahme

	$(".dialog").dialog({
		autoOpen: false,
		modal: true
	});

	$(".button.annahme").click(function() {
		$(".dialog.annahme").dialog("open");
	});
	$(".button.ablehnen").click(function() {
		$(".dialog.ablehnen").dialog("open");
	});
	$(".button.cancel").click(function() {
		$(this).parent(".dialog").dialog("close");
	});
	$("#slider").slider({
		range: "max",
		min: 0,
		max: 100,
		value: 0,
		step: 0.10,
		slide: function(event, ui) {
			$("#prozentwert").val(ui.value);
			var mengesum = parseInt($('span.total.menge').text());
			var mengeauswahl = Math.round((ui.value)*mengesum/100);
			$('span.current.menge').text(mengeauswahl);
			var gewichtsum = parseFloat($('span.total.gewicht').text());
			var gewichtauswahl = Math.round(((ui.value/100)*gewichtsum)*100)/100;
			$('span.current.gewicht').text(gewichtauswahl);
			var volumensum = parseFloat($('span.total.volumen').text());
			var volumenauswahl = Math.round(((ui.value/100)*volumensum)*100)/100;
			$('span.current.volumen').text(volumenauswahl);
			var palettensum = parseFloat($('span.total.paletten').text());
			var palettenauswahl = Math.round(((ui.value/100)*palettensum)*100)/100;
			$('span.current.paletten').text(palettenauswahl);
		}
	});
	$("#menge").val($("#slider").slider("value"));


	
	// Formular im Popup absenden
	$('#mailanrollesenden').click(function() {
		$('#mailanrolle').submit();
	});

	// Formular im Popup absenden
	$('#mengenformsenden').click(function() {
		$('#mengenform').submit();
	});
});
$.fn.sumValues = function() {
	this.each(function() {
		if ($(this).is(':input')) {
			var val = $(this).val();
		} else {
			var val = $(this).text();
		}
		sum -= parseFloat(('0' + val).replace(/[^0-9-\.]/g, ''), 10);
	});
	return sum;
};
function changeMarker() {
	var id = $(this).prop("id");
	var tid = id.substring(id.indexOf("-") + 1);
	var checkbox_status = $(this).prop('checked');
	$("#map").gmap3({
		get: {
			tag: [tid],
			all: true,
			callback: function (objs) {
				if (checkbox_status) {
					$.each(objs, function (i, obj) {
						obj.setIcon(activeMarker);
					});
				} else {
					$.each(objs, function (i, obj) {
						obj.setIcon(normalMarker);
					});
				}
			}
		}
	});    
}
function mengenRechner(Menge, Gesamtmenge, Gesamtgewicht, Gesamtvolumen, Gesamtpaletten) {
	var Gewicht		= Math.round((Gesamtgewicht / Gesamtmenge) * Menge * 100) / 100;
	var Volumen		= Math.round((Gesamtvolumen / Gesamtmenge) * Menge * 100) / 100;
	var Paletten	= Math.round((Gesamtpaletten / Gesamtmenge) * Menge * 100) / 100;
	var Werte = new Array(Menge, Gewicht, Volumen, Paletten);
	return Werte;
}
function gewichtRechner(Gewicht, Gesamtmenge, Gesamtgewicht, Gesamtvolumen, Gesamtpaletten) {
	var Menge		= Math.round((Gesamtmenge / Gesamtgewicht) * Gewicht * 100) / 100;
	var Volumen		= Math.round((Gesamtvolumen / Gesamtgewicht) * Gewicht * 100) / 100;
	var Paletten	= Math.round((Gesamtpaletten / Gesamtgewicht) * Gewicht * 100) / 100;
	var Werte = new Array(Menge, Gewicht, Volumen, Paletten);
	return Werte;
}
function volumenRechner(Volumen, Gesamtmenge, Gesamtgewicht, Gesamtvolumen, Gesamtpaletten) {
	var Menge		= Math.round((Gesamtmenge / Gesamtvolumen) * Volumen * 100) / 100;
	var Gewicht		= Math.round((Gesamtgewicht / Gesamtvolumen) * Volumen * 100) / 100;
	var Paletten	= Math.round((Gesamtpaletten / Gesamtvolumen) * Volumen * 100) / 100;
	var Werte = new Array(Menge, Gewicht, Volumen, Paletten);
	return Werte;
}
function palettenRechner(Paletten, Gesamtmenge, Gesamtgewicht, Gesamtvolumen, Gesamtpaletten) {
	var Menge		= Math.round((Gesamtmenge / Gesamtpaletten) * Paletten * 100) / 100;
	var Gewicht		= Math.round((Gesamtgewicht / Gesamtpaletten) * Paletten * 100) / 100;
	var Volumen		= Math.round((Gesamtvolumen / Gesamtpaletten) * Paletten * 100) / 100;
	var Werte = new Array(Menge, Gewicht, Volumen, Paletten);
	return Werte;
}