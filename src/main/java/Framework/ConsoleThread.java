package Framework;

import Message.Message;
import Message.MessageBus;

import java.io.Console;
import java.util.Scanner;

public class ConsoleThread extends Thread{
    private MessageBus msgBus;

    public ConsoleThread(MessageBus msgBus){
        this.msgBus = msgBus;
    }
    @Override
    public void run() {
        System.out.println("console:");
        Scanner in;
        in = new Scanner(System.in);
        while(true){
            String msg = in.nextLine();
            msgBus.post(new Message(msg));
        }
    }
}
