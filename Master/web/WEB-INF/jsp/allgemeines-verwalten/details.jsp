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
        <h2>Vorlage #${formular.id}: ${formular.titel}</h2>
        <ul>
            <li>
                <form:label for="titel" path="titel">Betreff</form:label>
                <form:input id="titel" type="text" class="field" path="titel" />
            </li>
            <li>
                <form:label for="inhalt" path="inhalt">Nachricht</form:label>
                <form:textarea id="inhalt" path="inhalt" cssClass="big" />
            </li>
            <li>
                <hr>
                <a href="<%=request.getContextPath()%>/allgemeines-verwalten" class="button minor">Abbrechen</a>
                <input type="submit" value="Speichern &raquo;" class="button">
                <div class="clear"></div>
            </li>
        </ul>
    </div>
</form:form>
<jsp:include page="../footer.jsp"/>