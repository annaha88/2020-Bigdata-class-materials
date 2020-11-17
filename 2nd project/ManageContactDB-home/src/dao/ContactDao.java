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
	 * 전체 연락처 확인
	 * @param : N/A
	 * @return :ArrayList<PersonVo>
	 */
	public ArrayList<PersonVo> searchAll(){
		ArrayList<PersonVo> contal = new ArrayList<PersonVo>();
//		전체 연락처를  검색하는 코드
//		1. Connection 생성
		Connection con = getConnection();		
//		2. PreparedStatement 생성 : 쿼리 전달 실행
		PreparedStatement pstmt = null;
		ResultSet rs			= null;
		
		StringBuilder sql = new StringBuilder();                       //Stringbuilder 는 java.lang package안에 있어서 import 불필요
		
		sql.append("  select * 		     ");
		sql.append("  from cont_all_view ");
//				          쿼리문 ""안에 ; 적으면 안된다.
		
		
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
	 * 새 연락처 추가
	 * @param : per
	 * @return :boolean
	 */
	public boolean insertCont(PersonVo per) {
		boolean isInsert = true;
//		데이터베이스에 데이터 추가 코드 => insert 문
//		1. Connection 생성
		Connection con = getConnection();
//		2. PreparedStatement 생성 : 쿼리 전달 실행
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
//		values('안나'
//					,'01039550179'
//					,'인천시'
//					, (select typecd
//		 					 from typecodes
//		 					where typenm = '회사'
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
//			executeUpdate() => Insert, Update, Delete 할때 이 메소드 써야함.
			int cnt = pstmt.executeUpdate();               //cnt 는 수행된 작업의 반영된 레코드 건수를 반환한다. 따라서 위에서는 1을 반환해야 정상반영이 된것이다.
			
//		3. boolean isInsert : 실행결과를 가지고 있는 놈
			if(cnt != 1) {         
				isInsert = false;
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
//		4. close() 처리
			close(con,pstmt);
		}
		return isInsert;
	}
	
	
	/**
	 * 이름으로 연락처 검색
	 * @param : String "검색할 이름"
	 * @return :ArrayList<SearchVo>
	 */
	public ArrayList<SearchVo> searchName(String name) {
		ArrayList<SearchVo> srchal = new ArrayList<SearchVo>();

//		1. Connection 생성
		Connection con = getConnection();
//		2. PreparedStatement 생성 : 쿼리 전달 실행
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
//			4. close() 처리
				close(con, pstmt,rs);
			}	
		return srchal;
	}
	
	
	/**
	 * 연락처 수정
	 * @param : PersonVo per, String "수정할 이름", String "수정할 번호"
	 * @return :boolean
	 */
	public boolean updateCont(PersonVo per, String name, String no) {
			boolean isUpdate = true;
//			데이터베이스에 사원을 수정하는 코드 => update 문
//			1. Connection 생성
			Connection con = getConnection();
//			2. PreparedStatement 생성 : 쿼리 전달 실행
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
				
//				3. boolean isUpdate : 실행결과를 가지고 있는 놈
				if(cnt != 1) {         
					isUpdate = false;
				   }
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
//				4. close() 처리
				close(con,pstmt);
			}
			return isUpdate;
		}

	
	/**
	 * 연락처 삭제
	 * @param :
	 * @return :boolean
	 */
	public boolean deleteCont(String name, String no) {
		boolean isDelete = true;
//		데이터베이스에서 사원을 삭제하는 코드 => 사원번호(PK)로 삭제 => delete 문
//		1. Connection 생성
		Connection con = getConnection();
//		2. PreparedStatement 생성 : 쿼리 전달 실행
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
//																	   and c.contnm = '안나'            --'안나' 자리에 삭제할 이름 입력
//																	 order by rownum
//																)s
//												where numb = '3'         -- 숫자 자리에 ? 로 입력받도록 한다. numb은 위의 결과 뷰의 rownum을 반환하는 컬럼.
//											)
//	 ;
//		
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt.setString(1, name);
			pstmt.setString(2, no);
			
			int cnt = pstmt.executeUpdate();
			
//		3. boolean isDelete : 실행결과를 가지고 있는 놈
			if(cnt != 1) {
				isDelete = false;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
//			4. close() 처리
			close(con,pstmt);
		}
		return isDelete;
	}
	
	
// 반복되는 작업, Connection & close 에 대한 메소드 생성
	/**
	 * Connection 생성
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
		} // connection 생성
		
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
		close(con, pstmt);   						  //반복되는 작업에 대해서는 위에 작성한 메소드를 호출한다.			
	}
		

}
