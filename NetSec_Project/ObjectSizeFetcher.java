import java.lang.instrument.Instrumentation;



public class ObjectSizeFetcher {

    private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
    }

    public static long getObjectSize(Object object) {
        if (instrumentation == null) {
            throw new IllegalStateException("Instrumentation agent not initialized");
        }
        return instrumentation.getObjectSize(object);
    }
}