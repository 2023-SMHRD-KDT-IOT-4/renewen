package kr.smhrd.renewen.model.api;

import lombok.Data;

@Data
public class ShotImg {

	private String plantLinkKey;
	private String cellSerialNum;
	private String img;
	private String imgName;
	private String imgExt;
	private String imgDesc;
	private String createdAt;
}