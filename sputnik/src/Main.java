import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {

        String url = "https://www.gazprom-spacesystems.ru/local/ajax/mapZones/db.php";

        try {

            downloadUsingStream(url, "zones.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
       ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
      Sputnik sputnik = objectMapper.readValue(new File("zones.json"), Sputnik.class);
   FeatureCollection collection = objectMapper.readValue(new File("zones.json"), FeatureCollection.class);

        List <Feature> arrayList= new ArrayList<Feature>();
      for( Feature list: sputnik.getFeatures()){
          if(list.getGeometry()!=null){
              arrayList.add(list);
          }

      }

      for(List<Sputnik> list: collection.getFeatures()){
          for(Sputnik listSputnik: list ){
             if(listSputnik.getFeatures()!=null){
              for(Feature listFeatures: listSputnik.getFeatures()){
              if(listFeatures!=null){
                arrayList.add( listFeatures);
              }
             }}
          }

      }
        try{
            FileWriter writer = new FileWriter("output.txt");
            for(Feature str: arrayList) {
                writer.write(str + System.lineSeparator());
            }
            writer.close();


        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void downloadUsingStream(String urlStr, String file) throws IOException{
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count=0;
        while((count = bis.read(buffer,0,1024)) != -1)
        {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();

    }
}