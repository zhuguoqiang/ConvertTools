package cube.convertTools;

import java.util.List;

public interface ConvertTaskListener {
	
	public void onConvertContacted(String identifier, String tag);

	public void onConvertCompleted(ConvertTask task, StateCode state);
	
	public void onConvertResult(String taskTag, List<String> result);
}
