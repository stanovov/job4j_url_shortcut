package ru.job4j.url.model;

public class UrlDTO {

    private String url;

    private int total;

    public static UrlDTO of(String url, int total) {
        UrlDTO urlDTO = new UrlDTO();
        urlDTO.setUrl(url);
        urlDTO.setTotal(total);
        return urlDTO;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
