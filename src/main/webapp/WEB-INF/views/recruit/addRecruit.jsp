<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<html>
<head>
    <title>봉사활동 모집</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer"/>
</head>
<body>
<my:navbar current="recruitList"></my:navbar>

<h1>봉사활동 모집</h1>
<form action="" method="post" enctype="multipart/form-data">

    <div class="mb-3">
        <label for="titleInput" class="form-label">제목 : </label>
        <input id="titleInput" type="form-control" name="title" value="${recruit.title }" />
    </div>
    <div>
        <label for="startDate">시작 날짜:</label>
        <input type="date" id="startDate" name="vStartDate" value="${recruit.vStartDate}" required><br>

        <label for="endDate">종료 날짜:</label>
        <input type="date" id="endDate" name="vEndDate" value="${recruit.vEndDate}" required><br>
    </div>
    <div>
        <label for="startTime">시작 시간:</label>
        <input type="time" id="startTime" name="vStartTime" value="${recruit.vStartTime}" required><br>

        <label for="endTime">종료 시간:</label>
        <input type="time" id="endTime" name="vEndTime" value="${recruit.vEndTime}" required><br>
    </div>
    <div>
        <span class="category_title" style="font-weight: 600;">카테고리</span>
        <select name="vField">
            <option value="생활편의지원"> 생활편의지원
            <option value="주거환경"> 주거환경
            <option value="상담"> 상담
            <option value="교육"> 교육
            <option value="보건의료"> 보건의료
            <option value="농어촌 봉사"> 농어촌 봉사
            <option value="문화행사"> 문화행사
            <option value="안전.예방"> 안전.예방
            <option value="멘토링"> 멘토링
        </select>
    </div>
    <div>
        <label for="vPlaceInput" class="form-label">봉사 장소 : </label>
        <input id="vPlaceInput" type="form-control" name="vPlace" value="${recruit.vPlace }" />
    </div>
    <div class="mb-3">
        <label for="bodyInput" class="from-label">본문</label> <br />
        <textarea id="bodyInput" name="body" cols="80" rows="10"\>${recruit.body }</textarea>
    </div>

    <div class="mb-3">
        <label for="fileInput" class="form-label">그림 파일</label>
        <input class="form-control" type="file" id="fileInput" name="files" multiple accept="image/*">
        <div class="form-text">
            총 10MB, 하나의 파일은 1MB 크기를 초과할 수 없습니다.
        </div>
    </div>

    <div class="mb-3">
        <input type="submit" value="올리기" />
    </div>

</form>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</body>
</html>
