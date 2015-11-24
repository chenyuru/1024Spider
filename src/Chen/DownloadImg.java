package Chen;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
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
public class DownloadImg {
	public static Logger log=Logger.getLogger("tesmsg");
	public static HashMap<String, String> map=new HashMap<String, String>();
	public static String getUrlAsString(String url) throws HttpException, IOException{
		log.info("getUrlAsString");
		HttpClient client = new HttpClient();		
		GetMethod gm=new GetMethod(url);
		gm.getParams().setContentCharset("gb2312"); 
		gm.addRequestHeader("Accept","text/html, application/xhtml+xml, */*");
		gm.addRequestHeader("Accept-Encoding","gzip, deflate");
		gm.addRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko");
		gm.addRequestHeader("Host","cl.loei.pw");
		client.executeMethod(gm);		
		GZIPInputStream gin=new GZIPInputStream(gm.getResponseBodyAsStream());
		BufferedReader reader = new BufferedReader(new InputStreamReader(gin,"gb2312"));				
		map.put("__cfduid", gm.getResponseHeader("Set-Cookie").getValue().trim().split(";")[0].split("=")[1]);  
		StringBuffer stringBuffer = new StringBuffer();  
		String str = "";  
		while((str = reader.readLine())!=null){  
		    stringBuffer.append(str);  
		}  
		return stringBuffer.toString();
	}
	public static String getSecondUrlAsString(String url) throws HttpException, IOException{
		log.info("getSecondUrlAsString");
		HttpClient client = new HttpClient();		
		GetMethod gm=new GetMethod(url);
		gm.getParams().setContentCharset("gb2312"); 
		gm.addRequestHeader("Accept","text/html, application/xhtml+xml, */*");
		gm.addRequestHeader("Accept-Encoding","gzip, deflate");
		gm.addRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko");
		gm.addRequestHeader("Host","cl.loei.pw");
		gm.addRequestHeader("Referer","http://cl.loei.pw/thread0806.php?fid=16&search=&page=1");
		gm.addRequestHeader(new Header("Cookie", map.get("__cfduid").toString()));
		client.executeMethod(gm);		
		GZIPInputStream gin=new GZIPInputStream(gm.getResponseBodyAsStream());
		BufferedReader reader = new BufferedReader(new InputStreamReader(gin,"gb2312"));				
		StringBuffer stringBuffer = new StringBuffer();  
		String str = "";  
		while((str = reader.readLine())!=null){  
		    stringBuffer.append(str);  
		}  
		return stringBuffer.toString();
	}
	public static ArrayList<String> getAllTitleUrl(String url) throws HttpException, IOException{
		log.info("getAllTitleUrl");
		Document doc=Jsoup.parse(getUrlAsString(url));
		Elements eles=doc.getElementsByClass("tr3");
		ArrayList<String> titles=new ArrayList<String>();
		String pre="http://cl.loei.pw/";
		for(int i=1;i<eles.size();i++){
			Element ele=eles.get(i);
			String re=pre+ele.child(0).child(0).attr("href");
			if(re.length()>25)
				titles.add(re);
		}
		return titles;
	}
	public static ArrayList<String> getAllImgUrl(String html){
		log.info("getAllImgUrl");
		ArrayList<String> result=new ArrayList<String>();
		Document doc=Jsoup.parse(html);
		Elements eles=doc.getElementsByTag("input");
		for(Element e:eles){
			String src=e.attr("src");
			if(!src.equals("")){
				result.add(src);
			}
		}
		return result;
	}
	public static void downloadImg(String url,String storeFileName,String imgName) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		GetMethod gm=new GetMethod(url);
		try{
			client.executeMethod(gm);
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
	public static void main(String[] args) throws HttpException, IOException{
		ArrayList<String> imgs=new ArrayList<String>();
		File file=new File("TEST\\se.txt");
		for(int i=10;i<100;i++)
		{	
			log.info(i+"");
			ArrayList<String> result=getAllTitleUrl("http://cl.loei.pw/thread0806.php?fid=16&search=&page="+i);
			log.info("getresult");
			for(String title:result){			
				imgs.addAll(getAllImgUrl(getSecondUrlAsString(title)));
			}				
			FileWriter fw=new FileWriter(file,true);
			for(String s:imgs){
				fw.write(s+"\n");
				log.info("write");
			}
			fw.close();
		}
	}
}
 