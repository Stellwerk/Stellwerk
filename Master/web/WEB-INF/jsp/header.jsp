<%-- 
    Document   : header
    Created on : 22.05.2013, 13:00:58
    Author     : Patrick
--%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"     uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="de-de">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tafelstellwerk</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css" type="text/css">
        <link rel="icon" href="<%=request.getContextPath()%>/img/favicon.ico" type="image/x-icon">
        <base href="<%=request.getContextPath()%>">
        <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
        <script type="text/javascript">!window.jQuery && document.write('<script src="<%=request.getContextPath()%>/js/jquery.min.js"><\/script>');</script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.custom.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/modernizr.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/forms.min.js"></script>
    </head>
    <body>
        <div id="page-wrap">
            <header>
                <h1><a href="<%=request.getContextPath()%>/">Tafelstellwerk - Portal f&uuml;r Tafellogistik</a></h1>
                <nav>
                    <ul>
                        <sec:authorize access="isAuthenticated()">
                            <%--------------Spender------------------%>
                            <sec:authorize access="hasAnyRole('ROLE_SPENDER')">
                                <li>
                                    <a href="<%=request.getContextPath()%>/spenden">Spende einreichen</a>
                                </li>                                
                                <li>
                                    <a href="<%=request.getContextPath()%>/meine-spenden">Meine Spenden</a>
                                </li>
                            </sec:authorize>
                            <%--------------Tafel------------------%>
                            <sec:authorize access="hasAnyRole('ROLE_TAFEL')">
                                <li>
                                    <a href="<%=request.getContextPath()%>/offene-spenden">Offene Spenden</a>
                                </li>
                                <li>
                                    <a href="<%=request.getContextPath()%>/verteilte-spenden">Abgeschlossene Spenden</a>
                                </li>
                                <li>
                                    <a href="<%=request.getContextPath()%>/spenden">Spende einreichen</a>
                                </li>                                
                                <li>
                                    <a href="<%=request.getContextPath()%>/meine-spenden">Meine Spenden</a>
                                </li>
                            </sec:authorize>
                            <%--------------Koordinator------------------%>
                            <sec:authorize access="hasAnyRole('ROLE_KOORD')">
                                <li>
                                    <a href="<%=request.getContextPath()%>/eingegangene-spenden">Eingegangen</a>
                                </li>
                                <li>
                                    <a href="<%=request.getContextPath()%>/spenden/spendenvorgaenge">Vorg&auml;nge</a>
                                </li>
                                <li>
                                    <a href="<%=request.getContextPath()%>/spenden/abgeschlossen">Abgeschlossen</a>
                                </li>
                                <li>
                                    <a href="<%=request.getContextPath()%>/benutzerverwaltung-koordinator">Benutzerverwaltung</a>
                                </li>
                            </sec:authorize>
                            <%--------------Administrator------------------%>
                            <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                                <li>
                                    <a href="<%=request.getContextPath()%>/benutzerverwaltung">Benutzerverwaltung</a>
                                </li>
                                <li>
                                    <a href="<%=request.getContextPath()%>/tafelverwaltung">Tafelverwaltung</a>
                                </li>
                                <li>
                                    <a href="<%=request.getContextPath()%>/spenderverwaltung">Spenderverwaltung</a>
                                </li>
                                <li>
                                    <a href="<%=request.getContextPath()%>/emailverwaltung">E-Mail-Verwaltung</a>
                                </li>
                                <li>
                                    <a href="<%=request.getContextPath()%>/allgemeines-verwalten">Sonstiges</a>
                                </li>
                            </sec:authorize>
                        </sec:authorize>
                        <sec:authorize access="isAnonymous()">
                            <li>
                                <a href="<%=request.getContextPath()%>/allgemeine-geschaeftsbedingungen">AGB</a>
                            </li>
                            <li>
                                <a href="<%=request.getContextPath()%>/kontakt">Kontakt</a>
                            </li>
                            <li>
                                <a href="<%=request.getContextPath()%>/impressum">Impressum</a>
                            </li>
                        </sec:authorize>
                    </ul>
                </nav>
                <div id="usercenter">
                    <sec:authorize access="isAuthenticated()">
                        Hallo <sec:authentication property="principal.username" /> &middot; <a href="<%=request.getContextPath()%>/mein-profil">Mein Profil</a> &middot; <a href="<%=request.getContextPath()%>/logout">Abmelden</a>
                    </sec:authorize>
                    <sec:authorize access="isAnonymous()">
                        Hallo Gast &middot; <a href="<%=request.getContextPath()%>/login">Anmelden</a>
                    </sec:authorize>
                </div>
            </header>
            <div id="wrap">