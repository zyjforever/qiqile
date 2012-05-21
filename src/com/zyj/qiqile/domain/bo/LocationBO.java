package com.zyj.qiqile.domain.bo;

public class LocationBO {
	String Code;

	String Address;
	String CountryName;
	String CountryCode;
	String AdministrativeAreaName;
	String LocalityName;
	String DependentLocalityName;
	String AddressLine;

	Double North;
	Double South;
	Double East;
	Double West;

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getCountryName() {
		return CountryName;
	}

	public void setCountryName(String countryName) {
		CountryName = countryName;
	}

	public String getCountryCode() {
		return CountryCode;
	}

	public void setCountryCode(String countryCode) {
		CountryCode = countryCode;
	}

	public String getAdministrativeAreaName() {
		return AdministrativeAreaName;
	}

	public void setAdministrativeAreaName(String administrativeAreaName) {
		AdministrativeAreaName = administrativeAreaName;
	}

	public String getLocalityName() {
		return LocalityName;
	}

	public void setLocalityName(String localityName) {
		LocalityName = localityName;
	}

	public String getDependentLocalityName() {
		return DependentLocalityName;
	}

	public void setDependentLocalityName(String dependentLocalityName) {
		DependentLocalityName = dependentLocalityName;
	}

	public String getAddressLine() {
		return AddressLine;
	}

	public void setAddressLine(String addressLine) {
		AddressLine = addressLine;
	}

	public Double getNorth() {
		return North;
	}

	public void setNorth(Double north) {
		North = north;
	}

	public Double getSouth() {
		return South;
	}

	public void setSouth(Double south) {
		South = south;
	}

	public Double getEast() {
		return East;
	}

	public void setEast(Double east) {
		East = east;
	}

	public Double getWest() {
		return West;
	}

	public void setWest(Double west) {
		West = west;
	}

}
