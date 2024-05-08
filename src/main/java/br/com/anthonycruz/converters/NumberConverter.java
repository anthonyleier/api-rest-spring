package br.com.anthonycruz.converters;

public class NumberConverter {
	public static Double convertToDouble(String number) {
		if (number == null)
			return 0D;
		String replacedNumber = number.replaceAll(",", ".");
		if (isNumeric(replacedNumber))
			return Double.parseDouble(replacedNumber);
		return 0D;
	}

	public static boolean isNumeric(String number) {
		if (number == null)
			return false;
		String replacedNumber = number.replaceAll(",", ".");
		return replacedNumber.matches("[-+]?[0-9]*\\.?[0-9]+");
	}
}
