package Download;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

import Chen.DownloadImg;

public class TryThread implements Runnable{
	public File file;
	public int skip;
	public static Logger log=Logger.getLogger("tesmsg");
	public TryThread(File file,int skip){
		this.file=file;
		this.skip=skip;
	}
	public void run(){
		try{
			FileReader fr=new FileReader(file);
			BufferedReader br=new BufferedReader(fr);
			String src=null;
			for(int i=0;i<skip;i++)
				br.readLine();
			for(int i=skip;(src=br.readLine())!=null;i++){
				if(src.contains("ihostimg")||src.contains("bucket"))
					continue;
				String[] names=src.split("/");
				String name=names[names.length-1];
				try{
					TestCase.downloadImg(src, "TW", name);
				}
				catch(Exception e){
					log.info(src);
				}
			}				
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
