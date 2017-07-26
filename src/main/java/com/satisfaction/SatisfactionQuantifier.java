package com.satisfaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class SatisfactionQuantifier {

	final static Logger logger = Logger.getLogger(SatisfactionQuantifier.class);

	public int getHigestSatisfaction(int inputTime, String fileName) throws Exception {
		logger.info("Given Time :" + inputTime);
		BufferedReader br = null;
		FileReader fr = null;
		int highestSatisfactionRatio = 0;
		try {
			br = new BufferedReader(new FileReader(fileName));
			int menuItems = parse(br.readLine(), 1, 0);
			List<Map<Integer, Integer>> timeToSatisfaction = readFile(br, menuItems);
			List<String> resList = new ArrayList<String>();
			getPairs(timeToSatisfaction, 0, 0, inputTime, "", resList);
			for (String comb : resList) {
				int total = getTotal(comb, timeToSatisfaction);
				if (total > highestSatisfactionRatio) {
					highestSatisfactionRatio = total;
				}
			}
		} catch (IOException e) {
			logger.error("Invalid input parsing the file.");
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				logger.error("Failed in closing the resources.");
			}
		}
		logger.info("Highest Satisfaction Value :" + highestSatisfactionRatio);
		return highestSatisfactionRatio;
	}

	/**
	 * Reads file and returns map of unique List of map  [time][satisfaction]
	 * @param br
	 * @param menuItems
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	private List<Map<Integer, Integer>> readFile(BufferedReader br, int menuItems)
			throws IOException, Exception {
		List<Map<Integer, Integer>> satisfactionList = new ArrayList<Map<Integer, Integer>>();
		for (int i = 0; i < menuItems; i++) {
			String nextLine = br.readLine();
			if (nextLine != null && !nextLine.equals("")) {
				int sat = parse(nextLine, 1, 0);
				int time = parse(nextLine, 2, 0);
				Map<Integer, Integer> timeToSatisfactionMap = new HashMap<Integer, Integer>();
				timeToSatisfactionMap.put(time, sat);
				satisfactionList.add(timeToSatisfactionMap);
			} else {
				logger.warn("End of File");
			}
		}
		logger.debug(satisfactionList);
		return satisfactionList;
	}

	/**
	 * returns total satisfaction for a given combination
	 * @param comb
	 * @param map
	 * @return
	 */
	private int getTotal(String comb, List<Map<Integer, Integer>> mapList) {
		String[] arr = comb.split(",");
		int total = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != null && !"".equals(arr[i]))
				total = total + mapList.get(Integer.parseInt(arr[i])).entrySet().iterator().next().getValue();
		}
		return total;
	}

	/**
	 * Extracts value for given file input line
	 * @param input
	 * @param a
	 * @param b
	 * @return
	 * @throws Exception
	 */
	private int parse(String input, int a, int b) throws Exception {
		try {
			return Integer.parseInt(input.split("\\[")[a].split("\\]")[b]);
		} catch (Exception e) {
			logger.error("Unparsable input " + input);
			throw new Exception("Unparsable input " + input);
		}
	}
	
	/**
	 * Gets combination for a given time
	 * @param list
	 * @param index
	 * @param current
	 * @param goal
	 * @param result
	 * @param resList
	 */
	private void getPairs(List<Map<Integer, Integer>> list, int index, int current,
			int goal, String result, List<String> resList) {
		if (list.size() < index || current > goal)
			return;
		for (int i = index; i < list.size(); i++) {
			int timeKey = list.get(i).entrySet().iterator().next().getKey();
			if (current + timeKey == goal) {
				resList.add(result + "," + i);
			} else if (current + timeKey < goal) {
				getPairs(list, i + 1, current + timeKey, goal, result + "," + i, resList);
			}
		}
	}
}
