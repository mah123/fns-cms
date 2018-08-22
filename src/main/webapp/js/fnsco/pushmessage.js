//初始化表格
$('#table').bootstrapTable({
    search: false, //是否启动搜索栏
    sidePagination: 'server',
    url: PROJECT_NAME + '/web/msg/query',
    showRefresh: false,//是否显示刷新按钮
    showPaginationSwitch: false,//是否显示 数据条数选择框(分页是否显示)
    toolbar: '#toolbar',  //工具按钮用哪个容器
    striped: true,   //是否显示行间隔色
    cache: false,   //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
    pagination: true,   //是否显示分页（*）
    sortable: true,   //是否启用排序
    sortOrder: "asc",   //排序方式
    pageNumber: 1,   //初始化加载第一页，默认第一页
    pageSize: 15,   //每页的记录行数（*）
    pageList: [15, 20, 50, 100], //可供选择的每页的行数（*）
    queryParams: queryParams,
    responseHandler: responseHandler,//处理服务器返回数据
    columns: [{
        field: 'id',
        title: '操作',
        width: '10%',
        align: 'center',
        width: 150,
        formatter: operateFormatter
    }, {
        field: 'msgSubject',
        title: '活动主题',
        width: '10%',
        formatter: formatMgsSubject
    }, {
        field: 'msgType',
        title: '推送类型',
        formatter: formatPushType
    }, {
        field: 'msgSubTitle',
        title: '推送内容',
        width: '10%',
        formatter: formatMsgSubTitle
    }, {
        field: 'modifyUser',
        title: '提交人'
    }, {
        field: 'sendTime',
        title: '发布时间',
        formatter: formatTime
    }, {
        field: 'modifyTime',
        title: '提交时间',
        formatter: formatTime
    }, {
        field: 'status',
        title: '状态',
        formatter: formatPushState
    }]
});

//活动内容处理
function formatMsgSubTitle(value, row, index) {
    if (value && value.length > 30) {
        return value.substr(0, 30) + '...';
    }
    return value;
}

//活动主题处理
function formatMgsSubject(value, row, index) {
    if (value && value.length > 15) {
        return value.substr(0, 15) + '...';
    }
    return value;
}

//手机类型处理
function formatPhoneType(value, row, index) {
    if (!value) {
        return '--';
    } else if (value == '1') {
        return '安卓';
    } else if (value == '2') {
        return 'IOS';
    } else {
        return '未知设备';
    }
}

//时间格式化
function formatTime(value, row, index) {
    return formatDate(new Date(value));
}

//时间格式化
function formatDate(date) {
    return formatDateUtil(date);

}

//操作格式化
function operateFormatter(value, row, index) {
    return [
        '<a class="redact" href="javascript:querySingle(' + value + ');" title="详情">',
        '<i class="glyphicon glyphicon-file"></i>',
        '</a>  ',
        '<a class="remove" href="javascript:deleteSingle(' + value + ')" title="删除">',
        '<i class="glyphicon glyphicon glyphicon-trash"></i>',
        '</a>'
    ].join('');
}

//推送类型格式化
function formatPushType(value, row, index) {
    if (!value) {
        return '-';
    }
    else if (value == '1') {
        return '数钱吧';
    } else if (value == '3') {
        return '收银台';
    } else {
        return '其他';
    }
}

//状态格式化
function formatPushState(value, row, index) {
    if (value == 0) {
        return '待发送';
    }
    else if (value == 1) {
        return '已发送';
    } else if (value == 2) {
        return '已取消';
    } else {
        return '其他';
    }
}

//条件查询按钮事件
function queryEvent() {
    $('#table').bootstrapTable('refresh');
}

//重置按钮事件
function resetEvent() {
    $('#formSearch')[0].reset();
}

window.operateEvents = {
    'click .redact': function (e, value, row, index) {
        alert('You click like action, row: ' + JSON.stringify(row));
    },
    'click .remove': function (e, value, row, index) {
        $table.bootstrapTable('remove', {
            field: 'id',
            values: [row.id]
        });
    }
};

//组装请求参数
function queryParams(params) {
    var param = {
        currentPageNum: this.pageNumber,
        pageSize: this.pageSize,
//			   msgType :$('#pushType').val(),
//			   msgType :1,
        status: $('#pushState').val(),
        startSendTime: $('#datetimepicker1').val(),
        endSendTime: $('#datetimepicker2').val()
    }
    return param;
}

//处理后台返回数据
function responseHandler(res) {
    unloginHandler(res);
    if (res) {
        return {
            "rows": res.list,
            "total": res.total
        };
    } else {
        return {
            "rows": [],
            "total": 0
        };
    }
}

//删除事件
function deleteSingle(id) {
    layer.confirm('确定要删除么', {
        btn: ['确认', '取消']
    }, function () {
        $.ajax({
            url: PROJECT_NAME + '/web/msg/delete',
            type: 'POST',
            data: {'id': id},
            success: function (data) {
                unloginHandler(data);
                if (data.success) {
                    layer.msg('删除成功');
                    queryEvent("table");
                } else {
                    layer.msg('消息推送已发布或取消，删除失败！');
                }
            },
            error: function (e) {
                layer.msg('系统异常!' + e);
            }
        });
    }, function () {
        layer.msg('取消成功');
        return false;
    });
}

//详情
function querySingle(id) {
    $.ajax({
        url: PROJECT_NAME + '/web/msg/querySingle',
        type: 'POST',
        data: {'id': id},
        success: function (data) {
            if (data.success) {
                $("#myModal").modal();
                $("#myModalLabel").html("活动推送消息详情");
                $("#uploadify_file").hide();
                $("#myModal input").attr("disabled", true);
                $("#myModal select").attr("disabled", true);
                $("#pushView").html('');
                $("#pushView").append('<img src=' + data.data.imageUrl + '>').show();
                $("#msgSubject").val(data.data.msgSubject);
                $("#datetimepicker3").val(data.data.sendTimeStr);
                $("#detailURL").val(data.data.detailUrl);
                $("#msgSubtitle").val(data.data.msgSubTitle);
                $(".remove-icon").hide();
                $(".sunmitBtn").hide();
            } else {
                layer.msg('系统异常!' + e);
            }

        },
        error: function (e) {
            layer.msg('系统异常!' + e);
        }
    });
}

$("#btn_add").click(function () {
    $("#myModalLabel").html("新增活动推送消息");
    $("#myModal input").val('').attr("disabled", false);
    $("#myModal select").attr("disabled", false);
    $("#uploadify_file").show();
    $("#fileQueue").html('').show();
    $("#pushView").html('<div class="remove-icon" onclick="delPushImg()"><span class="glyphicon glyphicon-remove"></span></div>').hide();
    $(".sunmitBtn").show();
})
$('#datetimepicker1').datetimepicker({
    format: 'yyyy-mm-dd',
    autoclose: true,
    todayBtn: true,
    todayHighlight: true,
    showMeridian: true,
    pickerPosition: "bottom-left",
    language: 'zh-CN',//中文，需要引用zh-CN.js包
    startView: 2,//月视图
    minView: 2//日期时间选择器所能够提供的最精确的时间选择视图
});
$('#datetimepicker2').datetimepicker({
    format: 'yyyy-mm-dd',
    autoclose: true,
    todayBtn: true,
    todayHighlight: true,
    showMeridian: true,
    pickerPosition: "bottom-left",
    language: 'zh-CN',//中文，需要引用zh-CN.js包
    startView: 2,//月视图
    minView: 2//日期时间选择器所能够提供的最精确的时间选择视图
});
var dateTime = new Date();
var year = dateTime.getFullYear();
var month = dateTime.getMonth() + 1;
var date = dateTime.getDate();
var hours = dateTime.getHours();
var minutes = dateTime.getMinutes();
var time = "" + year + "-" + month + "-" + date + " " + hours + ":" + minutes;
$('#datetimepicker3').datetimepicker('setStartDate', time, {
    format: 'yyyy-mm-dd hh:ii:ss',
    autoclose: true,
    todayBtn: true,
    todayHighlight: true,
    showMeridian: true,
    pickerPosition: "bottom-left",
    language: 'zh-CN',//中文，需要引用zh-CN.js包
    startView: 2,//月视图
    todayBtn: true,
    minView: 0//日期时间选择器所能够提供的最精确的时间选择视图
});
//上传文件
fileUp();

function fileUp() {
    var uploadLimit = 1;
    $('#uploadify_file').uploadify({
        //指定swf文件
        'swf': 'js/uploadify-v3.1/uploadify.swf',
        //指定显示的id
        'queueID': 'fileQueue',
        //后台处理的页面
        'uploader': PROJECT_NAME + '/web/fileInfo/msgfile',
        //按钮显示的文字
        'buttonText': '上传图片',
        //在浏览窗口底部的文件类型下拉菜单中显示的文本
        'fileTypeDesc': 'Image Files',
        //限制大小
        'fileSizeLimit': '500KB',
        //允许上传的文件后缀
        'fileTypeExts': '*.gif; *.jpg; *.png',
        //限制上传图片张数
        'simUploadLimit': uploadLimit,
        'multi': false,
        'auto': true,
        'multi': true,
        //参数
        'formData': {},
        'onUploadSuccess': function (file, response, data) {
            unloginHandler(data);
            var obj = eval('(' + response + ')');
            var fileName = file.name.replace(',', '');
            var filePath = obj.url;
            $('#imageURL').val(filePath);
            $('#pushView').html('<div class="remove-icon" onclick="delPushImg()"><span class="glyphicon glyphicon-remove"></span></div>');
            $('#pushView').append("" + fileName + "");
            $("#fileQueue").hide();
            $('#pushView').show();
            $("#uploadify_file").hide();
            // $('#uploadify_file').uploadify('settings','uploadLimit', ++uploadLimit);
            $('#uploadify_file').uploadify('cancel', 'SWFUpload_0_0');

        },
        'onUploadError': function (file, errorCode, errorMsg, errorString) {
            layer.msg(errorCode + "." + errorMsg + "" + errorString);

        }
    });
}

function delPushImg() {
    layer.confirm('确定要删除该图片么？', {
        btn: ['确认', '取消'] //按钮
    }, function () {
        var imgUrl = $("#imageURL").val();
        if (imgUrl) {
            $.ajax({
                url: PROJECT_NAME + '/web/fileInfo/deleteOssFile',
                type: 'POST',
                data: {'url': imgUrl},
                success: function (data) {
                    unloginHandler(data);
                    $('#imageURL').val('');
                    $("#pushView").hide();
                    $("#uploadify_file").show();
                    $("#fileQueue").html('').show();
                    //需要添加删除事件
                    layer.msg("删除成功");
                }
            });
        }
    }, function () {
        layer.msg("取消成功");
    })
}

//提交按钮事件
$('.sunmitBtn').click(function () {
    var reg = /(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/;
    var url = $("#detailURL").val();
    var msgSubject = $("#msgSubject").val();
    var msgSubtitle = $("#msgSubtitle").val();
    var msgType = $("#msgType").val();
    if (msgSubject == "") {
        layer.msg('主题不能为空');
        return false;
    }

    if (msgSubject.length > 50) {
        layer.msg('主题太长，重新输入');
        return false;
    }

    if ($("#pushView").css('display') == 'none') {
        layer.msg('图片不能为空');
        return false;
    }
//    if(msgType!='1'){
//        layer.msg('目前只支持“强推”,请重新选择');
//        return false;
//    }
    if ($("#datetimepicker3").val() == "") {
        layer.msg('推送日期不能为空');
        return false;
    }
    if (!reg.test(url)) {
        layer.msg('请输入正确的url');
        return false;
    }
    if (msgSubtitle == "") {
        layer.msg('副标题不能为空');
        return false;
    }

    if (msgSubtitle.length > 150) {
        layer.msg('副标题太长，请重新输入');
        return false;
    }

    $.ajax({
        url: PROJECT_NAME + '/web/msg/doAdd',
        type: 'POST',
        data: $('#addForm').serialize(),
        success: function (data) {
            unloginHandler(data);
            if (data.success) {
                layer.msg(data.message);
                $('#myModal').modal('hide');
                queryEvent("table");
            } else {
                layer.msg(data.message);
            }
        },
        error: function (e) {
            layer.msg('系统出错');
        }
    });
});
