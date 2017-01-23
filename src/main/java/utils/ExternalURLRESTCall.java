/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.json.JsonException;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author TimmosQuadros
 */
public class ExternalURLRESTCall {
    
    public static JsonObject readJsonFromUrl(String sURL) throws IOException, JsonException {
        // Connect to the URL using java's native library
        URL url = new URL(sURL);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        // Convert to a JSON object to print data
        JsonParser jp = new JsonParser(); //from gson
        InputStream is = (InputStream) request.getContent();
        String encoding = request.getContentEncoding();
        InputStreamReader isr = new InputStreamReader(is, encoding);
        JsonElement root = jp.parse(isr); //Convert the input stream to a json element
        JsonObject rootobj = root.getAsJsonObject();
        return rootobj;
    }
    
}
