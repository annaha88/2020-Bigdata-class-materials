package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import vo.PersonVo;
import vo.SearchVo;


public class ContactDao {
	
	/**
	 * ��ü ����ó Ȯ��
	 * @param : N/A
	 * @return :ArrayList<PersonVo>
	 */
	public ArrayList<PersonVo> searchAll(){
		ArrayList<PersonVo> contal = new ArrayList<PersonVo>();

//		1. Connection ����
		Connection con = getConnection();		
//		2. PreparedStatement ���� : ���� ���� ����
		PreparedStatement pstmt = null;
		ResultSet rs			= null;
		
		StringBuilder sql = new StringBuilder(); 
//		����� ��ü ����ó�� ����ϴ� ������
		sql.append("  select * 		     ");
		sql.append("  from cont_all_view ");		
		
		try {
			pstmt 	   = con.prepareStatement(sql.toString());					//	������ SQL�� ����
			rs 		   = pstmt.executeQuery();									//	���޵� ������ �����ϰ� rs �� �������� �޴´�.

//			SQL ���������� ������ �ִ� ��� row ������ ��ȯ�ϵ��� while ���ǹ��� ����Ѵ�. rs.getString�� ����ϰ� ���� �÷����� �Ķ���ͷ� �Է��Ѵ�.
			while(rs.next()) {	
				PersonVo per = new PersonVo();
				per.setContnm(rs.getString("contnm"));
				per.setConttelno(rs.getString("telnum"));
				per.setContadr(rs.getString("contadr"));
				per.setTypenm(rs.getString("typenm"));   
//				��µǴ� ��� ��ü ������ ArrayList�� contal �� �߰��Ѵ�.
				contal.add(per);
			}
		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println("����� �������µ� �����Ͽ����ϴ�.");
//			3. close : SQL���� ������ �����Ѵ�.
		}finally {
			close(con, pstmt, rs);
		}
//		����Ÿ���� Arraylist �� contal �̴�. �� Arraylist �� ���� Ŭ�������� ��� ��µǵ��� �� ���̴�.
		return contal;
	}


	/**
	 * �� ����ó �߰�
	 * @param : per
	 * @return :boolean
	 */
	public boolean insertCont(PersonVo per) {
		boolean isInsert = true;
//		�����ͺ��̽��� ������ �߰� �ڵ� => insert ��
//		1. Connection ����
		Connection con = getConnection();
//		2. PreparedStatement ���� : ���� ���� ����
		PreparedStatement pstmt = null;

//		����ó�� �߰��ϱ� ���� ������ sql�� �����Ѵ�.
		StringBuilder sql = new StringBuilder();
		sql.append("insert into contacts 			");
		sql.append("values(   ? 					");
		sql.append("		, ?						");
		sql.append("		, ?						");
		sql.append("		, (						");
		sql.append("			select typecd		");
		sql.append("			  from typecodes	");
		sql.append("			 where typenm =   ?	");
		sql.append("		  )						");
		sql.append("	   )						");
				
		
//		����ڿ� ���� �Էµ� ������ �� �������� ������� ���� �ȴ�. 
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, per.getContnm());
			pstmt.setString(2, per.getConttelno());
			pstmt.setString(3, per.getContadr());
			pstmt.setString(4, per.getTypenm());
//			Insert�� ����  executeUpdate() �����Ͽ� SQL���� DB �߰� �۾� ����ǵ��� �Ѵ�.
			int cnt = pstmt.executeUpdate();               //cnt �� ����� �۾��� �ݿ��� ���ڵ� �Ǽ��� ��ȯ�Ѵ�. ���� �������� 1�� ��ȯ�ؾ� ����ݿ��� �Ȱ��̴�.
			
//		3. boolean isInsert : �������� ������ �ִ� ��
			if(cnt != 1) {         					// �����۾��� ����� �ݿ����� �ʾҴٸ�, �� �޼ҵ�� false ��ȯ
				isInsert = false;
			}	
		} catch (SQLException e) {					// �Էµ� ������ ������ ���ܰ� �ִٸ� SQLException���� �޴´�.
//			e.printStackTrace();
			System.out.println("�߸��� �Է��Դϴ�. �޴��� ��ȣ�� Ȯ���Ͻðų�, ������ ��Ȯ�� ���� �Է��Ͻʽÿ�.");
			isInsert = false;
		}finally {
//		4. close() ó��
			close(con,pstmt);
		}
		return isInsert;
	}
	
	
	/**
	 * �̸����� ����ó �˻�
	 * @param : String "�˻��� �̸�"
	 * @return :ArrayList<SearchVo>
	 */
	public ArrayList<SearchVo> searchName(String name) {
		ArrayList<SearchVo> srchal = new ArrayList<SearchVo>();

//		1. Connection ����
		Connection con = getConnection();
//		2. PreparedStatement ���� : ���� ���� ����
		PreparedStatement pstmt = null;
		ResultSet rs			= null;

		StringBuilder sql = new StringBuilder();
		
//		�˻������ ����� select ���� DB �� �����Ѵ�.
		sql.append(" 		select rownum as numb, c.contnm, lpad(c.conttelno,11,'0') as telnum, c.contadr, t.typenm	");
		sql.append("   		  from contacts c																			");
		sql.append("		   , typecodes t																			");
		sql.append("		 where c.typecd = t.typecd 																	");
		sql.append("		   and c.contnm = ?	    																	");
		sql.append("  		order by rownum 																			");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1,name);

			rs 		   = pstmt.executeQuery();
			
			while(rs.next()) {
				SearchVo srch = new SearchVo();
				srch.setNumb(rs.getInt("numb"));
				srch.setContnm(rs.getString("contnm"));
				srch.setConttelno(rs.getString("telnum"));
				srch.setContadr(rs.getString("contadr"));
				srch.setTypenm(rs.getString("typenm"));
				
				srchal.add(srch);
			}
			 
		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println("�˻��� �����Ͽ����ϴ�.");
		}finally {
//			4. close() ó��
				close(con, pstmt,rs);
			}	
		return srchal;
	}
	
	
	/**
	 * ����ó ����
	 * @param : PersonVo per, String "������ �̸�", String "������ ��ȣ" 
	 * @return :boolean
	 */
	public boolean updateCont(PersonVo per, String name, String no) {
			boolean isUpdate = true;
//			�����ͺ��̽��� ����� �����ϴ� �ڵ� => update ��
//			1. Connection ����
			Connection con = getConnection();
//			2. PreparedStatement ���� : ���� ���� ����
			PreparedStatement pstmt = null;
			
//			������  ������ ������ �Ʒ��� ����. ���� ������ SQL ������ ����
			StringBuilder sql = new StringBuilder();
			sql.append(" update contacts																					");
			sql.append(" 	set conttelno = ?																				");
			sql.append(" 	  ,	contnm 	  = ?																				");
			sql.append(" 	  , contadr	  = ?																				");
			sql.append(" 	  , typecd 	  = (																				");
			sql.append(" 					  select typecd																	");
			sql.append(" 						from typecodes																");
			sql.append("					   where typenm =             ?													");
			sql.append("					)																				");
			sql.append("  where conttelno in (																				");
			sql.append("					   select s.conttelno															");
			sql.append("					  	 from ( 																	");
			sql.append("								select rownum as numb, c.contnm, c.conttelno, c.contadr, t.typenm	");
			sql.append("							  	  from contacts c     												");
			sql.append("							  	     , typecodes t     												");
			sql.append("								 where c.typecd = t.typecd     										");
			sql.append("								   and c.contnm =           ?  										");
			sql.append("								 order by rownum  													");
			sql.append("							)s  																	");
			sql.append("									 where numb =           ?  										");
			sql.append("					 )							  													");
			
			
			try {
				pstmt = con.prepareStatement(sql.toString());
//				�� ������ ? �׸� �Էµ� ����� �Է� ����
				pstmt.setString(1, per.getConttelno());
				pstmt.setString(2, per.getContnm());
				pstmt.setString(3, per.getContadr());
				pstmt.setString(4, per.getTypenm());
				pstmt.setString(5, name);			// ���� Ŭ������ ���ؼ� ����ڰ� �˻��� �̸��� �Ķ���ͷ� ����, ���⿡ name ���� ���鼭 ���������� ���޵ȴ�.
				pstmt.setString(6, no);				// ����ڰ� ������ ��ȣ�� ���� ���� ������ ���޵ȴ�. 
				
				int cnt = pstmt.executeUpdate();
				
//				3. boolean isUpdate : �������� ������ �ִ� ��
				if(cnt != 1) {         
					isUpdate = false;
				   }
			} catch (SQLException e) {
//				e.printStackTrace();
				System.out.println("�߸��� �Է��Դϴ�. �޴��� ��ȣ�� Ȯ���Ͻðų�, ������ ��Ȯ�� ���� �Է��Ͻʽÿ�.");
				isUpdate = false;
			}finally {
//				4. close() ó��
				close(con,pstmt);
			}
			return isUpdate;
		}

	
	/**
	 * ����ó ����
	 * @param : String "������ �̸�", String "������ �ѹ�"
	 * @return :boolean
	 */
	public boolean deleteCont(String name, String no) {
		boolean isDelete = true;
//		�����ͺ��̽����� ����� �����ϴ� �ڵ� => �����ȣ(PK)�� ���� => delete ��
//		1. Connection ����
		Connection con = getConnection();
//		2. PreparedStatement ���� : ���� ���� ����
		PreparedStatement pstmt = null;
//		������ ������ ������ �Ʒ��� ����. ���� ������ SQL���� ����
		StringBuilder sql = new StringBuilder();
		sql.append(" delete                      																		");
		sql.append("  from contacts                       												 				");
		sql.append(" where conttelno in (                        												 		");
		sql.append("                     select s.conttelno    												 			");
		sql.append("                       from  (  														 			");
		sql.append("                              select rownum as numb, c.contnm, c.conttelno, c.contadr, t.typenm 	");
		sql.append("                       			from contacts c			   											");
		sql.append("                        		   , typecodes t				  									");
		sql.append("                         		 where c.typecd = t.typecd			 								");
		sql.append("                         		   and c.contnm = 				? 	 								");
		sql.append("                         		 order by rownum		 											");
		sql.append("                         	 )s 		 															");
		sql.append("                      where numb = 	?	  					  										");
		sql.append("                    )    		 		 															");
		

		
		try {
			pstmt = con.prepareStatement(sql.toString());
//			����ڰ� �˻��� name, ����ڰ� ���� ������ ���� no, 2���� ������ ���޵Ǿ� ����� ������ �ϼ��ȴ�.
			pstmt.setString(1, name);
			pstmt.setString(2, no);
			
			int cnt = pstmt.executeUpdate();
			
//		3. boolean isDelete : �������� ������ �ִ� ��
			if(cnt != 1) {
				isDelete = false;
			}
			
			
		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println("�߸��� �Է��Դϴ�.");
			isDelete = false;
		}finally {
//			4. close() ó��
			close(con,pstmt);
		}
		return isDelete;
	}
	
	
// �ݺ��Ǵ� �۾�, Connection & close �� ���� �޼ҵ� ����
	/**
	 * Connection ����
	 * @param :N/A
	 * @return :Connection
	 */
	private Connection getConnection() {
		Connection con = null;
		String url 		= "jdbc:oracle:thin:@localhost:1521:xe";
		String user 	= "ora_user";
		String password = "hong";
//		String driver 	= "oracle.jdbc.driver.OracleDriver";
		
		try {
			con = DriverManager.getConnection(url,user,password);
		} catch (SQLException e) {
			e.printStackTrace();
		} // connection ����
		
		return con;
	}
	

	/**
	 * Connection, PreparedStatement close()
	 * @param con: Connection
	 * @param pstmt : PreparedStatement
	 * @return :void
	 */
	private void close(Connection con
			, PreparedStatement pstmt) {
//  PreparedStatement close()
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
//	Connection close()
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Connection, PreparedStatement, ResultSet close()
	 * @param con : Connection
	 * @param pstmt : PreparedStatement
	 * @param rs
	 * @return type : void
	 */	
	private void close(Connection con
			, PreparedStatement pstmt
			, ResultSet rs) {
//	ResultSet close()
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
//  PreparedStatement close()
//		Connection close()
		close(con, pstmt);   						  	
	}
}
