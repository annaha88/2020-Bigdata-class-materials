package controller;

import java.util.Scanner;

import service.ContService;


public class ContController {
	// controller �� ����Ǵ� ���̴�.
	public static void main(String[] args) {
		ContService contsrv = new ContService();
		
		
		Scanner scan = new Scanner(System.in);
		String n = null;
		
		boolean a = true;
		while(a) { 				
			contsrv.menu();
			n = scan.next();											//����ġ ���� ���� �Ķ���� n�� String Ÿ������ �Է¹޴´�. 
			
			switch(n) {							
			case "1" : contsrv.insertCont();								// �� ����ó �Է� ����
				break;
			case "2" : contsrv.searchAll();									// ��ü ����ó ��� ����
				break;
			case "3" : contsrv.updateCont();
				break;
			case "4" : contsrv.deleteCont();
				break;
			case "5" : 
				System.out.println("����Ǿ����ϴ�.");
				a = false;
				break;
			default :
				System.out.println("�߸��� �Է��Դϴ�. �޴��� ���ڸ� �Է����ּ���.");
			}
			
		}
		scan.close();
	}
}	

