package fly.front.tools;

/**
 * 设备种类的ID和名称转换
 * @author WongYong
 */
public class DeviceTools {

	/** 温湿度设备 */
	private static final Integer wenshidu = 1;
	private static final String wenshiduName = "温湿度设备";
	private static final String wenshiduPatten = "04[0-9a-fA-F]{6}";
	
	/** 床垫设备 */
	private static final Integer chuangdian = 2;
	private static final String chuangdianName = "床垫设备";
	private static final String chuangdianPatten = "03[0-9a-fA-F]{6}";
	
	/** 洗手间红外设备 */
	private static final Integer xishoujianhongwai = 3;
	private static final String xishoujianhongwaiName = "洗手间红外设备";
	
	/** 园区内定位设备 */
	private static final Integer yuanquneidingwei = 4;
	private static final String yuanquneidingweiName = "园区内定位设备";
	private static final String yuanquneidingweiPatten = "05[0-9a-fA-F]{6}";  //05000000-05FFFFFF
	
	/** 园区外定位设备 */
	private static final Integer yuanquwaidingwei = 5;
	private static final String yuanquwaidingweiName = "园区外定位设备";
	private static final String yuanquwaidingweiPatten = "07[0-9a-fA-F]{6}";  //07000000-07FFFFFF
	
	/** 园区内定位辅助设备 */
	private static final Integer yuanquneidingweifuzhu = 6;
	private static final String yuanquneidingweifuzhuName = "园区内定位辅助设备";
	private static final String yuanquneidingweifuzhuPatten = "06[0-9a-fA-F]{6}";  //06000000-06FFFFFF
	
	/** 报警器（室内网关）设备 */
	private static final Integer shineiwangguan = 7;
	private static final String shineiwangguanName = "室内网关设备";
	private static final String shineiwangguanPatten = "02[0-9a-fA-F]{6}";  //02000000-02FFFFFF
	
	/** 园区内定位辅助设备-越界 */
	private static final Integer yuejie = 8;
	private static final String yuejieName = "园区内定位辅助设备-越界";
	private static final String yuejiePatten = "06[0-9a-fA-F]{6}";  //06000000-06FFFFFF
	
	/** 一键报警终端 */
	private static final Integer yijianbaojing = 9;
	private static final String yijianbaojingName = "一键报警终端";
	private static final String yijianbaojingPatten = "08[0-9a-fA-F]{6}";  //08000000-08FFFFFF
	
	/** 无线门磁终端 */
	private static final Integer wuxianmenci = 10;
	private static final String wuxianmenciName = "无线门磁终端";
	private static final String wuxianmenciPatten = "09[0-9a-fA-F]{6}";  //09000000-09FFFFFF
	
	/** 尿湿感应设备 */
	private static final Integer niaoshi = 11;
	private static final String niaoshiName = "尿湿感应设备";
	private static final String niaoshiPatten = "0[fF][0-9a-fA-F]{6}";  //0F000000-0FFFFFFF
	
	/** 腕带呼叫器 */
	private static final Integer wandai = 12;
	private static final String wandaiName = "腕带呼叫器";
	private static final String wandaiPatten = "11[0-9a-fA-F]{6}";  //11000000-11FFFFFF
	
	/** 红外设备 */
	private static final Integer hongwai = 13;
	private static final String hongwaiName = "红外设备";
	private static final String hongwaiPatten = "12[0-9a-fA-F]{6}";  //12000000-12FFFFFF
	
	/** 护工胸牌 */
	public static final Integer hugongxiongpai = 14;
	public static final String hugongxiongpaiName = "护工胸牌";
	public static final String hugongxiongpaiPatten = "0[bB][0-9a-fA-F]{6}";  //0B000000-0BFFFFFF
	
	/** 发射器 */
	private static final Integer fasheqi = 15;
	private static final String fasheqiName = "护工胸牌发射器";
	
	/** 心率床垫 */
	private static final Integer xinlvchuangdian = 16;
	private static final String xinlvchuangdianName = "心率床垫";
	
	/** 平安星手表 */
	private static final Integer pinganxingshoubiao = 17;
	private static final String pinganxingshoubiaoName = "平安星手表";
	
	/** 优频定位器 */
	private static final Integer youpindingwei = 18;
	private static final String youpindingweiName = "优频定位器";
	
	/** 门灯 */
	public static final Integer mendeng = 19;
	private static final String mendengName = "门灯";
	private static final String mendengPatten = "0[dD][0-9a-fA-F]{6}";  //0D000000-0DFFFFFF
	
	/** LED屏 */
	public static final Integer led = 20;
	private static final String ledName = "LED屏";
	private static final String ledPatten = "0[eE][0-9a-fA-F]{6}";  //0E000000-0EFFFFFF
	
	/** 电子看板 */
	public static final Integer dianzikanban = 21;
	private static final String dianzikanbanName = "电子看板";
	private static final String dianzikanbanPatten = "10[0-9a-fA-F]{6}";  //10000000-10ffffff
	
	/**
	 * 根据设备ID获取设备名称
	 * @param typeId 设备ID
	 * @return 设备名称
	 */
	public static String getDeviceTypeNameByTypeId(Integer typeId) {
		String result = "";
		
		if (typeId == wenshidu) {
			result = wenshiduName;
		} else if (typeId == chuangdian) {
			result = chuangdianName;
		} else if (typeId == xishoujianhongwai) {
			result  = xishoujianhongwaiName;
		} else if (typeId == yuanquneidingwei) {
			result =  yuanquneidingweiName;
		} else if (typeId == yuanquwaidingwei) {
			result = yuanquwaidingweiName;
		} else if (typeId == yuanquneidingweifuzhu) {
			result  = yuanquneidingweifuzhuName;
		} else if (typeId == shineiwangguan) {
			result = shineiwangguanName;
		} else if (typeId == yuejie) {
			result  = yuejieName;
		} else if (typeId == yijianbaojing) {
			result = yijianbaojingName;
		} else if (typeId == wuxianmenci) {
			result = wuxianmenciName;
		} else if (typeId == niaoshi) {
			result = niaoshiName;
		} else if (typeId == wandai) {
			result = wandaiName;
		} else if (typeId == hongwai) {
			result = hongwaiName;
		} else if (typeId == hugongxiongpai) {
			result = hugongxiongpaiName;
		} else if (typeId == fasheqi) {
			result = fasheqiName;
		} else if (typeId == xinlvchuangdian) {
			result = xinlvchuangdianName;
		} else if (typeId == pinganxingshoubiao) {
			result = pinganxingshoubiaoName;
		} else if (typeId == youpindingwei) {
			result = youpindingweiName;
		} else if (typeId == mendeng) {
			result = mendengName;
		} else if (typeId == led) {
			result = ledName;
		} else if (typeId == dianzikanban) {
			result = dianzikanbanName;
		} else {
			result = "其它设备";
		}
		
		return result;
	}
	
	/**
	 * 检测设备mac地址是否合法
	 * @param devtype 设备类型ID
	 * @param devMac 设备MAC地址
	 * @return null-合法，错误信息-非法
	 */
	public static String checkDevMac(Integer devtype, String devMac) {
		String result = null;
		
		if (devMac != null || devMac.length() > 0) {
			if (devtype == wenshidu) {
				if (!devMac.matches(wenshiduPatten)) {
					result = wenshiduName + "的MAC地址应为04000000-04FFFFFF";
				}
			} else if (devtype == chuangdian) {
				if (!devMac.matches(chuangdianPatten)) {
					result = chuangdianName + "的MAC地址应为03000000-03FFFFFF";
				}
			} else if (devtype == yuanquneidingwei) {
				if (!devMac.matches(yuanquneidingweiPatten)) {
					result = yuanquneidingweiName + "的MAC地址应为05000000-05FFFFFF";
				}
			} else if (devtype == yuanquwaidingwei) {
				if (!devMac.matches(yuanquwaidingweiPatten)) {
					result = yuanquwaidingweiName + "的MAC地址应为07000000-07FFFFFF";
				}
			} else if (devtype == yuanquneidingweifuzhu) {
				if (!devMac.matches(yuanquneidingweifuzhuPatten)) {
					result = yuanquneidingweifuzhuName + "的MAC地址应为06000000-06FFFFFF";
				}
			} else if (devtype == shineiwangguan) {
				if (!devMac.matches(shineiwangguanPatten)) {
					result = shineiwangguanName + "的MAC地址应为02000000-02FFFFFF";
				}
			} else if (devtype == yuejie) {
				if (!devMac.matches(yuejiePatten)) {
					result = yuejieName + "的MAC地址应为06000000-06FFFFFF";
				}
			} else if (devtype == yijianbaojing) {
				if (!devMac.matches(yijianbaojingPatten)) {
					result = yijianbaojingName + "的MAC地址应为08000000-08FFFFFF";
				}
			} else if (devtype == wuxianmenci) {
				if (!devMac.matches(wuxianmenciPatten)) {
					result = wuxianmenciName + "的MAC地址应为09000000-09FFFFFF";
				}
			} else if (devtype == niaoshi) {
				if (!devMac.matches(niaoshiPatten)) {
					result = niaoshiName + "的MAC地址应为0F000000-0FFFFFFF";
				}
			} else if (devtype == wandai) {
				if (!devMac.matches(wandaiPatten)) {
					result = wandaiName + "的MAC地址应为11000000-11FFFFFF";
				}
			} else if (devtype == hongwai) {
				if (!devMac.matches(hongwaiPatten)) {
					result = hongwaiName + "的MAC地址应为12000000-12FFFFFF";
				}
			} else if (devtype == hugongxiongpai) {
				if (!devMac.matches(hugongxiongpaiPatten)) {
					result = hugongxiongpaiName + "的MAC地址应为0B000000-0BFFFFFF";
				}
			} else if (devtype == mendeng) {
				if (!devMac.matches(mendengPatten)) {
					result = mendengName + "的MAC地址应为0D000000-0DFFFFFF";
				}
			} else if (devtype == led) {
				if (!devMac.matches(ledPatten)) {
					result = ledName + "的MAC地址应为0E000000-0EFFFFFF";
				}
			} else if (devtype == dianzikanban) {
				if (!devMac.matches(dianzikanbanPatten)) {
					result = dianzikanbanName + "的MAC地址应为10000000-10FFFFFF";
				}
			}
		}
		return result;
	}
}
