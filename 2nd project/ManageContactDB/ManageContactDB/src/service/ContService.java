package service;

import java.util.ArrayList;
import java.util.Scanner;

import dao.ContactDao;
import vo.PersonVo;
import vo.SearchVo;


public class ContService {
//	사용자에 의해 정보가 입력될 수 있도록 Scanner를 열어준다.
	Scanner scan = new Scanner(System.in);
	
	
	/**
	 * 메인 메뉴 출력층
	 * @param : 없음
	 * @return :void
	 */
	public void menu() {
		System.out.println();
		System.out.println("========================");
		System.out.println(" 다음 메뉴 중 하나를 선택하세요.");
		System.out.println("========================");
		System.out.println("1. 회원 추가");
		System.out.println("2. 회원 목록 보기");
		System.out.println("3. 회원 정보 수정하기");
		System.out.println("4. 회원 삭제");
		System.out.println("5. 종료");		
	}

	/**
	 * 전체 연락처 출력층
	 * @param : 없음
	 * @return :void
	 */ 
//	Dao 객체를 new하여서 실행이 가능하도록 한 후, searchAll()에서 반환되는 ArrayList 를 모두 출력하도록 for문을 사용한다.
	public void searchAll() {
		ContactDao contDao = new ContactDao();
		ArrayList<PersonVo> contal = contDao.searchAll();
		System.out.println("총 " + contal.size() + "개의 연락처가 저장되어 있습니다.");
		for(int i =0 ; i< contal.size(); i++) {
			System.out.println(contal.get(i));             //PersonVo 에 toString 해두어서 get(index) 메소드로 해당 정보를 문자열로 불러올수 있다. (toString 메서드 따로 써줄 필요없음)
		}
	}
	
	/**
	 * 새 연락처 입력층
	 * @param :
	 * @return :void
	 */
	public void insertCont() {
//		Dao 객체 생성, 입력될 연락처 정보가 저장될 PersonVo 객체 생성
		ContactDao contDao = new ContactDao();
		PersonVo per = new PersonVo();
//		추가할 연락처 정보 입력
		System.out.println("등록할 회원의 정보를 입력하세요.(모든 입력은 공백없이 입력하시기 바랍니다.)");	 
		System.out.print("이름: ");
		per.setContnm(scan.next());
		System.out.print("전화번호(ex:01012334456): " );	
		per.setConttelno(scan.next());
		System.out.print("주소 : ");
		per.setContadr(scan.next());
		System.out.print("종류(ex. 가족, 친구, 회사, 기타) : ");
		per.setTypenm(scan.next());
//		Dao 를 통해 입력받은 정보를 DB에 추가한다.
		boolean isInsert = contDao.insertCont(per);						
		if (isInsert) {
		System.out.println("연락처가 추가 되었습니다");
		}
		else {
			System.out.println("메인 메뉴로 돌아갑니다.");				
		}
		

	}
	
	/**
	 * 검색된 연락처 출력 후 수정
	 * @param : 없음
	 * @return :void
	 */
//	DAO에서 사용될 파라미터인 
//	1.PersonVo per : 한사람의 수정정보가 담긴 객체, 2.String name: 수정할 때 검색한 이름, 3.String no : 검색결과로부터 선택한 번호 를 사용자로부터 입력받는다.

	public void updateCont() {
		ContactDao contDao = new ContactDao();
		System.out.println("수정하고자 하는 이름을 입력하세요.");
		System.out.print("이름:");
		String name = scan.next();
//		수정하고자 하는 이름 name 을 입력하면, Dao의 searchName(name) 메소드에 의해 ArrayList<SearchVo>를 얻고, 
//		for문을 돌려 결과를 모두 출력한다.
		ArrayList<SearchVo> srchal = contDao.searchName(name);
		System.out.println();
		System.out.println("총 " + srchal.size() + "명이 검색되었습니다.");
		for(int i =0; i < srchal.size(); i++) {
			System.out.println(srchal.get(i));
			}
//		연락처 목록에 없는 이름을 입력한다면, 검색결과는 0 명이 될 것 이다. if 조건문을 걸어, 0이 아닌 경우에만 수정작업이 진행되도록 한다.
		if(srchal.size() != 0) {
		System.out.println();
		System.out.println("위의 검색 결과에서 수정하고자 하는 번호를 입력해주세요.");
		
		try {
		String no = scan.next();
		
		//		여기서 검색 목록 번호외 입력했을 때 돌아가게 하는 예외처리를 해준다. 1.우선 String으로 받은 번호 no를 숫자로 형변환한다.
		int n = Integer.parseInt(no);
		
		if(n >srchal.size()  ^  n < 1) {					  //  2. 검색 목록에 해당하는 번호만 수정할 수 있도록, if조건문을 걸어주었다.
			System.out.println("검색 목록의 번호만 입력하세요");  
			
		}
//		사용자가 정상적으로 번호를 선택한다면, 수정작업이 진행된다.
		else {
			PersonVo per = new PersonVo();
			System.out.println("수정할 회원의 정보를 입력하세요.(모든 입력은 공백없이 입력하시기 바랍니다.)");	 
			System.out.print("이름: ");
			per.setContnm(scan.next());
			System.out.print("전화번호(ex:01012334456): " );	
			per.setConttelno(scan.next());
			System.out.print("주소 : ");
			per.setContadr(scan.next());
			System.out.print("종류(ex. 가족, 친구, 회사, 기타) : ");
			per.setTypenm(scan.next());
//			Dao 의 updateCont(per,name,no)를 통해 DB의 연락처가 수정된다. 
			boolean isUpdate = contDao.updateCont(per,name,no);
			if(isUpdate) { 
			System.out.println("연락처가 수정 되었습니다");
				}
			else {
			System.out.println("메인 메뉴로 돌아갑니다.");    
				}
			}
		}catch(NumberFormatException e) {								//검색 시 문자가 입력되면, NumberFormatException 처리된다.
		System.out.println("검색 목록의 번호만 입력하세요. 메인 메뉴로 돌아갑니다.");				
			}
		}
		else {
			System.out.println("해당 이름이 존재하지 않습니다.");
		}
		
	}
	
	/**
	 * 검색된 연락처 출력 후 삭제
	 * @param : 없음
	 * @return :void
	 */
//	삭제파트는 수정파트와 거의 동일한다. 
//	새로운 객체를 입력할 필요가 없기 때문에 personVo를 제외하고, 
//	검색할 이름 name과, 검색결과로부터 선택한 번호 no. 두가지의 파라미터를 사용자로부터 입력받는다.

	public void deleteCont() {
		ContactDao contDao = new ContactDao();
		System.out.println("삭제하고자 하는 이름을 입력하세요.");
		System.out.print("이름:");
		String name = scan.next();
		
		ArrayList<SearchVo> srchal = contDao.searchName(name);
		System.out.println();
		System.out.println("총 " + srchal.size() + "명이 검색되었습니다.");
		for(int i =0; i < srchal.size(); i++) {
			System.out.println(srchal.get(i));
			}
		
//		수정 파트와 동일하게  if 조건문을 걸어, 검색결과가 존재하는 경우에만 삭제가 진행되도록 한다.
		if(srchal.size() != 0) {
		System.out.println();
		System.out.println("위의 검색 결과에서 삭제하고자 하는 번호를 입력해주세요.");
		
			try {
				String no = scan.next();
		//		여기서 검색 목록 번호외 입력했을 때 돌아가게 하는 예외처리 
				int n = Integer.parseInt(no);
				if(n >srchal.size() ^ n<1) {
					System.out.println("검색 목록의 번호만 입력하세요. 메인 메뉴로 돌아갑니다.");	
					}
				
				boolean isDelete = contDao.deleteCont(name,no);
				if(isDelete) {
					System.out.println("연락처가 삭제 되었습니다");
				}
				else {
					System.out.println("메인 메뉴로 돌아갑니다.");    
				}
			}catch(NumberFormatException e) {
				System.out.println("검색 목록의 번호만 입력하세요. 메인 메뉴로 돌아갑니다.");
			}
		}else {
			System.out.println("해당 이름이 존재하지 않습니다.");
		}
	}
	
 }

