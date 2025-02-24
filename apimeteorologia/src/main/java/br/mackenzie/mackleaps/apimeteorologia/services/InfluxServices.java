package br.mackenzie.mackleaps.apimeteorologia.services;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InfluxServices {
    private static final String URL_CREATE = "http://localhost:8086/api/v2/buckets";
    private static final String URL_WRITE = "http://localhost:8086/api/v2/write";
    private static final String TOKEN = "1ZbyWcE2dSU0yVNBX1P1oBM7v3_6ZzL918BkHGEG3OQWMOWVqMV3qR3g5q4oyMRWYOTSmi53DQJYUyQmWmEkGA==";
    private static final String ORG_ID = "9e22eac596a6fe35";
    private static final String BUCKET = "test_bucket";
    private OkHttpClient client;

    public InfluxServices() {
        this.client = new OkHttpClient();
    }

    public void CreateBucket(String json) throws IOException {
        String createBucketPayload = String.format("{\"name\": \"%s\", \"orgID\": \"%s\", \"retentionRules\": [%s]}", 
        BUCKET, ORG_ID, json);

        String createBucketResponse = sendPostRequest(URL_CREATE, createBucketPayload);
        System.out.println("Create Bucket Response: " + createBucketResponse);
    }

    public void WriteOnBucket(String json) throws IOException {
        String writeBucketResponse = sendPostRequest(URL_WRITE + "?org=" + ORG_ID + "&bucket=" + BUCKET + "&precision=s", json);
        System.out.println("Write Bucket Response: " + writeBucketResponse);
    }

    private String sendPostRequest(String url, String jsonPayload) throws IOException {
        RequestBody body = RequestBody.create(jsonPayload, MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Authorization", "Token " + TOKEN)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Unexpected code " + response);
            }
        }
    }
}
