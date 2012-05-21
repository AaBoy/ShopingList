package edu.pernat.shopinglist.android.razredi;

import java.util.ArrayList;

public class SeznamIzBaze {
	String imeSeznama,imePosiljatelja;
	ArrayList<Integer> idBaze;

	public SeznamIzBaze(String imeSeznama, String imePosiljatelja,
			ArrayList<Integer> idBaze) {
		super();
		this.imeSeznama = imeSeznama;
		this.imePosiljatelja = imePosiljatelja;
		this.idBaze = idBaze;
	}

	public SeznamIzBaze() {
		super();
	}

	public String getImeSeznama() {
		return imeSeznama;
	}

	public void setImeSeznama(String imeSeznama) {
		this.imeSeznama = imeSeznama;
	}

	public String getImePosiljatelja() {
		return imePosiljatelja;
	}

	public void setImePosiljatelja(String imePosiljatelja) {
		this.imePosiljatelja = imePosiljatelja;
	}

	public SeznamIzBaze(String imeSeznama, String imePosiljatelja) {
		super();
		this.imeSeznama = imeSeznama;
		this.imePosiljatelja = imePosiljatelja;
		idBaze=new ArrayList<Integer>();
	}
	
	public void addIdBaze(Integer i)
	{
		idBaze.add(i);
	}
	public void setIdBaze()
	{
		idBaze=new ArrayList<Integer>();
	}
	public int getSize()
	{
		return idBaze.size();
	}
	public int getIndex(int i)
	{
		return idBaze.get(i);
	}
}
