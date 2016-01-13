package com.example.test4.json;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ListActivity {

    private static String Url="http://jsonplaceholder.typicode.com/posts";
    private static final String user="user";
    private static final String Id="id";
    private static final String Title="title";
    private static final String Body="body";
    ArrayList<HashMap<String,String>> arrayList= new ArrayList<HashMap<String, String>>();
    ListView l1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new ProgressTask(MainActivity.this).execute();
    }
    private  class ProgressTask extends AsyncTask<String,Void,Boolean>{
        private ListActivity activity;
        private ProgressDialog progressDialog;
        private Context context;
        public ProgressTask(ListActivity activity){
           this.activity=activity;
            context=activity;
           progressDialog= new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            this.progressDialog.setMessage("this is dialog");
            this.progressDialog.show();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
             if(progressDialog.isShowing()){
                 progressDialog.dismiss();
             }
            ListAdapter listAdapter= new SimpleAdapter(context,arrayList,
                    R.layout.item_list,new String[]{user,Id,Title,Body},
                    new int[]{R.id.user1,R.id.id1,R.id.title1,R.id.body1});
             setListAdapter(listAdapter);
            l1=getListView();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            JsonParsing jsonParser= new JsonParsing();
            JSONArray jsonArray= jsonParser.getjsonurl(Url);
            for(int i=0;i<jsonArray.length();i++){
                try {
                    JSONObject c=jsonArray.getJSONObject(i);
                    String user2=c.getString("userId");
                    String Id2=c.getString("id");
                    String title2=c.getString("title");
                    String Body2=c.getString("body");
                    HashMap<String,String> h1=new HashMap<String,String>();
                    h1.put(Body,Body2);
                    h1.put(Title, title2);
                    h1.put(Id,Id2);
                    h1.put(user,user2);
                    arrayList.add(h1);

                }
                catch (JSONException e){e.printStackTrace();}
            }
            return false;
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
