<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<html>
<head>
    <title>봉사활동 모집 공고 수정</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer"/>
</head>
<body>
<my:navbar current="recruitList"></my:navbar>
<div class="row justify-content-center">
  <div class="col-12 col-md-8 col-lg-10">
    <h1>${recruit.id }번 봉사활동 모집 공고 수정</h1>
    <form method="post" encType="multipart/form-data">
      <input type="hidden" name="id" value="${recruit.id }"/>
      <div class="mb-3">
        <label for="titleInput" class="form-label">제목</label>
        <input class="form-control" id="titleInput" type="text" name="title" value="${recruit.title }"/>
      </div>
      <div>
        <label for="startDate">시작 날짜:</label>
        <input type="date" id="startDate" name="vStartDate" value="${recruit.VStartDate}" required><br>

        <label for="endDate">종료 날짜:</label>
        <input type="date" id="endDate" name="vEndDate" value="${recruit.VEndDate}" required><br>
      </div>
      <div>
        <label for="startTime">시작 시간:</label>
        <input type="time" id="startTime" name="vStartTime" value="${recruit.VStartTime}" required><br>

        <label for="endTime">종료 시간:</label>
        <input type="time" id="endTime" name="vEndTime" value="${recruit.VEndTime}" required><br>
      </div>
      <div class="mb-3">
        <label for="bodyTextarea" class="form-label">본문</label>
        <textarea class="form-control" id="bodyTextarea" rows="10" name="body">${recruit.body }</textarea>
      </div>

      <div class="mb-3">
        <label for="" class="form-label">작성일시</label>
        <input class="form-control" type="text" value="${recruit.inserted }" readonly/>
      </div>

      <!-- 그림 파일 출력 -->
      <div class="mb-3">
        <c:forEach items="${recruit.fileName }" var="fileName" varStatus="status">

          <div class="form-check form-switch">
            <input name="modifyFiles" value="${fileName }" class="form-check-input" type="checkbox" role="switch"
                   id="modifyCheckBox${status.index }">
            <label class="form-check-label" for="modifyCheckBox${status.index }">
              <i class="fa-solid fa-trash-can text-danger"></i>
            </label>
          </div>

          <div class="mb-3">
            <img class="img-thumbnail img-fluid" src="${bucketUrl }/recruit/${recruit.id }/${fileName}"/>
          </div>
        </c:forEach>
      </div>

      <!-- 새 그림 파일 추가 -->
      <div class="mb-3">
        <label for="fileInput" class="form-label">그림 파일</label>
        <input class="form-control" type="file" id="fileInput" name="files" multiple accept="image/*">
      </div>


      <div class="mb-3">
        <input class="btn btn-secondary" type="submit" value="수정"/>
      </div>
    </form>
  </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</body>
</html>
