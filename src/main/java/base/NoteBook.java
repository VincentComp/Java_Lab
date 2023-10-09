package base;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoteBook implements java.io.Serializable{
    private static final long serialVersionID = 1L;
    private ArrayList<Folder> folders;
    public NoteBook(){
        folders = new ArrayList<Folder>();
    }

    public ArrayList<Folder> getFolders() {
        return folders;
    }

    public boolean insertNote(String folderName, Note note) {
        Folder f = new Folder(folderName);

        boolean not_existed = true;
        for(Folder folder: folders){
            if(f.equals(folder)){
                not_existed = false;
            }
        }
        if(not_existed){
            folders.add(f);
        }

        //Always exist
        for(Folder folder: folders){
            if(f.equals(folder)){
                for(Note n : folder.getNotes()){
                    if(n.equals(note)){
                        System.out.println("Creating note " + note.getTitle() + " under folder " + folderName + " failed.");
                        return false;
                    }
                }
                folder.getNotes().add(note);
                return true;
            }
        }

        return false;

    }

    public boolean createTextNote(String folderName, String title){
        TextNote note = new TextNote(title);
        return insertNote(folderName,note);
    }

    public boolean createImageNote(String folderName, String title){
        ImageNote note = new ImageNote(title);
        return insertNote(folderName, note);
    }

    public void sortFolders(){
        for(Folder folder: folders)
            folder.sortNotes();
        Collections.sort(folders);
    }

    public boolean createTextNote(String folderName, String title, String content){
        TextNote note = new TextNote(title,content);
        return insertNote(folderName,note);
    }

    public List<Note> searchNotes(String keywords){
        List<Note> list = new ArrayList<Note>();
        for(Folder folder: folders)
            list.addAll(folder.searchNotes(keywords));
        return list;
    }

    public boolean createNote(String folderName, String title){
        return createImageNote(folderName,title);
    }

    public boolean createNote(String folderName,String title, String content){
        return createTextNote(folderName,title,content);
    }

    public boolean save(String file){
        try{
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(this);
            out.close();
        }catch(Exception e){
            return false;
        }return true;
    }

    public NoteBook(String file){
        try{
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fis);
            NoteBook n = (NoteBook)(in.readObject());
            this.folders = n.folders; //Get object also has to asign
            in.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
