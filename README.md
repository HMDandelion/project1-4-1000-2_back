# 一瀉千里

![일사천리 로고2](https://github.com/HMDandelion/project1-4-1000-2_back/assets/154950075/9f5ca9da-10e6-483e-8867-6387d155c029)

## 프로젝트 소개

- 일사천리는 생산 및 재고관리 분야에 특화된 ERP 시스템 입니다.
- 모든 제조 및 유통업계 기업에게 도움을 드리기 위해 최선을 다하고 있습니다.

## 팀원 소개

|                                                            박예원                                                            |                                                           오나윤                                                            |                                                            조한결                                                            |                                                            신승재                                                            |                                                            고동환                                                            |    
|:-------------------------------------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------------------------------:| 
| ![154950167](https://github.com/HMDandelion/project1-4-1000-2_back/assets/154950075/dd5715e7-9c8d-415f-b137-7f58cb7c36be) | ![99164178](https://github.com/HMDandelion/project1-4-1000-2_back/assets/154950075/a517679d-b114-437b-940d-9e75ccbaf905) | ![154950168](https://github.com/HMDandelion/project1-4-1000-2_back/assets/154950075/151fe6a1-1dc5-416d-a4d8-b5a4f6648535) | ![154950075](https://github.com/HMDandelion/project1-4-1000-2_back/assets/154950075/8c16e8ec-733c-4fb4-b7ad-c220f6bd8ae8) | ![111329365](https://github.com/HMDandelion/project1-4-1000-2_back/assets/154950075/53098962-b3e6-4146-9019-42a8a80b8726) |
|                                                          계정, 주문                                                           |                                                          생산 계획                                                           |                                                            원자재                                                            |                                                          생산, 품질                                                           |                                                          재고, 출고                                                           |

## 개발 환경

![Git](https://img.shields.io/badge/Git-F05032?style=flat-square&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=github&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ_IDEA-000000?style=flat-square&logo=intellij-idea&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-000000?style=flat-square&logo=notion&logoColor=white)
![Discord](https://img.shields.io/badge/Discord-5865F2?style=flat-square&logo=discord&logoColor=white)
![Figma](https://img.shields.io/badge/Figma-F24E1E?style=flat-square&logo=figma&logoColor=white)

## 주요 기능

### 계정

- `Spring Security`를 활용한 로그인, 회원가입

### 주문

- 거래처 관리
- 주문 상태 추적
- 반품 및 교환 관리
- 비용 분석
- 요청 및 주문서 발행

### 생산 기획

- 생산 계획 및 일정 관리
- 작업 지시서 생성 및 관리

### 원자재

- 구매 요청 및 주문서 발행
- 공급업체 관리
- 입고 및 출고(반품) 관리
- 구매 비용 분석

### 생산

- 샐산 공정 모니터링
- 품질관리 및 불량품 처리
- 생산 과정에서 발생한 불량률 모니터링

### 재고

- 재고 수준 모니터링
- 재고 이동 및 추적
- 재고 보충 및 다오 주문 생성

### 물류

- 창고 관리
- 출고 관리
- 출고 및 재고 관리
- 운송 과정 관리

## ERD

![787b23c5-5ad6-4c4e-aa08-65cd668b725d](https://github.com/HMDandelion/project1-4-1000-2_back/assets/154950075/7521fed5-b37c-4fb7-94e6-af142573bf95)

## 규칙

### Pull Request

- 변경한 내용은 간결하게 설명
- 해결한 이슈가 있다면 링크 첨부
- 변경한 내용을 검토하고 테스트 방법 설명
- 필요하다면 변경 내용을 시각적으로 확인할 수 있게 스크린샷 첨부

### 기능 개발

- 개발할 기능에 대한 명확하고 간결한 설명
- 이 기능이 왜 필요한지, 어떤 문제를 해결할 수 있는지에 대한 설명
- 기술의 요구 사항을 명시
- 기능 구현에 필요한 기술적 사양이나 고려사항을 명시  (예: API 엔드포인트, 데이터베이스 스키마 변경 등)
- 기능 개발에 참고할 만한 자료(링크, 문서 등)를 추가
- 기능 개발에 필요한 추가 정보나 맥락 작성

### 버그 발생

- 버그에 대한 명확하고 간결한 설명 작성
- 예상했던 동작에 대한 명확하고 간결한 설명 작성
- 버그 관련 로그나 오류 메시지를 추가
- 바그 발생 이유와 해결 방법을 작성

### 기타 이슈

- 이슈의 유형을 선택 하고 명확하고 간결한 설명을 작성
- 이슈를 통해 달성하고자 하는 목표 설명
- 필요한 경우 추가 정보 작성 (스크린샷, 링크, 코드 등)

### Github

- 이슈(issue) 등록
- 개발할 기능을 이슈에 발행
- 발생 시 마일스톤, 프로젝트, 라벨 등을 추가

### 브랜치(branch) 생성 규칙

- { 라벨 }/{ 이슈번호 }-{ 이슈설명 } (ex. settings/1-start-project)
- 브랜치의 커밋 메시지(Commit Message)
- 키워드를 접두사(prefix)로 추가 후 내용을 입력

### 메시지 형식

- { PREFIXKEYWORD }: { COMMIT_MSG } (ex. feat: 로그인 개발)

### 접두사 목록

- feat : 기능 추가
- refactor : 동일 기능이나 코드 개선 시
- fix : 오타, 공백, 마크다운 파일 수정, reafactor 붙이기 애매할 때
- test  : 테스트 관련 사항 모두
- chore : 설정, 의존성, 파일 및 디렉터리 구조 변경/삭제 등
- 개발 완료 후에는 풀 리퀘스트(Pull Request) 등록
- #{이슈번호} {기능_설명} (ex. #1 기본 프로젝트 설정)
- 풀 리퀘스트 리뷰 및 병합(Merge)
- 리뷰 후에는 Squash Merge

## 개선목표

### 작성 예정

