package JavaNIO;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;

public class CharBufferTest {
  public static void main(String[] args) throws Exception{
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("abc".getBytes());
    ByteArrayInputStream byteArrayInputStream1 = new ByteArrayInputStream("def".getBytes());
    CharBuffer charBuffer = CharBuffer.allocate(1024);
    new InputStreamReader(byteArrayInputStream).read(charBuffer);
    new InputStreamReader(byteArrayInputStream1).read(charBuffer);
    charBuffer.flip();
    System.out.println(charBuffer);
  }
}
