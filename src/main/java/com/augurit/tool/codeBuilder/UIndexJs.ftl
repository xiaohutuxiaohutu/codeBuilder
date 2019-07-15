/**
 * Created by ${bean_author} on ${bean_created}.
 */
$(function(){
    //自适应宽度和高度
    initEasyuiContentPanel("contentPanel");
    //表格双击事件处理
    $('#datagrid').datagrid({
        url: ctx+'${mappingBaseUrl}/list.do',
        onDblClickRow: function (rowIndex, rowData) {
            editData();
        }
    })

    //表格双击事件处理
    $('#stageDateGrid').datagrid({
        url: ctx+'${mappingBaseUrl}/list.do',
        onDblClickRow: function (rowIndex, rowData) {
        }
    })
})
//${bean_desc}表格行修改操作
function format${vo_obj_name?uncap_first}Edit(val, row, index){
    return '<a href="#" class="easyui-linkbutton ex-grid-operate" iconCls="icon-save" onclick="selectRow('+index+');editData()">修改</a>';
}
//${bean_desc}表格行删除操作
function format${vo_obj_name?uncap_first}Delete(val, row, index){
    return '<a href="#" class="easyui-linkbutton ex-grid-operate" iconCls="icon-remove" onclick="selectRow('+index+');deleteData()">删除</a>';
}
//表格选择行
function selectRow(index){
    $('#datagrid').datagrid('selectRow', index);
}
//easyui表格重新加载
function reloadData(){
    $("#datagrid").datagrid('reload');
}
//新增${bean_desc}
function addData(){
    $('#detailInfo').dialog('open').dialog('setTitle', '新增${bean_desc}');
    $('#${vo_obj_name?uncap_first}_p_form').form('clear');
}

//编辑${bean_desc}
function editData(){
    var row = $('#datagrid').datagrid('getSelected');
    console.log(row);
    if (row){
        $('#detailInfo').dialog('open').dialog('setTitle', '编辑${bean_desc}');
		<#--传参主键字段-->
        $('#${vo_obj_name?uncap_first}_p_form').form('load', ctx+'${mappingBaseUrl}/get.do?id=' + row.${bean_pk_field?uncap_first});
    }
}

//保存${bean_desc}
function saveData(){
    $('#${vo_obj_name?uncap_first}_p_form').form('submit',{
        url: ctx+'${mappingBaseUrl}/save.do',
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
            var result = eval('('+result+')');
            if (result.success){
                if(result.content.${bean_pk_field?uncap_first}!=null){
                    $("#${bean_pk_field?uncap_first}").val(result.content.${bean_pk_field?uncap_first});
                    $.messager.alert('提示', "保存成功！", 'info');
                    $('#detailInfo').dialog('close');
                    reloadData();
                }
            } else {
                $.messager.alert('错误', result.message, 'error');
            }
        }
    });
}


//删除${bean_desc}
function deleteData(){
    var row = $('#datagrid').datagrid('getSelected');
    if (row){
        $.messager.confirm('提示', '确定要删除选中的记录吗？', function(confirm){
            if (confirm){
                $.post(ctx+'${mappingBaseUrl}/deleteById.do', {id:row.${bean_pk_field?uncap_first}}, function(result){
                    if (result.success){
                        reloadData();
                    } else {
                        $.messager.show({title: '错误',	msg: result.message});
                    }
                }, 'json');
            }
        });
    }
}



Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, // month
        "d+": this.getDate(), // day
        "h+": this.getHours(), // hour
        "m+": this.getMinutes(), // minute
        "s+": this.getSeconds(), // second
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
        "S": this.getMilliseconds()
        // millisecond
    }
    if (/(y+)/.test(format))
        format = format.replace(RegExp.$1, (this.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
    return format;
}

//日期格式转换
function formatDatebox(value) {
    if (value == null || value == '') {
        return '';
    }
    var dt;
    if (value instanceof Date) {
        dt = value;
    } else {
        dt = new Date(value);
    }
    return dt.format("yyyy-MM-dd"); //扩展的Date的format方法(上述插件实现)
}

//时间格式转换
function formatDatetimebox(value) {
    if (value == null || value == '') {
        return '';
    }
    var dt;
    if (value instanceof Date) {
        dt = value;
    } else {
        dt = new Date(value);
    }
    return dt.format("yyyy-MM-dd hh:mm:ss"); //扩展的Date的format方法(上述插件实现)
}