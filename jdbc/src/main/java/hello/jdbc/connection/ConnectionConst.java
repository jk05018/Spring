package hello.jdbc.connection;

public abstract class ConnectionConst {
	// JDBC에셔 연결할 정보들을 모아놓은 상수 인스턴스로 생성되면 안된다.
	// 이런 정보들은 노출되면 안되기 때문에 따로 설정 파일로 제공한다.
	public static final String URL = "jdbc:h2:tcp://localhost/~/test";
	public static final String username = "sa";
	public static final String password = "";
}
