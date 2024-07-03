package com.glos.api.userservice.facade;

import com.glos.api.userservice.client.GroupAPIClient;
import com.glos.api.userservice.client.UserAPIClient;
import com.glos.api.userservice.entities.AccessType;
import com.glos.api.userservice.entities.Group;
import com.glos.api.userservice.entities.User;
import com.glos.api.userservice.exeptions.ResourceNotFoundException;
import com.glos.api.userservice.exeptions.UnsupportedDeleteResourceException;
import com.glos.api.userservice.responseDTO.GroupFilterRequest;
import com.glos.api.userservice.responseDTO.Page;
import com.glos.api.userservice.responseMappers.UserDTOMapper;
import com.glos.api.userservice.utils.Constants;
import com.glos.api.userservice.utils.MapUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Service
public class GroupAPIFacade {

    private final GroupAPIClient groupAPIClient;
    private final UserAPIClient userAPIClient;
    private final UserDTOMapper userDTOMapper;

    public GroupAPIFacade(
            GroupAPIClient groupAPIClient,
            UserAPIClient userAPIClient,
            UserDTOMapper userDTOMapper
    ) {
        this.groupAPIClient = groupAPIClient;
        this.userAPIClient = userAPIClient;
        this.userDTOMapper = userDTOMapper;
    }

    public ResponseEntity<Group> putGroup(Group group, String ownerUsername, String groupName) {
        Optional<Group> groupOtp = getByOwnerAndName(ownerUsername, groupName);
        group.setName(groupName);
        if (group.getOwner() == null) {
            group.setOwner(new User());
            group.getOwner().setUsername(ownerUsername);
        }
        assignGroup(group);
        if (groupOtp.isEmpty()) {
            return groupAPIClient.createGroup(group);
        } else {
            Group found = groupOtp.get();
            return groupAPIClient.updateGroup(found.getId(), group);
        }
    }

    private void assignGroup(Group group) {
        group.setOwner(getUserByUsername(group.getOwner().getUsername()));
        group.setUsers(group.getUsers().stream()
                .map(x -> getUserByUsername(x.getUsername()))
                .toList());
    }

    private User getUserByUsername(String username) {
        ResponseEntity<User> owner = userAPIClient.getUserByUsername(username);
        if (owner.getStatusCode().is2xxSuccessful()) {
            return owner.getBody();
        }
        throw new ResourceNotFoundException("User not found");
    }

    public Page<Group> getAll(Map<String, Object> params) {
        ResponseEntity<Page<Group>> response = groupAPIClient.getAllGroups(params);
        Page<Group> page = response.getBody();
        page.setSortPattern((String) params.getOrDefault("sort", "id,asc"));
        return page;
    }

    public Page<Group> getAllFilter(GroupFilterRequest request) {
        Map<String, Object> map = MapUtils.map(request);
        ResponseEntity<Page<Group>> response = groupAPIClient.getGroupsByFilters(map);
        Page<Group> page = response.getBody();

        if(request.getSort() != null)
        {
            page.setSortPattern(request.getSort());
        }

        return page;
    }

    public Optional<Group> getById(Long id) {
        ResponseEntity<Group> response = groupAPIClient.getGroupById(id);
        return Optional.ofNullable(response.getBody());
    }

    public Page<Group> getAllByOwner(String username, GroupFilterRequest filter) {
        User user = new User();
        user.setUsername(username);
        filter.setOwner(user);
        Map<String, Object> map = MapUtils.map(filter);
        Page<Group> groups = groupAPIClient.getGroupsByFilters(map).getBody();
        groups.setSortPattern(filter.getSort());
        return groups;
    }

    public Optional<Group> getByOwnerAndName(String username, String groupName) {
        GroupFilterRequest filter = new GroupFilterRequest();
        filter.setName(groupName);
        User user = new User();
        user.setUsername(username);
        filter.setOwner(user);
        Map<String, Object> map = MapUtils.map(filter);
        ResponseEntity<Page<Group>> response = groupAPIClient.getGroupsByFilters(map);
        Optional<Group> opt = response.getBody().stream().findFirst();
        return opt;
    }

    public ResponseEntity<Group> appendUser(String ownerUsername, String groupName, String username) {
        Group group = getByOwnerAndName(ownerUsername, groupName)
                .orElseThrow(() -> new ResourceNotFoundException("Group is not found"));
        User user = getUserByUsername(username);
        if (group.getUsers() == null) {
            group.setUsers(new ArrayList<>());
        }
        group.getUsers().add(user);
        return groupAPIClient.updateGroup(group.getId(), group);
    }

    public ResponseEntity<Group> removeUser(String ownerUsername, String groupName, String username) {
        Group group = getByOwnerAndName(ownerUsername, groupName)
                .orElseThrow(() -> new ResourceNotFoundException("Group is not found"));
        User user = getUserByUsername(username);
        group.getUsers().removeIf(u -> u.getUsername().equals(user.getUsername()));
        return groupAPIClient.updateGroup(group.getId(), group);
    }

    public void deleteByOwnerAndName(String username, String groupName) {
        if (Constants.FRIENDS_GROUP_NAME.equals(groupName)) {
            throw new UnsupportedDeleteResourceException("Unsupported delete group '%s'".formatted(Constants.FRIENDS_GROUP_NAME));
        }
        Group group = getByOwnerAndName(username, groupName)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
        groupAPIClient.deleteGroup(group.getId());
    }
}
