package cube.converttools;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

public class ConvertUtils {

	/**
	 * 提取文件及后缀。
	 * 
	 * @param filePath
	 * @return
	 */
	public static String extractFileName(String filePath) {
		int lastIndex = filePath.lastIndexOf("/");
		if (lastIndex < 0) {
			return null;
		}

		return filePath.substring(lastIndex + 1, filePath.length());
	}

	/**
	 * 提取文件后缀名。
	 * 
	 * @param fileName
	 * @return
	 */
	public static String extractFileExtension(String fileName) {
		int lastIndex = fileName.lastIndexOf(".");
		if (lastIndex < 0) {
			return null;
		}

		return fileName.substring(lastIndex + 1, fileName.length());
	}

	/**
	 * 提取文件名,无后缀。
	 * 
	 * @param fileName
	 * @return
	 */
	public static String extractFileNameWithoutExtension(String filePath) {
		String fileName = extractFileName(filePath);
		int lastIndex = fileName.lastIndexOf(".");
		if (lastIndex < 0) {
			return null;
		}

		return fileName.substring(0, lastIndex);
	}
	
	public static List<String> parseToList(JSONArray array) {
		List<String> list = new ArrayList<String>();
		for(int i=0; i < array.length(); i++){
			String s = null;
			try {
				s = array.getString(i);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			list.add(s);
		}
		return list;
	}

}
