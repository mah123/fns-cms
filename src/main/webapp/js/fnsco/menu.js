/**页面加载时初始化表格**/
var ttable;
initTableData();

function initTableData() {
    ttable = $('#table').bootstrapTable({
        sidePagination: 'server',
        method: 'get',//提交方式
        search: false, // 是否启动搜索栏
        url: PROJECT_NAME + '/web/auth/menu/list',
        showRefresh: true,// 是否显示刷新按钮
        showPaginationSwitch: false,// 是否显示 数据条数选择框(分页是否显示)
//		toolbar : '#toolbar', // 搜索框绑定
        striped: true, // 是否显示行间隔色
        cache: false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true, // 是否显示分页（*）
        sortable: true, // 是否启用排序
        uniqueId: 'roleId', // 将index列设为唯一索引
        sortOrder: "asc", // 排序方式
        singleSelect: true,// 单选
        pageNumber: 1, // 初始化加载第一页，默认第一页
        pageSize: 15, // 每页的记录行数（*）
        pageList: [15, 20, 50, 100], // 可供选择的每页的行数（*）
        queryParams: queryParams,
        responseHandler: responseHandler,// 处理服务器返回数据
        columns: [{
            field: 'state',
            radio: 'true',//单选框
            rowspan: 1,
            align: 'center',
            width: 20,
            valign: 'middle'
        }, {
            field: 'id',
            title: '菜单ID',
            width: '10%',
            align: 'center',
            width: 40
        }, {
            width: 100,
            field: 'name',
            title: '菜单名称',
            width: 80
        }, {
            field: 'parentName',
            title: '上级菜单',
            width: 80
        }, {
            field: 'icon',
            title: '图标',
            width: 40,
            formatter: formatIcon
        }, {
            field: 'type',
            title: '类型',
            width: 40,
            formatter: formatType
        }, {
            field: 'orderNum',
            title: '排序号',
            width: 40
        }
            , {
                field: 'url',
                title: '菜单URL',
                width: 60
            }, {
                field: 'perms',
                title: '授权标识'
            }]
    });
}

// 处理后台返回数据
function responseHandler(res) {
    unloginHandler(res);
    if (res.data) {
        return {
            "rows": res.data.list,
            "total": res.data.total
        };
    } else {
        return {
            "rows": [],
            "total": 0
        };
    }
}

// 组装请求参数
function queryParams(params) {
    var param = {
        currentPageNum: this.pageNumber,
        pageSize: this.pageSize
    }
    return param;
}

function formatIcon(value, row, index) {
    if (row.type != 0) {
        return '';
    } else {
        return value == null ? '' : '<i class="' + value + ' fa-lg"></i>';
    }
}

function formatType(value, row, index) {
    if (value === 0) {
        return '<span class="label label-primary">目录</span>';
    }
    if (value === 1) {
        return '<span class="label label-success">菜单</span>';
    }
    if (value === 2) {
        return '<span class="label label-warning">按钮</span>';
    }
}

// 条件查询按钮事件
function queryEvent(id) {
    console.log("id:" + id);
    $('#' + id).bootstrapTable('refresh');
}

// 重置按钮事件
function resetEvent(form, id) {
    $('#' + form)[0].reset();
    $('#' + id).bootstrapTable('refresh');
}

//清除已经填写数据,重置单选按钮
function clearInput() {
    $('#id').val(null);
    $('#menuName').val(null);
    $('#parentId').val(null);
    $('#parentName').val(null);
    $('#menuUrl').val(null);
    $('#perms').val(null);
    $('#orderNum').val(null);
    $('#icon').val(null);
    $('#parentName').val(null);
    $('#parentName').val(null);
    $(':radio').attr('checked', false);
}

//绑定按钮点击事件(目录/菜单/按钮所展示的页面不同)
$(':radio').click(function () {
    if (this.checked) {
        //如果目录选中
        if (this.value == "0") {
            $('#id').html('目录名称');
            $('#menuNameDiv').show();
            $('#parentNameDiv').show();
            $('#menuUrlDiv').hide();
            $('#permsDiv').hide();
            $('#orderNumDiv').show();
            $('#iconDiv').show();
            //如果菜单选中
        } else if (this.value == "1") {
            $('#id').html('菜单名称');
            $('#menuNameDiv').show();
            $('#parentNameDiv').show();
            $('#menuUrlDiv').show();
            $('#permsDiv').show();
            $('#orderNumDiv').show();
            $('#iconDiv').hide();
            //如果按钮选中
        } else if (this.value == "2") {
            $('#id').html('按钮名称');
            $('#menuNameDiv').show();
            $('#parentNameDiv').show();
            $('#menuUrlDiv').hide();
            $('#permsDiv').show();
            $('#orderNumDiv').hide();
            $('#iconDiv').hide();
        }
    }
});

//菜单数树
function getMenuTree(id) {

    var m_setting = {
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "parentId",
                rootPId: -1
            },
            key: {
                url: "nourl"
            }
        }
    };

    // 组包发给后台
    $.ajax({
        type: 'POST',
        url: PROJECT_NAME + "/web/auth/menu/select",
        async: false,//同步加载
        success: function (data) {
            if (data.success) {
                var dd_ztree = $.fn.zTree.init($("#upMenuTree"), m_setting, data.data);

                //清空所有节点勾选
                dd_ztree.checkAllNodes(false);

                //id不等于空，表示修改
                if (id != null) {
                    //指定节点id选中
                    var node = dd_ztree.getNodeByParam("id", id);
                    if (node != null) {
                        dd_ztree.selectNode(node, true);// 指定选中ID的节点
//						dd_ztree.expandNode(node, true, false);// 指定选中ID节点展开
                    }
                }

                //展开所有节点
                dd_ztree.expandAll(true);
            } else if (!data.success) {
                layer.msg(data.message);
            }
        },
        error: function (data) {
            layer.msg("操作失败");
        }
    });
}

//点击一级菜单，弹出非按钮菜单树
function MenuTreeGet() {

    // 获取菜单树操作对象
    var add_deptTree = $.fn.zTree.getZTreeObj('upMenuTree');

    layer.open({
        type: 1,
        offset: '50px',
        skin: 'layui-layer-molv',
        title: "选择菜单",
        area: ['300px', '450px'],
        shade: 0,
        shadeClose: false,
        content: jQuery('#upMenuLayer'),
        btn: ['确定', '取消'],
        btn1: function (index) {

            //获取选中的上级部门的ID和名称，并赋值给相应的ID，以便后续读取使用
            var node = add_deptTree.getSelectedNodes();
            if (node.length != 0) {
                $('#parentName').val(node[0].name);
                $('#parentId').val(node[0].id);
                layer.close(index);
            } else {
                layer.msg('请选择父菜单!');
            }
        }
    });
}

/***** 新增add *******/
//点击增加按钮事件
$('#btn_add').click(function () {

    $('h4').html('新增菜单');

    //先清掉相关数据，设置menuType默认选中,并展示相应菜单
    clearInput();
    $('input[id=menuType]').prop('checked', 'checked');//固有属性用prop
    $('#id').html('菜单名称');
    $('#menuNameDiv').show();
    $('#parentNameDiv').show();
    $('#menuUrlDiv').show();
    $('#permsDiv').show();
    $('#orderNumDiv').show();
    $('#iconDiv').hide();

    //给当前菜单ID置空,防止与修改功能串线
    $('#id').val(null);

    //获取菜单树形结构(传空表示新增，不带父节点ID)
    getMenuTree(null);
})

/******* 修改edit*******/
//点击修改按钮
$('#btn_edit').click(function () {

    // 找到当前table选择的行
    var selectContent = ttable.bootstrapTable('getSelections');// 返回的是数组类型,Array

    // 判断当前选择的数据行，若为0代表没有选择，提示请选择一列数据
    if (selectContent.length == 0) {
        layer.msg('请选择一条数据!');
        return false;
    } else {

        $('h4').html('修改菜单');

        // 获取table选中数据的父菜单ID
        var parentId = selectContent[0].parentId;

        //获取树形结构，传入当前选中的父菜单ID，便于在菜单树形结构展示选中点
        getMenuTree(parentId);

        //给当前菜单ID赋值,表示当前使用功能是修改
        $('#id').val(selectContent[0].id);

        $('#menuName').val(selectContent[0].name);
        $('#parentId').val(selectContent[0].parentId);
        $('#parentName').val(selectContent[0].parentName);
        $('#menuUrl').val(selectContent[0].url);
        $('#perms').val(selectContent[0].perms);
        $('#orderNum').val(selectContent[0].orderNum);
        $('#icon').val(selectContent[0].icon);

        //设置单选按钮的值
        $("input[name=team]").each(function () {
            if (this.value == selectContent[0].type) {

                this.checked = true;//修改默认选中
                if (this.value == "0") {
                    $('#id').html('目录名称');
                    $('#menuNameDiv').show();
                    $('#parentNameDiv').show();
                    $('#menuUrlDiv').hide();
                    $('#permsDiv').hide();
                    $('#orderNumDiv').show();
                    $('#iconDiv').show();
                } else if (this.value == "1") {
                    $('#id').html('菜单名称');
                    $('#menuNameDiv').show();
                    $('#parentNameDiv').show();
                    $('#menuUrlDiv').show();
                    $('#permsDiv').show();
                    $('#orderNumDiv').show();
                    $('#iconDiv').hide();
                } else if (this.value == "2") {
                    $('#id').html('按钮名称');
                    $('#menuNameDiv').show();
                    $('#parentNameDiv').show();
                    $('#menuUrlDiv').hide();
                    $('#permsDiv').show();
                    $('#orderNumDiv').hide();
                    $('#iconDiv').hide();
                }
            }
        });

    }
})

/******************** 点击保存按钮 *************************************/
// 点击保存按钮,获取选择的数据以及输入框的数据，组包发给后台
$('#btn_save').click(function () {
    saveOrUpdate();
})

//保存/修改，点击确定按钮触发该函数
function saveOrUpdate() {

    var id = $('#id').val();
    var url;
    //如果当前菜单ID为空，则表示新增数据,否则为修改
    if (id == "") {
        url = "/web/auth/menu/add";
    } else {
        url = "/web/auth/menu/update";
    }

    // 菜单名称 校验，不能为空
    if (!$('#menuName').val()) {
        layer.msg('请输入有效名称!');
        return;
    }

    // 获取菜单树数据的父菜单ID
    if (!$('#parentId').val()) {
        layer.msg('请选择上级菜单!');
        return;
    }

    // 获取菜单树数据的父菜单名称
    if (!$('#parentName').val()) {
        layer.msg('请选择上级菜单!');
        return;
    }

    var param = {
        'id': $('#id').val(),//当前菜单ID(修改时会带这个值)
        'name': $('#menuName').val(),//菜单名称
        'parentId': $('#parentId').val(),
        'url': $('#menuUrl').val(),
        'perms': $('#perms').val(),
        'type': $('input[name="team"]:checked').val(),
        'icon': $('#icon').val(),
        'orderNum': $('#orderNum').val()
    }

    // 组包发给后台
    $.ajax({
        type: 'POST',
        url: PROJECT_NAME + url,
        data: param,
        success: function (data) {
            unloginHandler(data);
            if (data.success) {
                layer.msg("操作成功");
                $('#myModal').modal("hide");
                $("body").removeClass("modal-open");
                queryEvent("table");

            } else if (!data.success) {
                layer.msg(data.message);
            }
        },
        error: function (data) {
            layer.msg("操作失败");
        },
        exception: function () {
            layer.mag("异常报错");
        }
    });
    //alert("请手动刷新页面")
}

/**点击删除按钮**/
$('#btn_delete').click(function () {

    // 找到当前table选择的行
    var selectContent = ttable.bootstrapTable('getSelections');// 返回的是数组类型,Array
    if (selectContent.length == 0) {
        layer.msg('请选择一条数据!');
        return false;
    } else {

        layer.confirm('确定删除选中数据吗？', {
            time: 20000, // 20s后自动关闭
            btn: ['确定', '取消']
        }, function () {
            // 获取table选中数据的菜单ID和菜单类型
            var id = selectContent[0].id;
            var type = selectContent[0].type;

            var param = {
                'id': id,
                'type': type
            }

            // 组包发给后台
            $.ajax({
                type: 'POST',
                url: PROJECT_NAME + "/web/auth/menu/delete",
                data: param,
                traditional: true,
                success: function (data) {
                    unloginHandler(data);
                    if (data.success) {
                        layer.msg("删除成功");
                        queryEvent("table");
                    } else if (!data.success) {
                        layer.msg(data.message);
                    }
                },
                error: function (data) {
                    layer.msg("删除失败");
                },
                exception: function () {
                    layer.mag("异常报错");
                }
            });

        }, function () {
            layer.msg('取消成功');
        });
    }
})