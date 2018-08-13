package com.takipi.common.api.result.view;

import java.util.List;

import com.takipi.common.api.result.intf.ApiResult;

public class ViewsResult implements ApiResult {
	public List<SummarizedView> views;

	public static class SummarizedView {
		public String id;
		public String name;
		public boolean shared;
	}
}
