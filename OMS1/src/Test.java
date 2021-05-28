import java.util.Scanner;
import java.util.regex.Pattern;

class Person{
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

	static boolean checkSex(char sex){
		if (sex!='F' && sex!='M')	return false;
		return true;
	}

	static boolean checkNum(String phoneNum){
		return Pattern.matches("^(1([3-7][0-9])|(8[0-7]))[0-9]{4}0(3[1-9]|[4-6][0-9]|7[0-1])[0-1]$", phoneNum);
	}

	static Person addPerson(String name, char sex, String phoneNum){
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

public class Test {
	static void Check(String s){
		int len=s.length();
		if (len <= 1)	return;
		String []arr=s.split("\\s+");
		if (arr[0].compareTo("np") != 0)	return;
		
		//for (int i=0;i<arr.length;i++)	System.out.println(arr[i]);
		
		if (arr.length != 4)	{
			System.out.println("Arguments illegal");	return;
		}
		if (arr[2].length()!=1){
			System.out.println("Sex illegal");	return;
		}
		Person p=new Person().addPerson(arr[1],arr[2].charAt(0),arr[3]);
		if (p!=null)	System.out.println(p.toString());
	}

	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		String s;
		while (true) {
			s = in.nextLine();
			if (s.compareTo("QUIT")==0){
				System.out.println("----- Good Bye! -----");
				in.close();
				System.exit(0);
			}
			s = s.trim();
			Check(s);
		}
	}
}