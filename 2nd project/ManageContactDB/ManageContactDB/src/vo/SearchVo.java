package vo;

//	�̸� �˻� ������� �� ����� ������ ����� ��ü SearchVo Ŭ����
//	���� Ȥ�� ���� ������ ���� ��ȣ numb�� int Ÿ�� ������ �߰��ȴ�.
public class SearchVo {
	private int numb;
	private String contnm;												
	private String conttelno;
	private String contadr;
	private String typenm;
	
	public int getNumb() {
		return numb;
	}
	public void setNumb(int numb) {
		this.numb = numb;
	}
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
		return  numb + ". �̸� :" + contnm + "  ��ȭ��ȣ:" + conttelno + "  �ּ�:" + contadr
				+ "  ����:" + typenm;
	}

}
