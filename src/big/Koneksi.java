package big;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Koneksi {											//kelas Koneksi
	private static Connection mysqlconnect;						//
	public static Connection koneksiDB() throws SQLException {	
		if (mysqlconnect == null) {
			try {
				String url = "jdbc:mysql://192.168.23.220:3306/db_employee" +
						"?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Jakarta";				
				String user = "sofco";
				String password = "s3234";
				DriverManager.registerDriver(new com.mysql.jdbc.Driver());
				mysqlconnect = (Connection) DriverManager.getConnection(url, user, password);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Gagal Koneksi Ke Database");
			}
		}
		return mysqlconnect;
	}

	public static boolean insertEmployee(Employee newEmployee) {                        //newEmployee var untuk menampung inputann user baru
        boolean success = false;
        String sql = "INSERT INTO employee VALUES ('" + newEmployee.getNama()
                + "','" + newEmployee.getNoId() + "','" + newEmployee.getNoHp() + "','" + newEmployee.getAlamat()
                + "','" + newEmployee.getEmail() + "')";                                    //SQL database biasa
        java.sql.Connection conn = null;
        try {
            conn = (java.sql.Connection) Koneksi.koneksiDB();                            //mencoba konek ke Class Koneksi function koneksiDB()
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);                //pst mengirim query secara terpisah antara query inti dengan “data” dari query.
            pst.execute();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();                                                        //mentrace error yang didapat pada block catch
            System.out.println(e.toString());
        }
        return success;
    }

    public static boolean removeEmployee(Employee employees) {                            //employees karena mau hapus employee-employee berdasar ID, kalo employee gakbisa karena employee hanyalah templete kosong? :)
        boolean success = false;
        String sql = "delete from employee where noid = '" + employees.getNoId() + "'";
        java.sql.Connection conn = null;                                                //null untuk:
        try {
            conn = (java.sql.Connection) Koneksi.koneksiDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
        return success;
    }

    public static boolean updateEmployee(Employee updatedEmployee) {
        boolean success = false;
        String sql = "UPDATE employee SET nama='" + updatedEmployee.getNama() + "', noId=" + updatedEmployee.getNoId()
                + ", noHp=" + updatedEmployee.getNoHp() + ", alamat='" + updatedEmployee.getAlamat()
                + "', email='" + updatedEmployee.getEmail() + "' WHERE noid =" + updatedEmployee.getNoId();
        java.sql.Connection conn = null;
        try {
            conn = (java.sql.Connection) Koneksi.koneksiDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            success = pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
        return success;
    }

    public static List<Employee> sync() throws SQLException {           
        List<Employee> employeeList = new ArrayList<>();
        try {
            java.sql.Connection conn = (java.sql.Connection) big.Koneksi.koneksiDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet sql = stm.executeQuery("select * from employee");
            //			conn = (java.sql.Connection) Koneksi.koneksiDB();							//mencoba konek ke Class Koneksi function koneksiDB()
            //			java.sql.PreparedStatement pst = conn.prepareStatement(sql);				//pst mengirim query secara terpisah antara query inti dengan “data” dari query.
            //			success = pst.execute();													//pst di eksekusi masuk ke sucess??
            while (sql.next()) {
                String nama = sql.getString("nama");
                int noId = sql.getInt("noId");
                int noHp = sql.getInt("noHp");
                String alamat = sql.getString("alamat");
                String email = sql.getString("email");
                employeeList.add(new Employee(nama, noId, noHp, alamat, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();                                                        //mentrace error yang didapat pada block catch
            System.out.println(e.toString());
        }
        return employeeList;
    }
}