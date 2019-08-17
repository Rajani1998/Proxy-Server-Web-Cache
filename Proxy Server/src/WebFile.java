import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Objects;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mehdi Raza Rajani
 */
public class WebFile {
    String url;
    ArrayList<String> body;
    int occ = 1;
    String lastHitDate;

//    public WebFile(String url, String body) {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss",Locale.US);    
//        this.url = url;
//        this.body = body;
//        this.lastHitDate = dateFormat.format(new Date());
//    }

    public WebFile(String url) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss",Locale.US);  
        this.url = url;
        setBody();
        this.lastHitDate = dateFormat.format(new Date());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final WebFile other = (WebFile) obj;
        return Objects.equals(this.url, other.url);
    }

    public boolean equals(String str) {
        return str.equals(this.url);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.url);
        hash = 79 * hash + Objects.hashCode(this.body);
        hash = 79 * hash + this.occ;
        hash = 79 * hash + Objects.hashCode(this.lastHitDate);
        return hash;
    }

    @Override
    public String toString(){ 
        return body.toString();
//        return url + "{#}" + body + "{#}" + occ + "{#}" + lastHitDate ;
//        return "URL = " + url + ", body = " + body + ", occ = " + occ + ", lastHitDate = " + lastHitDate ;
//        return "URL = " + url + "occ = " + occ + ", lastHitDate = " + lastHitDate + " }" ;
    }
    
    //URL to HTML Body
    private void setBody() {
        body = new ArrayList<>();
        String USER_AGENT="Mozilla/5.0";
        try {
            URL obj=new URL(this.url);
            HttpURLConnection con=(HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("USER-AGENT",USER_AGENT);
            int responsecode=con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL :"+url);
            System.out.println("Response Code:"+responsecode);

//            StringBuilder stringBuilder;
                try (BufferedReader in1 = new BufferedReader(
                        new InputStreamReader(con.getInputStream(),"UTF-8"))) {
                    String inputline;
//                    stringBuilder = new StringBuilder();
                    while((inputline=in1.readLine())!=null)
                        body.add(inputline);
//                        stringBuilder.append(inputline).append("\n");
                }
//            this.body = stringBuilder.toString();
            
        } catch (MalformedURLException e) {
            this.body.add("MalformedURLException: " + e.getMessage());
        } catch (UnknownHostException e) {
            this.body.add("UnknownHostException: " + e.getMessage());
        } catch (IOException ex) { 
            this.body.add("IOException: " + ex.getMessage());
        }
                
    }
    
}
