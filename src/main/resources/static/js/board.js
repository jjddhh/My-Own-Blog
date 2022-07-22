let index = {
    init: function () {
        $("#btn-save").on("click", () => {
            this.save();
        });
    },

    save: function () {
        //alert('user의 save함수 호출됨')
        let data = {
            title: $("#title").val(),
            content: $("#content").val()
        };

        //console.log(data);

        $.ajax({
            type: "POST",
            url: "/api/board",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8", // body 데이터 타입
            dataType: "json" // 응답 받을 타입
        }).done(function (resp) {
            alert("글쓰기가 완료되었습니다.");
            console.log(resp);
            location.href= "/";
        }).fail(function (error) {
            alert(JSON.stringify(error))
        });
    }
}

index.init();