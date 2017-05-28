package org.smajko.watapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ListView;
import android.util.Log;
import android.util.Base64;
import android.widget.Toast;

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
    ProgressDialog pdLoading;
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

        //hard-coded condition descriptions
        dictionary.put("Acne", "Acne, or acne vulgaris, is a skin problem that starts when oil and dead skin cells clog up your pores.");
        dictionary.put("Hives", "Urticaria, also known as hives, is an outbreak of swollen, pale red bumps or plaques (wheals) on the skin that appear suddenly -- either as a result of the body's reaction to certain allergens, or for unknown reasons.");
        dictionary.put("Shingles", "Shingles is a painful skin rash. It is caused by the varicella zoster virus. Shingles usually appears in a band, a strip, or a small area on one side of the face or body.");
        dictionary.put("Chicken pox", "Chickenpox (varicella) is a contagious illness that causes an itchy rash and red spots or blisters (pox) all over the body. ");
        dictionary.put("Melanoma", "Melanoma, the most serious type of skin cancer, develops in the cells (melanocytes) that produce melanin — the pigment that gives your skin its color. Melanoma can also form in your eyes and, rarely, in internal organs, such as your intestines.");
        dictionary.put("Ingrown Hair", "Ingrown hairs are hairs that have curled around and grown back into your skin instead of rising up from it. Sometimes, dead skin can clog up a hair follicle. That forces the hair inside it to grow sideways under the skin, rather than upward and outward.");
        dictionary.put("Warts", "A wart is a skin growth caused by some types of the virus called the human papillomavirus (HPV). HPV infects the top layer of skin, usually entering the body in an area of broken skin. The virus causes the top layer of skin to grow rapidly, forming a wart.");
        dictionary.put("Poison Ivy Rash", "Poison ivy is a plant that can cause a red, itchy rash called allergic contact dermatitis. It is the most common skin problem caused by contact with plants.");
        dictionary.put("Scabies", "Scabies is not an infection, but an infestation. Tiny mites called Sarcoptes scabiei set up shop in the outer layers of human skin. The skin does not take kindly to the invasion. As the mites burrow and lay eggs inside the skin, the infestation leads to relentless itching and an angry rash.");

        //translated condition names to get from post
        dictionary2.put("Acne", "acne");
        dictionary2.put("Hives", "hives");
        dictionary2.put("Shingles", "herpes");
        dictionary2.put("Chicken pox", "varicella");
        dictionary2.put("Melanoma", "melanoma");
        dictionary2.put("Ingrown Hair", "ingrown_hair");
        dictionary2.put("Warts", "warts");
        dictionary2.put("Poison Ivy Rash", "poison_ivy");
        dictionary2.put("Scabies", "scabies");

        //get list of possible conditions
        conditions = (ArrayList<String>) getIntent().getStringArrayListExtra("conditions");
        request = getIntent().getBooleanExtra("took_picture", false);

        for (int i = 0; i < 10; i++)
            current[i] = "";

        //if picture was taken, do image processing
        if (request) {
            pdLoading = new ProgressDialog(ListResults.this);
            pdLoading.setMessage("\tProcessing image...");
            pdLoading.show();

            //create base64 encoded string from png
            RequestParams params = new RequestParams();
            outputFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myimage.png";
            File myFile = new File(outputFilePath);
            Bitmap bitmapOrg = BitmapFactory.decodeFile(myFile.getAbsolutePath());
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 100, bao);
            byte[] ba = bao.toByteArray();
            String ba1 = Base64.encodeToString(ba, Base64.DEFAULT);
            Log.d("ListResults", "size: " + ba1.getBytes().length);

            //send request as json string
            params.put("picture", ba1);
            params.setUseJsonStreamer(true);

            HttpUtils.post("calculate", params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // get percentages from JSONObject response
                    try {
                        serverResp = new JSONObject(response.toString());
                        for (int i = 0; i < conditions.size(); i++) {
                            String fullpercent = "Photo analysis: ";
                            try {
                                current[i] = response.getString(dictionary2.get(conditions.get(i)));
                            } catch (JSONException e) {
                                fullpercent += "n/a";
                            }
                            float result = Float.parseFloat(current[i]);
                            result *= 100;
                            String resultString = String.format("%.2f", result);
                            Log.d("ListResults", resultString);
                            fullpercent += resultString;
                            fullpercent += "%";
                            Result example = new Result(conditions.get(i), dictionary.get(conditions.get(i)), fullpercent);
                            resultList.add(example);
                        }
                        ListView listView = (ListView) findViewById(android.R.id.list);
                        ResultAdapter resultAdapter = new ResultAdapter(getApplicationContext(), R.layout.result_view, resultList);
                        listView.setAdapter(resultAdapter);
                        if (pdLoading.isShowing()) {
                            pdLoading.dismiss();
                        }

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
                    Log.d("ListResults", "onFailure response String: " + responseString);
                    for (int i = 0; i < conditions.size(); i++) {
                        String fullpercent = "Photo analysis: n/a";

                        Result example = new Result(conditions.get(i), dictionary.get(conditions.get(i)), fullpercent);
                        resultList.add(example);
                    }
                    ListView listView = (ListView) findViewById(android.R.id.list);
                    ResultAdapter resultAdapter = new ResultAdapter(getApplicationContext(), R.layout.result_view, resultList);
                    listView.setAdapter(resultAdapter);
                    if (pdLoading.isShowing()) {
                        pdLoading.dismiss();
                    }
                    Toast.makeText(ListResults.this, "Network error",
                            Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                    for (int i = 0; i < conditions.size(); i++) {
                        String fullpercent = "Photo analysis: n/a";

                        Result example = new Result(conditions.get(i), dictionary.get(conditions.get(i)), fullpercent);
                        resultList.add(example);
                    }
                    ListView listView = (ListView) findViewById(android.R.id.list);
                    ResultAdapter resultAdapter = new ResultAdapter(getApplicationContext(), R.layout.result_view, resultList);
                    listView.setAdapter(resultAdapter);
                    if (pdLoading.isShowing()) {
                        pdLoading.dismiss();
                    }
                    Toast.makeText(ListResults.this, "Network error",
                            Toast.LENGTH_LONG).show();
                }
            });
        //if not creating a request to process image
        } else {
            for (int i = 0; i < conditions.size(); i++) {
                Result example = new Result(conditions.get(i), dictionary.get(conditions.get(i)), "");
                resultList.add(example);
            }
            ListView listView = (ListView) findViewById(android.R.id.list);
            ResultAdapter resultAdapter = new ResultAdapter(getApplicationContext(), R.layout.result_view, resultList);
            listView.setAdapter(resultAdapter);
        }
    }
}
