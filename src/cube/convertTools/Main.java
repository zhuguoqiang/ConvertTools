package cube.convertTools;

import java.util.List;

import net.cellcloud.common.LogManager;
import net.cellcloud.common.Logger;
import net.cellcloud.util.Utils;

public class Main implements ConvertTaskListener {

	private Main() {
		
	}
	
	public static void main(String[] args) {
		LogManager.getInstance().addHandle(LogManager.createSystemOutHandle());
//		String host = "192.168.0.103";
		String host = "211.103.217.154";
		int port = 10000;
		
		ConvertTool.getInstance().startup(host, port);
		ConvertTool.getInstance().setListener(new Main());
		
//		ConvertTool.getInstance().setFilePath("/home/lztxhost/Documents/CubeConsole/Mooohe.ppt");
//		ConvertTool.getInstance().setFilePrefix("CC");
		
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
	public void onConvertContacted(String identifier, String tag){
		System.out.println("CONNECTED");
		//已连接
		Utils utils = new Utils();
		//封装转换任务
		String filePath1 = "/home/lztxhost/Documents/CubeConsole/MCE_Client_v2.doc";
		String filePrefix1 = "CC";
		String fileExtension1 = "png";
		String tag1 = utils.randomString(8);
		ConvertTask task1 = new ConvertTask(filePath1, filePrefix1, fileExtension1, tag1);
		
		ConvertTool.getInstance().addConvertTask(task1);
		
		String filePath2 = "/home/lztxhost/Documents/CubeConsole/Mooohe.ppt";
		String filePrefix2 = "MCE";
		String fileExtension2 = "png";
		String tag2 = utils.randomString(8);
		ConvertTask task2 = new ConvertTask(filePath2, filePrefix2, fileExtension2,tag2);
        ConvertTool.getInstance().addConvertTask(task2);
	}

	@Override
	public void onConvertCompleted(ConvertTask task, StateCode state) {
		Logger.d(this.getClass(),"ConvertTask: "+task.getTaskTag()+" state : "+ state.getDescription());
		
	}
	
	@Override
	public void onConvertResult(String taskTag, List<String> result){
		Logger.d(this.getClass(),"ConvertTask: "+taskTag+" result : "+result.toString());
		//TODO 结果处理
		
	}


}
