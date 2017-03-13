package example.com.findstarwar;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    BufferedReader bufferedReader;
    ProgressDialog progressDialog;


    Button buttonSearch;
    EditText editTextEntry;
    static List<StarWarCharacter> characters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //wire the widget
        buttonSearch = (Button)findViewById(R.id.activity_main_button_search);
        editTextEntry=(EditText)findViewById(R.id.activity_main_edit_text_entry);

        //set OnClickListener
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Loading");
                progressDialog.show();
                //fill in CharacterList and display it
                fillCharacterList();

            }
        });
    }

    private void fillCharacterList() {
        String baseUrl = "https://swapi.co/api/people/?search=";
        String search = editTextEntry.getText().toString();
        String fullUrl = baseUrl+ Uri.encode(search);

        new DownloadFileTask().execute(fullUrl);
    }

    private class DownloadFileTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {
            //intialize the transaction of fragment
            FragmentManager fm = getSupportFragmentManager();
            if(fm.findFragmentByTag("CharacterFragment")== null)
            {fm.beginTransaction().add(R.id.activity_main_frame_layout_character_container, new CharacterFragment(), "CharacterFragment").commit();}
            progressDialog.dismiss();
        }

        @Override
        protected String doInBackground(String... params) {

            // fetch data
            String jsonString="";
            try {
                URL url = new URL(params[0]);
                URLConnection urlConnection= url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while((line = bufferedReader.readLine()) != null){
                    jsonString = jsonString + line;
                }
            }
            catch (Exception e){
                Log.d("Fetch Data Failed: ", e.getMessage());
            }

            // data is fetched at this point
            // convert jsonString to an jsonObject
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.optJSONArray("results");

                // add each jsonobject into the List<StarWarCharacter> characters
                for (int i = 0; i < jsonArray.length(); i++){
                    // loop through the josonArray called results
                    // initialize a new starwarcharacter at each index of the array result
                    // fetch the string values
                    StarWarCharacter starWarCharacter = new StarWarCharacter();
                    starWarCharacter.setName(jsonArray.opt(0).toString());
                    starWarCharacter.setHeight(jsonArray.opt(1).toString());
                    starWarCharacter.setMass(jsonArray.opt(2).toString());
                    starWarCharacter.setHair_color(jsonArray.opt(3).toString());
                    starWarCharacter.setSkin_color(jsonArray.opt(4).toString());
                    starWarCharacter.setEye_color(jsonArray.opt(5).toString());
                    starWarCharacter.setBirth_year(jsonArray.opt(6).toString());
                    starWarCharacter.setBirth_year(jsonArray.opt(7).toString());

                    // fetch array values
                    //initialize a helper variable each time for adding objects in JSONArray into a string array
                    String temp[] = null;
                    for (int f = 0; f <jsonArray.optJSONObject(8).optJSONArray("films").length(); i ++ ){
                         temp = new String[jsonArray.optJSONObject(8).optJSONArray("films").length()];
                        temp[f] = jsonArray.optJSONObject(8).optJSONArray("films").optString(f);
                    }
                    if (temp != null) {
                        starWarCharacter.setFilms(temp);
                    }




                    temp = null;
                    for (int f = 0; f <jsonArray.optJSONObject(9).optJSONArray("species").length(); i ++ ){
                        temp = new String[jsonArray.optJSONObject(9).optJSONArray("species").length()];
                        temp[f] = jsonArray.optJSONObject(9).optJSONArray("species").optString(f);
                    }
                    if (temp != null) {
                        starWarCharacter.setSpecies(temp);
                    }



                    temp = null;
                    for (int f = 0; f <jsonArray.optJSONObject(10).optJSONArray("vehicles").length(); i ++ ){
                        temp = new String[jsonArray.optJSONObject(10).optJSONArray("vehicles").length()];
                        temp[f] = jsonArray.optJSONObject(10).optJSONArray("vehicles").optString(f);
                    }
                    if (temp!=null){
                        starWarCharacter.setVehicles(temp);
                    }

                    temp = null;
                    for (int f = 0; f <jsonArray.optJSONObject(11).optJSONArray("starships").length(); i ++ ){
                        temp = new String[jsonArray.optJSONObject(11).optJSONArray("starships").length()];
                        temp[f] = jsonArray.optJSONObject(11).optJSONArray("starships").optString(f);
                    }
                    if (temp!=null){
                        starWarCharacter.setStarships(temp);
                    }

                }
            }catch (JSONException e){
                Log.d("Failed Conversion: ", e.getMessage());
            }



            return jsonString;
        }
    }
}
