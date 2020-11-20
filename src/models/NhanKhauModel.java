package models;

public class NhanKhauModel {
	int id;
	String cmnd;
	String ten;
	int tuoi;
	String sdt;
	
	public NhanKhauModel() {}
	
	public NhanKhauModel(String cmnd, String ten, int tuoi , String sdt) {
		this.cmnd = cmnd;
		this.ten=ten;
		this.tuoi=tuoi;
		this.sdt = sdt;
	}
	
	public NhanKhauModel(int id,String cmnd, String ten, int tuoi , String sdt) {
		this.id=id;
		this.cmnd = cmnd;
		this.ten=ten;
		this.tuoi=tuoi;
		this.sdt = sdt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCmnd() {
		return cmnd;
	}

	public void setCmnd(String cmnd) {
		this.cmnd = cmnd;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public int getTuoi() {
		return tuoi;
	}

	public void setTuoi(int tuoi) {
		this.tuoi = tuoi;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}
	
	
}
