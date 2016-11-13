package com.rohan.user.httpdemo3;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
HttpURLConnection connection;
    BufferedReader reader;
    WebView webView;
    String resp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = (Button) findViewById(R.id.click);
        webView = (WebView) findViewById(R.id.webview);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HtmlTask().execute("https://www.google.com/");
            }
        });
    }

public class HtmlTask extends AsyncTask<String,String,String>{

    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            String line ="";
            while((line = reader.readLine())!=null){
              resp += line;
            }
            reader.close();
            return resp;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    protected void onPostExecute(String s) {
        webView.loadData(s,"text/html", null);
    }
}
}
