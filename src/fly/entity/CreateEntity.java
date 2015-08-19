package fly.entity;

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

public class CreateEntity {
	public static void main(String[] sage) {
		if(true){
			//--PositionEntity
			ReadTable r = new ReadTable();
	        String tableName = "mordo_position_info";
			List<Columnt> columntList = r.readColumntByTableName(tableName,"hs_15_02");
			
			CodeGenerate c= new CodeGenerate();
			
			EntityInfo entityInfo = new EntityInfo();		
			entityInfo.setBussiPackage("fly");
			Date date = new Date();
			SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			entityInfo.setCreateTime(formater1.format(date));
			
			entityInfo.setTableName(tableName);
			entityInfo.setTableDescription("位置信息");
			String temp="position";
			entityInfo.setEntityPackage(temp);
			entityInfo.setEntityName(StringUtil.firstUpperCase(temp));
			entityInfo.setTableUpperName(temp.toUpperCase());
			
			List<RelationInfo> relationList = new ArrayList<RelationInfo>();
			if(true){
			RelationInfo relation = new RelationInfo();
			relation.setRelation(RelationInfo.ManyToMany);
			String temp2 = "dev";
			relation.setJoinColumn(temp2);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
			relation.setJoinColumnUpper(temp2.toUpperCase());
			relation.setJionFirst("N");
			relationList.add(relation);
			}
			
			if(true){
				RelationInfo relation = new RelationInfo();
				relation.setRelation(RelationInfo.OneToOne);
				relation.setJoinColumn("position");
				relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase("position"));
				relation.setJoinColumnUpper("position".toUpperCase());
				relation.setJionFirst("Y");
				relationList.add(relation);
			}
			
			
		
			
			
			c.generatorCodeMessage(columntList, entityInfo,relationList);
		}if(true){
			//--UserEntity
			ReadTable r = new ReadTable();
	        String tableName = "mordo_sys_user";
			List<Columnt> columntList = r.readColumntByTableName(tableName,"hs_15_02");
			
			CodeGenerate c= new CodeGenerate();
			
			EntityInfo entityInfo = new EntityInfo();		
			entityInfo.setBussiPackage("fly");
			Date date = new Date();
			SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			entityInfo.setCreateTime(formater1.format(date));
			
			entityInfo.setTableName(tableName);
			entityInfo.setTableDescription("系统账号");
			String temp="user";
			entityInfo.setEntityPackage(temp);
			entityInfo.setEntityName(StringUtil.firstUpperCase(temp));
			entityInfo.setTableUpperName(temp.toUpperCase());
			
			List<RelationInfo> relationList = new ArrayList<RelationInfo>();
			RelationInfo relation = new RelationInfo();
			relation.setRelation(RelationInfo.OneToMany);
			String temp2 = "userLog";
			relation.setJoinColumn(temp2);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
			relation.setJoinColumnUpper(temp2.toUpperCase());
	
			relationList.add(relation);
			
			
			c.generatorCodeMessage(columntList, entityInfo,relationList);
		}
		
		
	
	if(true){
		//--UserEntity
		ReadTable r = new ReadTable();
        String tableName = "mordo_sys_user";
		List<Columnt> columntList = r.readColumntByTableName(tableName,"hs_15_02");
		
		CodeGenerate c= new CodeGenerate();
		
		EntityInfo entityInfo = new EntityInfo();		
		entityInfo.setBussiPackage("fly");
		Date date = new Date();
		SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		entityInfo.setCreateTime(formater1.format(date));
		
		entityInfo.setTableName(tableName);
		entityInfo.setTableDescription("系统账号");
		String temp="user";
		entityInfo.setEntityPackage(temp);
		entityInfo.setEntityName(StringUtil.firstUpperCase(temp));
		entityInfo.setTableUpperName(temp.toUpperCase());
		
		List<RelationInfo> relationList = new ArrayList<RelationInfo>();
		RelationInfo relation = new RelationInfo();
		relation.setRelation(RelationInfo.OneToMany);
		String temp2 = "userLog";
		relation.setJoinColumn(temp2);
		relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
		relation.setJoinColumnUpper(temp2.toUpperCase());

		relationList.add(relation);
		
		
		c.generatorCodeMessage(columntList, entityInfo,relationList);
	}
	if(true){
		//--UserLogEntity
		ReadTable r = new ReadTable();
        String tableName = "mordo_user_log";
		List<Columnt> columntList = r.readColumntByTableName(tableName,"hs_15_02");
		
		CodeGenerate c= new CodeGenerate();
		
		EntityInfo entityInfo = new EntityInfo();		
		entityInfo.setBussiPackage("fly");
		Date date = new Date();
		SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		entityInfo.setCreateTime(formater1.format(date));
		
		entityInfo.setTableName(tableName);
		entityInfo.setTableDescription("系统日志");
		String temp="userLog";
		entityInfo.setEntityPackage(temp);
		entityInfo.setEntityName(StringUtil.firstUpperCase(temp));
		entityInfo.setTableUpperName(temp.toUpperCase());
		
		List<RelationInfo> relationList = new ArrayList<RelationInfo>();
		RelationInfo relation = new RelationInfo();
		relation.setRelation(RelationInfo.ManyToOne);
		String temp2 = "user";
		relation.setJoinColumn(temp2);
		relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
		relation.setJoinColumnUpper(temp2.toUpperCase());

		relationList.add(relation);
		
		
		c.generatorCodeMessage(columntList, entityInfo,relationList);
	}
	//--AlarmCurrentEntity
	if(true){
		ReadTable r = new ReadTable();
        String tableName = "mordo_alarm_current";
		List<Columnt> columntList = r.readColumntByTableName(tableName,"hs_15_02");
		
		CodeGenerate c= new CodeGenerate();
		
		EntityInfo entityInfo = new EntityInfo();		
		entityInfo.setBussiPackage("fly");
		Date date = new Date();
		SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		entityInfo.setCreateTime(formater1.format(date));
		
		entityInfo.setTableName(tableName);
		entityInfo.setTableDescription("当前报警信息");
		entityInfo.setEntityPackage("alarmCurrent");
		entityInfo.setEntityName(StringUtil.firstUpperCase("alarmCurrent"));
		entityInfo.setTableUpperName("alarmCurrent".toUpperCase());
		
		List<RelationInfo> relationList = new ArrayList<RelationInfo>();
		RelationInfo relation = new RelationInfo();
		relation.setRelation(RelationInfo.ManyToOne);
		relation.setJoinColumn("dev");
		relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase("dev"));
		relation.setJoinColumnUpper("dev".toUpperCase());

		relationList.add(relation);
		
		
		c.generatorCodeMessage(columntList, entityInfo,relationList);
	}
	//--AlarmCurrentEntity
	if(true){
		ReadTable r = new ReadTable();
        String tableName = "mordo_alarm_history";
		List<Columnt> columntList = r.readColumntByTableName(tableName,"hs_15_02");
		
		CodeGenerate c= new CodeGenerate();
		
		EntityInfo entityInfo = new EntityInfo();		
		entityInfo.setBussiPackage("fly");
		Date date = new Date();
		SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		entityInfo.setCreateTime(formater1.format(date));
		
		entityInfo.setTableName(tableName);
		entityInfo.setTableDescription("历史报警信息");
		String temp="alarmHistory";
		entityInfo.setEntityPackage(temp);
		entityInfo.setEntityName(StringUtil.firstUpperCase(temp));
		entityInfo.setTableUpperName(temp.toUpperCase());
		
		List<RelationInfo> relationList = new ArrayList<RelationInfo>();
		RelationInfo relation = new RelationInfo();
		relation.setRelation(RelationInfo.ManyToOne);
		String temp2 = "dev";
		relation.setJoinColumn(temp2);
		relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
		relation.setJoinColumnUpper(temp2.toUpperCase());

		relationList.add(relation);
		
		
		c.generatorCodeMessage(columntList, entityInfo,relationList);
	}
	
	//--CurrentBedEntity
		if(true){
			ReadTable r = new ReadTable();
	        String tableName = "mordo_state_current_bed";
			List<Columnt> columntList = r.readColumntByTableName(tableName,"hs_15_02");
			
			CodeGenerate c= new CodeGenerate();
			
			EntityInfo entityInfo = new EntityInfo();		
			entityInfo.setBussiPackage("fly");
			Date date = new Date();
			SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			entityInfo.setCreateTime(formater1.format(date));
			
			entityInfo.setTableName(tableName);
			entityInfo.setTableDescription("床垫当前信息");
			String temp="currentBed";
			entityInfo.setEntityPackage(temp);
			entityInfo.setEntityName(StringUtil.firstUpperCase(temp));
			entityInfo.setTableUpperName(temp.toUpperCase());
			
			List<RelationInfo> relationList = new ArrayList<RelationInfo>();
			RelationInfo relation = new RelationInfo();
			relation.setRelation(RelationInfo.ManyToOne);
			String temp2 = "dev";
			relation.setJoinColumn(temp2);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
			relation.setJoinColumnUpper(temp2.toUpperCase());

			relationList.add(relation);
			
			
			c.generatorCodeMessage(columntList, entityInfo,relationList);
		}
		
		        //--CurrentDoorEntity
				if(true){
					ReadTable r = new ReadTable();
			        String tableName = "mordo_state_current_door";
					List<Columnt> columntList = r.readColumntByTableName(tableName,"hs_15_02");
					
					CodeGenerate c= new CodeGenerate();
					
					EntityInfo entityInfo = new EntityInfo();		
					entityInfo.setBussiPackage("fly");
					Date date = new Date();
					SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					entityInfo.setCreateTime(formater1.format(date));
					
					entityInfo.setTableName(tableName);
					entityInfo.setTableDescription("门当前信息");
					String temp="currentDoor";
					entityInfo.setEntityPackage(temp);
					entityInfo.setEntityName(StringUtil.firstUpperCase(temp));
					entityInfo.setTableUpperName(temp.toUpperCase());
					
					List<RelationInfo> relationList = new ArrayList<RelationInfo>();
					RelationInfo relation = new RelationInfo();
					relation.setRelation(RelationInfo.ManyToOne);
					String temp2 = "dev";
					relation.setJoinColumn(temp2);
					relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
					relation.setJoinColumnUpper(temp2.toUpperCase());

					relationList.add(relation);
					
					
					c.generatorCodeMessage(columntList, entityInfo,relationList);
				}
	//--CurrentGatewayEntity
	if(true){
		ReadTable r = new ReadTable();
        String tableName = "mordo_state_current_gateway";
		List<Columnt> columntList = r.readColumntByTableName(tableName,"hs_15_02");
		
		CodeGenerate c= new CodeGenerate();
		
		EntityInfo entityInfo = new EntityInfo();		
		entityInfo.setBussiPackage("fly");
		Date date = new Date();
		SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		entityInfo.setCreateTime(formater1.format(date));
		
		entityInfo.setTableName(tableName);
		entityInfo.setTableDescription("室内网关当前信息");
		String temp="currentGateway";
		entityInfo.setEntityPackage(temp);
		entityInfo.setEntityName(StringUtil.firstUpperCase(temp));
		entityInfo.setTableUpperName(temp.toUpperCase());
		
		List<RelationInfo> relationList = new ArrayList<RelationInfo>();
		RelationInfo relation = new RelationInfo();
		relation.setRelation(RelationInfo.ManyToOne);
		String temp2 = "dev";
		relation.setJoinColumn(temp2);
		relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
		relation.setJoinColumnUpper(temp2.toUpperCase());

		relationList.add(relation);
		
		
		c.generatorCodeMessage(columntList, entityInfo,relationList);
	}
	    //--CurrentKeyalarmEntity
		if(true){
			ReadTable r = new ReadTable();
	        String tableName = "mordo_state_current_keyalarm";
			List<Columnt> columntList = r.readColumntByTableName(tableName,"hs_15_02");
			
			CodeGenerate c= new CodeGenerate();
			
			EntityInfo entityInfo = new EntityInfo();		
			entityInfo.setBussiPackage("fly");
			Date date = new Date();
			SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			entityInfo.setCreateTime(formater1.format(date));
			
			entityInfo.setTableName(tableName);
			entityInfo.setTableDescription("一键报警当前信息");
			String temp="currentKeyalarm";
			entityInfo.setEntityPackage(temp);
			entityInfo.setEntityName(StringUtil.firstUpperCase(temp));
			entityInfo.setTableUpperName(temp.toUpperCase());
			
			List<RelationInfo> relationList = new ArrayList<RelationInfo>();
			RelationInfo relation = new RelationInfo();
			relation.setRelation(RelationInfo.ManyToOne);
			String temp2 = "dev";
			relation.setJoinColumn(temp2);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
			relation.setJoinColumnUpper(temp2.toUpperCase());

			relationList.add(relation);
			
			
			c.generatorCodeMessage(columntList, entityInfo,relationList);
		}
		//--CurrentIrEntity
				if(true){
					ReadTable r = new ReadTable();
			        String tableName = "mordo_state_current_ir";
					List<Columnt> columntList = r.readColumntByTableName(tableName,"hs_15_02");
					
					CodeGenerate c= new CodeGenerate();
					
					EntityInfo entityInfo = new EntityInfo();		
					entityInfo.setBussiPackage("fly");
					Date date = new Date();
					SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					entityInfo.setCreateTime(formater1.format(date));
					
					entityInfo.setTableName(tableName);
					entityInfo.setTableDescription("红外当前信息");
					String temp="currentIr";
					entityInfo.setEntityPackage(temp);
					entityInfo.setEntityName(StringUtil.firstUpperCase(temp));
					entityInfo.setTableUpperName(temp.toUpperCase());
					
					List<RelationInfo> relationList = new ArrayList<RelationInfo>();
					RelationInfo relation = new RelationInfo();
					relation.setRelation(RelationInfo.ManyToOne);
					String temp2 = "dev";
					relation.setJoinColumn(temp2);
					relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
					relation.setJoinColumnUpper(temp2.toUpperCase());

					relationList.add(relation);
					
					
					c.generatorCodeMessage(columntList, entityInfo,relationList);
				}
		        //--CurrentLocationEntity
				if(true){
					ReadTable r = new ReadTable();
			        String tableName = "mordo_state_current_location";
					List<Columnt> columntList = r.readColumntByTableName(tableName,"hs_15_02");
					
					CodeGenerate c= new CodeGenerate();
					
					EntityInfo entityInfo = new EntityInfo();		
					entityInfo.setBussiPackage("fly");
					Date date = new Date();
					SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					entityInfo.setCreateTime(formater1.format(date));
					
					entityInfo.setTableName(tableName);
					entityInfo.setTableDescription("定位器当前状态");
					String temp="currentLocation";
					entityInfo.setEntityPackage(temp);
					entityInfo.setEntityName(StringUtil.firstUpperCase(temp));
					entityInfo.setTableUpperName(temp.toUpperCase());
					List<RelationInfo> relationList = new ArrayList<RelationInfo>();
					if(true){
						
						RelationInfo relation = new RelationInfo();
						relation.setRelation(RelationInfo.ManyToOne);
						String temp2 = "dev";
						relation.setJoinColumn(temp2);
						relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
						relation.setJoinColumnUpper(temp2.toUpperCase());

						relationList.add(relation);
						
						
					}
					if(true){
						RelationInfo relation = new RelationInfo();
						relation.setRelation(RelationInfo.ManyToOne);
						String temp2 = "position";
						relation.setJoinColumn(temp2);
						relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
						relation.setJoinColumnUpper(temp2.toUpperCase());

						relationList.add(relation);
						
						
						
					}
					c.generatorCodeMessage(columntList, entityInfo,relationList);
					
					
				}
		        
				if(true){
					//--CurrentUrineEntity
					ReadTable r = new ReadTable();
			        String tableName = "mordo_state_current_urine";
					List<Columnt> columntList = r.readColumntByTableName(tableName,"hs_15_02");
					
					CodeGenerate c= new CodeGenerate();
					
					EntityInfo entityInfo = new EntityInfo();		
					entityInfo.setBussiPackage("fly");
					Date date = new Date();
					SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					entityInfo.setCreateTime(formater1.format(date));
					
					entityInfo.setTableName(tableName);
					entityInfo.setTableDescription("尿湿当前信息");
					String temp="currentUrine";
					entityInfo.setEntityPackage(temp);
					entityInfo.setEntityName(StringUtil.firstUpperCase(temp));
					entityInfo.setTableUpperName(temp.toUpperCase());
					
					List<RelationInfo> relationList = new ArrayList<RelationInfo>();
					RelationInfo relation = new RelationInfo();
					relation.setRelation(RelationInfo.ManyToOne);
					String temp2 = "dev";
					relation.setJoinColumn(temp2);
					relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
					relation.setJoinColumnUpper(temp2.toUpperCase());

					relationList.add(relation);
					
					
					c.generatorCodeMessage(columntList, entityInfo,relationList);
				}
				if(true){
					//--CurrentWandaiEntity
					ReadTable r = new ReadTable();
			        String tableName = "mordo_state_current_wandai";
					List<Columnt> columntList = r.readColumntByTableName(tableName,"hs_15_02");
					
					CodeGenerate c= new CodeGenerate();
					
					EntityInfo entityInfo = new EntityInfo();		
					entityInfo.setBussiPackage("fly");
					Date date = new Date();
					SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					entityInfo.setCreateTime(formater1.format(date));
					
					entityInfo.setTableName(tableName);
					entityInfo.setTableDescription("腕带当前信息");
					String temp="currentWandai";
					entityInfo.setEntityPackage(temp);
					entityInfo.setEntityName(StringUtil.firstUpperCase(temp));
					entityInfo.setTableUpperName(temp.toUpperCase());
					
					List<RelationInfo> relationList = new ArrayList<RelationInfo>();
					RelationInfo relation = new RelationInfo();
					relation.setRelation(RelationInfo.ManyToOne);
					String temp2 = "dev";
					relation.setJoinColumn(temp2);
					relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
					relation.setJoinColumnUpper(temp2.toUpperCase());

					relationList.add(relation);
					
					
					c.generatorCodeMessage(columntList, entityInfo,relationList);
				}
	if(true){
		//--HistoryBedEntity
		ReadTable r = new ReadTable();
        String tableName = "mordo_state_history_bed";
		List<Columnt> columntList = r.readColumntByTableName(tableName,"hs_15_02");
		
		CodeGenerate c= new CodeGenerate();
		
		EntityInfo entityInfo = new EntityInfo();		
		entityInfo.setBussiPackage("fly");
		Date date = new Date();
		SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		entityInfo.setCreateTime(formater1.format(date));
		
		entityInfo.setTableName(tableName);
		entityInfo.setTableDescription("床垫历史信息");
		String temp="historyBed";
		entityInfo.setEntityPackage(temp);
		entityInfo.setEntityName(StringUtil.firstUpperCase(temp));
		entityInfo.setTableUpperName(temp.toUpperCase());
		
		List<RelationInfo> relationList = new ArrayList<RelationInfo>();
		RelationInfo relation = new RelationInfo();
		relation.setRelation(RelationInfo.ManyToOne);
		String temp2 = "dev";
		relation.setJoinColumn(temp2);
		relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
		relation.setJoinColumnUpper(temp2.toUpperCase());

		relationList.add(relation);
		
		
		c.generatorCodeMessage(columntList, entityInfo,relationList);
	}
	if(true){
		//--HistoryIrEntity
		ReadTable r = new ReadTable();
        String tableName = "mordo_state_history_ir";
		List<Columnt> columntList = r.readColumntByTableName(tableName,"hs_15_02");
		
		CodeGenerate c= new CodeGenerate();
		
		EntityInfo entityInfo = new EntityInfo();		
		entityInfo.setBussiPackage("fly");
		Date date = new Date();
		SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		entityInfo.setCreateTime(formater1.format(date));
		
		entityInfo.setTableName(tableName);
		entityInfo.setTableDescription("红外历史信息");
		String temp="historyIr";
		entityInfo.setEntityPackage(temp);
		entityInfo.setEntityName(StringUtil.firstUpperCase(temp));
		entityInfo.setTableUpperName(temp.toUpperCase());
		
		List<RelationInfo> relationList = new ArrayList<RelationInfo>();
		RelationInfo relation = new RelationInfo();
		relation.setRelation(RelationInfo.ManyToOne);
		String temp2 = "dev";
		relation.setJoinColumn(temp2);
		relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
		relation.setJoinColumnUpper(temp2.toUpperCase());

		relationList.add(relation);
		
		
		c.generatorCodeMessage(columntList, entityInfo,relationList);
	}
	if(true){
		//--HistoryDoorEntity
		ReadTable r = new ReadTable();
        String tableName = "mordo_state_history_door";
		List<Columnt> columntList = r.readColumntByTableName(tableName,"hs_15_02");
		
		CodeGenerate c= new CodeGenerate();
		
		EntityInfo entityInfo = new EntityInfo();		
		entityInfo.setBussiPackage("fly");
		Date date = new Date();
		SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		entityInfo.setCreateTime(formater1.format(date));
		
		entityInfo.setTableName(tableName);
		entityInfo.setTableDescription("门磁历史信息");
		String temp="historyDoor";
		entityInfo.setEntityPackage(temp);
		entityInfo.setEntityName(StringUtil.firstUpperCase(temp));
		entityInfo.setTableUpperName(temp.toUpperCase());
		
		List<RelationInfo> relationList = new ArrayList<RelationInfo>();
		RelationInfo relation = new RelationInfo();
		relation.setRelation(RelationInfo.ManyToOne);
		String temp2 = "dev";
		relation.setJoinColumn(temp2);
		relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
		relation.setJoinColumnUpper(temp2.toUpperCase());

		relationList.add(relation);
		
		
		c.generatorCodeMessage(columntList, entityInfo,relationList);
	}
	if(true){
		//--HistoryKeyalarmEntity
		ReadTable r = new ReadTable();
        String tableName = "mordo_state_history_keyalarm";
		List<Columnt> columntList = r.readColumntByTableName(tableName,"hs_15_02");
		
		CodeGenerate c= new CodeGenerate();
		
		EntityInfo entityInfo = new EntityInfo();		
		entityInfo.setBussiPackage("fly");
		Date date = new Date();
		SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		entityInfo.setCreateTime(formater1.format(date));
		
		entityInfo.setTableName(tableName);
		entityInfo.setTableDescription("一键报警历史信息");
		String temp="historyKeyalarm";
		entityInfo.setEntityPackage(temp);
		entityInfo.setEntityName(StringUtil.firstUpperCase(temp));
		entityInfo.setTableUpperName(temp.toUpperCase());
		
		List<RelationInfo> relationList = new ArrayList<RelationInfo>();
		RelationInfo relation = new RelationInfo();
		relation.setRelation(RelationInfo.ManyToOne);
		String temp2 = "dev";
		relation.setJoinColumn(temp2);
		relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
		relation.setJoinColumnUpper(temp2.toUpperCase());

		relationList.add(relation);
		
		
		c.generatorCodeMessage(columntList, entityInfo,relationList);
	}
	if(true){
		//--HistoryLocationBodyEntity
		ReadTable r = new ReadTable();
        String tableName = "mordo_state_history_location_body";
		List<Columnt> columntList = r.readColumntByTableName(tableName,"hs_15_02");
		
		CodeGenerate c= new CodeGenerate();
		
		EntityInfo entityInfo = new EntityInfo();		
		entityInfo.setBussiPackage("fly");
		Date date = new Date();
		SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		entityInfo.setCreateTime(formater1.format(date));
		
		entityInfo.setTableName(tableName);
		entityInfo.setTableDescription("定位器身体历史状态");
		String temp="historyLocationBody";
		entityInfo.setEntityPackage(temp);
		entityInfo.setEntityName(StringUtil.firstUpperCase(temp));
		entityInfo.setTableUpperName(temp.toUpperCase());
		
		List<RelationInfo> relationList = new ArrayList<RelationInfo>();
		RelationInfo relation = new RelationInfo();
		relation.setRelation(RelationInfo.ManyToOne);
		String temp2 = "dev";
		relation.setJoinColumn(temp2);
		relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
		relation.setJoinColumnUpper(temp2.toUpperCase());

		relationList.add(relation);
		
		
		c.generatorCodeMessage(columntList, entityInfo,relationList);
	}
	if(true){
		//--HistoryLocationManualEntity
		ReadTable r = new ReadTable();
        String tableName = "mordo_state_history_location_manual";
		List<Columnt> columntList = r.readColumntByTableName(tableName,"hs_15_02");
		
		CodeGenerate c= new CodeGenerate();
		
		EntityInfo entityInfo = new EntityInfo();		
		entityInfo.setBussiPackage("fly");
		Date date = new Date();
		SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		entityInfo.setCreateTime(formater1.format(date));
		
		entityInfo.setTableName(tableName);
		entityInfo.setTableDescription("定位器手动报警历史状态");
		String temp="historyLocationManual";
		entityInfo.setEntityPackage(temp);
		entityInfo.setEntityName(StringUtil.firstUpperCase(temp));
		entityInfo.setTableUpperName(temp.toUpperCase());
		
		List<RelationInfo> relationList = new ArrayList<RelationInfo>();
		RelationInfo relation = new RelationInfo();
		relation.setRelation(RelationInfo.ManyToOne);
		String temp2 = "dev";
		relation.setJoinColumn(temp2);
		relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
		relation.setJoinColumnUpper(temp2.toUpperCase());

		relationList.add(relation);
		
		
		c.generatorCodeMessage(columntList, entityInfo,relationList);
	}
	if(true){
		//--HistoryLocationMoveEntity
		ReadTable r = new ReadTable();
        String tableName = "mordo_state_history_location_move";
		List<Columnt> columntList = r.readColumntByTableName(tableName,"hs_15_02");
		
		CodeGenerate c= new CodeGenerate();
		
		EntityInfo entityInfo = new EntityInfo();		
		entityInfo.setBussiPackage("fly");
		Date date = new Date();
		SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		entityInfo.setCreateTime(formater1.format(date));
		
		entityInfo.setTableName(tableName);
		entityInfo.setTableDescription("定位器人卡分离历史状态");
		String temp="historyLocationMove";
		entityInfo.setEntityPackage(temp);
		entityInfo.setEntityName(StringUtil.firstUpperCase(temp));
		entityInfo.setTableUpperName(temp.toUpperCase());
		
		List<RelationInfo> relationList = new ArrayList<RelationInfo>();
		RelationInfo relation = new RelationInfo();
		relation.setRelation(RelationInfo.ManyToOne);
		String temp2 = "dev";
		relation.setJoinColumn(temp2);
		relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
		relation.setJoinColumnUpper(temp2.toUpperCase());

		relationList.add(relation);
		
		
		c.generatorCodeMessage(columntList, entityInfo,relationList);
	}
	if(true){
		//--HistoryLocationPosEntity
		ReadTable r = new ReadTable();
        String tableName = "mordo_state_history_location_pos";
		List<Columnt> columntList = r.readColumntByTableName(tableName,"hs_15_02");
		
		CodeGenerate c= new CodeGenerate();
		
		EntityInfo entityInfo = new EntityInfo();		
		entityInfo.setBussiPackage("fly");
		Date date = new Date();
		SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		entityInfo.setCreateTime(formater1.format(date));
		
		entityInfo.setTableName(tableName);
		entityInfo.setTableDescription("定位器位置历史状态");
		String temp="historyLocationPos";
		entityInfo.setEntityPackage(temp);
		entityInfo.setEntityName(StringUtil.firstUpperCase(temp));
		entityInfo.setTableUpperName(temp.toUpperCase());
		
		List<RelationInfo> relationList = new ArrayList<RelationInfo>();
		RelationInfo relation = new RelationInfo();
		relation.setRelation(RelationInfo.ManyToOne);
		String temp2 = "dev";
		relation.setJoinColumn(temp2);
		relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
		relation.setJoinColumnUpper(temp2.toUpperCase());

		relationList.add(relation);
		
		
		c.generatorCodeMessage(columntList, entityInfo,relationList);
	}
	if(true){
		//--HistoryUrineEntity
		ReadTable r = new ReadTable();
        String tableName = "mordo_state_history_urine";
		List<Columnt> columntList = r.readColumntByTableName(tableName,"hs_15_02");
		
		CodeGenerate c= new CodeGenerate();
		
		EntityInfo entityInfo = new EntityInfo();		
		entityInfo.setBussiPackage("fly");
		Date date = new Date();
		SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		entityInfo.setCreateTime(formater1.format(date));
		
		entityInfo.setTableName(tableName);
		entityInfo.setTableDescription("尿湿历史信息");
		String temp="historyUrine";
		entityInfo.setEntityPackage(temp);
		entityInfo.setEntityName(StringUtil.firstUpperCase(temp));
		entityInfo.setTableUpperName(temp.toUpperCase());
		
		List<RelationInfo> relationList = new ArrayList<RelationInfo>();
		RelationInfo relation = new RelationInfo();
		relation.setRelation(RelationInfo.ManyToOne);
		String temp2 = "dev";
		relation.setJoinColumn(temp2);
		relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
		relation.setJoinColumnUpper(temp2.toUpperCase());

		relationList.add(relation);
		
		
		c.generatorCodeMessage(columntList, entityInfo,relationList);
	}
	if(true){
		//--HistoryWandaiEntity
		ReadTable r = new ReadTable();
        String tableName = "mordo_state_history_wandai";
		List<Columnt> columntList = r.readColumntByTableName(tableName,"hs_15_02");
		
		CodeGenerate c= new CodeGenerate();
		
		EntityInfo entityInfo = new EntityInfo();		
		entityInfo.setBussiPackage("fly");
		Date date = new Date();
		SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		entityInfo.setCreateTime(formater1.format(date));
		
		entityInfo.setTableName(tableName);
		entityInfo.setTableDescription("腕带历史信息");
		String temp="historyWandai";
		entityInfo.setEntityPackage(temp);
		entityInfo.setEntityName(StringUtil.firstUpperCase(temp));
		entityInfo.setTableUpperName(temp.toUpperCase());
		
		List<RelationInfo> relationList = new ArrayList<RelationInfo>();
		RelationInfo relation = new RelationInfo();
		relation.setRelation(RelationInfo.ManyToOne);
		String temp2 = "dev";
		relation.setJoinColumn(temp2);
		relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
		relation.setJoinColumnUpper(temp2.toUpperCase());

		relationList.add(relation);
		
		
		c.generatorCodeMessage(columntList, entityInfo,relationList);
	}
	//----DEVSERVICE
	if(true){
		ReadTable r = new ReadTable();
        String tableName = "mordo_dev_info";
		List<Columnt> columntList = r.readColumntByTableName(tableName,"hs_15_02");
		
		CodeGenerate c= new CodeGenerate();
		
		EntityInfo entityInfo = new EntityInfo();		
		entityInfo.setBussiPackage("fly");
		Date date = new Date();
		SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		entityInfo.setCreateTime(formater1.format(date));
		
		entityInfo.setTableName(tableName);
		entityInfo.setTableDescription("设备信息");
		entityInfo.setEntityPackage("dev");
		entityInfo.setTableUpperName("dev".toUpperCase());
		entityInfo.setEntityName(StringUtil.firstUpperCase("dev"));
		
		
		
		List<RelationInfo> relationList = new ArrayList<RelationInfo>();
		if(true){
			RelationInfo relation = new RelationInfo();
			relation.setRelation(RelationInfo.ManyToMany);
			relation.setJoinColumn("position");
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase("position"));
			relation.setJoinColumnUpper("position".toUpperCase());
			relation.setJionFirst("Y");
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
			relation.setRelation(RelationInfo.OneToOne);
			relation.setJoinColumn("dev");
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase("dev"));
			relation.setJoinColumnUpper("dev".toUpperCase());
			relation.setJionFirst("Y");
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn("alarmCurrent");
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase("alarmCurrent"));
			relation.setJoinColumnUpper("alarmCurrent".toUpperCase());
			relation.setJionFirst("Y");
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn("alarmHistory");
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase("alarmHistory"));
			relation.setJoinColumnUpper("alarmHistory".toUpperCase());
			relation.setJionFirst("Y");
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn("currentBed");
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase("currentBed"));
			relation.setJoinColumnUpper("currentBed".toUpperCase());
			relation.setJionFirst("Y");
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn("currentDoor");
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase("currentDoor"));
			relation.setJoinColumnUpper("currentDoor".toUpperCase());
			relation.setJionFirst("Y");
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn("currentGateway");
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase("currentGateway"));
			relation.setJoinColumnUpper("currentGateway".toUpperCase());
			relation.setJionFirst("Y");
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn("currentKeyalarm");
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase("currentKeyalarm"));
			relation.setJoinColumnUpper("currentKeyalarm".toUpperCase());
			relation.setJionFirst("Y");
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn("currentLocation");
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase("currentLocation"));
			relation.setJoinColumnUpper("currentLocation".toUpperCase());
			relation.setJionFirst("Y");
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn("currentUrine");
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase("currentUrine"));
			relation.setJoinColumnUpper("currentUrine".toUpperCase());
			relation.setJionFirst("Y");
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn("currentWandai");
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase("currentWandai"));
			relation.setJoinColumnUpper("currentWandai".toUpperCase());
			relation.setJionFirst("Y");
			relationList.add(relation);
		}if(true){
			RelationInfo relation = new RelationInfo();
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn("historyBed");
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase("historyBed"));
			relation.setJoinColumnUpper("historyBed".toUpperCase());
			relation.setJionFirst("Y");
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn("historyDoor");
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase("historyDoor"));
			relation.setJoinColumnUpper("historyDoor".toUpperCase());
			relation.setJionFirst("Y");
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
			relation.setRelation(RelationInfo.OneToMany);
			String rname="historyKeyalarm";
			relation.setJoinColumn(rname);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(rname));
			relation.setJoinColumnUpper(rname.toUpperCase());
			relation.setJionFirst("Y");
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
			relation.setRelation(RelationInfo.OneToMany);
			String rname="historyLocationBody";
			relation.setJoinColumn(rname);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(rname));
			relation.setJoinColumnUpper(rname.toUpperCase());
			relation.setJionFirst("Y");
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
			relation.setRelation(RelationInfo.OneToMany);
			String rname="historyLocationManual";
			relation.setJoinColumn(rname);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(rname));
			relation.setJoinColumnUpper(rname.toUpperCase());
			relation.setJionFirst("Y");
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
			relation.setRelation(RelationInfo.OneToMany);
			String rname="historyLocationMove";
			relation.setJoinColumn(rname);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(rname));
			relation.setJoinColumnUpper(rname.toUpperCase());
			relation.setJionFirst("Y");
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
			relation.setRelation(RelationInfo.OneToMany);
			String rname="historyLocationPos";
			relation.setJoinColumn(rname);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(rname));
			relation.setJoinColumnUpper(rname.toUpperCase());
			relation.setJionFirst("Y");
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
			relation.setRelation(RelationInfo.OneToMany);
			String rname="historyUrine";
			relation.setJoinColumn(rname);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(rname));
			relation.setJoinColumnUpper(rname.toUpperCase());
			relation.setJionFirst("Y");
			relationList.add(relation);
		}
		
		
		
		
		c.generatorCodeMessage(columntList, entityInfo,relationList);
	}}
}
