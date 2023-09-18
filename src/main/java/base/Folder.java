package base;

import java.util.ArrayList;
import java.util.Objects;

public class Folder {
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

}
