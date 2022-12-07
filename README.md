## 스프링 부트로 로그인 기능이 없는 나만의 항해 블로그 백엔드 서버 만들기


### 📌 1. Use Case 
![image](https://user-images.githubusercontent.com/117708164/204476645-f6788e0e-5cdf-4929-9185-10f0f1544061.png)


### 📌 2. 기능 요구사항

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
 
 회원 가입 API<br>
   - username, password를 Client에서 전달받기
   - username은  `최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)`로 구성되어야 한다.
   - password는  `최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자`로 구성되어야 한다.
   - DB에 중복된 username이 없다면 회원을 저장하고 Client 로 성공했다는 메시지, 상태코드 반환하기
   - 회원 권한 부여하기 (ADMIN, USER) - ADMIN 회원은 모든 게시글, 댓글 수정 / 삭제 가능
                   
 로그인 API<br>
   - username, password를 Client에서 전달받기
   - DB에서 username을 사용하여 저장된 회원의 유무를 확인하고 있다면 password 비교하기
   - 로그인 성공 시, 로그인에 성공한 유저의 정보와 JWT를 활용하여 토큰을 발급하고, 
   - 발급한 토큰을 Header에 추가하고 성공했다는 메시지, 상태코드 와 함께 Client에 반환하기
    
 댓글 작성 API<br>
   - 토큰을 검사하여, 유효한 토큰일 경우에만 댓글 작성 가능
   - 선택한 게시글의 DB 저장 유무를 확인하기
   - 선택한 게시글이 있다면 댓글을 등록하고 등록된 댓글 반환하기
    
 댓글 수정 API
   - 토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 댓글만 수정 가능
   - 선택한 댓글의 DB 저장 유무를 확인하기
   - 선택한 댓글이 있다면 댓글 수정하고 수정된 댓글 반환하기
    
댓글 삭제 API
   - 토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 댓글만 삭제 가능
   - 선택한 댓글의 DB 저장 유무를 확인하기
   - 선택한 댓글이 있다면 댓글 삭제하고 Client 로 성공했다는 메시지, 상태코드 반환하기
    
예외 처리
   - 토큰이 필요한 API 요청에서 토큰을 전달하지 않았거나 정상 토큰이 아닐 때는 "토큰이 유효하지 않습니다." 라는 에러메시지와 statusCode: 400을 Client에 반환하기
   - 토큰이 있고, 유효한 토큰이지만 해당 사용자가 작성한 게시글/댓글이 아닌 경우에는 “작성자만 삭제/수정할 수 있습니다.”라는 에러메시지와 statusCode: 400을 Client에 반환하기
   - DB에 이미 존재하는 username으로 회원가입을 요청한 경우 "중복된 username 입니다." 라는 에러메시지와 statusCode: 400을 Client에 반환하기
   - 로그인 시, 전달된 username과 password 중 맞지 않는 정보가 있다면 "회원을 찾을 수 없습니다."라는 에러메시지와 statusCode: 400을 Client에 반환하기
   

### 📌 3. 요구사항 분석
![image](https://user-images.githubusercontent.com/117708164/204755071-3004de7c-a40f-43c7-8637-a7f9aeb9d31b.png)


### 📌 3.1 ERD

📍 Erd Model  

![image](https://user-images.githubusercontent.com/117708164/206178818-c7ca225c-e2fa-47f6-b008-ac294a84624d.png)


📍 실제 설계된 Diagram

![image](https://user-images.githubusercontent.com/117708164/206176862-0f5d2c0c-858c-4eba-9971-814622f5ad60.png)


### 📌 4. API

#### 입문주차 API
![image](https://user-images.githubusercontent.com/117708164/204755200-928afdf2-7c49-42ac-bd99-8b053fcdf6b3.png)


#### 숙련주차 API
![image](https://user-images.githubusercontent.com/117708164/206185119-80504f42-95c7-4022-bd84-c9f6776da93a.png)


### 📌 5. 기술 스택
FE : -  <br>
BE : Java, Spring, JPA, H2 database
