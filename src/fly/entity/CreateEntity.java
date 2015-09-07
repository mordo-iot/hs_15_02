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
		
		//--DevPositionEntity
				if(true){
					ReadTable r = new ReadTable();
			        String tableName = "mordo_map_dev_position";
					List<Columnt> columntList = r.readColumntByTableName(tableName,"hs_15_02");
					
					CodeGenerate c= new CodeGenerate();
					
					EntityInfo entityInfo = new EntityInfo();		
					entityInfo.setBussiPackage("fly");
					Date date = new Date();
					SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					entityInfo.setCreateTime(formater1.format(date));
					
					entityInfo.setTableName(tableName);
					entityInfo.setTableDescription("设备安装位置映射");
					String temp="devPosition";
					entityInfo.setEntityPackage(temp);
					entityInfo.setEntityName(StringUtil.firstUpperCase(temp));
					entityInfo.setTableUpperName(temp.toUpperCase());
					
					List<RelationInfo> relationList = new ArrayList<RelationInfo>();
				
					
					
					c.generatorCodeMessage(columntList, entityInfo,relationList);
				}
		//--CurrentTizhengBedEntity
		if(true){
			ReadTable r = new ReadTable();
	        String tableName = "mordo_state_current_tizheng_bed";
			List<Columnt> columntList = r.readColumntByTableName(tableName,"hs_15_02");
			
			CodeGenerate c= new CodeGenerate();
			
			EntityInfo entityInfo = new EntityInfo();		
			entityInfo.setBussiPackage("fly");
			Date date = new Date();
			SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			entityInfo.setCreateTime(formater1.format(date));
			
			entityInfo.setTableName(tableName);
			entityInfo.setTableDescription("体征床垫当前信息表");
			String temp="currentTizhengBed";
			entityInfo.setEntityPackage(temp);
			entityInfo.setEntityName(StringUtil.firstUpperCase(temp));
			entityInfo.setTableUpperName(temp.toUpperCase());
			
			List<RelationInfo> relationList = new ArrayList<RelationInfo>();
			RelationInfo relation = new RelationInfo();
			relation.setRelation(RelationInfo.ManyToOne);
			String temp2 = "dev";
			relation.setJoinColumn(temp2);String tableName2="mordo_dev_info";List<Columnt> joinTableColumnts2 = r.readColumntByTableName(tableName2,"hs_15_02");
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
			relation.setJoinColumnUpper(temp2.toUpperCase());relation.setJoinTableName(tableName2);relation.setJoinTableColumnts2(joinTableColumnts2);

			relationList.add(relation);
			
			
			c.generatorCodeMessage(columntList, entityInfo,relationList);
		}
		//--HistoryTizhengBedEntity
				if(true){
					ReadTable r = new ReadTable();
			        String tableName = "mordo_state_history_tizheng_bed";
					List<Columnt> columntList = r.readColumntByTableName(tableName,"hs_15_02");
					
					CodeGenerate c= new CodeGenerate();
					
					EntityInfo entityInfo = new EntityInfo();		
					entityInfo.setBussiPackage("fly");
					Date date = new Date();
					SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					entityInfo.setCreateTime(formater1.format(date));
					
					entityInfo.setTableName(tableName);
					entityInfo.setTableDescription("体征床垫历史信息表");
					String temp="historyTizhengBed";
					entityInfo.setEntityPackage(temp);
					entityInfo.setEntityName(StringUtil.firstUpperCase(temp));
					entityInfo.setTableUpperName(temp.toUpperCase());
					
					List<RelationInfo> relationList = new ArrayList<RelationInfo>();
					RelationInfo relation = new RelationInfo();
					String tableName2="mordo_dev_info";
					List<Columnt> joinTableColumnts2 = r.readColumntByTableName(tableName2,"hs_15_02");
					String temp2 = "dev";
					relation.setRelation(RelationInfo.ManyToOne);					
					relation.setJoinColumn(temp2);					
					relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
					relation.setJoinColumnUpper(temp2.toUpperCase());
					relation.setJoinTableName(tableName2);
					relation.setJoinTableColumnts2(joinTableColumnts2);
					relationList.add(relation);
					
					
					c.generatorCodeMessage(columntList, entityInfo,relationList);
				}
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
//			String temp2 = "dev";
//			String tableName2="mordo_dev_info";
//			List<Columnt> joinTableColumnts2 = r.readColumntByTableName(tableName2,"hs_15_02");
//			relation.setRelation(RelationInfo.ManyToMany);
//			relation.setJoinColumn(temp2);			
//			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
//			relation.setJoinColumnUpper(temp2.toUpperCase());
//			relation.setJoinTableName(tableName2);
//			relation.setJoinTableColumnts2(joinTableColumnts2);
//			relation.setJionFirst("N");
			
			String tableName4="mordo_map_dev_position";
			String tableName5="mordo_dev_info";
			String temp4="dev";
			String jionFirst="N";
			List<Columnt> joinTableColumnts4 = r.readColumntByTableName(tableName4,"hs_15_02");
			List<Columnt> joinTableColumnts5 = r.readColumntByTableName(tableName5,"hs_15_02");
			relation.setRelation(RelationInfo.ManyToMany);
			relation.setJoinColumn(temp4);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp4));
			relation.setJoinColumnUpper(temp4.toUpperCase());
			relation.setJoinTableName(tableName4);
			relation.setJoinTableName2(tableName5);
			relation.setJoinTableColumnts(joinTableColumnts4);
			relation.setJoinTableColumnts2(joinTableColumnts5);
			relation.setJionFirst(jionFirst);
			
			
			relationList.add(relation);
			}
			
			if(true){
				RelationInfo relation = new RelationInfo();
				relation.setRelation(RelationInfo.ParentToOne);
//				relation.setJoinColumn("position");
//				relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase("position"));
//				relation.setJoinColumnUpper("position".toUpperCase());
//				relation.setJionFirst("Y");
				relationList.add(relation);
			}
			
			
		
			
			
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
//			RelationInfo relation = new RelationInfo();
//			relation.setRelation(RelationInfo.OneToMany);
//			String temp2 = "userLog";
//			relation.setJoinColumn(temp2);
//			String tableName2="mordo_dev_info";
//			List<Columnt> joinTableColumnts2 = r.readColumntByTableName(tableName2,"hs_15_02");
//			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
//			relation.setJoinColumnUpper(temp2.toUpperCase());
//			relation.setJoinTableName(tableName2);
//			relation.setJoinTableColumnts2(joinTableColumnts2);
			
			RelationInfo relation3 = new RelationInfo();
			String tableName3="mordo_user_log";
			String temp3="userLog";
			List<Columnt> joinTableColumnts3 = r.readColumntByTableName(tableName3,"hs_15_02");		
			relation3.setRelation(RelationInfo.OneToMany);
			relation3.setJoinColumn(temp3);
			relation3.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp3));
			relation3.setJoinColumnUpper(temp3.toUpperCase());
			relation3.setJoinTableName(tableName3);
			relation3.setJoinTableColumnts2(joinTableColumnts3);
	
			relationList.add(relation3);
			
			
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
		relation.setJoinColumn(temp2);String tableName2="mordo_sys_user";List<Columnt> joinTableColumnts2 = r.readColumntByTableName(tableName2,"hs_15_02");
		relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
		relation.setJoinColumnUpper(temp2.toUpperCase());relation.setJoinTableName(tableName2);relation.setJoinTableColumnts2(joinTableColumnts2);

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
		String tableName2="mordo_dev_info";
		List<Columnt> joinTableColumnts2 = r.readColumntByTableName(tableName2,"hs_15_02");
		String temp2 = "dev";
		relation.setRelation(RelationInfo.ManyToOne);					
		relation.setJoinColumn(temp2);					
		relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
		relation.setJoinColumnUpper(temp2.toUpperCase());
		relation.setJoinTableName(tableName2);
		relation.setJoinTableColumnts2(joinTableColumnts2);

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
		relation.setJoinColumn(temp2);String tableName2="mordo_dev_info";List<Columnt> joinTableColumnts2 = r.readColumntByTableName(tableName2,"hs_15_02");
		relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
		relation.setJoinColumnUpper(temp2.toUpperCase());relation.setJoinTableName(tableName2);relation.setJoinTableColumnts2(joinTableColumnts2);

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
			relation.setJoinColumn(temp2);String tableName2="mordo_dev_info";List<Columnt> joinTableColumnts2 = r.readColumntByTableName(tableName2,"hs_15_02");
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
			relation.setJoinColumnUpper(temp2.toUpperCase());relation.setJoinTableName(tableName2);relation.setJoinTableColumnts2(joinTableColumnts2);

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
					relation.setJoinColumn(temp2);String tableName2="mordo_dev_info";List<Columnt> joinTableColumnts2 = r.readColumntByTableName(tableName2,"hs_15_02");
					relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
					relation.setJoinColumnUpper(temp2.toUpperCase());relation.setJoinTableName(tableName2);relation.setJoinTableColumnts2(joinTableColumnts2);

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
		relation.setJoinColumn(temp2);String tableName2="mordo_dev_info";List<Columnt> joinTableColumnts2 = r.readColumntByTableName(tableName2,"hs_15_02");
		relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
		relation.setJoinColumnUpper(temp2.toUpperCase());relation.setJoinTableName(tableName2);relation.setJoinTableColumnts2(joinTableColumnts2);

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
			relation.setJoinColumn(temp2);String tableName2="mordo_dev_info";List<Columnt> joinTableColumnts2 = r.readColumntByTableName(tableName2,"hs_15_02");
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
			relation.setJoinColumnUpper(temp2.toUpperCase());relation.setJoinTableName(tableName2);relation.setJoinTableColumnts2(joinTableColumnts2);

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
					relation.setJoinColumn(temp2);String tableName2="mordo_dev_info";List<Columnt> joinTableColumnts2 = r.readColumntByTableName(tableName2,"hs_15_02");
					relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
					relation.setJoinColumnUpper(temp2.toUpperCase());relation.setJoinTableName(tableName2);relation.setJoinTableColumnts2(joinTableColumnts2);

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
						relation.setJoinColumn(temp2);String tableName2="mordo_dev_info";List<Columnt> joinTableColumnts2 = r.readColumntByTableName(tableName2,"hs_15_02");
						relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
						relation.setJoinColumnUpper(temp2.toUpperCase());relation.setJoinTableName(tableName2);relation.setJoinTableColumnts2(joinTableColumnts2);

						relationList.add(relation);
						
						
					}
					if(true){
						RelationInfo relation = new RelationInfo();
						relation.setRelation(RelationInfo.ManyToOne);
						String temp2 = "position";
						relation.setJoinColumn(temp2);String tableName2="mordo_position_info";List<Columnt> joinTableColumnts2 = r.readColumntByTableName(tableName2,"hs_15_02");
						relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
						relation.setJoinColumnUpper(temp2.toUpperCase());relation.setJoinTableName(tableName2);relation.setJoinTableColumnts2(joinTableColumnts2);

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
					relation.setJoinColumn(temp2);String tableName2="mordo_dev_info";List<Columnt> joinTableColumnts2 = r.readColumntByTableName(tableName2,"hs_15_02");
					relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
					relation.setJoinColumnUpper(temp2.toUpperCase());relation.setJoinTableName(tableName2);relation.setJoinTableColumnts2(joinTableColumnts2);

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
					relation.setJoinColumn(temp2);String tableName2="mordo_dev_info";List<Columnt> joinTableColumnts2 = r.readColumntByTableName(tableName2,"hs_15_02");
					relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
					relation.setJoinColumnUpper(temp2.toUpperCase());relation.setJoinTableName(tableName2);relation.setJoinTableColumnts2(joinTableColumnts2);

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
		relation.setJoinColumn(temp2);String tableName2="mordo_dev_info";List<Columnt> joinTableColumnts2 = r.readColumntByTableName(tableName2,"hs_15_02");
		relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
		relation.setJoinColumnUpper(temp2.toUpperCase());relation.setJoinTableName(tableName2);relation.setJoinTableColumnts2(joinTableColumnts2);

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
		relation.setJoinColumn(temp2);String tableName2="mordo_dev_info";List<Columnt> joinTableColumnts2 = r.readColumntByTableName(tableName2,"hs_15_02");
		relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
		relation.setJoinColumnUpper(temp2.toUpperCase());relation.setJoinTableName(tableName2);relation.setJoinTableColumnts2(joinTableColumnts2);

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
		relation.setJoinColumn(temp2);String tableName2="mordo_dev_info";List<Columnt> joinTableColumnts2 = r.readColumntByTableName(tableName2,"hs_15_02");
		relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
		relation.setJoinColumnUpper(temp2.toUpperCase());relation.setJoinTableName(tableName2);relation.setJoinTableColumnts2(joinTableColumnts2);

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
		relation.setJoinColumn(temp2);String tableName2="mordo_dev_info";List<Columnt> joinTableColumnts2 = r.readColumntByTableName(tableName2,"hs_15_02");
		relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
		relation.setJoinColumnUpper(temp2.toUpperCase());relation.setJoinTableName(tableName2);relation.setJoinTableColumnts2(joinTableColumnts2);

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
		relation.setJoinColumn(temp2);String tableName2="mordo_dev_info";List<Columnt> joinTableColumnts2 = r.readColumntByTableName(tableName2,"hs_15_02");
		relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
		relation.setJoinColumnUpper(temp2.toUpperCase());relation.setJoinTableName(tableName2);relation.setJoinTableColumnts2(joinTableColumnts2);

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
		relation.setJoinColumn(temp2);String tableName2="mordo_dev_info";List<Columnt> joinTableColumnts2 = r.readColumntByTableName(tableName2,"hs_15_02");
		relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
		relation.setJoinColumnUpper(temp2.toUpperCase());relation.setJoinTableName(tableName2);relation.setJoinTableColumnts2(joinTableColumnts2);

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
		relation.setJoinColumn(temp2);String tableName2="mordo_dev_info";List<Columnt> joinTableColumnts2 = r.readColumntByTableName(tableName2,"hs_15_02");
		relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
		relation.setJoinColumnUpper(temp2.toUpperCase());relation.setJoinTableName(tableName2);relation.setJoinTableColumnts2(joinTableColumnts2);

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
		relation.setJoinColumn(temp2);String tableName2="mordo_dev_info";List<Columnt> joinTableColumnts2 = r.readColumntByTableName(tableName2,"hs_15_02");
		relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
		relation.setJoinColumnUpper(temp2.toUpperCase());relation.setJoinTableName(tableName2);relation.setJoinTableColumnts2(joinTableColumnts2);

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
		relation.setJoinColumn(temp2);String tableName2="mordo_dev_info";List<Columnt> joinTableColumnts2 = r.readColumntByTableName(tableName2,"hs_15_02");
		relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
		relation.setJoinColumnUpper(temp2.toUpperCase());relation.setJoinTableName(tableName2);relation.setJoinTableColumnts2(joinTableColumnts2);

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
		relation.setJoinColumn(temp2);String tableName2="mordo_dev_info";List<Columnt> joinTableColumnts2 = r.readColumntByTableName(tableName2,"hs_15_02");
		relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp2));
		relation.setJoinColumnUpper(temp2.toUpperCase());relation.setJoinTableName(tableName2);relation.setJoinTableColumnts2(joinTableColumnts2);

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
//			relation.setRelation(RelationInfo.ManyToMany);
//			relation.setJoinColumn("position");
//			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase("position"));
//			relation.setJoinColumnUpper("position".toUpperCase());
//			relation.setJionFirst("Y");
			
			String tableName4="mordo_map_dev_position";
			String tableName5="mordo_position_info";
			String temp4="position";
			String jionFirst="Y";
			List<Columnt> joinTableColumnts4 = r.readColumntByTableName(tableName4,"hs_15_02");
			List<Columnt> joinTableColumnts5 = r.readColumntByTableName(tableName5,"hs_15_02");
			relation.setRelation(RelationInfo.ManyToMany);
			relation.setJoinColumn(temp4);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp4));
			relation.setJoinColumnUpper(temp4.toUpperCase());
			relation.setJoinTableName(tableName4);
			relation.setJoinTableName2(tableName5);
			relation.setJoinTableColumnts(joinTableColumnts4);
			relation.setJoinTableColumnts2(joinTableColumnts5);
			relation.setJionFirst(jionFirst);
			
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
			relation.setRelation(RelationInfo.ParentToOne);
//			relation.setJoinColumn("dev");
//			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase("dev"));
//			relation.setJoinColumnUpper("dev".toUpperCase());
//			relation.setJionFirst("Y");
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
//			relation.setRelation(RelationInfo.OneToMany);
//			relation.setJoinColumn("alarmCurrent");
//			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase("alarmCurrent"));
//			relation.setJoinColumnUpper("alarmCurrent".toUpperCase());
//			relation.setJionFirst("Y");

			String tableName3="mordo_alarm_current";
			String temp3="alarmCurrent";
			List<Columnt> joinTableColumnts3 = r.readColumntByTableName(tableName3,"hs_15_02");		
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn(temp3);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp3));
			relation.setJoinColumnUpper(temp3.toUpperCase());
			relation.setJoinTableName(tableName3);
			relation.setJoinTableColumnts2(joinTableColumnts3);
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
//			relation.setRelation(RelationInfo.OneToMany);
//			relation.setJoinColumn("alarmHistory");
//			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase("alarmHistory"));
//			relation.setJoinColumnUpper("alarmHistory".toUpperCase());
//			relation.setJionFirst("Y");
			
			String tableName3="mordo_alarm_history";
			String temp3="alarmHistory";
			List<Columnt> joinTableColumnts3 = r.readColumntByTableName(tableName3,"hs_15_02");		
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn(temp3);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp3));
			relation.setJoinColumnUpper(temp3.toUpperCase());
			relation.setJoinTableName(tableName3);
			relation.setJoinTableColumnts2(joinTableColumnts3);
			
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
//			relation.setRelation(RelationInfo.OneToMany);
//			relation.setJoinColumn("currentBed");
//			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase("currentBed"));
//			relation.setJoinColumnUpper("currentBed".toUpperCase());
//			relation.setJionFirst("Y");
			
			String tableName3="mordo_state_current_bed";
			String temp3="currentBed";
			List<Columnt> joinTableColumnts3 = r.readColumntByTableName(tableName3,"hs_15_02");		
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn(temp3);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp3));
			relation.setJoinColumnUpper(temp3.toUpperCase());
			relation.setJoinTableName(tableName3);
			relation.setJoinTableColumnts2(joinTableColumnts3);
			
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
//			relation.setRelation(RelationInfo.OneToMany);
//			relation.setJoinColumn("currentDoor");
//			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase("currentDoor"));
//			relation.setJoinColumnUpper("currentDoor".toUpperCase());
//			relation.setJionFirst("Y");
			
			String tableName3="mordo_state_current_door";
			String temp3="currentDoor";
			List<Columnt> joinTableColumnts3 = r.readColumntByTableName(tableName3,"hs_15_02");		
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn(temp3);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp3));
			relation.setJoinColumnUpper(temp3.toUpperCase());
			relation.setJoinTableName(tableName3);
			relation.setJoinTableColumnts2(joinTableColumnts3);
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
//			relation.setRelation(RelationInfo.OneToMany);
//			relation.setJoinColumn("currentGateway");
//			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase("currentGateway"));
//			relation.setJoinColumnUpper("currentGateway".toUpperCase());
//			relation.setJionFirst("Y");
			String tableName3="mordo_state_current_gateway";
			String temp3="currentGateway";
			List<Columnt> joinTableColumnts3 = r.readColumntByTableName(tableName3,"hs_15_02");		
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn(temp3);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp3));
			relation.setJoinColumnUpper(temp3.toUpperCase());
			relation.setJoinTableName(tableName3);
			relation.setJoinTableColumnts2(joinTableColumnts3);
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
//			relation.setRelation(RelationInfo.OneToMany);
//			relation.setJoinColumn("currentKeyalarm");
//			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase("currentKeyalarm"));
//			relation.setJoinColumnUpper("currentKeyalarm".toUpperCase());
//			relation.setJionFirst("Y");
			String tableName3="mordo_state_current_keyalarm";
			String temp3="currentKeyalarm";
			List<Columnt> joinTableColumnts3 = r.readColumntByTableName(tableName3,"hs_15_02");		
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn(temp3);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp3));
			relation.setJoinColumnUpper(temp3.toUpperCase());
			relation.setJoinTableName(tableName3);
			relation.setJoinTableColumnts2(joinTableColumnts3);
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
//			relation.setRelation(RelationInfo.OneToMany);
//			relation.setJoinColumn("currentLocation");
//			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase("currentLocation"));
//			relation.setJoinColumnUpper("currentLocation".toUpperCase());
//			relation.setJionFirst("Y");
			String tableName3="mordo_state_current_location";
			String temp3="currentLocation";
			List<Columnt> joinTableColumnts3 = r.readColumntByTableName(tableName3,"hs_15_02");		
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn(temp3);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp3));
			relation.setJoinColumnUpper(temp3.toUpperCase());
			relation.setJoinTableName(tableName3);
			relation.setJoinTableColumnts2(joinTableColumnts3);
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
//			relation.setRelation(RelationInfo.OneToMany);
//			relation.setJoinColumn("currentUrine");
//			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase("currentUrine"));
//			relation.setJoinColumnUpper("currentUrine".toUpperCase());
//			relation.setJionFirst("Y");
			String tableName3="mordo_state_current_urine";
			String temp3="currentUrine";
			List<Columnt> joinTableColumnts3 = r.readColumntByTableName(tableName3,"hs_15_02");		
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn(temp3);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp3));
			relation.setJoinColumnUpper(temp3.toUpperCase());
			relation.setJoinTableName(tableName3);
			relation.setJoinTableColumnts2(joinTableColumnts3);
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
//			relation.setRelation(RelationInfo.OneToMany);
//			relation.setJoinColumn("currentWandai");
//			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase("currentWandai"));
//			relation.setJoinColumnUpper("currentWandai".toUpperCase());
//			relation.setJionFirst("Y");
			String tableName3="mordo_state_current_wandai";
			String temp3="currentWandai";
			List<Columnt> joinTableColumnts3 = r.readColumntByTableName(tableName3,"hs_15_02");		
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn(temp3);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp3));
			relation.setJoinColumnUpper(temp3.toUpperCase());
			relation.setJoinTableName(tableName3);
			relation.setJoinTableColumnts2(joinTableColumnts3);
			relationList.add(relation);
		}if(true){
			RelationInfo relation = new RelationInfo();
//			relation.setRelation(RelationInfo.OneToMany);
//			relation.setJoinColumn("historyBed");
//			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase("historyBed"));
//			relation.setJoinColumnUpper("historyBed".toUpperCase());
//			relation.setJionFirst("Y");
			String tableName3="mordo_state_history_bed";
			String temp3="historyBed";
			List<Columnt> joinTableColumnts3 = r.readColumntByTableName(tableName3,"hs_15_02");		
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn(temp3);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp3));
			relation.setJoinColumnUpper(temp3.toUpperCase());
			relation.setJoinTableName(tableName3);
			relation.setJoinTableColumnts2(joinTableColumnts3);
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
//			relation.setRelation(RelationInfo.OneToMany);
//			relation.setJoinColumn("historyDoor");
//			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase("historyDoor"));
//			relation.setJoinColumnUpper("historyDoor".toUpperCase());
//			relation.setJionFirst("Y");
			String tableName3="mordo_state_history_door";
			String temp3="historyDoor";
			List<Columnt> joinTableColumnts3 = r.readColumntByTableName(tableName3,"hs_15_02");		
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn(temp3);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp3));
			relation.setJoinColumnUpper(temp3.toUpperCase());
			relation.setJoinTableName(tableName3);
			relation.setJoinTableColumnts2(joinTableColumnts3);
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
//			relation.setRelation(RelationInfo.OneToMany);
//			String rname="historyKeyalarm";
//			relation.setJoinColumn(rname);
//			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(rname));
//			relation.setJoinColumnUpper(rname.toUpperCase());
//			relation.setJionFirst("Y");
			String tableName3="mordo_state_history_keyalarm";
			String temp3="historyKeyalarm";
			List<Columnt> joinTableColumnts3 = r.readColumntByTableName(tableName3,"hs_15_02");		
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn(temp3);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp3));
			relation.setJoinColumnUpper(temp3.toUpperCase());
			relation.setJoinTableName(tableName3);
			relation.setJoinTableColumnts2(joinTableColumnts3);
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
//			relation.setRelation(RelationInfo.OneToMany);
//			String rname="historyLocationBody";
//			relation.setJoinColumn(rname);
//			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(rname));
//			relation.setJoinColumnUpper(rname.toUpperCase());
//			relation.setJionFirst("Y");
			String tableName3="mordo_state_history_location_body";
			String temp3="historyLocationBody";
			List<Columnt> joinTableColumnts3 = r.readColumntByTableName(tableName3,"hs_15_02");		
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn(temp3);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp3));
			relation.setJoinColumnUpper(temp3.toUpperCase());
			relation.setJoinTableName(tableName3);
			relation.setJoinTableColumnts2(joinTableColumnts3);
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
//			relation.setRelation(RelationInfo.OneToMany);
//			String rname="historyLocationManual";
//			relation.setJoinColumn(rname);
//			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(rname));
//			relation.setJoinColumnUpper(rname.toUpperCase());
//			relation.setJionFirst("Y");
			String tableName3="mordo_state_history_location_manual";
			String temp3="historyLocationManual";
			List<Columnt> joinTableColumnts3 = r.readColumntByTableName(tableName3,"hs_15_02");		
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn(temp3);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp3));
			relation.setJoinColumnUpper(temp3.toUpperCase());
			relation.setJoinTableName(tableName3);
			relation.setJoinTableColumnts2(joinTableColumnts3);
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
//			relation.setRelation(RelationInfo.OneToMany);
//			String rname="historyLocationMove";
//			relation.setJoinColumn(rname);
//			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(rname));
//			relation.setJoinColumnUpper(rname.toUpperCase());
//			relation.setJionFirst("Y");
			String tableName3="mordo_state_history_location_move";
			String temp3="historyLocationMove";
			List<Columnt> joinTableColumnts3 = r.readColumntByTableName(tableName3,"hs_15_02");		
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn(temp3);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp3));
			relation.setJoinColumnUpper(temp3.toUpperCase());
			relation.setJoinTableName(tableName3);
			relation.setJoinTableColumnts2(joinTableColumnts3);
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
//			relation.setRelation(RelationInfo.OneToMany);
//			String rname="historyLocationPos";
//			relation.setJoinColumn(rname);
//			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(rname));
//			relation.setJoinColumnUpper(rname.toUpperCase());
//			relation.setJionFirst("Y");
			String tableName3="mordo_state_history_location_pos";
			String temp3="historyLocationPos";
			List<Columnt> joinTableColumnts3 = r.readColumntByTableName(tableName3,"hs_15_02");		
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn(temp3);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp3));
			relation.setJoinColumnUpper(temp3.toUpperCase());
			relation.setJoinTableName(tableName3);
			relation.setJoinTableColumnts2(joinTableColumnts3);
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
//			relation.setRelation(RelationInfo.OneToMany);
//			String rname="historyUrine";
//			relation.setJoinColumn(rname);
//			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(rname));
//			relation.setJoinColumnUpper(rname.toUpperCase());
//			relation.setJionFirst("Y");
			String tableName3="mordo_state_history_urine";
			String temp3="historyUrine";
			List<Columnt> joinTableColumnts3 = r.readColumntByTableName(tableName3,"hs_15_02");		
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn(temp3);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp3));
			relation.setJoinColumnUpper(temp3.toUpperCase());
			relation.setJoinTableName(tableName3);
			relation.setJoinTableColumnts2(joinTableColumnts3);
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
			String tableName3="mordo_state_current_ir";
			String temp3="currentIr";
			List<Columnt> joinTableColumnts3 = r.readColumntByTableName(tableName3,"hs_15_02");		
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn(temp3);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp3));
			relation.setJoinColumnUpper(temp3.toUpperCase());
			relation.setJoinTableName(tableName3);
			relation.setJoinTableColumnts2(joinTableColumnts3);
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
			String tableName3="mordo_state_history_ir";
			String temp3="historyIr";
			List<Columnt> joinTableColumnts3 = r.readColumntByTableName(tableName3,"hs_15_02");		
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn(temp3);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp3));
			relation.setJoinColumnUpper(temp3.toUpperCase());
			relation.setJoinTableName(tableName3);
			relation.setJoinTableColumnts2(joinTableColumnts3);
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
			String tableName3="mordo_state_current_tizheng_bed";
			String temp3="currentTizhengBed";
			List<Columnt> joinTableColumnts3 = r.readColumntByTableName(tableName3,"hs_15_02");		
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn(temp3);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp3));
			relation.setJoinColumnUpper(temp3.toUpperCase());
			relation.setJoinTableName(tableName3);
			relation.setJoinTableColumnts2(joinTableColumnts3);
			relationList.add(relation);
		}
		if(true){
			RelationInfo relation = new RelationInfo();
			String tableName3="mordo_state_history_tizheng_bed";
			String temp3="historyTizhengBed";
			List<Columnt> joinTableColumnts3 = r.readColumntByTableName(tableName3,"hs_15_02");		
			relation.setRelation(RelationInfo.OneToMany);
			relation.setJoinColumn(temp3);
			relation.setJoinColumnFirstUpper(StringUtil.firstUpperCase(temp3));
			relation.setJoinColumnUpper(temp3.toUpperCase());
			relation.setJoinTableName(tableName3);
			relation.setJoinTableColumnts2(joinTableColumnts3);
			relationList.add(relation);
		}
		
		
		
		
		c.generatorCodeMessage(columntList, entityInfo,relationList);
	}}
}
