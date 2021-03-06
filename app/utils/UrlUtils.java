package utils;

public class UrlUtils {
    
    // converts the name to a URL friendly name
    // lowercase
    // consecutive whitespace becomes a single dash
    // anything not a-z, 0-9, or a dash is removed
    public static String getUrlFriendlyName(String name) {
        String urlFriendlyName = name.toLowerCase().replaceAll("\\s+", "-");
        urlFriendlyName = urlFriendlyName.replaceAll("[^a-z0-9\\-]","");
        return urlFriendlyName;
    }

}
