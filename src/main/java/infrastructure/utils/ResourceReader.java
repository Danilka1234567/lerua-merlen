package infrastructure.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class ResourceReader {

    public static String read(String path){

        ClassLoader classLoader = ResourceReader.class.getClassLoader();

        try(InputStream inputStream = classLoader.getResourceAsStream(path)){

            if (inputStream == null)
                throw new FileNotFoundException("can't find the file in resources package");

            String content =  new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            content = content.replace("\u00A0", " ").replaceAll("[\\p{Zl}\\p{Zp}\\p{Zs}&&[^ ]]", " ");
            return content.replace("\r", " ");
        }catch (IOException e){
            throw new RuntimeException(
                    "Can't init classLoader", e
            );
        }

    }
}
