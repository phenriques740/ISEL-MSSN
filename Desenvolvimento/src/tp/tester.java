package tp;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class tester {

	public static void main(String[] args) {
		Method[] cenas = processing.core.PApplet.class.getDeclaredMethods();
		for (Method method : cenas) {
			if (Modifier.isPublic(method.getModifiers())) {
				System.out.println(method);
			}
		}
	}
}
