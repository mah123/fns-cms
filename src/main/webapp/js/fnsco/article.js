/**
 * 初始化表格的列
 */
$('#table').bootstrapTable({
    search: false, // 是否启动搜索栏
    sidePagination: 'server',
    url: PROJECT_NAME + '/web/article/query',
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
        field: 'selectItem',
        checkbox: true
    }, {
        title: '文章ID',
        field: 'articleId'
    }, {
        title: '文章标题',
        field: 'articleTitle'
    }, {
        title: '文章内容',
        field: 'articleContent'
    }, {
        title: '文章描述',
        field: 'articleDesc'
    }, {
        title: '文章阅读量',
        field: 'articleReadTimes'
    }, {
        title: '文章作者',
        field: 'articleAuthor'
    }/*, {
        title: '文章状态',
        field: 'articleState'
    }*/]
});

//组装请求参数
function queryParams(params) {
    var param = {
        articleTitle: $('#articleTitle').val(),
        //searchStatus : document.getElementById("search_status").value,
        currentPageNum: this.pageNumber,
        pageSize: this.pageSize,
    }
    return param;
}

// 重置按钮事件
function resetEvent(form, id) {
    $('#' + form)[0].reset();
    $('#' + id).bootstrapTable('refresh');
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

//添加确认事件方法
function add(date) {
    $.ajax({
        url: PROJECT_NAME + '/web/article/add',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(date),
        type: 'POST',
        success: function (data) {
            unloginHandler(data);
            if (data.success) {
                layer.msg('保存成功');
                $('#addModal').modal("hide");
                queryEvent("table");
                layer.close();
                return true;
            } else {
                layer.msg('保存失败');
            }
        }
    });
}

//修改确认事件方法
function edit(date) {
    $.ajax({
        url: PROJECT_NAME + '/web/article/modify',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(date),
        type: 'POST',
        success: function (data) {
            unloginHandler(data);
            if (data.success) {
                layer.msg('修改成功');
                $('#editModal').modal("hide");
                queryEvent("table");
                layer.close();
                return true;
            } else {
                layer.msg('修改失败');
            }
        }
    });
}

//修改信息查询
function queryById(articleId) {
    $.ajax({
        url: PROJECT_NAME + '/web/article/queryById',
        type: 'POST',
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({
            articleId: articleId
        }),
        success: function (data) {
            unloginHandler(data);
            //console.log(data.data);
            // 基本信息
            $('#article_title1').val(data.data.articleTitle);
            $('#article_from1').val(data.data.articleFrom);
            $('#article_author1').val(data.data.articleAuthor);
            //$('#article_content1').val(data.data.articleContent);
            $('#article_desc1').val(data.data.articleDesc);
            KindEditor.html("#article_content1", data.data.articleContent);
            $('#editModal').modal();
        }
    });
}

/*
 * 判断是否选择记录方法
 */
function getArticleId() {
    var select_data = $('#table').bootstrapTable('getSelections');
    if (select_data.length == 0) {
        layer.msg('请选择一条记录!');
        return null;
    }
    if (select_data.length > 1) {
        layer.msg('只能修改一条记录!');
        return null;
    } else {
        return select_data[0].articleId;
    }
}

//表格刷新
function queryEvent(id) {
    $('#' + id).bootstrapTable('refresh');
}


//清除所有表单数据
function clearArticle() {
    $('#article_title').val(null);
    $('#article_from').val(null);
    $('#article_author').val(null);
    KindEditor.html("#article_content", null);
    //$('#article_content').val(null);
    $('#article_desc').val(null);
}

//新增按钮事件
$('#btn_add').click(function () {
    clearArticle();
    $('#addModal').modal();
});
//新增确认按钮事件
$('#btn_yes').click(
    function () {
            var articleTitle = $('#article_title').val();
            var articleFrom = $('#article_from').val();
            var articleAuthor = $('#article_author').val();
            //var articleContent = document.forms['article_content'];
            var articleContent = $('#article_content').val();
            var articleDesc = $('#article_desc').val();
        var date = {
            "articleTitle": articleTitle,
            "articleFrom": articleFrom,
            "articleAuthor" : articleAuthor,
            "articleContent": articleContent,
            "articleDesc" : articleDesc
        };
        if (articleTitle == null || articleTitle.length == 0) {
            layer.msg('文章标题不能为空!');
            return false;
        }
        if (articleFrom == null || articleFrom.length == 0) {
            layer.msg('文章来源不能为空!');
            return false;
        }
        if (articleAuthor == null || articleAuthor.length == 0) {
            layer.msg('文章作者不能为空!');
            return false;
        }
        /*if (articleContent == null || articleContent.length == 0) {
            layer.msg('文章内容不能为空!');
            return false;
        }*/
        if (articleDesc == null || articleDesc.length == 0) {
            layer.msg('文章描述不能为空!');
            return false;
        }
        add(date);
    });
//修改按钮事件
$('#btn_edit').click(function () {
    var articleId = getArticleId();
    if (articleId == null) {
        return;
    }
    queryById(articleId);
});
//修改确认按钮事件
$('#btn_yes1').click(
    function () {
        var articleId = getArticleId();
        if (articleId == null) {
            return;
        }
        var articleTitle = $('#article_title1').val();
        var articleFrom = $('#article_from1').val();
        var articleAuthor = $('#article_author1').val();
        //var articleContent = document.forms['article_content1'];
        var articleContent = $('#article_content1').val();
        var articleDesc = $('#article_desc1').val();
        var date = {
            "articleId" : articleId,
            "articleTitle": articleTitle,
            "articleFrom": articleFrom,
            "articleAuthor" : articleAuthor,
            "articleContent": articleContent,
            "articleDesc" : articleDesc
        };
        if (articleTitle == null || articleTitle.length == 0) {
            layer.msg('文章标题不能为空!');
            return false;
        }
        if (articleFrom == null || articleFrom.length == 0) {
            layer.msg('文章来源不能为空!');
            return false;
        }
        if (articleAuthor == null || articleAuthor.length == 0) {
            layer.msg('文章作者不能为空!');
            return false;
        }
        /*if (articleContent == null || articleContent.length == 0) {
            layer.msg('文章内容不能为空!');
            return false;
        }*/
        if (articleDesc == null || articleDesc.length == 0) {
            layer.msg('文章描述不能为空!');
            return false;
        }
        layer.confirm('确定修改选中数据吗？', {
            time: 20000, //20s后自动关闭
            btn: ['确定', '取消']
        }, function () {
            edit(date);
        }, function () {
            layer.msg('取消成功');
        });
    });
//删除按钮事件
$('#btn_delete').click(function () {
    var select_data = $('#table').bootstrapTable('getSelections');
    if (select_data.length == 0) {
        layer.msg('请选择一条记录!');
        return false;
    }
    if (select_data.length > 1) {
        layer.msg('只能删除一条记录!');
        return false;
    }
    var articleId = select_data[0].articleId;
    /*var dataId = [];
    for (var i = 0; i < select_data.length; i++) {
        dataId = dataId.concat(select_data[i].id);
    }*/
    layer.confirm('确定删除选中数据吗？', {
        time: 20000, //20s后自动关闭
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: PROJECT_NAME + '/web/article/delete',
            type: 'POST',
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                articleId: articleId
            }),
            success: function (data) {
                unloginHandler(data);
                console.log(data);
                if (data.success) {
                    layer.msg('删除成功');
                    queryEvent("table");
                } else {
                    layer.msg(data.message);
                }
            },
            error: function (e) {
                layer.msg('系统异常!' + e);
            }
        });
    }, function () {
        layer.msg('取消成功');
    });
});
