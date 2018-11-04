package com.atguigu.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.atguigu.mybatis.bean.Department;
import com.atguigu.mybatis.bean.Employee;

public class MyBatisTest {

	@Test
//	public SqlSessionFactory getSqlSessionFactory() throws IOException {
//		// TODO Auto-generated method stub
//		String resource = "mybatis_config.xml";
//		InputStream inputStream = Resources.getResourceAsStream(resource);
//		return new SqlSessionFactoryBuilder().build(inputStream);
//	}
	//day1
//	public void test() throws IOException {
//		String resource = "mybatis_config.xml";
//		InputStream inputStream = Resources.getResourceAsStream(resource);
//		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//		SqlSession opSession=sqlSessionFactory.openSession();
//		EmployeeMapper mapper=opSession.getMapper(EmployeeMapper.class);
//		try {
//			Employee employee=mapper.getEmpById(1);
//			System.out.println(employee);
//		} finally {
//			opSession.close();
//		}	
//	}
	public void test() throws IOException{
		String resource = "mybatis_config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		//1.获取到的sql'Session不会主动提交
//		SqlSession opSession=sqlSessionFactory.openSession();
		//2.自动提交
		SqlSession opSession=sqlSessionFactory.openSession(true);
		try {
			EmployeeMapper mapper=opSession.getMapper(EmployeeMapper.class);
			
			
			Map<Integer,Employee>mmm=mapper.getEmpByIdReurnEmp("j%");
			System.out.println(mmm);
			
//			
//			Map <String,Object> mm=mapper.getEmpByIdReurnMap(1);
//			System.out.println(mm);
			
//			List<Employee> e=mapper.getEmpsByLastNameLike("%e%");
//			System.out.println(e);
			
//			Map<String,Object> map=new HashMap<>();
//			map.put("id", 1);
//			map.put("lastname", "jerry");
//			Employee employee=mapper.getEmpByMap(map);
//			System.out.println(employee);
//			Employee e=new Employee(1,"jerry","jery@163.com","2");
//			mapper.addEmp(e);
//			mapper.updateEmp(e);
//			mapper.delete(e);
			//手动提交
//			opSession.commit();
		} finally {
			// TODO: handle finally clause
		}
	}
	@Test
	public void test02() throws IOException{
		String resource = "mybatis_config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession openSeesion=sqlSessionFactory.openSession();
		
		try {
			EmployeeMapperPlus mapper=openSeesion.getMapper(EmployeeMapperPlus.class);
			Employee empById=mapper.getEmpById(2);
			System.out.println(empById);
		} finally {
			openSeesion.close();
		}
	}
	@Test
	public void test03() throws IOException{
		String resource = "mybatis_config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession openSeesion=sqlSessionFactory.openSession();
		
		try {
			EmployeeMapperPlus mapper=openSeesion.getMapper(EmployeeMapperPlus.class);
			Employee e=mapper.getEmpAndDep(1);
			System.out.println(e);
//			System.out.println(e.getDep());
			
		} finally {
			openSeesion.close();
		}
	} 

	@Test
	public void test04() throws IOException{
		String resource = "mybatis_config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession openSeesion=sqlSessionFactory.openSession();
		
		try {
			EmployeeMapperPlus mapper=openSeesion.getMapper(EmployeeMapperPlus.class);
			Employee e=mapper.getEmpByIdStep(2);
			System.out.println(e);
			System.out.println(e.getDep());
			
		} finally {
			openSeesion.close();
		}
	} 
	@Test
	public void test06() throws IOException{
		String resource = "mybatis_config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession openSeesion=sqlSessionFactory.openSession();
		
		try {
			DepartmentMapper mapper=openSeesion.getMapper(DepartmentMapper.class);
			Department d=mapper.getDeptBtIdPlus(1);
			System.out.println(d);
			System.out.println(d.getEmps());

			
		} finally {
			openSeesion.close();
		}
	} 
}
