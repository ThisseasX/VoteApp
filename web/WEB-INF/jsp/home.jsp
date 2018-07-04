<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: thiss
  Date: 13/2/2018
  Time: 3:51 πμ
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>

<head>
  <title>VoteApp - Login</title>
  <%@include file="../fragments/header.jspf" %>
</head>

<body>

<%@include file="../fragments/navbar.jspf" %>

<div class="container-fluid bg">

  <%@include file="../fragments/jumbotron.jspf" %>

  <%@include file="../fragments/form.jspf" %>

  <%@include file="../fragments/error.jspf" %>

</div>

<%@include file="../fragments/footer.jspf" %>

<script>

    (function validatePassword() {
        let password = document.getElementById("register-password");
        let confirm_password = document.getElementById("confirm-password");

        function validatePassword() {
            if (password.value !== confirm_password.value) {
                confirm_password.setCustomValidity("Passwords don't match");
            } else {
                confirm_password.setCustomValidity('');
            }
        }

        password.onchange = validatePassword;
        confirm_password.onkeyup = validatePassword;

        $('input').keypress(function (e) {
            if (e.which === 32)
                return false;
        });
    })();

</script>

</body>

</html>