import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class CPUSch {
	static ArrayList<process> readyProcesses = new ArrayList<process>();
	static ArrayList<process> allProcesses = new ArrayList<process>();
	static ArrayList<process> runningProcesses = new ArrayList<process>();
	static ArrayList<process> blockedProcesses = new ArrayList<process>();
	static ArrayList<process> finishedProcesses = new ArrayList<process>();


//stores processes
	static Semaphore semaphore = new Semaphore(2);

	static void Schedule() throws InterruptedException {

		execute();

	};

	public static String getWaitingProcesses() {
		String procs = "Waiting processes Queue :";
		if (readyProcesses.size() == 0)
			return "No processes waiting";
		for (int i = 0; i < readyProcesses.size(); i++) {
			procs += readyProcesses.get(i).pcb.id + "<";

		}
		return procs;
	}

	public static void printAllProcesses() {
		System.out.println(">>>>>>>>>>>>>>>>>>>ALL PROCS<<<<<<<<<<<<<<<<<<<<<<<<");
		for (int i = 0; i < allProcesses.size(); i++) {
			System.out.println("id: " + allProcesses.get(i).pcb.id + " state: " + allProcesses.get(i).pcb.state);

		}

	}

	static void execute() throws InterruptedException {
		System.out.println(getWaitingProcesses());
		semaphore.acquire();
		System.out.println("remaining semaphore tokens is " + semaphore.availablePermits());
		if (readyProcesses.size() == 0) {
			semaphore.release();
			return;
		}
		process excuted = (process) readyProcesses.get(0);

		readyProcesses.remove(0);

		runningProcesses.add(excuted);
		excuted.pcb.setState("Running");
		allProcesses.get(excuted.pcb.address).pcb.setState("Running");
		System.out.println("new State " + excuted.pcb.state);
		
		try {
			excuted.start();
		} catch (IllegalThreadStateException e) {
		}
	}

//determines which method to be executed and execute it 
	static void addProcess(process p) throws InterruptedException {
		if (semaphore.availablePermits() < 1) {
			readyProcesses.add(p);
			allProcesses.add(p);
			System.out.println(semaphore.availablePermits());
		} else {
			readyProcesses.add(p);
			allProcesses.add(p);

			Schedule();
		}
//adds process to the table

	}

	public static void printRunningProcesses() {
		System.out.println(">>>>>>>>>>>>>>>>>>>ALL RUNNING PROCS<<<<<<<<<<<<<<<<<<<<<<<<");
		for (int i = 0; i < runningProcesses.size(); i++) {
			System.out
					.println("id: " + runningProcesses.get(i).pcb.id + " state: " + runningProcesses.get(i).pcb.state);

		}

	}

	public static void printBlockedProcesses() {
		System.out.println(">>>>>>>>>>>>>>>>>>>ALL BLOCKED PROCS<<<<<<<<<<<<<<<<<<<<<<<<");
		for (int i = 0; i < blockedProcesses.size(); i++) {
			System.out
					.println("id: " + blockedProcesses.get(i).pcb.id + " state: " + blockedProcesses.get(i).pcb.state);

		}

	}

	@SuppressWarnings("deprecation")
	public static void interrupt(int id) throws Exception {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>");

		for (int i = 0; i < runningProcesses.size(); i++) {
			if (runningProcesses.get(i).pcb.id == id) {
				System.out.println("heloooooo");
				changeState(id, runningProcesses, "Blocked");
				blockedProcesses.add(runningProcesses.get(i));
				runningProcesses.get(i).stop();
				runningProcesses.remove(i);
				semaphore.release();
				Schedule();
			}
		}
	}

	private static void changeState(int id, ArrayList<process> array, String newState) {
		for (int i = 0; i < array.size(); i++) {
			if (id == array.get(i).pcb.id) {
				array.get(i).pcb.state = newState;
			}
		}

	}

	public static void resume(int id)throws Exception {
		for (int i = 0; i < blockedProcesses.size(); i++) {
			if (blockedProcesses.get(i).pcb.id == id) {
				System.out.println("heloooooo");
				changeState(id, blockedProcesses, "Ready");
				readyProcesses.add(new process(blockedProcesses.get(i).pcb));
				process.decId();
				blockedProcesses.remove(i);
				Schedule();
			}
		}
	}

	public static void printFinishedProcesses() {
		System.out.println(">>>>>>>>>>>>>>>>>>>ALL FINISHED PROCS<<<<<<<<<<<<<<<<<<<<<<<<");
		for (int i = 0; i < finishedProcesses.size(); i++) {
			System.out.println("id: " + finishedProcesses.get(i).pcb.id + " state: " + finishedProcesses.get(i).pcb.state);

		}		
	}
}