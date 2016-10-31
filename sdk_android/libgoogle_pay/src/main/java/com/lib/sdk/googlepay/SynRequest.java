package com.lib.sdk.googlepay;

import android.os.AsyncTask;
import android.util.Log;

import com.lib.sdk.googlepay.util.Purchase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public  class SynRequest extends AsyncTask<Void,Void,Void > {

    public interface Callback{
        public void didComlection(SynRequest request, boolean success, int errorCode, String error);
    }

    public SynRequest(GooglePayPurchase googlePurchase, SynStorage.Item item, Callback callback){
        super();
        mCallback = callback;
        mItem = item;
        mGooglePurchase = googlePurchase;
    }

    public SynStorage.Item  getItem(){
        return mItem;
    }

    public  void startSyncConsumeResult(){
        mPostJson = new JSONObject();
        try {
            mPostJson.put("signature", mItem.signature);//
            mPostJson.put("purchase", mItem.originalJson);
            this.execute();
        }
        catch (JSONException e){

        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            String strJson = mPostJson.toString();
            byte[] byteJson = strJson.getBytes();

            String strurl = mGooglePurchase.getPayUrl();
            URL url = new URL(strurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");// 提交模式
            conn.setConnectTimeout(10000);//连接超时 单位毫秒
            conn.setReadTimeout(10000);//读取超时 单位毫秒
            conn.setDoOutput(true);// 是否输入参数
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Length", String.valueOf(byteJson.length));
            conn.setRequestProperty("Content-Type", "plain/text; charset=UTF-8");

            OutputStream outStream = conn.getOutputStream();
            outStream.write(byteJson, 0, byteJson.length);// 输入参数
            outStream.flush();
           

            mStatusCode = conn.getResponseCode();
            if(200 == mStatusCode) {
                InputStream inStream = conn.getInputStream();
                StringBuffer sb = new StringBuffer();
                byte[] buffer = new byte[8192];
                while (true) {
                    int readSize = inStream.read(buffer, 0, 8192);
                    if (readSize <= 0)
                        break;
                    sb.append(new String(buffer, 0, readSize));
                }

                try {
                    String resultJson = sb.toString();
                    this.log("SynRequest " + resultJson);
                    mResultJson = new JSONObject(resultJson);
                } catch (JSONException e) {
                    mError = e.getLocalizedMessage();
                    mResultJson = null;
                    this.log("SynRequest 90" + e.getLocalizedMessage());
                }
            }
            outStream.close();
        }
        catch(MalformedURLException e){
            mError = e.getLocalizedMessage();
            mResultJson = null;
            this.log("SynRequest 96"+e.getLocalizedMessage());
            e.printStackTrace();
        }
        catch(ProtocolException e){
            mError = e.getLocalizedMessage();
            mResultJson = null;
            this.log("SynRequest 101"+e.getLocalizedMessage());
            e.printStackTrace();
        }
        catch (IOException e){
            mError = e.getLocalizedMessage();
            mResultJson = null;
            this.log("SynRequest 106"+e.getLocalizedMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void params) {
        this.log("onPostExecute ");
        if(null == mCallback)
            return;
        if(null != mError) {
            mCallback.didComlection(this, false, -1, mError);
            this.log("onPostExecute 118"+mError);
        }
        else{
            if(mStatusCode != 200){
                mCallback.didComlection(this, false, -1, "Response code "+mStatusCode);
                this.log("onPostExecute Response code "+mStatusCode);
                return;
            }

            if(null == mResultJson) {
                mCallback.didComlection(this, false, -1, "Json error");
                this.log("onPostExecute json error 123");
                return;
            }

            if(!mResultJson.has("errcode")) {
                mCallback.didComlection(this, false, -1, "Error code not found");
                this.log("onPostExecute Error code not found 129");
                return;
            }

            int error = mResultJson.optInt("errcode");
            if(0 != error) {//0 表示正常
                String errmsg = mResultJson.optString("errmsg");
                mCallback.didComlection(this, false, error, errmsg);
                this.log("onPostExecute 137"+errmsg);
                return;
            }
            mCallback.didComlection(this, true, error, null);
        }
    }

    private void log(String log){
        Log.d("GooglePayPurchase", "GooglePayPurchase " + log);
    }
    protected Callback mCallback;
    protected SynStorage.Item mItem;
    protected JSONObject mPostJson;
    protected JSONObject mResultJson;
    protected String mError;
    protected int mStatusCode;
    GooglePayPurchase mGooglePurchase;
}
