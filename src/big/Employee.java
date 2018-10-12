package big;


//class dari sebuah objek == Employee
public class Employee { 			//employee adalah sebuah KTM atau template
    private String nama;			//ktm mempunyai nama
    private int noId; 				//no id
    private int noHp; 				//no hp
    private String alamat; 			//alamat
    private String email; 			//email

    Employee() { //cons class

    }

    Employee (String nama, int noId, int noHp, String alamat, String email ){
        this.nama = nama;
        this.noId = noId;
        this.noHp = noHp;
        this.alamat = alamat;
        this.email = email;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getNoId() {
        return noId;
    }

    public void setNoId(int noId) {
        this.noId = noId;
    }

    public int getNoHp() {
        return noHp;
    }

    public void setNoHp(int noHp) {
        this.noHp = noHp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}