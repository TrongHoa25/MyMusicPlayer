package com.thesep.tronghoa.mymusicplayer.util.listener;

import com.thesep.tronghoa.mymusicplayer.data.Response;

public interface APICallback {
    void onResponse (Response response);
    void onFailure(Throwable throwable);
}
