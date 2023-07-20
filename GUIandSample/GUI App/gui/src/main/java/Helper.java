import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Helper {
    public static String postService(String link, String object,String requestMethod) throws Exception{
        String result = "";
        try {
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("Content-Type","application/json");

            String input = object;
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();
            System.out.println(input);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String out = br.readLine();
            System.out.println("Output form Server ...\n");
            result = out;
            conn.disconnect();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }
    public static String getService(String link) throws Exception{
        String result = "";
        URL url = new URL(link);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("GET");

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String out = br.readLine();
        System.out.println("Output form Server ...\n");
        result = out;
        conn.disconnect();
        return result;
    }
    public static void deleteService(String link) throws Exception{
        URL url = new URL(link);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("DELETE");

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        conn.disconnect();
    }



}
