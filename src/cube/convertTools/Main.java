package cube.convertTools;

import net.cellcloud.common.LogManager;
import net.cellcloud.common.Logger;

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
		
		ConvertTool.getInstance().setFilePath("/home/lztxhost/Documents/CubeConsole/MCE_Client_v2.doc");
		ConvertTool.getInstance().setFilePrefix("MCE");
		
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
		//已连接， 执行转换
		ConvertTool.getInstance().convert();
	}

	@Override
	public void onConvertCompleted(StateCode state) {
		Logger.d(this.getClass(),"ConvertTask: state :"+ state.getDescription());
		
	}


}
