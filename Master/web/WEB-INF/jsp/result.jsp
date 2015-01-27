<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec"     uri="http://www.springframework.org/security/tags" %>

<jsp:include page="header.jsp"/>
<div id="content">
    <h2>Formularinhalt</h2>
    <table>
        <tr>
            <td>Name</td>
            <td>${name}</td>
        </tr>
        <tr>
            <td>Stra&szlig;e</td>
            <td>${strasse}</td>
        </tr>
        <tr>
            <td>PLZ</td>
            <td>${plz}</td>
        </tr>
        <tr>
            <td>Ort</td>
            <td>${ort}</td>
        </tr>
        <tr>
            <td>Land</td>
            <td>${land}</td>
        </tr>
        <tr>
            <td>Telefon</td>
            <td>${tel}</td>
        </tr>
        <tr>
            <td>Fax</td>
            <td>${fax}</td>
        </tr>
        <tr>
            <td>E-Mail</td>
            <td>${email}</td>
        </tr>
        <tr>
            <td>Nachricht</td>
            <td>${nachricht}</td>
        </tr>
    </table>  
</div>
<jsp:include page="footer.jsp"/>