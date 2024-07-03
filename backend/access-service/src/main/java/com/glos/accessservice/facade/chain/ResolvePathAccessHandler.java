package com.glos.accessservice.facade.chain;

import com.glos.accessservice.facade.chain.base.AccessHandler;
import com.glos.accessservice.facade.chain.base.AccessRequest;
import com.pathtools.Path;
import com.pathtools.PathParser;
import com.pathtools.exception.InvalidPathFormatException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public class ResolvePathAccessHandler extends AccessHandler {

    @Override
    public boolean check(AccessRequest request) throws InvalidPathFormatException {
        super.check(request);
        final Map<String, Object> data = request.getData();
        final Path path = PathParser.getInstance().parse(request.getPath());
        data.put("path", path);
        return checkNext(request);
    }

}
