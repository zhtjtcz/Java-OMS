public class Person{
	private String Name;
	private char Sex;
	private long PhoneNum;
	private String PID;
	private String PWD;
	public Order POrder;

	public Person() {}
	public Person(String name, char sex, long phoneNum, String Pid) {
		Name = name;
		Sex = sex;
		PhoneNum = phoneNum;
		PID = Pid;
		PWD = "oms1921SE";
		POrder = null;
	}

	public String getName() {return Name;}
	public char getSex() {return Sex;}
	public long getPhoneNum() {return PhoneNum;}
	@Override
	public String toString() {return "PID:" + PID + ",Name:" + Name
		+ ",Sex:" + Sex + ",Phone:" + PhoneNum + ",PWD:" + PWD;}
	public String getPID() {return PID;}
	public String getPWD() {return PWD;}
	public void setPWD(String pWD) {PWD = pWD;}
}