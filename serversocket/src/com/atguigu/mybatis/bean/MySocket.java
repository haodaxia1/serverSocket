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
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.atguigu.mybatis.test.EmployeeMapper;
import com.atguigu.mybatis.test.UserMapper;

import net.sf.json.JSONObject;

public class MySocket {

	private static final int PORT = 30003;
	private List<Socket> mList = new ArrayList<Socket>();
	private ServerSocket server = null;
	private ExecutorService mExecutorService = null;
	private String receiveMsg;
	private String sendMsg;

	public static void main(String[] args) {
		new MySocket();
		
	}

	
	public MySocket() {
		try {
			server = new ServerSocket(PORT);
			mExecutorService = Executors.newCachedThreadPool();
			System.out.println("������3������...");
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
//				printWriter.println("�ɹ����ӷ�����" + "�����������ͣ�");
				System.out.println("�ɹ����ӷ�����");
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
						String username=object.getString("username");
						String password=object.getString("password");
						int zhuangtai=object.getInt("zhuangtai");
//						System.out.println(zhuangtai);
						String resource = "mybatis_config.xml";
						InputStream inputStream = Resources.getResourceAsStream(resource);
						SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
						if(zhuangtai==0){
							System.out.println("������ע��");
							SqlSession opSession=sqlSessionFactory.openSession(true);
							UserMapper mapper=opSession.getMapper(UserMapper.class);
							user u=new user(username,password);
							System.out.println(u);
							mapper.addUser(u);
						}else if(zhuangtai==1){
							System.out.println("�����ڵ�½");
							SqlSession opSession=sqlSessionFactory.openSession(true);
							UserMapper mapper=opSession.getMapper(UserMapper.class);
							user u=mapper.getUserByName(username);
							 
							int postion;
							if(u.getPassword().equals(password)){
								postion=99;
								System.out.println("�����û���ƥ��ɹ����ظ��ͻ���");
								JSONObject obj=new JSONObject();
								obj.put("postion", postion);
								printWriter.println(obj);
							}else{
								postion=-99;
								System.out.println("�����û���ƥ��ʧ�����ѿͻ���");
								JSONObject obj=new JSONObject();
								obj.put("postion", postion);
								printWriter.println(obj);
							}	
						}
					}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
