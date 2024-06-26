package com.example.areport_dpm_xbrl;


import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;

public class DomToArray {

    public static Document invoke(String path) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document dom = builder.parse(new File(path));
        return dom;
    }

    public static Map<String, Object> getArray(String path) throws Exception {
        Document dom = invoke(path);
        Element root = dom.getDocumentElement();
        Map<String, Object> output = domnodeToArray(root);
        output.put("@root", root.getTagName());
        return output;
    }

    private static Map<String, Object> domnodeToArray(Node node) {
        Map<String, Object> output = new HashMap<>();
        switch (node.getNodeType()) {
            case Node.CDATA_SECTION_NODE:
            case Node.TEXT_NODE:
                return Collections.singletonMap("textContent", node.getTextContent().trim());

            case Node.ELEMENT_NODE:
                NodeList childNodes = node.getChildNodes();
                for (int i = 0; i < childNodes.getLength(); i++) {
                    Node child = childNodes.item(i);
                    Map<String, Object> childOutput = domnodeToArray(child);

                    if (child.getNodeType() == Node.ELEMENT_NODE) {
                        String tagName = child.getNodeName();
                        if (!output.containsKey(tagName)) {
                            output.put(tagName, new ArrayList<>());
                        }
                        ((ArrayList<Object>) output.get(tagName)).add(childOutput);
                    } else if (childOutput != null) {
                        return childOutput;
                    }
                }

                if (node.hasAttributes()) {
                    NamedNodeMap attributes = node.getAttributes();
                    Map<String, String> attrMap = new HashMap<>();
                    for (int i = 0; i < attributes.getLength(); i++) {
                        Node attr = attributes.item(i);
                        attrMap.put(attr.getNodeName(), attr.getNodeValue());
                    }
                    output.put("@attributes", attrMap);
                }
                break;
        }
        return output;
    }

    public static List<Map<String, Object>> searchMultiDim(List<Map<String, Object>> arr, String field, String value) {
        List<Map<String, Object>> found = new ArrayList<>();
        if (!arr.isEmpty()) {
            for (Map<String, Object> row : arr) {
                if (row.containsKey(field) && value.equals(row.get(field))) {
                    found.add(row);
                }
            }
        }
        return found;
    }

    public static String searchMultiDimMultiVal(List<Map<String, Object>> arr, String value, String role) {
        List<Map<String, Object>> foundKey = new ArrayList<>();
        for (Map<String, Object> element : arr) {
            if (role.equals(element.get("role"))) {
                foundKey.add(element);
            }
        }

        if (!arr.isEmpty()) {
            if ("http://www.eba.europa.eu/xbrl/role/dpm-db-id".equals(role)) {
                List<Map<String, Object>> a = searchMultiDim(foundKey, "from", value);
                if (!a.isEmpty()) {
                    return (String) a.get(0).get("@content");
                }
            }
        }
        return null;
    }

 
    public static Map<Integer, List<String>> getPath(String path, List<String> strings, String returnPath) {
        Map<Integer, List<String>> dir = new HashMap<>();
        try {
            File folder = new File(path);
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        continue;
                    }
                    String content = file.getAbsolutePath();
                    String extension = content.substring(content.lastIndexOf(".") + 1);

                    for (int i = 0; i < strings.size(); i++) {
                        String str = strings.get(i);
                        if (content.contains(str) && "xsd".equals(extension)) {
                            if (returnPath == null) {
                                dir.computeIfAbsent(i, k -> new ArrayList<>()).add(content);
                            } else {
                                return Collections.singletonMap(0, Collections.singletonList(content));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dir;
    }


    public static String buildUrl(Map<String, String> parts) {
        String scheme = parts.getOrDefault("scheme", "") + "://";
        String host = Config.owner;
        String port = parts.containsKey("port") ? ":" + parts.get("port") : "";
        String user = parts.getOrDefault("user", "");
        String path = parts.getOrDefault("path", "");
        String query = parts.containsKey("query") ? "?" + parts.get("query") : "";
        String fragment = parts.containsKey("fragment") ? "#" + parts.get("fragment") : "";

        return scheme + user + host + port + path + query + fragment;
    }


    public static int strposArr(String haystack, String[] needles) {
        for (String needle : needles) {
            int pos = needle.indexOf(haystack);
            if (pos != -1) {
                return pos;
            }
        }
        return -1;
    }

    public static Map<String, Object> multidimensionalArrToSingle(Map<String, Object> array) {
        Map<String, Object> result = new HashMap<>();
        if (array != null) {
            for (Map.Entry<String, Object> entry : array.entrySet()) {
                if (entry.getValue() instanceof Map) {
                    result.putAll(multidimensionalArrToSingle((Map<String, Object>) entry.getValue()));
                } else {
                    result.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return result;
    }
}
