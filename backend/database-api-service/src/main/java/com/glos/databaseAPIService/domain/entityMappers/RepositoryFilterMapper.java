package com.glos.databaseAPIService.domain.entityMappers;

import com.glos.databaseAPIService.domain.entities.Repository;
import com.glos.databaseAPIService.domain.entities.User;
import com.glos.databaseAPIService.domain.filters.RepositoryFilter;
import com.glos.databaseAPIService.domain.mappers.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class RepositoryFilterMapper
        extends AbstractMapper<Repository, RepositoryFilter> {

    @Override
    protected void postDtoCopy(Repository source, RepositoryFilter destination) {
        if (source.getOwner() != null ) {
            if(source.getOwner().getId() != null) {
                destination.setOwnerId(source.getOwner().getId());
            }
            if(source.getOwner().getUsername() != null) {
                destination.setOwnerUsername(source.getOwner().getUsername());
            }
        }
    }

    @Override
    protected void postEntityCopy(RepositoryFilter source, Repository destination) {
        if (destination.getOwner() == null) {
            destination.setOwner(new User());
        }
        if (source.getOwnerId() != null) {
            destination.getOwner().setId(source.getOwnerId());
            destination.getOwner().setUsername(source.getOwnerUsername());
        }
    }
}
