package Message;
import Systems.*;
import java.util.ArrayList;

/**
 *  Classe du MessageBus. Elle doit avoir une référence vers toutes les classes System.
 *  La fonction refresh simule ce qui se passe à chaque frame. Je ne sais pas si c'est
 *  comme ça que l'on doit l'implémenter, ça me parait couteux.
 *  Je ne sais pas non plus à quel moment on enlève le message du bus car plusieurs classes
 *  système peuvent avoir à réagir au même message.
 */

public class MessageBus {
    private ArrayList<Message> messageBus;
    private ArrayList<SystemClass> systemsArray;

    public MessageBus(){
        this.messageBus = new ArrayList<>();
        this.systemsArray = new ArrayList<>();
    }

    public void addSystem(SystemClass SC){
        this.systemsArray.add(SC);
    }

    public void post(Message message){
        messageBus.add(message);
    }

    public void refresh() {
        for (Message msg : messageBus) {
            for (SystemClass SC : systemsArray) {
                SC.hanldeMessage(msg);
            }
        }

        messageBus = new ArrayList<Message>();
    }

    public ArrayList<Message> getMessageBus() {
        return messageBus;
    }
}
