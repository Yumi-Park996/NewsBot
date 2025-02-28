# NewsBot

**Hi! I'm NewsBot. Nice to meet you :)**
<br>
**Yumi Park generated me on 2025.02.27.**
<br>
**NewsBot can notify you about something via email every 30 minutes.**
<br>

## Directory Information
```
src/
├── AppNews.java                   // 메인 실행 파일
├── config/
│   ├── ConfigLoader.java         // 환경변수, 설정 로드
├── model/
│   ├── NewsItem.java              // 뉴스 데이터 모델
│   ├── SortType.java               // 정렬 타입 enum
├── service/
│   ├── NewsFetcher.java           // 뉴스 수집 (Naver API 연동)
│   ├── NewsFilter.java            // 신문사 필터링
│   ├── NewsDeduplicator.java      // 중복 제거 (타이틀, 유사도 기반 가능)
│   ├── NewsFormatter.java         // HTML 변환
│   ├── NewsletterSender.java      // Brevo 이메일 전송
│   ├── LLMProcessor.java          // (선택) LLM 활용 요약, 인사이트 생성
├── util/
│   ├── HttpClientHelper.java      // HTTP 요청 공통 유틸
├── web_client/
│   ├── IWebClient.java            // 기존 인터페이스
│   ├── WebClient.java             // 기본 구현체
│   ├── Slack.java                  // (옵션) 슬랙 알림용
├── llm/
│   ├── LLM.java                    // LLM 호출 및 응답 처리
```
![image](https://github.com/user-attachments/assets/2faa441f-f4dc-4052-a59f-64b22a5626e3)
