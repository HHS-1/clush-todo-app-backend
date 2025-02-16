# 프로젝트명: [ClushTodoApp - backend]  with Spring Boot 3.4.0

## 1. 자신이 개발한 앱에 대한 설명

**ClushTodoApp**은 개인적인 용도로도 사용 가능하며, 팀 또는 그룹 등 공용으로도 활용할 수 있는 다목적 캘린더 웹 애플리케이션입니다. 사용자는 날짜별로 할 일을 추가하고 관리할 수 있으며, 직관적인 캘린더 UI를 통해 일정을 한 눈에 쉽게 확인할 수 있습니다.

또한, 가입한 사용자 간에 캘린더를 공유할 수 있는 기능을 제공하여, 공유된 캘린더를 바탕으로 일정을 손쉽게 관리하고 협업할 수 있습니다. 개인의 일정뿐만 아니라, 팀원들과의 일정 조율이 필요한 경우에도 유용한 기능을 제공합니다.

**🚩프론트 및 백엔드 모두 배포를 완료하였습니다.
http://34.22.66.2/
주소로 이동해 확인하실 수 있습니다.🚩**

데모계정 : 
ID qazplm1021@naver.com, PW test
ID qazplm10219@gmail.com , PW test

**주요 기능**:
- **ToDo**: 제목, 내용, 날짜, 우선순위, 진행상태로 이루어진 ToDo데이터를 생성, 조회, 수정할 수 있습니다. 
- **캘린더(개인)**: ToDo를 생성함에 따라 캘린더에 표시가 되며, 우선순위에 따라 색이 다르게 표시됩니다.
- **캘린더(공유)**: 이메일을 통해 가입자를 초대하며, 공유 작업자와 함께 캘린더의 날짜 칸을 클릭하여 작업을 추가할 수 있습니다.

### 기술 스택
- **Spring Boot 3.4.0**
- **mysql 8.0.36**
- **JPA**
- **Spring Security**
- **JWT**
- **Javax.mail**
- **Juit**
- **Swagger**
- **Google Cloud PlatForm**
- **Nginx**

## 2. 소스 빌드 및 실행 방법 메뉴얼(로컬)

### 2.1. 프로젝트 빌드 및 실행 방법

1. **Git Clone**:
   ```bash
   git clone https://github.com/HHS-1/clush-todo-app-backend.git

2. **properties 파일 저장**
   서버 정보가 담긴 properties 파일을 src/main/resource에 저장합니다.

3. **프로젝트 실행**
   ```bash
   mvn clean install   // 의존성 설치
   mvn spring-boot:run // 어플리케이션 실행

   http://localhost:8080 접속 후 실행 확인
4. **프론트(React)와 함께 실행   

### 2.2. DB 스키마 및 기초데이터 백업 파일

1. DB 스키마 파일 링크 : [https://github.com/HHS-1/clush-todo-app-backend/blob/main/db-schema.sql](https://github.com/HHS-1/clush-todo-app-backend/blob/main/schema_backup.sql)
2. 기초데이터 백업 파일 링크 : [https://github.com/HHS-1/clush-todo-app-backend/blob/main/db-initial.sql](https://github.com/HHS-1/clush-todo-app-backend/blob/main/initial_backup.sql)



## 3. 주력으로 사용한 라이브러리와 그 이유
**1. Spring Security, JJWT, Redis**
- 사용자 인증 / 인가를 통한 보안 강화
- 인증된 사용자에 한에 SecurityContextHolder에 인증정보를 저장하여 사용자의 아이디(id)를 가져오는 Util코드 작성 -> DB 조회 시 유용하게 사용함.
- AccessToken은 HTTPOnly 쿠키에 저장하고, RefreshToken은 Redis에 저장하여 클라이언트가 토큰에 접근할 수 없도록 함.

**2. Springdoc**
- RESTApi 명세서 작성을 위해 사용.

**3. Junit**
- 테스트 코드 작성

**4. Lombok**
- getter,setter, 생성자 자동 주입을 위함.

**5. javax.mail**
- 캘린더 공유를 위한 이메일 초대 방식 구현을 위함.



## 4. RESTApi
http://34.64.133.198:8081/swagger-ui/index.html 또는
http://localhost:8081/swagger-ui/index.html



## 5. 테스트 케이스 - SharedCalendarControllerTest 테스트 설명

### 1. testGetSharedCalendar
- **목적**: `/shared` 엔드포인트가 정상적으로 데이터를 반환하는지 확인합니다.

- **테스트 과정**:
  1. `SharedCalendarService`가 두 개의 캘린더 데이터(`Calendar 1`, `Calendar 2`)를 포함한 리스트를 반환하도록 설정합니다.
  2. `MockMvc`를 사용해 `/shared` 경로로 GET 요청을 보냅니다.
  3. 다음 사항을 검증합니다:
     - HTTP 상태 코드가 **200 OK**인지 확인합니다.
     - 응답의 콘텐츠 타입이 `application/json`인지 확인합니다.
     - 응답 JSON 배열의 첫 번째 항목 이름이 "Calendar 1"이고, 두 번째 항목 이름이 "Calendar 2"인지 확인합니다.

- **기대 결과**: `/shared` 경로 요청 시, 예상한 캘린더 리스트가 올바르게 반환됩니다.

---

### 2. testGetSharedCalendarNoContent
- **목적**: `/shared` 엔드포인트가 데이터가 없을 경우 적절한 응답을 반환하는지 확인합니다.

- **테스트 과정**:
  1. `SharedCalendarService`가 데이터가 없음을 나타내는 HTTP 상태 코드 **204 No Content**를 반환하도록 설정합니다.
  2. `MockMvc`를 사용해 `/shared` 경로로 GET 요청을 보냅니다.
  3. 다음 사항을 검증합니다:
     - HTTP 상태 코드가 **204 No Content**인지 확인합니다.

- **기대 결과**: `/shared` 경로 요청 시, 데이터가 없을 경우 204 상태 코드를 반환합니다.

## 6. 시연영상
https://github.com/HHS-1/clush-todo-app-backend/raw/refs/heads/main/%EA%B3%B5%EC%9C%A0%EC%BA%98%EB%A6%B0%EB%8D%94%20%EC%8B%9C%EC%97%B0%EC%97%B0%EC%83%81.mp4

