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
        <link href="${root}/css/custom.css" rel="stylesheet">
    </head>

    <body>
    <!-- Navbar -->
    <nav class="navbar fixed-top navbar-expand-lg navbar-dark scrolling-navbar flex-column flex-md-row">
        <div class="container-fluid">
            <a class="navbar-brand mr-0 mr-md-2" style="color: white">
                <strong><fmt:message key="periodicals"/></strong>
            </a>
            <!-- Collapse -->
            <button class="navbar-toggler" type="button" data-toggle="collapse"
                    data-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <div class="navbar-nav-scroll">
                    <ul class="navbar-nav bd-navbar-nav flex-row">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}">
                                <fmt:message key="home_page"/>
                            </a>
                        </li>
                    </ul>
                </div>
                <ul class="navbar-nav ml-md-auto">
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
                    <c:if test="${sessionScope.userType == 'GUEST'}">
                        <li class="nav-item mr-4">
                            <button type="button" class="btn btn-outline-white waves-effect"
                                    data-toggle="modal" data-target="#modalLogin">
                                <fmt:message key="sign_in"/>
                            </button>
                        </li>
                        <li class="nav-item">
                            <button type="button" class="btn btn-outline-white waves-effect"
                                    data-toggle="modal" data-target="#modalRegister">
                                <fmt:message key="sign_up"/>
                            </button>
                        </li>
                    </c:if>
                    <c:if test="${sessionScope.userType != 'GUEST'}">
                        <li class="nav-item" style="max-width: 50%">
                            <a class="nav-link">
                                    ${sessionScope.login}
                            </a>
                        </li>
                        <li class="nav-item">
                            <form class="mx-auto" action="./" method="post">
                                <input type="text" name="command" value="SIGN_OUT" hidden="hidden">
                                <button type="submit" class="btn btn-outline-white waves-effect">
                                    <fmt:message key="sign_out"/>
                                </button>
                            </form>
                        </li>
                    </c:if>
                </ul>
            </div>
        </div>
    </nav>
    <!-- Navbar -->

    <!-- Full Page Intro -->
    <div class="view"
         style="background-image: url(${root}/img/background.jpg); background-repeat: no-repeat; background-size: cover;">

        <!-- Mask & flexbox options-->
        <div class="mask rgba-black-light d-flex justify-content-center align-items-center"
             style="min-width: available">

            <!-- Content -->
            <div id="initial_width" class="container-fluid text-center white-text mx-5">
                <c:if test="${sessionScope.userType == 'GUEST'}">
                    <h1 class="mb-4">
                        <strong><fmt:message key="guest_message"/></strong>
                    </h1>
                </c:if>
                <c:if test="${sessionScope.userType == 'USER'}">
                    <h1 class="mb-4">
                        <strong><fmt:message key="periodicals"/></strong>
                    </h1>

                    <div class="container-fluid mt-5">
                        <!--Grid row-->
                        <div class="row flex-center">
                            <!--Grid column-->
                            <div class="col-md-4 mb-4">
                                <!--Card-->
                                <div class="card mb-4" style="background-color: transparent">
                                    <!--Card content-->
                                    <div class="card-body">
                                        <div class="list-group list-group-flush">
                                            <a href="./?command=USER_PAGE" class="list-group-item active waves-effect">
                                                <em class="fas fa-chart-pie mr-3"></em>
                                                <fmt:message key="my_account"/>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                                <!--/.Card-->
                            </div>
                            <!--Grid column-->
                        </div>
                        <!--Grid row-->
                    </div>
                </c:if>
                <c:if test="${sessionScope.userType == 'ADMIN'}">
                    <h1 class="mb-4">
                        <strong><fmt:message key="periodicals"/></strong>
                    </h1>

                    <div class="container-fluid mt-5">
                        <!--Grid row-->
                        <div class="row flex-center">
                            <!--Grid column-->
                            <div class="col-md-4 mb-4">
                                <!--Card-->
                                <div class="card mb-4" style="background-color: transparent">
                                    <!--Card content-->
                                    <div class="card-body">
                                        <div class="list-group list-group-flush">
                                            <a href="./?command=ADMIN_PAGE" class="list-group-item active waves-effect">
                                                <em class="fas fa-chart-pie mr-3"></em>
                                                <fmt:message key="dashboard"/>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                                <!--/.Card-->
                            </div>
                            <!--Grid column-->
                        </div>
                        <!--Grid row-->
                    </div>
                </c:if>
            </div>
            <!-- Content -->

        </div>
        <!-- Mask & flexbox options-->

    </div>
    <!-- Full Page Intro -->

    <!--Footer-->
    <footer class="page-footer text-center font-small">

        <!--Copyright-->
        <div class="footer-copyright py-3">
            <crt:copyright/>
        </div>
        <!--/.Copyright-->

    </footer>
    <!--/.Footer-->

    <c:if test="${sessionScope.userType == 'GUEST'}">
        <section>
            <!--Modal: Login -->
            <div class="modal fade" id="modalLogin" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="loginHeader"><fmt:message key="sign_in"/></h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <form class="mx-auto">
                                <input type="hidden" name="command" value="SIGN_IN"/>
                                <div class="form-group">
                                    <label for="user-name-label"><fmt:message key="login"/></label>
                                    <input type="text" class="form-control" id="user-name-label" name="login"
                                           aria-describedby="loginHelp"
                                           placeholder="<fmt:message key="enter_login"/>">
                                    <small id="loginHelp" class="form-text text-muted"><fmt:message
                                            key="login_text"/></small>
                                </div>
                                <div class="form-group">
                                    <label for="password-label"><fmt:message key="password"/></label>
                                    <input type="password" class="form-control" id="password-label" name="password"
                                           placeholder="<fmt:message key="enter_password"/>">
                                </div>
                                <div id="sign_in_div" class="alert alert-danger" role="alert"></div>
                            </form>
                            <button id="sign_in_button" class="btn btn-primary"><fmt:message key="sign_in"/></button>
                            <br>
                        </div>
                    </div>
                </div>
            </div>
            <!--Modal: Login -->

            <!--Modal: Register -->
            <div class="modal fade" id="modalRegister" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="registerHeader"><fmt:message key="sign_up"/></h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <form class="mx-auto">
                                <input type="hidden" name="command" value="SIGN_UP"/>
                                <div class="form-group">
                                    <label for="user-name-label-register"><fmt:message key="login"/></label>
                                    <input type="text" class="form-control" id="user-name-label-register" name="login"
                                           aria-describedby="login-help-register"
                                           placeholder="<fmt:message key="enter_login"/>">
                                    <small id="login-help-register" class="form-text text-muted"><fmt:message
                                            key="login_text"/></small>
                                </div>
                                <div class="form-group">
                                    <label for="password-label-register"><fmt:message key="password"/></label>
                                    <input type="password" class="form-control" id="password-label-register"
                                           name="password" placeholder="<fmt:message key="enter_password"/>">
                                </div>
                                <div class="form-group">
                                    <label for="full-name-label-register"><fmt:message key="full_name"/></label>
                                    <input type="text" class="form-control" id="full-name-label-register"
                                           name="full_name" placeholder="<fmt:message key="enter_full_name"/>">
                                </div>
                                <div class="form-group">
                                    <label for="email-label-register"><fmt:message key="email"/></label>
                                    <input type="email" class="form-control" id="email-label-register"
                                           name="email" placeholder="<fmt:message key="enter_email"/>">
                                </div>
                                <div id="sign_up_div" class="alert alert-danger" role="alert"></div>
                            </form>
                            <button id="sign_up_button" class="btn btn-primary"><fmt:message key="sign_up"/></button>
                            <br>
                        </div>
                    </div>
                </div>
            </div>
            <!--Modal: Register -->
        </section>
    </c:if>

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

    <script>
        $(document).on('click', '#sign_up_button', function () {
            var data = {
                command: 'SIGN_UP',
                login: $('#user-name-label-register').val(),
                password: $('#password-label-register').val(),
                full_name: $('#full-name-label-register').val(),
                email: $('#email-label-register').val()
            };

            $.post('./', $.param(data), function (responseText) {
                if (responseText.length < 100) {
                    $('#sign_up_div').text(responseText);
                } else {
                    document.open();
                    document.write(responseText);
                    document.close();
                }
            });
        });
    </script>

    <script>
        $(document).on('click', '#sign_in_button', function () {
            var data = {
                command: 'SIGN_IN',
                login: $('#user-name-label').val(),
                password: $('#password-label').val()
            };

            $.post('./', $.param(data), function (responseText) {
                if (responseText.length < 100) {
                    $('#sign_in_div').text(responseText);
                } else {
                    document.open();
                    document.write(responseText);
                    document.close();
                }
            });
        });
    </script>

    </body>

    </html>
</fmt:bundle>