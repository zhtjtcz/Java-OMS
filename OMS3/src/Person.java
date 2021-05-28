public class Person{
	private String Name;
	private char Sex;
	private long PhoneNum;

	public Person() {}
	public Person(String name, char sex, String phoneNum) {
		Name = name;
		Sex = sex;
		PhoneNum = Long.valueOf(phoneNum);
	}

	public String getName() {return Name;}
	public void setName(String name) {Name = name;}
	public char getSex() {return Sex;}
	public void setSex(char sex) {Sex = sex;}
	public long getPhoneNum() {return PhoneNum;}
	public void setPhoneNum(int phoneNum) {PhoneNum = phoneNum;}
	public String toString() {return "Name:" + Name + "\nSex:" + Sex + "\nPhone:" + PhoneNum;}

	public static boolean checkSex(char sex){
		if (sex!='F' && sex!='M')	return false;
		return true;
	}

	public static boolean checkNum(String phoneNum){
		if (phoneNum.length() != 11)	return false;
		long num=Long.valueOf(phoneNum);
		if (num<=0)	return false;
		if (num/100000000 < 130 || num/100000000 > 187)	return false;
		if ((num%10000)/10 < 31 || (num%10000)/10 > 71)	return false;
		return true;
	}

	public static Person addPerson(String name, char sex, String phoneNum){
		if (checkSex(sex) == false){
			System.out.println("Sex illegal");
			return null;
		}
		if (checkNum(phoneNum) == false){
			System.out.println("Phone number illegal");
			return null;
		}
		long x=Long.valueOf(phoneNum);
		if ((sex=='F' && x%10!=1) || (sex=='M' && x%10!=0)){
			System.out.println("Phone number illegal");
			return null;
		}
		Person p = new Person(name,sex,phoneNum);
		return p;
	}
}