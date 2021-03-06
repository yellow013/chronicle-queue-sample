package net.openhft.chronicle.queue.simple.input;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import net.openhft.chronicle.core.Jvm;
import net.openhft.chronicle.queue.ExcerptAppender;
import net.openhft.chronicle.queue.RollCycles;
import net.openhft.chronicle.queue.impl.single.SingleChronicleQueue;
import net.openhft.chronicle.queue.impl.single.SingleChronicleQueueBuilder;

/**
 * Created by catherine on 17/07/2016.
 */
public class InputMain {

//	public static void main(String[] args) {
//		String path = "backup-msg";
//		SingleChronicleQueue queue = SingleChronicleQueueBuilder.binary(path).build();
//		ExcerptAppender appender = queue.acquireAppender();
//		try (Scanner read = new Scanner(System.in)) {
//			while (true) {
//				System.out.println("type something");
//				String line = read.nextLine();
//				if (line.isEmpty())
//					break;
//				appender.writeText(line);
//			}
//		}
//		System.out.println("... bye.");
//	}

	public static void main(String[] args) {
		String path = "backup-msg";
		SingleChronicleQueue queue = SingleChronicleQueueBuilder.binary(path).rollCycle(RollCycles.MINUTELY).build();
		ExcerptAppender appender = queue.acquireAppender();
		while (true) {
			ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
			appender.writeText(now.toString());
			System.out.println(now);
			System.out.println(now.toLocalTime().toSecondOfDay() / 60);
			System.out.println(queue.cycle());
			System.out.println(now.toEpochSecond() / 60);
			Jvm.pause(10000);
		}
	}

}
