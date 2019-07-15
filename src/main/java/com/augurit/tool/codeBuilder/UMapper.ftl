package ${bean_pkg};
import ${vo_class};
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
* ${bean_desc}-Mapper数据与持久化接口类
<ul>
    <li>项目名：奥格工程建设审批系统</li>
    <li>版本信息：v4.0</li>
    <li>版权所有(C)2019广州奥格智能科技有限公司-版权所有</li>
    <li>创建人:小糊涂</li>
    <li>创建时间：${bean_created}</li>
</ul>
*/
${r'@Mapper'}
public interface ${bean_type} {

int insert${bean_table}(${vo_class_name} ${vo_obj_name}) throws Exception;
int update${bean_table}(${vo_class_name} ${vo_obj_name}) throws Exception;
int delete${bean_table}(${r'@Param("id")'} String id) throws Exception;
List <${vo_class_name}> list${bean_table}(${vo_class_name} ${vo_obj_name}) throws Exception;
${vo_class_name} get${bean_table}ById(${r'@Param("id")'} String id) throws Exception;
}
