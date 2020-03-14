<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${empty sessionScope.language}">
    <fmt:setLocale value="en_US"/>
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
            <fmt:message key="xml_parser"/>
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
            <strong><fmt:message key="xml_parser"/></strong>
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
        <ul class="navbar-nav ml-md-auto">
            <li class="nav-item mr-4 dropdown">
                <a class="nav-link dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"
                   aria-expanded="false">
                    <fmt:message key="change_language"/>
                </a>
                <div class="dropdown-menu">
                    <a class="dropdown-item" href="./?command=CHANGE_LANGUAGE&targetLanguage=en_US">
                        <fmt:message key="english"/>
                    </a>
                    <a class="dropdown-item" href="./?command=CHANGE_LANGUAGE&targetLanguage=ru_RU">
                        <fmt:message key="russian"/>
                    </a>
                </div>
            </li>
            <c:if test="${sessionScope.userRole == 'GUEST'}">
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
            <c:if test="${sessionScope.userRole == 'USER'}">
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
            </c:if>
        </ul>
    </nav>
    <!-- Navbar -->

    <!-- Full Page Intro -->
    <div class="view"
         style="background-image: url(${root}/img/background.jpg); background-repeat: no-repeat; background-size: cover;">

        <!-- Mask & flexbox options-->
        <div class="mask rgba-black-light d-flex justify-content-center align-items-center" style="min-width: available">

            <!-- Content -->
            <div id="initial_width" class="text-center white-text mx-5 wow fadeIn">
                <c:if test="${sessionScope.userRole == 'GUEST'}">
                    <h1 class="mb-4">
                        <strong><fmt:message key="guest_message"/></strong>
                    </h1>
                </c:if>
                <c:if test="${sessionScope.userRole == 'USER'}">
                    <h1 class="mb-4">
                        <strong><fmt:message key="xml_parser"/></strong>
                    </h1>

                    <a href="./?command=XML_UPLOAD_FORM" class="btn btn-outline-white btn-lg">
                        <fmt:message key="parse_xml"/>
                    </a>
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
            <fmt:message key="copyright"/>
        </div>
        <!--/.Copyright-->

    </footer>
    <!--/.Footer-->

    <c:if test="${sessionScope.userRole == 'GUEST'}">
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
                            <form class="mx-auto" action="./" method="post">
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
        $(document).on("click", "#sign_up_button", function () {
            var data = {
                command: "SIGN_UP",
                login: $("#user-name-label-register").val(),
                password: $("#password-label-register").val()
            };

            $.post("./", $.param(data), function (responseText) {
                if (responseText.length < 50) {
                    $("#sign_up_div").text(responseText);
                } else {
                    document.open();
                    document.write(responseText);
                    document.close();
                }
            });
        });
    </script>

    <script>
        $(document).on("click", "#sign_in_button", function () {
            var data = {
                command: "SIGN_IN",
                login: $("#user-name-label").val(),
                password: $("#password-label").val()
            };

            $.post("./", $.param(data), function (responseText) {
                if (responseText.length < 50) {
                    $("#sign_in_div").text(responseText);
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