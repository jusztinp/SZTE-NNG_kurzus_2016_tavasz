package challenge;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

public class MagicalLandTest {
	private MagicalLand land;
	private Logger loggerMock;

	@Test
	public void patTest(){
		givenAMagicalLand();
		whenRunIsCalled();
		thenAllTheUnicornsPattedOnlyOnce();
	}

	private void thenAllTheUnicornsPattedOnlyOnce() {
		verify(loggerMock, times(1)).info("UNICORN #1: PAT THIS UNICORN ONCE");
		verify(loggerMock, times(1)).info("UNICORN #2: PAT THIS UNICORN ONCE");
		verify(loggerMock, times(1)).info("END OF PROGRAM");
	}

	private void whenRunIsCalled() {
		land.run(loggerMock);
	}

	private void givenAMagicalLand() {
		land = new MagicalLand();
		loggerMock = Mockito.mock(Logger.class);
	}
}
