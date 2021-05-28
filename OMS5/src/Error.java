class Error {
	public Error(){}
	public void FoundErr(){System.out.println("Command not exist");}
	public void CountErr(){System.out.println("Params' count illegal");}
	public boolean CkTypeLen(String []a, int type, int TarType, int len){
		if (type!=TarType){
			FoundErr();	return false;
		}
		if (a.length != len){
			CountErr();	return false;
		}
		return true;
	}
}