package com.cdn.appsusage.avikal;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.cdn.appsusage.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

/**
 * Created by himanshurathore on 8/11/17.
 */

public class ConverterClass extends AppCompatActivity {

    String countriesJson;
    String convertcsvJson;
    Gson gson = new Gson();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new ReadTask().execute();
    }

    public class ReadTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            countriesJson = readCountriesJson();
            convertcsvJson = readConvertCSVJson();
            Log.e("Countries Json", countriesJson);
            Log.e("ConvertCSV Json", convertcsvJson);

            MainJsonArray mainJsonArray = gson.fromJson(countriesJson, MainJsonArray.class);
            CodeJsonArray codeJsonArray = gson.fromJson(convertcsvJson, CodeJsonArray.class);

            List<MainJson> mainList = mainJsonArray.getMainJsonList();
            List<CodeJson> codeList = codeJsonArray.getCodeJsonList();

            for (int i = 0; i < codeList.size(); i++) {
                mainList.get(i).setPolice(codeList.get(i).getB());
                mainList.get(i).setAmbulance(codeList.get(i).getC());
                mainList.get(i).setFire(codeList.get(i).getD());
            }

            mainJsonArray.setMainJsonList(mainList);
            String finalStr = gson.toJson(mainJsonArray);
            Log.e("Final Json", finalStr);
            writeFinalJsonToFile(finalStr);
            return null;
        }

    }

    public String readCountriesJson(){
        try{
            InputStream is = getResources().openRawResource(R.raw.countries);
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }

            return writer.toString();
        } catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public String readConvertCSVJson(){
        try{
            InputStream is = getResources().openRawResource(R.raw.convertcsv);
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }

            return writer.toString();
        } catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public void writeFinalJsonToFile(String jsonObj){
        try {
            Writer output = null;
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/finalJson.json");
            if (!file.exists()){
                file.createNewFile();
            }
            output = new BufferedWriter(new FileWriter(file));
            output.write(jsonObj);
            output.close();
         //   Toast.makeText(getApplicationContext(), "Composition saved", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
