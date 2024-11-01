package telran.net;

import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;
import telran.view.StandartInputOutput;

public class Main {
    static ReverseLengthClient reverseLengthClient;

    public static void main(String[] args) {
        Item[] items = {
            Item.of("Start session", Main::startSession),
            Item.of("Exit", Main::exit, true)
        };
        Menu menu = new Menu("Reverse-Length Application", items);
        menu.perform(new StandartInputOutput());
    }

    static void startSession(InputOutput io) {
        String host = io.readString("Enter hostname");
        int port = io.readNumberRange("Enter port", "Wrong port", 3000, 50000).intValue();
        if (reverseLengthClient != null) {
            reverseLengthClient.close();
        }
        reverseLengthClient = new ReverseLengthClient(host, port);
        Item[] items = {
            Item.of("Get reverse string", Main::getReverseString),
            Item.of("Get length of string", Main::getLengthOfString),
            Item.ofExit()
        };
        Menu menu = new Menu("String operations", items);
        menu.perform(io);
    }

    static void getReverseString(InputOutput io) {
        String string = io.readString("Enter any string");
        String response = reverseLengthClient.sendAndReceive(string, "reverse");
        io.writeLine(response);
    }

    static void getLengthOfString(InputOutput io) {
        String string = io.readString("Enter any string");
        String response = reverseLengthClient.sendAndReceive(string, "length");
        io.writeLine(response);
    }

    static void exit(InputOutput io) {
        if (reverseLengthClient != null) {
            reverseLengthClient.close();
        }
    }
}