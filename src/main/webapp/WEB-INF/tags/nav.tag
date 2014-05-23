<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<li ng-class="{active: $state.includes('client')}"><a ui-sref="client.search"><i class="glyphicon glyphicon-group"></i> Clients</a></li>
<li ui-sref-active="active"><a ui-sref="schedule"><i class="glyphicon glyphicon-calendar"></i> Schedule</a></li>
<li ui-sref-active="active"><a ui-sref="booking"><i class="glyphicon glyphicon-book"></i> Booking</a></li>
<li ng-class="{active: $state.includes('views')}">
    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
        <i class="glyphicon glyphicon-eye"></i>
        Views
        <b class="caret"></b>
    </a>
    <ul class="dropdown-menu">
        <li><a>Distribution</a></li>
        <li><a>Confirmation</a></li>
        <li><a ui-sref="views.revenue">Revenue</a></li>
    </ul>
</li>
<li>
    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
        <i class="glyphicon glyphicon-list-alt"></i>
        Reporting
        <b class="caret"></b>
    </a>
    <ul class="dropdown-menu">
        <li><a>Standards Reports</a></li>
        <li><a>Call History</a></li>
        <li><a>Marketing</a></li>
    </ul>
</li>
<li ng-class="{active: $state.includes('admin')}">
    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
        <i class="glyphicon glyphicon-gears"></i>
        Admin
        <b class="caret"></b>
    </a>
    <ul class="dropdown-menu">
        <li><a ui-sref="admin.zones">Zones</a></li>
        <li><a ui-sref="admin.services">Services</a></li>
        <li><a ui-sref="admin.users">Users</a></li>
        <li><a ui-sref="admin.sources">Sources</a></li>
    </ul>
</li>