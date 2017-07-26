package com.satisfaction;


public class SatisfactionQuantifierMain {
	public static void main(String[] args) throws Exception {
		SatisfactionQuantifier satisfactionQuantifier = new SatisfactionQuantifier();
		String fileName = System.getProperty("input.file");
		int time = Integer.parseInt(System.getProperty("input.time"));
		satisfactionQuantifier.getHigestSatisfaction(time, fileName);		
	}
}
