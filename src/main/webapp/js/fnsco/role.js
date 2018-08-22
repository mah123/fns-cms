var ttable;
initTableData();

function initTableData() {
    ttable = $('#table').bootstrapTable({
        sidePagination: 'server',
        method: 'get',//提交方式
        search: false, // 是否启动搜索栏
        url: PROJECT_NAME + '/web/auth/role/list',
        showRefresh: true,// 是否显示刷新按钮
        showPaginationSwitch: false,// 是否显示 数据条数选择框(分页是否显示)
        striped: true, // 是否显示行间隔色
        cache: false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true, // 是否显示分页（*）
        sortable: true, // 是否启用排序
        uniqueId: 'roleId', //将index列设为唯一索引
        sortOrder: "asc", // 排序方式
        pageNumber: 1, // 初始化加载第一页，默认第一页
        pageSize: 15, // 每页的记录行数（*）
        singleSelect: true,// 单选
        pageList: [15, 20, 50, 100], // 可供选择的每页的行数（*）
        queryParams: queryParams,
        responseHandler: responseHandler,// 处理服务器返回数据
        columns: [{
            field: 'state',
            radio: 'true',//单选框
            rowspan: 1,
            align: 'center',
            valign: 'middle'
        }, {
            field: 'roleId',
            title: '角色ID',
            width: '10%',
            align: 'center',
            width: 20
        }, {
            width: 100,
            field: 'roleName',
            title: '角色名称',
            width: 60
        }, {
            field: 'roleDesc',
            title: '备注',
            formatter: formatRemark
        }, {
            field: 'roleCreateTime',
            title: '创建时间',
            width: 30,
            //获取日期列的值进行转换
            formatter: function (value, row, index) {
                return changeDateFormat(value)
            }
        }]
    });
}

//转换日期格式(时间戳转换为datetime格式)
function changeDateFormat(cellval) {
    var dateVal = cellval + "";
    if (cellval != null) {
        var date = new Date(parseInt(dateVal.replace("/Date(", "").replace(")/", ""), 10));
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();

        var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();

        return date.getFullYear() + "-" + month + "-" + currentDate + " " + hours + ":" + minutes + ":" + seconds;
    }
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

//
function formatRemark(value, row, index) {
    if (value && value.length > 30) {
        return value.substr(0, 30) + '...';
    }
    return value;
}

// 组装请求参数
function queryParams(params) {
    var param = {
        currentPageNum: this.pageNumber,
        pageSize: this.pageSize,
        roleId: $('#txt_search_roleId').val(),
        roleName: $('#txt_search_roleName').val()
    }
    return param;
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

//清除所有表单数据
function clearDate() {
    $('#roleId').val(null);//角色ID，新增无此值，修改此值必须有值
    $('#roleName').val(null);
    $('#deptId').val(null);
    $('#deptName').val(null);
    $('#remark').val(null);
}

/***************************** 新增add ********************************************/
//菜单树， 获取菜单树数据
var menu_setting = {
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
    },
    check: {
        // 设置是否显示checkbox复选框
        enable: true,
        nocheckInherit: true
    }
};

function getMenuTreeAdd(id) {

    // 加载权限树，组包发给后台
    $.ajax({
        type: 'get',
        url: PROJECT_NAME + "/web/auth/menu/list",
        async: false,//同步获取数据
        success: function (data) {
            if (data.success) {
                var menu_ztree = $.fn.zTree.init($("#menuTree"), menu_setting, data.data.list);

                //全部取消勾选
                menu_ztree.checkAllNodes(false);

                //id不为空，表示修改；否则表示新增
                if (id != null) {

                    //修改，则需将当前所选的菜单展示出来
                    for (var i = 0; i < id.length; i++) {

                        //指定节点id选中
                        var node = menu_ztree.getNodeByParam("id", id[i], null);
                        if (node != null) {
                            menu_ztree.checkNode(node, true);// 勾选指定ID的节点
//							menu_ztree.expandNode(node, true, false);// 指定ID节点展开
                        }
                    }
                    //展开所有节点
                    menu_ztree.expandAll(true);
                } else {
                    //展开所有节点
                    menu_ztree.expandAll(false);
                }
            } else if (!data.success) {
                layer.msg(data.message);
            }
        }
    });
}

//数据权限树
var data_setting = {
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
    },
    check: {
        enable: true,
        nocheckInherit: true,
        chkboxType: {
            "Y": "",
            "N": ""
        }
    }
};

//function getDataTreeAdd(id) {

// 	// 加载权限树，组包发给后台
//     $.ajax({
//         type: 'get',
//         url: PROJECT_NAME + "/web/auth/dept/queryNoRoottree",
//         async: false,//同步获取数据
//         success: function (data) {
//             if (data.success) {
//                 var data_ztree = $.fn.zTree.init($("#dataTree"), data_setting, data.data);
//
//                 //全部取消勾选
//                 data_ztree.checkAllNodes(false);
//
//                 //ID不为空，表示修改；否则表示新增
//                 if (id != null) {
//                     //修改，则需将当前所选的数据权限展示出来
//                     for (var i = 0; i < id.length; i++) {
//                         //指定节点id选中
//                         var node = data_ztree.getNodeByParam("id", id[i], null);
//                         if (node != null) {
//                             data_ztree.checkNode(node, true);// 勾选指定ID的节点
// //							data_ztree.expandNode(node, true, false);// 指定ID节点展开
//                         }
//                     }
//                     //展开所有节点
//                     data_ztree.expandAll(true);
//                 } else {
//                     //不展开所有节点
//                     data_ztree.expandAll(false);
//                 }
//             } else if (!data.success) {
//                 layer.msg(data.message);
//             }
//         }
//     });
// }

// //所属部门树
// var d_setting = {
//     data: {
//         simpleData: {
//             enable: true,
//             idKey: "id",
//             pIdKey: "parentId",
//             rootPId: -1
//         },
//         key: {
//             url: "nourl"
//         }
//     }
// };
//
// function getDeptTree(id) {
//     // 组包发给后台
//     $.ajax({
//         type: 'get',
//         url: PROJECT_NAME + "/web/auth/dept/queryNoRoottree",
//         async: false,//同步获取数据
//         success: function (data) {
//             if (data.success) {
//                 var dd_ztree = $.fn.zTree.init($("#deptTree"), d_setting, data.data);
//
//                 //全部取消勾选
//                 dd_ztree.checkAllNodes(false);
//
//                 //ID不为空，表示修改；否则表示新增
//                 if (id != null) {
//                     //修改，则需将当前所选指定节点id选中
//                     var node = dd_ztree.getNodeByParam("id", id, null);
//                     if (node != null) {
//                         dd_ztree.selectNode(node, true);// 指定选中ID的节点
// //						dd_ztree.expandNode(node, true, false);// 指定选中ID节点展开
//                     }
//                 }
//
//                 //展开所有节点
//                 dd_ztree.expandAll(true);
//             } else if (!data.success) {
//                 layer.msg(data.message);
//             }
//         },
//         error: function (data) {
//             layer.msg("操作失败");
//         }
//     });
// }
//
// // //点击所属部门弹窗,弹出ztreeId的窗体
// function deptTreeGet() {
//
//     // 获取所属部门树
//     var add_deptTree = $.fn.zTree.getZTreeObj('deptTree');
//
//     layer.open({
//         type: 1,
//         offset: '50px',
//         skin: 'layui-layer-molv',
//         title: "选择部门",
//         area: ['300px', '450px'],
//         shade: 0,
//         shadeClose: false,
//         content: jQuery('#deptLayer'),
//         btn: ['确定', '取消'],
//         btn1: function (index) {
//
//             var node = add_deptTree.getSelectedNodes();
//             if (node.length != 0) {
//                 $('#deptName').val(node[0].name);
//                 $('#deptId').val(node[0].id);
//                 layer.close(index);
//             } else {
//                 layer.msg('请选择所属部门!');
//             }
//         }
//     });
// }

//点击增加按钮事件
$('#btn_add').click(function () {

    //加载完成之后，将所有数据清掉
    clearDate();

    $('h4').html('角色新增');

    getMenuTreeAdd(null);

    // getDataTreeAdd(null);

    // getDeptTree(null);
})

/********* 修改edit*************************************************/
//点击修改按钮
$('#btn_edit').click(function () {

    // 找到当前table选择的行
    var selectContent = ttable.bootstrapTable('getSelections');// 返回的是数组类型,Array

    // 判断当前选择的数据行，若为0代表没有选择，提示请选择一列数据
    if (selectContent.length == 0) {
        layer.msg('请选择一条数据!');
        return false;
    } else {

        $('h4').html('角色修改');

        // 获取table选中数据的部门ID
        $('#roleId').val(selectContent[0].roleId);
        $('#roleName').val(selectContent[0].roleName);
        $('#remark').val(selectContent[0].roleDesc);
        //$('#deptName').val(selectContent[0].deptName);
        //$('#deptId').val(selectContent[0].deptId);

        //var deptId = selectContent[0].deptId;
        var menuList = [];
        var dataList = [];
        menuList = selectContent[0].menuIdList;//菜单树
        //dataList = selectContent[0].deptIdList;//数据权限树

        getMenuTreeAdd(menuList);

        //getDataTreeAdd(dataList);

        //getDeptTree(deptId);

        // 获取所属部门树数据对象
        //edit_deptTree = $.fn.zTree.getZTreeObj("deptTree");

        console.info(selectContent);

    }
})

/****** 点击保存按钮 *****/
// 点击保存按钮,获取选择的数据以及输入框的数据，组包发给后台
$('#btn_save').click(function () {
    saveOrUpdate();
})

function saveOrUpdate() {

    var roleId = $('#roleId').val();
    var url;

    //为空表示新增；否则表示修改
    if (roleId == "") {
        url = "/web/auth/role/add";
        roleId = null;
    } else {
        url = "/web/auth/role/update";
    }

    //角色名称  校验，不能为空
    if (!$('#roleName').val()) {
        layer.msg('请输入有效角色名称!');
        return;
    }

    //角色名称  校验，不能超过100
    if ($('#roleName').val().length > 20) {
        layer.msg('角色名称超长，请不要超过20!');
        return;
    }

    //部门选择不能为空
    /* if (!$('#deptId').val()) {
         layer.msg('请选择部门!');
         return;
     }*/

    //备注长度判断
    if ($('#remark').val().length > 100) {
        layer.msg('备注超长，请不要超过100!');
        return;
    }

    // 获取选择的菜单树数据
    var m_ztree = $.fn.zTree.getZTreeObj('menuTree');// 获取菜单树对象
    var nodes = m_ztree.getCheckedNodes(true);
    var menuIdList = [];
    if (nodes.length > 0) {
        for (var i = 0; i < nodes.length; i++) {
            menuIdList[i] = nodes[i].id;
        }
    } else {
        layer.msg("请选择一个菜单");
        return false;
    }

    // 获取数据权限树的数据
    /*var da_tree = $.fn.zTree.getZTreeObj('dataTree');// 获取数据权限树对象
    var nodes = da_tree.getCheckedNodes(true);
    var deptIdList = [];
    if (nodes.length > 0) {
        for (var i = 0; i < nodes.length; i++) {
            deptIdList[i] = nodes[i].id;
        }
    } else {
        layer.msg("请选择一个数据权限");
        return false;
    }*/

    //获取部门ID数据
    //var de_tree = $.fn.zTree.getZTreeObj(dataTree);//获取部门树对象

    var param = {
        'menuIdList': menuIdList,
        //'deptIdList': deptIdList,
        'roleId': $('#roleId').val(),// 新增角色的roleId自动生成,没有此项
        //'deptId': $('#deptId').val(),
        'roleName': $('#roleName').val(),
        'roleDesc': $('#remark').val()
    }

    // 组包发给后台
    $.ajax({
        type: 'get',
        url: PROJECT_NAME + url,
        data: param,
        traditional: true,//提交带数组类型必须设置true
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
            time: 2000,
            btn: ['确定', '取消']
        }, function () {
            // 获取table选中数据的角色ID
            var roleId = selectContent[0].roleId;

            var param = {
                'roleId': roleId
            }

            // 组包发给后台
            $.ajax({
                type: 'POST',
                url: PROJECT_NAME + "/web/auth/role/delete",
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
            layer.mas('取消成功');
        });
    }
})