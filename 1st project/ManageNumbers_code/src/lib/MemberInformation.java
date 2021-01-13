package lib;


public class MemberInformation {

	private String name;												//private ���� ��������� ���� ĸ��ȭ�Ѵ�.
	private String number;
	private String address;
	private String type;
	
	public MemberInformation() {}
	
	public MemberInformation(String name, String number, String address, String type) {                         //4���� ������ �Ķ���ͷ� �� �����ڸ� �������. 
		this.name = name;
		this.number = number;
		this.address = address;
		this.type = type;
		}
	

	public String getName() {												//������ ������� getter �� ����Ѵ�.
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
	public String toString() {                                //toString�� �������̵� �ؼ� ��ü ������ ���ڿ��� ǥ�� ����������.
		return "ȸ�� ���� : �̸� = "	 +  name 
								 + ", ��ȭ��ȣ : "
								 + number
								 + ", �ּ� : "
								 + address
								 + ", ���� : "
								 + type;
	}

}
