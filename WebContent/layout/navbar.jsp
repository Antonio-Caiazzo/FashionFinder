<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<nav class="navbar-container">

    <div class="navbar-logo">
        <a href="${pageContext.request.contextPath}/index.jsp">FashionFinder</a>
    </div>
    
    <div class="navbar-container-center">
        <div class="navbar-container-center-element">
            <a href="${pageContext.request.contextPath}/prodotti?sesso=u">Uomo</a>
        </div>
        <div class="navbar-container-center-element">
            <a href="${pageContext.request.contextPath}/prodotti?sesso=d">Donna</a>
        </div>
    </div>
    
    <div class="navbar-container-right">
        <div>Cerca</div>
        <div>Accedi</div>
        <div><a href="${pageContext.request.contextPath}/carrello.jsp">Carrello</a></div>
    </div>
</nav>
