package com.glos.accessservice.facade.chain;

import com.glos.accessservice.clients.GroupClient;
import com.accesstools.AccessNode;
import com.accesstools.AccessNodeType;
import com.accesstools.AccessUtils;
import com.glos.accessservice.entities.AccessType;
import com.glos.accessservice.entities.Group;
import com.glos.accessservice.entities.User;
import com.glos.accessservice.exeptions.ResourceNotFoundException;
import com.glos.accessservice.exeptions.ResponseEntityException;
import com.glos.accessservice.facade.chain.base.AccessHandler;
import com.glos.accessservice.facade.chain.base.AccessRequest;
import com.glos.accessservice.responseDTO.Page;
import com.glos.accessservice.utils.MapUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GroupAvailableAccessHandler extends AccessHandler {

    private final GroupClient groupClient;

    public GroupAvailableAccessHandler(GroupClient groupClient) {
        this.groupClient = groupClient;
    }

    @Override
    public boolean check(AccessRequest request) {
        super.check(request);
        final Map<String, Object> data = request.getData();
        final User owner = (User) data.get("owner");
        final User user = (User) data.get("user");

        final Set<AccessNode> nodes = (Set<AccessNode>) data.get("accessNodes");

        if (nodes == null || nodes.isEmpty()) {
            List<Group> groupsWithUser = getGroupByOwnerUsername(owner.getUsername());
            groupsWithUser = groupsWithUser.stream()
                    .filter(x -> x.getUsers().stream().anyMatch(u -> u.getUsername().equals(user.getUsername())))
                    .toList();

            if (!groupsWithUser.isEmpty()) {
                final List<AccessType> accessTypes = (List<AccessType>) data.get("accessTypes");
                final Set<AccessNode> accessNodes = new HashSet<>();
                for(Group group : groupsWithUser) {
                    List<AccessNode> accessNodesWithGroup = accessTypes.stream()
                            .map(x -> AccessNode.builder(x.getName()).build())
                            .filter(x -> AccessUtils.isType(x, AccessNodeType.GROUP))
                            .toList();
                    accessNodesWithGroup = accessNodesWithGroup.stream()
                            .filter(x -> AccessUtils.checkAndReadOnly(x, group.getName(), request.isReadOnly()))
                            .toList();
                    accessNodes.addAll(accessNodesWithGroup);
                }
                data.put("accessNodes", accessNodes);
            }
        }

        return checkNext(request);
    }

    private List<Group> getGroupByOwnerUsername(String username) {
        Group filter = new Group();
        User owner = new User();
        owner.setUsername(username);
        filter.setOwner(owner);
        Map<String, Object> map = MapUtils.map(filter);
        map.put("size", 50);
        ResponseEntity<Page<Group>> groupResponse = groupClient.getGroupsByFilters(map);
        if (!groupResponse.getStatusCode().is2xxSuccessful()) {
            throw new ResponseEntityException(groupResponse);
        } else if (!groupResponse.hasBody()) {
            throw new RuntimeException("Internal server error");
        }
        Page<Group> page = groupResponse.getBody();
        return page.getContent();
    }

}
