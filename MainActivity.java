package com.example.john.myapplication;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Entity;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * HttpCLient-->HttpGet | httpPost---->HttpResponse
         */
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet("http://www.baidu.com");
        /**
         * HttpGet
         */
        try {
            HttpParams params = new BasicHttpParams();
            /**
             * HttpGet 通过setParameter
             */
            // params.setParameter("name", "vaule");
            // get.setParams(params);
            HttpResponse response = client.execute(get);

            if (response.getStatusLine().getStatusCode() == 200) {
                InputStream content = response.getEntity().getContent();
                //对content进行解析
                BufferedOutputStream bos = new BufferedOutputStream(
                        new FileOutputStream(new File("test.jpg")));
                byte[] bytes = new byte[1024];
                while (content.read(bytes) != -1) {
                    bos.write(bytes);
                }

                bos.flush();
                bos.close();
                content.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * HttpPost
         * 设置参数
         *     1. setParameter()
         *     2. List<NameValuepair> paras
         *        paras.add(new BasicNameValuePara("name"，"value"));
         *        post.setEntity(
         */
        List<NameValuePair> paras = new ArrayList<NameValuePair>();
        paras.add(new BasicNameValuePair("name", "value"));
        HttpPost post = new HttpPost("");
        try {
            post.setEntity(new UrlEncodedFormEntity(paras, HTTP.UTF_8));
            try {
                HttpResponse response = client.execute(post);
                /**
                 * 之后与上述Get方法类似
                 */
                if (response.getStatusLine().getStatusCode() == 200) {
                    //.....
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
