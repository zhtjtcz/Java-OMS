import java.util.*;
import java.util.regex.Pattern;

class PersonList{
	private static PersonList instance;
	private int total;
	private Customer[] Cu;
	private Waiter[] Wa;
	private Cook[] Co;
	private String[] T = {"Customer", "Waiter", "Cook"};
	private Error Err = new Error();

	private PersonList(){
		total = 0;
		Cu = new Customer[100000];
		Wa = new Waiter[100000];
		Co = new Cook[100000];
	}

	public static PersonList getInstance(){
		if (instance == null)	instance = new PersonList();
		return instance;
	}

	public boolean checkSex(char sex){return (sex=='F' || sex=='M');}

	public boolean checkNum(String phoneNum){
		return Pattern.matches("^1(([3-7][0-9])|(8[0-7]))[0-9]{4}0(3[1-9]|[4-6][0-9]|7[0-1])[0-1]$", phoneNum);
	}

	public boolean checkPID(String PID){
		return Pattern.matches("^(Cu|Wa|Bo|Co)+[0-9]{5}$", PID);
	}

	public boolean checkPWD(String PWD){
		int len = PWD.length();
		if (len<8 || len>18)	return false;
		int flag=0;
		for (int i=0;i<len;i++){
			if (PWD.charAt(i)<33 || PWD.charAt(i)>126)	return false;
			if (PWD.charAt(i)>='0' && PWD.charAt(i)<='9')	flag|=1;
			if (PWD.charAt(i)>='a' && PWD.charAt(i)<='z')	flag|=2;
			if (PWD.charAt(i)>='A' && PWD.charAt(i)<='Z')	flag|=2;
		}
		return (flag==3);
	}

	public boolean checkName(String Name){
		return Pattern.matches("^[a-zA-Z0-9]{1,}$", Name);
	}

	public boolean samePhone(long x){
		for (int i=0;i<100000;i++){
			if (Cu[i]!=null && Cu[i].getPhoneNum()==x)	return true;
			if (Co[i]!=null && Co[i].getPhoneNum()==x)	return true;
			if (Wa[i]!=null && Wa[i].getPhoneNum()==x)	return true;
		}
		return false;
	}

	public boolean samePID(String x){
		for (int i=0;i<100000;i++){
			if (Cu[i]!=null && Cu[i].getPID().compareTo(x)==0)	return true;
			if (Co[i]!=null && Co[i].getPID().compareTo(x)==0)	return true;
			if (Wa[i]!=null && Wa[i].getPID().compareTo(x)==0)	return true;
		}
		return false;
	}

	public boolean sameName(String x){
		for (int i=0;i<100000;i++){
			if (Cu[i]!=null && Cu[i].getName().compareTo(x)==0)	return true;
			if (Co[i]!=null && Co[i].getName().compareTo(x)==0)	return true;
			if (Wa[i]!=null && Wa[i].getName().compareTo(x)==0)	return true;
		}
		return false;
	}

	public void AddPerson(String []a, int type){
		if (a.length != 5){
			Err.CountErr();	return;
		}
		String name = a[1], phoneNum = a[3], PID = a[4];
		char sex = a[2].charAt(0);
		if (a[2].length()!=1 || checkSex(sex) == false){
			System.out.println("Sex illegal");
			return;
		}
		if (checkNum(phoneNum) == false){
			System.out.println("Phone number illegal");
			return;
		}
		long x=Long.valueOf(phoneNum);
		if ((sex=='F' && x%10!=1) || (sex=='M' && x%10!=0)){
			System.out.println("Phone number doesn't match sex");
			return;
		}
		if (samePhone(x)){
			System.out.println("Phone number exists");
			return;
		}
		if (checkPID(PID) == false || PID.substring(0, 2).compareTo(T[type].substring(0, 2)) != 0){
			System.out.println(T[type] + " PID illegal");
			return;
		}
		if (samePID(PID)){
			System.out.println(T[type] + " PID exists");
			return;
		}
		total++;
		int index = Integer.valueOf(PID.substring(2, 7));
		switch (type){
			case 0: Cu[index] = new Customer(name, sex, x, PID);	break;
			case 1:	Wa[index] = new Waiter(name, sex, x, PID); 	break;
			case 2: Co[index] = new Cook(name, sex, x, PID); break;
		}
		System.out.println("Add new " + T[type].toLowerCase() + " success");
	}

	public void DelPerson(String []a, int type){
		if (a.length != 2){
			Err.CountErr();	return;
		}
		if (checkPID(a[1]) == false){
			System.out.println("D-" + T[type] +" PID illegal");
			return;
		}
		if (samePID(a[1]) == false){
			System.out.println("D-" + T[type] +" PID doesn't exist");
			return;
		}
		total--;
		int index = Integer.valueOf(a[1].substring(2, 7));
		switch (type){
			case 0 : Cu[index]=null; break;
			case 1 : Wa[index]=null; break;
			case 2 : Co[index]=null; break;
		}
		System.out.println("Delete "+ T[type].toLowerCase() +" success");
	}

	public void PP(String a[]){
		if (a.length != 1){
			Err.CountErr();	return;
		}
		if (total == 0){
			System.out.println("Empty person list");	return;
		}
		int num = 1;
		for (int i=0;i<100000;i++)
			if (Cu[i] != null)	System.out.println((num++)+"."+Cu[i]);
		for (int i=0;i<100000;i++)
			if (Wa[i] != null)	System.out.println((num++)+"."+Wa[i]);
		for (int i=0;i<100000;i++)
			if (Co[i] != null)	System.out.println((num++)+"."+Co[i]);
	}

	public int LogbyID(String PID, String PWD){
		if (checkPID(PID) == false){
			System.out.println("PID illegal");
			return -1;
		}
		if (samePID(PID) == false){
			System.out.println("Pid not exist");
			return -1;
		}

		int type = 0;
		int id = Integer.valueOf(PID.substring(2, 7));
		if (PID.substring(0,2).compareTo("Cu") == 0){
			if (Cu[id].getPWD().compareTo(PWD) != 0){
				System.out.println("Password not match");
				return -1;
			}
		}	else if (PID.substring(0,2).compareTo("Wa") == 0){
			type=1;
			if (Wa[id].getPWD().compareTo(PWD) != 0){
				System.out.println("Password not match");
				return -1;
			}
		}	else {
			type=2;
			if (Co[id].getPWD().compareTo(PWD) != 0){
				System.out.println("Password not match");
				return -1;
			}
		}
		return id*10+type;
	}

	public int LogbyName(String Name, String PWD){
		if (checkName(Name) == false){
			System.out.println("Pname illegal");
			return -1;
		}
		if (sameName(Name) == false){
			System.out.println("Pname not exist");
			return -1;
		}
		for (int i=0;i<100000;i++){
			if (Cu[i]!=null && Cu[i].getName().compareTo(Name)==0)	return LogbyID(Cu[i].getPID(), PWD);
			if (Co[i]!=null && Co[i].getName().compareTo(Name)==0)	return LogbyID(Co[i].getPID(), PWD);
			if (Wa[i]!=null && Wa[i].getName().compareTo(Name)==0)	return LogbyID(Wa[i].getPID(), PWD);
		}
		return -1; // Make VSCode happy~
	}

	public void ChangePWD(String []a, int type, int index){
		if (a.length != 3){
			Err.CountErr();	return;
		}
		if (checkPWD(a[1]) == false){
			System.out.println("New password illegal");
			return;
		}
		if (a[1].compareTo(a[2]) != 0){
			System.out.println("Not match");
			return;
		}
		if (type == 0)	Cu[index].setPWD(a[1]);
		else if (type == 1) Wa[index].setPWD(a[1]);
		else Co[index].setPWD(a[1]);
		System.out.println("Change password success");
	}

	public void PrintInfo(int type, int index){
		if (type == 0) System.out.println("[info]\n" +
			"| name:\t" + Cu[index].getName() + "\n" +	"| Sex:\t" + Cu[index].getSex() + "\n" +
			"| Pho:\t" + Cu[index].getPhoneNum() + "\n" +	"| PID:\t" + Cu[index].getPID() + "\n" +
			"| Pwd:\t" + Cu[index].getPWD() + "\n" +	"| Type:\tCustomer"
			);
		else if (type == 1) System.out.println("[info]\n" +
			"| name:\t" + Wa[index].getName() + "\n" +	"| Sex:\t" + Wa[index].getSex() + "\n" +
			"| Pho:\t" + Wa[index].getPhoneNum() + "\n" +	"| PID:\t" + Wa[index].getPID() + "\n" +
			"| Pwd:\t" + Wa[index].getPWD() + "\n" +	"| Type:\tWaiter"
			);
		else System.out.println("[info]\n" +
			"| name:\t" + Co[index].getName() + "\n" +	"| Sex:\t" + Co[index].getSex() + "\n" +
			"| Pho:\t" + Co[index].getPhoneNum() + "\n" +	"| PID:\t" + Co[index].getPID() + "\n" +
			"| Pwd:\t" + Co[index].getPWD() + "\n" +	"| Type:\tCook"
			);
	}

	public void CgBalance(String []a, int type, int index){
		if (type != 0){
			Err.FoundErr();	return;
		}
		if ((a[0].compareTo("rc")==0 && a.length!=2) || (a[0].compareTo("gb")==0 && a.length!=1)){
			Err.CountErr();	return;
		}
		if (a[0].compareTo("gb")==0){
			System.out.println("Balance: " + Cu[index].getBalance());
			return;
		}
		double y;
		try {
			y = Double.valueOf(a[1]);
		} catch (Exception e){
			System.out.println("Recharge input illegal");
			return;
		}
		if (y<100.0 || y>=1000.0){
			System.out.println("Recharge input illegal");
			return;
		}
		Cu[index].setBalance(Cu[index].getBalance()+y);
	}

	public void VIP(String []a, int type, int index){
		if (type != 0){
			Err.FoundErr();	return;
		}
		if (a.length!=1){
			Err.CountErr();	return;
		}
		double x = Cu[index].getBalance();
		if (x<200){
			System.out.println("Please recharge more");
			Cu[index].setVIP(false);
			return;
		}
		Cu[index].setVIP(true);
		System.out.println("Apply VIP success");
	}

	public void Login(String a[], Scanner in){
		int x = 0;
		x=(a[1].compareTo("-i")==0)?LogbyID(a[2],a[3]):LogbyName(a[2],a[3]);
		if (x==-1)	return;
		int type = x%10, index = x/10;
		System.out.println("Login success");
		String s;
		String []arr;
		while (true){
			s=in.nextLine().trim();
			arr=s.split("\\s+");
			switch (arr[0]){
				case "chgpw" : ChangePWD(arr,type,index);	break;
				case "myinfo" : PrintInfo(type, index); break;
				case "back" : System.out.println("Logout success"); return;
				case "QUIT" : System.out.println("----- Good Bye! -----");
								in.close();	System.exit(0); break;
				case "rc" :
				case "gb" :  CgBalance(arr,type,index); break;
				case "aplVIP": VIP(arr,type,index);	break;
				default : Err.FoundErr(); break;
			}
		}
	}
}