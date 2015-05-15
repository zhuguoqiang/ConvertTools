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

		ConvertTool.getInstance().start(host, port);
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
		String outPut = "/home/lztxhost/apache-tomcat-7.0.61/webapps/ROOT/cubewhiteboard/shared/";

		/**pdf**/
		 String filePath1 =
		 "/home/lztxhost/apache-tomcat-7.0.61/webapps/ROOT/local/upload/zhuguoqiang@163.com/MCE_Cube_Cloud.pdf";
		 ConvertTask task1 = new ConvertTask(filePath1, outPut);
		 ConvertTool.getInstance().addConvertTask(task1);

		/**doc**/
		 String filePath2 =
				 "/home/lztxhost/apache-tomcat-7.0.61/webapps/ROOT/local/upload/zhuguoqiang@163.com/MCE服务器技术说明书v.2.doc";
		 ConvertTask task2 = new ConvertTask(filePath2, outPut);
		 ConvertTool.getInstance().addConvertTask(task2);
		 
		 /**png**/
		 String filePath3 =
				 "/home/lztxhost/apache-tomcat-7.0.61/webapps/ROOT/local/upload/zhuguoqiang@163.com/cerqdsOmxuuRI.png";
		 ConvertTask task3 = new ConvertTask(filePath3, outPut);
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
				+ " FileURIList : " + task.getConvertedFileURIList().toString());

	}

	@Override
	public void onTaskFailed(ConvertTask task, StateCode code) {
		// TODO
		Logger.d(
				this.getClass(),
				"\n ConvertTask: " + task.getTaskTag() + " Failed : "
						+ code.getDescription() + " failCode : "
						+ task.getFaileCode() + " FileURIList : "
						+ task.getConvertedFileURIList().toString());
	}

}
