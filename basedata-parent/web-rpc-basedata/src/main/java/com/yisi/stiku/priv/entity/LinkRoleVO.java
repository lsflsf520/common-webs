package com.yisi.stiku.priv.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.yisi.stiku.common.bean.BaseEntity;
import com.yisi.stiku.priv.constant.LinkType;

public class LinkRoleVO extends BaseEntity<Integer> implements Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Set<Integer/*roleId*/> roleIds = new HashSet<Integer>();
	
	private int id;
	private String name;
	private String code;
	private String link;
	private LinkType type;
	private int orderRank;
	private String projectName;
	private Integer parentId;
	private boolean isShare;
	private boolean needDataCheck;
	private List<LinkRoleVO> children = new ArrayList<LinkRoleVO>(15);
	
	private boolean isVirtualMenu;
	
	public LinkRoleVO() {
		this(false);
	}
	
	public LinkRoleVO(boolean isVirtualMenu){
		this.isVirtualMenu = isVirtualMenu;
	}

	public Set<Integer> getRoleIds() {
		return Collections.unmodifiableSet(roleIds);
	}

	public void addRoleId(Integer roleId) {
		roleIds.add(roleId);
	}
	
	public void addRoleIds(Set<Integer> roleIds){
		this.roleIds.addAll(roleIds);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public LinkType getType() {
		return type;
	}

	public void setType(LinkType type) {
		this.type = type;
	}

	public int getOrderRank() {
		return orderRank;
	}

	public void setOrderRank(int orderRank) {
		this.orderRank = orderRank;
	}

	public List<LinkRoleVO> getChildren() {
		return Collections.unmodifiableList(children);
	}

	public void addChild(LinkRoleVO child) {
		this.children.add(child);
	}
	
	public void addChilren(List<LinkRoleVO> children){
		this.children.addAll(children);
	}
	
	public void removeChild(LinkRoleVO child){
		this.children.remove(child);
	}
	
	public void clearChildren(){
		this.children.clear();
	}

	public boolean isVirtualMenu() {
		return isVirtualMenu;
	}
	
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public boolean isShare() {
		return isShare;
	}

	public void setShare(boolean isShare) {
		this.isShare = isShare;
	}

	public boolean isNeedDataCheck() {
		return needDataCheck;
	}

	public void setNeedDataCheck(boolean needDataCheck) {
		this.needDataCheck = needDataCheck;
	}

	@Override
	public Integer getPK() {
		return this.id;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		LinkRoleVO linkRole = new LinkRoleVO();
		linkRole.setId(this.id);
		linkRole.setCode(this.code);
		linkRole.setLink(this.link);
		linkRole.setName(this.name);
		linkRole.setNeedDataCheck(this.needDataCheck);
		linkRole.setOrderRank(this.orderRank);
		linkRole.setParentId(this.parentId);
		linkRole.setProjectName(this.projectName);
		linkRole.setShare(this.isShare);
		linkRole.setType(this.type);
		linkRole.addRoleIds(this.getRoleIds());
		
		return linkRole;
	}
	
	@Override
	public String toString() {
		return "{id=" + this.getId() + ",name='" + this.getName() + "'}";
	}
}
