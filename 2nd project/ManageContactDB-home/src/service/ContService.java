package service;

import java.util.ArrayList;
import java.util.Scanner;

import dao.ContactDao;
import vo.PersonVo;
import vo.SearchVo;


public class ContService {
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
//		Scanner scan = new Scanner(System.in);
		ContactDao contDao = new ContactDao();
		PersonVo per = new PersonVo();
		

		System.out.println("등록할 회원의 정보를 입력하세요.(모든 입력은 공백없이 입력하시기 바랍니다.)");	 
		System.out.print("이름: ");
		per.setContnm(scan.next());
		System.out.print("전화번호(ex:01012334456): " );	
		per.setConttelno(scan.next());
		System.out.print("주소 : ");
		per.setContadr(scan.next());
		System.out.print("종류(ex. 가족, 친구, 회사, 기타) : ");
		per.setTypenm(scan.next());
			
		contDao.insertCont(per);						//사용자로부터 입력받은 정보를 DB에 입력시킨다.
		System.out.println("연락처가 추가 되었습니다");
		
//		scan.close();

	}
	
	/**
	 * 검색된 연락처 출력 후 수정
	 * @param : 없음
	 * @return :void
	 */
	public void updateCont() {
		ContactDao contDao = new ContactDao();
		System.out.println("수정하고자 하는 이름을 입력하세요.");
		System.out.print("이름:");
//		Scanner scan = new Scanner(System.in);
		String name = scan.next();
		
		ArrayList<SearchVo> srchal = contDao.searchName(name);
		System.out.println();
		System.out.println("총 " + srchal.size() + "명이 검색되었습니다.");
		for(int i =0; i < srchal.size(); i++) {
			System.out.println(srchal.get(i));
			}
		System.out.println();
		System.out.println("위의 검색 결과에서 수정하고자 하는 번호를 입력해주세요.");
		String no = scan.next();
		
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
		
//		System.out.println("입력성공");
//		System.out.println(per.toString());
		contDao.updateCont(per,name,no);
		System.out.println("연락처가 수정 되었습니다");
		
//		scan.close();
		
		
		}
	
	/**
	 * 검색된 연락처 출력 후 삭제
	 * @param : 없음
	 * @return :void
	 */
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
		System.out.println();
		System.out.println("위의 검색 결과에서 삭제하고자 하는 번호를 입력해주세요.");
		String no = scan.next();
		
		contDao.deleteCont(name, no);
		System.out.println("연락처가 삭제 되었습니다");
		
	}
 }

