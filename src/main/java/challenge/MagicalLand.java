package challenge;

import org.slf4j.Logger;

public class MagicalLand {

	public void run(Logger logger) {
		for (int i = 0; i < (Math.random() * 500) + 2; i++) {	
			if (Unicorn.pat()) {
				logger.info("UNICORN #1: PAT THIS UNICORN ONCE");
			}
		}

		for (int i = 0; i < (Math.random() * 500) + 2; i++) {
			if (Unicorn.pat()) {
				logger.info("UNICORN #2: PAT THIS UNICORN ONCE");
			}
		}
		logger.info("END OF PROGRAM");
	}
}
