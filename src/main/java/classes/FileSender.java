package classes;

import mainclass.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class FileSender {

    // Establishes a socket channel connection
        public SocketChannel createChannel() {

            SocketChannel socketChannel = null;
            try {
                socketChannel = SocketChannel.open();
                SocketAddress socketAddress = new InetSocketAddress(Main.serverip, 9999);
                socketChannel.connect(socketAddress);
                System.out.println("Connected..Now sending the file");

            } catch (IOException e) {
                e.printStackTrace();
            }
            return socketChannel;
        }
    public void sendFile(SocketChannel socketChannel, String quizID) {
        RandomAccessFile aFile = null;
        try {
            String cwd = System.getProperty("user.dir");
            System.out.println(cwd+quizID);
            File file = new File(cwd+"/"+quizID);
            aFile = new RandomAccessFile(file, "r");
            FileChannel inChannel = aFile.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (inChannel.read(buffer) > 0) {
                buffer.flip();
                socketChannel.write(buffer);
                buffer.clear();
            }
            System.out.println("End of file reached..");
            socketChannel.close();
            aFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}

