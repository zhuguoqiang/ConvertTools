package cube.convertTools;

public interface ConvertTaskListener {
	
	public void onConvertContacted(String identifier, String tag);

	public void onConvertCompleted(StateCode state);
}
