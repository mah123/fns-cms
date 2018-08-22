/**
 * 初始化表格的列
 */
$('#table').bootstrapTable({
    search: false, // 是否启动搜索栏
    sidePagination: 'server',
    url: json.auth_user_query,
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
        title: '用户ID',
        field: 'userId'
    }, {
        title: '账号',
        field: 'userAccount'
    }, {
        title: '状态',
        field: 'userState',
        formatter: formatstatus
    }]
});

//正常禁用图形化显示
function formatstatus(value, row, index) {
    return value === 2 ?
        '<span class="label label-danger">禁用</span>' :
        '<span class="label label-primary">正常</span>';
}

//组装请求参数
function queryParams(params) {
    var param = {
        currentPageNum: this.pageNumber,
        pageSize: this.pageSize,
        userAccount: $('#name').val()
    }

    return param;
}

// 处理后台返回数据
function responseHandler(res) {
    unloginHandler(res);
    if (res) {
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

//添加确认事件方法
function add(date) {
    $.ajax({
        traditional: true,
        url: json.auth_user_toAdd,
        data: date,
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
        traditional: true,
        url: json.auth_user_toEdit,
        data: date,
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
                layer.msg(data.message);
            }
        }
    });
}

//查询账号是否重复
var isdata;

function queryByName(name) {
    $.ajax({
        url: json.auth_user_queryUserByName,
        type: 'POST',
        dataType: "json",
        async: false,//同步获取数据
        data: {
            'userAccount': name
        },
        success: function (data) {
            unloginHandler(data);
            isdata = data;
        }
    });
}

//请求所有代理商数据
// function requestAgent(type){
// 	 $.ajax({
// 		   url:json.web_merchantinfo_queryAgents,
// 		   type:'POST',
// 		   success:function(data){
// 			   unloginHandler(data);
// 			   console.log(data);
// 			   var agtS = data.data;
// 			   var html_opt = '';
// 			   $.each(agtS,function(index,value){
// 				   if(type && type == value.id){
// 					   html_opt += '<option value="'+value.id+'" selected ="selected">'+value.name+'</option>';
// 				   }else{
// 					   html_opt += '<option value="'+value.id+'">'+value.name+'</option>';
// 				   }
// 			   })
// 			   $('#agentId').html(html_opt);
// 			   $('#agentId1').html(html_opt);
// 		   },
// 		   error:function(e){
// 			   layer.msg('服务器出错');
// 		   }
// 	   })
// }


//修改信息查询
function queryById(id) {

    $.ajax({
        url: json.auth_user_queryUserById,
        type: 'POST',
        dataType: "json",
        data: {
            'userId': id
        },
        success: function (data) {
            unloginHandler(data);
            // data.data就是所有数据集
            //console.log(data.data);
            // 基本信息
            $("#username1").val(data.data.userAccount);
            $("#realname1").val(data.data.userRealName);
            $("#mobile1").val(data.data.userTelphone);
            $("#sex1").val(data.data.userSex);
            $("#aliasname1").val(data.data.userNickName);
            //$("#department1").val(data.data.department);
            //$("#agentId1").val(data.data.agentId);
            $("#status1").val(data.data.userState);
            //$("#remark1").val(data.data.remark);
            var list = data.data.roleList;
            console.log(list)
            //角色展示下拉框！！！！！！！！！
            for (var i = 0; i < list.length; i++) {
                var l = list[i].roleId;
                $("input[type='checkbox'][value=" + l + "]").prop('checked', 'true');
            }

            $('#editModal').modal();
            $('#sex1hide').hide();
            $('#agentId1hide').hide();
            $('#aliasname1hide').hide();
            $('#mobile1hide').hide();
            $('#remark1hide').hide();
        }
    });
}

/*
 * 判断是否选择记录方法
 */
function getUserId() {
    var select_data = $('#table').bootstrapTable('getSelections');
    if (select_data.length == 0) {
        layer.msg('请选择一条记录!');
        return null;
    }
    if (select_data.length > 1) {
        layer.msg('只能修改一条记录!');
        return null;
    } else {
        return select_data[0].userId;
    }
}

//部门结构树
// var dept_ztree;
// var dept_setting = {
// 	data : {
// 		simpleData : {
// 			enable : true,
// 			idKey : "id",
// 			pIdKey : "parentId",
// 			rootPId : -1
// 		},
// 		key : {
// 			url : "nourl"
// 		}
// 	}
// };
//初始化树方法
// function getDept() {
// 	$.ajax({
// 		url :json.auth_user_querytree,
// 		//type : 'POST',
// 		success : function(data) {
// 			dept_ztree = $.fn.zTree.init($("#deptTree"), dept_setting,data.data);
// 			// data.data就是所有数据集
// 		},
// 		error : function(data) {
// 			alert(" 数据加载失败！" + data);
// 		}
// 	});
// }


//部门选择框确认方法
// function dotext(a) {
// 	getDept();
// 	layer.open({
// 		type : 1,
// 		offset : '50px',
// 		skin : 'layui-layer-molv',
// 		title : "选择部门",
// 		area : [ '300px', '450px' ],
// 		shade : 0,
// 		shadeClose : false,
// 		content : jQuery("#deptLayer"),
// 		btn : [ '确定', '取消' ],
// 		btn1 : function(index) {
// 			var node = dept_ztree.getSelectedNodes();
// 			//选择上级部门
// 			name = node[0].name;
// 			id = node[0].id;
// 			if(a==1){
// 				$("#department").val(name);
// 				$("#departId").val(id);
// 			}else if(a==2){
// 				$("#department1").val(name);
// 				$("#departId1").val(id);
// 			}
// 			layer.close(index);
// 		}
// 	});
// }


//查询条件按钮事件
function queryEvent(id) {
    $('#' + id).bootstrapTable('refresh');
}

//重置按钮事件
function resetEvent(form, id) {
    $('#' + form)[0].reset();
    $('#' + id).bootstrapTable('refresh');
}

//角色查询添加拼接
function showdates(data, id) {
    $("#" + id).html('');
    for (i = 0; i < data.length; i++) {
        html = '<div class="col-sm-6"><label><input type="checkbox" name="' + id + '" value="' + data[i].roleId + '"/>' + data[i].roleName + '</label></div>';
        $("#" + id).append(html);
    }
};

//获取添加的角色id
function getRoleId(name) {
    var obj = document.getElementsByName(name);
    check_val = [];
    for (k in obj) {
        if (obj[k].checked)
            check_val.push(obj[k].value);
    }
    return check_val;
};

//角色查询
function queryRole(id) {
    $.ajax({
        url: json.auth_user_queryRole,
        type: 'POST',
        async: false,//同步获取数据
        success: function (data) {
            unloginHandler(data);
            console.log(data);
            if (data.data == null) {
                layer.msg('没有任何角色');
            } else {
                showdates(data.data, id);
//				showdates1(data.data);
            }
        }
    });
};

//清除所有表单数据
function clearDate() {
    $("#username").val(null);
    $("#password").val(null);
    $("#realname").val(null);
    $("#mobile").val(null);
    $("#sex").val(1);
    $("#aliasname").val(null);
    $("#department").val(null);
    $("#agentId").val(null);
    $("#status").val(1);
    $("#remark").val(null);
}

//新增按钮事件
$('#btn_add').click(function () {
    clearDate();
    queryRole("role");
    $('#addModal').modal();
    $('#sexhide').hide();
    $('#agentIdhide').hide();
    $('#aliasnamehide').hide();
    $('#mobilehide').hide();
    $('#remarkhide').hide();
});
//新增确认按钮事件
$('#btn_yes').click(function () {
    //var roleid=getRoleId("role");
    //获得当前选中的值
    //var sex = $('#sex').val();
    var status = $('#status').val();
    var username = $('#username').val();
    var password = $('#password').val();
    //var mobile = $('#mobile').val();
    //var department = $('#department').val();
    //var remark =$('#remark').val();
    var date = {
        "userAccount": username,
        "userPassword": password,
        //"roleList" : roleid,
        //"mobile" : mobile,
        //"department" : department,
        //"departId" : $('#departId').val(),
        //"sex" : sex,
        "userState": status,
        "userRealName": $('#realname').val(),
        //"aliasName" : $('#aliasname').val(),
        //"agentId" : $('#agentId').val(),
        //"remark" : remark
    };
    if (username == null || username.length == 0) {
        layer.msg('用户名不能为空!');
        return false;
    }
    queryByName(username);
    if (isdata == false) {
        layer.msg('用户名已存在!');
        return false;
    }
    if (password == null || password.length == 0) {
        layer.msg('密码不能为空!');
        return false;
    }
    if (password.length < 6) {
        layer.msg('密码最少6位');
        return false;
    }
    /*if (mobile == null || mobile.length == 0) {
        layer.msg('手机号不能为空!');
        return false;
    }*/
    /*if (department == null || department.length == 0) {
        layer.msg('所属部门不能为空!');
        return false;
    }*/
    /*if (remark.length>50) {
        layer.msg('备注最多50个字!');
        return false;
    }*/
    add(date);
});
//修改按钮事件
$('#btn_edit').click(function () {
    var userId = getUserId();
    if (userId == null) {
        return;
    }
    queryRole("role1");
    queryById(userId);
});
//修改确认按钮事件
$('#btn_yes1').click(
    function () {
        var id = getUserId();
        if (id == null) {
            return;
        }
        var roleid = getRoleId("role1");
        //获得当前选中的值
        var status = $('#status1').val();
        var username = $('#username1').val();
        //var department = $('#department1').val();
        var date = {
            "userId": id,
            "userAccount": username,
            "roleIdList": roleid,
            //"department" : department,
            //"departId" : $('#departId1').val(),
            "userState": status,
            "userRealName": $('#realname1').val(),
            //"agentId" : $('#agentId1').val(),
        };
        /*if (department == null || department.length == 0) {
            layer.msg('所属部门不能为空!');
            return false;
        }*/
        layer.confirm('确定修改选中数据吗？', {
            time: 20000, //20s后自动关闭
            btn: ['确定', '取消']
        }, function () {
            edit(date);
        }, function () {
            layer.msg('取消成功');
        });
    });
//部门点击事件
$('#department').click(function () {
    var a = 1;
    dotext(a);
});
//部门点击1事件
$('#department1').click(function () {
    var a = 2;
    dotext(a);
});
//批量删除按钮事件
$('#btn_delete').click(function () {
    var select_data = $('#table').bootstrapTable('getSelections');
    if (select_data.length == 0) {
        layer.msg('请选择一行删除!');
        return false;
    }
    ;
    var dataId = [];
    for (var i = 0; i < select_data.length; i++) {
        dataId = dataId.concat(select_data[i].userId);
        alert(dataId)
    }
    layer.confirm('确定删除选中数据吗？', {
        time: 20000, //20s后自动关闭
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: json.auth_user_deleteUserById,
            type: 'POST',
            dataType: "json",
            data: {'userId': dataId},
            success: function (data) {
                unloginHandler(data);
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
