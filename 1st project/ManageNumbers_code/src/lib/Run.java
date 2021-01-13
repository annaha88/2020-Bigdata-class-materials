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
				Menu.menu();												//메인 메뉴가 Menu lib class의 Static메소드를 통해 출력된다.
				n = scan.next();											//스위치 문에 사용될 파라미터 n을 String 타입으로 입력받는다. 
				Set <String> numberKeys = membHs.keySet();             		//메인 해시맵의 키 리스트를 생성한다.
				
				switch(n) {													//n을 int로 사용하면 문자 입력시 오류 발생하여 프로그램 종료되므로 String 타입으로 선언된 변수를 사용하여 처리했다.
				case "1" : {	
					System.out.println("등록할 회원의 정보를 입력하세요.(모든 입력은 공백없이 입력하시기 바랍니다.)");	  //공백에 의한 토큰 분리를 막기 위해, 안내문구를 삽입했다. 
					System.out.print("이름: ");
					name = scan.next();
					System.out.print("전화번호(ex:01012334456): " );				
					try {																			   //전화번호 숫자 이외 입력 받았을 때 예외 처리 : try catch.
						number = scan.next();				
						@SuppressWarnings("unused")													   //예외처리를 위해 설정한 int num 은 실제로 사용되지 않으므로 Suppress로 눌러 오류표시를 제거했다.
						int num = Integer.parseInt(number);                                 		   //입력받은 스트링 타입 number를 Integer class의 static한 parseInt 메소드를 통해 int 타입으로 변환했다. 
						}catch(NumberFormatException e) {											   //숫자 이외의 문자가 입력되었을 때 catch로 에러를 잡는다.
							System.out.println("전화번호는 숫자만 입력가능합니다. 메인 메뉴로 돌아갑니다.");
							break;
						}				
					if(membHs.containsKey(number)) {												   //이미 있는 번호 입력하려고 했을 때 예외처리 : 다시 메인으로 돌아간다.
						System.out.println("이미 존재하는 번호입니다. 삭제 후 재등록 가능합니다. 메인 메뉴로 돌아갑니다.");
						break;
						}
					System.out.print("주소 : ");
					address = scan.next();
					System.out.print("종류(ex. 가족, 친구, 기타) : ");
					type = scan.next();
					
					MemberInformation membInfo = new MemberInformation(name, number, address, type);    //새로 입력될 정보를 저장할 새로운 MemberInformation class 객체를 생성한다.
					membHs.put(number, membInfo);					        							//입력받은 정보를 해시맵에 인풋한다.			
					break;
					}
				
				case "2" : 																				// 회원 목록 보기
					System.out.println("총 " + membHs.size() + "명의 회원이 저장되어 있습니다.");				
	     
					Iterator <String> itNumberKeys = numberKeys.iterator();								//이터레이터로 위에서 만들었던 키셋 numberKeys의 값들을 itNumberKeys라는 이름으로 생성했다.
							
					while(itNumberKeys.hasNext()) {														//값이 존재하는 동안, 각 키값이 numberkeys라는 이름으로 반복 생성된다.
							String numberkeys = itNumberKeys.next();								 	 
							MemberInformation memb = membHs.get(numberkeys);							//생성된 키값에 해당하는 value를 memb라는 이름으로 얻는다.
							
							System.out.println(memb.toString());										//해당 정보를 toString()를 이용하여 출력한다. while문과 이터레이터에 의해서, 해시맵에 저장된 모든 정보가 출력된다.
							}
					break;
					
				case "3" :                               //수정파트 : keyset, equal문, 2nd hashmap 을 사용하는 방법.          								
					MemberInformation contact=null;						// 키셋으로 받아낸 키로 얻어낼, 클래스 value의 객체를 선언했다. 
					String contactinfo = null;							// toString 을 오버라이딩해서 검색된 목록의 객체 정보를 문자열 형태로 얻어낼 변수를 선언했다.
					
					int m = 0;											//검색된 이름의 카운팅에 사용될 int 타입 변수 m과 s를 초기화했다.
					int s = 0;
					
					System.out.println("수정할 회원의 이름을 입력하세요.");
					System.out.print("이름: ");
					name = scan.next();
					System.out.println();
		
					for (String key : numberKeys ) { 							//키셋과 for each 문을 이용하여, 모든 키값을 얻게한다.
						contact= membHs.get(key);								//for each 문에서 얻어지는 key값에 해당하는 연락처 정보를 contact라는 이름으로 얻는다.			
						if (name.equals(contact.getName())){					//contact 객체를 이용하여 getName 한후, 입력받은 이름 name 과 equals 을 이용하여 비교한다.
						m++;													//입력값과 일치하는 이름이 메인해시맵에 존재한다면, 카운팅이 올라간다.
						}
						}

					if(m!=0) {													//카운팅 된 숫자가 0이 아니라면, 즉 검색된 이름이 존재하면, 수정을 시작한다.
					HashMap <String, MemberInformation> searchHs = new HashMap<String, MemberInformation>();         //검색결과를 저장할 두번째 해시맵 searchHs 를 생성했다. 
					System.out.println("총 " + m + "명의 검색되었습니다");			//카운팅 완료된 수 m 을 이용하여 검색목록의 개수를 표시한다.
					System.out.println("아래 목록 중 수정할 회원의 번호를 입력하세요.");
					for (String key : numberKeys ) {							//위와 동일한 for each 문을 한번 더 돌려서, 검색된 객체들의 모든 정보를 반복출력하게 한다.
						contact= membHs.get(key);
						if (name.equals(contact.getName())){                    //여기서 카운팅 되는 숫자의 값은 s 로 얻어진다.
						s++;
						
						contactinfo = contact.toString();								 	//toString 을 오버라이딩해서 객체 정보의 문자열 형태 contactinfo 를 얻었다.
						System.out.println(s + "." + contactinfo);                          //s는 검색된 각 결과의 번호로 사용된다. 궁극적으로는 이 값 s를 두번째 해시맵의 키로 사용하게 만들것이다. 
						
						Integer sInt = s;                          							//int 타입 변수 s를 Integer 클래스 이용해서 객체로 Wrapping 한다. 이 객체는 이제 toString 메서드를 사용해서 형변환 가능해졌다. 
						String k = sInt.toString();     			 						//s의 int값을 스트링 타입 k로 변환했다. 나중에 입력될 값에서 문자 입력시 프로그램 종료를 막기 위해 String으로 설정한다.
						
						searchHs.put(k, contact);        								    //searchHs 해시맵에, 검색된 결과가 저장된다. 여기서 검색된 번호 s가 스트링 타입으로 형변환된 k가 키로 사용된다. k값은 중복될 일이 없으므로,키로 사용할 수 있다. 
						}}

						String t = scan.next();                								//수정 혹은 삭제하고 싶은 번호 t가 사용자에 의해 입력된다.

						if(searchHs.containsKey(t)){                         				//입력된 번호와 일치하는 두번째 해시맵의 키값이 있다면, 연락처를 수정한다.
						MemberInformation oldcontact = searchHs.get(t);                     //입력된 번호를 키로 가진 해당하는 클래스 객체를  oldcontact 라는 이름으로 얻는다. 이 클래스에서 겟넘버할 수 있고, 그걸 메인해시맵의 키로 사용해서, 삭제하면 된다.
				
						System.out.println("수정할 정보를 입력하세요.");
						System.out.print("이름 : ");
						rename = scan.next();
						System.out.print("전화번호(ex:01012334456): " );

						try {																//수정파트에서도 동일하게, 숫자외 문자가 입력되면 try catch 로 잡는다.
							renumber = scan.next();				
							@SuppressWarnings("unused")
							int num = Integer.parseInt(renumber);                                 
							}catch(NumberFormatException e) {
								System.out.println("전화번호는 숫자만 입력가능합니다. 메인 메뉴로 돌아갑니다.");
								break;
							}
						
						if(membHs.containsKey(renumber)) {									//수정파트에서도 동일하게, 이미 있는 번호 입력하려고 했을 때, 다시 메인으로 돌아간다.
							System.out.println("이미 존재하는 번호입니다. 해당 번호 삭제후 재등록 가능합니다. 메인 메뉴로 돌아갑니다.");
							System.out.println();
							break;
							}
						System.out.print("주소 : ");
						readdress = scan.next();
						System.out.print("종류(ex. 가족, 친구, 기타) : ");
						retype = scan.next();
						
						membHs.remove(oldcontact.getNumber());															// 수정하고자 하는 옛연락처의 번호를 얻어서 키로 사용해서,  메인해시맵의 해당 value를 삭제한다. 새로운 번호 입력받기 전에 삭제 하면 동일번호 오류처리 할수 없으므로, 새로운 번호를 입력 받고 삭제한다.	

						MemberInformation membInfo = new MemberInformation(rename, renumber, readdress, retype); 		// 수정될 새 연락처값을 인풋하기 위한 새로운 객체 생성한다.
						membHs.put(renumber, membInfo);           														// 새로 생성된 객체를 메인해시맵에 인풋한다. 어차피 이터레이터로 출력되는 전체 정보는 순서가 없으므로, 꼭 이전 정보를 삭제한 후에 replace 할 필요는 없다.    								
																										
						System.out.println("수정이 완료 되었습니다.");
						System.out.println();
						
						}else {																							//검색목록 번호 이외에 다른 문자나 숫자가 입력되면, 수정진행하지 않고 메인으로 돌아간다.
							System.out.println("잘못된 입력입니다. 목록에 해당하는 숫자만 입력해야 합니다.");
							System.out.println();
							break;
							}
							}
									
					else {
					System.out.println("해당하는 회원 정보가 없습니다. 메인 메뉴로 돌아갑니다.");										//이름 검색시 일치하는 이름이 해시맵에 존재하지 않는다면, 메인으로 돌아간다. 
					System.out.println();
						}				                 

					break;
					
				case "4":          											 								// 삭제 메소드는, 새로운 정보가 인풋되는 부분만 제외하면 위 수정메소드와 동일하다.
					System.out.println("삭제할 회원의 이름을 입력하세요.");
					System.out.print("이름: ");
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
						System.out.println("총 " + mm + "명의 검색되었습니다");
						System.out.println("아래 목록 중 삭제할 회원의 번호를 입력하세요.");
						
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

						if(searchHs.containsKey(t)){                        					//입력된 번호와 일치하는 두번째 해시맵의 키값이 있다면, 연락처를 삭제한다.
							MemberInformation oldcontact = searchHs.get(t);                     
							membHs.remove(oldcontact.getNumber());
							System.out.println("삭제가 완료되었습니다.");								// 수정하고자 하는 옛연락처의 번호를 얻어서 키로 사용해서, 메인해시맵의 해당 value를 삭제한다.
							}          
						else {
							System.out.println("잘못된 입력입니다. 목록에 해당하는 숫자만 입력해야 합니다. 메인메뉴로 돌아갑니다.");
							System.out.println();
							break;
							}		
						}
						else {
							System.out.println("해당하는 회원 정보가 없습니다. 메인 메뉴로 돌아갑니다.");
							System.out.println();
							}
					break;									
					
				case "5":																	//5가 입력되면, while 의 boolean값을 false 로 받으면서, while 문을 빠져나가며, 프로세스 종료된다.
					System.out.println("종료되었습니다.");
					a = false;
					break;
				
				default :																	//메인 메뉴의 숫자 5개 이외의 입력을 받으면(즉 case 문에 해당되지 않는 입력값을 받으면), default 처리되어 메인메뉴로 돌아간다. 
					System.out.println("잘못된 입력입니다. 메뉴의 숫자만 입력해주세요.");
					}
				}
			scan.close();
			}                                                                  //스캐너를 닫는다.
}
