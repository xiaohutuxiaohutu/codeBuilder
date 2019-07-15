package com.augurit.tool.codeBuilder;

import java.util.Map;

public class Main {


    public static void main(String args[]) throws Exception {
        String[] tableNames = {
                "aea_applyinst_proj",
                "aea_applyinst_proj_inner",
                "aea_bus_cert",
                "aea_bus_certinst",
                "aea_cert",
                "aea_cert_type",
                "aea_hi_accept_data",
                "aea_hi_accreditation_data",
                "aea_hi_apply_data",
                "aea_hi_apply_mat",
                "aea_hi_applyinst",
                "aea_hi_approval_data",
                "aea_hi_certinst",
                "aea_hi_concluded_data",
                "aea_hi_item_inoutinst",
                "aea_hi_item_matinst",
                "aea_hi_item_stateinst",
                "aea_hi_iteminst",
                "aea_hi_par_stageinst",
                "aea_hi_par_stateinst",
                "aea_hi_receive",
                "aea_hi_seriesinst",
                "aea_hi_sms_info",
                "aea_item",
                "aea_item_accept_category",
                "aea_item_accept_category_rel",
                "aea_item_accept_range",
                "aea_item_agency",
                "aea_item_agency_aux",
                "aea_item_aux_service",
                "aea_item_basic",
                "aea_item_charge_group",
                "aea_item_charge_group_rel",
                "aea_item_cond",
                "aea_item_directory",
                "aea_item_exeorg",
                "aea_item_foront_item",
                "aea_item_guide",
                "aea_item_guide_accordings",
                "aea_item_guide_attr",
                "aea_item_guide_charges",
                "aea_item_guide_conditions",
                "aea_item_guide_departments",
                "aea_item_guide_extend",
                "aea_item_guide_matconditions",
                "aea_item_guide_materials",
                "aea_item_guide_questions",
                "aea_item_guide_specials",
                "aea_item_inner",
                "aea_item_inout",
                "aea_item_legal_remedy",
                "aea_item_mat",
                "aea_item_mat_attr",
                "aea_item_mat_type",
                "aea_item_material_main",
                "aea_item_relevance",
                "aea_item_rights_obligations",
                "aea_item_seq",
                "aea_item_service_basic",
                "aea_item_service_cert",
                "aea_item_service_charge",
                "aea_item_service_consulting",
                "aea_item_service_flow",
                "aea_item_service_front",
                "aea_item_service_legal",
                "aea_item_service_legal_clause",
                "aea_item_service_serve",
                "aea_item_service_timelimit",
                "aea_item_service_window",
                "aea_item_service_window_rel",
                "aea_item_situation",
                "aea_item_situation_version",
                "aea_item_state",
                "aea_item_state_form",
                "aea_item_state_ver",
                "aea_item_supervise_inspect",
                "aea_item_timelimit_special",
                "aea_item_timelimit_state",
                "aea_item_ver",
                "aea_linkman_info",
                "aea_local_directory",
                "aea_log_apply_state_hist",
                "aea_log_item_state_hist",
                "aea_par_in",
                "aea_par_share_mat",
                "aea_par_stage",
                "aea_par_stage_item",
                "aea_par_stage_item_in",
                "aea_par_state",
                "aea_par_state_form",
                "aea_par_state_item",
                "aea_par_theme",
                "aea_par_theme_seq",
                "aea_par_theme_ver",
                "aea_parent_proj",
                "aea_proj_info",
                "aea_proj_info_log",
                "aea_proj_linkman",
                "aea_unit_info",
                "aea_unit_info_log",
                "aea_unit_linkman",
                "aea_unit_proj"};
        String tableName = "AEA_UNIT_INFO";
        String[] units={"aea_unit_info",
                "aea_unit_info_log",
                "aea_unit_linkman",
                "aea_unit_proj"};
        String[] projs={"aea_parent_proj",
                "aea_proj_info",
                "aea_proj_info_log",
                "aea_proj_linkman",};
        String[] links={"aea_linkman_info"};
        for (String s : links) {
            CodeCommon my = new CodeCommon(s);
            Main main = new Main();
            main.setMy(my);
            main.genarate(s);
        }

    }

    private CodeCommon my;

    public CodeCommon getMy() {
        return my;
    }

    public void setMy(CodeCommon my) {
        this.my = my;
    }

    public void genarate(String tableName) throws Exception {
        /*CodeCommon my;
        //表名必须使用下划线分隔！！
                *//*String tableName="erp_report_sev_contract_lw";
        String tableName="crm_contract_info_value_item";*//*
        my=new CodeCommon(tableName);*/
        my.init(tableName);
//        my=new CodeCommon("erp_mgmt_okr_target");
//        my.onUsingTableNames="crm_base_client_info,crm1_base_linkman_info,crm1_proj_base_info,spl1_base_supplier_info,erp_mgmt_okr_task,";
//        if(my.hasTableOnUsing(my.getTableName())){
//            System.out.println("该表目前正在使用，重新覆盖代码或许会对系统造成较大影响！\n 请尽量换个tablename来进行代码生成....");
//            return;
//        }
        System.out.println(my.getTableName());
        //setTableNameDesc(createTableNameDesc().substring(0,createTableNameDesc().length()-1));
//        setTableNameDesc("项目计划模板节点"); //如果数据库中表名没有描述,则自己手动赋值给tablenamedesc
        //setTableNameDesc("项目管理信息"); //如果数据库中表名没有描述,则自己手动赋值给tablenamedesc
        Map root = my.builTemplateForOneTable();
       /* if(false==(validCodeConflict(root,tableName))){
            System.out.println("该表目前正在使用，重新覆盖代码或许会对系统造成较大影响！\n 请尽量换个tablename来进行代码生成....");
            return;
        }*/
        my.createCodeForSqlMapperXml(root);
        my.createCodeForVo(root);        //居然有人把这句话注释了然后提交，害我编辑到一半发现不对然后又要备份文件重新生成！
        my.createCodeForMapper(root);
        my.createCodeForService(root);
        my.createCodeForServiceImpl(root);
        my.createCodeForController(root);
//        my.createCodeForJspFile(root);
//        my.createCodeForJsFile(root);
    }
}
