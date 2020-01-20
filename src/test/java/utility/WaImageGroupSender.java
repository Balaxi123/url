package utility;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;


public class WaImageGroupSender 
{

    class ImageGroupMessage 
    {
        String group_name = null;
        String caption = null;
        String image = null;
    }
    

    public void sendPhotoGroupMessage(String group, byte[] imageBytes, String caption, String waGatewayURL, String clientID, String clientSecret) throws Exception 
    {
        byte[] encodedBytes = Base64.encodeBase64(imageBytes);
        String base64Image = new String(encodedBytes);
        
        ImageGroupMessage imageMsgObj = new ImageGroupMessage();
        imageMsgObj.group_name = group;
        imageMsgObj.image = base64Image;
        imageMsgObj.caption = caption;

        Gson gson = new Gson();
        String jsonPayload = gson.toJson(imageMsgObj);

        URL url = new URL(waGatewayURL);
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
        System.out.println("Response from WhatsApp Gateway: \n");
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