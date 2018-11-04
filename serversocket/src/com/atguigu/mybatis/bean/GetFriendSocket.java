package com.atguigu.mybatis.bean;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.atguigu.mybatis.test.EmployeeMapper;
import com.atguigu.mybatis.test.MessageMapper;
import com.atguigu.mybatis.test.RealtionMapper;
import com.atguigu.mybatis.test.UserMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetFriendSocket {

	private static final int PORT = 30006;
	private List<Socket> mList = new ArrayList<Socket>();
	private ServerSocket server = null;
	private ExecutorService mExecutorService = null;
	private String receiveMsg;
	private String sendMsg;

	public static void main(String[] args) {
		new GetFriendSocket();
		
	}

	
	public GetFriendSocket() {
		try {
			server = new ServerSocket(PORT);
			mExecutorService = Executors.newCachedThreadPool();
			System.out.println("服务器2已启动...");
			Socket client = null;
			while (true) {
				client = server.accept();
				mList.add(client);
				mExecutorService.execute(new Service(client));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class Service implements Runnable {
		private Socket socket;
		private BufferedReader in = null;
		private PrintWriter printWriter = null;

		public Service(Socket socket) {
			this.socket = socket;
			try {
				printWriter = new PrintWriter(
						new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")), true);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
//				printWriter.println("成功连接服务器" + "（服务器发送）");
				System.out.println("成功连接服务器");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			try {
				while (true) {
					if ((receiveMsg = in.readLine()) != null) {
						System.out.println("receiveMsg:" + receiveMsg);
						JSONObject object=JSONObject.fromObject(receiveMsg);
						String fromname=object.getString("fromname");
//						String toname=object.getString("toname");
//						String time=object.getString("time");
//						String message=object.getString("message");
//						System.out.println(fromname);
						//
						String resource = "mybatis_config.xml";
						InputStream inputStream = Resources.getResourceAsStream(resource);
						SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
						SqlSession opSession=sqlSessionFactory.openSession(true);
						RealtionMapper mapper=opSession.getMapper(RealtionMapper.class);
						List<realtion> rr=mapper.getReal(fromname);
						JSONArray jsonArray=new JSONArray();
						Iterator<realtion> it=rr.iterator();
						while(it.hasNext()){
							JSONObject obj=new JSONObject();					
							realtion r=it.next();
							obj.put("mname",r.getMname());
							obj.put("fname", r.getMname());
							jsonArray.add(obj);
						}
							
						
						String jsondata=jsonArray.toString();
						System.out.println(jsondata);
						printWriter.println(jsonArray);
							
							
						}
						
//						if (receiveMsg.equals("0")) {
//							System.out.println("客户端请求断开连接");
//							printWriter.println("服务端断开连接" + "（服务器发送）");
//							mList.remove(socket);
//							in.close();
//							socket.close();
//							break;
//						} else {
//							sendMsg = "我已接收：" + receiveMsg + "（服务器发送）";
////							printWriter.println(sendMsg);
//						}
					}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
