<div class="row justify-content-md-center">
    <div class="col-sm-5">
        <h2 class="text-center">Welcome to MegaCity</h2>
        <hr>
        <h3 class="text-center">Sign Up</h3>
        <form method="post" action="register">
            <div class="mb-3">
                <label for="firstName" class="form-label">First Name</label>
                <input type="text" class="form-control" name="firstName" id="firstName" required>
            </div>
            <div class="mb-3">
                <label for="lastName" class="form-label">Last Name</label>
                <input type="text" class="form-control" name="lastName" id="lastName" required>
            </div>
            <div class="mb-3">
                <label for="username" class="form-label">Username</label>
                <input type="text" class="form-control" name="username" id="username" required>
            </div>
            <div class="mb-3">
                <label for="address" class="form-label">Address</label>
                <textarea class="form-control" id="address" name="address" required></textarea>
            </div>
            <div class="mb-3">
                <label for="phoneNumber" class="form-label">Phone Number</label>
                <input type="tel" class="form-control" name="phoneNumber" id="phoneNumber" required>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email address</label>
                <input type="email" class="form-control" name="email" id="email" required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="password" name="password" minlength="8">
            </div>

            <% if(request.getParameter("error") != null) { %>
            <% String errorString = (String) request.getParameter("error"); %>
            <div class="text-danger mb-2"><%= errorString%></div>
            <button type="submit" class="btn btn-danger w-100">Sign Up</button>
            <% } else {%>
            <button type="submit" class="btn btn-primary w-100">Sign Up</button>
            <% } %>
        </form>
        <a href="login" class="anchor-link text-primary w-100">login</a>
    </div>
</div>