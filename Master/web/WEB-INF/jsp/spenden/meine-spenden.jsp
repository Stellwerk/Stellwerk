<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec"     uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../header.jsp"/>
<c:if test="${not empty Ausgabe}">
	${Ausgabe}
</c:if>
<div id="content" class="list">
    <h2>Meine Spenden</h2>
    <ul>
        <c:forEach items="${liste}" var="element">
            <li>
                <h3>${element.produktbezeichnung}</h3>
                <ul class="table">
                    <li>  
                        <h4>Beschreibung</h4>
                        <p>${element.beschreibung}</p>
                    </li>
                    <li>
                        <h4>Daten</h4>
                        <dl>
                            <dt>Erstellt am</dt>
                            <dd><fmt:formatDate value="${element.erstellungsdatum}" pattern="dd.MM.yyyy" /></dd>
                            
                            <dt>MHD</dt>
                            <dd><fmt:formatDate value="${element.mhd}" pattern="dd.MM.yyyy" /></dd>
                            
                            <dt>R&uuml;ckmeldefrist</dt>
                            <dd><fmt:formatDate value="${element.rueckmeldefrist}" pattern="dd.MM.yyyy" /></dd>

                            <dt>Menge</dt>
                            <dd>${element.anzahl} St&uuml;ck</dd>

                            <dt>Gesamtgewicht</dt>
                            <dd>${element.gewicht} kg</dd>

                            <dt>Tiefk&uuml;hlware</dt>
                            <dd>${element.kuehlung}</dd>                         
                        </dl>                     
                    </li>
                </ul>
                <div class="buttons">
                    <a href="<%=request.getContextPath()%>/meine-spenden/spende-${element.spendeID}" class="button" title="Weitere Details &raquo;">Weitere Details &raquo;</a>
                    <a href="<%=request.getContextPath()%>/meine-spenden/${element.spendeID}/reklamieren" class="button minor" title="Reklamieren">Reklamieren</a>
                    <div class="clear"></div>
                </div>
            </li>
        </c:forEach>
    </ul>
</div>
<jsp:include page="../footer.jsp"/>