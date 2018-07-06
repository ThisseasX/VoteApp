<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
  <%@include file="../fragments/header.jspf" %>
</head>

<body>

<%@include file="../fragments/navbar.jspf" %>

<div class="container-fluid bg">

  <%@include file="../fragments/jumbotron.jspf" %>

  <c:choose>
    <c:when test="${list.size() gt 0}">

      <%@include file="../fragments/filter.jspf" %>

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

            <c:forEach items="${list}" var="c" varStatus="s">
              <tr>
                <td>
                    ${s.index + 1}
                </td>
                <td class="one">
                    ${c.candidateId}
                </td>
                <td class="two">
                    ${c.candidateName}
                </td>
                <td>
                    ${c.candidateSurname}
                </td>
                <td>
                  <p data-placement="top" data-toggle="tooltip" title="vote">
                      ${c.totalVotes}
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

      <%@include file="../fragments/error.jspf" %>

    </c:otherwise>
  </c:choose>

</div>

<%@include file="../fragments/footer.jspf" %>

</body>

</html>
