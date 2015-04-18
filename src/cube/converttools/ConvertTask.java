package cube.converttools;

import java.util.ArrayList;
import java.util.List;

import net.cellcloud.util.Utils;

public class ConvertTask {

	/**
	 * 状态
	 */
	StateCode state;

	/**
	 * 文件路径
	 */
	private String filePath = null;

	/**
	 * 目标文件访问路径
	 */
	private String targetFilePath = null;

	/**
	 * 文件前缀
	 */
	private String filePrefix = null;

	/**
	 * 文件类型， 扩展名
	 */
	private String fileExtension = null;

	/**
	 * 任务标签
	 */
	private String taskTag = null;
	
	/**
	 * 转换后的文件地址 
	 */
	private List<String> convertedFileList = null;

	private final String FILE_EXTENSION = "png";
	
	/**
	 * 构造转换任务
	 * 
	 * @param filePath
	 *            文件路径
	 * @param targetFilePath
	 *            目标文件访问路径
	 */
	public ConvertTask(String filePath, String targetFilePath) {
		this.filePath = filePath;
		this.filePrefix = ConvertUtils
				.extractFileNameWithoutExtension(filePath);
		this.fileExtension = FILE_EXTENSION;
		this.taskTag = Utils.randomString(8);
		this.targetFilePath = targetFilePath;

	}

	/**
	 * 构造转换任务
	 * 
	 * @param filePath
	 *            文件路径
	 * @param targetFilePath
	 *            目标文件访问路径
	 * @param taskTag
	 *            任务标签
	 */
	public ConvertTask(String filePath, String targetFilePath, String taskTag) {
		this.filePath = filePath;
		this.filePrefix = ConvertUtils
				.extractFileNameWithoutExtension(filePath);
		this.fileExtension = FILE_EXTENSION;
		this.taskTag = taskTag;
		this.targetFilePath = targetFilePath;

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

	public void setTargetFilePath(String targetPath) {
		this.targetFilePath = targetPath;
	}

	public String getTargetFilePath() {
		return this.targetFilePath;
	}

	public void setTaskTag(String tag) {
		this.taskTag = tag;
	}

	public String getTaskTag() {
		return this.taskTag;
	}
	
	public void setConvertedFileList(List<String> list) {
		this.convertedFileList = list;
	}

	public List<String> getConvertedFileList() {
		return this.convertedFileList;
	}

	public void fireConvert() {
		// TODO
		if (null != this.filePath) {
			NucleusAssistant.getInstance().convert(this);
		} else {
			System.out.println(this.getClass() + "ConvertTask: " + this.taskTag
					+ "filePath is null");
		}
	}

}
