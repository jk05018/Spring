package org.example.coverage;

public class Moim {
	int amountOfWork;
	int amountOfFinishedWork;

	public boolean canGoHome() {
		if (amountOfFinishedWork == 0) {
			return false;
		}

		if (amountOfFinishedWork < amountOfWork) {
			return false;
		}

		return true;
	}
}
