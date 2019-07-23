package com.thesep.tronghoa.mymusicplayer.data;

public class Response {
    private int mStatusCode;
    private StringBuffer mResult;

    public int getmStatusCode() {
        return mStatusCode;
    }

    public StringBuffer getmResult() {
        return mResult;
    }

    public Response(int mStatusCode, StringBuffer mResult) {
        this.mStatusCode = mStatusCode;
        this.mResult = mResult;

    }
}
