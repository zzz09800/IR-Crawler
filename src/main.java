import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashSet;

/**
 * Created by andrew on 4/20/17.
 */

public class main {
	public static void main(String[] args) throws Exception
	{
		AcerCrawler acerCrawler = new AcerCrawler();
		AMGCrawler amgCrawler = new AMGCrawler();
		DellCrawler dellCrawler = new DellCrawler();
		LenovoCrawler lenovoCrawler = new LenovoCrawler();

		acerCrawler.Crawl();
		amgCrawler.Crawl();
		dellCrawler.Crawl();
		lenovoCrawler.Crawl();
	}
}
