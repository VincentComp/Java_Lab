package base;

public class TextNote extends Note{
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
}
