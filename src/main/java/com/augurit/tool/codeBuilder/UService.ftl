package ${bean_pkg};
import ${vo_class};
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import java.util.List;
/**
* ${bean_desc}-Service服务调用接口类
<ul>
    <li>项目名：奥格工程建设审批系统</li>
    <li>版本信息：v4.0</li>
    <li>版权所有(C)2019广州奥格智能科技有限公司-版权所有</li>
    <li>创建人:小糊涂</li>
    <li>创建时间：${bean_created}</li>
</ul>
*/
public interface ${bean_type} {
    public void save${bean_table}(${vo_class_name} ${vo_obj_name}) throws Exception;
    public void update${bean_table}(${vo_class_name} ${vo_obj_name}) throws Exception;
    public void delete${bean_table}ById(String id) throws Exception;
    public PageInfo<${vo_class_name}> list${bean_table}(${vo_class_name} ${vo_obj_name},Page page) throws Exception;
    public ${vo_class_name} get${bean_table}ById(String id) throws Exception;
    public List<${vo_class_name}> list${bean_table}(${vo_class_name} ${vo_obj_name}) throws Exception;

}
