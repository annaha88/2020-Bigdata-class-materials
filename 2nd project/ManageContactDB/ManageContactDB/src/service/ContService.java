package service;

import java.util.ArrayList;
import java.util.Scanner;

import dao.ContactDao;
import vo.PersonVo;
import vo.SearchVo;


public class ContService {
//	����ڿ� ���� ������ �Էµ� �� �ֵ��� Scanner�� �����ش�.
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
//	Dao ��ü�� new�Ͽ��� ������ �����ϵ��� �� ��, searchAll()���� ��ȯ�Ǵ� ArrayList �� ��� ����ϵ��� for���� ����Ѵ�.
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
//		Dao ��ü ����, �Էµ� ����ó ������ ����� PersonVo ��ü ����
		ContactDao contDao = new ContactDao();
		PersonVo per = new PersonVo();
//		�߰��� ����ó ���� �Է�
		System.out.println("����� ȸ���� ������ �Է��ϼ���.(��� �Է��� ������� �Է��Ͻñ� �ٶ��ϴ�.)");	 
		System.out.print("�̸�: ");
		per.setContnm(scan.next());
		System.out.print("��ȭ��ȣ(ex:01012334456): " );	
		per.setConttelno(scan.next());
		System.out.print("�ּ� : ");
		per.setContadr(scan.next());
		System.out.print("����(ex. ����, ģ��, ȸ��, ��Ÿ) : ");
		per.setTypenm(scan.next());
//		Dao �� ���� �Է¹��� ������ DB�� �߰��Ѵ�.
		boolean isInsert = contDao.insertCont(per);						
		if (isInsert) {
		System.out.println("����ó�� �߰� �Ǿ����ϴ�");
		}
		else {
			System.out.println("���� �޴��� ���ư��ϴ�.");				
		}
		

	}
	
	/**
	 * �˻��� ����ó ��� �� ����
	 * @param : ����
	 * @return :void
	 */
//	DAO���� ���� �Ķ������ 
//	1.PersonVo per : �ѻ���� ���������� ��� ��ü, 2.String name: ������ �� �˻��� �̸�, 3.String no : �˻�����κ��� ������ ��ȣ �� ����ڷκ��� �Է¹޴´�.

	public void updateCont() {
		ContactDao contDao = new ContactDao();
		System.out.println("�����ϰ��� �ϴ� �̸��� �Է��ϼ���.");
		System.out.print("�̸�:");
		String name = scan.next();
//		�����ϰ��� �ϴ� �̸� name �� �Է��ϸ�, Dao�� searchName(name) �޼ҵ忡 ���� ArrayList<SearchVo>�� ���, 
//		for���� ���� ����� ��� ����Ѵ�.
		ArrayList<SearchVo> srchal = contDao.searchName(name);
		System.out.println();
		System.out.println("�� " + srchal.size() + "���� �˻��Ǿ����ϴ�.");
		for(int i =0; i < srchal.size(); i++) {
			System.out.println(srchal.get(i));
			}
//		����ó ��Ͽ� ���� �̸��� �Է��Ѵٸ�, �˻������ 0 ���� �� �� �̴�. if ���ǹ��� �ɾ�, 0�� �ƴ� ��쿡�� �����۾��� ����ǵ��� �Ѵ�.
		if(srchal.size() != 0) {
		System.out.println();
		System.out.println("���� �˻� ������� �����ϰ��� �ϴ� ��ȣ�� �Է����ּ���.");
		
		try {
		String no = scan.next();
		
		//		���⼭ �˻� ��� ��ȣ�� �Է����� �� ���ư��� �ϴ� ����ó���� ���ش�. 1.�켱 String���� ���� ��ȣ no�� ���ڷ� ����ȯ�Ѵ�.
		int n = Integer.parseInt(no);
		
		if(n >srchal.size()  ^  n < 1) {					  //  2. �˻� ��Ͽ� �ش��ϴ� ��ȣ�� ������ �� �ֵ���, if���ǹ��� �ɾ��־���.
			System.out.println("�˻� ����� ��ȣ�� �Է��ϼ���");  
			
		}
//		����ڰ� ���������� ��ȣ�� �����Ѵٸ�, �����۾��� ����ȴ�.
		else {
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
//			Dao �� updateCont(per,name,no)�� ���� DB�� ����ó�� �����ȴ�. 
			boolean isUpdate = contDao.updateCont(per,name,no);
			if(isUpdate) { 
			System.out.println("����ó�� ���� �Ǿ����ϴ�");
				}
			else {
			System.out.println("���� �޴��� ���ư��ϴ�.");    
				}
			}
		}catch(NumberFormatException e) {								//�˻� �� ���ڰ� �ԷµǸ�, NumberFormatException ó���ȴ�.
		System.out.println("�˻� ����� ��ȣ�� �Է��ϼ���. ���� �޴��� ���ư��ϴ�.");				
			}
		}
		else {
			System.out.println("�ش� �̸��� �������� �ʽ��ϴ�.");
		}
		
	}
	
	/**
	 * �˻��� ����ó ��� �� ����
	 * @param : ����
	 * @return :void
	 */
//	������Ʈ�� ������Ʈ�� ���� �����Ѵ�. 
//	���ο� ��ü�� �Է��� �ʿ䰡 ���� ������ personVo�� �����ϰ�, 
//	�˻��� �̸� name��, �˻�����κ��� ������ ��ȣ no. �ΰ����� �Ķ���͸� ����ڷκ��� �Է¹޴´�.

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
		
//		���� ��Ʈ�� �����ϰ�  if ���ǹ��� �ɾ�, �˻������ �����ϴ� ��쿡�� ������ ����ǵ��� �Ѵ�.
		if(srchal.size() != 0) {
		System.out.println();
		System.out.println("���� �˻� ������� �����ϰ��� �ϴ� ��ȣ�� �Է����ּ���.");
		
			try {
				String no = scan.next();
		//		���⼭ �˻� ��� ��ȣ�� �Է����� �� ���ư��� �ϴ� ����ó�� 
				int n = Integer.parseInt(no);
				if(n >srchal.size() ^ n<1) {
					System.out.println("�˻� ����� ��ȣ�� �Է��ϼ���. ���� �޴��� ���ư��ϴ�.");	
					}
				
				boolean isDelete = contDao.deleteCont(name,no);
				if(isDelete) {
					System.out.println("����ó�� ���� �Ǿ����ϴ�");
				}
				else {
					System.out.println("���� �޴��� ���ư��ϴ�.");    
				}
			}catch(NumberFormatException e) {
				System.out.println("�˻� ����� ��ȣ�� �Է��ϼ���. ���� �޴��� ���ư��ϴ�.");
			}
		}else {
			System.out.println("�ش� �̸��� �������� �ʽ��ϴ�.");
		}
	}
	
 }

