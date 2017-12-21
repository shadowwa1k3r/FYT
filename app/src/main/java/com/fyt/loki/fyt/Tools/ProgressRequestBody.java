package com.fyt.loki.fyt.Tools;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * Created by ergas on 12/21/2017.
 */

public class ProgressRequestBody extends RequestBody {
    private InputStream mStream;
    private UploadCallbacks listener;
    private MediaType mMediaType;


    private static final int DEFAULT_BUFFER_SIZE=2048;

    public interface UploadCallbacks{
        void onProgressUpdate(int percentage);
    }

    public ProgressRequestBody(final InputStream file,final UploadCallbacks listener, MediaType mediaType){
        this.mStream=file;
        this.listener=listener;
        this.mMediaType=mediaType;
    }

    @Override
    public MediaType contentType(){
        return mMediaType;
    }

    @Override
    public long contentLength()throws IOException{
        return mStream.available();
    }

    @Override
    public void writeTo(BufferedSink sink)throws IOException{
        long fileLength=mStream.available();

        byte[] buffer=new byte[DEFAULT_BUFFER_SIZE];
        long uploaded=0;

        try {
            int read;

            while ((read=mStream.read(buffer))!=-1){
                if(listener!=null){
                    listener.onProgressUpdate((int)(100*uploaded/fileLength));
                }

                uploaded+=read;
                sink.write(buffer,0,read);

            }
        }
        finally {
            mStream.close();

        }
    }

}
