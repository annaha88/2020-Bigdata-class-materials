package vo;

//	�� ����� ����ó ������ ����Ǵ� ��ü PersonVo class
public class PersonVo {

//	private ���� ��������� ���� ĸ��ȭ�Ѵ�. �̸�, ��ȭ��ȣ, �ּ�, ������ 4�� ��� ���� ����
	private String contnm;												
	private String conttelno;
	private String contadr;
	private String typenm;
	
//	getter setter�� get,set �޼��� ����
	public String getContnm() {
		return contnm;
	}
	public void setContnm(String contnm) {
		this.contnm = contnm;
	}
	public String getConttelno() {
		return conttelno;
	}
	public void setConttelno(String conttelno) {
		this.conttelno = conttelno;
	}
	public String getContadr() {
		return contadr;
	}
	public void setContadr(String contadr) {
		this.contadr = contadr;
	}
	public String getTypenm() {
		return typenm;
	}
	public void setTypenm(String typenm) {
		this.typenm = typenm;
	}
//	toString �������̵� �Ͽ� ��µ� ����ó ������ �����Ѵ�.
	@Override
	public String toString() {
		return "�̸� :" + contnm + "   ��ȭ��ȣ:" + conttelno + "  �ּ�:" + contadr + "   ����:" + typenm
				;
	}
	

	
}
