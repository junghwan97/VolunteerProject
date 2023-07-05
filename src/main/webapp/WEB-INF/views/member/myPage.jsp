<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Insert title here</title>
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
</style>
<body>

<my:navbar></my:navbar>


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
            <td><a href="/member/writeByMe?id=${member.id }" style="color: #55A44E">${member.id}가 쓴 글</a></td>
          </tr>

          </tbody>
        </sec:authorize>
        <sec:authorize access="isAuthenticated() and !hasAuthority('admin')">
          <tbody>
          <tr>
            <td><a href="/member/info?id=${member.id}" style="color: #55A44E">내 정보</a></td>
          </tr>
          <tr>
            <td><a href="/member/writeByMe?id=${member.id }" style="color: #55A44E">내가 쓴 글</a></td>
          </tr>



          </tbody>
        </sec:authorize>
      </table>
    </div>
    <div class="col-12 col-md-10 col-lg-8 detailPage">
      <h1>${member.id }님의 페이지</h1>
      <table class="table table-no-border">
        <tbody>
        <tr>
          <th>아이디</th>
          <td>${member.id}</td>
        </tr>
        <tr>
          <th>이름</th>
          <td>${member.name}</td>
        </tr>
        <tr>
          <th>성별</th>
          <td>${member.gender}</td>
        </tr>
        <tr>
          <th>이메일</th>
          <td>${member.email}</td>
        </tr>
        <tr>
          <th>핸드폰 번호</th>
          <td>${member.phoneNum}</td>
        </tr>
        <tr>
          <th>별명</th>
          <td>${member.nickName}</td>
        </tr>
        <tr>
          <th>주소</th>
          <td>${member.address}</td>
        </tr>
        <tr>
          <th>권한</th>
          <td>${member.authority}</td>
        </tr>
        </tbody>
      </table>

      <div>
        <sec:authorize access="hasAuthority('admin') or authentication.name eq #member.id">
          <a class="btn btn-secondary" href="/member/modify?id=${member.id}">수정</a>
        </sec:authorize>
        <sec:authorize access="authentication.name eq #member.id">
          <button type="button" data-bs-toggle="modal" class="btn btn-danger" data-bs-target="#confirmModal">탈퇴</button>
        </sec:authorize>
      </div>
    </div>
  </div>
</div>

<sec:authorize access="isAuthenticated()">
  <%--    <sec:authorize access="authentication.name eq #member.id or hasAuthority('admin')"> --%>
  <!-- 탈퇴 확인 Modal -->
  <div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="exampleModalLabel">탈퇴 확인</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <form id="removeForm" action="/member/remove" method="post">
            <input type="hidden" name="id" value="${member.id }" /> <label for="passwordInput1" class="form-label">암호</label> <input id="passwordInput1" type="password" name="password" class="form-control" />
          </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
          <button type="submit" form="removeForm" class="btn btn-danger">확인</button>
        </div>
      </div>
    </div>
  </div>
</sec:authorize>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</body>
</html>