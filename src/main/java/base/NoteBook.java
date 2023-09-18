package base;

import java.util.ArrayList;

public class NoteBook {
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
}
