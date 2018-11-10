package seedu.address.logic;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Connecting to outlook with appropriate tokens to send email
     */
    public static void sendMail(OutlookRequest outlookRequest) throws Exception {

        try {
            // Opens microsoft authentication page in a browser tab
            Desktop.getDesktop().browse(new URI(ApplicationDetails.authURL));

            // setup a http listener on localhost:8000 to wait for the returned authcode
            ServerSocket serverSocket = new ServerSocket(8000);
            Socket socket = serverSocket.accept();
            InputStream is = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String inputLine;
            ArrayList<String> content = new ArrayList<>();
            while ((inputLine = bufferedReader.readLine()) != null) {
                content.add(inputLine);
            }

            // extract authcode from returned request
            String authCode = content.get(13).split("&")[0].split("code=")[1];

            // get the access token
            String tokenParams = String.format("client_id=%s"
                            + "&grant_type=authorization_code"
                            + "&scope=mail.send"
                            + "&code=%s"
                            + "&redirect_uri=%s",
                    ApplicationDetails.appID,
                    authCode,
                    ApplicationDetails.redirectURI);

            HttpURLConnection tokenRequest = seedu.address.logic.Request.send(ApplicationDetails.tokenEndpoint, "POST", tokenParams);
            List<String> response = seedu.address.logic.Request.read(tokenRequest);

            String s = response.get(0);
            String[] split = s.split(":");
            int i;
            for (i = 0; i < split.length; i++) {
                if (split[i].indexOf("access_token") != -1) {
                    break;
                }
            }
            String token = split[i + 1].substring(1, split[i + 1].length() - 2);

            String subject = outlookRequest.getSubject();
            String body = outlookRequest.getBody();
            String emailAdd = outlookRequest.getEmailAdd();

            String emailBody = "{\"Message\":{\"Subject\":\""
                    + subject + "\",\"Body\":{\"ContentType\":\"Text\",\"Content\":\""
                    + body + "\"},\"ToRecipients\":[{\"EmailAddress\":{\"Address\":\""
                    + emailAdd + "\"}}]},\"SaveToSentItems\":\"true\"}";


            HttpURLConnection emailRequest = Request.sendMail(token, ApplicationDetails.mailEndpoint, "POST", emailBody);
            emailRequest.connect();
            int responseCode = emailRequest.getResponseCode();
            String responseMsg = emailRequest.getResponseMessage();
            if (responseCode >= 400 && responseCode <= 499) {
                throw new Exception(responseMsg + " :: " + responseCode);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
