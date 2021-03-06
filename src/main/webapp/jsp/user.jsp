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
                <a class="navbar-brand waves-effect" href="./?command=USER_PAGE" style="font-size: 1.0rem">
                    <strong class="blue-text"><fmt:message key="periodicals"/></strong>
                    <span>/</span>
                    <span><fmt:message key="my_account"/></span>
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
                <a href="./?command=SHOW_EDITION_SEARCH_FORM"
                   class="list-group-item list-group-item-action waves-effect">
                    <em class="fas fa-book mr-3"></em><fmt:message key="editions_search"/></a>
                <a href="./?command=SHOW_USER_PAYMENTS" class="list-group-item list-group-item-action waves-effect">
                    <em class="fas fa-money-bill-alt mr-3"></em><fmt:message key="payments"/></a>
                <a href="./?command=SHOW_USER_SUBSCRIPTIONS"
                   class="list-group-item list-group-item-action waves-effect">
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
                                <c:when test="${empty userPageOption or userPageOption.toString() == 'MAIN'}">
                                    <c:if test="${not empty message}">
                                        <div id="users_div" class="alert alert-danger" role="alert">${message}</div>
                                    </c:if>
                                    <div class="list-group list-group-flush">
                                        <h4><fmt:message key="welcome"/> ${sessionScope.fullName}!</h4>
                                    </div>
                                    <!-- List group links -->
                                    <div class="list-group list-group-flush">
                                        <div class="list-group-item">
                                            <fmt:message key="subscriptions"/>
                                            <span class="badge badge-warning badge-pill pull-right">
                                                ${subscriptionsCount}
                                                <em class="fas fa-check-square ml-1"></em>
                                            </span>
                                        </div>
                                        <div class="list-group-item">
                                            <fmt:message key="balance"/>
                                            <span class="badge badge-primary badge-pill pull-right">
                                                <div class="row mx-0">
                                                    <div class="col-sm px-0" id="userBalance">
                                                        <fmt:formatNumber type="number"
                                                                          maxFractionDigits="2"
                                                                          groupingUsed="false"
                                                                          value = "${userBalance}" />
                                                    </div>
                                                    <div class="col-sm px-0">
                                                        <em class="fas fa-money-bill ml-1"></em>
                                                    </div>
                                                </div>
                                            </span>
                                        </div>
                                        <div class="list-group-item">
                                            <button type="button" class="btn btn-outline-primary waves-effect"
                                                    id="refillBalance"
                                                    data-toggle="modal" data-target="#modalRefill">
                                                <fmt:message key="refill_balance"/>
                                            </button>
                                        </div>
                                    </div>
                                    <!-- List group links -->
                                </c:when>
                                <c:when test="${userPageOption.toString() == 'EDITIONS'}">
                                    <div id="editions_div" class="alert alert-danger" role="alert"></div>
                                    <div class="d-flex">
                                        <div class="p-0">
                                            <h4><fmt:message key="editions_search"/></h4>
                                        </div>
                                    </div>

                                    <div class="d-flex">
                                        <div class="p-0 pt-3">
                                            <label for="edition-name-label"><fmt:message key="name"/></label>
                                            <input type="text" class="form-control" id="edition-name-label" name="name"
                                                   placeholder="<fmt:message key="enter_name"/>"
                                                   value="${editionNameValue}">
                                        </div>
                                    </div>
                                    <div class="d-flex">
                                        <div class="p-0 pt-3">
                                            <label for="edition-type-label"><fmt:message key="type"/></label>
                                            <select class="browser-default custom-select" id="edition-type-label">
                                                <option value="">
                                                    <fmt:message key="any_type"/>
                                                </option>
                                                <c:forEach var="type" items="${editionsTypes}">
                                                    <option value="${type.id}">${type.type}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="d-flex">
                                        <div class="p-0 pt-3">
                                            <label for="edition-theme-label"><fmt:message key="theme"/></label>
                                            <select class="browser-default custom-select" id="edition-theme-label">
                                                <option value="" selected>
                                                    <fmt:message key="any_theme"/>
                                                </option>
                                                <c:forEach var="theme" items="${editionsThemes}">
                                                    <option value="${theme.id}">${theme.title}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="d-flex">
                                        <div class="p-0 pt-3">
                                            <form method="get" action="./">
                                                <input hidden type="text" value="FIND_EDITIONS" name="command"/>
                                                <input id="editionName" hidden type="text" value="" name="name"/>
                                                <input id="editionType" hidden type="text" value="" name="type_id"/>
                                                <input id="editionTheme" hidden type="text" value="" name="theme_id"/>
                                                <button onclick="onFindEditionsClick()" type="submit" class="btn btn-outline-primary waves-effect"
                                                        id="findEditions">
                                                    <fmt:message key="find_editions"/>
                                                </button>
                                            </form>

                                        </div>
                                    </div>
                                    <div class="d-flex">
                                        <div class="p-0 pt-3">
                                            <div id="find-editions-message" class="alert alert-danger"
                                                 role="alert"></div>
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
                                                        <fmt:message key="name"/>
                                                    </th>
                                                    <th class="th-sm">
                                                        <fmt:message key="periodicity"/>
                                                    </th>
                                                    <th class="th-sm">
                                                        <fmt:message key="min_period_reduced"/>
                                                    </th>
                                                    <th class="th-sm">
                                                        <fmt:message key="price_reduced"/>
                                                    </th>
                                                    <th class="th-sm">
                                                        <fmt:message key="subscribe"/>
                                                    </th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="edition" items="${editions}" varStatus="status">
                                                    <tr id="row${edition.key.id}">
                                                        <td><c:out value="${ edition.key.name }"/></td>
                                                        <td><c:out value="${ edition.key.periodicityPerYear }"/></td>
                                                        <td><c:out value="${ edition.key.minimumSubscriptionPeriodInMonths }"/></td>
                                                        <td>
                                                            <fmt:formatNumber type="number"
                                                                              groupingUsed="false"
                                                                              maxFractionDigits="2"
                                                                              value = "${ edition.key.priceForMinimumSubscriptionPeriod }" />
                                                        </td>
                                                        <td>
                                                            <button class="btn btn-outline-danger waves-effect py-0 px-1 m-0 disabled"
                                                                    type="button"
                                                                    id="subscribed${edition.key.id}"
                                                                    <c:if test="${edition.value eq false}">
                                                                        hidden="hidden"
                                                                    </c:if>
                                                                >
                                                                <fmt:message key="subscribed"/>
                                                            </button>
                                                            <button class="btn btn-outline-success waves-effect py-0 px-1 m-0"
                                                                    type="button"
                                                                    id="subscribe${edition.key.id}"
                                                                    onclick="openModalWindowToSubscribeEdition(this)"
                                                                    <c:if test="${edition.value eq true}">
                                                                        hidden="hidden"
                                                                    </c:if>
                                                                >
                                                                <fmt:message key="subscribe"/>
                                                                <input type="hidden" name="id"
                                                                       value="${edition.key.id}"/>
                                                                <input type="hidden" name="name"
                                                                       value="<c:out value="${ edition.key.name }"/>"/>
                                                                <input type="hidden" name="type"
                                                                       value="<c:out value="${ edition.key.editionType.type }"/>"/>
                                                                <input type="hidden" name="theme"
                                                                       value="<c:out value="${ edition.key.editionTheme.title }"/>"/>
                                                                <input type="hidden" name="periodicity"
                                                                       value="${edition.key.periodicityPerYear}"/>
                                                                <input type="hidden" name="minPeriod"
                                                                       value="${edition.key.minimumSubscriptionPeriodInMonths}"/>
                                                                <input type="hidden" name="price"
                                                                       value="<fmt:formatNumber type="number"
                                                                            maxFractionDigits="2"
                                                                            groupingUsed="false"
                                                                            value = "${ edition.key.priceForMinimumSubscriptionPeriod }"/>"/>
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
                                                    <form method="get" action="./">
                                                        <input hidden type="text" value="FIND_EDITIONS" name="command"/>
                                                        <input hidden type="text" value="${page}" name="page"/>
                                                        <input id="editionNameOnPage${page}" hidden type="text" value="" name="name"/>
                                                        <input id="editionTypeOnPage${page}" hidden type="text" value="" name="type_id"/>
                                                        <input id="editionThemeOnPage${page}" hidden type="text" value="" name="theme_id"/>
                                                        <button onclick="onEditionsPageClick(${page})" type="submit" type="button" class="btn btn-page
                                                           <c:if test="${page == currentPage}">btn-primary</c:if> ">
                                                                ${page}
                                                        </button>
                                                    </form>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                </c:when>
                                <c:when test="${userPageOption.toString() == 'PAYMENTS'}">
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
                                                        <fmt:message key="type"/>
                                                    </th>
                                                    <th class="th-sm">
                                                        <fmt:message key="date"/>
                                                    </th>
                                                    <th class="th-sm">
                                                        <fmt:message key="amount"/>
                                                    </th>
                                                    <th class="th-sm">
                                                        <fmt:message key="purpose"/>
                                                    </th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="payment" items="${payments}" varStatus="status">
                                                    <tr>
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
                                                                              value = "${payment.amount}" />
                                                        </td>
                                                        <td>
                                                            <c:if test="${not empty payment.subscription}">
                                                                <c:out value="${ payment.subscription.edition.name }"/>
                                                            </c:if>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </c:if>
                                        <div class="btn-toolbar" role="toolbar">
                                            <div class="btn-group" role="group">
                                                <c:forEach var="page" begin="1" end="${pages}">
                                                    <a href="./?command=SHOW_USER_PAYMENTS&page=${page}" type="button" class="btn btn-page
                                                           <c:if test="${page == currentPage}">btn-primary</c:if> ">
                                                            ${page}
                                                    </a>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                </c:when>
                                <c:when test="${userPageOption.toString() == 'SUBSCRIPTIONS'}">
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
                                                        <fmt:message key="edition"/>
                                                    </th>
                                                    <th class="th-sm">
                                                        <fmt:message key="subscription_start_date"/>
                                                    </th>
                                                    <th class="th-sm">
                                                        <fmt:message key="subscription_end_date"/>
                                                    </th>
                                                    <th class="th-sm">
                                                        <fmt:message key="status"/>
                                                    </th>
                                                    <th class="th-sm">
                                                        <fmt:message key="unsubscribe"/>
                                                    </th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="subscription" items="${subscriptions}" varStatus="status">
                                                    <c:if test="${subscription.value eq 'active'}">
                                                        <tr id="rowSub${subscription.key.id}">
                                                    </c:if>
                                                    <c:if test="${subscription.value eq 'expired'}">
                                                        <tr id="rowSub${subscription.key.id}" class="table-danger">
                                                    </c:if>
                                                        <td><c:out value="${ subscription.key.edition.name }"/></td>
                                                        <td><c:out value="${ subscription.key.subscriptionStartDate }"/></td>
                                                        <td><c:out value="${ subscription.key.subscriptionEndDate }"/></td>
                                                        <td>
                                                            <fmt:message key="${ subscription.value }"/>
                                                        </td>
                                                        <td>
                                                            <c:if test="${subscription.value eq 'active'}">

                                                                    <button class="btn btn-outline-danger waves-effect py-0 px-1 m-0"
                                                                            type="button" id="stopSubscription${subscription.key.id}"
                                                                            onclick="stopSubscription(this)"
                                                                    >
                                                                        <input type="hidden" name="id" value="${subscription.key.id}"/>
                                                                        <input type="hidden" name="command"
                                                                               value="STOP_SUBSCRIPTION"/>
                                                                        <fmt:message key="unsubscribe"/>
                                                                    </button>

                                                            </c:if>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </c:if>
                                        <div class="btn-toolbar" role="toolbar">
                                            <div class="btn-group" role="group">
                                                <c:forEach var="page" begin="1" end="${pages}">
                                                    <a href="./?command=SHOW_USER_SUBSCRIPTIONS&page=${page}" type="button" class="btn btn-page
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
        <!--Modal: subscribe to editions -->
        <div class="modal fade" id="modalSubscribe" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
             aria-hidden="true">
            <div class="modal-dialog w-50" role="document" style="max-width: 1000px;">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="subscribeHeader"><fmt:message key="new_subscription"/></h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form class="mx-auto">
                            <input type="hidden" id="edition-id-label-sub" name="id"
                                   value="" readonly/>
                            <div class="form-group">
                                <label for="edition-name-label-sub"><fmt:message key="name"/></label>
                                <input type="text" class="form-control" id="edition-name-label-sub" name="name"
                                       value="" readonly>
                            </div>

                            <div class="form-group">
                                <label for="edition-type-label-sub"><fmt:message key="type"/></label>
                                <input type="text" class="form-control" id="edition-type-label-sub" name="type"
                                       value="" readonly>
                            </div>

                            <div class="form-group">
                                <label for="edition-theme-label-sub"><fmt:message key="theme"/></label>
                                <input type="text" class="form-control" id="edition-theme-label-sub" name="theme"
                                       value="" readonly>
                            </div>

                            <div class="form-group">
                                <label for="edition-periodicity-label-sub"><fmt:message key="periodicity"/></label>
                                <input type="text" class="form-control" id="edition-periodicity-label-sub"
                                       name="periodicity"
                                       value="" readonly>
                            </div>

                            <div class="form-group">
                                <label for="edition-min-period-label-sub"><fmt:message key="min_period"/></label>
                                <input type="text" class="form-control" id="edition-min-period-label-sub"
                                       name="min-period"
                                       value="" readonly>
                            </div>

                            <div class="form-group">
                                <label for="edition-price-label-sub"><fmt:message key="price"/></label>
                                <input type="text" class="form-control" id="edition-price-label-sub" name="price"
                                       value="" readonly>
                            </div>

                            <div class="form-group">
                                <label for="edition-period-label-sub"><fmt:message key="period"/></label>
                                <input type="number" class="form-control" id="edition-period-label-sub" name="period"
                                       placeholder="<fmt:message key="enter_subscription_period"/>" min="1" max="12"
                                       oninput="onPeriodChange()">
                            </div>

                            <div class="form-group">
                                <label for="edition-final-price-label-sub"><fmt:message key="final_price"/></label>
                                <input type="text" class="form-control" id="edition-final-price-label-sub"
                                       name="finalPrice"
                                       value="" readonly>
                            </div>

                            <div id="add-new-subscription-message" class="alert alert-danger" role="alert"></div>

                        </form>
                        <button id="add-new-subscription-button" class="btn btn-primary"><fmt:message
                                key="subscribe"/></button>
                        <br>
                    </div>
                </div>
            </div>
        </div>
        <!--Modal: subscribe to editions -->

        <!--Modal: refill balance -->
        <div class="modal fade" id="modalRefill" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
             aria-hidden="true">
            <div class="modal-dialog w-50" role="document" style="max-width: 1000px;">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="refillHeader"><fmt:message key="refill_balance"/></h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form class="mx-auto">
                            <div class="form-group">
                                <label for="balance-increase-label"><fmt:message key="balance_increase_amount"/></label>
                                <input type="number" class="form-control" id="balance-increase-label" name="name"
                                       value="" step="0.01" min="0.01" max="99999999.99">
                            </div>
                            <div id="refill-balance-message" class="alert alert-danger" role="alert"></div>
                        </form>
                        <button id="refill-balance-button" class="btn btn-primary"><fmt:message
                                key="refill_balance"/></button>
                        <br>
                    </div>
                </div>
            </div>
        </div>
        <!--Modal: refill balance -->
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
    <!-- Toast -->
    <script type="text/javascript" src="${root}/js/toast.min.js"></script>

    <!-- select inputs setting -->
    <script type="text/javascript">
        $(document).ready(function () {
            const nameInput = $('#edition-name-label');
            const typeInput = $('#edition-type-label');
            const themeInput = $('#edition-theme-label');

            nameInput.val('${editionNameValue}');
            typeInput.val('${editionTypeIdValue}');
            themeInput.val('${editionThemeIdValue}');
        })
    </script>

    <!-- open modal window to subscribe edition -->
    <script type="text/javascript">
        function openModalWindowToSubscribeEdition(buttonElement) {
            const modalWindow = $('#modalSubscribe');

            const idInput = $('#edition-id-label-sub');
            const nameInput = $('#edition-name-label-sub');
            const typeInput = $('#edition-type-label-sub');
            const themeInput = $('#edition-theme-label-sub');
            const periodicityInput = $('#edition-periodicity-label-sub');
            const minPeriodInput = $('#edition-min-period-label-sub');
            const priceInput = $('#edition-price-label-sub');
            const periodInput = $('#edition-period-label-sub');
            const finalPriceInput = $('#edition-final-price-label-sub');

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
            periodInput.val(minPeriod);
            finalPriceInput.val(price);

            periodInput.attr("min", minPeriod);

            modalWindow.modal('show');
        }
    </script>

    <!-- new subscription button -->
    <script>
        $(document).on('click', '#add-new-subscription-button', function () {

            const idInput = $('#edition-id-label-sub');
            const periodInput = $('#edition-period-label-sub');

            const data = {
                command: 'ADD_SUBSCRIPTION',
                id: idInput.val(),
                period: periodInput.val(),
                amount: $('#finalPriceInput').val()
            };

            const regexInt = RegExp(/^[1-9]\d*$/i);
            const regexPeriod = RegExp(/^\d{1,2}$/i);
            let isValid = true;

            if (!regexInt.test(data.id)) {
                idInput.css('border-color', 'red');
                isValid = false;
            } else {
                idInput.css('border-color', '');
            }

            if (!regexPeriod.test(data.period)) {
                periodInput.css('border-color', 'red');
                isValid = false;
            } else {
                periodInput.css('border-color', '');
            }

            if (isValid) {
                $.post('./', $.param(data), function (responseText) {
                    if (responseText.length !== 0) {
                        $('#add-new-subscription-message').text(responseText);
                    } else {
                        let subscribeButtonElement = $('#subscribe' + data.id);
                        let subscribedButtonElement = $('#subscribed' + data.id);
                        $(subscribeButtonElement).prop('hidden', 'hidden');
                        $(subscribedButtonElement).prop('hidden', '');
                        $('#modalSubscribe').modal('hide');
                    }
                });
            }
        });
    </script>

    <!-- on period change event -->
    <script type="text/javascript">
        function onPeriodChange() {
            const minPeriodInput = $('#edition-min-period-label-sub');
            const priceInput = $('#edition-price-label-sub');
            const periodInput = $('#edition-period-label-sub');
            const finalPriceInput = $('#edition-final-price-label-sub');

            let price = periodInput.val() * priceInput.val().replace(/,/g, '.') / minPeriodInput.val();
            price = Math.round((price + Number.EPSILON) * 100) / 100;
            finalPriceInput.val(price);
            if ('<c:out value="${sessionScope.language}"/>' === 'ru') {
                finalPriceInput.val(finalPriceInput.val().replace(/\./g, ','));
            }
        }
    </script>

    <!-- on editions page click -->
    <script type="text/javascript">
        function onEditionsPageClick(page) {
            const nameInputOnPage = $('#editionNameOnPage' + page);
            const typeInputOnPage = $('#editionTypeOnPage' + page);
            const themeInputOnPage = $('#editionThemeOnPage' + page);

            const nameInput = $('#edition-name-label');
            const typeInput = $('#edition-type-label');
            const themeInput = $('#edition-theme-label');

            nameInputOnPage.val(nameInput.val());
            typeInputOnPage.val(typeInput.val());
            themeInputOnPage.val(themeInput.val());
        }
    </script>

    <!-- on find editions click -->
    <script type="text/javascript">
        function onFindEditionsClick(page) {
            const nameInputFind = $('#editionName');
            const typeInputFind = $('#editionType');
            const themeInputFind = $('#editionTheme');

            const nameInput = $('#edition-name-label');
            const typeInput = $('#edition-type-label');
            const themeInput = $('#edition-theme-label');

            nameInputFind.val(nameInput.val());
            typeInputFind.val(typeInput.val());
            themeInputFind.val(themeInput.val());
        }
    </script>

    <!-- refill balance button -->
    <script type="text/javascript">
        $(document).on('click', '#refill-balance-button', function () {
            const modalWindow = $('#modalRefill');
            const userBalance = $('#userBalance');

            const buttonElement = $('#refill-balance-button');
            $(buttonElement).prop('disabled', true);

            const amountInput = $('#balance-increase-label');

            const data = {
                command: 'REFILL_USER_BALANCE',
                amount: amountInput.val().replace(/,/g, '.'),
            };

            const regexDouble = RegExp(/^[1-9]\d{0,8}(\.\d{1,2})?$/i);
            let isValid = true;

            if (!regexDouble.test(data.amount)) {
                amountInput.css('border-color', 'red');
                isValid = false;
            } else {
                amountInput.css('border-color', '');
            }

            if (isValid) {
                $.post('./', $.param(data), function (responseText) {
                    if (responseText.length !== 0) {
                        $('#refill-balance-message').text(responseText);
                    } else {
                        if ('<c:out value="${sessionScope.language}"/>' === 'ru') {
                            userBalance.text(+(userBalance.text().replace(/,/g, '.')) + +(data.amount));
                            userBalance.text(userBalance.text().replace(/\./g, ','));
                        } else {
                            userBalance.text(+(userBalance.text()) + +(data.amount));
                        }
                        amountInput.val('0');
                        modalWindow.modal('hide');
                    }
                });
            }
            $(buttonElement).prop('disabled', false);
        });
    </script>

    <!-- stop subscription button -->
    <script type="text/javascript">
        function stopSubscription(buttonElement) {
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
                    iqwerty.toast.Toast('<fmt:message key="unsubscribed"/>', options);
                    const row = $('#rowSub' + data.id);
                    row.addClass('table-danger');
                    row.find('td:eq(2)').text('');
                    row.find('td:eq(3)').text('<fmt:message key="expired"/>');
                    $('#stopSubscription' + data.id).prop('hidden', 'hidden');
                }
            });
            $(buttonElement).prop('disabled', false);
        }
    </script>

    </body>

    </html>
</fmt:bundle>