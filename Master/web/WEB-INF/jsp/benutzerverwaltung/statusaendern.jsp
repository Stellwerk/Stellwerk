<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"     uri="http://www.springframework.org/security/tags" %>

<jsp:include page="../header.jsp"/>
<div id="content">
	<h2>Benutzer bearbeiten</h2>

    <form:form method="POST"  modelAttribute="benutzer">


		<ul>
			<li>
				<h3>Status &auml;ndern</h3>
			</li>	
                        <c:choose>
                            <c:when test="${not empty benutzer.tafel}">
                                <!-- Benutzer ist Tafel //-->
                                <form:input path="tafel.id" type="hidden"/>
                        <li>
                            <form:label path="tafel.status">Status</form:label>
                            <div class="select">
                                <form:select path="tafel.status" cssClass="field">
                                    <form:option value="grün" label="grün" />
                                    <form:option value="gelb" label="gelb" />
                                </form:select>
                            </div>
                        </li>
                        <li>
                            <form:label path="tafel.notizen">Kommentar</form:label>
                            <form:textarea path="tafel.notizen" />
			</li>      
                                
                            </c:when>
                            <c:when test="${not empty benutzer.spender}">
                                <!-- Benutzer ist Spender //-->
                                <form:input path="spender.id" type="hidden"/>
                        <li>
                            <form:label path="spender.status">Status des Benutzers</form:label>
                            <div class="select">
                                <form:select path="spender.status" cssClass="field">
                                    <form:option value="grün" label="grün" />
                                    <form:option value="gelb" label="gelb" />
                                </form:select>
                            </div>
                        </li>
                        <li>
                            <form:label path="spender.notizen">Kommentar</form:label>
                            <form:textarea path="spender.notizen" />
			</li> 
                                
                            </c:when>
                            <c:otherwise></c:otherwise>
                        </c:choose>
                                    

				
			
		</ul>
		<hr>
		<div class="buttons">
			<a href="<%=request.getContextPath()%>/benutzerverwaltung-koordinator" class="button minor">Abbrechen</a>
			<input type="submit" value="Weiter &raquo;" class="button"> 
			<div class="clear"></div>   
		</div>
	</form:form>

</div>
<jsp:include page="../footer.jsp"/>