$(".submit").click(function () {
    var name = $("#name").val();
    var phoneNum = $("#phoneNum").val();
    var shopName = $("#shopName").val();
    var recommendName = $("#recommendName").val();
    var recommendPhontNum = $("#recommendPhontNum").val();
    for (var i = 0; i < $(".input").length; i++) {
        if ($(".input").eq(i).find('input').val() == '') {
            layer.open({
                content: $(".input").eq(i).find('.inputName').html() + "不能为空"
                , skin: 'msg'
                , time: 2 //2秒后自动关闭
            });
            return;
        }
    }
    if (!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(phoneNum))) {
        layer.open({
            content: "您的联系方式格式不正确，请重新输入"
            , skin: 'msg'
            , time: 2 //2秒后自动关闭
        });
        return;
    }
    if (!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(recommendPhontNum))) {
        layer.open({
            content: "推荐人的联系方式格式不正确，请重新输入"
            , skin: 'msg'
            , time: 2 //2秒后自动关闭
        });
        return;
    }
    $.ajax({
        type: 'post',
        url: '../mobile/act/addApplyUser',
        data: {
            "userName": name,
            "contactNum": phoneNum,
            "mercName": shopName,
            "fromUserName": recommendName,
            "fromUserContactNum": recommendPhontNum
        },
        success: function () {
            layer.open({
                content: "提交成功！"
                , skin: 'msg'
                , time: 2 //2秒后自动关闭
            });
            $(".input input").val('');
        }
    })
})