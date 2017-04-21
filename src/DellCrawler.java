import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashSet;

/**
 * Created by andrew on 4/20/17.
 */

//TODO: DellCrawler needs Jsoup too.
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
		String res;
		res=runner.JavaHTTPGetContent(this.url_Dell_home);
		res=res.replaceAll("\\t+","");

		HashSet<String> hrefs;
		HashSet<String> potential_target_urls = new HashSet<String>();
		HashSet<String> potential_target_pages = new HashSet<String>();

		hrefs=runner.hrefExtractor(res);

		try{
			File dump_dir = new File("Dell");
			if(dump_dir.exists())
				FileUtils.deleteDirectory(dump_dir);
			dump_dir.mkdir();
		}
		catch (Exception e)
		{
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
				potential_target_urls.add(iter_url);
				System.out.println(iter_url);

				res=runner.JavaHTTPGetContent(iter_url);
				res=res.replaceAll("\\t+","");

				try{
					String dump_file_name = iter_url.replace("http://","").replaceAll("/","_");
					File page_dump=new File("Dell/"+dump_file_name);

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
				}
				catch (Exception e)
				{
					System.out.println("Exception: " + e);
					e.printStackTrace();
				}
			}
		}

		res=runner.JavaHTTPGetContent(this.url_Dell_work);
		res=res.replaceAll("\\t+","");
		res=res.replaceAll("\'","\"");
		hrefs=runner.hrefExtractor(res);
		potential_target_pages.clear();
		potential_target_urls.clear();

		for(String iter_url:hrefs)
		{
			if(runner.Potential_Checker("Dell_work",iter_url))
			{
				if(!iter_url.startsWith("http"))
				{
					iter_url=this.url_Dell_work_base +iter_url;
				}
				potential_target_urls.add(iter_url);
				System.out.println(iter_url);

				res=runner.JavaHTTPGetContent(iter_url);
				res=res.replaceAll("\\t+","");

				try{
					String dump_file_name = iter_url.replace("http://","").replaceAll("/","_");
					File page_dump=new File("Dell/"+dump_file_name);

					if(!page_dump.exists())
					{
						page_dump.createNewFile();
						FileWriter fileWriter = new FileWriter(page_dump);
						BufferedWriter bufferedFileWriter = new BufferedWriter(fileWriter);
						//bufferedFileWriter.write(iter_url+"\n\n");
						bufferedFileWriter.write(res);
						bufferedFileWriter.flush();
						bufferedFileWriter.close();
						fileWriter.close();
					}
				}
				catch (Exception e)
				{
					System.out.println("Exception: " + e);
					e.printStackTrace();
				}
			}
		}
	}
}
