package start;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueue {

	public static void main(String[] args) throws InterruptedException {
		ToastQueue dryQueue = new ToastQueue(),
				bufferedQueue = new ToastQueue(),
				finishedQueue = new ToastQueue();
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new Toaster(dryQueue));
		exec.execute(new Butterer(dryQueue, bufferedQueue));
		exec.execute(new Jammer(finishedQueue, bufferedQueue));
		exec.execute(new Eater(finishedQueue));
		TimeUnit.SECONDS.sleep(1);
		exec.shutdownNow();
	}

}

class Toast{
	public enum Status{DRY,BUFFERED,JAMMED};
	private Status status = Status.DRY;
	private final int id;
	public Toast(int id){
		this.id = id;
	}
	public void buffer(){
		status = Status.BUFFERED;
	}
	public void jam(){
		status = Status.JAMMED;
	}
	public Status getStatus(){
		return status;
	}
	public int getId(){
		return id;
	}
	public String toString(){
		return "Toast " + id + ":" + status;
	}
}

class ToastQueue extends LinkedBlockingQueue<Toast>{}

class Toaster implements Runnable{
	private ToastQueue toastQueue;
	private int count = 0;
	private Random rand = new Random(47);
	public Toaster(ToastQueue tq){
		toastQueue = tq;
	}
	public void run(){
		try{
			while(!Thread.interrupted()){
				TimeUnit.MILLISECONDS.sleep(rand.nextInt(5));
				Toast t = new Toast(count++);
				System.out.println(t);
				toastQueue.put(t);
			}
		}catch(InterruptedException e){
			System.out.println("Toaster interrupted");
		}
		System.out.println("Toaster off");
	}
}

class Butterer implements Runnable{
	private ToastQueue dryQueue,bufferedQueue;
	public Butterer(ToastQueue dry,ToastQueue buffered){
		dryQueue = dry;
		bufferedQueue = buffered;
	}
	public void run(){
		try{
			while(!Thread.interrupted()){
				Toast t = dryQueue.take();
				t.buffer();
				System.out.println(t);
				bufferedQueue.put(t);
			}
		}catch(InterruptedException e){
			System.out.println("Butterer interrupted");
		}
		System.out.println("Butterer off");
	}
}

class Jammer implements Runnable{
	private ToastQueue finishedQueue,bufferedQueue;
	public Jammer(ToastQueue finished,ToastQueue buffered){
		finishedQueue = finished;
		bufferedQueue = buffered;
	}
	public void run(){
		try{
			while(!Thread.interrupted()){
				Toast t = bufferedQueue.take();
				t.jam();
				System.out.println(t);
				finishedQueue.put(t);
			}
		}catch(InterruptedException e){
			System.out.println("Jammer interrupted");
		}
		System.out.println("Jammer off");
	}
}

class Eater implements Runnable{
	private ToastQueue finishedQueue;
	private int count = 0;
	public Eater(ToastQueue finished){
		finishedQueue = finished;
	}
	public void run(){
		try{
			while(!Thread.interrupted()){
				Toast t = finishedQueue.take();
				if(t.getId()!=count++||t.getStatus()!=Toast.Status.JAMMED){
					System.out.println("Error: "+t);
				}
				else{
					System.out.println("Eat! "+t);
				}
			}
		}catch(InterruptedException e){
			System.out.println("Eater interrupted");
		}
		System.out.println("Eater off");
	}
}







