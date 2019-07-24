package com.thesep.tronghoa.mymusicplayer.util.helpers;

import android.net.Uri;
import android.os.AsyncTask;

import com.thesep.tronghoa.mymusicplayer.data.Response;
import com.thesep.tronghoa.mymusicplayer.util.CommonUtils;
import com.thesep.tronghoa.mymusicplayer.util.listener.APICallback;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIFactory extends AsyncTask<Void, Void, Response> {

    private static final int READ_TIMEOUT = 5000;
    private static final int CONNECTION_TIMEOUT = 5000;

    private String mUrl;
    private  String mMethod;
    private List<HashMap<String, String>> mAttributes;
    private APICallback mAPICAllback;

    public APIFactory(Builder builder) {

    }

    public interface Method{
        String GET = "GET";
        String POST = "POST";
    }

    public APIFactory(String mUrl, String mMethod, List<HashMap<String, String>> mAttributes, APICallback mAPICAllback) {
        this.mUrl = mUrl;
        this.mMethod = mMethod;
        this.mAttributes = mAttributes;
        this.mAPICAllback = mAPICAllback;
    }

    public static class  Builder{
        private String mUrl;
        private APICallback mCallBack;
        private String mMethod;
        List<HashMap<String, String>> mAttributes;
        
        public Builder baseUrl(String url){
            mUrl = url;
            return this;
        }
        
        public Builder method(String method){
            mMethod = method;
            return  this;
        }
        
        public Builder attribute(List<HashMap<String, String>> attributes){
            mAttributes = attributes;
            return this;
        }
        
        public void build(){
            mUrl = CommonUtils.checkNotNull(mUrl) ? mUrl : Method.GET;
            mMethod = CommonUtils.checkNotNull(mMethod) ? mMethod : Method.GET;
            new APIFactory(this).execute();
        }

        public String getmUrl() {
            return mUrl;
        }

        public APICallback getmCallBack() {
            return mCallBack;
        }

        public String getmMethod() {
            return mMethod;
        }

        public List<HashMap<String, String>> getmAttributes() {
            return mAttributes;
        }
    }

    @Override
    protected Response doInBackground(Void... voids) {
        try {
            StringBuffer result = null;
            URL urlParse = new URL(mUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlParse.openConnection();
            httpURLConnection.setConnectTimeout(CONNECTION_TIMEOUT);
            httpURLConnection.setReadTimeout(READ_TIMEOUT);
            switch (mMethod){
                case Method.GET:
                    httpURLConnection.setRequestMethod(Method.GET);
                    result = fetchData(httpURLConnection);
                    break;
                case Method.POST:
                    httpURLConnection.setRequestMethod(Method.POST);
                    result = configPostMethod(httpURLConnection);
                    break;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private StringBuffer configPostMethod(HttpURLConnection httpURLConnection) {
        try {

            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder();

            for (int i = 0; i < mAttributes.size(); i ++){
                String key = null;
                String value = null;
                for (Map.Entry<String, String> values : mAttributes.get(i).entrySet()){
                    key = values.getKey();
                    value = values.getValue();
                }
                builder.appendQueryParameter(key, value);
            }

            String query = builder.build().getEncodedQuery();
            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter writer = new BufferedWriter(streamWriter);
            writer.write(query);
            writer.flush();

            writer.close();
            streamWriter.close();
            outputStream.close();

            return fetchData(httpURLConnection);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private StringBuffer fetchData(HttpURLConnection httpURLConnection) {
        InputStream inputStream;
        try {
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuffer data = new StringBuffer();
            String line;

            while ((line = bufferedReader.readLine()) != null){
                data.append(line);
            }

            //Free memory
            bufferedReader.close();
            reader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return data;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Response response) {
        super.onCancelled(response);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
