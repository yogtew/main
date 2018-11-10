package seedu.address.logic;

/**
 * Details on application registration under Azure, and necessary endpoints
 */
public class ApplicationDetails {
    public static String getAppId() {
        return appId;
    }

    public static String getAuthUrl() {
        return authUrl;
    }

    public static String getTokenEndpoint() {
        return tokenEndpoint;
    }

    public static String getMailEndpoint() {
        return mailEndpoint;
    }

    public static String getRedirectUri() {
        return redirectUri;
    }

    private static String appId = "ba267106-3b1d-4c68-895e-70353f8b4843";
    private static String authUrl = "https://login.microsoftonline.com/common/oauth2/v2.0/authorize?client_id=ba267106"
            + "-3b1d-4c68-895e-70353f8b4843&scope=Mail.send&response_type=code&response_mode=form_post&redirect_uri="
            + "http%3A%2F%2Flocalhost%3A8000%2Fcontact&prompt=consent";
    private static String mailEndpoint = "https://graph.microsoft.com/v1.0/me/sendMail";
    private static String redirectUri = "http%3A%2F%2Flocalhost%3A8000%2Fcontact";
    private static String tokenEndpoint = "https://login.microsoftonline.com/common/oauth2/v2.0/token";

}

