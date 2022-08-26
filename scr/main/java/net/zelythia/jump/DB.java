package net.zelythia.jump;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DB {

    private static String sessionID;

    public static void getSessionID(){
        try {
            URL url = new URL("https://zelythia.net/jkapi/createsession");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();

            sessionID = (String) new JSONObject(content.toString()).get("sessionID");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static JSONArray getTopPlayers(){
        try {
            URL url = new URL("https://zelythia.net/jkapi/top");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

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
            URL url = new URL("https://zelythia.net/jkapi/add/" + name);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type","application/json");
            con.setDoOutput(true);

            String postJsonData = "{\"score\":" + score + ",\"time\":" + time + ",\"sessionID\":\"" + sessionID + "\"}";
            System.out.println(postJsonData);

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postJsonData);
            wr.flush();
            wr.close();

            System.out.println(con.getResponseCode());

            con.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
