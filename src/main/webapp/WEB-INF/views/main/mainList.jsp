<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>HI-FIVE</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
          integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
</head>
<style>

    .flexbox {
        display: flex;
        margin-top: 120px;
        margin-left: 150px;
        margin-right: 115px;
    }

    .header__center{
        display: flex;
        justify-content: center;
        margin-top: 10px;
        margin-bottom: 20px;
    }

    .input-group{
        width: 900px;
    }
    .mainContainer {
        flex: 1; /* mainContainer가 확장 가능하도록 설정 */
        margin-right: 40px;
    }

    .rightMenu {
        flex: 0 0 500px; /* rightMenu의 고정 너비를 설정 */
        margin-left: 50px; /* mainContainer와의 간격을 설정 */
        text-align: center;
    }

    .sideMenu {
        width: 100%;
        height: 300px;
        background-color: #F7F4F3;
        padding: 20px;
        border-radius: 5px;
    }

    .notice {
        width: 100%;
        height: 300px;
        margin-top: 17px;
        background-color: #F7F4F3;
        padding: 20px;
        border-radius: 5px;
    }

    .items {
        margin-right: 10px;
        white-space: normal;
        vertical-align: top;
    }

    .items img {
        width: 300px;
        height: 300px;
        object-fit: cover;
        border-radius: 20%;
    }

    .inner {
        display: flex;
        background-color: #F7F4F3;
        border-radius: 2%;
    }

    .inner .inner-content {
        width: 500px;
        margin-top: 100px;
        margin-left: 80px;
        margin-bottom: 20px;
        color: black;
    }

    a {
        text-decoration: none;
        color: black;
    }

    @media (max-width: 992px) {
        .flexbox {
            flex-direction: column;
            margin-left: 20px;
            margin-right: 20px;
        }

        .rightMenu {
            flex: 1; /* rightMenu가 확장 가능하도록 설정 */
            margin-left: 0;
            margin-top: 20px;
        }
    }
</style>
<body>

<my:navbar></my:navbar>

<!-- 검색창 -->
<div class="header__center">
    <form action="./mainList" class="d-flex" role="search">
        <div class="input-group">
            <select class="form-select flex-grow-0" style="width: 100px;" name="type" id="">
                <option value="all">전체</option>
                <option value="title" ${param.type eq 'title' ? 'selected' : '' }>제목</option>
                <option value="body" ${param.type eq 'body' ? 'selected' : '' }>본문</option>
                <option value="writer" ${param.type eq 'writer' ? 'selected' : '' }>작성자</option>
            </select> <input value="${param.search }" name="search" class="form-control" type="search" placeholder="Search" aria-label="Search">
            <button class="btn btn-outline-success" type="submit">
                <i class="fa-solid fa-magnifying-glass"></i>
            </button>
        </div>
    </form>
</div>

<div class="flexbox">
    <div class="mainContainer">
        <div calss="container2">
            <div class="content">
                <c:forEach items="${campaignList}" var="campaign">
                    <ul class="items">
                        <a href="/campaign/campaignId/${campaign.id}">
                            <div class="inner">
                                <div>
                                    <img class="img-thumbnail img-fluid"
                                         src="${bucketUrl }/campaign/${campaign.id }/${campaign.repFileName}"/>
                                </div>
                                <div class="inner-content">
                                    <span class="title"> ${campaign.title}</span> <br>
                                    <span class="writer">작성자 : ${campaign.writer}</span> <br>
                                    <span class="inserted">등록일 : ${campaign.inserted}</span>
                                    <br>
                                    <br>
<%--                                    <div class="graph">--%>
<%--                                        <span style="width: ${percent}%">${percent}%</span>--%>
<%--                                    </div>--%>
                                    <span id="likeNumber"><i class="like-icon fa-solid fa-heart" style="color: crimson"></i> X ${campaign.likeCount}</span>
                                </div>
                            </div>
                        </a>
                    </ul>
                </c:forEach>
            </div>
        </div>
    </div>
    <div class="rightMenu">
        <div class="sideMenu">
            <sec:authorize access="isAuthenticated()">
                <h3>${member.nickName}님의 정보</h3>
                <p>간단한 나의 정보</p>
                <p>간단한 나의 정보</p>
                <h4>나의 총 기부금액 : ${allDonation}원</h4>
                <h4><a href="/member/myPage?id=${member.id}">마이페이지 바로 가기</a></h4>
            </sec:authorize>
            <sec:authorize access="isAnonymous()">
                <h1>Login</h1>
                <form method="post" action="/member/login" id="login-form">
                    <input id="inputUsername" type="text" name="username" placeholder="ID"> <br>
                    <input id="inputPassword" type="password" name="password" placeholder="Password"><br>
                    <input type="submit" value="Login">
                    <input type="button" value="Sign Up" onclick="location.href='/member/signup'"/>
                        <%--        <div class="find-buttons">--%>
                        <%--            <input type="button" value="아이디 찾기" onclick="location.href='/member/userSearch '" />--%>
                        <%--            <input type="button" value="임시 비밀번호 발급" onclick="location.href='/member/sendTempPw'" />--%>
                        <%--        </div>--%>
                </form>
            </sec:authorize>
        </div>
        <div class="notice">
            <h3>공지사항</h3>
            <c:forEach items="${notice}" var="notice">
                <ul>
                    <li><a href="/notice/noticeId/${notice.id }"> ${notice.title}</a></li>
                </ul>
            </c:forEach>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7
