package com;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Text {
    private ArrayList<String> text = new ArrayList<>();

    ArrayList<String> getText() { return text; }

    public void clear() { text.clear(); }

    public void replaceText(String request, String replace) {
        for (int i = 0; i < text.size(); ++i) {
            Pattern pattern = Pattern.compile(request);
            Matcher matcher = pattern.matcher(text.get(i));
            text.set(i, matcher.replaceAll(replace));
        }
    }

    public Map<Integer,Integer> findText(String find) {
        Map<Integer,Integer> map = new TreeMap<Integer,Integer>();
        for (int i = 0; i < text.size(); ++i) {
            Pattern pattern = Pattern.compile(find);
            Matcher matcher = pattern.matcher(text.get(i));
            int cmd = 0;
            while(matcher.find()){
                cmd++;
            }
            map.put(i,cmd);
        }
        return map;
    }

    public void readText(Scanner scanner) {
        text.clear();
        while (scanner.hasNextLine())
            text.add(scanner.nextLine());
        scanner.close();
    }

//    public void setText(String name) throws IOException {
//        File file = new File(name);
//        if(!file.exists()){
//            file.createNewFile();
//        }
//        PrintWriter writer = new PrintWriter(file);
//        for(var c : text){
//            writer.println(c);
//        }
//        writer.close();
//    }

//    public void saveText(String name) throws IOException {
//        setText(name);
//    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : text)
            stringBuilder.append(str).append('\n');
        return stringBuilder.toString();
    }

    /*public void OpenText(String name) throws IOException {
        ReadText(name);
    }*/
}


