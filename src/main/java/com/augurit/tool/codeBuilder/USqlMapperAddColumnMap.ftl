<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<!--created by ${bean_author}    ${bean_created} -->
<!--0 一些前置信息配置 -->
<mapper namespace="${bean_class_package}">
    <!--1 返回所有字段的SQL字句 -->
    <sql id="allColumns">
        <#list bean_property as p>
            <#if p_index+1==  (bean_property?size)>${p.db_fields}  ${p.fields?uncap_first}
            <#else >${p.db_fields}  ${p.fields?uncap_first},
            </#if>
        </#list>
    </sql>

    <!--2 根据主键ID查询单个实体 -->
    <select id="get${bean_table}ById" resultType="${bean_table}">
        select
        <include refid="allColumns"/>
        from ${bean_db_table}
        where ${bean_pk} = ${r'#{id}'}
        <#if isreallydelete>
            AND  IS_DELETED = '0'
        </#if>
    </select>

    <!--3 根据条件查询实体list -->
    <select id="list${bean_table}" resultType="${bean_table}">
        select
        <include refid="allColumns"/>
        from ${bean_db_table}
        <where>
            <#list bean_property as p>
                <if test="${p.fields?uncap_first} != null">
                    AND ${p.db_fields} = ${r'#{'}${p.fields?uncap_first}${r'}'}
                </if>
            </#list>
            <#if isreallydelete>
                AND  IS_DELETED = '0'
            </#if>
        </where>
    </select>
    <!--4 根据条件查询实体list,返回分页对象 -->

    <!--5 新增实体对象 -->
    <insert id="insert${bean_table}" parameterType="${bean_table}">
        insert into ${bean_db_table} (
        <#list bean_property as p><#if p_index+1==  (bean_property?size)>${p.db_fields}  <#else >${p.db_fields}  ,</#if></#list>
        ) values (
        <#list bean_property as p><#if p_index+1==  (bean_property?size)>  ${r'#{'}${p.fields?uncap_first}${r'}'}<#else >${r'#{'}${p.fields?uncap_first}${r'}'},</#if></#list>
        )
    </insert>
    <!--6 修改实体对象 -->
    <update id="update${bean_table}" parameterType="${bean_table}">
        update ${bean_db_table}
        <set>
            <#list bean_property as p>
                <if test="${p.fields?uncap_first} != null">
                    ${p.db_fields} = ${r'#{'}${p.fields?uncap_first}${r'}'},
                </if>
            </#list>
        </set>
        where ${bean_pk} = ${r'#{'}${bean_pk_field?uncap_first}${r'}'}
    </update>
    <!--7 删除实体对象,根据主键ID -->
    <#if isreallydelete>
        <update id="delete${bean_table}">
            update ${bean_db_table}
            <set>
                IS_DELETED = '1'
            </set>
            where ${bean_pk} = ${r'#{id}'}
        </update>
    <#else >
        <delete id="delete${bean_table}">
            delete from ${bean_db_table}
            where
            ${bean_pk} = ${r'#{id}'}
        </delete>
    </#if>
    <!--8 删除实体对象列表,根据主键ID列表 -->
    <!--9 返回所有字段的对象map,用于级联 -->
    <resultMap type="${bean_table}" id="${bean_table}Map">
        <#if bean_pk??>
            <id property="${bean_pk_field?uncap_first}" column="${bean_pk}"/>
        </#if>
        <#list bean_property as p>
            <#if bean_pk!=p.db_fields>
                <result property="${p.fields?uncap_first}" column="${p.db_fields}"/>
            </#if>
        </#list>
    </resultMap>
</mapper>