package com.actorn.netcapture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.net.Proxy;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.Objects;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView responseText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        responseText = (TextView) findViewById(R.id.response_text);

        Button button_1 = (Button) findViewById(R.id.no_verify);
        Button button_2 = (Button) findViewById(R.id.cert_verify);
        Button button_3 = (Button) findViewById(R.id.check_proxy);
        Button button_4 = (Button) findViewById(R.id.ssl_pinning);
        Button button_5 = (Button) findViewById(R.id.ssl_pinning2);
        Button button_6 = (Button) findViewById(R.id.ssl_pinning3);
        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
        button_5.setOnClickListener(this);
        button_6.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.no_verify){
            showResponse("Null");
            sendRequestWithOkHttp1();
        }else if(view.getId()==R.id.cert_verify) {
            showResponse("Null");
            sendRequestWithOkHttp2();
        }else if(view.getId()==R.id.check_proxy){
            showResponse("Null");
            sendRequestWithOkHttp3();
        }else if(view.getId()==R.id.ssl_pinning){
            showResponse("Null");
            sendRequestWithOkHttp4();
        }else if(view.getId()==R.id.ssl_pinning2){
            showResponse("Null");
            sendRequestWithOkHttp5();
        }else if(view.getId()==R.id.ssl_pinning3){
            showResponse("Null");
            sendRequestWithOkHttp6();
        }
    }
    private void sendRequestWithOkHttp1(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient mClient = new OkHttpClient().newBuilder().sslSocketFactory(HttpsTrustAllCerts.createSSLSocketFactory(),new HttpsTrustAllCerts()).hostnameVerifier(new HttpsTrustAllCerts.TrustAllHostnameVerifier()).build();
                Request request = new Request.Builder()
                        .url("https://www.baidu.com")
                        .build();
                try {
                    Response response = mClient.newCall(request).execute();
                    String responseData = response.body().string();
                    showResponse(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void sendRequestWithOkHttp2(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://www.baidu.com")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    showResponse(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void sendRequestWithOkHttp3(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient().newBuilder().proxy(Proxy.NO_PROXY).build();
                Request request = new Request.Builder()
                        .url("https://www.baidu.com")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    showResponse(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void sendRequestWithOkHttp4(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String CA_DOMAIN = "www.baidu.com";
                //获取公钥：
                //openssl s_client -connect www.baidu.com:443 -servername www.baidu.com | openssl x509 -pubkey -noout | openssl rsa -pubin -outform der | openssl dgst -sha256 -binary | openssl enc -base64
                final String CA_PUBLIC_KEY = "sha256/Zhv4cvwdHmEmE0edWEcIdmLfwsqxrrOmp+vbngwNnrU=";
                CertificatePinner pinner = new CertificatePinner.Builder()
                        .add(CA_DOMAIN,CA_PUBLIC_KEY)
                        .build();
                OkHttpClient client = new OkHttpClient().newBuilder().certificatePinner(pinner).build();
                Request request = new Request.Builder()
                        .url("https://www.baidu.com")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    showResponse(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void sendRequestWithOkHttp5(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://so.com")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    showResponse(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void sendRequestWithOkHttp6(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://zhihu.com")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    showResponse(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(response);
            }
        });
    }
}