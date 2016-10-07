package com.lib.sdk.googlepay;

import android.app.Activity;
import android.util.Log;

import com.lib.sdk.googlepay.util.Purchase;
import com.lib.x.SDKCenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class SynStorage {

    public static class Item{
        public String gameUserServer;//服务器id
        public String gameUserId;//用户id
        public String orderSerial;//开发者的订单号
        public String productId;//productId
        public float price;//价格
        int state;
        protected Purchase mPurchase; //google 支付所对应的Purchase对象，可能为空
        public String originalJson;
        public String signature;

        public Item(){
        }

        /**
         * 设置同步状态值
         * public static final int NotConsume = 100;//未消费
         ＊ public static final int NotSynServer = 101;//未和服务器同步
         * @param state
         */
        public void setState(int state){
            this.state = state;
        }

        /**
         * 设置为未消费状态
         */
        public void setNotConsumeState(){
            this.state = NotConsume;
        }

        /**
         * 设置为未与服务器同步状态
         */
        public void setNotSynServverState(){
            this.state = NotSynServer;
        }

        public boolean isNotConsume(){
            return this.state == NotConsume;
        }

        public boolean isNotSynServer(){
            return this.state == NotSynServer;
        }

        /**
         * 将同步项转为json，也就是序列化
         * @return
         */
        public JSONObject toJson(){
            JSONObject json = null;
            try {
                json = new JSONObject();
                if(null != gameUserServer)
                    json.put("gameUserServer", gameUserServer);
                if(null != gameUserId)
                    json.put("gameUserId", gameUserId);
                if(null != orderSerial)
                    json.put("orderSerial", orderSerial);
                if(null != productId)
                    json.put("productId", productId);
                if(null != originalJson)
                    json.put("originalJson", originalJson);
                if(null != signature)
                    json.put("signature", signature);

                json.put("state", state);
                json.put("price", price);

            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            return json;
        }

        /**
         * 从一个json对象中解析同步项，也就是反序列化
         * @param json
         */
        public void parse(JSONObject json){
            try {
                if(json.has("gameUserServer"))
                    gameUserServer = json.getString("gameUserServer");

                if(json.has("gameUserId"))
                    gameUserId = json.getString("gameUserId");

                if(json.has("orderSerial"))
                    orderSerial = json.getString("orderSerial");

                if(json.has("productId"))
                    productId = json.getString("productId");

                if(json.has("price"))
                    price = (float)json.getDouble("price");

                if(json.has("originalJson"))
                    originalJson = json.getString("originalJson");

                if(json.has("signature"))
                    signature = json.getString("signature");

                if(json.has("state"))
                    state = json.getInt("state");
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void setPurchase(Purchase purchase){
            mPurchase = purchase;
            originalJson = mPurchase.getOriginalJson();
            signature = mPurchase.getSignature();
        }

        public Purchase getPurchase(){
            return mPurchase;
        }
    }

    public void init(){
        mSynItemMap = new HashMap<String, Item>();
        JSONArray jsonArray = this.readFromFile();
        if(null == jsonArray)
            return;

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(null != jsonObject) {
                    Item item = new Item();
                    item.parse(jsonObject);
                    if(item.orderSerial != null)
                        mSynItemMap.put(item.orderSerial, item);
                }
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    /**
     * 添加一个同步项
     * @param newItem
     */
    public void add(Item newItem){
        if(mSynItemMap.containsKey(newItem.orderSerial)){
            return;
        }

        mSynItemMap.put(newItem.orderSerial, newItem);
    }

    /**
     * 移除一个同步项，通过开发者订单
     * @param devOrderId
     */
    public void remove(String devOrderId)	{
        if(mSynItemMap.containsKey(devOrderId)){
            mSynItemMap.remove(devOrderId);
        }
    }

    /**
     * 获取一个同步项，通过开发者订单号
     * @param devOrderId
     * @return
     */
    public Item get(String devOrderId){
        if(mSynItemMap.containsKey(devOrderId)){
            return mSynItemMap.get(devOrderId);
        }
        else{
            return null;
        }
    }

    /**
     * 保存,以json格式
     */
    public void save(){
        JSONArray jsonArray = new JSONArray();
        for(Item item : mSynItemMap.values()){
            JSONObject json = item.toJson();
            if(null != json) {
                jsonArray.put(json);
            }
        }
        this.writeToFile(jsonArray);
    }

    /**
     * 通过开发订单号移除并保存
     * @param devOrderId
     */
    public void removeAndSave(String devOrderId){
        this.remove(devOrderId);
        this.save();
    }

    /**
     * 添加一个同步项，并保存
     * @param newItem
     */
    public void addAndSave(Item newItem){
        this.add(newItem);
        this.save();
    }

    /**
     * 把一个json对象通过字符串的方式写到文件中
     * @param jsonObj
     */
    private void writeToFile(JSONArray jsonObj){
        try{
            String savePath = this.getSavePath();
            File file = new File(savePath);
            FileOutputStream w = new FileOutputStream(file);
            String strJson = jsonObj.toString();
            w.write(strJson.getBytes("UTF-8"));
            w.flush();
            w.close();
            this.log("write to file ");
        }catch(Exception e){
            e.printStackTrace();
            this.log("write to file exception");
        }
    }

    /**
     * 从已经保存的文件中读取漏单信息，返回一个json对象，可能为空
     * @return
     */
    private JSONArray readFromFile(){
        try{
            String savePath = this.getSavePath();
            File file = new File(savePath);
            if(!file.exists())
                return null;
            this.log("file size " + file.length());

            FileInputStream in = new  FileInputStream(file);
            StringBuffer sb = new StringBuffer();
            byte[] buffer = new byte[8192];
            while(true){
                int size = in.read(buffer, 0, 8192);
                if(size <= 0)
                    break;
                sb.append(new String(buffer, 0, size, "UTF-8"));
            }
            in.close();
            String strJson = sb.toString();
            this.log("readFromFile " + strJson);
            if(strJson.isEmpty())
                return null;
            JSONArray jsonArray = new JSONArray(strJson);
            File file2 = new File(savePath);
            this.log("readFromFile2 " + file2.length());
            return jsonArray;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取存储文件的全路径，包括文件名
     * @return
     */
    private String getSavePath(){
        Activity activity = SDKCenter.getInstance().getGameActivity();
        String savePath = activity.getFilesDir().getAbsolutePath();
        if(!savePath.isEmpty() && (savePath.charAt(savePath.length() - 1) != '/')){
            savePath += "/";
        }
        savePath += mFileName;
        return savePath;
    }

    /**
     * 获取说有的key集合，可能为空
     * @return
     */
    public Set<String> getKeys(){
        if(null == mSynItemMap)
            return null;
        return mSynItemMap.keySet();
    }

    private void log(String log){
        Log.d("GooglePayPurchase", "GooglePayPurchase " + log);
    }

    private String mFileName = "syn_storage"; //文件名
    protected  HashMap<String, Item> mSynItemMap; //通过开发者订单号作为key来存储同步信息

    public static final int NotConsume = 100;//未消费
    public static final int NotSynServer = 101;//未和服务器同步
}
