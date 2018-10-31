package seedu.address.logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
    public static void sendMail(OutlookRequest outlookRequest) {

        try {

            String url = "https://outlook.office.com/api/v2.0/me/sendmail";

            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            String basicAuth = "Bearer EwA4A+l3BAAUv0lYxoez7x2t6RowHa2liVeLW/wAAZv01ygjLaIsxm+"
                    + "uty3OAuwW0rqZEKW1mTT5dDr/GuAx3zDGSrVl5o/CgN6BeS9U7mesRgnkUNsLwYRoVDcoz/hA2yvz204My"
                    + "FnXo86xT4PmkbshXrpkl2cSkbyy8DxzQTO60K3qRXYsEI5Ypn0Kqvx4OC+hAo7QnWSrQcNkMPry5BpI3/50f8P4"
                    + "GUu3G0E2tlsEEOQtRmaUu0udvJHonSXmGLIJN6zC7N2+LQ6w/SneP474D/Zn1ujGcpVDBMJWEns44V0S5/q5eKxgc1"
                    + "zlnAFf6GbWYJMJ/VOVSHxGAG9BjZ4OlCENPA8K2oNj7jjzc8GCC9Su4hDdxBbYx4Z/yS4DZgAACDJWRudcrPxBCAI3"
                    + "vNfEGZwb091B6iRzpToU+F+uc6EMqspQ5SmXHzgF0qYi1e1pSSRbnKWYjUd5zWnbXu46s9VT1EfmCZ0CM1NrIsw0lpmr"
                    + "5KWHqAwJPXCuENsXJ8iVnFbdNix3JRKGqgP/4PXu8fd8ICO29JmoeDNKlmmSuB0iFXBzR75ETmLNd3mZ26sN0x91ICxc"
                    + "xSu/fFwyZzx39byFg1VgTULVH4PHvwc1jSEUO8oV8T2fidb3WAwFoePWc/HyR7PRPa0r99uUtPK5BuXyUpE66Cy0RjL4o"
                    + "B+GKw5LR4flKwPHuZAqUa0NjPqu1Wur8TmmQrJwqDNc9FjHNvum95muBtIUKrP7tmIB74uIwVYK2zk4gURyM3yqdjbuj"
                    + "8+RvAVFykqhu/bNVhJMI7Beg3+xlkDHGB9qhHrCTolwZ7VfVR6+9P/nV2rOASt5pK9PBZ5wZSucAjjMQ04MhtokWJAA"
                    + "7mKpn5F0pakxi4LnQLU90vwD68B+OrEBtCj9FSbOtD1vQCwU51HKtI1QW40kzEddsbfyzrDqw0+1iEDt0PyEuoVzit2"
                    + "d5uiwL/ABxPQwEGY4g33wzgBceinG2clZeriOWAbaGAG7e6O99ZaeM2XcE3/5/236i7K9Wqq0/yDELDWklmQD2X8UFq"
                    + "s/4C8fHjsuSU+8lAwQvfIuYA1FFE2LWX/tAK4Tazy5uF4N68vtMwI=";

            conn.setRequestProperty ("Authorization", basicAuth);
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

}
