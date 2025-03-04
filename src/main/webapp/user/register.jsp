<div class="row justify-content-md-center">
    <div class="col-sm-5">
        <h2 class="text-center">Welcome to MegaCity</h2>
        <h3>Register</h3>
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
                <label for="address" class="form-label">Textarea</label>
                <textarea class="form-control" id="address" name="address" required></textarea>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email address</label>
                <input type="email" class="form-control" name="email" id="email" required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="password">
            </div>

            <% if(request.getParameter("error") != null) { %>
            <% String errorString = (String) request.getParameter("error"); %>
            <div class="text-danger mb-2"><%= errorString%></div>
            <button type="submit" class="btn btn-danger w-100">Sign up</button>
            <% } else {%>
            <button type="submit" class="btn btn-primary w-100">Sign up</button>
            <% } %>
        </form>
        <a href="register" class="anchor-link text-primary w-100">Register</a>
    </div>
</div>