<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec"     uri="http://www.springframework.org/security/tags" %>

<jsp:include page="../header.jsp"/>
<!-- Ausgabe -->
<c:if test="${not empty Ausgabe}">
    ${Ausgabe}
</c:if>
<div id="content" class="table">
    
    <h2>Benutzerverwaltung</h2>   
     <p>Derzeit sind insgesamt <b>${benutzeranzahl}</b> Benutzer registriert.</p>
    <table>
        <thead>
            <tr>
                <td class="small">Nr.</td>
                <td>E-Mail</td>
                <td>Name</td>
                <td>Rolle</td>
                <td>&numsp;</td>
            </tr>
        </thead>
        <tbody>
            <!-- Schleife -->
            <c:forEach items="${liste}" var="element" varStatus="line" >
                <!-- ###### Farbauswahl der einzelnen Zeile ##### -->
                <c:choose>
                    <c:when test='${(line.index)%2 eq 0}'>
                        <c:set var="rowColor" value="even" scope="page"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="rowColor" value="odd" scope="page"/>
                    </c:otherwise>
                </c:choose>
                
                <tr class="${rowColor}">
                    <td>${line.index +1}</td>
                    <td><a href="<%=request.getContextPath()%>/benutzerverwaltung/${element.benutzerID}/details">${element.EMail}</a></td>
                    <td>${element.vorname}&nbsp;${element.nachname}</td>
                    <td>${element.rolle}</td>
                    <td><a href="<%=request.getContextPath()%>/benutzerverwaltung/${element.benutzerID}/details">Details &raquo;</a></td>
                </tr>
            </c:forEach>
                
        </tbody>
    </table>
    <hr />
    <div class="buttons">
        <a href="<%=request.getContextPath()%>/benutzerverwaltung/benutzer/hinzufuegen" class="button" title="Benutzer hinzuf&uuml;gen &raquo;">Benutzer hinzuf&uuml;gen &raquo;</a>
        <div class="clear"></div>
    </div>
</div>
<jsp:include page="../footer.jsp"/>