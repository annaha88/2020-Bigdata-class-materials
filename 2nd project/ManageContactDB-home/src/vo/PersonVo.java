package vo;

public class PersonVo {

	
//	private String id;
	private String contnm;												//private ���� ��������� ���� ĸ��ȭ�Ѵ�.
	private String conttelno;
	private String contadr;
	private String typenm;
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
	@Override
	public String toString() {
		return "�̸� :" + contnm + "   ��ȭ��ȣ:" + conttelno + "  �ּ�:" + contadr + "   ����:" + typenm
				;
	}
	

	
}
