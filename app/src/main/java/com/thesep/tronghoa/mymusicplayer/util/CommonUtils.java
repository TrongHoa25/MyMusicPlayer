package com.thesep.tronghoa.mymusicplayer.util;

import com.thesep.tronghoa.mymusicplayer.BuildConfig;

public class CommonUtils {

    public static final String URL_SERVER = "http://api.soundcloud.com";
    public static final String AUTHORIZED_SERVER = "?" + KeyParams.CLIENT_ID + "&" + BuildConfig.API_KEY;
    public static final String API_TRACKS = CommonUtils.URL_SERVER
            + APIReference.TRACKS + AUTHORIZED_SERVER + "&";

    public interface APIReference {
        String TRACKS = "/tracks";
    }

    public interface KeyParams{
        String CLIENT_ID = "client_id=";
        String TAGS = "tags=";
        String LIMIT = "limit=";
        String OFFSET = "offset=";
        String ORDER = "order=";
        String GENRES = "genres=";
    }

    public static <T> boolean checkNotNull(T reference) {
        return reference != null && !reference.equals("") && !reference.equals("null");
    }
}
