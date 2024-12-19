# 프로젝트명: [ClushTodoApp - backend]

## 1. 자신이 개발한 앱에 대한 설명

**ClushTodoApp**은 개인적인 용도로도 사용 가능하며, 팀 또는 그룹 등 공용으로도 활용할 수 있는 다목적 캘린더 웹 애플리케이션입니다. 사용자는 날짜별로 할 일을 추가하고 관리할 수 있으며, 직관적인 캘린더 UI를 통해 일정을 한 눈에 쉽게 확인할 수 있습니다.

또한, 가입한 사용자 간에 캘린더를 공유할 수 있는 기능을 제공하여, 공유된 캘린더를 바탕으로 일정을 손쉽게 관리하고 협업할 수 있습니다. 개인의 일정뿐만 아니라, 팀원들과의 일정 조율이 필요한 경우에도 유용한 기능을 제공합니다.

**🚩프론트 및 백엔드 모두 배포를 완료하였습니다.
http://34.64.133.198/
주소로 이동해 확인하실 수 있습니다.🚩**

**주요 기능**:
- **ToDo**: 제목, 내용, 날짜, 우선순위, 진행상태로 이루어진 ToDo데이터를 생성, 조회, 수정할 수 있습니다. 
- **캘린더(개인)**: ToDo를 생성함에 따라 캘린더에 표시가 되며, 우선순위에 따라 색이 다르게 표시됩니다.
- **캘린더(공유)**: 이메일을 통해 가입자를 초대하며, 공유 작업자와 함께 캘린더의 날짜 칸을 클릭하여 작업을 추가할 수 있습니다.

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

1. DB 스키마 파일 링크 : https://github.com/HHS-1/clush-todo-app-backend/blob/main/db-schema.sql
2. 기초데이터 백업 파일 링크 : https://github.com/HHS-1/clush-todo-app-backend/blob/main/db-initial.sql

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


