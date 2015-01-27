<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec"     uri="http://www.springframework.org/security/tags" %>

<jsp:include page="../header.jsp"/>
<!-- Ausgabe -->
<c:if test="${not empty Ausgabe}">
    ${Ausgabe}
</c:if>
<div id="content" class="table">
    
    <h2>Spendenvorg&auml;nge</h2>   
    <table>
        <thead>
            <tr>
                <td class="small">ID</td>
                <td>Spende</td>
                <td>Tafel</td>
                <td>Zugegriffen</td>
                <td>Annahme(in%)</td>
            </tr>
        </thead>
        <tbody>
            <!-- Schleife -->
            <c:forEach items="${spendenvorgangsliste}" var="element" varStatus="line" >
                
                <!-- ###### Farbauswahl der einzelnen Zeile ##### -->
                <c:choose>
                    <c:when test='${(element.spende.spendeID)%2 eq 0}'>
                        <c:set var="rowColor" value="even" scope="page"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="rowColor" value="odd" scope="page"/>
                    </c:otherwise>
                </c:choose>
                
                <!-- ###### Darstellung der Zeilen ##### -->
                <tr class="${rowColor}">
                    <td>${element.spende.spendeID}</td>
                    <td><a href="<%=request.getContextPath()%>/spenden/spendenvorgang-${element.spende.spendeID}/details">${element.spende.produktbezeichnung}</a></td>
                    <td><a href="mailto:${element.tafel.kontakt.eMail}" class="black">${element.tafel.tafelname}</a></td>
                    
                    <!-- ###### Feld Status ##### -->
                    
                    <td>
                        <c:choose>
                            <c:when test="${element.status == 'ignoriert'}">--</c:when>
                            <c:when test="${element.status == 'zugegriffen'}">X</c:when>
                            <c:otherwise>Fehler</c:otherwise>
                        </c:choose>
                    </td>
                    <td> 
                        <c:if test="${element.status == 'zugegriffen'}">
                            <c:choose>
                                <c:when test="${element.anteil != null and element.anteil != 0.0}">${element.anteil}</c:when>
                                <c:otherwise>Abgelehnt</c:otherwise>
                            </c:choose>
                        </c:if>
                    </td>
                    
                    
                </tr>
            </c:forEach>      
        </tbody>
    </table>
</div>
<jsp:include page="../footer.jsp"/>