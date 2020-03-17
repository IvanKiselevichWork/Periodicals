<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${empty sessionScope.language}">
    <fmt:setLocale value="en"/>
</c:if>
<c:if test="${not empty sessionScope.language}">
    <fmt:setLocale value="${sessionScope.language}"/>
</c:if>
<fmt:bundle basename="pagecontent">
    <c:set var="root" value="${pageContext.request.contextPath}"/>
    <html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>
            <fmt:message key="wrong_request"/>
        </title>
        <!-- Font Awesome -->
        <link rel="stylesheet" href="${root}/css/all.css">
        <!-- Bootstrap core CSS -->
        <link href="${root}/css/bootstrap.min.css" rel="stylesheet">
        <!-- Material Design Bootstrap -->
        <link href="${root}/css/mdb.min.css" rel="stylesheet">
        <!-- Your custom styles (optional) -->
        <link href="${root}/css/style.min.css" rel="stylesheet">
        <!-- My custom css -->
        <link href="${root}/css/custom.css" rel="stylesheet">
    </head>

    <body>

    <!-- Navbar -->
    <nav class="navbar fixed-top navbar-expand-lg navbar-dark scrolling-navbar flex-column flex-md-row">
        <a class="navbar-brand mr-0 mr-md-2" style="color: white">
            <strong><fmt:message key="periodicals"/></strong>
        </a>
        <div class="navbar-nav-scroll">
            <ul class="navbar-nav bd-navbar-nav flex-row">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}">
                        <fmt:message key="home_page"/>
                    </a>
                </li>
            </ul>
        </div>
    </nav>
    <!-- Navbar -->

    <!-- Full Page Intro -->
    <div class="view"
         style="background-image: url(${root}/img/background.jpg); background-repeat: no-repeat; background-size: cover;">

        <!-- Mask & flexbox options-->
        <div class="mask rgba-black-light d-flex justify-content-center align-items-center">

            <!-- Content -->
            <div class="text-center white-text mx-5 wow fadeIn">
                <h3><fmt:message key="wrong_request_message"/></h3>
                <fmt:message key="wrong_request_go_home"/><br/>
                <form action="${pageContext.request.contextPath}" method="get">
                    <button type="submit" class="btn btn-outline-white waves-effect"><fmt:message key="home_page"/></button>
                </form>
            </div>
            <!-- Content -->

        </div>
        <!-- Mask & flexbox options-->

    </div>
    <!-- Full Page Intro -->

    <!--Footer-->
    <footer class="page-footer text-center font-small wow fadeIn">

        <!--Copyright-->
        <div class="footer-copyright py-3">
            <fmt:message key="copyright"/>
        </div>
        <!--/.Copyright-->

    </footer>
    <!--/.Footer-->

    <!-- SCRIPTS -->
    <!-- JQuery -->
    <script type="text/javascript" src="${root}/js/jquery-3.4.1.min.js"></script>
    <!-- Bootstrap tooltips -->
    <script type="text/javascript" src="${root}/js/popper.min.js"></script>
    <!-- Bootstrap core JavaScript -->
    <script type="text/javascript" src="${root}/js/bootstrap.min.js"></script>
    <!-- MDB core JavaScript -->
    <script type="text/javascript" src="${root}/js/mdb.min.js"></script>
    <!-- Initializations -->
    <script type="text/javascript">
        // Animations initialization
        new WOW().init();

    </script>

    </body>

    </html>
</fmt:bundle>