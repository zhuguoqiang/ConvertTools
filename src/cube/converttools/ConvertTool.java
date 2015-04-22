package cube.converttools;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 转换工具类
 */
public class ConvertTool {

	/**
	 * 转换任务队列
	 */
	private Queue<ConvertTask> taskQueue = new LinkedList<ConvertTask>();

	/**
	 * 守护线程任务
	 */
	DaemonTask threadTask = null;

	/**
	 * 互斥锁
	 */
	private Object mutex = new Object();

	boolean spinning = false;

	private static ConvertTool instance = new ConvertTool();

	/**
	 * 转换任务监听器。
	 */
	private ConvertTaskListener listener;

	/**
	 * 构造
	 */

	private ConvertTool() {

	}

	/**
	 * 单例
	 */
	public static ConvertTool getInstance() {
		return ConvertTool.instance;
	}

	/**
	 * set转换监听器。
	 */
	public void setListener(ConvertTaskListener listener) {
		this.listener = listener;
	}

	/**
	 * get转换监听器。
	 */
	public ConvertTaskListener getListener() {
		return listener;
	}

	/**
	 * 启动Cellet
	 */
	public void startup(final String host, final int port) {
		NucleusAssistant.getInstance().start(host, port);
		threadTask = new DaemonTask(mutex, taskQueue);
		threadTask.start();
	}

	/**
	 * 停止Cellet
	 */
	public void stop() {
		NucleusAssistant.getInstance().stop();
		synchronized (this.mutex) {
			mutex.notifyAll();
		}
		threadTask = null;
	}

	/**
	 * 入队 task 转换文件任务
	 */
	public void addConvertTask(ConvertTask task) {
		// TODO
		if (null != task) {
			// 入队
			synchronized (mutex) {
				taskQueue.offer(task);
				task.state = StateCode.Queueing;
				threadTask.spinning = true;
				mutex.notify();
			}
		} else {
			System.out.println(this.getClass() + " task is null");
		}
	}

	/**
	 * 删除文件
	 */
	public void removeFile(String filePath) {
		// TODO 删除文件
		NucleusAssistant.getInstance().removeFile(filePath);
		;
	}

	/**
	 * 通知监听器
	 */
	public void notifyContaced(String identifier, String tag) {
		if (listener != null) {
			listener.onConvertContacted(identifier, tag);
		}
	}

	public void notifyListener(ConvertTask task, StateCode state) {

		task.setStateCode(state);
		if (listener != null) {
			if (StateCode.Queueing.getCode() == state.getCode()) {
				listener.onQueueing(task);
			}
			else if (StateCode.Started.getCode() == state.getCode()) {
				listener.onStarted(task);
			} 
			else if (StateCode.Executing.getCode() == state.getCode()) {
				listener.onExecuting(task);
			} 
			else if (StateCode.Successed.getCode() == state.getCode()) {
				listener.onCompleted(task);
			}
			else if (StateCode.Failed.getCode() == state.getCode()) {
				listener.onTaskFailed(task, state);
			}
		}

	}
}
