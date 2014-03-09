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
package data;

import java.util.LinkedList;

public class WebVTTModule {

	private boolean isReady=false;

	private int currentPosition=0;
	private LinkedList<WebVTTData> subtitles=new LinkedList<WebVTTData>();
	
	
	public WebVTTModule(){
	}
	
	protected void clear(){
		currentPosition=0;
		subtitles.clear();
	}
	
	public boolean isEmpty(){
		return subtitles.isEmpty();
	}
	
	public void addWebVTTData(WebVTTData subtitle){
		subtitles.add(subtitle);
	}
	
	public WebVTTData getWebVTTData(){
		
		if(currentPosition>=subtitles.size()||currentPosition<0){
			return null;
		}
		currentPosition++;
		return subtitles.get(currentPosition);
	}

	
	public void updateToStartInTime(long time){
		int direction=0;
		int directionlast=0;
		if(currentPosition>=subtitles.size()||currentPosition<0){
			return;
		}
		do{
			direction=subtitles.get(currentPosition).compreWithTime(time);			
			if(direction==0){
				return;}			
			currentPosition=currentPosition+direction;
			if(direction+directionlast==0){return;}
			directionlast=direction;
		}while(currentPosition>0&&currentPosition<subtitles.size());
	}
	
	public void setReady(){
		isReady=true;
	}
	
	public boolean isReady(){
		return isReady;
	}
}
