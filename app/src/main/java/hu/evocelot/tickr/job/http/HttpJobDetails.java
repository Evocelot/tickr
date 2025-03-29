package hu.evocelot.tickr.job.http;

/**
 * Represents the HTTP details for a task, including the HTTP method,
 * the target URL, and an optional request body.
 * 
 * @author mark.danisovszky
 */
public class HttpJobDetails {
    private String method;
    private String url;
    private String body;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
