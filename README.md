# Kotlin GPT Demo

1. 과제를 어떻게 분석 하셨나요?
```
요구사항을 보고 우선 도메인 모델을 설계하고 내부 상태와 필요한 메서드 그리고 제한사항들에 대해 체크하고 이를 도메인 모델에서 미리 구현합니다.
그리고 기존 아키텍처를 준수하는 방식으로 의존성 주입에 대한 방향성을 설계한 후에 필요한 의존성 방향을 탐색하여 나중에 테스트에 문제가 발생하지 않도록 개발 순서를 정합니다.
```

2. 과제의 진행함에 있어 AI 를 어떻게 활용 하셨나요? 어떤 어려움이 있었나요?
```
과제를 진행함에 있어서 GPT 연동 부분을 물어보시는건지, 아니면 AI와 병행해서 개발하는 도구로서 물어보시는지 약간 애매한데, 
우선 도구로서 답변드리자면, 기존에서 개발을 하면서 최적화해나간 아키텍처 구조를 그대로 사용할순 없어서 내부 도메인내용을 제거하고 아키텍처와 컨벤션을 구축하는 정도로만 사용합니다.
그 이외에 비즈니스로직이나 그런 부분은 도구로서 사용해봤었지만 토큰이 부족해지면 이상하게 작성하는 경우가 있어서 메서드 단위로만 제작합니다.

그리고 이 작업을 하면서 GPT API 연동에 시간이 없어서 그 부분은 인터페이스로 모킹할수 있는 구조로 제작에 들어갔습니다.
```


3. 지원자가 구현하기 가장 어려웠던 1개 이상의 기능을 설명 해주세요.
```
주요 기능이었던 GPT로 스레드 단위로 대화를 저장하고 삭제하는 영역이었습니다.
우선 스레드와 대화가 엮여있고, 그리고 30분이라는 시간텀에 대해 활성화 스레드를 구분하여 처리하고 이를 대화와 엮는 과정에서
구현한 부분중에선 가장 고민을 많이 햇던 부분입니다. 
```

-----


## 🚀 주요 기능

### 1. 사용자 관리 및 인증
- **회원가입**: 이메일, 패스워드, 이름을 통한 사용자 등록
- **로그인**: JWT 토큰 기반 인증
- **권한 관리**: 멤버(`member`)와 관리자(`admin`) 역할 구분

### 2. 대화(채팅) 관리
- **대화 생성**: OpenAI GPT API를 통한 AI 응답 생성
- **스레드 관리**: 30분 간격으로 자동 스레드 생성/유지
- **대화 목록 조회**: 스레드별 그룹화된 대화 히스토리
- **스레드 삭제**: 사용자별 스레드 관리

### 3. 피드백 시스템
- **피드백 생성**: 대화에 대한 긍정/부정 피드백
- **피드백 조회**: 사용자별 피드백 목록
- **상태 관리**: `pending`, `resolved` 상태 관리

### 4. 분석 및 보고서
- **사용자 활동 기록**: 일일 회원가입, 로그인, 대화 생성 통계
- **보고서 생성**: CSV 형태의 사용자 대화 보고서

## �� 환경 설정

### 필수 요구사항
- Java 17
- PostgreSQL
- OpenAI API 키

### 환경 변수 설정
```bash
export OPENAI_API_KEY="your-openai-api-key"
```

### 데이터베이스 설정
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/postgres
    username: postgres
    password: postgres
```

## 🏃‍♂️ 실행 방법

### 1. 프로젝트 빌드
```bash
./gradlew build
```

### 2. 애플리케이션 실행
```bash
./gradlew bootRun
```

### 3. 테스트 실행
```bash
./gradlew test
```

## 📡 API 엔드포인트

### 인증 API
- `POST /api/v1/auth/signup` - 회원가입
- `POST /api/v1/auth/login` - 로그인

### 채팅 API
- `POST /api/v1/chat` - 대화 생성
- `GET /api/v1/chat` - 대화 목록 조회 (페이지네이션 지원)
- `DELETE /api/v1/chat/thread/{threadId}` - 스레드 삭제

## 🔐 보안

- **JWT 기반 인증**: 모든 API 요청에 JWT 토큰 필요
- **역할 기반 접근 제어**: 관리자/일반 사용자 권한 분리
- **입력 검증**: Bean Validation을 통한 요청 데이터 검증

## 🏛️ 아키텍처

이 프로젝트는 **Clean Architecture** 패턴을 따르며 다음과 같은 계층 구조를 가집니다:

- **Presentation Layer**: REST API 컨트롤러
- **Application Layer**: 비즈니스 로직 및 UseCase
- **Domain Layer**: 핵심 비즈니스 모델 및 규칙
- **Infrastructure Layer**: 외부 시스템 연동 (DB, OpenAI API)

## �� 데이터베이스 스키마

### 주요 엔티티
- **User**: 사용자 정보 (이메일, 패스워드, 이름, 역할)
- **Thread**: 대화 스레드 (사용자별 대화 그룹)
- **Chat**: 개별 대화 (질문, 답변)
- **Feedback**: 피드백 정보

## �� 테스트

프로젝트는 JUnit 5를 사용한 테스트를 지원합니다:
- 단위 테스트
- 통합 테스트
- API 테스트

## �� 개발 가이드

### 코드 컨벤션
- Kotlin 코딩 컨벤션 준수
- Clean Architecture 원칙 적용
- SOLID 원칙 준수

### 새로운 기능 추가
1. Domain 모델 정의
2. Port 인터페이스 작성
3. Application 서비스 구현
4. Infrastructure 어댑터 구현
5. Presentation 컨트롤러 작성

## �� 기여 방법

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다.

## �� 문의

프로젝트에 대한 문의사항이 있으시면 이슈를 생성해 주세요.