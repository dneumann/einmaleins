package dn.einmaleins;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Calculator {

	private int number1;
	private int number2;
	private Map<String, Integer> hints = new HashMap<>();
	
	public Calculator() {
		hints.put("3*4", R.string.answer3x4);
		hints.put("3*5", R.string.answer3x5);
		hints.put("3*6", R.string.answer3x6);
		hints.put("3*7", R.string.answer3x7);
		hints.put("3*8", R.string.answer3x8);
		hints.put("3*9", R.string.answer3x9);
		hints.put("4*4", R.string.answer4x4);
		hints.put("4*5", R.string.answer4x5);
		hints.put("4*6", R.string.answer4x6);
		hints.put("4*7", R.string.answer4x7);
		hints.put("4*8", R.string.answer4x8);
		hints.put("4*9", R.string.answer4x9);
		hints.put("5*5", R.string.answer5x5);
		hints.put("5*6", R.string.answer5x6);
		hints.put("5*7", R.string.answer5x7);
		hints.put("5*8", R.string.answer5x8);
		hints.put("5*9", R.string.answer5x9);
		hints.put("6*6", R.string.answer6x6);
		hints.put("6*7", R.string.answer6x7);
		hints.put("6*8", R.string.answer6x8);
		hints.put("6*9", R.string.answer6x9);
		hints.put("7*7", R.string.answer7x7);
		hints.put("7*8", R.string.answer7x8);
		hints.put("7*9", R.string.answer7x9);
		hints.put("8*8", R.string.answer8x8);
		hints.put("8*9", R.string.answer8x9);
		hints.put("9*9", R.string.answer9x9);
	}

	public void createNewExercise() {
		Random rand = new Random();
		int random1 = rand.nextInt(7) + 3;
		int random2 = rand.nextInt(7) + 3;
		if (random1 == number1 && random2 == number2 || random1 == 3 && random2 == 3) {
			createNewExercise();
		} else {
			number1 = random1;
			number2 = random2;
		}
	}

	public String getMultiExercise() {
		return "  " + number1 + " · " + number2 + " =";
	}

	public String getMultiAnswer() {
		return "" + number1 * number2;
	}

	public int getHint() {
		String hintKey = "";
		if (number1 <= number2) {
			hintKey = number1 + "*" + number2;
		} else {
			hintKey = number2 + "*" + number1;
		}
		Integer hint = hints.get(hintKey);
		if (hint == null) {
			return 0;
		}
		return hint;
	}

	public String getDiviExercise() {
		return "  " + number1 * number2 + " : " + number1 + " =";
	}

	public String getDiviAnswer() {
		return "" + number2;
	}

}
