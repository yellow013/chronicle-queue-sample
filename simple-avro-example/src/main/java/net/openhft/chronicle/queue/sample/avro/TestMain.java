package net.openhft.chronicle.queue.sample.avro;

public class TestMain {

	public static void main(String[] args) {
		try {
			User user = User.newBuilder().setName("Jim").build();
			System.out.println(user);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
