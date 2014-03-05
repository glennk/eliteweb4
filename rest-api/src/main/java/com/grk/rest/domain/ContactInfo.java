package com.grk.rest.domain;

import com.grk.core.domain.Parent;

public class ContactInfo {
	private String name;
	private String phone;
	private String email;

	public static ContactInfo copyFromParent(Parent p) {
		ContactInfo ci = new ContactInfo();
		if (p != null) {
			ci.name = p.getName();
			ci.email = p.getEmail();
			ci.phone = p.getPhone();
		}
		return ci;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "ContactInfo [name=" + name + ", phone=" + phone + ", email="
				+ email + "]";
	}
}