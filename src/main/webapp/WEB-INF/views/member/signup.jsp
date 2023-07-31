<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>회원 가입</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<style>
  /** {*/
  /*  padding: 0;*/
  /*  margin: 0;*/
  /*  border: none;*/
  /*}*/

  /*body {*/
  /*  font-size: 14px;*/
  /*  font-family: 'Roboto', sans-serif;*/
  /*}*/

  /*h1 {*/
  /*  font-size: 60px;*/
  /*  color: #55A44E;*/
  /*  margin-bottom: 20px;*/
  /*  !* 	font-weight: bold; *!*/
  /*}*/

  /*#checkIdBtn, #checkNameBtn, #checkEmailBtn, #verifyEmailBtn,*/
  /*#checkPhoneNumBtn, #checkNicknameBtn {*/
  /*  color: #fff;*/
  /*  font-size: 16px;*/
  /*  background-color: #55A44E;*/
  /*}*/

  /*.btn-group label.btn {*/
  /*  color: #000000;*/
  /*  background-color: transparent;*/
  /*  border-color: #55A44E;*/
  /*}*/

  /*.btn-group input:checked+label.btn {*/
  /*  background-color: #55A44E;*/
  /*}*/

  /*.container-lg {*/
  /*  margin-top: 50px;*/
  /*}*/

  p{
    color: red;
  }
</style>
<body>
<my:navbar current="signup"></my:navbar>

<div class="container-lg">
  <div class="row justify-content-center">
    <div class="col-12 col-md-8 col-lg-8">
      <h1>회원 가입</h1>
      <form method="post" id="signup-form" enctype="multipart/form-data">
        <div class="mb-3">
          <div class="input-group">
            <input id="inputId" type="text" class="form-control" name="id" value="${member.id }" placeholder="ID" />
            <button class="btn btn-outline-primary" type="button" id="checkIdBtn">중복확인</button>
          </div>

          <div class="d-none form-text text-primary" id="availableIdMessage">
            <i class="fa-solid fa-check"></i>사용 가능한 ID입니다.
          </div>
          <div class="d-none form-text text-danger" id="notAvailableIdMessage">
            <i class="fa-solid fa-triangle-exclamation"></i>사용 불가능한 ID입니다.
          </div>
        </div>

        <div class="mb-3">
          <input id="inputPassword" type="password" class="form-control" name="password" placeholder="Password" />
        </div>

        <div class="mb-3">
          <input id="inputPasswordCheck" type="password" class="form-control" placeholder="Password 확인" />

          <div id="passwordSuccessText" class="d-none form-text text-primary">
            <i class="fa-solid fa-check"></i> 패스워드가 일치 합니다.
          </div>

          <div id="passwordFailText" class="d-none form-text text-danger">
            <i class="fa-solid fa-triangle-exclamation"></i> 패스워드가 일치하지 않습니다.
          </div>
        </div>

        <div class="mb-3">
          <div class="input-group">
            <input id="inputName" type="text" class="form-control" name="name" value="${member.name }" placeholder="이름" />
            <button class="btn btn-outline-primary" type="button" id="checkNameBtn">확인</button>
          </div>
          <div class="d-none form-text text-primary" id="availableNameMessage">
            <i class="fa-solid fa-check"></i> 이름이 확인되었습니다.
          </div>
        </div>

        <div class="mb-3">
          <div class="btn-group" role="group" aria-label="Basic radio toggle button group">
            <input type="radio" class="btn-check" name="gender" id="inputGenderM" autocomplete="off" value="남"> <label class="btn btn-outline-primary" for="inputGenderM">남</label> <input type="radio" class="btn-check" name="gender" id="inputGenderW" autocomplete="off" value="여"> <label class="btn btn-outline-primary" for="inputGenderW">여</label> <input type="radio" class="btn-check" name="gender" id="inputGenderX" autocomplete="off" value="선택 안함"> <label class="btn btn-outline-primary" for="inputGenderX">선택 안함</label>
          </div>
        </div>

        <div class="mb-3">
          <div class="input-group">
            <input id="inputEmail" type="email" class="form-control" name="email" value="${member.email }" placeholder="E-mail" />
            <button class="btn btn-outline-primary" type="button" id="checkEmailBtn">인증하기</button>
          </div>
          <div class="d-none form-text text-primary" id="availableEmailMessage">
            <i class="fa-solid fa-check"></i>사용 가능한 메일입니다.
          </div>
          <div class="d-none form-text text-danger" id="notAvailableEmailMessage">
            <i class="fa-solid fa-triangle-exclamation"></i>사용 불가능한 메일입니다.
          </div>
        </div>

        <!-- 인증번호 입력 칸 -->
        <div class="mb-3 d-none" id="inputVerificationCode">
          <div class="input-group">
            <input type="text" class="form-control" id="verificationCode" name="verificationCode" placeholder="인증번호를 입력하세요" />
            <button class="btn btn-outline-primary" type="button" id="verifyEmailBtn" style="display: none;">확인</button>
          </div>
        </div>

        <!-- 인증하기 버튼 -->

        <!-- 인증 완료 메시지 -->
        <div class="d-none form-text text-primary" id="verificationSuccessText">
          <i class="fa-solid fa-check"></i>이메일 인증이 완료되었습니다.
        </div>

        <div class="mb-3">
          <div class="input-group">
            <input id="inputPhoneNum" type="tell" class="form-control" name="phoneNum" value="${member.phoneNum }" title="전화번호를 입력하세요." placeholder="핸드폰 번호" />
            <%-- 							<input id="inputPhoneNum" type="tell" class="form-control" name="phoneNum" value="${member.phoneNum }" title="전화번호를 입력하세요." placeholder="010-0000-0000" pattern="[0-9]{2,3}-[0-9]{3,4}-[0-9]{3,4}" maxlength="13" /> --%>
            <button class="btn btn-outline-primary" type="button" id="checkPhoneNumBtn">중복확인</button>
          </div>
          <div class="d-none form-text text-primary" id="availablePhoneNumMessage">
            <i class="fa-solid fa-check"></i>등록 가능한 핸드폰 번호입니다.
          </div>
          <div class="d-none form-text text-danger" id="notAvailablePhoneNumMessage">
            <i class="fa-solid fa-triangle-exclamation"></i>이미 등록된 핸드폰 번호입니다.
          </div>
        </div>

        <div class="mb-3">
          <div class="input-group">
            <input id="inputNickName" type="text" class="form-control" name="nickName" value="${member.nickName }" placeholder="별명" />
            <button class="btn btn-outline-primary" type="button" id="checkNicknameBtn">중복확인</button>
          </div>

          <div class="d-none form-text text-primary" id="availableNicknameMessage">
            <i class="fa-solid fa-check"></i>사용 가능한 별명입니다.
          </div>
          <div class="d-none form-text text-danger" id="notAvailableNicknameMessage">
            <i class="fa-solid fa-triangle-exclamation"></i>사용 불가능한 별명입니다.
          </div>

        </div>

        <!-- 도로명 주소 API 사용 -->
        <div class="mb-3">
          <button type="button" class="btn btn-primary" onClick="goPopup();">주소 검색</button>
          <div class="input-group">
            <input name="address" id="inputAddress" type="text" class="form-control" placeholder="Enter Address" required readonly />
          </div>
          <div class="d-none">
            <input name="addressSggNm" id="inputAddressSggNm" type="text" class="form-control" placeholder="Enter Address" required />
          </div>
        </div>
<%--        <div class="d-none">--%>
<%--          <input type="text" class="form-control" name="authority" value="user">--%>
<%--        </div>--%>

        <div class="mb-3">
          <h5 style="display: flex"><p>*</p>권한 신청</h5>
          <div class="btn-group" role="group" aria-label="Basic radio toggle button group">
            <input type="radio" class="btn-check" name="authority" id="volunteer" autocomplete="off" value="volunteer" checked>
            <label class="btn btn-outline-primary" for="volunteer">자원 봉사자 / 후원자</label>
            <input type="radio" class="btn-check" name="authority" id="needVolunteer" autocomplete="off" value="preNeedVolunteer">
            <label class="btn btn-outline-primary" for="needVolunteer">봉사 수혜기관 / 수혜기관</label>
          </div>
        </div>

            <div class="mb-3 d-none" id="submitDocu">
              <span style="color: #EB4F43">봉사 수혜기관 / 수혜기관 선택 시 하단 파일 양식 작성 후 제출해주세요!</span> <br>
              <h6>ex) 자원봉사 수요처 등록신청서(기관명).hwpx</h6>
              <br>
              <div>
                <label for="add-file" class="form-label"></label>
                <span style="font-weight: 600;">증빙자료</span>
                <input id="add-file" class="form-control" type="file"  name="docu" multiple accept=".pdf, .doc, .docx, .hwpx"/>
              </div>
              <br>
              <a href="${bucketUrl}/notice/10/자원봉사 수요처 등록신청서.hwpx" download>파일 양식: 자원봉사 수요처 등록신청서.hwpx
           </div>


        <div class="mb-3">
          <input disabled id="signupSubmit" type="submit" class="btn btn-outline-primary" value="가입" />
        </div>
      </form>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

<script src="/js/member/signup.js"></script>

</body>
</html>