let checkId = false;
let checkPassword = false;
let checkName = false;
let checkGender = false;
let checkEmail = false;
let checkPhoneNum = false;
let checkNickname = false;

function enableSubmit() {
    if (checkId && checkPassword && checkName && checkGender && checkEmail && checkPhoneNum && checkNickname) {
        $("#signupSubmit").removeAttr("disabled");
    } else {
        $("#signupSubmit").attr("disabled", "");
    }
}

// id 중복확인 버튼이 클릭되면
$("#checkIdBtn").click(function() {
    const userid = $("#inputId").val();
    // 입력한 ID와 ajax 요청 보내서
    $.ajax("/member/checkId/" + userid, {
        success: function(data) {

            if (data.available) {
                // 사용 가능하다는 메세지 출력
                $("#availableIdMessage").removeClass("d-none");
                $("#notAvailableIdMessage").addClass("d-none");
                checkId = true;
            } else {
                // 사용 가능하지 않다는 메세지 출력
                $("#availableIdMessage").addClass("d-none");
                $("#notAvailableIdMessage").removeClass("d-none");
                checkId = false;
            }
        },
        complete: enableSubmit
    })
});

// 패스워드 입력칸의 값과 패스워드 확인칸 값
$("#inputPassword, #inputPasswordCheck").keyup(function() {
    const pw1 = $("#inputPassword").val();
    const pw2 = $("#inputPasswordCheck").val();

    //  pw1과 pw2의 값이 같다면
    if (pw1 == pw2) {
        $("#passwordSuccessText").removeClass("d-none");
        $("#passwordFailText").addClass("d-none");
        checkPassword = true;
    }
    else {
        $("#passwordSuccessText").addClass("d-none");
        $("#passwordFailText").removeClass("d-none");
        checkPassword = false;
    }
    complete: enableSubmit
})

// 이름 확인 버튼 클릭 시
$("#checkNameBtn").click(function() {
    $("#availableNameMessage").removeClass("d-none");
    checkName = true;
    enableSubmit();
});

$("#checkNameBtn").click(function() {
    checkName = true;
    enableSubmit();
})

$("#inputGenderM").change(function() {
    checkGender = true;
    enableSubmit();
})

$("#inputGenderW").change(function() {
    checkGender = true;
    enableSubmit();
})

$("#inputGenderX").change(function() {
    checkGender = true;
    enableSubmit();
})

//$("#checkEmailBtn").click(function() {
//	checkEmail = true;
//	enableSubmit();
//})

// 핸드폰 번호 중복확인 버튼이 클릭되면
$("#checkPhoneNumBtn").click(function() {
    const userphoneNum = $("#inputPhoneNum").val();
    // 입력한 ID와 ajax 요청 보내서
    $.ajax("/member/checkPhoneNum/" + userphoneNum, {
        success: function(data) {

            if (data.available) {
                // 사용 가능하다는 메세지 출력
                $("#availablePhoneNumMessage").removeClass("d-none");
                $("#notAvailablePhoneNumMessage").addClass("d-none");
                checkPhoneNum = true;
            } else {
                // 사용 가능하지 않다는 메세지 출력
                $("#availablePhoneNumMessage").addClass("d-none");
                $("#notAvailablePhoneNumMessage").removeClass("d-none");
                checkPhoneNum = false;
            }
        },
        complete: enableSubmit
    })
});

// 별명 중복확인 버튼이 클릭되면
$("#checkNicknameBtn").click(function() {
    const usernickName = $("#inputNickName").val();
    // 입력한 ID와 ajax 요청 보내서
    $.ajax("/member/checkNickName/" + usernickName, {
        success: function(data) {

            if (data.available) {
                // 사용 가능하다는 메세지 출력
                $("#availableNicknameMessage").removeClass("d-none");
                $("#notAvailableNicknameMessage").addClass("d-none");
                checkNickname = true;
            } else {
                // 사용 가능하지 않다는 메세지 출력
                $("#availableNicknameMessage").addClass("d-none");
                $("#notAvailableNicknameMessage").removeClass("d-none");
                checkNickname = false;
            }
        },
        complete: enableSubmit
    })
});

$("#inputId").keyup(function() {
    checkId = false;
    $("#availableIdMessage").addClass("d-none");
    $("#notAvailableIdMessage").addClass("d-none");
    enableSubmit();
})

$("#inputPassword").keyup(function() {
    checkPassword = false;
    $("#passwordSuccessText").addClass("d-none");
    $("#passwordFailText").addClass("d-none");
    enableSubmit();
})

$("#inputName").keyup(function() {
    checkName = false;
    enableSubmit();
})

// email 인풋에 키보드 입력 발생시
$("#inputEmail").keyup(function() {
    // 이메일 중복확인 다시
    checkEmail = false;
    // submit 버튼 비활성화
    enableSubmit();
})

// 핸드폰 번호 input에 키보드 입력 발생시
$("#inputPhoneNum").keyup(function() {
    // 별명 중복확인 다시
    checkPhoneNum = false;
    $("#availableNicknameMessage").addClass("d-none");
    $("#notAvailableNicknameMessage").addClass("d-none");
    // submit 버튼 비활성화
    enableSubmit();
})

// nickName input에 키보드 입력 발생시
$("#inputNickName").keyup(function() {
    // 별명 중복확인 다시
    checkNickname = false;
    $("#availablePhoneNumMessage").addClass("d-none");
    $("#notAvailablePhoneNumMessage").addClass("d-none");
    // submit 버튼 비활성화
    enableSubmit();
})

// 이메일 인증 버튼 클릭 이벤트 처리
$("#checkEmailBtn").click(function() {
    // 인증하기 버튼을 클릭하면 인증번호 입력 칸과 확인 버튼을 나타내고, 인증하기 버튼은 숨김
    $("#inputVerificationCode").removeClass("d-none");
    $("#verifyEmailBtn").show();
    $("#checkEmailBtn").hide();

    var email = $("#inputEmail").val();
    if (email) {
        // 이메일 전송 요청
        $.ajax({
            url: "/member/mail",
            method: "POST",
            data: {
                email: email
            },
            success: function(response) {
                // 이메일 전송 성공 시 처리
                $("#inputEmail").prop("disabled", true);
                $("#verifyEmailBtn").hide();
                $("#inputVerificationCode").removeClass("d-none");
                $("#verifyCodeBtn").show();
            },
            error: function() {
                // 에러 처리 로직 추가
            }
        });
    }
});

// 확인 버튼 클릭 시 동작
$("#verifyEmailBtn").click(function() {
    // 인증 확인 버튼을 클릭하면 인증번호 입력 칸과 확인 버튼을 숨기고, 인증 완료 메시지를 나타냄
    //    $("#inputVerificationCode").hide();
    //    $("#verifyEmailBtn").hide();

    $("#verificationSuccessText").show();
});


// 확인 버튼 클릭 이벤트 처리
$("#verifyEmailBtn").click(function() {
    var enteredCode = $("#verificationCode").val();
    if (enteredCode) {
        // 이메일 전송 요청
        $.ajax({
            url: "/member/mailCheck",
            method: "POST",
            data: {
                enteredCode: enteredCode
            },
            success: function(response) {
                var authentication = response.authentication;


                if (authentication) {
                    // 인증번호 일치 시 회원 가입 진행
                    checkEmail = true;
                    enableSubmit();

                    alert("인증이 완료되었습니다. 회원 가입을 진행합니다.");
                } else {
                    alert("인증번호가 일치하지 않습니다. 다시 확인해 주세요.");
                }
            }

        });
    }
});
// /teamProject/src/main/webapp/WEB-INF/views/member/jusoPopup.jsp
function goPopup() {
    var pop = window.open("/member/jusoPopup", "pop",
        "width=570,height=420, scrollbars=yes, resizable=yes");
}

function jusoCallBack(roadFullAddr, siNm, sggNm) {
    var addressEI = document.querySelector("#inputAddress");
    addressEI.value = roadFullAddr;

    var addressSggNm = document.querySelector("#inputAddressSggNm");
    addressSggNm.value = siNm + " " + sggNm;


}

