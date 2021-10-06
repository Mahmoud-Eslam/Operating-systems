
import java.util.Scanner;

public class IO extends Thread {
	static String input;

	public static void main(String[] args) throws Exception {
//	CPUSch.Schedule();

		while (true) {
			System.out.println("enter your Addvertisement");

			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			input = sc.nextLine();
			if (input.contentEquals("all procs")) {
				CPUSch.printAllProcesses();
				continue;
			}
			if (input.contentEquals("all running")) {
				CPUSch.printRunningProcesses();
				continue;
			}
			if (input.contentEquals("all blocked")) {
				CPUSch.printBlockedProcesses();
				continue;
			}
			if (input.contentEquals("all finished")) {
				CPUSch.printFinishedProcesses();
				continue;
			}

			String[] S1 = input.split(" ");

			if (input.contains("$s")) {
				if (S1.length != 2 || !isNumber(S1[1])) {
					System.out.println("Invalid Command");
					continue;
				}

				int id = Integer.parseInt(S1[1]);

				CPUSch.interrupt(id);
				continue;
			}
			if (input.contains("$c")) {
				if (S1.length != 2 || !isNumber(S1[1])) {
					System.out.println("Invalid Command");
					continue;
				}
				int id = Integer.parseInt(S1[1]);
				CPUSch.resume(id);
				continue;
			}

			if (!(isNumber(S1[S1.length - 1]))) {
				System.out.println("enter valid number");
				continue;
			}
			int address = MemoryManager.store(input);
			int size = count(S1);
			if (size > 25) {
				System.out.println("Addvertisement cann't exceed 25 characters");
				continue;
			}
			System.out.println("input is stored at address " + address);
			process newP = new process(new PCB(address, process.id, "Ready", size));

			System.out.println("A process is created");

			System.out.println("new State " + newP.pcb.getState());
			CPUSch.addProcess(newP);

		}

	}

	private static int count(String[] s1) {
		int c = 0;
		for (int i = 0; i < s1.length - 1; i++) {
			c += s1[i].length();

		}
		return c;
	}

	private static boolean isNumber(String string) {
		try {
			Integer.parseInt(string);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}
