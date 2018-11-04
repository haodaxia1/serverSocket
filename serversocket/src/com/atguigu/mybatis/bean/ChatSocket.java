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
import com.atguigu.mybatis.test.SpaceMapper;
import com.atguigu.mybatis.test.UserMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ChatSocket {

	private static final int PORT = 30001;
	private List<Socket> mList = new ArrayList<Socket>();
	private ServerSocket server = null;
	private ExecutorService mExecutorService = null;
	private String receiveMsg;
	private String sendMsg;

	public static void main(String[] args) {
		new ChatSocket();
		
	}

	
	public ChatSocket() {
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
				
					if ((receiveMsg = in.readLine()) != null) {
						System.out.println("receiveMsg:" + receiveMsg);
						JSONObject object=JSONObject.fromObject(receiveMsg);
						int zhuangtai=object.getInt("zhuangtai");
						if(zhuangtai==0){
							String fromname=object.getString("fromname");
							String toname=object.getString("toname");
							String time=object.getString("time");
							String message=object.getString("message");
							System.out.println(fromname);
							//
							String resource = "mybatis_config.xml";
							InputStream inputStream = Resources.getResourceAsStream(resource);
							SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
							SqlSession opSession=sqlSessionFactory.openSession(true);
							MessageMapper mapper=opSession.getMapper(MessageMapper.class);
							message mm=new message(fromname,toname,time,message);
							mapper.setMes(mm);
							String tooname="aaa";
							List<message> mess=mapper.getMes(fromname,tooname);
							JSONArray jsonArray=new JSONArray();
							Iterator<message> it=mess.iterator();
							while(it.hasNext()){
								JSONObject obj=new JSONObject();					
								message ms=it.next();
								obj.put("fromname",ms.getFromname());
								obj.put("message", ms.getMessage());
								jsonArray.add(obj);
							}
								
							
							String jsondata=jsonArray.toString();
							System.out.println(jsondata);
							printWriter.println(jsonArray);
						}else if(zhuangtai==1){
							String fromname=object.getString("fromname");
							String tooname=object.getString("toname");
							String resource = "mybatis_config.xml";
							InputStream inputStream = Resources.getResourceAsStream(resource);
							SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
							SqlSession opSession=sqlSessionFactory.openSession(true);
							MessageMapper mapper=opSession.getMapper(MessageMapper.class);
							
							List<message> mess=mapper.getMes(fromname,tooname);
							JSONArray jsonArray=new JSONArray();
							Iterator<message> it=mess.iterator();
							while(it.hasNext()){
								JSONObject obj=new JSONObject();					
								message ms=it.next();
								obj.put("fromname",ms.getFromname());
								obj.put("message", ms.getMessage());
								jsonArray.add(obj);
							}
							String jsondata=jsonArray.toString();
							System.out.println(jsondata);
							printWriter.println(jsonArray);
						}else if(zhuangtai==2){
							//传送好友列表
							String fromname=object.getString("fromname");
							String resource = "mybatis_config.xml";
							InputStream inputStream = Resources.getResourceAsStream(resource);
							SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
							SqlSession opSession=sqlSessionFactory.openSession(true);
							RealtionMapper mapper=opSession.getMapper(RealtionMapper.class);
							List<realtion> rea=mapper.getReal(fromname);
							JSONArray jsonArray=new JSONArray();
							Iterator<realtion> it=rea.iterator();
							while(it.hasNext()){
								JSONObject obj=new JSONObject();					
								realtion rr=it.next();
								obj.put("fname",rr.getFname());
								jsonArray.add(obj);
							}
							String jsondata=jsonArray.toString();
							System.out.println(jsondata);
							printWriter.println(jsonArray);
							
						}else if(zhuangtai==3){
							String username=object.getString("username");
							String password=object.getString("password");
							String resource = "mybatis_config.xml";
							InputStream inputStream = Resources.getResourceAsStream(resource);
							SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
							SqlSession opSession=sqlSessionFactory.openSession(true);
							System.out.println("这是在注册");
							UserMapper mapper=opSession.getMapper(UserMapper.class);
							user u=new user(username,password);
							System.out.println(u);
							mapper.addUser(u);
							
						}else if(zhuangtai==4){
							String username=object.getString("username");
							String password=object.getString("password");
							String resource = "mybatis_config.xml";
							InputStream inputStream = Resources.getResourceAsStream(resource);
							SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
							SqlSession opSession=sqlSessionFactory.openSession(true);
							System.out.println("这是在登陆");
							UserMapper mapper=opSession.getMapper(UserMapper.class);
							user u=mapper.getUserByName(username);
							 
							int postion;
							if(u.getPassword().equals(password)){
								postion=99;
								System.out.println("密码用户名匹配成功返回给客户端");
								JSONObject obj=new JSONObject();
								obj.put("postion", postion);
								printWriter.println(obj);
							}else{
								postion=-99;
								System.out.println("密码用户名匹配失败提醒客户端");
								JSONObject obj=new JSONObject();
								obj.put("postion", postion);
								printWriter.println(obj);
							}	
						}else if(zhuangtai==5){//接收朋友圈
							System.out.println(zhuangtai);
							String fromname=object.getString("fromname");
							String resource = "mybatis_config.xml";
							InputStream inputStream = Resources.getResourceAsStream(resource);
							SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
							SqlSession opSession=sqlSessionFactory.openSession(true);
							SpaceMapper mapper=opSession.getMapper(SpaceMapper.class);
							List<space> rea=mapper.getSpace(fromname);
							JSONArray jsonArray=new JSONArray();
							Iterator<space> it=rea.iterator();
							while(it.hasNext()){
								JSONObject obj=new JSONObject();					
								space rr=it.next();
								obj.put("fromname",rr.getFromname());
								obj.put("time",rr.getTime() );
								obj.put("num", rr.getNum());
								obj.put("message", rr.getMessage());
								jsonArray.add(obj);
							}
							String jsondata=jsonArray.toString();
							System.out.println(jsondata);
							printWriter.println(jsonArray);
						}else if(zhuangtai==6){//发送朋友圈消息
							String fromname=object.getString("fromname");
							String message=object.getString("message");
							String time=object.getString("time");
							String resource = "mybatis_config.xml";
							InputStream inputStream = Resources.getResourceAsStream(resource);
							SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
							SqlSession opSession=sqlSessionFactory.openSession(true);
							SpaceMapper mapper=opSession.getMapper(SpaceMapper.class);
							space mm=new space(fromname,message,time);
							System.out.println("这是在发送朋友圈");
							mapper.insertSpace(mm);						
							}else if(zhuangtai==7){
								//点赞
								System.err.println(zhuangtai);
								String message=object.getString("message");
								String resource = "mybatis_config.xml";
								InputStream inputStream = Resources.getResourceAsStream(resource);
								SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
								SqlSession opSession=sqlSessionFactory.openSession(true);
								SpaceMapper mapper=opSession.getMapper(SpaceMapper.class);
								space ss=mapper.getOneSpace(message);
								int num=ss.getNum();
								num=num+1;
								ss.setNum(num);
								mapper.updateSpace(ss);
								
							}
							
						}
						

				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
