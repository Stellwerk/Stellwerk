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
    <div class="login-fehler">
        <p>E-Mail Adresse nicht gefunden.</p>
    </div>
</c:if>
    ${nachricht}
<form action="" method="post" id="login" commandName="formular">
    <label for="" class="hide">Passwort</label>
    <input id="" name="passwort" placeholder="Ihr neues Passwort?" class="field" />
    <label for="" class="hide">Passwort_check</label>
    <input id="" name="passwort_check" placeholder="Passwort wiederholen" class="field" />
    <c:if test="${schluesselOK}">
        <dl class="info check valid">
            <dt>&#xe002;</dt>
            <dd>Der Sicherheitsschl&uuml;ssel wurde akzeptiert</dd>
        </dl>
    </c:if>
    <c:if test="${ not schluesselOK}">
        <dl class="info check invalid">
            <dt>&#xe001;</dt>
            <dd>Kein Sicherheitsschl&umacr;ssel vorhanden</dd>
        </dl>
    </c:if>
    <input id="j_submit" type="submit" value="Passwort &auml;ndern" class="button"/>
    <div class="clear"></div>
</form>
<jsp:include page="footer.jsp"/>
