package base;

import java.io.*;
import java.util.HashMap;

public class TextNote extends Note implements Iconifiable{
    private String content;

    public TextNote(String title) {
        super(title);
    }

    public TextNote(String title,String content){
        super(title);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {

        if(content ==  null || content.indexOf(".") == -1)
            return "TextNote: " + super.toString();
        else
            return "TextNote: " + super.toString() + "\t" +((content.split("\\."))[0]).substring(0,Math.min((content.split("\\."))[0].length(),30)) ;

    }

    public TextNote(File f){
        super(f.getName());
        this.content = getTextFromFile(f.getAbsolutePath());
    }

    private  String getTextFromFile(String absolutePath){
        String result ="";
        try{
            //Generated by gpt : what java.io.BufferReader does
            BufferedReader reader = new BufferedReader(new FileReader(absolutePath)); //bind to src
            String sentence;
            while((sentence = reader.readLine()) != null){
                result+=sentence; //read all from src
            }
            reader.close(); //store in result

        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return result;
    }

    public void exportTextToFile(String pathFolder){
        try{
            File file = new File(pathFolder + File.separator + super.getTitle().replace(" ","_") +".txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter((file))); //Similar to Printwriter
            writer.write(content); //Write the whole content
            writer.close(); //No try() -> remember to close
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public void iconify() {
        char firstCharacter = content.charAt(0);
        if('a' <= firstCharacter && firstCharacter <='z') {
            content = new IconLowerCase(firstCharacter).base + content.substring(1); //why can't directly assign
        }else if('A' <= firstCharacter && firstCharacter <='Z'){
            content = new IconUpperCase(firstCharacter).base + content.substring(1);
        }else if('0' <= firstCharacter && firstCharacter <='9'){
            content = new IconDigit(firstCharacter).base + content.substring(1);
        }
    }

    public TextNote(TextNote note){
        super(note);
        this.content = note.content;

    }


    public Character unknownFunction(){
        HashMap<Character,Integer> count = new HashMap<Character,Integer>();
        String a = this.getTitle() + this.getContent();
        int b = 0;
        Character r = ' ';
        for (int i = 0; i < a.length(); i++) {
            Character c = a.charAt(i);
            if (c <= 'Z' && c >= 'A' || c <= 'z' && c >= 'a') {
                if (!count.containsKey(c)) {
                    count.put(c, 1);
                } else {
                    count.put(c, count.get(c) + 1);
                    if (count.get(c) > b) {
                        b = count.get(c);
                        r = c;
                    }
                }
            }
        }
        return r;
    }
}
