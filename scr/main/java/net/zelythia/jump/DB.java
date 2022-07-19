package net.zelythia.jump;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DB {

    public DB(){

    }

    public static JSONArray getTopPlayers(){
        try {
            URL url = new URL("http://79.250.24.53/jkapi/top");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            con.disconnect();

            return new JSONArray(content.substring(content.indexOf("["), content.length()-1));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addPlayerScore(String name, float score, float time){
        try {
            URL url = new URL("http://79.250.24.53/jkapi/add/" + name);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type","application/json");
            con.setDoOutput(true);

            String postJsonData = "{\"score\":" + score + ",\"time\":" + time + "}";

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postJsonData);
            wr.flush();
            wr.close();

            con.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
