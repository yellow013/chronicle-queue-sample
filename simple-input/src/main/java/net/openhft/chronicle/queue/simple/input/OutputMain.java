package net.openhft.chronicle.queue.simple.input;

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
		ExcerptTailer tailer = queue.createTailer();
		System.out.println(tailer.index());
		while (true) {
			String text = tailer.readText();
			if (text == null)
				Jvm.pause(500);
			else {
				System.out.println("tailer.cycle() -> " + tailer.cycle());
				System.out.println(text);
			}
		}
	}

}
