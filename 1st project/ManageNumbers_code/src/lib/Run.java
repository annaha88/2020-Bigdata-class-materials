package lib;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Run {

		public void runProgram() {
			Scanner scan = new Scanner(System.in);
			HashMap <String, MemberInformation> membHs = new HashMap<String, MemberInformation>();
			
			String name=null;
			String number = null;
			String address= null;
			String type=null;
			
			String rename = null;
			String renumber = null;
			String readdress= null;
			String retype=null;
			
			String n = null;
			
			boolean a = true;
			
			while(a) { 				
				Menu.menu();												//���� �޴��� Menu lib class�� Static�޼ҵ带 ���� ��µȴ�.
				n = scan.next();											//����ġ ���� ���� �Ķ���� n�� String Ÿ������ �Է¹޴´�. 
				Set <String> numberKeys = membHs.keySet();             		//���� �ؽø��� Ű ����Ʈ�� �����Ѵ�.
				
				switch(n) {													//n�� int�� ����ϸ� ���� �Է½� ���� �߻��Ͽ� ���α׷� ����ǹǷ� String Ÿ������ ����� ������ ����Ͽ� ó���ߴ�.
				case "1" : {	
					System.out.println("����� ȸ���� ������ �Է��ϼ���.(��� �Է��� ������� �Է��Ͻñ� �ٶ��ϴ�.)");	  //���鿡 ���� ��ū �и��� ���� ����, �ȳ������� �����ߴ�. 
					System.out.print("�̸�: ");
					name = scan.next();
					System.out.print("��ȭ��ȣ(ex:01012334456): " );				
					try {																			   //��ȭ��ȣ ���� �̿� �Է� �޾��� �� ���� ó�� : try catch.
						number = scan.next();				
						@SuppressWarnings("unused")													   //����ó���� ���� ������ int num �� ������ ������ �����Ƿ� Suppress�� ���� ����ǥ�ø� �����ߴ�.
						int num = Integer.parseInt(number);                                 		   //�Է¹��� ��Ʈ�� Ÿ�� number�� Integer class�� static�� parseInt �޼ҵ带 ���� int Ÿ������ ��ȯ�ߴ�. 
						}catch(NumberFormatException e) {											   //���� �̿��� ���ڰ� �ԷµǾ��� �� catch�� ������ ��´�.
							System.out.println("��ȭ��ȣ�� ���ڸ� �Է°����մϴ�. ���� �޴��� ���ư��ϴ�.");
							break;
						}				
					if(membHs.containsKey(number)) {												   //�̹� �ִ� ��ȣ �Է��Ϸ��� ���� �� ����ó�� : �ٽ� �������� ���ư���.
						System.out.println("�̹� �����ϴ� ��ȣ�Դϴ�. ���� �� ���� �����մϴ�. ���� �޴��� ���ư��ϴ�.");
						break;
						}
					System.out.print("�ּ� : ");
					address = scan.next();
					System.out.print("����(ex. ����, ģ��, ��Ÿ) : ");
					type = scan.next();
					
					MemberInformation membInfo = new MemberInformation(name, number, address, type);    //���� �Էµ� ������ ������ ���ο� MemberInformation class ��ü�� �����Ѵ�.
					membHs.put(number, membInfo);					        							//�Է¹��� ������ �ؽøʿ� ��ǲ�Ѵ�.			
					break;
					}
				
				case "2" : 																				// ȸ�� ��� ����
					System.out.println("�� " + membHs.size() + "���� ȸ���� ����Ǿ� �ֽ��ϴ�.");				
	     
					Iterator <String> itNumberKeys = numberKeys.iterator();								//���ͷ����ͷ� ������ ������� Ű�� numberKeys�� ������ itNumberKeys��� �̸����� �����ߴ�.
							
					while(itNumberKeys.hasNext()) {														//���� �����ϴ� ����, �� Ű���� numberkeys��� �̸����� �ݺ� �����ȴ�.
							String numberkeys = itNumberKeys.next();								 	 
							MemberInformation memb = membHs.get(numberkeys);							//������ Ű���� �ش��ϴ� value�� memb��� �̸����� ��´�.
							
							System.out.println(memb.toString());										//�ش� ������ toString()�� �̿��Ͽ� ����Ѵ�. while���� ���ͷ����Ϳ� ���ؼ�, �ؽøʿ� ����� ��� ������ ��µȴ�.
							}
					break;
					
				case "3" :                               //������Ʈ : keyset, equal��, 2nd hashmap �� ����ϴ� ���.          								
					MemberInformation contact=null;						// Ű������ �޾Ƴ� Ű�� ��, Ŭ���� value�� ��ü�� �����ߴ�. 
					String contactinfo = null;							// toString �� �������̵��ؼ� �˻��� ����� ��ü ������ ���ڿ� ���·� �� ������ �����ߴ�.
					
					int m = 0;											//�˻��� �̸��� ī���ÿ� ���� int Ÿ�� ���� m�� s�� �ʱ�ȭ�ߴ�.
					int s = 0;
					
					System.out.println("������ ȸ���� �̸��� �Է��ϼ���.");
					System.out.print("�̸�: ");
					name = scan.next();
					System.out.println();
		
					for (String key : numberKeys ) { 							//Ű�°� for each ���� �̿��Ͽ�, ��� Ű���� ����Ѵ�.
						contact= membHs.get(key);								//for each ������ ������� key���� �ش��ϴ� ����ó ������ contact��� �̸����� ��´�.			
						if (name.equals(contact.getName())){					//contact ��ü�� �̿��Ͽ� getName ����, �Է¹��� �̸� name �� equals �� �̿��Ͽ� ���Ѵ�.
						m++;													//�Է°��� ��ġ�ϴ� �̸��� �����ؽøʿ� �����Ѵٸ�, ī������ �ö󰣴�.
						}
						}

					if(m!=0) {													//ī���� �� ���ڰ� 0�� �ƴ϶��, �� �˻��� �̸��� �����ϸ�, ������ �����Ѵ�.
					HashMap <String, MemberInformation> searchHs = new HashMap<String, MemberInformation>();         //�˻������ ������ �ι�° �ؽø� searchHs �� �����ߴ�. 
					System.out.println("�� " + m + "���� �˻��Ǿ����ϴ�");			//ī���� �Ϸ�� �� m �� �̿��Ͽ� �˻������ ������ ǥ���Ѵ�.
					System.out.println("�Ʒ� ��� �� ������ ȸ���� ��ȣ�� �Է��ϼ���.");
					for (String key : numberKeys ) {							//���� ������ for each ���� �ѹ� �� ������, �˻��� ��ü���� ��� ������ �ݺ�����ϰ� �Ѵ�.
						contact= membHs.get(key);
						if (name.equals(contact.getName())){                    //���⼭ ī���� �Ǵ� ������ ���� s �� �������.
						s++;
						
						contactinfo = contact.toString();								 	//toString �� �������̵��ؼ� ��ü ������ ���ڿ� ���� contactinfo �� �����.
						System.out.println(s + "." + contactinfo);                          //s�� �˻��� �� ����� ��ȣ�� ���ȴ�. �ñ������δ� �� �� s�� �ι�° �ؽø��� Ű�� ����ϰ� ������̴�. 
						
						Integer sInt = s;                          							//int Ÿ�� ���� s�� Integer Ŭ���� �̿��ؼ� ��ü�� Wrapping �Ѵ�. �� ��ü�� ���� toString �޼��带 ����ؼ� ����ȯ ����������. 
						String k = sInt.toString();     			 						//s�� int���� ��Ʈ�� Ÿ�� k�� ��ȯ�ߴ�. ���߿� �Էµ� ������ ���� �Է½� ���α׷� ���Ḧ ���� ���� String���� �����Ѵ�.
						
						searchHs.put(k, contact);        								    //searchHs �ؽøʿ�, �˻��� ����� ����ȴ�. ���⼭ �˻��� ��ȣ s�� ��Ʈ�� Ÿ������ ����ȯ�� k�� Ű�� ���ȴ�. k���� �ߺ��� ���� �����Ƿ�,Ű�� ����� �� �ִ�. 
						}}

						String t = scan.next();                								//���� Ȥ�� �����ϰ� ���� ��ȣ t�� ����ڿ� ���� �Էµȴ�.

						if(searchHs.containsKey(t)){                         				//�Էµ� ��ȣ�� ��ġ�ϴ� �ι�° �ؽø��� Ű���� �ִٸ�, ����ó�� �����Ѵ�.
						MemberInformation oldcontact = searchHs.get(t);                     //�Էµ� ��ȣ�� Ű�� ���� �ش��ϴ� Ŭ���� ��ü��  oldcontact ��� �̸����� ��´�. �� Ŭ�������� �ٳѹ��� �� �ְ�, �װ� �����ؽø��� Ű�� ����ؼ�, �����ϸ� �ȴ�.
				
						System.out.println("������ ������ �Է��ϼ���.");
						System.out.print("�̸� : ");
						rename = scan.next();
						System.out.print("��ȭ��ȣ(ex:01012334456): " );

						try {																//������Ʈ������ �����ϰ�, ���ڿ� ���ڰ� �ԷµǸ� try catch �� ��´�.
							renumber = scan.next();				
							@SuppressWarnings("unused")
							int num = Integer.parseInt(renumber);                                 
							}catch(NumberFormatException e) {
								System.out.println("��ȭ��ȣ�� ���ڸ� �Է°����մϴ�. ���� �޴��� ���ư��ϴ�.");
								break;
							}
						
						if(membHs.containsKey(renumber)) {									//������Ʈ������ �����ϰ�, �̹� �ִ� ��ȣ �Է��Ϸ��� ���� ��, �ٽ� �������� ���ư���.
							System.out.println("�̹� �����ϴ� ��ȣ�Դϴ�. �ش� ��ȣ ������ ���� �����մϴ�. ���� �޴��� ���ư��ϴ�.");
							System.out.println();
							break;
							}
						System.out.print("�ּ� : ");
						readdress = scan.next();
						System.out.print("����(ex. ����, ģ��, ��Ÿ) : ");
						retype = scan.next();
						
						membHs.remove(oldcontact.getNumber());															// �����ϰ��� �ϴ� ������ó�� ��ȣ�� �� Ű�� ����ؼ�,  �����ؽø��� �ش� value�� �����Ѵ�. ���ο� ��ȣ �Է¹ޱ� ���� ���� �ϸ� ���Ϲ�ȣ ����ó�� �Ҽ� �����Ƿ�, ���ο� ��ȣ�� �Է� �ް� �����Ѵ�.	

						MemberInformation membInfo = new MemberInformation(rename, renumber, readdress, retype); 		// ������ �� ����ó���� ��ǲ�ϱ� ���� ���ο� ��ü �����Ѵ�.
						membHs.put(renumber, membInfo);           														// ���� ������ ��ü�� �����ؽøʿ� ��ǲ�Ѵ�. ������ ���ͷ����ͷ� ��µǴ� ��ü ������ ������ �����Ƿ�, �� ���� ������ ������ �Ŀ� replace �� �ʿ�� ����.    								
																										
						System.out.println("������ �Ϸ� �Ǿ����ϴ�.");
						System.out.println();
						
						}else {																							//�˻���� ��ȣ �̿ܿ� �ٸ� ���ڳ� ���ڰ� �ԷµǸ�, ������������ �ʰ� �������� ���ư���.
							System.out.println("�߸��� �Է��Դϴ�. ��Ͽ� �ش��ϴ� ���ڸ� �Է��ؾ� �մϴ�.");
							System.out.println();
							break;
							}
							}
									
					else {
					System.out.println("�ش��ϴ� ȸ�� ������ �����ϴ�. ���� �޴��� ���ư��ϴ�.");										//�̸� �˻��� ��ġ�ϴ� �̸��� �ؽøʿ� �������� �ʴ´ٸ�, �������� ���ư���. 
					System.out.println();
						}				                 

					break;
					
				case "4":          											 								// ���� �޼ҵ��, ���ο� ������ ��ǲ�Ǵ� �κи� �����ϸ� �� �����޼ҵ�� �����ϴ�.
					System.out.println("������ ȸ���� �̸��� �Է��ϼ���.");
					System.out.print("�̸�: ");
					name = scan.next();
					System.out.println();
					MemberInformation contacts=null;
					String contactinfos = null;
					int mm = 0;
					int ss = 0;
		
					for (String key : numberKeys ) {
						contacts= membHs.get(key);
						if (name.equals(contacts.getName())){
							mm++;
							}
						}

					if(mm!=0) {
						HashMap <String, MemberInformation> searchHs = new HashMap<String, MemberInformation>();
						System.out.println("�� " + mm + "���� �˻��Ǿ����ϴ�");
						System.out.println("�Ʒ� ��� �� ������ ȸ���� ��ȣ�� �Է��ϼ���.");
						
						for (String key : numberKeys ) {
							contacts= membHs.get(key);
							if (name.equals(contacts.getName())){
							ss++;
						
							contactinfos = contacts.toString();									
							System.out.println(ss + "." + contactinfos);                        
						
							Integer sss = ss;                          							
							String k = sss.toString();     			 							
						
							searchHs.put(k, contacts);            								
							}}
					
					String t = scan.next();                

						if(searchHs.containsKey(t)){                        					//�Էµ� ��ȣ�� ��ġ�ϴ� �ι�° �ؽø��� Ű���� �ִٸ�, ����ó�� �����Ѵ�.
							MemberInformation oldcontact = searchHs.get(t);                     
							membHs.remove(oldcontact.getNumber());
							System.out.println("������ �Ϸ�Ǿ����ϴ�.");								// �����ϰ��� �ϴ� ������ó�� ��ȣ�� �� Ű�� ����ؼ�, �����ؽø��� �ش� value�� �����Ѵ�.
							}          
						else {
							System.out.println("�߸��� �Է��Դϴ�. ��Ͽ� �ش��ϴ� ���ڸ� �Է��ؾ� �մϴ�. ���θ޴��� ���ư��ϴ�.");
							System.out.println();
							break;
							}		
						}
						else {
							System.out.println("�ش��ϴ� ȸ�� ������ �����ϴ�. ���� �޴��� ���ư��ϴ�.");
							System.out.println();
							}
					break;									
					
				case "5":																	//5�� �ԷµǸ�, while �� boolean���� false �� �����鼭, while ���� ����������, ���μ��� ����ȴ�.
					System.out.println("����Ǿ����ϴ�.");
					a = false;
					break;
				
				default :																	//���� �޴��� ���� 5�� �̿��� �Է��� ������(�� case ���� �ش���� �ʴ� �Է°��� ������), default ó���Ǿ� ���θ޴��� ���ư���. 
					System.out.println("�߸��� �Է��Դϴ�. �޴��� ���ڸ� �Է����ּ���.");
					}
				}
			scan.close();
			}                                                                  //��ĳ�ʸ� �ݴ´�.
}
