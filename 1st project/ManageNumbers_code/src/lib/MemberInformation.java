package lib;


public class MemberInformation {

	private String name;												//private 으로 멤버변수를 만들어서 캡슐화한다.
	private String number;
	private String address;
	private String type;
	
	public MemberInformation() {}
	
	public MemberInformation(String name, String number, String address, String type) {                         //4개의 정보를 파라미터로 한 생성자를 만들었다. 
		this.name = name;
		this.number = number;
		this.address = address;
		this.type = type;
		}
	

	public String getName() {												//정보를 얻기위한 getter 를 사용한다.
		return name;
	}


	public String getNumber() {
		return number;
	}


	public String getAddress() {
		return address;
	}


	public String getType() {
		return type;
	}
	
	@Override
	public String toString() {                                //toString을 오버라이딩 해서 객체 정보를 문자열로 표시 가능해졌다.
		return "회원 정보 : 이름 = "	 +  name 
								 + ", 전화번호 : "
								 + number
								 + ", 주소 : "
								 + address
								 + ", 종류 : "
								 + type;
	}

}
