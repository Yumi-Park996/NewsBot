# NewsBot

**Hi! I'm NewsBot. Nice to meet you :)**
<br>
**Yumi Park generated me on 2025.02.27.**
<br>
**NewsBot can notify you about something via email every 30 minutes.**
<br>

![image](https://github.com/user-attachments/assets/2faa441f-f4dc-4052-a59f-64b22a5626e3)

<br>

## 📧 NewsBot Explanation (English Version)

**NewsBot** is a **Java-based application** developed by **Yumi Park** on **February 27, 2025**.  
This application fetches **news articles** from **Naver News API** based on a **specific keyword**, and **sends them via email every 09:00** using **Brevo** (formerly known as Sendinblue). It helps users stay up-to-date with the latest news on topics they care about.

### 🔧 Key Features & Components

1. **App_news.java**  
   This is the **main entry point** of the application.  
   - It reads the keyword from the environment variable `KEYWORD`.
   - It initializes the `Monitoring_news` component.
   - It **retrieves, formats, and sends the news articles via email** every 09:00.
   - News articles are converted into **HTML format** for better readability in email.

2. **Monitoring_news.java**  
   This component handles:  
   - **Connecting to Naver News API** to fetch articles related to the specified keyword.
   - **Parsing the JSON response** and extracting article details (title, link, description, etc.).
   - **Formatting articles into HTML** suitable for email body.
   - **Sending emails using Brevo's SMTP service**, with configurations (SMTP host, port, credentials) provided via environment variables.

3. **SortType.java**  
   Defines the **sorting options** for news retrieval:  
   - `sim` – Sort by relevance (similarity).
   - `date` – Sort by publication date.

---

### 📬 How News Delivery Works

- The keyword is dynamically set using the **`KEYWORD` environment variable**.
- Every **30 minutes**, the app runs automatically to:  
    1. Fetch the latest news.
    2. Format the articles into an **HTML email**.
    3. Send the email using **Brevo**.
- This **scheduled delivery** keeps users informed without manual effort.

---

## 📧 NewsBot 설명 (한글 버전)

**NewsBot**은 **2025년 2월 27일 Yumi Park**이 개발한 **Java 기반 애플리케이션**입니다.  
이 애플리케이션은 **네이버 뉴스 API**에서 특정 키워드와 관련된 뉴스를 검색한 후, **Brevo**(구 Sendinblue)를 통해 **매일 오전 9시마다 이메일로 전송**하는 기능을 제공합니다. 사용자는 관심 있는 주제의 최신 뉴스를 실시간으로 받아볼 수 있습니다.

### 🔧 주요 기능 및 구성 요소

1. **App_news.java**  
   애플리케이션의 **메인 실행 파일**로,  
   - 환경 변수 `KEYWORD`에서 키워드를 읽어옵니다.
   - `Monitoring_news` 객체를 초기화합니다.
   - 30분마다 키워드와 관련된 뉴스를 검색하고, **이메일로 전송**합니다.
   - 뉴스는 **HTML 형식**으로 변환되어 이메일 본문에 삽입됩니다.

2. **Monitoring_news.java**  
   이 구성 요소는 다음을 담당합니다:  
   - **네이버 뉴스 API**와 연결해 키워드와 관련된 뉴스 기사를 검색.
   - **JSON 응답을 파싱**해 기사 제목, 링크, 설명 등의 정보를 추출.
   - 뉴스를 **이메일 본문에 적합한 HTML 형식**으로 변환.
   - Brevo의 **SMTP 서비스**를 이용해 이메일 전송 (SMTP 호스트, 포트, 계정 정보는 환경 변수로 설정).

3. **SortType.java**  
   뉴스 검색 시 사용할 수 있는 **정렬 옵션**을 정의합니다:  
   - `sim` – 유사도(관련성) 순 정렬.
   - `date` – 최신순 정렬.

---

### 📬 뉴스 전송 프로세스

- 관심 키워드는 **`KEYWORD` 환경 변수**로 설정.
- 애플리케이션은 **매일 오전 9시마다 자동 실행**되어:  
    1. 최신 뉴스 검색.
    2. 검색된 뉴스를 **HTML 이메일**로 변환.
    3. **Brevo**를 통해 이메일 전송.
- 이러한 **자동 뉴스 알림 시스템**으로 사용자는 편리하게 최신 뉴스를 받아볼 수 있습니다.
