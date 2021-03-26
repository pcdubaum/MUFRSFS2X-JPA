package infra;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class MUFRFileManager {
	
	private static String dataPath = "extensions/MUFRExtension/Data/";
	
	public static String getDataPath() {
		return dataPath;
	}

	public static void setDataPath(String dataPath) {
		MUFRFileManager.dataPath = dataPath;
	}
	
	public MUFRFileManager() {

	}

	// Get a formated JSON file
	public JSONArray GetFile(String url, JSONParser jsonParser)
	{
		JSONArray jsonArray = null;

		// Parsing the contents of the JSON file
		JSONObject data;
		try {
			
			data = (JSONObject) jsonParser.parse(new FileReader(dataPath + url));
			jsonArray = (JSONArray) data.get("$values");
			
		} catch (IOException | NoSuchFieldError | org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		
		return jsonArray;
	}
}
