package seedu.address.logic;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.security.PrivilegedExceptionAction;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationResult;
import net.minidev.json.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.naming.ServiceUnavailableException;

/**
 * Handles the sending of the mail
 */
public class OutlookRequest {

    private String emailAdd;
    private String subject;
    private String body;

    public OutlookRequest(String emailAdd, String subject, String body) {
        this.emailAdd = emailAdd;
        this.subject = subject;
        this.body = body;
    }

    private String getEmailAdd() {
        return emailAdd;
    }

    public String getBody() {
        return body;
    }

    public String getSubject() {
        return subject;
    }

    private final static String CLIENT_ID = "f6570890-f782-400d-a2a4-22374e313a33";

    /**
     * Connecting to outlook with appropriate tokens to send email
     */
    public static void sendMail(OutlookRequest outlookRequest) {

        try {

            String url = "https://outlook.office.com/api/v2.0/me/sendmail"; //here is definitely this url

            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            //this is for the Auth-code grant method
            String authCode = getAuthCode();
            String accessToken = getAccessToken();

            /*String email = "johndoe2103@outlook.com";
            String password = "software2103";
            AuthenticationResult result = getAccessTokenFromUserCredentials("https://login.microsoftonline.com/organizations/oauth2/v2.0/authorize", email, password);
            String accessToken = result.getAccessToken();
            */

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            conn.setRequestProperty ("Authorization", "Bearer" + accessToken);
            conn.setRequestProperty("Accept", "application/json; odata.metadata=none");
            conn.setRequestProperty("Content-Type", "application/json");

            String subject = outlookRequest.getSubject();
            String body = outlookRequest.getBody();
            String emailAdd = outlookRequest.getEmailAdd();

            String str = "{\"Message\":{\"Subject\":\""
                    + subject + "\",\"Body\":{\"ContentType\":\"Text\",\"Content\":\""
                    + body + "\"},\"ToRecipients\":[{\"EmailAddress\":{\"Address\":\""
                    + emailAdd + "\"}}]},\"SaveToSentItems\":\"true\"}";
            byte[] outputInBytes = str.getBytes("UTF-8");
            OutputStream os = conn.getOutputStream();
            os.write(outputInBytes);
            os.close();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static String getAuthCode() {
        try {
            String authEndpoint = "https://login.microsoftonline.com/organizations/oauth2/v2.0/authorize";
            URL obj = new URL(authEndpoint);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            //connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String urlParameters = "client_id="
                    + CLIENT_ID
                    + "&response_type=code"
                    + "&redirect_uri=https://login.microsoftonline.com/common/oauth2/nativeclient"
                    + "&response_mode=form_post"
                    + "&scope=https://graph.microsoft.com/mail.send"
                    + "&state=12345";
            byte[] outputInBytes = urlParameters.getBytes("UTF-8");
            OutputStream os = connection.getOutputStream();
            os.write(outputInBytes);
            os.close();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            String inputLine;
            String[] array = new String[100];
            int i = 0;
            while((inputLine =in.readLine()) != null){
                array[i] = inputLine;
                i++;
            }
           /* String[] sb = new String[];
            for (int c; (c = in.read()) >= 0;)
                sb.add((char)c);
            String authCode = sb.toString();
           */
           in.close();
            String authCode = array[0];
            System.out.println(authCode);
            return authCode;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String getAccessToken() {

        String testEmail = "johndoe2103@outlook.com";
        String testPassword = "software123";
        String urlParams = "username=" + testEmail + "&password=" + testPassword + "&grant_type=password"
                + "&scope=openid" + "&client_id=" + CLIENT_ID + "&resource=https://login.microsoftonline.com/organization/oauth2/nativeclient";

        byte[] postData = urlParams.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;

        String accessToken = null;
        try {
            String tokenEndpoint = "https://login.microsoftonline.com/common/oauth2/v2.0/token";
            URL obj = new URL(tokenEndpoint);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            connection.setUseCaches(false);

            /*connection.setRequestProperty("client_id", CLIENT_ID);
            connection.setRequestProperty("scope","https://outlook.office.com/mail.send");
            connection.setRequestProperty("redirect_uri", "https://login.microsoftonline.com/common/oauth2/nativeclient");
            connection.setRequestProperty("code", authCode);
            connection.setRequestProperty("grant_type", "authorization_code");
            connection.setRequestMethod("POST");
            */

            /*String urlParameters = "client_id="
                    + CLIENT_ID
                    + "&grant_type=password"
                    + "&scope=https://outlook.office.com/openid"
                    + ""
            */


            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.write(postData);
            }

            int responseCode = connection.getResponseCode();
            String responseMsg = connection.getResponseMessage();
            if (responseCode >= 400 && responseCode <= 499) {
                throw new Exception(responseMsg + " :: " + responseCode);
            }

            InputStream in = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            StringBuffer response = new StringBuffer();

            String inputLine;

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }

            accessToken = response.toString();

            // JSONObject myResponse = new JSONObject(response);





            /*BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            System.out.println(content.toString());
            return content.toString();

            /*String inputLine;
            String[] array = new String[100];
            int i = 0;
            while((inputLine = in.readLine()) != null){
                array[i] = inputLine;
                i++;
            }
            in.close();
            String accessToken = array[0];
            System.out.println(accessToken);
            return accessToken;
            */

        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessToken;
    }


    /*private static AuthenticationResult getAccessTokenFromUserCredentials(String url, String email, String password) throws Exception {
        AuthenticationContext context;
        AuthenticationResult result;
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(1);
            context = new AuthenticationContext(url, false, service);
            Future<AuthenticationResult> future = context.acquireToken(
                    "https://graph.windows.net", CLIENT_ID, email, password,
                    null);
            result = future.get();
        } finally {
            service.shutdown();
        }

        if (result == null) {
            throw new ServiceUnavailableException(
                    "authentication result was null");
        }
        return result;
    }
    */



}
