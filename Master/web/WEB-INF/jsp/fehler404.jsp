<%-- 
    Document   : Fehler
    Created on : 22.07.2013, 18:10:58
    Author     : Simon Lau
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"     uri="http://www.springframework.org/security/tags" %>

<jsp:include page="header.jsp"/>
<div id="content">
    <form:form  commandName="Fehler">
        <h2>Die angeforderte Seite existiert nicht! (Fehler 404)</h2>
        <p>Das tut uns Leid!</p>

    </form:form>
        <div class="clear"></div>
</div>
<jsp:include page="footer.jsp"/>