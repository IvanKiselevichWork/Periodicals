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
                <a class="navbar-brand waves-effect" href="./?command=ADMIN_PAGE" style="font-size: 1.0rem">
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
                            <form class="mx-auto" action="./" method="post">
                                <input type="text" name="command" value="SIGN_OUT" hidden="hidden">
                                <button type="submit" class="btn btn-outline-dark waves-effect">
                                    <fmt:message key="sign_out"/>
                                </button>
                            </form>
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
            <div class="row">

                <!--Grid column-->
                <div class="col-md-12 mb-4 mt-5">

                    <!--Card-->
                    <div class="card mb-4">

                        <!--Card content-->
                        <div class="card-body">

                            <c:choose>
                                <c:when test="${empty adminPageOption or adminPageOption.toString() == 'MAIN'}">
                                    <c:if test="${not empty message}">
                                        <div id="users_div" class="alert alert-danger" role="alert">${message}</div>
                                    </c:if>
                                    <div class="list-group list-group-flush">
                                        <h4><fmt:message key="welcome"/> ${sessionScope.fullName}!</h4>
                                    </div>
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
                                                    <c:if test="${not user.available}">
                                                        <tr id="row${user.id}" class="table-danger">
                                                    </c:if>
                                                    <c:if test="${user.available}">
                                                        <tr id="row${user.id}">
                                                    </c:if>
                                                        <td><c:out value="${ user.id }"/></td>
                                                        <td><c:out value="${ user.fullName }"/></td>
                                                        <td><c:out value="${ user.available }"/></td>
                                                        <td>
                                                            <button class="btn btn-outline-danger waves-effect py-0 px-1 m-0"
                                                                    type="button" id="blockUser${user.id}"
                                                                    onclick="blockUser(this)"
                                                                    <c:if test="${not user.available}">
                                                                        hidden="hidden"
                                                                    </c:if>
                                                                    >
                                                                <input type="hidden" name="id" value="${user.id}"/>
                                                                <input type="hidden" name="command"
                                                                       value="BLOCK_USER"/>
                                                                <fmt:message key="block"/>
                                                            </button>
                                                            <button class="btn btn-outline-danger waves-effect py-0 px-1 m-0"
                                                                    type="button" id="unblockUser${user.id}"
                                                                    onclick="unblockUser(this)"
                                                                    <c:if test="${user.available}">
                                                                        hidden="hidden"
                                                                    </c:if>
                                                                    >
                                                                <input type="hidden" name="id" value="${user.id}"/>
                                                                <input type="hidden" name="command"
                                                                       value="UNBLOCK_USER"/>
                                                                <fmt:message key="unblock"/>
                                                            </button>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </c:if>
                                        <div class="btn-toolbar" role="toolbar">
                                            <div class="btn-group" role="group">
                                                <c:forEach var="page" begin="1" end="${pages}">
                                                    <a href="./?command=SHOW_USERS&page=${page}" type="button" class="btn btn-page
                                                           <c:if test="${page == currentPage}">btn-primary</c:if> ">
                                                            ${page}
                                                    </a>
                                                </c:forEach>
                                            </div>
                                        </div>
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
                                                    <th class="th-sm">
                                                        <fmt:message key="edit"/>
                                                    </th>
                                                    <th class="th-sm">
                                                        <fmt:message key="block_unblock"/>
                                                    </th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="edition" items="${editions}" varStatus="status">
                                                    <c:if test="${edition.blocked}">
                                                        <tr class="table-danger" id="row${edition.id}">
                                                    </c:if>
                                                    <c:if test="${not edition.blocked}">
                                                        <tr id="row${edition.id}">
                                                    </c:if>
                                                        <td><c:out value="${ edition.id }"/></td>
                                                        <td><c:out value="${ edition.name }"/></td>
                                                        <td><c:out value="${ edition.editionType.type }"/></td>
                                                        <td><c:out value="${ edition.editionTheme.title }"/></td>
                                                        <td>
                                                            <button class="btn btn-outline-primary waves-effect py-0 px-1 m-0"
                                                                    type="button" id="edition${edition.id}"
                                                                    onclick="openModalWindowToEditEdition(this)">
                                                                <fmt:message key="edit"/>
                                                                <input type="hidden" name="id"
                                                                       value="${edition.id}"/>
                                                                <input type="hidden" name="name"
                                                                       value="<c:out value="${ edition.name }"/>"/>
                                                                <input type="hidden" name="type"
                                                                       value="${edition.editionType.id}"/>
                                                                <input type="hidden" name="theme"
                                                                       value="${edition.editionTheme.id}"/>
                                                                <input type="hidden" name="periodicity"
                                                                       value="${edition.periodicityPerYear}"/>
                                                                <input type="hidden" name="minPeriod"
                                                                       value="${edition.minimumSubscriptionPeriodInMonths}"/>
                                                                <input type="hidden" name="price"
                                                                       value="<fmt:formatNumber type="number" maxFractionDigits="2" groupingUsed="false" value = "${ edition.priceForMinimumSubscriptionPeriod }"/>"/>
                                                            </button>
                                                        </td>
                                                        <td>
                                                            <button class="btn btn-outline-danger waves-effect py-0 px-1 m-0"
                                                                    type="button" id="blockEdition${edition.id}"
                                                                    onclick="blockEdition(this)"
                                                                    <c:if test="${edition.blocked}">
                                                                        hidden="hidden"
                                                                    </c:if>
                                                            >
                                                                <input type="hidden" name="id" value="${edition.id}"/>
                                                                <input type="hidden" name="command"
                                                                       value="BLOCK_EDITION"/>
                                                                <fmt:message key="block"/>
                                                            </button>
                                                            <button class="btn btn-outline-danger waves-effect py-0 px-1 m-0"
                                                                    type="button" id="unblockEdition${edition.id}"
                                                                    onclick="unblockEdition(this)"
                                                                    <c:if test="${not edition.blocked}">
                                                                        hidden="hidden"
                                                                    </c:if>
                                                            >
                                                                <input type="hidden" name="id" value="${edition.id}"/>
                                                                <input type="hidden" name="command"
                                                                       value="UNBLOCK_EDITION"/>
                                                                <fmt:message key="unblock"/>
                                                            </button>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </c:if>
                                        <div class="btn-toolbar" role="toolbar">
                                            <div class="btn-group" role="group">
                                                <c:forEach var="page" begin="1" end="${pages}">
                                                    <a href="./?command=SHOW_EDITIONS&page=${page}" type="button" class="btn btn-page
                                                           <c:if test="${page == currentPage}">btn-primary</c:if> ">
                                                            ${page}
                                                    </a>
                                                </c:forEach>
                                            </div>
                                        </div>
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
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${ payment.paymentType.type eq 'refill'}">
                                                                    <span class="badge badge-success badge-pill pull-right">
                                                                        <c:out value="${ payment.paymentType.type }"/>
                                                                        <em class="fas fa-arrow-up ml-1"></em>
                                                                    </span>
                                                                </c:when>
                                                                <c:when test="${ payment.paymentType.type eq 'refund'}">
                                                                    <span class="badge badge-warning badge-pill pull-right">
                                                                        <c:out value="${ payment.paymentType.type }"/>
                                                                        <em class="fas fa-arrow-up ml-1"></em>
                                                                    </span>
                                                                </c:when>
                                                                <c:when test="${ payment.paymentType.type eq 'payment'}">
                                                                    <span class="badge badge-danger badge-pill pull-right">
                                                                        <c:out value="${ payment.paymentType.type }"/>
                                                                        <em class="fas fa-arrow-down ml-1"></em>
                                                                    </span>
                                                                </c:when>
                                                            </c:choose>
                                                        </td>
                                                        <td><c:out value="${ payment.date }"/></td>
                                                        <td>
                                                            <fmt:formatNumber type="number"
                                                                              maxFractionDigits="2"
                                                                              groupingUsed="false"
                                                                              value = "${ payment.amount }" />
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </c:if>
                                        <div class="btn-toolbar" role="toolbar">
                                            <div class="btn-group" role="group">
                                                <c:forEach var="page" begin="1" end="${pages}">
                                                    <a href="./?command=SHOW_PAYMENTS&page=${page}" type="button" class="btn btn-page
                                                           <c:if test="${page == currentPage}">btn-primary</c:if> ">
                                                            ${page}
                                                    </a>
                                                </c:forEach>
                                            </div>
                                        </div>
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
                                                        <fmt:message key="status"/>
                                                    </th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="subscription" items="${subscriptions}"
                                                           varStatus="status">
                                                    <c:if test="${subscription.value eq 'active'}">
                                                        <tr>
                                                    </c:if>
                                                    <c:if test="${subscription.value eq 'expired'}">
                                                        <tr class="table-danger">
                                                    </c:if>
                                                        <td><c:out value="${ subscription.key.id }"/></td>
                                                        <td><c:out value="${ subscription.key.edition.name }"/></td>
                                                        <td><c:out value="${ subscription.key.subscriptionStartDate }"/></td>
                                                        <td><c:out value="${ subscription.key.subscriptionEndDate }"/></td>
                                                        <td><c:out value="${ subscription.key.user.fullName }"/></td>
                                                        <td>
                                                            <fmt:message key="${ subscription.value }"/>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </c:if>
                                        <div class="btn-toolbar" role="toolbar">
                                            <div class="btn-group" role="group">
                                                <c:forEach var="page" begin="1" end="${pages}">
                                                    <a href="./?command=SHOW_SUBSCRIPTIONS&page=${page}" type="button" class="btn btn-page
                                                           <c:if test="${page == currentPage}">btn-primary</c:if> ">
                                                        ${page}
                                                    </a>
                                                </c:forEach>
                                            </div>
                                        </div>
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

        <!--Modal: edit editions -->
        <div class="modal fade" id="modalEditEdition" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
             aria-hidden="true">
            <div class="modal-dialog w-50" role="document" style="max-width: 1000px;">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editHeader"><fmt:message key="edit_edition"/></h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form class="mx-auto">
                            <input type="hidden" id="edition-id-label-edit" name="id"
                                   value="" readonly/>
                            <div class="form-group">
                                <label for="edition-name-label-edit"><fmt:message key="name"/></label>
                                <input type="text" class="form-control" id="edition-name-label-edit" name="name"
                                       value="">
                            </div>

                            <div class="form-group">
                                <label for="edition-type-label-edit"><fmt:message key="type"/></label>
                                <select class="browser-default custom-select" id="edition-type-label-edit">
                                    <c:forEach var="type" items="${editionsTypes}">
                                        <option value="${type.id}">${type.type}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="edition-theme-label-edit"><fmt:message key="theme"/></label>
                                <select class="browser-default custom-select" id="edition-theme-label-edit">
                                    <c:forEach var="theme" items="${editionsThemes}">
                                        <option value="${theme.id}">${theme.title}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="edition-periodicity-label-edit"><fmt:message key="periodicity"/></label>
                                <input type="text" class="form-control" id="edition-periodicity-label-edit"
                                       name="periodicity"
                                       value="">
                            </div>

                            <div class="form-group">
                                <label for="edition-min-period-label-edit"><fmt:message key="min_period"/></label>
                                <input type="text" class="form-control" id="edition-min-period-label-edit"
                                       name="min-period"
                                       value="">
                            </div>

                            <div class="form-group">
                                <label for="edition-price-label-edit"><fmt:message key="price"/></label>
                                <input type="text" class="form-control" id="edition-price-label-edit" name="price"
                                       value="">
                            </div>

                            <div id="edit-edition-message" class="alert alert-danger" role="alert"></div>
                        </form>
                        <button id="edit-edition-button" class="btn btn-primary"><fmt:message
                                key="edit"/></button>
                        <br>
                    </div>
                </div>
            </div>
        </div>
        <!--Modal: subscribe to editions -->

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
    <script type="text/javascript" src="${root}/js/addons/datatables.min.js"></script>
    <!-- DataTables Select CSS -->
    <link href="${root}/css/addons/datatables-select.min.css" rel="stylesheet">
    <!-- DataTables Select JS -->
    <script type="text/javascript" src="${root}/js/addons/datatables-select.min.js"></script>
    <!-- Toast -->
    <script type="text/javascript" src="${root}/js/toast.min.js"></script>

    <!-- Block user -->
    <script type="text/javascript">
        function blockUser(buttonElement) {
            $(buttonElement).prop('disabled', true);
            const data = {
                command: $(buttonElement).find('input[name="command"]').val(),
                id: $(buttonElement).find('input[name="id"]').val()
            };

            $.post('./', $.param(data), function (responseText) {
                const options = {
                    style: {
                        main: {
                            background: "pink",
                            color: "black"
                        }
                    },
                    settings: {
                        duration: 2000
                    }
                };
                if (responseText.length !== 0) {
                    iqwerty.toast.Toast(responseText, options);
                } else {
                    options.style.main.background = 'lightseagreen';
                    iqwerty.toast.Toast('OK', options);
                    $('#row' + data.id).addClass('table-danger');
                    $(buttonElement).prop('hidden', 'hidden');
                    $('#unblockUser' + data.id).prop('hidden', '');
                }
            });
            $(buttonElement).prop('disabled', false);
        }
    </script>

    <!-- Unblock user -->
    <script type="text/javascript">
        function unblockUser(buttonElement) {
            $(buttonElement).prop('disabled', true);
            const data = {
                command: $(buttonElement).find('input[name="command"]').val(),
                id: $(buttonElement).find('input[name="id"]').val()
            };

            $.post('./', $.param(data), function (responseText) {
                const options = {
                    style: {
                        main: {
                            background: "pink",
                            color: "black"
                        }
                    },
                    settings: {
                        duration: 2000
                    }
                };
                if (responseText.length !== 0) {
                    iqwerty.toast.Toast(responseText, options);
                } else {
                    options.style.main.background = 'lightseagreen';
                    iqwerty.toast.Toast('OK', options);
                    $('#row' + data.id).removeClass('table-danger');
                    $(buttonElement).prop('hidden', 'hidden');
                    $('#blockUser' + data.id).prop('hidden', '');
                }
            });
            $(buttonElement).prop('disabled', false);
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
                price_for_minimum_subscription_period: priceInput.val().replace(/,/g, '.')
            };

            const regexAny = RegExp(/^.{1,200}$/i);
            const regexInt = RegExp(/^[1-9]\d*$/i);
            const regexDouble = RegExp(/^[1-9]\d*(\.\d+)?$/i);
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

    <!-- edition block -->
    <script type="text/javascript">
        function blockEdition(buttonElement) {
            $(buttonElement).prop('disabled', true);
            const data = {
                command: $(buttonElement).find('input[name="command"]').val(),
                id: $(buttonElement).find('input[name="id"]').val()
            };

            $.post('./', $.param(data), function (responseText) {
                const options = {
                    style: {
                        main: {
                            background: "pink",
                            color: "black"
                        }
                    },
                    settings: {
                        duration: 2000
                    }
                };
                if (responseText.length !== 0) {
                    iqwerty.toast.Toast(responseText, options);
                } else {
                    options.style.main.background = 'lightseagreen';
                    iqwerty.toast.Toast('OK', options);
                    $('#row' + data.id).addClass('table-danger');
                    $(buttonElement).prop('hidden', 'hidden');
                    $('#unblockEdition' + data.id).prop('hidden', '');
                }
            });
            $(buttonElement).prop('disabled', false);
        }
    </script>

    <!-- edition unblock -->
    <script type="text/javascript">
        function unblockEdition(buttonElement) {
            $(buttonElement).prop('disabled', true);
            const data = {
                command: $(buttonElement).find('input[name="command"]').val(),
                id: $(buttonElement).find('input[name="id"]').val()
            };

            $.post('./', $.param(data), function (responseText) {
                const options = {
                    style: {
                        main: {
                            background: "pink",
                            color: "black"
                        }
                    },
                    settings: {
                        duration: 2000
                    }
                };
                if (responseText.length !== 0) {
                    iqwerty.toast.Toast(responseText, options);
                } else {
                    options.style.main.background = 'lightseagreen';
                    iqwerty.toast.Toast('OK', options);
                    $('#row' + data.id).removeClass('table-danger');
                    $(buttonElement).prop('hidden', 'hidden');
                    $('#blockEdition' + data.id).prop('hidden', '');
                }
            });
            $(buttonElement).prop('disabled', false);
        }
    </script>

    <!-- open modal window to edit edition -->
    <script type="text/javascript">
        function openModalWindowToEditEdition(buttonElement) {
            const modalWindow = $('#modalEditEdition');

            const idInput = $('#edition-id-label-edit');
            const nameInput = $('#edition-name-label-edit');
            const typeInput = $('#edition-type-label-edit');
            const themeInput = $('#edition-theme-label-edit');
            const periodicityInput = $('#edition-periodicity-label-edit');
            const minPeriodInput = $('#edition-min-period-label-edit');
            const priceInput = $('#edition-price-label-edit');

            const id = $(buttonElement).find('input[name="id"]').val();
            const name = $(buttonElement).find('input[name="name"]').val();
            const type = $(buttonElement).find('input[name="type"]').val();
            const theme = $(buttonElement).find('input[name="theme"]').val();
            const periodicity = $(buttonElement).find('input[name="periodicity"]').val();
            const minPeriod = $(buttonElement).find('input[name="minPeriod"]').val();
            const price = $(buttonElement).find('input[name="price"]').val();

            idInput.val(id);
            nameInput.val(name);
            typeInput.val(type);
            themeInput.val(theme);
            periodicityInput.val(periodicity);
            minPeriodInput.val(minPeriod);
            priceInput.val(price);

            modalWindow.modal('show');
        }
    </script>

    <script>
        $(document).on('click', '#edit-edition-button', function () {
            const buttonElementModal = $('#edit-edition-button');
            $(buttonElementModal).prop('disabled', true);

            const idInput = $('#edition-id-label-edit');
            const nameInput = $('#edition-name-label-edit');
            const typeInput = $('#edition-type-label-edit');
            const themeInput = $('#edition-theme-label-edit');
            const periodicityInput = $('#edition-periodicity-label-edit');
            const minPeriodInput = $('#edition-min-period-label-edit');
            const priceInput = $('#edition-price-label-edit');

            const data = {
                command: 'UPDATE_EDITION',
                id: idInput.val(),
                name: nameInput.val(),
                type_id: typeInput.val(),
                theme_id: themeInput.val(),
                periodicity_per_year: periodicityInput.val(),
                minimum_subscription_period: minPeriodInput.val(),
                price_for_minimum_subscription_period: priceInput.val().replace(/,/g, '.')
            };

            const regexAny = RegExp(/^.{1,200}$/i);
            const regexInt = RegExp(/^[1-9]\d*$/i);
            const regexDouble = RegExp(/^[1-9]\d*(\.\d+)?$/i);
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
                    if (responseText.length !== 0) {
                        $('#edit-edition-message').text(responseText);
                    } else {
                        const row = $('#row' + data.id);
                        row.find('td:eq(1)').text(data.name);
                        row.find('td:eq(2)').text(typeInput.children("option:selected").text());
                        row.find('td:eq(3)').text(themeInput.children("option:selected").text());

                        let buttonElement = ('#edition' + data.id);
                        $(buttonElement).find('input[name="name"]').val(data.name);
                        $(buttonElement).find('input[name="type"]').val(data.type_id);
                        $(buttonElement).find('input[name="theme"]').val(data.theme_id);
                        $(buttonElement).find('input[name="periodicity"]').val(data.periodicity_per_year);
                        $(buttonElement).find('input[name="minPeriod"]').val(data.minimum_subscription_period);
                        if ('<c:out value="${sessionScope.language}"/>' === 'ru') {
                            $(buttonElement).find('input[name="price"]').val(data.price_for_minimum_subscription_period.replace(/\./g, ','));
                        } else {
                            $(buttonElement).find('input[name="price"]').val(data.price_for_minimum_subscription_period);
                        }

                        $(buttonElementModal).prop('disabled', false);
                        $('#modalEditEdition').modal('hide');
                    }
                });
            }
        });
    </script>

    </body>

    </html>
</fmt:bundle>