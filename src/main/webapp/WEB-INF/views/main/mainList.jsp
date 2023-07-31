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

    :root {
        --accent-color: #C5CAE9 ;
    }

    body{
        background-color: #F7F7F7;
    }
    .navbar-nav li:hover {
        background-color: var(--accent-color);
        border-radius: 3px;
    }
    .flexbox {
        display: flex;
        margin-top: 50px;
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

    .topMenu{
        width: 80vw; /* 화면 너비의 80% */
        height: 10vh; /* 화면 높이의 10% */
        background-color: #03A9F4;
        border-radius: 5px;
        display: flex;
        align-items: center;
        justify-content: center;
        margin-left: calc((100vw - 78vw) / 2);;
        /*position: absolute;*/
        /*left: 50%;*/
        /*top: 31%;*/
        /*transform: translate(-50%, -50%);*/
        color: #F7F7F7;
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
        background-color: #FFFFFF;
        padding: 20px;
        border-radius: 5px;
        box-shadow:  5px 5px 5px #D1CDC7, /* 오른쪽 아래 방향의 그림자 */
        -5px -5px 5px #FFFFFF, /* 왼쪽 위 방향의 그림자 */
        5px -5px 5px #D1CDC7, /* 오른쪽 위 방향의 그림자 */
        -5px 5px 5px #FFFFFF; /* 왼쪽 아래 방향의 그림자 */
        overflow: hidden;
    }

    .notice {
        width: 100%;
        height: 300px;
        margin-top: 17px;
        color: #03A9F4;
        background-color: #FFFFFF;
        padding: 20px;
        border-radius: 5px;
        box-shadow:  5px 5px 5px #D1CDC7, /* 오른쪽 아래 방향의 그림자 */
        -5px -5px 5px #FFFFFF, /* 왼쪽 위 방향의 그림자 */
        5px -5px 5px #D1CDC7, /* 오른쪽 위 방향의 그림자 */
        -5px 5px 5px #FFFFFF; /* 왼쪽 아래 방향의 그림자 */
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

    /*.inner {*/
    /*    display: flex;*/
    /*    background-color: #E0E0E0;*/
    /*    border-radius: 2%;*/
    /*}*/

    /*.inner .inner-content {*/
    /*    width: 500px;*/
    /*    margin-top: 100px;*/
    /*    margin-left: 80px;*/
    /*    margin-bottom: 20px;*/
    /*    color: black;*/
    /*}*/

    .inner {
        display: flex;
        background-color: #FFFFFF;
        border-radius: 10px;
        padding: 20px;
        margin-bottom: 30px;
        box-shadow:  5px 5px 5px #D1CDC7, /* 오른쪽 아래 방향의 그림자 */
        -5px -5px 5px #FFFFFF, /* 왼쪽 위 방향의 그림자 */
        5px -5px 5px #D1CDC7, /* 오른쪽 위 방향의 그림자 */
        -5px 5px 5px #FFFFFF; /* 왼쪽 아래 방향의 그림자 */
    }

    .inner .inner-content {
        color: black;
        font-size: 2vw;
    }

    a {
        text-decoration: none;
        color: #03A9F4;
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

    /** {*/
    /*    padding: 0;*/
    /*    margin: 0;*/
    /*    border: none;*/
    /*}*/

    /*body {*/
    /*    font-size: 14px;*/
    /*    font-family: 'Roboto', sans-serif;*/
    /*}*/

    .login-wrapper {
        /*width: 400px;*/
        /*height: 350px;*/
        /*padding: 40px;*/

        /*box-sizing: border-box;*/
        /*display: flex;*/
        /*flex-direction: column;*/
        /*align-items: center;*/
        /*justify-content: center;*/
    }

    .login-wrapper>h1 {
        font-size: 35px;
        color: #03A9F4;
        margin-bottom: 20px;
        font-weight: bold;
    }

    #login-form>.submitBtn>input {
        width: 80%;
        height: 48px;
        padding: 0 10px;
        box-sizing: border-box;
        margin-bottom: 50px;
        border-radius: 6px;
        background-color: #F8F8F8;
    }
    #login-form>input {
        width: 80%;
        height: 48px;
        padding: 0 10px;
        box-sizing: border-box;
        margin-bottom: 16px;
        border-radius: 6px;
        background-color: #F8F8F8;
    }

    #login-form>input::placeholder {
        color: #D2D2D2;
    }

    #login-form>.submitBtn>input[type="submit"] {
        color: #fff;
        font-size: 16px;
        background-color: #03A9F4;
        margin-top: 10px;

    }

    #login-form>.submitBtn>input[type="button"] {
        color: #fff;
        font-size: 16px;
        background-color: #03A9F4;

        /* 	margin-top: 20px; */
    }

    .button-wrapper {
        display: flex;
        justify-content: space-between;
        margin-top: 10px;
    }

    .find-buttons input[type="button"] {
        margin-top: 0;
        width: calc(50% - 5px); /* 버튼 사이의 간격을 조정 */
        color: #fff;
        font-size: 16px;
        background-color: #55A44E;
    }
</style>
<body>

<my:navbar></my:navbar>

<!-- 검색창 -->
<div class="header__center" style="margin-top: 40px;">
    <form action="./mainList" class="d-flex" role="search">
        <div class="input-group">
            <select class="form-select flex-grow-0" style="width: 100px;" name="type" id="">
                <option value="all">전체</option>
                <option value="title" ${param.type eq 'title' ? 'selected' : '' }>제목</option>
                <option value="body" ${param.type eq 'body' ? 'selected' : '' }>본문</option>
                <option value="writer" ${param.type eq 'writer' ? 'selected' : '' }>작성자</option>
            </select> <input value="${param.search }" name="search" class="form-control" type="search" placeholder="Search" aria-label="Search">
            <button class="btn btn-outline-primary" type="submit">
                <i class="fa-solid fa-magnifying-glass"></i>
            </button>
        </div>
    </form>
</div>
<div class="topMenu">
    <h3><i class="fa-solid fa-hand-holding-dollar" style="color: #ffffff;"></i> 총 기부 횟수 : ${countDonation}건
        / <i class="fa-solid fa-coins" style="color: #ffffff;"></i> 총 기부금액 : ${allDonationMoney}원
        / <i class="fa-solid fa-handshake-angle" style="color: #ffffff;"></i> 진행된 봉사활동 : ${countVolunteer}회
    </h3>
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
                                <div class="inner-content" style="margin-left: 15px; margin-top: 70px">
                                    <span class="title" style="font-size: 20px"> ${campaign.title}</span> <br>
                                    <span class="writer" style="font-size: 17px">작성자 : ${campaign.writer}</span> <br>
                                    <span class="inserted" style="font-size: 17px">등록일 : ${campaign.inserted}</span>
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
            <sec:authorize access="isAnonymous()">
                <div class="login-wrapper">
                    <h1>Login</h1>
                    <form method="post" action="/member/login" id="login-form">
                        <input id="inputUsername" type="text" name="username" placeholder="ID"> <br>
                        <input id="inputPassword" type="password" name="password" placeholder="Password"><br>
                        <div class="submitBtn" style="margin-bottom: 30px;">
                            <input type="submit" value="Login">
                        </div>
<%--                        <input type="button" value="Sign Up" onclick="location.href='/member/signup'"/>--%>
                            <%--        <div class="find-buttons">--%>
                            <%--            <input type="button" value="아이디 찾기" onclick="location.href='/member/userSearch '" />--%>
                            <%--            <input type="button" value="임시 비밀번호 발급" onclick="location.href='/member/sendTempPw'" />--%>
                            <%--        </div>--%>
                    </form>
                </div>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <h3>${member.nickName}님의 정보</h3>
                <br>
                <br>
                <h4><i class="fa-solid fa-comment" style="color: #02a9f4;"></i> 나의 응원 댓글 수 : ${countComment}회</h4>
                <h4><i class="fa-solid fa-hand-holding-dollar" style="color: #02a9f4;"></i> 나의 기부 횟수 : ${countDonaTime}건</h4>
                <h4><i class="fa-solid fa-coins" style="color: #02a9f4;"></i> 나의 총 기부금액 : ${allDonation}원</h4>
                <h4><a href="/member/myPage?id=${member.id}" class="btn btn-primary" style="background-color: #02a9f4">마이페이지 바로 가기</a></h4>
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
