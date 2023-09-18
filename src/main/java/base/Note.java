package base;
//1. Provided Equal function prototype not match???
//2 Provdied Equal function in test case doesn't checl super class???
//3 Match expected output then fine
import java.util.Date;
public class Note {
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
}
