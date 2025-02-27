import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import com.google.gson.*;

class Monitoring_news {
    private final Logger logger;
    private final HttpClient client;

    public Monitoring_news() {
        logger = Logger.getLogger(Monitoring_news.class.getName());
        client = HttpClient.newHttpClient();
        logger.info("✅ Monitoring_news 객체 생성");
    }

    // 뉴스 검색 및 HTML 변환
    public String getNews(String keyword, int display, int start, SortType sort) {
        // 환경 변수 확인 로그 추가
        logger.info("🔍 KEYWORD: " + keyword);

        if (keyword == null || keyword.isEmpty()) {
            throw new IllegalArgumentException("🚨 환경 변수 'KEYWORD'가 설정되지 않았습니다.");
        }

        String url = "https://openapi.naver.com/v1/search/news.json";
        String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
        String params = "query=%s&display=%d&start=%d&sort=%s".formatted(
                encodedKeyword, display, start, sort.value
        );

        String clientId = System.getenv("NAVER_CLIENT_ID");
        String clientSecret = System.getenv("NAVER_CLIENT_SECRET");

        logger.info("🔍 NAVER_CLIENT_ID: " + clientId);
        logger.info("🔍 NAVER_CLIENT_SECRET: " + clientSecret);

        if (clientId == null || clientSecret == null) {
            throw new IllegalArgumentException("🚨 Naver API 인증 정보(NAVER_CLIENT_ID, NAVER_CLIENT_SECRET)가 설정되지 않았습니다.");
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "?" + params))
                .GET()
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            logger.info("✅ HTTP 상태 코드: " + response.statusCode());

            if (response.statusCode() == 200) {
                return parseNewsToHtml(response.body()); // HTML 변환 결과 반환
            } else {
                logger.warning("❌ API 요청 실패: " + response.body());
                return "<h2>❌ 뉴스 검색 실패</h2><p>API 요청이 실패했습니다.</p>";
            }

        } catch (IOException | InterruptedException e) {
            logger.warning("❌ 오류 발생: " + e.getMessage());
            return "<h2>❌ 뉴스 검색 오류</h2><p>" + e.getMessage() + "</p>";
        }
    }

    // JSON 응답을 파싱하고 HTML 형식으로 변환하여 반환
    private String parseNewsToHtml(String jsonResponse) {
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonArray items = jsonObject.getAsJsonArray("items");

        StringBuilder newsHtml = new StringBuilder();
        newsHtml.append("<h2>🔹 자동 뉴스 모니터링 결과</h2>");

        for (JsonElement element : items) {
            JsonObject newsItem = element.getAsJsonObject();
            String title = newsItem.get("title").getAsString().replaceAll("<.*?>", ""); // HTML 태그 제거
            String link = newsItem.get("link").getAsString();
            String description = newsItem.get("description").getAsString().replaceAll("<.*?>", ""); // HTML 태그 제거
            String pubDate = newsItem.get("pubDate").getAsString();

            newsHtml.append("<div style='border-bottom:1px solid #ddd; padding:10px;'>");
            newsHtml.append("<h3>📌 제목: <a href='" + link + "'>" + title + "</a></h3>");
            newsHtml.append("<p>📝 설명: " + description + "</p>");
            newsHtml.append("<p>📅 발행일: " + pubDate + "</p>");
            newsHtml.append("</div>");
        }

        return newsHtml.toString();
    }
}
