<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec"     uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../header.jsp"/>
<c:if test="${not empty Ausgabe}">
	${Ausgabe}
</c:if>
<div id="content" class="list">
    <h2>Offene Spenden</h2>
    <ul>
        <c:forEach items="${liste}" var="element" >
            <li id="spende-${element.spende.spendeID}">
                <h3>${element.spende.produktbezeichnung}</h3>
                <ul class="table">
                    <li>  
                        <h4>Beschreibung</h4>
                        <p>${element.spende.beschreibung}</p>
                    </li>
                    <li>
                        <h4>Daten</h4>
                        <dl>
                            
                            <dt>Erstellungsdatum</dt>
                            <dd><fmt:formatDate value="${element.spende.erstellungsdatum}" pattern="dd.MM.yyyy" /></dd>
                            
                            <dt>Mindesthaltbarkeitsdatum</dt>
                            <dd><fmt:formatDate value="${element.spende.mhd}" pattern="dd.MM.yyyy" /></dd>
                            
                            <dt>Menge</dt>
                            <dd>${element.spende.anzahl}</dd>

                            <dt>Gesamtgewicht</dt>
                            <dd>${element.spende.gewicht} kg</dd>

                            <dt>Tiefk&uuml;hlware</dt>
                            <dd>${element.spende.kuehlung}</dd>

                            
                        </dl> 
                        <h4>Spender</h4>
                        <dl>
                            <dt>Unternehmen</dt>
                            <dd><c:if test="${not empty element.spende.benutzer.tafel}">${element.spende.benutzer.tafel.tafelname}</c:if>
                                <c:if test="${not empty element.spende.benutzer.spender}">${element.spende.benutzer.spender.firmenname}</c:if></dd>

                            <dt>Ort</dt>
                            <dd>${element.spende.adresse.plz} ${element.spende.adresse.ort}</dd>
                        </dl>                      
                    </li>
                </ul>
                <div class="buttons">
                    <a href="<%=request.getContextPath()%>/offene-spenden/spende-${element.spendenvorgangsID}" class="button" title="Weitere Details &raquo;">Weitere Details &raquo;</a>
                    <div class="clear"></div>
                </div>
            </li>
        </c:forEach>
    </ul>
</div>
<jsp:include page="../footer.jsp"/>