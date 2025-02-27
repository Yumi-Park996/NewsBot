import java.net.URI;
import java.net.http.*;
import java.util.logging.Logger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

class Monitoring_news {
    private final Logger logger;

    public Monitoring_news() {
        logger = Logger.getLogger(Monitoring_news.class.getName());
        logger.info("Monitoring_news 객체 생성");
    }

    // 1. 검색어를 통해서 최근 10개의 뉴스를 받아오자
    public void getNews(String keyword, int display, int start, SortType sort) {
        String url = "https://openapi.naver.com/v1/search/news.json";
        String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
        String params = "query=%s&display=%d&start=%d&sort=%s".formatted(
                encodedKeyword, display, start, sort.value
        );
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "?" + params))
                .GET()
                .header("X-Naver-Client-id", System.getenv("NAVER_CLIENT_ID"))
                .header("X-Naver-Client-Secret", System.getenv("NAVER_CLIENT_SECRET"))
                .build();
        try {
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            logger.info(Integer.toString(response.statusCode()));
            logger.info(response.body());
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }
}


