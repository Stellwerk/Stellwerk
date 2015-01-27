<%-- 
    Document   : emailverwaltung
    Created on : 11.09.2013, 15:59:11
    Author     : Patrick
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"     uri="http://www.springframework.org/security/tags" %>

<jsp:include page="../header.jsp"/>

<form:form method="POST" commandName="formular">
    <!--form:errors path="*" cssClass="fehler" element="div" //-->
    <div id="content">    
        <h2>Vorlage #${formular.vorlageID}: ${formular.betreff}</h2>
        <ul>
            <li>
                <form:label for="betreff" path="betreff">Betreff</form:label>
                <form:input id="betreff" type="text" class="field" path="betreff" />
            </li>
            <li>
                <form:label for="nachricht" path="nachricht">Nachricht</form:label>
                <form:textarea id="nachricht" path="nachricht" cssClass="big" />
            </li>
            <li>
                <hr>
                <a href="<%=request.getContextPath()%>/emailverwaltung" class="button minor">Abbrechen</a>
                <input type="submit" value="Speichern &raquo;" class="button">
                <div class="clear"></div>
            </li>
        </ul>
    </div>
</form:form>
<jsp:include page="../footer.jsp"/>