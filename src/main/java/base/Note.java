package base;
//1. Provided Equal function prototype not match???
//2 Provdied Equal function in test case doesn't checl super class???
//3 Match expected output then fine
import java.util.Collections;
import java.util.Date;
public class Note implements Comparable<Note>{
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
    public int compareTo(Note o) {
        int result = (this.date).compareTo(o.date);
        return result*-1;
    }

    @Override
    public String toString() {
        return date.toString() + "\t" + title;
    }
}
