package ${bean_pkg};
import ${vo_class};
import com.augurit.agcloud.framework.exception.InvalidParameterException;
import ${service_class};
import ${mapper_class};
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.augurit.agcloud.framework.ui.pager.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
/**
* ${bean_desc}-Service服务接口实现类
<ul>
    <li>项目名：奥格工程建设审批系统</li>
    <li>版本信息：v4.0</li>
    <li>版权所有(C)2019广州奥格智能科技有限公司-版权所有</li>
    <li>创建人:小糊涂</li>
    <li>创建时间：${bean_created}</li>
</ul>
*/
@Service
@Transactional
public class ${bean_type} implements ${bean_type?substring(0,  bean_type?length-4)}{

    private static Logger logger = LoggerFactory.getLogger(${bean_type}.class);

    @Autowired
    private ${mapper_class_name}  ${mapper_class_name?uncap_first};

    <#--@Override-->
    public void save${bean_table}(${vo_class_name} ${vo_obj_name}) throws Exception{
        ${mapper_class_name?uncap_first}.insert${bean_table}(${vo_obj_name});
    }
    <#--@Override-->
    public void update${bean_table}(${vo_class_name} ${vo_obj_name}) throws Exception{
        ${mapper_class_name?uncap_first}.update${bean_table}(${vo_obj_name});
    }
    <#--@Override-->
    public void delete${bean_table}ById(String id) throws Exception{
        if(id == null)
        throw new InvalidParameterException(id);
        ${mapper_class_name?uncap_first}.delete${bean_table}(id);
    }
    <#--@Override-->
    public PageInfo<${vo_class_name}> list${bean_table}(${vo_class_name} ${vo_obj_name},Page page) throws Exception{
        PageHelper.startPage(page);
        List<${vo_class_name}> list = ${mapper_class_name?uncap_first}.list${bean_table}(${vo_obj_name});
        logger.debug("成功执行分页查询！！");
        return new PageInfo<${vo_class_name}>(list);
    }
    <#--@Override-->
    public ${vo_class_name} get${bean_table}ById(String id) throws Exception{
        if(id == null)
        throw new InvalidParameterException(id);
        logger.debug("根据ID获取Form对象，ID为：{}", id);
        return ${mapper_class_name?uncap_first}.get${bean_table}ById(id);
    }
    <#--@Override-->
    public List<${vo_class_name}> list${bean_table}(${vo_class_name} ${vo_obj_name}) throws Exception{
        List<${vo_class_name}> list = ${mapper_class_name?uncap_first}.list${bean_table}(${vo_obj_name});
        logger.debug("成功执行查询list！！");
        return list;
    }
}

