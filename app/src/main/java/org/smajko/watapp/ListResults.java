package org.smajko.watapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class ListResults extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        //new AsyncCaller().execute();
        List<Result> resultList = new ArrayList<>();
        Result example = new Result("foo","bar",R.drawable.ic_launcher);
        resultList.add(example);
        resultList.add(example);

        ListView listView = (ListView)findViewById(android.R.id.list);
        ResultAdapter resultAdapter = new ResultAdapter(this, R.layout.result_view, resultList);
        listView.setAdapter(resultAdapter);
    }

    /**********************************************************************
     *
     *      Async caller to fetch data from webservice in background
     *
     **********************************************************************/
    private class AsyncCaller extends AsyncTask<Void, Void, Void>
    {
        ProgressDialog pdLoading = new ProgressDialog(ListResults.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.show();
        }
        @Override
        protected Void doInBackground(Void... params) {

            /**
                        TO BE IMPLEMENTED

             RequestParams params = new RequestParams();

             params.put("symptoms", symptoms);
             params.put("gender", gender);
             params.put("age",age);
             params.put("days",days);
             params.put("reoccurring",reoccurring);

             try {
             outputFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myimage.png";
             File myFile = new File(outputFilePath);
             params.put("picture", myFile);
             } catch(FileNotFoundException e) {}

             HttpUtils.post(AppConstant.URL_FEED, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            // If the response is JSONObject instead of expected JSONArray
            Log.d("res", "---------------- response : " + response);
            try {
            JSONObject serverResp = new JSONObject(response.toString());
            } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
            // Pull out the first event on the public timeline
            }
            });

             **/


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //list results

            pdLoading.dismiss();
        }

    }
}
