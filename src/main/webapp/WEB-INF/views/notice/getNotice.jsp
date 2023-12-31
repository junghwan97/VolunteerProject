<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>공지사항</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer"/>

</head>
<body>
<my:navbar current="noticeList"></my:navbar>
<div class="container-lg">
    <div class="row justify-content-center">
        <div class="col-12 col-md-8 col-lg-6">
            <h3>${notice.title }</h3>

            <div>
                <div class="mb-3">
                    <label for="" class="form-label">작성자</label> <input type="text" class="form-control" value="${notice.writer }" readonly/>
                </div>
                <div class="mb-3">
                    <label for="" class="form-label">작성일시</label> <input type="text" readonly class="form-control" value="${notice.inserted }"/>
                </div>
                <div class="mb-3">
                    <label for="" class="form-label">본문</label>
                    <textarea class="form-control" readonly rows="15">${notice.body }</textarea>
                </div>
                <div class="mb-3">
                    <c:forEach items="${notice.fileName}">
                        <div class="mb-3">
                            <a href="${bucketUrl}/notice/${notice.id}/${notice.fileName}" download>문서
                                보기: ${notice.fileName}
                        </div>
                    </c:forEach>
                </div>
                <a href="/notice/noticeList" class="btn btn-primary">목록보기</a>
                <sec:authorize access="hasAuthority('admin')">
                    <a href="/notice/modify/${notice.id}" class="btn btn-primary">수정하기</a>
                    <button id="removeButton" class="btn btn-danger" data-bs-toggle="modal"
                            data-bs-target="#deleteConfirmModal">삭제
                    </button>
                </sec:authorize>

                <div class="d-none">
                    <form action="/notice/remove/${notice.id}" method="post" id="removeForm">
                        <input type="text" name="id" value="${notice.id }"/>
                    </form>
                </div>

            </div>

            <div class="modal fade" id="deleteConfirmModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="exampleModalLabel">삭제 확인</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">삭제 하시겠습니까?</div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                            <button type="submit" class="btn btn-danger" form="removeForm">삭제</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</body>
</html>
