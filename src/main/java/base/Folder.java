package base;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Folder implements Comparable<Folder>, java.io.Serializable, Cloneable {
    private ArrayList<Note> notes;
    private String name;

    public Folder(String name){
        this.name = name;
        notes = new ArrayList<Note>();
    }

    public void addNote(Note note){
        this.notes.add(note);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public String toString() {
        int  nText = 0;
        int nImage = 0;
        for(Note note: notes){
            if(note instanceof ImageNote){
                nImage++;
            }else{
                nText++;
            }
        }

        return name + ':' + nText + ':' + nImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Folder folder = (Folder) o;

        return Objects.equals(name, folder.name);
    }

    @Override
    public int compareTo(Folder o) {
        return (this.name).compareTo(o.name);
    }

    public void sortNotes(){
        Collections.sort(notes);
    }

    //Helper function
    public static boolean check(String msg, String keywords){

        String[] result = keywords.toLowerCase().replace(" or ","@").split(" "); //"Key1 or Key2 or Key3 Key4" --> "Key1@Key2@Key3 Key4"

        for(String s: result){
            boolean Not_found= true;

            for(String inner_s: s.split("@")){                  //[ [Key1,Key2,Key3] [Key4]] --> get one is fine
                if(msg.toLowerCase().indexOf(inner_s) == -1){
                    continue;
                }else{
                    Not_found = false;
                }
            }

            if(Not_found)
                return false;
        }


        return true;
    }



    public List<Note> searchNotes(String keywords){
        List<Note> list = new ArrayList<Note>();

        for(Note n: notes){
            if(n instanceof ImageNote){
                if(check(n.getTitle(),keywords)){
                    list.add(n);
                }
            } else if (n instanceof TextNote) {
                if(check(n.getTitle() + " " +((TextNote) n).getContent(),keywords)){ //Search Title & content at the same time
                    list.add(n);
                }
            }
        }


        return list;

    }

    @Override
    public Folder clone(){
        try{
            Folder clone = (Folder)super.clone();
            clone.notes = new ArrayList<>();//File.notes -> [][][][][][] <- clone.notes
            for(int i = 0; i < notes.size(); i++){
                if(notes.get(i) instanceof TextNote)
                    clone.notes.add(i,new TextNote((TextNote) notes.get(i)));
                else
                    clone.notes.add(i,new ImageNote((ImageNote)notes.get(i)));
            }
            return clone;
        }catch(CloneNotSupportedException e){
            throw new AssertionError();
        }
    }
}
