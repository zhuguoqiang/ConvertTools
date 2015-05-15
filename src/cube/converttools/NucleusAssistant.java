package cube.converttools;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
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
					TalkService.getInstance()
							.addListener(NucleusAssistant.this);
					List<String> identifiers = new ArrayList();
					identifiers.add(CubeToolsAPI.ConsoleCelletIdentifier);

					InetSocketAddress address = new InetSocketAddress(host,
							port);
					TalkCapacity capacity = new TalkCapacity(99, 5000);
					TalkService.getInstance().call(identifiers, address,
							capacity);
				} else {
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
				this.processActionDialect((ActionDialect) dialect);
			}
		}
	}

	@Override
	public void failed(String tag, TalkServiceFailure failure) {
		TalkFailureCode code = failure.getCode();
		if (TalkFailureCode.CALL_FAILED == code) {
			Logger.d(this.getClass(), "Talk service: call faield");
		} else if (TalkFailureCode.TALK_LOST == code) {
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

	public void convert(ConvertTask task) {
		ActionDialect ad = new ActionDialect();
		ad.setAction(CubeToolsAPI.ACTION_CONVERT);
		ad.act(new ActionDelegate() {
			@Override
			public void doAction(ActionDialect dialect) {

			}
		});

		JSONObject value = null;
		try {
			value = new JSONObject();
			value.put("filePath", task.getFilePath());
			value.put("outPut", task.getOutPutPath());
			value.put("taskTag", task.getTaskTag());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ad.appendParam("data", value.toString());

		// 发送数据
		TalkService.getInstance()
				.talk(CubeToolsAPI.ConsoleCelletIdentifier, ad);
	}

	/**
	 * 删除文件
	 */
	public void removeFile(String filePath) {
		ActionDialect ad = new ActionDialect();
		ad.setAction(CubeToolsAPI.ACTION_REMOVE_FILE);
		ad.act(new ActionDelegate() {
			@Override
			public void doAction(ActionDialect dialect) {

			}
		});

		JSONObject value = null;
		try {
			value = new JSONObject();
			// TODO 删除文件
			value.put("filePath", filePath);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ad.appendParam("data", value.toString());

		// 发送数据
		TalkService.getInstance()
				.talk(CubeToolsAPI.ConsoleCelletIdentifier, ad);
	}
	
	/**
	 * mkdir 
	 */
	public void makeDir(String dirPath) {
		ActionDialect ad = new ActionDialect();
		ad.setAction(CubeToolsAPI.ACTION_MKDIR);
		ad.act(new ActionDelegate() {
			@Override
			public void doAction(ActionDialect dialect) {

			}
		});

		JSONObject value = null;
		try {
			value = new JSONObject();
			// TODO makedir
			value.put("dirPath", dirPath);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ad.appendParam("data", value.toString());

		// 发送数据
		TalkService.getInstance()
				.talk(CubeToolsAPI.ConsoleCelletIdentifier, ad);
	}

	private void processActionDialect(ActionDialect dialect) {
		if (dialect.getAction().equals(CubeToolsAPI.ACTION_CONVERT_STATE)) {
			String stringData = ((ActionDialect) dialect)
					.getParamAsString("data");
			JSONObject data = null;
			int state = 0;
			String filePath = null;
			String output = null;
			String taskTag = null;
			List<String> convertedFileUris = null;
			String faileCode = null;
			JSONArray ja = null;
			try {
				data = new JSONObject(stringData);
				state = data.getInt("state");
				output = data.getString("outPath");
				taskTag = data.getString("taskTag");
				filePath = data.getString("filePath");
				if (data.has("convertedFileUris")) {
					ja = data.getJSONArray("convertedFileUris");
					convertedFileUris = ConvertUtils.parseToList(ja);
				}

				if (data.has("faileCode")) {
					faileCode = data.getString("faileCode");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			ConvertTask task = new ConvertTask(filePath, output, taskTag);
			task.setConvertedFileURIList(convertedFileUris);
			
		    if (StateCode.Started.getCode() == state) {
				ConvertTool.getInstance().notifyListener(task,
						StateCode.Started);
			} else if (StateCode.Executing.getCode() == state) {
				ConvertTool.getInstance().notifyListener(task,
						StateCode.Executing);
			} else if (StateCode.Failed.getCode() == state) {
				task.setFaileCode(faileCode);
				ConvertTool.getInstance()
						.notifyListener(task, StateCode.Failed);
			} else if (StateCode.Successed.getCode() == state) {
				
				ConvertTool.getInstance().notifyListener(task,
						StateCode.Successed);
			} else if (StateCode.Unknown.getCode() == state) {
				ConvertTool.getInstance().notifyListener(task,
						StateCode.Unknown);
			}

		} 
		else if (dialect.getAction().equals(
				CubeToolsAPI.ACTION_REMOVE_FILE_RESULT)) {
			// TODO 删除文件
		}
		else if (dialect.getAction().equals(
				CubeToolsAPI.ACTION_MKDIR_ACK)) {
			String stringData = ((ActionDialect) dialect)
					.getParamAsString("data");
			JSONObject data = null;
		    String state = null;
			try {
				data = new JSONObject(stringData);
				state = data.getString("state");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

}
