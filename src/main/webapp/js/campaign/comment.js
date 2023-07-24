const boardId = $("#boardIdText").text().trim();
$.ajax("/comment/list?board=" + boardId, {
    method: "get", // 생략 가능
    success: function(data) {
        console.log(data);
    }
});