<nav class="navbar navbar-expand-lg " style="background-color: #e3f2fd;">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">Soen 387 School App</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item p-1">
            <a class="nav-link" href="../webapp/StudentPage.jsp">Student Page</a>
            </li>
            <li class="nav-item p-1">
            <a class="nav-link" href="../webapp/AdminPage.jsp">Admin Page</a>
            </li>
        </ul>
        <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
            <li class="nav-item">
                <%
                if(session.getAttribute("name") != null) 
                {
                    out.print("Logged in as " + session.getAttribute("name"));
                }
                else
                {
                    response.sendRedirect("../webapp/MainPage.jsp");
                }
                 %>
                <a class="nav-link" aria-current="page" href="../webapp/logout.jsp">
                <i class="bi bi-power" width="100" height="100" style="color: red;"></i>Logout
                </a>
            </li>
        </ul>
    </div>
</div>
</nav>
