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

//		1. Connection 생성
		Connection con = getConnection();		
//		2. PreparedStatement 생성 : 쿼리 전달 실행
		PreparedStatement pstmt = null;
		ResultSet rs			= null;
		
		StringBuilder sql = new StringBuilder(); 
//		저장된 전체 연락처를 출력하는 쿼리문
		sql.append("  select * 		     ");
		sql.append("  from cont_all_view ");		
		
		try {
			pstmt 	   = con.prepareStatement(sql.toString());					//	쿼리를 SQL로 전달
			rs 		   = pstmt.executeQuery();									//	전달된 쿼리를 실행하고 rs 로 실행결과를 받는다.

//			SQL 실행결과에서 가지고 있는 모든 row 내용을 반환하도록 while 조건문을 사용한다. rs.getString를 출력하고 싶은 컬럼명을 파라미터로 입력한다.
			while(rs.next()) {	
				PersonVo per = new PersonVo();
				per.setContnm(rs.getString("contnm"));
				per.setConttelno(rs.getString("telnum"));
				per.setContadr(rs.getString("contadr"));
				per.setTypenm(rs.getString("typenm"));   
//				출력되는 모든 객체 정보를 ArrayList인 contal 에 추가한다.
				contal.add(per);
			}
		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println("목록을 가져오는데 실패하였습니다.");
//			3. close : SQL과의 연결을 종료한다.
		}finally {
			close(con, pstmt, rs);
		}
//		리턴타입은 Arraylist 인 contal 이다. 이 Arraylist 를 서비스 클래스에서 모두 출력되도록 할 것이다.
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

//		연락처를 추가하기 위한 쿼리를 sql에 전달한다.
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
				
		
//		사용자에 의해 입력된 정보가 위 쿼리문에 순서대로 들어가게 된다. 
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, per.getContnm());
			pstmt.setString(2, per.getConttelno());
			pstmt.setString(3, per.getContadr());
			pstmt.setString(4, per.getTypenm());
//			Insert를 위한  executeUpdate() 실행하여 SQL에서 DB 추가 작업 실행되도록 한다.
			int cnt = pstmt.executeUpdate();               //cnt 는 수행된 작업의 반영된 레코드 건수를 반환한다. 따라서 위에서는 1을 반환해야 정상반영이 된것이다.
			
//		3. boolean isInsert : 실행결과를 가지고 있는 놈
			if(cnt != 1) {         					// 실행작업이 제대로 반영되지 않았다면, 본 메소드는 false 반환
				isInsert = false;
			}	
		} catch (SQLException e) {					// 입력된 쿼리에 오류나 예외가 있다면 SQLException으로 받는다.
//			e.printStackTrace();
			System.out.println("잘못된 입력입니다. 휴대폰 번호를 확인하시거나, 종류에 정확한 값을 입력하십시오.");
			isInsert = false;
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
		
//		검색결과를 출력할 select 문을 DB 로 전달한다.
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
			System.out.println("검색에 실패하였습니다.");
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
			
//			수정을  진행할 쿼리는 아래와 같다. 쿼리 설명은 SQL 파일을 참조
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
//				위 쿼리의 ? 항목에 입력될 사용자 입력 정보
				pstmt.setString(1, per.getConttelno());
				pstmt.setString(2, per.getContnm());
				pstmt.setString(3, per.getContadr());
				pstmt.setString(4, per.getTypenm());
				pstmt.setString(5, name);			// 서비스 클래스를 통해서 사용자가 검색한 이름이 파라미터로 들어가고, 여기에 name 으로 들어가면서 쿼리문으로 전달된다.
				pstmt.setString(6, no);				// 사용자가 선택한 번호도 위와 같은 원리로 전달된다. 
				
				int cnt = pstmt.executeUpdate();
				
//				3. boolean isUpdate : 실행결과를 가지고 있는 놈
				if(cnt != 1) {         
					isUpdate = false;
				   }
			} catch (SQLException e) {
//				e.printStackTrace();
				System.out.println("잘못된 입력입니다. 휴대폰 번호를 확인하시거나, 종류에 정확한 값을 입력하십시오.");
				isUpdate = false;
			}finally {
//				4. close() 처리
				close(con,pstmt);
			}
			return isUpdate;
		}

	
	/**
	 * 연락처 삭제
	 * @param : String "삭제할 이름", String "삭제할 넘버"
	 * @return :boolean
	 */
	public boolean deleteCont(String name, String no) {
		boolean isDelete = true;
//		데이터베이스에서 사원을 삭제하는 코드 => 사원번호(PK)로 삭제 => delete 문
//		1. Connection 생성
		Connection con = getConnection();
//		2. PreparedStatement 생성 : 쿼리 전달 실행
		PreparedStatement pstmt = null;
//		삭제를 진행할 쿼리는 아래와 같다. 쿼리 설명은 SQL파일 참조
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
//			사용자가 검색한 name, 사용자가 삭제 선택한 순번 no, 2개의 정보가 전달되어 실행될 쿼리가 완성된다.
			pstmt.setString(1, name);
			pstmt.setString(2, no);
			
			int cnt = pstmt.executeUpdate();
			
//		3. boolean isDelete : 실행결과를 가지고 있는 놈
			if(cnt != 1) {
				isDelete = false;
			}
			
			
		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println("잘못된 입력입니다.");
			isDelete = false;
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
		close(con, pstmt);   						  	
	}
}
