//初始化日期组件
$('#datetimepicker1').datetimepicker({
    format: 'yyyy-mm-dd',
    autoclose: true,
    todayBtn: true,
    todayHighlight: true,
    showMeridian: true,
    pickerPosition: "bottom-left",
    language: 'zh-CN',// 中文，需要引用zh-CN.js包
    startView: 2,// 月视图
    minView: 2
// 日期时间选择器所能够提供的最精确的时间选择视图
});
$('#datetimepicker2').datetimepicker({
    format: 'yyyy-mm-dd',
    autoclose: true,
    todayBtn: true,
    todayHighlight: true,
    showMeridian: true,
    pickerPosition: "bottom-left",
    language: 'zh-CN',// 中文，需要引用zh-CN.js包
    startView: 2,// 月视图
    minView: 2
// 日期时间选择器所能够提供的最精确的时间选择视图
});
// 初始化表格
$('#table').bootstrapTable({
    search: false, // 是否启动搜索栏
    sidePagination: 'server',
    url: PROJECT_NAME + '/web/syssuggest/query',
    showRefresh: false,// 是否显示刷新按钮
    showPaginationSwitch: false,// 是否显示 数据条数选择框(分页是否显示)
    toolbar: '#toolbar', // 工具按钮用哪个容器
    striped: true, // 是否显示行间隔色
    cache: false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
    pagination: true, // 是否显示分页（*）
    sortable: true, // 是否启用排序
    sortOrder: "asc", // 排序方式
    pageNumber: 1, // 初始化加载第一页，默认第一页
    pageSize: 15, // 每页的记录行数（*）
    pageList: [15, 20, 50, 100], // 可供选择的每页的行数（*）
    queryParams: queryParams,
    responseHandler: responseHandler,// 处理服务器返回数据
    columns: [{
        field: 'id',
        title: '操作',
        width: '10%',
        align: 'center',
        width: 150,
        formatter: operateFormatter
    }, {
        field: 'index',
        title: '序号',
        width: '10%',
        align: 'center',
        width: 150,
        formatter: formatindex
    }, {
        field: 'mobile',
        title: '联系电话',
        width: '10%'
    }, {
        field: 'content',
        title: '反馈内容',
        formatter: formatContent

    }, {
        field: 'userName',
        title: '提交人'
    }, {
        field: 'submitTime',
        title: '提交时间',
        formatter: formatTime
    }]
});

// 组装请求参数
function queryParams(params) {
    var param = {
        currentPageNum: this.pageNumber,
        pageSize: this.pageSize,
        startTime: $('#datetimepicker1').val(),
        endTime: $('#datetimepicker2').val()
    }
    return param;
}

// 处理后台返回数据
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

// 操作格式化
function operateFormatter(value, row, index) {
    return [
        '<a class="redact" href="javascript:querySingle(' + value
        + ');" title="详情">',
        '<i class="glyphicon glyphicon-file"></i>', '</a>  '].join('');
}

function formatindex(value, row, index) {
    return [index + 1].join('');
}

//
function formatContent(value, row, index) {
    if (value && value.length > 50) {
        return value.substr(0, 50) + '...';
    }
    return value;
}

// 条件查询按钮事件
function queryEvent(id) {
    $('#' + id).bootstrapTable('refresh');
}

// 查询本周按钮事件
function queryweek(id) {
    $('#datetimepicker2').val("");
    $('#datetimepicker1').val(timeUtil.getFirstDayOfMonth());
    $('#' + id).bootstrapTable('refresh');
    $('#datetimepicker1').val("");
}

//查询本月按钮事件
function querymonth(id) {
    $('#datetimepicker2').val("");
    $('#datetimepicker1').val(timeUtil.getFirstDayOfMonth());
    $('#' + id).bootstrapTable('refresh');
    $('#datetimepicker1').val("");
}

// 重置按钮事件
function resetEvent(form, id) {
    $('#' + form)[0].reset();
    $('#' + id).bootstrapTable('refresh');
}

// 时间格式化
function formatTime(value, row, index) {
    return formatDateUtil(new Date(value));
}

//根据id查询详情
function querySingle(id) {
    $.ajax({
        url: PROJECT_NAME + '/web/syssuggest/querySuggestInfo',
        type: 'POST',
        dataType: "json",
        data: {
            'id': id
        },
        success: function (data) {
            unloginHandler(data);
            // data.data就是所有数据集
            console.log(data.data);

            // 基本信息
            $('input[name=mobile]').val(data.data.mobile);
            $('input[name="userName"]').val(data.data.userName);
            var subTime = data.data.submitTime;
            if (subTime) {
                subTime = subTime.substr(0, subTime.length - 2);
            }
            $('input[name="submitTime"]').val(subTime);
            $('textarea[name="content"]').val(data.data.content);

            $('#detailsModal').modal();
            // 全部默认不可编辑
            $("#detailsModal").find('input').attr('disabled', true);
            $("#detailsModal").find('select').attr('disabled', true);
            $("#detailsModal").find('textarea').attr('disabled', true);
            $("#detailsModal .subBankName").attr('readonly', false);
            $(".remove-icon").hide();
            $(".btn-addList").hide();
            $(".deletefileImg").hide();
            $(".uploadify").hide();

        }
    });
}

var timeUtil = {
    // 获取本星期第一天
    getFirstDayOfWeek: function () {
        var now = new Date();
        var day = now.getDate(), // 获取本月几号
            weekday = now.getDay(0), // 获取星期几
            month = now.getMonth(), // 获取本月
            year = now.getFullYear();// 获取本年

        return this.timeFormate(now);
    },

    // 获取本月第一天
    getFirstDayOfMonth: function () {
        var now = new Date();
        var day = now.setDate(1);

        return this.timeFormate(now);
    },

    // 时间格式化
    timeFormate: function (date) {
        if (!date || typeof (date) === "string") {
            this.error("参数异常，请检查...");
        }
        var year = date.getFullYear(); // 年
        var month = date.getMonth() + 1; // 月
        var day = date.getDate(); // 日

        if (date.getMinutes() / 60 > 1) {
            hh += Math.floor(date.getMinutes()) / 60;
        }
        var clock = year + "-";
        if (month < 10)
            clock += "0";
        clock += month + "-";
        if (day < 10)
            clock += "0";
        clock += day + " ";
        return clock;
    }
}