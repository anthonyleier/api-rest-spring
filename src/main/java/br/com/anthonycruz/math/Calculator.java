package br.com.anthonycruz.math;

public class Calculator {
	public Double sum(Double numberOne, Double numberTwo) {
		return numberOne + numberTwo;
	}

	public Double sub(Double numberOne, Double numberTwo) {
		return numberOne - numberTwo;
	}

	public Double mult(Double numberOne, Double numberTwo) {
		return numberOne * numberTwo;
	}

	public Double div(Double numberOne, Double numberTwo) {
		return numberOne / numberTwo;
	}

	public Double avg(Double numberOne, Double numberTwo) {
		return (numberOne + numberTwo) / 2D;
	}

	public Double sqrt(Double number) {
		return Math.sqrt(number);
	}

}
