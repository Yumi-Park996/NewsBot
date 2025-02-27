public class App_news {
    public static void main(String[] args) {
        Monitoring_news monitoring = new Monitoring_news();
        monitoring.getNews(System.getenv("KEYWORD"), 10, 1, SortType.date);
    }
}