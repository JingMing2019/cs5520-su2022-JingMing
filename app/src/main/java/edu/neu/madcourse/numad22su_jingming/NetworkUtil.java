package edu.neu.madcourse.numad22su_jingming;

import android.util.Log;
import android.util.Patterns;
import android.webkit.URLUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public final class NetworkUtil {

    public static class MyException extends Exception {
        public MyException() {
        }

        public MyException(String message) {
            super(message);
        }
    }

    public static String validInput(String url) throws MyException {
        if (Patterns.WEB_URL.matcher(url).matches() || URLUtil.isValidUrl(url)) {
            if(!(url.startsWith("https://")||url.startsWith("http://"))){
                return "https://" + url;
            }
            return url;
        }

        throw new MyException("Invalid Input");
    }

    public static String convertStreamToString(InputStream inputStream){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String len;
            while((len = bufferedReader.readLine()) != null){
                stringBuilder.append(len);
            }
            bufferedReader.close();
            return stringBuilder.toString().replace(",", ",\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String httpResponse(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        Log.v("JMWebServiceActivity", "open success");
        conn.setRequestMethod("GET");
        Log.v("JMWebServiceActivity", "set request success");
        conn.setDoInput(true);
        Log.v("JMWebServiceActivity", "set do input success");

        conn.connect();
        Log.v("JMWebServiceActivity", "connect success");

        // Read response.
        InputStream inputStream = conn.getInputStream();
        Log.v("JMWebServiceActivity", "get input stream success");
        String resp = NetworkUtil.convertStreamToString(inputStream);
        Log.v("JMWebServiceActivity", "convert stream to string success");

        return resp;
    }
}