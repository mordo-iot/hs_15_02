package com.mordo.fly.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.framework.system.code.generate.CodeGenerate;
import com.framework.system.code.generate.ReadTable;
import com.framework.system.code.po.Columnt;
import com.framework.system.code.po.EntityInfo;
import com.framework.system.code.po.RelationInfo;
import com.framework.system.util.StringUtil;

public class test {
	public static void main(String[] sage) {
		ReadTable r = new ReadTable();
        String tableName = "mordo_sys_user";
		List<Columnt> columntList = r.readColumntByTableName(tableName,"hs_15_02");
		
		CodeGenerate c= new CodeGenerate();
		
		EntityInfo entityInfo = new EntityInfo();		
		entityInfo.setBussiPackage("fly");
		Date date = new Date();
		SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		entityInfo.setCreateTime(formater1.format(date));
		entityInfo.setEntityName(StringUtil.firstUpperCase("user"));
		entityInfo.setTableName(tableName);
		entityInfo.setTableDescription("系统账号");
		entityInfo.setEntityPackage("user");
		entityInfo.setTableUpperName("user".toUpperCase());
		
		List<RelationInfo> relationList = new ArrayList<RelationInfo>();
		RelationInfo relation = new RelationInfo();
		relation.setRelation(RelationInfo.OneToMany);
		relation.setJoinColumn("userLog");
		relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase("userLog"));
		relation.setJoinColumnUpper("userLog".toUpperCase());

		relationList.add(relation);
		
		
		c.generatorCodeMessage(columntList, entityInfo,relationList);
	}
//	public static void main(String[] sage) {
//		ReadTable r = new ReadTable();
//        String tableName = "mordo_user_log";
//		List<Columnt> columntList = r.readColumntByTableName(tableName,"hs_15_02");
//		
//		CodeGenerate c= new CodeGenerate();
//		
//		EntityInfo entityInfo = new EntityInfo();		
//		entityInfo.setBussiPackage("fly");
//		Date date = new Date();
//		SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		entityInfo.setCreateTime(formater1.format(date));
//		entityInfo.setEntityName(StringUtil.firstUpperCase("userLog"));
//		entityInfo.setTableName(tableName);
//		entityInfo.setTableDescription("系统日志");
//		entityInfo.setEntityPackage("userLog");
//		entityInfo.setTableUpperName("userLog".toUpperCase());
//		
//		List<RelationInfo> relationList = new ArrayList<RelationInfo>();
//		RelationInfo relation = new RelationInfo();
//		relation.setRelation(RelationInfo.ManyToOne);
//		relation.setJoinColumn("user");
//		relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase("user"));
//		relation.setJoinColumnUpper("user".toUpperCase());
//
//		relationList.add(relation);
//		
//		
//		c.generatorCodeMessage(columntList, entityInfo,relationList);
//	}
}
