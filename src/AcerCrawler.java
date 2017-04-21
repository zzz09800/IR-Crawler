import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashSet;

/**
 * Created by andrew on 4/21/17.
 */
public class AcerCrawler {
	String url_Acer_home;
	String url_Acer_home_base;
	String url_Acer_work;
	String url_Acer_work_base;

	public AcerCrawler()
	{
		this.url_Acer_home="https://www.acer.com/ac/en/US/content/group/laptops";
		this.url_Acer_home_base="https://www.acer.com/ac/";
		this.url_Acer_work="https://www.acer.com/ac/en/US/content/professional-group/laptops";
		this.url_Acer_work_base="https://www.acer.com/ac/";
	}

	public void Crawl()
	{
		JobRunner runner = new JobRunner();
		HashSet<String> hrefs = new HashSet<String>();
		HashSet<String> potential_target_urls = new HashSet<String>();

		hrefs.clear();

		try{
			String res= Jsoup.connect(this.url_Acer_home).get().html();
			hrefs.addAll(runner.hrefExtractor(res));
			res=Jsoup.connect(this.url_Acer_work).get().html();
			hrefs.addAll(runner.hrefExtractor(res));
		} catch (Exception e) {
			System.out.println("Exception: " + e);
			e.printStackTrace();
		}

		for(String iter_url:hrefs)
		{
			if(runner.Potential_Checker("Acer_home",iter_url))
			{
				String url_construct = this.url_Acer_home_base+iter_url;
				url_construct=url_construct.replace("series","models/laptops");
				potential_target_urls.add(url_construct);
				//System.out.println(url_construct);
			}
		}

		try{
			File dump_dir = new File("Acer");
			if(dump_dir.exists())
				FileUtils.deleteDirectory(dump_dir);
			dump_dir.mkdir();
		} catch (Exception e) {
			System.out.println("Exception: " + e);
			e.printStackTrace();
		}

		for(String iter_url:potential_target_urls)
		{
			System.out.println(iter_url);
			try{
				String res=Jsoup.connect(iter_url).get().html();
				String dump_file_name = iter_url.replace("http://","").replaceAll("/","_");
				File page_dump=new File("Acer/"+dump_file_name);

				if(page_dump.exists())
					FileUtils.forceDelete(page_dump);

				page_dump.createNewFile();
				FileWriter fileWriter = new FileWriter(page_dump);
				BufferedWriter bufferedFileWriter = new BufferedWriter(fileWriter);
				//bufferedFileWriter.write(iter_url+"\n\n");
				bufferedFileWriter.write(res);
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
