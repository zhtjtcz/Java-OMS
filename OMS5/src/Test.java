import java.util.*;

public class Test {
	private static Menu menu;
	private static PersonList PList;
	public static Scanner in;
	private static Error Err = new Error();

	public static void SUDO(String []arr){
		if (arr.length != 1){
			Err.FoundErr();	return;
		}
		System.out.println("Enter sudo mode");
		while (true){
			String []a=in.nextLine().trim().split("\\s+");
			switch (a[0]){
				case "sncu":	PList.AddPerson(a,0);	break;
				case "snwa":	PList.AddPerson(a,1);	break;
				case "snco":	PList.AddPerson(a,2);	break;
				case "dcu":		PList.DelPerson(a,0);	break;
				case "dwa":		PList.DelPerson(a,1);	break;
				case "dco":		PList.DelPerson(a,2);	break;
				case "pp":		PList.PP(a);	break;
				case "nd": 		menu.ND(a);	break;
				case "pm": 		menu.PM(a, in);	break;
				case "gd": 		menu.GD(a, in);	break;
				case "SQ":
					if (a.length != 1){
						Err.CountErr();	break;
					}
					System.out.println("Quit sudo mode");	return;
				default: System.out.println("Call sudo method illegal");	break;
			}
		}
	}

	public static void LOGIN(String []a){
		if (a[1].compareTo("-n")!=0 && a[1].compareTo("-i")!=0){
			Err.FoundErr();	return;
		}
		if (a.length != 4){
			Err.CountErr();	return;
		}
		PList.Login(a, in, menu);
	}

	public static void main(String[] args){
		in = new Scanner(System.in);
		
		String s;
		menu = Menu.GetMenu();
		PList = PersonList.getInstance();
		while (true) {
			s = in.nextLine();
			if (s.compareTo("QUIT")==0){
				System.out.println("----- Good Bye! -----");
				in.close();
				System.exit(0);
			}
			String []arr=s.trim().split("\\s+");
			switch (arr[0]){
				case "SUDO": 	SUDO(arr);	break;
				case "login": 	LOGIN(arr);	break;
				default:  		Err.FoundErr(); break;
			}
		}
	}
}