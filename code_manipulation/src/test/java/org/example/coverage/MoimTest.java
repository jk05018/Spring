package org.example.coverage;

import org.junit.Assert;
import org.junit.Test;

public class MoimTest{

	@Test
	public void 퇴근() throws Exception{
		Moim moim = new Moim();
		moim.amountOfWork = 100;
		moim.amountOfFinishedWork = 10;
		Assert.assertFalse(moim.canGoHome());

	}

}
