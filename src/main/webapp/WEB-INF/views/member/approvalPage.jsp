<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>마이페이지</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<style>
  h1 {
    font-size: 45px;
    color: #55A44E;
    margin: 20px;
  }

  a {
    text-decoration: none;
  }

  .table-no-border td, .table-no-border th {
    border: none;
  }

  .myTab {
    position: relative;
    top: 50px;
    right: 70px;
  }

  .detailPage {
    border: 1px solid #CED4DA;
    border-width: 1px 0 0;
    padding: 10px;
  }

  .detailPage th:first-child, .detailPage td:first-child {
    border-left: none;
  }

  .detailPage th:last-child, .detailPage td:last-child {
    border-right: none;
  }

  .detailPage th {
    border-bottom: 1px solid #CED4DA;
  }

  .detailPage td {
    border-bottom: 1px solid #CED4DA;
  }

  .mainBody{
    justify-content: center; /* 수평 정렬 */
    align-items: center; /* 수직 정렬 */
  }
</style>
<body>

<my:navbar current="mypage"></my:navbar>


<div class="container-lg">
  <div class="row justify-content-center">
    <div class="col-12 col-md-10 col-lg-3 myTab" style="position: relative; top: 70px; right: 50px">
      <table class="table table-no-border">

        <sec:authorize access="hasAuthority('admin')">
          <tbody>
          <tr>
            <td><a href="/member/myPage?id=${member.id}" style="color: #55A44E">${member.id} 정보</a></td>
          </tr>
          <tr>
            <td><a href="/member/sponsored?id=${member.id }" style="color: #55A44E">${member.id}가 쓴 글</a></td>
          </tr>
          <sec:authorize access="hasAuthority('volunteer')">
            <tr>
              <td><a href="/member/recruit?id=${member.id }" style="color: #55A44E">${member.id}가 지원한 봉사활동</a></td>
            </tr>
          </sec:authorize>
          <sec:authorize access="hasAuthority('needVolunteer')">
            <tr>
              <td><a href="/member/recruit?id=${member.id }" style="color: #55A44E">봉사활동 신청 승인</a></td>
            </tr>
          </sec:authorize>
          </tbody>
        </sec:authorize>

        <sec:authorize access="isAuthenticated() and !hasAuthority('admin')">
          <tbody>
          <tr>
            <td><a href="/member/myPage?id=${member.id}" style="color: #55A44E">내 정보</a></td>
          </tr>
          <tr>
            <td><a href="/member/sponsored?id=${member.id }" style="color: #55A44E">내가 후원한 캠페인</a></td>
          </tr>
          <sec:authorize access="hasAuthority('volunteer')">
            <tr>
              <td><a href="/member/applyRecruitPage?id=${member.id}" style="color: #55A44E">${member.id}가 지원한 봉사활동</a></td>
            </tr>
          </sec:authorize>
          <sec:authorize access="hasAuthority('needVolunteer')">
            <tr>
              <td><a href="/member/approvalPage?id=${member.id }" style="color: #55A44E">봉사활동 신청 승인</a></td>
            </tr>
          </sec:authorize>
          </tbody>
        </sec:authorize>
      </table>
    </div>
    <div class="col-12 col-md-10 col-lg-8 detailPage">
      <h1>${member.nickName }님의 페이지</h1>
      <table class="table table-no-border">
        <thead>
        <tr>
          <th>제목</th>
          <th>이름</th>
          <th>승인 여부</th>
        </tr>
        </thead>
        <tbody class="mainBody">
        <tr>
          <td><a href="/recruit/recruitId/${applyRecruit.recruitId}">${applyRecruit.title}</a></td>
          <td>${applyRecruit.name}</td>
          <c:if test="${not empty applyRecruit.participation}">
            <td><div class="btn btn-primary">${applyRecruit.participation}</div></td>
          </c:if>
          <c:if test="${empty applyRecruit.participation}">
            <td></td>
          </c:if>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</body>
</html>