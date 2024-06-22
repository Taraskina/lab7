package org.example.commands.utility;

import org.example.exceptions.minusVibe;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.HashMap;

import static org.example.utility.ServerMessaging.nioRead;
import static org.example.utility.ServerMessaging.nioSend;

public class CommandMapper {

    public static HashMap<String, CommandTypes> nameToTypeMap = new HashMap<>();

    public static void setCommands(SocketChannel chanel) throws IOException {
        chanel.configureBlocking(true);
        try {
            nioSend(chanel, "commands");
            String coded = nioRead(chanel).getMessages().get(0);
            Arrays.stream(coded.split(";"))
                    .forEach(w -> nameToTypeMap.
                            put(w.split(",")[0],
                                    CommandTypes.valueOf(w.split(",")[1])));
        } catch (IOException | minusVibe ignored) {
        }
        chanel.configureBlocking(false);

    }

    private CommandMapper() {
    }
}
