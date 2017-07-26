package com.satisfaction.test;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import com.satisfaction.SatisfactionQuantifier;

public class SatisfactionQuantifierTest {

	SatisfactionQuantifier satisfactionQuantifier = new SatisfactionQuantifier();

	@Test
	public void testUniqueMenu() throws Exception {
		File file = new File("src/test/resources/correct_input.txt");
		String fileName = file.getAbsolutePath();
		assertEquals(35, satisfactionQuantifier.getHigestSatisfaction(8, fileName));
	}
	
	@Test(expected = Exception.class)
	public void testIncorrectFile() throws Exception {
		File file = new File("src/test/resources/incorrect_input.txt");
		String fileName = file.getAbsolutePath();
		satisfactionQuantifier.getHigestSatisfaction(3, fileName);
	}
	
}
