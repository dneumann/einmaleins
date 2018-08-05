package dn.einmaleins;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Calculator {

	private int number1;
	private int number2;
	private Map<String, Integer[]> answers = new HashMap<>();
	
	public Calculator() {
		answers.put("3*4", new Integer[]{R.string.answer3x4, R.drawable.image3x4});
		answers.put("3*5", new Integer[]{R.string.answer3x5, R.drawable.image3x5});
		answers.put("3*6", new Integer[]{R.string.answer3x6, R.drawable.image3x6});
		answers.put("3*7", new Integer[]{R.string.answer3x7, R.drawable.image3x7});
		answers.put("3*8", new Integer[]{R.string.answer3x8, R.drawable.image3x8});
		answers.put("3*9", new Integer[]{R.string.answer3x9, R.drawable.image3x9});
		answers.put("4*4", new Integer[]{R.string.answer4x4, R.drawable.image4x4});
		answers.put("4*5", new Integer[]{R.string.answer4x5, R.drawable.image4x5});
		answers.put("4*6", new Integer[]{R.string.answer4x6, R.drawable.image4x6});
		answers.put("4*7", new Integer[]{R.string.answer4x7, R.drawable.image4x7});
		answers.put("4*8", new Integer[]{R.string.answer4x8, R.drawable.image4x8});
		answers.put("4*9", new Integer[]{R.string.answer4x9, R.drawable.image4x9});
		answers.put("5*5", new Integer[]{R.string.answer5x5, R.drawable.image5x5});
		answers.put("5*6", new Integer[]{R.string.answer5x6, R.drawable.image5x6});
		answers.put("5*7", new Integer[]{R.string.answer5x7, R.drawable.image5x7});
		answers.put("5*8", new Integer[]{R.string.answer5x8, R.drawable.image5x8});
		answers.put("5*9", new Integer[]{R.string.answer5x9, R.drawable.image5x9});
		answers.put("6*6", new Integer[]{R.string.answer6x6, R.drawable.image6x6});
		answers.put("6*7", new Integer[]{R.string.answer6x7, R.drawable.image6x7});
		answers.put("6*8", new Integer[]{R.string.answer6x8, R.drawable.image6x8});
		answers.put("6*9", new Integer[]{R.string.answer6x9, R.drawable.image6x9});
		answers.put("7*7", new Integer[]{R.string.answer7x7, R.drawable.image7x7});
		answers.put("7*8", new Integer[]{R.string.answer7x8, R.drawable.image7x8});
		answers.put("7*9", new Integer[]{R.string.answer7x9, R.drawable.image7x9});
		answers.put("8*8", new Integer[]{R.string.answer8x8, R.drawable.image8x8});
		answers.put("8*9", new Integer[]{R.string.answer8x9, R.drawable.image8x9});
		answers.put("9*9", new Integer[]{R.string.answer9x9, R.drawable.image9x9});
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

	private String currentAnswerKey() {
		if (number1 <= number2) {
			return number1 + "*" + number2;
		} else {
			return number2 + "*" + number1;
		}
	}

	public int getHint() {
		Integer answer = answers.get(currentAnswerKey())[0];
		if (answer == null) {
			return 0;
		}
		return answer;
	}

	public int getAnswerImage() {
		Integer answerImage = answers.get(currentAnswerKey())[1];
		if (answerImage == null) {
			return 0;
		}
		return answerImage;
	}

	public String getDiviExercise() {
		return "  " + number1 * number2 + " : " + number1 + " =";
	}

	public String getDiviAnswer() {
		return "" + number2;
	}

}
