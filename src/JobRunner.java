/**
 * Created by andrew on 4/20/17.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class JobRunner {
	public String JavaHTTPGetContent(String urlString)
	{
		String httpResponseContent="";
		String tmp;
		try {
			URL target_url = new URL(urlString);
			URLConnection httpURLConnection = target_url.openConnection();
			httpURLConnection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:52.0) Gecko/20100101 Firefox/52.0");

			httpURLConnection.connect();

			BufferedReader httpReponseContentReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

			while((tmp=httpReponseContentReader.readLine())!=null)
			{
				httpResponseContent=httpResponseContent+tmp;
			}

		}
		catch (Exception e){
			System.out.println("Exception: " + e);
			e.printStackTrace();
		}

		return httpResponseContent;
	}

	public Boolean Potential_Checker(String brand, String url)
	{
		if(brand.equals("Lenovo"))
		{
			url=url.toLowerCase();
			if(url.contains("ideapad")||url.contains("thinkpad")||url.contains("yoga"))
				return true;
			else
				return false;
		}
		return false;
	}
}
