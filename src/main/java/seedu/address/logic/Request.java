package seedu.address.logic;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Handles the steps for different requests.
 */
public class Request {

    public Request(String address, String requestMethod) {
        try {
            HttpURLConnection conn = openConnection(address, requestMethod);
            //URL url = new URL(address);
            //HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //conn.setRequestMethod(requestMethod);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            BufferedWriter out = new BufferedWriter(new FileWriter("tmp.html"));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = bufferedReader.readLine()) != null) {
                out.append(inputLine).append("\n");
            }

            out.close();

            File htmlFile = new File("tmp.html");
            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Details of a request for a access token
     */
    public static HttpURLConnection tokenRequest(String address, String requestMethod, String data) {
        try {
            HttpURLConnection conn = openConnection(address, requestMethod);
            //URL url = new URL(address);
            //HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            byte[] postData;
            postData = data.getBytes(StandardCharsets.UTF_8);
            //conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(postData);
            os.flush();
            os.close();

            return conn;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Details for a POST request to send a mail, using the accessToken
     */
    public static HttpURLConnection sendMail(String accessToken, String address, String requestMethod, String data) {
        try {
            HttpURLConnection conn = openConnection(address, requestMethod);
            //URL url = new URL(address);
            //HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            byte[] postData;
            postData = data.getBytes(StandardCharsets.UTF_8);
            //conn.setRequestMethod(requestMethod);
            conn.setRequestProperty ("Authorization", "Bearer " + accessToken);
            conn.setRequestProperty("Accept", "application/json; odata.metadata=none");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(postData);
            os.flush();
            os.close();

            return conn;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Opening a url connection to given address and setting the request type
     */
    public static HttpURLConnection openConnection(String address, String requestMethod) {
        try {
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(requestMethod);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Reads the response of the request sent through the HttpURLConnection.
     */
    public static ArrayList<String> read(HttpURLConnection conn) {

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            ArrayList<String> content = new ArrayList<>();
            while ((inputLine = bufferedReader.readLine()) != null) {
                content.add(inputLine);
            }
            return content;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
