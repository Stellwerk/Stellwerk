<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="header.jsp"/>
<c:if test="${not empty Ausgabe}">
    ${Ausgabe}
</c:if>
<div id="content">
    <h2>${titel}</h2>
    <p>${fn:replace(inhalt, '
', '<br>')}</p>
</div>
<jsp:include page="footer.jsp"/>