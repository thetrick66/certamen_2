package cl.telematica.patricio.certamen2_v2.conexion;

/**
 * Created by Patricio on 30-09-2016.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpServer {

    public String connectToServer(String myUrl, int timeOut){
        try {
            URL url = new URL(myUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(timeOut);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            conn.connect();

            InputStream is = conn.getInputStream();
            return readIt(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String readIt(InputStream stream) throws IOException {
        Reader reader = null;
        StringBuilder inputStringBuilder = new StringBuilder();

        reader = new InputStreamReader(stream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(reader);

        String line = bufferedReader.readLine();
        while(line != null){
            inputStringBuilder.append(line);
            inputStringBuilder.append('\n');
            line = bufferedReader.readLine();
        }
        return inputStringBuilder.toString();
    }

}