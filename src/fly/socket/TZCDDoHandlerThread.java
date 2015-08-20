package fly.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import fly.handler.TZCDTCPMessageHandler;
import fly.service.DataService;

public class TZCDDoHandlerThread extends Thread {
	private static Logger logger = Logger.getLogger(TZCDDoHandlerThread.class);
	static final int BUFFER_SIZE = 4096;
	private Socket socket;
	private static TZCDTCPMessageHandler handler = new TZCDTCPMessageHandler();

	private SimpleDateFormat formater1 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private static SimpleDateFormat formater = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	public TZCDDoHandlerThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			while (!socket.isClosed()) {
				try {
					Thread.sleep(1000);// 等待时间
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				InputStream in = socket.getInputStream();

				byte[] reciveBytes = new byte[0];

				int offset = 0;

				int len = 0;
				byte[] buffer = new byte[4096];
				while (len != -1) {
					try {
						len = in.read(buffer, 0, 4096);
					} catch (SocketTimeoutException e) {
						break;
					}
					if (len != -1) {
						reciveBytes = ArrayUtils.addAll(reciveBytes, buffer);
						offset += len;
					}

					byte[] reqbyte = ArrayUtils.subarray(buffer, 0, len);
					if (reqbyte.length >= 0) {
						break;
					}
				}
				byte[] data = ArrayUtils.subarray(reciveBytes, 0, offset);
				if (data.length > 0) {
					logger.debug("TZCD数据信息监听: 数据长度byte: " + data.length);
					String bytestr = DataService.printHexString(data);
					logger.debug("TZCD数据信息监听: 数据内容: " + bytestr);
					// 06 5A3530313637 50 303030 3030 54 F1 04
					if (data[0] == (byte) 0x06 && data[15] == (byte) 0x04) {
						byte[] responsestr = handler.hanlder(data);
						byte[] rep = { 0x40, 0x4F, 0x46, 0x46, 0x40, 0x0d };
						logger.debug("TZCD数据信息监听: 回复数据: "
								+ DataService.printHexString(rep));
						OutputStream out = socket.getOutputStream();
						out.write(rep);
					}else{
						logger.debug("体征床垫信息监听:头尾帧格式不符！");
					}

				}

			}
		} catch (Exception e) {
			logger.debug("体征床垫信息监听:数据处理出错！");
			logger.error(e);
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				logger.error(e);
			}
			logger.debug("体征床垫信息监听:数据处理完毕！");
		}
	}
}