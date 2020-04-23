package com.ashcorps.consumerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchUsersResponse implements Parcelable {

	@SerializedName("total_count")
	private int totalCount;

	@SerializedName("incomplete_results")
	private boolean incompleteResults;

	@SerializedName("items")
	private ArrayList<UserItems> items;

	protected SearchUsersResponse(Parcel in) {
		totalCount = in.readInt();
		incompleteResults = in.readByte() != 0;
		items = in.createTypedArrayList(UserItems.CREATOR);
	}

	public static final Creator<SearchUsersResponse> CREATOR = new Creator<SearchUsersResponse>() {
		@Override
		public SearchUsersResponse createFromParcel(Parcel in) {
			return new SearchUsersResponse(in);
		}

		@Override
		public SearchUsersResponse[] newArray(int size) {
			return new SearchUsersResponse[size];
		}
	};

	public void setTotalCount(int totalCount){
		this.totalCount = totalCount;
	}

	public int getTotalCount(){
		return totalCount;
	}

	public void setIncompleteResults(boolean incompleteResults){
		this.incompleteResults = incompleteResults;
	}

	public boolean isIncompleteResults(){
		return incompleteResults;
	}

	public void setItems(ArrayList<UserItems> items){
		this.items = items;
	}

	public ArrayList<UserItems> getItems(){
		return items;
	}

	@Override
 	public String toString(){
		return 
			"SearchUsersResponse{" + 
			"total_count = '" + totalCount + '\'' + 
			",incomplete_results = '" + incompleteResults + '\'' + 
			",items = '" + items + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeInt(totalCount);
		parcel.writeByte((byte) (incompleteResults ? 1 : 0));
		parcel.writeTypedList(items);
	}


}