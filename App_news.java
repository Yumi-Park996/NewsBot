public class App_news {
    public static void main(String[] args) {
        Monitoring_news monitoring = new Monitoring_news();
        String htmlContent = monitoring.getNews(System.getenv("KEYWORD"), 30, 1, SortType.sim);
        System.out.println(htmlContent); // HTML을 출력하여 GitHub Actions에서 사용
    }
}
