package Download;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class TestCase {
	public static Logger log=Logger.getLogger("tesmsg");
	public static void downloadImg(String url,String storeFileName,String imgName) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		client.setConnectionTimeout(10000);
		client.setTimeout(10000);
		client.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
		GetMethod gm=new GetMethod(url);		
		gm.addRequestHeader("Host",url.split("/")[2]);
		gm.addRequestHeader("Accept","text/html, application/xhtml+xml, */*");
		gm.addRequestHeader("Accept-Encoding",	"gzip, deflate");
		gm.addRequestHeader("Connection","Keep-Alive");
		gm.addRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko");
		log.info(client.executeMethod(gm)+"");
		File f=new File(storeFileName);
		if(!f.exists()){
			f.mkdirs();
		}
		File storeFile = new File(storeFileName+"\\"+imgName + ".jpeg");
        FileOutputStream output = new FileOutputStream(storeFile);
        InputStream is=gm.getResponseBodyAsStream();
        int temp=0;
        byte[] bytes=new byte[10*1024];
        log.info("begin writing");
        while( (temp=is.read(bytes))>0){
        	output.write(bytes,0,temp);
        }
        log.info("after writing");
        is.close();
        output.close();
	}
	public static void main(String[] args) throws HttpException, IOException {
		File file=new File("TEST\\se2.txt");   //url file name
		ExecutorService executor = Executors.newFixedThreadPool(50);
		ArrayList<Thread> threadArray=new ArrayList<Thread>();
		for(int i=0;i<500;i++){
			executor.submit(new Thread(new TryThread(file,50*1000)));
		}
		executor.shutdown();
	}
		
}
