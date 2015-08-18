/*     */ package fly.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.sf.json.JSONArray;
/*     */ import net.sf.json.JSONObject;
/*     */ import net.sf.json.JsonConfig;
/*     */ import net.sf.json.util.CycleDetectionStrategy;
/*     */ 
/*     */ public class JsonUtil
/*     */ {
/*     */   public static Object getObject4JsonString(String jsonString, Class<?> pojoCalss)
/*     */   {
/*  29 */     JSONObject jsonObject = JSONObject.fromObject(jsonString);
/*  30 */     Object pojo = JSONObject.toBean(jsonObject, pojoCalss);
/*  31 */     return pojo;
/*     */   }
/*     */ 
/*     */   public static Map<String, Object> getMap4Json(String jsonString)
/*     */   {
/*  42 */     JSONObject jsonObject = JSONObject.fromObject(jsonString);
/*  43 */     Iterator keyIter = jsonObject.keys();
/*     */ 
/*  46 */     Map valueMap = new HashMap();
/*  47 */     while (keyIter.hasNext())
/*     */     {
/*  49 */       String key = (String)keyIter.next();
/*  50 */       Object value = jsonObject.get(key);
/*  51 */       valueMap.put(key, value);
/*     */     }
/*  53 */     return valueMap;
/*     */   }
/*     */ 
/*     */   public static Object[] getObjectArray4Json(String jsonString)
/*     */   {
/*  63 */     JSONArray jsonArray = JSONArray.fromObject(jsonString);
/*  64 */     return jsonArray.toArray();
/*     */   }
/*     */ 
/*     */   public static List<Object> getList4Json(String jsonString, Class<?> pojoClass)
/*     */   {
/*  76 */     JSONArray jsonArray = JSONArray.fromObject(jsonString);
/*     */ 
/*  79 */     List list = new ArrayList();
/*  80 */     for (int i = 0; i < jsonArray.size(); i++) {
/*  81 */       JSONObject jsonObject = jsonArray.getJSONObject(i);
/*  82 */       Object pojoValue = JSONObject.toBean(jsonObject, pojoClass);
/*  83 */       list.add(pojoValue);
/*     */     }
/*     */ 
/*  86 */     return list;
/*     */   }
/*     */ 
/*     */   public static String[] getStringArray4Json(String jsonString)
/*     */   {
/*  97 */     JSONArray jsonArray = JSONArray.fromObject(jsonString);
/*  98 */     String[] stringArray = new String[jsonArray.size()];
/*  99 */     for (int i = 0; i < jsonArray.size(); i++) {
/* 100 */       stringArray[i] = jsonArray.getString(i);
/*     */     }
/*     */ 
/* 104 */     return stringArray;
/*     */   }
/*     */ 
/*     */   public static Long[] getLongArray4Json(String jsonString)
/*     */   {
/* 114 */     JSONArray jsonArray = JSONArray.fromObject(jsonString);
/* 115 */     Long[] longArray = new Long[jsonArray.size()];
/* 116 */     for (int i = 0; i < jsonArray.size(); i++) {
/* 117 */       longArray[i] = Long.valueOf(jsonArray.getLong(i));
/*     */     }
/*     */ 
/* 120 */     return longArray;
/*     */   }
/*     */ 
/*     */   public static Integer[] getIntegerArray4Json(String jsonString)
/*     */   {
/* 130 */     JSONArray jsonArray = JSONArray.fromObject(jsonString);
/* 131 */     Integer[] integerArray = new Integer[jsonArray.size()];
/* 132 */     for (int i = 0; i < jsonArray.size(); i++) {
/* 133 */       integerArray[i] = Integer.valueOf(jsonArray.getInt(i));
/*     */     }
/*     */ 
/* 136 */     return integerArray;
/*     */   }
/*     */ 
/*     */   public static Double[] getDoubleArray4Json(String jsonString)
/*     */   {
/* 167 */     JSONArray jsonArray = JSONArray.fromObject(jsonString);
/* 168 */     Double[] doubleArray = new Double[jsonArray.size()];
/* 169 */     for (int i = 0; i < jsonArray.size(); i++) {
/* 170 */       doubleArray[i] = Double.valueOf(jsonArray.getDouble(i));
/*     */     }
/*     */ 
/* 173 */     return doubleArray;
/*     */   }
/*     */ 
/*     */   public static String getJsonString4JavaPOJO(Object javaObj)
/*     */   {
/* 184 */     JSONObject json = JSONObject.fromObject(javaObj);
/* 185 */     return json.toString();
/*     */   }
/*     */ 
/*     */   public static String getJsonString4JavaPOJO(Object javaObj, String dataFormat)
/*     */   {
/* 201 */     JsonConfig jsonConfig = configJson(dataFormat);
/* 202 */     JSONObject json = JSONObject.fromObject(javaObj, jsonConfig);
/* 203 */     return json.toString();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*     */   }
/*     */ 
/*     */   public static JsonConfig configJson(String datePattern)
/*     */   {
/* 224 */     JsonConfig jsonConfig = new JsonConfig();
/* 225 */     jsonConfig.setExcludes(new String[] { "" });
/* 226 */     jsonConfig.setIgnoreDefaultExcludes(false);
/* 227 */     jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
/* 228 */     jsonConfig.registerJsonValueProcessor(Date.class, 
/* 229 */       new DateJsonValueProcessor(datePattern));
/*     */ 
/* 231 */     return jsonConfig;
/*     */   }
/*     */ 
/*     */   public static JsonConfig configJson(String[] excludes, String datePattern)
/*     */   {
/* 242 */     JsonConfig jsonConfig = new JsonConfig();
/* 243 */     jsonConfig.setExcludes(excludes);
/* 244 */     jsonConfig.setIgnoreDefaultExcludes(false);
/* 245 */     jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
/* 246 */     jsonConfig.registerJsonValueProcessor(Date.class, 
/* 247 */       new DateJsonValueProcessor(datePattern));
/*     */ 
/* 249 */     return jsonConfig;
/*     */   }
/*     */ }

/* Location:           E:\jhpt\WEB-INF\classes\
 * Qualified Name:     jhpt.util.JsonUtil
 * JD-Core Version:    0.6.1
 */