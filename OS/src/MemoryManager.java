
public class MemoryManager {
static	Object memo[]=new Object[100];
	
static int Address=0;

// Memory array that will be storing Processes 
	
	public static int store(Object data) {

		//read(Address);
		memo[Address]= data;
		Address++;
		//System.out.println("Stored in "+(Address-1));
		return Address-1;
	}
	// store data and return its address
	public static Object read(int address) {

		return memo[address];
	}


}
