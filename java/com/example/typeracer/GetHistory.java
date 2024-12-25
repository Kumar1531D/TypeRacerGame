package com.example.typeracer;

public class GetHistory {
	
	public static String get(String h) {
		int ans = 0;
		
		for(int i=h.length()-1;i>=0;i--) {
			if(h.charAt(i)=='f') {
				ans = i;
				break;
			}
		}
		
		return h.substring(ans,h.length());
	}

}
