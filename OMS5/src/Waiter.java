import java.util.*;

class Waiter extends Person{
	public Vector<Order> OList;
	public Vector<Order> Wait;
	public Vector<Order> Finish;

	public Waiter(String name, char sex, long phoneNum, String PID){
		super(name, sex, phoneNum, PID);
		OList = new Vector<Order>(0);
		Wait = new Vector<Order>(0);
		Finish = new Vector<Order>(0);
	}
}