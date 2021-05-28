public class Person{
	private String Name;
	private char Sex;
	private long PhoneNum;
	private String PID;
	private String PWD;

	public Person() {}
	public Person(String name, char sex, long phoneNum, String Pid) {
		Name = name;
		Sex = sex;
		PhoneNum = phoneNum;
		PID = Pid;
		PWD = "oms1921SE";
	}

	public String getName() {return Name;}
	public void setName(String name) {Name = name;}
	public char getSex() {return Sex;}
	public void setSex(char sex) {Sex = sex;}
	public long getPhoneNum() {return PhoneNum;}
	public void setPhoneNum(int phoneNum) {PhoneNum = phoneNum;}
	@Override
	public String toString() {return "PID:" + PID + ",Name:" + Name
		+ ",Sex:" + Sex + ",Phone:" + PhoneNum + ",PWD:" + PWD;}
	public String getPID() {return PID;}
	public void setPID(String pID) {PID = pID;}
	public String getPWD() {return PWD;}
	public void setPWD(String pWD) {PWD = pWD;}
}