import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jdi.connect.Connector;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws Exception {
        URL url = new URL("https://www.telesat.com/geo-satellites/");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");


        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        Pattern pattern = Pattern.compile("<a[^>]+href=\"(https://app.telesat.com/satellites/[^\"]*)\"");
        Matcher matcher = pattern.matcher(content);
        ArrayList<String> links = new ArrayList<String>();
        while (matcher.find()) {
            if(!matcher.group(1).equals("https://app.telesat.com/satellites/")){
                if(links.size()>0){int t=0;
                for(String str: links){
                    if(str.equals(matcher.group(1))){
                        t=1;
                    }
                }
                if(t==0){
                String href = matcher.group(1);
                links.add(href);}}
                else{
                    String href = matcher.group(1);
                    links.add(href);
                }
            }
        }
        links.add("https://app.telesat.com/satellites/anik_f4");
        Pattern pattern1 = Pattern.compile("<a[^>]+href=\"(http://app.telesat.com/satellites/[^\"]*)\"");
        Matcher matcher1 = pattern1.matcher(content);
        while (matcher1.find()) {
            if(!matcher1.group(1).equals("http://app.telesat.com/satellites/")||!matcher1.group(1).equals("https://app.telesat.com/satellites/")){
                String href = matcher1.group(1);
                links.add(href);
            }
        }
        ArrayList<String> linksForJson = new ArrayList<>();
        for(String str: links){
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\Настя\\Downloads\\chromedriver_win32 (1)\\chromedriver.exe");


            WebDriver driver = new ChromeDriver();


            driver.get(str);


            String pageSource = driver.getPageSource();
            Document doc = Jsoup.parse(pageSource);
            Elements options = doc.select("option[value]");

            String insertString = "static/";
            int index;
            if(str.contains("https")){
            index = 23;
            }
            else {
                index = 22;
            }
            str = str.substring(0, index + 1) + insertString + str.substring(index + 1);
            for (Element option : options) {
                String value = option.attr("value");

                if (value.contains("eirp")||value.contains("gt")) {
                    linksForJson.add(str +"/"+value+".json");
                }


            driver.quit();
        }
        }
        try {
            FileWriter writer = new FileWriter("output.txt");
            for (String str1 : linksForJson) {
                URL url2 = new URL(str1);
                BufferedInputStream inputStream = new BufferedInputStream(url2.openStream());
                FileOutputStream outputStream = new FileOutputStream("zones.json");
                byte[] buffer = new byte[1024];
                int bytesRead = 0;
                while ((bytesRead = inputStream.read(buffer, 0, buffer.length)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.close();
                inputStream.close();
                String filePath = "zones.json";
                String searchString = "<html>";
                int k = 0;
                try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (Pattern.compile(searchString).matcher(line).find()) {
                            if (str1.contains("http")) {
                                str1 = str1.replace("http", "https");
                            } else if (str1.contains("https")) {
                                str1 = str1.replace("https", "http");
                            }
                            URL url3 = new URL(str1);
                            BufferedInputStream inputStream1 = new BufferedInputStream(url3.openStream());
                            FileOutputStream outputStream1 = new FileOutputStream("zones.json");
                            byte[] buffer1 = new byte[1024];
                            int bytesRead1 = 0;
                            while ((bytesRead1 = inputStream1.read(buffer1, 0, buffer1.length)) != -1) {
                                outputStream1.write(buffer1, 0, bytesRead1);
                            }
                            outputStream1.close();
                            inputStream1.close();
                            break;
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

                String[] strName = str1.split("/");
                writer.write(strName[5] + " ");
                writer.write(strName[6].replace(".json","") + System.lineSeparator());

                Sputnik sputnik = objectMapper.readValue(new File("zones.json"), Sputnik.class);
                SputnikForSummary sputnikForSummary = objectMapper.readValue(new File("zones.json"), SputnikForSummary.class);
                List<Feature> arrayList = new ArrayList<Feature>();
                for (int i = 0; i < sputnik.getFeatures().size(); i++) {
                    if (!(sputnik.getFeatures().get(i).getGeometry().getGeometries() == null)) {
                        for (int j = 0; j < sputnik.getFeatures().get(i).getGeometry().getGeometries().size(); j++) {
                            if (sputnik.getFeatures().get(i).getGeometry().getGeometries().get(j).getPolygons().size() < 1) {
                                sputnik.getFeatures().get(i).getGeometry().getGeometries().remove(j);
                                j--;
                            }
                        }
                    }
                }
                for (Feature list : sputnik.getFeatures()) {
                    if (list.getGeometry() != null) {
                        if (list.getGeometry().getGeometries() != null) {
                            arrayList.add(list);
                        }
                    }
                }
                for (Feature str : arrayList) {
                    writer.write(str + System.lineSeparator());
                }


                List<FeatureForSummary> arrayList1 = new ArrayList<FeatureForSummary>();

                for (FeatureForSummary list : sputnikForSummary.getFeaturesForSummary()) {
                    if (list.getGeometryForSummary().getPolygonsForSummary().size()>1) {
                        arrayList1.add(list);
                    }
                }
                for (FeatureForSummary str : arrayList1) {
                    writer.write(str + System.lineSeparator());
                }
            }
            writer.close();
        }


    catch (IOException e) {
        e.printStackTrace();
    }
}}







