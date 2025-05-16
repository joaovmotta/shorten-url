package br.com.shorten_url.model.responses;

public class ShortUrlResponse {

    private String url;

    private String shortUrl;

    public ShortUrlResponse(String url, String shortUrl) {
        this.url = url;
        this.shortUrl = shortUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
