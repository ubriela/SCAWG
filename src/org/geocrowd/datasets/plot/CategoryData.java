package org.geocrowd.datasets.plot;

public class CategoryData {
	double value;
	String rowKey;
	String columnKey;
	public CategoryData(double value, String rowKey, String columnKey) {
		super();
		this.value = value;
		this.rowKey = rowKey;
		this.columnKey = columnKey;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getRowKey() {
		return rowKey;
	}
	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}
	public String getColumnKey() {
		return columnKey;
	}
	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}
}
