/*    */ package fly.util;
/*    */ 
/*    */ import java.text.DateFormat;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import net.sf.json.JsonConfig;
/*    */ import net.sf.json.processors.JsonValueProcessor;
/*    */ 
/*    */ public class DateJsonValueProcessor
/*    */   implements JsonValueProcessor
/*    */ {
/*    */   public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
/*    */   private DateFormat dateFormat;
/*    */ 
/*    */   public DateJsonValueProcessor(String datePattern)
/*    */   {
/* 26 */     if (datePattern == null)
/* 27 */       this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
/*    */     else
/* 29 */       this.dateFormat = new SimpleDateFormat(datePattern);
/*    */   }
/*    */ 
/*    */   public Object processArrayValue(Object arg0, JsonConfig arg1)
/*    */   {
/* 40 */     return process(arg0);
/*    */   }
/*    */ 
/*    */   public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2)
/*    */   {
/* 48 */     return process(arg1);
/*    */   }
/*    */ 
/*    */   private Object process(Object value) {
/* 52 */     return this.dateFormat.format((Date)value);
/*    */   }
/*    */ }

/* Location:           E:\jhpt\WEB-INF\classes\
 * Qualified Name:     jhpt.util.DateJsonValueProcessor
 * JD-Core Version:    0.6.1
 */