//import java.util.*;

class Customer extends Person{
	private boolean isVIP;
	private double balance;
/*	private boolean isDining;
	private Vector<Dish> orderDish;
	private Vector<Dish> waitDish;*/

	public Customer(String name, char sex, long phoneNum, String PID){
		super(name, sex, phoneNum, PID);
		isVIP = false;
		balance = 0;
		/*isDining = false;
		orderDish = new Vector<Dish> (0);
		waitDish = new Vector<Dish> (0);*/
	}

	public double getBalance() {return balance;}
	public void setBalance(double balance) {this.balance = balance;}
	public boolean isVIP() {return isVIP;}
	public void setVIP(boolean isVIP) {this.isVIP = isVIP;}
}