package work.iruby.wshop.common.utils;

import com.alibaba.fastjson.JSON;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OkHttpClientUtils {

    private OkHttpClientUtils() {
    }

    private static final OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new LoggingInterceptor()).build();

    private static final MediaType MEDIA_TYPE_JSON = MediaType.get("application/json; charset=utf-8");

    public static String getCookie(String method, String url, String json, Map<String, String> heads) throws IOException {
        RequestBody body = RequestBody.create(json, MEDIA_TYPE_JSON);
        Request.Builder builder = new Request.Builder().url(url);
        requestBuilderHandle(method, heads, body, builder);
        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            return response.header("Set-Cookie");
        }
    }

    @SuppressFBWarnings(value = "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE")
    public static String getBody(String method, String url, String json, Map<String, String> heads) throws IOException {
        RequestBody body = RequestBody.create(json, MEDIA_TYPE_JSON);
        Request.Builder builder = new Request.Builder().url(url);

        requestBuilderHandle(method, heads, body, builder);
        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            return Objects.requireNonNull(response.body()).string();
        }
    }

    private static void requestBuilderHandle(String method, Map<String, String> heads, RequestBody body, Request.Builder builder) {
        if (method == null) {
            builder.get();
        } else {
            switch (method.toLowerCase()) {
                case "post":
                    builder.post(body);
                    break;
                case "delete":
                    builder.delete(body);
                    break;
                case "patch":
                    builder.patch(body);
                    break;
                case "head":
                    builder.head();
                    break;
                case "put":
                    builder.put(body);
                    break;
                default:
                    builder.get();
                    break;
            }
        }

        if (heads != null && heads.size() != 0) {
            for (Map.Entry<String, String> entry : heads.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    public static String getCookie(String method, String url, Object obj, Map<String, String> heads) throws IOException {
        return getCookie(method, url, JSON.toJSONString(obj), heads);
    }

    public static String getBody(String method, String url, Object obj) throws IOException {
        return getBody(method, url, obj, (Map<String, String>) null);
    }

    public static String getBody(String method, String url, String cookie) throws IOException {
        return getBody(method, url, null, cookie);
    }

    public static String getBody(String method, String url, Object obj, Map<String, String> heads) throws IOException {
        return getBody(method, url, JSON.toJSONString(obj), heads);
    }

    public static String getBody(String method, String url, Object obj, String cookie) throws IOException {
        Map<String, String> Heads = new HashMap<>();
        Heads.put("Cookie", cookie);
        return getBody(method, url, obj, Heads);
    }
}
