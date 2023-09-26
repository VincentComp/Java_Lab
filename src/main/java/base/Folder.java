package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Folder implements Comparable<Folder>{
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


        String[] result = keywords.toLowerCase().replace(" or ","@").split(" ");

        for(String s: result){
            boolean Not_found= true;

            for(String inner_s: s.split("@")){
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
                if(check(n.getTitle(),keywords) || check(((TextNote) n).getContent(),keywords)){
                    list.add(n);
                }
            }
        }


        return list;


    }
}
