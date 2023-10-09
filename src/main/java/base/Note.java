package base;

import java.util.Collections;
import java.util.Date;
public class Note implements Comparable<Note>, java.io.Serializable{
    private Date date;
    private String title;

    public Note(String title){
        this.title = title;
        this.date = new Date();
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        Note note = (Note) o;

        return title.equals(note.title);
    }

    @Override
    public int compareTo(Note o){

        if(this instanceof TextNote && o instanceof ImageNote)
            return -1; //Text note first
        if(this instanceof ImageNote && o instanceof  TextNote)
            return 1;
        return title.compareTo(o.title); //Otherwise compare the title
    }

    /*@Override
    public int compareTo(Note o) {
        int result = (this.date).compareTo(o.date);
        return result*-1;
    }*/

    @Override
    public String toString() {
        return date.toString() + "\t" + title;
    }
}
