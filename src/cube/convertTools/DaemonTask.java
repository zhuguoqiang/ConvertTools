package cube.convertTools;

import java.util.Queue;

public class DaemonTask extends Thread {

	private Queue<ConvertTask> taskQueue = null;
	private Object mutex = null;
	boolean spinning = true;

	public DaemonTask(Object mutex, Queue<ConvertTask> taskQueue) {
		super();
		this.mutex = mutex;
		this.taskQueue = taskQueue;
	}

	@Override
	public void run() {

		synchronized (this.mutex) {
			while (spinning) {
				if (this.taskQueue.isEmpty()) {
					try {
						this.mutex.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					ConvertTask task = taskQueue.poll();
					
					task.state = StateCode.Executing;

					// 开始转换
					task.fireConvert();
				}
			}
		}
	}
}
