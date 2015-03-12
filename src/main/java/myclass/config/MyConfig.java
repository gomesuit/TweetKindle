package myclass.config;

import java.util.ResourceBundle;

public class MyConfig {
	private static final String FILE = "common";
	private static ResourceBundle resourceBundle;
	@SuppressWarnings("unused")
	private static MyConfig instance = new MyConfig();

	private MyConfig(){
    	resourceBundle = ResourceBundle.getBundle(FILE);
    }
    
    public static String getConfig(String key){
        return resourceBundle.getString(key);
    }
}
