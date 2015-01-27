	

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec"     uri="http://www.springframework.org/security/tags" %>

<jsp:include page="../header.jsp"/>
<!-- Ausgabe -->
<c:if test="${not empty Ausgabe}">
    ${Ausgabe}
</c:if>
${nachricht}
<div id="content" class="table">

    <h2>Spenderverwaltung</h2>     
     <p>Derzeit sind insgesamt <b>${spenderanzahl}</b> Spender registriert.</p>
    <table>
        <thead>
            <tr>
                <td>ID</td>
                <td>Firma</td>
                <td>PLZ</td>
                <td>Ort</td>
                <td>Telefon</td>
                <td>Status</td>
                <td>&nbsp</td>



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
                    <td>${element.id}</td>
                    <td><a href="<%=request.getContextPath()%>/spenderverwaltung/${element.id}/spender-details">${element.firmenname}</a></td>
                    <td>${element.adresse.plz}</td>
                    <td>${element.adresse.ort}</td>
                    <td>${element.kontakt.telefon}</td>
                    <td>
                        <c:choose>
                            <c:when test="${element.status == 'gelb'}">
                                <dl class="info check invalid">
                                    <dt>&#xe001;</dt>
                                    <c:if test="${not empty element.notizen}"><dd>${element.notizen}</dd></c:if>
                                    </dl>
                            </c:when>
                            <c:otherwise>
                                <dl class="info check valid">  
                                    <dt>&#xe002;</dt>
                                    <c:if test="${not empty element.notizen}"><dd>${element.notizen}</dd></c:if>
                                </c:otherwise>
                            </c:choose>
                    </td>
                    <td><a href="<%=request.getContextPath()%>/spenderverwaltung/${element.id}/spender-details">Details &raquo;</a></td>
                </tr>
            </c:forEach>

        </tbody>
    </table>
    <hr />
    <div class="buttons">
        <a href="<%=request.getContextPath()%>/spenderverwaltung/spender/hinzufuegen" class="button" title="Spender hinzuf&uuml;gen &raquo;">Spender hinzuf&uuml;gen &raquo;</a>
        <div class="clear"></div>
    </div>
</div>
<jsp:include page="../footer.jsp"/>

