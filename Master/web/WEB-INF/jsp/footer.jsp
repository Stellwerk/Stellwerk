<%-- 
    Document   : footer
    Created on : 22.05.2013, 13:03:35
    Author     : Patrick
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"     uri="http://www.springframework.org/security/tags" %>
</div>
<footer>
    <nav>
        <ul>
            <li><a href="<%=request.getContextPath()%>/allgemeine-geschaeftsbedingungen">AGB</a></li>
            <li><a href="<%=request.getContextPath()%>/kontakt">Kontakt</a></li>
            <li><a href="<%=request.getContextPath()%>/impressum">Impressum</a></li>
            <li class="tafel icon"><a href="http://www.tafel-niedersachsen.de/" title="Die Tafeln in Niedersachsen und Bremen">Die Tafeln in Niedersachsen und Bremen</a></li>
            <li class="hsos icon"><a href="http://www.campus-lingen.hs-osnabrueck.de/fakultaet-mkt.html" title="Hochschule Osnabr&uuml;ck: Fakult&auml;t f&uuml;r Management, Kultur und Technik">Hochschule Osnabr&uuml;ck: Fakult&auml;t f&uuml;r Management, Kultur und Technik</a></li>
            <li class="enactus icon"><a href="http://lueneburg.enactus.de/" title="enactus - Leuphana Universit&auml;t L&uuml;neburg">enactus - Leuphana Universit&auml;t L&uuml;neburg</a></li>
        </ul>
    </nav>
</footer>
</div>
<%--div id="rolleninfo">Rolle:
    <sec:authorize access="hasAnyRole('ROLE_SPENDER')">Spender</sec:authorize>
    <sec:authorize access="hasAnyRole('ROLE_TAFEL')">Tafel</sec:authorize>
    <sec:authorize access="hasAnyRole('ROLE_ADMIN')">Administrator</sec:authorize>
    <sec:authorize access="hasAnyRole('ROLE_KOORD')">Koordinator</sec:authorize>
    <sec:authorize access="isAnonymous()">Anonym</sec:authorize>
</div--%>
</body>
</html>