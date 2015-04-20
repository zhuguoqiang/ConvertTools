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
	 * 目标文件访问子路径
	 * admin@123/
	 */
	private String subPath = null;

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
	private List<String> convertedFileURLList = null;

	private final String FILE_EXTENSION = "png";
	
	/**
	 * 构造转换任务
	 * 
	 * @param filePath
	 *            文件路径
	 * @param subPath
	 *            目标文件访问子路径
	 */
	public ConvertTask(String filePath, String subPath) {
		this.filePath = filePath;
		this.filePrefix = ConvertUtils
				.extractFileNameWithoutExtension(filePath);
		this.fileExtension = FILE_EXTENSION;
		this.taskTag = Utils.randomString(8);
		this.subPath = subPath;

	}

	/**
	 * 构造转换任务
	 * 
	 * @param filePath
	 *            文件路径
	 * @param subPath
	 *            目标文件访问子路径
	 * @param taskTag
	 *            任务标签
	 */
	public ConvertTask(String filePath, String subPath, String taskTag) {
		this.filePath = filePath;
		this.filePrefix = ConvertUtils
				.extractFileNameWithoutExtension(filePath);
		this.fileExtension = FILE_EXTENSION;
		this.taskTag = taskTag;
		this.subPath = subPath;

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

	public void setSubPath(String subPath) {
		this.subPath = subPath;
	}

	public String getSubPath() {
		return this.subPath;
	}

	public void setTaskTag(String tag) {
		this.taskTag = tag;
	}

	public String getTaskTag() {
		return this.taskTag;
	}
	
	public void setConvertedFileURLList(List<String> list) {
		this.convertedFileURLList = list;
	}

	public List<String> getConvertedFileURLList() {
		return this.convertedFileURLList;
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
