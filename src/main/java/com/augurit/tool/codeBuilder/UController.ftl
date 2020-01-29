package ${bean_pkg};
import ${vo_class};
import ${service_class};

import com.augurit.agcloud.framework.ui.result.ResultForm;
import com.augurit.agcloud.framework.ui.result.ContentResultForm;
import com.augurit.agcloud.framework.exception.InvalidParameterException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.augurit.agcloud.framework.ui.pager.PageHelper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.UUID;

/**
* ${bean_desc}-Controller 页面控制转发类
<ul>
    <li>项目名：奥格工程建设审批系统</li>
    <li>版本信息：v4.0</li>
    <li>版权所有(C)2019广州奥格智能科技有限公司-版权所有</li>
    <li>创建人:小糊涂</li>
    <li>创建时间：${bean_created}</li>
</ul>
*/
@RestController
@RequestMapping("${mappingBaseUrl}")
public class ${bean_type} {

private static Logger logger = LoggerFactory.getLogger(${bean_type}.class);

@Autowired
private ${service_class_name} ${service_class_name?uncap_first};


@RequestMapping("/index${bean_table}.do")
public ModelAndView index${bean_table}(${vo_class_name} ${vo_obj_name}, String infoType){
return new ModelAndView("${jspFolder?substring(1,jspFolder?length)}/${jspFileName}");
}

@RequestMapping("/list${bean_table}.do")
public PageInfo<${bean_table}> list${bean_table}(  ${vo_class_name} ${vo_obj_name}, Page page) throws Exception {
logger.debug("分页查询，过滤条件为{}，查询关键字为{}", ${vo_obj_name});
return ${service_class_name?uncap_first}.list${bean_table}(${vo_obj_name},page);
}

@RequestMapping("/get${bean_table}.do")
public ${vo_class_name} get${vo_class_name}(String id) throws Exception {
if (id != null){
logger.debug("根据ID获取${vo_class_name}对象，ID为：{}", id);
return ${service_class_name?uncap_first}.get${vo_class_name}ById(id);
}
else {
logger.debug("构建新的${vo_class_name}对象");
return new ${vo_class_name}();
}
}

@RequestMapping("/update${bean_table}.do")
public ResultForm update${vo_class_name}(${vo_class_name} ${vo_obj_name}) throws Exception {
logger.debug("更新客户档案信息Form对象，对象为：{}", ${vo_obj_name});
${service_class_name?uncap_first}.update${vo_class_name}(${vo_obj_name});
return new ResultForm(true);
}


/**
* 保存或编辑${bean_desc}
* @param ${vo_obj_name} ${bean_desc}
* @param result 校验对象
* @return 返回结果对象 包含结果信息
* @throws Exception
*/
@RequestMapping("/save${bean_table}.do")
public ResultForm save${vo_class_name}(${vo_class_name} ${vo_obj_name}, BindingResult result) throws Exception {
if(result.hasErrors()) {
logger.error("保存${bean_desc}Form对象出错");
throw new InvalidParameterException(${vo_obj_name});
}

<#--logger.debug("保存${bean_desc}Form对象，对象为：{}", ${vo_obj_name});-->
<#--${service_class_name?uncap_first}.save${vo_class_name}(${vo_obj_name});-->
<#--return  new ResultForm(true);-->
<#--
if(${vo_obj_name}.getCreateTime()==null)${vo_obj_name}.setCreateTime(new Timestamp(System.currentTimeMillis()));
-->
if(${vo_obj_name}.get${bean_pk_field}()!=null&&!"".equals(${vo_obj_name}.get${bean_pk_field}())){
<#--
${vo_obj_name}.setModifyTime(new Timestamp(System.currentTimeMillis()));
 -->
${service_class_name?uncap_first}.update${vo_class_name}(${vo_obj_name});
}else{
if(${vo_obj_name}.get${bean_pk_field}()==null||"".equals(${vo_obj_name}.get${bean_pk_field}()))
${vo_obj_name}.set${bean_pk_field}(UUID.randomUUID().toString());
${service_class_name?uncap_first}.save${vo_class_name}(${vo_obj_name});
}

return  new ContentResultForm<${vo_class_name}>(true,${vo_obj_name});
}

@RequestMapping("/delete${bean_table}ById.do")
public ResultForm delete${vo_class_name}ById(String id) throws Exception{
logger.debug("删除${bean_desc}Form对象，对象id为：{}", id);
if(id!=null)
${service_class_name?uncap_first}.delete${vo_class_name}ById(id);
return new ResultForm(true);
}

}
