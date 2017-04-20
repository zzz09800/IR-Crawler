import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by andrew on 4/20/17.
 */

public class main {
	public static void main(String[] args)
	{
		int i,j,k;
		int pin_start, pin_end;
		JobRunner runner = new JobRunner();
		String test1="http://www3.lenovo.com/us/en/laptops/c/LAPTOPS";
		String base1="http://www3.lenovo.com/";

		String res;
		res=runner.JavaHTTPGetContent(test1);
		res=res.replaceAll("\\t+","");

		HashSet<String> hrefs = new HashSet<String>();
		HashSet<String> potential_target_urls = new HashSet<String>();
		HashSet<String> potential_target_pages = new HashSet<String>();

		i=0;
		while(i<res.length()-4)
		{
			if(res.substring(i,i+4).equals("href")){
				j=i;
				while(res.charAt(j)!='\"') {
					j++;
				}
				j++;
				pin_start=j;
				while(res.charAt(j)!='\"'&&res.charAt(j)!='?'&&res.charAt(j)!='#') {
					j++;
				}
				pin_end=j;

				hrefs.add(res.substring(pin_start,pin_end));
				j++;
				i=j;
				continue;
			}
			i++;
		}

		hrefs.remove("");
		hrefs.remove("&");

		for(String iter_url:hrefs)
		{
			if(runner.Potential_Checker("Lenovo",iter_url))
			{
				String target_url=base1+iter_url;
				potential_target_urls.add(target_url);
				System.out.println(target_url);
				res=runner.JavaHTTPGetContent(target_url);
				res=res.replaceAll("\\t+","");
				try{

					/*File dump_dir = new File("Lenovo");
					if(dump_dir.exists())
						FileUtils.deleteDirectory(dump_dir);
					dump_dir.mkdir();*/

					String dump_file_name = target_url.replace("http://","").replaceAll("/","_");
					File page_dump=new File(dump_file_name);

					if(page_dump.exists())
						FileUtils.forceDelete(page_dump);

					page_dump.createNewFile();
					FileWriter fileWriter = new FileWriter(page_dump);
					BufferedWriter bufferedFileWriter = new BufferedWriter(fileWriter);
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
	}
}
