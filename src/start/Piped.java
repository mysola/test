package start;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Piped {

	public static void main(String[] args) throws Exception {
//		LinkedBlockingQueue<Character> lbq = new LinkedBlockingQueue<Character>();
		Sender sender = new Sender();
		Receiver receier = new Receiver(sender);
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(receier);
		TimeUnit.SECONDS.sleep(1);
		System.out.println();
		exec.execute(sender);

		exec.shutdown();
	}

}
class Sender implements Runnable{
	private PipedWriter out = new PipedWriter();
	public PipedWriter getOut() {
		return out;
	}
//	private LinkedBlockingQueue bq;
//	public Sender(LinkedBlockingQueue bq){
//		this.bq = bq;
//	}
	public void run(){
		try{
			for(char c = 'A'; c < 'z' ; c++){
				out.write(c);
				System.out.println("write:"+c);
		//		bq.put(c);
		//		TimeUnit.MILLISECONDS.sleep(1);
			}
		}catch(IOException e){
			System.out.println("Sender interrupted");
		}
//		}catch(IOException e){
//			System.out.println("Sender write Exception");
//		}
	}
}

class Receiver implements Runnable{
	private PipedReader in;

	public Receiver(Sender sender) throws IOException {
		in = new PipedReader(sender.getOut());
	}
//	private LinkedBlockingQueue bq;
//	public Receiver(LinkedBlockingQueue bq){
//		this.bq = bq;
//	}
	public void run(){
		try{
			while(in.read()!=0)
				System.out.println("read:"+(char)in.read());
//				try {
//					System.out.println("read:"+bq.take());
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			
		}catch(IOException e){
			System.out.println("Receiver read Exception");
		}
	}
}


