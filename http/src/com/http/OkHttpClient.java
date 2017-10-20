package com.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.util.concurrent.TimeUnit;

public class OkHttpClient
{
    private static final Logger LOG = LogManager.getLogger(OkHttpClient.class);


    public static OkHttpClient getInstance()
    {
        return SingletonHolder.INSTALL;
    }

    private static class SingletonHolder
    {
        private final static OkHttpClient INSTALL = new OkHttpClient();
    }

    private okhttp3.OkHttpClient okHttpClient;

    private OkHttpClient()
    {
        okHttpClient = new okhttp3.OkHttpClient.Builder()
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        return true;
                    }
                })
                .connectTimeout(2000, TimeUnit.MILLISECONDS)
                .readTimeout(1000 * 10, TimeUnit.MILLISECONDS)
                .writeTimeout(1000, TimeUnit.MILLISECONDS)
                .build();
    }

    private static final MediaType CONTENT_TYPE = MediaType.parse("application/json; charset=utf-8");

    public JSONObject post (String host, String service, String business, JSONObject params)
    {
        StringBuilder url = new StringBuilder(host);
        if(host.lastIndexOf("/") <= -1)
            url.append("/");
        String _url = url.append(service).append("/").append(business).toString();

        return post(_url, params);
    }

    public JSONObject post (String url, JSONObject params)
    {
        try
        {
            RequestBody body = RequestBody.create(CONTENT_TYPE, params.toJSONString());

            Request request = new Request.Builder().url(url).post(body).build();

            Response response = okHttpClient.newCall(request).execute();

            if(response != null && response.isSuccessful() && response.body() != null)
            {
                JSONObject jsonObject = JSON.parseObject(response.body().string());
                LOG.info("HTTP Post Response Body: " + jsonObject);
                return jsonObject;
            }
            else
                LOG.error("HTTP Post Unexpected Error", response);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            LOG.error(e);
        }

        return null;
    }

}
