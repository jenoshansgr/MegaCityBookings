<div class="row justify-content-md-center">
    <div class="col-sm-5">
        <h3 class="text-center">Login</h3>
        <hr>
        <form method="post" action="login">
            <div class="mb-3">
                <label for="username" class="form-label">Username</label>
                <input type="text" class="form-control" name="username" id="username" required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="password" name="password">
            </div>
            <% if(request.getParameter("error") != null) { %>
                <% String errorString = (String) request.getParameter("error"); %>
                <div class="text-danger mb-2"><%= errorString%></div>
                <button type="submit" class="btn btn-danger w-100">Login</button>
            <% } else {%>
                <button type="submit" class="btn btn-primary w-100">Login</button>
            <% } %>
        </form>
        <hr>
        <a href="register" class="btn btn-secondary w-100 text-center">Sign up</a>
    </div>
</div>