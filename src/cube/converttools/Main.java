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
		 String filePath1 =
		 "/home/lztxhost/Documents/CubeConsole/MCE_Client_v2.doc";
		String subPath = "admin/";
		 ConvertTask task1 = new ConvertTask(filePath1, subPath);
		 ConvertTool.getInstance().addConvertTask(task1);

		 String filePath2 =
		 "/home/lztxhost/Documents/CubeConsole/MCE_Cube_Cloud.pdf";
		 ConvertTask task2 = new ConvertTask(filePath2, subPath);
		 ConvertTool.getInstance().addConvertTask(task2);

		String filePath3 = "/home/lztxhost/Documents/CubeConsole/aaaaa.png";
		ConvertTask task3 = new ConvertTask(filePath3, subPath);
		ConvertTool.getInstance().addConvertTask(task3);
	}

	@Override
	public void onQueueing(ConvertTask task) {
		Logger.d(this.getClass(), "\n ConvertTask: " + task.getTaskTag()
				+ " Queueing ");
	}

	@Override
	public void onStarted(ConvertTask task) {
		// TODO
		Logger.d(this.getClass(), "\n ConvertTask: " + task.getTaskTag()
				+ " Started ");

	}

	@Override
	public void onExecuting(ConvertTask task) {
		Logger.d(this.getClass(), "\n ConvertTask: " + task.getTaskTag()
				+ " Executing ");
	}

	@Override
	public void onCompleted(ConvertTask task) {
		Logger.d(this.getClass(), "\n ConvertTask: " + task.getTaskTag()
				+ " state : " + task.state.getDescription() + "\n"
				+ " FileURLList : " + task.getConvertedFileURLList().toString());

	}

	@Override
	public void onTaskFailed(ConvertTask task, StateCode code) {
		// TODO
		Logger.d(
				this.getClass(),
				"\n ConvertTask: " + task.getTaskTag() + " Failed : "
						+ code.getDescription() + " failCode : "
						+ task.getFaileCode() + " FileURLList : "
						+ task.getConvertedFileURLList().toString());
	}

}
