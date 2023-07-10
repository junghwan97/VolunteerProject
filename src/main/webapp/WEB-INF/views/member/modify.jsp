<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>회원정보 수정</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>

<my:navbar current="mypage"></my:navbar>
  <div class="container-lg">
  <div class="row justify-content-center">
    <div class="col-12 col-md-8 col-lg-6">
      <h1>회원 정보 수정</h1>
      <form id="modifyForm" action="/member/modify" method="post">
        <table class="table">
          <tbody>
          <tr>
            <th scope="row">아이디</th>
            <td><input id="inputId" class="form-control" type="text" name="id" value="${member.id }" readonly /></td>
          </tr>
          <tr>
            <th scope="row">패스워드</th>
            <td><input id="inputPassword" class="form-control" type="text" name="password" value="" />
              <div class="form-text">입력하지 않으면 기존 패스워드를 유지합니다.</div></td>
          </tr>
          <tr>
            <th scope="row">패스워드 확인</th>
            <td><input id="inputPasswordCheck" class="form-control" type="text" value="" />
              <div id="passwordSuccessText" class="d-none form-text text-primary">
                <i class="fa-solid fa-check"></i> 패스워드가 일치 합니다.
              </div>
              <div id="passwordFailText" class="d-none form-text text-danger">
                <i class="fa-solid fa-triangle-exclamation"></i> 패스워드가 일치하지 않습니다.
              </div></td>
          </tr>
          <tr>
            <th scope="row">이름</th>
            <td>
              <div class="input-group">
                <input id="inputName" class="form-control" type="text" name="name" value="${member.name }" />
                <button class="btn btn-outline-secondary" type="button" id="checkNameBtn">확인</button>
              </div>
              <div class="d-none form-text text-primary" id="availableNameMessage">
                <i class="fa-solid fa-check"></i> 이름이 확인되었습니다.
              </div>
            </td>
          </tr>
          <tr>
            <th scope="row">성별</th>
            <td>${member.gender }</td>
          </tr>
          <tr>
            <th scope="row">이메일</th>
            <td>
              <div class="input-group">
                <input id="inputEmail" class="form-control" type="email" name="email" value="${member.email }" readonly />
              </div>
            </td>
          </tr>
          <tr>
            <th scope="row">핸드폰 번호</th>
            <td>
              <div class="input-group">
                <input id="inputPhoneNum" class="form-control" type="text" name="phoneNum" value="${member.phoneNum }" />
                <button class="btn btn-outline-secondary" type="button" id="checkPhoneNumBtn">중복확인</button>
              </div>
              <div class="d-none form-text text-primary" id="availablePhoneNumMessage">
                <i class="fa-solid fa-check"></i> 등록 가능한 핸드폰 번호입니다.
              </div>
              <div class="d-none form-text text-danger" id="notAvailablePhoneNumMessage">
                <i class="fa-solid fa-triangle-exclamation"></i> 이미 등록된 핸드폰 번호입니다.
              </div>
            </td>
          </tr>
          <tr>
            <th scope="row">별명</th>
            <td>
              <div class="input-group">
                <input id="inputNickName" class="form-control" type="text" name="nickName" value="${member.nickName }" />
                <button class="btn btn-outline-secondary" type="button" id="checkNickNameBtn">중복확인</button>
              </div>
              <div class="d-none form-text text-primary" id="availableNickNameMessage">
                <i class="fa-solid fa-check"></i> 사용 가능한 별명입니다.
              </div>
              <div class="d-none form-text text-danger" id="notAvailableNickNameMessage">
                <i class="fa-solid fa-triangle-exclamation"></i> 사용 불가능한 별명입니다.
              </div>
            </td>
          </tr>
          <tr>
            <th scope="row">주소</th>
            <td>
              <button type="button" class="btn btn-success" onClick="goPopup();">주소 검색</button>
              <div class="input-group">
                <input name="address" id="inputAddress" type="text" class="form-control" value="${member.address }" required readonly />
              </div>
              <div class="d-none form-text text-primary" id="availableAddressMessage">
                <i class="fa-solid fa-check"></i> 주소가 확인되었습니다.
              </div>
            </td>
          </tr>
          <sec:authorize access="hasAuthority('admin')">
            <tr>
              <th scope="row">권한</th>
              <td>
                <div class="mb-3">
                  <div class="btn-group" role="group" aria-label="Basic radio toggle button group">
                    <input type="radio" class="btn-check" name="authority" id="user" autocomplete="off" value="user" checked> <label class="btn btn-outline-success" for="user">사용자</label> <input type="radio" class="btn-check" name="authority" id="cheat" autocomplete="off" value="cheat"> <label class="btn btn-outline-success" for="cheat">사기 신고</label>
                  </div>
                </div>
              </td>
            </tr>
          </sec:authorize>
          </tbody>
        </table>
        <button id="modifyButton" type="button" data-bs-toggle="modal" data-bs-target="#confirmModal" class="btn btn-primary">수정</button>
      </form>

    </div>
  </div>
</div>

  <!-- 수정 확인 Modal -->
  <div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="exampleModalLabel">수정 확인</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <label for="inputOldPassword" class="form-label">이전 암호</label> <input form="modifyForm" id="inputOldPassword" class="form-control" type="password" name="oldPassword" />
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
        <button type="submit" form="modifyForm" class="btn btn-primary">확인</button>
      </div>
    </div>
  </div>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="/js/member/modify.js"></script>
</body>
</html>
