package net.openhft.chronicle.queue.simple.input;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import net.openhft.chronicle.core.Jvm;
import net.openhft.chronicle.queue.ExcerptTailer;
import net.openhft.chronicle.queue.RollCycles;
import net.openhft.chronicle.queue.impl.single.SingleChronicleQueue;
import net.openhft.chronicle.queue.impl.single.SingleChronicleQueueBuilder;

/**
 * Created by catherine on 17/07/2016.
 */
public class OutputMain {

	public static void main(String[] args) {
		String path = "backup-msg";

		SingleChronicleQueue queue = SingleChronicleQueueBuilder.binary(path).rollCycle(RollCycles.MINUTELY).build();
		ZonedDateTime firstQueueInitCycle = ZonedDateTime.ofInstant(Instant.ofEpochSecond(queue.firstCycle() * 60),
				ZoneId.systemDefault());
		System.out.println("firstQueueInitCycle -> " + firstQueueInitCycle + " | " + queue.firstCycle());
		ZonedDateTime queueInitCycle = ZonedDateTime.ofInstant(Instant.ofEpochSecond(queue.cycle() * 60),
				ZoneId.systemDefault());
		System.out.println("queueInitCycle -> " + queueInitCycle + " | " + queue.cycle());

		LocalDateTime wantOf = LocalDateTime.of(LocalDate.of(2019, 9, 24), LocalTime.of(15, 10));
		long want = wantOf.toEpochSecond(ZoneOffset.ofHours(+8)) / 60;
		System.out.println("wantOf epoch ->" + want);
		System.out.println("new -> " + queue.cycle());

		ExcerptTailer tailer = queue.createTailer();
		ZonedDateTime tailerInitCycle = ZonedDateTime.ofInstant(Instant.ofEpochSecond(tailer.cycle() * 60),
				ZoneId.systemDefault());
		System.out.println("tailerInitCycle -> " + tailerInitCycle);

		long index = RollCycles.MINUTELY.toIndex(26155150, 0);
		System.out.println(index);
		//boolean moveToIndex = tailer.moveToIndex(index);
		//System.out.println(moveToIndex);
		
		while (true) {
			String text = tailer.readText();
			if (text == null)
				Jvm.pause(500);
			else {
				System.out.println(tailer.cycle());
				System.out.println(tailer.index());
				// Date date = new Date(TimeUnit.MINUTES.toMillis(tailer.cycle()));
				// System.out.println(date);
				// System.out.println("tailer.cycle() -> " + tailer.cycle());
				System.out.println(text);
				System.out.println("=================================");
			}
		}
	}

}
