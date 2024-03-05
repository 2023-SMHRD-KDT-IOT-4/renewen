package kr.smhrd.renewen.service;

public interface PlantStatsService {

	// 발전소 금일 누적 발전량
	public double genTotal(long plantNo);
	// 발전소 금일 현재 발전량
	public double genCurrent(long plantNo);
}
