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

import java.util.Comparator;

public class WebVTTData implements Comparable<WebVTTData>
{
    private long startTimeMs = 0;
    private long endTimeMs = -1;
    private String text;

    
    public WebVTTData(long startTimeMs, long endTimeMs, String text) {
        this.startTimeMs = startTimeMs;
        this.endTimeMs = endTimeMs;
        if(text == null)
            text ="";
        this.text = text;
    }

    public long getStartTimeMs() {
        return startTimeMs;
    }

    public long getEndTimeMs() {
        return endTimeMs;
    }

    protected void adjustOffsetMs(long timeOffsetMs) {
        startTimeMs += timeOffsetMs;
        endTimeMs += timeOffsetMs;
    }

    public String getText() {
        return text;
    }
    
    public int compreWithTime(long time){
    	if(time<startTimeMs)return -1;
    	if(time>endTimeMs)return 1;
    	return 0;
    }

    private final static Comparator<WebVTTData> subtitleDataSetComparator = new Comparator<WebVTTData>() {
        public int compare(WebVTTData info1, WebVTTData info2)
        {
        	long time1 = info1.startTimeMs;
        	long time2 = info2.startTimeMs;
            if (time1 < time2) {
                return -1;
            } else if (time1 > time2) {
                return 1;
            }
            return 0;
        }
    };

    public final static Comparator<WebVTTData> getComparator() {
        return subtitleDataSetComparator;
    }

    private static String timestampMsToString(long timestampMs) {
    	long hh = timestampMs/3600000;
        timestampMs = timestampMs % 3600000;
        long mm = timestampMs/60000;
        timestampMs = timestampMs % 60000;
        long ss = timestampMs / 1000;
        timestampMs = timestampMs % 1000;
        long uuu = timestampMs;
        return String.format("%d:%02d:%02d:%03d", hh, mm, ss, uuu);
    }

    @Override
    public String toString() {
        return "[" + timestampMsToString(startTimeMs) + "-" + timestampMsToString(endTimeMs) + "]" + text;
    }

    @Override
    public int compareTo( WebVTTData arg0 )
    {
        return subtitleDataSetComparator.compare( this, arg0 );
    }
}
