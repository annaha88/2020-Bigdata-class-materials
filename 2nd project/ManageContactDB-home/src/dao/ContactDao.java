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
//		��ü ����ó��  �˻��ϴ� �ڵ�
//		1. Connection ����
		Connection con = getConnection();		
//		2. PreparedStatement ���� : ���� ���� ����
		PreparedStatement pstmt = null;
		ResultSet rs			= null;
		
		StringBuilder sql = new StringBuilder();                       //Stringbuilder �� java.lang package�ȿ� �־ import ���ʿ�
		
		sql.append("  select * 		     ");
		sql.append("  from cont_all_view ");
//				          ������ ""�ȿ� ; ������ �ȵȴ�.
		
		
		try {
			pstmt 	   = con.prepareStatement(sql.toString());
			rs 		   = pstmt.executeQuery();
			
			while(rs.next()) {
				PersonVo per = new PersonVo();
				per.setContnm(rs.getString("contnm"));
				per.setConttelno(rs.getString("conttelno"));
				per.setContadr(rs.getString("contadr"));
				per.setTypenm(rs.getString("typenm"));
				
				contal.add(per);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(con, pstmt, rs);
		}
	
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
		
		
//		insert into contacts
//		values('�ȳ�'
//					,'01039550179'
//					,'��õ��'
//					, (select typecd
//		 					 from typecodes
//		 					where typenm = 'ȸ��'
//		 			  )
//		);
//		
//		
//		
		
		
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, per.getContnm());
			pstmt.setString(2, per.getConttelno());
			pstmt.setString(3, per.getContadr());
			pstmt.setString(4, per.getTypenm());
//			executeUpdate() => Insert, Update, Delete �Ҷ� �� �޼ҵ� �����.
			int cnt = pstmt.executeUpdate();               //cnt �� ����� �۾��� �ݿ��� ���ڵ� �Ǽ��� ��ȯ�Ѵ�. ���� �������� 1�� ��ȯ�ؾ� ����ݿ��� �Ȱ��̴�.
			
//		3. boolean isInsert : �������� ������ �ִ� ��
			if(cnt != 1) {         
				isInsert = false;
			}	
		} catch (SQLException e) {
			e.printStackTrace();
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
//		sql.append(" create or replace view search_view as										");
		sql.append(" 		select rownum as numb, c.contnm, c.conttelno, c.contadr, t.typenm	");
		sql.append("   		  from contacts c													");
		sql.append("		   , typecodes t													");
		sql.append("		 where c.typecd = t.typecd 											");
		sql.append("		   and c.contnm = ?	    											");
		sql.append("  		order by rownum 													");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1,name);
//			int cnt = pstmt.executeUpdate();
		
			
//			sql.append(" select * 			");
//			sql.append("   from search_view ");
			rs 		   = pstmt.executeQuery();
			
			while(rs.next()) {
				SearchVo srch = new SearchVo();
				srch.setNumb(rs.getInt("numb"));
				srch.setContnm(rs.getString("contnm"));
				srch.setConttelno(rs.getString("conttelno"));
				srch.setContadr(rs.getString("contadr"));
				srch.setTypenm(rs.getString("typenm"));
				
				srchal.add(srch);
			}
			 
		} catch (SQLException e) {
			e.printStackTrace();
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
				
				pstmt.setString(1, per.getConttelno());
				pstmt.setString(2, per.getContnm());
				pstmt.setString(3, per.getContadr());
				pstmt.setString(4, per.getTypenm());
				pstmt.setString(5, name);
				pstmt.setString(6, no);
				
				int cnt = pstmt.executeUpdate();
				
//				3. boolean isUpdate : �������� ������ �ִ� ��
				if(cnt != 1) {         
					isUpdate = false;
				   }
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
//				4. close() ó��
				close(con,pstmt);
			}
			return isUpdate;
		}

	
	/**
	 * ����ó ����
	 * @param :
	 * @return :boolean
	 */
	public boolean deleteCont(String name, String no) {
		boolean isDelete = true;
//		�����ͺ��̽����� ����� �����ϴ� �ڵ� => �����ȣ(PK)�� ���� => delete ��
//		1. Connection ����
		Connection con = getConnection();
//		2. PreparedStatement ���� : ���� ���� ����
		PreparedStatement pstmt = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append(" delete                      																	");
		sql.append("  from contacts c                       												 		");
		sql.append(" where conttelno in (                        												 	");
		sql.append("                     select s.conttelno    												 		");
		sql.append("                       from  (  														 		");
		sql.append("                              select rownum as numb, c.contnm, c.conttelno, c.contadr, t.typenm ");
		sql.append("                       			from contacts c			   										");
		sql.append("                        		   , typecodes t				  								");
		sql.append("                         		 where c.typecd = t.typecd			 							");
		sql.append("                         		   and c.contnm = 				? 	 							");
		sql.append("                         		 order by rownum		 										");
		sql.append("                         	 )s 		 														");
		sql.append("                      where numb = 								?	  		  					");
		sql.append("                    )    		 		 														");
		
		
//		delete
//	 	from contacts c
//	 where conttelno in (
//												select s.conttelno
//													from  (
//																	select rownum as numb, c.contnm, c.conttelno, c.contadr, t.typenm
//																		from contacts c
//																			 , typecodes t
//																	 where c.typecd = t.typecd
//																	   and c.contnm = '�ȳ�'            --'�ȳ�' �ڸ��� ������ �̸� �Է�
//																	 order by rownum
//																)s
//												where numb = '3'         -- ���� �ڸ��� ? �� �Է¹޵��� �Ѵ�. numb�� ���� ��� ���� rownum�� ��ȯ�ϴ� �÷�.
//											)
//	 ;
//		
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt.setString(1, name);
			pstmt.setString(2, no);
			
			int cnt = pstmt.executeUpdate();
			
//		3. boolean isDelete : �������� ������ �ִ� ��
			if(cnt != 1) {
				isDelete = false;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
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
		close(con, pstmt);   						  //�ݺ��Ǵ� �۾��� ���ؼ��� ���� �ۼ��� �޼ҵ带 ȣ���Ѵ�.			
	}
		

}
