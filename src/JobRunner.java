/**
 * Created by andrew on 4/20/17.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
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

		if(brand.equals("Dell_home"))
		{
			url=url.toLowerCase();
			if(url.contains("productdetails")&&!url.contains("desktop"))
				return true;
			else
				return false;
		}

		if(brand.equals("Dell_work"))
		{
			url=url.toLowerCase();
			if(url.contains("latitude")||url.contains("inspiron")||url.contains("precision")||url.contains("xps")||url.contains("chromebook"))
				return true;
			else
				return false;
		}
		return false;
	}

	public HashSet<String> hrefExtractor(String content)
	{
		int i,j;
		int pin_start, pin_end;
		HashSet<String> hrefs = new HashSet<String>();

		i=0;
		while(i<content.length()-4)
		{
			if(content.substring(i,i+4).equals("href")){
				j=i;
				while(content.charAt(j)!='\"') {
					j++;
				}
				j++;
				pin_start=j;
				while(content.charAt(j)!='\"'&&content.charAt(j)!='?'&&content.charAt(j)!='#') {
					j++;
				}
				pin_end=j;

				hrefs.add(content.substring(pin_start,pin_end));
				j++;
				i=j;
				continue;
			}
			i++;
		}

		hrefs.remove("");
		hrefs.remove("&");

		return hrefs;
	}
}
