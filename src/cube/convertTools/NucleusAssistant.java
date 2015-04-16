package cube.convertTools;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import net.cellcloud.common.Logger;
import net.cellcloud.core.Nucleus;
import net.cellcloud.core.NucleusConfig;
import net.cellcloud.exception.SingletonException;
import net.cellcloud.talk.Primitive;
import net.cellcloud.talk.TalkCapacity;
import net.cellcloud.talk.TalkFailureCode;
import net.cellcloud.talk.TalkListener;
import net.cellcloud.talk.TalkService;
import net.cellcloud.talk.TalkServiceFailure;
import net.cellcloud.talk.dialect.ActionDelegate;
import net.cellcloud.talk.dialect.ActionDialect;
import net.cellcloud.talk.dialect.ChunkDialect;
import net.cellcloud.talk.dialect.Dialect;

public final class NucleusAssistant implements TalkListener {
	
	private final static NucleusAssistant instance = new NucleusAssistant();

	private String host = null;
	private int port = 0;

	private NucleusAssistant() {
	}

	public static NucleusAssistant getInstance() {
		return NucleusAssistant.instance;
	}

	public void start(final String host, final int port) {
		this.host = host;
		this.port = port;

		if (null == Nucleus.getInstance()) {
			try {
				// 启动 Cell Cloud
				NucleusConfig config = new NucleusConfig();
				config.role = NucleusConfig.Role.CONSUMER;
				config.device = NucleusConfig.Device.DESKTOP;

				// 创建实例
				Nucleus.createInstance(config);
			} catch (SingletonException e) {
				Logger.e(NucleusAssistant.class, e.getMessage());
			}
		}
		
		(new Thread() {
			@Override
			public void run() {
		
			if (Nucleus.getInstance().startup()) {
				// 添加监听器
				TalkService.getInstance().addListener(NucleusAssistant.this);
				List<String> identifiers = new ArrayList();
				identifiers.add(CubeToolsAPI.ConsoleCelletIdentifier);
				
				InetSocketAddress address = new InetSocketAddress(host, port);
				TalkCapacity capacity = new TalkCapacity(99, 5000);
				TalkService.getInstance().call(identifiers, address, capacity);	
			}
			else {
				Logger.e(NucleusAssistant.class, "Startup nucleus failed.");
			 }
		}
	 }).start();

	}

	public void stop() {
		Nucleus.getInstance().shutdown();
	}
	
	@Override
	public void dialogue(String identifier, Primitive primitive) {
		if (primitive.isDialectal()) {
			Dialect dialect = primitive.getDialect();
			if (dialect instanceof ActionDialect) {			
				this.processActionDialect((ActionDialect)dialect);
			}
		}
	}

	@Override
	public void failed(String tag, TalkServiceFailure failure) {
		TalkFailureCode code = failure.getCode();
		if (TalkFailureCode.CALL_FAILED == code) {
			Logger.d(this.getClass(), "Talk service: call faield");
		}
		else if (TalkFailureCode.TALK_LOST == code) {
			Logger.d(this.getClass(), "Talk service: talk lost");
		}
	}

	@Override
	public void contacted(String identifier, String tag) {
		ConvertTool.getInstance().notifyContaced(identifier, tag);
	}

	@Override
	public void quitted(String identifier, String tag) {
		
	}

	@Override
	public void resumed(String tag, long timestamp, Primitive primitive) {
		// Nothing
		
	}

	@Override
	public void suspended(String tag, long timestamp, int mode) {
		// Nothing
	}
	
	public void convert(ConvertTask task){
	
			ActionDialect ad = new ActionDialect();
			ad.setAction(CubeToolsAPI.ACTION_CONVERT);
			ad.act(new ActionDelegate(){
				@Override
				public void doAction(ActionDialect dialect) {
					
				}
			});
			
			JSONObject value = null;
			try{
				value = new JSONObject();
				value.put("filePath", task.getFilePath());
				value.put("filePrefix", task.getFilePrefix());
				value.put("fileExtension", task.getFileExtension());
				value.put("taskTag", task.getTaskTag());
			}catch(JSONException e){			
				e.printStackTrace();
			}
			ad.appendParam("data", value.toString());
		
			// 发送数据
			TalkService.getInstance().talk(CubeToolsAPI.ConsoleCelletIdentifier, ad);
		
	}
	
	/**
	 * 查找文件 
	 */
	
	public void findFile(ConvertTask task ){
		ActionDialect ad = new ActionDialect();
		ad.setAction(CubeToolsAPI.ACTION_FIND_FILE);
		ad.act(new ActionDelegate(){
			@Override
			public void doAction(ActionDialect dialect) {
				
			}
		});
		
		JSONObject value = null;
		try{
			value = new JSONObject();
			value.put("filePrefix", task.getFilePrefix());
			value.put("fileExtension", task.getFileExtension());
			value.put("taskTag", task.getTaskTag());
		}catch(JSONException e){			
			e.printStackTrace();
		}
		ad.appendParam("data", value.toString());
	
		// 发送数据
		TalkService.getInstance().talk(CubeToolsAPI.ConsoleCelletIdentifier, ad);
	}
	
	private void processActionDialect(ActionDialect dialect){
		if(dialect.getAction().equals(CubeToolsAPI.ACTION_CONVERT_STATE)){
			String tag = dialect.getOwnerTag();
			String stringData = ((ActionDialect)dialect).getParamAsString("data");
			JSONObject data = null;
			int result = 0;
			String filePath = null;
			String fileExtension = null;
			String filePrefix = null;
			String taskTag = null;
			try {
				data = new JSONObject(stringData);
				result = data.getInt("state");
				taskTag = data.getString("taskTag");
				filePath = data.getString("filePath");
				fileExtension = data.getString("fileExtension");
				filePrefix = data.getString("filePrefix");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ConvertTask task = new ConvertTask(filePath, filePrefix,
					 fileExtension, taskTag);
			
			if(StateCode.Queueing.getCode() == result){
				ConvertTool.getInstance().notifyListener(task, StateCode.Queueing);
			}else if(StateCode.Executing.getCode() == result){
				ConvertTool.getInstance().notifyListener(task, StateCode.Executing);
			}else if(StateCode.Failed.getCode() == result){
				ConvertTool.getInstance().notifyListener(task, StateCode.Failed);
			}else if(StateCode.Successed.getCode() == result){
				ConvertTool.getInstance().notifyListener(task, StateCode.Successed);
			}else if(StateCode.Unknown.getCode() == result){
				ConvertTool.getInstance().notifyListener(task, StateCode.Unknown);
			}
				
			
		}else if (dialect.getAction().equals(CubeToolsAPI.ACTION_FIND_FILE_RESULT)){
			String tag = dialect.getOwnerTag();
			String stringData = ((ActionDialect)dialect).getParamAsString("data");
			JSONObject data = null;
			String result = null;
			String taskTag = null;
			List<String> resultArray = new ArrayList();
			try {
				data = new JSONObject(stringData);
				result = data.getString("result");
				taskTag = data.getString("taskTag");
				if(null != result){
					String[] array = result.split("\n");
					for(String str : array){
						int index = str.lastIndexOf("/");
						String subStr = str.substring(index+1, str.length());
						resultArray.add(subStr);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			//TODO
			ConvertTool.getInstance().notifyFindFileResult(taskTag, resultArray);
			
		}
	}


}
