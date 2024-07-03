package com.glos.accessservice.facade.chain;

import com.glos.accessservice.facade.chain.base.AccessHandler;
import com.glos.accessservice.facade.chain.base.AccessRequest;
import org.springframework.stereotype.Component;

@Component
public class InitAccessChain extends AccessHandler {

    public InitAccessChain(
            ResolvePathAccessHandler resolvePathAccessHandler,
            UserExistsAccessHandler userExistsAccessHandler,
            RepositoryAccessHandler repositoryAccessHandler,
            DirectoryAccessHandler directoryAccessHandler,
            FileAccessHandler fileAccessHandler,
            ArchiveAccessHandler archiveAccessHandler,
            GroupAvailableAccessHandler groupAvailableAccessHandler,
            UserAvailableAccessHandler userAvailableAccessHandler
    ) {
        add(resolvePathAccessHandler);
        add(userExistsAccessHandler);
        add(repositoryAccessHandler);
        add(directoryAccessHandler);
        add(fileAccessHandler);
        add(archiveAccessHandler);
        add(groupAvailableAccessHandler);
        add(userAvailableAccessHandler);
    }

    @Override
    public boolean check(AccessRequest request) {
        super.check(request);
        return checkNext(request);
    }
}
