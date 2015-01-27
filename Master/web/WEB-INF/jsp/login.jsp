
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="header.jsp"/>
<c:if test="${not empty param.login_error}">
    <div class="login fehler">
        <p>Anmeldung fehlgeschlagen.<br />
            Grund: ${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}
        </p>
    </div>
</c:if>
${nachricht}
<%-- Die ID's sind von Spring vorgegeben. --%>
<form action="<%=request.getContextPath()%>/j_spring_security_check" method="post" id="login">
    <label for="j_username" class="hide">E-Mail</label>
    <input id="j_username" name="j_username" type="email" placeholder="E-Mail" class="field" tabindex="1" />            
    <label for="j_password" class="hide">Passwort</label>
    <input id="j_password" name="j_password" type="password" placeholder="Passwort" class="field" tabindex="2" />            
    <div class="checkbox">
        <input id="j_remember" name="_spring_security_remember_me" type="checkbox" tabindex="3" />
        <label for="j_remember">Erinnern?</label>
    </div>
    <input id="j_submit" type="submit" value="Anmelden &raquo;" class="button" tabindex="4" />
    <a id="forgot_pw" href="<%=request.getContextPath()%>/passwort-vergessen">Passwort vergessen?</a>
    <div class="clear"></div>
</form>
<jsp:include page="footer.jsp"/>