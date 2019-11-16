package con.norman.crawler;

public class Norman {
    public static void main(String[] args) throws Exception {
//        HttpClientHelper httpClientHelper = new HttpClientHelper();
//        httpClientHelper.get();
        JsoupHelper jsoupHelper = new JsoupHelper();
        jsoupHelper.parseUrl();

    }
}
