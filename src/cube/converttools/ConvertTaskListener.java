package cube.converttools;

public interface ConvertTaskListener {
	
	public void onQueueing(ConvertTask task);

	public void onStarted(ConvertTask task);

	public void onCompleted(ConvertTask task);

	public void onTaskFailed(ConvertTask task, StateCode code);
	
	public void onConvertContacted(String identifier, String tag);
	
}
