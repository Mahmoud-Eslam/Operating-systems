
public class process extends Thread {

	PCB pcb;
	static int id = 0;

	public process(PCB pcb) {
		this.pcb = pcb;
		id++;

	}

	@Override
	public void run() {
		String s = (String) MemoryManager.read(this.pcb.address);// take it from memo
		if (this.pcb.type == "interrupt") {
			// "stop 1"

		}
		String[] S1 = s.split(" ");

		try {
			System.out.println("current addvertisement :" + s);
			for (int i = Integer.parseInt(S1[S1.length - 1]); i > 0; i--) {
				// check state if interrupted
				Thread.sleep(1000);
				System.out.println(i);
			}
			System.out.println("releasing semaphore");
			CPUSch.semaphore.release();
			System.out.println("Process with ID:" + this.pcb.id + " is finished");
			CPUSch.allProcesses.get(this.pcb.address).pcb.setState("Finished");
			for (int i = 0; i < CPUSch.runningProcesses.size(); i++) {
				if (CPUSch.runningProcesses.get(i).pcb.id == CPUSch.allProcesses.get(this.pcb.address).pcb.id) {
					CPUSch.finishedProcesses.add(CPUSch.runningProcesses.get(i));
					CPUSch.runningProcesses.remove(i);
				}
			}
			CPUSch.Schedule();
		} catch (InterruptedException e) {
			System.out.println("fy error");
		}

	}

	public static void decId() {
		id--;
	}
}

class PCB {
	public String type;
	// unique id for each process
	int id;
	String state;
	int address;
	int size;

	public PCB(int id, int address, String state, int size) {
		this.id = id;
		this.state = state;
		this.address = address;
		this.size = size;
	}

	public void setState(String state) {

		this.state = state;

	}

	public String getState() {
		return this.state;

	}
}
