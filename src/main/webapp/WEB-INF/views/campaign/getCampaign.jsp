<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>상세 캠페인</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer"/>
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
    .content > header {
        width: 65%; /* Set the width to 100% */
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
        /*display: flex;*/
        position: fixed;
        top: 147px;
        right: 85px;
        width: 20%;
        height: 50vh;
        padding: 20px;
        background-color: #f1f1f1;
        text-align: center;
    }

    .sideMenuInner{
        display: flex;
        font-size: 25px;
        text-align: center;
    }

    .graph{
        background-color: white;
        border-color: black;
        height: 40px;
        border-radius: 40px;
    }

    .graph span{
        display: block;
        padding: 0 10px;
        height: 40px;
        line-height: 40px;
        text-align: right;
        /*width: 75%;*/
        background-color: aquamarine;
        color: black;
        border-radius: 40px;
        box-sizing: border-box;
        animation: stack 2s 1;

    }

        @keyframes stack {
            0% {width: 0; color: rgba(25,255,255,0);}
            40% {color: rgba(25,255,255,1);}
            100% {width: 100%;}

        }


</style>
<body>

<my:navbar current="campaignList"></my:navbar>
<div class="mainContainer">
    <div class="content">
<%--        <div class="d-flex">--%>
            <div class="header">
                <h3>${campaign.title }</h3>
                <div>by ${campaign.writer }</div>
                <a href="/campaign/campaignList" class="btn btn-primary" style="float: right">목록보기</a>
                <span id="campaignIdText" class="d-none"> ${campaign.id } </span>
                <img class="campaign-image"/>
                    <c:forEach items="${campaign.repFileName }" var="fileName">
                <div class="mb-3">
                    <img class="img-thumbnail img-fluid campaign-image" src="${bucketUrl }/campaign/${campaign.id }/${fileName}"/>
                </div>
                </c:forEach>
            </div>
<%--        </div>--%>

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
                             src="${bucketUrl }/campaign/${campaign.id }/${fileName}"/>
                    </div>
                </c:forEach>
            </div>


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
        <%--좋아요 버튼--%>
        <div class="sideMenuInner">
            <p>
            <h3 id="likeNumber">${campaign.likeCount}</h3>번의
            <h2 id="likeIcon">
                <c:if test="${campaign.liked}"><i class="like-icon fa-solid fa-heart"
                                                  style="color: crimson"></i></c:if>
                <c:if test="${not campaign.liked}"><i class="like-icon fa-regular fa-heart"></i></c:if>
            </h2>
            을 받고 있습니다!
            </p>

        </div>
        <div>
            <h4>기부 금액 현황 : ${allDonation}원</h4>
            <h5>목표 기부 금액 : ${targetAmount}원</h5>
        </div>
        <div class="graph">
            <span style="width: ${percent}%">${percent}%</span>
        </div>
        <sec:authorize access="isAuthenticated()">
            <div class="donation" id="donation">
                <form action="/kakaoPay">
                    <input id="" type="hidden" class="form-control" name="campaignId" value="${campaign.id }" readonly/>
                    <input id="" type="hidden" class="form-control" name="campaignName" value="${campaign.title }" readonly/>
                    <input id="donationSubmit" type="submit" class="btn btn-outline-success" value="기부하기"/>
                </form>
            </div>
        </sec:authorize>

    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

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
