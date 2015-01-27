<%@ page contentType="text/html" pageEncoding="UTF-8" language="java" buffer="64kb"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"     uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>

<jsp:include page="../header.jsp"/>
<c:if test="${not empty Ausgabe}">
    ${Ausgabe}
</c:if>
<form:form method="POST"  modelAttribute="benutzer">

    <form:errors path="*" cssClass="fehler" element="div" />
    ${nachricht}
    <div id="content">
        <h2>Benutzer hinzuf&uuml;gen</h2>



        <ul>
            <li>
                <h3>Benutzerkonto</h3>
            </li>
            <li>
                <form:label path="rolle">Funktionsumfang</form:label>
                    <div class="select">
                    <form:select path="rolle" cssClass="field">
                        <form:option value="Spender" label="Spender" />
                        <form:option value="Tafel" label="Tafel" />
                        <form:option value="Koordinator" label="Koordinator" />
                        <form:option value="Administrator" label="Administrator" />
                    </form:select>
                </div>
            </li>
            <li>
                <form:label path="vorname">Vorname</form:label>
                <form:input path="vorname" cssClass="field"/>
            </li>
            <li>
                <form:label path="nachname">Nachname</form:label>
                <form:input path="nachname" cssClass="field" />
            </li>
            <li>
                <form:label path="eMail">E-Mail</form:label>
                <form:input path="eMail" cssClass="field" />
            </li>
            <li>
                <form:label path="passwort">Passwort</form:label>
                <form:input path="passwort" cssClass="field" type="password" />
            </li>
        </ul>
        <hr>
        <div class="buttons">
            <a href="<%=request.getContextPath()%>/benutzerverwaltung" class="button minor">Abbrechen</a>
            <input type="submit" value="Weiter &raquo;" class="button"> 
            <div class="clear"></div>   
        </div>
        <br>
        <p>Alle Felder sind Pflichtfelder. <br/> Bitte vergessen Sie nicht dem Benutzer einen Spender / einer Tafel zuzuweisen!</p>
    </div>
</form:form>
<jsp:include page="../footer.jsp"/>
