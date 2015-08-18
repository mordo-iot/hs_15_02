/*    */ package fly.util;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class Distance
/*    */ {
/*    */   private static final double EARTH_RADIUS = 6378137.0D;
/*    */ 
/*    */   private static double rad(double d)
/*    */   {
/*  7 */     return d * 3.141592653589793D / 180.0D;
/*    */   }
/*    */ 
/*    */   public static double GetDistance(double lng1, double lat1, double lng2, double lat2)
/*    */   {
/* 20 */     double radLat1 = rad(lat1);
/* 21 */     double radLat2 = rad(lat2);
/* 22 */     double a = radLat1 - radLat2;
/* 23 */     double b = rad(lng1) - rad(lng2);
/* 24 */     double s = 2.0D * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2.0D), 2.0D) + 
/* 25 */       Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2.0D), 2.0D)));
/* 26 */     s *= 6378137.0D;
/* 27 */     s = Math.round(s * 10000.0D) / 10000L;
/* 28 */     return s;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 38 */     double distance = GetDistance(121.49190900000001D, 31.233233999999999D, 121.41199400000001D, 31.206133999999999D);
/* 39 */     System.out.println("Distance is:" + distance);
/*    */   }
/*    */ }

/* Location:           E:\jhpt\WEB-INF\classes\
 * Qualified Name:     jhpt.util.Distance
 * JD-Core Version:    0.6.1
 */