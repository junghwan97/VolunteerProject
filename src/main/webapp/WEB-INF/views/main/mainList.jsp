<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
          integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
</head>
<style>
    .mainContainer{
        display: flex;
        margin-top: 100px;
        margin-left: 200px;
        margin-right: 450px;
    }
    .sideMenu{
        margin-top: 55px;
        margin-right: 170px;
        position: fixed;
        top: 100px;
        right: 20px;
        background-color: #F7F4F3;
    }

    .box {
        white-space: nowrap;
        overflow-x: auto;
    }

    .items {
        /*display: inline-block;*/
        margin-right: 10px;
        white-space: normal;
        vertical-align: top;
    }

    .items img {
        width: 300px;
        height: 300px;
        object-fit: cover; /* 이미지 비율을 유지하며 요소에 맞춰 자르기 */
        border-radius: 20%;
    }
    .inner{
        display: flex;
        background-color: #F7F4F3;
        border-radius: 2%;
    }
    .inner .inner-content{
        margin-top: 100px;
        margin-left: 120px;
        margin-bottom: 20px;
        color: black;
    }
    .items a {
        text-decoration: none;
    }


</style>
<body>

<my:navbar></my:navbar>
<div class="mainContainer">
    <div calss="container2">
        <div class="content">
                <c:forEach items="${campaign}" var="campaign">
                    <ul class="items">
                        <a href="/campaign/campaignId/${campaign.id }">
                            <div class="inner">
                                <div>
                                    <img class="img-thumbnail img-fluid" src="${bucketUrl }/${campaign.id }/${campaign.repFileName}"/>
                                </div>
                                <div class="inner-content">
                                    <span class="title">제목 : ${campaign.title}</span> <br>
                                    <span class="writer">작성자 : ${campaign.writer}</span> <br>
                                    <span class="inserted">등록일 : ${campaign.inserted}</span>
                                </div>
                            </div>
                        </a>
                    </ul>
                </c:forEach>
        </div>
    </div>
</div>
<div class="sideMenu">
    <sec:authorize access="isAuthenticated()">
        <h3>${user.nickName}님의 정보</h3>
    </sec:authorize>


</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js"
        integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</body>
</html>
