package com.example.local.activity;

import java.io.*;

import android.os.AsyncTask;
import android.util.Log;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class LoginLocal extends AsyncTask<Void, Void, String> {
    public JSONObject reader;
    public String page;
    public static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
    public SharedPreferences prefs;

    private String login;
    private String pass;
    private StringBuilder request_body = new StringBuilder();
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(Void... params) {
        RequestBody body = RequestBody.create(JSON, request_body.toString());
        Request request = new Request.Builder()
                .url("https://program.yousystem.com.ua/frontend/api/user/login")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            page = response.body().string();
            Log.i("PAGE", "page from try" + page);
            return page;

        } catch (IOException e) {
            Log.i("ERROR", "1" + e.toString());
            e.printStackTrace();
            return null;
        }

    }

    @Override
    protected void onPostExecute(String page) {

        try {
            reader = new JSONObject(page);
            String passs = reader.getString("response");
            Log.i("PAGE", "response " + passs);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("PAGE", "page from post" + page);

    }

    LoginLocal(String login, String pass) {
        // "{username: \"1700000095226\", pass: \"andriybohak\", lng: \"ua\", prgCode: \"prg1\"}";
        this.login = login;
        this.pass = pass;
        this.request_body.append("{username: \"");
        this.request_body.append(login);
        this.request_body.append("\", pass: \"");
        this.request_body.append(pass);
        this.request_body.append("\", lng: \"ua\", prgCode: \"prg1\"}\"");
        Log.i("", "page constructor" + request_body.toString());

    }
}

