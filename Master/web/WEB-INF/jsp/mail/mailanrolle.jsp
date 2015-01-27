<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"     uri="http://www.springframework.org/security/tags" %>

<jsp:include page="../header.jsp"/>


<form:form method="POST"  modelAttribute="email" id="mailanrolle">

    <form:errors path="*" cssClass="fehler" element="div" />
    <div id="content">
        <h2>Benutzergruppe kontaktieren</h2>
        <ul>
            <li>
                <h3>E-Mail an Benutzergruppe senden</h3>
            </li>
            <li>
                <form:label path="betreff">Empf&auml;ngergruppe</form:label>
                    <div class="select">
                        <%-- Benutzergruppe hier auswählen, wird in empfaenger übergeben um es im Controller aufzufangen   --%>
                    <form:select path="empfaenger" cssClass="field">
                        <form:option value="Administrator" label="Admins" />
                        <form:option value="Koordinator" label="Koordinator" />
                        <form:option value="Spender" label="Spender" />
                        <form:option value="Tafel" label="Tafel" />
                    </form:select>
                </div>
            </li>
            <%-- Betreff --%>
            <li>
                <form:label path="betreff">Betreff</form:label>
                <form:input path="betreff" cssClass="field" />
            </li>
            <%-- Nachricht  --%>
            <li>
                <form:label path="nachricht">Nachricht</form:label>
                <form:textarea path="nachricht" />
            </li>
        </ul>
        <hr>
        <div class="buttons">
            <a href="<%=request.getContextPath()%>/benutzerverwaltung-koordinator" class="button minor">Abbrechen</a>
            <button type="button" class="annahme button" title="Senden">Senden</button>
            <div class="clear"></div>   
        </div>

        <%-- Bestätigung EMail senden --%>
        <div class="annahme dialog">
            <h4>E-Mail Senden</h4>
            <p>M&ouml;chten sie diese Mail an alle Benutzer der ausgew&auml;hlten Gruppe senden?</p>
            <input type="button" class="button minor cancel" value="Abbrechen" />
            <input type="button" class="button" value="Bestätigen" id="mailanrollesenden"/>
        </div>           

    </div>
</form:form>
<jsp:include page="../footer.jsp"/>