import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashSet;

/**
 * Created by andrew on 4/20/17.
 */
public class LenovoCrawler {
	String url_Lenovo;
	String url_Lenovo_base;

	public LenovoCrawler()
	{
		this.url_Lenovo="http://www3.lenovo.com/us/en/laptops/c/LAPTOPS";
		this.url_Lenovo_base="http://www3.lenovo.com";
	}

	public void Crawl()
	{
		int i,j,k;

		JobRunner runner = new JobRunner();
		String res;
		res=runner.JavaHTTPGetContent(this.url_Lenovo);
		res=res.replaceAll("\\t+","");

		HashSet<String> hrefs;
		HashSet<String> potential_target_urls = new HashSet<String>();
		HashSet<String> potential_target_pages = new HashSet<String>();

		hrefs=runner.hrefExtractor(res);

		try{
			File dump_dir = new File("Lenovo");
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
			if(runner.Potential_Checker("Lenovo",iter_url))
			{
				String target_url=this.url_Lenovo_base+iter_url;
				potential_target_urls.add(target_url);
				System.out.println(target_url);

				res=runner.JavaHTTPGetContent(target_url);
				res=res.replaceAll("\\t+","");

				try{
					String dump_file_name = target_url.replace("http://","").replaceAll("/","_");
					File page_dump=new File("Lenovo/"+dump_file_name);

					if(page_dump.exists())
						FileUtils.forceDelete(page_dump);

					page_dump.createNewFile();
					FileWriter fileWriter = new FileWriter(page_dump);
					BufferedWriter bufferedFileWriter = new BufferedWriter(fileWriter);
					bufferedFileWriter.write(target_url+"\n\n");
					bufferedFileWriter.write(res);
					bufferedFileWriter.flush();
					bufferedFileWriter.close();
					fileWriter.close();
				}catch (Exception e) {
					System.out.println("Exception: " + e);
					e.printStackTrace();
				}
			}
		}
	}
}
