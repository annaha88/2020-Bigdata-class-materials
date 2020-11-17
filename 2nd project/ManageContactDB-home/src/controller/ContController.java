package controller;

import java.util.Scanner;

import service.ContService;


public class ContController {
	// controller 는 실행되는 놈이다.
	public static void main(String[] args) {
		ContService contsrv = new ContService();
		
		
		Scanner scan = new Scanner(System.in);
		String n = null;
		
		boolean a = true;
		while(a) { 				
			contsrv.menu();
			n = scan.next();											//스위치 문에 사용될 파라미터 n을 String 타입으로 입력받는다. 
			
			switch(n) {							
			case "1" : contsrv.insertCont();								// 새 연락처 입력 실행
				break;
			case "2" : contsrv.searchAll();									// 전체 연락처 출력 실행
				break;
			case "3" : contsrv.updateCont();
				break;
			case "4" : contsrv.deleteCont();
				break;
			case "5" : 
				System.out.println("종료되었습니다.");
				a = false;
				break;
			default :
				System.out.println("잘못된 입력입니다. 메뉴의 숫자만 입력해주세요.");
			}
			
		}
		scan.close();
	}
}	

