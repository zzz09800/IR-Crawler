/**
 * Created by andrew on 4/20/17.
 */

public class main {
	public static void main(String[] args) throws Exception
	{
		LenovoCrawler lenovoCrawler = new LenovoCrawler();
		DellCrawler dellCrawler = new DellCrawler();
		AMGCrawler amgCrawler = new AMGCrawler();
		JobRunner runner = new JobRunner();

		dellCrawler.Crawl();
	}
}
