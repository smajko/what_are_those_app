package org.smajko.watapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ListView;
import android.util.Log;
import android.util.Base64;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class ListResults extends Activity {
    private String outputFilePath;
    JSONObject serverResp;
    ArrayList<String> conditions = new ArrayList<String>();
    Map<String, String> dictionary = new HashMap<String, String>();
    Map<String, String> dictionary2 = new HashMap<String, String>();
    String current[] = new String[10];
    List<Result> resultList = new ArrayList<>();

    boolean request = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        dictionary.put("Acne","Acne, or acne vulgaris, is a skin problem that starts when oil and dead skin cells clog up your pores.");
        dictionary.put("Hives","Urticaria, also known as hives, is an outbreak of swollen, pale red bumps or plaques (wheals) on the skin that appear suddenly -- either as a result of the body's reaction to certain allergens, or for unknown reasons.");
        dictionary.put("Shingles","Shingles is a painful skin rash. It is caused by the varicella zoster virus. Shingles usually appears in a band, a strip, or a small area on one side of the face or body.");
        dictionary.put("Chicken pox","Chickenpox (varicella) is a contagious illness that causes an itchy rash and red spots or blisters (pox) all over the body. ");
        dictionary.put("Melanoma","Melanoma, the most serious type of skin cancer, develops in the cells (melanocytes) that produce melanin — the pigment that gives your skin its color. Melanoma can also form in your eyes and, rarely, in internal organs, such as your intestines.");
        dictionary.put("Ingrown Hair","Ingrown hairs are hairs that have curled around and grown back into your skin instead of rising up from it. Sometimes, dead skin can clog up a hair follicle. That forces the hair inside it to grow sideways under the skin, rather than upward and outward.");
        dictionary.put("Warts","A wart is a skin growth caused by some types of the virus called the human papillomavirus (HPV). HPV infects the top layer of skin, usually entering the body in an area of broken skin. The virus causes the top layer of skin to grow rapidly, forming a wart.");
        dictionary.put("Poison Ivy Rash","Poison ivy is a plant that can cause a red, itchy rash called allergic contact dermatitis. It is the most common skin problem caused by contact with plants.");
        dictionary.put("Scabies","Scabies is not an infection, but an infestation. Tiny mites called Sarcoptes scabiei set up shop in the outer layers of human skin. The skin does not take kindly to the invasion. As the mites burrow and lay eggs inside the skin, the infestation leads to relentless itching and an angry rash.");

        dictionary2.put("Acne","acne");
        dictionary2.put("Hives","hives");
        dictionary2.put("Shingles","herpes");
        dictionary2.put("Chicken pox","varicella");
        dictionary2.put("Melanoma","melanoma");
        dictionary2.put("Ingrown Hair","ingrown_hair");
        dictionary2.put("Warts","warts");
        dictionary2.put("Poison Ivy Rash","poison_ivy");
        dictionary2.put("Scabies","scabies");

        conditions = (ArrayList<String>)getIntent().getStringArrayListExtra("conditions");
        String percent = "Percentage: ";

        for (int i = 0; i < 10; i++)
            current[i] = "";

        RequestParams params = new RequestParams();
        //new AsyncCaller().execute();

            outputFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myimage.png";
            File myFile = new File(outputFilePath);
            Bitmap bitmapOrg = BitmapFactory.decodeFile(myFile.getAbsolutePath());
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 100, bao);
            byte [] ba = bao.toByteArray();
            String ba1=Base64.encodeToString(ba,Base64.DEFAULT);
            Log.d("ListResults", "size: " + ba1.getBytes().length);


        params.put("picture", ba1);
        //send as json string
        params.setUseJsonStreamer(true);

        HttpUtils.post("calculate", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    serverResp = new JSONObject(response.toString());
                    //serverResp2 = response;
                    for (int i = 0; i < conditions.size(); i++)
                    {
                        String fullpercent = "Photo analysis: ";
                        try { current[i] = response.getString(dictionary2.get(conditions.get(i))); }
                        catch (JSONException e) { fullpercent += "n/a"; }
                        float result = Float.parseFloat(current[i]);
                        result *= 100;
                        String resultString = String.format("%.2f",result);
                        Log.d("ListResults",resultString);
                        fullpercent += resultString;
                        fullpercent += "%";
                        Result example = new Result(conditions.get(i),dictionary.get(conditions.get(i)), fullpercent);
                        resultList.add(example);
                    }
                    ListView listView = (ListView)findViewById(android.R.id.list);
                    ResultAdapter resultAdapter = new ResultAdapter(getApplicationContext(), R.layout.result_view, resultList);
                    listView.setAdapter(resultAdapter);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                String respArray = timeline.toString();
                Log.d("ListResults", "onSuccess Array: " + respArray.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //super.onFailure(statusCode, headers, responseString, throwable);
                //String respString = responseString.toString();
                Log.d("ListResults", "onFailure response String: " + responseString);
                for (int i = 0; i < conditions.size(); i++)
                {
                    String fullpercent = "Photo analysis: n/a";

                    Result example = new Result(conditions.get(i),dictionary.get(conditions.get(i)), fullpercent);
                    resultList.add(example);
                }
                ListView listView = (ListView)findViewById(android.R.id.list);
                ResultAdapter resultAdapter = new ResultAdapter(getApplicationContext(), R.layout.result_view, resultList);
                listView.setAdapter(resultAdapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                //String respString = response.toString();
                //Log.d("ListResults", "onFailure response String: " + respString);
                for (int i = 0; i < conditions.size(); i++)
                {
                    String fullpercent = "Photo analysis: n/a";

                    Result example = new Result(conditions.get(i),dictionary.get(conditions.get(i)), fullpercent);
                    resultList.add(example);
                }
                ListView listView = (ListView)findViewById(android.R.id.list);
                ResultAdapter resultAdapter = new ResultAdapter(getApplicationContext(), R.layout.result_view, resultList);
                listView.setAdapter(resultAdapter);
            }
        });

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

            RequestParams rparams = new RequestParams();

            //convert png file to base64 encoded string
            outputFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myimage.png";
            File myFile = new File(outputFilePath);
            Bitmap bitmapOrg = BitmapFactory.decodeFile(myFile.getAbsolutePath());
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 100, bao);
            byte [] ba = bao.toByteArray();
            String ba1=Base64.encodeToString(ba,Base64.DEFAULT);

            rparams.put("picture", ba1);

            HttpUtils.post("calculate", rparams, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // If the response is JSONObject instead of expected JSONArray
                    try {
                        serverResp = new JSONObject(response.toString());
                        for (int i = 0; i < conditions.size(); i++)
                        {
                            String fullpercent = "Photo analysis: ";
                            try { current[i] = response.getString(dictionary2.get(conditions.get(i))); }
                            catch (JSONException e) { fullpercent += "n/a"; }
                            float result = Float.parseFloat(current[i]);
                            result *= 100;
                            String resultString = String.format("%.2f",result);
                            Log.d("ListResults",resultString);
                            fullpercent += resultString;
                            fullpercent += "%";
                            Result example = new Result(conditions.get(i),dictionary.get(conditions.get(i)), fullpercent);
                            resultList.add(example);
                        }
                        ListView listView = (ListView)findViewById(android.R.id.list);
                        ResultAdapter resultAdapter = new ResultAdapter(getApplicationContext(), R.layout.result_view, resultList);
                        listView.setAdapter(resultAdapter);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                    String respArray = timeline.toString();
                    Log.d("ListResults", "onSuccess Array: " + respArray);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("ListResults", "onFailure response String: " + responseString);
                    for (int i = 0; i < conditions.size(); i++)
                    {
                        String fullpercent = "Photo analysis: n/a";

                        Result example = new Result(conditions.get(i),dictionary.get(conditions.get(i)), fullpercent);
                        resultList.add(example);
                    }
                    ListView listView = (ListView)findViewById(android.R.id.list);
                    ResultAdapter resultAdapter = new ResultAdapter(getApplicationContext(), R.layout.result_view, resultList);
                    listView.setAdapter(resultAdapter);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                    String respString = response.toString();
                    Log.d("ListResults", "onFailure response String: " + respString);
                    for (int i = 0; i < conditions.size(); i++)
                    {
                        String fullpercent = "Photo analysis: n/a";

                        Result example = new Result(conditions.get(i),dictionary.get(conditions.get(i)), fullpercent);
                        resultList.add(example);
                    }
                    ListView listView = (ListView)findViewById(android.R.id.list);
                    ResultAdapter resultAdapter = new ResultAdapter(getApplicationContext(), R.layout.result_view, resultList);
                    listView.setAdapter(resultAdapter);
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (pdLoading.isShowing()) {
                pdLoading.dismiss();
            }
        }
    }
}
