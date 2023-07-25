listComment();

$("#sendCommentBtn").click(function (){
    const campaignId = $("#campaignIdText").text().trim();
    const content = $("#commentTextArea").val();
    const data = {campaignId, content};

    $.ajax("/comment/add",{
        method: "post",
        contentType : "application/json",
        data: JSON.stringify(data),
        complete: function (){
            listComment();
        }
    });
})

function listComment(){
const campaignId = $("#campaignIdText").text().trim();
$.ajax("/comment/list?campaign=" + campaignId, {
    method: "get", // 생략 가능
    success: function(comments) {
        // console.log(data);
        $("#commentListContainer").empty();
        for(const comment of comments){
            // console.log(comment)
            $("#commentListContainer").append(`
                <div>
                    ${comment.content} 
                    :${comment.memberId}
                    :${comment.inserted} 
                </div>
            `);
        }
    }
});
}
