package fly.front.entity;

import java.util.ArrayList;

public class HistoryDataResult {
	
	public HistoryDataResult() {
		super();
	}

	public HistoryDataResult(ArrayList<HistoryData> list, String result, Integer pageno, String paginhtml, Integer totalitem, Integer totalpage) {
		super();
		this.list = list;
		this.result = result;
		this.pageno = pageno;
		this.paginhtml = paginhtml;
		this.totalitem = totalitem;
		this.totalpage = totalpage;
	}


	private ArrayList<HistoryData> list;
	
	private String result;
	
	private Integer pageno;
	
	private String paginhtml;
	
	private Integer totalitem;
	
	private Integer totalpage;

	public Integer getTotalitem() {
		return totalitem;
	}

	public void setTotalitem(Integer totalitem) {
		this.totalitem = totalitem;
	}

	public Integer getTotalpage() {
		return totalpage;
	}

	public void setTotalpage(Integer totalpage) {
		this.totalpage = totalpage;
	}

	public ArrayList<HistoryData> getList() {
		return list;
	}

	public void setList(ArrayList<HistoryData> list) {
		this.list = list;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Integer getPageno() {
		return pageno;
	}

	public void setPageno(Integer pageno) {
		this.pageno = pageno;
	}

	public String getPaginhtml() {
		return paginhtml;
	}

	public void setPaginhtml(String paginhtml) {
		this.paginhtml = paginhtml;
	}
	
}
