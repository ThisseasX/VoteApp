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
    <c:when test="${list ne null and list.size() gt 0}">

      <%@include file="../fragments/filter.jspf" %>

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
            <c:forEach var="v" items="${list}">
              <tr>
                <td class="one">
                    ${v.candidateByCandidateId.candidateId}
                </td>
                <td class="two">
                    ${v.candidateByCandidateId.candidateName}
                </td>
                <td>
                    ${v.candidateByCandidateId.candidateSurname}
                </td>
                <td>
                  <p data-placement="top" data-toggle="tooltip" title="vote">
                    <button class="btn btn-${v.vote > 0 ? 'primary' : 'danger'}"
                            data-title="${v.vote > 0 ? 'yes' : 'no'}">
                      <span class="glyphicon glyphicon-thumbs-${v.vote > 0 ? 'up' : 'down'}"></span>
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

      <%@include file="../fragments/error.jspf" %>

    </c:otherwise>
  </c:choose>

</div>

<%@include file="../fragments/footer.jspf" %>

</body>

</html>
