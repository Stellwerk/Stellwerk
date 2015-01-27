<%@ page contentType="text/html" pageEncoding="UTF-8" language="java" buffer="64kb"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"     uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>

<jsp:include page="../header.jsp"/>

<c:if test="${not empty Ausgabe}" >
    ${Ausgabe}
</c:if>

<form:form method="POST" modelAttribute="benutzer">
    <form:errors path="*" cssClass="fehler" element="div" />
    <div id="content">
        <h2>Benutzerdetails</h2>

        <%-- ############################Allgemein############################## --%>

        <ul>
            <li>
                <h3>Passwort &auml;ndern</h3>
            </li>
            <li>
                <form:label path="eMail">E-Mail</form:label>
                <form:input path="eMail" cssClass="field" disabled="true" />
            </li>
            <li>
                <form:label path="passwort">Neues Passwort</form:label>
                <form:input path="passwort" type="password" cssClass="field" />
            </li>

        </ul>        
        <hr>
        <div class="buttons">
            <a href="<%=request.getContextPath()%>/benutzerverwaltung" class="button minor">Abbrechen</a>
            <input type="submit" value="Weiter &raquo;" class="button">         
            <div class="clear"></div>   
        </div>

    </div>
</form:form>

<jsp:include page="../footer.jsp"/>