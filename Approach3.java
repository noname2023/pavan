package poc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Approach3 {

	static HashMap<Integer, List<Integer>> map = new HashMap<>();
	static int cacheSize = 1000;
	static int mapSize = 55;
	static {
		int j = 1;
		for (int i = 1; i <= mapSize; i++) {
			List<Integer> value = new ArrayList<>();
			for (; j <= (cacheSize * i); j++) {
				value.add(j);
			}
			map.put(i, value);
		}
	}

	public static void main(String[] args) {

		// logic(4, 999, 1000); // 1-900, 901-1800, 1801-2700, 2701-3600, 3601-4500
		logic(1, 2500, 1000); // 1-1800, 1801-3600, 3600-5400

	}

	private static void logic(int pageNumber, int pageSize, int cacheSize) {
		int startPageNo = (int) Math.floor(pageNumber * pageSize / cacheSize);
		int endPageNo = (int) Math.floor((pageSize * (pageNumber + 1) - 1) / cacheSize);

		System.out.println("startPageNo --> " + startPageNo + " endPageNo--> " + endPageNo);

		int startingIndexonStartPage = (pageNumber * pageSize) % cacheSize;
		int endIndexonEndPage = (pageSize * (pageNumber + 1) - 1) % cacheSize;

		System.out.println("startingIndexonStartPage  --> " + startingIndexonStartPage + " endIndexonEndPage --> "
				+ endIndexonEndPage);
		int diff = endPageNo - startPageNo;

		if (pageSize == cacheSize) {
			List<Integer> subList = map.get(startPageNo + 1).subList(startingIndexonStartPage, endIndexonEndPage + 1);
			System.out.println(subList);
		} else if (pageSize < cacheSize) {
			if (diff == 0 || startPageNo == endPageNo) {
				List<Integer> subList = map.get(startPageNo + 1).subList(startingIndexonStartPage,
						endIndexonEndPage + 1);
				System.out.println(subList);
			} else if (diff == 1) {
				List<Integer> result = new ArrayList<>();
				List<Integer> firstList = map.get(startPageNo + 1).subList(startingIndexonStartPage, cacheSize);
				result.addAll(firstList);
				List<Integer> secondList = map.get(endPageNo + 1).subList(0, endIndexonEndPage + 1);
				result.addAll(secondList);
				System.out.println(" result --> " + result);
			} else if (diff > 1) {
				System.out.println("difference is greater than 1");
//TODO Re-think if this is possible or not
			}

		} else if (pageSize > cacheSize) {
			if (diff == 0 || startPageNo == endPageNo) {
				// TODO - Not possible
			} else if (diff == 1) {
				List<Integer> result = new ArrayList<>();
				List<Integer> firstSubList = map.get(startPageNo + 1).subList(startingIndexonStartPage, cacheSize);
				result.addAll(firstSubList);

				List<Integer> secondSubList = map.get(endPageNo + 1).subList(0, endIndexonEndPage + 1);
				result.addAll(secondSubList);

				System.out.println("result --> " + result);
			} else if (diff > 1) {
				// System.out.println("diff before " + diff);

				List<Integer> result = new ArrayList<>();
				List<Integer> firstSubList = map.get(startPageNo + 1).subList(startingIndexonStartPage, cacheSize);
				result.addAll(firstSubList);

				diff--;
				int nextPage = startPageNo + 1;
				// System.out.println("diff now " + diff);
				for (int i = 0; i < diff; i++) {
					result.addAll(map.get(nextPage + 1).subList(0, cacheSize));
					nextPage++;
				}

				List<Integer> lastSubList = map.get(endPageNo + 1).subList(0, endIndexonEndPage + 1);
				// System.out.println("lastSubList --> " + lastSubList);
				result.addAll(lastSubList);
				System.out.println("result --> " + result);

			}
		}

	}
}
