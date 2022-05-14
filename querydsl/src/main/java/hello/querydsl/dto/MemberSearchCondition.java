package hello.querydsl.dto;

import lombok.Data;

@Data
public class MemberSearchCondition {
	// 고객 정보를 가져올 때 지정해주는 파라미터들
	private String username;
	private String teamName;
	private Integer ageGoe;
	private Integer ageLoe;
}
