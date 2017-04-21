import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by andrew on 4/20/17.
 */
//amg stands for ASUS MSI Gigabyte & the famout Apple
public class AMGCrawler {
	String url_MSI="http://www.pro-star.com/index.php?r=product/index&filter=32";
	String url_ASUS="http://www.pro-star.com/index.php?r=product/index&filter=6";
	String url_Gigabyte="http://www.pro-star.com/index.php?r=product/index&filter=30";
	String url_Apple_1="https://www.apple.com/macbook/specs/";
	String url_Apple_2="https://www.apple.com/macbook-air/specs/";
	String url_Apple_3="https://www.apple.com/macbook-pro/specs/";


	public void Crawl(){
		String dump_file_name, dump_file_content;
		File page_dump;

		FileWriter fileWriter;
		BufferedWriter bufferedFileWriter;

		HashMap<String, String > target_urls = new HashMap<String,String>();
		target_urls.put("MSI",url_MSI);
		target_urls.put("ASUS",url_ASUS);
		target_urls.put("Gigabyte",url_Gigabyte);
		target_urls.put("Apple1",url_Apple_1);
		target_urls.put("Apple2",url_Apple_2);
		target_urls.put("Apple3",url_Apple_3);

		Iterator it = target_urls.entrySet().iterator();
		try{
			File dump_dir = new File("AMG");
			if(dump_dir.exists())
				FileUtils.deleteDirectory(dump_dir);
			dump_dir.mkdir();
		}catch (Exception e){
			System.out.println("Exception: " + e);
			e.printStackTrace();
		}

		while(it.hasNext())
		{
			Map.Entry tuple = (Map.Entry) it.next();
			dump_file_name = tuple.getKey()+"Content";
			page_dump = new File("AMG/" + dump_file_name);

			try{
				if (page_dump.exists())
					FileUtils.forceDelete(page_dump);
				page_dump.createNewFile();
				fileWriter = new FileWriter(page_dump);
				bufferedFileWriter = new BufferedWriter(fileWriter);
				dump_file_content = Jsoup.connect(tuple.getValue().toString()).get().html();
				bufferedFileWriter.write(dump_file_content);
				bufferedFileWriter.flush();
				bufferedFileWriter.close();
				fileWriter.close();
			} catch (Exception e) {
				System.out.println("Exception: " + e);
				e.printStackTrace();
			}
		}

	}

}
