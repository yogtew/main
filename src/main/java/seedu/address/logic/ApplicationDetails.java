package seedu.address.logic;

public class ApplicationDetails {
    public static String appID = "ba267106-3b1d-4c68-895e-70353f8b4843";
    public static String authEndpoint = "https://login.microsoftonline.com/common/oauth2/v2.0/authorize";
    public static String authURL = "https://login.microsoftonline.com/common/oauth2/v2.0/authorize?client_id=ba267106-3b1d-4c68-895e-70353f8b4843&scope=Mail.send&response_type=code&response_mode=form_post&redirect_uri=http%3A%2F%2Flocalhost%3A8000%2Fcontact&prompt=consent";
    public static String tokenEndpoint = "https://login.microsoftonline.com/common/oauth2/v2.0/token";
    public static String mailEndpoint = "https://graph.microsoft.com/v1.0/me/sendMail";
    public static String redirectURI = "http%3A%2F%2Flocalhost%3A8000%2Fcontact";

    // sample: https://login.microsoftonline.com/common/oauth2/v2.0/authorize?client_id=20c72e9d-83fc-4186-a182-2c453b05ca18&scope=Mail.send
    // https://login.live.com/oauth20_desktop.srf?code=OAQABAAIAAAC5una0EUFgTIF8ElaxtWjTAytoik-2xQU3sp3OxehaxNXAzLpbUc7pfy5k164Xw5Khim6CXf_SrQkV24Tc0hV6s_GhJHqR9lrwj0qJQqICthjdgM9XWq6rJvifqP4V4O4ROVMFT8kgOg0NG1L1NAtAin6b4eP_d9EoFkGAfTyH6VDMsaInSUtNwtG8f6zcizetWBayn8jE3srb9x1U81pDot23XmYDyxYxkCl0wxvixyFvF2mI7xpYIMPRF0_k5vgjS67Lo-V1l5dBkvurkPG43mGBsGxpuXWf3Oc8MlOFgZvfj9LSSFlt4zxXteIqu5gaelzsYgXWDI8g-rHGoi99OeNykbnXxOocaNT19RsdzKRWgjY0dzN1Rqy-ufyEZWKbVeN0pwbZBR8jpmVsW9vhfN8Kt6jTfM043E56FKBSxzDLVCmQMewWt-usHWf7t-64Rk8QsjRyUWfpN_c1L6k6WPrUPo-t4Jglhi5SUMbD8x1jZn52ey45PvhwR-AKQgFNLmBgXDN5bu0P1Ur3VBBRJsSQcusO4ecbFZHN9yAx2uuX0vZT5iLJG9c5emLd2iHTQVo6FS6IDuekgGFgAdIXe9WzqHI0ZWgtt6pifd9DXSAA&session_state=ae71e912-ae76-4d0d-a671-f946e1c38de2
}

