<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec"     uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../header.jsp"/>
<c:if test="${not empty Ausgabe}">
    ${Ausgabe}
</c:if>
<c:if test="${not empty rAusgabe}">
    ${rAusgabe}
</c:if>
<div id="content" class="list">
    <h2>Eingegangene Spenden</h2>
    <ul>
         <c:forEach items="${spendenliste}" var="element" >
            <li id="spende-${element.spendeID}">
                <h3>${element.produktbezeichnung}</h3>
                <ul class="table">
                    <li>  
                        <h4>Beschreibung</h4>
                        <p>${element.beschreibung}</p>
                        <h4>Kommentar</h4>
                        <p>${element.kommentar}</p>
                    </li>
                    
                    <li>
                        <h4>Daten</h4>
                        <dl>
                            <dt>R&uuml;ckmeldefrist</dt>
                            <dd><fmt:formatDate value="${element.rueckmeldefrist}" pattern="dd.MM.yyyy" /></dd>
                            
                            <dt>Mindesthaltbarkeitsdatum</dt>
                            <dd><fmt:formatDate value="${element.mhd}" pattern="dd.MM.yyyy" /></dd>
                            
                            <dt>Menge</dt>
                            <dd>${element.anzahl}</dd>

                            <dt>Gesamtgewicht</dt>
                            <dd>${element.gewicht} kg</dd>

                            <dt>Tiefk&uuml;hlware</dt>
                            <dd>${element.kuehlung}</dd>
                        </dl> 
                        <h4>Spender</h4>
                        <dl>
                            <dt>Unternehmen</dt>
                            <dd><c:if test="${not empty element.benutzer.tafel}">${element.benutzer.tafel.tafelname}</c:if>
                            <c:if test="${not empty element.benutzer.spender}">${element.benutzer.spender.firmenname}</c:if></dd>
                            
                            <dt>Ort</dt>
                            <dd>${element.adresse.plz} ${element.adresse.ort}, ${element.adresse.strasse}</dd>

                            <dd></dd>
                        </dl>                      
                    </li>
                </ul>
                <div class="buttons">
                    <a href="<%=request.getContextPath()%>/eingegangene-spenden/spende-${element.spendeID}" class="button" title="Weitere Details &raquo;">Weitere Details &raquo;</a>
                    <div class="clear"></div>
                </div>
            </li>
        </c:forEach>
    </ul>
</div>
<jsp:include page="../footer.jsp"/>