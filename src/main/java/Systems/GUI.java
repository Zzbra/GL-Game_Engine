package Systems;
import Message.*;

/**
 *  Classe Système UI.
 */

public class GUI extends SystemClass {

    public GUI(MessageBus messageBus) {
        super(messageBus);
    }

    @Override
    public void hanldeMessage(Message message) {
        switch (message.getType()){
            case "OPEN_INVENTORY":
                System.out.println("UI opens inventory");
                break;
            default:
                break;
        }
    }


}
