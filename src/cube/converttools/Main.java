package cube.converttools;

import java.util.List;

import net.cellcloud.common.LogManager;
import net.cellcloud.common.Logger;
import net.cellcloud.util.Utils;

public class Main implements ConvertTaskListener {

	private Main() {

	}

	public static void main(String[] args) {
		LogManager.getInstance().addHandle(LogManager.createSystemOutHandle());
		// String host = "192.168.0.103";
		String host = "211.103.217.154";
		int port = 10000;

		ConvertTool.getInstance().startup(host, port);
		ConvertTool.getInstance().setListener(new Main());

		// ConvertTool.getInstance().setFilePath("/home/lztxhost/Documents/CubeConsole/Mooohe.ppt");
		// ConvertTool.getInstance().setFilePrefix("CC");

		boolean spinning = true;
		while (spinning) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onConvertContacted(String identifier, String tag) {
		// 已连接
		Utils utils = new Utils();
		// 封装转换任务
		String filePath1 = "/home/lztxhost/Documents/CubeConsole/MCE_Client_v2.doc";
//		String targetPath = "/Users/admin/Documents/workspace/.metadata/.plugins"
//				+ "/org.eclipse.wst.server.core/tmp1/wtpwebapps/CubeCloud/assets/images";
		String targetPath = "/home/lztxhost/Documents/";
		ConvertTask task1 = new ConvertTask(filePath1, targetPath);

		ConvertTool.getInstance().addConvertTask(task1);

		String filePath2 = "/home/lztxhost/Documents/CubeConsole/Mooohe.ppt";
		ConvertTask task2 = new ConvertTask(filePath2, targetPath);
		ConvertTool.getInstance().addConvertTask(task2);
	}

	@Override
	public void onConvertCompleted(ConvertTask task, StateCode state) {
		Logger.d(this.getClass(), "\n ConvertTask: " + task.getTaskTag()
				+ " state : " + state.getDescription());
	}

	@Override
	public void onConvertTaskWithFileList(ConvertTask task) {
		// 打印输出png文件访问地址
		Logger.d(this.getClass(), "\n ConvertTask: " + task.getTaskTag() + "\n"
				+ " FileList.size() : " + task.getConvertedFileList().size()
				+ "\n" + " FileList : "
				+ task.getConvertedFileList().toString());
	}

}
