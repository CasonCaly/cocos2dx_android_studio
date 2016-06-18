package com.lib.x;

import java.util.HashMap;
import android.os.Bundle;
import android.os.Message;

/**
 * 
 * @author zhuang yusong
 *
 */
public class AnalysisSDK extends ISDK{

	public AnalysisSDK() {
		super();
	}

	public void trackEvent(String event){
		
	}

	public void trackEvent(String eventId, String eventLabel){
		
	}

	public void trackEvent(String eventId, String eventLabel, HashMap<String, String> parameters){
		
	}

	public void handleMessage(Message msg){
		super.handleMessage(msg);
		int what = msg.what;
		Bundle data = msg.getData();
		String event = data.getString("event");
		if(AnalysisSDK.TrackEventMsg == what)
			this.trackEvent(event);
		else if(AnalysisSDK.TrackEvenLabelMsg == what){
			String label = data.getString("label");
			this.trackEvent(event, label);
		}
		else if(AnalysisSDK.TrackEventLabelParametersMsg == what){
			String label = data.getString("label");
			@SuppressWarnings("unchecked")
			HashMap<String, String> parameters = (HashMap<String, String>) data.getSerializable("parameter");
			this.trackEvent(event, label, parameters);
		}
	}
	
	protected void trackEventInGLThread(String event){
		Message msg = new Message();
		msg.what = TrackEventMsg;
		Bundle data = new Bundle();
		data.putString("event", event);
		this.sendMessage(msg);	
	}
	
	protected void trackEventLabelInGLThread(String event, String eventLabel){
		Message msg = new Message();
		msg.what = TrackEventMsg;
		Bundle data = new Bundle();
		data.putString("event", event);
		data.putString("label", eventLabel);
		this.sendMessage(msg);	
	}
	
	protected void trackEventLabelParameterInGLThread(String event, String eventLabel, HashMap<String, String> parameters){
		Message msg = new Message();
		msg.what = TrackEventMsg;
		Bundle data = new Bundle();
		data.putString("event", event);
		data.putString("label", eventLabel);
		data.putSerializable("parameter", parameters);
		this.sendMessage(msg);	
	}
	
	private final static int TrackEventMsg = 1;
	
	private final static int TrackEvenLabelMsg = 2;
	
	private final static int TrackEventLabelParametersMsg = 3;
}
