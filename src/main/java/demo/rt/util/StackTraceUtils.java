package demo.rt.util;

public class StackTraceUtils {

    public static String getStackMsg(StackTraceElement[] stackTraceElements) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < stackTraceElements.length; i++) {
            StackTraceElement element = stackTraceElements[i];
            sb.append(element.toString() + "\n");
        }
        return sb.toString();
    }
}
