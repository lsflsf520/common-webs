package com.yisi.stiku.intg.entity;

import java.util.Date;

import com.yisi.stiku.common.bean.BaseEntity;

public class Integration extends BaseEntity<Integer> {

	private Integer id;

	private String repoName;

	private String envName;

	private String devDemandIds;

	private String conflictBranch;

	private String waitResolveBranch;

	private Byte inRelease;

	private String releaser;

	private String releaseProject;

	private Date lastReleaseTime;

	private String outputFile;

	public Integer getId() {

		return id;
	}

	public void setId(Integer id) {

		this.id = id;
	}

	public String getRepoName() {

		return repoName;
	}

	public void setRepoName(String repoName) {

		this.repoName = repoName == null ? null : repoName.trim();
	}

	public String getEnvName() {

		return envName;
	}

	public void setEnvName(String envName) {

		this.envName = envName == null ? null : envName.trim();
	}

	public String getDevDemandIds() {

		return devDemandIds;
	}

	public void setDevDemandIds(String devDemandIds) {

		this.devDemandIds = devDemandIds == null ? null : devDemandIds.trim();
	}

	public String getConflictBranch() {

		return conflictBranch;
	}

	public void setConflictBranch(String conflictBranch) {

		this.conflictBranch = conflictBranch == null ? null : conflictBranch.trim();
	}

	public String getWaitResolveBranch() {

		return waitResolveBranch;
	}

	public void setWaitResolveBranch(String waitResolveBranch) {

		this.waitResolveBranch = waitResolveBranch == null ? null : waitResolveBranch.trim();
	}

	public Byte getInRelease() {

		return inRelease;
	}

	public void setInRelease(Byte inRelease) {

		this.inRelease = inRelease;
	}

	public String getReleaser() {

		return releaser;
	}

	public void setReleaser(String releaser) {

		this.releaser = releaser;
	}

	public Date getLastReleaseTime() {

		return lastReleaseTime;
	}

	public void setLastReleaseTime(Date lastReleaseTime) {

		this.lastReleaseTime = lastReleaseTime;
	}

	public String getReleaseProject() {

		return releaseProject;
	}

	public void setReleaseProject(String releaseProject) {

		this.releaseProject = releaseProject;
	}

	public String getOutputFile() {

		return outputFile;
	}

	public void setOutputFile(String outputFile) {

		this.outputFile = outputFile;
	}

	@Override
	public Integer getPK() {

		return id;
	}
}