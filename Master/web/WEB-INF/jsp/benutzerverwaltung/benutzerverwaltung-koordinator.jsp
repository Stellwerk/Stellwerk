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
    <table>
        <thead>
            <tr>
                <td>E-Mail</td>
                <td>Name</td>
                <td>Status</td>
                <td>Rolle</td>
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
                    <td><a href="mailto:${element.EMail}">${element.EMail}</a></td> <!-- ?subject= -->
                    <td>${element.vorname}&nbsp;${element.nachname}</td>
                    <!-- Hier die auswahl, ob der Benutzer einer Tafel, oder einem Spender zugewiesen ist-->
                    <td>
                        <a href="<%=request.getContextPath()%>/benutzerverwaltung-koordinator/status/${element.benutzerID}/">
                            <c:choose>
                                <c:when test="${not empty element.tafel}"> 

                                    <c:choose>
                                        <c:when test="${element.tafel.status == 'gelb'}">
                                            <dl class="info check invalid">
                                                <dt>&#xe001;</dt>
                                                <dd>
                                                    Tafel: ${element.tafel.tafelname}<br/> 
                                                    Notizen: ${element.tafel.notizen}
                                                </dd>
                                            </dl>
                                        </c:when>
                                        <c:otherwise>
                                            <dl class="info check valid">  
                                                <dt>&#xe002;</dt>
                                                <dd>
                                                    Tafel: ${element.tafel.tafelname}<br/> 
                                                    Notizen: ${element.tafel.notizen}
                                                </dd>
                                            </dl>

                                        </c:otherwise>
                                    </c:choose>
                                </c:when>

                                <c:when test="${not empty element.spender}"> 
                                    <c:choose>
                                        <c:when test="${element.spender.status == 'gelb'}">
                                            <dl class="info check invalid">
                                                <dt>&#xe001;</dt>
                                                <dd>
                                                    Spender: ${element.spender.firmenname}<br/> 
                                                    Notizen: ${element.spender.notizen}</dd>  
                                            </dl>
                                        </c:when>
                                        <c:otherwise>
                                            <dl class="info check valid">  
                                                <dt>&#xe002;</dt>
                                                <dd>
                                                    Spender: ${element.spender.firmenname}<br/> 
                                                    Notizen: ${element.spender.notizen}</dd>
                                            </dl>
                                        </c:otherwise>
                                    </c:choose>  
                                </c:when>
                            </c:choose>
                        </a>        
                    </td>
                    <td>${element.rolle}</td>                
                </tr>
            </c:forEach>

        </tbody>
    </table>
    <hr />
    <div class="buttons">
        <a href="<%=request.getContextPath()%>/benutzerverwaltung-koordinator/email" class="button" title="Benutzer hinzuf&uuml;gen &raquo;">Benutzergruppe kontaktieren &raquo;</a>
        <div class="clear"></div>
    </div>
</div>
<jsp:include page="../footer.jsp"/>