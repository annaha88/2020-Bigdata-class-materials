package service;

import java.util.ArrayList;
import java.util.Scanner;

import dao.ContactDao;
import vo.PersonVo;
import vo.SearchVo;


public class ContService {
	Scanner scan = new Scanner(System.in);
	
	
	/**
	 * ���� �޴� �����
	 * @param : ����
	 * @return :void
	 */
	public void menu() {
		System.out.println();
		System.out.println("========================");
		System.out.println(" ���� �޴� �� �ϳ��� �����ϼ���.");
		System.out.println("========================");
		System.out.println("1. ȸ�� �߰�");
		System.out.println("2. ȸ�� ��� ����");
		System.out.println("3. ȸ�� ���� �����ϱ�");
		System.out.println("4. ȸ�� ����");
		System.out.println("5. ����");		
	}

	/**
	 * ��ü ����ó �����
	 * @param : ����
	 * @return :void
	 */
	public void searchAll() {
		ContactDao contDao = new ContactDao();
		ArrayList<PersonVo> contal = contDao.searchAll();
		System.out.println("�� " + contal.size() + "���� ����ó�� ����Ǿ� �ֽ��ϴ�.");
		for(int i =0 ; i< contal.size(); i++) {
			System.out.println(contal.get(i));             //PersonVo �� toString �صξ get(index) �޼ҵ�� �ش� ������ ���ڿ��� �ҷ��ü� �ִ�. (toString �޼��� ���� ���� �ʿ����)
		}
	}
	
	/**
	 * �� ����ó �Է���
	 * @param :
	 * @return :void
	 */
	public void insertCont() {
//		Scanner scan = new Scanner(System.in);
		ContactDao contDao = new ContactDao();
		PersonVo per = new PersonVo();
		

		System.out.println("����� ȸ���� ������ �Է��ϼ���.(��� �Է��� ������� �Է��Ͻñ� �ٶ��ϴ�.)");	 
		System.out.print("�̸�: ");
		per.setContnm(scan.next());
		System.out.print("��ȭ��ȣ(ex:01012334456): " );	
		per.setConttelno(scan.next());
		System.out.print("�ּ� : ");
		per.setContadr(scan.next());
		System.out.print("����(ex. ����, ģ��, ȸ��, ��Ÿ) : ");
		per.setTypenm(scan.next());
			
		contDao.insertCont(per);						//����ڷκ��� �Է¹��� ������ DB�� �Է½�Ų��.
		System.out.println("����ó�� �߰� �Ǿ����ϴ�");
		
//		scan.close();

	}
	
	/**
	 * �˻��� ����ó ��� �� ����
	 * @param : ����
	 * @return :void
	 */
	public void updateCont() {
		ContactDao contDao = new ContactDao();
		System.out.println("�����ϰ��� �ϴ� �̸��� �Է��ϼ���.");
		System.out.print("�̸�:");
//		Scanner scan = new Scanner(System.in);
		String name = scan.next();
		
		ArrayList<SearchVo> srchal = contDao.searchName(name);
		System.out.println();
		System.out.println("�� " + srchal.size() + "���� �˻��Ǿ����ϴ�.");
		for(int i =0; i < srchal.size(); i++) {
			System.out.println(srchal.get(i));
			}
		System.out.println();
		System.out.println("���� �˻� ������� �����ϰ��� �ϴ� ��ȣ�� �Է����ּ���.");
		String no = scan.next();
		
		PersonVo per = new PersonVo();
		System.out.println("������ ȸ���� ������ �Է��ϼ���.(��� �Է��� ������� �Է��Ͻñ� �ٶ��ϴ�.)");	 
		System.out.print("�̸�: ");
		per.setContnm(scan.next());
		System.out.print("��ȭ��ȣ(ex:01012334456): " );	
		per.setConttelno(scan.next());
		System.out.print("�ּ� : ");
		per.setContadr(scan.next());
		System.out.print("����(ex. ����, ģ��, ȸ��, ��Ÿ) : ");
		per.setTypenm(scan.next());
		
//		System.out.println("�Է¼���");
//		System.out.println(per.toString());
		contDao.updateCont(per,name,no);
		System.out.println("����ó�� ���� �Ǿ����ϴ�");
		
//		scan.close();
		
		
		}
	
	/**
	 * �˻��� ����ó ��� �� ����
	 * @param : ����
	 * @return :void
	 */
	public void deleteCont() {
		ContactDao contDao = new ContactDao();
		System.out.println("�����ϰ��� �ϴ� �̸��� �Է��ϼ���.");
		System.out.print("�̸�:");
		String name = scan.next();
		
		ArrayList<SearchVo> srchal = contDao.searchName(name);
		System.out.println();
		System.out.println("�� " + srchal.size() + "���� �˻��Ǿ����ϴ�.");
		for(int i =0; i < srchal.size(); i++) {
			System.out.println(srchal.get(i));
			}
		System.out.println();
		System.out.println("���� �˻� ������� �����ϰ��� �ϴ� ��ȣ�� �Է����ּ���.");
		String no = scan.next();
		
		contDao.deleteCont(name, no);
		System.out.println("����ó�� ���� �Ǿ����ϴ�");
		
	}
 }

