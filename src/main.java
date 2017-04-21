import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;

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
	public static void main(String[] args) throws Exception
	{
		/*LenovoCrawler lenovoCrawler = new LenovoCrawler();
		DellCrawler dellCrawler = new DellCrawler();
		JobRunner runner = new JobRunner();
		dellCrawler.Crawl();*/

		String url_MSI="http://www.pro-star.com/index.php?r=product/index&filter=16";

		String html = Jsoup.connect(url_MSI).get().html();
		System.out.println(html);
	}
}
