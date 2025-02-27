import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import com.google.gson.*;

class Monitoring_news {
    private final Logger logger;
    private final HttpClient client;

    public Monitoring_news() {
        logger = Logger.getLogger(Monitoring_news.class.getName());
        client = HttpClient.newHttpClient();
        logger.info("Monitoring_news 객체 생성");
    }

    // 뉴스 검색 및 output.txt 저장
    public void getNews(String keyword, int display, int start, SortType sort) {
        String url = "https://openapi.naver.com/v1/search/news.json";
        String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
        String params = "query=%s&display=%d&start=%d&sort=%s".formatted(
                encodedKeyword, display, start, sort.value
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "?" + params))
                .GET()
                .header("X-Naver-Client-Id", System.getenv("NAVER_CLIENT_ID"))
                .header("X-Naver-Client-Secret", System.getenv("NAVER_CLIENT_SECRET"))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 로그 출력
            logger.info("HTTP 상태 코드: " + response.statusCode());

            if (response.statusCode() == 200) {
                // JSON 응답 파싱 및 파일 저장
                parseAndSaveNews(response.body());
            } else {
                logger.warning("API 요청 실패: " + response.body());
            }

        } catch (IOException | InterruptedException e) {
            logger.warning("오류 발생: " + e.getMessage());
        }
    }

    // JSON 응답을 파싱하고 output.txt로 저장
    private void parseAndSaveNews(String jsonResponse) {
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonArray items = jsonObject.getAsJsonArray("items");

        StringBuilder newsContent = new StringBuilder();
        newsContent.append("🔹 [자동 뉴스 모니터링 결과]\n\n");

        for (JsonElement element : items) {
            JsonObject newsItem = element.getAsJsonObject();
            String title = newsItem.get("title").getAsString().replaceAll("<.*?>", ""); // HTML 태그 제거
            String link = newsItem.get("link").getAsString();
            String description = newsItem.get("description").getAsString().replaceAll("<.*?>", ""); // HTML 태그 제거
            String pubDate = newsItem.get("pubDate").getAsString();

            newsContent.append("📌 제목: ").append(title).append("\n");
            newsContent.append("🔗 링크: ").append(link).append("\n");
            newsContent.append("📝 설명: ").append(description).append("\n");
            newsContent.append("📅 발행일: ").append(pubDate).append("\n");
            newsContent.append("-------------------------------------------------\n");
        }

        // 파일 저장
        try {
            Path outputPath = Path.of("output.txt");
            Files.writeString(outputPath, newsContent.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            logger.info("✅ output.txt 파일 저장 완료");
        } catch (IOException e) {
            logger.warning("파일 저장 오류: " + e.getMessage());
        }
    }
}
