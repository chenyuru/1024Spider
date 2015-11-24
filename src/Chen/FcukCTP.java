package Chen;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;
import java.nio.ByteBuffer.*;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.params.AllClientPNames;
import org.apache.http.util.EntityUtils;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.util.zip.*;

public class FcukCTP {
	public static Logger log=Logger.getLogger("tesmsg");
	public static void downloadImg(String url,String storeFileName,String imgName) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		GetMethod gm=new GetMethod(url);
		//gm.addRequestHeader("Host","chuantupian.com");
		gm.addRequestHeader("Host","g1.ihostimg.com");
//		gm.addRequestHeader("Host","showss.net");
//		gm.addRequestHeader("Host","img.picuphost.com");
		gm.addRequestHeader("Accept","text/html, application/xhtml+xml, */*");
		gm.addRequestHeader("Accept-Encoding",	"gzip, deflate");
		gm.addRequestHeader("Connection","Keep-Alive");
		gm.addRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko");
		try{
			log.info(client.executeMethod(gm)+"");
		}

		catch(HttpException he){
			log.info("here");
		}
		File f=new File(storeFileName);
		if(!f.exists()){
			f.mkdirs();
		}
		File storeFile = new File(storeFileName+"\\"+imgName + ".jpeg");
        FileOutputStream output = new FileOutputStream(storeFile);
        InputStream is=gm.getResponseBodyAsStream();
        int temp=0;
        while( (temp=is.read())!= -1){
        	
        	output.write(temp);
        }
        output.close();
	}
	public static void main(String[] args) throws HttpException, IOException {
		FcukCTP.downloadImg("http://g1.ihostimg.com/g1/201511090454056wa14.jpeg", "TEST", "ctp");

	}

}
