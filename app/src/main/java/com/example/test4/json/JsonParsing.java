package com.example.test4.json;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by bridgelabz4 on 13/1/16.
 */
public class JsonParsing {
    static InputStream inputStream=null;
    static JSONArray jsonArray=null;
    static String json="";
    public JsonParsing()
    {

    }
    public JSONArray getjsonurl(String url){
        StringBuilder stbuil= new StringBuilder();
        HttpClient    hclient= new DefaultHttpClient();
        HttpGet       htget= new HttpGet(url);
        try{
            HttpResponse hresp=hclient.execute(htget);
            StatusLine statusLine=hresp.getStatusLine();
            int Statuscode=statusLine.getStatusCode();
            if(Statuscode == 200){
                HttpEntity entity= hresp.getEntity();
                InputStream content= entity.getContent();
                BufferedReader buffread= new BufferedReader(new InputStreamReader(content));
                String Line;
                while ((Line =buffread.readLine()) != null){
                    stbuil.append(Line);
                }
            }else {
                Log.e("d==","download not completed");
            }
        }
        catch (ClientProtocolException eb){
            eb.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        try{
            jsonArray= new JSONArray(stbuil.toString());
        }catch (JSONException e){Log.e("json","Error"+e.toString());
        }
        return jsonArray;
    }
}
