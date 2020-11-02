package Systems;
import Message.Message;
import Message.MessageBus;

/**
 *  Classe abstraite dont hérite toutes les classe System (Moteur)
 *  Elles doivent avoir une référence vers le MessageBus
 */

public abstract class SystemClass {
    MessageBus messageBus;
    public SystemClass(MessageBus messageBus){
        this.messageBus = messageBus;
    }

    public void postMessage(Message message){
        messageBus.post(message);
    }

    public abstract void hanldeMessage(Message message);
}
