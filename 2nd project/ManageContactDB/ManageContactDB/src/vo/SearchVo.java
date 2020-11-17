package vo;

//	이름 검색 결과에서 한 사람의 정보가 저장될 객체 SearchVo 클래스
//	수정 혹은 삭제 선택을 위한 번호 numb이 int 타입 변수로 추가된다.
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
		return  numb + ". 이름 :" + contnm + "  전화번호:" + conttelno + "  주소:" + contadr
				+ "  구분:" + typenm;
	}

}
