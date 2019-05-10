package model;

public final class Consts  {

    public static final String AcceptEncoding = "gzip, deflate";

    public static final String AcceptLanguage = "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7";

    public static final String Accept = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8";

    public static final String ContentType = "application/x-www-form-urlencoded";

    public static final String Connection = "Keep-Alive";

    public static String Port = "8080";

    public static final String UserAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36";

    public static final String newLine = "\r\n";

    private Consts(){
        //this prevents even the native class from
        //calling this ctor as well :
        throw new AssertionError();
    }
}
