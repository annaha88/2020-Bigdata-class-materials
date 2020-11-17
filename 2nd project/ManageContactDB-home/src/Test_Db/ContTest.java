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
		
		Connection con 			= null;  // DB ����ó�� Ŭ����                    //Connection �� interface�̴�.
		PreparedStatement pstmt = null;  // ����� �����ϴ� Ŭ����                //PreparedStatement�� interface
		ResultSet rs 			= null;  // ����� �޴� �� => select �� 
		ArrayList<PersonVo> empal	= new ArrayList<PersonVo>();
		
		try {
//			Class.forName(driver);      //�Ʒ��� driver manager�� ���ֱ� ������ ���� ���⼭ ���ʿ�� ����.
//			�����ͺ��̽����� ��������� ���� => DAO layer
			con = DriverManager.getConnection(url,user,password);   // connection ����        //DriverManager�� getConnection() �޼ҵ��� ����Ÿ���� Connection �̴�.
			System.out.println("���� ����");

			
			StringBuilder sql = new StringBuilder();                       //Stringbuilder �� java.lang package�ȿ� �־ import ���ʿ�
			sql.append("select e.empno	 				"); // append �̿��ϸ� + ��ȣ ���ʿ��ϴ�. �ӵ��� ��������.
			sql.append("	, e.empnm	 				");
			sql.append("	, e.salary	 				");
			sql.append("	, d.deptno	 				");
			sql.append("	, d.deptnm	 				");
			
			
			sql.append("	from emp e 					");
			sql.append(", dept d						");
			sql.append(" where e.deptno = d.deptno(+)	"); 
					           // ""�ȿ� ; ������ �ȵȴ�.
			pstmt 	   = con.prepareStatement(sql.toString());            // append ���� ��� Stringbuilder�� toString �ɾ���� �Ѵ�. 
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
			
//			����� start
//			
			for(int i=0; i < empal.size(); i++) {
				System.out.println(empal.get(i));                  //EmpVo Ŭ������ toString �� �ɾ�����ϱ� println�� �ص� �׳� ���ڿ��� ��ȯ�ȴ�.
			}
//			��� end			
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("���� ����");
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

