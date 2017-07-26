<!-- Navigation -->
<nav class="navbar navbar-default navbar-fixed-top topnav" role="navigation">
    <div class="container topnav">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="/">Home</a>
                </li>
                <li>
                    <a href="/listbooking">List Booking</a>
                </li>
                <li>
                    <a href="/addbooking">Add Booking</a>
                </li>
                <li>
                    <a href="/login">Login</a>
                </li>
                <li>
                    <a href="/register">Register</a>
                </li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>

<div class="content-section-a">
    <div class="container">
        <br/>
        <h2 class="section-heading">Available Rooms {{ctrlBooking.rooms}}</h2>
        <p class="category"><h5>You can see list of available rooms to book, based on your given data.</h5></p>
        <br/><br/>
        <div class="content table-responsive table-full-width">
            <table class="table table-hover table-striped data">
                <thead>
                <tr>
                    <th>No.</th>
                    <th>NAME</th>
                    <th>CAPACITY</th>
                    <th>HAVE CONFERENCE</th>
                    <th>HAVE PROJECTOR</th>
                    <th>NUMBER EXTENSION</th>
                    <th>OFFICE</th>
                    <th width="100"></th>
                    <th width="100"></th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="u in ctrlBooking.getAvailableRoom()">
                    <!--<td>{{u.idRoom}}</td>-->
                    <td>{{$index +1}}</td>
                    <td>{{u.name}}</td>
                    <td>{{u.capacity}}</td>
                    <td>{{u.isConference}}</td>
                    <td>{{u.isProjector}}</td>
                    <td>{{u.numberExtension}}</td>
                    <td>{{u.office.name}}</td>
                    <td><button type="button" ng-click="ctrlRoom.editRoom(u.idRoom)" data-toggle="modal" data-target="#myModalAdd" class="btn btn-success custom-width">Details</button></td>
                    <td><button type="button" ng-click="ctrlRoom.removeRoom(u.idRoom)" class="btn btn-danger custom-width">Book</button></td>
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