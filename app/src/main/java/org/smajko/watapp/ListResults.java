package org.smajko.watapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ListResults extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Map<String, String> dictionary = new HashMap<String, String>();

        dictionary.put("Acne","Acne, or acne vulgaris, is a skin problem that starts when oil and dead skin cells clog up your pores.");
        dictionary.put("Hives","Urticaria, also known as hives, is an outbreak of swollen, pale red bumps or plaques (wheals) on the skin that appear suddenly -- either as a result of the body's reaction to certain allergens, or for unknown reasons.");
        dictionary.put("Shingles","Shingles is a painful skin rash. It is caused by the varicella zoster virus. Shingles usually appears in a band, a strip, or a small area on one side of the face or body.");
        dictionary.put("Chicken pox","Chickenpox (varicella) is a contagious illness that causes an itchy rash and red spots or blisters (pox) all over the body. ");
        dictionary.put("Melanoma","Melanoma, the most serious type of skin cancer, develops in the cells (melanocytes) that produce melanin — the pigment that gives your skin its color. Melanoma can also form in your eyes and, rarely, in internal organs, such as your intestines.");
        dictionary.put("Cold sore","Cold sores, sometimes called fever blisters, are groups of small blisters on the lip and around the mouth. The skin around the blisters is often red, swollen, and sore.");
        dictionary.put("Warts","A wart is a skin growth caused by some types of the virus called the human papillomavirus (HPV). HPV infects the top layer of skin, usually entering the body in an area of broken skin. The virus causes the top layer of skin to grow rapidly, forming a wart.");
        dictionary.put("Poison Ivy Rash","Poison ivy is a plant that can cause a red, itchy rash called allergic contact dermatitis. It is the most common skin problem caused by contact with plants.");
        dictionary.put("Scabies","Scabies is not an infection, but an infestation. Tiny mites called Sarcoptes scabiei set up shop in the outer layers of human skin. The skin does not take kindly to the invasion. As the mites burrow and lay eggs inside the skin, the infestation leads to relentless itching and an angry rash.");

        ArrayList<String> conditions = new ArrayList<String>();
        conditions = (ArrayList<String>)getIntent().getStringArrayListExtra("conditions");
        List<Result> resultList = new ArrayList<>();
        String percent = "Percentage: ";

        for (int i = 0; i < conditions.size(); i++)
        {
            Result example = new Result(conditions.get(i),dictionary.get(conditions.get(i)), percent);
            resultList.add(example);
        }

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
