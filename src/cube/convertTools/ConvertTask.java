package cube.convertTools;

public class ConvertTask {

	StateCode state; 
	String filePath;
	String filePrefix;
	String tag;
	
	public ConvertTask(String tag){
		this.tag = tag;
	}
	
	public ConvertTask(String filePath, String tag){
		this.filePath = filePath;
		this.tag = tag;
	}
	
	public ConvertTask(String filePath, String filePrefix, String tag){
			this.filePath = filePath;
			this.filePrefix = filePrefix;
			this.tag = tag;
	}
	
	public void setStateCode(StateCode state){
		this.state = state;
	}
	
	public StateCode getStateCode(){
		return this.state;
	}
	
	public void setTag(String tag){
		this.tag = tag;
	}
	
	public String getTag(){
		return this.tag;
	}	
	
}
