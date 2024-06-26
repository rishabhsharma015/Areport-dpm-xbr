package com.example.areport_dpm_xbrl;


import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Config {

    public static String monetaryItem = "EUR";

    public static Map<String, String> lang = new HashMap<>();
	static {
		lang.put("0", "en");
		lang.put("1", "bs-Latn-BA");
		lang.put("2", "ba");
	}

    public static Map<String, String> confSet = new HashMap<String, String>() ;
	static{
    	confSet.put("lab-codes", "lab-codes");
    	confSet.put("rend", "rend");
    	confSet.put("def", "def");
    	confSet.put("pre", "pre");
    	confSet.put("tab", "tab");
    };

    public static Map<String, String> moduleSet = new HashMap<String, String>() ;
	static{
    	moduleSet.put("pre", "pre");
    	moduleSet.put("rend", "rend");
    	moduleSet.put("lab-codes", "lab-codes");
    };

    public static Map<String, String> createInstance = new HashMap<String, String>(); 
	static{
    	createInstance.put("rend", "rend");
    	createInstance.put("def", "def");
    };

    public static String owner = "www.eba.europa.eu";

    public static String publicDir() {
        return storage_path("app/public/tax/");
    }

	public static String prefixOwner = "fba";

    public static String setLogoPath() {
        return Paths.get(public_path(), "images", "logo.svg").toString();
    }


	public static Map<String, Map<String, String>> owners() {
        Map<String, Map<String, String>> ownersMap = new HashMap<>();
        ownersMap.put("fba", new HashMap<String, String>() {
        	{
	            put("namespace", "http://www.fba.ba");
	            put("officialLocation", "http://www.fba.ba/xbrl");
	            put("prefix", "fba");
	            put("copyright", "(C) FBA");
        	}
        });
        ownersMap.put("eba", new HashMap<String, String>() {
        	{
	            put("namespace", "http://www.eba.europa.eu/xbrl/crr");
	            put("officialLocation", "http://www.eba.europa.eu/eu/fr/xbrl/crr");
	            put("prefix", "eba");
	            put("copyright", "(C) EBA");
        	}
        });
        ownersMap.put("audt", new HashMap<String, String>() {
        	{
            put("namespace", "http://www.auditchain.finance/");
            put("officialLocation", "http://www.auditchain.finance/fr/dpm");
            put("prefix", "audt");
            put("copyright", "(C) Auditchain");
        	}
        });
        return ownersMap;
    }

    public static String tmpPdfDir() {
        return Paths.get(storage_path(), "logs").toString();
    }
    
    
    // Created Methods.
    
    private static String storage_path() {
		return null;
	}

	private static String storage_path(String string) {
		return null;
	}
	
	private static String public_path() {
		return null;
	}

}


