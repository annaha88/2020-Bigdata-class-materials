package vo;

//	한 사람의 연락처 정보가 저장되는 객체 PersonVo class
public class PersonVo {

//	private 으로 멤버변수를 만들어서 캡슐화한다. 이름, 전화번호, 주소, 종류의 4개 멤버 변수 선언
	private String contnm;												
	private String conttelno;
	private String contadr;
	private String typenm;
	
//	getter setter로 get,set 메서드 생성
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
//	toString 오버라이딩 하여 출력될 연락처 형식을 지정한다.
	@Override
	public String toString() {
		return "이름 :" + contnm + "   전화번호:" + conttelno + "  주소:" + contadr + "   구분:" + typenm
				;
	}
	

	
}
