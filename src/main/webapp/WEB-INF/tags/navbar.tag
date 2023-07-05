<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="current"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<nav class="navbar navbar-expand-lg bg-body-tertiary mb-5">
    <div class="container-lg">
        <a class="navbar-brand" href="">
            <img src="/img/volunteer.png" alt="" height="24" />
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <sec:authorize access="isAnonymous()">
                    <li class="nav-item">
                        <a class="nav-link ${current eq 'login' ? 'active' : '' }" href="/member/login">로그인</a>
                    </li>
                </sec:authorize>
                <li class="nav-item">
                    <a class="nav-link ${current eq 'campaignList' ? 'active' : '' }" href="/campaign/campaignList">후원하기</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link ${current eq 'recruitList' ? 'active' : '' }" href="/recruit/recruitList">봉사활동 지원 / 모집</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link ${current eq 'noticeList' ? 'active' : '' }" href="/notice/noticeList">공지사항</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link ${current eq 'mypage' ? 'active' : '' }" href="/member/myPage?id=<sec:authentication property="name"/>">마이페이지</a>
                </li>
                <sec:authorize access="isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link" href="/member/logout">로그아웃</a>
                    </li>
                </sec:authorize>
            </ul>
<%--            <form action="/list" class="d-flex" role="search">--%>

<%--                <div class="input-group">--%>
<%--                    <select class="form-select flex-grow-0" style="width: 100px;" name="type" id="">--%>
<%--                        <option value="all">전체</option>--%>
<%--                        <option value="title" ${param.type eq 'title' ? 'selected' : '' }>제목</option>--%>
<%--                        <option value="body" ${param.type eq 'body' ? 'selected' : '' }>본문</option>--%>
<%--                        <option value="writer" ${param.type eq 'writer' ? 'selected' : '' }>작성자</option>--%>
<%--                    </select>--%>

<%--                    <input value="${param.search }" name="search" class="form-control" type="search" placeholder="Search" aria-label="Search">--%>
<%--                    <button class="btn btn-outline-success" type="submit">--%>
<%--                        <i class="fa-solid fa-magnifying-glass"></i>--%>
<%--                    </button>--%>
<%--                </div>--%>
<%--            </form>--%>
        </div>
        <sec:authorize access="isAuthenticated()">
            <div class="nav-item">
                <a class="nav-link">${pageContext.request.userPrincipal.name}님 환영합니다!</a>
            </div>
        </sec:authorize>

    </div>
</nav>



