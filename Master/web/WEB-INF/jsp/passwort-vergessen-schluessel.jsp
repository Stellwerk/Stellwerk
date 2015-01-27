<%-- 
    Document   : passwort-vergessen
    Created on : 10.08.2013, 15:43:47
    Author     : Patrick
--%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="header.jsp"/>
<c:if test="${not empty param.login_error}">
    <div class="login fehler">
        <p>E-Mail Adresse nicht gefunden.</p>
    </div>
</c:if>
${nachricht}
<form action="" method="post" id="login" commandName="formular">
    <label for="eMail" class="hide">E-Mail</label>
    <input id="eMail" name="eMail" type="email" placeholder="E-Mail" class="field" value="${eMail}" />
    <label for="schluessel" class="hide">Best&auml;tigungsschl&uuml;ssel</label>
    <input id="schluessel" name="schluessel"  placeholder="Ihr Sicherheitsschlüssel" class="field" value="${schluessel}" />
    <input id="j_submit" type="submit" value="Sicherheitsschl&uuml;ssel einl&ouml;sen &raquo;" class="button"/>
    <div class="clear"></div>
</form>
<jsp:include page="footer.jsp"/>