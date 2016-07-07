package base.designpattern.creational;

/**
 * 建造者模式
 * <p>
 * 案例:创建网络请求
 * Created by yutianran on 16/7/1.
 */
public class BuilderPattern {

    public static void main(String[] args) {
        String url = "http://baidu.com";
        Request request = new Request.Builder()
                .url(url)
                .method("POST")
                .headers("Content-Type:application/json;charset=UTF-8")
                .body("{\"userId\":145}")
                .tag(url)
                .build();
        System.out.println("网络请求:" + request.toString());
    }

    //网络请求类
    public static class Request {
        private String url;
        private String method;
        private String headers;
        private String body;
        private String tag;

        public Request(Builder builder) {
            if (null == builder.url) {
                throw new IllegalArgumentException("url == null");
            }
            this.url = builder.url;
            this.method = builder.method;
            this.headers = builder.headers;
            this.body = builder.body;
            this.tag = builder.tag;
        }

        public String getUrl() {
            return url;
        }

        public String getMethod() {
            return method;
        }

        public String getHeaders() {
            return headers;
        }

        public String getBody() {
            return body;
        }

        public String getTag() {
            return tag;
        }

        @Override
        public String toString() {
            return "Request{" +
                    "url='" + url + '\'' +
                    ", methodB='" + method + '\'' +
                    ", headers='" + headers + '\'' +
                    ", body='" + body + '\'' +
                    ", tag='" + tag + '\'' +
                    '}';
        }

        //建造者
        public static class Builder {
            private String url;
            private String method;
            private String headers;
            private String body;
            private String tag;

            public Builder url(String url) {
                this.url = url;
                return this;
            }

            public Builder method(String method) {
                this.method = method;
                return this;
            }

            public Builder headers(String headers) {
                this.headers = headers;
                return this;
            }

            public Builder body(String body) {
                this.body = body;
                return this;
            }

            public Builder tag(String tag) {
                this.tag = tag;
                return this;
            }

            public Request build() {
                return new Request(this);
            }
        }
    }
}
