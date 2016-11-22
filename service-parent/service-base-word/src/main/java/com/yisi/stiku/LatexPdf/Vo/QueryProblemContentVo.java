package com.yisi.stiku.LatexPdf.Vo;

import java.util.concurrent.Future;

public class QueryProblemContentVo {
	
	private ProblemShaVo problemShaVo;
	private Future<ProblemShaVo> futureProblemShaVo;
	
	public QueryProblemContentVo(ProblemShaVo queryResult,
			Future<ProblemShaVo> futureProblemShaVo) {
		super();
		this.problemShaVo = queryResult;
		this.futureProblemShaVo = futureProblemShaVo;
	}
	public QueryProblemContentVo(ProblemShaVo queryResult) {
		super();
		this.problemShaVo = queryResult;
	}
	public ProblemShaVo getProblemShaVo() {
		return problemShaVo;
	}
	public void setProblemShaVo(ProblemShaVo queryResult) {
		this.problemShaVo = queryResult;
	}
	public Future<ProblemShaVo> getFutureProblemShaVo() {
		return futureProblemShaVo;
	}
	public void setFutureProblemShaVo(Future<ProblemShaVo> futureProblemShaVo) {
		this.futureProblemShaVo = futureProblemShaVo;
	}
	
	
}
