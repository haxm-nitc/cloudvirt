import haxm.*;
public class Test {

	public static void main(String[] args) {
		LogFile lf = new LogFile("test.txt");
		lf.append("this is a test string");
		lf.close();

	}

}
