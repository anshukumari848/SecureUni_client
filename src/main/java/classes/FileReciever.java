package classes;

import Utilities.Status;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class FileReciever {
    //use to recieve file
    public SocketChannel createServerSocketChannel() {
        ServerSocketChannel serverSocketChannel=null;
        SocketChannel socketChannel = null;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(9999));
            socketChannel = serverSocketChannel.accept();
            System.out.println("Connection established...." + socketChannel.getRemoteAddress());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return socketChannel;
    }
//  Reads the bytes from socket and writes to file
    public String readFileFromSocket(SocketChannel socketChannel,String quizID) {
        RandomAccessFile aFile = null;
        try {
            String cwd = System.getProperty("user.dir");
            File file=new File(cwd+"/"+quizID);
            file.createNewFile();
            aFile = new RandomAccessFile(cwd+"/"+quizID, "rw");
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            FileChannel fileChannel = aFile.getChannel();
            while (socketChannel.read(buffer)> 0) {
                buffer.flip();
                fileChannel.write(buffer);
                buffer.clear();
            }
            Thread.sleep(1000);
            fileChannel.close();
            System.out.println("End of file reached..Closing channel");
            socketChannel.close();
            return String.valueOf(Status.SUCCESS);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return String.valueOf(Status.FAILED);
        } catch (IOException e) {
            e.printStackTrace();
            return String.valueOf(Status.FAILED);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return String.valueOf(Status.FAILED);
        } catch (Exception e){
            return String.valueOf(Status.FAILED);
        }
    }

}
