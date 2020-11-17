--1. 테이블 생성
-- 2개의 테이블을 생성할 것이며, 아래와 같다.
--	1) typecodes 테이블 : 연락처의 구분코드 컬럼의 정보
--	2) contacts 테이블 : 관리될 연락처 정보

-- table 1. 연락처의 구분 타입을 참조할 typecodes 테이블을 생성
--	typecods 테이블의 구분 코드 컬럼에 기본키를 걸어주고, 이 기본키를 참조로 하여 contacts에서 외래키를 걸어줄 것이기 때문에, 부모테이블인 typecodes 테이블을 먼저 생성한다.
--  자바 프로그램을 통한 client에 의해서 수정할 수 없다. 즉 SQL에서만 수정 가능한 테이블이다.
--  총 2개의 컬럼으로 구성된다. : 1. 구분코드넘버(pk)	2.구분 이름
create table typecodes(
				typecd		varchar2(10)								-- 구분 코드 0,1,2,3 값이 입력될 typecd 컬럼 생성
			,	typenm		varchar2(20)								-- 위 코드에 매칭될 가족, 친구, 회사, 기타 가 입력될 typenm 컬럼 생성
	-- 코드성 값으로 저장될 typecd 컬럼을 기본키로 걸어준다.
			,	constraints pk_typecodes_typecd
				primary key(typecd)
)
;

--	typecodes 테이블에 값 입력 : 아래의 값을 입력해준다.
insert into typecodes values (0, '가족');
insert into typecodes values (1, '친구');
insert into typecodes values (2, '회사');
insert into typecodes values (3, '기타');

-- select 문으로 입력이 정확히 된것을 확인하고 commit 해준다.
select * from typecodes;
commit;


-- table 2. 연락처가 저장될 contacts 테이블 생성
-- 	DB에 저장되어 관리될 연락처 정보 테이블
--	Client에 의해서 추가, 수정, 삭제가 가능하다.
--	총 4개의 컬럼으로 구성된다. : 1.이름 2.전화번호(pk) 3.주소 4.구분코드(fk)
create table contacts(
		  contnm 	 	 varchar2(20) not null					 -- 이름이 반드시 입력되도록 not null 을 걸어준다.
		, conttelno  number													 -- 전화번호에 check 제약조건을 걸어서 휴대폰 번호 형식만 입력될 수 있도록 한다.
			constraints check_telnum
			check (conttelno between 01000000000 and 02000000000)
		, contadr 	 varchar2(20)
		, typecd		 varchar2(10) not null
		, constraints pk_contacts_conttelno			   	 -- 전화번호를 pk 로 잡아주어 unique 와 not null 제약조건을 갖도록 한다.
			primary key(conttelno)
		, constraints fk_contacts_conttype					 -- 구분 컬럼을 fk 로 잡아주어, 구분 테이블에 없는 값이 들어오면 오류 발생시켜 이상한 값이 들어오지 못하도록 하여 데이터의 참조 무결성을 보장한다.
			foreign key(typecd)
			references typecodes(typecd)
		)
;

-- 연락처 테이블에 입력에 사용되는 기본 쿼리 구조----
insert into contacts values('안나','01020250179','수원시','0');

-- 두번째 테이블 생성 후 커밋한다.
commit;


-- 2. View 생성 : 사용자 화면에서 출력될, 전체 연락처 출력 결과를 보여줄 select문 쿼리의 View를 만든다.
create or replace view cont_all_view as
-- 숫자 형식의 구분코드를 제외한 모든 컬럼이 들어갔다. 전화번호는 lpad를 찍어서 제일 앞의 숫자 0이 포함된  정상적인 휴대폰 번호를 출력하게 한다.
select c.contnm, lpad(c.conttelno, 11, '0') as telnum, c.contadr, t.typenm
	from contacts c
		 , typecodes t
 where c.typecd = t.typecd													-- c와 t의 조인조건을 걸어주어 코드와 매칭되는 구분이름이 출력되도록 한다.
 order by contnm
;

commit;
-- 여기까지 커밋 해주면, 테이블 스페이스 영역에서의 모든 세팅이 끝났다.


-- 3. "자바"에 입력될 쿼리문 짜기

-- 전체 연락처 출력 쿼리
-- 위에 만들어준 view 의 select 문으로 전체 연락처 정보를 출력한다.
select *
	from cont_all_view
;

-- 연락처 추가 쿼리
-- 자바 사용자에 의해 연락처가 입력될 때, 코드가 아닌, 구분 이름으로 입력하게 되므로, contacts 테이블의 형식에 맞게 입력이 될수 있도록 쿼리를 구성한다.
insert into contacts
values('안나'
			,'01039550179'
			,'인천시'
-- typecodes table에 where문을 사용하여 입력하는 값과 매칭되는 코드를 출력하게 하는 서브 쿼리를 넣어준다.
			, (select typecd
 					 from typecodes
 					where typenm = '회사'
 			  )
)
;

-- 이름 검색결과를 출력할 쿼리
-- rownum 컬럼을 사용하여, 검색한 이름의 순번을 부여하는 select문을 만든다.
select rownum as numb, c.contnm, lpad(c.conttelno,11,'0') as telnum, c.contadr, t.typenm
	from contacts c
		 , typecodes t
-- where 조건문을 통해 사용자에 의해 입력될 이름에 매칭되는 row만 출력되도록 한다.
 where c.typecd = t.typecd
   and c.contnm = '안나'
 order by rownum    -- rownum으로 orderby 하여서, 수정/삭제 시 사용되는 rownum 과 동일한 number로 매칭되게 한다.
;


-- 수정을 수행할 명령쿼리 --
--  update 문을 사용하여 수정작업을 수행한다.
-- 	set 영역은 위의 입력쿼리와 동일한 구조를 가진다.
update contacts
 	set conttelno = '01058000179'
 		,	contnm = '안나'
 		, contadr = '인천시'
 		, typecd = (
 								select typecd
 									from typecodes
 								 where typenm = '회사'
 								)
 -- 수정을 위한 조건 작성 : pk 인 전화번호를 이용하여 수정 혹은 삭제한다. 서브쿼리를 이용한다.
 -- 출력된 검색결과에서 선택한 rownum 번호에 해당하는 전화번호 값과 일치하는 contacts 테이블의 row만 선택되게 한다.
 where conttelno in (
											select s.conttelno
												from (
															-- 이름 검색결과가 출력되는 서브쿼리
															select rownum as numb, c.conttelno
																from contacts c
																	 , typecodes t
															 where c.typecd = t.typecd
															   and c.contnm = '안나'
															 order by rownum
														)s
											-- 출력된 검색결과에서 수정하고자 하는 번호를 선택하여, 여기에 해당하는 전화번호만 출력되게 한다.
											where numb = '2'
										)
 ;



-- 삭제를 수행할 명령쿼리
-- delete 를 사용하여 삭제한다.
-- 조건문은 수정 쿼리와 동일하다.
delete
 	from contacts c
 where conttelno in (
											select s.conttelno
												from  (
																select rownum as numb, c.conttelno
																	from contacts c
																		 , typecodes t
																 where c.typecd = t.typecd
																   and c.contnm = '안나'
																 order by rownum
															)s
											where numb = '1'
										)
 ;




-- A. 인덱스 설정 (필수X, 프로그램 작동 화면에 달라지는 것은 없다)
--  데이터양이 많이 쌓였을 때, 검색 성능을 향상 시키기 위한 목적으로, 연락처의 이름인 contnm 으로 인덱스를 생성한다. => 이름은 중복 가능하므로 Non Unique Index 가 되어야 한다.
--  만약 번호로 인덱스 생성한다면?  => 중복안되므로 Unique index 생성 가능하다. 그리고 위에서 번호를 이미 pk로 잡아두었기 때문에 오라클이 자동으로 이에 해당하는 유니크 타입 인덱스를 생성해줬다.
create index idx_contacts_contnm
on contacts(contnm)
;

--  생성된 인덱스 확인
select index_name, index_type, table_name, uniqueness
	from user_indexes -- user_indexes => 유저가 생성한 인덱스 정보가 들어있다.
 where table_name = upper('contacts')
 ;

