package com.fintecho.littleforest.vo.admin;

import java.util.List;
import java.util.Map;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdminCountsVO {
	private int users;
	private int growingTrees;
	private int grownTrees;

	private int dau;
	private int wau;
	private int mau;

	private List<Map<String, Object>> monthlyNewUsers; // ym, cnt
	private List<Map<String, Object>> weekdayAttendance; // dow, cnt
	private List<Map<String, Object>> topStreaks; // userId, startDate, endDate, days
	private List<Map<String, Object>> treeLevels; // treeLevel, cnt
	private List<Map<String, Object>> grownMonthly; // ym, cnt
	private List<Map<String, Object>> pointMonthlyByType; // ym, type, amount
	private List<Map<String, Object>> giftTopSent; // sender, amount
	private List<Map<String, Object>> giftTopReceived; // receiver, amount
	private List<Map<String, Object>> salesByCategory; // category, sales, carbonEffect
	private List<Map<String, Object>> emissionMonthly; // ym, total
	private List<Map<String, Object>> communityPosts; // ym, cnt
	private List<Map<String, Object>> communityComments; // ym, cnt
	private List<Map<String, Object>> communityLikes; // ym, cnt
	private List<Map<String, Object>> donationMonthly; // ym, total, donors
}
