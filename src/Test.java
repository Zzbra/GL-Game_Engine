import Message.*;
import Systems.*;

/**
 *  Classe de test.
 *  On créé un MessageBus, on lui ajoute un UI qui hérite de SystemClass et on poste un message déstiné à l'UI.
 *  Ensuite on appelle la fonction refresh du MessageBus pour que l'UI réceptionne le message.
 *  Je ne sais pas si c'est efféctivement comme ça qu'on doit faire. Mais en gros je cherche à simuler l'écriture de
 *  "OPEN_INVENTORY" dans la console.
 */
public class Test {
    public static void main(String[] args) {
        MessageBus msgBus = new MessageBus();
        UI ui = new UI(msgBus);
        msgBus.addSystem(ui);
        Message msg = new Message("OPEN_INVENTORY");
        msgBus.post(msg);
        msgBus.refresh();
    }
}
