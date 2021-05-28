import java.util.Scanner;
public class Test {
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		String s;
		while (true) {
			s = in.nextLine();
			if (s.length()==4){
				if (s.compareTo("QUIT")==0){
					System.out.println("----- Good Bye! -----");
					in.close();
					System.exit(0);
				}
			}
		}
	}
}