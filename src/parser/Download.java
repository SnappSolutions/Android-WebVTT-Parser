/**
 * The MIT License (MIT)

Copyright (c) 2014 Snapp Solutions Ltd

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import data.WebVTTData;
import data.WebVTTModule;

import android.os.AsyncTask;
import android.util.Log;


public class Download extends AsyncTask<Void, Void, String> {
	
	private static final String TIME_MATCHER="(\\d+):(\\d+):(\\d+).(\\d+)\\s[-][-][>]\\s(\\d+):(\\d+):(\\d+).(\\d+)";
    private static final String SPLIT_TIME_MATCHER="-->";
	
	private File file;
	private final WebVTTModule webVTTModule;

	public Download(File file, final WebVTTModule webVTTModule) {	    
		this.file=file;
		this.webVTTModule=webVTTModule;
	}

	@Override
	protected String doInBackground(Void... params) {
 	try {

 		FileInputStream input =new FileInputStream(file); 
        InputStreamReader isr=new InputStreamReader(input);
    	BufferedReader in = new BufferedReader(isr);
    	    String str=null;
    	    String[]table;
    	    Long startTime=null;
    	    Long endTime=null;
    	    String text=null;
    	    while ((str = in.readLine()) != null) {
    	    	if(str.matches(TIME_MATCHER)){
    	    		table=str.split(SPLIT_TIME_MATCHER);
    	    		if(table.length==2){
    	    			startTime=getASSTime(table[0].trim());
    	    			endTime=getASSTime(table[1].trim());    	    	    			
    	    		}
    	    		text = in.readLine();
    	    		str = in.readLine();
    	    		if(str!=""&&str!="\n"){
    	    			text=text+str;
    	    		}
    	    		webVTTModule.addWebVTTData(new WebVTTData(startTime, endTime, text));
    	    	}
    	        
    	    }
    	    in.close();
    	    webVTTModule.setReady();
	} catch (IOException e) {
		Log.d("DownloadSubtitle", "SUBTITLE_DOWNLOAD_ERROR" + e.getMessage());
	}
 	return null;
	}

	protected void onPostExecute(String result) {}
	
	 private static long getASSTime(String ss)
	    {
	        //0:00:03.38;
	        String[] start = ss.split(":");
	        long h = Integer.parseInt(start[0]) * 60 * 60 * 1000;
	        long m = Integer.parseInt(start[1]) * 60 * 1000;
	        String[] seconds = start[2].split("\\.");
	        long s = Integer.parseInt(seconds[0]) * 1000;
	        long ms = Integer.parseInt(seconds[1].replaceAll("[^0-9.]","")) * 10;
	        return h + m + s + ms;
	    }
	
}
