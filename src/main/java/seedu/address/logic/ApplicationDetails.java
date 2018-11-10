package seedu.address.logic;

/**
 * Details on application registration under Azure, and necessary endpoints
 */
public class ApplicationDetails {

    public static final String appId = "ba267106-3b1d-4c68-895e-70353f8b4843";
    public static final String authUrl = "https://login.microsoftonline.com/common/oauth2/v2.0/authorize?client_id=" +
            appId + "&scope=Mail.send&response_type=code&response_mode=form_post&redirect_uri="
            + "http%3A%2F%2Flocalhost%3A8000%2Fcontact&prompt=consent";
    public static final String mailEndpoint = "https://graph.microsoft.com/v1.0/me/sendMail";
    public static final String redirectUri = "http%3A%2F%2Flocalhost%3A8000%2Fcontact";
    public static final String tokenEndpoint = "https://login.microsoftonline.com/common/oauth2/v2.0/token";


}

