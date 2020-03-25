<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="crt" uri="customtags" %>
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
            <fmt:message key="periodicals"/>
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
        <link href="${root}/css/admin.css" rel="stylesheet">
    </head>

    <body class="grey lighten-3">

    <!--Main Navigation-->
    <header>

        <!-- Navbar -->
        <nav class="navbar fixed-top navbar-expand-lg navbar-light white scrolling-navbar" id="navbar">
            <div class="container-fluid">

                <!-- Brand -->
                <a class="navbar-brand waves-effect" href="#">
                    <strong class="blue-text"><fmt:message key="periodicals"/></strong>
                </a>

                <!-- Collapse -->
                <button class="navbar-toggler" type="button" data-toggle="collapse"
                        data-target="#navbarSupportedContent"
                        aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <!-- Links -->
                <div class="collapse navbar-collapse" id="navbarSupportedContent">

                    <!-- Left -->
                    <ul class="navbar-nav mr-auto">

                    </ul>

                    <!-- Right -->
                    <ul class="navbar-nav nav-flex-icons">
                        <li class="nav-item mr-4 dropdown">
                            <a class="nav-link dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"
                               aria-expanded="false">
                                <fmt:message key="change_language"/>
                            </a>
                            <div class="dropdown-menu">
                                <a class="dropdown-item" href="./?command=CHANGE_LANGUAGE&target_language=en">
                                    <fmt:message key="english"/>
                                </a>
                                <a class="dropdown-item" href="./?command=CHANGE_LANGUAGE&target_language=ru">
                                    <fmt:message key="russian"/>
                                </a>
                            </div>
                        </li>
                        <li class="nav-item" style="max-width: 50%">
                            <a class="nav-link">
                                    ${sessionScope.login}
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="./?command=SIGN_OUT" class="nav-link border border-light rounded">
                                <fmt:message key="sign_out"/>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <!-- Navbar -->

        <!-- Sidebar -->
        <div class="sidebar-fixed position-fixed">

            <a class="waves-effect">
                <img src="${root}/img/logo.png" class="img-fluid" alt="logo">
            </a>

            <div class="list-group list-group-flush">
                <a href="./?command=SHOW_USERS" class="list-group-item list-group-item-action waves-effect">
                    <em class="fas fa-user mr-3"></em><fmt:message key="users"/></a>
                <a href="./?command=SHOW_EDITIONS" class="list-group-item list-group-item-action waves-effect">
                    <em class="fas fa-book mr-3"></em><fmt:message key="editions"/></a>
                <a href="./?command=SHOW_PAYMENTS" class="list-group-item list-group-item-action waves-effect">
                    <em class="fas fa-money-bill-alt mr-3"></em><fmt:message key="payments"/></a>
            </div>

        </div>
        <!-- Sidebar -->

    </header>
    <!--Main Navigation-->

    <!--Main layout-->
    <main class="pt-4 mx-lg-0" style="min-height: 100%">
        <div class="container-fluid mt-5">

            <!-- Heading -->
            <div class="card mb-4 wow fadeIn">

                <!--Card content-->
                <div class="card-body d-sm-flex justify-content-between">
                    <h4 class="mb-2 mb-sm-0 pt-1">
                        <a href="./?command=ADMIN"><fmt:message key="periodicals"/></a>
                        <span>/</span>
                        <span><fmt:message key="dashboard"/></span>
                    </h4>
                </div>

            </div>
            <!-- Heading -->

            <!--Grid row-->
            <div class="row wow fadeIn">


                <!--Grid column-->
                <div class="col-md-12 mb-4">

                    <!--Card-->
                    <div class="card mb-4">

                        <!--Card content-->
                        <div class="card-body">

                            <c:choose>
                                <c:when test="${empty adminPageOption or adminPageOption.toString() == 'MAIN'}">
                                    <!-- List group links -->
                                    <div class="list-group list-group-flush">
                                        <a class="list-group-item list-group-item-action waves-effect">
                                            <fmt:message key="users"/>
                                            <span class="badge badge-success badge-pill pull-right">
                                                ${usersCount}<em class="fas fa-user ml-1"></em>
                                            </span>
                                        </a>
                                        <a class="list-group-item list-group-item-action waves-effect">
                                            <fmt:message key="editions"/>
                                            <span class="badge badge-danger badge-pill pull-right">
                                                ${editionsCount}<em class="fas fa-book ml-1"></em>
                                            </span>
                                        </a>
                                        <a class="list-group-item list-group-item-action waves-effect">
                                            <fmt:message key="payments"/>
                                            <span class="badge badge-primary badge-pill pull-right">
                                                ${paymentsCount}<em class="fas fa-money-bill ml-1"></em>
                                            </span>
                                        </a>
                                    </div>
                                    <!-- List group links -->
                                </c:when>
                                <c:when test="${adminPageOption.toString() == 'USERS'}">
                                    <!-- List group links -->
                                    <div class="list-group list-group-flush">
                                        <c:if test="${empty users}">
                                            ${message}
                                        </c:if>
                                        <c:if test="${not empty users}">
                                        <!-- MDBootstrap table -->
                                            <table id="dtMaterialDesignExample" class="table table-striped w-100" cellspacing="0">
                                                <thead>
                                                <tr>
                                                    <th class="th-sm">Id
                                                    </th>
                                                    <th class="th-sm">Full name
                                                    </th>
                                                    <th class="th-sm">Is active
                                                    </th>
                                                    <th class="th-sm">Block
                                                    </th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="user" items="${users}" varStatus="status">
                                                    <tr>
                                                        <td><c:out value="${ user.id }" /></td>
                                                        <td><c:out value="${ user.fullName }" /></td>
                                                        <td><c:out value="${ user.available }" /></td>
                                                        <td>
                                                            <button type="button" onclick="userBlockUnblock()">
                                                                <input type="hidden" name="id" value="${user.id}"/>
                                                                <c:if test="${user.available}">
                                                                    <input type="hidden" name="action" value="block"/>
                                                                    <fmt:message key="block"/>
                                                                </c:if>
                                                                <c:if test="${not user.available}">
                                                                    <input type="hidden" name="action" value="unblock"/>
                                                                    <fmt:message key="unblock"/>
                                                                </c:if>
                                                            </button>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </c:if>
                                    </div>
                                </c:when>
                                <c:when test="${adminPageOption.toString() == 'EDITIONS'}">
                                    <!-- List group links -->
                                    <div class="list-group list-group-flush">
                                        <a class="list-group-item list-group-item-action waves-effect">
                                            <fmt:message key="editions"/>
                                            <span class="badge badge-danger badge-pill pull-right">
                                                ${editionsCount}<em class="fas fa-book ml-1"></em>
                                            </span>
                                        </a>
                                    </div>
                                </c:when>
                                <c:when test="${adminPageOption.toString() == 'PAYMENTS'}">
                                    <!-- List group links -->
                                    <div class="list-group list-group-flush">
                                        <a class="list-group-item list-group-item-action waves-effect">
                                            <fmt:message key="payments"/>
                                            <span class="badge badge-primary badge-pill pull-right">
                                                ${paymentsCount}<em class="fas fa-money-bill ml-1"></em>
                                            </span>
                                        </a>
                                    </div>
                                </c:when>
                            </c:choose>

                        </div>
                        <!--/.Card content-->

                    </div>
                    <!--/.Card-->

                </div>
                <!--Grid column-->

            </div>
            <!--Grid row-->

        </div>
    </main>
    <!--Main layout-->

    <!--Footer-->
    <footer class="page-footer text-center font-small">
        <!--Copyright-->
        <div class="footer-copyright py-3">
            <crt:copyright/>
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
    <!-- DataTables CSS -->
    <link href="${root}/css/addons/datatables.min.css" rel="stylesheet">
    <!-- DataTables JS -->
    <script src="${root}/js/addons/datatables.min.js" type="text/javascript"></script>
    <!-- DataTables Select CSS -->
    <link href="${root}/css/addons/datatables-select.min.css" rel="stylesheet">
    <!-- DataTables Select JS -->
    <script src="${root}/js/addons/datatables-select.min.js" type="text/javascript"></script>
    <!-- Initializations -->
    <script type="text/javascript">
        // Animations initialization
        new WOW().init();
    </script>

    <script type="text/javascript">
        // user block/unblock
        function userBlockUnblock() {
            //todo ajax post
        }
    </script>

    </body>

    </html>
</fmt:bundle>