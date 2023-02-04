package net.zelythia.jungleJumper;

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
    private static JSONArray sessionScores = new JSONArray();

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
            System.out.println("Could not establish a connection with the api");
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
            System.out.println(sessionScores);
            return sessionScores;
        }
    }

    public static void addPlayerScore(String name, float score, float time){
        try {
            URL url = new URL("https://zelythia.net/jkapi/add/" + name);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            System.out.println(con);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type","application/json");
            con.setDoOutput(true);

            String postJsonData = "{\"score\":" + score + ",\"time\":" + time + ",\"sessionID\":\"" + sessionID + "\"}";

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postJsonData);
            wr.flush();
            wr.close();

            con.disconnect();
        } catch (IOException e) {
        }

        JSONObject newScore = new JSONObject();
        newScore.put("name", name);
        newScore.put("score", (int) score);
        newScore.put("time", (int) time);
        sessionScores.put(newScore);
    }
}
