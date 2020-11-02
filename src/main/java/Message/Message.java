package Message;
import java.util.Map;

/**
 *  Structure de donnée pour les message.
 *  Je ne sais pas si l'implémentation est bonne, à discuter.
 */

public class Message {
    private String type;
    private String data;
    private Map<String, String> message;

    public Message(String type, String data){
        this.type = type;
        this.data = data;
    }

    public Message(String type){
        this.type = type;
        this.data = null;
    }

    public String getType(){return this.type;}
    public String getData(){return this.data;}

    public boolean hasData(){ return this.data != null;}
}
