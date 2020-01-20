package utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WaTextGroupSender 
{	
	public static void sendGroupMessage(String instanceID, String clientID, String clientSecret,String  awGatewayURL, String groupAdmin, String groupName, String message) throws Exception 
	{
		    // TODO: Should have used a 3rd party library to make a JSON string from an object
			String jsonPayload = new StringBuilder()
		      .append("{")
		      .append("\"group_admin\":\"")
		      .append(groupAdmin)
		      .append("\",")
		      .append("\"group_name\":\"")
		      .append(groupName)
		      .append("\",")
		      .append("\"message\":\"")
		      .append(message)
		      .append("\"")
		      .append("}")
		      .toString();

			System.out.println("***"+message+"***");
		    URL url = new URL(awGatewayURL);
		    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		    conn.setDoOutput(true);
		    conn.setRequestMethod("POST");
		    conn.setRequestProperty("X-WM-CLIENT-ID", clientID);
		    conn.setRequestProperty("X-WM-CLIENT-SECRET", clientSecret);
		    conn.setRequestProperty("Content-Type", "application/json");

		    OutputStream os = conn.getOutputStream();
		    os.write(jsonPayload.getBytes());
		    os.flush();
		    os.close();

		    int statusCode = conn.getResponseCode();
		    System.out.println("Response from WA Gateway: \n");
		    System.out.println("Status Code: " + statusCode);
		    BufferedReader br = new BufferedReader(new InputStreamReader((statusCode == 200) ? conn.getInputStream() : conn.getErrorStream()));
		    String output;
		    while ((output = br.readLine()) != null) 
		    {
		        System.out.println(output);
		    }
		    conn.disconnect();
		  }
}
