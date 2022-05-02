package com.jayam.appservice.utility;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class AppServiceUtility {

	public static final Logger LOGGER = LoggerFactory.getLogger(AppServiceUtility.class);

	private static final String[] IP_HEADER_CANDIDATES = { 
		    "X-Forwarded-For",
		    "Proxy-Client-IP",
		    "WL-Proxy-Client-IP",
		    "HTTP_X_FORWARDED_FOR",
		    "HTTP_X_FORWARDED",
		    "HTTP_X_CLUSTER_CLIENT_IP",
		    "HTTP_CLIENT_IP",
		    "HTTP_FORWARDED_FOR",
		    "HTTP_FORWARDED",
		    "HTTP_VIA",
		    "REMOTE_ADDR" };
	private final static String LOCALHOST_IPV4 = "127.0.0.1";
	private final static String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";
	
	public static int getRandomNumberInRange(int min, int max) {

		Random r = new Random();
		return r.ints(min, (max + 1)).findAny().getAsInt();

	}	
	
	
	public static List<Integer> generateNumbersList(int numberIntegers){
		List<Integer> list =new ArrayList<Integer>(); 
		for(int i=0;i<numberIntegers;i++){
			list.add(getRandomNumberInRange( 5,  20));
		}
		return list;		
		
	}
	
	public static Integer getAnswer(String userInput){
	        
		LOGGER.info(" userInput :"+userInput);
	        Pattern pattern = Pattern.compile(AppConstants.LAST_MATCH_PATTERN);
	        Matcher matcher = pattern.matcher(userInput);
	        int sum = 0;
	        if(matcher.find()) {          
	            sum = Integer.valueOf(matcher.group().toString());
	        }
	        return sum;
	}
	
	public static Integer getNumberTotal(String userInput){
        
        Pattern pattern = Pattern.compile(AppConstants.NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(userInput);
        int sum = 0;
        while( matcher.find() ) {
        	sum +=Integer.valueOf(matcher.group());
        }
        LOGGER.info("total Sum : "+sum);
          return sum;
		}
	
	


		public static String getClientIpAddress(HttpServletRequest request) {
		    for (String header : IP_HEADER_CANDIDATES) {
		        String ip = request.getHeader(header);
		        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
		            return ip;
		        }
		    }
		    return request.getRemoteAddr();
		}
		
		public static String getClientIp(HttpServletRequest request) {
			String ipAddress = request.getHeader("X-Forwarded-For");
			if(StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("Proxy-Client-IP");
			}
			
			if(StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("WL-Proxy-Client-IP");
			}
			
			if(StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getRemoteAddr();
				if(LOCALHOST_IPV4.equals(ipAddress) || LOCALHOST_IPV6.equals(ipAddress)) {
					try {
						InetAddress inetAddress = InetAddress.getLocalHost();
						ipAddress = inetAddress.getHostAddress();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
				}
			}
			
			if(!StringUtils.isEmpty(ipAddress) 
					&& ipAddress.length() > 15
					&& ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
			
			return ipAddress;
		}
}
