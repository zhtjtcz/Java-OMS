import java.util.*;
import java.util.regex.Pattern;

class PersonList{
	private static PersonList instance;
	private int total;
	private Customer[] Cu;
	private Waiter[] Wa;
	private Cook[] Co;
	private String[] T = {"Customer", "Waiter", "Cook"};
	// The pre-saved output
	private Error Err = new Error();

	private Kitchen kitchen = new Kitchen();

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
		return Pattern.matches("^(?=.*[a-zA-Z])(?=.*\\d)[!-~]{8,18}$", PWD);
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
		if (type == 0)	Cu[index] = new Customer(name, sex, x, PID);
		else if (type == 1)	Wa[index] = new Waiter(name, sex, x, PID);
		else Co[index] = new Cook(name, sex, x, PID);
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
		if (type == 0)	Cu[index]=null;
		else if (type == 1)	Wa[index]=null;
		else Co[index]=null;
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

	public int LoginbyID(String PID, String PWD){
		if (checkPID(PID) == false){
			System.out.println("PID illegal");
			return -1;
		}
		if (samePID(PID) == false){
			System.out.println("Pid not exist");
			return -1;
		}

		int type = 0, flag = 0;
		int id = Integer.valueOf(PID.substring(2, 7));
		if (PID.substring(0,2).compareTo("Cu") == 0){
			if (Cu[id].getPWD().compareTo(PWD) != 0)	flag = -1;
		}	else if (PID.substring(0,2).compareTo("Wa") == 0){
			type=1;
			if (Wa[id].getPWD().compareTo(PWD) != 0)	flag = -1;
		}	else {
			type=2;
			if (Co[id].getPWD().compareTo(PWD) != 0)	flag = -1;
		}
		if (flag == -1){
			System.out.println("Password not match");
			return -1;
		}	else return id*10+type;
		// Type:
		// Cu -> 0
		// Wa -> 1
		// Co -> 2
		// For example, PID Cu123456 will become 1234560
		// Then, I can get the PID and type by a number rather than PID
		// It can be proved that it’s more convenient this way 
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
			if (Cu[i]!=null && Cu[i].getName().compareTo(Name)==0)	return LoginbyID(Cu[i].getPID(), PWD);
			if (Co[i]!=null && Co[i].getName().compareTo(Name)==0)	return LoginbyID(Co[i].getPID(), PWD);
			if (Wa[i]!=null && Wa[i].getName().compareTo(Name)==0)	return LoginbyID(Wa[i].getPID(), PWD);
		}
		return -1; // Make VSCode happy~
	}

	public void ChangePWD(String []a, int type, int index){
		if (Err.CkTypeLen(a, type, 0 , 3) == false)	return;
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
		Person x;
		if (type == 0)	x = Cu[index];
		else x = (type==1)?Wa[index]:Co[index];
		System.out.println("[info]\n" +
			"| name:\t" + x.getName() + "\n" +	"| Sex:\t" + x.getSex() + "\n" +
			"| Pho:\t" + x.getPhoneNum() + "\n" +	"| PID:\t" + x.getPID() + "\n" +
			"| Pwd:\t" + x.getPWD() + "\n" +	"| Type:\t"+T[type]);
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
	// Change the balance

	public void VIP(String []a, int type, int index){
		if (Err.CkTypeLen(a, type, 0, 1) == false)	return;
		double x = Cu[index].getBalance();
		if (x<200){
			System.out.println("Please recharge more");
			Cu[index].setVIP(false);	return;
		}
		Cu[index].setVIP(true);
		System.out.println("Apply VIP success");
	}

	public void Add(String []a, Menu menu, int index){
		if (a[1].compareTo("-i")!=0 && a[1].compareTo("-n")!=0){
			Err.FoundErr(); return;
		}
		if (a.length != 4){
			Err.CountErr();	return;
		}
		if (Cu[index].order == null){
			String p = ""+index;
			while (p.length()<5)	p="0"+p;
			Cu[index].order = new Order("Cu"+p+"_"+(++Cu[index].count), index);
		}
		Dish d = null;
		d = (a[1].compareTo("-i") == 0)?menu.OrderDishById(a[2]):menu.OrderDishByname(a[2]);
		if (d == null){
			System.out.println("Dish selected not exist");
			return;
		}
		if (d.getTotal()<=0){
			System.out.println("Dish selected is sold out");
			return;
		}
		if (d.getTotal() < Integer.valueOf(a[3])){
			System.out.println("Dish is out of stock");
			return;
		}
		
		d.setTotal(d.getTotal()-Integer.valueOf(a[3]));
		
		Cu[index].order.len++;
		Cu[index].order.dlist.Insert(new Dish(d.getName(), d.getDid(), d.getPrice(), Integer.valueOf(a[3])));
	}
	// Customer order a dish

	public boolean Finish(String []a, int index){
		if (a.length != 1){
			Err.CountErr();	return false;
		}
		if (Cu[index].order == null || Cu[index].order.len == 0){
			System.out.println("Please select at least one dish to your order");
			return false;
		}
		return true;
	}

	public void Order(String []a, Scanner in, int type, int index, Menu menu){
		if (Err.CkTypeLen(a, type, 0, 1) == false)	return;
		while (true){
			String []arr=in.nextLine().trim().split("\\s+");
			switch (arr[0]){
				case "add" : Add(arr,menu,index);	break;
				case "finish" : if (Finish(arr,index))	return;	else break;
				default : Err.FoundErr(); break;
			}
		}
	}

	public void Co(String []a, int type, int index){
		if (Err.CkTypeLen(a, type, 0, 1) == false)	return;
		if (Cu[index].order == null || Cu[index].order.len == 0){
			System.out.println("No order");
			return;
		}
		Cu[index].order.dlist.Print();
	}

	public void Confirm(String []a, int type, int index){
		if (Err.CkTypeLen(a, type, 0, 1) == false)	return;
		if (Cu[index].order == null){
			System.out.println("No order can be confirmed");
			return;
		}

		int pos = 0, tot = 0x3f3f3f3f;
		// Pos is the ID, tot is the number of orders received
		// 0x3f3f3f3f is large enough
		for (int i=0;i<100000;i++)
			if (Wa[i]!=null && Wa[i].OList.size()<tot){
				pos = i;	tot = Wa[i].OList.size();
			}
		// Find the waiter who is supposed to take the order
		// Because the loop goes from small to large
		// Ensure that the same number of orders when the priority to choose a small ID waiter
		// So just need to compare orders

		Wa[pos].OList.add(Cu[index].order);
		Cu[index].order = null;
		System.out.println("Order Confirmed");
	}

	public void Gl(String []a, int type, int index){
		if (Err.CkTypeLen(a, type, 1, 1) == false)	return;
		if (Wa[index].OList.size()==0){
			System.out.println("No serving order");
			return;
		}
		int num=1;
		for (Order i : Wa[index].OList)	System.out.println((num++)+". "+i);
	}

	public void Mo(String []a, int type, int index){
		if (Err.CkTypeLen(a, type, 1, 1) == false)	return;
		if (Wa[index].OList.size()==0){
			System.out.println("No serving order");
			return;
		}

		Order x = Wa[index].OList.get(0);
		Wa[index].OList.remove(0);
		Wa[index].Wait.add(x);
		kitchen.Wait.add(x);
		// The waiter delivered the order to the kitchen
		System.out.println("Manage order success");
	}

	public void Sr(String []a, int type, int index){
		if (Err.CkTypeLen(a, type, 1, 2) == false)	return;
		for (Order i : Wa[index].OList)	if (i.OrderID.compareTo(a[1])==0){
			System.out.println("Order not cooked");	return;
		}
		for (Order i : Wa[index].Wait)
			if (i.OrderID.compareTo(a[1])==0){
				System.out.println("Order not cooked");	return;
			}
		for (Order i : Wa[index].Finish)
			if (i.OrderID.compareTo(a[1])==0 && i.IsDelivered==true){
				System.out.println("Order already checkout");	return;
			}
		Order x = null;
		for (Order i : Wa[index].Finish)
			if (i.OrderID.compareTo(a[1]) == 0){
				x = i;	break;
			}
		if (x == null){
			System.out.println("Order serve illegal");	return;
		}
		double c = x.dlist.GetCharge(); 
		
		int id = x.CustomerID;
		if (Cu[id].isVIP() == true)	c*=0.8;
		if (Cu[id].getBalance() < c){
			System.out.println("Insufficient balance");	return;
		}
		System.out.print("OID:"+x.OrderID+",DISH:"+x.Sinfo());
		System.out.printf(",TOTAL:%.1f,BALANCE:%.1f\n",c,Cu[id].getBalance()-c);
		Cu[id].setBalance(Cu[id].getBalance()-c);
		x.IsDelivered = true;
	}

	public void Rw(String []a, int type, int index){
		if (Err.CkTypeLen(a, type, 1, 3) == false)	return;
		int CUID = Integer.valueOf(a[1].substring(2, 7));
		double y;
		try {
			y = Double.valueOf(a[2]);
		} catch (Exception e){
			System.out.println("Recharge input illegal");	return;
		}
		if (y<100 || y>1000){
			System.out.println("Recharge input illegal");	return;
		}
		Cu[CUID].setBalance(Cu[CUID].getBalance()+y);
		if (Cu[CUID].getBalance()>=200)	Cu[CUID].setVIP(true);
	}

	public void Cook(String []a, int type, int index){
		if (Err.CkTypeLen(a, type, 2, 1) == false || kitchen.Wait.size() <= 0)	return;
		Order x = kitchen.Wait.elementAt(0);
		kitchen.Wait.remove(0);
		System.out.println("Finish order:"+x.OrderID);
		for (int i=0;i<100000;i++)
			if (Wa[i] != null && Wa[i].Wait.capacity() != 0 && Wa[i].Wait.indexOf(x) != -1){
				Wa[i].Finish.add(x);	Wa[i].Wait.remove(x);	break;
			}
	}

	public void Login(String a[], Scanner in, Menu menu){
		int x = 0;
		x=(a[1].compareTo("-i")==0)?LoginbyID(a[2],a[3]):LogbyName(a[2],a[3]);
		if (x==-1)	return;
		
		int type = x%10, index = x/10;
		// Get the id and the type
		System.out.println("Login success");
		while (true){
			String []arr=in.nextLine().trim().split("\\s+");
			switch (arr[0]){
				case "chgpw" : 	ChangePWD(arr,type,index);	break;
				case "myinfo" : PrintInfo(type, index); break;
				case "back" : 	System.out.println("Logout success"); return;
				case "QUIT" : 	System.out.println("----- Good Bye! -----");
								in.close();	System.exit(0); break;
				case "rc" :
				case "gb" :  	CgBalance(arr,type,index); break;
				case "aplVIP": 	VIP(arr,type,index);	break;
				case "gd": 		menu.GD(arr, in);	break;
				case "pm": 		menu.PM(arr, in);	break;
				case "order": 	Order(arr, in, type, index, menu);	break;
				case "co": 		Co(arr, type, index);	break;
				case "confirm": Confirm(arr, type, index);	break;
				//Customer
				case "gl": Gl(arr, type, index);	break;
				case "mo": Mo(arr, type, index);	break;
				case "sr": Sr(arr, type, index); break;
				case "rw": Rw(arr, type, index); break;
				//Waiter
				case "cook": Cook(arr, type, index); break;
				//Cook
				default : Err.FoundErr(); break;
			}
		}
	}
}