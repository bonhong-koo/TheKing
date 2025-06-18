/**
 * Package Name : com.pcwk.ehr.user.domain <br/>
 * 파일명: Level.java <br/>
 */
package com.pcwk.ehr.user.domain;

public enum Level {

	GOLD(3, null), SILVER(2, GOLD), BASIC(1, SILVER);

	private final int value;// 현재 Level 값
	private final Level nextLevel; // 다음 Level

	/**
	 * @param value
	 * @param netLevl
	 */
	private Level(int value, Level netLevl) {
		this.value = value;
		this.nextLevel = netLevl;
	}

	/**
	 * @return the netLevl
	 */
	public Level getNetLevel() {
		return nextLevel;
	}

	public int getValue() {
		return value;
	}

	// 값으로 부터 Level 오브젝트 return
	public static Level valueOf(int value) {

		switch (value) {
		case 1:
			return BASIC;
		case 2:
			return SILVER;
		case 3:
			return GOLD;
		default:
			throw new AssertionError("Unknown value :" + value);
		}
	}

}
