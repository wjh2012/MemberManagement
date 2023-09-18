# MemberManagement
ggomg.net

## 주 기능
#### 회원가입/로그인
- 일반 form 회원가입/로그인
- OAuth2 소셜 회원가입/로그인
  
#### 회원조회
- 조건 검색
- Paging(offset)
  
#### 회원삭제
- MANAGER 이상의 권한을 가진 사용자만 가능
- 하위 권한을 대상으로만 삭제 가능
- 회원 삭제 시, redis 삭제 정보 등록
- 삭제된 회원은 필터에서 검사하여 세션 중단
  
#### 회원정보 변경
- 회원 닉네임 변경 기능

## 🚀배포/기타
- CI : Github
- CD : Github Actions
- Docker
- AWS EC2
