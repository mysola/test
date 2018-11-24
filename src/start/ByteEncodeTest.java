package start;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ByteEncodeTest {
  public static void main(String[] args)throws Exception{
    FileChannel channel =
      new RandomAccessFile("/Users/wangyang/projects/test/tmpFile/t.csv","rw").getChannel();
    byte[] s = "撒旦".getBytes(StandardCharsets.UTF_8);
    ByteBuffer byteBuffer = ByteBuffer.allocate(10);
    byteBuffer.putChar('a');
    System.out.println(Arrays.toString(byteBuffer.array()));
    byteBuffer.clear();
    CharBuffer charBuffer = byteBuffer.asCharBuffer();
    charBuffer.put('a');
    System.out.println(Arrays.toString(byteBuffer.array()));
    System.out.println(byteBuffer.getChar());

    System.out.println(byteBuffer.asCharBuffer().toString());
     channel.write(byteBuffer);
//    int read = channel.read(dst);
//    dst.flip();
//    byte[] array = dst.array();
    channel.close();
  }
}