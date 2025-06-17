package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Bc;

public class BcDAO {
	// 引数card指定された項目で検索して、取得されたデータのリストを返す
	public List<Bc> select(Bc card) {
		Connection conn = null;
		List<Bc> cardList = new ArrayList<Bc>();

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/webapp2?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "SELECT number, company, department, position, name, furigana, address, "
					+ "zipcode, man_phone, corp_phone, man_email, corp_email, remarks " + "FROM Bc "
					+ "WHERE company LIKE ? AND " + "department LIKE ? AND " + "position LIKE ? AND "
					+ "name LIKE ? AND " + "furigana LIKE ? AND " + "address LIKE ? AND " + "zipcode LIKE ? AND "
					+ "man_phone LIKE ? AND " + "corp_phone LIKE ? AND " + "man_email LIKE ? AND "
					+ "corp_email LIKE ? AND " + "remarks LIKE ? " + "ORDER BY number";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる

			if (card.getCompany() != null) {
				pStmt.setString(1, "%" + card.getCompany() + "%");
			} else {
				pStmt.setString(1, "%");
			}

			if (card.getDepartment() != null) {
				pStmt.setString(2, "%" + card.getDepartment() + "%");
			} else {
				pStmt.setString(2, "%");
			}

			if (card.getPosition() != null) {
				pStmt.setString(3, "%" + card.getPosition() + "%");
			} else {
				pStmt.setString(3, "%");
			}

			if (card.getName() != null) {
				pStmt.setString(4, "%" + card.getName() + "%");
			} else {
				pStmt.setString(4, "%");
			}

			if (card.getFurigana() != null) {
				pStmt.setString(5, "%" + card.getFurigana() + "%");
			} else {
				pStmt.setString(5, "%");
			}

			if (card.getAddress() != null) {
				pStmt.setString(6, "%" + card.getAddress() + "%");
			} else {
				pStmt.setString(6, "%");
			}

			if (card.getZipcode() != null) {
				pStmt.setString(7, "%" + card.getZipcode() + "%");
			} else {
				pStmt.setString(7, "%");
			}

			if (card.getMan_phone() != null) {
				pStmt.setString(8, "%" + card.getMan_phone() + "%");
			} else {
				pStmt.setString(8, "%");
			}

			if (card.getCorp_phone() != null) {
				pStmt.setString(9, "%" + card.getCorp_phone() + "%");
			} else {
				pStmt.setString(9, "%");
			}

			if (card.getMan_email() != null) {
				pStmt.setString(10, "%" + card.getMan_email() + "%");
			} else {
				pStmt.setString(10, "%");
			}

			if (card.getCorp_email() != null) {
				pStmt.setString(11, "%" + card.getCorp_email() + "%");
			} else {
				pStmt.setString(11, "%");
			}

			if (card.getRemarks() != null) {
				pStmt.setString(12, "%" + card.getRemarks() + "%");
			} else {
				pStmt.setString(12, "%");
			}

			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();

			// 結果表をコレクションにコピーする
			while (rs.next()) {
				Bc bc = new Bc(rs.getInt("number"), rs.getString("company"), rs.getString("department"),
						rs.getString("position"), rs.getString("name"), rs.getString("furigana"),
						rs.getString("address"), rs.getString("zipcode"), rs.getString("man_phone"),
						rs.getString("corp_phone"), rs.getString("man_email"), rs.getString("corp_email"),
						rs.getString("remarks"));
				cardList.add(bc);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			cardList = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			cardList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					cardList = null;
				}
			}
		}

		// 結果を返す
		return cardList;
	}

	// 引数cardで指定されたレコードを登録し、成功したらtrueを返す
	public boolean insert(Bc card) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/webapp2?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "INSERT INTO Bc VALUES (0, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			if (card.getCompany() != null) {
				pStmt.setString(1, card.getCompany());
			} else {
				pStmt.setString(1, "");
			}

			if (card.getDepartment() != null) {
				pStmt.setString(2, card.getDepartment());
			} else {
				pStmt.setString(2, "");
			}

			if (card.getPosition() != null) {
				pStmt.setString(3, card.getPosition());
			} else {
				pStmt.setString(3, "");
			}

			if (card.getName() != null) {
				pStmt.setString(4, card.getName());
			} else {
				pStmt.setString(4, "");
			}

			if (card.getFurigana() != null) {
				pStmt.setString(5, card.getFurigana());
			} else {
				pStmt.setString(5, "");
			}

			if (card.getAddress() != null) {
				pStmt.setString(6, card.getAddress());
			} else {
				pStmt.setString(6, "");
			}

			if (card.getZipcode() != null) {
				pStmt.setString(7, card.getZipcode());
			} else {
				pStmt.setString(7, "");
			}

			if (card.getMan_phone() != null) {
				pStmt.setString(8, card.getMan_phone());
			} else {
				pStmt.setString(8, "");
			}

			if (card.getCorp_phone() != null) {
				pStmt.setString(9, card.getCorp_phone());
			} else {
				pStmt.setString(9, "");
			}

			if (card.getMan_email() != null) {
				pStmt.setString(10, card.getMan_email());
			} else {
				pStmt.setString(10, "");
			}

			if (card.getCorp_email() != null) {
				pStmt.setString(11, card.getCorp_email());
			} else {
				pStmt.setString(11, "");
			}

			if (card.getRemarks() != null) {
				pStmt.setString(12, card.getRemarks());
			} else {
				pStmt.setString(12, "");
			}

			// SQL文を実行する
			if (pStmt.executeUpdate() == 1) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		// 結果を返す
		return result;
	}

	// 引数cardで指定されたレコードを更新し、成功したらtrueを返す
	public boolean update(Bc card) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/webapp2?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "UPDATE Bc SET " + "company = ?, " + "department = ?, " + "position = ?, " + "name = ?, "
					+ "furigana = ?, " + "address = ?, " + "zipcode = ?, " + "man_phone = ?, " + "corp_phone = ?, "
					+ "man_email = ?, " + "corp_email = ?, " + "remarks = ? " + "WHERE number = ?";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			if (card.getCompany() != null) {
				pStmt.setString(1, card.getCompany());
			} else {
				pStmt.setString(1, null);
			}

			if (card.getDepartment() != null) {
				pStmt.setString(2, card.getDepartment());
			} else {
				pStmt.setString(2, null);
			}

			if (card.getPosition() != null) {
				pStmt.setString(3, card.getPosition());
			} else {
				pStmt.setString(3, null);
			}

			if (card.getName() != null) {
				pStmt.setString(4, card.getName());
			} else {
				pStmt.setString(4, null);
			}

			if (card.getFurigana() != null) {
				pStmt.setString(5, card.getFurigana());
			} else {
				pStmt.setString(5, null);
			}

			if (card.getAddress() != null) {
				pStmt.setString(6, card.getAddress());
			} else {
				pStmt.setString(6, null);
			}

			if (card.getZipcode() != null) {
				pStmt.setString(7, card.getZipcode());
			} else {
				pStmt.setString(7, null);
			}

			if (card.getMan_phone() != null) {
				pStmt.setString(8, card.getMan_phone());
			} else {
				pStmt.setString(8, null);
			}

			if (card.getCorp_phone() != null) {
				pStmt.setString(9, card.getCorp_phone());
			} else {
				pStmt.setString(9, null);
			}

			if (card.getMan_email() != null) {
				pStmt.setString(10, card.getMan_email());
			} else {
				pStmt.setString(10, null);
			}

			if (card.getCorp_email() != null) {
				pStmt.setString(11, card.getCorp_email());
			} else {
				pStmt.setString(11, null);
			}

			if (card.getRemarks() != null) {
				pStmt.setString(12, card.getRemarks());
			} else {
				pStmt.setString(12, null);
			}

			// 最後に主キー（int型）number
			pStmt.setInt(13, card.getNumber());

			// SQL文を実行する
			if (pStmt.executeUpdate() == 1) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		// 結果を返す
		return result;
	}

	// 引数cardで指定された番号のレコードを削除し、成功したらtrueを返す
	public boolean delete(Bc card) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/webapp2?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "DELETE FROM Bc WHERE number=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			pStmt.setInt(1, card.getNumber());

			// SQL文を実行する
			if (pStmt.executeUpdate() == 1) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		// 結果を返す
		return result;
	}

	public int countAll() { // 現在登録されている件数カウント
		Connection conn = null;
		int count = 0;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/webapp2?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			String sql = "SELECT COUNT(*) AS cnt FROM Bc";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt("cnt");
			}

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			count = 0;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return count;
	}

}
