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
        <link href="${root}/css/dashboard.css" rel="stylesheet">
    </head>

    <body class="grey lighten-3">

    <!--Main Navigation-->
    <header>

        <!-- Navbar -->
        <nav class="navbar fixed-top navbar-expand-lg navbar-light white scrolling-navbar" id="navbar">
            <div class="container-fluid">

                <!-- Brand -->
                <a class="navbar-brand waves-effect" href="./?command=ADMIN" style="font-size: 1.0rem">
                    <strong class="blue-text"><fmt:message key="periodicals"/></strong>
                    <span>/</span>
                    <span><fmt:message key="dashboard"/></span>
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
                <a href="./?command=SHOW_SUBSCRIPTIONS" class="list-group-item list-group-item-action waves-effect">
                    <em class="fas fa-check-square mr-3"></em><fmt:message key="subscriptions"/></a>
            </div>

        </div>
        <!-- Sidebar -->

    </header>
    <!--Main Navigation-->

    <!--Main layout-->
    <main class="pt-4 mx-lg-0" style="min-height: 100%">
        <div class="container-fluid mt-5">

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
                                    <c:if test="${not empty message}">
                                        <div id="users_div" class="alert alert-danger" role="alert">${message}</div>
                                    </c:if>
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
                                        <a class="list-group-item list-group-item-action waves-effect">
                                            <fmt:message key="subscriptions"/>
                                            <span class="badge badge-warning badge-pill pull-right">
                                                ${subscriptionsCount}<em class="fas fa-check-square ml-1"></em>
                                            </span>
                                        </a>
                                    </div>
                                    <!-- List group links -->
                                </c:when>
                                <c:when test="${adminPageOption.toString() == 'USERS'}">
                                    <div id="users_div" class="alert alert-danger" role="alert"></div>
                                    <h4><fmt:message key="users"/></h4>
                                    <!-- List group links -->
                                    <div class="list-group list-group-flush">
                                        <c:if test="${empty users}">
                                            ${message}
                                        </c:if>
                                        <c:if test="${not empty users}">
                                            <!-- MDBootstrap table -->
                                            <table id="dtMaterialDesignExample" class="table table-striped w-100"
                                                   cellspacing="0">
                                                <thead>
                                                <tr>
                                                    <th class="th-sm">
                                                        <fmt:message key="id"/>
                                                    </th>
                                                    <th class="th-sm">
                                                        <fmt:message key="full_name"/>
                                                    </th>
                                                    <th class="th-sm">
                                                        <fmt:message key="is_active"/>
                                                    </th>
                                                    <th class="th-sm">
                                                        <fmt:message key="block_unblock"/>
                                                    </th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="user" items="${users}" varStatus="status">
                                                    <tr>
                                                        <td><c:out value="${ user.id }"/></td>
                                                        <td><c:out value="${ user.fullName }"/></td>
                                                        <td><c:out value="${ user.available }"/></td>
                                                        <td>
                                                            <button class="btn btn-outline-danger waves-effect py-0 px-1 m-0"
                                                                    type="button" id="user${user.id}"
                                                                    onclick="userBlockUnblock(this)">
                                                                <input type="hidden" name="id" value="${user.id}"/>
                                                                <c:if test="${user.available}">
                                                                    <input type="hidden" name="command"
                                                                           value="BLOCK_USER"/>
                                                                    <fmt:message key="block"/>
                                                                </c:if>
                                                                <c:if test="${not user.available}">
                                                                    <input type="hidden" name="command"
                                                                           value="UNBLOCK_USER"/>
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
                                    <div id="editions_div" class="alert alert-danger" role="alert"></div>
                                    <div class="d-flex">
                                        <div class="p-0">
                                            <h4><fmt:message key="editions"/></h4>
                                        </div>
                                        <div class="ml-auto p-0">
                                            <button type="button" class="btn btn-outline-primary waves-effect"
                                                    data-toggle="modal" data-target="#modalNewEdition">
                                                <fmt:message key="add_new_edition"/>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- List group links -->
                                    <div class="list-group list-group-flush">
                                        <c:if test="${empty editions}">
                                            ${message}
                                        </c:if>
                                        <c:if test="${not empty editions}">
                                            <!-- MDBootstrap table -->
                                            <table id="dtMaterialDesignExample" class="table table-striped w-100"
                                                   cellspacing="0">
                                                <thead>
                                                <tr>
                                                    <th class="th-sm">
                                                        <fmt:message key="id"/>
                                                    </th>
                                                    <th class="th-sm">
                                                        <fmt:message key="name"/>
                                                    </th>
                                                    <th class="th-sm">
                                                        <fmt:message key="type"/>
                                                    </th>
                                                    <th class="th-sm">
                                                        <fmt:message key="theme"/>
                                                    </th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="edition" items="${editions}" varStatus="status">
                                                    <tr>
                                                        <td><c:out value="${ edition.id }"/></td>
                                                        <td><c:out value="${ edition.name }"/></td>
                                                        <td><c:out value="${ edition.editionType.type }"/></td>
                                                        <td><c:out value="${ edition.editionTheme.title }"/></td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </c:if>
                                    </div>
                                </c:when>
                                <c:when test="${adminPageOption.toString() == 'PAYMENTS'}">
                                    <div id="payments_div" class="alert alert-danger" role="alert"></div>
                                    <h4><fmt:message key="payments"/></h4>
                                    <!-- List group links -->
                                    <div class="list-group list-group-flush">
                                        <c:if test="${empty payments}">
                                            ${message}
                                        </c:if>
                                        <c:if test="${not empty payments}">
                                            <!-- MDBootstrap table -->
                                            <table id="dtMaterialDesignExample" class="table table-striped w-100"
                                                   cellspacing="0">
                                                <thead>
                                                <tr>
                                                    <th class="th-sm">
                                                        <fmt:message key="id"/>
                                                    </th>
                                                    <th class="th-sm">
                                                        <fmt:message key="user"/>
                                                    </th>
                                                    <th class="th-sm">
                                                        <fmt:message key="type"/>
                                                    </th>
                                                    <th class="th-sm">
                                                        <fmt:message key="date"/>
                                                    </th>
                                                    <th class="th-sm">
                                                        <fmt:message key="amount"/>
                                                    </th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="payment" items="${payments}" varStatus="status">
                                                    <tr>
                                                        <td><c:out value="${ payment.id }"/></td>
                                                        <td><c:out value="${ payment.user.fullName }"/></td>
                                                        <td><c:out value="${ payment.paymentType.type }"/></td>
                                                        <td><c:out value="${ payment.date }"/></td>
                                                        <td><c:out value="${ payment.amount }"/></td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </c:if>
                                    </div>
                                </c:when>
                                <c:when test="${adminPageOption.toString() == 'SUBSCRIPTIONS'}">
                                    <div id="subscriptions_div" class="alert alert-danger" role="alert"></div>
                                    <h4><fmt:message key="subscriptions"/></h4>
                                    <!-- List group links -->
                                    <div class="list-group list-group-flush">
                                        <c:if test="${empty subscriptions}">
                                            ${message}
                                        </c:if>
                                        <c:if test="${not empty subscriptions}">
                                            <!-- MDBootstrap table -->
                                            <table id="dtMaterialDesignExample" class="table table-striped w-100"
                                                   cellspacing="0">
                                                <thead>
                                                <tr>
                                                    <th class="th-sm">
                                                        <fmt:message key="id"/>
                                                    </th>
                                                    <th class="th-sm">
                                                        <fmt:message key="edition"/>
                                                    </th>
                                                    <th class="th-sm">
                                                        <fmt:message key="subscription_start_date"/>
                                                    </th>
                                                    <th class="th-sm">
                                                        <fmt:message key="subscription_end_date"/>
                                                    </th>
                                                    <th class="th-sm">
                                                        <fmt:message key="user"/>
                                                    </th>
                                                    <th class="th-sm">
                                                        <fmt:message key="is_paid"/>
                                                    </th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="subscription" items="${subscriptions}"
                                                           varStatus="status">
                                                    <tr>
                                                        <td><c:out value="${ subscription.id }"/></td>
                                                        <td><c:out value="${ subscription.edition.name }"/></td>
                                                        <td><c:out value="${ subscription.subscriptionStartDate }"/></td>
                                                        <td><c:out value="${ subscription.subscriptionEndDate }"/></td>
                                                        <td><c:out value="${ subscription.user.fullName }"/></td>
                                                        <td><c:out value="${ subscription.paid }"/></td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </c:if>
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


    <section>
        <!--Modal: Add new edition -->
        <div class="modal fade" id="modalNewEdition" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
             aria-hidden="true">
            <div class="modal-dialog w-50" role="document" style="max-width: 1000px;">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="newEditionHeader"><fmt:message key="add_new_edition"/></h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form class="mx-auto">
                            <div class="form-group">
                                <label for="edition-name-label"><fmt:message key="name"/></label>
                                <input type="text" class="form-control" id="edition-name-label" name="name"
                                       placeholder="<fmt:message key="enter_name"/>">
                            </div>

                            <div class="form-group">
                                <label for="edition-type-label"><fmt:message key="type"/></label>
                                <select class="browser-default custom-select" id="edition-type-label">
                                    <option value="" disabled selected><fmt:message key="enter_type"/></option>
                                    <c:forEach var="type" items="${editionsTypes}">
                                        <option value="${type.id}">${type.type}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="edition-theme-label"><fmt:message key="theme"/></label>
                                <select class="browser-default custom-select" id="edition-theme-label">
                                    <option value="" disabled selected><fmt:message key="enter_theme"/></option>
                                    <c:forEach var="theme" items="${editionsThemes}">
                                        <option value="${theme.id}">${theme.title}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="edition-periodicity-label"><fmt:message key="periodicity"/></label>
                                <input type="text" class="form-control" id="edition-periodicity-label"
                                       name="periodicity"
                                       placeholder="<fmt:message key="enter_periodicity"/>">
                            </div>

                            <div class="form-group">
                                <label for="edition-min-period-label"><fmt:message key="min_period"/></label>
                                <input type="text" class="form-control" id="edition-min-period-label" name="min-period"
                                       placeholder="<fmt:message key="enter_min_period"/>">
                            </div>

                            <div class="form-group">
                                <label for="edition-price-label"><fmt:message key="price"/></label>
                                <input type="text" class="form-control" id="edition-price-label" name="price"
                                       placeholder="<fmt:message key="enter_price"/>">
                            </div>

                            <div id="add-new-edition-message" class="alert alert-danger" role="alert"></div>

                        </form>
                        <button id="add-new-edition-button" class="btn btn-primary"><fmt:message key="add"/></button>
                        <br>
                    </div>
                </div>
            </div>
        </div>
        <!--Modal: Add new edition -->

    </section>

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
        function userBlockUnblock(buttonElement) {
            $(buttonElement).prop('disabled', true);
            var data = {
                command: $(buttonElement).find('input[name="command"]').val(),
                id: $(buttonElement).find('input[name="id"]').val()
            };

            $.post('./', $.param(data), function (responseText) {
                console.log(responseText);
                if (responseText.length < 50) {
                    $('#users_div').text(responseText);
                    $(buttonElement).prop('disabled', false);
                } else {
                    document.open();
                    document.write(responseText);
                    document.close();
                }
            });
        }
    </script>

    <script>
        $(document).on('click', '#add-new-edition-button', function () {

            const nameInput = $('#edition-name-label');
            const typeInput = $('#edition-type-label');
            const themeInput = $('#edition-theme-label');
            const periodicityInput = $('#edition-periodicity-label');
            const minPeriodInput = $('#edition-min-period-label');
            const priceInput = $('#edition-price-label');

            const data = {
                command: 'ADD_EDITION',
                name: nameInput.val(),
                type_id: typeInput.val(),
                theme_id: themeInput.val(),
                periodicity_per_year: periodicityInput.val(),
                minimum_subscription_period: minPeriodInput.val(),
                price_for_minimum_subscription_period: priceInput.val()
            };

            const regexAny = RegExp(/^.{1,200}$/i);
            const regexInt = RegExp(/^[1-9]\d*$/i);
            const regexDouble = RegExp(/^[0-9]\d*[.\d+]?$/i);
            let isValid = true;
            if (!regexAny.test(data.name)) {
                nameInput.css('border-color', 'red');
                isValid = false;
            } else {
                nameInput.css('border-color', '');
            }

            if (data.type_id == null || !regexInt.test(data.type_id)) {
                typeInput.css('border-color', 'red');
                isValid = false;
            } else {
                typeInput.css('border-color', '');
            }

            if (data.theme_id == null || !regexInt.test(data.theme_id)) {
                themeInput.css('border-color', 'red');
                isValid = false;
            } else {
                themeInput.css('border-color', '');
            }

            if (!regexInt.test(data.periodicity_per_year)) {
                periodicityInput.css('border-color', 'red');
                isValid = false;
            } else {
                periodicityInput.css('border-color', '');
            }

            if (!regexInt.test(data.minimum_subscription_period)) {
                minPeriodInput.css('border-color', 'red');
                isValid = false;
            } else {
                minPeriodInput.css('border-color', '');
            }

            if (!regexDouble.test(data.price_for_minimum_subscription_period)) {
                priceInput.css('border-color', 'red');
                isValid = false;
            } else {
                priceInput.css('border-color', '');
            }

            if (isValid) {
                $.post('./', $.param(data), function (responseText) {
                    if (responseText.length < 50) {
                        $('#add-new-edition-message').text(responseText);
                    } else {
                        document.open();
                        document.write(responseText);
                        document.close();
                    }
                });
            }
        });
    </script>

    </body>

    </html>
</fmt:bundle>