import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by andrew on 4/20/17.
 */

public class DellCrawler {
	String url_Dell_home;
	String url_Dell_home_base;
	String url_Dell_work;
	String url_Dell_work_base;

	public DellCrawler()
	{
		this.url_Dell_home ="http://www.dell.com/en-us/shop/category/laptops";
		this.url_Dell_home_base ="http://www.dell.com";
		this.url_Dell_work ="http://www.dell.com/us/business/p/laptops";
		this.url_Dell_work_base ="http://www.dell.com";
	}

	public void Crawl()
	{
		JobRunner runner = new JobRunner();
		HashSet<String> hrefs = new HashSet<String>();
		HashSet<String> potential_target_urls_home = new HashSet<String>();
		HashSet<String> potential_target_urls_work = new HashSet<String>();

		hrefs.clear();

		try{
			String res= Jsoup.connect(this.url_Dell_home).get().html();
			hrefs.addAll(runner.hrefExtractor(res));
		} catch (Exception e) {
			System.out.println("Exception: " + e);
			e.printStackTrace();
		}

		for(String iter_url:hrefs)
		{
			if(runner.Potential_Checker("Dell_home",iter_url))
			{
				if(!iter_url.startsWith("http"))
				{
					iter_url=this.url_Dell_home_base +iter_url;
				}
				potential_target_urls_home.add(iter_url);
				//System.out.println(iter_url);
			}
		}

		hrefs.clear();
		try{
			String res= Jsoup.connect(this.url_Dell_work).get().html();
			hrefs.addAll(runner.hrefExtractor(res));
		} catch (Exception e) {
			System.out.println("Exception: " + e);
			e.printStackTrace();
		}

		for(String iter_url:hrefs)
		{
			if(runner.Potential_Checker("Dell_work",iter_url))
			{
				if(!iter_url.startsWith("http"))
				{
					iter_url=this.url_Dell_home_base +iter_url;
				}
				potential_target_urls_work.add(iter_url);
				//System.out.println(iter_url);
			}
		}


		try{
			File dump_dir = new File("Dell");
			if(dump_dir.exists())
				FileUtils.deleteDirectory(dump_dir);
			dump_dir.mkdir();
		} catch (Exception e) {
			System.out.println("Exception: " + e);
			e.printStackTrace();
		}

		for(String iter_url:potential_target_urls_home)
		{
			System.out.println(iter_url);
			try{
				String dump_file_name = iter_url.replace("http://","").replaceAll("/","_");
				File page_dump=new File("Dell/"+dump_file_name);

				if(page_dump.exists())
					FileUtils.forceDelete(page_dump);
				page_dump.createNewFile();
				FileWriter fileWriter = new FileWriter(page_dump);
				BufferedWriter bufferedFileWriter = new BufferedWriter(fileWriter);

				Process process = new ProcessBuilder(
						"lib/PhantomJS/bin/phantomjs","lib/PhantomJS/bin/DellCrawl_home.js",iter_url).start();

				InputStream is = process.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String line;
				String res="";

				while ((line = br.readLine()) != null) {
					line=line.trim();
					if(line.length()!=0)
						res=res+line+"\n";
					//System.out.println(line);
				}

				bufferedFileWriter.write(res);
				bufferedFileWriter.flush();
				bufferedFileWriter.close();
				fileWriter.close();
			}catch (Exception e) {
				System.out.println("Exception: " + e);
				e.printStackTrace();
			}
		}

		for(String iter_url:potential_target_urls_work)
		{
			System.out.println(iter_url);
			try{
				String dump_file_name = iter_url.replace("http://","").replaceAll("/","_");
				File page_dump=new File("Dell/"+dump_file_name);

				if(page_dump.exists())
					FileUtils.forceDelete(page_dump);
				page_dump.createNewFile();
				FileWriter fileWriter = new FileWriter(page_dump);
				BufferedWriter bufferedFileWriter = new BufferedWriter(fileWriter);

				Process process = new ProcessBuilder(
						"lib/PhantomJS/bin/phantomjs","lib/PhantomJS/bin/DellCrawl_work.js",iter_url).start();

				InputStream is = process.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String line;
				String res="";

				while ((line = br.readLine()) != null) {
					res=res+line.trim()+"\n";
					//System.out.println(line);
				}

				if(res.length()>=1024)
					bufferedFileWriter.write(res);
				bufferedFileWriter.flush();
				bufferedFileWriter.close();
				fileWriter.close();
				if(res.length()<1024)
					page_dump.delete();
			}catch (Exception e) {
				System.out.println("Exception: " + e);
				e.printStackTrace();
			}
		}
	}
}
