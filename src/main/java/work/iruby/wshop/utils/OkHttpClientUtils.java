package work.iruby.wshop.utils;

import com.alibaba.fastjson.JSON;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class OkHttpClientUtils {

    private OkHttpClientUtils() {
    }

    private static final OkHttpClient client = new OkHttpClient();

    private static final MediaType MEDIA_TYPE_JSON = MediaType.get("application/json; charset=utf-8");

    public static String getCookie(Boolean isGet, String url, String json, Map<String, String> addHeads, String[] RemoveHeads) throws IOException {
        RequestBody body = RequestBody.create(json, MEDIA_TYPE_JSON);
        Request.Builder builder = new Request.Builder().url(url);
        requestBuilderHandle(isGet, addHeads, RemoveHeads, body, builder);
        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            return response.header("Set-Cookie");
        }
    }

    public static String getBody(Boolean isGet, String url, String json, Map<String, String> addHeads, String[] RemoveHeads) throws IOException {
        RequestBody body = RequestBody.create(json, MEDIA_TYPE_JSON);
        Request.Builder builder = new Request.Builder().url(url);
        requestBuilderHandle(isGet, addHeads, RemoveHeads, body, builder);
        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            return Objects.requireNonNull(response.body()).string();
        }
    }

    private static void requestBuilderHandle(Boolean isGet, Map<String, String> addHeads, String[] RemoveHeads, RequestBody body, Request.Builder builder) {
        if (!isGet) {
            builder.post(body);
        }
        if (addHeads != null) {
            for (Map.Entry<String, String> entry : addHeads.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        if (RemoveHeads != null) {
            for (String removeHead : RemoveHeads) {
                builder.removeHeader(removeHead);
            }
        }
    }

    public static String getCookie(Boolean isGet, String url, Object obj, Map<String, String> addHeads, String[] RemoveHeads) throws IOException {
        return getCookie(isGet, url, JSON.toJSONString(obj), addHeads, RemoveHeads);
    }

    public static String getBody(Boolean isGet, String url, Object obj, Map<String, String> addHeads, String[] RemoveHeads) throws IOException {
        return getBody(isGet, url, JSON.toJSONString(obj), addHeads, RemoveHeads);
    }
}
