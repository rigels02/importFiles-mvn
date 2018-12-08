package com.molport.impo.parsers;

import java.util.ArrayList;
import java.util.List;

import com.molport.impo.out.OutputFormater;

/**
 * Requirement: In case of error in field value the field must be set as Null.
 * Fields: packUnitList, qtyMeasureList, etc. must be the same size. This is
 * required for {@link OutputFormater#outPrint(java.io.PrintStream, List)} to
 * work properly.
 * 
 * @author raitis
 */
public class Rec {

	private long lineNum;
	private String fileName;
	private String catNum;
	private List<Float> packUnitList = new ArrayList<>();
	private List<String> qtyMeasureList = new ArrayList<>();
	private List<Float> priceList = new ArrayList<>();
	private List<String> currList = new ArrayList<>();
	private String priceGroup;
	private String casNum;
	private List<String> errors = new ArrayList<>();

	public long getLineNum() {
		return lineNum;
	}

	public void setLineNum(long lineNum) {
		this.lineNum = lineNum;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCatNum() {
		return catNum;
	}

	public void setCatNum(String catNum) {
		this.catNum = catNum;
	}

	public List<String> getCurrList() {
		return currList;
	}

	public void setCurrList(List<String> currList) {
		this.currList = currList;
	}

	public String getPriceGroup() {
		return priceGroup;
	}

	public void setPriceGroup(String priceGroup) {
		this.priceGroup = priceGroup;
	}

	public String getCasNum() {
		return casNum;
	}

	public void setCasNum(String casNum) {
		this.casNum = casNum;
	}

	public List<String> getErrors() {
		return errors;
	}

	public List<Float> getPackUnitList() {
		return packUnitList;
	}

	public void setPackUnitList(List<Float> packUnitList) {
		this.packUnitList = packUnitList;
	}

	public List<String> getQtyMeasureList() {
		return qtyMeasureList;
	}

	public void setQtyMeasureList(List<String> qtyMeasureList) {
		this.qtyMeasureList = qtyMeasureList;
	}

	public List<Float> getPriceList() {
		return priceList;
	}

	public void setPriceList(List<Float> priceList) {
		this.priceList = priceList;
	}

	@Override
	public String toString() {
		return "fileName=" + fileName + " [" + lineNum + "], catNum=" + catNum + ", packUnit=" + packUnitList
				+ ", quantityMeasure=" + qtyMeasureList + ", price=" + priceList + ", curr=" + currList
				+ ", priceGroup=" + priceGroup + ", casNum=" + casNum + " ,Errors: " + errors;
	}

	public String print() {
		StringBuilder sb = new StringBuilder();
		sb.append("fileName=").append(fileName).append("[").append(lineNum).append("] catNum= ").append(catNum)
				.append("\tPrieGroup= ").append(priceGroup).append("\tCASNum= ").append(casNum).append("\n")
				.append("packUnitList:\n");
		for (float un : packUnitList) {
			sb.append("un: ").append(un).append("\n");
		}
		for (String qty : qtyMeasureList) {
			sb.append("qtyMeas: ").append(qty).append("\n");
		}
		for (Float pr : priceList) {
			sb.append("price: ").append(pr).append("\n");
		}
		for (String cur : currList) {
			sb.append("Cur: ").append(cur).append("\n");
		}
		return sb.toString();
	}
}
