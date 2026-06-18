package Context;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {

    private static ScenarioContext instance;

    private static Map<String, Object> contextMap = new HashMap<>();

    private ScenarioContext() {
    }

    public static ScenarioContext getInstance() {
        
    	if (instance == null) {
            instance = new ScenarioContext();
        }
        return instance;
    }

    // SET
    public void set(String key, Object value) {
        contextMap.put(key, value);
    }

    // GENERIC GET (NO MANUAL CAST NEEDED)
    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        return (T) contextMap.get(key);
    }

    // OPTIONAL: remove single key
    public void remove(String key) {
        contextMap.remove(key);
    }

    // OPTIONAL: clear all between scenarios
    public void clear() {
        contextMap.clear();
    }
}
