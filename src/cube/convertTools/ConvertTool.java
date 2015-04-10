package cube.convertTools;

import java.io.File;

/**
 * 转换工具类
 */
public class ConvertTool {
	
	public String filePath = null;
	
	public String filePrefix = null;
	
	private static ConvertTool instance = new ConvertTool();
	
	private ConvertTaskListener listener;
	
	private ConvertTool(){
		
	}
	
	public static ConvertTool getInstance() {
		return ConvertTool.instance;
	}
	
	public void setFilePath(String fileP){
		this.filePath = fileP;
	}
	
	public String getFilePath(){
		return this.filePath;
	}
	
	public void setFilePrefix(String filePre){
		this.filePrefix = filePre;
	}
	
	public String getFilePrefix(){
		return this.filePrefix;
	}
	
	/** set转换监听器。
	 */
	public void setListener(ConvertTaskListener listener) {
		this.listener = listener;
	}

	/** get转换监听器。
	 */
	public ConvertTaskListener getListener() {
			return listener;
	}

	/**
	 * 启动cellCloud 
	 */
	public void startup(final String host, final int port){
		NucleusAssistant.getInstance().start(host, port);
	}
	
	/**
	 * 转换方法 
	 * filePath 需要转换的文件路径
	 * 返回：文件路径
	 */
	public String convert (){
		String  str = null;
		//TODO 
		if(null!=filePath){
			NucleusAssistant.getInstance().convert(filePath, filePrefix);
		}else{
			System.out.println("filePath is null");
		}
		
		return str;
	}
	
	/**
	 * 上传文件
     * file  需要转换的文件
     * 返回：文件路径
	 */
	public String uploadFile (File file){
		String  str = null;
		//TODO 
		return str;
	}
	
	/**
	 * 转换状态
	 * 排队， 执行， 执行失败， 执行成功 
	 */
	public StateCode getConvertTaskState(){
		//TODO 获取任务转换状态
		return StateCode.Successed;
	}
	
	/**
	 * 通知监听器 
	 */
	public void notifyContaced(String identifier, String tag){
		if(listener!=null){
			listener.onConvertContacted(identifier, tag);
		}
	}
	
	public void notifyListener(StateCode state){
		if(listener != null){
			listener.onConvertCompleted(state);
		}
	}
	

}
