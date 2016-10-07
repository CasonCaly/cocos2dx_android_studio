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
        public Purchase purchase; //google 支付所对应的Purchase对象，可能为空

        public Item(){
        }

        public void setState(int state){
            this.state = state;
        }

        public void setNotConsumeState(){
            this.state = NotConsume;
        }

        public void setNotSynServverState(){
            this.state = NotSynServer;
        }

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
                json.put("price", price);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            return json;
        }

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
            }catch (JSONException e) {
                e.printStackTrace();
            }
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

    public void add(Item newItem){
        if(mSynItemMap.containsKey(newItem.orderSerial)){
            return;
        }

        mSynItemMap.put(newItem.orderSerial, newItem);
    }

    public void remove(String devOrderId)	{
        if(mSynItemMap.containsKey(devOrderId)){
            mSynItemMap.remove(devOrderId);
        }
    }

    public Item get(String devOrderId){
        if(mSynItemMap.containsKey(devOrderId)){
            return mSynItemMap.get(devOrderId);
        }
        else{
            return null;
        }
    }

    public void save(){
        JSONArray jsonArray = new JSONArray();
        for(Item item : mSynItemMap.values()){
            JSONObject json = item.toJson();
            if(null != json) {
                jsonArray.put(json);
                this.log("save put a json");
            }
        }
        this.writeToFile(jsonArray);
    }

    public void removeAndSave(String devOrderId){
        this.remove(devOrderId);
        this.save();
    }

    public void addAndSave(Item newItem){
        this.add(newItem);
        this.save();
    }

    //
    private void writeToFile(JSONArray jsonObj){
        try{
            String savePath = this.getSavePath();
            File file = new File(savePath);
            ObjectOutputStream w = new ObjectOutputStream(new FileOutputStream(file));
            w.writeObject(jsonObj.toString());
            w.flush();
            w.close();
            this.log("writeToFile finish array count " + jsonObj.length());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //
    private JSONArray readFromFile(){
        try{
            String savePath = this.getSavePath();
            File file = new File(savePath);
            if(!file.exists())
                return null;
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            String str = (String)in.readObject();
            JSONArray jsonArray = new JSONArray(str);
            this.log("readFromFile " + str);
            in.close();
            return jsonArray;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private String getSavePath(){
        Activity activity = SDKCenter.getInstance().getGameActivity();
        String savePath = activity.getFilesDir().getAbsolutePath();
        if(!savePath.isEmpty() && (savePath.charAt(savePath.length() - 1) != '/')){
            savePath += "/";
        }
        savePath += mFileName;
        return savePath;
    }

    public Set<String> getKeys(){
        if(null == mSynItemMap)
            return null;
        return mSynItemMap.keySet();
    }

    private void log(String log){
        Log.d("GooglePayPurchase", "GooglePayPurchase " + log);
    }

    private String mFileName = "syn_storage";
    protected  HashMap<String, Item> mSynItemMap;

    public static final int NotConsume = 100;//未消费
    public static final int NotSynServer = 101;//未和服务器同步
}
