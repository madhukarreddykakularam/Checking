package RegexExExpression;

public class Class1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
String abc="123abc.456def.789ghi";
String alpha = "123456";
String regex = "[0-9]+"; //+is used so that it will take repeated values
System.out.println(abc.replaceAll("[^a-zA-z0-9]+", ""));
System.out.println(alpha.matches(regex));
}

}