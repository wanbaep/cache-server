package com.wanbaep;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private String method;
    private String uri;
    private String httpVersion;
    private Map<String, String> header = new HashMap<>();

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(String key, String value) {
        this.header.put(key, value);
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }
}
