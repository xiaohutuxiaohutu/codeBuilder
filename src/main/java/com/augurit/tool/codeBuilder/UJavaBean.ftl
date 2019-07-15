package ${bean_pkg};
import java.io.Serializable;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;
<#list bean_property as p>
    <#if   p.javaTypeDesc=="java.util.Date">import org.springframework.format.annotation.DateTimeFormat;<#break>
    </#if>
</#list>

/**
* ${bean_desc}-模型
<ul>
    <li>项目名：奥格工程建设审批系统</li>
    <li>版本信息：v4.0</li>
    <li>版权所有(C)2019广州奥格智能科技有限公司-版权所有</li>
    <li>创建人:小糊涂</li>
    <li>创建时间：${bean_created}</li>
</ul>
*/
@Data
public class ${bean_type} implements Serializable{
// ----------------------------------------------------- Properties

private static final long serialVersionUID = 1L;
<#list bean_property as p>
    <#if   p.javaTypeDesc=="java.util.Date">@DateTimeFormat(pattern="yyyy-MM-dd")
    <#elseif (p.javaTypeDesc=="java.lang.Long"||p.javaTypeDesc=="java.lang.Integer"||p.javaTypeDesc=="java.lang.Double")>@Size(max=${p.length})
    <#else ></#if>
    /** ${p.remark!} */
    private ${p.javaTypeDesc} ${p.fields?uncap_first}; // (${p.remark!})
</#list>
// ----------------------------------------------------- Constructors
// ----------------------------------------------------- Methods
<#--<#list bean_property as p>
    <#if p.javaTypeDesc=="java.lang.String" >
    public ${p.javaTypeDesc} get${p.fields?cap_first}(){
        return ${p.fields?uncap_first};
    }
    public void set${p.fields?cap_first}( ${p.javaTypeDesc} ${p.fields?uncap_first} ) {
        this.${p.fields?uncap_first} = ${p.fields?uncap_first} == null ? null : ${p.fields?uncap_first}.trim();
    }
    <#else >
    public ${p.javaTypeDesc} get${p.fields?cap_first}() {
        return ${p.fields?uncap_first};
    }
    public void set${p.fields?cap_first}( ${p.javaTypeDesc} ${p.fields?uncap_first} ){
        this.${p.fields?uncap_first} = ${p.fields?uncap_first};
    }

    </#if>
</#list>-->
    //public String getTableName()  {
    //    return "${bean_type}";
    //}
}
