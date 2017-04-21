import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Created by andrew on 4/20/17.
 */
//amg stands for ASUS MSI Gigabyte
public class AMGCrawler {
	String url_MSI="http://www.pro-star.com/index.php?r=product/index&filter=32";
	String url_ASUS="http://www.pro-star.com/index.php?r=product/index&filter=6";
	String url_Gigabyte="http://www.pro-star.com/index.php?r=product/index&filter=30";


	public void Crawl(){
		String dump_file_name;
		File page_dump;

		FileWriter fileWriter;
		BufferedWriter bufferedFileWriter;

		try{
			String content_MSI = Jsoup.connect(url_MSI).get().html();
			String content_ASUS = Jsoup.connect(url_ASUS).get().html();
			String content_Gigab = Jsoup.connect(url_Gigabyte).get().html();

			File dump_dir = new File("AMG");
			if(dump_dir.exists())
				FileUtils.deleteDirectory(dump_dir);
			dump_dir.mkdir();

			dump_file_name = "MSI_Content";
			page_dump=new File("AMG/"+dump_file_name);
			if(page_dump.exists())
				FileUtils.forceDelete(page_dump);
			page_dump.createNewFile();
			fileWriter = new FileWriter(page_dump);
			bufferedFileWriter = new BufferedWriter(fileWriter);
			bufferedFileWriter.write(content_MSI);
			bufferedFileWriter.flush();
			bufferedFileWriter.close();
			fileWriter.close();

			dump_file_name = "ASUS_Content";
			page_dump=new File("AMG/"+dump_file_name);
			if(page_dump.exists())
				FileUtils.forceDelete(page_dump);
			page_dump.createNewFile();
			fileWriter = new FileWriter(page_dump);
			bufferedFileWriter = new BufferedWriter(fileWriter);
			bufferedFileWriter.write(content_ASUS);
			bufferedFileWriter.flush();
			bufferedFileWriter.close();
			fileWriter.close();

			dump_file_name = "Gigabyte_Content";
			page_dump=new File("AMG/"+dump_file_name);
			if(page_dump.exists())
				FileUtils.forceDelete(page_dump);
			page_dump.createNewFile();
			fileWriter = new FileWriter(page_dump);
			bufferedFileWriter = new BufferedWriter(fileWriter);
			bufferedFileWriter.write(content_Gigab);
			bufferedFileWriter.flush();
			bufferedFileWriter.close();
			fileWriter.close();

		}catch (Exception e) {
			System.out.println("Exception: " + e);
			e.printStackTrace();
		}

	}


}
