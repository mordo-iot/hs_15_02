/*     */ package fly.util;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.Properties;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class FileUtil
/*     */ {
/*  23 */   private static Logger logger = Logger.getLogger(FileUtil.class);
/*     */ 
/*     */   public static String getFileSeparator()
/*     */   {
/*  29 */     String fs = System.getProperties().getProperty("file.separator");
/*  30 */     return fs;
/*     */   }
/*     */ 
/*     */   public static void writeFile(String filePath, String head, String content)
/*     */   {
/*  39 */     FileWriter fw = null;
/*  40 */     BufferedWriter bw = null;
/*  41 */     FileReader fr = null;
/*  42 */     BufferedReader buf = null;
/*  43 */     StringBuffer sb = new StringBuffer();
/*     */     try
/*     */     {
/*  46 */       File file = new File(filePath);
/*  47 */       if (!file.exists()) {
/*  48 */         File f = new File(filePath.substring(0, filePath.lastIndexOf(getFileSeparator())));
/*     */ 
/*  50 */         if (!f.isDirectory()) {
/*  51 */           f.mkdirs();
/*     */         }
/*  53 */         if ((head != null) && (!"".equals(head.trim())))
/*  54 */           sb.append(head + "\r\n" + content + "\r\n");
/*     */       }
/*     */       else {
/*  57 */         fr = new FileReader(file);
/*  58 */         buf = new BufferedReader(fr);
/*  59 */         String s = buf.readLine();
/*  60 */         sb.append(s + "\r\n" + content + "\r\n");
/*  61 */         while (s != null)
/*     */         {
/*  63 */           s = buf.readLine();
/*  64 */           if (s != null) {
/*  65 */             sb.append(s + "\r\n");
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*  70 */       fw = new FileWriter(file);
/*  71 */       bw = new BufferedWriter(fw);
/*  72 */       bw.write(sb.toString());
/*     */     }
/*     */     catch (Exception e) {
/*  75 */       logger.error(e);
/*     */       try
/*     */       {
/*  78 */         if (bw != null) {
/*  79 */           bw.close();
/*  80 */           bw = null;
/*     */         }
/*  82 */         if (fw != null) {
/*  83 */           fw.close();
/*  84 */           fw = null;
/*     */         }
/*  86 */         if (fr != null) {
/*  87 */           fr.close();
/*  88 */           fr = null;
/*     */         }
/*  90 */         if (buf != null) {
/*  91 */           buf.close();
/*  92 */           buf = null;
/*     */         }
/*     */       }
/*     */       catch (IOException e1) {
/*  96 */         logger.error(e);
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/*  78 */         if (bw != null) {
/*  79 */           bw.close();
/*  80 */           bw = null;
/*     */         }
/*  82 */         if (fw != null) {
/*  83 */           fw.close();
/*  84 */           fw = null;
/*     */         }
/*  86 */         if (fr != null) {
/*  87 */           fr.close();
/*  88 */           fr = null;
/*     */         }
/*  90 */         if (buf != null) {
/*  91 */           buf.close();
/*  92 */           buf = null;
/*     */         }
/*     */       }
/*     */       catch (IOException e) {
/*  96 */         logger.error(e);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void writeFile(String filePath, String content)
/*     */   {
/* 108 */     FileWriter fw = null;
/* 109 */     BufferedWriter bw = null;
/* 110 */     FileReader fr = null;
/* 111 */     BufferedReader buf = null;
/*     */     try
/*     */     {
/* 114 */       File file = new File(filePath);
/* 115 */       if (!file.exists()) {
/* 116 */         File f = new File(filePath.substring(0, filePath.lastIndexOf(getFileSeparator())));
/*     */ 
/* 118 */         if (!f.isDirectory()) {
/* 119 */           f.mkdirs();
/*     */         }
/*     */       }
/*     */ 
/* 123 */       fw = new FileWriter(file);
/* 124 */       bw = new BufferedWriter(fw);
/* 125 */       bw.write(content);
/*     */     }
/*     */     catch (Exception e) {
/* 128 */       logger.error(e);
/*     */       try
/*     */       {
/* 131 */         if (bw != null) {
/* 132 */           bw.close();
/* 133 */           bw = null;
/*     */         }
/* 135 */         if (fw != null) {
/* 136 */           fw.close();
/* 137 */           fw = null;
/*     */         }
/* 139 */         if (fr != null) {
/* 140 */           fr.close();
/* 141 */           fr = null;
/*     */         }
/* 143 */         if (buf != null) {
/* 144 */           buf.close();
/* 145 */           buf = null;
/*     */         }
/*     */       }
/*     */       catch (IOException e1) {
/* 149 */         logger.error(e);
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 131 */         if (bw != null) {
/* 132 */           bw.close();
/* 133 */           bw = null;
/*     */         }
/* 135 */         if (fw != null) {
/* 136 */           fw.close();
/* 137 */           fw = null;
/*     */         }
/* 139 */         if (fr != null) {
/* 140 */           fr.close();
/* 141 */           fr = null;
/*     */         }
/* 143 */         if (buf != null) {
/* 144 */           buf.close();
/* 145 */           buf = null;
/*     */         }
/*     */       }
/*     */       catch (IOException e) {
/* 149 */         logger.error(e);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void mkdirFile(String filePath)
/*     */   {
/* 160 */     FileWriter fw = null;
/* 161 */     BufferedWriter bw = null;
/*     */     try
/*     */     {
/* 164 */       File file = new File(filePath);
/* 165 */       if (!file.exists()) {
/* 166 */         File f = new File(filePath.substring(0, filePath.lastIndexOf(getFileSeparator())));
/*     */ 
/* 168 */         if (!f.isDirectory()) {
/* 169 */           f.mkdirs();
/*     */         }
/*     */       }
/*     */ 
/* 173 */       fw = new FileWriter(file, true);
/* 174 */       bw = new BufferedWriter(fw);
/* 175 */       bw.write("");
/*     */     }
/*     */     catch (Exception e) {
/* 178 */       logger.error(e);
/*     */       try
/*     */       {
/* 181 */         if (bw != null) {
/* 182 */           bw.close();
/* 183 */           bw = null;
/*     */         }
/* 185 */         if (fw != null) {
/* 186 */           fw.close();
/* 187 */           fw = null;
/*     */         }
/*     */       }
/*     */       catch (IOException e1) {
/* 191 */         logger.error(e);
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 181 */         if (bw != null) {
/* 182 */           bw.close();
/* 183 */           bw = null;
/*     */         }
/* 185 */         if (fw != null) {
/* 186 */           fw.close();
/* 187 */           fw = null;
/*     */         }
/*     */       }
/*     */       catch (IOException e) {
/* 191 */         logger.error(e);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Properties readProperties(String filePath)
/*     */   {
/* 202 */     InputStream in = null;
/* 203 */     Properties pros = new Properties();
/*     */     try {
/* 205 */       in = new FileInputStream(filePath);
/* 206 */       pros.load(in);
/*     */     } catch (Exception e) {
/* 208 */       logger.error(e);
/*     */       try
/*     */       {
/* 211 */         in.close();
/*     */       } catch (IOException e1) {
/* 213 */         logger.error(e1);
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 211 */         in.close();
/*     */       } catch (IOException e1) {
/* 213 */         logger.error(e1);
/*     */       }
/*     */     }
/* 216 */     return pros;
/*     */   }
/*     */ 
/*     */   public static String getProjectName()
/*     */   {
/* 224 */     String projectName = "";
/*     */     try {
/* 226 */       String path = FileUtil.class.getResource("FileUtil.class").toString();
/* 227 */       if (path.indexOf("webapps/") > 0) {
/* 228 */         String[] sage = path.split("webapps/");
/* 229 */         if ((sage != null) && (sage.length > 0)) {
/* 230 */           String[] temp = sage[1].split("/");
/* 231 */           if ((temp != null) && (temp.length > 0))
/* 232 */             projectName = temp[0];
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 237 */       logger.error(e);
/*     */     }
/* 239 */     return projectName;
/*     */   }
/*     */ 
/*     */   public static String readFileContent(String filePath)
/*     */   {
/* 248 */     String content = "";
/* 249 */     BufferedReader br = null;
/* 250 */     FileReader fr = null;
/*     */     try {
/* 252 */       File file = new File(filePath);
/*     */ 
/* 254 */       if (file.exists()) {
/* 255 */         fr = new FileReader(file);
/* 256 */         br = new BufferedReader(fr);
/* 257 */         String s = br.readLine();
/* 258 */         while (s != null)
/*     */         {
/* 260 */           content = content + s + "\r\n";
/* 261 */           s = br.readLine();
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 265 */       logger.error(e);
/*     */       try
/*     */       {
/* 268 */         if (br != null) {
/* 269 */           br.close();
/* 270 */           br = null;
/*     */         }
/* 272 */         if (fr != null) {
/* 273 */           fr.close();
/* 274 */           fr = null;
/*     */         }
/*     */       }
/*     */       catch (IOException e1) {
/* 278 */         logger.error(e);
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 268 */         if (br != null) {
/* 269 */           br.close();
/* 270 */           br = null;
/*     */         }
/* 272 */         if (fr != null) {
/* 273 */           fr.close();
/* 274 */           fr = null;
/*     */         }
/*     */       }
/*     */       catch (IOException e) {
/* 278 */         logger.error(e);
/*     */       }
/*     */     }
/* 281 */     return content;
/*     */   }
/*     */ 
/*     */   public static int getAge(Date birthDay) throws Exception {
/* 285 */     Calendar cal = Calendar.getInstance();
/*     */ 
/* 287 */     if (cal.before(birthDay)) {
/* 288 */       throw new IllegalArgumentException(
/* 289 */         "The birthDay is before Now.It's unbelievable!");
/*     */     }
/* 291 */     int yearNow = cal.get(1);
/* 292 */     int monthNow = cal.get(2);
/* 293 */     int dayOfMonthNow = cal.get(5);
/* 294 */     cal.setTime(birthDay);
/*     */ 
/* 296 */     int yearBirth = cal.get(1);
/* 297 */     int monthBirth = cal.get(2);
/* 298 */     int dayOfMonthBirth = cal.get(5);
/*     */ 
/* 300 */     int age = yearNow - yearBirth;
/*     */ 
/* 302 */     if (monthNow <= monthBirth) {
/* 303 */       if (monthNow == monthBirth) {
/* 304 */         if (dayOfMonthNow < dayOfMonthBirth) age--; 
/*     */       }
/*     */       else {
/* 306 */         age--;
/*     */       }
/*     */     }
/*     */ 
/* 310 */     return age;
/*     */   }
/*     */ }

/* Location:           E:\jhpt\WEB-INF\classes\
 * Qualified Name:     jhpt.util.FileUtil
 * JD-Core Version:    0.6.1
 */