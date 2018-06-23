<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://example.com/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: thiss
  Date: 13/2/2018
  Time: 9:39 μμ
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>

<head>
  <title>VoteApp - My Votes</title>
  <%@include file="/reusables/header.jspf" %>
</head>

<body>

<c:catch>
  <jsp:useBean id="voter" scope="session" type="models.Voter"/>
</c:catch>

<c:choose>
  <c:when test="${voter eq null}">
    <c:redirect url="/home.jsp"/>
  </c:when>
  <c:otherwise>
    <c:set var="list" scope="page" value="${f:getRankings()}"/>
  </c:otherwise>
</c:choose>

<%@include file="/reusables/navbar.jspf" %>

<div class="container-fluid bg">

  <%@include file="/reusables/jumbotron.jspf" %>

  <c:choose>
    <c:when test="${list.size() gt 0}">

      <%@include file="/reusables/filter.jspf" %>

      <div class="row">
        <div class="col-md-6 col-md-offset-3">

          <table id="myTable" class="table table-bordered table-striped table-hover">

            <thead>
            <tr>
              <th>Rank</th>
              <th class="one">AFM</th>
              <th class="two">Name</th>
              <th>Surname</th>
              <th>Vote</th>
            </tr>
            </thead>

            <tbody>

            <c:forEach var="i" begin="0" end="${list.size() - 1}">
              <tr>
                <td>
                    ${i + 1}
                </td>
                <td class="one">
                    ${list[i].afm}
                </td>
                <td class="two">
                    ${list[i].name}
                </td>
                <td>
                    ${list[i].surname}
                </td>
                <td>
                  <p data-placement="top" data-toggle="tooltip" title="vote">
                      ${list[i].votes}
                  </p>
                </td>
              </tr>
            </c:forEach>

            </tbody>
          </table>

        </div>
      </div>

    </c:when>
    <c:otherwise>

      <c:set var="error" scope="page" value="Nobody has been voted yet!"/>

      <%@include file="/reusables/error.jspf" %>

    </c:otherwise>
  </c:choose>

</div>

<%@include file="/reusables/footer.jspf" %>

</body>

</html>
