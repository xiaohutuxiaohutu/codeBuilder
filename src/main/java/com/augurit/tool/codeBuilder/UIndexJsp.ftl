<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<title>${bean_desc}</title>
	<%${r'@'} include file="/agcloud/frame/jsp/meta-easyui.jsp"%>
	<script type="text/javascript" src="${r'${'}pageContext.request.contextPath}/erp/ui/${jspFileName}.js"  ></script>
</head>
<body>

	<!-- 页面  -->
	<div class="ex-default-page">

		<!-- 页面标题  -->
		<div class="ex-default-title">
			<div class="icon-left"></div>
			<div class="text">
				<div class="text-main">${bean_desc}管理</div>
				<div class="text-tip"></div>
			</div>
			<div class="icon-right"></div>
		</div>

		<!-- 表格 -->
		<div id="contentPanel" border="false">
			<!--sortName:排序属性名 -->
			<table style="width: 100%" id="datagrid" class="easyui-datagrid" title="&nbsp;${bean_desc}列表" iconCls="icon-ok" toolbar="#datagridToolbar" sortName="" sortOrder="asc" rownumbers="true" pagination="true" singleSelect="true" fit="true">
				<thead>
				<tr>
				<#list bean_property as p>

					<#if p.columnKey?? && p.columnKey!="PRI">
                    <th field="${p.fields?uncap_first}"  <#if p.javaTypeDesc=="java.util.Date">formatter="formatDatebox"</#if>  style="width:7%;" >${p.remark}</th>
					</#if>
				</#list>

					<th field="_${vo_obj_name?uncap_first}Edit" width="60px" align="center" formatter="format${vo_obj_name?uncap_first}Edit"></th>
					<th field="_${vo_obj_name?uncap_first}Delete" width="60px" align="center" formatter="format${vo_obj_name?uncap_first}Delete"></th>
				</tr>
				</thead>
			</table>
		</div>

		<!-- 表格工具栏 -->
		<div id="datagridToolbar">
			<table cellpadding="0" cellspacing="0" style="width: 100%">
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0">
							<tr>
								<td><div class="datagrid-btn-separator"/></td>
								<td><a class="easyui-linkbutton" iconcls="icon-add" plain="true" onclick="addData()">新增</a></td>
								<td><div class="datagrid-btn-separator"/></td>
							</tr>
						</table>
					</td>
                    <td style="text-align: right">
                        查询条件：
                        <select class="easyui-combobox" panelHeight="auto" name="condition" id="condition" style="width:100px">
                            <!--<option value="clientCode">客户编号</option>
                            <option value="clientName">客户名称</option>-->
                        </select>
                        <input  id="keyword" name="keyword" class="easyui-textbox" data-options="prompt:'请输入查询关键字'" style="width:200px"/>&nbsp;
                        <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchCondition()">查询</a>
                    </td>
				</tr>

			</table>
		</div>

		<!-- 新增或编辑表单 -->
		<div id="detailInfo" title="${bean_desc}" class="easyui-dialog" style="width:45%;height: auto;" mask="true" modal="true" closed="true" buttons="#detailInfoButtons">
			<div style="width: 99%">
				<form id="${vo_obj_name?uncap_first}_p_form" method="post">
					<table cellpadding="5" width="100%" table-layout="fixed" class="detailInfo_table">
					<tr>
					<#list bean_property as p>
						<#--主键字段不显示-->
						<#if  p.columnKey?? && p.columnKey=="PRI">
						<input  type="hidden" id="${p.fields?uncap_first}" name="${p.fields?uncap_first}"  />
						</#if>
						<#--非主键-->
						<#if p.columnKey?? &&  p.columnKey!="PRI">
                        <td ><span> ${p.remark} </span>
						</td>
						<#--时间字段需要添加formatDatebox转换，非空非时间字段需要添加required和其他校验,可为空非时间字段直接添加最大长度校验-->
                        <td >
							<input <#if p.javaTypeDesc=="java.util.Date"> class="easyui-datebox"  formatter="formatDatebox" <#else> class="easyui-textbox"  </#if> type="text" name="${p.fields?uncap_first}"  value="" data-options="<#if !p.enableNull && p.javaTypeDesc!="java.util.Date">required:true,validType:'length[0,${p.length}]'<#elseif p.javaTypeDesc!="java.util.Date">validType:'maxlength[${p.length}]'</#if>" style="width: 96%"/>
						</td>
						<#if   (p_index-1)%3==0 &&p_has_next>
					</tr>
					<tr>
						<#elseif !p_has_next >
                    </tr>
						</#if></#if>
					</#list>


					</table>
				</form>
			</div>
			
			<div id="detailInfoButtons">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveData()" style="width:90px">保存</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:${r'$('}'#detailInfo').dialog('close')" style="width:90px">取消</a>
			</div>
			<!-- 收款阶段编辑窗口 -->
		</div>
	</div>
</body>
</html>