package com.takipi.api.client.request.team;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.takipi.api.client.data.team.ServiceUsers;
import com.takipi.api.client.data.team.ServiceUsers.TeamMember;
import com.takipi.api.client.data.team.ServiceUsersResponseMessage;
import com.takipi.api.client.request.ServiceRequest;
import com.takipi.api.core.request.intf.ApiDeleteRequest;

import java.util.List;

public class DeleteTeamMembersRequest extends ServiceRequest implements ApiDeleteRequest<ServiceUsersResponseMessage>
{
	private final ServiceUsers serviceUsers;
	
	DeleteTeamMembersRequest(String serviceId, ServiceUsers serviceUsers) {
		super(serviceId);
		
		this.serviceUsers = serviceUsers;
	}
	
	@Override
	public String postData() {
		return ((new Gson()).toJson(serviceUsers));
	}
	
	@Override
	public String urlPath() {
		return baseUrlPath() + "/team";
	}
	
	@Override
	public Class<ServiceUsersResponseMessage> resultClass() {
		return ServiceUsersResponseMessage.class;
	}
	
	public static Builder newBuilder() {
		return new Builder();
	}
	
	public static class Builder extends ServiceRequest.Builder {
		private List<String> usersToRemove = Lists.newArrayList();
		
		Builder() {
		
		}
		
		@Override
		public Builder setServiceId(String serviceId) {
			super.setServiceId(serviceId);
			
			return this;
		}
		
		public DeleteTeamMembersRequest.Builder addTeamMemberToDelete(String email) {
			this.usersToRemove.add(email);
			
			return this;
		}
		
		@Override
		protected void validate()
		{
			super.validate();
			
			if (this.usersToRemove.size() == 0)
			{
				throw new IllegalArgumentException("Request is empty");
			}
			
			for (String email : usersToRemove)
			{
				if (Strings.isNullOrEmpty(email))
				{
					throw new IllegalArgumentException("User email cannot be empty");
				}
			}
		}
		
		public DeleteTeamMembersRequest build() {
			validate();
			
			ServiceUsers serviceUsers = new ServiceUsers();
			serviceUsers.team_members = Lists.newArrayList();
			
			for (String email: this.usersToRemove)
			{
				TeamMember teamMember = new TeamMember();
				teamMember.email = email;
				
				serviceUsers.team_members.add(teamMember);
			}
			
			return new DeleteTeamMembersRequest(serviceId, serviceUsers);
		}
	}
}

