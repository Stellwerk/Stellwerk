<%-- 
    Document   : kontakt
    Created on : 22.07.2013, 18:10:58
    Author     : Patrick
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"     uri="http://www.springframework.org/security/tags" %>
<jsp:include page="header.jsp"/>
<form:form method="POST" commandName="Kontakt">
 <form:errors path="*" cssClass="fehler" element="div" />
 ${nachricht}
<div id="content">
        <h2>Kontaktformular</h2>
        
            <ul>
                <li>
                    <ul>
                        <li>
                            <form:label path="name">Name *</form:label>
                            <form:input path="name" cssClass="field" />
                        </li>
                        <li>
                            <form:label path="strasse">Stra&szlig;e</form:label>
                            <form:input path="strasse" cssClass="field" />
                        </li>
                        <li>         
                            <form:label path="plz">PLZ / Ort</form:label>
                            <form:input path="plz" cssClass="field small1" />
                            <form:input path="ort" cssClass="field small3" />
                        </li>
                        <li>
                            <form:label path="tel">Telefon</form:label>
                            <form:input path="tel" cssClass="field" type="tel" />
                        </li>
                        <li>
                            <form:label path="fax">Telefax</form:label>
                            <form:input path="fax" cssClass="field" type="tel" />
                        </li>
                        <li>
                            <form:label path="email">E-Mail *</form:label>
                            <form:input path="email" cssClass="field" type="tel" />
                        </li>
                        <li>
                            <form:label path="text">Nachricht *</form:label>
                            <form:textarea path="text"></form:textarea>
                        </li>
                    </ul>
                </li>
                <li>
                    <hr>
                   <a href="<%=request.getContextPath()%>" class="button minor">Abbrechen</a>
                   <input type="submit" value="Senden &raquo;" class="button">
                   <div class="clear"></div>
                </li>
            </ul>
            <br>
            <p>Alle mit * gekennzeichneten Felder sind Pflichtfelder.</p>
</div>
</form:form>
<jsp:include page="footer.jsp"/>