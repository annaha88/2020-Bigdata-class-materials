package Test_Db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vo.PersonVo;

public class ContTest {

	public static void main(String[] args) {
		String url 		= "jdbc:oracle:thin:@localhost:1521:xe";
		String user 	= "ora_user";
		String password = "hong";
//		String driver 	= "oracle.jdbc.driver.OracleDriver";
		
		Connection con 			= null;  // DB 연결처리 클래스                    //Connection 은 interface이다.
		PreparedStatement pstmt = null;  // 명령을 전달하는 클래스                //PreparedStatement도 interface
		ResultSet rs 			= null;  // 결과를 받는 놈 => select 문 
		ArrayList<PersonVo> empal	= new ArrayList<PersonVo>();
		
		try {
//			Class.forName(driver);      //아래의 driver manager가 해주기 때문에 굳이 여기서 쓸필요는 없다.
//			데이터베이스에서 사원정보를 추출 => DAO layer
			con = DriverManager.getConnection(url,user,password);   // connection 생성        //DriverManager의 getConnection() 메소드의 리턴타입이 Connection 이다.
			System.out.println("접속 성공");

			
			StringBuilder sql = new StringBuilder();                       //Stringbuilder 는 java.lang package안에 있어서 import 불필요
			sql.append("select e.empno	 				"); // append 이용하면 + 기호 불필요하다. 속도도 빨라진다.
			sql.append("	, e.empnm	 				");
			sql.append("	, e.salary	 				");
			sql.append("	, d.deptno	 				");
			sql.append("	, d.deptnm	 				");
			
			
			sql.append("	from emp e 					");
			sql.append(", dept d						");
			sql.append(" where e.deptno = d.deptno(+)	"); 
					           // ""안에 ; 적으면 안된다.
			pstmt 	   = con.prepareStatement(sql.toString());            // append 쓰는 경우 Stringbuilder에 toString 걸어줘야 한다. 
			rs 		   = pstmt.executeQuery();
			
//			while(rs.next()) {
//				PersonVo emp = new PersonVo();
//				emp.setEmpno(rs.getString("empno"));
//				emp.setEmpnm(rs.getString("empnm"));
//				emp.setSalary(rs.getInt("salary"));
//				emp.setDeptno(rs.getString("deptno"));
//				emp.setDeptnm(rs.getString("deptnm"));
//				
//				empal.add(emp);
//			}
			
//			DAO Layer end
			
//			출력층 start
//			
			for(int i=0; i < empal.size(); i++) {
				System.out.println(empal.get(i));                  //EmpVo 클래스에 toString 을 걸어놨으니까 println만 해도 그냥 문자열로 반환된다.
			}
//			출력 end			
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("접속 에러");
		}
		finally {
			try {
				if(rs != null) {
				rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(pstmt != null) {
				pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(con != null) {
				con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
}

