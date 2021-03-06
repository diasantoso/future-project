<!-- Navigation -->
<nav class="navbar navbar-default">
    <div class="container-fluid">
    <#--<div class="navbar-header">-->
    <#--<a class="navbar-brand" href="">Blibli.com</a>-->
    <#--</div>-->
        <ul class="nav navbar-nav">
            <#--<li>-->
                <#--<a href="/" class="logo"><img src="https://www.blibli.com/page/wp-content/uploads/logo-blibli.png" alt="logo karir" width="100" height="25"></a>-->
            <#--</li>-->
            <li class="active"><a href="/">Home</a></li>
            <li><a href="/listbooking">Upcoming Booking</a></li>
            <li><a href="/checkroom">Check Room</a></li>
            <li><a href="/checkticket">Check Ticket</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li><a href="/register"><span class="glyphicon glyphicon-user"></span> Register</a></li>
            <li><a href="/login"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
        </ul>
    </div>
</nav>

<div class="content-section-a">
    <div class="container">
        <h2 class="section-heading">Upcoming Booking</h2>
        <br/>
        <div class="content table-responsive table-full-width">
            <table class="table table-hover table-striped data">
                <thead>
                <tr>
                    <!--<th width="200">SUBJECT</th>
                    <th width="250">DESCRIPTION</th>
                    <th width="200">ADDED DATE</th>
                    <th width="200">STATUS</th>-->
                    <th width="180">MEETING DATE</th>
                    <th width="200">START</th>
                    <th width="200">END</th>
                    <th width="200">ROOM</th>
                    <th width="200">REQUEST BY</th>
                    <th width="200">CONTACT</th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="u in ctrlBooking.getUpcomingBookings()">
                    <!--<td>{{u.subject}}</td>
                    <td>{{u.description}}</td>
                    <td>{{u.addedDate}}</td>
                    <td>{{u.status}}</td>-->
                    <td  ng-if-start="u.status == 1">{{u.dateMeeting}}</td>
                    <td>{{u.startTime}}</td>
                    <td>{{u.endTime}}</td>
                    <td>{{u.room.name}}</td>
                    <td>{{u.employee.name}}</td>
                    <td  ng-if-end="u.status == 1">{{u.picContact}}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <!-- /.container -->

</div>

<!-- Footer -->
<footer>
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <p class="copyright text-muted small">Copyright &copy; Future Program Batch 1 - 2017. All Rights Reserved</p>
            </div>
        </div>
    </div>
</footer>