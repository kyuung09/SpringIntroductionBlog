## 스프링 부트로 로그인 기능이 없는 나만의 항해 블로그 백엔드 서버 만들기


### 1. Use Case 
![image](https://user-images.githubusercontent.com/117708164/204476645-f6788e0e-5cdf-4929-9185-10f0f1544061.png)


### 2. 기능 요구사항

전체 게시글 목록 조회 API
 - 제목, 작성자명, 작성 내용, 작성 날짜를 조회하기
 - 작성 날짜 기 내림차순으로 정렬하기
 
 게시글 작성 API
 - 제목, 작성자명, 비밀번호, 작성 내용을 저장하고
 - 저장된 게시글을 Client 로 반환하기


선택한 게시글 조회 API
 - 선택한 게시글의 제목, 작성자명, 작성 날짜, 작성 내용을 조회하기 


선택한 게시글 수정 API
 - 수정을 요청할 때 수정할 데이터와 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후
 - 제목, 작성자명, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기


선택한 게시글 삭제 API
 - 삭제를 요청할 때 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후
 - 선택한 게시글을 삭제하고 Client 로 성공했다는 표시 반환하기



### 3. 요구사항 분석
![image](https://user-images.githubusercontent.com/117708164/204755071-3004de7c-a40f-43c7-8637-a7f9aeb9d31b.png)


### 4. API
![image](https://user-images.githubusercontent.com/117708164/204755200-928afdf2-7c49-42ac-bd99-8b053fcdf6b3.png)


### 5. 기술 스택
FE : -  <br>
BE : Java, Spring, JPA, H2 database
