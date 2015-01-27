<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"     uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../header.jsp"/>
<c:if test="${not empty Ausgabe}">
    ${Ausgabe}
</c:if>
<form:form method="POST"  modelAttribute="email">
    <form:errors path="*" cssClass="fehler" element="div" />
    <div id="content">
        <h2>Reklamieren</h2>

        <ul>
            <li>
                <h3>E-Mail an Koordinator senden</h3>
            </li>
            <li>
                <form:label path="betreff">Art der Reklamation</form:label>
                    <div class="select">
                    <form:select path="betreff" cssClass="field">
                        <form:option value="Tafelstellwerk - Antrag Spende ändern" label="Ändern" />
                        <form:option value="Tafelstellwerk - Antrag Spende löschen" label="Löschen" />
                        <form:option value="Tafelstellwerk - Anmerkung eines Spenders" label="Anmerkung" />
                    </form:select>
                </div>
            </li>
            <li>
                <form:label path="nachricht">Nachricht</form:label>
                <form:textarea path="nachricht" />
                <dl class="info left">
                    <dt>&#x69;</dt>
                    <dd>Bitte teilen Sie uns mit, was an Ihrer Spende zu ver&auml;ndern ist.
                        Die ausgewählte Spende wird an die Mail angeh&auml;ngt. <br>
                        Spende: ${spendenname} <br>
                        Erstellungsdatum: <fmt:formatDate value="${spendenerstellungsdatum}" pattern="dd.MM.yyyy" />
                    </dd>
                </dl>
            </li>
        </ul>
        <hr>
        <div class="buttons">
            <a href="<%=request.getContextPath()%>/meine-spenden" class="button minor">Abbrechen</a>
            <input type="submit" value="Weiter &raquo;" class="button"> 
            <div class="clear"></div>   
        </div>

    </div>
</form:form>
<jsp:include page="../footer.jsp"/>
