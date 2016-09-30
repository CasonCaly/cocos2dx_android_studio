package com.lib.sdk.googlepay;

import java.io.*;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class SynStorage {

    public static final int NotConsume = 100;//未消费
    public static final int NotSynServer = 101;//未和服务器同步

    public class Item{
        public String serverId;//服务器id
        public String userId;//用户id
        public String devOrderId;//开发者的订单号
        public String skn;//productId
        public float price;//价格
        int state;

        public void setState(int state){
            this.state = state;
        }
    }

    protected  HashMap<String, Item> mSynTtemMap;

    public void remove(String sku)	{
        if(mSynTtemMap.containsKey(sku)){
            mSynTtemMap.remove(sku);
        }
    }

    public void save(){

    }

    public Item getItem(String sku){
        if(mSynTtemMap.containsKey(sku)){
            return mSynTtemMap.get(sku);
        }
        else{
            return null;
        }
    }

    public void jsonWrite(String file){
//        try {
//            //ITEM_TYPE_INAPP,gpkey,payload,reqid
//            JSONArray jsonarr=new JSONArray();
//            for(Purchase p:mPurchaseMap.values()){
//                JSONObject json=new JSONObject();
//                json.put("type",p.getItemType());
//                json.put("gpkey",p.getSku());
//                json.put("payload",p.getDeveloperPayload());
//                json.put("reqid",p.getRequestCode());
//                json.put("state", p.getState());
//                jsonarr.put(json);
//            }
//            this.write(jsonarr.toString(), file);
//            Log.e(TAG, "save json "+jsonarr.toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    public void write(String cnt,String file){
        try	{
            FileWriter fw = new FileWriter(file);
            fw.write(cnt);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void jsonReader(String file){
//        try {
//            String jsonstr = this.reader(file);
//            Log.e(TAG, "read json"+jsonstr.toString());
//            mPurchaseMap = new HashMap<Integer,Purchase>();
//            JSONArray jsonarr=new JSONArray(jsonstr);
//            for(int i=0;i<jsonarr.length();i++){
//                JSONObject json=(JSONObject)jsonarr.get(i);
//                Purchase p=new Purchase(json.getString("type"),json.getString("gpkey")
//                        ,json.getString("payload"),json.getInt("reqid"));
//                p.setState(json.getInt("state"));
//                mPurchaseMap.put(json.getInt("reqid"), p);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    public String reader(String file){
        try	{
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String s;
            String jsonstr="";
            while ((s = br.readLine()) != null) {
                jsonstr = jsonstr + s;
            }
            br.close();
            fr.close();
            return jsonstr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    //
    public void writeo(Object o,String file){
        try{
            ObjectOutputStream w = new ObjectOutputStream(new FileOutputStream(file));
            w.writeObject(o);
            w.flush();
            w.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //
    public Object readero(String file){
        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            Object o = in.readObject();
            in.close();
            return o;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
