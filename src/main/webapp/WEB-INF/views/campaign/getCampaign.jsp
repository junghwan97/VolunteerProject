<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>상세 캠페인</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
          integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
</head>
<style>
    .content {
        display: flex;
        flex-direction: column;
        margin-left: 150px;
        flex: 1;
    }

    .content > * {
        width: 65%; /* Set the width to 100% */
    }

    .like-icon {
        font-size: 24px;
        margin-left: 20px;
    }

    .campaign-image {
        width: 100%; /* 너비를 100%로 설정합니다 */
        height: auto; /* 가로 세로 비율을 유지합니다 */
    }

    .content > div > img {
        width: 100%;
        height: auto;
    }

    #target-textarea {
        box-sizing: border-box;
        resize: none;
        /*border: 1px solid rgb(42, 42, 42);*/
        width: 100%;
        background-color: white;
        overflow: hidden;
        border: none;
    }

    .sideMenu {
        position: fixed;
        top: 147px;
        right: 85px;
        width: 20%;
        height: 50vh;
        padding: 20px;
        background-color: #f1f1f1;
    }

    /* 아래는 기본 래퍼 설정 */
    /*#wrapper {*/
    /*    margin: 0 auto;*/
    /*    padding: 10px 20%;*/
    /*    max-width: 1170px;*/
    /*}*/

</style>
<body>

<my:navbar current="campaignList"></my:navbar>
<div class="mainContainer">
    <div class="content">
        <div class="d-flex">
            <div class="header">
                <h3>${campaign.title }</h3>
                <div>by ${campaign.writer }</div>
                <span id="campaignIdText" class="d-none"> ${campaign.id } </span>
                <div>
                    <c:forEach items="${campaign.repFileName }" var="fileName">
                </div>
                <div class="mb-3">
                    <img class="img-thumbnail img-fluid campaign-image" src="${bucketUrl }/${campaign.id }/${fileName}"/>
                </div>
                </c:forEach>
            </div>
        </div>

        <div>
            <%--        <div class="mb-3">--%>
            <%--             by<input type="text" class="form-control" value="${campaign.writer }" readonly/>--%>
            <%--             by ${campaign.writer }--%>
            <%--        </div>--%>
            <div class="mb-3">
                <label for="" class="form-label">작성일시</label> <input type="text" readonly class="form-control"
                                                                     value="${campaign.inserted }"/>
            </div>
            <div class="mb-3" id="wrapper">
                <label for="" class="form-label">본문</label>
                <textarea class="form-control" id="target-textarea" disabled>${campaign.body }</textarea>
            </div>
            <!-- 그림 파일 출력 -->
            <div class="mb-3">
                <c:forEach items="${campaign.fileName }" var="fileName">
                    <div class="mb-3">
                        <img class="img-thumbnail img-fluid campaign-image"
                             src="${bucketUrl }/${campaign.id }/${fileName}"/>
                    </div>
                </c:forEach>
            </div>

            <a href="/campaign/campaignList" class="btn btn-primary">목록보기</a>
            <sec:authorize access="hasAuthority('admin')">
                <a href="/campaign/modify/${campaign.id}" class="btn btn-primary">수정하기</a>
                <button id="removeButton" class="btn btn-danger" data-bs-toggle="modal"
                        data-bs-target="#deleteConfirmModal">삭제
                </button>
            </sec:authorize>

            <div class="d-none">
                <form action="/campaign/remove/${campaign.id}" method="post" id="removeForm">
                    <input type="text" name="id" value="${campaign.id }"/>
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

    <div class="sideMenu">
        <sec:authorize access="isAuthenticated()">
            <%--좋아요 버튼--%>
            <div>
                <div id="likeIcon">
                    <c:if test="${campaign.liked}">
                        <i class="like-icon fa-solid fa-heart"></i>
                    </c:if>
                    <c:if test="${not campaign.liked }">
                        <i class="like-icon fa-regular fa-heart"></i>
                    </c:if>
                </div>
            </div>


        </sec:authorize>
        <sec:authorize access="isAnonymous()">

        </sec:authorize>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js"
        integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer"></script>

<script src="/js/campaign/like.js"></script>
<script>
    function resize() {
        let textarea = document.getElementById("target-textarea");

        textarea.style.height = "0px";

        let scrollHeight = textarea.scrollHeight;
        let style = window.getComputedStyle(textarea);
        let borderTop = parseInt(style.borderTop);
        let borderBottom = parseInt(style.borderBottom);

        textarea.style.height = (scrollHeight + borderTop + borderBottom) + "px";
    }

    window.addEventListener("load", resize);
    window.onresize = resize;
</script>

</body>
</html>
