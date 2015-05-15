package cube.converttools;

import java.util.Queue;

public class DaemonTask extends Thread {

	private Queue<ConvertTask> taskQueue = null;
	boolean spinning = true;

	public DaemonTask(Queue<ConvertTask> taskQueue) {
		super();
		this.taskQueue = taskQueue;
	}

	public void shutdown() {
		this.spinning = false;
	}

	@Override
	public void run() {
		while (spinning) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			ConvertTask task = null;

			synchronized (this.taskQueue) {
				if (!this.taskQueue.isEmpty()) {
					task = this.taskQueue.poll();
				}
			}

			if (null != task) {
				task.fireConvert();
			}
		}
	}
}
