<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://example.com/functions" prefix="f" %>
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
    <c:set var="list" scope="page" value="${f:getHistory(voter)}"/>
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
              <th class="one">AFM</th>
              <th class="two">Name</th>
              <th>Surname</th>
              <th>Vote</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="c" items="${list}">
              <tr>
                <td class="one">
                    ${c.afm}
                </td>
                <td class="two">
                    ${c.name}
                </td>
                <td>
                    ${c.surname}
                </td>
                <td>
                  <p data-placement="top" data-toggle="tooltip" title="vote">
                    <button class="btn btn-${c.votes > 0 ? 'primary' : 'danger'}"
                            data-title="${c.votes > 0 ? 'yes' : 'no'}">
                      <span class="glyphicon glyphicon-thumbs-${c.votes > 0 ? 'up' : 'down'}"></span>
                    </button>
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

      <c:set var="error" scope="page" value="You haven't voted yet!"/>

      <%@include file="/reusables/error.jspf" %>

    </c:otherwise>
  </c:choose>

</div>

<%@include file="/reusables/footer.jspf" %>

</body>

</html>
