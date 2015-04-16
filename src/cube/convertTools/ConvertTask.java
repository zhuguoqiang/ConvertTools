package cube.convertTools;

public class ConvertTask {

	/**
	 * 状态
	 */
	StateCode state;

	/**
	 * 文件路径
	 */
	public String filePath = null;

	/**
	 * 文件前缀
	 */
	public String filePrefix = null;

	/**
	 * 文件类型， 扩展名
	 */
	public String fileExtension = null;

	/**
	 * 任务标签
	 */
	String taskTag = null;

	public ConvertTask(String tag) {
		this.taskTag = tag;
	}

	public ConvertTask(String filePath, String tag) {
		this.filePath = filePath;
		this.taskTag = tag;
	}

	public ConvertTask(String filePath, String filePrefix,
			String fileExtension, String taskTag) {
		this.filePath = filePath;
		this.filePrefix = filePrefix;
		this.fileExtension = fileExtension;
		this.taskTag = taskTag;
		
	}

	public void setStateCode(StateCode state) {
		this.state = state;
	}

	public StateCode getStateCode() {
		return this.state;
	}

	public void setFilePath(String fileP) {
		this.filePath = fileP;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePrefix(String filePre) {
		this.filePrefix = filePre;
	}

	public String getFilePrefix() {
		return this.filePrefix;
	}

	public void setFileExtension(String fileExt) {
		this.fileExtension = fileExt;
	}

	public String getFileExtension() {
		return this.fileExtension;
	}

	public void setTaskTag(String tag) {
		this.taskTag = tag;
	}

	public String getTaskTag() {
		return this.taskTag;
	}

	public void fireConvert() {
		// TODO
		if (null != this.filePath) {
			NucleusAssistant.getInstance().convert(this);
		} else {
			System.out.println(this.getClass() + "ConvertTask: " +this.taskTag + "filePath is null");
		}
	}

}
